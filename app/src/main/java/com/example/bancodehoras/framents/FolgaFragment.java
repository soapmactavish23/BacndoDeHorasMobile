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
import com.example.bancodehoras.activity.FolgaActivity;
import com.example.bancodehoras.adapter.BancoHorasAdapter;
import com.example.bancodehoras.adapter.FolgaAdapter;
import com.example.bancodehoras.helper.FuncionarioDAO;
import com.example.bancodehoras.helper.RecyclerItemClickListener;
import com.example.bancodehoras.model.Funcionario;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FolgaFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Funcionario> listaFolga = new ArrayList<>();
    private FolgaAdapter folgaAdapter;
    private Funcionario funcionarioSelecionado;

    public FolgaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_folga, container, false);

        recyclerView = view.findViewById(R.id.recyclerFolga);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Recuperar funcionario para edicao
                                funcionarioSelecionado = listaFolga.get(position);

                                //Enviar tarefa
                                Intent intent = new Intent(getContext(), FolgaActivity.class);
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

        return view;
    }
    @Override
    public void onStart() {
        super.onStart();
        carregarFolgas();
    }

    public void carregarFolgas(){
        try{

            //Limpa as existentes
            listaFolga.clear();

            //Listar Banco
            FuncionarioDAO bancoHorasDAO = new FuncionarioDAO(getContext());
            listaFolga = bancoHorasDAO.listarFolgas();

            //Configurar um adaptador
            folgaAdapter = new FolgaAdapter(listaFolga);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
            recyclerView.setAdapter(folgaAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
