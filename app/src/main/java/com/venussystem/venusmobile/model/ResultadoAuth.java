package com.venussystem.venusmobile.model;

public class ResultadoAuth {
    private final boolean sucesso;
    private final String erro;

    private ResultadoAuth(boolean sucesso, String erro) {
        this.sucesso = sucesso;
        this.erro = erro;
    }

    public static ResultadoAuth sucesso() {
        return new ResultadoAuth(true, null);
    }

    public static ResultadoAuth erro(String mensagem) {
        return new ResultadoAuth(false, mensagem);
    }

    public boolean isSucesso() {
        return sucesso;
    }

    public String getErro() {
        return erro;
    }
}
