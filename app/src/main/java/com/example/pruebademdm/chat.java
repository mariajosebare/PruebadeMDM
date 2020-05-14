package com.example.pruebademdm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import c.e.p.util.HttpUtils;
import cz.msebera.android.httpclient.Header;

import static com.example.pruebademdm.Login.MyPREFERENCES;
import static com.example.pruebademdm.Login.USUARIO_CHAT;
import static com.example.pruebademdm.Login.USUARIO_CHAT_NOMBRE;
import static com.example.pruebademdm.Login.USUARIO_ID;
import static com.example.pruebademdm.Login.USUARIO_NOMBRE;

public class chat extends AppCompatActivity {

    private EditText editText;
    List<Mensaje> mensajes = new ArrayList<Mensaje>();
    Boolean procesando = false;
    String idMiUsuario;
    String idOtroUsuario;
    String nombreMiUsuario;
    String nombreOtroUsuario;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        idOtroUsuario = this.getIntent().getStringExtra(USUARIO_CHAT);
        nombreOtroUsuario = this.getIntent().getStringExtra(USUARIO_CHAT_NOMBRE);

        idMiUsuario = sharedPreferences.getString(USUARIO_ID, "");
        nombreMiUsuario = "Yo";

        //Comienza el codigo para colocar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        // Finaliza codigo icono action bar

        ListView lista = findViewById(R.id.listaMensaje);
        ArrayAdapter<Mensaje> adapter = new ArrayAdapter<Mensaje>(context, android.R.layout.simple_list_item_1, mensajes){
            @Override
            public View getView (int position, View convertView, ViewGroup parent) {
                //View v = super.getView(position, convertView, parent);
                MessageViewHolder holder = new MessageViewHolder();
                LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                Mensaje mensaje = mensajes.get(position);

                if (mensaje.esUsuarioActual()) {
                    convertView = messageInflater.inflate(R.layout.my_mensaje, null);
                    holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
                    convertView.setTag(holder);
                    holder.messageBody.setText(mensaje.getText());
                } else { // this message was sent by someone else so let's create an advanced chat bubble on the left
                    convertView = messageInflater.inflate(R.layout.su_mensaje, null);
                    holder.name = (TextView) convertView.findViewById(R.id.name);
                    holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
                    convertView.setTag(holder);

                    holder.name.setText(mensaje.nombreUsuario);
                    holder.messageBody.setText(mensaje.getText());
                }

                return convertView;
            }
        };
        lista.setAdapter(adapter);

        // Crear Handler
        final Handler handler = new Handler();
        Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                if (! procesando) {
                    procesando = true;
                    HttpUtils.get("/chat/" + idMiUsuario + "/" + idOtroUsuario, null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                JSONArray mensajesJSON = new JSONArray(new String(responseBody));
                                mensajes.clear();
                                for (int i = 0; i < mensajesJSON.length(); i++) {
                                    JSONObject mensajeJSON = mensajesJSON.getJSONObject(i);
                                    String texto = mensajeJSON.getString("mensaje");
                                    String idUsuario = mensajeJSON.getString("ID_usuario_1");
                                    Boolean miMensaje = idMiUsuario.equals(idUsuario);
                                    String nombreUsuario = miMensaje ? nombreMiUsuario : nombreOtroUsuario;
                                    Mensaje mensaje = new Mensaje(texto, nombreUsuario, miMensaje);
                                    mensajes.add(mensaje);
                                }
                                ListView lista = findViewById(R.id.listaMensaje);
                                ((ArrayAdapter<Mensaje>) lista.getAdapter()).notifyDataSetChanged();
                                lista.setSelection(lista.getCount() - 1);
                            }
                            catch (JSONException e){
                                e.printStackTrace();
                            }
                            finally {
                                procesando = false;
                            }
                        }
                        @Override
                        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                            error.printStackTrace();
                            procesando = false;
                        }
                    });
                }
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(runnableCode, 0);
    }

    public void enviarMensaje(View view) {
        String mensaje = ((EditText) findViewById(R.id.textMensaje)).getText().toString();
        RequestParams request = new RequestParams();
        request.add("mensaje", mensaje);
        HttpUtils.put("/chat/" + idMiUsuario + "/" + idOtroUsuario, request, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                ((EditText) findViewById(R.id.textMensaje)).setText("");
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
     }

    class Mensaje {
        private String texto; // cuerpo del mensaje
        private String nombreUsuario; // datos del usuario que envio el mensaje
        private boolean usuarioActual; // el mensaje es enviado por nosotros?

        public Mensaje(String texto, String nombreUsuario, boolean usuarioActual) {
            this.texto = texto;
            this.nombreUsuario = nombreUsuario;
            this.usuarioActual = usuarioActual;
        }

        public String getText() {
            return texto;
        }

        public String getUserData() {
            return nombreUsuario;
        }

        public boolean esUsuarioActual() {
            return usuarioActual;
        };

        public String toString() {
            return nombreUsuario + ": \n" + texto;
        }
    }

    class MessageViewHolder {
        public TextView name;
        public TextView messageBody;
    }

    //ACTION BAR
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();

        if(id == R.id.modificar){
            Toast.makeText(this, "Editar perfil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, selec_habilidades.class));
        } else if(id == R.id.ir_chat){
            Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, chat.class));
        } else if(id == R.id.salir){
            Toast.makeText(this, "Cerrar sesión", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    //FINALIZA ACTION BAR
}




