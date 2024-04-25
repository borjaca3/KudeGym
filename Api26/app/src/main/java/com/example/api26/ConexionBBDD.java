package com.example.api26;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;
import android.database.Cursor;
import android.widget.Toast;
import android.util.Log;

public class ConexionBBDD   {

    private SQLITE dbHelper;

    public ConexionBBDD (Context context){
        dbHelper = new  SQLITE(context,"alimentos",null,1);

    }




    public void nuevoDia() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues registro = new ContentValues();

        int nuevoCodigo=obtenerUtlimoCodigo()+1;
        registro.put("codigo", nuevoCodigo);
        registro.put("caloriasObjetivo", devolverObj());
        registro.put("calorias", 0);
        registro.put("fecha", LocalDate.now().toString());

        db.insert("objetivo",null,registro );



    }

    public LocalDate cosultarFechaBBDD() { ////consulta la fecha de la base de datos y la devuelve
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        LocalDate fechaBBDD = null;

        Cursor cursor = db.query("objetivo", new String[]{"fecha"}, null, null, null, null, "fecha DESC", "1");

        if (cursor != null && cursor.moveToFirst()) {
            @SuppressLint("Range") String fechaString = cursor.getString(cursor.getColumnIndex("fecha"));
            fechaBBDD = LocalDate.parse(fechaString);
            cursor.close();
        }

        return fechaBBDD;

    }


    public int obtenerUtlimoCodigo (){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int codigo=0;
        String ultimoCodigoQuery = "SELECT codigo FROM objetivo ORDER BY codigo DESC LIMIT 1";
        Cursor cursor = db.rawQuery(ultimoCodigoQuery, null);
        if (cursor.moveToFirst()) {
            codigo = cursor.getInt(0);
        }
        cursor.close();
        return codigo;
    }

    public int obtenerUtlimoCodigoAlimentos (){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int codigo=0;
        String ultimoCodigoQuery = "SELECT codigo FROM alimentos ORDER BY codigo DESC LIMIT 1";
        Cursor cursor = db.rawQuery(ultimoCodigoQuery, null);
        if (cursor.moveToFirst()) {
            codigo = cursor.getInt(0);
        }
        cursor.close();
        return codigo;
    }





    public int devolverCal(){////esta funcion coge las calorias que has consumido hasta el momento
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int c=-1;
        Cursor cursor = db.rawQuery("SELECT calorias FROM objetivo WHERE codigo = ?",new String[]{String.valueOf(obtenerUtlimoCodigo())});
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

    public int obtenerCaloriasPorNombre(String nombreAlimento) {

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

    public int devolverObj(){////esta funcion indica cual es el objetivo guardadp
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int c=-1;
        Cursor cursor = db.rawQuery("SELECT caloriasObjetivo FROM objetivo WHERE codigo = ?",new String[]{String.valueOf(obtenerUtlimoCodigo())});
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

    public void updateObjetivo (int codigo, ContentValues registro){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.update("objetivo", registro, "codigo=?", new String[]{String.valueOf(codigo)});
    }


    public void insertAlimentos( ContentValues registro) {
        SQLiteDatabase baseDatos = dbHelper.getWritableDatabase();
        baseDatos.insert("alimentos",null,registro );

    }

    public int obtenerCaloriasPorFecha(String fecha) {

        int calorias = 0; // Valor predeterminado si no se encuentra el alimento

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT calorias FROM objetivo WHERE fecha=?", new String[]{fecha});

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
    public int obtenerObjetivoPorFecha(String fecha) {

        int caloriasObjetivo = 0; // Valor predeterminado si no se encuentra el alimento

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT caloriasObjetivo FROM objetivo WHERE fecha=?", new String[]{fecha});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    caloriasObjetivo = cursor.getInt(0); // Obtener las calorías desde el primer registro
                }
            } finally {
                cursor.close(); // Asegúrate de cerrar el cursor
            }
        }

        return caloriasObjetivo;
    }

    public boolean obtenerOjetivoCumplidoPorFecha(String fecha) {

        int calorias = 0; // Valor predeterminado si no se encuentra el alimento
        int objetivo = obtenerObjetivoPorFecha(fecha);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT calorias FROM objetivo WHERE fecha=?", new String[]{fecha});

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    calorias = cursor.getInt(0); // Obtener las calorías desde el primer registro
                    if (calorias >= objetivo){
                        return true;
                    }else return false;
                }
            } finally {
                cursor.close(); // Asegúrate de cerrar el cursor
            }
        }

        return false;
    }


    public void insertPerfil(ContentValues registro) {
        SQLiteDatabase baseDatos = dbHelper.getWritableDatabase();


        baseDatos.insert("perfil",null,registro );

    }

    public void insertRutina(String rutina) {
        SQLiteDatabase baseDatos = dbHelper.getWritableDatabase();
        ContentValues reg = new ContentValues();
        reg.put("nombre",rutina);
        baseDatos.insert("rutina",null,reg );

    }

    public void insertEjercicio(String ejercicio) {
        SQLiteDatabase baseDatos = dbHelper.getWritableDatabase();
        ContentValues reg = new ContentValues();
        reg.put("nombre",ejercicio);
        baseDatos.insert("ejercicios",null,reg );

    }
    public void insertRutinaEjercicio(int rutina , int ejercicio) {
        SQLiteDatabase baseDatos = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_rutina", rutina);
        values.put("id_ejercicio", ejercicio);
        baseDatos.insert("RutinaEjercicio",null,values );
    }
    public void insertRutinaDia(int rutina, int dia) {
        SQLiteDatabase baseDatos = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_rutina", rutina);
        values.put("id_dia", dia);
        baseDatos.insert("RutinaDias",null,values );
    }

    public int obtenerCodigoRutina (String rutina){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int codigo=0;
        String codigoQuery = "SELECT codigo_rutina FROM rutina WHERE nombre = ? LIMIT 1";
        Cursor cursor = db.rawQuery(codigoQuery, new String[] { rutina });
        if (cursor.moveToFirst()) {
            codigo = cursor.getInt(0);
        }
        cursor.close();
        return codigo;
    }
    public int obtenerCodigoEjercicio (String ejercicio){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int codigo=0;
        String codigoQuery = "SELECT codigo_ejercicio FROM ejercicios WHERE nombre = ? LIMIT 1";
        Cursor cursor = db.rawQuery(codigoQuery, new String[] { ejercicio });
        if (cursor.moveToFirst()) {
            codigo = cursor.getInt(0);
        }
        cursor.close();
        return codigo;
    }


    public void insertdias() {
        SQLiteDatabase baseDatos = dbHelper.getWritableDatabase();
        ContentValues reg = new ContentValues();
        reg.put("dias","MONDAY");
        baseDatos.insert("dias",null,reg );
        reg.put("dias","TUESDAY");
        baseDatos.insert("dias",null,reg );
        reg.put("dias","WEDNESDAY");
        baseDatos.insert("dias",null,reg );
        reg.put("dias","THURSDAY");
        baseDatos.insert("dias",null,reg );
        reg.put("dias","FRIDAY");
        baseDatos.insert("dias",null,reg );
        reg.put("dias","SATURDAY");
        baseDatos.insert("dias",null,reg );
        reg.put("dias","SUNDAY");
        baseDatos.insert("dias",null,reg );

    }

    private static final String TAG = MainActivity.class.getSimpleName(); // Etiqueta para el log
    public Cursor getRutinasPorDia(String dia) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "SELECT r.nombre FROM rutina AS r " +
                "JOIN RutinaDias AS rd ON r.codigo_rutina = rd.id_rutina " +
                "JOIN dias AS d ON rd.id_dia = d.codigo_dias " +
                "WHERE d.dias = ?";


        // Realizar la consulta y devolver el Cursor resultante
        return db.rawQuery(query, new String[]{dia});



    }
}
