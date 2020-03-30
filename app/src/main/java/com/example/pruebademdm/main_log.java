package com.example.pruebademdm;


import android.os.Bundle;

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

<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
            //COnexión con XML
            necesidad = (EditText)findViewById(R.id.textInput_necesidad);
            spinner1 = (Spinner)findViewById(R.id.categorias);

            //Asignar valores al spinner
            String [] Categorías = ("Arte","Mecánica","Fotografía","Electricidad","Cocina","Belleza")

            ArrayAdapter <String> adapter = new ArrayAdapter<String>( this, android.R.layout.simple_spinner_dropdown_item, Categorías);
            spinner1.setAdapter(adapter);


=======
=======
>>>>>>> 8d5695102ee5aa7c840e50d067b56d73606e5f04
=======
>>>>>>> 8d5695102ee5aa7c840e50d067b56d73606e5f04
            //Comienza el codigo para colocar icono en el action bar
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setIcon(R.mipmap.ic_launcher);
            // Finaliza codigo icono action bar
<<<<<<< HEAD
<<<<<<< HEAD
>>>>>>> 8d5695102ee5aa7c840e50d067b56d73606e5f04
=======
>>>>>>> 8d5695102ee5aa7c840e50d067b56d73606e5f04
=======
>>>>>>> 8d5695102ee5aa7c840e50d067b56d73606e5f04
        }
    }
