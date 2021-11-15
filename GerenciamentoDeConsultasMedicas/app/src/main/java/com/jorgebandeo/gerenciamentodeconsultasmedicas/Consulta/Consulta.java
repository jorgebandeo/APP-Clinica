package com.jorgebandeo.gerenciamentodeconsultasmedicas.Consulta;


/*
O dia foi separa do tempo de início e de fim, isto para podermos trabalhar melhor com os dados e os picker
 */
public class Consulta {

    private int _id;
    private int medico_id;
    private int paciente_id;
    private String dia;
    private String data_hora_inicio;
    private String data_hora_fim;
    private String observacao ;

    public Consulta (){}

    public void setDia (String dia){
        this.dia=dia;
    }
    public String getDia(){
        return this.dia;
    }

    public void set_id (int _id){
        this._id=_id;
    }
    public int get_id (){
        return this._id;
    }

    public void setMedico_id (int _id){
        this.medico_id=_id;
    }
    public int getMedico_id (){
        return this.medico_id;
    }

    public void setPaciente_id(int _id){
        this.paciente_id=_id;
    }
    public int getPaciente_id (){
        return this.paciente_id;
    }

    public void setdata_hora_inicio(String data_hora_inicio){
        this.data_hora_inicio=data_hora_inicio;
    }
    public String getdata_hora_inicio (){
        return this.data_hora_inicio;
    }

    public void setdata_hora_fim(String data_hora_fim){
        this.data_hora_fim=data_hora_fim;
    }
    public String getdata_hora_fim (){
        return this.data_hora_fim;
    }

    public void setObservacao(String obs){
        this.observacao=obs;
    }
    public String getObservacao (){
        return this.observacao;
    }

}
