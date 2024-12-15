/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package br.edu.imepac.administrativo.telas.Perfil;
import java.sql.*;
import javax.swing.*;
import br.edu.imepac.administrativo.daos.ConexaoDatabase;

/**
 *
 * @author User
 */
public class EditarPerfil extends javax.swing.JFrame {

    /**
     * Creates new form EditarPerfil
     */
    public EditarPerfil() {
        initComponents();
        this.setLocationRelativeTo(null);
        conectarBanco();
        carregarPerfis();
        limparCampos();
        String nomePerfilSelecionado = (String) jComboBox1.getSelectedItem();
        if (nomePerfilSelecionado != null) {
            carregarPermissoes(nomePerfilSelecionado);
        }
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });


    }

    private Connection conn;

    private void conectarBanco() {
        try {
            // Utilizando sua classe ConexaoDatabase para obter a conexão
            conn = ConexaoDatabase.getConnection();  // Supondo que o método getConnection() retorne uma conexão
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro na conexão com o banco de dados.");
        }
    }

    // Carrega os perfis para o JComboBox
    private void carregarPerfis() {
        String sql = "SELECT nome FROM perfil";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                jComboBox1.addItem(nome);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar os perfis.");
        }
    }

    // Carrega as permissões do perfil selecionado
    private void carregarPermissoes(String nomePerfil) {
        String sql = "SELECT * FROM perfil WHERE nome = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nomePerfil);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Marca os JCheckBox conforme os dados do banco
                CriarFuncionarioCheckBox.setSelected(rs.getInt("cadastrarFuncionario") == 1);
                ListarFuncionarioCheckBox.setSelected(rs.getInt("listarFuncionario") == 1);
                EditarFuncionarioCheckbox.setSelected(rs.getInt("editarFuncionario") == 1);

                CriarPacienteCheckBox.setSelected(rs.getInt("cadastrarPaciente") == 1);
                ListarPacienteCheckBox.setSelected(rs.getInt("listarPaciente") == 1);
                EditarPacienteCheckBox.setSelected(rs.getInt("editarPaciente") == 1);

                CriarProntuarioCheckBox.setSelected(rs.getInt("cadastrarProntuario") == 1);
                ListarProntuarioCheckBox.setSelected(rs.getInt("listarProntuario") == 1);
                EditarProntuarioCheckBox.setSelected(rs.getInt("editarProntuario") == 1);

                CriarConsultaCheckBox.setSelected(rs.getInt("cadastrarConsulta") == 1);
                ListarConsultaCheckBox.setSelected(rs.getInt("listarConsulta") == 1);
                EditarConsultaCheckBox.setSelected(rs.getInt("editarConsulta") == 1);

                CriarEspecialidadeCheckBox.setSelected(rs.getInt("cadastrarEspecialidade") == 1);
                ListarEspecialidadeCheckBox.setSelected(rs.getInt("listarEspecialidade") == 1);
                EditarEspecialidadeCheckBox.setSelected(rs.getInt("editarEspecialidade") == 1);

                CriarConvenioCheckBox.setSelected(rs.getInt("cadastrarConvenio") == 1);
                ListarConvenioCheckBox.setSelected(rs.getInt("listarConvenio") == 1);
                EditarConvenioCheckBox.setSelected(rs.getInt("editarConvenio") == 1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar permissões.");
        }
    }
    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {
        // Obtém o nome do perfil selecionado
        String nomePerfil = (String) jComboBox1.getSelectedItem();

        // Se o perfil não for nulo, carrega as permissões para esse perfil
        if (nomePerfil != null) {
            carregarPermissoes(nomePerfil);
        }
    }
    private void jButton2_SalvarActionPerformed(java.awt.event.ActionEvent evt) {
        // Obter o nome do perfil selecionado
        String nomePerfil = jComboBox1.getSelectedItem().toString();

        // Atualizar permissões no banco de dados
        String sql = "UPDATE perfil SET "
                + "cadastrarFuncionario = ?, listarFuncionario = ?, editarFuncionario = ?, "
                + "cadastrarPaciente = ?, listarPaciente = ?, editarPaciente = ?, "
                + "cadastrarProntuario = ?, listarProntuario = ?, editarProntuario = ?, "
                + "cadastrarConsulta = ?, listarConsulta = ?, editarConsulta = ?, "
                + "cadastrarEspecialidade = ?, listarEspecialidade = ?, editarEspecialidade = ?, "
                + "cadastrarConvenio = ?, listarConvenio = ?, editarConvenio = ? "
                + "WHERE nome = ?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Definindo os parâmetros com os valores das JCheckBox
            stmt.setInt(1, CriarFuncionarioCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(2, ListarFuncionarioCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(3, EditarFuncionarioCheckbox.isSelected() ? 1 : 0);

            stmt.setInt(4, CriarPacienteCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(5, ListarPacienteCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(6, EditarPacienteCheckBox.isSelected() ? 1 : 0);

            stmt.setInt(7, CriarProntuarioCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(8, ListarProntuarioCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(9, EditarProntuarioCheckBox.isSelected() ? 1 : 0);

            stmt.setInt(10, CriarConsultaCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(11, ListarConsultaCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(12, EditarConsultaCheckBox.isSelected() ? 1 : 0);

            stmt.setInt(13, CriarEspecialidadeCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(14, ListarEspecialidadeCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(15, EditarEspecialidadeCheckBox.isSelected() ? 1 : 0);

            stmt.setInt(16, CriarConvenioCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(17, ListarConvenioCheckBox.isSelected() ? 1 : 0);
            stmt.setInt(18, EditarConvenioCheckBox.isSelected() ? 1 : 0);

            // Definir o nome do perfil para o WHERE
            stmt.setString(19, nomePerfil);

            // Executar a atualização
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(this, "Permissões salvas com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao salvar permissões.");
        }
    }
    private void jButton3_ApagarActionPerformed(java.awt.event.ActionEvent evt) {
        // Obter o nome do perfil selecionado
        String nomePerfil = jComboBox1.getSelectedItem().toString();

        // Confirmação antes de apagar
        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja apagar as permissões deste perfil?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            // Remover permissões do banco de dados
            String sql = "DELETE FROM perfil WHERE nome = ?";

            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, nomePerfil);

                // Executar a exclusão
                stmt.executeUpdate();

                // Mostrar mensagem de sucesso
                JOptionPane.showMessageDialog(this, "Permissões apagadas com sucesso!");
                // Limpar as JCheckBox
                limparCampos();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Erro ao apagar permissões.");
            }
        }
    }
    private void limparCampos() {
        CriarFuncionarioCheckBox.setSelected(false);
        ListarFuncionarioCheckBox.setSelected(false);
        EditarFuncionarioCheckbox.setSelected(false);

        CriarPacienteCheckBox.setSelected(false);
        ListarPacienteCheckBox.setSelected(false);
        EditarPacienteCheckBox.setSelected(false);

        CriarProntuarioCheckBox.setSelected(false);
        ListarProntuarioCheckBox.setSelected(false);
        EditarProntuarioCheckBox.setSelected(false);

        CriarConsultaCheckBox.setSelected(false);
        ListarConsultaCheckBox.setSelected(false);
        EditarConsultaCheckBox.setSelected(false);

        CriarEspecialidadeCheckBox.setSelected(false);
        ListarEspecialidadeCheckBox.setSelected(false);
        EditarEspecialidadeCheckBox.setSelected(false);

        CriarConvenioCheckBox.setSelected(false);
        ListarConvenioCheckBox.setSelected(false);
        EditarConvenioCheckBox.setSelected(false);
    }




    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jCheckBoxMenuItem1 = new javax.swing.JCheckBoxMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton2_Salvar = new javax.swing.JButton();
        jButton1_Cancelar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jButton3_Apagar = new javax.swing.JButton();
        CriarFuncionarioCheckBox = new javax.swing.JCheckBox();
        EditarFuncionarioCheckbox = new javax.swing.JCheckBox();
        ListarFuncionarioCheckBox = new javax.swing.JCheckBox();
        CriarPacienteCheckBox = new javax.swing.JCheckBox();
        EditarPacienteCheckBox = new javax.swing.JCheckBox();
        ListarPacienteCheckBox = new javax.swing.JCheckBox();
        CriarConsultaCheckBox = new javax.swing.JCheckBox();
        EditarConsultaCheckBox = new javax.swing.JCheckBox();
        ListarConsultaCheckBox = new javax.swing.JCheckBox();
        CriarProntuarioCheckBox = new javax.swing.JCheckBox();
        EditarProntuarioCheckBox = new javax.swing.JCheckBox();
        ListarProntuarioCheckBox = new javax.swing.JCheckBox();
        CriarEspecialidadeCheckBox = new javax.swing.JCheckBox();
        EditarEspecialidadeCheckBox = new javax.swing.JCheckBox();
        ListarEspecialidadeCheckBox = new javax.swing.JCheckBox();
        CriarConvenioCheckBox = new javax.swing.JCheckBox();
        EditarConvenioCheckBox = new javax.swing.JCheckBox();
        ListarConvenioCheckBox = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox<>();

        jCheckBoxMenuItem1.setSelected(true);
        jCheckBoxMenuItem1.setText("jCheckBoxMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Edição Perfil");

        jPanel2.setLayout(null);

        jLabel4.setText("Funcionario");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(10, 70, 80, 16);

        jLabel5.setText("Paciente");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(80, 70, 70, 16);

        jLabel6.setText("Consulta");
        jPanel2.add(jLabel6);
        jLabel6.setBounds(150, 70, 70, 16);

        jLabel9.setText("Prontuário");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(220, 70, 70, 16);

        jLabel3.setText("Especialidade");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(290, 70, 90, 16);

        jLabel7.setText("Convênio");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(380, 70, 80, 16);

        jButton2_Salvar.setText("Salvar ");
        jButton2_Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2_SalvarActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2_Salvar);
        jButton2_Salvar.setBounds(120, 180, 72, 23);

        jButton1_Cancelar.setText("Cancelar");
        jButton1_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1_CancelarActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1_Cancelar);
        jButton1_Cancelar.setBounds(300, 180, 76, 23);

        jLabel2.setText("Nome do Perfil");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(10, 20, 120, 16);

        jButton3_Apagar.setText("Apagar");
        jButton3_Apagar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3_ApagarActionPerformed(evt);
            }
        });
        jPanel2.add(jButton3_Apagar);
        jButton3_Apagar.setBounds(210, 180, 72, 23);

        CriarFuncionarioCheckBox.setText("Criar");
        CriarFuncionarioCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CriarFuncionarioCheckBoxActionPerformed(evt);
            }
        });
        jPanel2.add(CriarFuncionarioCheckBox);
        CriarFuncionarioCheckBox.setBounds(10, 90, 70, 20);

        EditarFuncionarioCheckbox.setText("Editar");
        jPanel2.add(EditarFuncionarioCheckbox);
        EditarFuncionarioCheckbox.setBounds(10, 110, 60, 20);

        ListarFuncionarioCheckBox.setText("Listar");
        jPanel2.add(ListarFuncionarioCheckBox);
        ListarFuncionarioCheckBox.setBounds(10, 130, 84, 20);

        CriarPacienteCheckBox.setText("Criar");
        jPanel2.add(CriarPacienteCheckBox);
        CriarPacienteCheckBox.setBounds(80, 90, 60, 20);

        EditarPacienteCheckBox.setText("Editar");
        jPanel2.add(EditarPacienteCheckBox);
        EditarPacienteCheckBox.setBounds(80, 110, 60, 20);

        ListarPacienteCheckBox.setText("Listar");
        jPanel2.add(ListarPacienteCheckBox);
        ListarPacienteCheckBox.setBounds(80, 130, 70, 20);

        CriarConsultaCheckBox.setText("Criar");
        jPanel2.add(CriarConsultaCheckBox);
        CriarConsultaCheckBox.setBounds(150, 90, 60, 20);

        EditarConsultaCheckBox.setText("Editar");
        jPanel2.add(EditarConsultaCheckBox);
        EditarConsultaCheckBox.setBounds(150, 110, 60, 20);

        ListarConsultaCheckBox.setText("Listar");
        jPanel2.add(ListarConsultaCheckBox);
        ListarConsultaCheckBox.setBounds(150, 130, 70, 20);

        CriarProntuarioCheckBox.setText("Criar");
        jPanel2.add(CriarProntuarioCheckBox);
        CriarProntuarioCheckBox.setBounds(220, 90, 90, 20);

        EditarProntuarioCheckBox.setText("Editar");
        jPanel2.add(EditarProntuarioCheckBox);
        EditarProntuarioCheckBox.setBounds(220, 110, 60, 20);

        ListarProntuarioCheckBox.setText("Listar");
        jPanel2.add(ListarProntuarioCheckBox);
        ListarProntuarioCheckBox.setBounds(220, 130, 60, 20);

        CriarEspecialidadeCheckBox.setText("Criar");
        jPanel2.add(CriarEspecialidadeCheckBox);
        CriarEspecialidadeCheckBox.setBounds(290, 90, 70, 20);

        EditarEspecialidadeCheckBox.setText("Editar");
        jPanel2.add(EditarEspecialidadeCheckBox);
        EditarEspecialidadeCheckBox.setBounds(290, 110, 60, 20);

        ListarEspecialidadeCheckBox.setText("Listar");
        jPanel2.add(ListarEspecialidadeCheckBox);
        ListarEspecialidadeCheckBox.setBounds(290, 130, 60, 20);

        CriarConvenioCheckBox.setText("Criar");
        jPanel2.add(CriarConvenioCheckBox);
        CriarConvenioCheckBox.setBounds(380, 90, 70, 20);

        EditarConvenioCheckBox.setText("Editar");
        jPanel2.add(EditarConvenioCheckBox);
        EditarConvenioCheckBox.setBounds(380, 110, 60, 20);

        ListarConvenioCheckBox.setText("Listar");
        jPanel2.add(ListarConvenioCheckBox);
        ListarConvenioCheckBox.setBounds(380, 130, 70, 20);

        jPanel2.add(jComboBox1);
        jComboBox1.setBounds(110, 20, 240, 22);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 489, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel1))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    }// </editor-fold>



    private void jButton1_CancelarActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }



    private void CriarFuncionarioCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {
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
            java.util.logging.Logger.getLogger(EditarPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarPerfil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarPerfil().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JCheckBox CriarConsultaCheckBox;
    private javax.swing.JCheckBox CriarConvenioCheckBox;
    private javax.swing.JCheckBox CriarEspecialidadeCheckBox;
    private javax.swing.JCheckBox CriarFuncionarioCheckBox;
    private javax.swing.JCheckBox CriarPacienteCheckBox;
    private javax.swing.JCheckBox CriarProntuarioCheckBox;
    private javax.swing.JCheckBox EditarConsultaCheckBox;
    private javax.swing.JCheckBox EditarConvenioCheckBox;
    private javax.swing.JCheckBox EditarEspecialidadeCheckBox;
    private javax.swing.JCheckBox EditarFuncionarioCheckbox;
    private javax.swing.JCheckBox EditarPacienteCheckBox;
    private javax.swing.JCheckBox EditarProntuarioCheckBox;
    private javax.swing.JCheckBox ListarConsultaCheckBox;
    private javax.swing.JCheckBox ListarConvenioCheckBox;
    private javax.swing.JCheckBox ListarEspecialidadeCheckBox;
    private javax.swing.JCheckBox ListarFuncionarioCheckBox;
    private javax.swing.JCheckBox ListarPacienteCheckBox;
    private javax.swing.JCheckBox ListarProntuarioCheckBox;
    private javax.swing.JButton jButton1_Cancelar;
    private javax.swing.JButton jButton2_Salvar;
    private javax.swing.JButton jButton3_Apagar;
    private javax.swing.JCheckBoxMenuItem jCheckBoxMenuItem1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration
}
