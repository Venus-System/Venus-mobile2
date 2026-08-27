package com.venussystem.venusmobile.view;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.EditText;

import com.venussystem.venusmobile.R;

public class SenhaVisibilidade {
    private SenhaVisibilidade() {
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void aplicar(EditText campo) {
        campo.setOnTouchListener((v, event) -> {
            if (event.getAction() != MotionEvent.ACTION_UP) {
                return false;
            }

            Drawable icone = campo.getCompoundDrawablesRelative()[2];
            if (icone == null) {
                return false;
            }

            int inicioDoIcone = campo.getWidth() - campo.getPaddingEnd() - icone.getBounds().width();
            if (event.getX() < inicioDoIcone) {
                return false;
            }

            alternar(campo);
            v.performClick();
            return true;
        });
    }

    private static void alternar(EditText campo) {
        boolean estaVisivel = campo.getTransformationMethod() == null;

        campo.setTransformationMethod(estaVisivel
                ? PasswordTransformationMethod.getInstance()
                : null);

        campo.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0, 0, estaVisivel ? R.drawable.ic_olho : R.drawable.ic_olho_fechado, 0);

        campo.setSelection(campo.getText().length());
    }
}
