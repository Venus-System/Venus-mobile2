package com.venussystem.venusmobile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.venussystem.venusmobile.model.ResultadoAuth;
import com.venussystem.venusmobile.repository.AutenticacaoRepository;

import java.util.regex.Pattern;

public class RecuperarSenhaViewModel extends ViewModel {
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    private final AutenticacaoRepository repository = new AutenticacaoRepository();

    private final MutableLiveData<String> erroEmail = new MutableLiveData<>();
    private final MutableLiveData<Boolean> carregando = new MutableLiveData<>(false);
    private final MutableLiveData<ResultadoAuth> resultado = new MutableLiveData<>();

    public LiveData<String> getErroEmail() {
        return erroEmail;
    }

    public LiveData<Boolean> getCarregando() {
        return carregando;
    }

    public LiveData<ResultadoAuth> getResultado() {
        return resultado;
    }

    public void enviarEmail(String email) {
        if (Boolean.TRUE.equals(carregando.getValue())) {
            return;
        }

        String emailLimpo = email == null ? "" : email.trim();

        erroEmail.setValue(null);
        if (emailLimpo.isEmpty()) {
            erroEmail.setValue("Informe seu email");
            return;
        }
        if (!EMAIL.matcher(emailLimpo).matches()) {
            erroEmail.setValue("Email inválido");
            return;
        }

        carregando.setValue(true);
        repository.recuperarSenha(emailLimpo)
                .observeForever(r -> {
                    carregando.setValue(false);
                    resultado.setValue(r);
                });
    }
}
