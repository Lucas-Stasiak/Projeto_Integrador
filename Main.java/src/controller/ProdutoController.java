package controller;

import dao.ClienteDAO;
import dao.CompraDAO;
import dao.Conexao;
import dao.HistoricoDAO;
import dao.ProdutoDAO;
import dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Produto;
import view.VendaPane;

public class ProdutoController {

    private VendaPane view;

    public ProdutoController(VendaPane view) {
        this.view = view;
    }

    public void readTabelaProduto() throws SQLException {
        DefaultTableModel modelo = (DefaultTableModel) view.getTabelaProduto().getModel();
        modelo.setNumRows(0);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        view.getTabelaProduto().setRowSorter(sorter);

        // Adiciona os produtos à lista
        for (Produto produto : new ProdutoDAO().readProduto()) {
            // Verifica se a quantidade do produto é maior que 0
            if (produto.getQuantidade() > 0) {
                modelo.addRow(new Object[]{
                    produto.getNome(),
                    produto.getCategoria(),
                    produto.getDescricao(),
                    produto.getQuantidade(),
                    produto.getUnidade(),
                    produto.getPreco()
                });
            }
        }
    }

    public Produto readProdutosSelecionados(String nomeProduto) throws SQLException {
        String nome, descricao, categoria, unidade;
        float preco;
        int quantidade, id_produto;

        for (Produto produto : new ProdutoDAO().readProduto()) {

            if (produto.getNome().equals(nomeProduto)) {

                // Se o produto for encontrado, retorna os outros valores
                id_produto = produto.getId_produto();
                nome = produto.getNome();
                descricao = produto.getDescricao();
                preco = produto.getPreco();
                unidade = produto.getUnidade();
                quantidade = produto.getQuantidade();
                categoria = produto.getCategoria();

                // salva as informações em um modelo do produto
                Produto pr = new Produto(id_produto, nome, categoria, descricao, quantidade, unidade, preco);

                return pr;
            }
        }

        // Se o produto não for encontrado
        return null;

    }

    public String calcularValorTotalCarrinho() {
        double total = 0.00;

        // Itera sobre as linhas da tabela de carrinho
        for (int i = 0; i < view.getTabelaCarrinho().getRowCount(); i++) {

            // Pega o valor na coluna de preço
            double preco = Double.parseDouble(view.getTabelaCarrinho().getValueAt(i, 5).toString().replace(',', '.'));

            // Pega a quantidade na coluna de quantidade
            int quantidade = Integer.parseInt(view.getTabelaCarrinho().getValueAt(i, 3).toString().replace(',', '.'));
            double subtotal = preco * quantidade;

            total += subtotal;
        }

        // Retorna o valor total calculado no campo de valor total do carrinho
        return (String.format("%.2f", total));
    }

    // Atualiza o valor total com base na quantidade do produto inserida
    public void alteraQuantidadeProduto() throws SQLException {
        // Verifica se a String esta vazia ou é null ou se um produto esta selecionado
        if (view.getCampoQuantidade() != null && !view.getCampoQuantidade().getText().isEmpty() && view.getTabelaProduto().getSelectedRow() != -1) {

            DefaultTableModel modelo = (DefaultTableModel) view.getTabelaProduto().getModel();

            Produto produtoSelecionado;
            // aqui pega o valor da tabela na linha selecionada na coluna 1 que e o nome
            produtoSelecionado = readProdutosSelecionados(modelo.getValueAt(view.getTabelaProduto().getSelectedRow(), 0).toString());

            // Pega a quantidade
            int quantidade = Integer.parseInt(view.getCampoQuantidade().getText().replace(',', '.'));

            // Verifica se o estoque possui a quantidade inserida
            if (quantidade > produtoSelecionado.getQuantidade()) {

                // Avisa caso a quantidade e maior que a disponivel no estoque
                JOptionPane.showMessageDialog(null, "Quantidade de '" + produtoSelecionado.getNome() + "' disponível no estoque: " + produtoSelecionado.getQuantidade());
                view.getCampoQuantidade().setText(String.valueOf(produtoSelecionado.getQuantidade()));
                float valorTotal = Float.parseFloat(view.getCampoQuantidade().getText().replace(',', '.')) * produtoSelecionado.getPreco();
                view.getCampoValorTotal().setText(String.format("%.2f", valorTotal));
            } else {
                float valorTotal = Float.parseFloat(view.getCampoQuantidade().getText().replace(',', '.')) * produtoSelecionado.getPreco();
                view.getCampoValorTotal().setText(String.format("%.2f", valorTotal));
            }
        }
    }

