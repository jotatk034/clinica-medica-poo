/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

package br.edu.imepac.administrativo.telas.Consulta;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.*;
import javax.swing.*;
import java.awt.event.*;
import java.text.*;

/**
 *
 * @author User
 */
public class EditarConsulta extends javax.swing.JFrame {

    // Conexão com o banco de dados
    private Connection conectarBanco() {
        Connection conexao = null;
        try {
            String url = "jdbc:mysql://localhost:3306/clinica_medica_poo";
            String usuario = "root";
            String senha = "12345";
            conexao = DriverManager.getConnection(url, usuario, senha);
            System.out.println("Conexão estabelecida com o banco de dados!");
        } catch (SQLException e) {
            System.out.println("Erro ao conectar com o banco de dados: " + e.getMessage());
        }
        return conexao;
    }

    // Carregar dados para os comboboxes
    private void carregarComboboxes() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = conectarBanco();

            // Carrega os médicos
            String sqlMedicos = "SELECT nome FROM medico";
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sqlMedicos);
            while (rs.next()) {
                jComboBox2.addItem(rs.getString("nome"));
            }

            // Carrega os pacientes
            String sqlPacientes = "SELECT nome FROM paciente";
            rs = stmt.executeQuery(sqlPacientes);
            while (rs.next()) {
                jComboBox3.addItem(rs.getString("nome"));
            }

            // Carrega os convênios
            String sqlConvenios = "SELECT nome FROM convenio";
            rs = stmt.executeQuery(sqlConvenios);
            while (rs.next()) {
                jComboBox4.addItem(rs.getString("nome"));
            }

            // Carrega os IDs das consultas (para edição)
            String sqlConsultas = "SELECT id FROM consulta";
            rs = stmt.executeQuery(sqlConsultas);
            while (rs.next()) {
                jComboBox7.addItem(String.valueOf(rs.getInt("id")));  // Converte o ID para String antes de adicionar
            }

            // Carrega os atendentes
            String sqlAtendentes = "SELECT nome FROM atendente";
            rs = stmt.executeQuery(sqlAtendentes);
            while (rs.next()) {
                jComboBox6.addItem(rs.getString("nome"));  // jComboBox6 é o combo para Atendente
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para carregar os dados da consulta com base no ID
    private void carregarDadosConsultaParaEdicao() {
        // Pegando o ID da consulta como String primeiro
        String selectedItem = (String) jComboBox7.getSelectedItem();

        // Convertendo o String para Integer
        Integer idConsulta = Integer.parseInt(selectedItem);

        if (idConsulta != null) {
            try {
                Connection conn = conectarBanco();
                String sql = "SELECT * FROM consulta WHERE id = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, idConsulta);  // Passando o ID como Integer para a consulta
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    // Preenche os campos com os dados da consulta
                    jTextField1.setText(rs.getString("data_horario"));
                    jTextField2.setText(rs.getString("sintoma"));

                    // Preenche os campos de médicos, pacientes, convênios, atendentes
                    jComboBox2.setSelectedItem(getNomeMedico(rs.getInt("medico_id")));
                    jComboBox3.setSelectedItem(getNomePaciente(rs.getInt("paciente_id")));
                    jComboBox4.setSelectedItem(getNomeConvenio(rs.getInt("convenio_id")));
                    jComboBox6.setSelectedItem(getNomeAtendente(rs.getInt("atendente_id")));
                }
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao carregar os dados da consulta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Métodos para obter nomes a partir dos IDs
    private String getNomeMedico(int idMedico) {
        String nome = "";
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT nome FROM medico WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idMedico);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nome = rs.getString("nome");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar nome do médico: " + e.getMessage());
        }
        return nome;
    }

    private String getNomePaciente(int idPaciente) {
        String nome = "";
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT nome FROM paciente WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idPaciente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nome = rs.getString("nome");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar nome do paciente: " + e.getMessage());
        }
        return nome;
    }

    private String getNomeConvenio(int idConvenio) {
        String nome = "";
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT nome FROM convenio WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idConvenio);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nome = rs.getString("nome");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar nome do convênio: " + e.getMessage());
        }
        return nome;
    }

    private String getNomeAtendente(int idAtendente) {
        String nome = "";
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT nome FROM atendente WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, idAtendente);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                nome = rs.getString("nome");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar nome do atendente: " + e.getMessage());
        }
        return nome;
    }

    // Método para salvar a consulta (editar a consulta existente)
    private void salvarConsulta() {
        // Obter dados dos campos
        String dataHora = jTextField1.getText();  // Data e Hora
        String sintomas = jTextField2.getText();  // Sintomas
        String medico = jComboBox2.getSelectedItem().toString();  // Médico
        String paciente = jComboBox3.getSelectedItem().toString();  // Paciente
        String convenio = jComboBox4.getSelectedItem().toString();  // Convênio
        String atendente = jComboBox6.getSelectedItem().toString();  // Atendente

        // Obter o ID do médico, paciente, convênio e atendente
        int medicoId = getMedicoId(medico);  // Método para pegar ID do médico
        int pacienteId = getPacienteId(paciente);  // Método para pegar ID do paciente
        int convenioId = getConvenioId(convenio);  // Método para pegar ID do convênio
        int idAtendente = getAtendenteId(atendente);  // Método para pegar ID do atendente

        // Obter o ID da consulta para atualizar
        int idConsulta = Integer.parseInt(jComboBox7.getSelectedItem().toString());  // jComboBox7 é o combo para ID da consulta

        // Atualizar dados no banco
        try {
            Connection conn = conectarBanco();
            String sql = "UPDATE consulta SET data_horario = ?, sintoma = ?, medico_id = ?, paciente_id = ?, convenio_id = ?, atendente_id = ? WHERE id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, dataHora);
            stmt.setString(2, sintomas);
            stmt.setInt(3, medicoId);  // Usar ID do médico
            stmt.setInt(4, pacienteId);  // Usar ID do paciente
            stmt.setInt(5, convenioId);  // Usar ID do convênio
            stmt.setInt(6, idAtendente);  // Usar ID do atendente
            stmt.setInt(7, idConsulta);

            // Executa a atualização
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Consulta atualizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println("Erro ao salvar a consulta: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao salvar a consulta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para buscar o ID do atendente a partir do nome
    private int getAtendenteId(String nomeAtendente) {
        int id = 0;
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT id FROM atendente WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeAtendente);  // Usando nomeAtendente como parâmetro
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ID do atendente: " + e.getMessage());
        }
        return id;
    }
    private int getMedicoId(String nomeMedico) {
        int id = 0;
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT id FROM medico WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeMedico);  // Passando o nome do médico
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ID do médico: " + e.getMessage());
        }
        return id;
    }
    private int getPacienteId(String nomePaciente) {
        int id = 0;
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT id FROM paciente WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomePaciente);  // Passando o nome do paciente
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ID do paciente: " + e.getMessage());
        }
        return id;
    }
    private int getConvenioId(String nomeConvenio) {
        int id = 0;
        try {
            Connection conn = conectarBanco();
            String sql = "SELECT id FROM convenio WHERE nome = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nomeConvenio);  // Passando o nome do convênio
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao buscar ID do convênio: " + e.getMessage());
        }
        return id;
    }





    // Limpar campos para nova inserção
    private void limparCampos() {
        jTextField1.setText("");
        jTextField2.setText("");
        jComboBox2.setSelectedIndex(0);  // Médico
        jComboBox3.setSelectedIndex(0);  // Paciente
        jComboBox4.setSelectedIndex(0);  // Convênio
        jComboBox6.setSelectedIndex(0);  // Atendente
    }

    // Método do construtor
    public EditarConsulta() {
        initComponents();
        this.setLocationRelativeTo(null);
        carregarComboboxes();
        carregarDadosConsultaParaEdicao();

        jComboBox7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                carregarDadosConsultaParaEdicao();
            }
        });

        jToggleButton1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                salvarConsulta();
            }
        });
    }
    // Método para apagar a consulta
    private void apagarConsulta() {
        // Pega o ID da consulta selecionada
        String selectedItem = (String) jComboBox7.getSelectedItem();

        // Converte o ID para Integer
        Integer idConsulta = Integer.valueOf(selectedItem);

        if (idConsulta != null) {
            int confirm = JOptionPane.showConfirmDialog(
                    null,
                    "Tem certeza que deseja apagar a consulta com ID " + idConsulta + "?",
                    "Confirmar Exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    // Conectar com o banco de dados
                    Connection conn = conectarBanco();

                    // Preparar a SQL para excluir a consulta
                    String sql = "DELETE FROM consulta WHERE id = ?";
                    PreparedStatement stmt = conn.prepareStatement(sql);
                    stmt.setInt(1, idConsulta);  // Passa o ID da consulta para a SQL

                    // Executar a exclusão
                    stmt.executeUpdate();

                    // Confirmar a exclusão
                    JOptionPane.showMessageDialog(null, "Consulta apagada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);

                    // Limpar os campos após a exclusão
                    limparCampos();
                    carregarComboboxes();  // Atualizar os combo boxes

                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Erro ao apagar a consulta: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione uma consulta para apagar.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
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
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jComboBox5 = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        jComboBox6 = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jComboBox7 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Atualização de Constulta");

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

        jLabel8.setText("ID da Consulta");

        jLabel9.setText("Status");

        jComboBox5.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ativa", "Inativa", " " }));
        jComboBox5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox5ActionPerformed(evt);
            }
        });

        jLabel10.setText("Atendente");

        jComboBox6.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar o atendente", " " }));

        jButton2.setText("Apagar");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                apagarConsulta();  // Chama o método para apagar a consulta
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jTextField2)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 247, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(110, 110, 110))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(138, 138, 138)
                .addComponent(jToggleButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1_Cancelar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel8)
                    .addComponent(jComboBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(jComboBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jComboBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jToggleButton1)
                    .addComponent(jButton1_Cancelar)
                    .addComponent(jButton2))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 482, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
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

    private void jComboBox5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBox5ActionPerformed

    private void jComboBox7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox7ActionPerformed

    }//GEN-LAST:event_jComboBox7ActionPerformed

    private void jButton1_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1_CancelarActionPerformed
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
            java.util.logging.Logger.getLogger(EditarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditarConsulta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EditarConsulta().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1_Cancelar;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBox2;
    private javax.swing.JComboBox<String> jComboBox3;
    private javax.swing.JComboBox<String> jComboBox4;
    private javax.swing.JComboBox<String> jComboBox5;
    private javax.swing.JComboBox<String> jComboBox6;
    private javax.swing.JComboBox<String> jComboBox7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
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
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JToggleButton jToggleButton1;
    // End of variables declaration//GEN-END:variables

}
