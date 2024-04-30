package com.example.api26;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;

public class Formulario extends Activity {
    ConexionBBDD conexion;
    EditText editTextName = null;
    EditText editTextEdad = null;
    EditText editTextAltura = null;
    EditText editTextPeso = null;
    Spinner spinner = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);

        conexion = new ConexionBBDD(getApplicationContext());

        editTextName = findViewById(R.id.editTextName);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextPeso = findViewById(R.id.editTextPeso);
        editTextAltura = findViewById(R.id.editTextAltura);
        spinner=findViewById(R.id.spinner);


        String[] dropdownitems= getResources().getStringArray(R.array.Genero);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,dropdownitems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        //conexion.insertdias();
        //conexion.insertRutina();
        //conexion.insertRutinaDia();ç

    }

    public void goToMainActivity(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void nuevoPerfil(View view){
        String nombre = editTextName.getText().toString();
        String edad = editTextEdad.getText().toString();
        String peso = editTextPeso.getText().toString();
        String altura = editTextAltura.getText().toString();
        String genero = spinner.getSelectedItem().toString();


        if(genero.equals("Sexo")){
            Toast.makeText(this, "Escoja un gÃ©nero", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nombre.length()!=0 || edad.length()!=0|| peso.length()!=0 || altura.length()!=0) {
            String message = "Nombre: " + nombre + "\n" +
                    "Edad: " + edad + "\n" +
                    "Peso: " + peso + "\n" +
                    "Altura" + altura;
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();


            ContentValues registro = new ContentValues();
            registro.put("codigo", 1);
            registro.put("nombre", nombre);
            registro.put("edad", edad);
            registro.put("altura", altura);
            registro.put("peso", peso);
            registro.put("genero", genero);
            conexion.insertPerfil(registro);
        } else {
            Toast.makeText(this, "Debe rellenar todos los campos. " , Toast.LENGTH_SHORT).show();
        }

    }

}
