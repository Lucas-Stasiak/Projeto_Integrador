package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Produto;

public class ProdutoDAO {

    private final Connection connection;

    public ProdutoDAO() throws SQLException {
        this.connection = new Conexao().getConnection(); // Obtém a conexão corretamente
    }

    public ArrayList<Produto> readProduto() throws SQLException {

        String sql = "select * from produtos";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();

        ArrayList<Produto> produtos = new ArrayList<>();

        //busca todos os produtos
        while (resultSet.next()) {

            String nome = resultSet.getString("nome");
            String descricao = resultSet.getString("descricao");
            float preco = resultSet.getFloat("preco");
            String unidade = resultSet.getString("unidade");
            int quantidade = resultSet.getInt("quantidade");
            String categoria = resultSet.getString("fk_nome_categoria");

            Produto produtoComDadosDoBanco = new Produto(nome, descricao, preco, unidade, quantidade, categoria);

            // verifica se a quantidade do produto no estoque é maior que 0 para mostrar na listaprodutos  
            produtos.add(produtoComDadosDoBanco);//adiciona o produto dentro do array
        }

        resultSet.close();
        statement.close();
        return produtos;
    }

    public ArrayList<Produto> buscarProduto(String nomeProduto) throws SQLException {
        String sql = "select * from produtos where lower(nome) like ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setString(1, "%" + nomeProduto.toLowerCase() + "%");
        ResultSet resultSet = statement.executeQuery();
        // Cria uma lisa com varios produtos
        ArrayList<Produto> produtos = new ArrayList<>();

        // Itera sobre os resultados da consulta e adiciona os produtos a lista
        while (resultSet.next()) {
            String nome = resultSet.getString("nome");
            String descricao = resultSet.getString("descricao");
            float preco = resultSet.getFloat("preco");
            String unidade = resultSet.getString("unidade");
            int quantidade = resultSet.getInt("quantidade");
            String categoria = resultSet.getString("fk_nome_categoria");

            // Cria o produto com base nas váriaveis 
            Produto produto = new Produto(nome, descricao, preco, unidade, quantidade, categoria);
            // Adiciona o produto criado na lista produto
            produtos.add(produto);
        }

        // Fecha as conexões com o banco de dados
        resultSet.close();
        statement.close();

        return produtos;
    }

    public void aumentarQuantidade(int quantidade, String nome) throws SQLException {
        String sql = "update produtos set quantidade = quantidade + ? where nome = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, quantidade);
        statement.setString(2, nome);

        statement.executeUpdate();
        statement.close();
    }

    public void diminuirQuantidade(int quantidade, String nome) throws SQLException {
        String sql = "update produtos set quantidade = quantidade - ? where nome = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setInt(1, quantidade);
        statement.setString(2, nome);

        statement.executeUpdate();
        statement.close();
    }

}
