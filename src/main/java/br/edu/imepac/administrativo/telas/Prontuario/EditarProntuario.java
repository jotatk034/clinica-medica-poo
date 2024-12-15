package br.edu.imepac.administrativo.telas.Prontuario;

import br.edu.imepac.administrativo.daos.ConexaoDatabase;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.*;
import java.sql.PreparedStatement;

public class EditarProntuario extends javax.swing.JFrame {

    public EditarProntuario() {
        initComponents();
        this.setLocationRelativeTo(null);
        preencherComboBoxMedicos();
        preencherComboBoxPacientes();
        preencherComboBoxIdConsulta();
        // Preencher os campos de Receituário, Exames e Observações com dados já existentes
        preencherCampos();
    }

    private void preencherComboBoxMedicos() {
        try (Connection conn = ConexaoDatabase.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT nome FROM medico";
            ResultSet rs = stmt.executeQuery(query);

            // Adicionando os médicos ao ComboBox
            while (rs.next()) {
                jComboBox1.addItem(rs.getString("nome"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherComboBoxPacientes() {
        try (Connection conn = ConexaoDatabase.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT nome FROM paciente";
            ResultSet rs = stmt.executeQuery(query);

            // Adicionando os pacientes ao ComboBox
            while (rs.next()) {
                jComboBox2.addItem(rs.getString("nome"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void preencherComboBoxIdConsulta() {
        try (Connection conn = ConexaoDatabase.getConnection();
             Statement stmt = conn.createStatement()) {
            String query = "SELECT ID FROM prontuario";
            ResultSet rs = stmt.executeQuery(query);

            // Adicionando os IDs de consulta ao ComboBox
            while (rs.next()) {
                jComboBox3.addItem(rs.getString("ID"));
            }

            // Adicionando listener para atualizar os campos quando mudar o ID da consulta
            jComboBox3.addActionListener(evt -> preencherCampos());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para preencher os campos de texto com dados existentes
    private void preencherCampos() {
        try (Connection conn = ConexaoDatabase.getConnection()) {
            // Recuperando os dados do prontuário
            String idConsulta = jComboBox3.getSelectedItem().toString();
            String query = "SELECT * FROM prontuario WHERE ID = ?";

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, idConsulta);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Preenchendo os campos de texto com os dados recuperados
                    jEditorPane2.setText(rs.getString("receituario"));
                    jEditorPane3.setText(rs.getString("exames"));
                    jEditorPane4.setText(rs.getString("observacoes"));
                } else {
                    // Caso não encontre dados para o ID selecionado
                    jEditorPane2.setText("");
                    jEditorPane3.setText("");
                    jEditorPane4.setText("");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void salvarDados() {
        String receituario = jEditorPane2.getText();
        String exames = jEditorPane3.getText();
        String observacoes = jEditorPane4.getText();
        String medico = jComboBox1.getSelectedItem().toString();
        String paciente = jComboBox2.getSelectedItem().toString();
        String idConsulta = jComboBox3.getSelectedItem().toString();

        try (Connection conn = ConexaoDatabase.getConnection()) {
            String query = "UPDATE prontuario SET medico = ?, paciente = ?, receituario = ?, exames = ?, observacoes = ? WHERE id_consulta = ?";

            // Preparando a statement para atualizar os dados
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, medico);      // Médico
                stmt.setString(2, paciente);    // Paciente
                stmt.setString(3, receituario); // Receituário
                stmt.setString(4, exames);      // Exames
                stmt.setString(5, observacoes); // Observações
                stmt.setString(6, idConsulta);  // ID da consulta

                // Executando a atualização
                stmt.executeUpdate();

                // Exibindo uma mensagem de sucesso
                JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar os dados.");
        }
    }

    // Método main para iniciar a aplicação
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EditarProntuario().setVisible(true);
        });
    }

    // Código de inicialização do formulário
    @SuppressWarnings("unchecked")
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jEditorPane2 = new javax.swing.JEditorPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jEditorPane3 = new javax.swing.JEditorPane();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jEditorPane4 = new javax.swing.JEditorPane();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18));
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Editar Prontuário");

        jLabel3.setText("Dados de Consulta Agendada");

        jScrollPane2.setViewportView(jEditorPane2);

        jLabel2.setText("Receituário");

        jLabel5.setText("Exames");

        jScrollPane3.setViewportView(jEditorPane3);

        jLabel6.setText("Observações");

        jScrollPane4.setViewportView(jEditorPane4);

        jButton1.setText("Salvar");
        jButton1.addActionListener(evt -> salvarDados());

        jButton2.setText("Cancelar");

        jLabel7.setText("Paciente");

        jLabel8.setText("Médico");

        jLabel9.setText("ID Consulta");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addComponent(jLabel3)
                                                                .addGap(140, 140, 140)
                                                                .addComponent(jLabel6))
                                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGap(135, 135, 135)
                                                                .addComponent(jButton1)
                                                                .addGap(29, 29, 29)
                                                                .addComponent(jButton2)))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel7)
                                                                .addComponent(jLabel8)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                                        .addComponent(jComboBox2, javax.swing.GroupLayout.Alignment.LEADING, 0, 158, Short.MAX_VALUE)
                                                                        .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                        .addComponent(jComboBox3, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(72, 72, 72)
                                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(34, 34, 34))
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(16, 16, 16)
                                        .addComponent(jLabel9)
                                        .addContainerGap(561, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(40, 40, 40)
                                                .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(18, 18, 18)
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(132, 132, 132)
                                        .addComponent(jLabel9)
                                        .addContainerGap(282, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JEditorPane jEditorPane2;
    private javax.swing.JEditorPane jEditorPane3;
    private javax.swing.JEditorPane jEditorPane4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
}
