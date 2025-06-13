package com.bea.projetojef;

public class Atendimento {
    private String cracha;
    private String nome;
    private String inicio;
    private String termino;
    private long id;

    public Atendimento() {}

    public Atendimento(long id, String cracha, String nome, String inicio, String termino) {
        this.id = id;
        this.cracha = cracha;
        this.nome = nome;
        this.inicio = inicio;
        this.termino = termino;
    }

    // Getters
    public long getId() { return id; }
    public String getCracha() { return cracha; }
    public String getNome() { return nome; }
    public String getInicio() { return inicio; }
    public String getTermino() { return termino; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setCracha(String cracha) { this.cracha = cracha; }
    public void setNome(String nome) { this.nome = nome; }
    public void setInicio(String inicio) { this.inicio = inicio; }
    public void setTermino(String termino) { this.termino = termino; }
}
