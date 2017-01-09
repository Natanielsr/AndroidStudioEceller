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
import com.natanielsoares.eceller.WebService.Produto.ProdutoPesquisaWS;
import com.natanielsoares.eceller.WebService.Produto.ProdutoPorEmpresaWS;

import java.util.List;

public class ResultadoProdutos extends AppCompatActivity {

    ListView lv;
    List<Produto> listaProdutoAc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado_produtos);
        lv = (ListView)findViewById(R.id.ResultadoProdutosLstOpcoes);

        //procurar produtos
        ProdutoPesquisaWS produtoPesquisaWS = new ProdutoPesquisaWS(ResultadoProdutos.this);
        String textoPesquisa = getIntent().getStringExtra("textoPesquisa");
        produtoPesquisaWS.obterProdutos(textoPesquisa);

    }

    public void carregarLista(List<Produto> listaProduto)
    {

        listaProdutoAc = listaProduto;
        String[] atividades = new String[listaProduto.size()];


        for(int i = 0; i < atividades.length; i++)
        {
            atividades[i] = listaProduto.get(i).getNome()+" - R$ "+ listaProduto.get(i).getPreco()+"\n"+listaProduto.get(i).getNomeEmpresa();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, atividades);


        lv.setAdapter(adapter);
        lv.setOnItemClickListener(clicaOpcao());

    }
    public AdapterView.OnItemClickListener clicaOpcao(){
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("Eceller", "botao foi clicado");
                Intent intent = new Intent(ResultadoProdutos.this, DadosOpcao.class);
                intent.putExtra("produto",listaProdutoAc.get(position));
                startActivity(intent);

            }
        });
    }
}
