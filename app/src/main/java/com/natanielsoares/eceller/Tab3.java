package com.natanielsoares.eceller;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.natanielsoares.eceller.WebService.Conexao.ConexaoWS;

public class Tab3 extends Activity {

    Button btnTab3Logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab3);

        btnTab3Logout = (Button) findViewById(R.id.btnTab3Logout);

        btnTab3Logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ConexaoWS conexaoWS = new ConexaoWS(Tab3.this);
                conexaoWS.deleteFile();

                Intent intent = new Intent(Tab3.this, SignInActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });

    }
}
