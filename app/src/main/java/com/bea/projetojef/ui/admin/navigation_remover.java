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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

        //Conexão com o banco
        db = FirebaseFirestore.getInstance();

        adapter = new AdminAdapter(lista);
        //organização dos itens na tela
        binding.recyclerView2.setLayoutManager(new LinearLayoutManager(getContext()));

//        Conecta a lista na tela com o nosso "adaptador". Agora o adaptador sabe onde ele deve mostrar os itens.
        binding.recyclerView2.setAdapter(adapter);

        carregarMesPassado();
    }

    private void carregarMesPassado() {
        db.collection("colaborador")
                .get()
                .addOnSuccessListener(snapshots -> {
                    lista.clear();

                    // configura datas para o mes passado
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MONTH, -1);
                    calendar.set(Calendar.DAY_OF_MONTH, 1);
                    Date inicioMesPassado = calendar.getTime();

                    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                    Date fimMesPassado = calendar.getTime();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

                    for (QueryDocumentSnapshot doc : snapshots) {
                        Administrador a = doc.toObject(Administrador.class);
                        a.setDocumentId(doc.getId());
                        Long id = doc.getLong("id");

                        if (id != null) {
                            a.setId(id);
                        }

                        String inicio = a.getInicio();

                        if (inicio == null || inicio.isEmpty()) {
                            continue;
                        }

                        try {
                            Date dataColaborador = sdf.parse(inicio);

                            if (dataColaborador.compareTo(inicioMesPassado) >= 0 &&
                                    dataColaborador.compareTo(fimMesPassado) <= 0) {
                                lista.add(a);
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                    if (lista.isEmpty()) {
                        Toast.makeText(getContext(), "Nenhum colaborador encontrado do mes passado.", Toast.LENGTH_SHORT).show();
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
