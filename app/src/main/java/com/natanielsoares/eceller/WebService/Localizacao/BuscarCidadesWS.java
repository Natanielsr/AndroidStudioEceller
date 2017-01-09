package com.natanielsoares.eceller.WebService.Localizacao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.natanielsoares.eceller.CadastroUsuarioActivity;
import com.natanielsoares.eceller.Objetos.Cidade;
import com.natanielsoares.eceller.Objetos.Estado;
import com.natanielsoares.eceller.WebService.Conexao.ConexaoWS;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nataniel on 03/10/2016.
 */

public class BuscarCidadesWS
{
    private ProgressDialog mProgressDialog;
    Context context;
    ConexaoWS conexaoWS;
    CadastroUsuarioActivity cadastroUsuarioActivity;
    private int idEstado;

    public BuscarCidadesWS(CadastroUsuarioActivity cadastroUsuarioActivity, int idEstado){
        this.context = cadastroUsuarioActivity;
        this.cadastroUsuarioActivity =cadastroUsuarioActivity;
        conexaoWS = new ConexaoWS(context);
        this.idEstado = idEstado;
    }

    static String POST(String url, int idEstado){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            //parametros post
            ArrayList<NameValuePair> postParameters;
            postParameters = new ArrayList<NameValuePair>();
            postParameters.add(new BasicNameValuePair("idEstado", String.valueOf(idEstado)));

            httpPost.setEntity(new UrlEncodedFormEntity(postParameters));


            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        // 11. return result
        return result;
    }

    public void buscarCidade()
    {
        conexaoWS.showProgressDialog();
        new BuscarCidadesWS.HttpAsyncTask().execute(ConexaoWS.url+"/retornaCidades");
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls)
        {
            return POST(urls[0], idEstado);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            JSONArray json = null;
            List<Cidade> listaCidade = new ArrayList<>();
            try {
                json = new JSONArray(result);

                for(int i = 0 ; i < json.length(); i++)
                {
                    JSONObject jsonObject = json.getJSONObject(i);

                    Cidade cidade = new Cidade();
                    cidade.setIdCidade(Integer.parseInt(jsonObject.getString("idCidade").toString()));
                    cidade.setNomeCidade(jsonObject.getString("nomeCidade").toString());
                    cidade.setIdEstado(Integer.parseInt(jsonObject.getString("idEstado").toString()));

                    listaCidade.add(cidade);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("servidor",json.toString());
           // Toast.makeText(context, "Data Sent!", Toast.LENGTH_LONG).show();
            conexaoWS.hideProgressDialog();
            cadastroUsuarioActivity.carregarCidades(listaCidade);
        }
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
}

