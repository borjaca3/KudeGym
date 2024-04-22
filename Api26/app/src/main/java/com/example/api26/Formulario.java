package com.example.api26;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Formulario extends Activity {
    EditText editTextName = null;
    EditText editTextEdad = null;
    EditText editTextAltura = null;
    EditText editTextPeso = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario);

        editTextName = findViewById(R.id.editTextName);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextPeso = findViewById(R.id.editTextPeso);
        editTextAltura = findViewById(R.id.editTextAltura);
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

        String message = "Nombre: " + nombre + "\n" +
                "Edad: " + edad + "\n" +
                "Peso: " + peso + "\n" +
                "Altura" + altura;
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

    }
}
