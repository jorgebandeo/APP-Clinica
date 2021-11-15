package com.jorgebandeo.gerenciamentodeconsultasmedicas.Medico;

import androidx.dynamicanimation.animation.SpringAnimation;

public class Medico {
    private int _id;
    private String nome;
    private String crm;
    private int celular;
    private int fixo;

    public Medico (){

    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCrm(String crm) {
        this.crm = crm;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public void setFixo(int fixo) {
        this.fixo = fixo;
    }

    public int get_id() {
        return _id;
    }

    public String getNome() {
        return nome;
    }

    public String getCrm() {
        return crm;
    }

    public int getCelular() {
        return celular;
    }

    public int getFixo() {
        return fixo;
    }
}
