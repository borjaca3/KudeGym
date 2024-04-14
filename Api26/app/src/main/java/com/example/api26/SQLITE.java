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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Implementación para actualización de la base de datos, si es necesario.
    }
}
