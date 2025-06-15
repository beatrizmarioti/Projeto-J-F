package com.bea.projetojef.ui.admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bea.projetojef.Administrador;
import com.bea.projetojef.ui.AdminAdapter;
import com.bea.projetojef.databinding.FragmentNavigationRemoverBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class navigation_remover extends Fragment {

    private FragmentNavigationRemoverBinding binding;
    private FirebaseFirestore db;
    private final List<Administrador> lista = new ArrayList<>();
    private AdminAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNavigationRemoverBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        adapter = new AdminAdapter(lista);
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView2.setAdapter(adapter);

        carregarTudo();
    }

    private void carregarTudo() {
        db.collection("colaborador")
                .get()
                .addOnSuccessListener(snapshots -> {
                    lista.clear();
                    for (QueryDocumentSnapshot doc : snapshots) {
                        Administrador a = doc.toObject(Administrador.class);
                        Long id = doc.getLong("id");
                        if (id != null) {
                            a.setId(id);
                        }
                        lista.add(a);
                    }

                    if (lista.isEmpty()) {
                        Toast.makeText(getContext(), "Nenhum colaborador encontrado.", Toast.LENGTH_SHORT).show();
                    }

                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Erro ao carregar: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
