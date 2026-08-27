package com.venussystem.venusmobile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.venussystem.venusmobile.model.ResultadoAuth;
import com.venussystem.venusmobile.repository.AutenticacaoRepository;

import java.util.regex.Pattern;

public class LoginViewModel extends ViewModel {
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final AutenticacaoRepository repository = new AutenticacaoRepository();

    private final MutableLiveData<String> erroEmail = new MutableLiveData<>();
    private final MutableLiveData<String> erroSenha = new MutableLiveData<>();
    private final MutableLiveData<Boolean> carregando = new MutableLiveData<>(false);
    private final MutableLiveData<ResultadoAuth> resultado = new MutableLiveData<>();

    public LiveData<String> getErroEmail() {
        return erroEmail;
    }

    public LiveData<String> getErroSenha() {
        return erroSenha;
    }

    public LiveData<Boolean> getCarregando() {
        return carregando;
    }

    public LiveData<ResultadoAuth> getResultado() {
        return resultado;
    }

    public void entrar(String email, String senha) {
        if (Boolean.TRUE.equals(carregando.getValue())) {
            return;
        }

        String emailLimpo = email == null ? "" : email.trim();
        String senhaLimpa = senha == null ? "" : senha;

        if (!validar(emailLimpo, senhaLimpa)) {
            return;
        }

        carregando.setValue(true);
        repository.entrar(emailLimpo, senhaLimpa)
                .observeForever(r -> {
                    carregando.setValue(false);
                    resultado.setValue(r);
                });
    }

    
    public void entrarComGoogle(String idToken) {
        carregando.setValue(true);
        repository.entrarComGoogle(idToken)
                .observeForever(r -> {
                    carregando.setValue(false);
                    resultado.setValue(r);
                });
    }

    
    public void falhaGoogle(String mensagem) {
        carregando.setValue(false);
        resultado.setValue(ResultadoAuth.erro(mensagem));
    }

    public void cancelarCarregamento() {
        carregando.setValue(false);
    }

    
    private boolean validar(String email, String senha) {
        erroEmail.setValue(null);
        erroSenha.setValue(null);
        boolean ok = true;

        if (email.isEmpty()) {
            erroEmail.setValue("Informe seu email");
            ok = false;
        } else if (!EMAIL.matcher(email).matches()) {
            erroEmail.setValue("Email inválido");
            ok = false;
        }
        if (senha.isEmpty()) {
            erroSenha.setValue("Informe sua senha");
            ok = false;
        }

        return ok;
    }
}
