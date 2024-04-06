package controller;

import java.sql.Connection;
import dao.Conexao;
import dao.EnderecoDAO;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import model.Endereco;
import view.CadastroClienteView;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.ComboBoxEditor;
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
        // Verifica se o CEP tem mais de 8 dígitos
        if (cep.length() > 8) {
            try {
                // Faz a conexão com ViaCEP
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
                    System.out.println("Erro na requisição: " + responseCode);
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
                    // Verifica se o complemento é composto apenas por dígitos
                    if (complemento.matches("\\d+")) {
                        numero = complemento;
                    }
                }
            }

            endereco = new Endereco(logradouro, bairro, cidade, uf, cep, numero);
        } catch (NumberFormatException e) {
            // Trate a exceção adequadamente

        }
        return endereco;
    }

    private void preencherCamposEndereco(Endereco endereco) {
        if (endereco != null) {
            // Define os valores nos campos JTextField com os dados do endereço
            view.getCampoBairro().setText(endereco.getBairro());
            view.getCampoCidade().setText(endereco.getCidade());
            view.getComboBoxUF().setSelectedItem(endereco.getSigla());
            view.getCampoLogradouro().setText(endereco.getLogradouro());
            view.getCampoNumero().setText(endereco.getNumero());
        } else {
            // Se o endereço não for encontrado, limpe os campos
            view.getCampoBairro().setText("");
            view.getCampoCidade().setText("");
            view.getComboBoxUF().setSelectedIndex(-1);
            view.getCampoLogradouro().setText("");
            view.getCampoNumero().setText("");
        }
    }
    
    public void estados() throws SQLException{
        
        ArrayList<Endereco> listaEstado = new ArrayList<>();//Novo array para listar todos os estados do banco de dados
        
        
        //Realiza a conexao e a envia para 
        Connection conexao = new Conexao().getConnection();
        EnderecoDAO enderecoDao = new EnderecoDAO(conexao);
       
  
       //Repete até que todos os estados são obtidos
        for(Endereco estado : enderecoDao.readEstado()){
            
            view.getComboBoxEstado().setSelectedIndex(-1); // Evita o Combo Box já ter algum estado selecionado
            view.getComboBoxEstado().addItem(estado.getEstado());  // Adiciona o nome do estado no combo box estado
            
            view.getComboBoxUF().setSelectedIndex(-1); // Evita o Combo Box já ter algum estado selecionado
            view.getComboBoxUF().addItem(estado.getSigla()); // Adiciona a sigla do estado no combo box uf
        }      
    }
    
    public void pesquisaEstado(){
        if(view.getSelecionado()>0){
            if(view.getComboBoxEstado().getSelectedIndex() != view.getSelecionado()){
                view.getComboBoxEstado().setSelectedIndex(view.getSelecionado());
            }
            
            if(view.getComboBoxUF().getSelectedIndex() != view.getSelecionado()){
                view.getComboBoxUF().setSelectedIndex(view.getSelecionado());
            }
            
        }
        
    }
}