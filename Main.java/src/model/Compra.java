
package model;

public class Compra {
    private int id_compra;
    private float preco;
    private String unidade;
    private int fk_id_historico;
    private int fk_id_produto;

    public Compra(int id_compra, float preco, String unidade, int fk_id_historico, int fk_id_produto) {
        this.id_compra = id_compra;
        this.preco = preco;
        this.unidade = unidade;
        this.fk_id_historico = fk_id_historico;
        this.fk_id_produto = fk_id_produto;
    }

    public int getId_compra() {
        return id_compra;
    }

    public void setId_compra(int id_compra) {
        this.id_compra = id_compra;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getFk_id_historico() {
        return fk_id_historico;
    }

    public void setFk_id_historico(int fk_id_historico) {
        this.fk_id_historico = fk_id_historico;
    }

    public int getFk_id_produto() {
        return fk_id_produto;
    }

    public void setFk_id_produto(int fk_id_produto) {
        this.fk_id_produto = fk_id_produto;
    }

    

    
}
