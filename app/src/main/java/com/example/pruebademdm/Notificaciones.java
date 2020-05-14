package com.example.pruebademdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Notificaciones extends AppCompatActivity {

    private Button btn_notif;
    NotificationCompat.Builder notificacion;
    private static final int idUnica = 2233;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notificaciones);

        btn_notif = (Button) findViewById(R.id.btn_notif);

        notificacion = new NotificationCompat.Builder(this);
        notificacion.setAutoCancel(true);

        btn_notif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notificacion.setSmallIcon(R.mipmap.ic_launcher);
                notificacion.setTicker("Nueva notificación");
                notificacion.setWhen(System.currentTimeMillis());
                notificacion.setContentTitle("Tienes una nueva notificación");
                notificacion.setContentText("Mensaje");


                Intent intent = new Intent(Notificaciones.this, chat.class);

                PendingIntent pendingIntent = PendingIntent.getActivity(Notificaciones.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notificacion.setContentIntent(pendingIntent);


                NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                nm.notify(idUnica, notificacion.build());
            }
        });
    }
}

