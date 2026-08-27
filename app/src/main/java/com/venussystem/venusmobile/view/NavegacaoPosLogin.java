package com.venussystem.venusmobile.view;

import android.app.Activity;
import android.content.Intent;

import com.venussystem.venusmobile.repository.PerfilRepository;
public class NavegacaoPosLogin {
    private NavegacaoPosLogin() {
    }

    public static void seguir(Activity origem) {
        PerfilRepository perfil = new PerfilRepository(origem);

        if (perfil.jaRespondeuQuestionario()) {
            irParaMenu(origem);
            return;
        }

        origem.startActivity(new Intent(origem, PesquisaInicialActivity.class));
        origem.finish();
    }

    public static void irParaMenu(Activity origem) {
        Intent intent = new Intent(origem, MenuActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        origem.startActivity(intent);
    }
}
