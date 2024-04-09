package controller;

import java.sql.Connection;
import dao.Conexao;
import dao.EnderecoDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import model.Endereco;
import view.CadastroClienteView;
import java.sql.SQLException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxEditor;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class EnderecoController {

    private CadastroClienteView view;

    public EnderecoController(CadastroClienteView view) {
        this.view = view;
    }

    public CadastroClienteView getView() {
        return view;
    }

    public void setView(CadastroClienteView view) {
        this.view = view;
    }

    public void buscarEndereco(String cep) throws SQLException {
        // Verifica se o CEP tem mais de 8 digitos
        if (cep.length() > 8) {
            try {
                // Faz a conex�o com ViaCEP
                String url = "https://viacep.com.br/ws/" + cep + "/json/";
                @SuppressWarnings("deprecation")
                HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
                // Pega os resultados
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    StringBuilder response = new StringBuilder();
                    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                    }

                    // Processar a resposta JSON manualmente
                    Endereco endereco = parseJsonToAddress(response.toString());
                    preencherCamposEndereco(endereco);
                } else {
                    System.out.println("Erro na requisicao: " + responseCode);
                }
            } catch (IOException e) {
            }
        }
    }

    private Endereco parseJsonToAddress(String json) {
        Endereco endereco = null;
        try {
            // Extrai os valores relevantes do JSON
            String[] lines = json.split(",");
            // Separa por linha(,)
            String logradouro = null;
            String bairro = null;
            String cidade = null;
            String uf = null;
            String cep = null;
            String numero = null;
            for (String line : lines) {
                if (line.contains("\"logradouro\"")) {
                    logradouro = line.split(":")[1].replaceAll("\"", "").trim();
                } else if (line.contains("\"bairro\"")) {
                    bairro = line.split(":")[1].replaceAll("\"", "").trim();
                } else if (line.contains("\"localidade\"")) {
                    cidade = line.split(":")[1].replaceAll("\"", "").trim();
                } else if (line.contains("\"uf\"")) {
                    uf = line.split(":")[1].replaceAll("\"", "").trim();
                } else if (line.contains("\"cep\"")) {
                    cep = line.split(":")[1].replaceAll("\"", "").trim();
                } else if (line.contains("\"complemento\"")) {
                    String complemento = line.split(":")[1].replaceAll("\"", "").trim();
                    // Verifica se o complemento � composto apenas por d�gitos
                    if (complemento.matches("\\d+")) {
                        numero = complemento;
                    }
                }
            }

            endereco = new Endereco(logradouro, bairro, cidade, uf, cep, numero);
        } catch (NumberFormatException e) {
            // Trate a exce��o adequadamente

        }
        return endereco;
    }

    private void preencherCamposEndereco(Endereco endereco) {
        if (endereco != null) {
            // Define os valores nos campos JTextField com os dados do endere�o
            view.getComboBoxUF().setSelectedItem(endereco.getSigla());
            view.getComboBoxEstado().setSelectedIndex(view.getComboBoxUF().getSelectedIndex());
            view.getComboBoxCidade().setSelectedItem(endereco.getCidade());
            view.getComboBoxBairro().setSelectedItem(endereco.getBairro());
            view.getComboBoxLogradouro().setSelectedItem(endereco.getLogradouro());
            view.getCampoNumero().setText(endereco.getNumero());
        } else {
            // Se o endere�o n�o for encontrado, limpe os campos
            view.getComboBoxBairro().setSelectedIndex(-1);
            view.getComboBoxCidade().setSelectedIndex(-1);
            view.getComboBoxUF().setSelectedIndex(-1);
            view.getComboBoxEstado().setSelectedIndex(-1);
            view.getComboBoxLogradouro().setSelectedIndex(-1);
            view.getCampoNumero().setText("");
        }
    }
    
    //Realiza o tratamento da string
    public String tratamentoString(String url){
        url = tratamentoCaracteresEspeciais(url);
        url = tratamentoEspacos(url);
        
        return url;
    }
    
    //Verifica se é um caracter especial se for ele modifica
    public String tratamentoCaracteresEspeciais(String url){
        
        
        //Inicia um Normalizer com a string, pega todos os caracteres separadamente
        return Normalizer.normalize(url, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", ""); // Todos os caracteres que não são ASCII são substuidos pelo vazio: ""
    }
    
    //Tratamento de espaços para envio da url
    public String tratamentoEspacos(String url){
        return url.replace(" ", "%20"); //Caso tiver um espaço, ele retornara %20 no lugar dos espaços;
    }


    //Parecida com a BuscarEndereco -- Estudar mais ela para poder comentar
    public void buscarCEP(Endereco endereco) {
        
        try{
        // Constr�i a URL com os par�metros do endere�o
        String url = "https://viacep.com.br/ws/" + endereco.getSigla() + "/" + endereco.getCidade() + "/" + endereco.getLogradouro() + "/json/";
        url = tratamentoString(url);
            
        @SuppressWarnings("deprecation")
        // Cria um objeto URL com a URL construida
        URL apiUrl;
        apiUrl = new URL(url);
            
        // Abre uma conexão HTTP com a URL
        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();

        // Configura o método de requisição
        connection.setRequestMethod("GET");

        // Obtém o código de resposta da requisição
        int responseCode = connection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Se a conexão foi bem-sucedida, lê os dados da resposta
            StringBuilder response = new StringBuilder();
             try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }
            endereco = parseJsonToAddress(response.toString());
            view.getCampoCep().setText(endereco.getCep());
            view.getComboBoxBairro().setSelectedItem(endereco.getBairro());
        } else {
            // Se a conexão não foi bem-sucedida, mostra o c�digo de erro
            System.out.println("Erro na requisi��o: " + responseCode);
        }
        }catch (IOException e) {
            System.out.println("Erro!");
        }
}
    
    //função para leitura dos estados
    @SuppressWarnings("unchecked")
    public void estados() throws SQLException{
        
        //Realiza a conexao e a envia para 
        Connection conexao = new Conexao().getConnection();
        EnderecoDAO enderecoDao = new EnderecoDAO(conexao);
       
  
       //Repete at� que todos os estados s�o obtidos
        for(Endereco estado : enderecoDao.readEstado()){   
            view.getComboBoxEstado().addItem(estado.getEstado());  // Adiciona o nome do estado no combo box estado
            view.getComboBoxUF().addItem(estado.getSigla()); // Adiciona a sigla do estado no combo box uf
        } 
        
        view.getComboBoxEstado().setSelectedIndex(-1); // Evita o Combo Box j� ter algum estado selecionado
        view.getComboBoxUF().setSelectedIndex(-1); // Evita o Combo Box j� ter algum estado selecionado
    }
    
    public void atualizaComboBoxEstado(){
        
        //Seleciona o combo box de estado e uf respectivamente ao seu estado ou uf selecionado
        view.getComboBoxEstado().setSelectedIndex(view.getEstadoSelecionado());
        view.getComboBoxUF().setSelectedIndex(view.getEstadoSelecionado());
        
        try {
            cidades();//chama fun��o para leitura das cidades
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      
    @SuppressWarnings("unchecked")
    public void cidades() throws SQLException{  
        
        //Realiza a conexao
        Connection conexao = new Conexao().getConnection();
        EnderecoDAO enderecoDao = new EnderecoDAO(conexao);
        

        view.getComboBoxCidade().removeAllItems();//remove todos os itens do Combo Box
        String sigla = (String) view.getComboBoxUF().getSelectedItem();
        
        //Le todas as cidades com o id do estado selecionado
        for(Endereco cidade : enderecoDao.readCidadePorEstado(sigla)){
            view.getComboBoxCidade().addItem(cidade.getCidade());     
        }
        view.getComboBoxCidade().setSelectedIndex(-1);
    }
    
    
    @SuppressWarnings("unchecked")
    public void bairros() throws SQLException{
        
        //Realiza a conexao
        Connection conexao = new Conexao().getConnection();
        EnderecoDAO enderecoDao = new EnderecoDAO(conexao);
        
 
        String nome_cidade = (String) view.getComboBoxCidade().getSelectedItem(); //Pega o nome da cidade selecionada
        String sigla = (String) view.getComboBoxUF().getSelectedItem();
        
        int id_cidade = enderecoDao.pegarIdCidade(sigla, nome_cidade); //Pega o id da cidade selecionada
     
        view.getComboBoxBairro().removeAllItems();//Remove todos os itens do Combo Box
      
        //Le todos os bairros pelo id da cidadade selecionada
        for(Endereco bairro : enderecoDao.readBairroPorCidade(id_cidade)){
           view.getComboBoxBairro().addItem(bairro.getBairro());
        }
        view.getComboBoxBairro().setSelectedIndex(-1);   
    }
    
    @SuppressWarnings("unchecked")
    public void logradouro() throws SQLException{
        
        //Realiza a conexao
        Connection conexao = new Conexao().getConnection();
        EnderecoDAO enderecoDao = new EnderecoDAO(conexao);
        
        String nome_bairro = (String) view.getComboBoxBairro().getSelectedItem();
        String sigla = (String) view.getComboBoxUF().getSelectedItem();
        String cidade = (String) view.getComboBoxCidade().getSelectedItem();
        
        int id_cidade = enderecoDao.pegarIdCidade(sigla, cidade);
        int id_bairro = enderecoDao.pegarIdBairro(nome_bairro,id_cidade);
        
        view.getComboBoxLogradouro().removeAllItems();
        for(Endereco logradouro : enderecoDao.readLogradouroPorBairro(id_bairro)){
            view.getComboBoxLogradouro().addItem(logradouro.getLogradouro());
        }
        view.getComboBoxLogradouro().setSelectedIndex(-1);
    }
    
    public void cadastroEndereco() throws SQLException{
        
        if(campoNuloEndereco()){
            JOptionPane.showMessageDialog(null, "Campo(s) de endereço vazio(s)", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        else{
            //Realiza a conexao
            Connection conexao = new Conexao().getConnection();
            EnderecoDAO enderecoDao = new EnderecoDAO(conexao);
        
            Endereco endereco = new Endereco();
            endereco = enderecoDosCamposPreenchidos();

            //Caso o cep não existe no banco de dados realizará a verificação e inserção no banco de dados
            if(!enderecoDao.existeCEP(endereco.getCep())){
                int id_cidade = enderecoDao.pegarIdCidade(endereco.getSigla(), endereco.getCidade());
            
                //Verifica se o bairro do endereço existe -- Se não existe ele entra
                if(!enderecoDao.existeBairro(endereco.getBairro(), id_cidade)){
                     enderecoDao.novoBairro(endereco.getBairro(),id_cidade);
                }
                int id_bairro = enderecoDao.pegarIdBairro(endereco.getBairro(),id_cidade);
                enderecoDao.novoLogradouro(endereco, id_cidade, id_bairro); //Inseri o endereco no banco de dados
            }
            //Insere o endereço no banco de dados de endereços cliente e usuario
            //enderecoDao.insertEndereco(){
            view.dispose();
        }
    }
     
    
    //Pega o que está escrito nos campos
    public Endereco enderecoDosCamposPreenchidos(){
        
        String cep = view.getCampoCep().getText();//Pega o que esta escrito no campo cep
        String logradouro = (String) view.getComboBoxLogradouro().getSelectedItem();
        String cidade = (String) view.getComboBoxCidade().getSelectedItem();
        String uf = (String) view.getComboBoxUF().getSelectedItem();
        String bairro = (String) view.getComboBoxBairro().getSelectedItem();
        String numero = view.getCampoNumero().getText();
        String complemento = view.getCampoComplemento().getText();

        Endereco endereco = new Endereco(logradouro, bairro, cidade, cep, numero, uf, complemento);
        
        return endereco;
    }

    public boolean campoNuloEndereco(){
        String cep = view.getCampoCep().getText();
        String numero = view.getCampoNumero().getText();
        String estado = (String) view.getComboBoxEstado().getSelectedItem();
        String bairro = (String) view.getComboBoxBairro().getSelectedItem();
        String logradouro = (String) view.getComboBoxLogradouro().getSelectedItem();
        String complemento = view.getCampoComplemento().getText();
        
        return (cep.isEmpty() || numero.isEmpty() || estado.isEmpty() || bairro.isEmpty() || logradouro.isEmpty() || complemento.isEmpty());
        
    }   
    
}