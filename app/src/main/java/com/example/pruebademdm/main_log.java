package com.example.pruebademdm;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONObject;

import c.e.p.util.HttpUtils;
import cz.msebera.android.httpclient.Header;

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
            HttpUtils.get("/necesidades", null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    JSONObject response_habilidades=new JSONObject(new String(responseBody));

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            }

        }
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_log);
            //COnexión con XML
            /*necesidad = (EditText)findViewById(R.id.textInput_necesidad);
            spinner1 = (Spinner)findViewById(R.id.categorias);

            //Asignar valores al spinner
            String [] Categorías = ("Arte","Mecánica","Fotografía","Electricidad","Cocina","Belleza")

            ArrayAdapter <String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item, Categorías);
            spinner1.setAdapter(adapter);*/
            //Comienza el codigo para colocar icono en el action bar
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
            // Finaliza codigo icono action bar
        }

        // Comienza metodo boton buscar necesidad
        public void busquedanece (View view) {
            Intent busquedanece = new Intent(this, main_resultadosmatch.class);
            startActivity(busquedanece);
            // Finaliza metodo boton buscar necesidad
        }

        public void main_log(View view) {
            final Intent main_log = new Intent(this, main_log.class);
            HttpUtils.get("/necesidades", null, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    JSONObject response_habilidades=new JSONObject(new String(responseBody));
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                }
            }
        }
    }

