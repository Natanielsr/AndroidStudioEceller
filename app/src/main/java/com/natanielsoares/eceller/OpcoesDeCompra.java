package com.natanielsoares.eceller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.natanielsoares.eceller.Objetos.Produto;
import com.natanielsoares.eceller.WebService.Produto.ProdutoPorEmpresaWS;

import java.util.List;

public class OpcoesDeCompra extends AppCompatActivity {

    List<Produto> listaProdutoAc;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opcoes_de_compra);


        lv = (ListView)findViewById(R.id.opcoesDeCompraLstOpcoes);


        //procurar produtos
        ProdutoPorEmpresaWS produtoPorEmpresaWS = new ProdutoPorEmpresaWS(OpcoesDeCompra.this);
        int empresaId = getIntent().getIntExtra("empresaId",0);
        produtoPorEmpresaWS.obterProdutos(empresaId);

    }

    public void carregarLista(List<Produto> listaProduto){

        listaProdutoAc = listaProduto;
        String[] atividades = new String[listaProduto.size()];


        for(int i = 0; i < atividades.length; i++)
        {
            atividades[i] = listaProduto.get(i).getNome()+" - R$"+ listaProduto.get(i).getPreco();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, atividades);


        lv.setAdapter(adapter);
        lv.setOnItemClickListener(clicaOpcao());



    }

    public AdapterView.OnItemClickListener clicaOpcao(){
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Eceller","botao foi clicado");
                Intent intent = new Intent(OpcoesDeCompra.this, DadosOpcao.class);
                intent.putExtra("produto",listaProdutoAc.get(position));
                startActivity(intent);

            }
        });
    }
}
