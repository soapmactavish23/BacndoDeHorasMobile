package com.example.bancodehoras.helper;

import com.example.bancodehoras.model.Funcionario;

import java.util.List;

public interface iFuncionarioDAO {

    public void salvarFuncionario(Funcionario funcionario);
    public List<Funcionario> listarFuncionario();
    public boolean deletarFuncionario(Long id);
    public boolean atualizarFuncionario(Funcionario funcionario);

}
