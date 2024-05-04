
package com.example.api26;
// CalendarAdapter.java
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CalendarAdapter extends BaseAdapter {
    public int cal=0;
    public int obj=0;
    private Context mContext;
    private Calendar mCalendar;
    private ConexionBBDD mConexionBBDD;
    private SimpleDateFormat mDateFormat;

    public CalendarAdapter(Context context, Calendar calendar) {
        mContext = context;
        mCalendar = calendar;
        mConexionBBDD = new ConexionBBDD(context);
        mDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    }

    // Otros métodos del adaptador aquí...

    @Override
    public int getCount() {
        return mCalendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView textView;
        if (convertView == null) {
            textView = new TextView(mContext);
            textView.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            textView.setPadding(8, 8, 8, 8);
            textView.setGravity(android.view.Gravity.CENTER);
        } else {
            textView = (TextView) convertView;
        }

        // Establecer el número del día
        textView.setText(String.valueOf(position + 1));

        // Obtener la fecha correspondiente al día
        mCalendar.set(Calendar.DAY_OF_MONTH, position + 1);
        String date = mDateFormat.format(mCalendar.getTime());

        // Obtener el estado del objetivo cumplido para la fecha correspondiente
        boolean objetivoCumplido = mConexionBBDD.obtenerOjetivoCumplidoPorFecha(date);

        // Determinar el color de fondo según el estado del objetivo
        if (objetivoCumplido) {
            textView.setBackgroundColor(Color.GREEN);
        } else {
            textView.setBackgroundColor(Color.RED);
        }

        // Agregar clic en cada día
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = position + 1;
                String selectedDate = mDateFormat.format(mCalendar.getTime());
                selectedDate = selectedDate.substring(0, 8) + String.format(Locale.getDefault(), "%02d", day);
                // Mostrar la fecha seleccionada en un Toast
                Toast.makeText(mContext, "Calorias consumidas: " + mConexionBBDD.obtenerCaloriasPorFecha(selectedDate)
                        + "\nCalorias obetivo: " + mConexionBBDD.obtenerObjetivoPorFecha(selectedDate), Toast.LENGTH_SHORT).show();

              //  obj = mConexionBBDD.obtenerObjetivoPorFecha(selectedDate);
              //  cal = mConexionBBDD.obtenerCaloriasPorFecha(selectedDate);
            }
        });

        return textView;
    }
}
