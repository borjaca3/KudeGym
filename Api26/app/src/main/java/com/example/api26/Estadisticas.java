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

public class Estadisticas extends AppCompatActivity {
    private Calendar mCalendar;
    private GridView mGridView;
    private CalendarAdapter mCalendarAdapter;
    private TextView mMonthTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estadisticas);

        mGridView = findViewById(R.id.gridView);
        mMonthTextView = findViewById(R.id.monthTextView);

        mCalendar = Calendar.getInstance();
        updateCalendar();

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
}

