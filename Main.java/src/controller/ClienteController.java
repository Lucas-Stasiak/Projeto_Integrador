
package controller;

import java.awt.Color;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Endereco;
import view.CadastroClienteView;
import view.ClientePane;


public class ClienteController extends EnderecoController{
    
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
    
    @SuppressWarnings("unchecked") //Preenche os comboboxs dos estados e UF
    public void comboBoxEstadados() throws SQLException{
        ArrayList<Endereco> estadosParaCB = estados();

        
        for(Endereco estado : estadosParaCB){
            cadastroView.getComboBoxEstado().addItem(estado.getEstado());
            cadastroView.getComboBoxUF().addItem(estado.getSigla());
         }  
        cadastroView.getComboBoxEstado().setSelectedIndex(-1);//Não seleciona nenhuma linha
        cadastroView.getComboBoxUF().setSelectedIndex(-1);//Não seleciona nenhuma linha
    }
    
    //Atualiza os combobox estado e chama a função para preencher o da cidade conforme o estado selecionado
     public void atualizaComboBoxEstado(){
        
        //Seleciona o combo box de estado e uf respectivamente ao seu estado ou uf selecionado
        cadastroView.getComboBoxEstado().setSelectedIndex(cadastroView.getEstadoSelecionado());
        cadastroView.getComboBoxUF().setSelectedIndex(cadastroView.getEstadoSelecionado());
        
        try {
            comboBoxCidades();//chama função para leitura das cidades
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     
    @SuppressWarnings("unchecked") //Preenche o combobox da cidade
    public void comboBoxCidades() throws SQLException{
        
        String sigla = (String) cadastroView.getComboBoxUF().getSelectedItem();
        ArrayList<String> cidadesParaCB = cidades(sigla);
        
        cadastroView.getComboBoxCidade().removeAllItems();//remove todos os itens do Combo Box
        for(String cidade : cidadesParaCB){
            cadastroView.getComboBoxCidade().addItem(cidade);
        }
        cadastroView.getComboBoxCidade().setSelectedIndex(-1);
    }
    
    public void comboBoxBairros() throws SQLException{
        
        String nome_cidade = (String) cadastroView.getComboBoxCidade().getSelectedItem(); //Pega o nome da cidade selecionada
        String uf = (String) cadastroView.getComboBoxUF().getSelectedItem();
        
        ArrayList<String> bairrosParaCB = bairros(nome_cidade, uf);
        
        cadastroView.getComboBoxBairro().removeAllItems();
        for(String bairro : bairrosParaCB){
            cadastroView.getComboBoxBairro().addItem(bairro);
        }
        cadastroView.getComboBoxBairro().setSelectedIndex(-1);
        
    }
    
    public void comboBoxLogradouros() throws SQLException{
        String bairro = (String) cadastroView.getComboBoxBairro().getSelectedItem();
        String sigla = (String) cadastroView.getComboBoxUF().getSelectedItem();
        String cidade = (String) cadastroView.getComboBoxCidade().getSelectedItem();
        
        ArrayList<String> logradourosParaCB = logradouro(bairro, sigla, cidade);
        
        cadastroView.getComboBoxLogradouro().removeAllItems();
        for(String logradouro : logradourosParaCB){
            cadastroView.getComboBoxLogradouro().addItem(logradouro);
        }
        cadastroView.getComboBoxLogradouro().setSelectedIndex(-1);
    }
    
     
    //Preenche tudo com o endereco do cep
    public void preencherCamposEndereco(String cep) throws SQLException {
        Endereco endereco = buscarEndereco(cep);
        
        if (endereco != null) {
            // Define os valores nos campos JTextField com os dados do endereço
            cadastroView.getComboBoxUF().setSelectedItem(endereco.getSigla());
            cadastroView.getComboBoxEstado().setSelectedIndex(cadastroView.getComboBoxUF().getSelectedIndex());
            cadastroView.getComboBoxCidade().setSelectedItem(endereco.getCidade());
            cadastroView.getComboBoxBairro().setSelectedItem(endereco.getBairro());
            cadastroView.getComboBoxLogradouro().setSelectedItem(endereco.getLogradouro());
            cadastroView.getCampoNumero().setText(endereco.getNumero());
        } else {
            // Se o endereço não for encontrado, limpe os campos
            cadastroView.getComboBoxBairro().setSelectedIndex(-1);
            cadastroView.getComboBoxCidade().setSelectedIndex(-1);
            cadastroView.getComboBoxUF().setSelectedIndex(-1);
            cadastroView.getComboBoxEstado().setSelectedIndex(-1);
            cadastroView.getComboBoxLogradouro().setSelectedIndex(-1);
            cadastroView.getCampoNumero().setText("");
        }
    }
    
    //Pega o que está escrito nos campos
    public Endereco enderecoDosCamposPreenchidos() throws SQLException{
        
        String cep = cadastroView.getCampoCep().getText();//Pega o que esta escrito no campo cep
        String logradouro = (String) cadastroView.getComboBoxLogradouro().getSelectedItem();
        String cidade = (String) cadastroView.getComboBoxCidade().getSelectedItem();
        String uf = (String) cadastroView.getComboBoxUF().getSelectedItem();
        String bairro = (String) cadastroView.getComboBoxBairro().getSelectedItem();
        String numero = cadastroView.getCampoNumero().getText();
        String complemento = cadastroView.getCampoComplemento().getText();

        Endereco endereco = new Endereco(logradouro, bairro, cidade, cep, numero, uf, complemento);
        return endereco;
        
    }
    
    public void realizarCadastroEndereco() throws SQLException{
        Endereco endereco = enderecoDosCamposPreenchidos();
        cadastroEndereco(enderecoDosCamposPreenchidos(), campoNuloEndereco());
    }
    
    public void preencherCEP() throws SQLException{
        
        Endereco endereco = buscarCEP(enderecoDosCamposPreenchidos());
        cadastroView.getCampoCep().setText(endereco.getCep());
        cadastroView.getComboBoxBairro().setSelectedItem(endereco.getBairro());
        cadastroView.getCampoNumero().setText(endereco.getNumero());
    }
    
    public boolean campoNuloEndereco(){
        String cep = cadastroView.getCampoCep().getText();
        String numero = cadastroView.getCampoNumero().getText();
        String estado = (String) cadastroView.getComboBoxEstado().getSelectedItem();
        String bairro = (String) cadastroView.getComboBoxBairro().getSelectedItem();
        String logradouro = (String) cadastroView.getComboBoxLogradouro().getSelectedItem();
        String complemento = cadastroView.getCampoComplemento().getText();
        
        return (cep.isEmpty() || numero.isEmpty() || estado.isEmpty() || bairro.isEmpty() || logradouro.isEmpty() || complemento.isEmpty());
        
    }   
     
    
    
    
}
