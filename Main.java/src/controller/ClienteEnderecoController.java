package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import model.Endereco;
import view.CadastroClienteView;
import java.sql.SQLException;

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
            String estado = null;
            String cep = null;
            for (String line : lines) {
                if (line.contains("\"logradouro\"")) {
                    logradouro = line.split(":")[1].replaceAll("\"", "").trim();
                } else if (line.contains("\"bairro\"")) {
                    bairro = line.split(":")[1].replaceAll("\"", "").trim();
                } else if (line.contains("\"localidade\"")) {
                    cidade = line.split(":")[1].replaceAll("\"", "").trim();
                } else if (line.contains("\"uf\"")) {
                    estado = line.split(":")[1].replaceAll("\"", "").trim();
                } else if (line.contains("\"cep\"")) {
                    cep = line.split(":")[1].replaceAll("\"", "").trim();
                }
            }

            endereco = new Endereco(logradouro, bairro, cidade, estado, cep);
        } catch (Exception e) {
        }
        return endereco;
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
