package br.edu.imepac.administrativo.entidades;

import java.time.LocalDate;

public class Prontuario {
    private int id;
    private String descricao;
    private LocalDate data;
    private int pacienteId;

    public Prontuario() {
    }

    public Prontuario(int id, String descricao, LocalDate data, int pacienteId) {
        this.id = id;
        this.descricao = descricao;
        this.data = data;
        this.pacienteId = pacienteId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }
}
