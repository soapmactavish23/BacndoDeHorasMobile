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
                +" (id INTEGER PRIMARY KEY AUTOINCREMENT, nome VARCHAR NOT NULL, horas INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void salvarFuncionario(String nome, String horas){
        ContentValues cv = new ContentValues();
        cv.put("nome", nome);
        cv.put("horas", horas);
        getWritableDatabase().insert(Db.TABELA_FUNCIONARIO, null , cv);
    }

    public List<Funcionario> listarFuncionario(){

        List<Funcionario> funcionarios = new ArrayList<>();

        String sql = "SELECT id, nome FROM " + Db.TABELA_FUNCIONARIO + ";";
        Cursor c = getReadableDatabase().rawQuery(sql, null);

        while (c.moveToNext()){
            Funcionario funcionario = new Funcionario();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeFuncionario = c.getString(c.getColumnIndex("nome"));

            funcionario.setId(id);
            funcionario.setNome(nomeFuncionario);

            funcionarios.add(funcionario);

        }

        return funcionarios;

    }

    public boolean deletarFuncionario(Long id){
        try{
            String[] args = {Long.toString(id)};
            getWritableDatabase().delete(Db.TABELA_FUNCIONARIO, "id=?", args);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    public boolean atualizarFuncionario(Funcionario funcionario){
        ContentValues cv = new ContentValues();
        cv.put("nome", funcionario.getNome());
        cv.put("horas", Integer.toString(funcionario.getHoras()));
        try{
            String[] args = {Long.toString(funcionario.getId())};
            getWritableDatabase().update(Db.TABELA_FUNCIONARIO, cv, "id=?", args);
            Log.e("INFO", "Sucesso ao atualizar funcionário");

        }catch (Exception e){

            Log.e("INFO", "Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }

        return true;

    }

}
