package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Usuario;

public class UsuarioDAO {

    private final Connection connection;
    public int id;
    public String nome;
    public String cpf;
    public String senha;
    public String telefone;
    public boolean admin;

    public UsuarioDAO(Connection connection) {
        this.connection = connection;
    }

    //Esta comentada para evitar ficar inserindo usuario atoa
    public void insert(Usuario usuario) throws SQLException {
        String sql = "insert into usuario(nome, cpf, senha, telefone, admin, observacao) values(?, ?, ?, ?, ?, ?); ";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getNome());
        statement.setString(2, usuario.getCpf());
        statement.setString(3, usuario.getSenha());
        statement.setString(4, usuario.getTelefone());
        statement.setBoolean(5, usuario.isAdmin());
        statement.setString(6, usuario.getObservacao());
        statement.execute();

    }
    
    public void insertComEndereco(Usuario usuario, int id_endereco) throws SQLException{
        String sql = "insert into usuario(nome, cpf, senha, telefone, admin, observacao, fk_id_endereco) values(?, ?, ?, ?, ?, ?, ?); ";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getNome());
        statement.setString(2, usuario.getCpf());
        statement.setString(3, usuario.getSenha());
        statement.setString(4, usuario.getTelefone());
        statement.setBoolean(5, usuario.isAdmin());
        statement.setString(6, usuario.getObservacao());
        statement.setInt(7, id_endereco);
        statement.execute();
    }

    public void delete(Usuario usuario) throws SQLException {
        String sql = "delete from usuario where id_usuario = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, usuario.getId());
        statement.execute();
        
    }

    public void update(Usuario usuario) throws SQLException {
        String sql = "update usuario set nome = ?, cpf = ?, senha = ?, telefone = ?, admin = ? where cpf = ?";

        PreparedStatement statement = connection.prepareStatement(sql);

        //Evita SQL Injection 
        statement.setString(1, usuario.getNome());
        statement.setString(2, usuario.getCpf());
        statement.setString(3, usuario.getSenha());
        statement.setString(4, usuario.getTelefone());
        statement.setBoolean(5, usuario.isAdmin());
        statement.setString(6, usuario.getCpf());

        statement.execute();

    }

    public boolean verificaLoginPorCPFeSenha(Usuario usuario) throws SQLException {
        String sql = "select * from usuario where cpf = ? and senha = ? ";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getCpf());
        statement.setString(2, usuario.getSenha());
        statement.execute();

        ResultSet resultSet = statement.getResultSet();

        return resultSet.next();
    }

    public boolean verificaExistencia(Usuario usuario) throws SQLException {
        String sql = "select * from usuario where cpf = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getCpf());
        statement.execute();

        ResultSet resultSet = statement.getResultSet();

        return resultSet.next();
    }

    public boolean verificaAdmin(Usuario usuario) throws SQLException {
        String sql = "select * from usuario where cpf = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getCpf());
        statement.execute();

        ResultSet resultSet = statement.getResultSet();

        while (resultSet.next()) {
            admin = resultSet.getBoolean("admin");
        }
        return admin;
    }

    //Leitura de todos os usuarios
    public ArrayList<Usuario> readUsuario() throws SQLException {

        String sql = "select * from usuario";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();

        ArrayList<Usuario> usuarios = new ArrayList<>();//Array da várivael usuario

        //Enquanto tiver resultado do banco de dados ele continia
        while (resultSet.next()) {

            id = resultSet.getInt("id_usuario");
            nome = resultSet.getString("nome");
            cpf = resultSet.getString("cpf");
            telefone = resultSet.getString("telefone");
            admin = resultSet.getBoolean("admin");

            Usuario usuarioComDadosDoBanco = new Usuario(id, nome, cpf, telefone, admin);//Pega os dados do Banco de dados e envia para um usuario

            usuarios.add(usuarioComDadosDoBanco);//adiciona o usuario dentro do array
        }
        
        return usuarios;
    }

    public ArrayList<Usuario> buscarUsuarioNOMEeCPF(Usuario usuario) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE nome LIKE ? and cpf LIKE ?";

        // Conexao com o bd
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, "%" + usuario.getNome() + "%");
        statement.setString(2, "%" + usuario.getCpf() + "%");
        statement.execute();

        // Executando a consulta
        ResultSet resultSet = statement.executeQuery();

        ArrayList<Usuario> usuarios = new ArrayList<>();

        // Iterando sobre os resultados
        while (resultSet.next()) {
            int idUsuario = resultSet.getInt("id_usuario");
            String nomeUsuario = resultSet.getString("nome");
            String cpfUsuario = resultSet.getString("cpf");
            String telefoneUsuario = resultSet.getString("telefone");
            boolean adminUsuario = resultSet.getBoolean("admin");

            Usuario usuarioPesquisado = new Usuario(idUsuario, nomeUsuario, cpfUsuario, telefoneUsuario, adminUsuario);
            usuarios.add(usuarioPesquisado);
        }

        return usuarios;
    }

    public ArrayList<Usuario> buscarUsuarioNOMEeCPFeADM(Usuario usuario) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE admin = ? and nome LIKE ? and cpf LIKE ?";

        //Conexao com o bd
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setBoolean(1, usuario.isAdmin());
        statement.setString(2, "%" + usuario.getNome() + "%");
        statement.setString(3, "%" + usuario.getCpf() + "%");
        statement.execute();

        ResultSet resultSet = statement.executeQuery();

        ArrayList<Usuario> usuarios = new ArrayList<>();

        // Iterando sobre os resultados
        while (resultSet.next()) {
            int idUsuario = resultSet.getInt("id_usuario");
            String nomeUsuario = resultSet.getString("nome");
            String cpfUsuario = resultSet.getString("cpf");
            String telefoneUsuario = resultSet.getString("telefone");
            boolean adminUsuario = resultSet.getBoolean("admin");

            Usuario usuarioPesquisado = new Usuario(idUsuario, nomeUsuario, cpfUsuario, telefoneUsuario, adminUsuario);
            usuarios.add(usuarioPesquisado);
        }
        return usuarios;

    }

    public int buscarIdUsuarioCPF(String cpf) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE cpf = ?";

        // Conexao com o bd
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cpf);

        // Executando a consulta
        ResultSet resultSet = statement.executeQuery();

        if (resultSet.next()) {
            return resultSet.getInt("id_usuario");
        } else {
            System.out.println("NAO ACHADO");
            return -1;
        }
    }

}
