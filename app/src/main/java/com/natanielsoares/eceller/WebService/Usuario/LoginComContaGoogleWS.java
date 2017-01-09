package com.natanielsoares.eceller.WebService.Usuario;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.natanielsoares.eceller.CadastroUsuarioSimples;
import com.natanielsoares.eceller.MenuPrincipal;
import com.natanielsoares.eceller.Objetos.Usuario;
import com.natanielsoares.eceller.WebService.Conexao.ConexaoWS;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Nataniel on 22/10/2016.
 */

public class LoginComContaGoogleWS
{

    Context context;
    String idGoogle;
    String nome;
    String email;
    ConexaoWS conexaoWS;

    public LoginComContaGoogleWS(Context context, String idGoogle, String email, String nome){
        this.context = context;
        this.idGoogle = idGoogle;
        this.nome = nome;
        this.email = email;
        conexaoWS = new ConexaoWS(context);
    }

    static String POST(String url, String idGoogleP){
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);

            // 4. convert JSONObject to JSON to String
            String json = "{\"idGoogle\":"+idGoogleP+"}";
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

    public void login()
    {
        conexaoWS.showProgressDialog();
        new HttpAsyncTask().execute(ConexaoWS.url+"/loginComContaGoogle");
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls)
        {
            return POST(urls[0],idGoogle);
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

                if(r.equals("null"))
                {
                    //se não existir a conta cadastrada , abre o cadastro
                    Intent intent = new Intent(context, CadastroUsuarioSimples.class);
                    intent.putExtra("nomeUsuarioGoogle",nome);
                    intent.putExtra("emailUsuarioGoogle",email);
                    intent.putExtra("idUsuarioGoogle",idGoogle);
                    context.startActivity(intent);


                    /*Intent intent = new Intent(context, MenuPrincipal.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    context.startActivity(intent);*/
                }
                else
                {
                    JSONObject usuarioJson = json.getJSONObject("d");

                    int idUsuario = usuarioJson.getInt("idUsuario");
                    if(idUsuario > 0) {

                        conexaoWS.writeLogin(""+idUsuario);
                        Intent intent = new Intent(context, MenuPrincipal.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                    else
                    {
                        Toast.makeText(context, "Ocorreu um erro tente novamente mais tarde", Toast.LENGTH_LONG).show();
                    }


                    //Toast.makeText(context, r, Toast.LENGTH_LONG).show();
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
