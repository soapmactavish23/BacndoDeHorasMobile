package com.example.bancodehoras.model;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.telephony.SmsManager;

import androidx.appcompat.app.AlertDialog;

import java.io.Serializable;

public class Funcionario implements Serializable {

    private Long id;
    private String nome;
    private String telefone;
    private Integer horas;
    private Integer horas_extras;


    public Integer getHoras_extras() {
        return horas_extras;
    }

    public void setHoras_extras(Integer horas_extras) {
        this.horas_extras = horas_extras;
    }

    public Integer getHoras() { return horas; }

    public void setHoras(Integer horas) { this.horas = horas; }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

}
