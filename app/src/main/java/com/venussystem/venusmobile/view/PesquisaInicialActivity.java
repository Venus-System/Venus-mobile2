package com.venussystem.venusmobile.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.venussystem.venusmobile.R;

/**
 * Porta de entrada do questionario. O usuario pode comecar a responder
 * ou pular — nos dois casos ele acaba no menu principal.
 */
public class PesquisaInicialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pesquisa_inicial);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        findViewById(R.id.btnAvancar).setOnClickListener(v ->
                startActivity(new Intent(this, PerguntaCabeloActivity.class)));

        findViewById(R.id.textResponderDepois).setOnClickListener(v -> irParaMenu());
    }

    private void irParaMenu() {
        // TODO: trocar por startActivity da tela de menu (Busca) quando existir.
        finish();
    }
}
