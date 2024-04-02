package controller;

import dao.ClienteDAO;
import dao.Conexao;
import dao.UsuarioDAO;
import java.sql.Connection;
import model.Endereco;
import view.CadastroClienteView;
import java.sql.SQLException;
import javax.swing.JTextField;

public class ClienteEnderecoController {
    private CadastroClienteView view;

    public ClienteEnderecoController(CadastroClienteView view) {
        this.view = view;
    }

    public CadastroClienteView getView() {
        return view;
    }

    public void setView(CadastroClienteView view) {
        this.view = view;
    }
    
    public void buscarEndereco(String cep) throws SQLException{  
        // Verifica se o CEP tem mais de 8 dígitos
        if (cep.length() > 8) {
            Connection conexao = new Conexao().getConnection();
            ClienteDAO clienteDAO = new ClienteDAO(conexao);
            try {
                // Chama o método para buscar o endereço correspondente ao CEP
                Endereco endereco = clienteDAO.buscarEnderecoPorCEP(cep);

                // Faça o que for necessário com o objeto Endereco (por exemplo, preencher campos na interface gráfica)
                System.out.println(endereco);
                preencherCamposEndereco(endereco);
            } catch (SQLException ex) {
                ex.printStackTrace();
                // Tratar exceção
            }
        }
    }
    
    private void preencherCamposEndereco(Endereco endereco) {
    if (endereco != null) {
        // Define os valores nos campos JTextField com os dados do endereço
        view.getCampoBairro().setText(endereco.getBairro());
        view.getCampoCidade().setText(endereco.getCidade());
        view.getCampoEstado().setText(endereco.getEstado());
        view.getCampoLogradouro().setText(endereco.getLogradouro());
    } else {
        // Se o endereço não for encontrado, limpe os campos
        view.getCampoBairro().setText("");
        view.getCampoCidade().setText("");
        view.getCampoEstado().setText("");
        view.getCampoLogradouro().setText("");
    }
}

    
}
