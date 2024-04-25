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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import java.util.ArrayList;
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
        codigo();



    }

    private void codigo() {

        Cursor cursor=conexion.getRutinasPorDia(semana);
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    // Extraer el nombre de la rutina del cursor
                    String nombreRutina = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                    // Imprimir el nombre en la consola



                    rutinas.add(nombreRutina);


                } while (cursor.moveToNext());
            }


        } finally {
            if (cursor != null) {
                cursor.close(); // Cerrar el cursor para liberar recursos
            }

        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, rutinas);
        listView.setAdapter(adapter);
    }

    private void diasemana() {
        LocalDate fechaActual = LocalDate.now();
        semana= String.valueOf(fechaActual.getDayOfWeek());
    }


}
