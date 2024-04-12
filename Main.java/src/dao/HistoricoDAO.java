package dao;

import java.sql.Connection;
import model.Historico;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.text.ParseException;

public class HistoricoDAO {

    private final Connection connection;

    public HistoricoDAO(Connection connection) {
        this.connection = connection;
    }

    public int adicionarCarrinhoHistorico(float precoTotal, int id_usuario, Integer id_cliente) throws SQLException, ParseException {
        Historico historico = new Historico(precoTotal, id_usuario, id_cliente);

        // Formatando a data para o formato "dia-mês-ano"
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String dataFormatada = dateFormat.format(historico.getData());

        // Formatando o tempo para o formato "hora:minuto:segundo:milissegundo"
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss.SSS");
        String tempoFormatado = timeFormat.format(historico.getTempo());

        String sql = "INSERT INTO historico (data, tempo, preco_total, fk_id_usuario, fk_id_cliente) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);

        statement.setString(1, dataFormatada);
        statement.setString(2, tempoFormatado);
        statement.setFloat(3, historico.getPrecoTotal());
        statement.setInt(4, historico.getFk_id_usuario());
        statement.setInt(5, historico.getFk_id_cliente());

        statement.executeUpdate();

        // Obtendo o ID do histórico inserido
        ResultSet generatedKeys = statement.getGeneratedKeys();
        int id = -1;
        if (generatedKeys.next()) {
            id = generatedKeys.getInt(1);
        }

        // Fechando as conexões e retornando o ID do histórico
        statement.close();
        generatedKeys.close();
        return id;
    }

}
