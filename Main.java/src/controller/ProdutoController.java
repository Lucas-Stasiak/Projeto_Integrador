package controller;

import dao.ProdutoDAO;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import model.Produto;
import view.VendaPane;

public class ProdutoController {

    private VendaPane view;

    public ProdutoController(VendaPane view) {
        this.view = view;
    }

    public void readListaProduto() throws SQLException {
        DefaultListModel<String> modelo = new DefaultListModel<>();
        view.getListaProdutos().setModel(modelo); // Define o modelo na lista

        // Limpa a lista antes de adicionar novos produtos
        modelo.clear();

        // Adiciona os produtos à lista
        for (Produto produto : new ProdutoDAO().readProduto()) {
            modelo.addElement(produto.getNome());
        }
    }

    public Produto readProdutosSelecionados(String nomeProduto) throws SQLException {
        String nome, descricao, unidade;
        float preco;
        int quantidade;

        for (Produto produto : new ProdutoDAO().readProduto()) {

            if (produto.getNome().equals(nomeProduto)) {
                // Se o produto for encontrado, retorna os outros valores
                nome = produto.getNome();
                descricao = produto.getDescricao();
                preco = produto.getPreco();
                unidade = produto.getUnidade();
                quantidade = produto.getQuantidade();
                // salva as informações em um modelo do produto
                Produto pr = new Produto(nome, descricao, preco, unidade, quantidade);

                return pr;
            }
        }

        // Se o produto não for encontrado
        return null;

    }

}
