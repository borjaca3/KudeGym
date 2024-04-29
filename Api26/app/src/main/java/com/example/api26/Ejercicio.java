package com.example.api26;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Ejercicio extends Activity {
    ConexionBBDD conexion;
    private ListView listView;
    private List<String> rutinas = new ArrayList<>();
    String semana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ejercicio);

        conexion = new ConexionBBDD(getApplicationContext());
        listView = findViewById(R.id.lvRutinas);

        diasemana();
        loadRoutinesAndExercises();



    }

    private void loadRoutinesAndExercises() {
        HashMap<String, List<String>> routineWithExercises = new HashMap<>();
        List<String> routineNames = new ArrayList<>();

        Cursor cursor = conexion.getRutinasPorDia(semana);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String nombreRutina = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                routineNames.add(nombreRutina);

                Cursor cursor2 = conexion.getEjercicioPorRutina(nombreRutina);
                List<String> exercises = new ArrayList<>();
                if (cursor2 != null && cursor2.moveToFirst()) {
                    do {
                        String nombreEjercicio = cursor2.getString(cursor2.getColumnIndexOrThrow("nombre"));
                        exercises.add(nombreEjercicio);
                    } while (cursor2.moveToNext());
                    cursor2.close();
                }
                routineWithExercises.put(nombreRutina, exercises);
            } while (cursor.moveToNext());
            cursor.close();
        }

        ExpandableListAdapter listAdapter = new ExpandableListAdapter(this, routineNames, routineWithExercises);
        ExpandableListView expListView = findViewById(R.id.expandableListView);
        expListView.setAdapter(listAdapter);
    }



    private void diasemana() {
        LocalDate fechaActual = LocalDate.now();
        semana= String.valueOf(fechaActual.getDayOfWeek());
    }
    public void irRutina(View view){
        Intent intent= new Intent(this, Rutina.class);
        startActivity(intent);}


    }
