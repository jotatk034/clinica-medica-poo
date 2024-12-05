package br.edu.imepac.administrativo.daos;

import br.edu.imepac.administrativo.entidades.Paciente;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PacienteDAO {

    public void criar(Paciente paciente) throws SQLException {
        String sql = "INSERT INTO Paciente (nome, usuario, senha, sexo, cpf, rua, numero, complemento, bairro, cidade, estado, contato, email, dataNascimento) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getUsuario());
            stmt.setString(3, paciente.getSenha());
            stmt.setString(4, String.valueOf(paciente.getSexo()));
            stmt.setString(5, paciente.getCpf());
            stmt.setString(6, paciente.getRua());
            stmt.setString(7, paciente.getNumero());
            stmt.setString(8, paciente.getComplemento());
            stmt.setString(9, paciente.getBairro());
            stmt.setString(10, paciente.getCidade());
            stmt.setString(11, paciente.getEstado());
            stmt.setString(12, paciente.getContato());
            stmt.setString(13, paciente.getEmail());
            stmt.setDate(14, Date.valueOf(paciente.getDataNascimento()));
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
                paciente.setUsuario(rs.getString("usuario"));
                paciente.setSenha(rs.getString("senha"));
                paciente.setSexo(rs.getString("sexo").charAt(0));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setRua(rs.getString("rua"));
                paciente.setNumero(rs.getString("numero"));
                paciente.setComplemento(rs.getString("complemento"));
                paciente.setBairro(rs.getString("bairro"));
                paciente.setCidade(rs.getString("cidade"));
                paciente.setEstado(rs.getString("estado"));
                paciente.setContato(rs.getString("contato"));
                paciente.setEmail(rs.getString("email"));
                paciente.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                return paciente;
            }
        }
        return null;
    }

    public void atualizar(Paciente paciente) throws SQLException {
        String sql = "UPDATE Paciente SET nome = ?, usuario = ?, senha = ?, sexo = ?, cpf = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, contato = ?, email = ?, dataNascimento = ? WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, paciente.getNome());
            stmt.setString(2, paciente.getUsuario());
            stmt.setString(3, paciente.getSenha());
            stmt.setString(4, String.valueOf(paciente.getSexo()));
            stmt.setString(5, paciente.getCpf());
            stmt.setString(6, paciente.getRua());
            stmt.setString(7, paciente.getNumero());
            stmt.setString(8, paciente.getComplemento());
            stmt.setString(9, paciente.getBairro());
            stmt.setString(10, paciente.getCidade());
            stmt.setString(11, paciente.getEstado());
            stmt.setString(12, paciente.getContato());
            stmt.setString(13, paciente.getEmail());
            stmt.setDate(14, Date.valueOf(paciente.getDataNascimento()));
            stmt.setInt(15, paciente.getId());
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
                paciente.setUsuario(rs.getString("usuario"));
                paciente.setSenha(rs.getString("senha"));
                paciente.setSexo(rs.getString("sexo").charAt(0));
                paciente.setCpf(rs.getString("cpf"));
                paciente.setRua(rs.getString("rua"));
                paciente.setNumero(rs.getString("numero"));
                paciente.setComplemento(rs.getString("complemento"));
                paciente.setBairro(rs.getString("bairro"));
                paciente.setCidade(rs.getString("cidade"));
                paciente.setEstado(rs.getString("estado"));
                paciente.setContato(rs.getString("contato"));
                paciente.setEmail(rs.getString("email"));
                paciente.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                pacientes.add(paciente);
            }
        }
        return pacientes;
    }
}
