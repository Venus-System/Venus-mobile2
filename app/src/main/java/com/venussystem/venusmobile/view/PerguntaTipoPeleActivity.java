package com.venussystem.venusmobile.view;

import androidx.annotation.Nullable;

import com.venussystem.venusmobile.R;

public class PerguntaTipoPeleActivity extends PerguntaBaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_pergunta_tipo_pele;
    }

    @Override
    @Nullable
    protected Class<?> getProximaTela() {
        return PerguntaSensibilidadeActivity.class;
    }
}
