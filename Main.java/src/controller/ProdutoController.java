package controller;

import dao.ProdutoDAO;
import java.sql.SQLException;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
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
            // Verifica se a quantidade do produto é maior que 0
            if (produto.getQuantidade() > 0) {
                modelo.addElement(produto.getNome());
            }
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
        // Verifica se a String esta vazia ou é null ou se um produto esta selecionado
        if (view.getCampoQuantidade() != null && !view.getCampoQuantidade().getText().isEmpty() && view.getListaProdutos().getSelectedValue() != null) {

            Produto produtoSelecionado;
            produtoSelecionado = readProdutosSelecionados(view.getListaProdutos().getSelectedValue());

            // Pega a quantidade
            int quantidade = Integer.parseInt(view.getCampoQuantidade().getText().replace(',', '.'));

            // Verifica se o estoque possui a quantidade inserida
            if (quantidade > produtoSelecionado.getQuantidade()) {

                // Avisa caso a quantidade e maior que a disponivel no estoque
                JOptionPane.showMessageDialog(null, "Quantidade de '" + produtoSelecionado.getNome() + "' disponível no estoque: " + produtoSelecionado.getQuantidade());
                view.getCampoQuantidade().setText(String.valueOf(produtoSelecionado.getQuantidade()));
                float valorTotal = Float.parseFloat(view.getCampoQuantidade().getText().replace(',', '.')) * Float.parseFloat(view.getCampoValorUnitario().getText().replace(',', '.'));
                view.getCampoValorTotal().setText(String.format("%.2f", valorTotal));
            } else {
                float valorTotal = Float.parseFloat(view.getCampoQuantidade().getText().replace(',', '.')) * Float.parseFloat(view.getCampoValorUnitario().getText().replace(',', '.'));
                view.getCampoValorTotal().setText(String.format("%.2f", valorTotal));
            }
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
        int quantidade = Integer.parseInt(view.getCampoQuantidade().getText());
        if (quantidade >= 1) {
            Produto produtoSelecionado = readProdutosSelecionados(view.getListaProdutos().getSelectedValue());

            if (quantidade <= produtoSelecionado.getQuantidade()) {

                // Pega a categoria do produto
                String categoria = getCategoriaDoProduto(produtoSelecionado.getNome());

                // Adiciona os dados do produto à tabela de carrinho
                DefaultTableModel model = (DefaultTableModel) view.getTabelaCarrinho().getModel();
                model.addRow(new Object[]{produtoSelecionado.getNome(), categoria, view.getCampoQuantidade().getText(), produtoSelecionado.getPreco()});

                //isso serve para atualizar o valor total dos itens do carrinho
                view.getCampoValorTotalCarrinho().setText(calcularValorTotalCarrinho());

                ProdutoDAO produtoDAO = new ProdutoDAO();
                // Diminui a quantidade adicionada ao carrinho do estoque
                produtoDAO.diminuirQuantidade(Integer.parseInt(view.getCampoQuantidade().getText()), produtoSelecionado.getNome());
                // Para atualizar a lista de produtos
                readListaProduto();
            } else {
                JOptionPane.showMessageDialog(null, "Quantidade máxima de '" + produtoSelecionado.getNome() + "' disponível: " + produtoSelecionado.getQuantidade());
                view.getCampoQuantidade().setText(String.valueOf(produtoSelecionado.getQuantidade()));
                float valorTotal = Float.parseFloat(view.getCampoQuantidade().getText().replace(',', '.')) * Float.parseFloat(view.getCampoValorUnitario().getText().replace(',', '.'));
                view.getCampoValorTotal().setText(String.format("%.2f", valorTotal));
            }
        } else {
            JOptionPane.showMessageDialog(null, "A quantidade precisa ser maior/igual a 1.");
        }
    }

    public void removerProdutoCarrinho() throws SQLException {
        // pega a linha selecionada na tabelaProdutos
        int linhaSelecionada = view.getTabelaCarrinho().getSelectedRow();

        // Verifica se uma linha está selecionada na tabela de carrinho
        if (linhaSelecionada != -1) { // Se uma linha estiver selecionada

            ProdutoDAO produtoDAO = new ProdutoDAO();

            // Retorna a quantidade removida para o carrinho
            // pega o valor da coluna quantidade da tabela na linha selecionada
            Object quantidadeProduto = view.getTabelaCarrinho().getValueAt(linhaSelecionada, 2);

            // transforma a quantidade que retorna como objeto em String
            int quantidade = Integer.parseInt((String) quantidadeProduto);

            Object nomeProduto = view.getTabelaCarrinho().getValueAt(linhaSelecionada, 0);
            String nome = nomeProduto.toString();
            produtoDAO.aumentarQuantidade(quantidade, nome);

            // Remove a linha selecionada da tabela de carrinho
            DefaultTableModel model = (DefaultTableModel) view.getTabelaCarrinho().getModel();
            model.removeRow(linhaSelecionada);

            //isso serve para atualizar o valor total dos itens do carrinho
            view.getCampoValorTotalCarrinho().setText(calcularValorTotalCarrinho());

            // Para atualizar a lista de produtos
            readListaProduto();
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
            if (produto.getQuantidade() > 0) {
                modelo.addElement(produto.getNome());
            }
        }
    }

    public void cancelarCompra() throws SQLException {
        DefaultTableModel modeloTabela = (DefaultTableModel) view.getTabelaCarrinho().getModel();
        if (modeloTabela.getRowCount() > 0) {

            // Itera sobre todas as linhas da tabela
            for (int i = modeloTabela.getRowCount() - 1; i >= 0; i--) {

                Object nomeProduto = modeloTabela.getValueAt(i, 0);
                Object quantidadeProduto = modeloTabela.getValueAt(i, 2);

                int quantidade = Integer.parseInt(quantidadeProduto.toString());
                String nome = nomeProduto.toString();

                // Atualiza a quantidade no estoque no banco de dados
                ProdutoDAO produtoDAO = new ProdutoDAO();
                produtoDAO.aumentarQuantidade(quantidade, nome);

                // Remove a linha da tabela
                modeloTabela.removeRow(i);
                view.getCampoValorTotalCarrinho().setText(calcularValorTotalCarrinho());
                readListaProduto();
            }
        }
    }

}
