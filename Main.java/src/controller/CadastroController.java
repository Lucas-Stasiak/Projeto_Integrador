
package controller;

import dao.Conexao;
import dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import model.Usuario;
import view.CadastroUsuarioView;


public class CadastroController {
    private CadastroUsuarioView view;

    public CadastroController(CadastroUsuarioView view) {
        this.view = view;
    }

    public void cadastrarUsuario() throws SQLException {
        
        boolean existe;
        boolean senhaCorreta;
        
        String nome = view.getCampoTextoNome().getText();
        String cpf = view.getCampoTextoCpf().getText();
        char[] senhaChar = view.getCampoTextoSenhaUsuario().getPassword();
        char[] senhaCharConfirma = view.getCampoTextoConfirmaSenhaUsuario().getPassword();
        String telefone = view.getCampoTextoTelefone().getText();
        boolean admin = view.getCheckAdmin().isSelected();
        
        //Transforma a senha character em string
        String senha = new String(senhaChar);
        String senhaConfirma = new String(senhaCharConfirma);
        
        Usuario usuarioCadastrar = new Usuario(nome, senha, cpf, telefone, admin);//Cria variável usuário
        
        //Peaga a conexao e envia para usuarioDao
        Connection conexao = new Conexao().getConnection();
        UsuarioDAO usuarioDao = new UsuarioDAO(conexao);
        
        
        existe = usuarioDao.verificaExistencia(usuarioCadastrar); //Verifica a existencia do usuario
        senhaCorreta = comparacaoStrings(senha, senhaConfirma); //Verifica se a senha esta compativel nos dois bancos
        
        //Se não existe o usuário 
        if(!existe){
            //Verifica se as senhas estão iguais
            if(senhaCorreta){
                usuarioDao.insert(usuarioCadastrar);
                JOptionPane.showMessageDialog(null, "Usuário foi cadastrado com sucesso!", "Sucesso",JOptionPane.INFORMATION_MESSAGE);
            }
            //Se as senhas não estiverem iguais informar o usuário
            else{
                JOptionPane.showMessageDialog(null, "Senha está incorreta!", "Erro",JOptionPane.ERROR_MESSAGE);
            }
        }
        //Caso o usuário não existe ele informa o usuário
        else{
            int resposta = JOptionPane.showConfirmDialog(null, "Usuario ja existe! Deseja atualizar o usuario?", "Alerta",JOptionPane.YES_NO_OPTION);
            
            //Caso o usuario aperte o botão yes, ele fará o update
            if(resposta==JOptionPane.YES_OPTION){
                    if(senhaCorreta){
                        //usuarioDao.update(usuarioCadastrar);
                        JOptionPane.showMessageDialog(null, "Usuário foi cadastrado com sucesso!", "Sucesso",JOptionPane.INFORMATION_MESSAGE);
                    }
                    else{
                        JOptionPane.showMessageDialog(null, "Senha está incorreta!", "Erro",JOptionPane.ERROR_MESSAGE);
                    }
            }
        }    
    }
    
    
    //Comparar strings e ver se são iguais
    public boolean comparacaoStrings(String string, String string2){
        if(string.intern()==string2.intern()){
            return true;
        }else{
            return false;
        }
    }
    
}

