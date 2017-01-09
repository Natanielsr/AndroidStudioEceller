package com.natanielsoares.eceller.WebService.Localizacao;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.natanielsoares.eceller.CadastroUsuarioActivity;
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

public class BuscarEstadoWS
{
    private ProgressDialog mProgressDialog;
    Context context;
    ConexaoWS conexaoWS;
    CadastroUsuarioActivity cadastroUsuarioActivity;

    public BuscarEstadoWS(CadastroUsuarioActivity cadastroUsuarioActivity){
        this.context = cadastroUsuarioActivity;
        this.cadastroUsuarioActivity =cadastroUsuarioActivity;
        conexaoWS = new ConexaoWS(context);
    }

    static String POST(String url){
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
            postParameters.add(new BasicNameValuePair("idPais", "1"));

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

    public void buscarEstados()
    {
        conexaoWS.showProgressDialog();
        new BuscarEstadoWS.HttpAsyncTask().execute(ConexaoWS.url+"/retornaEstados");
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls)
        {
            return POST(urls[0]);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {

            JSONArray json = null;
            List<Estado> listaEstados = new ArrayList<>();
            try {
                json = new JSONArray(result);

                for(int i = 0 ; i < json.length(); i++)
                {
                    JSONObject jsonObject = json.getJSONObject(i);

                    Estado estado = new Estado();
                    estado.setIdEstado(Integer.parseInt(jsonObject.getString("idEstado").toString()));
                    estado.setNomeEstado(jsonObject.getString("nomeEstado").toString());
                    estado.setSigla(jsonObject.getString("sigla").toString());
                    estado.setIdPais(Integer.parseInt(jsonObject.getString("idPais").toString()));

                    listaEstados.add(estado);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.i("servidor",json.toString());
           // Toast.makeText(context, "Data Sent!", Toast.LENGTH_LONG).show();
            conexaoWS.hideProgressDialog();
            cadastroUsuarioActivity.carregarEstados(listaEstados);
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

