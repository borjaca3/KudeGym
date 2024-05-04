package com.example.api26;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;

public class EjDiario extends Activity {

    ConexionBBDD conexion;
    EditText comentarios = null;
    EditText repeticiones  = null;
    EditText peso = null;
    EditText series = null;
    String nombreEjercicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ej_diario);

        TextView textView = findViewById(R.id.textView);
        peso=findViewById(R.id.peso);
        repeticiones=findViewById(R.id.repeticiones);
        series=findViewById(R.id.series);
        comentarios = findViewById(R.id.comentarios);
        conexion = new ConexionBBDD(getApplicationContext());


        // Recibe el nombre del ejercicio enviado por el Intent
        nombreEjercicio = getIntent().getStringExtra("nombreEjercicio");
        textView.setText(nombreEjercicio);  // Mostrar el nombre del ejercicio en la actividad
        //conexion.ejercicioHecho(childText);
    }

    public void ejercicioRealizado(View view){

        SQLITE con = new SQLITE(this, "alimentos", null, 1);
        SQLiteDatabase baseDatos = con.getWritableDatabase();


        String repeticionesStr = repeticiones.getText().toString();
        String pesoStr = peso.getText().toString();
        String seriesStr = series.getText().toString();
        String comentariosString = comentarios.getText().toString();

        if( pesoStr.isEmpty()==false && repeticionesStr.isEmpty()==false){
            ContentValues registro = new ContentValues();


            registro.put("ejercicio", nombreEjercicio);
            registro.put("fecha", LocalDate.now().toString());
            registro.put("repeticiones", repeticionesStr);
            registro.put("peso", pesoStr);
            registro.put("series", seriesStr);
            registro.put("comentarios",comentariosString );


            conexion.ejercicioHecho(registro);

            //baseDatos.insert("alimentos",null,registro );

            peso.setText("");
            repeticiones.setText("");
            series.setText("");
            Toast.makeText(this,"Se ha insertado correctamente", Toast.LENGTH_LONG).show();


            Intent intent= new Intent(this, Ejercicio.class);
            startActivity(intent);


        }else{
            Toast.makeText(this,"Rellana los campos correctamente", Toast.LENGTH_LONG).show();

        }

    }
}
