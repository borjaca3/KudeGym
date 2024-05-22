package com.example.api26;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class EstadisticasEjercicio extends AppCompatActivity {
    private ConexionBBDD conexion;
    private Spinner spinner = null;
    private Spinner spinnerFecha = null;
    String fecha;
    TextView comentarios, series, peso, repeticiones;
    private List<Ejercicio> ejerciciosList;
    Ejercicio ejDiario;
    private BarChart barChart;
    private BottomNavigationView bottomNavigationView;
    String ejercicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas_ejercicio);
        spinner = findViewById(R.id.spinner);
        spinnerFecha = findViewById(R.id.spinnerFecha);
        conexion = new ConexionBBDD(getApplicationContext());
        barChart = findViewById(R.id.bar_chart);

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

        series = findViewById(R.id.series);
        repeticiones = findViewById(R.id.repeticiones);
        comentarios = findViewById(R.id.comentarios);
        peso = findViewById(R.id.peso);

        loadSpinnerData();


    }

    private void loadSpinnerData() {
        List<String> options = conexion.getAllEjercicios();

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, options);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

    }

    private void loadSpinnerFecha(String ejercicio) {
        List<String> fechas = conexion.getAllFechas(ejercicio);

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, fechas);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFecha.setAdapter(dataAdapter);

    }

    public void cargaEjercicios(View view){
        fecha = spinnerFecha.getSelectedItem().toString();
        ejDiario = conexion.getEstadisticasEjercicioFecha(ejercicio, fecha);
     //   Log.d("hola", "series" + ejDiario.series);
        series.setText("Series: " + ejDiario.series);
        comentarios.setText("Comentarios: " + ejDiario.comentarios);
        peso.setText("Peso: " + ejDiario.peso);
        repeticiones.setText("Repeticiones: " + ejDiario.repeticiones);

    }

    public void eliminarEjercicio(View view){
        if(fecha==null || ejercicio==null) {
            Toast.makeText(this, "Elige una sesión de entrenamiento", Toast.LENGTH_SHORT).show();
        }else{
            ejDiario = conexion.getEstadisticasEjercicioFecha(ejercicio, fecha);
            if(conexion.eliminaEjericio(ejercicio,fecha)==true){
                Toast.makeText(this, "Ejercicio '" + ejercicio + "' (" + fecha + ") eliminado", Toast.LENGTH_SHORT).show();
                cargaGrafico(view);
            }else {
                Toast.makeText(this, "Error al eliminar la sesión", Toast.LENGTH_SHORT).show();
            }
            
            

        }

    }
    public void cargaGrafico(View view){

        ejercicio = spinner.getSelectedItem().toString();

        ejerciciosList = conexion.getEstadisticasEjercicio(ejercicio);

        if (ejerciciosList != null && !ejerciciosList.isEmpty()) {
            // Crear una lista de entradas de datos para el gráfico de barras
            List<BarEntry> entries = new ArrayList<>();
            int dayCounter = 0; // Contador de días

            for (int i = 0; i < ejerciciosList.size(); i++) {
                Ejercicio ejercicio = ejerciciosList.get(i);

                // Agregar la entrada de datos al gráfico de barras
                entries.add(new BarEntry(dayCounter++, ejercicio.getPeso()));
            }

            // Crear un conjunto de datos para el gráfico de barras
            BarDataSet dataSet = new BarDataSet(entries, "Peso Levantado");

            // Configurar color para las barras
            dataSet.setColor(Color.rgb(0, 155, 0));

            // Crear los datos del gráfico de barras
            BarData barData = new BarData(dataSet);

            // Configurar el eje X
            XAxis xAxis = barChart.getXAxis();
            xAxis.setValueFormatter(new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    // Convertir el índice del día a una etiqueta de fecha o día de la semana
                    // Puedes implementar tu lógica de formato de fecha aquí
                    return "Día " + ((int) value + 1); // Sumamos 1 para empezar desde el día 1
                }
            });
            xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
            xAxis.setGranularity(1f); // Espaciamiento entre las barras
            xAxis.setGranularityEnabled(true);

            // Configurar el eje Y
            YAxis yAxis = barChart.getAxisLeft(); // Obtener el eje Y izquierdo
            yAxis.setAxisMinimum(0f); // Valor mínimo en el eje Y
            yAxis.setGranularity(1f); // Espaciamiento entre los valores del eje Y

            // Configurar el gráfico de barras
            barChart.setData(barData);
            barChart.getDescription().setEnabled(false); // Deshabilitar la descripción
            barChart.setDrawValueAboveBar(true); // Mostrar los valores encima de las barras
            barChart.invalidate(); // Actualizar el gráfico

            loadSpinnerFecha(ejercicio);
        }
    }

    public void volverEstadisticas(){
        Intent intent= new Intent(this, Estadisticas.class);
        startActivity(intent);
    }
    public void volverAlimentacion(){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void volverEjercicio(){
        Intent intent= new Intent(this, Ejercicio.class);
        startActivity(intent);
    }
}