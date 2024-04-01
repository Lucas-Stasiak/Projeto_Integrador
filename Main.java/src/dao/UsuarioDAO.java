/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Usuario;

/**
 *
 * @author Th3Br
 */
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
       /*String sql = "insert into usuario(nome_usuario, cpf, senha, telefone, admin) values(?, ?, ?, ?, ?); ";
       
       PreparedStatement statement = connection.prepareStatement(sql);
       statement.setString(1, usuario.getNome());
       statement.setString(2, usuario.getCpf());
       statement.setString(3, usuario.getSenha());
       statement.setString(4, usuario.getTelefone());
       statement.setBoolean(5, usuario.isAdmin());
       statement.execute();*/
       
    }
    
    
    public void delete(Usuario usuario) throws SQLException{
        String sql = "delete from usuario where id_usuario = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, usuario.getId());
        statement.execute();
               
    }
    
    
    public void update(Usuario usuario) throws SQLException{                                                   
        String sql = "update usuario set nome_usuario = ?, cpf = ?, senha = ?, telefone = ?, admin = ? where cpf = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getNome());
        statement.setString(2, usuario.getCpf());
        statement.setString(3, usuario.getSenha());
        statement.setString(4, usuario.getTelefone());
        statement.setBoolean(5, usuario.isAdmin());
        statement.setString(6, usuario.getCpf());
        
        statement.execute();
  
    } 
    
    public ArrayList<Usuario> readUsuario() throws SQLException{
        
        String sql = "select * from usuario";
        
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        ResultSet resultSet = statement.getResultSet();
        
        
        ArrayList<Usuario> usuarios = new ArrayList<>();//Array da várivael usuario
        
        
        //Enquanto tiver resultado do banco de dados ele continia
        while(resultSet.next()){
            
            id = resultSet.getInt("id_usuario");
            nome = resultSet.getString("nome_usuario");
            cpf = resultSet.getString("cpf");
            telefone = resultSet.getString("telefone");
            admin = resultSet.getBoolean("admin");
           
            Usuario usuarioComDadosDoBanco = new Usuario(id, nome, cpf, telefone, admin);//Pega os dados do Banco de dados e envia para um usuario
            
            usuarios.add(usuarioComDadosDoBanco);//adiciona o usuario dentro do array

        }
        
        return usuarios;
       
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
     
    
    public boolean verificaExistencia(Usuario usuario) throws SQLException{
        String sql = "select * from usuario where cpf = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getCpf());
        statement.execute();
        
        ResultSet resultSet = statement.getResultSet();
        
        return resultSet.next();
    }
    
    public boolean verificaAdmin(Usuario usuario) throws SQLException{
        String sql = "select * from usuario where cpf = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, usuario.getCpf());
        statement.execute();
        
        ResultSet resultSet = statement.getResultSet();
        
        while(resultSet.next()){
            admin = resultSet.getBoolean("admin");
        }
            return admin;
    }

}
