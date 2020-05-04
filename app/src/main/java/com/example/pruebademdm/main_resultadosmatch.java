package com.example.pruebademdm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
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

public class main_resultadosmatch  extends AppCompatActivity {

    public static final String MyPREFERENCES = "Preferencias" ;
    public static final String USUARIO_ID = "usuario_id";
    public static final String USUARIO_NOMBRE = "usuario_nombre";
    public static final String USUARIO_APELLIDO = "usuario_apellido";
    public static final String NECESIDAD_SELECCIONADA = "necesidad";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final main_resultadosmatch pantalla = this;
        final SharedPreferences sharedpreference = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_resultadosmatch);
        String necesidadId = sharedpreference.getString(NECESIDAD_SELECCIONADA, "");
        HttpUtils.get("/matcheo/" + necesidadId, null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject response_habilidades = new JSONObject(new String(responseBody));
                    ScrollView scroll_layout = findViewById(R.id.scrollViewmatch);
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
                        TextView texto = new TextView(pantalla);
                        texto.setText(necesidad.get_nombreUsuario() + "" + necesidad.get_apellidoUsuario() + "" + necesidad.get_descripcion() + "");
                        scroll_layout.addView(texto);


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

    public void matcheo(View view) {

    }

    // Comienza metodo boton ir_al_chat
    public void ir_al_chat(View view) {
        Intent ir_al_chat = new Intent(this, chat.class);
        startActivity(ir_al_chat);
        // Finaliza metodo boton para ir al chat
    }
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
    Necesidad()
    {

    }
}



