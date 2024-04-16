
package controller;

import dao.Conexao;
import dao.UsuarioDAO;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.Endereco;
import model.Usuario;
import org.jdesktop.swingx.sort.SortUtils;
import view.AtualizarUsuarioView;
import view.CadastroUsuarioView;
import view.UsuarioPanel;


public class UsuarioController extends EnderecoController{

    private UsuarioPanel view;
    private CadastroUsuarioView viewCadastro;
    private AtualizarUsuarioView viewAtualizar;
    private boolean pesquisaAdm;

    //Construtor
    public UsuarioController(UsuarioPanel view, CadastroUsuarioView viewCadastro, AtualizarUsuarioView viewAtualizar) {
        this.view = view;
        this.viewCadastro = viewCadastro;
        this.viewAtualizar = viewAtualizar;
    }

    //Remove o usuário selecionado na tabela
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
    
    //Função para habilitar e desabilitar os campos de endereco
     public void enderecoHabilitado(){
        if (viewCadastro.getBotaoRadioEndereco().isSelected()){
            habilitarEndereco();
            apagarCamposCadastroEndereco();
        }
        else{
            desabilitarEndereco();
        }
    }   
     
    public void habilitarEndereco(){
        viewCadastro.getCampoCadastroCEP().setEnabled(true);
        viewCadastro.getCampoCadastroNumero().setEnabled(true);
        viewCadastro.getCampoCadastroComplemento().setEnabled(true);
        viewCadastro.getBotaoAtualizarCEP().setEnabled(true);
        viewCadastro.getComboBoxEstado().setEnabled(true);
        viewCadastro.getComboBoxUF().setEnabled(true);
        viewCadastro.getComboBoxCidade().setEnabled(true);
        viewCadastro.getComboBoxBairro().setEnabled(true);
        viewCadastro.getComboBoxLogradouro().setEnabled(true);
        viewCadastro.getBotaoApagarCamposCadastroEndereco().setEnabled(true);
    }
    
    public void desabilitarEndereco(){
        viewCadastro.getCampoCadastroCEP().setEnabled(false);
        viewCadastro.getCampoCadastroNumero().setEnabled(false);
        viewCadastro.getCampoCadastroComplemento().setEnabled(false);
        viewCadastro.getBotaoAtualizarCEP().setEnabled(false);
        viewCadastro.getComboBoxEstado().setEnabled(false);
        viewCadastro.getComboBoxUF().setEnabled(false);
        viewCadastro.getComboBoxCidade().setEnabled(false);
        viewCadastro.getComboBoxBairro().setEnabled(false);
        viewCadastro.getComboBoxLogradouro().setEnabled(false);
        viewCadastro.getBotaoApagarCamposCadastroEndereco().setEnabled(false);
    }
    
    //Apagar campos de informacoes do endereço
    public void apagarCamposCadastroEndereco(){
       viewCadastro.getComboBoxBairro().setSelectedIndex(-1);
       viewCadastro.getComboBoxCidade().setSelectedIndex(-1);
       viewCadastro.getComboBoxEstado().setSelectedIndex(-1);
       viewCadastro.getComboBoxUF().setSelectedIndex(-1);
       viewCadastro.getComboBoxLogradouro().setSelectedIndex(-1); 
       viewCadastro.getCampoCadastroCEP().setText("");
       viewCadastro.getCampoCadastroComplemento().setText("");
       viewCadastro.getCampoCadastroNumero().setText("");
    }
     
    //Apaga os campos de pesquisa
    public void apagarCampos() {
        
        view.getCampoPesquisaId().setText("");
        view.getCampoPesquisaNome().setText("");
        view.getCampoPesquisaCPF().setText("");
        view.getComboBoxPesquisa().setSelectedIndex(0);
    }
    
    public void apagarCamposCadastro(){
        apagarCamposCadastroDadosIdentificacao();
        apagarCamposCadastroEndereco();
    }
    
    //Preenche o comboBox com base no tipo de usuario selecionado
    public int comboBoxPreenchimento(){
        if((boolean)view.getTabelaUsuario().getValueAt(view.getTabelaUsuario().getSelectedRow(), 4)){
            return 2;
        }
        else{
            return 1;
        }
    }
    
