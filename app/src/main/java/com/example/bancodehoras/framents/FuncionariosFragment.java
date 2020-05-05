package com.example.bancodehoras.framents;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;

import com.example.bancodehoras.R;
import com.example.bancodehoras.activity.FuncionarioActivity;
import com.example.bancodehoras.activity.MainActivity;
import com.example.bancodehoras.adapter.FuncionarioAdapter;
import com.example.bancodehoras.helper.RecyclerItemClickListener;
import com.example.bancodehoras.model.Db;
import com.example.bancodehoras.model.Funcionario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FuncionariosFragment extends Fragment {

    private RecyclerView recyclerView;
    private FuncionarioAdapter funcionarioAdapter;
    private Funcionario funcionario;
    private Funcionario funcionarioSelecionado;
    private List<Funcionario> listaFuncionario = new ArrayList<>();
    private FloatingActionButton btnNovoFuncionario;

    public FuncionariosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_funcionarios, container, false);

        recyclerView = view.findViewById(R.id.recyclerFuncionarios);

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getContext(),
                        recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                //Recuperar funcionario para edicao
                                funcionarioSelecionado = listaFuncionario.get(position);

                                //Enviar tarefa
                                Intent intent = new Intent(getContext(), FuncionarioActivity.class);
                                intent.putExtra("funcionarioSelecionado", funcionarioSelecionado);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                //Recuperar funcionario para remoção
                                funcionarioSelecionado = listaFuncionario.get(position);

                                //Criar o Alert
                                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
                                dialog.setTitle("Confirmar Exclusão");
                                dialog.setMessage("Deseja Excluir o funcionário: "+ funcionarioSelecionado.getNome() + "?");
                                dialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Db db = new Db(getContext());
                                        if(db.deletarFuncionario(funcionarioSelecionado.getId())){
                                            carregarFuncionarios();
                                            Toast.makeText(
                                                    getContext(),
                                                    "Sucesso ao excluir funcionário",
                                                    Toast.LENGTH_SHORT
                                            ).show();
                                        }
                                    }
                                });
                                dialog.setNegativeButton("Não", null);

                                //Exibir Dialog
                                dialog.create();
                                dialog.show();

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        btnNovoFuncionario = view.findViewById(R.id.btnNovoFuncionario);
        btnNovoFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), FuncionarioActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        carregarFuncionarios();
    }

    public void carregarFuncionarios(){
        //Limpa as existentes
        listaFuncionario.clear();

        //Listar Funcionarios
        Db db = new Db(getContext());
        listaFuncionario = db.listarFuncionario();

        //Configuar um adapter
        funcionarioAdapter = new FuncionarioAdapter(listaFuncionario);

        //Configurar um RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(funcionarioAdapter);

    }

}
