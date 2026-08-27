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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.venussystem.venusmobile.R;
import com.venussystem.venusmobile.repository.PerfilRepository;

public class PerguntaCabeloActivity extends AppCompatActivity {
    
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

        findViewById(R.id.textAjudaCabelo).setOnClickListener(v -> abrirAjudaTipoCabelo());
    }

    private void abrirAjudaTipoCabelo() {
        BottomSheetDialog sheet = new BottomSheetDialog(this);
        View conteudo = getLayoutInflater().inflate(R.layout.sheet_tipo_cabelo, null);
        sheet.setContentView(conteudo);

        conteudo.findViewById(R.id.btnFecharSheet).setOnClickListener(v -> sheet.dismiss());
        conteudo.findViewById(R.id.btnEntendi).setOnClickListener(v -> sheet.dismiss());

        sheet.show();
    }

    
    private void prepararOpcoes() {
        LinearLayout lista = findViewById(R.id.listaOpcoes);
        for (int i = 0; i < lista.getChildCount(); i++) {
            View opcao = lista.getChildAt(i);
            opcao.setOnClickListener(v -> selecionar(lista, v));
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

        new PerfilRepository(this).marcarQuestionarioRespondido();
        NavegacaoPosLogin.irParaMenu(this);
    }
}
