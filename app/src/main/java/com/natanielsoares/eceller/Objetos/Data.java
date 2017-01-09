package com.natanielsoares.eceller.Objetos;

/**
 * Created by Nataniel on 01/10/2016.
 */

public class Data
{
    private int dia;
    private int mes;
    private int ano;

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String dataJson(){
        return ano+"-"+mes+"-"+dia;
    }

    public String dataNormal(){
        return dia+"/"+mes+"/"+ano;
    }
}
