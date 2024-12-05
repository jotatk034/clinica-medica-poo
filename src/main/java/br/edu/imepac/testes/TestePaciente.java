package br.edu.imepac.testes;

import br.edu.imepac.administrativo.daos.PacienteDAO;
import br.edu.imepac.administrativo.entidades.Paciente;
import java.sql.SQLException;
import java.time.LocalDate;

public class TestePaciente {
    public static void main(String[] args) {
        Paciente paciente = new Paciente();
        paciente.setNome("Rogélio");
        paciente.setDataNascimento(LocalDate.of(2001, 4, 16));
        paciente.setCpf("12345678901");
        paciente.setUsuario("rogelio.claro");
        paciente.setSenha("senha123");
        paciente.setSexo('M');
        paciente.setRua("Rua Olegario Maciel");
        paciente.setNumero("1232");
        paciente.setComplemento("casa");
        paciente.setBairro("Centro");
        paciente.setCidade("Araguari");
        paciente.setEstado("Minas Gerais");
        paciente.setContato("34984141504");
        paciente.setEmail("rogelio.claro@gmail.com");

        PacienteDAO pacienteDAO = new PacienteDAO();
        try {
            pacienteDAO.criar(paciente);
            System.out.println("Paciente inserido com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir paciente: " + e.getMessage());
        }
    }
}
