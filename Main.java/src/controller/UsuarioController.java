package controller;

import dao.Conexao;
import dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Usuario;
import view.CadastroUsuarioView;
import view.UsuarioPanel;

public class UsuarioController {

    private UsuarioPanel view;
    private CadastroUsuarioView viewCadastro;

    public UsuarioController(UsuarioPanel view, CadastroUsuarioView viewCadastro) {
        this.view = view;
        this.viewCadastro = viewCadastro;
    }

    //Leitura da tabela
    public void readTabelaUsuario() throws SQLException {

        DefaultTableModel modelo = (DefaultTableModel) view.getTabelaUsuario().getModel(); //Pega o modelo da tabela 
        modelo.setNumRows(0);
        view.getTabelaUsuario().setRowSorter(new TableRowSorter(modelo)); //Classifica as linha da tabela 

        //Realiza a conexao
        Connection conexao = new Conexao().getConnection();
        UsuarioDAO usuarioDao = new UsuarioDAO(conexao);

        //Chama a função de leitura de usuario e adiciona nas linhas e colunas
        for (Usuario usuario : usuarioDao.readUsuario()) {

            modelo.addRow(new Object[]{
                usuario.getId(),
                usuario.getNome(),
                usuario.getCpf(),
                usuario.getTelefone(),
                usuario.isAdmin()
            });
        }
    }

    public void removerUsuario() throws SQLException {

        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente deletar o usuario " + view.getCampoPesquisaNome().getText(), "Alerta", JOptionPane.YES_NO_OPTION);

        if (resposta == JOptionPane.YES_OPTION) {
            int id = Integer.parseInt(view.getCampoPesquisaId().getText());

            Usuario usuarioParaRemover = new Usuario(id);

            //Realiza a conexao
            Connection conexao = new Conexao().getConnection();
            UsuarioDAO usuarioDao = new UsuarioDAO(conexao);

            usuarioDao.delete(usuarioParaRemover); // Chama função para deletar usuário
            readTabelaUsuario(); // Releitura da tabela
            apagarCampos(); // Apaga os campos de pesquisa
            JOptionPane.showMessageDialog(null, "Usuario foi removido com sucesso!", "sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void apagarCampos() {

        view.getCampoPesquisaId().setText("");
        view.getCampoPesquisaNome().setText("");
        view.getCampoPesquisaCPF().setText("");
        view.getCampoPesquisaTelefone().setText("");
        view.getCheckAdmin().setSelected(false);
    }

    public void buscarUsuario(String id, String nome, String cpf, String telefone, boolean adm ) throws SQLException {

        DefaultTableModel modelo = (DefaultTableModel) view.getTabelaUsuario().getModel(); //Pega o modelo da tabela 
        modelo.setNumRows(0);
        view.getTabelaUsuario().setRowSorter(new TableRowSorter(modelo)); //Classifica as linha da tabela 

        //Realiza a conexao
        Connection conexao = new Conexao().getConnection();
        UsuarioDAO usuarioDao = new UsuarioDAO(conexao);

        //Chama a função de leitura de usuario e adiciona nas linhas e colunas
        for (Usuario usuario : usuarioDao.buscarUsuario(id, nome, cpf, telefone, adm)) {

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
