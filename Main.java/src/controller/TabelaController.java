
package controller;

import dao.Conexao;
import dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Usuario;
import view.UsuarioPanel;


public class TabelaController {
    
    private UsuarioPanel view;

    public TabelaController(UsuarioPanel view) {
        this.view = view;
    }
    
    
    
    //Leitura da tabela
    public void readTabela() throws SQLException{
        
        
        DefaultTableModel modelo = (DefaultTableModel) view.getTabelaUsuario().getModel(); //Pega o modelo da tabela 
        view.getTabelaUsuario().setRowSorter(new TableRowSorter(modelo)); //Classifica as linha da tabela 
        
        
        //Realiza a conexao
        Connection conexao = new Conexao().getConnection();
        UsuarioDAO usuarioDao = new UsuarioDAO(conexao);
        
        
        //Chama a função de leitura de usuario e adiciona nas linhas e colunas
        for(Usuario usuario: usuarioDao.readUsuario()){
            
            modelo.addRow(new Object[]{
            usuario.getId(),
            usuario.getNome(),
            usuario.getCpf(),
            usuario.getTelefone(),
            usuario.isAdmin()
            });
        }
        }
        
    }
