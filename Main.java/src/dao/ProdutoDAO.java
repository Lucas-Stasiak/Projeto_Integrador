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


}
