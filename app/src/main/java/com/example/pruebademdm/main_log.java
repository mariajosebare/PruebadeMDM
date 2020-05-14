package com.example.pruebademdm;


import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import c.e.p.util.HttpUtils;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

import static com.example.pruebademdm.Login.MyPREFERENCES;
import static com.example.pruebademdm.Login.NECESIDAD_SELECCIONADA;
import static com.example.pruebademdm.Login.USUARIO_APELLIDO;
import static com.example.pruebademdm.Login.USUARIO_CHAT;
import static com.example.pruebademdm.Login.USUARIO_ID;
import static com.example.pruebademdm.Login.USUARIO_NOMBRE;
import static com.example.pruebademdm.MainActivity.CHANNEL_ID;


public class main_log extends AppCompatActivity {

        Boolean procesando = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            final main_log pantalla = this;
            final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_log);
            String nombre_usuario  = sharedPreferences.getString(USUARIO_NOMBRE, "");
            String apellido_usuario = sharedPreferences.getString(USUARIO_APELLIDO, "");
            TextView texto_nombre = findViewById(R.id.user_name);
            TextView texto_apellido = findViewById(R.id._apellido);
            texto_nombre.setText(nombre_usuario);
            texto_apellido.setText(apellido_usuario);

            HttpUtils.get("/habilidades", null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONArray response_habilidades=new JSONArray(new String(responseBody));
                        Spinner categorias = findViewById(R.id.categorias);
                        List<Habilidad> habilidades = new ArrayList<Habilidad>();
                        for (int i = 0; i < response_habilidades.length(); i++) {
                            JSONObject habilidad = response_habilidades.getJSONObject(i);
                            habilidades.add(new Habilidad(habilidad.getString("ID_habilidad"),habilidad.getString("nombre")));
                        }
                        ArrayAdapter<Habilidad> dataAdapter = new ArrayAdapter<Habilidad>(pantalla,
                                android.R.layout.simple_spinner_item, habilidades);
                        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        categorias.setAdapter(dataAdapter);

                        //Comienza el codigo para colocar icono en el action bar
                        getSupportActionBar().setDisplayShowHomeEnabled(true);
                        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
                        // Finaliza codigo icono action bar
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            });

            // Crear Handler
            final Handler handler = new Handler();
            Runnable runnableCode = new Runnable() {
                @Override
                public void run() {
                if (! procesando) {
                    procesando = true;
                    String id_usuario = sharedPreferences.getString(USUARIO_ID, "");
                    HttpUtils.get("/chat/" + id_usuario + "/alertas", null, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                            try {
                                JSONArray alertasJSON = new JSONArray(new String(responseBody));
                                for (int i = 0; i < alertasJSON.length(); i++) {
                                    JSONObject alertaJSON = alertasJSON.getJSONObject(i);
                                    String id_usuario_chat = alertaJSON.getString("ID_usuario_1");

                                    Intent intent = new Intent(pantalla, chat.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.putExtra(USUARIO_CHAT, id_usuario_chat);
                                    PendingIntent pendingIntent = PendingIntent.getActivity(pantalla, 0, intent, 0);

                                    NotificationCompat.Builder builder = new NotificationCompat.Builder(pantalla, CHANNEL_ID)
                                            .setSmallIcon(R.drawable.medoymanarosa)
                                            .setContentTitle("Tienes un mensaje!")
                                            .setContentText("Haz click aquí para chatear ahora")
                                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                            // Set the intent that will fire when the user taps the notification
                                            .setContentIntent(pendingIntent)
                                            .setAutoCancel(true);

                                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(pantalla);

                                    notificationManager.notify(i, builder.build());
                                }
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
                handler.postDelayed(this, 15000);
                }
            };
            handler.postDelayed(runnableCode, 3000);
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
startActivity(new Intent(this, Activitymod_perfil.class));
            } else if(id == R.id.ir_chat){
                Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
startActivity(new Intent(this, chat.class));
            } else if(id == R.id.salir){
                Toast.makeText(this, "Cerrar sesión", Toast.LENGTH_SHORT).show();
startActivity(new Intent(this, MainActivity.class));
            } else if(id == R.id.notif){
                Toast.makeText(this, "Notificaciones", Toast.LENGTH_SHORT).show();
startActivity(new Intent(this, Notificaciones.class));
            } else if(id == R.id.publi){
                Toast.makeText(this, "Mis publicaciones", Toast.LENGTH_SHORT).show();
startActivity(new Intent(this, Mis_publicaciones.class));
            } else if(id == R.id.habil){
                Toast.makeText(this, "Mis habilidades", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, selec_habilidades.class));
            }

            return super.onOptionsItemSelected(item);
        }

        //FINALIZA ACTION BAR

        // Comienza metodo boton buscar necesidad
        public void busqueda_necesidades (View view) {
            final Intent busqueda_necesidades = new Intent(this, main_resultadosmatch.class);
            final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            String necesidad = ((EditText) findViewById(R.id.escribir_necesidad)).getText().toString();
            String idHabilidad = ((Habilidad) ((Spinner) findViewById(R.id.categorias)).getSelectedItem()).getId();
            String idUsuario = sharedPreferences.getString(USUARIO_ID, "");
            RequestParams request = new RequestParams();
            request.add("ID_usuario", idUsuario);
            request.add("ID_habilidad", idHabilidad);
            request.add("necesidad",necesidad);
            HttpUtils.put("/necesidades", request, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONObject response_necesidad = new JSONObject(new String(responseBody));
                        String necesidadId = response_necesidad.getString("ID_necesidad");
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(NECESIDAD_SELECCIONADA, necesidadId);
                        editor.commit();
                        startActivity(busqueda_necesidades);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toasty.error(getApplicationContext(),
                            "Ha ocurrido un error",
                            Toast.LENGTH_SHORT,
                            true
                    ).show();
                }
            });


        }

        class Habilidad {
            String _id;
            String _nombre;

            Habilidad (String id, String nombre) {
                _id = id;
                _nombre = nombre;
            }

            public String getId(){
                return _id;
            }

            public String toString(){
                return _nombre;
            }
        }
    }



