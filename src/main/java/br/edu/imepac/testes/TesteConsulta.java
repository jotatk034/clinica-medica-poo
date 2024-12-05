import java.sql.*;
import java.time.LocalDate;

public class TesteConsulta {
    public static void main(String[] args) {
        // Configurações para conexão ao banco
        String url = "jdbc:mysql://localhost:3306/clinica_medica_poo";  // Altere o nome do banco se necessário
        String user = "root";  // Seu usuário do MySQL
        String password = "12345";  // Sua senha do MySQL

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            // Criar os registros necessários no banco

            // Inserindo um funcionário (médico)
            String insertFuncionario = "INSERT INTO funcionario (nome, usuario, senha, sexo, cpf, rua, numero, complemento, bairro, cidade, estado, contato, email, dataNascimento) " +
                    "VALUES ('Dr. João', 'drjoao', '1234', 'M', '12345678900', 'Rua A', '100', 'Apt 101', 'Bairro A', 'Cidade A', 'Estado A', '999999999', 'drjoao@email.com', '1980-01-01')";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(insertFuncionario);
            }

            // Inserindo um paciente
            String insertPaciente = "INSERT INTO paciente (nome, usuario, senha, sexo, cpf, rua, numero, complemento, bairro, cidade, estado, contato, email, dataNascimento) " +
                    "VALUES ('Carlos Silva', 'carlos', 'abcd', 'M', '98765432100', 'Rua B', '200', 'Apt 202', 'Bairro B', 'Cidade B', 'Estado B', '888888888', 'carlos@email.com', '1990-05-15')";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(insertPaciente);
            }

            // Inserindo um convênio
            String insertConvenio = "INSERT INTO convenio (nome, descricao) VALUES ('Unimed', 'Plano de Saúde Unimed')";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(insertConvenio);
            }

            // Inserindo um prontuário
            String insertProntuario = "INSERT INTO prontuario (receituario, observacoes) VALUES ('Receita de medicamento', 'Observações gerais')";
            try (Statement stmt = conn.createStatement()) {
                stmt.executeUpdate(insertProntuario);
            }

            // Verificando o ID do prontuário inserido
            int prontuarioId = -1;
            String selectProntuarioId = "SELECT ID FROM prontuario ORDER BY ID DESC LIMIT 1";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectProntuarioId)) {
                if (rs.next()) {
                    prontuarioId = rs.getInt("ID");
                }
            }

            if (prontuarioId == -1) {
                System.out.println("Erro: Não foi possível obter o ID do prontuário.");
                return;
            }

            // Agora que temos o ID do prontuário, podemos inserir a consulta
            String insertConsulta = "INSERT INTO consulta (sintoma, diagnostico, data_horario, esta_ativa, medico_id, paciente_id, convenio_id, prontuario_id, atendente_id) " +
                    "VALUES ('Febre alta e dor de cabeça', 'Viral', ?, true, 1, 1, 1, ?, 1)";
            try (PreparedStatement ps = conn.prepareStatement(insertConsulta)) {
                ps.setObject(1, LocalDate.now());  // Data e horário atual
                ps.setInt(2, prontuarioId);  // Usando o ID do prontuário inserido
                ps.executeUpdate();
                System.out.println("Consulta criada com sucesso!");
            }

            // Caso precise verificar se a consulta foi criada
            String selectConsulta = "SELECT * FROM consulta WHERE paciente_id = 1";
            try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(selectConsulta)) {
                while (rs.next()) {
                    System.out.println("Consulta ID: " + rs.getInt("id") + " - Sintoma: " + rs.getString("sintoma") + " - Diagnóstico: " + rs.getString("diagnostico"));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
