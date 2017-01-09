package com.natanielsoares.eceller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.natanielsoares.eceller.Objetos.Empresa;

import java.util.List;

public class Tab2 extends Activity {

    public static List<Empresa> listaEmpresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2);

        if(listaEmpresa == null)
            return;

        String[] atividades = new String[listaEmpresa.size()];

        for(int i = 0; i < atividades.length; i++)
        {
            atividades[i] = listaEmpresa.get(i).getNome()+"\ntel:("+listaEmpresa.get(i).getDddTelefone()+")"+listaEmpresa.get(i).getTelefone();
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, atividades);

        ListView lv = (ListView)findViewById(R.id.listalojas);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(clicaLoja());


    }

    public AdapterView.OnItemClickListener clicaLoja(){
        return (new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(Tab2.this, DadosEmpresa.class);
                intent.putExtra("empresa",listaEmpresa.get(position));
                startActivity(intent);

            }
        });


    }
}
