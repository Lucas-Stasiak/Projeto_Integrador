
package controller;

import dao.ClienteDAO;
import dao.Conexao;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Cliente;
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

    public void apagarTodosCampos(){
        apagarCamposCadastroEndereco();
        apagarCamposInformacaoCliente();
    }
    
    //Apagar campos de informacoes do endereço
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
    
    //Apagar campos de informacoes do cliente
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

    //Função para habilitar e desabilitar os campos de endereco
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
    
    //Preenche o Combo Box de bairro
    @SuppressWarnings("unchecked")
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
    
    //Preenche o Combo Box de logradouro
    @SuppressWarnings("unchecked")
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
    
    //Pega o que está escrito nos campos de endereco
    public Endereco enderecoDosCamposPreenchidos() throws SQLException{
        
        String cep = cadastroView.getCampoCep().getText();
        String logradouro = (String) cadastroView.getComboBoxLogradouro().getSelectedItem();
        String cidade = (String) cadastroView.getComboBoxCidade().getSelectedItem();
        String uf = (String) cadastroView.getComboBoxUF().getSelectedItem();
        String bairro = (String) cadastroView.getComboBoxBairro().getSelectedItem();
        String numero = cadastroView.getCampoNumero().getText();
        String complemento = cadastroView.getCampoComplemento().getText();

        Endereco endereco = new Endereco(logradouro, bairro, cidade, cep, numero, uf, complemento);
        return endereco;
        
    }
    
    //Função para realização do cadastro do cliente, ela recebe um true or false do Radio Button para habilitar ou não o endereço
    public void realizarCadastro(boolean ativado) throws SQLException{
        int id_endereco = -1;//id do endereço é setado como -1
        
        //Se os campos de endereço estejam ativados ele entra
        if(ativado){
            id_endereco = realizarCadastroEndereco(); //Insere o endereço no banco de dados e pega o seu id
            
            //Caso o id do endereço for maior do que 0 significa que o cadastro pode continuar
            if(id_endereco>=0){
                realizarCadastroClienteComEndereco(id_endereco);//Função para o cadastro do cliente com endereço, é necessário enviar o id_endereco
                JOptionPane.showMessageDialog(null, "Cliente foi cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                cadastroView.dispose();//Fecha a tela de cadastro
            }
        }
        //Caso o Radio Button esteja desativo o cadastro será realizado sem endereço
        else{
            realizarCadastroClienteSemEndereco();//Função para cadastro sem endereço
            JOptionPane.showMessageDialog(null, "Cliente foi cadastrado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            cadastroView.dispose();//Fecha a tela de cadastro
        }
    }
    
    //Função para cadastro do cliente com endereço
    public void realizarCadastroClienteComEndereco(int id_endereco) throws SQLException {
        Cliente clienteParaCadastro = informacaoDosCamposPessoais(); //Pega os campos pessoais preenchidos
        
        //Realiza a conexão
        Connection conexao = new Conexao().getConnection();
        ClienteDAO clienteDao = new ClienteDAO(conexao);
        
        clienteDao.insertComEndereco(clienteParaCadastro, id_endereco);//Função para inserir usuario com endereço
    }
    
    //Função para cadastro do cliente sem endereço
    public void realizarCadastroClienteSemEndereco() throws SQLException{
        Cliente clienteParaCadastro = informacaoDosCamposPessoais();//Pega os campos pessoais preenchidos
        
        //Realiza a conexão
        Connection conexao = new Conexao().getConnection();
        ClienteDAO clienteDao = new ClienteDAO(conexao);
        
        clienteDao.insert(clienteParaCadastro);//Função para inserir usuario sem endereço
    }
    
    //Função para pegar as informações dos campos pessoais
    public Cliente informacaoDosCamposPessoais(){
        String nome, cpf, rg, telefone, observacao;
        
        nome = cadastroView.getCampoNomeCliente().getText();
        cpf = cadastroView.getCampoCpfCliente().getText();
        rg = cadastroView.getCampoRgCliente().getText();
        telefone = cadastroView.getCampoTelefoneCliente().getText();
        observacao = cadastroView.getCampoObservacaoCliente().getText();
       
        
        Cliente clienteComDados = new Cliente(nome, cpf, rg, telefone, observacao);
        
        return clienteComDados;  
    }
    
    //Função para realizar o cadastro do endereço -- retorna o id_endereço
    public int realizarCadastroEndereco() throws SQLException{
        int id_endereco;
        Endereco endereco = enderecoDosCamposPreenchidos();//Pega as informações dos campos de endereçp
        id_endereco = cadastroEndereco(endereco, campoNuloEndereco());//Chama a função para cadastrar o endereço e armazena o id retornado
        
        return id_endereco;
    }
    
    //Função para preenchar o CEP com informações do endereçp
    public void preencherCEP() throws SQLException{
        
        Endereco endereco = buscarCEP(enderecoDosCamposPreenchidos());//Chama a função para buscar o CEP pelas informções do endereço
        cadastroView.getCampoCep().setText(endereco.getCep());
        cadastroView.getComboBoxBairro().setSelectedItem(endereco.getBairro());
        cadastroView.getCampoNumero().setText(endereco.getNumero());
    }
    
    //Verifica se tem campo nulo em endereço -- retorna true or false
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