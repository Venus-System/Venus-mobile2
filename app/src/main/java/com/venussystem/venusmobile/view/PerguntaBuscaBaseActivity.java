package com.venussystem.venusmobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.ArrayRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.venussystem.venusmobile.R;
import com.venussystem.venusmobile.repository.PerfilRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Pergunta de multipla escolha por busca: o usuario digita, escolhe da
 * sugestao, e o item vira um chip removivel.
 *
 * Diferente da PerguntaBaseActivity, aqui responder nada e uma resposta
 * valida — "nenhuma alergia" e uma informacao legitima.
 */
public abstract class PerguntaBuscaBaseActivity extends AppCompatActivity {

    private final List<String> selecionados = new ArrayList<>();
    private LinearLayout lista;

    @LayoutRes
    protected abstract int getLayout();

    @ArrayRes
    protected abstract int getOpcoes();

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

        lista = findViewById(R.id.listaSelecionados);

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());
        findViewById(R.id.btnAvancar).setOnClickListener(v -> avancar());

        prepararBusca();
    }

    private void prepararBusca() {
        AppCompatAutoCompleteTextView campo = findViewById(R.id.campoBusca);
        List<String> opcoes = Arrays.asList(getResources().getStringArray(getOpcoes()));

        campo.setAdapter(new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, opcoes));

        campo.setOnItemClickListener((parent, view, posicao, id) -> {
            adicionar((String) parent.getItemAtPosition(posicao));
            campo.setText("");
        });
    }

    private void adicionar(String item) {
        if (selecionados.contains(item)) {
            return;
        }
        selecionados.add(item);

        View chip = getLayoutInflater().inflate(R.layout.item_chip_selecionado, lista, false);
        ((TextView) chip.findViewById(R.id.textChip)).setText(item);
        chip.findViewById(R.id.btnRemoverChip).setOnClickListener(v -> {
            selecionados.remove(item);
            lista.removeView(chip);
        });
        lista.addView(chip);
    }

    private void avancar() {
        Class<?> proxima = getProximaTela();
        if (proxima != null) {
            startActivity(new Intent(this, proxima));
            return;
        }

        new PerfilRepository(this).marcarQuestionarioRespondido();
        NavegacaoPosLogin.irParaMenu(this);
    }

    protected List<String> getSelecionados() {
        return selecionados;
    }
}
