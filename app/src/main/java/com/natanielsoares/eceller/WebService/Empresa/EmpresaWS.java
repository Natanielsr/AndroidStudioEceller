package com.natanielsoares.eceller.WebService.Empresa;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kosalgeek.asynctask.AsyncResponse;
import com.kosalgeek.asynctask.PostResponseAsyncTask;
import com.natanielsoares.eceller.Objetos.Empresa;
import com.natanielsoares.eceller.R;
import com.natanielsoares.eceller.Tab1;
import com.natanielsoares.eceller.Tab2;
import com.natanielsoares.eceller.WebService.Conexao.ConexaoWS;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nataniel on 01/09/2016.
 */
public class EmpresaWS implements AsyncResponse
{
    Context context;

    public EmpresaWS(Context context)
    {
        this.context = context;
    }

    //OBTER EMPRESAS MAPA
    public void obterEmpresas()
    {
        try
        {

            PostResponseAsyncTask task = new PostResponseAsyncTask(context, this);

            task.execute(ConexaoWS.url+"/obterListaEmpresa");


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

            List<Empresa> listaEmpresa = new ArrayList<Empresa>();

            for(int i = 0; i < json.length(); i++){

                JSONObject obj1 = new JSONObject(json.get(i).toString());
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

                //adicionando ponto no mapa
                MarkerOptions markerOptions= new MarkerOptions()
                        .position(new LatLng(empresa.getLatitude(), empresa.getLongitude()))
                        .title(empresa.getNome())
                        .icon(BitmapDescriptorFactory.fromResource(selecIcon(empresa.getTipoEstab())));


                Tab1.googleMap.addMarker(markerOptions);

                listaEmpresa.add(empresa);

            }
            Tab2.listaEmpresa = listaEmpresa;


        } catch (JSONException e) {
            e.printStackTrace();
            Log.i("Eceller", e.getMessage());
        }
    }
    int selecIcon(int id)
    {
        switch (id){

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
