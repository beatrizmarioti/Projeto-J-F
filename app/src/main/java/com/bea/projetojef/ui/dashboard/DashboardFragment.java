package com.bea.projetojef.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bea.projetojef.databinding.FragmentDashboardBinding;
import com.bea.projetojef.ui.Database;
import com.google.android.material.textfield.TextInputLayout;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Referências aos inputs e botão
        TextInputLayout nomeInput = binding.nomeIn;
        TextInputLayout numeroInput = binding.numIn;
        Button btnSalvar = binding.btnSalvar;

        // Instanciar sua classe Database
        Database database = new Database();


        btnSalvar.setOnClickListener(v -> {
            database.salvarCracha(requireContext(), nomeInput, numeroInput);
        });

        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }
//
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}