    public void pegaValoresProdutoSelecionado() throws SQLException {
        int linhaSelecionada = view.getTabelaProduto().getSelectedRow();

        DefaultTableModel modelo = (DefaultTableModel) view.getTabelaProduto().getModel();

        // recebe o objeto com os valores do produto selecionado
        Produto produtoSelecionado = readProdutosSelecionados(modelo.getValueAt(view.getTabelaProduto().getSelectedRow(), 0).toString());
        if (linhaSelecionada != -1 && produtoSelecionado != null) {
            view.getCampoQuantidade().setText("1");

            // calcula o valor unitario do produto * a quantidade
            float valorTotal = Float.parseFloat(view.getCampoQuantidade().getText()) * produtoSelecionado.getPreco();
            view.getCampoValorTotal().setText(String.format("%.2f", valorTotal));
        }
    }

    public void adicionaProdutoCarrinho() throws SQLException {
        int quantidade = Integer.parseInt(view.getCampoQuantidade().getText());
        int novaQuantidade = Integer.parseInt((view.getTabelaProduto().getValueAt(view.getTabelaProduto().getSelectedRow(), 3)).toString()) - quantidade;
        if (quantidade >= 1) {

            Produto produtoSelecionado = readProdutosSelecionados(view.getTabelaProduto().getValueAt(view.getTabelaProduto().getSelectedRow(), 0).toString());

            DefaultTableModel modeloCarrinho = (DefaultTableModel) view.getTabelaCarrinho().getModel();
            DefaultTableModel modeloProduto = (DefaultTableModel) view.getTabelaProduto().getModel();

            if (Integer.parseInt((modeloProduto.getValueAt(view.getTabelaProduto().getSelectedRow(), 3)).toString()) - quantidade >= 0) {
                // Adiciona os dados do produto à tabela de carrinho

                modeloCarrinho.addRow(new Object[]{
                    produtoSelecionado.getNome(),
                    produtoSelecionado.getCategoria(),
                    produtoSelecionado.getDescricao(),
                    quantidade,
                    produtoSelecionado.getUnidade(),
                    produtoSelecionado.getPreco()
                });

                //isso serve para atualizar o valor total dos itens do carrinho
                view.getCampoValorTotalCarrinho().setText(calcularValorTotalCarrinho());

                modeloProduto.setValueAt(novaQuantidade, view.getTabelaProduto().getSelectedRow(), 3);
            } else {
                if (Integer.parseInt((modeloProduto.getValueAt(view.getTabelaProduto().getSelectedRow(), 3)).toString()) == 0) {
                    JOptionPane.showMessageDialog(null, "Estoque de '" + produtoSelecionado.getNome() + "' vazio.");
                } else {
                    JOptionPane.showMessageDialog(null, "Quantidade máxima de '" + produtoSelecionado.getNome() + "' disponível: " + Math.abs(novaQuantidade));
                    view.getCampoQuantidade().setText(String.valueOf(Math.abs(novaQuantidade)));
                    float valorTotal = Math.abs(novaQuantidade) * produtoSelecionado.getPreco();
                    view.getCampoValorTotal().setText(String.format("%.2f", valorTotal));
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "A quantidade precisa ser maior/igual a 1.");
        }
    }

    public void removerProdutoCarrinho() throws SQLException {
        // Pega a linha selecionada na tabela de carrinho
        int linhaSelecionada = view.getTabelaCarrinho().getSelectedRow();

        // Verifica se tem uma linha selecionada
        if (linhaSelecionada != -1) {
            DefaultTableModel modeloProduto = (DefaultTableModel) view.getTabelaProduto().getModel();
            DefaultTableModel modeloCarrinho = (DefaultTableModel) view.getTabelaCarrinho().getModel();

            // nome do produto que ta sendo removido do carrinho
            String nomeProduto = (String) modeloCarrinho.getValueAt(linhaSelecionada, 0);

            // quantidade a ser removida
            int quantidadeRemovida = (int) modeloCarrinho.getValueAt(linhaSelecionada, 3);

            // Itera sobre as linhas da tabela de produtos para encontrar o produto com o mesmo nome
            for (int i = 0; i < modeloProduto.getRowCount(); i++) {
                if (modeloProduto.getValueAt(i, 0).equals(nomeProduto)) {
                    // quantidade atual do produto na tabela de produtos
                    int quantidadeAtual = (int) modeloProduto.getValueAt(i, 3);

                    // atualiza a quantidade na tabela de produtos menos a quantidade removida
                    modeloProduto.setValueAt(quantidadeAtual + quantidadeRemovida, i, 3);
                    break; // Sai do loop
                }
            }

            // Remove a linha selecionada da tabela de carrinho
            modeloCarrinho.removeRow(linhaSelecionada);

            // Isso serve para atualizar o valor total dos itens do carrinho
            view.getCampoValorTotalCarrinho().setText(calcularValorTotalCarrinho());

        } else {
            // Se nenhuma linha estiver selecionada, exibe uma mensagem de alerta
            JOptionPane.showMessageDialog(null, "Selecione um item para remover.");
        }
    }

    public void buscarProduto(String nomeProduto) throws SQLException {
        DefaultTableModel modelo = (DefaultTableModel) view.getTabelaProduto().getModel();
        modelo.setNumRows(0);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo);
        view.getTabelaProduto().setRowSorter(sorter);

        // Chama o método buscarProduto em ProdutoDAO
        ProdutoDAO produtoDAO = new ProdutoDAO();
        for (Produto produto : produtoDAO.buscarProduto(nomeProduto)) {
            if (produto.getQuantidade() > 0) {
                Object[] data = {
                    produto.getNome(),
                    produto.getCategoria(),
                    produto.getDescricao(),
                    produto.getQuantidade(),
                    produto.getUnidade(),
                    produto.getPreco()
                };
                modelo.addRow(data);
            }
        }
    }

