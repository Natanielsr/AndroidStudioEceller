package com.natanielsoares.eceller.Objetos;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

/**
 * Created by Nataniel on 01/10/2016.
 */

public class Usuario
{
    private String nomeCompleto;
    private int dddTelefone ;
    private String telefone ;
    private int dddCelular ;
    private String celular;
    private String cpf ;
    private Data dataNasc ;
    private String email;
    private String senha;
    private String cep;
    private String endereco;
    private int numeroEndereco;
    private String bairro;
    private String cidade;
    private String estado;
    private String complemento;
    private String idGoogle;

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public int getDddTelefone() {
        return dddTelefone;
    }

    public void setDddTelefone(int dddTelefone) {
        this.dddTelefone = dddTelefone;
    }

    public void setDddTelefone(String dddTelefone)
    {
        try {
            this.dddTelefone = Integer.parseInt(dddTelefone);
        }catch (Exception e)
        {
            this.dddTelefone = 0;
        }
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getDddCelular() {
        return dddCelular;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public void setDddCelular(int dddCelular) {
        this.dddCelular = dddCelular;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Data getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(Data dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
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

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getIdGoogle() {
        return idGoogle;
    }

    public void setIdGoogle(String idGoogle) {
        this.idGoogle = idGoogle;
    }

    public JSONObject toJson()
    {
        JSONObject jsonObject = new JSONObject();
        try {
            if(nomeCompleto != null)
                jsonObject.accumulate("nomeCompleto", nomeCompleto);

            if(dddTelefone != 0)
                jsonObject.accumulate("dddTelefone", dddTelefone);

            if(telefone != null)
                jsonObject.accumulate("telefone", telefone);

            if(dddCelular != 0)
                jsonObject.accumulate("dddCelular", dddCelular);

            if(celular != null)
                jsonObject.accumulate("celular", celular);

            if(cpf != null)
                jsonObject.accumulate("cpf", cpf);

            if(dataNasc != null)
                jsonObject.accumulate("dataNasc", dataNasc.dataJson());

            if(email != null)
                jsonObject.accumulate("email", email);

            if(senha != null)
                jsonObject.accumulate("senha", senha);

            if(cep != null)
                jsonObject.accumulate("cep", cep);

            if(endereco != null)
                jsonObject.accumulate("endereco", endereco);

            if(numeroEndereco != 0)
                jsonObject.accumulate("numeroEndereco", numeroEndereco);

            if(cidade != null)
                jsonObject.accumulate("cidade", cidade);

            if(estado != null)
                jsonObject.accumulate("estado", estado);

            if(complemento != null)
                jsonObject.accumulate("complemento", complemento);

            if(idGoogle != null)
                jsonObject.accumulate("idGoogle", idGoogle);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
}
