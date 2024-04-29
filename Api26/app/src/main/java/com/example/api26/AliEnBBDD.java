package com.example.api26;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
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

    ConexionBBDD conexion;

    EditText aliNombre = null;
    EditText aliCalorias = null;


    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alienbbdd);


        aliNombre=findViewById(R.id.aliNombre);
        aliCalorias=findViewById(R.id.aliCalorias);

        conexion = new ConexionBBDD(getApplicationContext());



    }
    public void insertarAli(View view) {
        SQLITE con = new SQLITE(this, "alimentos", null, 1);
        SQLiteDatabase baseDatos = con.getWritableDatabase();


        String aliNombreStr = aliNombre.getText().toString();
        String aliCaloriasStr = aliCalorias.getText().toString();

        if( aliNombreStr.isEmpty()==false && aliCaloriasStr.isEmpty()==false){
            ContentValues registro = new ContentValues();

            int ultimoCodigo = conexion.obtenerUtlimoCodigoAlimentos();

            int nuevoCodigo=ultimoCodigo+1;
            registro.put("codigo", nuevoCodigo);
            registro.put("nombre", aliNombreStr);
            registro.put("calorias", aliCaloriasStr);

            conexion.insertAlimentos(registro);

            //baseDatos.insert("alimentos",null,registro );

            aliNombre.setText("");
            aliCalorias.setText("");
            Toast.makeText(this,"Se ha insertado correctamente", Toast.LENGTH_LONG).show();


        }else{
            Toast.makeText(this,"Rellana los campos correctamente", Toast.LENGTH_LONG).show();

        }



    }
    public void volver(View view){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}
