package br.edu.imepac.testes;

import br.edu.imepac.administrativo.daos.PerfilDAO;
import br.edu.imepac.administrativo.entidades.Perfil;

import java.sql.SQLException;

public class TesteInsercaoPerfilAdministrador {
    public static void main(String[] args) {
        PerfilDAO perfilDAO = new PerfilDAO();
        Perfil perfil = new Perfil();
        perfil.setNome("Rogelio");
        perfil.setCadastrarFuncionario(true);
        perfil.setLerFuncionario(true);
        perfil.setAtualizarFuncionario(true);
        perfil.setDeletarFuncionario(true);
        perfil.setListarFuncionario(true);
        perfil.setCadastrarPaciente(true);
        perfil.setLerPaciente(true);
        perfil.setAtualizarPaciente(true);
        perfil.setDeletarPaciente(true);
        perfil.setListarPaciente(true);
        perfil.setCadastrarConsulta(true);
        perfil.setLerConsulta(true);
        perfil.setAtualizarConsulta(true);
        perfil.setDeletarConsulta(true);
        perfil.setListarConsulta(true);
        perfil.setCadastrarEspecialidade(true);
        perfil.setLerEspecialidade(true);
        perfil.setAtualizarEspecialidade(true);
        perfil.setDeletarEspecialidade(true);
        perfil.setListarEspecialidade(true);
        perfil.setCadastrarConvenio(true);
        perfil.setLerConvenio(true);
        perfil.setAtualizarConvenio(true);
        perfil.setDeletarConvenio(true);
        perfil.setListarConvenio(true);
        perfil.setCadastrarProntuario(true);
        perfil.setLerProntuario(true);
        perfil.setAtualizarProntuario(true);
        perfil.setDeletarProntuario(true);
        perfil.setListarProntuario(true);

        try {
            perfilDAO.criar(perfil);
            System.out.println("Perfil 'Rogelio' criado como Administrador com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao criar perfil 'Rogelio': " + e.getMessage());
        }
    }
}
