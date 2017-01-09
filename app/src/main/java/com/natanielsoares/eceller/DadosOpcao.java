package com.natanielsoares.eceller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.natanielsoares.eceller.Objetos.Empresa;
import com.natanielsoares.eceller.Objetos.Produto;
import com.natanielsoares.eceller.WebService.Empresa.EmpresaPorId;

import java.util.List;

public class DadosOpcao extends AppCompatActivity {

    Produto produto;
    TextView dadosOpcaoTxtNome;
    TextView dadosOpcaoTxtPreco;
    TextView dadosOpcaoTxtDescricao;

    TextView dadosOpcaoTxtNomeEmpresa;
    TextView dadosOpcaoTxtTelefone;
    TextView dadosOpcaoTxtEndereco;

    MapView mapView;
    GoogleMap googleMap;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dados_opcao);

        produto = (Produto) getIntent().getSerializableExtra("produto");
        dadosOpcaoTxtNome = (TextView) findViewById(R.id.dadosOpcaoTxtNome);
        dadosOpcaoTxtPreco = (TextView) findViewById(R.id.dadosOpcaoTxtPreco);
        dadosOpcaoTxtDescricao = (TextView) findViewById(R.id.dadosOpcaoTxtDescricao);

        dadosOpcaoTxtNomeEmpresa = (TextView) findViewById(R.id.dadosOpcaotxtnomeEmpresa);
        dadosOpcaoTxtTelefone = (TextView) findViewById(R.id.dadosOpcaotxtTelefone);
        dadosOpcaoTxtEndereco = (TextView) findViewById(R.id.dadosOpcaotxtEnderco);

       // Log.i("produto",produto.getEmpresa().getNome());

        dadosOpcaoTxtNome.setText(produto.getNome().toString());
        dadosOpcaoTxtPreco.setText(produto.getPreco().toString());
        dadosOpcaoTxtDescricao.setText(produto.getDescricao().toString());

        carregarMapa(savedInstanceState);

        new EmpresaPorId(DadosOpcao.this).obterEmpresas(produto.getEmpresaId());
    }

    public void infoEmpresa(Empresa empresa){

        dadosOpcaoTxtNomeEmpresa.setText(empresa.getNome().toString());
        dadosOpcaoTxtTelefone.setText("("+empresa.getDddTelefone()+") "+empresa.getTelefone());
        dadosOpcaoTxtEndereco.setText(empresa.getEndereco().toString()+", "+empresa.getNumeroEndereco());

        posicaoMapa(empresa);
    }

    void carregarMapa(Bundle savedInstanceState)
    {

        //recebe o mapview da view
        mapView = (MapView) findViewById(R.id.mapaOpcao);
        mapView.onCreate(savedInstanceState);

        //seta o googlemap com o mapview
        googleMap = mapView.getMap();
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setMyLocationEnabled(true);

    }
    void posicaoMapa(Empresa empresa)
    {
        //set latitude longitude
        LatLng latLng = new LatLng(empresa.getLatitude(), empresa.getLongitude());

        //muda a camera do mapa
        CameraUpdate center= CameraUpdateFactory.newLatLng(latLng);
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(16);
        googleMap.moveCamera(zoom);
        googleMap.animateCamera(center);

        //adiciona o marca no mapa
        googleMap.addMarker(new MarkerOptions().position(latLng).title(empresa.getNome()));

    }
}
