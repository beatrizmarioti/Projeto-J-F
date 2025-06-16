package com.bea.projetojef.ui.Busca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bea.projetojef.Atendimento;
import com.bea.projetojef.ui.AtendimentoAdapter;
import com.bea.projetojef.databinding.FragmentBuscaBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class BuscaFragment extends Fragment {

    private FragmentBuscaBinding binding;
    private FirebaseFirestore db;
    private final List<Atendimento> lista = new ArrayList<>();
    private AtendimentoAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentBuscaBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();
        adapter = new AtendimentoAdapter(lista);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        carregarTudo();

        binding.buscar.setOnClickListener(v -> {
            if (binding.textInputLayout.getEditText() == null) {
                Toast.makeText(getContext(), "crachá inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            String cracha = binding.textInputLayout.getEditText().getText().toString().trim();
            if (cracha.isEmpty()) {
                Toast.makeText(getContext(), "Digite o crachá", Toast.LENGTH_SHORT).show();
                return;
            }

            db.collection("colaborador")
                    .whereEqualTo("cracha", cracha)
                    .get()
                    .addOnSuccessListener(snapshots -> {
                        lista.clear();

                        for (QueryDocumentSnapshot doc : snapshots) {
                            Atendimento a = doc.toObject(Atendimento.class);
                            Long id = doc.getLong("id");
                            if (id != null) {
                                a.setId(id);
                            }

                            String dataHoraAtual = getDataHoraAtual();
                            a.setInicio(dataHoraAtual);

                            db.collection("colaborador")
                                    .document(String.valueOf(a.getId()))
                                    .update("inicio", dataHoraAtual);

                            lista.add(a);
                        }

                        if (lista.isEmpty()) {
                            Toast.makeText(getContext(), "Colaborador não encontrado", Toast.LENGTH_SHORT).show();
                        }

                        adapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(getContext(), "Erro ao buscar: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });
    }

    private void carregarTudo() {
        db.collection("colaborador")
                .get()
                .addOnSuccessListener(snapshots -> {
                    lista.clear();
                    for (QueryDocumentSnapshot doc : snapshots) {
                        Atendimento a = doc.toObject(Atendimento.class);
                        Long id = doc.getLong("id");
                        if (id != null) {
                            a.setId(id);
                        }
                        lista.add(a);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Erro ao carregar " , Toast.LENGTH_SHORT).show()
                );
    }

    private String getDataHoraAtual() {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
