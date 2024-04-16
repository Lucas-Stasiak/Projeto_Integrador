
package model;


public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String observacao;

    public Cliente(String nome, String cpf, String telefone, String observacao) {
        this.nome = nome;
        this.cpf = cpf;
        this.telefone = telefone;
        this.observacao = observacao;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    } 

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
    
    
}
