package model;

import java.util.Date;
import java.sql.Timestamp;

public class Historico {

    private int id_historico;
    private Date data;
    private Timestamp tempo;
    private float precoTotal;
    private int fk_id_cliente;
    private int fk_id_usuario;

    public Historico(float precoTotal, int fk_id_usuario, int fk_id_cliente) {
        this.data = new Date(); // Cria a data atual
        this.tempo = new Timestamp(System.currentTimeMillis()); // Cria o timestamp atual
        this.precoTotal = precoTotal;
        this.fk_id_cliente = fk_id_cliente;
        this.fk_id_usuario = fk_id_usuario;
    }

    public int getId_historico() {
        return id_historico;
    }

    public void setId_historico(int id_historico) {
        this.id_historico = id_historico;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Timestamp getTempo() {
        return tempo;
    }

    public void setTempo(Timestamp tempo) {
        this.tempo = tempo;
    }

    public float getPrecoTotal() {
        return precoTotal;
    }

    public void setPrecoTotal(float precoTotal) {
        this.precoTotal = precoTotal;
    }

    public int getFk_id_cliente() {
        return fk_id_cliente;
    }

    public void setFk_id_cliente(int fk_id_cliente) {
        this.fk_id_cliente = fk_id_cliente;
    }

    public int getFk_id_usuario() {
        return fk_id_usuario;
    }

    public void setFk_id_usuario(int fk_id_usuario) {
        this.fk_id_usuario = fk_id_usuario;
    }
    
    

}
