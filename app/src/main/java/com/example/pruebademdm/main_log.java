package com.example.pruebademdm;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

    public class main_log extends AppCompatActivity {

        private EditText necesidad;
        private Spinner spinner1;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_log);

            //COnexión con XML
            necesidad = (EditText)findViewById(R.id.textInput_necesidad);
            spinner1 = (Spinner)findViewById(R.id.categorias);

            //Asignar valores al spinner
            String [] Categorías = ("Arte","Mecánica","Fotografía","Electricidad","Cocina","Belleza");

            ArrayAdapter <String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item, Categorías);
            spinner1.setAdapter(adapter);


        }
        //Método del botón

        String seleccion = spinner1.getSelectedItem().toString();
    }

