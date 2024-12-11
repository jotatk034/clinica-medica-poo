package br.edu.imepac.administrativo.entidades;

public class Prontuario {
    private int id;
    private String nome;
    private String descricao;
    private String dadosConsulta;
    private String receituario;
    private String exames;
    private String observacoes;

    // Construtores
    public Prontuario() {
        // Construtor padrão
    }

    public Prontuario(int id, String nome, String descricao, String dadosConsulta, String receituario, String exames, String observacoes) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dadosConsulta = dadosConsulta;
        this.receituario = receituario;
        this.exames = exames;
        this.observacoes = observacoes;
    }

    // Getter and Setter para id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getters e Setters para os demais atributos
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDadosConsulta() {
        return dadosConsulta;
    }

    public void setDadosConsulta(String dadosConsulta) {
        this.dadosConsulta = dadosConsulta;
    }

    public String getReceituario() {
        return receituario;
    }

    public void setReceituario(String receituario) {
        this.receituario = receituario;
    }

    public String getExames() {
        return exames;
    }

    public void setExames(String exames) {
        this.exames = exames;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}