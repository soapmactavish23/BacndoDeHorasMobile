package com.example.bancodehoras.framents;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.bancodehoras.R;
import com.example.bancodehoras.adapter.BancoHorasAdapter;
import com.example.bancodehoras.adapter.FuncionarioAdapter;
import com.example.bancodehoras.helper.BancoHorasDAO;
import com.example.bancodehoras.model.BancoHoras;
import com.example.bancodehoras.model.Db;
import com.example.bancodehoras.model.Funcionario;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BancoHorasFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<BancoHoras> listaBancoHoras = new ArrayList<>();
    private BancoHorasAdapter bancoHorasAdapter;

    public BancoHorasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banco_horas, container, false);

        recyclerView = view.findViewById(R.id.recyclerBancoHoras);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        carregarBancoHoras();
    }

    public void carregarBancoHoras(){
        //Limpa as existentes
        listaBancoHoras.clear();

        //Listar Banco
        BancoHorasDAO bancoHorasDAO = new BancoHorasDAO(getContext());
        //listaBancoHoras = bancoHorasDAO.listar();

        BancoHoras bancoHoras1 = new BancoHoras();
        bancoHoras1.setNomeFuncionario("Henrick");
        bancoHoras1.setHoras(10);
        //bancoHoras1.setIdfuncionario();
        listaBancoHoras.add(bancoHoras1);

        //Configurar um adaptador
        bancoHorasAdapter = new BancoHorasAdapter(listaBancoHoras);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(bancoHorasAdapter);
    }
}
