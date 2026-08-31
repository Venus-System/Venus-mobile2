package com.venussystem.venusmobile.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.venussystem.venusmobile.R;

public class PerguntaCabeloActivity extends PerguntaBaseActivity {

    @Override
    protected int getLayout() {
        return R.layout.activity_pergunta_cabelo;
    }

    @Override
    @Nullable
    protected Class<?> getProximaTela() {
        return PerguntaCouroCabeludoActivity.class;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        findViewById(R.id.textAjudaCabelo).setOnClickListener(v -> abrirAjudaTipoCabelo());
    }

    private void abrirAjudaTipoCabelo() {
        BottomSheetDialog sheet = new BottomSheetDialog(this);
        View conteudo = getLayoutInflater().inflate(R.layout.sheet_tipo_cabelo, null);
        sheet.setContentView(conteudo);

        conteudo.findViewById(R.id.btnFecharSheet).setOnClickListener(v -> sheet.dismiss());
        conteudo.findViewById(R.id.btnEntendi).setOnClickListener(v -> sheet.dismiss());

        sheet.show();
    }
}
