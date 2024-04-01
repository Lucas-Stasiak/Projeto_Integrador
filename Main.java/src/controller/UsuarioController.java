
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
    public void readTabelaUsuario() throws SQLException{
        
        
        DefaultTableModel modelo = (DefaultTableModel) view.getTabelaUsuario().getModel(); //Pega o modelo da tabela 
        modelo.setNumRows(0);
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
    
    
    //Remover usuario selecionado na tabela
    public void removerUsuario() throws SQLException{
        
        
        int resposta = JOptionPane.showConfirmDialog(null, "Deseja realmente deletar o usuario " + view.getCampoPesquisaNome().getText(), "Alerta", JOptionPane.YES_NO_OPTION);
        
        if(resposta == JOptionPane.YES_OPTION){
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
    
    //Apaga campos de pesquisa do Usuario Pane
    public void apagarCampos(){
        
        view.getCampoPesquisaId().setText("");
        view.getCampoPesquisaNome().setText("");
        view.getCampoPesquisaCPF().setText("");
        view.getCampoPesquisaTelefone().setText("");
        view.getCheckAdmin().setSelected(false);             
        }    
    
    
    //Cadastro de usuario
    public void cadastrarUsuario() throws SQLException {

        boolean campoEmBranco;
        boolean existe;
        boolean senhaCorreta;

        String nome = viewCadastro.getCampoTextoNome().getText();
        String cpf = viewCadastro.getCampoTextoCpf().getText();
        char[] senhaChar = viewCadastro.getCampoTextoSenhaUsuario().getPassword();
        char[] senhaCharConfirma = viewCadastro.getCampoTextoConfirmaSenhaUsuario().getPassword();
        String telefone = viewCadastro.getCampoTextoTelefone().getText();
        boolean admin = viewCadastro.getCheckAdmin().isSelected();

        //Transforma a senha character em string
        String senha = new String(senhaChar);
        String senhaConfirma = new String(senhaCharConfirma);

        Usuario usuarioCadastrar = new Usuario(nome, senha, cpf, telefone, admin);//Cria variável usuário

        //Peaga a conexao e envia para usuarioDao
        Connection conexao = new Conexao().getConnection();
        UsuarioDAO usuarioDao = new UsuarioDAO(conexao);

        campoEmBranco = verificaCampoPreenchido(nome, cpf, senha, senhaConfirma, telefone); //verifica se os campos estão preenchidos
        existe = usuarioDao.verificaExistencia(usuarioCadastrar); //Verifica a existencia do usuario
        senhaCorreta = comparacaoStrings(senha, senhaConfirma); //Verifica se a senha esta compativel nos dois bancos

        // se o campo  não esta em branco
        if (!campoEmBranco) {
            //Se não existe o usuário 
            if (!existe) {
                //Verifica se as senhas estão iguais
                if (senhaCorreta) {
                    usuarioDao.insert(usuarioCadastrar);
                    JOptionPane.showMessageDialog(null, "Usuário foi cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                    viewCadastro.dispose();//fecha a tela de cadastro
                } //Se as senhas não estiverem iguais informar o usuário
                else {
                    JOptionPane.showMessageDialog(null, "Senha está incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                }
            } //Caso o usuário já existe ele informa ao usuário
            else {
                int resposta = JOptionPane.showConfirmDialog(null, "Usuario ja existe! Deseja atualizar o usuario?", "Alerta", JOptionPane.YES_NO_OPTION);

                //Caso o usuario aperte o botão yes, ele fará o update
                if (resposta == JOptionPane.YES_OPTION) {
                    if (senhaCorreta) {
                        usuarioDao.update(usuarioCadastrar);
                        JOptionPane.showMessageDialog(null, "Usuário foi atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                        viewCadastro.dispose();//fecha a tela de cadastro
                    } else {
                        JOptionPane.showMessageDialog(null, "Senha está incorreta!", "Erro", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }   else {
            JOptionPane.showMessageDialog(null, "Campo(s) em branco!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Comparar strings e ver se são iguais
    public boolean comparacaoStrings(String string, String string2) {
        if (string.intern() == string2.intern()) {
            return true;
        } else {
            return false;
        }
    }

    // verifica se a algum campo em branco antes de cadastrar o usuario
    public boolean verificaCampoPreenchido(String nome, String cpf, String senha, String senhaConfirma, String telefone) {
        if (nome.isEmpty()) {
            return true;
        }
        if (cpf.isEmpty()) {
            return true;
        }
        if (senha.isEmpty()) {
            return true;
        }
        if (senhaConfirma.isEmpty()) {
            return true;
        }
        if (telefone.isEmpty()) {
            return true;
        }
        return false;
    }
    
    }
