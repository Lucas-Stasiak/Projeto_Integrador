
package controller;

import dao.Conexao;
import dao.UsuarioDAO;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import model.Usuario;
import view.LoginView;
import view.FuncionarioView;
import view.AdmView;


public class LoginController {
    private LoginView view;

    public LoginController(LoginView view) {
            this.view = view;
    }
    
    public void autenticarUsuario() throws SQLException {
        
            boolean existe;
            boolean admin;
    
            //Pegar textos dos campos de login
            String cpf = view.getCampoCPFLogin().getText();
            char[] senhaChar = view.getCampoSenhaLogin().getPassword();
            
            //Transformar senhaChar em string e criar uma váriavel Usuario
            String senha = new String(senhaChar);
            Usuario usuarioAutenticar = new Usuario(cpf, senha);
            
            //Realizar a conexao e envia-la para variável UsuarioDao
            Connection conexao = new Conexao().getConnection();
            UsuarioDAO UsuarioDao = new UsuarioDAO(conexao);
            
            //Verifica se usuario existe por CPF e Senha
            existe = UsuarioDao.verificaLoginPorCPFeSenha (usuarioAutenticar);
            
            //Se existe um usuario havera outra verificação;
            if(existe){
                //Verifica se usuario é admin
                admin = UsuarioDao.verificaAdmin (usuarioAutenticar);
                
                //Aparece a tela com base no true or false da váriavel admin
                JFrame telaMenu = admin ? new AdmView() : new FuncionarioView();
                telaMenu.setVisible(true);//torna a tela do menu visivel
                view.dispose();//fecha tela login
            }
            
            //Caso não existe é inserida uma mensagem no console
            else{
                JOptionPane.showMessageDialog(null, "Usuário ou senha incorretos.", "Erro de login", JOptionPane.ERROR_MESSAGE);
            }
    }
}