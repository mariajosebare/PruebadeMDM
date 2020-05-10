package com.example.pruebademdm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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
                    JSONArray response_habilidades=new JSONArray(new String(responseBody));
                    for (int i = 0; i < response_habilidades.length(); i++) {
                        JSONObject habilidad = response_habilidades.getJSONObject(i);
                        habilidades.add(new Habilidad(habilidad.getString("ID_habilidad"),habilidad.getString("nombre"), false));
                    }
                    ArrayAdapter<Habilidad> dataAdapter = new ArrayAdapter<Habilidad>(context,
                            android.R.layout.simple_list_item_1, habilidades) {
                        @Override
                        public View getView (int position, View convertView, ViewGroup parent) {
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
        List<String> idHabilidades = new ArrayList<String>();

        for (Habilidad habilidad:habilidades){
            if(habilidad.get_seleccionado()) {
                idHabilidades.add(habilidad.get_id());
            }
        }

        RequestParams request = new RequestParams();
        request.add("habilidades", TextUtils.join(",",idHabilidades));
        HttpUtils.put("/usuario/1/habilidades", request, new AsyncHttpResponseHandler() {
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

        Habilidad (String id, String nombre, Boolean seleccionado) {
            _id = id;
            _nombre = nombre;
            _seleccionado = seleccionado;
        }
    }

}

