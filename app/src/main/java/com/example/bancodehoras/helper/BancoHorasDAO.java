package com.example.bancodehoras.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bancodehoras.model.BancoHoras;
import com.example.bancodehoras.model.Db;
import com.example.bancodehoras.model.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class BancoHorasDAO implements iBancoHorasDAO {

    private SQLiteDatabase escreve;
    private SQLiteDatabase le;


    public BancoHorasDAO(Context context) {

        Db db = new Db(context);
        escreve = db.getWritableDatabase();
        le = db.getReadableDatabase();

    }

    @Override
    public void salvar(BancoHoras bancoHoras) {
        ContentValues cv = new ContentValues();
        cv.put("idfuncionario", bancoHoras.getIdfuncionario());
        cv.put("hora_extra", bancoHoras.getHoras());
        escreve.insert(Db.TABELA_BANCO_HORAS, null , cv);
    }

    @Override
    public List<BancoHoras> listar() {
        List<BancoHoras> bancosHoras = new ArrayList<BancoHoras>();

        String sql = "SELECT idBanco, idfuncionario , hora_extra FROM banco_horas";

        Cursor c = le.rawQuery(sql, null);

        while (c.moveToNext()){
            BancoHoras bancoHoras = new BancoHoras();

            Long id = c.getLong(c.getColumnIndex("id_banco"));
            Long idfuncionario = c.getLong(c.getColumnIndex("idfuncionario"));
            Integer hora_extra = c.getInt(c.getColumnIndex("hora_extra"));
            String nomeFuncionario = c.getString(c.getColumnIndex("nome"));

            bancoHoras.setId_banco(id);
            bancoHoras.setIdfuncionario(idfuncionario);
            bancoHoras.setHoras(hora_extra);
            bancoHoras.setNomeFuncionario(nomeFuncionario);
            bancosHoras.add(bancoHoras);

        }

        return bancosHoras;
    }

    @Override
    public boolean deletar(Long id) {
        try{
            String[] args = {Long.toString(id)};
            escreve.delete(Db.TABELA_BANCO_HORAS, "id_banco=?", args);
        }catch (Exception e){
            return false;
        }
        return true;
    }

    @Override
    public boolean atualizar(BancoHoras bancoHoras) {
        ContentValues cv = new ContentValues();
        cv.put("id_banco", bancoHoras.getId_banco());
        cv.put("idfuncionario", bancoHoras.getIdfuncionario());
        cv.put("hora_extra", bancoHoras.getHoras());
        try{
            String[] args = {Long.toString(bancoHoras.getId_banco())};
            escreve.update(Db.TABELA_BANCO_HORAS, cv, "id_banco=?", args);
            Log.e("INFO", "Sucesso ao atualizar Banco");

        }catch (Exception e){

            Log.e("INFO", "Erro ao atualizar Banco: " + e.getMessage());
            return false;
        }

        return true;
    }
}
