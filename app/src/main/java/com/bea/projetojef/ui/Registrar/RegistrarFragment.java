package com.bea.projetojef.ui.Registrar;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bea.projetojef.databinding.FragmentRegistrarBinding;
import com.bea.projetojef.ui.Database;
import com.google.android.material.textfield.TextInputLayout;

public class RegistrarFragment extends Fragment {

    private FragmentRegistrarBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        RegistrarViewModel dashboardViewModel =
                new ViewModelProvider(this).get(RegistrarViewModel.class);

        binding = FragmentRegistrarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        // Referências aos inputs e botão
        TextInputLayout nomeInput = binding.senhaAdmin;
        TextInputLayout numeroInput = binding.numIn;
        Button btnSalvar = binding.btnSalvar;

        // Instanciar classe Database
        Database database = new Database();


        btnSalvar.setOnClickListener(v -> {
            database.salvarCracha(requireContext(), nomeInput, numeroInput);

            nomeInput.getEditText().setText("");
            numeroInput.getEditText().setText("");
        });



        return root;
    }
//
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}