package br.edu.imepac.administrativo.telas.Perfil;

import br.edu.imepac.administrativo.daos.ConexaoDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 * Classe para a Listagem de Perfis
 */
public class ListagemPerfil extends javax.swing.JFrame {

    /**
     * Creates new form ListagemPerfil
     */
    public ListagemPerfil() {
        initComponents();
        this.setLocationRelativeTo(null); // Centraliza a janela
        listarPerfis(); // Carrega os dados dos perfis ao abrir a janela
    }

    /**
     * Método para listar perfis do banco de dados na tabela
     */
    private void listarPerfis() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Limpa todas as linhas antes de adicionar novos dados

        // Query para selecionar todos os dados da tabela perfil
        String sql = "SELECT * FROM perfil";

        try (Connection conexao = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),  // ID do perfil
                        rs.getString("nome"),  // Nome do perfil
                        rs.getBoolean("cadastrarFuncionario") ? "Sim" : "Não",
                        rs.getBoolean("listarFuncionario") ? "Sim" : "Não",
                        rs.getBoolean("editarFuncionario") ? "Sim" : "Não",
                        rs.getBoolean("cadastrarPaciente") ? "Sim" : "Não",
                        rs.getBoolean("listarPaciente") ? "Sim" : "Não",
                        rs.getBoolean("editarPaciente") ? "Sim" : "Não",
                        rs.getBoolean("listarProntuario") ? "Sim" : "Não",
                        rs.getBoolean("cadastrarProntuario") ? "Sim" : "Não",
                        rs.getBoolean("editarProntuario") ? "Sim" : "Não",
                        rs.getBoolean("listarConsulta") ? "Sim" : "Não",
                        rs.getBoolean("cadastrarConsulta") ? "Sim" : "Não",
                        rs.getBoolean("editarConsulta") ? "Sim" : "Não",
                        rs.getBoolean("listarEspecialidade") ? "Sim" : "Não",
                        rs.getBoolean("cadastrarEspecialidade") ? "Sim" : "Não",
                        rs.getBoolean("editarEspecialidade") ? "Sim" : "Não",
                        rs.getBoolean("cadastrarConvenio") ? "Sim" : "Não",
                        rs.getBoolean("editarConvenio") ? "Sim" : "Não",
                        rs.getBoolean("listarConvenio") ? "Sim" : "Não"
                };

                model.addRow(row); // Adiciona a linha na tabela
            }

        } catch (SQLException e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Erro ao listar os perfis: " + e.getMessage(),
                    "Erro",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Método gerado automaticamente para inicializar os componentes
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // Ajuste do título
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Listagem de Perfis");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "ID", "Nome", "Cadastrar Func.", "Listar Func.", "Editar Func.",
                        "Cadastrar Pac.", "Listar Pac.", "Editar Pac.", "Listar Pront.",
                        "Cadastrar Pront.", "Editar Pront.", "Listar Cons.", "Cadastrar Cons.",
                        "Editar Cons.", "Listar Esp.", "Cadastrar Esp.", "Editar Esp.",
                        "Cadastrar Conv.", "Editar Conv.", "Listar Conv."
                }
        ));
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF); // Habilita rolagem horizontal
        jScrollPane1.setViewportView(jTable1);

        jButton3.setText("Voltar");
        jButton3.addActionListener(evt -> dispose()); // Fecha a janela ao clicar em "Voltar"

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1500, Short.MAX_VALUE) // Aumenta largura para acomodar todas as colunas
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jButton3)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE) // Altura da tabela ajustada
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
    }// </editor-fold>//GEN-END:initComponents

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new ListagemPerfil().setVisible(true));
    }

    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
}
