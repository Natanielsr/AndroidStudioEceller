package com.natanielsoares.eceller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.natanielsoares.eceller.Objetos.Cidade;
import com.natanielsoares.eceller.Objetos.Data;
import com.natanielsoares.eceller.Objetos.Estado;
import com.natanielsoares.eceller.Objetos.Usuario;
import com.natanielsoares.eceller.WebService.Localizacao.BuscarCidadesWS;
import com.natanielsoares.eceller.WebService.Localizacao.BuscarEstadoWS;
import com.natanielsoares.eceller.WebService.Usuario.CadastroUsuarioWS;

import java.util.Date;
import java.util.List;

public class CadastroUsuarioActivity extends AppCompatActivity {

    EditText edtCadastrarUsuarioNome;
    EditText edtCadastrarUsuarioDddTelefone;
    EditText edtCadastrarUsuarioTelefone;
    EditText edtCadastrarUsuarioDddCelular;
    EditText edtCadastrarUsuarioCelular;
    EditText edtCadastrarUsuarioCpf;
    DatePicker dpCadastrarUsuarioDataDeNasc;
    EditText edtCadastrarUsuarioEmail;
    EditText edtCadastrarUsuarioRptEmail;
    EditText edtCadastrarUsuarioSenha;
    EditText edtCadastrarUsuarioRptSenha;
    EditText edtCadastrarUsuarioCep;
    EditText edtCadastrarUsuarioEndereco;
    EditText edtCadastrarUsuarioNumeroEndereco;
    Spinner spCadastrarUsuarioCidade;
    Spinner spCadastrarUsuarioEstado;
    EditText edtCadastrarUsuarioComplemento;

    Button btnCadastrarUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        edtCadastrarUsuarioNome = (EditText) findViewById(R.id.edtCadastrarUsuarioNome);
        edtCadastrarUsuarioEmail = (EditText) findViewById(R.id.edtCadastrarUsuarioEmail);
        edtCadastrarUsuarioRptEmail = (EditText) findViewById(R.id.edtCadastrarUsuarioRptEmail);
        edtCadastrarUsuarioNome = (EditText) findViewById(R.id.edtCadastrarUsuarioNome);
        edtCadastrarUsuarioDddTelefone = (EditText) findViewById(R.id.edtCadastrarUsuarioDddTelefone);
        edtCadastrarUsuarioTelefone = (EditText) findViewById(R.id.edtCadastrarUsuarioTelefone);
        edtCadastrarUsuarioDddCelular = (EditText) findViewById(R.id.edtCadastrarUsuarioDddCelular);
        edtCadastrarUsuarioCelular = (EditText) findViewById(R.id.edtCadastrarUsuarioCelular);
        edtCadastrarUsuarioCpf = (EditText) findViewById(R.id.edtCadastrarUsuarioCpf);
        dpCadastrarUsuarioDataDeNasc = (DatePicker) findViewById(R.id.dpCadastrarUsuarioDataDeNasc);
        edtCadastrarUsuarioSenha = (EditText) findViewById(R.id.edtCadastrarUsuarioSenha);
        edtCadastrarUsuarioRptSenha = (EditText) findViewById(R.id.edtCadastrarUsuarioRptSenha);
        edtCadastrarUsuarioCep = (EditText) findViewById(R.id.edtCadastrarUsuarioCep);
        edtCadastrarUsuarioEndereco = (EditText) findViewById(R.id.edtCadastrarUsuarioEndereco);
        edtCadastrarUsuarioNumeroEndereco = (EditText) findViewById(R.id.edtCadastrarUsuarioNumeroEndereco);
        spCadastrarUsuarioCidade = (Spinner) findViewById(R.id.spCadastrarUsuarioCidade);
        spCadastrarUsuarioEstado = (Spinner) findViewById(R.id.spCadastrarUsuarioEstado);
        edtCadastrarUsuarioComplemento = (EditText) findViewById(R.id.edtCadastrarUsuarioComplemento);

