import br.edu.imepac.administrativo.entidades.Especialidade;
import br.edu.imepac.administrativo.daos.EspecialidadeDAO;
import java.sql.SQLException;

public class TesteEspecialidade {
    public static void main(String[] args) {
        // Criando uma nova especialidade
        Especialidade novaEspecialidade = new Especialidade();
        novaEspecialidade.setNome("Cardiologia");
        novaEspecialidade.setDescricao("Especialidade focada em doenças cardíacas.");

        // Criando uma instância do DAO
        EspecialidadeDAO especialidadeDAO = new EspecialidadeDAO();

        try {
            // Inserindo no banco de dados
            especialidadeDAO.criar(novaEspecialidade);
            System.out.println("Especialidade inserida com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir especialidade: " + e.getMessage());
        }
    }
}
