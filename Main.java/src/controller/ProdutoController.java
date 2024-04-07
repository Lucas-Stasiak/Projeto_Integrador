package controller;

import dao.ProdutoDAO;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
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

    public String getCategoriaDoProduto(String nome) throws SQLException {
        String categoria = null;
        ProdutoDAO produtoDAO = new ProdutoDAO();
        categoria = produtoDAO.getCategoriaDoProduto(nome);
        return categoria;
    }

    public String calcularValorTotalCarrinho() {
        double total = 0.00;

        // Itera sobre as linhas da tabela de carrinho
        for (int i = 0; i < view.getTabelaCarrinho().getRowCount(); i++) {
            // Pega o valor na coluna de preço
            double preco = Double.parseDouble(view.getTabelaCarrinho().getValueAt(i, 3).toString().replace(',', '.'));
            // Pega a quantidade na coluna de quantidade
            int quantidade = Integer.parseInt(view.getTabelaCarrinho().getValueAt(i, 2).toString().replace(',', '.'));
            double subtotal = preco * quantidade;

            total += subtotal;
        }

        // Retorna o valor total calculado no campo de valor total do carrinho
        return (String.format("%.2f", total));
    }

    // Atualiza o valor total com base na quantidade do produto inserida
    public void alteraQuantidadeProduto() throws SQLException {
        Produto produtoSelecionado;
        produtoSelecionado = readProdutosSelecionados(view.getListaProdutos().getSelectedValue());
        // verifica se o estoque possui a quantidade inserida
        if (Float.parseFloat(view.getCampoQuantidade().getText().replace(',', '.')) > produtoSelecionado.getQuantidade()) {
            // avisa caso a quantidade e maior que a disponivel no estoque
            JOptionPane.showMessageDialog(null, "Quantidade de '" + produtoSelecionado.getNome() + "' disponível no estoque: " + produtoSelecionado.getQuantidade());
            view.getCampoQuantidade().setText(String.valueOf(produtoSelecionado.getQuantidade()));
            float valorTotal = Float.parseFloat(view.getCampoQuantidade().getText().replace(',', '.')) * Float.parseFloat(view.getCampoValorUnitario().getText().replace(',', '.'));
            view.getCampoValorTotal().setText(String.format("%.2f", valorTotal));
        } else {
            float valorTotal = Float.parseFloat(view.getCampoQuantidade().getText().replace(',', '.')) * Float.parseFloat(view.getCampoValorUnitario().getText().replace(',', '.'));
            view.getCampoValorTotal().setText(String.format("%.2f", valorTotal));
        }
    }

    public void pegaValoresProdutoSelecionado() throws SQLException {
        // recebe o objeto com os valores do produto selecionado
        Produto produtoSelecionado = readProdutosSelecionados(view.getListaProdutos().getSelectedValue());
        view.getCampoProduto().setText(produtoSelecionado.getNome());
        view.getCampoQuantidade().setText("1");
        view.getCampoValorUnitario().setText(String.format("%.2f", produtoSelecionado.getPreco()));
        // calcula o valor unitario do produto * a quantidade
        float valorTotal = Float.parseFloat(view.getCampoQuantidade().getText()) * produtoSelecionado.getPreco();
        view.getCampoValorTotal().setText(String.format("%.2f", valorTotal));
    }

    public void adicionaProdutoCarrinho() throws SQLException {
        Produto produtoSelecionado = readProdutosSelecionados(view.getListaProdutos().getSelectedValue());

        // Pega a categoria do produto
        String categoria = getCategoriaDoProduto(produtoSelecionado.getNome());
        // Adiciona os dados do produto à tabela de carrinho
        DefaultTableModel model = (DefaultTableModel) view.getTabelaCarrinho().getModel();
        model.addRow(new Object[]{produtoSelecionado.getNome(), categoria, view.getCampoQuantidade().getText(), produtoSelecionado.getPreco()});
        //isso serve para atualizar o valor total dos itens do carrinho
        view.getCampoValorTotalCarrinho().setText(calcularValorTotalCarrinho());
    }

    public void removerProdutoCarrinho() {
        // Verifica se uma linha está selecionada na tabela de carrinho
        int selectedRow = view.getTabelaCarrinho().getSelectedRow();
        if (selectedRow != -1) { // Se uma linha estiver selecionada
            // Remove a linha selecionada da tabela de carrinho
            DefaultTableModel model = (DefaultTableModel) view.getTabelaCarrinho().getModel();
            model.removeRow(selectedRow);
            //isso serve para atualizar o valor total dos itens do carrinho
            view.getCampoValorTotalCarrinho().setText(calcularValorTotalCarrinho());
        } else {
            // Se nenhuma linha estiver selecionada, exiba uma mensagem de alerta
            JOptionPane.showMessageDialog(null, "Selecione um item para remover.");
        }
    }

    public void buscarProduto(String nomeProduto) throws SQLException {
        DefaultListModel<String> modelo = new DefaultListModel<>();
        view.getListaProdutos().setModel(modelo); // Define o modelo na lista

        // Limpa a lista antes de adicionar novos produtos
        modelo.clear();

        // Chama o método buscarProduto em ProdutoDAO
        ProdutoDAO produtoDAO = new ProdutoDAO();
        for (Produto produto : produtoDAO.buscarProduto(nomeProduto)) {
            modelo.addElement(produto.getNome());
        }
    }

}
