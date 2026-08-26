package com.venussystem.venusmobile.view;

import android.app.Activity;

import androidx.core.content.ContextCompat;
import androidx.credentials.Credential;
import androidx.credentials.CredentialManager;
import androidx.credentials.CredentialManagerCallback;
import androidx.credentials.CustomCredential;
import androidx.credentials.GetCredentialRequest;
import androidx.credentials.GetCredentialResponse;
import androidx.credentials.exceptions.GetCredentialCancellationException;
import androidx.credentials.exceptions.GetCredentialException;

import com.google.android.libraries.identity.googleid.GetGoogleIdOption;
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential;
import com.venussystem.venusmobile.R;


public class LoginGoogleHelper {

    public interface Callback {
        void aoObterToken(String idToken);

        void aoFalhar(String mensagem);

        void aoCancelar();
    }

    private final Activity activity;
    private final CredentialManager credentialManager;

    public LoginGoogleHelper(Activity activity) {
        this.activity = activity;
        this.credentialManager = CredentialManager.create(activity);
    }

    public void iniciar(Callback callback) {

        String webClientId = activity.getString(R.string.default_web_client_id);

        GetGoogleIdOption opcaoGoogle = new GetGoogleIdOption.Builder()
                .setServerClientId(webClientId)
                .setFilterByAuthorizedAccounts(false)
                .build();

        GetCredentialRequest requisicao = new GetCredentialRequest.Builder()
                .addCredentialOption(opcaoGoogle)
                .build();

        credentialManager.getCredentialAsync(
                activity,
                requisicao,
                null,
                ContextCompat.getMainExecutor(activity),
                new CredentialManagerCallback<GetCredentialResponse, GetCredentialException>() {
                    @Override
                    public void onResult(GetCredentialResponse resultado) {
                        extrairToken(resultado.getCredential(), callback);
                    }

                    @Override
                    public void onError(GetCredentialException e) {
                        if (e instanceof GetCredentialCancellationException) {
                            callback.aoCancelar();
                            return;
                        }
                        callback.aoFalhar(activity.getString(R.string.erro_google));
                    }
                }
        );
    }

    private void extrairToken(Credential credencial, Callback callback) {
        if (!(credencial instanceof CustomCredential)) {
            callback.aoFalhar(activity.getString(R.string.erro_google));
            return;
        }

        CustomCredential custom = (CustomCredential) credencial;
        if (!GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL.equals(custom.getType())) {
            callback.aoFalhar(activity.getString(R.string.erro_google));
            return;
        }

        GoogleIdTokenCredential tokenGoogle = GoogleIdTokenCredential.createFrom(custom.getData());
        callback.aoObterToken(tokenGoogle.getIdToken());
    }
}
