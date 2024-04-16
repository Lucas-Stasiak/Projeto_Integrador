package dao;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.NClob;
import java.sql.PreparedStatement;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;
import model.Cliente;

public class ClienteDAO {

    private final Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }

    //Novo cliente com endereço
    public void insertComEndereco(Cliente cliente, int id_endereco) throws SQLException {
        String sql = "INSERT INTO cliente (nome, cpf, telefone, fk_id_endereco, observacao) VALUES (?,?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cliente.getNome());
        statement.setString(2, cliente.getCpf());
        statement.setString(3, cliente.getTelefone());
        statement.setInt(4, id_endereco);
        statement.setString(5, cliente.getObservacao());
        statement.execute();

    }

    //Novo cliente sem endereço
    public void insert(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (nome, cpf, telefone, observacao) VALUES (?,?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cliente.getNome());
        statement.setString(2, cliente.getCpf());
        statement.setString(3, cliente.getTelefone());
        statement.setString(4, cliente.getObservacao());
        statement.execute();

    }

    public int buscarIdClienteCPF(String cpf) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE cpf LIKE ?";

        // Conexao com o bd
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cpf);

        // Executando a consulta
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            // Se houver resultados, retorna o valor do campo "id_cliente" da primeira linha
            return resultSet.getInt("id_cliente");
        } else {
            return -1;
        }
    }
    
    public boolean existeClientePorCPF(String cpf) throws SQLException{
        String sql = "SELECT * FROM cliente WHERE cpf like ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cpf);
        statement.execute();
        
        ResultSet resultSet = statement.getResultSet();
        
        return resultSet.next();

    }

}
