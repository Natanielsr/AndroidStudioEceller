package com.natanielsoares.eceller.Objetos;

import java.io.Serializable;

/**
 * Created by Nataniel on 01/09/2016.
 */
public class Empresa implements Serializable {

    private int id;
    private String nome;
    private String endereco;
    private int numeroEndereco;
    private int dddTelefone;
    private String telefone;
    private double latitude;
    private double longitude;
    private int tipoEstab;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumeroEndereco() {
        return numeroEndereco;
    }

    public void setNumeroEndereco(int numeroEndereco) {
        this.numeroEndereco = numeroEndereco;
    }

    public int getDddTelefone() {
        return dddTelefone;
    }

    public void setDddTelefone(int dddTelefone) {
        this.dddTelefone = dddTelefone;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getTipoEstab() {
        return tipoEstab;
    }

    public void setTipoEstab(int tipoEstab) {
        this.tipoEstab = tipoEstab;
    }
}
