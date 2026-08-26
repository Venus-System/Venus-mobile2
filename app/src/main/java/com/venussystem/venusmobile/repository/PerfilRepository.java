package com.venussystem.venusmobile.repository;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Diz se o usuario ja respondeu o questionario.
 *
 * PROVISORIO: hoje grava no proprio aparelho, porque a API ainda nao existe.
 * Quando ela ficar pronta, so este arquivo muda — as telas continuam
 * chamando jaRespondeuQuestionario() do mesmo jeito.
 *
 * Limitacao de agora: reinstalar o app ou trocar de celular faz o usuario
 * responder tudo de novo. Some quando os dados forem para a API.
 */
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

    /** Util para testar o fluxo de primeira entrada sem reinstalar o app. */
    public void limpar() {
        prefs.edit().clear().apply();
    }
}
