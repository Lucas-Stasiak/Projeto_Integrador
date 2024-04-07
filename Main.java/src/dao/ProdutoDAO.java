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

        String sql = "select * from produto";

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

            Produto produtoComDadosDoBanco = new Produto(nome, descricao, preco, unidade, quantidade);

            produtos.add(produtoComDadosDoBanco);//adiciona o produto dentro do array
        }

        resultSet.close();
        statement.close();
        return produtos;
    }

    public String getCategoriaDoProduto(String nome) {
        String categoria = null;

        try {
            // Consulta SQL para pegar a categoria(chave estrangeira) do produto com base no nome
            String sql = "select nomecategoria from produto where nome = ?";

            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, nome);

            ResultSet resultSet = statement.executeQuery();

            // Verificar resultados
            if (resultSet.next()) {
                categoria = resultSet.getString("nomecategoria");
            }

            resultSet.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categoria;
    }

    public ArrayList<Produto> buscarProduto(String nomeProduto) throws SQLException {
        String sql = "SELECT * FROM produto WHERE LOWER(nome) LIKE ?";
        
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
            
            // Cria o produto com base nas váriaveis 
            Produto produto = new Produto(nome, descricao, preco, unidade, quantidade);
            // Adiciona o produto criado na lista produto
            produtos.add(produto);
        }

        // Fecha as conexões com o banco de dados
        resultSet.close();
        statement.close();

        return produtos;
    }

}
