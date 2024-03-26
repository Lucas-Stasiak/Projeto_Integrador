/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.Conexao;
import dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JFrame;
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
                JFrame tela = admin ? new AdmView() : new FuncionarioView();
                tela.setVisible(true);
            }
            
            //Caso não existe é inserida uma mensagem no console
            else{
                System.out.println("Usuario ou senha incorreto");
            }
    }
}