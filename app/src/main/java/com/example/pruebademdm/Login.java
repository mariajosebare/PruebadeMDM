package com.example.pruebademdm;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import c.e.p.util.HttpUtils;
import cz.msebera.android.httpclient.Header;
import es.dmoral.toasty.Toasty;

public class Login extends AppCompatActivity {

    public static final String MyPREFERENCES = "Preferencias" ;
    public static final String USUARIO_ID = "usuario_id";
    public static final String USUARIO_NOMBRE = "usuario_nombre";
    public static final String USUARIO_APELLIDO = "usuario_apellido";
    public static final String NECESIDAD_SELECCIONADA = "necesidad";
    public static final String USUARIO_CHAT = "usuario_chat";
    public static final String USUARIO_CHAT_NOMBRE = "usuario_chat_nombre";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void login(View view) {
        final Intent login = new Intent(this, main_log.class);
        final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String username = ((EditText) findViewById(R.id.user_name)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        RequestParams request = new RequestParams();
        request.add("email", username);
        request.add("password", password);
        HttpUtils.post("/login", request, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject response=new JSONObject(new String(responseBody));
                    if (!response.has("error")) {
                        //Creamos el codigo nesesario para que luego de logueado nos guarde los datos.
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        JSONObject usuario = response.getJSONObject("usuario");
                        String idUsuario = usuario.getString("ID_usuario");
                        String nombreUsuario= usuario.getString("nombre");
                        String apellidoUsuario = usuario.getString("apellido");
                        editor.putString(USUARIO_ID,idUsuario);
                        editor.putString(USUARIO_NOMBRE, nombreUsuario);
                        editor.putString(USUARIO_APELLIDO, apellidoUsuario);
                        editor.commit();
                        startActivity(login);
                    } else {
                        Toasty.error(getApplicationContext(),
                                "Usuario o contraseña inválidos",
                                Toast.LENGTH_SHORT,
                                true
                        ).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toasty.error(getApplicationContext(),
                            e.getMessage(),
                            Toast.LENGTH_SHORT,
                            true
                    ).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                error.printStackTrace();
            }
        });
    }





}




