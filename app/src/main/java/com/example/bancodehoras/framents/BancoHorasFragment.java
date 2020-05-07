package com.example.bancodehoras.framents;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.example.bancodehoras.R;
import com.example.bancodehoras.activity.BancoHorasActivity;
import com.example.bancodehoras.activity.FuncionarioActivity;
import com.example.bancodehoras.adapter.BancoHorasAdapter;
import com.example.bancodehoras.helper.FuncionarioDAO;
import com.example.bancodehoras.helper.RecyclerItemClickListener;
import com.example.bancodehoras.model.Funcionario;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BancoHorasFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Funcionario> listaBancoHoras = new ArrayList<>();
    private BancoHorasAdapter bancoHorasAdapter;
    private Funcionario funcionarioSelecionado;

    public BancoHorasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_banco_horas, container, false);

        recyclerView = view.findViewById(R.id.recyclerBancoHoras);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Recuperar funcionario para edicao
                                funcionarioSelecionado = listaBancoHoras.get(position);

                                //Enviar tarefa
                                Intent intent = new Intent(getContext(), BancoHorasActivity.class);
                                intent.putExtra("funcionarioSelecionado", funcionarioSelecionado);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        carregarBancoHoras();
    }

    public void carregarBancoHoras(){
        try{

            //Limpa as existentes
            listaBancoHoras.clear();

            //Listar Banco
            FuncionarioDAO bancoHorasDAO = new FuncionarioDAO(getContext());
            listaBancoHoras = bancoHorasDAO.listarFuncionario();

            //Configurar um adaptador
            bancoHorasAdapter = new BancoHorasAdapter(listaBancoHoras);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
            recyclerView.setAdapter(bancoHorasAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
