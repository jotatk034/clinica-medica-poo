package br.edu.imepac.administrativo.telas.Especialidade;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import br.edu.imepac.administrativo.daos.ConexaoDatabase;

public class EditarEspecialidade extends javax.swing.JFrame {

    // Referência para a conexão
    private Connection con;
    private PreparedStatement pst;
    private ResultSet rs;

    // Lista para armazenar as especialidades
    private List<String> especialidadesList = new ArrayList<>();

    // Construtor da classe
    public EditarEspecialidade() {
        initComponents();
        this.setLocationRelativeTo(null);
        try {
            System.out.println("Tentando conectar ao banco de dados...");
            con = ConexaoDatabase.getConnection(); // Usando a sua classe ConexaoDatabase para conectar
            System.out.println("Conexão bem-sucedida!");
            preencherListaEspecialidades();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro de conexão com o banco de dados: " + e.getMessage());
            System.out.println("Erro de conexão: " + e.getMessage());
        }
    }

    // Método principal para rodar a aplicação
    public static void main(String[] args) {
        try {
            System.out.println("Iniciando a aplicação...");
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new EditarEspecialidade().setVisible(true);
                }
            });
        } catch (Exception e) {
            System.out.println("Erro ao iniciar a aplicação: " + e.getMessage());
        }
    }

    // Preenche a lista de especialidades no JList
    private void preencherListaEspecialidades() {
        try {
            String sql = "SELECT id, nome FROM especialidade";
            pst = con.prepareStatement(sql);
            rs = pst.executeQuery();

            especialidadesList.clear();
            while (rs.next()) {
                especialidadesList.add(rs.getInt("id") + " - " + rs.getString("nome"));
            }

            // Atualiza o JList com a lista de especialidades
            jListEspecialidades.setListData(especialidadesList.toArray(new String[0]));

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados.");
        }
    }

    // Método para preencher os campos com os dados da especialidade selecionada
    private void preencherCamposEspecialidade(String especialidadeSelecionada) {
        if (especialidadeSelecionada == null || especialidadeSelecionada.isEmpty()) {
            return;
        }

        String[] partes = especialidadeSelecionada.split(" - ");
        int idEspecialidade = Integer.parseInt(partes[0]);

        try {
            String sql = "SELECT nome, descricao FROM especialidade WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, idEspecialidade);
            rs = pst.executeQuery();

            if (rs.next()) {
                jTextField1.setText(String.valueOf(idEspecialidade));  // ID
                jTextField2.setText(rs.getString("nome"));  // Nome
                jTextField3.setText(rs.getString("descricao"));  // Descrição
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao carregar os dados da especialidade.");
        }
    }

    // Método para salvar as alterações na especialidade
    private void salvarEspecialidade() {
        String idEspecialidade = jTextField1.getText(); // O ID não deve ser editado
        String nome = jTextField2.getText();
        String descricao = jTextField3.getText();

        if (nome.isEmpty() || descricao.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos os campos devem ser preenchidos!");
            return;
        }

        try {
            String sql = "UPDATE especialidade SET nome = ?, descricao = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, nome);
            pst.setString(2, descricao);
            pst.setInt(3, Integer.parseInt(idEspecialidade));

            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Especialidade atualizada com sucesso!");
                preencherListaEspecialidades();  // Atualiza a lista após o salvar
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao atualizar a especialidade!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados.");
        }
    }

    // Método para apagar a especialidade
    private void apagarEspecialidade() {
        String idEspecialidade = jTextField1.getText();

        if (idEspecialidade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O ID da especialidade não pode ser vazio!");
            return;
        }

        try {
            String sql = "DELETE FROM especialidade WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setInt(1, Integer.parseInt(idEspecialidade));

            int resultado = pst.executeUpdate();

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Especialidade apagada com sucesso!");
                jTextField1.setText(""); // Limpa o campo ID
                jTextField2.setText(""); // Limpa o campo Nome
                jTextField3.setText(""); // Limpa o campo Descrição
                preencherListaEspecialidades(); // Atualiza a lista após o apagar
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao apagar a especialidade!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao acessar o banco de dados.");
        }
    }

    // Método para cancelar (fechar a janela)
    private void cancelar() {
        this.dispose(); // Fecha a janela
    }

    // Método para inicializar os componentes do JFrame
    @SuppressWarnings("unchecked")
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListEspecialidades = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Editar Especialidade");

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Editar Especialidade");

        jLabel2.setText("ID Especialidade");

        jLabel3.setText("Nome");

        jLabel4.setText("Descrição");

        jButton1.setText("Salvar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                salvarEspecialidade();
            }
        });

        jButton2.setText("Cancelar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelar();
            }
        });

        jButton3.setText("Apagar");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apagarEspecialidade();
            }
        });

        jListEspecialidades.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                preencherCamposEspecialidade(jListEspecialidades.getSelectedValue());
            }
        });
        jScrollPane1.setViewportView(jListEspecialidades);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap(20, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(20, 20, 20))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addComponent(jButton1)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton2)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jButton3)
                                                .addContainerGap())))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(30, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel2)
                                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel3)
                                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel4)
                                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jButton1)
                                        .addComponent(jButton2)
                                        .addComponent(jButton3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(20, Short.MAX_VALUE))
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
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> jListEspecialidades;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
}
