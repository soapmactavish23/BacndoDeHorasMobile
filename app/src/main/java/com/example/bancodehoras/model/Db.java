package com.example.bancodehoras.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Db extends SQLiteOpenHelper {

    public static int VERSION = 1;
    public static String NOME_DB = "banco_horas";
    public static String TABELA_FUNCIONARIO = "funcionario";
    public Context context;

    public Db(Context context1) {
        super(context1, NOME_DB, null, VERSION);
        this.context = context1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                +TABELA_FUNCIONARIO
                +" (id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR NOT NULL, telefone VARCHAR NOT NULL, horas INTEGER NOT NULL, horas_extras INTEGER DEFAULT '0')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}