package com.example.venusmobile.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.venusmobile.R;

public class LoginActivity extends AppCompatActivity {
    TextView txtEsqueciSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Sem padding embaixo: o card precisa encostar na borda da tela.
            // O respiro da barra de navegação vai no conteúdo do card, não aqui.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            return insets;
        });

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());
        txtEsqueciSenha = findViewById(R.id.textEsqueciSenha);
        txtEsqueciSenha.setOnClickListener(v ->
                startActivity(new Intent(this, RecuperarSenhaActivity.class)));
    }
}
