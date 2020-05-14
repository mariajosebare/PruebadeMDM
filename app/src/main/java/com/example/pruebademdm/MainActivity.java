package com.example.pruebademdm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

import com.example.pruebademdm.data.LoginDataSource;
import com.example.pruebademdm.data.LoginRepository;
import com.example.pruebademdm.data.model.LoggedInUser;
import com.example.pruebademdm.ui.home.HomeViewModel;

import javax.security.auth.login.LoginException;

public class MainActivity extends AppCompatActivity {

    public static final String CHANNEL_ID = "Canal1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Comienza el codigo para colocar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        // Finaliza codigo icono action bar

        createNotificationChannel();
    }


    // Comienza metodo boton crear usuario
    public void botcrearusuario(View view) {
        Intent botcrearusuario = new Intent(this, creaciondeusuario.class);
        startActivity(botcrearusuario);
        // Finaliza metodo boton crear usuario
    }

    // Comienza metodo boton Inicio
    public void botingresar(View view) {
        Intent botingresar = new Intent(this,Login.class);
        startActivity(botingresar);
    }
    // Finaliza metodo boton inicio

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Canal1";
            String description = "Canal por defecto";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}





