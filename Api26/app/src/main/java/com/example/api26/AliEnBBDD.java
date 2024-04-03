package com.example.api26;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


public class AliEnBBDD extends AppCompatActivity {



    EditText aliNombre = null;
    EditText aliCalorias = null;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alienbbdd);


        aliNombre=findViewById(R.id.aliNombre);
        aliCalorias=findViewById(R.id.aliCalorias);



    }
    public void insertarAli(View view) {
        SQLITE con = new SQLITE(this, "alimentos", null, 1);
        SQLiteDatabase baseDatos = con.getWritableDatabase();


        String aliNombreStr = aliNombre.getText().toString();
        String aliCaloriasStr = aliCalorias.getText().toString();

        if( aliNombreStr.isEmpty()==false && aliCaloriasStr.isEmpty()==false){
            ContentValues registro = new ContentValues();
            //Codigo
            String ultimoCodigoQuery = "SELECT codigo FROM alimentos ORDER BY codigo DESC LIMIT 1";
            Cursor cursor = baseDatos.rawQuery(ultimoCodigoQuery, null);
            int ultimoCodigo = 0;
            if (cursor.moveToFirst()) {
                ultimoCodigo = cursor.getInt(0);
            }
            cursor.close();

            int nuevoCodigo=ultimoCodigo+1;
            registro.put("codigo", nuevoCodigo);
            registro.put("nombre", aliNombreStr);
            registro.put("calorias", aliCaloriasStr);

            baseDatos.insert("alimentos",null,registro );

            aliNombre.setText("");
            aliCalorias.setText("");
            Toast.makeText(this,"Se ha insertado correctamente", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"Rellana los campos correctamente", Toast.LENGTH_LONG).show();

        }
    }

}
