package com.example.pruebademdm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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


