package com.example.api26;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

public class EditarRutinas extends Activity {
    ConexionBBDD conexion;
    Spinner spinner;
    String rutina;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.editar_rutinas);

        conexion = new ConexionBBDD(getApplicationContext());
        spinner = findViewById(R.id.spinner);
        loadSpinnerData();

    }

    private void loadSpinnerData() {
        List<String> options = conexion.getAllRutinas();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    public void eliminaRutina(View view){
        rutina = spinner.getSelectedItem().toString();
        conexion.eliminarRutina(rutina);
        Toast.makeText(this, "Rutina '" + rutina +"' eliminada", Toast.LENGTH_SHORT).show();
        Intent intent= new Intent(this, Ejercicio.class);
        startActivity(intent);

    }
}
