package com.natanielsoares.eceller;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.natanielsoares.eceller.Objetos.Usuario;
import com.natanielsoares.eceller.WebService.Usuario.CadastroUsuarioSimplesWS;

public class CadastroUsuarioSimples extends AppCompatActivity
{
    EditText edtCadastroUsuarioSimplesNomeCompleto;
    EditText edtCadastroUsuarioSimplesEmail;
    EditText edtCadastroUsuarioSimplesRptEmail;
    EditText edtCadastroUsuarioSimplesSenha;
    EditText edtCadastroUsuarioSimplesRptSenha;
    Button btnCadastroUsuarioSimples;
    String idG;
    String validacao = "";
    boolean[] aprovados = new boolean[5] ;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario_simples);

        String nome = getIntent().getStringExtra("nomeUsuarioGoogle");
        String email = getIntent().getStringExtra("emailUsuarioGoogle");

        idG = getIntent().getStringExtra("idUsuarioGoogle");


        edtCadastroUsuarioSimplesNomeCompleto = (EditText) findViewById(R.id.edtCadastraUsuarioSimplesNomeCompleto);
        edtCadastroUsuarioSimplesEmail = (EditText) findViewById(R.id.edtCadastraUsuarioSimplesEmail);
        edtCadastroUsuarioSimplesRptEmail = (EditText) findViewById(R.id.edtCadastraUsuarioSimplesRptEmail);
        edtCadastroUsuarioSimplesSenha = (EditText) findViewById(R.id.edtCadastraUsuarioSimplesSenha);
        edtCadastroUsuarioSimplesRptSenha = (EditText) findViewById(R.id.edtCadastraUsuarioSimplesRptSenha);
        btnCadastroUsuarioSimples = (Button) findViewById(R.id.btnCadastroUsuarioSimples);


        edtCadastroUsuarioSimplesNomeCompleto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    validaNome(edtCadastroUsuarioSimplesNomeCompleto,"Nome Completo");
                }
            }
        });

        edtCadastroUsuarioSimplesEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    validaEmail(edtCadastroUsuarioSimplesEmail,"Email");
                }
            }
        });

        edtCadastroUsuarioSimplesRptEmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    validaRptEmail(edtCadastroUsuarioSimplesRptEmail,"Repitir o Email");
                }
            }
        });

        edtCadastroUsuarioSimplesSenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    validaSenha(edtCadastroUsuarioSimplesSenha,"Senha");
                }
            }
        });

        edtCadastroUsuarioSimplesRptSenha.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus)
                {
                    validaRptSenha(edtCadastroUsuarioSimplesRptSenha,"Repitir Senha");
                }
            }
        });


        edtCadastroUsuarioSimplesNomeCompleto.setText(nome);

        if(email != null)
        {
            edtCadastroUsuarioSimplesEmail.setEnabled(false);
            edtCadastroUsuarioSimplesEmail.setText(email);
            edtCadastroUsuarioSimplesRptEmail.setEnabled(false);
            edtCadastroUsuarioSimplesRptEmail.setText(email);
        }
        else
        {
            edtCadastroUsuarioSimplesEmail.setEnabled(true);
            edtCadastroUsuarioSimplesRptEmail.setVisibility(View.VISIBLE);
        }

        btnCadastroUsuarioSimples.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int resultadoVal = 0;

                resultadoVal += validaNome(edtCadastroUsuarioSimplesNomeCompleto,"Nome Completo");
                resultadoVal += validaEmail(edtCadastroUsuarioSimplesEmail,"Email");
                resultadoVal += validaRptEmail(edtCadastroUsuarioSimplesRptEmail,"Repetir Email");
                resultadoVal += validaSenha(edtCadastroUsuarioSimplesSenha,"Senha");
                resultadoVal += validaRptSenha(edtCadastroUsuarioSimplesRptSenha,"Repetir Senha");

                if(resultadoVal != 5)
                    return;

                Usuario usuario = new Usuario();
                usuario.setNomeCompleto(edtCadastroUsuarioSimplesNomeCompleto.getText().toString());
                usuario.setEmail(edtCadastroUsuarioSimplesEmail.getText().toString());
                usuario.setSenha(edtCadastroUsuarioSimplesSenha.getText().toString());
                usuario.setIdGoogle(idG);

                new CadastroUsuarioSimplesWS(CadastroUsuarioSimples.this,usuario).cadastrar();
            }
        });

    }

    public int validaNome(final EditText editText, final String campo){

        boolean resultaoCampo = validaCampo(editText,campo);
        if(!resultaoCampo)
            return 0;

        String value = editText.getText().toString();
        if(value.length() < 3)
        {
            validacao = "Nome muito curto";
            editText.setBackgroundColor(Color.RED); //mostra como incorreto
            Toast.makeText(CadastroUsuarioSimples.this, validacao, Toast.LENGTH_LONG).show();
            return 0;
        }

        editText.setBackgroundColor(Color.GREEN);
        return 1;
    }


    public int validaEmail(final EditText editText, final String campo)
    {
        boolean resultaoCampo = validaCampo(editText,campo);
        if(!resultaoCampo)
            return 0;

        editText.setBackgroundColor(Color.GREEN);
        return 1;
    }

    public int validaRptEmail(final EditText editText, final String campo)
    {
        boolean resultaoCampo = validaCampo(editText,campo);
        if(!resultaoCampo)
            return 0;

        String value = editText.getText().toString();

        if(!value.equals(edtCadastroUsuarioSimplesEmail.getText().toString()))
        {
            validacao = "Os emails não se coincidem";
            editText.setBackgroundColor(Color.RED); //mostra como incorreto
            Toast.makeText(CadastroUsuarioSimples.this, validacao, Toast.LENGTH_LONG).show();
            return 0;
        }
        editText.setBackgroundColor(Color.GREEN);
        return 1;
    }
    public int validaSenha(final EditText editText, final String campo)
    {
        boolean resultaoCampo = validaCampo(editText,campo);
        if(!resultaoCampo)
            return 0;

        String value = editText.getText().toString();

        if(value.length() < 8)
        {
            validacao = "Minímo de 8 caracteres para a senha";
            editText.setBackgroundColor(Color.RED); //mostra como incorreto
            Toast.makeText(CadastroUsuarioSimples.this, validacao, Toast.LENGTH_LONG).show();
            return 0;
        }

        editText.setBackgroundColor(Color.GREEN);
        return 1;
    }

    public int validaRptSenha(final EditText editText, final String campo)
    {
        boolean resultaoCampo = validaCampo(editText,campo);
        if(!resultaoCampo)
            return 0;

        String value = editText.getText().toString();

        if(!value.equals(edtCadastroUsuarioSimplesSenha.getText().toString()))
        {
            validacao = "As senhas não se coincidem";
            editText.setBackgroundColor(Color.RED); //mostra como incorreto
            Toast.makeText(CadastroUsuarioSimples.this, validacao, Toast.LENGTH_LONG).show();
            return 0;
        }

        editText.setBackgroundColor(Color.GREEN);
        return 1;
    }



    public boolean validaCampo(final EditText editText, final String campo)
    {
        final boolean resultado;
        String value = editText.getText().toString();
        if (value.equals(""))
        {
            editText.setBackgroundColor(Color.RED); //mostra como incorreto
            validacao = "Insira o campo '" + campo + "'";
            Toast.makeText(CadastroUsuarioSimples.this, validacao, Toast.LENGTH_LONG).show();
            return false;
        } else {
            return true;
        }
    }
}
