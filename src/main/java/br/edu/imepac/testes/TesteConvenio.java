import br.edu.imepac.administrativo.daos.ConvenioDAO;
import br.edu.imepac.administrativo.entidades.Convenio;

import java.sql.SQLException;
import java.util.List;

public class TesteConvenio {
    public static void main(String[] args) {
        ConvenioDAO convenioDAO = new ConvenioDAO();

        // Criar um novo convênio
        Convenio convenio = new Convenio();
        convenio.setNome("Convênio Saúde");
        convenio.setDescricao("Convênio de saúde para atendimento geral.");

        try {
            convenioDAO.criar(convenio);
            System.out.println("Convênio criado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao criar convênio: " + e.getMessage());
        }

        // Ler um convênio específico
        try {
            Convenio convenioLido = convenioDAO.ler(1); // Certifique-se de que o ID existe no banco.
            if (convenioLido != null) {
                System.out.println("Convênio encontrado: " + convenioLido.getNome() + " - " + convenioLido.getDescricao());
            } else {
                System.out.println("Convênio não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao ler convênio: " + e.getMessage());
        }

        // Atualizar um convênio
        try {
            Convenio convenioParaAtualizar = convenioDAO.ler(1); // Certifique-se de que o ID existe no banco.
            if (convenioParaAtualizar != null) {
                convenioParaAtualizar.setNome("Convênio Saúde Atualizado");
                convenioParaAtualizar.setDescricao("Descrição atualizada do convênio.");
                convenioDAO.atualizar(convenioParaAtualizar);
                System.out.println("Convênio atualizado com sucesso!");
            } else {
                System.out.println("Convênio para atualização não encontrado.");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao atualizar convênio: " + e.getMessage());
        }

        // Listar todos os convênios
        try {
            List<Convenio> convenios = convenioDAO.listar();
            System.out.println("Lista de convênios:");
            for (Convenio c : convenios) {
                System.out.println("ID: " + c.getId() + ", Nome: " + c.getNome() + ", Descrição: " + c.getDescricao());
            }
        } catch (SQLException e) {
            System.out.println("Erro ao listar convênios: " + e.getMessage());
        }

        // Deletar um convênio
        try {
            convenioDAO.deletar(1); // Certifique-se de que o ID existe no banco.
            System.out.println("Convênio deletado com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao deletar convênio: " + e.getMessage());
        }
    }
}
