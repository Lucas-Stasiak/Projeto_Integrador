/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.Endereco;
import java.sql.PreparedStatement;

/**
 *
 * @author luizf
 */
public class EnderecoDAO {
    private final Connection connection;

    public EnderecoDAO(Connection connection) {
        this.connection = connection;
    }
    
    //Le a tabela de estados no BD
    public ArrayList<Endereco> readEstado() throws SQLException{
        String sql = "SELECT * FROM estados";
        
        //Realiza a conexao
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        
        ResultSet resultSet = statement.getResultSet(); //Pega resultados
        
        
        //Cria um array de Endereco para armazenar todos os resultados
        ArrayList<Endereco> estados = new ArrayList<>();
        
        //Enquanto tiver resultado ele roda
        while(resultSet.next()){
            String nome = resultSet.getString("nome");
            String uf = resultSet.getString("sigla");
            
            //Novo objeto Endereco
            Endereco estadoEndereco = new Endereco();
            estadoEndereco.setEstado(nome);
            estadoEndereco.setSigla(uf);
            
            estados.add(estadoEndereco);//Envia o objeto pro array de Endereco
        }
        
        return estados; //Retorna o array
        
    }
    
    
    //Leitura das cidades -- Não está sendo utilizada!
    public ArrayList<Endereco> readCidade() throws SQLException{
        String sql = "SELECT * FROM cidades";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.execute();
        
        ResultSet resultSet = statement.executeQuery();
        
        ArrayList<Endereco> cidades = new ArrayList<>();
        
        while(resultSet.next()){
            String cidade = resultSet.getString("nome");
            
            Endereco cidadeEndereco = new Endereco();
            cidadeEndereco.setCidade(cidade);
            
            cidades.add(cidadeEndereco);
        }
        
        return cidades;
    }
    
    
    //Leitura das cidades por estados
    public ArrayList<Endereco> readCidadePorEstado(String sigla) throws SQLException{
        String sql = "SELECT * FROM cidades WHERE fk_sigla = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, sigla);
        statement.execute();
        
        ResultSet resultSet = statement.executeQuery();
        
        ArrayList<Endereco> cidades = new ArrayList<>();
        
        while(resultSet.next()){
            String cidade = resultSet.getString("nome");
            
            Endereco cidadeEndereco = new Endereco();
            cidadeEndereco.setCidade(cidade);
            
            cidades.add(cidadeEndereco);
        }
        
        return cidades;
    }
    
    //Pega o id da cidade
    public int pegarIdCidade(String sigla, String nome_cidade) throws SQLException{
        String sql = "SELECT * FROM cidades WHERE fk_sigla = ? AND nome = ?";
        int id_cidade = 0;
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, sigla);
        statement.setString(2, nome_cidade);
        statement.execute();
        
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            id_cidade = resultSet.getInt("id_cidades");
        }
       return id_cidade;
    }
    
    //Leitura dos bairros por cidade
    public ArrayList<Endereco> readBairroPorCidade(int id_cidade) throws SQLException{
        String sql = "SELECT * FROM bairros WHERE id_cidade = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_cidade);
        
        ResultSet resultSet = statement.executeQuery();
        
        ArrayList<Endereco> bairros = new ArrayList<>();
        
        while(resultSet.next()){
            String bairro = resultSet.getString("nome");
            
            Endereco bairroEndereco = new Endereco();
            bairroEndereco.setBairro(bairro);
            
            bairros.add(bairroEndereco);
        }
        
        return bairros;
    }
}
