package br.edu.imepac.administrativo.daos;

import br.edu.imepac.administrativo.entidades.Prontuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProntuarioDAO {

    public void criar(Prontuario prontuario) throws SQLException {
        String sql = "INSERT INTO Prontuario (descricao, data, pacienteId) VALUES (?, ?, ?)";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prontuario.getDescricao());
            stmt.setDate(2, Date.valueOf(prontuario.getData()));
            stmt.setInt(3, prontuario.getPacienteId());
            stmt.executeUpdate();
        }
    }

    public Prontuario ler(int id) throws SQLException {
        String sql = "SELECT * FROM Prontuario WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Prontuario prontuario = new Prontuario();
                prontuario.setId(rs.getInt("id"));
                prontuario.setDescricao(rs.getString("descricao"));
                prontuario.setData(rs.getDate("data").toLocalDate());
                prontuario.setPacienteId(rs.getInt("pacienteId"));
                return prontuario;
            }
        }
        return null;
    }

    public void atualizar(Prontuario prontuario) throws SQLException {
        String sql = "UPDATE Prontuario SET descricao = ?, data = ?, pacienteId = ? WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, prontuario.getDescricao());
            stmt.setDate(2, Date.valueOf(prontuario.getData()));
            stmt.setInt(3, prontuario.getPacienteId());
            stmt.setInt(4, prontuario.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Prontuario WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Prontuario> listar() throws SQLException {
        List<Prontuario> prontuarios = new ArrayList<>();
        String sql = "SELECT * FROM Prontuario";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Prontuario prontuario = new Prontuario();
                prontuario.setId(rs.getInt("id"));
                prontuario.setDescricao(rs.getString("descricao"));
                prontuario.setData(rs.getDate("data").toLocalDate());
                prontuario.setPacienteId(rs.getInt("pacienteId"));
                prontuarios.add(prontuario);
            }
        }
        return prontuarios;
    }
}
