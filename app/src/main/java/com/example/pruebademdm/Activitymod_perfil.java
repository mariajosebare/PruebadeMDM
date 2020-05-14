package com.example.pruebademdm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import c.e.p.util.HttpUtils;
import cz.msebera.android.httpclient.Header;

import static com.example.pruebademdm.Login.MyPREFERENCES;
import static com.example.pruebademdm.Login.USUARIO_ID;
import static com.example.pruebademdm.Login.CORREO_USUARIO;
import static com.example.pruebademdm.Login.CONTRASEÑA_CORREO;
import static com.example.pruebademdm.Login.USUARIO_NOMBRE;


public class Activitymod_perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitymod_perfil);
        // Se procede a realizar el el llamado de  datos del usuario para la implementacion del cambio de contrasena
        final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        EditText correControl = findViewById(R.id.editText);
        String correoActual = sharedPreferences.getString(CORREO_USUARIO, "");
        correControl.setText(correoActual);
        //EditText nombreControl = findViewById(R.id.texnombre);
        //String nombreActual = sharedPreferences.getString(USUARIO_NOMBRE, "");
        //correControl.setText(nombreActual);
        //Finaliza metodo llamado de datos para cambios de contrasena

        //Comienza el codigo para colocar icono en el action bar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        // Finaliza codigo icono action bar
    }

    // Relizamos el codigo para poder modificar los datos de usuario de ser necesrio
    public void modificarUsuario(View view) {
        final Intent modificarUsuario = new Intent(this, perfil_usuario.class);
        final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        final String correo = ((EditText) findViewById(R.id.editText)).getText().toString();
        RequestParams request = new RequestParams();
        String nombre_usuario = ((EditText) findViewById(R.id.texnombre)).getText().toString();
        //String ci = ((EditText) findViewById(R.id.textci)).getText().toString();


        //request.add("ci", ci);
        request.add("nombre", nombre_usuario);


        String usuarioId = sharedPreferences.getString(USUARIO_ID, "");
        HttpUtils.post("/usuario/" + usuarioId, request, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject usuarioJSON = new JSONObject(new String(responseBody));
                    String password = usuarioJSON.getString("password");
                    String nombre = usuarioJSON.getString("nombre");


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(CONTRASEÑA_CORREO, password);
                    editor.putString(USUARIO_NOMBRE, nombre);
                    editor.commit();
                    startActivity(modificarUsuario);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
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
            startActivity(new Intent(this, selec_habilidades.class));
        } else if (id == R.id.ir_chat) {
            Toast.makeText(this, "Chat", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, chat.class));
        } else if (id == R.id.salir) {
            Toast.makeText(this, "Cerrar sesión", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    //FINALIZA ACTION BAR
}
