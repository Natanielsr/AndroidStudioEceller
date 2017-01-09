package com.natanielsoares.eceller.WebService.Usuario;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.natanielsoares.eceller.MenuPrincipal;
import com.natanielsoares.eceller.Objetos.Produto;
import com.natanielsoares.eceller.Objetos.Usuario;
import com.natanielsoares.eceller.ResultadoProdutos;
import com.natanielsoares.eceller.WebService.Conexao.ConexaoWS;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
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
 * Created by Nataniel on 11/09/2016.
 */
public class CadastroUsuarioSimplesWS
{
    Context context;
    Usuario usuario;
    ConexaoWS conexaoWS;

    public CadastroUsuarioSimplesWS(Context context, Usuario usuario){
        this.context = context;
        this.usuario = usuario;
        conexaoWS = new ConexaoWS(context);
    }

    static String POST(String url, Usuario usuario){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            String json = "";

            // 3. build jsonObject
            JSONObject jsonObject = usuario.toJson();

            // 4. convert JSONObject to JSON to String
            json = "{\"usuario\":"+jsonObject.toString()+"}";
            Log.i("Json", json);

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json; charset=UTF-8");

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

    public void cadastrar()
    {
        conexaoWS.showProgressDialog();
        new HttpAsyncTask().execute(ConexaoWS.url+"/cadastrarUsuarioSimples");
    }
    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls)
        {
            return POST(urls[0],usuario);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result)
        {
            conexaoWS.hideProgressDialog();
            Log.i("servidor",result);

            JSONObject json = null;
            try {
                json = new JSONObject(result);
                String r = json.getString("d").toString();

                if(r.equals("OK"))
                {
                    Intent intent = new Intent(context, MenuPrincipal.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);
                }
                else
                {
                    Toast.makeText(context, r, Toast.LENGTH_LONG).show();
                }

            } catch (JSONException e)
            {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
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
