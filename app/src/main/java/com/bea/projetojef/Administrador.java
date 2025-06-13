package com.bea.projetojef;

public class Administrador {
    private String cracha;
    private String nome;
    private String inicio;
    private long id;

    public Administrador() {}

    public Administrador(long id, String cracha, String nome, String inicio) {
        this.id = id;
        this.cracha = cracha;
        this.nome = nome;
        this.inicio = inicio;
    }

    // Getters
    public long getId() { return id; }
    public String getCracha() { return cracha; }
    public String getNome() { return nome; }
    public String getInicio() { return inicio; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setCracha(String cracha) { this.cracha = cracha; }
    public void setNome(String nome) { this.nome = nome; }
    public void setInicio(String inicio) { this.inicio = inicio; }
}
