package br.edu.imepac.administrativo.daos;

import br.edu.imepac.administrativo.entidades.Especialidade;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecialidadeDAO {

    public void criar(Especialidade especialidade) throws SQLException {
        String sql = "INSERT INTO Especialidade (nome, descricao) VALUES (?, ?)";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especialidade.getNome());
            stmt.setString(2, especialidade.getDescricao());
            stmt.executeUpdate();
        }
    }

    public Especialidade ler(int id) throws SQLException {
        String sql = "SELECT * FROM Especialidade WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Especialidade especialidade = new Especialidade();
                especialidade.setId(rs.getInt("id"));
                especialidade.setNome(rs.getString("nome"));
                especialidade.setDescricao(rs.getString("descricao"));
                return especialidade;
            }
        }
        return null;
    }

    public void atualizar(Especialidade especialidade) throws SQLException {
        String sql = "UPDATE Especialidade SET nome = ?, descricao = ? WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, especialidade.getNome());
            stmt.setString(2, especialidade.getDescricao());
            stmt.setInt(3, especialidade.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Especialidade WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Especialidade> listar() throws SQLException {
        List<Especialidade> especialidades = new ArrayList<>();
        String sql = "SELECT * FROM Especialidade";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Especialidade especialidade = new Especialidade();
                especialidade.setId(rs.getInt("id"));
                especialidade.setNome(rs.getString("nome"));
                especialidade.setDescricao(rs.getString("descricao"));
                especialidades.add(especialidade);
            }
        }
        return especialidades;
    }
}
