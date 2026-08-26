package com.venussystem.venusmobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.venussystem.venusmobile.R;
import com.venussystem.venusmobile.model.Usuario;
import com.venussystem.venusmobile.repository.AutenticacaoRepository;
import com.venussystem.venusmobile.repository.PerfilRepository;

/**
 * PROVISORIA: existe so para o fluxo ter um destino visivel.
 *
 * Sem ela, login e questionario terminavam em finish() e pareciam
 * "voltar para a tela de Bem vindo" — o que confunde na hora de testar.
 *
 * Sera substituida pela tela de Busca com a barra de navegacao inferior.
 */
public class MenuActivity extends AppCompatActivity {

    private final AutenticacaoRepository autenticacao = new AutenticacaoRepository();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        mostrarUsuario();

        findViewById(R.id.btnSair).setOnClickListener(v -> sair());

        // Atalho de teste: zera a marcacao para o questionario aparecer de novo
        // no proximo login, sem precisar limpar os dados do app.
        findViewById(R.id.btnRefazerQuestionario).setOnClickListener(v -> {
            new PerfilRepository(this).limpar();
            Toast.makeText(this, R.string.questionario_reiniciado, Toast.LENGTH_SHORT).show();
        });
    }

    private void mostrarUsuario() {
        Usuario usuario = autenticacao.usuarioLogado();
        TextView texto = findViewById(R.id.textUsuario);
        if (usuario == null) {
            texto.setText(R.string.menu_sem_usuario);
            return;
        }
        String nome = usuario.getNome() == null ? "" : usuario.getNome();
        texto.setText(getString(R.string.menu_logado_como, nome, usuario.getEmail()));
    }

    private void sair() {
        autenticacao.sair();
        // CLEAR_TASK limpa a pilha: sem isso o botao voltar traria de volta
        // esta tela, ja com o usuario deslogado.
        Intent intent = new Intent(this, BemVindoActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
