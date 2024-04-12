
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CompraDAO {
    private final Connection connection;

    public CompraDAO() throws SQLException {
        this.connection = new Conexao().getConnection();
    }
    
    public void adicionarCarrinhoCompra(float preco, String unidade, int quantidade, int id_historico, int id_produto) throws SQLException{

        String sql = "INSERT INTO compra (preco, unidade, quantidade, fk_id_historico, fk_id_produto) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setFloat(1, preco);
        statement.setString(2, unidade);
        statement.setInt(3, quantidade);
        statement.setInt(4, id_historico);
        statement.setInt(5, id_produto);

        statement.executeUpdate();
    }
}
