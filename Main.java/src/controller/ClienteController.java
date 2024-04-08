
package controller;

import view.CadastroClienteView;
import view.ClientePane;


public class ClienteController {
    
    private CadastroClienteView cadastroView;
    private ClientePane view;

    public ClienteController(CadastroClienteView cadastroView, ClientePane view) {
        this.cadastroView = cadastroView;
        this.view = view;
    }

    public void apagarCamposCadastro(){
       cadastroView.getComboBoxBairro().setSelectedIndex(-1);
       cadastroView.getComboBoxCidade().setSelectedIndex(-1);
       cadastroView.getComboBoxEstado().setSelectedIndex(-1);
       cadastroView.getComboBoxUF().setSelectedIndex(-1);
       cadastroView.getComboBoxLogradouro().setSelectedIndex(-1); 
        
    }
    
    
}
