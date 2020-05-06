package com.example.bancodehoras.helper;

import com.example.bancodehoras.model.BancoHoras;

import java.util.List;

public interface iBancoHorasDAO {

    public void salvar(BancoHoras bancoHoras);
    public List<BancoHoras> listar();
    public boolean deletar(Long id);
    public boolean atualizar(BancoHoras bancoHoras);

}
