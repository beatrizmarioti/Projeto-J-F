package com.bea.projetojef;

public class AdministradorHelo {
    private long id;
    private String senha;
    private String nome;

    public AdministradorHelo() {}
    public AdministradorHelo(long id, String senha) {
        this.id = id;
        this.senha = senha;
    }

    public AdministradorHelo(long id, String senha, String nome) {
        this.id = id;
        this.senha = senha;
        this.nome = nome;
    }

    // Getters
    public long getId() { return id; }
    public String getSenha() { return senha; }
    public String getNome() { return nome; }

    // Setters
    public void setId(long id) { this.id = id; }
    public void setSenha(String senha) { this.senha = senha; }
    public void setNome(String nome) { this.nome = nome; }

}
