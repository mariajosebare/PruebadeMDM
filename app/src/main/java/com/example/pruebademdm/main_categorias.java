package com.example.pruebademdm;
package com.androidhive.spinner;

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


public class main_categorias extends Activity implements OnItemSelectedListener{
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_categorias);

        // Elementos del spinner
        Spinner spinner = (Spinner) view.findViewById(R.id.spinner);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elementos
        List categories = new ArrayList<>();
        categories.add("Mecánica");
        categories.add("Electricidad");
        categories.add("Belleza");
        categories.add("Cocina");
        categories.add("Fotografía");
        categories.add("Arte");

        // Creating adapter for spinner
        ArrayAdapter dataAdapter = new ArrayAdapter (getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView&lt;?&gt; parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    public void onNothingSelected(AdapterView&lt;?&gt; arg0) {
        // TODO Auto-generated method stub

    }

}
