package com.example.api26;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.time.LocalDate;


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




}
