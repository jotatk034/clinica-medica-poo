/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package br.edu.imepac.administrativo.telas.Consulta;
import java.sql.*;
import javax.swing.JOptionPane;


/**
 *
 * @author User
 */
public class CadastrarConsulta extends javax.swing.JFrame {

    private Statement Conexao;

    private void carregarComboboxes() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // Estabelece a conexão com o banco de dados
            conn = conectarBanco();  // Usa o método conectarBanco() para obter a conexão

            // Consulta para obter os médicos
            String sqlMedicos = "SELECT nome FROM medico";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlMedicos);

            // Preencher o JComboBox de médicos
            while (rs.next()) {
                jComboBox2.addItem(rs.getString("nome"));
            }

            // Consulta para obter os pacientes
            String sqlPacientes = "SELECT nome FROM paciente";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlPacientes);

            // Preencher o JComboBox de pacientes
            while (rs.next()) {
                jComboBox3.addItem(rs.getString("nome"));
            }

            // Consulta para obter os convênios
            String sqlConvenios = "SELECT nome FROM convenio";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlConvenios);

            // Preencher o JComboBox de convênios
            while (rs.next()) {
                jComboBox4.addItem(rs.getString("nome"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Fechar recursos
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para verificar ou obter o ID do prontuário com base no paciente e médico
    private int getProntuarioId(String nomePaciente, String nomeMedico) {
        int id = 0;
        try {
            Connection conn = conectarBanco();

            // Verifica se já existe um prontuário para o paciente e médico
            String sql = "SELECT ID FROM prontuario WHERE paciente = ? AND medico = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomePaciente);
            stmt.setString(2, nomeMedico);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Se já existir, retorna o ID do prontuário
                id = rs.getInt("ID");
            } else {
                // Caso contrário, cria um novo prontuário
                String insertSql = "INSERT INTO prontuario (paciente, medico) VALUES (?, ?)";
                PreparedStatement insertStmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                insertStmt.setString(1, nomePaciente);
                insertStmt.setString(2, nomeMedico);
                insertStmt.executeUpdate();

                // Obtém o ID do prontuário recém-criado
                ResultSet generatedKeys = insertStmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
                insertStmt.close();
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao obter o ID do prontuário: " + e.getMessage());
        }
        return id;
    }

    // Método para estabelecer a conexão com o banco de dados
    public Connection conectarBanco() {
        Connection conexao = null;
        try {
            // URL de conexão com o banco (altere conforme necessário)
            String url = "jdbc:mysql://localhost:3306/clinica_medica_poo";
            String usuario = "root";  // Usuário do MySQL
            String senha = "12345";    // Senha do MySQL

            // Estabelecendo a conexão
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão estabelecida com o banco de dados!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
        return conexao;
    }

    /** Creates new form CadastrarConsulta */
    public CadastrarConsulta() {
        initComponents();
        this.setLocationRelativeTo(null);
        carregarComboboxes();

        // Adiciona o ActionListener ao botão "Salvar"
        jToggleButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvarConsulta();  // Chama o método que faz a inserção no banco
            }
        });
    }

    // Método para salvar consulta no banco
    private void salvarConsulta() {
        // Captura os dados dos campos
        String dataHora = jTextField1.getText();  // Data e Hora
        String sintomas = jTextField2.getText();  // Sintomas
        String retorno = jComboBox1.getSelectedItem().toString();  // Retorno
        String medico = jComboBox2.getSelectedItem().toString();  // Médico
        String paciente = jComboBox3.getSelectedItem().toString();  // Paciente
        String convenio = jComboBox4.getSelectedItem().toString();  // Convênio

        // Converte 'Sim' para 1 e 'Não' para 0 para o campo 'retorno'
        int retornoValor = retorno.equals("Sim") ? 1 : 0;

        // Obtém os IDs do médico, paciente e convênio
        int medicoId = getMedicoId(medico);  // Método para pegar ID do médico
        int pacienteId = getPacienteId(paciente);  // Método para pegar ID do paciente
        int convenioId = getConvenioId(convenio);  // Método para pegar ID do convênio

        // Definir prontuario_id como NULL ou um valor válido
        int prontuarioId = getProntuarioId(paciente, medico); // Novo método que pode retornar o ID do prontuário ou -1 (nulo)

        // Conecta com o banco
        Connection conexao = conectarBanco();
        if (conexao != null) {
            // Realiza a inserção no banco
            try {
                String sql = "INSERT INTO consulta (SINTOMA, DIAGNOSTICO, data_horario, esta_ativa, medico_id, paciente_id, convenio_id, prontuario_id, retorno) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conexao.prepareStatement(sql);

                // Atribui os valores aos parâmetros da consulta
                stmt.setString(1, sintomas);  // Sintomas
                stmt.setString(2, "Exemplo de Diagnóstico");  // Diagnóstico (exemplo)
                stmt.setString(3, dataHora);  // Data e Hora
                stmt.setInt(4, 1);  // Esta ativa (Exemplo)
                stmt.setInt(5, medicoId);  // Médico
                stmt.setInt(6, pacienteId);  // Paciente
                stmt.setInt(7, convenioId);  // Convênio
                if (prontuarioId != -1) {
                    stmt.setInt(8, prontuarioId);  // Prontuário (se encontrado)
                } else {
                    stmt.setNull(8, java.sql.Types.INTEGER);  // Caso não tenha prontuário, insere null
                }
                stmt.setInt(9, retornoValor);  // Retorno (0 ou 1)

                // Executa a inserção
                stmt.executeUpdate();

                // Exibe a mensagem de sucesso
                JOptionPane.showMessageDialog(null, "Consulta salva com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                // Limpa os campos para nova inserção
                limparCampos();

            } catch (SQLException e) {
                System.out.println("Erro ao salvar a consulta: " + e.getMessage());
                JOptionPane.showMessageDialog(null, "Erro ao salvar a consulta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para limpar os campos
    private void limparCampos() {
        // Limpa os campos de texto
        jTextField1.setText("");  // Data e Hora
        jTextField2.setText("");  // Sintomas

        // Limpa os JComboBox
        jComboBox1.setSelectedIndex(0);  // Retorno (seleciona o primeiro item)
        jComboBox2.setSelectedIndex(0);  // Médico (seleciona o primeiro item)
        jComboBox3.setSelectedIndex(0);  // Paciente (seleciona o primeiro item)
        jComboBox4.setSelectedIndex(0);  // Convênio (seleciona o primeiro item)
    }

    // Método para obter o ID do prontuário com base no paciente e médico
    private int getProntuarioId(int pacienteId, String nomeMedico) {
        int id = -1;  // Retorna -1 se não encontrar o prontuário
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT ID FROM prontuario WHERE paciente_id = ? AND medico_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, pacienteId);
            stmt.setString(2, nomeMedico);  // Usando nome do médico, ajuste conforme sua tabela
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("ID");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o ID do prontuário: " + e.getMessage());
        }
        return id;
    }

    // Método para obter o ID do médico com base no nome
    private int getMedicoId(String nomeMedico) {
        int id = 0;
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT id FROM medico WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeMedico);  // Passa o nome do médico para o SQL
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            } else {
                System.out.println("Médico não encontrado: " + nomeMedico);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o ID do médico: " + e.getMessage());
        }
        return id;
    }

    // Método para obter o ID do paciente com base no nome
    private int getPacienteId(String nomePaciente) {
        int id = 0;
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT id FROM paciente WHERE nome = ?"; // Corrigido para buscar pelo nome
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomePaciente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o ID do paciente: " + e.getMessage());
        }
        return id;
    }

    // Método para obter o ID do convênio com base no nome
    private int getConvenioId(String nomeConvenio) {
        int id = 0;
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT id FROM convenio WHERE nome = ?"; // Corrigido para buscar pelo nome
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeConvenio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar o ID do convênio: " + e.getMessage());
        }
        return id;
    }




        /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jComboBox3 = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jComboBox4 = new javax.swing.JComboBox<>();
        jToggleButton1 = new javax.swing.JToggleButton();
        jButton1_Cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cadastro de Consulta");

        jLabel2.setText("Data e Hora");

        jLabel3.setText("Sintômas");

        jLabel4.setText("Retorno");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sim", "Não", " " }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        jLabel5.setText("Médico");

        jComboBox2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione o médico" }));

        jLabel6.setText("Paciente");

        jComboBox3.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione o paciente", " " }));

        jLabel7.setText("Convênio");

        jComboBox4.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione o convenio", " " }));

        jToggleButton1.setText("Salvar");

        jButton1_Cancelar.setText("Cancelar");
        jButton1_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_CancelarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jTextField2)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jToggleButton1)
                        .addGap(29, 29, 29)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1_Cancelar)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(110, 110, 110))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(22, 22, 22)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6))
                                .addGap(29, 29, 29)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jButton1_Cancelar))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
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
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton1_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_CancelarActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_jButton1_CancelarActionPerformed

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
            java.util.logging.Logger.getLogger(CadastrarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CadastrarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CadastrarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CadastrarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CadastrarConsulta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1_Cancelar;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables

}