package com.example.pruebademdm;
//package com.androidhive.spinner;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.appcompat.app.AppCompatActivity;


public class main_categorias extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_categorias);
    }
}

//public class main_categorias extends Activity implements OnItemSelectedListener{
    //@Override
    //public void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main_categorias);

        // Elementos del spinner
        //Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        // Spinner click listener
        //spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elementos
        //List categories = new ArrayList<>();
        //categories.add("Mecánica");
        //categories.add("Electricidad");
        //categories.add("Belleza");
        //categories.add("Cocina");
        //categories.add("Fotografía");
        //categories.add("Arte");

        // Creating adapter for spinner
        //ArrayAdapter dataAdapter = new ArrayAdapter (getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        //dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        //spinner.setAdapter(dataAdapter);
    //}


