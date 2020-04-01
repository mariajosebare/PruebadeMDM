package com.example.pruebademdm;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

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
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_log);

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
    }

