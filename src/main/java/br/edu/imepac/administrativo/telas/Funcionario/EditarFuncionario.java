/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.edu.imepac.administrativo.telas.Funcionario;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import br.edu.imepac.administrativo.daos.ConexaoDatabase;
import java.sql.*;
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class EditarFuncionario extends javax.swing.JFrame {

    /**
     * Creates new form EditarFuncionario
     */
    public EditarFuncionario() {
        initComponents();
        this.setLocationRelativeTo(null);
        inicializarComboBoxes();
        jButton_Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvarFuncionario();  // Chama o método de salvar quando o botão for pressionado
            }
        });

        // Adiciona o ouvinte de evento para o botão "Apagar"
        jButton1_Apagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apagarFuncionario();  // Chama o método de apagar quando o botão for pressionado
            }
        });
    }

    public class ConexaoDatabase {

        private static final String URL = "jdbc:mysql://localhost:3306/clinica_medica_poo"; // exemplo para MySQL
        private static final String USER = "root";
        private static final String PASSWORD = "12345";

        public static Connection getConnection() throws SQLException {
            try {
                Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
                return conn;
            } catch (SQLException e) {
                System.err.println("Erro ao conectar ao banco de dados: " + e.getMessage());
                throw e;
            }
        }
    }

    // Popula o ComboBox de IDs de Funcionário
    private void popularComboBoxIdFuncionario() {
        try (Connection conn = ConexaoDatabase.getConnection()) {
            // Atualizando a consulta para buscar 'id' e 'nome' da tabela 'funcionario'
            String sql = "SELECT id, nome FROM funcionario";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Limpar os itens existentes no ComboBox antes de adicionar novos
            jComboBox1.removeAllItems();

            while (rs.next()) {
                String idFuncionario = rs.getString("id");
                String nomeFuncionario = rs.getString("nome");

                // Concatenar id e nome para exibir no ComboBox
                String itemComboBox = idFuncionario + " - " + nomeFuncionario;
                jComboBox1.addItem(itemComboBox);  // Adiciona o item ao ComboBox
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar IDs dos funcionários: " + e.getMessage());
        }
    }


    // Preenche os campos de texto com os dados do funcionário
    private void preencherCamposFuncionario(String idFuncionario) {
        try (Connection conn = ConexaoDatabase.getConnection()) {
            String sql = "SELECT * FROM funcionario WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, idFuncionario);  // Configura o id para a consulta
            ResultSet rs = stmt.executeQuery();

            // Verifica se há algum resultado
            if (rs.next()) {
                // Preencher as caixas de texto com os dados do banco
                jTextField49.setText(rs.getString("usuario") != null ? rs.getString("usuario") : "");  // Preenche o JTextField com o nome de usuário
                jPasswordField5.setText(rs.getString("senha") != null ? rs.getString("senha") : "");  // Preenche o campo de senha
                jTextField50.setText(rs.getString("nome") != null ? rs.getString("nome") : "");      // Preenche o campo 'nome'
                jComboBox20.setSelectedItem(rs.getString("sexo") != null && rs.getString("sexo").equals("M") ? "Masculino" : "Feminino");  // Preenche o ComboBox de sexo
                jTextField52.setText(rs.getString("cpf") != null ? rs.getString("cpf") : "");       // Preenche o campo 'cpf'
                jTextField53.setText(rs.getString("rua") != null ? rs.getString("rua") : "");       // Preenche o campo 'rua'
                jTextField1.setText(rs.getString("numero") != null ? rs.getString("numero") : "");     // Preenche o campo 'número'
                jTextField56.setText(rs.getString("bairro") != null ? rs.getString("bairro") : "");    // Preenche o campo 'bairro'
                jTextField58.setText(rs.getString("cidade") != null ? rs.getString("cidade") : "");    // Preenche o campo 'cidade'
                jTextField55.setText(rs.getString("estado") != null ? rs.getString("estado") : "");    // Preenche o campo 'estado'
                jTextField59.setText(rs.getString("contato") != null ? rs.getString("contato") : "");   // Preenche o campo 'contato'
                jTextField60.setText(rs.getString("email") != null ? rs.getString("email") : "");     // Preenche o campo 'email'
                jTextField54.setText(rs.getString("dataNascimento") != null ? rs.getString("dataNascimento") : "");  // Preenche o campo 'dataNascimento'
                jTextField51.setText(rs.getString("idade") != null ? rs.getString("idade") : "");     // Preenche o campo 'idade'
                jComboBox18.setSelectedItem(rs.getString("especialidade") != null ? rs.getString("especialidade") : "");  // Preenche o ComboBox de especialidade
                jComboBox19.setSelectedItem(rs.getString("perfil") != null ? rs.getString("perfil") : "");
                String idFuncionarioStr = (String) jComboBox1.getSelectedItem();
                String id = idFuncionarioStr.split(" - ")[0];// Preenche o ComboBox de perfil

                // Preenche o ComboBox de tipo de funcionário baseado no ID
                String tipoFuncionario = rs.getInt("tipoFuncionario_id") == 1 ? "Atendente" :
                        rs.getInt("tipoFuncionario_id") == 2 ? "Médico" : "Outro";
                jComboBox17.setSelectedItem(tipoFuncionario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar as informações do funcionário: " + e.getMessage());
        }
    }

    // Salva as alterações do funcionário no banco de dados
    // Salva ou atualiza o funcionário no banco de dados
    private void salvarFuncionario() {
        // Extrair apenas o ID do item selecionado no ComboBox
        String idFuncionarioStr = (String) jComboBox1.getSelectedItem();
        String idFuncionario = idFuncionarioStr != null ? idFuncionarioStr.split(" - ")[0] : null;

        String nome = jTextField50.getText();
        String usuario = jTextField49.getText();
        String senha = new String(jPasswordField5.getPassword());
        String sexo = (String) jComboBox20.getSelectedItem();
        String cpf = jTextField52.getText();
        String rua = jTextField53.getText();
        String numero = jTextField1.getText();
        String bairro = jTextField56.getText();
        String cidade = jTextField58.getText();
        String estado = jTextField55.getText();
        String contato = jTextField59.getText();
        String email = jTextField60.getText();
        String dataNascimento = jTextField54.getText();
        String idade = jTextField51.getText();
        String especialidade = (String) jComboBox18.getSelectedItem();
        String perfil = (String) jComboBox19.getSelectedItem();
        String tipoFuncionario = (String) jComboBox17.getSelectedItem();

        // Determina o tipo de funcionário
        int tipoFuncionarioId = tipoFuncionario.equals("Atendente") ? 1 :
                tipoFuncionario.equals("Médico") ? 2 : 3;
        String sexoCodigo = sexo.equals("Masculino") ? "M" : "F";

        try (Connection conn = ConexaoDatabase.getConnection()) {
            conn.setAutoCommit(false); // Inicia uma transação

            String sqlFuncionario;
            boolean isUpdate = idFuncionario != null && !idFuncionario.isEmpty();

            if (isUpdate) {
                // Atualiza o funcionário existente
                sqlFuncionario = "UPDATE funcionario SET usuario = ?, senha = ?, nome = ?, sexo = ?, cpf = ?, rua = ?, numero = ?, " +
                        "bairro = ?, cidade = ?, estado = ?, contato = ?, email = ?, dataNascimento = ?, tipoFuncionario_id = ?, idade = ?, especialidade = ?, perfil = ? WHERE id = ?";
            } else {
                // Insere um novo funcionário
                sqlFuncionario = "INSERT INTO funcionario (usuario, senha, nome, sexo, cpf, rua, numero, bairro, cidade, estado, contato, email, dataNascimento, tipoFuncionario_id, idade, especialidade, perfil) " +
                        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            }

            try (PreparedStatement stmtFuncionario = conn.prepareStatement(sqlFuncionario, Statement.RETURN_GENERATED_KEYS)) {
                stmtFuncionario.setString(1, usuario);
                stmtFuncionario.setString(2, senha);
                stmtFuncionario.setString(3, nome);
                stmtFuncionario.setString(4, sexoCodigo);
                stmtFuncionario.setString(5, cpf);
                stmtFuncionario.setString(6, rua);
                stmtFuncionario.setString(7, numero);
                stmtFuncionario.setString(8, bairro);
                stmtFuncionario.setString(9, cidade);
                stmtFuncionario.setString(10, estado);
                stmtFuncionario.setString(11, contato);
                stmtFuncionario.setString(12, email);
                stmtFuncionario.setString(13, dataNascimento);
                stmtFuncionario.setInt(14, tipoFuncionarioId);
                stmtFuncionario.setString(15, idade);
                stmtFuncionario.setString(16, especialidade);
                stmtFuncionario.setString(17, perfil);

                if (isUpdate) {
                    stmtFuncionario.setString(18, idFuncionario);
                }

                int rowsAffected = stmtFuncionario.executeUpdate();

                if (rowsAffected > 0) {
                    if (!isUpdate) {
                        // Recupera o ID gerado no caso de uma inserção
                        ResultSet rs = stmtFuncionario.getGeneratedKeys();
                        if (rs.next()) {
                            idFuncionario = rs.getString(1);
                        }
                    }

                    conn.commit();
                    JOptionPane.showMessageDialog(this, "Funcionário salvo com sucesso!");
                    popularComboBoxIdFuncionario(); // Atualiza o ComboBox após salvar
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao salvar o funcionário.");
                }
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o funcionário: " + e.getMessage());
        }
    }



    // Apaga um funcionário do banco de dados
    private void apagarFuncionario() {
        // Extrair apenas o ID do item selecionado no ComboBox (id - nome)
        String idFuncionarioStr = (String) jComboBox1.getSelectedItem();
        String idFuncionario = idFuncionarioStr != null ? idFuncionarioStr.split(" - ")[0] : null;

        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja apagar o funcionário?",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection conn = ConexaoDatabase.getConnection()) {
                conn.setAutoCommit(false);

                // Deleta o funcionário pelo id
                String sql = "DELETE FROM funcionario WHERE id = ?";
                try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, idFuncionario);

                    int rowsDeleted = stmt.executeUpdate();
                    if (rowsDeleted > 0) {
                        conn.commit();
                        JOptionPane.showMessageDialog(this, "Funcionário apagado com sucesso!");
                        popularComboBoxIdFuncionario(); // Atualiza o ComboBox após excluir
                    } else {
                        JOptionPane.showMessageDialog(this, "Erro ao apagar o funcionário.");
                    }
                } catch (SQLException e) {
                    conn.rollback();
                    throw e;
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Erro ao apagar o funcionário: " + e.getMessage());
            }
        }
    }




    // Método para pegar o tipo de funcionário baseado no ID
    private int getTipoFuncionarioId(Connection conn, String idFuncionario) throws SQLException {
        String sql = "SELECT tipoFuncionario_id FROM funcionario WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, idFuncionario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("tipoFuncionario_id");
                }
            }
        }
        return 0; // Retorna 0 se não encontrar
    }



    // Popula o ComboBox de especialidades
    private void popularComboBoxEspecialidade() {
        try (Connection conn = ConexaoDatabase.getConnection()) {
            // Atualizando a consulta para buscar da tabela especialidade
            String sql = "SELECT nome FROM especialidade";  // Supondo que a tabela especialidade tenha uma coluna 'nome'
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String especialidade = rs.getString("nome");  // Ajuste conforme o nome da coluna
                jComboBox18.addItem(especialidade);  // Adiciona a especialidade ao ComboBox
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar especialidades: " + e.getMessage());
        }
    }


    // Popula o ComboBox de perfis
    private void popularComboBoxPerfil() {
        try (Connection conn = ConexaoDatabase.getConnection()) {
            // Atualizando a consulta para buscar da tabela perfil
            String sql = "SELECT nome FROM perfil";  // Supondo que a tabela 'perfil' tenha uma coluna 'nome' para armazenar os perfis
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String perfil = rs.getString("nome");  // Ajuste conforme o nome da coluna na tabela 'perfil'
                jComboBox19.addItem(perfil);  // Adiciona o perfil ao ComboBox
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar perfis: " + e.getMessage());
        }
    }


    // Inicializa os ComboBoxes com os dados do banco
    private void inicializarComboBoxes() {
        popularComboBoxIdFuncionario();    // Popula o ComboBox de IDs
        popularComboBoxEspecialidade();   // Popula o ComboBox de especialidades
        popularComboBoxPerfil();          // Popula o ComboBox de perfis
        // Popula o ComboBox de tipo de funcionário
        jComboBox1.addActionListener(e -> {
            String idFuncionario = (String) jComboBox1.getSelectedItem();
            preencherCamposFuncionario(idFuncionario);  // Atualiza os campos com os dados do funcionário selecionado
        });
    }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        jTextField49 = new javax.swing.JTextField();
        jLabel71 = new javax.swing.JLabel();
        jTextField50 = new javax.swing.JTextField();
        jLabel72 = new javax.swing.JLabel();
        jPasswordField5 = new javax.swing.JPasswordField();
        jLabel73 = new javax.swing.JLabel();
        jTextField51 = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        jTextField52 = new javax.swing.JTextField();
        jLabel75 = new javax.swing.JLabel();
        jTextField53 = new javax.swing.JTextField();
        jLabel76 = new javax.swing.JLabel();
        jTextField54 = new javax.swing.JTextField();
        jTextField55 = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        jLabel78 = new javax.swing.JLabel();
        jTextField56 = new javax.swing.JTextField();
        jLabel80 = new javax.swing.JLabel();
        jTextField58 = new javax.swing.JTextField();
        jLabel81 = new javax.swing.JLabel();
        jTextField59 = new javax.swing.JTextField();
        jLabel82 = new javax.swing.JLabel();
        jTextField60 = new javax.swing.JTextField();
        jLabel83 = new javax.swing.JLabel();
        jComboBox17 = new javax.swing.JComboBox<>();
        jLabel84 = new javax.swing.JLabel();
        jComboBox18 = new javax.swing.JComboBox<>();
        jLabel85 = new javax.swing.JLabel();
        jComboBox19 = new javax.swing.JComboBox<>();
        jLabel86 = new javax.swing.JLabel();
        jComboBox20 = new javax.swing.JComboBox<>();
        jButton_Salvar = new javax.swing.JButton();
        jButton10_Cancel = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jButton1_Apagar = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jTextField1 = new javax.swing.JTextField();
        jLabel79 = new javax.swing.JLabel();
        jLabel87 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel70.setText("Usuário");

        jTextField49.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField49ActionPerformed(evt);
            }
        });

        jLabel71.setText("Nome");

        jTextField50.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField50ActionPerformed(evt);
            }
        });

        jLabel72.setText("Senha");

        jPasswordField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jPasswordField5ActionPerformed(evt);
            }
        });

        jLabel73.setText("Idade");

        jTextField51.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField51ActionPerformed(evt);
            }
        });

        jLabel74.setText("CPF");

        jTextField52.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField52ActionPerformed(evt);
            }
        });

        jLabel75.setText("Rua");

        jLabel76.setText("Data de Nascimento");

        jTextField54.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField54ActionPerformed(evt);
            }
        });

        jLabel77.setText("Estado");

        jLabel78.setText("Bairro");

        jLabel80.setText("Cidade");

        jLabel81.setText("Contato");

        jLabel82.setText("Email");

        jLabel83.setText("Tipo de Funcionario");

        jComboBox17.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Atendente", "Médico", "Outro", " " }));
        jComboBox17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox17ActionPerformed(evt);
            }
        });

        jLabel84.setText("Especialidade");

        jComboBox18.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar", " " }));
        jComboBox18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox18ActionPerformed(evt);
            }
        });

        jLabel85.setText("Perfil");

        jComboBox19.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar", " " }));
        jComboBox19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox19ActionPerformed(evt);
            }
        });

        jLabel86.setText("Sexo");

        jComboBox20.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));
        jComboBox20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox20ActionPerformed(evt);
            }
        });

        jButton_Salvar.setText("Salvar");

        jButton10_Cancel.setText("Cancelar");
        jButton10_Cancel.addActionListener(e -> {
            this.dispose();  // Fecha a janela
        });

        jLabel1.setText("Funcionario");

        jButton1_Apagar.setText("Apagar");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel79.setText("Numero");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                        .addComponent(jLabel73)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                        .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addGap(18, 18, 18)
                                                                        .addComponent(jLabel76)
                                                                        .addGap(208, 208, 208))
                                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                        .addComponent(jLabel81)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jTextField59, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jLabel82)
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(jTextField60))
                                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jLabel75)
                                                                                .addComponent(jLabel79))
                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                                .addComponent(jTextField53, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                                .addGroup(jPanel6Layout.createSequentialGroup()
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addComponent(jLabel78)
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                                        .addComponent(jTextField56))
                                                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                                                        .addGap(18, 18, 18)
                                                                                        .addComponent(jLabel80)
                                                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                        .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addComponent(jLabel1)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addComponent(jLabel83)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                .addComponent(jLabel84)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addComponent(jLabel85)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(26, 26, 26)
                                                                .addComponent(jLabel86)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                                .addComponent(jLabel72)
                                                                                .addGap(18, 18, 18)
                                                                                .addComponent(jPasswordField5, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                                .addComponent(jLabel70)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                                .addGap(26, 26, 26)
                                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                                .addComponent(jLabel71)
                                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                                                .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                                                .addComponent(jLabel74)
                                                                                .addGap(26, 26, 26)
                                                                                .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                                                .addComponent(jTextField54, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jLabel77)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                        .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGap(106, 106, 106)
                                                .addComponent(jButton_Salvar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton1_Apagar)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton10_Cancel)))
                                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
                jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                                .addContainerGap(9, Short.MAX_VALUE)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel1)
                                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel70)
                                        .addComponent(jTextField49, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel71)
                                        .addComponent(jTextField50, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel72)
                                        .addComponent(jPasswordField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel74)
                                        .addComponent(jTextField52, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel73)
                                        .addComponent(jTextField51, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel76)
                                        .addComponent(jTextField54, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField55, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel77))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel75)
                                        .addComponent(jTextField53, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel78)
                                        .addComponent(jTextField56, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel80)
                                        .addComponent(jTextField58, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel79))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel81)
                                        .addComponent(jTextField59, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel82)
                                        .addComponent(jTextField60, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel84)
                                        .addComponent(jComboBox18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel83))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jComboBox19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel85)
                                        .addComponent(jComboBox20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel86))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton_Salvar)
                                        .addComponent(jButton10_Cancel)
                                        .addComponent(jButton1_Apagar))
                                .addGap(17, 17, 17))
        );

        jLabel87.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel87.setForeground(new java.awt.Color(255, 255, 255));
        jLabel87.setText("Edição de Funcionário");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel87)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(40, Short.MAX_VALUE)
                                .addComponent(jLabel87)
                                .addGap(18, 18, 18)
                                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void jPasswordField5ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField51ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField54ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jComboBox17ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jComboBox20ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jComboBox18ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jComboBox19ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField49ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField50ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField52ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditarFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarFuncionario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton jButton10_Cancel;
    private javax.swing.JButton jButton1_Apagar;
    private javax.swing.JButton jButton_Salvar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox17;
    private javax.swing.JComboBox<String> jComboBox18;
    private javax.swing.JComboBox<String> jComboBox19;
    private javax.swing.JComboBox<String> jComboBox20;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPasswordField jPasswordField5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField49;
    private javax.swing.JTextField jTextField50;
    private javax.swing.JTextField jTextField51;
    private javax.swing.JTextField jTextField52;
    private javax.swing.JTextField jTextField53;
    private javax.swing.JTextField jTextField54;
    private javax.swing.JTextField jTextField55;
    private javax.swing.JTextField jTextField56;
    private javax.swing.JTextField jTextField58;
    private javax.swing.JTextField jTextField59;
    private javax.swing.JTextField jTextField60;
    // End of variables declaration
}
