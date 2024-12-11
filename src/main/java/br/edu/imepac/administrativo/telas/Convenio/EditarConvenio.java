package br.edu.imepac.administrativo.telas.Convenio;

import br.edu.imepac.administrativo.daos.ConexaoDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 * Classe para editar convênios.
 */
public class EditarConvenio extends javax.swing.JFrame {

    /**
     * Construtor para criar a tela EditarConvenio.
     */
    public EditarConvenio() {
        initComponents();
        this.setLocationRelativeTo(null);
        carregarIDs();
        configurarAcoesBotoes();
    }

    /**
     * Método para carregar os IDs de convênios no JComboBox.
     */
    private void carregarIDs() {
        try (Connection conexao = ConexaoDatabase.getConnection()) {
            String sql = "SELECT ID FROM convenio";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            jComboBox1.removeAllItems(); // Limpa o ComboBox
            while (rs.next()) {
                jComboBox1.addItem(rs.getString("ID"));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar IDs dos convênios: " + ex.getMessage());
        }
    }

    /**
     * Configura os eventos dos botões e JComboBox.
     */
    private void configurarAcoesBotoes() {
        // Ação para carregar os dados ao selecionar um ID no JComboBox
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                carregarDados();
            }
        });

        // Ação para o botão "Salvar"
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                salvar();
            }
        });

        // Ação para o botão "Cancelar"
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                dispose(); // Fecha a janela
            }
        });

        // Ação para o botão "Apagar"
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                apagar();
            }
        });
    }

    /**
     * Carrega os dados do convênio a partir do ID selecionado no JComboBox.
     */
    private void carregarDados() {
        String ID = (String) jComboBox1.getSelectedItem();

        if (ID == null) {
            return;
        }

        try (Connection conexao = ConexaoDatabase.getConnection()) {
            String sql = "SELECT nome, descricao FROM convenio WHERE ID = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, ID);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                jTextField2.setText(rs.getString("nome"));
                jTextField3.setText(rs.getString("descricao"));
            } else {
                JOptionPane.showMessageDialog(this, "Convênio não encontrado.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar os dados do convênio: " + ex.getMessage());
        }
    }

    /**
     * Salva as alterações do convênio no banco de dados.
     */
    private void salvar() {
        String ID = (String) jComboBox1.getSelectedItem();
        String nome = jTextField2.getText();
        String descricao = jTextField3.getText();

        if (ID == null || nome.isEmpty() || descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos para salvar o convênio.");
            return;
        }

        try (Connection conexao = ConexaoDatabase.getConnection()) {
            String sql = "UPDATE convenio SET nome = ?, descricao = ? WHERE ID = ?";
            PreparedStatement stmt = conexao.prepareStatement(sql);
            stmt.setString(1, nome);
            stmt.setString(2, descricao);
            stmt.setString(3, ID);

            int linhasAfetadas = stmt.executeUpdate();

            if (linhasAfetadas > 0) {
                JOptionPane.showMessageDialog(this, "Convênio atualizado com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "Nenhuma alteração foi feita.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar o convênio: " + ex.getMessage());
        }
    }

    /**
     * Apaga o convênio selecionado no banco de dados.
     */
    private void apagar() {
        String ID = (String) jComboBox1.getSelectedItem();

        if (ID == null) {
            JOptionPane.showMessageDialog(this, "Selecione um convênio para apagar.");
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this, "Você tem certeza que deseja apagar este convênio?", "Confirmar Exclusão", JOptionPane.YES_NO_OPTION);
        if (confirmar == JOptionPane.YES_OPTION) {
            try (Connection conexao = ConexaoDatabase.getConnection()) {
                String sql = "DELETE FROM convenio WHERE ID = ?";
                PreparedStatement stmt = conexao.prepareStatement(sql);
                stmt.setString(1, ID);

                int linhasAfetadas = stmt.executeUpdate();

                if (linhasAfetadas > 0) {
                    JOptionPane.showMessageDialog(this, "Convênio apagado com sucesso!");
                    carregarIDs(); // Recarrega os IDs após a exclusão
                    jTextField2.setText(""); // Limpa o campo nome
                    jTextField3.setText(""); // Limpa o campo descrição
                } else {
                    JOptionPane.showMessageDialog(this, "Erro ao apagar o convênio.");
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Erro ao apagar o convênio: " + ex.getMessage());
            }
        }
    }

    /**
     * Método principal para executar a tela EditarConvenio.
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarConvenio().setVisible(true);
            }
        });
    }

    // Declaração de variáveis
    private javax.swing.JButton jButton1; // Botão "Salvar"
    private javax.swing.JButton jButton2; // Botão "Cancelar"
    private javax.swing.JButton jButton3; // Botão "Apagar"
    private javax.swing.JComboBox<String> jComboBox1; // ComboBox de IDs
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField2; // Campo "Nome"
    private javax.swing.JTextField jTextField3; // Campo "Descrição"

    // Código de layout gerado automaticamente pelo NetBeans
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel(new java.awt.BorderLayout());
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel("ID do Convênio", javax.swing.SwingConstants.LEFT);
        jLabel3 = new javax.swing.JLabel("Nome", javax.swing.SwingConstants.LEFT);
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel("Descrição", javax.swing.SwingConstants.LEFT);
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();

        // Custom layout code starts here
        jPanel1.setBackground(new java.awt.Color(70, 130, 180));
        jPanel1.add(jLabel1);

        jLabel1.setFont(new java.awt.Font("Serif", java.awt.Font.BOLD, 20));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Editar Convênio");

        jLabel2.setText("ID do Convênio");
        jLabel3.setText("Nome");
        jLabel4.setText("Descrição");

        jButton1.setText("Salvar");
        jButton2.setText("Cancelar");
        jButton3.setText("Apagar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField2)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jTextField3)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jButton1)
                                .addComponent(jButton2)
                                .addComponent(jButton3))
                        .addContainerGap());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        setSize(400, 300);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Editar Convênio");
        pack();
    }
}
