package com.example.api26;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    Spinner spinner;

    EditText aliNombre = null;


    ProgressBar progressBar;
    private SQLITE dbHelper;
    private SQLITE dbObj;

    int MAX_PROGRESO=1000;
    int caloriasTotales=0;

    Button boton;


    @SuppressLint("MissingInflatedId")//deplegable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new SQLITE(this, "alimentos", null, 1);


        aliNombre=findViewById(R.id.aliHoy);

        spinner=findViewById(R.id.spinner);
        String[] dropdownitems= getResources().getStringArray(R.array.drowpdownitems);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,dropdownitems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar); // Inicializa la referencia al ProgressBar

        barraPro();
        ////////////////////////////////////////// solo la primera vez que se ejecuta
        /*SQLITE con = new SQLITE(this, "alimentos", null, 1);
        SQLiteDatabase baseDatos = con.getWritableDatabase();





        ContentValues registro = new ContentValues();
        String[] whereArgs = new String[]{"1"};
        registro.put("codigo", 1);
        registro.put("caloriasObjetivo", 1);
        registro.put("calorias", 1);

        baseDatos.insert("objetivo",null,registro );*/
        ///////////////////////////////////////


        boton=(Button)findViewById(R.id.botonPasar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, AliEnBBDD.class);
                startActivity(intent);
            }
        });


    }

    private void barraPro() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int cal=devolverCal(db);
        int obj= devolverObj(db);

        progressBar.setMax(obj);

        if (progressBar.getProgress() < obj) {
            progressBar.setProgress(cal); // Incrementa el progreso (puedes ajustar esto según tus necesidades)
        }
    }

    public  void objetivo (View view){
        String objetivoSeleccionado = spinner.getSelectedItem().toString();
        Boolean per=true;

        SQLiteDatabase db2 = dbHelper.getWritableDatabase();


        if (objetivoSeleccionado.equals("Volumen")) {
            MAX_PROGRESO = 1000;
        } else if (objetivoSeleccionado.equals("Deficit")) {
            MAX_PROGRESO = 500;
        } else if (objetivoSeleccionado.equals("Mantenimiento")) {
            MAX_PROGRESO = 750;
        } else if (objetivoSeleccionado.equals("Personalizado")) {
            per=false;
            Intent intent= new Intent(MainActivity.this, PersonalizaObjetivo.class);
            startActivity(intent);
        }
        if( per) {

            int c=devolverCal(db2);

            ContentValues registro = new ContentValues();
            registro.put("codigo", 1);
            registro.put("caloriasObjetivo", MAX_PROGRESO);
            registro.put("calorias", c);

            db2.update("objetivo", registro, "codigo=?", new String[]{"1"});
            barraPro();
        }


    }


    public void calculoCalorias(View view){




        if (aliNombre != null && progressBar != null) {

            String alimento = aliNombre.getText().toString();
            int calorias = obtenerCaloriasPorNombre(alimento);

            if (calorias != -1) {
                Toast.makeText(this, "Alimento guardado " , Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "El alimento no se encontró en la base de datos.", Toast.LENGTH_SHORT).show();
            }

            SQLiteDatabase db = dbHelper.getWritableDatabase();

            caloriasTotales=devolverCal(db);
            int obj= devolverObj(db);

            caloriasTotales = calorias+caloriasTotales;
            Toast.makeText(this, "Calorais" + calorias,  Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Totales: " + caloriasTotales, Toast.LENGTH_SHORT).show();



            ContentValues registro = new ContentValues();

            registro.put("codigo", 1);
            registro.put("calorias", caloriasTotales);
            registro.put("caloriasObjetivo", obj);

            db.update("objetivo", registro, "codigo=?", new String[]{"1"});
            barraPro();
        } else {
            // Manejar el caso donde aliNombre o progressBar son nulos
        }
    }

    private int obtenerCaloriasPorNombre(String nombreAlimento) {
        int calorias = -1; // Valor predeterminado si no se encuentra el alimento

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT calorias FROM alimentos WHERE nombre=?", new String[]{nombreAlimento});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    calorias = cursor.getInt(0); // Obtener las calorías desde el primer registro
                }
            } finally {
                cursor.close(); // Asegúrate de cerrar el cursor
            }
        }

        return calorias;
    }

    private int devolverCal(SQLiteDatabase db){////esta funcion coge las calorias que has consumido hasta el momento
        int c=-1;
        Cursor cursor = db.rawQuery("SELECT calorias FROM objetivo WHERE codigo = ?",new String[]{"1"});
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    c = cursor.getInt(0); // Obtener las calorías desde el primer registro
                }
            } finally {
                cursor.close(); // Asegúrate de cerrar el cursor
            }
        }
        return c;
    }


    private int devolverObj(SQLiteDatabase db){////esta funcion indica cual es el objetivo guardadp
        int c=-1;
        Cursor cursor = db.rawQuery("SELECT caloriasObjetivo FROM objetivo WHERE codigo = ?",new String[]{"1"});
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    c = cursor.getInt(0); // Obtener las calorías desde el primer registro
                }
            } finally {
                cursor.close(); // Asegúrate de cerrar el cursor
            }
        }
        return c;
    }


}