package com.example.bancodehoras.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bancodehoras.R;
import com.example.bancodehoras.model.BancoHoras;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import java.util.List;

public class BancoHorasAdapter extends RecyclerView.Adapter<BancoHorasAdapter.MyViewHolder>{

    public List<BancoHoras> listaBanco;

    public BancoHorasAdapter(List<BancoHoras> listaBanco) {
        this.listaBanco = listaBanco;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_banco_adapter, parent, false);

        return new MyViewHolder(itemLista);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        BancoHoras bancoHoras = listaBanco.get(position);
        holder.funcionario.setText(bancoHoras.getNomeFuncionario());

        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("Horas: NN");
        MaskTextWatcher maskHoras = new MaskTextWatcher(holder.horas, simpleMaskFormatter);
        holder.horas.addTextChangedListener(maskHoras);
        holder.horas.setText(bancoHoras.getHoras().toString());
    }

    @Override
    public int getItemCount() {
        return this.listaBanco.size();
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
