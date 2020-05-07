package com.example.bancodehoras.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bancodehoras.model.Db;
import com.example.bancodehoras.model.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO implements iFuncionarioDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;


    public FuncionarioDAO(Context context) {
        Db db = new Db(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();
    }

    @Override
    public void salvarFuncionario(Funcionario funcionario) {
        ContentValues cv = new ContentValues();
        cv.put("nome", funcionario.getNome());
        cv.put("telefone", funcionario.getTelefone());
        cv.put("horas", Integer.toString(funcionario.getHoras()));
        escreve.insert(Db.TABELA_FUNCIONARIO, null , cv);
    }

    @Override
    public List<Funcionario> listarFuncionario() {
        List<Funcionario> funcionarios = new ArrayList<>();

        String sql = "SELECT * FROM " + Db.TABELA_FUNCIONARIO + ";";
        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){
            Funcionario funcionario = new Funcionario();

            Long id = c.getLong(c.getColumnIndex("id"));
            String nomeFuncionario = c.getString(c.getColumnIndex("nome"));
            String telefoneFuncionario = c.getString(c.getColumnIndex("telefone"));
            Integer horasFuncionario = c.getInt(c.getColumnIndex("horas"));
            Integer horasExtras = c.getInt(c.getColumnIndex("horas_extras"));
            funcionario.setId(id);
            funcionario.setNome(nomeFuncionario);
            funcionario.setTelefone(telefoneFuncionario);
            funcionario.setHoras(horasFuncionario);
            funcionario.setHoras_extras(horasExtras);
            funcionarios.add(funcionario);

        }

        return funcionarios;
    }

    @Override
    public boolean deletarFuncionario(Long id) {
        try{
            String[] args = {Long.toString(id)};
            escreve.delete(Db.TABELA_FUNCIONARIO, "id=?", args);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizarFuncionario(Funcionario funcionario) {
        ContentValues cv = new ContentValues();
        cv.put("nome", funcionario.getNome());
        cv.put("telefone", funcionario.getTelefone());
        cv.put("horas", Integer.toString(funcionario.getHoras()));
        try{
            String[] args = {Long.toString(funcionario.getId())};
            escreve.update(Db.TABELA_FUNCIONARIO, cv, "id=?", args);
            Log.e("INFO", "Sucesso ao atualizar funcionário");

        }catch (Exception e){

            Log.e("INFO", "Erro ao atualizar funcionário: " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean atualizarHora(Funcionario funcionario) {
        ContentValues cv = new ContentValues();
        cv.put("horas_extras", Integer.toString(funcionario.getHoras_extras()));
        try {

            String[] args = {Long.toString(funcionario.getId())};
            escreve.update(Db.TABELA_FUNCIONARIO, cv, "id=?", args);
            Log.e("INFO", "Sucesso ao atualizar funcionário");
            return true;

        }catch (Exception e){

            Log.e("INFO", "Erro ao atualizar funcionário" + e.getMessage());
            return false;

        }
    }


}
