package br.edu.imepac.administrativo.daos;

import br.edu.imepac.administrativo.entidades.Convenio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConvenioDAO {
    public void criar(Convenio convenio) throws SQLException {
        String sql = "INSERT INTO convenio (nome, descricao) VALUES (?, ?)";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, convenio.getNome());
            stmt.setString(2, convenio.getDescricao());
            stmt.executeUpdate();
        }
    }

    public Convenio ler(int id) throws SQLException {
        String sql = "SELECT * FROM convenio WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Convenio convenio = new Convenio();
                convenio.setId(rs.getInt("id"));
                convenio.setNome(rs.getString("nome"));
                convenio.setDescricao(rs.getString("descricao"));
                return convenio;
            }
        }
        return null;
    }

    public void atualizar(Convenio convenio) throws SQLException {
        String sql = "UPDATE convenio SET nome = ?, descricao = ? WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, convenio.getNome());
            stmt.setString(2, convenio.getDescricao());
            stmt.setInt(3, convenio.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM convenio WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Convenio> listar() throws SQLException {
        List<Convenio> convenios = new ArrayList<>();
        String sql = "SELECT * FROM convenio";
        try (Connection conn = ConexaoDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Convenio convenio = new Convenio();
                convenio.setId(rs.getInt("id"));
                convenio.setNome(rs.getString("nome"));
                convenio.setDescricao(rs.getString("descricao"));
                convenios.add(convenio);
            }
        }
        return convenios;
    }
}
