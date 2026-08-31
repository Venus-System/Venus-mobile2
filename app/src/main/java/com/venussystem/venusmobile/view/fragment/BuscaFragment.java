package com.venussystem.venusmobile.view.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.venussystem.venusmobile.R;
import com.venussystem.venusmobile.view.adapter.ProdutoAdapter;
import com.venussystem.venusmobile.viewmodel.BuscaViewModel;

public class BuscaFragment extends Fragment {

    private BuscaViewModel viewModel;
    private ProdutoAdapter adapter;
    private TextView textVazio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_busca, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(this).get(BuscaViewModel.class);
        textVazio = view.findViewById(R.id.textVazio);

        adapter = new ProdutoAdapter(produto ->
                Toast.makeText(requireContext(), produto.getName(), Toast.LENGTH_SHORT).show());

        RecyclerView lista = view.findViewById(R.id.listaProdutos);
        lista.setLayoutManager(new LinearLayoutManager(requireContext()));
        lista.setAdapter(adapter);

        EditText campo = view.findViewById(R.id.campoBuscaProduto);
        campo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int j, int k) {
            }

            @Override
            public void onTextChanged(CharSequence s, int i, int j, int k) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.buscar(s.toString());
            }
        });

        viewModel.getProdutos().observe(getViewLifecycleOwner(), produtos -> {
            adapter.atualizar(produtos);
            boolean vazio = produtos == null || produtos.isEmpty();
            textVazio.setVisibility(vazio ? View.VISIBLE : View.GONE);
        });
    }
}
