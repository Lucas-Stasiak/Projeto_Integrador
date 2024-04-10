
package model;

public class Compra {
    private int id_compra;
    private int fk_id_historico;
    private int fk_id_produto;

    public Compra(int id_compra, int fk_id_historico, int fk_id_produto) {
        this.id_compra = id_compra;
        this.fk_id_historico = fk_id_historico;
        this.fk_id_produto = fk_id_produto;
    }

    
}
