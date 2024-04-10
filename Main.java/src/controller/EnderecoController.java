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


    
    public Endereco buscarEndereco(String cep) throws SQLException {
        Endereco endereco = new Endereco();
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
                    endereco = parseJsonToAddress(response.toString());  
                } else {
                    System.out.println("Erro na requisicao: " + responseCode);
                }
            } catch (IOException e) {
            }
        }
        return endereco;
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
    public Endereco buscarCEP(Endereco endereco) {
        
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
        } else {
            // Se a conexão não foi bem-sucedida, mostra o c�digo de erro
            System.out.println("Erro na requisi��o: " + responseCode);
        }
        }catch (IOException e) {
            System.out.println("Erro!");
        }
        return endereco;
}
    
    //função para leitura dos estados
    @SuppressWarnings("unchecked")
    public ArrayList<Endereco> estados() throws SQLException{
        
        //Realiza a conexao e a envia para 
        Connection conexao = new Conexao().getConnection();
        EnderecoDAO enderecoDao = new EnderecoDAO(conexao);
       
        ArrayList<Endereco> estados = new ArrayList<>();
        
       //Repete at� que todos os estados s�o obtidos
        for(Endereco estado : enderecoDao.readEstado()){   
            estados.add(estado);
        } 
        return estados;
       
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<String> cidades(String sigla) throws SQLException{  
        
        //Realiza a conexao
        Connection conexao = new Conexao().getConnection();
        EnderecoDAO enderecoDao = new EnderecoDAO(conexao);
        
        ArrayList<String> cidades = new ArrayList<>();
        
        //Le todas as cidades com o id do estado selecionado
        for(Endereco cidade : enderecoDao.readCidadePorEstado(sigla)){
            cidades.add(cidade.getCidade());
        }
        return cidades;
    }
    
   
    @SuppressWarnings("unchecked")
    public ArrayList<String> bairros(String nome_cidade, String uf) throws SQLException{
        
        //Realiza a conexao
        Connection conexao = new Conexao().getConnection();
        EnderecoDAO enderecoDao = new EnderecoDAO(conexao); 
        
        int id_cidade = enderecoDao.pegarIdCidade(uf, nome_cidade); //Pega o id da cidade selecionada
     
        ArrayList<String> bairros = new ArrayList<>();
       
        //Le todos os bairros pelo id da cidadade selecionada
        for(Endereco bairro : enderecoDao.readBairroPorCidade(id_cidade)){
           bairros.add(bairro.getBairro());
        }
        return bairros;
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<String> logradouro(String bairro, String uf, String cidade) throws SQLException{
        
        //Realiza a conexao
        Connection conexao = new Conexao().getConnection();
        EnderecoDAO enderecoDao = new EnderecoDAO(conexao);
        
        int id_cidade = enderecoDao.pegarIdCidade(uf, cidade);
        int id_bairro = enderecoDao.pegarIdBairro(bairro,id_cidade);
        
        ArrayList<String> logradouros = new ArrayList<>();
        
        
        for(Endereco logradouro : enderecoDao.readLogradouroPorBairro(id_bairro)){
            logradouros.add(logradouro.getLogradouro());
        }
        return logradouros;
    }
    
    
    public void cadastroEndereco(Endereco endereco, boolean temCampoNulo) throws SQLException{
        int id_endereco = -1;
        
        if(temCampoNulo){
            JOptionPane.showMessageDialog(null, "Campo(s) de endereço vazio(s)", "Erro", JOptionPane.ERROR_MESSAGE);
        }
        else{
            //Realiza a conexao
            Connection conexao = new Conexao().getConnection();
            EnderecoDAO enderecoDao = new EnderecoDAO(conexao);      

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
            //id_endereco = enderecoDao.insertEndereco(endereco);
            //System.out.println(id_endereco);
        }
    }   
}