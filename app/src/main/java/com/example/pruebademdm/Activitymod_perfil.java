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

import static com.example.pruebademdm.Login.DOCUMENTO;
import static com.example.pruebademdm.Login.MyPREFERENCES;
import static com.example.pruebademdm.Login.TELEFONO;
import static com.example.pruebademdm.Login.USUARIO_APELLIDO;
import static com.example.pruebademdm.Login.USUARIO_ID;
import static com.example.pruebademdm.Login.CORREO_USUARIO;
import static com.example.pruebademdm.Login.CONTRASEÑA_CORREO;
import static com.example.pruebademdm.Login.USUARIO_NOMBRE;
import static com.example.pruebademdm.Login.FECHA_NACIMIENTO;
import static com.example.pruebademdm.Login.DOCUMENTO;




public class Activitymod_perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activitymod_perfil);
        // Se procede a realizar el el llamado de  datos del usuario para la implementacion del cambio de contrasena
        final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //Se realizan las variables editext correo para el respectibo llamado y posterior modificacion
        EditText correoControl = findViewById(R.id.editText);
        String correoActual = sharedPreferences.getString(CORREO_USUARIO, "");
        correoControl.setText(correoActual);

        //Se realizan las variables editext nombre para el respectibo llamado y posterior modificacion
        EditText nombreControl = findViewById(R.id.texnombre);
        String nombreActual = sharedPreferences.getString(USUARIO_NOMBRE, "");
        nombreControl.setText(nombreActual);

        //Se realizan las variables editext apellido para el respectibo llamado y posterior modificacion
        EditText apellidoControl = findViewById(R.id.newapellido);
        String apellidoActual = sharedPreferences.getString(USUARIO_APELLIDO, "");
        apellidoControl.setText(apellidoActual);

        //Se realizan las variables editext telefono para el respectibo llamado y posterior modificacion
        EditText telefonoControl = findViewById(R.id.newtel);
        String telefonoActual = sharedPreferences.getString(TELEFONO, "");
        telefonoControl.setText(telefonoActual);

        //EditText fechaNacimientoControl = findViewById(R.id.fecha_de_nacimiento);
        //String fechaNacimientoActual = sharedPreferences.getString(FECHA_NACIMIENTO, "");
        //fechaNacimientoControl.setText(fechaNacimientoActual);
        //EditText documentoControl = findViewById(R.id.documento_ci);
        //String documentoActual = sharedPreferences.getString(DOCUMENTO, "");
        //documentoControl.setText(documentoActual);

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
        final String nombre = ((EditText) findViewById(R.id.texnombre)).getText().toString();
        final String apellido = ((EditText) findViewById(R.id.newapellido)).getText().toString();
        final String telefono = ((EditText) findViewById(R.id.newtel)).getText().toString();
        RequestParams request = new RequestParams();

        String correo_usuario = ((EditText) findViewById(R.id.editText)).getText().toString();
        String nombre_usuario = ((EditText) findViewById(R.id.texnombre)).getText().toString();
        String apellido_usuario = ((EditText) findViewById(R.id.newapellido)).getText().toString();
        String telefono_usuario = ((EditText) findViewById(R.id.newtel)).getText().toString();


        request.add("email", correo_usuario);
        request.add("nombre", nombre_usuario);
        request.add("apellido", apellido_usuario);
        request.add("telefono", telefono_usuario);


        String usuarioId = sharedPreferences.getString(USUARIO_ID, "");
        HttpUtils.post("/usuario/" + usuarioId, request, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject usuarioJSON = new JSONObject(new String(responseBody));
                    String correo = usuarioJSON.getString("email");
                    String nombre = usuarioJSON.getString("nombre");
                    String apellido = usuarioJSON.getString("apellido");
                    String telefono = usuarioJSON.getString("telefono");


                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(CORREO_USUARIO, correo);
                    editor.putString(USUARIO_NOMBRE, nombre);
                    editor.putString(USUARIO_APELLIDO, apellido);
                    editor.putString(TELEFONO, telefono);
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
