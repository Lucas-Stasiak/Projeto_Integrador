
package controller;

import java.awt.Color;
import view.CadastroClienteView;
import view.ClientePane;


public class ClienteController {
    
    private CadastroClienteView cadastroView;
    private ClientePane view;

    public ClienteController(CadastroClienteView cadastroView, ClientePane view) {
        this.cadastroView = cadastroView;
        this.view = view;
    }

    public void apagarCamposCadastroEndereco(){
       cadastroView.getComboBoxBairro().setSelectedIndex(-1);
       cadastroView.getComboBoxCidade().setSelectedIndex(-1);
       cadastroView.getComboBoxEstado().setSelectedIndex(-1);
       cadastroView.getComboBoxUF().setSelectedIndex(-1);
       cadastroView.getComboBoxLogradouro().setSelectedIndex(-1); 
       cadastroView.getCampoCep().setText("");
       cadastroView.getCampoComplemento().setText("");
       cadastroView.getCampoNumero().setText("");
        
    }
    
    public void apagarCamposInformacaoCliente(){
        cadastroView.getCampoNomeCliente().setText("");
        cadastroView.getCampoCpfCliente().setText("");
        cadastroView.getCampoRgCliente().setText("");
        cadastroView.getCampoTelefoneCliente().setText("");
        cadastroView.getCampoObservacaoCliente().setText("");
    }
    
    public void habilitarEndereco(){
        cadastroView.getCampoCep().setEnabled(true);
        cadastroView.getCampoNumero().setEnabled(true);
        cadastroView.getCampoComplemento().setEnabled(true);
        cadastroView.getBotaoAtualizarCEP().setEnabled(true);
        cadastroView.getComboBoxEstado().setEnabled(true);
        cadastroView.getComboBoxUF().setEnabled(true);
        cadastroView.getComboBoxCidade().setEnabled(true);
        cadastroView.getComboBoxBairro().setEnabled(true);
        cadastroView.getComboBoxLogradouro().setEnabled(true);
        cadastroView.getBotaoApagarCamposEndereco().setEnabled(true);
    }
    
    public void desabilitarEndereco(){
        cadastroView.getCampoCep().setEnabled(false);
        cadastroView.getCampoNumero().setEnabled(false);
        cadastroView.getCampoComplemento().setEnabled(false);
        cadastroView.getBotaoAtualizarCEP().setEnabled(false);
        cadastroView.getComboBoxEstado().setEnabled(false);
        cadastroView.getComboBoxUF().setEnabled(false);
        cadastroView.getComboBoxCidade().setEnabled(false);
        cadastroView.getComboBoxBairro().setEnabled(false);
        cadastroView.getComboBoxLogradouro().setEnabled(false);
        cadastroView.getBotaoApagarCamposEndereco().setEnabled(false);
    }

    public void enderecoHabilitado(){
        if (cadastroView.getBotaoRadioEndereco().isSelected()){
            habilitarEndereco();
            apagarCamposCadastroEndereco();
        }
        else{
            desabilitarEndereco();
        }
    }
    
    
}
