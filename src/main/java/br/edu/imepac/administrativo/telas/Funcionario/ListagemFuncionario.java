package br.edu.imepac.administrativo.telas.Funcionario;

import br.edu.imepac.administrativo.daos.ConexaoDatabase;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;

/**
 * Tela para Listagem de Todos os Funcionários
 */
public class ListagemFuncionario extends javax.swing.JFrame {

    /**
     * Construtor
     */
    public ListagemFuncionario() {
        initComponents();
        this.setLocationRelativeTo(null); // Centraliza a janela
        listarFuncionarios(); // Carrega os dados na tabela ao abrir
    }

    /**
     * Método para listar todos os funcionários e exibir os dados na JTable
     */
    private void listarFuncionarios() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

        // Query para selecionar todas as colunas da tabela funcionario
        String sql = "SELECT * FROM funcionario";

        try (Connection conexao = ConexaoDatabase.getConnection();
             PreparedStatement stmt = conexao.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Enquanto houver linhas no resultado, adicione na tabela
            while (rs.next()) {
                Object[] row = {
                        rs.getInt("id"),                  // ID
                        rs.getString("usuario"),          // Usuário
                        rs.getString("senha"),            // Senha
                        rs.getString("nome"),             // Nome
                        rs.getString("sexo"),             // Sexo
                        rs.getString("cpf"),              // CPF
                        rs.getString("rua"),              // Rua
                        rs.getString("numero"),           // Número
                        rs.getString("complemento"),      // Complemento
                        rs.getString("bairro"),           // Bairro
                        rs.getString("cidade"),           // Cidade
                        rs.getString("estado"),           // Estado
                        rs.getString("contato"),          // Contato
                        rs.getString("email"),            // Email
                        rs.getDate("dataNascimento"),     // Data de Nascimento
                        rs.getObject("tipoFuncionario_id"), // Tipo Funcionario ID
                        rs.getObject("IDADE"),            // Idade
                        rs.getString("especialidade"),    // Especialidade
                        rs.getString("perfil")            // Perfil
                };
                model.addRow(row); // Adiciona a linha na tabela
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Exibe o erro no console se houver
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Erro ao listar os funcionários: " + e.getMessage(),
                    "Erro",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Inicializador de componentes visuais
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

        // Ajusta a cor de fundo e configura o título
        jPanel1.setBackground(new java.awt.Color(51, 153, 255));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // Tamanho maior do título
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Listagem Completa de Funcionários");

        // Configura a tabela com rolagem
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {
                        // Nenhuma linha inicial
                },
                new String [] {
                        "ID", "Usuário", "Senha", "Nome", "Sexo", "CPF", "Rua", "Número", "Complemento",
                        "Bairro", "Cidade", "Estado", "Contato", "Email", "Data de Nascimento",
                        "Tipo Funcionario ID", "Idade", "Especialidade", "Perfil"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class, java.lang.String.class, java.lang.String.class,
                    java.lang.String.class, java.lang.String.class, java.sql.Date.class,
                    java.lang.Object.class, java.lang.Object.class, java.lang.String.class,
                    java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }
        });
        jTable1.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF); // Não redimensionar automaticamente
        jScrollPane1.setViewportView(jTable1);

        // Configuração do botão "Voltar"
        jButton3.setText("Voltar");
        jButton3.addActionListener(evt -> dispose()); // Fecha a janela quando clicado

        // Configura o layout do painel principal
        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1100, Short.MAX_VALUE) // Ajuste de largura
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(jButton3)))
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE) // Ajuste de altura
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addContainerGap(15, Short.MAX_VALUE))
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
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    /**
     * Método Main para iniciar a aplicação
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            new ListagemFuncionario().setVisible(true);
        });
    }

    // Declaração de variáveis geradas
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // Fim da declaração
}