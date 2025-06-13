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
import com.bea.projetojef.databinding.FragmentNavigationRemoverBinding;
import com.bea.projetojef.ui.AdminAdapter;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class navigation_remover extends Fragment {

    private FragmentNavigationRemoverBinding binding;
    private FirebaseFirestore db;
    private final List<Administrador> lista = new ArrayList<>();
    private AdminAdapter adapter;

    private String inicioMesPassadoStr;
    private String fimMesPassadoStr;

    public navigation_remover() {
    }

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

        calcularIntervaloMesPassado();
        carregarTudo();

        binding.buscar.setOnClickListener(v -> {
            if (binding.textInputLayout.getEditText() == null) {
                Toast.makeText(getContext(), "Crachá inválido", Toast.LENGTH_SHORT).show();
                return;
            }

            String cracha = binding.textInputLayout.getEditText().getText().toString().trim();
            if (cracha.isEmpty()) {
                Toast.makeText(getContext(), "Digite o crachá", Toast.LENGTH_SHORT).show();
                return;
            }

            db.collection("colaborador")
                    .whereEqualTo("cracha", cracha)
                    .whereGreaterThanOrEqualTo("dataRegistro", inicioMesPassadoStr)
                    .whereLessThanOrEqualTo("dataRegistro", fimMesPassadoStr)
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
                            Toast.makeText(getContext(), "Colaborador não encontrado no mês passado", Toast.LENGTH_SHORT).show();
                        }

                        adapter.notifyDataSetChanged();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(getContext(), "Erro ao buscar: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });
    }

    private void calcularIntervaloMesPassado() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calInicio = Calendar.getInstance();
        calInicio.add(Calendar.MONTH, -1);
        calInicio.set(Calendar.DAY_OF_MONTH, 1);
        inicioMesPassadoStr = sdf.format(calInicio.getTime());

        Calendar calFim = Calendar.getInstance();
        calFim.add(Calendar.MONTH, -1);
        calFim.set(Calendar.DAY_OF_MONTH, calFim.getActualMaximum(Calendar.DAY_OF_MONTH));
        fimMesPassadoStr = sdf.format(calFim.getTime());
    }

    private void carregarTudo() {
        db.collection("colaborador")
                .whereGreaterThanOrEqualTo("dataRegistro", inicioMesPassadoStr)
                .whereLessThanOrEqualTo("dataRegistro", fimMesPassadoStr)
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
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(getContext(), "Erro ao carregar", Toast.LENGTH_SHORT).show()
                );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
