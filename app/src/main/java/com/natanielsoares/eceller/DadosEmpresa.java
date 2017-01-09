package com.natanielsoares.eceller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.natanielsoares.eceller.Objetos.Empresa;

public class DadosEmpresa extends AppCompatActivity
{

    GoogleMap googleMap;
    MapView mapView;
    Empresa empresa;

    TextView dadosEmpresaNome;
    TextView dadosEmpresaEndereco;
    TextView dadosEmpresaTelefone;
    Button dadosEmpresaBtnOpcoesDeCompra;

    @Override
    protected void onResume(){
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    protected void onPause(){
        super.onPause();
        mapView.onPause();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_empresa);

        //obtem empresa da activity anteterior por parametro
        empresa = (Empresa) getIntent().getSerializableExtra("empresa");

        //recebe os textview nos objetos
        dadosEmpresaNome = (TextView) findViewById(R.id.DadosEmpresaNome);
        dadosEmpresaEndereco = (TextView) findViewById(R.id.DadosEmpresaEndereco);
        dadosEmpresaTelefone = (TextView) findViewById(R.id.DadosEmpresaTelefone);
        dadosEmpresaBtnOpcoesDeCompra = (Button) findViewById(R.id.dadosEmpresaBtnOpcoesDeCompra);

        //seta os textview com os nomes
        dadosEmpresaNome.setText(empresa.getNome().toString());
        dadosEmpresaEndereco.setText(empresa.getEndereco().toString() + ", " + empresa.getNumeroEndereco());
        dadosEmpresaTelefone.setText("(" + empresa.getDddTelefone() + ") " + empresa.getTelefone().toString());

        //set latitude longitude
        double latitude = empresa.getLatitude();
        double longitude = empresa.getLongitude();

        //recebe o mapview da view
        mapView = (MapView) findViewById(R.id.mapa2);
        mapView.onCreate(savedInstanceState);

        //seta o googlemap com o mapview
        googleMap = mapView.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);

        //muda a camera do mapa
        CameraUpdate center= CameraUpdateFactory.newLatLng(new LatLng(latitude, longitude));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        googleMap.moveCamera(zoom);
        googleMap.animateCamera(center);

        //adiciona o marca no mapa
        googleMap.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).title(empresa.getNome()));


        dadosEmpresaBtnOpcoesDeCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DadosEmpresa.this, OpcoesDeCompra.class);
                intent.putExtra("empresaId",empresa.getId());
                startActivity(intent);
            }
        });


    }
}
