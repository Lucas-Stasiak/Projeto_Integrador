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
}
