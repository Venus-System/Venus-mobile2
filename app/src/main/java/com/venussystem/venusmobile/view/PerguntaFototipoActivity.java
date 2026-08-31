package com.venussystem.venusmobile.view;

import androidx.annotation.Nullable;

import com.venussystem.venusmobile.R;

public class PerguntaFototipoActivity extends PerguntaBaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_pergunta_fototipo;
    }

    @Override
    @Nullable
    protected Class<?> getProximaTela() {
        return PerguntaFaixaEtariaActivity.class;
    }
}
