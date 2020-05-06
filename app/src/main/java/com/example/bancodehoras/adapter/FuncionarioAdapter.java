package com.example.bancodehoras.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bancodehoras.R;
import com.example.bancodehoras.model.Funcionario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.List;

public class FuncionarioAdapter extends RecyclerView.Adapter<FuncionarioAdapter.MyViewHolder> {

    private List<Funcionario> listaFuncionario;

    public FuncionarioAdapter(List<Funcionario> listaFuncionario) {
        this.listaFuncionario = listaFuncionario;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.lista_funcionario_adapter, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Funcionario funcionario = listaFuncionario.get(position);
        holder.funcionario.setText(funcionario.getNome());

        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("+NN (NN) NNNNN-NNNN");
        MaskTextWatcher maskTelefone = new MaskTextWatcher(holder.telefone, simpleMaskFormatter);
        holder.telefone.addTextChangedListener(maskTelefone);
        holder.telefone.setText(funcionario.getTelefone());

    }

    @Override
    public int getItemCount() {
        return this.listaFuncionario.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView funcionario;
        TextView telefone;

        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            funcionario = itemView.findViewById(R.id.textFuncionario);
            telefone = itemView.findViewById(R.id.textHoras);

        }

    }

}
