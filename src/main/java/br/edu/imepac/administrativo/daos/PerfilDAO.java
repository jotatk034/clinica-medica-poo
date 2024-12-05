package br.edu.imepac.administrativo.daos;

import br.edu.imepac.administrativo.entidades.Perfil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PerfilDAO {

    public void criar(Perfil perfil) throws SQLException {
        String sql = "INSERT INTO Perfil (nome) VALUES (?)";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, perfil.getNome());
            stmt.executeUpdate();
        }
    }

    public Perfil ler(int id) throws SQLException {
        String sql = "SELECT * FROM Perfil WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Perfil perfil = new Perfil();
                perfil.setId(rs.getInt("id"));
                perfil.setNome(rs.getString("nome"));
                return perfil;
            }
        }
        return null;
    }

    public void atualizar(Perfil perfil) throws SQLException {
        String sql = "UPDATE Perfil SET nome = ? WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, perfil.getNome());
            stmt.setInt(2, perfil.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Perfil WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Perfil> listar() throws SQLException {
        List<Perfil> perfis = new ArrayList<>();
        String sql = "SELECT * FROM Perfil";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Perfil perfil = new Perfil();
                perfil.setId(rs.getInt("id"));
                perfil.setNome(rs.getString("nome"));
                perfis.add(perfil);
            }
        }
        return perfis;
    }
}
