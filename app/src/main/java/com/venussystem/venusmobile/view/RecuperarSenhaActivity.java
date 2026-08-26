package com.venussystem.venusmobile.view;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.venussystem.venusmobile.R;
import com.venussystem.venusmobile.viewmodel.RecuperarSenhaViewModel;

public class RecuperarSenhaActivity extends AppCompatActivity {

    private RecuperarSenhaViewModel viewModel;
    private EditText editEmail;
    private AppCompatButton btnEnviarEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acitivity_recuperar_senha);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            Insets teclado = insets.getInsets(WindowInsetsCompat.Type.ime());
            // Sem padding embaixo: o card precisa encostar na borda da tela.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            // O respiro vai no card: com o teclado aberto ele cresce, e o
            // conteúdo rola por cima em vez de ficar escondido.
            int folga = Math.max(teclado.bottom, systemBars.bottom);
            findViewById(R.id.cardRecuperar).setPadding(0, 0, 0, folga);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(RecuperarSenhaViewModel.class);

        editEmail = findViewById(R.id.editEmail);
        btnEnviarEmail = findViewById(R.id.btnEnviarEmail);

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        btnEnviarEmail.setOnClickListener(v ->
                viewModel.enviarEmail(editEmail.getText().toString()));

        observarViewModel();
    }

    private void observarViewModel() {
        viewModel.getErroEmail().observe(this, erro -> editEmail.setError(erro));

        viewModel.getCarregando().observe(this, carregando -> {
            btnEnviarEmail.setEnabled(!carregando);
            btnEnviarEmail.setText(carregando
                    ? getString(R.string.estado_enviando)
                    : getString(R.string.acao_enviar_email));
        });

        viewModel.getResultado().observe(this, resultado -> {
            if (resultado == null) {
                return;
            }
            if (resultado.isSucesso()) {
                // Texto proposital: nao confirma se a conta existe, para nao
                // permitir descobrir quais emails estao cadastrados.
                new AlertDialog.Builder(this)
                        .setTitle(R.string.recuperar_titulo)
                        .setMessage(R.string.recuperar_email_enviado)
                        .setPositiveButton(android.R.string.ok, (d, w) -> finish())
                        .show();
            } else {
                Toast.makeText(this, resultado.getErro(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
