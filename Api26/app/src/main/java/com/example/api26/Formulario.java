package com.example.api26;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Formulario extends Activity {
    EditText editTextName = null;
    EditText editTextEdad = null;
    EditText editTextAltura = null;
    EditText editTextPeso = null;
    Spinner spinner = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);

        editTextName = findViewById(R.id.editTextName);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextPeso = findViewById(R.id.editTextPeso);
        editTextAltura = findViewById(R.id.editTextAltura);
        spinner=findViewById(R.id.spinner);



        String[] dropdownitems= getResources().getStringArray(R.array.Genero);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,dropdownitems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

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
        if(genero.equals("Género")){
            Toast.makeText(this, "Escoja un género", Toast.LENGTH_SHORT).show();
            return;
        }
        String message = "Nombre: " + nombre + "\n" +
                "Edad: " + edad + "\n" +
                "Peso: " + peso + "\n" +
                "Altura: " + altura + "\n" +
                "Género: " + genero;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}
