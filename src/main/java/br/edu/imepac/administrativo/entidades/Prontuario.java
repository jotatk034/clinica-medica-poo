package br.edu.imepac.administrativo.entidades;

public class Prontuario {
    private int id;
    private String receituario;
    private String observacoes;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceituario() {
        return receituario;
    }

    public void setReceituario(String receituario) {
        this.receituario = receituario;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
