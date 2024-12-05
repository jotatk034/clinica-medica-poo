package br.edu.imepac.administrativo.daos;

import br.edu.imepac.administrativo.entidades.Funcionario;
import br.edu.imepac.administrativo.entidades.Especialidade;
import br.edu.imepac.administrativo.entidades.Perfil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDAO {
    public void criar(Funcionario funcionario) throws SQLException {
        String sql = "INSERT INTO funcionario (usuario, senha, nome, sexo, cpf, rua, numero, complemento, bairro, cidade, estado, contato, email, data_nascimento, especialidade_id, perfil_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getUsuario());
            stmt.setString(2, funcionario.getSenha());
            stmt.setString(3, funcionario.getNome());
            stmt.setString(4, String.valueOf(funcionario.getSexo()));
            stmt.setString(5, funcionario.getCpf());
            stmt.setString(6, funcionario.getRua());
            stmt.setString(7, funcionario.getNumero());
            stmt.setString(8, funcionario.getComplemento());
            stmt.setString(9, funcionario.getBairro());
            stmt.setString(10, funcionario.getCidade());
            stmt.setString(11, funcionario.getEstado());
            stmt.setString(12, funcionario.getContato());
            stmt.setString(13, funcionario.getEmail());
            stmt.setDate(14, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setInt(15, funcionario.getEspecialidade().getId());
            stmt.setInt(16, funcionario.getPerfil().getId());
            stmt.executeUpdate();
        }
    }

    public Funcionario ler(int id) throws SQLException {
        String sql = "SELECT * FROM funcionario WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setUsuario(rs.getString("usuario"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSexo(rs.getString("sexo").charAt(0));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setRua(rs.getString("rua"));
                funcionario.setNumero(rs.getString("numero"));
                funcionario.setComplemento(rs.getString("complemento"));
                funcionario.setBairro(rs.getString("bairro"));
                funcionario.setCidade(rs.getString("cidade"));
                funcionario.setEstado(rs.getString("estado"));
                funcionario.setContato(rs.getString("contato"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                // Configurar Especialidade e Perfil com DAOs correspondentes
                Especialidade especialidade = new Especialidade();
                especialidade.setId(rs.getInt("especialidade_id"));
                funcionario.setEspecialidade(especialidade);

                Perfil perfil = new Perfil();
                perfil.setId(rs.getInt("perfil_id"));
                funcionario.setPerfil(perfil);

                return funcionario;
            }
        }
        return null;
    }

    public void atualizar(Funcionario funcionario) throws SQLException {
        String sql = "UPDATE funcionario SET usuario = ?, senha = ?, nome = ?, sexo = ?, cpf = ?, rua = ?, numero = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, contato = ?, email = ?, data_nascimento = ?, especialidade_id = ?, perfil_id = ? WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, funcionario.getUsuario());
            stmt.setString(2, funcionario.getSenha());
            stmt.setString(3, funcionario.getNome());
            stmt.setString(4, String.valueOf(funcionario.getSexo()));
            stmt.setString(5, funcionario.getCpf());
            stmt.setString(6, funcionario.getRua());
            stmt.setString(7, funcionario.getNumero());
            stmt.setString(8, funcionario.getComplemento());
            stmt.setString(9, funcionario.getBairro());
            stmt.setString(10, funcionario.getCidade());
            stmt.setString(11, funcionario.getEstado());
            stmt.setString(12, funcionario.getContato());
            stmt.setString(13, funcionario.getEmail());
            stmt.setDate(14, Date.valueOf(funcionario.getDataNascimento()));
            stmt.setInt(15, funcionario.getEspecialidade().getId());
            stmt.setInt(16, funcionario.getPerfil().getId());
            stmt.setInt(17, funcionario.getId());
            stmt.executeUpdate();
        }
    }

    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM funcionario WHERE id = ?";
        try (Connection conn = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    public List<Funcionario> listar() throws SQLException {
        List<Funcionario> funcionarios = new ArrayList<>();
        String sql = "SELECT * FROM funcionario";
        try (Connection conn = ConexaoDatabase.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setUsuario(rs.getString("usuario"));
                funcionario.setSenha(rs.getString("senha"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setSexo(rs.getString("sexo").charAt(0));
                funcionario.setCpf(rs.getString("cpf"));
                funcionario.setRua(rs.getString("rua"));
                funcionario.setNumero(rs.getString("numero"));
                funcionario.setComplemento(rs.getString("complemento"));
                funcionario.setBairro(rs.getString("bairro"));
                funcionario.setCidade(rs.getString("cidade"));
                funcionario.setEstado(rs.getString("estado"));
                funcionario.setContato(rs.getString("contato"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setDataNascimento(rs.getDate("data_nascimento").toLocalDate());
                // Configurar Especialidade e Perfil com DAOs correspondentes
                Especialidade especialidade = new Especialidade();
                especialidade.setId(rs.getInt("especialidade_id"));
                funcionario.setEspecialidade(especialidade);

                Perfil perfil = new Perfil();
                perfil.setId(rs.getInt("perfil_id"));
                funcionario.setPerfil(perfil);

                funcionarios.add(funcionario);
            }
        }
        return funcionarios;
    }
}
