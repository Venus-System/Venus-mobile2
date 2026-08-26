package com.venussystem.venusmobile.model;

/**
 * Dados do usuario autenticado. Sem logica e sem nada de Android:
 * so guarda o que veio do Firebase.
 */
public class Usuario {

    private final String uid;
    private final String nome;
    private final String email;

    public Usuario(String uid, String nome, String email) {
        this.uid = uid;
        this.nome = nome;
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }
}
