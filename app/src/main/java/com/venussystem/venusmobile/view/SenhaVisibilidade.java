package com.venussystem.venusmobile.view;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.widget.EditText;

import com.venussystem.venusmobile.R;

/**
 * Faz o olho do campo de senha alternar entre mostrar e ocultar.
 *
 * O icone e desenhado pelo drawableEnd do proprio EditText, entao nao existe
 * um botao separado para receber clique: e preciso detectar o toque na regiao
 * dele dentro do campo.
 */
public class SenhaVisibilidade {

    private SenhaVisibilidade() {
    }

    @SuppressLint("ClickableViewAccessibility")
    public static void aplicar(EditText campo) {
        campo.setOnTouchListener((v, event) -> {
            if (event.getAction() != MotionEvent.ACTION_UP) {
                return false;
            }

            // indice 2 = drawableEnd
            Drawable icone = campo.getCompoundDrawablesRelative()[2];
            if (icone == null) {
                return false;
            }

            int inicioDoIcone = campo.getWidth() - campo.getPaddingEnd() - icone.getBounds().width();
            if (event.getX() < inicioDoIcone) {
                // Toque no meio do campo: deixa passar para o EditText posicionar o cursor.
                return false;
            }

            alternar(campo);
            v.performClick();
            return true;
        });
    }

    private static void alternar(EditText campo) {
        boolean estaVisivel = campo.getTransformationMethod() == null;

        // Troca a transformacao em vez do inputType: mexer no inputType
        // reseta a fonte do campo para monospace no meio da digitacao.
        campo.setTransformationMethod(estaVisivel
                ? PasswordTransformationMethod.getInstance()
                : null);

        campo.setCompoundDrawablesRelativeWithIntrinsicBounds(
                0, 0, estaVisivel ? R.drawable.ic_olho : R.drawable.ic_olho_fechado, 0);

        // Sem isso o cursor volta para o inicio a cada toque no olho.
        campo.setSelection(campo.getText().length());
    }
}
