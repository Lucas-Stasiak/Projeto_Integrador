package dao;

import java.sql.Connection;
import model.Historico;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;

public class HistoricoDAO {

    private final Connection connection;

    public HistoricoDAO(Connection connection) {
        this.connection = connection;
    }

    public int adicionarCarrinhoHistorico(float precoTotal, int id_usuario, int cpfCliente) throws SQLException {
        Historico historico = new Historico(precoTotal, id_usuario, cpfCliente);

        String sql = "INSERT INTO historico (data, tempo, preco_total, fk_id_cliente, fk_id_usuario) VALUES (?, ?, ?, ?, ?)";

        PreparedStatement statement = connection.prepareStatement(sql);

        statement.setDate(1, new java.sql.Date(historico.getData().getTime()));
        statement.setTimestamp(2, historico.getTempo());
        statement.setFloat(3, historico.getPrecoTotal());
        statement.setInt(4, historico.getFk_id_cliente());
        statement.setInt(5, historico.getFk_id_usuario());

        statement.executeUpdate();

        String sql2 = "SELECT * FROM historico WHERE tempo = ?";
        PreparedStatement statementSelect = connection.prepareStatement(sql2);

        statementSelect.setTimestamp(1, historico.getTempo());

        ResultSet resultSet = statementSelect.executeQuery();

        return resultSet.getInt("id");
    }
}
