package com.example.bancodehoras.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bancodehoras.R;
import com.example.bancodehoras.model.Db;
import com.example.bancodehoras.model.Funcionario;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class FuncionarioActivity extends AppCompatActivity {

    private TextInputEditText txtNome;
    private TextInputEditText txtHoras;
    private FloatingActionButton btnSalvaFuncionario;
    private Funcionario funcionarioAtual = new Funcionario();
    private Db db = new Db(FuncionarioActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);

        txtNome = findViewById(R.id.txtNomeEdit);
        txtHoras = findViewById(R.id.txtHorasEdit);
        btnSalvaFuncionario = findViewById(R.id.btnNovoFuncionario);

        //Recuperar funconario, case seja edicao
        funcionarioAtual = (Funcionario) getIntent().getSerializableExtra("funcionarioSelecionado");

        if (funcionarioAtual != null){
            txtNome.setText(funcionarioAtual.getNome());
            txtHoras.setText(Integer.toString(funcionarioAtual.getHoras()));
        }

        //Salvar Funcionario
        btnSalvaFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = txtNome.getText().toString();
                String horas = txtHoras.getText().toString();

                if(funcionarioAtual != null){
                    if(nome.equals("") || horas.equals("")){
                        Toast.makeText(getApplicationContext(), "Preencha o campo", Toast.LENGTH_SHORT).show();
                    }else{

                        Funcionario funcionario = new Funcionario();
                        funcionario.setId(funcionarioAtual.getId());
                        funcionario.setNome(nome);
                        funcionario.setHoras(Integer.parseInt(horas));

                        //Atualizar no banco de dados
                        if(db.atualizarFuncionario(funcionario)){
                            Toast.makeText(
                                    FuncionarioActivity.this,
                                    "Funcionário Atualizada com Sucesso!",
                                    Toast.LENGTH_SHORT
                            ).show();
                            //Voltar para a intent
                            finish();
                        }
                    }
                }else{
                    if(nome.equals("") || horas.equals("")){
                        Toast.makeText(getApplicationContext(), "Preencha o campo", Toast.LENGTH_SHORT).show();
                    }else{
                        //Salvar objeto
                        Db db = new Db(getApplicationContext());
                        db.salvarFuncionario(nome, horas);
                        Toast.makeText(getApplicationContext(), "Funcionário Salvo com sucesso", Toast.LENGTH_SHORT).show();
                        //Voltar para a intent
                        finish();
                    }
                }
            }
        });


    }
}
