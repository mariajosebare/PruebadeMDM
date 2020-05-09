package com.example.pruebademdm;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import c.e.p.util.HttpUtils;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

import static com.example.pruebademdm.Login.MyPREFERENCES;
import static com.example.pruebademdm.Login.NECESIDAD_SELECCIONADA;
import static com.example.pruebademdm.Login.USUARIO_CHAT;

public class main_resultadosmatch  extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final main_resultadosmatch pantalla = this;
        final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_resultadosmatch);
        String necesidadId = sharedPreferences.getString(NECESIDAD_SELECCIONADA, "");
        HttpUtils.get("/matcheo/" + necesidadId, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject response_habilidades = new JSONObject(new String(responseBody));
                    ListView resultado = findViewById(R.id.resultado);
                    List<Necesidad> necesidades = new ArrayList<Necesidad>();
                    JSONArray necesidadesJSON = response_habilidades.getJSONArray("necesidades");
                    JSONObject publicacionJSON = response_habilidades.getJSONObject("publicacion");
                    String miDescripcion = publicacionJSON.getString("descripcion_necesidad");
                    String habilidad = publicacionJSON.getJSONObject("habilidad").getString("nombre");
                    for (int i = 0; i < necesidadesJSON.length(); i++) {
                        JSONObject necesidadJSON = necesidadesJSON.getJSONObject(i);
                        JSONObject usuarioJSON = necesidadJSON.getJSONObject("usuario");
                        Necesidad necesidad = new Necesidad();
                        necesidad.set_descripcion(necesidadJSON.getString("descripcion_necesidad"));
                        necesidad.set_idNecesidad(necesidadJSON.getString("ID_necesidad"));
                        necesidad.set_idUsuario(necesidadJSON.getString("ID_usuario"));
                        necesidad.set_nombreUsuario(usuarioJSON.getString("nombre"));
                        necesidad.set_apellidoUsuario(usuarioJSON.getString("apellido"));
                        necesidades.add(necesidad);

                        ArrayAdapter<Necesidad> dataAdapter = new ArrayAdapter<Necesidad>(pantalla,
                                android.R.layout.simple_list_item_1, necesidades);
                        resultado.setAdapter(dataAdapter);
                        resultado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                ir_al_chat((Necesidad) parent.getItemAtPosition(position));
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                //Comienza el codigo para colocar icono en el action bar
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setIcon(R.mipmap.ic_launcher);
                // Finaliza codigo icono action bar
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

        //Comienza el codigo para colocar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        // Finaliza codigo icono action bar
    }

    // Comienza metodo boton ir_al_chat
    public void ir_al_chat(Necesidad necesidad) {
        Intent ir_al_chat = new Intent(this, chat.class);
        final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USUARIO_CHAT, necesidad.get_idUsuario());
        editor.commit();
        startActivity(ir_al_chat);
        // Finaliza metodo boton para ir al chat
    }

    // Comienza metodo boton para regresar a la seleccion de necesidad
    public void mis_habilidades(View view) {
        Intent mis_habilidades = new Intent(this, main_log.class);
        startActivity(mis_habilidades);
        // Finaliza metodo boton para regresar a la seleccion de necesidad
    }

    class Necesidad {
        private String _idNecesidad;
        private String _descripcion;
        private String _idUsuario;
        private String _nombreUsuario;
        private String _apellidoUsuario;

        public String get_idNecesidad() {
            return _idNecesidad;
        }

        public void set_idNecesidad(String _idNecesidad) {
            this._idNecesidad = _idNecesidad;
        }

        public String get_descripcion() {
            return _descripcion;
        }

        public void set_descripcion(String _descripcion) {
            this._descripcion = _descripcion;
        }

        public String get_idUsuario() {
            return _idUsuario;
        }

        public void set_idUsuario(String _idUsuario) {
            this._idUsuario = _idUsuario;
        }

        public String get_nombreUsuario() {
            return _nombreUsuario;
        }

        public void set_nombreUsuario(String _nombreUsuario) {
            this._nombreUsuario = _nombreUsuario;
        }

        public String get_apellidoUsuario() {
            return _apellidoUsuario;
        }

        public void set_apellidoUsuario(String _apellidoUsuario) {
            this._apellidoUsuario = _apellidoUsuario;
        }


        Necesidad(String idNecesidad, String descripcion, String idUsuario, String nombre, String apellido) {
            _idNecesidad = idNecesidad;
            _descripcion = descripcion;
            _idUsuario = idUsuario;
            _nombreUsuario = nombre;
            _apellidoUsuario = apellido;
        }

        Necesidad() {

        }

        public String toString() {
            return _nombreUsuario + " " + _apellidoUsuario + ": " + _descripcion;
        }
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
startActivity(new Intent(this, chat.class));
        } else if(id == R.id.salir){
            Toast.makeText(this, "Cerrar sesión", Toast.LENGTH_SHORT).show();
startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    //FINALIZA ACTION BAR
}



