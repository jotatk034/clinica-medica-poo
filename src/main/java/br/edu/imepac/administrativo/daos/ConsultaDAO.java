package br.edu.imepac.administrativo.daos;

import br.edu.imepac.administrativo.entidades.Consulta;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO extends BaseDAO<Consulta> {

    public ConsultaDAO() throws SQLException {
        super();
    }

    public void save(Consulta consulta) throws SQLException {
        String sql = "INSERT INTO consulta (data_horario, sintoma, diagnostico, esta_ativa, medico_id, atendente_id, paciente_id, convenio_id, prontuario_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(consulta.getDataHorario()));
            stmt.setString(2, consulta.getSintoma());
            stmt.setString(3, consulta.getDiagnostico());
            stmt.setBoolean(4, consulta.isEstaAtiva());
            stmt.setInt(5, consulta.getMedico().getId());
            stmt.setInt(6, consulta.getAtendente().getId());
            stmt.setInt(7, consulta.getPaciente().getId());
            stmt.setInt(8, consulta.getConvenio().getId());
            stmt.setInt(9, consulta.getProntuario().getId());
            stmt.executeUpdate();
        }
    }

    public Consulta findById(int id) throws SQLException {
        String sql = "SELECT * FROM consulta WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return buildConsulta(rs);
                }
            }
        }
        return null;
    }

    public void update(Consulta consulta) throws SQLException {
        String sql = "UPDATE consulta SET data_horario = ?, sintoma = ?, diagnostico = ?, esta_ativa = ?, medico_id = ?, atendente_id = ?, paciente_id = ?, convenio_id = ?, prontuario_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, Timestamp.valueOf(consulta.getDataHorario()));
            stmt.setString(2, consulta.getSintoma());
            stmt.setString(3, consulta.getDiagnostico());
            stmt.setBoolean(4, consulta.isEstaAtiva());
            stmt.setInt(5, consulta.getMedico().getId());
            stmt.setInt(6, consulta.getAtendente().getId());
            stmt.setInt(7, consulta.getPaciente().getId());
            stmt.setInt(8, consulta.getConvenio().getId());
            stmt.setInt(9, consulta.getProntuario().getId());
            stmt.setInt(10, consulta.getId());
            stmt.executeUpdate();
        }
    }

    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM consulta WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Consulta> findAll() throws SQLException {
        List<Consulta> consultas = new ArrayList<>();
        String sql = "SELECT * FROM consulta";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                consultas.add(buildConsulta(rs));
            }
        }
        return consultas;
    }

    private Consulta buildConsulta(ResultSet rs) throws SQLException {
        Consulta consulta = new Consulta();
        consulta.setId(rs.getInt("id"));
        consulta.setDataHorario(rs.getTimestamp("data_horario").toLocalDateTime());
        consulta.setSintoma(rs.getString("sintoma"));
        consulta.setDiagnostico(rs.getString("diagnostico"));
        consulta.setEstaAtiva(rs.getBoolean("esta_ativa"));
        return consulta;
    }
}
