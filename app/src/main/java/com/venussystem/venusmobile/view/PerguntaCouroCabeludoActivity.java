package com.venussystem.venusmobile.view;

import androidx.annotation.Nullable;

import com.venussystem.venusmobile.R;

public class PerguntaCouroCabeludoActivity extends PerguntaBaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_pergunta_couro;
    }

    @Override
    @Nullable
    protected Class<?> getProximaTela() {
        return PerguntaTipoPeleActivity.class;
    }
}
