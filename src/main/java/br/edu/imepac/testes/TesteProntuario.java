import br.edu.imepac.administrativo.daos.ProntuarioDAO;
import br.edu.imepac.administrativo.entidades.Prontuario;

import java.sql.SQLException;
import java.util.List;

public class TesteProntuario {
    public static void main(String[] args) {
        ProntuarioDAO prontuarioDAO = new ProntuarioDAO();

        // Criar um novo prontuário
        Prontuario prontuario = new Prontuario();
        prontuario.setReceituario("Receita para o paciente.");
        prontuario.setObservacoes("Observações sobre o paciente.");

        try {
            prontuarioDAO.criar(prontuario);
            System.out.println("Prontuário criado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar prontuário: " + e.getMessage());
        }

        // Ler um prontuário específico
        try {
            Prontuario prontuarioLido = prontuarioDAO.ler(1); // Certifique-se de que o ID existe no banco.
            if (prontuarioLido != null) {
                System.out.println("Prontuário encontrado: " + prontuarioLido.getReceituario());
            } else {
                System.out.println("Prontuário não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao ler prontuário: " + e.getMessage());
        }

        // Atualizar um prontuário
        try {
            Prontuario prontuarioParaAtualizar = prontuarioDAO.ler(1); // Certifique-se de que o ID existe no banco.
            if (prontuarioParaAtualizar != null) {
                prontuarioParaAtualizar.setReceituario("Receita atualizada.");
                prontuarioParaAtualizar.setObservacoes("Observações atualizadas.");
                prontuarioDAO.atualizar(prontuarioParaAtualizar);
                System.out.println("Prontuário atualizado com sucesso!");
            } else {
                System.out.println("Prontuário para atualização não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar prontuário: " + e.getMessage());
        }

        // Listar todos os prontuários
        try {
            List<Prontuario> prontuarios = prontuarioDAO.listar();
            System.out.println("Lista de prontuários:");
            for (Prontuario p : prontuarios) {
                System.out.println("ID: " + p.getId() + ", Receita: " + p.getReceituario() + ", Observações: " + p.getObservacoes());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar prontuários: " + e.getMessage());
        }

        // Deletar um prontuário
        try {
            prontuarioDAO.deletar(1); // Certifique-se de que o ID existe no banco.
            System.out.println("Prontuário deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar prontuário: " + e.getMessage());
        }
    }
}
