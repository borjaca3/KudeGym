package com.example.api26;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLITE extends SQLiteOpenHelper {
    public SQLITE(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }





    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table alimentos (codigo integer primary key, nombre text, calorias integer)");
        db.execSQL("create table objetivo (codigo integer primary key,caloriasObjetivo integer, calorias integer, fecha text)");
        db.execSQL("create table perfil (codigo integer primary key,edad integer, altura integer, peso integer,genero text, nombre text )");

        db.execSQL("create table rutina (codigo_rutina integer primary key AUTOINCREMENT,nombre text)");
        db.execSQL("create table dias (codigo_dias integer primary key AUTOINCREMENT,dias text)");
        db.execSQL("create table RutinaDias (id_rutina INTEGER,id_dia INTEGER,FOREIGN KEY (id_rutina) REFERENCES rutinas (id_rutina),FOREIGN KEY (id_dia) REFERENCES dias (id_dia))");

        db.execSQL("create table ejercicios (codigo_ejercicio integer primary key AUTOINCREMENT,nombre text)");
        db.execSQL("create table RutinaEjercicio (id_rutina INTEGER,id_ejercicio INTEGER,FOREIGN KEY (id_rutina) " +
                "REFERENCES rutinas (id_rutina),FOREIGN KEY (id_ejercicio) REFERENCES ejercicios (id_ejercicio))");


        db.execSQL("create table ejdiario (codigo integer primary key AUTOINCREMENT,ejercicio text,fecha text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implementación para actualización de la base de datos, si es necesario.
    }
}
