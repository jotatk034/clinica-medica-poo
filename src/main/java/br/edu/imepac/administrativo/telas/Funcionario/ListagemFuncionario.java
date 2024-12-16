package br.edu.imepac.administrativo.telas.Funcionario;

import br.edu.imepac.administrativo.daos.ConexaoDatabase;
import java.sql.*;
import javax.swing.table.DefaultTableModel;

public class ListagemFuncionario extends javax.swing.JFrame {

    // Construtor
    public ListagemFuncionario() {
        initComponents();
        listarFuncionarios();
        this.setLocationRelativeTo(null);
    }

    // Método para listar os funcionários
    private void listarFuncionarios() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        try {
            // Conexão com o banco de dados
            Connection conn = ConexaoDatabase.getConnection();

            // SQL para consultar os dados
            String sql = "SELECT id, usuario, senha, nome, sexo, cpf, rua, numero, bairro, cidade, estado, contato, email, dataNascimento, tipoFuncionario_id, IDADE, especialidade, perfil FROM funcionario";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // Limpa a tabela antes de adicionar novos dados
            model.setRowCount(0);

            // Preenche a tabela com os dados da consulta
            while (rs.next()) {
                Object[] row = new Object[19]; // Número de colunas
                row[0] = rs.getInt("id");
                row[1] = rs.getString("usuario");
                row[2] = rs.getString("senha");
                row[3] = rs.getString("nome");
                row[4] = rs.getString("sexo");
                row[5] = rs.getString("cpf");
                row[6] = rs.getString("rua");
                row[7] = rs.getString("numero");
                row[8] = rs.getString("bairro");
                row[9] = rs.getString("cidade");
                row[10] = rs.getString("estado");
                row[11] = rs.getString("contato");
                row[12] = rs.getString("email");
                row[13] = rs.getString("dataNascimento");
                row[14] = rs.getInt("tipoFuncionario_id");
                row[15] = rs.getString("IDADE");
                row[16] = rs.getString("especialidade");
                row[17] = rs.getString("perfil");

                model.addRow(row);
            }

            // Fecha a conexão com o banco
            rs.close();
            stmt.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método gerado automaticamente para inicializar a interface gráfica
    private void initComponents() {
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Listagem de Funcionários");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {},
                new String [] {
                        "ID", "Usuario", "Senha", "Nome", "Sexo", "CPF", "Rua", "Número",  "Bairro", "Cidade", "Estado", "Contato", "Email", "Data Nascimento", "Tipo Funcionario", "Idade", "Especialidade", "Perfil"
                }
        ) {
            Class[] types = new Class [] {
                    java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jButton3.setText("Voltar");
        jButton3.addActionListener(e -> {
            this.dispose();  // Fecha a janela
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addContainerGap(355, Short.MAX_VALUE)
                                .addComponent(jButton3)
                                .addGap(332, 332, 332))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1)
                                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 456, javax.swing.GroupLayout.PREFERRED_SIZE) // Aumentei a altura da tabela
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jButton3)
                                .addContainerGap(8, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addComponent(jLabel1)
                                                .addGap(0, 0, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
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
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(1920, 800)); // Ajuste do tamanho para 1366x768

    }

    // Método main para executar a aplicação
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListagemFuncionario().setVisible(true);
            }
        });
    }

    // Declaração das variáveis do JFrame
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
}
