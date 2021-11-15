package com.jorgebandeo.gerenciamentodeconsultasmedicas.Paciente;

public class Paciente {
    private  int id;
    private String nome;
    private String grp_sanguineo;
    private int celular;
    private int fixo;

    public Paciente (){}
    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setGrp_sanguineo(String grp_sanguineo) {
        this.grp_sanguineo = grp_sanguineo;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public void setFixo(int fixo) {
        this.fixo = fixo;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getGrp_sanguineo() {
        return grp_sanguineo;
    }

    public int getCelular() {
        return celular;
    }

    public int getFixo() {
        return fixo;
    }
}
