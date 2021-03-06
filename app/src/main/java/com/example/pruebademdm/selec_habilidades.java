package com.example.pruebademdm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import c.e.p.util.HttpUtils;
import cz.msebera.android.httpclient.Header;

import static com.example.pruebademdm.Login.MyPREFERENCES;
import static com.example.pruebademdm.Login.USUARIO_ID;

public class selec_habilidades extends AppCompatActivity {
    List<Habilidad> habilidades = new ArrayList<Habilidad>();
    ListView listaHabilidades;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selec_habilidades);
        listaHabilidades = findViewById(R.id.habilidades);
        HttpUtils.get("/habilidades", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONArray response_habilidades = new JSONArray(new String(responseBody));
                    for (int i = 0; i < response_habilidades.length(); i++) {
                        JSONObject habilidad = response_habilidades.getJSONObject(i);
                        habilidades.add(new Habilidad(habilidad.getString("ID_habilidad"), habilidad.getString("nombre"), false));
                    }
                    ArrayAdapter<Habilidad> dataAdapter = new ArrayAdapter<Habilidad>(context,
                            android.R.layout.simple_list_item_1, habilidades) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                            Habilidad habilidad = habilidades.get(position);
                            convertView = messageInflater.inflate(R.layout.habilidades_list_item, null);
                            TextView nombreHabilidad = convertView.findViewById(R.id.nombre_habilidad);
                            CheckBox checkBoxHabilidad = convertView.findViewById(R.id.checkBox_habilidad);
                            nombreHabilidad.setText(habilidad.get_nombre());
                            checkBoxHabilidad.setChecked(habilidad.get_seleccionado());
                            checkBoxHabilidad.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                @Override
                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                    Integer position = listaHabilidades.getPositionForView(buttonView);
                                    Habilidad habilidad = habilidades.get(position);
                                    habilidad.set_seleccionado(isChecked);
                                }
                            });
                            return convertView;
                        }
                    };
                    listaHabilidades.setAdapter(dataAdapter);
                    listaHabilidades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            CheckBox checkBox = view.findViewById(R.id.checkBox_habilidad);
                            checkBox.setChecked(!checkBox.isChecked());
                        }
                    });

                    //Comienza el codigo para colocar icono en el action bar
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setIcon(R.mipmap.ic_launcher);
                    // Finaliza codigo icono action bar
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }

    // Comienza metodo boton crear usuario para ir a main_log
    public void mis_habilidades(View view) {
        final Intent mis_habilidades = new Intent(this, main_log.class);
        final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        List<String> idHabilidades = new ArrayList<String>();

        for (Habilidad habilidad : habilidades) {
            if (habilidad.get_seleccionado()) {
                idHabilidades.add(habilidad.get_id());
            }
        }

        RequestParams request = new RequestParams();
        request.add("habilidades", TextUtils.join(",", idHabilidades));
        String idUsuario = sharedPreferences.getString(USUARIO_ID, "");
        HttpUtils.put("/usuario/" + idUsuario + "/habilidades", request, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                startActivity(mis_habilidades);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        // Finaliza metodo boton crear usuario para ir a perfil de usuario
    }

    class Habilidad {
        String _id;
        String _nombre;
        Boolean _seleccionado;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String get_nombre() {
            return _nombre;
        }

        public void set_nombre(String _nombre) {
            this._nombre = _nombre;
        }

        public Boolean get_seleccionado() {
            return _seleccionado;
        }

        public void set_seleccionado(Boolean _seleccionado) {
            this._seleccionado = _seleccionado;
        }

        Habilidad(String id, String nombre, Boolean seleccionado) {
            _id = id;
            _nombre = nombre;
            _seleccionado = seleccionado;
        }
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
        } else if (id == R.id.contacto) {
            Toast.makeText(this, "Contáctanos", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, Contacto.class));
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

