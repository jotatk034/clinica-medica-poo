import br.edu.imepac.administrativo.daos.FuncionarioDAO;
import br.edu.imepac.administrativo.entidades.Funcionario;
import br.edu.imepac.administrativo.entidades.Especialidade;
import br.edu.imepac.administrativo.entidades.Perfil;
import br.edu.imepac.administrativo.entidades.EnumTipoFuncionario;

import java.sql.SQLException;
import java.time.LocalDate;

public class TesteFuncionario {
    public static void main(String[] args) {
        // Criar uma especialidade fictícia (essa especialidade deve existir no banco de dados)
        Especialidade especialidade = new Especialidade();
        especialidade.setId(1);  // Assume que a especialidade com ID 1 existe no banco de dados

        // Criar um perfil fictício (esse perfil também deve existir no banco de dados)
        Perfil perfil = new Perfil();
        perfil.setId(1);  // Assume que o perfil com ID 1 existe no banco de dados

        // Criar o objeto Funcionario
        Funcionario funcionario = new Funcionario();
        funcionario.setUsuario("johndoe");
        funcionario.setSenha("password123");
        funcionario.setNome("John Doe");
        funcionario.setSexo('M');
        funcionario.setCpf("123.456.789-00");
        funcionario.setRua("Rua Fictícia");
        funcionario.setNumero("123");
        funcionario.setComplemento("Apto 101");
        funcionario.setBairro("Centro");
        funcionario.setCidade("Cidade Exemplo");
        funcionario.setEstado("EX");
        funcionario.setContato("99999-9999");
        funcionario.setEmail("johndoe@example.com");
        funcionario.setDataNascimento(LocalDate.of(1990, 5, 15));  // Data de nascimento fictícia
        funcionario.setEspecialidade(especialidade);
        funcionario.setPerfil(perfil);

        // Usar o EnumTipoFuncionario diretamente
        funcionario.setTipoFuncionario(EnumTipoFuncionario.MEDICO); // Ou ATENDENTE ou ADMINISTRADOR

        // Instanciar o DAO e chamar o método criar
        FuncionarioDAO funcionarioDAO = new FuncionarioDAO();
        try {
            funcionarioDAO.criar(funcionario);
            System.out.println("Funcionário inserido com sucesso!");
        } catch (SQLException e) {
            System.out.println("Erro ao inserir funcionário: " + e.getMessage());
        }
    }
}
