
package model;

import java.text.DecimalFormat;

public class Produto {
    private int id_produto;
    private String nome;
    private String descricao;
    private float preco;
    private String unidade;
    private int quantidade;
    private String categoria;

    public Produto(String nome, String descricao, float preco, String unidade, int quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.unidade = unidade;
        this.quantidade = quantidade;
    }

    public Produto(String nome, String categoria, String descricao, int quantidade, String unidade, float preco) {
        this.nome = nome;
        this.categoria = categoria;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.unidade = unidade;
        this.preco = preco;
    }

    public int getId_produto() {
        return id_produto;
    }

    public void setId_produto(int id_produto) {
        this.id_produto = id_produto;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
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
