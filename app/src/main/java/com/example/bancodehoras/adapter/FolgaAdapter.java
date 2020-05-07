package com.example.bancodehoras.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bancodehoras.R;
import com.example.bancodehoras.model.Funcionario;

import java.util.List;

public class FolgaAdapter extends RecyclerView.Adapter<FolgaAdapter.MyViewHolder> {

    private List<Funcionario> listaFolga;

    public FolgaAdapter(List<Funcionario> listaFolga) { this.listaFolga = listaFolga; }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_folga_adapter, parent, false);

        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try{
            Funcionario funcionarioClass = listaFolga.get(position);
            holder.funcionario.setText(funcionarioClass.getNome());

            String horas_extras = Integer.toString(funcionarioClass.getHoras_extras());
            if(horas_extras == null) horas_extras = "0";
            else horas_extras = Integer.toString(funcionarioClass.getHoras_extras());
            holder.horas.setText("00/00/0000");

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return this.listaFolga.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView funcionario;
        TextView horas;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            funcionario = itemView.findViewById(R.id.textFuncionario);
            horas = itemView.findViewById(R.id.textHoras);

        }
    }
}
