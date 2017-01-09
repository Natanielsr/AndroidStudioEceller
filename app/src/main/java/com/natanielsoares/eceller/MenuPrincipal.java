package com.natanielsoares.eceller;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MenuPrincipal extends TabActivity {

    EditText MenuPrincialTxtPesquisa;
    Button MenuPrincialBtnPesquisa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);

        TabHost tabHost = (TabHost) findViewById(android.R.id.tabhost);

        TabHost.TabSpec mapa = tabHost.newTabSpec("mapa");
        TabHost.TabSpec lojas = tabHost.newTabSpec("ABA DOIS");
        TabHost.TabSpec home = tabHost.newTabSpec("ABA TRES");

        home.setIndicator("Home");
        home.setContent(new Intent(this, Tab3.class));

        mapa.setIndicator("Mapa");
        //tab1.setIndicator("",getResources().getDrawable(R.mipmap.ic_launcher));
        mapa.setContent(new Intent(this, Tab1.class));

        lojas.setIndicator("Pontos de Venda");
        lojas.setContent(new Intent(this, Tab2.class));


        tabHost.addTab(mapa);
        tabHost.addTab(lojas);
        tabHost.addTab(home);

        MenuPrincialTxtPesquisa = (EditText) findViewById(R.id.MenuPrincialTxtPesquisa);
        MenuPrincialBtnPesquisa = (Button) findViewById(R.id.MenuPrincialBtnPesquisa);

        MenuPrincialBtnPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MenuPrincipal.this, ResultadoProdutos.class);
                intent.putExtra("textoPesquisa", MenuPrincialTxtPesquisa.getText().toString());
                startActivity(intent);

            }
        });


    }
}
