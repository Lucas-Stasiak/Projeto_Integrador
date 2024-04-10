
package dao;

import java.sql.Connection;
import java.sql.SQLException;

public class CompraDAO {
    private final Connection connection;

    public CompraDAO() throws SQLException {
        this.connection = new Conexao().getConnection();
    }
    
    public void adicionarCarrinhoCompra(){
        
    }
}
