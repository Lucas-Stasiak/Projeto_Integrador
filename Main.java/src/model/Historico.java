
package model;

import java.util.Date;
import java.sql.Timestamp;

public class Historico {
    private int id_historico;
    private Date data;
    private Timestamp tempo;
    private int fk_id_cliente;
    private int fk_id_usuario;
    private float precoTotal;

    public Historico(int id_historico, Date data, Timestamp tempo, int fk_id_cliente, int fk_id_usuario, float precoTotal) {
        this.id_historico = id_historico;
        this.data = data;
        this.tempo = tempo;
        this.fk_id_cliente = fk_id_cliente;
        this.fk_id_usuario = fk_id_usuario;
        this.precoTotal = precoTotal;
    }
    
}
