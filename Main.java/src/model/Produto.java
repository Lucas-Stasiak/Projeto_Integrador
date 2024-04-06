
package model;

import java.text.DecimalFormat;

public class Produto {
    private String nome;
    private String descricao;
    private float preco;
    private String unidade;
    private int quantidade;

    public Produto(String nome, String descricao, float preco, String unidade, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.unidade = unidade;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        DecimalFormat df = new DecimalFormat("#.00");
        df.format(preco);
        this.preco = preco;
    }

    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    
    
    
    
    
}
