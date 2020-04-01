package com.example.pruebademdm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class selec_habilidades extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_habilidades);
    }
    // Comienza metodo boton crear usuario para ir a main_log
    public void mis_habilidades(View view) {
        Intent mis_habilidades = new Intent(this, main_log.class);
        startActivity(mis_habilidades);
        // Finaliza metodo boton crear usuario para ir a perfil de usuario
    }

}

