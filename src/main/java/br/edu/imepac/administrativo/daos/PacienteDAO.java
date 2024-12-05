package br.edu.imepac.administrativo.daos;

import br.edu.imepac.administrativo.entidades.Paciente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public void criar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO Paciente (nome, dataNascimento, cpf, endereco) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setDate(2, Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(3, paciente.getCpf());
            stmt.setString(4, paciente.getEndereco());
            stmt.executeUpdate();
        }
    }

    public Paciente ler(int id) throws SQLException {
        String sql = "SELECT * FROM Paciente WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                paciente.setCpf(rs.getString("cpf"));
                paciente.setEndereco(rs.getString("endereco"));
                return paciente;
            }
        }
        return null;
    }

    public void atualizar(Paciente paciente) throws SQLException {
        String sql = "UPDATE Paciente SET nome = ?, dataNascimento = ?, cpf = ?, endereco = ? WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setDate(2, Date.valueOf(paciente.getDataNascimento()));
            stmt.setString(3, paciente.getCpf());
            stmt.setString(4, paciente.getEndereco());
            stmt.setInt(5, paciente.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Paciente WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Paciente> listar() throws SQLException {
        List<Paciente> pacientes = new ArrayList<>();
        String sql = "SELECT * FROM Paciente";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Paciente paciente = new Paciente();
                paciente.setId(rs.getInt("id"));
                paciente.setNome(rs.getString("nome"));
                paciente.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                paciente.setCpf(rs.getString("cpf"));
                paciente.setEndereco(rs.getString("endereco"));
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }
}
