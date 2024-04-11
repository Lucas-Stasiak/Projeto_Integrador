package dao;

import java.sql.Connection;

public class HistoricoDAO {

    private final Connection connection;

    public HistoricoDAO(Connection connection) {
        this.connection = connection;
    }
    
    public void adicionarCarrinhoHistorico(float precoTotal, int id_usuario, int cpfCliente){
        
        
    }
}
