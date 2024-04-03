package com.example.api26;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


public class PersonalizaObjetivo extends AppCompatActivity {

    EditText objetivo = null;
    private SQLITE dbHelper;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personalizaobjetivo);
        objetivo=findViewById(R.id.objetivo);



    }
    public void personalizaObjetivo(View view) {
        String objetivoStr = objetivo.getText().toString();
        dbHelper = new SQLITE(PersonalizaObjetivo.this, "alimentos", null, 1);
        SQLiteDatabase db2 = dbHelper.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("codigo", 1);
        registro.put("caloriasObjetivo", objetivoStr);
        String[] whereArgs = new String[]{"1"};
        int filasActualizadas = db2.update("objetivo", registro, "codigo=?", whereArgs);

        Intent intent= new Intent(PersonalizaObjetivo.this, MainActivity.class);
        startActivity(intent);


    }


}