    //Apaga os campos do cadastro
    public void apagarCamposCadastroDadosIdentificacao(){
        
        viewCadastro.getCampoTextoNome().setText("");
        viewCadastro.getCampoTextoCpf().setText("");
        viewCadastro.getCampoTextoTelefone().setText("");
        viewCadastro.getCampoTextoSenhaUsuario().setText("");
        viewCadastro.getCampoTextoConfirmaSenhaUsuario().setText("");
        viewCadastro.getCampoCadastroObservacaoUsuario().setText("");
        viewCadastro.getCheckAdmin().setSelected(false);
    }
    
    @SuppressWarnings("unchecked") //Preenche os comboboxs dos estados e UF
    public void comboBoxEstados() throws SQLException{
        ArrayList<Endereco> estadosParaCB = estados();

        
        for(Endereco estado : estadosParaCB){
            viewCadastro.getComboBoxEstado().addItem(estado.getEstado());
            viewCadastro.getComboBoxUF().addItem(estado.getSigla());
         }  
        viewCadastro.getComboBoxEstado().setSelectedIndex(-1);//Não seleciona nenhuma linha
        viewCadastro.getComboBoxUF().setSelectedIndex(-1);//Não seleciona nenhuma linha
    }
    
    
    public void atualizaComboBoxEstado(){
        //Seleciona o combo box de estado e uf respectivamente ao seu estado ou uf selecionado
        viewCadastro.getComboBoxEstado().setSelectedIndex(viewCadastro.getEstadoSelecionado());
        viewCadastro.getComboBoxUF().setSelectedIndex(viewCadastro.getEstadoSelecionado());
        
        try {
            comboBoxCidades();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @SuppressWarnings("unchecked")
    public void comboBoxCidades() throws SQLException{
        
        String sigla = (String) viewCadastro.getComboBoxUF().getSelectedItem();
        ArrayList<String> cidadesParaCB = cidades(sigla);
        
        viewCadastro.getComboBoxCidade().removeAllItems();//remove todos os itens do Combo Box
        for(String cidade : cidadesParaCB){
            viewCadastro.getComboBoxCidade().addItem(cidade);
        }
        viewCadastro.getComboBoxCidade().setSelectedIndex(-1);
    }
    
    
    //Preenche o Combo Box de bairro
    @SuppressWarnings("unchecked")
    public void comboBoxBairros() throws SQLException{
        
        String nome_cidade = (String) viewCadastro.getComboBoxCidade().getSelectedItem(); //Pega o nome da cidade selecionada
        String uf = (String) viewCadastro.getComboBoxUF().getSelectedItem();
        
        ArrayList<String> bairrosParaCB = bairros(nome_cidade, uf);
        
        viewCadastro.getComboBoxBairro().removeAllItems();
        for(String bairro : bairrosParaCB){
            viewCadastro.getComboBoxBairro().addItem(bairro);
        }
        viewCadastro.getComboBoxBairro().setSelectedIndex(-1);
        
    }
    
    //Preenche o Combo Box de logradouro
    @SuppressWarnings("unchecked")
    public void comboBoxLogradouros() throws SQLException{
        String bairro = (String) viewCadastro.getComboBoxBairro().getSelectedItem();
        String sigla = (String) viewCadastro.getComboBoxUF().getSelectedItem();
        String cidade = (String) viewCadastro.getComboBoxCidade().getSelectedItem();
        
        ArrayList<String> logradourosParaCB = logradouro(bairro, sigla, cidade);
        
        viewCadastro.getComboBoxLogradouro().removeAllItems();
        for(String logradouro : logradourosParaCB){
            viewCadastro.getComboBoxLogradouro().addItem(logradouro);
        }
        viewCadastro.getComboBoxLogradouro().setSelectedIndex(-1);
    }
    
    //Preenche tudo com o endereco do cep
    public void preencherCamposEndereco(String cep) throws SQLException {
        Endereco endereco = buscarEndereco(cep);
        
        if (endereco != null) {
            // Define os valores nos campos JTextField com os dados do endereço
            viewCadastro.getComboBoxUF().setSelectedItem(endereco.getSigla());
            viewCadastro.getComboBoxEstado().setSelectedIndex(viewCadastro.getComboBoxUF().getSelectedIndex());
            viewCadastro.getComboBoxCidade().setSelectedItem(endereco.getCidade());
            viewCadastro.getComboBoxBairro().setSelectedItem(endereco.getBairro());
            viewCadastro.getComboBoxLogradouro().setSelectedItem(endereco.getLogradouro());
            viewCadastro.getCampoCadastroNumero().setText(endereco.getNumero());
        } else {
            // Se o endereço não for encontrado, limpe os campos
            viewCadastro.getComboBoxBairro().setSelectedIndex(-1);
            viewCadastro.getComboBoxCidade().setSelectedIndex(-1);
            viewCadastro.getComboBoxUF().setSelectedIndex(-1);
            viewCadastro.getComboBoxEstado().setSelectedIndex(-1);
            viewCadastro.getComboBoxLogradouro().setSelectedIndex(-1);
            viewCadastro.getCampoCadastroNumero().setText("");
        }
    }
    
    //Pega o que está escrito nos campos de endereco
    public Endereco enderecoDosCamposPreenchidos() throws SQLException{
        
        String cep = viewCadastro.getCampoCadastroCEP().getText();
        String logradouro = (String) viewCadastro.getComboBoxLogradouro().getSelectedItem();
        String cidade = (String) viewCadastro.getComboBoxCidade().getSelectedItem();
        String uf = (String) viewCadastro.getComboBoxUF().getSelectedItem();
        String bairro = (String) viewCadastro.getComboBoxBairro().getSelectedItem();
        String numero = viewCadastro.getCampoCadastroNumero().getText();
        String complemento = viewCadastro.getCampoCadastroComplemento().getText();

        Endereco endereco = new Endereco(logradouro, bairro, cidade, cep, numero, uf, complemento);
        return endereco;
        
    }
    
    //Função para preenchar o CEP com informações do endereço
    public void preencherCEP() throws SQLException{
        
        Endereco endereco = buscarCEP(enderecoDosCamposPreenchidos());//Chama a função para buscar o CEP pelas informções do endereço
        viewCadastro.getCampoCadastroCEP().setText(endereco.getCep());
        viewCadastro.getComboBoxBairro().setSelectedItem(endereco.getBairro());
        viewCadastro.getCampoCadastroNumero().setText(endereco.getNumero());
    }
    
    //Leitura da tabela
    @SuppressWarnings("unchecked")
    public void readTabelaUsuario() throws SQLException {

        DefaultTableModel modelo = (DefaultTableModel) view.getTabelaUsuario().getModel(); //Pega o modelo da tabela 
        modelo.setNumRows(0); //Seta o numero de linhas como 0, isso evita a tabela repetir informções quando atualizada
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
    
    //Verifica o tipo de usuario selecionado no combobox
    public void comboBoxAdmin(){
        if(view.getComboBoxPesquisa().getSelectedIndex()!=0){
            if(view.getComboBoxPesquisa().getSelectedIndex()==1){
                pesquisaAdm = false;
            }
            if(view.getComboBoxPesquisa().getSelectedIndex()==2){
                pesquisaAdm = true;
            }
        }
    }

    //Função para busca de usuário
    @SuppressWarnings("unchecked")
    public void buscarUsuario() throws SQLException {
        
        view.getCampoPesquisaId().setText("");//Seta o campo de id como vazio
        
        DefaultTableModel modelo = (DefaultTableModel) view.getTabelaUsuario().getModel(); //Pega o modelo da tabela 
         modelo.setNumRows(0);
        view.getTabelaUsuario().setRowSorter(new TableRowSorter(modelo)); //Classifica as linha da tabela 

        //Realiza a conexao
        Connection conexao = new Conexao().getConnection();
        UsuarioDAO usuarioDao = new UsuarioDAO(conexao);
        
        Usuario usuarioPesquisa = new Usuario();
        
        usuarioPesquisa.setNome(view.getCampoPesquisaNome().getText());
        usuarioPesquisa.setCpf(view.getCampoPesquisaCPF().getText());
        
        //Se não for selecionado o tipo de usuario para pesquisa é executado o if
        if(view.getComboBoxPesquisa().getSelectedIndex()==0){
        
            //Chama a função de leitura de usuario e adiciona nas linhas e colunas
            for (Usuario usuario : usuarioDao.buscarUsuarioNOMEeCPF(usuarioPesquisa)) {

                modelo.addRow(new Object[]{
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getCpf(),
                    usuario.getTelefone(),
                    usuario.isAdmin()
                });
            }
        }
        //Caso contrário é realizado outro tipo de pesquisa, pelo nome, cpf e adm
        else{
            comboBoxAdmin();//Verifica se é admin ou não
            
            usuarioPesquisa.setAdmin(pesquisaAdm);
            
            for(Usuario usuario : usuarioDao.buscarUsuarioNOMEeCPFeADM(usuarioPesquisa)){
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
    
    //Função para realizar o cadastro do endereço -- retorna o id_endereço
    public int realizarCadastroEndereco() throws SQLException{
        int id_endereco;
        Endereco endereco = enderecoDosCamposPreenchidos();//Pega as informações dos campos de endereçp
        id_endereco = cadastroEndereco(endereco, campoNuloEndereco());//Chama a função para cadastrar o endereço e armazena o id retornado
        
        return id_endereco;
    }
    
    //Verifica se tem campo nulo em endereço -- retorna true or false
    public boolean campoNuloEndereco(){
        String cep = viewCadastro.getCampoCadastroCEP().getText();
        String numero = viewCadastro.getCampoCadastroNumero().getText();
        String estado = (String) viewCadastro.getComboBoxEstado().getSelectedItem();
        String bairro = (String) viewCadastro.getComboBoxBairro().getSelectedItem();
        String logradouro = (String) viewCadastro.getComboBoxLogradouro().getSelectedItem();
        
        return cep.isEmpty() || numero.isEmpty() || estado.isEmpty() || bairro.isEmpty() || logradouro.isEmpty();//Caso algum campos está nulo ele retorna true    
    }
    
    //Função para realização do cadastro do cliente, ela recebe um true or false do Radio Button para habilitar ou não o endereço
    public void realizarCadastro(boolean ativado) throws SQLException{
        int id_endereco = -1;//id do endereço é setado como -1
        
        //Se os campos de endereço estejam ativados ele entra
        if(ativado){
            id_endereco = realizarCadastroEndereco(); //Insere o endereço no banco de dados e pega o seu id
            
            //Caso o id do endereço for maior do que 0 significa que o cadastro pode continuar
            if(id_endereco>=0){
                realizarCadastroUsuarioComEndereco(id_endereco);//Função para o cadastro do cliente com endereço, é necessário enviar o id_endereco
            }
        }
        //Caso o Radio Button esteja desativo o cadastro será realizado sem endereço
        else{
            realizarCadastroUsuarioSemEndereco();//Função para cadastro sem endereço
        }
    }
    

    public void realizarCadastroUsuarioComEndereco(int id_endereco) throws SQLException{
        Usuario usuarioCadastrar = informacaoDosCamposPessoais();//Pega os campos pessoais preenchidos
        boolean campoEmBranco, existe, senhaCorreta, cpfValido;
        
        char[] senhaCharConfirma = viewCadastro.getCampoTextoConfirmaSenhaUsuario().getPassword();//Pega o que foi escrito de senha confirma
        String senhaConfirma = new String(senhaCharConfirma);//Transforma a senha character em string
        
        //Realiza a conexão
        Connection conexao = new Conexao().getConnection();
        UsuarioDAO usuarioDao = new UsuarioDAO(conexao);
        
        campoEmBranco = verificaCampoPreenchido(); //verifica se os campos estão preenchidos
        existe = usuarioDao.verificaExistencia(usuarioCadastrar); //Verifica a existencia do usuario
        senhaCorreta = comparacaoStrings(usuarioCadastrar.getSenha(), senhaConfirma); //Verifica se a senha esta compativel nos dois bancos
        cpfValido = verificaCPFvalido(usuarioCadastrar.getCpf());// Verifica se o CPF é valido
        
        //Se os campos não estiverem de acordo com as validações ele entra e avisa o erro
        if(campoEmBranco || existe || !senhaCorreta || !cpfValido){
            avisosErro(campoEmBranco, existe, !senhaCorreta, !cpfValido);
        }
        //Caso contrário ele insere!
        else{
            usuarioDao.insertComEndereco(usuarioCadastrar, id_endereco);//Função para inserir usuario sem endereço
            JOptionPane.showMessageDialog(null, "Usuário cadastrado", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
            viewCadastro.dispose();//fecha a tela de cadastro
        }
    }
    
    //Cadastro de usuario
    public void realizarCadastroUsuarioSemEndereco() throws SQLException {

        boolean campoEmBranco, existe, senhaCorreta, cpfValido;

        char[] senhaCharConfirma = viewCadastro.getCampoTextoConfirmaSenhaUsuario().getPassword();//Pega o que foi escrito de senha confirma
        String senhaConfirma = new String(senhaCharConfirma);//Transforma a senha character em string
        
        Usuario usuarioCadastrar = informacaoDosCamposPessoais();//Cria variável usuário e armazena as informações colocadas nos campos de cadastro

        //Realiza a conexao e envia para usuarioDao
        Connection conexao = new Conexao().getConnection();
        UsuarioDAO usuarioDao = new UsuarioDAO(conexao);

        campoEmBranco = verificaCampoPreenchido(); //verifica se os campos estão preenchidos
        existe = usuarioDao.verificaExistencia(usuarioCadastrar); //Verifica a existencia do usuario
        senhaCorreta = comparacaoStrings(usuarioCadastrar.getSenha(), senhaConfirma); //Verifica se a senha esta compativel nos dois bancos
        cpfValido = verificaCPFvalido(usuarioCadastrar.getCpf());// Verifica se o CPF é valido

        //Se os campos não estiverem de acordo com as validações ele entra e avisa o erro
        if(campoEmBranco || existe || !senhaCorreta || !cpfValido){
            avisosErro(campoEmBranco, existe, !senhaCorreta, !cpfValido);
        }
        //Caso contrário ele insere!
        else{
            usuarioDao.insert(usuarioCadastrar);
            JOptionPane.showMessageDialog(null, "Usuário cadastrado", "Sucesso!", JOptionPane.INFORMATION_MESSAGE);
            viewCadastro.dispose();//fecha a tela de cadastro

        }
    }
    //Função para pegar as informações dos campos pessoais
    public Usuario informacaoDosCamposPessoais(){
        String nome, cpf, telefone, observacao,senha;
        char[] senhaChar;
        boolean admin;
        
        //Pega o que foi inserido nos campos e armazena em váriaveis
        nome = viewCadastro.getCampoTextoNome().getText();
        cpf = viewCadastro.getCampoTextoCpf().getText();
        telefone = viewCadastro.getCampoTextoTelefone().getText();
        observacao = viewCadastro.getCampoCadastroObservacaoUsuario().getText();
        senhaChar = viewCadastro.getCampoTextoSenhaUsuario().getPassword();
        admin = viewCadastro.getCheckAdmin().isSelected();
           
        //Transforma a senha character em string
        senha = new String(senhaChar);

        Usuario clienteComDados = new Usuario(nome, senha, cpf, telefone, admin, observacao);
        return clienteComDados;

    }

    //Comparar strings e ver se são iguais
    public boolean comparacaoStrings(String string, String string2) {
        return string.intern().equals(string2.intern());//Se são iguais retorna true
    }
    
    // verifica se a algum campo em branco antes de cadastrar o usuario
    public boolean verificaCampoPreenchido() {
        String nome = viewCadastro.getCampoTextoNome().getText();
        String cpf = viewCadastro.getCampoTextoCpf().getText();
        char[] senha = viewCadastro.getCampoTextoSenhaUsuario().getPassword();
        char[] senhaConfirma = viewCadastro.getCampoTextoConfirmaSenhaUsuario().getPassword();
        String telefone = viewCadastro.getCampoTextoTelefone().getText();
                
        return nome.isEmpty() || cpf.isEmpty() || senha.length==0 || senhaConfirma.length==0 || telefone.isEmpty(); //Se algum desses campos for vazio será retornado true
    }
    
    //Função para verificar o se o cpf é valido
    public boolean verificaCPFvalido(String cpf){
        return cpf.length() == 14; //Se o tamanho do cpf for 14 retorna true
    }

    //Função para mandar os avisos de erro de acordo com os erros
    public void avisosErro(boolean campoEmBranco, boolean usuarioExiste, boolean senhaIncorreta, boolean cpfInvalido){
    if(campoEmBranco){
        JOptionPane.showMessageDialog(null, "Campo(s) em branco!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
    else if(cpfInvalido){
        JOptionPane.showMessageDialog(null, "CPF invalido", "Erro", JOptionPane.ERROR_MESSAGE);
    }
    else if(usuarioExiste){
        JOptionPane.showMessageDialog(null, "Usuário já existe!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
    else if(senhaIncorreta){
        JOptionPane.showMessageDialog(null, "Campos de senha discrepantes!", "Erro", JOptionPane.ERROR_MESSAGE);
    }
    }
}