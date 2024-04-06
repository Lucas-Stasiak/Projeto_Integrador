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
    
    
    
    
    
    
}
