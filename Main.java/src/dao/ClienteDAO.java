package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Endereco;

public class ClienteDAO {

    private final Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    public Endereco buscarEnderecoPorCEP(String cep) throws SQLException {
        Endereco endereco = null;
        String sql = "SELECT e.rua, b.nome AS bairro, c.nome AS cidade, est.sigla AS estado "
                + "FROM enderecos e "
                + "INNER JOIN bairros b ON e.id_bairros = b.id_bairros "
                + "INNER JOIN cidades c ON e.id_cidades = c.id_cidades "
                + "INNER JOIN estados est ON c.id_estados = est.id_estados "
                + "WHERE e.cep = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, cep);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String logradouro = rs.getString("rua");
                String bairro = rs.getString("bairro");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                endereco = new Endereco(logradouro, bairro, cidade, estado, cep);
            }
        }
        return endereco;
    }
}
