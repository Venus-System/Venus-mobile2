package com.venussystem.venusmobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.venussystem.venusmobile.R;
import com.venussystem.venusmobile.repository.PerfilRepository;

public abstract class PerguntaBaseActivity extends AppCompatActivity {

    private String respostaSelecionada;

    @LayoutRes
    protected abstract int getLayout();

    /** Null quando esta e a ultima pergunta do questionario. */
    @Nullable
    protected abstract Class<?> getProximaTela();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(getLayout());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());
        findViewById(R.id.btnAvancar).setOnClickListener(v -> avancar());

        prepararOpcoes();
    }

    private void prepararOpcoes() {
        LinearLayout lista = findViewById(R.id.listaOpcoes);
        for (int i = 0; i < lista.getChildCount(); i++) {
            lista.getChildAt(i).setOnClickListener(v -> selecionar(lista, v));
        }
    }

    private void selecionar(LinearLayout lista, View escolhida) {
        for (int i = 0; i < lista.getChildCount(); i++) {
            lista.getChildAt(i).setSelected(false);
        }
        escolhida.setSelected(true);
        respostaSelecionada = String.valueOf(escolhida.getTag());
    }

    private void avancar() {
        if (respostaSelecionada == null) {
            Toast.makeText(this, R.string.pergunta_escolha_uma, Toast.LENGTH_SHORT).show();
            return;
        }

        Class<?> proxima = getProximaTela();
        if (proxima != null) {
            startActivity(new Intent(this, proxima));
            return;
        }

        new PerfilRepository(this).marcarQuestionarioRespondido();
        NavegacaoPosLogin.irParaMenu(this);
    }

    protected String getRespostaSelecionada() {
        return respostaSelecionada;
    }
}
