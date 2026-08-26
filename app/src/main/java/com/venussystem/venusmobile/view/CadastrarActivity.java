package com.venussystem.venusmobile.view;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.venussystem.venusmobile.R;
import com.venussystem.venusmobile.viewmodel.CadastrarViewModel;

public class CadastrarActivity extends AppCompatActivity {

    private CadastrarViewModel viewModel;
    private EditText editNome;
    private EditText editEmail;
    private EditText editSenha;
    private AppCompatButton btnCriarConta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cadastrar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            // Sem padding embaixo na raiz: o card precisa encostar na borda.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0);
            // O respiro da barra de navegação vai no conteúdo do card.
            // O teclado não entra na conta: quem cuida dele é o adjustPan.
            // Preserva o padding lateral do XML: setPadding sobrescreve os quatro lados.
            View card = findViewById(R.id.cardCadastrar);
            card.setPadding(card.getPaddingLeft(), card.getPaddingTop(),
                    card.getPaddingRight(), systemBars.bottom);
            return insets;
        });

        viewModel = new ViewModelProvider(this).get(CadastrarViewModel.class);

        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editSenha = findViewById(R.id.editSenha);
        btnCriarConta = findViewById(R.id.btnCriarConta);

        findViewById(R.id.btnVoltar).setOnClickListener(v -> finish());

        SenhaVisibilidade.aplicar(editSenha);

        btnCriarConta.setOnClickListener(v -> viewModel.cadastrar(
                editNome.getText().toString(),
                editEmail.getText().toString(),
                editSenha.getText().toString()));

        LoginGoogleHelper google = new LoginGoogleHelper(this);
        findViewById(R.id.btnGoogle).setOnClickListener(v -> google.iniciar(
                new LoginGoogleHelper.Callback() {
                    @Override
                    public void aoObterToken(String idToken) {
                        viewModel.entrarComGoogle(idToken);
                    }

                    @Override
                    public void aoFalhar(String mensagem) {
                        viewModel.falhaGoogle(mensagem);
                    }

                    @Override
                    public void aoCancelar() {
                        viewModel.cancelarCarregamento();
                    }
                }));

        observarViewModel();
    }

    private void observarViewModel() {
        viewModel.getErroNome().observe(this, erro -> editNome.setError(erro));
        viewModel.getErroEmail().observe(this, erro -> editEmail.setError(erro));
        viewModel.getErroSenha().observe(this, erro -> editSenha.setError(erro));

        viewModel.getCarregando().observe(this, carregando -> {
            btnCriarConta.setEnabled(!carregando);
            btnCriarConta.setText(carregando
                    ? getString(R.string.estado_criando_conta)
                    : getString(R.string.acao_criar_conta));
        });

        viewModel.getResultado().observe(this, resultado -> {
            if (resultado == null) {
                return;
            }
            if (resultado.isSucesso()) {
                Toast.makeText(this, R.string.cadastro_sucesso, Toast.LENGTH_LONG).show();
                // O cadastro ja deixa o usuario autenticado: segue o mesmo
                // caminho do login, sem passar pela tela de entrar.
                NavegacaoPosLogin.seguir(this);
            } else {
                Toast.makeText(this, resultado.getErro(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
