package com.example.pruebademdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class Mis_publicaciones extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_publicaciones);

        //Comienza el codigo para colocar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        // Finaliza codigo icono action bar
    }

    //ACTION BAR
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.modificar) {
            Toast.makeText(this, "Editar perfil", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Activitymod_perfil.class));
        } else if (id == R.id.home_1) {
            Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, main_log.class));
        } else if (id == R.id.ir_chat) {
            Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, chat.class));
        } else if (id == R.id.salir) {
            Toast.makeText(this, "Cerrar sesión", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        } else if (id == R.id.valoracion) {
            Toast.makeText(this, "Mis valoraciones", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, perfil_usuario.class));
        } else if (id == R.id.publi) {
            Toast.makeText(this, "Mis publicaciones", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Mis_publicaciones.class));
        } else if (id == R.id.habil) {
            Toast.makeText(this, "Mis habilidades", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, selec_habilidades.class));
        }

        return super.onOptionsItemSelected(item);
    }
    //FINALIZA ACTION BAR
}
