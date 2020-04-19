package com.example.pruebademdm;


import android.content.Intent;
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

//public class main_log implements xml {
//}   @Override
//protected void onCreate(Bundle savedInstanceState) {
    //super.onCreate(savedInstanceState);
    //setContentView(R.layout.activity_main_log);
//}

    //private void setContentView(int activity_main_log) {
    //}
//}
    public class main_log extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            final main_log pantalla = this;
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_log);
            HttpUtils.get("/habilidades", null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    try {
                        JSONArray response_habilidades=new JSONArray(new String(responseBody));
                        Spinner categorias = findViewById(R.id.categorias);
                        List<String> habilidades = new ArrayList<String>();
                        for (int i = 0; i < response_habilidades.length(); i++) {
                            JSONObject habilidad = response_habilidades.getJSONObject(i);
                            habilidades.add(habilidad.getString("nombre"));
                        }
                        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(pantalla,
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
        }

        // Comienza metodo boton buscar necesidad
        public void busqueda_necesidades (View view) {
            final Intent busqueda_necesidades = new Intent(this, main_resultadosmatch.class);
            String necesidad = ((EditText) findViewById(R.id.escribir_necesidad)).getText().toString();
            RequestParams request = new RequestParams();
            request.add("id_usuario", "2");
            request.add("id_habilidad", "1");
            request.add("necesidad",necesidad);
            HttpUtils.put("/necesidades", request, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    startActivity(busqueda_necesidades);
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

            // Finaliza metodo boton buscar necesidad
        }


    }

