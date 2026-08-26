package com.venussystem.venusmobile.view;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.venussystem.venusmobile.R;
import com.venussystem.venusmobile.repository.PerfilRepository;

public class PerguntaCabeloActivity extends AppCompatActivity {

    /** Guarda a alternativa escolhida. Null enquanto o usuario nao decide. */
    private String respostaSelecionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pergunta_cabelo);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        prepararOpcoes();

        findViewById(R.id.btnAvancar).setOnClickListener(v -> avancar());
    }

    /**
     * Liga o clique em cada alternativa. Percorrer os filhos evita repetir
     * findViewById para as seis opcoes — e nao muda se voce adicionar mais
     * alternativas no XML.
     */
    private void prepararOpcoes() {
        LinearLayout lista = findViewById(R.id.listaOpcoes);
        for (int i = 0; i < lista.getChildCount(); i++) {
            View opcao = lista.getChildAt(i);
            opcao.setOnClickListener(v -> selecionar(lista, v));
        }
    }

    private void selecionar(LinearLayout lista, View escolhida) {
        // Desmarca todas antes: o drawable reage a state_selected sozinho.
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
        // TODO: enviar respostaSelecionada para a API, e seguir para a
        // proxima pergunta do questionario quando as outras existirem.
        new PerfilRepository(this).marcarQuestionarioRespondido();
        NavegacaoPosLogin.irParaMenu(this);
    }
}