    public void cancelarCompra() throws SQLException {
        DefaultTableModel modeloCarrinho = (DefaultTableModel) view.getTabelaCarrinho().getModel();

        if (modeloCarrinho.getRowCount() > 0) {

            // Itera sobre todas as linhas da tabela
            for (int i = modeloCarrinho.getRowCount() - 1; i >= 0; i--) {

                // Remove a linha da tabela
                modeloCarrinho.removeRow(i);
                view.getCampoValorTotalCarrinho().setText(calcularValorTotalCarrinho());
                readTabelaProduto();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Carrinho Vazio.");
        }
    }

    public void concluirVenda() throws SQLException, ParseException {
        DefaultTableModel modeloCarrinho = (DefaultTableModel) view.getTabelaCarrinho().getModel();
        if (modeloCarrinho.getRowCount() > 0) {

            Connection conexao = new Conexao().getConnection();
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            Integer id_cliente = clienteDAO.buscarIdClienteCPF(view.getCampoCpfCliente().getText());

            // cliente padrão caso ele não seja informado
            if (view.getCampoCpfCliente().getText().equals("")) {
                id_cliente = 0;
            }

            if (id_cliente != -1) {

                UsuarioDAO usuarioDAO = new UsuarioDAO(conexao);
                int id_usuario = usuarioDAO.buscarIdUsuarioCPF(view.getCpf());

                float valorTotal = Float.parseFloat(view.getCampoValorTotalCarrinho().getText().replace(',', '.'));

                HistoricoDAO historicoDAO = new HistoricoDAO(conexao);
                int id_historico = historicoDAO.adicionarCarrinhoHistorico(valorTotal, id_usuario, id_cliente);

                for (int i = 0; i < modeloCarrinho.getRowCount(); i++) {

                    Produto produtoSelecionado = readProdutosSelecionados(modeloCarrinho.getValueAt(i, 0).toString());

                    int quantidade = (int) modeloCarrinho.getValueAt(i, 3);

                    CompraDAO compraDAO = new CompraDAO();
                    compraDAO.adicionarCarrinhoCompra(produtoSelecionado.getPreco(), produtoSelecionado.getUnidade(), quantidade, id_historico, produtoSelecionado.getId_produto());

                    ProdutoDAO produtoDAO = new ProdutoDAO();
                    // Diminui a quantidade de produtos vendidos do estoque
                    produtoDAO.diminuirQuantidade(quantidade, produtoSelecionado.getNome());
                }
                limparCarrinho();
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não encontrado.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Carrinho Vazio.");
        }
    }

    public void limparCarrinho() {
        DefaultTableModel modeloCarrinho = (DefaultTableModel) view.getTabelaCarrinho().getModel();
        modeloCarrinho.setRowCount(0);
        JTextField campoValorTotal = new JTextField();
        campoValorTotal.setText("");
        view.setCampoValorTotal(campoValorTotal);
    }

}
