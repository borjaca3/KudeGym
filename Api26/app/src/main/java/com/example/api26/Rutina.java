package com.example.api26;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Rutina extends Activity {
    ConexionBBDD conexion;
    CheckBox monday, tuesday, wednesday, thursday, friday, saturday, sunday;

    EditText nombreRutina = null;
    EditText ejRutina = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nuevarutina);

        conexion = new ConexionBBDD(getApplicationContext());

        monday = findViewById(R.id.checkbox_monday);
        tuesday = findViewById(R.id.checkbox_tuesday);
        wednesday = findViewById(R.id.checkbox_wednesday);
        thursday = findViewById(R.id.checkbox_thursday);
        friday = findViewById(R.id.checkbox_friday);
        saturday = findViewById(R.id.checkbox_saturday);
        sunday = findViewById(R.id.checkbox_sunday);

        nombreRutina = findViewById(R.id.nombreRutina);
        ejRutina = findViewById(R.id.ejRutina);


    }

    public void anadirEjRutina(View view){
        String rutinaNombre=nombreRutina.getText().toString();
        String ejercicioNombre=ejRutina.getText().toString();
        int codigoRutina, codigoEjercicio;
        codigoRutina= conexion.obtenerCodigoRutina(rutinaNombre);
        codigoEjercicio= conexion.obtenerCodigoEjercicio(ejercicioNombre);
        if(codigoRutina==0){
            conexion.insertRutina(rutinaNombre);
            codigoRutina= conexion.obtenerCodigoRutina(rutinaNombre);}
        if(codigoEjercicio==0){

            conexion.insertEjercicio(ejercicioNombre);
            codigoEjercicio= conexion.obtenerCodigoEjercicio(ejercicioNombre);}
        conexion.insertRutinaEjercicio(codigoRutina,codigoEjercicio);

    }
    public void handleCheckboxSelection(View view) {
        //StringBuilder selectedDays = new StringBuilder("Días seleccionados: ");
        String rutinaNombre=nombreRutina.getText().toString();
        int codigoRutina= conexion.obtenerCodigoRutina(rutinaNombre);

        if (monday.isChecked()) conexion.insertRutinaDia(codigoRutina,1);
        if (tuesday.isChecked()) conexion.insertRutinaDia(codigoRutina,2);
        if (wednesday.isChecked()) conexion.insertRutinaDia(codigoRutina,3);
        if (thursday.isChecked()) conexion.insertRutinaDia(codigoRutina,4);
        if (friday.isChecked()) conexion.insertRutinaDia(codigoRutina,5);
        if (saturday.isChecked()) conexion.insertRutinaDia(codigoRutina,6);
        if (sunday.isChecked()) conexion.insertRutinaDia(codigoRutina,7);

        // Mostrar los días seleccionados o manejar como desees
        //Toast.makeText(this, selectedDays.toString(), Toast.LENGTH_LONG).show();
    }





}