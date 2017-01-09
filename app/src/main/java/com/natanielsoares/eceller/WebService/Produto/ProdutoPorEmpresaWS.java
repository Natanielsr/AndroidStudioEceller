package com.natanielsoares.eceller.WebService.Produto;

import android.content.Context;
import android.util.Log;

import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.natanielsoares.eceller.Objetos.Produto;
import com.natanielsoares.eceller.OpcoesDeCompra;
import com.natanielsoares.eceller.WebService.Conexao.ConexaoWS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nataniel on 07/09/2016.
 */
public class ProdutoPorEmpresaWS implements AsyncResponse
{
    Context context;
    OpcoesDeCompra o;


    public ProdutoPorEmpresaWS(OpcoesDeCompra o)
    {
        this.context = o;
        this.o = o;
    }

    //OBTER PRODUTOS
    public void obterProdutos(int empresaId)
    {
        try
        {

            HashMap postData = new HashMap();

            postData.put("empresaId", String.valueOf(empresaId));

            PostResponseAsyncTask task = new PostResponseAsyncTask(context,postData, this);

            task.execute(ConexaoWS.url+"/obterProdutoPorEmpresa");


        }catch (Exception error)
        {
            Log.i("Eceller", error.getMessage());
        }
    }

    @Override
    public void processFinish(String result) {

        JSONArray json = null;
        try {
            json = new JSONArray(result);

            List<Produto> listaProduto = new ArrayList<Produto>();

            for(int i = 0; i < json.length(); i++){

                JSONObject obj1 = new JSONObject(json.get(i).toString());
                Produto produto = new Produto();

                produto.setIdProduto(Integer.parseInt(obj1.getString("idProduto").toString()));
                produto.setNome(obj1.getString("nome").toString());
                produto.setDescricao(obj1.getString("descricao").toString());
                produto.setPreco(Double.parseDouble(obj1.getString("preco").toString()));
                produto.setEmpresaId(Integer.parseInt(obj1.getString("empresaId").toString()));

                listaProduto.add(produto);

            }
            //OpcoesDeCompra.listaProduto = listaProduto;
            o.carregarLista(listaProduto);

        } catch (JSONException e) {
            Log.i("Eceller",e.getMessage());
            e.printStackTrace();
        }
    }
}
