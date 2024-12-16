/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.edu.imepac.administrativo.telas.Paciente;
import java.sql.*;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import br.edu.imepac.administrativo.daos.ConexaoDatabase;



/**
 *
 * @author User
 */
public class EditarPaciente extends javax.swing.JFrame {
    private Connection connection;

    public EditarPaciente() {
        initComponents();
        this.setLocationRelativeTo(null);
        inicializarConexao();
        carregarIDs();
        configurarEventoComboBox();
        jButton1.addActionListener(e -> savePaciente());  // Associa o botão "Salvar" ao método savePaciente
        jButton3.addActionListener(e -> deletePaciente());// Chama o método para configurar o evento de mudança de ID

    }


    // Método para configurar o evento do JComboBox
    private void configurarEventoComboBox() {
        IDPACIENTE_JCOMBOBOX.addActionListener(e -> carregarDadosPaciente());  // Chama o método para carregar os dados do paciente
    }

    // Método para inicializar a conexão com o banco de dados
    private void inicializarConexao() {
        String url = "jdbc:mysql://localhost:3306/clinica_medica_poo";  // URL de conexão com o banco de dados
        String usuario = "root";  // Usuário do banco de dados
        String senha = "12345";   // Senha do banco de dados

        try {
            connection = DriverManager.getConnection(url, usuario, senha);
            if (connection != null) {
                System.out.println("Conexão com o banco de dados estabelecida com sucesso.");
            } else {
                JOptionPane.showMessageDialog(this, "A conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao conectar ao banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();  // Exibe o erro completo no console para depuração
        }
    }

    // Método para carregar os IDs no JComboBox
    private void carregarIDs() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "SELECT id FROM paciente"; // Verifique se a tabela e o campo estão corretos
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
            IDPACIENTE_JCOMBOBOX.removeAllItems(); // Limpa os itens existentes no JComboBox

            while (rs.next()) {
                int id = rs.getInt("id");
                IDPACIENTE_JCOMBOBOX.addItem(String.valueOf(id)); // Adiciona o ID ao JComboBox
            }

            if (IDPACIENTE_JCOMBOBOX.getItemCount() == 0) {
                JOptionPane.showMessageDialog(this, "Nenhum ID encontrado na tabela de pacientes.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar IDs: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para carregar os dados do paciente ao selecionar o ID
    private void carregarDadosPaciente() {
        // Verifica se há um ID selecionado no JComboBox
        String idSelecionado = (String) IDPACIENTE_JCOMBOBOX.getSelectedItem();

        if (idSelecionado == null || idSelecionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um ID válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "SELECT * FROM paciente WHERE id = ?"; // SQL para buscar o paciente pelo ID
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(idSelecionado));  // Define o ID no SQL
            ResultSet rs = stmt.executeQuery();

            // Verifica se algum dado foi retornado
            if (rs.next()) {
                System.out.println("Dados encontrados para o ID: " + idSelecionado);  // Depuração

                // Preenche os campos com os dados do paciente, para edição
                CampoNome.setText(rs.getString("NOME"));
                CPF.setText(rs.getString("CPF"));
                CampoIdade.setText(String.valueOf(rs.getInt("IDADE")));
                CampoBairro.setText(rs.getString("BAIRRO"));
                CampoCidade.setText(rs.getString("CIDADE"));
                CampoNumero.setText(rs.getString("NUMERO"));
                CampoRua.setText(rs.getString("RUA"));
                CampoEstado.setText(rs.getString("ESTADO"));
                CampoComplemento.setText(rs.getString("COMPLEMENTO"));
                CampoContato.setText(rs.getString("CONTATO"));
                DataNascimento.setText(rs.getString("DATANASCIMENTO"));
                CampoEmail.setText(rs.getString("EMAIL"));

                // Carrega o sexo do paciente no JComboBox
                String sexo = rs.getString("SEXO");
                if (sexo != null) {
                    if ("M".equalsIgnoreCase(sexo)) {
                        jComboBox4.setSelectedItem("Masculino");
                    } else if ("F".equalsIgnoreCase(sexo)) {
                        jComboBox4.setSelectedItem("Feminino");
                    }
                }

                // Ativa os campos para edição (se necessário)
                ativarCamposEdicao(true);
            } else {
                JOptionPane.showMessageDialog(this, "Paciente não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar dados do paciente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace(); // Exibe o erro completo no console
        }
    }

    // Método para ativar/desativar campos de edição
    private void ativarCamposEdicao(boolean ativar) {
        CampoNome.setEditable(ativar);
        CPF.setEditable(ativar);
        CampoIdade.setEditable(ativar);
        CampoBairro.setEditable(ativar);
        CampoCidade.setEditable(ativar);
        CampoNumero.setEditable(ativar);
        CampoRua.setEditable(ativar);
        CampoEstado.setEditable(ativar);
        CampoComplemento.setEditable(ativar);
        CampoContato.setEditable(ativar);
        DataNascimento.setEditable(ativar);
        CampoEmail.setEditable(ativar);
        jComboBox4.setEnabled(ativar);  // Caso queira permitir edição do sexo
    }
    // Método para salvar paciente
    // Método para salvar paciente
    private void savePaciente() {
        // Verifica se a conexão está ativa
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Conexão com o banco de dados não foi estabelecida.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Coleta os dados dos campos
        String nome = CampoNome.getText();
        String sexo = jComboBox4.getSelectedItem().toString();
        String cpf = CPF.getText();
        String idadeTexto = CampoIdade.getText();
        String bairro = CampoBairro.getText();
        String cidade = CampoCidade.getText();
        String numero = CampoNumero.getText();
        String rua = CampoRua.getText();
        String estado = CampoEstado.getText();
        String complemento = CampoComplemento.getText();
        String contato = CampoContato.getText();
        String dataNascimento = DataNascimento.getText();
        String email = CampoEmail.getText();

        // Verifica se os campos obrigatórios foram preenchidos
        if (nome.isEmpty() || sexo.isEmpty() || cpf.isEmpty() || dataNascimento.isEmpty() || idadeTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idade;
        try {
            idade = Integer.parseInt(idadeTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Idade inválida. Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (sexo.equalsIgnoreCase("Masculino")) {
            sexo = "M";
        } else if (sexo.equalsIgnoreCase("Feminino")) {
            sexo = "F";
        } else {
            JOptionPane.showMessageDialog(this, "Sexo inválido. Por favor, selecione Masculino ou Feminino.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String sql = "INSERT INTO paciente (NOME, SEXO, CPF, IDADE, BAIRRO, CIDADE, NUMERO, RUA, ESTADO, COMPLEMENTO, CONTATO, DATANASCIMENTO, EMAIL) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.setString(2, sexo);
            stmt.setString(3, cpf);
            stmt.setInt(4, idade);
            stmt.setString(5, bairro);
            stmt.setString(6, cidade);
            stmt.setString(7, numero);
            stmt.setString(8, rua);
            stmt.setString(9, estado);
            stmt.setString(10, complemento);
            stmt.setString(11, contato);
            stmt.setString(12, dataNascimento);
            stmt.setString(13, email);

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Paciente salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            limparCampos();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar paciente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Método para limpar os campos
    private void limparCampos() {
        CampoNome.setText("");
        CPF.setText("");
        CampoIdade.setText("");
        CampoBairro.setText("");
        CampoCidade.setText("");
        CampoNumero.setText("");
        CampoRua.setText("");
        CampoEstado.setText("");
        CampoComplemento.setText("");
        CampoContato.setText("");
        DataNascimento.setText("");
        CampoEmail.setText("");
        jComboBox4.setSelectedIndex(0);  // Reseta o ComboBox
        CampoNome.requestFocus();
    }

    // Método para excluir paciente
    // Método para excluir paciente
    private void deletePaciente() {
        String idSelecionado = IDPACIENTE_JCOMBOBOX.getSelectedItem().toString();

        if (idSelecionado == null || idSelecionado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um ID válido.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String sql = "DELETE FROM paciente WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, Integer.parseInt(idSelecionado));
            int rowsDeleted = stmt.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "Paciente apagado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                carregarIDs();  // Recarrega os IDs no JComboBox após a exclusão
                limparCampos(); // Limpa os campos do formulário
            } else {
                JOptionPane.showMessageDialog(this, "Nenhum paciente encontrado com o ID informado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao apagar paciente: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID inválido. Por favor, selecione um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
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
        jLabel4 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        CampoNome = new javax.swing.JTextField();
        CPF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        DataNascimento = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        CampoEstado = new javax.swing.JTextField();
        CampoBairro = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        CampoRua = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        CampoNumero = new javax.swing.JTextField();
        CampoCidade = new javax.swing.JTextField();
        CampoContato = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        CampoEmail = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        BotaoCancelar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        CampoIdade = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        CampoComplemento = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        IDPACIENTE_JCOMBOBOX = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Editar Paciente");

        jLabel2.setText("Nome");

        jLabel6.setText("CPF");

        jLabel8.setText("Data de Nascimento");

        DataNascimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DataNascimentoActionPerformed(evt);
            }
        });

        jLabel9.setText("Estado");

        jLabel10.setText("Bairro");

        jLabel7.setText("Rua");

        jLabel11.setText("Número");

        jLabel12.setText("Cidade");

        CampoCidade.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CampoCidadeActionPerformed(evt);
            }
        });

        jLabel14.setText("Email");

        jLabel13.setText("Contato");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Feminino" }));

        jLabel18.setText("Sexo");

        jButton1.setText("Salvar");

        BotaoCancelar.setText("Cancelar");
        BotaoCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BotaoCancelarActionPerformed(evt);
            }
        });

        jLabel3.setText("Idade");

        jLabel15.setText("Complemento");

        jButton3.setText("Apagar");

        jLabel5.setText("ID Paciente");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BotaoCancelar)
                                .addGap(95, 95, 95))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jLabel11)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(CampoNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel7)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(CampoRua, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel9)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(CampoEstado))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jLabel10)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(CampoBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel12)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(CampoCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jLabel15)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(CampoComplemento)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel13)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(CampoContato, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jLabel8)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(DataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel14)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(CampoEmail)))
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jLabel5)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(IDPACIENTE_JCOMBOBOX, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jLabel6)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(CPF, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jLabel3)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(CampoIdade))
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                        .addComponent(jLabel2)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(CampoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addGap(18, 18, 18)
                                                        .addComponent(jLabel18)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(19, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel5)
                                        .addComponent(IDPACIENTE_JCOMBOBOX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(CampoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel18)
                                        .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel6)
                                        .addComponent(CPF, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel3)
                                        .addComponent(CampoIdade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel10)
                                        .addComponent(CampoBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(CampoCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel11)
                                        .addComponent(CampoNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel7)
                                        .addComponent(CampoRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(CampoEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel13)
                                        .addComponent(CampoContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel15)
                                        .addComponent(CampoComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(DataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel8)
                                        .addComponent(jLabel14)
                                        .addComponent(CampoEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(BotaoCancelar)
                                        .addComponent(jButton3))
                                .addGap(24, 24, 24))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel4)
                                                .addGap(0, 298, Short.MAX_VALUE))
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>

    private void DataNascimentoActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void CampoCidadeActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void BotaoCancelarActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        BotaoCancelar.addActionListener(e -> cancelar());
    }
    // Método para o botão de cancelar
    private void cancelar() {
       this.dispose();
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
            java.util.logging.Logger.getLogger(EditarPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarPaciente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JButton BotaoCancelar;
    private javax.swing.JTextField CPF;
    private javax.swing.JTextField CampoBairro;
    private javax.swing.JTextField CampoCidade;
    private javax.swing.JTextField CampoComplemento;
    private javax.swing.JTextField CampoContato;
    private javax.swing.JTextField CampoEmail;
    private javax.swing.JTextField CampoEstado;
    private javax.swing.JTextField CampoIdade;
    private javax.swing.JTextField CampoNome;
    private javax.swing.JTextField CampoNumero;
    private javax.swing.JTextField CampoRua;
    private javax.swing.JTextField DataNascimento;
    private javax.swing.JComboBox<String> IDPACIENTE_JCOMBOBOX;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration
}
