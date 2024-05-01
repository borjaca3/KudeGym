package com.example.api26;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.time.LocalDate;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    Spinner spinner, spinner2;

    String aliNombre = null;

    ProgressBar progressBar;

    private BottomNavigationView bottomNavigationView;
    float MAX_PROGRESO=1000;
    int caloriasTotales=0;

    Button boton;

    ConexionBBDD conexion;



    @SuppressLint("MissingInflatedId")//deplegable
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //getApplicationContext().deleteDatabase("alimentos");
        //Esta linea elimina la BBDD. Hay que hacerlo cuando cambias la estructura de la BBDD

        conexion = new ConexionBBDD(getApplicationContext());
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.estadisticas){
                volverEstadisticas();
            }else if(itemId == R.id.alimentacion){
                volverAlimentacion();
            }else if(itemId == R.id.ejercicio){
                volverEjercicio();
            }
            return true;
        });

        spinner2 = findViewById(R.id.spinnerAli);
        loadSpinnerData();
        String texto =spinner2.getSelectedItem().toString();
        aliNombre = texto.replaceAll("\\s*\\(.*?\\)", "");
        Log.d("hola", "aliNombre: " + aliNombre);
        spinner=findViewById(R.id.spinner);
        String[] dropdownitems= getResources().getStringArray(R.array.drowpdownitems);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_item,dropdownitems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        progressBar = findViewById(R.id.progressBar); // Inicializa la referencia al ProgressBar


        ////////////////////////////////////////// solo la primera vez que se ejecuta
        if(conexion.vacia()){
             SQLITE con = new SQLITE(this, "alimentos", null, 1);
        SQLiteDatabase baseDatos = con.getWritableDatabase();


        ContentValues registro = new ContentValues();
        String[] whereArgs = new String[]{"1"};
        registro.put("codigo", 1);
        registro.put("caloriasObjetivo", 1);
        registro.put("calorias", 1);
        registro.put("fecha",LocalDate.now().toString());

        baseDatos.insert("objetivo",null,registro );
            ///////////////////////////////////////

        }

        barraPro();
        guardarDia();

        boton=(Button)findViewById(R.id.botonPasar);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(MainActivity.this, AliEnBBDD.class);
                startActivity(intent);
            }
        });


    }
    private void loadSpinnerData() {
        List<String> options = conexion.getAllAlimentos();
        //Log.d("hola","tamaño:"+ options.size());
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(dataAdapter);

    }

    private void guardarDia() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate fechaBBDD;
        fechaBBDD=conexion.cosultarFechaBBDD();

        if (fechaActual.isAfter(fechaBBDD)) {
            conexion.nuevoDia();
        }


    }


    private void barraPro() {

        int cal=conexion.devolverCal();
        int obj= conexion.devolverObj();

        progressBar.setMax(obj);

        if (progressBar.getProgress() < obj) {
            progressBar.setProgress(cal); // Incrementa el progreso (puedes ajustar esto según tus necesidades)
        }

        TextView textViewCalorias = findViewById(R.id.textViewCalorias);
        TextView textViewObjetivo = findViewById(R.id.textViewObjetivo);

        textViewCalorias.setText("Calorías: " + cal);
        textViewObjetivo.setText("Objetivo: " + obj);
    }

    public  void objetivo (View view){
        String objetivoSeleccionado = spinner.getSelectedItem().toString();
       // Toast.makeText(this, "altura" + conexion.masculino(),  Toast.LENGTH_SHORT).show();
        if(conexion.masculino()){
            MAX_PROGRESO = (float) ((10 * conexion.obtenerPeso()) + (6.25 * conexion.obtenerAltura()) - (5 * conexion.obtenerEdad()) + 5);


        }else{
            MAX_PROGRESO= (float) (10 * conexion.obtenerPeso() * (6.25 *conexion.obtenerAltura())-(5 *conexion.obtenerEdad()) -161);
        }

        if (objetivoSeleccionado.equals("Objetivo")){
            Toast.makeText(this, "Elige un objetivo " , Toast.LENGTH_SHORT).show();
        }else{
            Boolean per=true;




        if (objetivoSeleccionado.equals("Volumen")) {
            MAX_PROGRESO= (float) ((MAX_PROGRESO ) * 1.55)+300;
        } else if (objetivoSeleccionado.equals("Deficit")) {
            MAX_PROGRESO=(float) ((MAX_PROGRESO ) * 1.55)-600;
        } else if (objetivoSeleccionado.equals("Mantenimiento")) {
            MAX_PROGRESO=(float) (MAX_PROGRESO* 1.55);
        } else if (objetivoSeleccionado.equals("Personalizado")) {
            per=false;
            Intent intent= new Intent(MainActivity.this, PersonalizaObjetivo.class);
            startActivity(intent);
        }
        if( per) {

            int c=conexion.devolverCal();
            int codigo=conexion.obtenerUtlimoCodigo();

            ContentValues registro = new ContentValues();
            registro.put("codigo", codigo);
            registro.put("caloriasObjetivo", MAX_PROGRESO);
            registro.put("calorias", c);
            registro.put("fecha",LocalDate.now().toString());

            conexion.updateObjetivo(codigo,registro);


            barraPro();}
        }


    }


    public void calculoCalorias(View view){

        if (aliNombre != null && progressBar != null) {

            String alimento = aliNombre;
            int calorias = conexion.obtenerCaloriasPorNombre(alimento);

            if (calorias == -1) {
                Toast.makeText(this, "El alimento no se encontró en la base de datos." , Toast.LENGTH_SHORT).show();
                calorias=0;
            } else {
                Toast.makeText(this, "Alimento guardado. ", Toast.LENGTH_SHORT).show();


                caloriasTotales = conexion.devolverCal();
                int obj = conexion.devolverObj();

                caloriasTotales = calorias + caloriasTotales;
                Toast.makeText(this, "Calorias" + calorias, Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Totales: " + caloriasTotales, Toast.LENGTH_SHORT).show();


                ContentValues registro = new ContentValues();

                int codigo = conexion.obtenerUtlimoCodigo();

                registro.put("codigo", codigo);
                registro.put("calorias", caloriasTotales);
                registro.put("caloriasObjetivo", obj);
                registro.put("fecha", LocalDate.now().toString());

                conexion.updateObjetivo(codigo, registro);

                barraPro();
            }
            //aliNombre.setText("");
        } else {
            // Manejar el caso donde aliNombre o progressBar son nulos
        }
    }


    public void volverAlimentacion(){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void volverEstadisticas(){
        Intent intent= new Intent(this, Estadisticas.class);
        startActivity(intent);
    }

    public void volverEjercicio(){
        Intent intent= new Intent(this, Ejercicio.class);
        startActivity(intent);
    }




}
