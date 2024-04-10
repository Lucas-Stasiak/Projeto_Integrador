package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class HistoricoDAO {

    private final Connection connection;

    public HistoricoDAO() throws SQLException {
        this.connection = new Conexao().getConnection();
    }
    
    public void adicionarCarrinhoHistorico(){
        
    }
}
