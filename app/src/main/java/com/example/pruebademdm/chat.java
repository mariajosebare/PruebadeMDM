package com.example.pruebademdm;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.service.autofill.UserData;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import c.e.p.util.HttpUtils;
import cz.msebera.android.httpclient.Header;

public class chat extends AppCompatActivity {

    private EditText editText;
    ArrayList<String> listaMensajes=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final chat pantalla = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        //Comienza el codigo para colocar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        // Finaliza codigo icono action bar

        // Crear Handler
        Handler handler = new Handler();
            Runnable runnableCode = new Runnable() {
            @Override
            public void run() {
                HttpUtils.get("/chat/1/3", null, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            JSONArray mensajes=new JSONArray(new String(responseBody));
                            for (int i = 0; i < mensajes.length(); i++) {
                                JSONObject mensaje = mensajes.getJSONObject(i);
                                listaMensajes.add(mensaje.getString("mensaje"));
                            }
                            ListView lista = findViewById(R.id.listaMensaje);
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(pantalla, android.R.layout.simple_list_item_1, listaMensajes);
                            lista.setAdapter(adapter);
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                         }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                        error.printStackTrace();
                    }
                });

            }
        };
        handler.postDelayed(runnableCode, 3000);
    }

    public void enviarMensaje(View view) {
        String mensaje = ((EditText) findViewById(R.id.textMensaje)).getText().toString();
        RequestParams request = new RequestParams();
        request.add("mensaje", mensaje);
        HttpUtils.put("/chat/1/3", request, new AsyncHttpResponseHandler() {
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
        private UserData nombreUsuario; // datos del usuario que envio el mensaje
        private boolean usuarioActual; // el mensaje es enviado por nosotros?

        public Mensaje(String texto, UserData nombreUsuario, boolean usuarioActual) {
            this.texto = texto;
            this.nombreUsuario = nombreUsuario;
            this.usuarioActual = usuarioActual;
        }

        public String getText() {
            return texto;
        }

        public UserData getUserData() {
            return nombreUsuario;
        }

        public boolean isBelongsToCurrentUser() {
            return usuarioActual;
        };
    }

     // MessageAdapter.java
     class MessageAdapter extends BaseAdapter {
        List<Mensaje> mensajes = new ArrayList<Mensaje>();
        Context context;

        public MessageAdapter(Context context) {
            this.context = context;
        }

        public void add(Mensaje mensaje) {
            this.mensajes.add(mensaje);
            notifyDataSetChanged(); // to render the list we need to notify
        }

        @Override
        public int getCount() {
            return mensajes.size();
            }

        @Override
        public Object getItem(int i) {
            return mensajes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        // Creacion de la ListView (chat bubble)
        @RequiresApi(api = Build.VERSION_CODES.P)
        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            MessageViewHolder holder = new MessageViewHolder();
            LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            Mensaje mensaje = mensajes.get(i);
                if (mensaje.isBelongsToCurrentUser()) { // mensaje enviado por nosotros, burbuja a la derecha
                    convertView = messageInflater.inflate(R.layout.my_mensaje, null);
                    holder.Mensaje = (TextView) convertView.findViewById(R.id.boxMensaje);
                    convertView.setTag(holder);
                    holder.Mensaje.setText(mensaje.getText());
                }
                else { // mensaje enviado por otra persona, burbuja a la izquierda
                    assert messageInflater != null;
                    convertView = messageInflater.inflate(R.layout.su_mensaje, null);
                    holder.Usuario = (TextView) convertView.findViewById(R.id.Usuario);
                    holder.Mensaje = (TextView) convertView.findViewById(R.id.boxMensaje);
                    convertView.setTag(holder);
                    holder.Usuario.setText(mensaje.getUserData().getId());
                    holder.Mensaje.setText(mensaje.getText());
                }
                return convertView;
        }

        class MessageViewHolder {
        TextView Usuario;
        TextView Mensaje;
        }
     }
}




