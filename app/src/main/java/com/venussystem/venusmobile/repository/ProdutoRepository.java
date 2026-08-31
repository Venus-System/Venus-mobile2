package com.venussystem.venusmobile.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.venussystem.venusmobile.model.Produto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * PROVISORIO: devolve uma lista fixa porque a Venus-CRUD ainda nao expoe
 * endpoints — hoje ela tem os DTOs e as entidades, mas nenhum controller.
 *
 * As fotos vem do Open Beauty Facts (banco aberto de cosmeticos), so para
 * as telas terem imagem real enquanto o Cloudinary nao esta populado.
 * As notas sao inventadas.
 *
 * Quando a API subir, so este arquivo muda: troca a lista pela chamada
 * Retrofit. A assinatura dos metodos e o model continuam iguais.
 */
public class ProdutoRepository {

    private static final String IMG = "https://images.openbeautyfacts.org/images/products/";

    private static final List<Produto> CATALOGO = Arrays.asList(
            new Produto(1L, "Nivea Creme", "Nivea", 87,
                    IMG + "400/580/889/0590/front_en.18.400.jpg"),
            new Produto(2L, "Nivea Soft", "Nivea", 74,
                    IMG + "400/580/889/0576/front_en.26.400.jpg"),
            new Produto(3L, "Body Lotion", "Nivea", 68,
                    IMG + "400/590/036/9581/front_ar.21.400.jpg"),
            new Produto(4L, "Pearl & Beauty", "Nivea", 55,
                    IMG + "400/580/883/7359/front_en.26.400.jpg"),
            new Produto(5L, "Coconut Milk", "Herbal Essences", 62,
                    IMG + "800/109/066/2231/front_fr.11.400.jpg"),
            new Produto(6L, "Golden Moringa Oil Shampoo", "Herbal Essences", 71,
                    IMG + "800/109/066/1999/front_fr.8.400.jpg"),
            new Produto(7L, "Argan Oil Shampoo", "Deliplus", 45,
                    IMG + "848/000/044/3373/front_en.24.400.jpg"),
            new Produto(8L, "Crema Hidratante", "Deliplus", 83,
                    IMG + "848/000/046/8338/front_es.17.400.jpg"),
            new Produto(9L, "Anti Dandruff Shampoo", "Dercos", 22,
                    IMG + "333/787/133/0262/front_fr.3.400.jpg"),
            new Produto(10L, "Shampoo", "Clear", 38,
                    IMG + "622/115/505/8522/front_en.20.400.jpg"),
            new Produto(11L, "Hidratante", "Deliplus", 90,
                    IMG + "840/200/100/5558/front_en.13.400.jpg"),
            new Produto(12L, "Johnsons", "Johnsons", 51,
                    IMG + "622/300/065/9243/front_fr.3.400.jpg")
    );

    public LiveData<List<Produto>> listar() {
        return new MutableLiveData<>(CATALOGO);
    }

    public LiveData<List<Produto>> buscar(String termo) {
        if (termo == null || termo.trim().isEmpty()) {
            return listar();
        }
        String alvo = termo.trim().toLowerCase(Locale.getDefault());

        List<Produto> encontrados = new ArrayList<>();
        for (Produto p : CATALOGO) {
            boolean bate = p.getName().toLowerCase(Locale.getDefault()).contains(alvo)
                    || p.getBrandName().toLowerCase(Locale.getDefault()).contains(alvo);
            if (bate) {
                encontrados.add(p);
            }
        }
        return new MutableLiveData<>(encontrados);
    }
}
