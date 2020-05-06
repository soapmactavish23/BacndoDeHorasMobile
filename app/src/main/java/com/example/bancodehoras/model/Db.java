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
    public static String TABELA_BANCO_HORAS = "banco_horas";
    public Context context;

    public Db(Context context1) {
        super(context1, NOME_DB, null, VERSION);
        this.context = context1;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "
                +TABELA_FUNCIONARIO
                +" (id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR NOT NULL, telefone VARCHAR NOT NULL, horas VARCHAR NOT NULL)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "
                + TABELA_BANCO_HORAS
                + " (id_banco PRIMARY KEY AUTOINCREMENT, idfuncionario INTEGER NOT NULL, hora_extra Integer NOT NULL)"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}