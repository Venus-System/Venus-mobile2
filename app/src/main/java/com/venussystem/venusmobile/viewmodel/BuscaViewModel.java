package com.venussystem.venusmobile.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.venussystem.venusmobile.model.Produto;
import com.venussystem.venusmobile.repository.ProdutoRepository;

import java.util.List;

public class BuscaViewModel extends ViewModel {

    private final ProdutoRepository repository = new ProdutoRepository();
    private final MediatorLiveData<List<Produto>> produtos = new MediatorLiveData<>();
    private LiveData<List<Produto>> fonteAtual;

    public BuscaViewModel() {
        buscar(null);
    }

    public LiveData<List<Produto>> getProdutos() {
        return produtos;
    }

    public void buscar(String termo) {
        // Solta a busca anterior antes de ligar a nova, senao o resultado
        // de uma digitacao antiga pode chegar depois e sobrescrever a atual.
        if (fonteAtual != null) {
            produtos.removeSource(fonteAtual);
        }
        fonteAtual = repository.buscar(termo);
        produtos.addSource(fonteAtual, produtos::setValue);
    }
}
