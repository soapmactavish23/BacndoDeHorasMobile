package com.example.bancodehoras.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bancodehoras.R;
import com.example.bancodehoras.helper.Permissao;
import com.example.bancodehoras.model.Db;
import com.example.bancodehoras.model.Funcionario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class FuncionarioActivity extends AppCompatActivity {

    private TextInputEditText txtNome;
    private EditText txtTelefone;
    private EditText txtCodArea;
    private EditText txtDdd;
    private TextInputEditText txtHoras;
    private FloatingActionButton btnSalvaFuncionario;
    private Funcionario funcionarioAtual = new Funcionario();
    private Db db = new Db(FuncionarioActivity.this);
    private String[] permissaoMensagem = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);

        Permissao.validaPermissoes(1,this, permissaoMensagem);

        txtNome = findViewById(R.id.txtNomeEdit);
        txtTelefone = findViewById(R.id.txtTelefoneEdit);
        txtCodArea = findViewById(R.id.editCodArea);
        txtDdd = findViewById(R.id.editDdd);
        txtHoras = findViewById(R.id.txtHoras);
        btnSalvaFuncionario = findViewById(R.id.btnNovoFuncionario);

        //Criando Formato da Mascara
        SimpleMaskFormatter simpleMaskCodArea = new SimpleMaskFormatter("+NN");
        SimpleMaskFormatter simpleMaskDDD = new SimpleMaskFormatter("NN");
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NNNNN-NNNN");
        SimpleMaskFormatter simpleMaskHoras = new SimpleMaskFormatter("NN");

        //Ajustando para os textos
        MaskTextWatcher maskCodArea = new MaskTextWatcher(txtCodArea, simpleMaskCodArea);
        MaskTextWatcher maskDdd = new MaskTextWatcher(txtDdd, simpleMaskDDD);
        MaskTextWatcher maskTelefone = new MaskTextWatcher(txtTelefone, simpleMaskFormatter);
        MaskTextWatcher maskHoas = new MaskTextWatcher(txtHoras, simpleMaskHoras);

        //Aplicando Mascara
        txtCodArea.addTextChangedListener(maskCodArea);
        txtDdd.addTextChangedListener(maskDdd);
        txtTelefone.addTextChangedListener(maskTelefone);
        txtHoras.addTextChangedListener(maskHoas);

        //Recuperar funconario, case seja edicao
        funcionarioAtual = (Funcionario) getIntent().getSerializableExtra("funcionarioSelecionado");

        if (funcionarioAtual != null){
            txtNome.setText(funcionarioAtual.getNome());
            txtTelefone.setText(funcionarioAtual.getTelefone().substring(4, 13));
            txtCodArea.setText(funcionarioAtual.getTelefone().substring(0,2));
            txtDdd.setText(funcionarioAtual.getTelefone().substring(2,4));
            txtHoras.setText(funcionarioAtual.getHoras());
        }

        //Salvar Funcionario
        btnSalvaFuncionario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = txtNome.getText().toString();
                String horas = txtHoras.getText().toString();

                String telefoneCompleto =
                                txtCodArea.getText().toString()
                                + txtDdd.getText().toString()
                                + txtTelefone.getText().toString();

                String telefone = telefoneCompleto.replace("+", "");
                telefone = telefone.replace("-", "");

                if(funcionarioAtual != null){
                    if(nome.equals("") || telefone.equals("") || horas.equals("")){
                        Toast.makeText(getApplicationContext(), "Preencha o campo", Toast.LENGTH_SHORT).show();
                    }else{

                        Funcionario funcionario = new Funcionario();
                        funcionario.setId(funcionarioAtual.getId());
                        funcionario.setNome(nome);
                        funcionario.setTelefone(telefone);
                        funcionario.setHoras(horas);

                        //Atualizar no banco de dados
                        if(db.atualizarFuncionario(funcionario)){
                            Toast.makeText(
                                    FuncionarioActivity.this,
                                    "Funcionário Atualizada com Sucesso!",
                                    Toast.LENGTH_SHORT
                            ).show();

                            //Enviar SMS
                            String mensagem = "Olá " + nome + ", você acabou de ser atualizado no Banco de Horas Mobile";
                            boolean enviado = enviarSMS("+" + telefone, mensagem);

                            //Voltar para a intent
                            finish();
                        }
                    }
                }else{
                    if(nome.equals("") || telefone.equals("") || horas.equals("")){
                        Toast.makeText(getApplicationContext(), "Preencha o campo", Toast.LENGTH_SHORT).show();
                    }else{
                        //Criando um funcionario
                        Funcionario funcionario = new Funcionario();
                        funcionario.setNome(nome);
                        funcionario.setTelefone(telefone);
                        funcionario.setHoras(horas);

                        //Salvar objeto
                        Db db = new Db(getApplicationContext());
                        db.salvarFuncionario(funcionario);
                        Toast.makeText(getApplicationContext(), "Funcionário Salvo com sucesso", Toast.LENGTH_SHORT).show();

                        //Enviar SMS
                        String mensagem = "Olá " + nome + ", você acabou de ser cadastrado no Banco de Horas Mobile";
                        boolean enviado = enviarSMS("+" + telefone, mensagem);

                        //Voltar para a intent
                        finish();
                    }
                }
            }
        });
    }

    public boolean enviarSMS(String telefone, String mensagem){
        try{

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(telefone, null, mensagem, null, null);
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){

        super.onRequestPermissionsResult(requestCode, permissions,grantResults);
        for(int resultado : grantResults){
            if (resultado == PackageManager.PERMISSION_DENIED){
                alertValidacaoPermissao();
            }
        }
    }

    public void alertValidacaoPermissao(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permissão Negada");
        builder.setMessage("Para utilizar o app, é necessário aceitar as permissões");

        builder.setPositiveButton("CONFIRMAR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        builder.create();
        builder.show();

    }

}
