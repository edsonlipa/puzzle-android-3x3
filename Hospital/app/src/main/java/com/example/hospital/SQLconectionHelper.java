package com.example.hospital;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.hospital.utilidades.Utilidades;

public class SQLconectionHelper extends SQLiteOpenHelper {



    public SQLconectionHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
//        context.deleteDatabase(name);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //db.execSQL("DROP TABLE IF EXISTS Hospital");
//        db.execSQL("DROP TABLE IF EXISTS Booking");
        db.execSQL("DROP TABLE IF EXISTS Hospital");
        db.execSQL("DROP TABLE IF EXISTS Doctor");
        db.execSQL("DROP TABLE IF EXISTS Patient");
        db.execSQL("DROP TABLE IF EXISTS Booking");
        db.execSQL(Utilidades.CREAR_HOSPITAL);
        db.execSQL(Utilidades.CREAR_DOCTOR);
        db.execSQL(Utilidades.CREAR_PATIENT);
        db.execSQL(Utilidades.CREAR_BOOKING);
        Log.i("CREACION","Base de datos creada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Hospital");
        db.execSQL("DROP TABLE IF EXISTS Doctor");
        db.execSQL("DROP TABLE IF EXISTS Patient");
        db.execSQL("DROP TABLE IF EXISTS Booking");

        Log.i("ACTUALIZACION","Base de datos re-inisializada");
        onCreate(db);

    }

}
