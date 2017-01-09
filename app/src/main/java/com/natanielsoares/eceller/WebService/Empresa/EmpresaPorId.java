package com.natanielsoares.eceller.WebService.Empresa;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.natanielsoares.eceller.DadosOpcao;
import com.natanielsoares.eceller.Objetos.Empresa;
import com.natanielsoares.eceller.R;
import com.natanielsoares.eceller.Tab1;
import com.natanielsoares.eceller.Tab2;
import com.natanielsoares.eceller.WebService.Conexao.ConexaoWS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nataniel on 29/09/2016.
 */
public class EmpresaPorId implements AsyncResponse
{
    Context context;
    DadosOpcao dadosOpcao;

    public EmpresaPorId(DadosOpcao dadosOpcao)
    {
        this.context = dadosOpcao;
        this.dadosOpcao = dadosOpcao;
    }

    //OBTER EMPRESAS MAPA
    public void obterEmpresas(int id)
    {
        try
        {
            HashMap postData = new HashMap();

            postData.put("id", String.valueOf(id));
            PostResponseAsyncTask task = new PostResponseAsyncTask(context,postData, this);

            task.execute(ConexaoWS.url+"/obterEmpresaPorID");


        }catch (Exception error)
        {
            Log.i("Eceller", error.getMessage());
        }
    }

    @Override
    public void processFinish(String result) {

        JSONObject obj1 = null;
        try
        {
            obj1 = new JSONObject(result);
            Empresa empresa = new Empresa();

            empresa.setId(Integer.parseInt(obj1.getString("id").toString()));
            empresa.setNome(obj1.getString("nome").toString());
            empresa.setEndereco(obj1.getString("endereco").toString());
            empresa.setNumeroEndereco(Integer.parseInt(obj1.getString("numeroEndereco").toString()));
            empresa.setDddTelefone(Integer.parseInt(obj1.getString("dddTelefone").toString()));
            empresa.setTelefone(obj1.getString("telefone").toString());
            empresa.setLatitude(Double.parseDouble(obj1.getString("latitude").toString()));
            empresa.setLongitude(Double.parseDouble(obj1.getString("longitude").toString()));
            empresa.setTipoEstab(obj1.getInt("tipoEstab"));

            dadosOpcao.infoEmpresa(empresa);



        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Eceller", e.getMessage());
        }
    }
    int selecIcon(int id) {
        switch (id) {

            case 1: //bar
                return R.drawable.icm_bar;
            case 2: //mercado
                return R.drawable.icm_mercado;
            case 3://pizzaria
                return R.drawable.icm_pizza;
        }
        return 0;
    }
}
