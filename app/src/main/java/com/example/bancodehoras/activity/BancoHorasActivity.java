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
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bancodehoras.R;
import com.example.bancodehoras.helper.FuncionarioDAO;
import com.example.bancodehoras.model.Funcionario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

public class BancoHorasActivity extends AppCompatActivity {

    private TextView txtNome;
    private Funcionario funcionarioAtual = new Funcionario();
    private EditText txtHoras;
    private ImageButton btnMais;
    private ImageButton btnMenos;
    private String[] permissaoMensagem = new String[]{
            Manifest.permission.SEND_SMS,
            Manifest.permission.INTERNET
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banco_horas);

        txtNome = findViewById(R.id.txtNomeFuncionario);
        txtHoras = findViewById(R.id.txtHorasEstras);
        btnMais = findViewById(R.id.btnMais);
        btnMenos = findViewById(R.id.btnMenos);

        //Recuperar funconario
        funcionarioAtual = (Funcionario) getIntent().getSerializableExtra("funcionarioSelecionado");
        txtNome.setText(funcionarioAtual.getNome());

        //Formato das horas
        SimpleMaskFormatter simpleMaskFormatter = new SimpleMaskFormatter("NN");
        MaskTextWatcher maskTextWatcher = new MaskTextWatcher(txtHoras,simpleMaskFormatter);
        txtHoras.addTextChangedListener(maskTextWatcher);


        //Somando as horas
        btnMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtHoras.getText().toString().equals("") || txtHoras.getText().toString() == null){
                    Toast.makeText(getApplicationContext(), "Preencha o Campo", Toast.LENGTH_SHORT).show();
                }else{
                    //Operacoes
                    int hora_extra = Integer.parseInt(txtHoras.getText().toString());
                    int horas_atuais;
                    if (funcionarioAtual.getHoras_extras() == null) horas_atuais = 0;
                    else horas_atuais = funcionarioAtual.getHoras_extras();
                    int horas_res = horas_atuais + hora_extra;

                    FuncionarioDAO funcionarioDAO = new FuncionarioDAO(getApplicationContext());
                    funcionarioAtual.setHoras_extras(horas_res);

                    if(funcionarioDAO.atualizarHora(funcionarioAtual)){
                        Toast.makeText(
                                getApplicationContext(),
                                "Sucesso ao Somar horas",
                                Toast.LENGTH_SHORT
                        ).show();

                        //Enviar SMS
                        String mensagem = "Olá " + funcionarioAtual.getNome() + ", você acabou de ter "+horas_res+" acrescentadas no Banco de Horas Mobile";
                        boolean enviado = enviarSMS("+" + funcionarioAtual.getTelefone(), mensagem);

                        //Finalizar a intent
                        finish();
                    }
                }
            }
        });

        //Subtraindo horas
        btnMenos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtHoras.getText().toString().equals("") || txtHoras.getText().toString() == null){
                    Toast.makeText(getApplicationContext(), "Preencha o Campo", Toast.LENGTH_SHORT).show();
                }else{
                    //Operacoes
                    int hora_extra = Integer.parseInt(txtHoras.getText().toString());
                    int horas_atuais;
                    if (funcionarioAtual.getHoras_extras() == null) horas_atuais = 0;
                    else horas_atuais = funcionarioAtual.getHoras_extras();
                    int horas_res = horas_atuais - hora_extra;

                    FuncionarioDAO funcionarioDAO = new FuncionarioDAO(getApplicationContext());
                    funcionarioAtual.setId(funcionarioAtual.getId());
                    funcionarioAtual.setHoras_extras(horas_res);

                    if(funcionarioDAO.atualizarHora(funcionarioAtual)){
                        Toast.makeText(
                                getApplicationContext(),
                                "Sucesso ao Subtrair horas",
                                Toast.LENGTH_SHORT
                        ).show();

                        //Enviar SMS
                        String mensagem = "Olá " + funcionarioAtual.getNome() + ", você acabou de ter "+horas_res+" descontadas no Banco de Horas Mobile";
                        boolean enviado = enviarSMS("+" + funcionarioAtual.getTelefone(), mensagem);

                        //Finalizar a intent
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