        btnCadastrarUsuario = (Button) findViewById(R.id.btnCadastrarUsuario);


        String nome = getIntent().getStringExtra("nomeUsuarioGoogle");
        String email = getIntent().getStringExtra("emailUsuarioGoogle");
        int idG = getIntent().getIntExtra("idUsuarioGoogle",0);

        edtCadastrarUsuarioNome.setText(nome);

        if(email != null)
        {
            edtCadastrarUsuarioEmail.setEnabled(false);
            edtCadastrarUsuarioEmail.setText(email);
            edtCadastrarUsuarioRptEmail.setVisibility(View.INVISIBLE);
        }
        else
        {
            edtCadastrarUsuarioEmail.setEnabled(true);
            edtCadastrarUsuarioRptEmail.setVisibility(View.VISIBLE);
        }




        btnCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Usuario usuario = new Usuario();
                usuario.setNomeCompleto(edtCadastrarUsuarioNome.getText().toString());
                usuario.setDddTelefone(edtCadastrarUsuarioDddTelefone.getText().toString());
                usuario.setTelefone(edtCadastrarUsuarioTelefone.getText().toString());
                usuario.setDddCelular(Integer.parseInt(edtCadastrarUsuarioDddCelular.getText().toString()));
                usuario.setCelular(edtCadastrarUsuarioCelular.getText().toString());
                usuario.setCpf(edtCadastrarUsuarioCpf.getText().toString());

                Data date = new Data();
                date.setDia(dpCadastrarUsuarioDataDeNasc.getDayOfMonth());
                date.setMes(dpCadastrarUsuarioDataDeNasc.getMonth());
                date.setAno(dpCadastrarUsuarioDataDeNasc.getYear());

               // Log.i("Data",dpCadastrarUsuarioDataDeNasc.getDayOfMonth()+"");
               // Log.i("Data",dpCadastrarUsuarioDataDeNasc.getMonth()+"");
               // Log.i("Data",dpCadastrarUsuarioDataDeNasc.getYear()+"");

                usuario.setDataNasc(date);
                usuario.setEmail(edtCadastrarUsuarioEmail.getText().toString());
                usuario.setSenha(edtCadastrarUsuarioSenha.getText().toString());
                usuario.setCep(edtCadastrarUsuarioCep.getText().toString());
                usuario.setEndereco(edtCadastrarUsuarioEndereco.getText().toString());
                usuario.setNumeroEndereco(Integer.parseInt(edtCadastrarUsuarioNumeroEndereco.getText().toString()));
               // usuario.setCidade(edtCadastrarUsuarioCidade.getText().toString());
                usuario.setComplemento(edtCadastrarUsuarioComplemento.getText().toString());





                new CadastroUsuarioWS(CadastroUsuarioActivity.this,usuario).cadastrar();
            }
        });

        new BuscarEstadoWS(CadastroUsuarioActivity.this).buscarEstados();

       // Gson
    }

    public void carregarEstados(List<Estado> estados)
    {
        ArrayAdapter<Estado> adapter = new ArrayAdapter<Estado>(this,android.R.layout.simple_spinner_item,estados);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spCadastrarUsuarioEstado.setAdapter(adapter);

        spCadastrarUsuarioEstado.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Estado estado = (Estado) parent.getItemAtPosition(position);
                //carregar as cidades
                new BuscarCidadesWS(CadastroUsuarioActivity.this, estado.getIdEstado()).buscarCidade();//carregar as cidades
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void carregarCidades(List<Cidade> cidades)
    {
        String cidadesArray[] = new String[cidades.size()];
        for(int i = 0 ; i < cidades.size(); i++) {
            cidadesArray[i] = cidades.get(i).getNomeCidade();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,cidadesArray);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spCadastrarUsuarioCidade.setAdapter(adapter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
