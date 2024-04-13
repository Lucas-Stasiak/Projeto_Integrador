/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Statement;
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
    
    
    //Inserir novo endereco na tabela Endereco
    public int insertEndereco(String complemento, int numero, int id_logradouro) throws SQLException{
        String sql = "INSERT INTO endereco(numero, complemento, fk_id_logradouro) VALUES(?,?,?)";
        int id_endereco = -1;
        
        PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setInt(1, numero);
        statement.setString(2, complemento);
        statement.setInt(3, id_logradouro);
        statement.execute();

        
        ResultSet resultSet = statement.getGeneratedKeys();

        if(resultSet.next()){
            id_endereco = resultSet.getInt("id_endereco");
        }
        
        return id_endereco;
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
    
    //Pegar o id da cidade pela sigla do estado e seu nome
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
    
    //Pegar id do logradouro pelo seu CEP
    public int pegerIdLogradouro(String cep) throws SQLException{
        String sql = "SELECT * FROM logradouros WHERE cep = ?";
        int id_logradouro = 0;
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cep);
        statement.execute();
        
        ResultSet resultSet = statement.executeQuery();

        while(resultSet.next()){
            id_logradouro = resultSet.getInt("id_logradouro");
        }
        
       return id_logradouro;      

    } 
    
    //Pegar id do bairro pelo seu nome
    public int pegarIdBairro(String nome, int id_cidade) throws SQLException{
        String sql = "SELECT * FROM bairros WHERE nome = ? AND fk_id_cidades = ? ";
        int id_bairro = 0;
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, nome);
        statement.setInt(2, id_cidade);
        statement.execute();
        
        ResultSet resultSet = statement.executeQuery();
        
        
        while(resultSet.next()){
            id_bairro = resultSet.getInt("id_bairro");
        }
        
        return id_bairro; 
    }
    
    
    //Ler logradouro pelo id do bairro
    public ArrayList<Endereco> readLogradouroPorBairro(int id_bairro) throws SQLException{
        String sql = "SELECT * FROM logradouros WHERE fk_id_bairro = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_bairro);
        
        ResultSet resultSet = statement.executeQuery();
        
        ArrayList<Endereco> logradouros = new ArrayList<>();
        
        while(resultSet.next()){
            String logradouro = resultSet.getString("nome");
            Endereco logradouroEndereco = new Endereco();
            logradouroEndereco.setLogradouro(logradouro);
            
            logradouros.add(logradouroEndereco);
        }
        
        return logradouros;
        
    }
    
    
    //Leitura dos bairros por cidade
    public ArrayList<Endereco> readBairroPorCidade(int id_cidade) throws SQLException{
        String sql = "SELECT * FROM bairros WHERE fk_id_cidades = ?";
        
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
    
    public boolean existeCEP(String cep) throws SQLException{
        String sql = "SELECT * FROM logradouros WHERE cep = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, cep);
        statement.execute();
        
        ResultSet resultSet = statement.getResultSet();
        
        return resultSet.next();
    }
    
    public void novoLogradouro(Endereco endereco, int id_cidade, int id_bairro) throws SQLException{
        String sql = "INSERT INTO logradouros(cep, nome, uf, fk_id_cidades, fk_id_bairro) VALUES(?,?,?,?,?)";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, endereco.getCep());
        statement.setString(2, endereco.getLogradouro());
        statement.setString(3, endereco.getSigla());
        statement.setInt(4, id_cidade);
        statement.setInt(5, id_bairro);
        statement.execute();
        
        
    }
    
    public boolean existeBairro(String bairro, int id_cidade) throws SQLException{
        String sql = "SELECT * FROM bairros WHERE fk_id_cidades = ? AND nome = ?";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setInt(1, id_cidade);
        statement.setString(2, bairro);
        statement.execute();
        
        ResultSet resultSet = statement.getResultSet();
        
        return resultSet.next();
    }
    
    public void novoBairro(String bairro, int id_cidade) throws SQLException{
        String sql = "INSERT INTO bairros(nome,fk_id_cidades) VALUES(?,?)";
        
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, bairro);
        statement.setInt(2, id_cidade);
        statement.execute();
        
    }
}
