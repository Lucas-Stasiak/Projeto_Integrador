
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Cliente;


public class ClienteDAO {
    
    private final Connection connection;

    public ClienteDAO(Connection connection) {
        this.connection = connection;
    }
   
   //Novo cliente com endereço
   public void insertComEndereco(Cliente cliente, int id_endereco) throws SQLException{
       String sql = "INSERT INTO cliente (nome, cpf, telefone, rg, fk_id_endereco, observacao) VALUES (?,?,?,?,?,?)";
       
       PreparedStatement statement = connection.prepareStatement(sql);
       statement.setString(1, cliente.getNome());
       statement.setString(2, cliente.getCpf());
       statement.setString(3, cliente.getTelefone());
       statement.setString(4, cliente.getRg());
       statement.setInt(5, id_endereco);
       statement.setString(6, cliente.getObservacao());
       statement.execute();
       
   } 
   
   //Novo cliente sem endereço
   public void insert(Cliente cliente) throws SQLException{
       String sql = "INSERT INTO cliente (nome, cpf, telefone, rg, observacao) VALUES (?,?,?,?,?)";
       
       PreparedStatement statement = connection.prepareStatement(sql);
       statement.setString(1, cliente.getNome());
       statement.setString(2, cliente.getCpf());
       statement.setString(3, cliente.getTelefone());
       statement.setString(4, cliente.getRg());
       statement.setString(5, cliente.getObservacao());
       statement.execute();
       
   }
   
   public int buscarIdClienteCPF(String cpf) throws SQLException {
        String sql = "SELECT * FROM cliente WHERE cpf LIKE ?";

        // Conexao com o bd
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cpf);
        statement.execute();

        // Executando a consulta
        ResultSet resultSet = statement.executeQuery();

        return resultSet.getInt("id_cliente");
}
    
}
