package com.example.pruebademdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import c.e.p.util.HttpUtils;
import cz.msebera.android.httpclient.Header;

public class creaciondeusuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creaciondeusuario);

        //Comienza el codigo para colocar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        // Finaliza codigo icono action bar
    }

    // Comienza metodo boton crear usuario para ir a seleccion de habilidades
    public void validarusuario(View view) {
        final Intent validarusuario = new Intent(this, selec_habilidades.class);
        String Nombre = ((EditText) findViewById(R.id.Nombre)).getText().toString();
        String Apellido = ((EditText) findViewById(R.id.Apellido)).getText().toString();
        String Telefono = ((EditText) findViewById(R.id.Telefono)).getText().toString();
        String Documento = ((EditText) findViewById(R.id.Documento)).getText().toString();
        String fecha_nacimiento = ((EditText) findViewById(R.id.fecha_nacimiento)).getText().toString();
        String email = ((EditText) findViewById(R.id.email)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        RequestParams request = new RequestParams();
        request.add("nombre", Nombre);
        request.add("apellido", Apellido);
        request.add("telefono", Telefono);
        request.add("ci", Documento);
        request.add("fecha de nacimiento", fecha_nacimiento);
        request.add("email", email);
        request.add("password", password);
        request.add("calle","");
        request.add("numero de puerta","0");
        request.add("esquina 1","");
        request.add("esquina 2","");

        HttpUtils.put("/usuarios", request, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                startActivity(validarusuario);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
    }
}



