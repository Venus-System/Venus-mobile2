package com.venussystem.venusmobile.view;

import androidx.annotation.Nullable;

import com.venussystem.venusmobile.R;

public class PerguntaSensibilidadeActivity extends PerguntaBaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_pergunta_sensibilidade;
    }

    @Override
    @Nullable
    protected Class<?> getProximaTela() {
        return PerguntaFototipoActivity.class;
    }
}
