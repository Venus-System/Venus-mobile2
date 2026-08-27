package com.venussystem.venusmobile.repository;

import android.content.Context;
import android.content.SharedPreferences;

public class PerfilRepository {
    private static final String ARQUIVO = "venus_perfil";
    private static final String CHAVE_RESPONDEU = "respondeu_questionario";

    private final SharedPreferences prefs;

    public PerfilRepository(Context context) {
        this.prefs = context.getApplicationContext()
                .getSharedPreferences(ARQUIVO, Context.MODE_PRIVATE);
    }

    public boolean jaRespondeuQuestionario() {
        return prefs.getBoolean(CHAVE_RESPONDEU, false);
    }

    public void marcarQuestionarioRespondido() {
        prefs.edit().putBoolean(CHAVE_RESPONDEU, true).apply();
    }

    
    public void limpar() {
        prefs.edit().clear().apply();
    }
}
