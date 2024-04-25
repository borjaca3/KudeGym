package com.example.api26;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CalendarView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
// MainActivity.java
import android.os.Bundle;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Estadisticas extends AppCompatActivity {
    private Calendar mCalendar;
    private GridView mGridView;
    private CalendarAdapter mCalendarAdapter;
    private TextView mMonthTextView;
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);
        mGridView = findViewById(R.id.gridView);
        mMonthTextView = findViewById(R.id.monthTextView);

        mCalendar = Calendar.getInstance();
        updateCalendar();
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




        findViewById(R.id.prevMonthButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar.add(Calendar.MONTH, -1);
                updateCalendar();
            }
        });

        findViewById(R.id.nextMonthButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mCalendar.add(Calendar.MONTH, 1);
                updateCalendar();
            }
        });

        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView selectedTextView = (TextView) view;
                int day = Integer.parseInt(selectedTextView.getText().toString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                String selectedDate = sdf.format(mCalendar.getTime());
                selectedDate = selectedDate.substring(0, 8) + String.format(Locale.getDefault(), "%02d", day);
                // Aquí puedes hacer algo con la fecha seleccionada, como mostrarla en un Toast

            }
        });
    }

    private void updateCalendar() {
        mCalendarAdapter = new CalendarAdapter(this, mCalendar);
        mGridView.setAdapter(mCalendarAdapter);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy", Locale.getDefault());
        mMonthTextView.setText(sdf.format(mCalendar.getTime()));
    }

    public void volver(View view){
        Intent intent= new Intent(this, MainActivity.class);
        startActivity(intent);
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
        Intent intent= new Intent(this, Formulario.class);
        startActivity(intent);
    }

}

