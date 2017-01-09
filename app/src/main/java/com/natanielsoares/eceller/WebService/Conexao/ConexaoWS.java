package com.natanielsoares.eceller.WebService.Conexao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Nataniel on 07/09/2016.
 */
public class ConexaoWS
{
    public static String url = "http://eceller.tecnologia.ws/webservice.asmx";

    private ProgressDialog mProgressDialog;
    Context context;

    public ConexaoWS(Context context)
    {
        this.context = context;
    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage("Carregando...");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    //TESTA SE TEM INTERNET
    public boolean isConnected(){
        ConnectivityManager connMgr = (ConnectivityManager)  context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    public void writeLogin(String data){
        try {
            FileOutputStream fileOutputStream = context.openFileOutput("login", Context.MODE_PRIVATE);
            fileOutputStream.write(data.getBytes());
            fileOutputStream.close();
            Log.i("login","O login foi salvo");
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String readLogin()
    {
        try
        {
            String message;
            FileInputStream fileOutputStream = context.openFileInput("login");
            InputStreamReader inputStreamReader = new InputStreamReader(fileOutputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuffer stringBuffer = new StringBuffer();
            while((message = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(message+"\n");
            }
            return stringBuffer.toString();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public void deleteFile(){
        context.deleteFile("login");
    }

}
