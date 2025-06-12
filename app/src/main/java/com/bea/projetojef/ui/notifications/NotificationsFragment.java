package com.bea.projetojef.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.bea.projetojef.R;
import com.bea.projetojef.databinding.FragmentNotificationsBinding;
import com.bea.projetojef.ui.Database;
import com.bea.projetojef.ui.SenhaCallback;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private final List<com.bea.projetojef.Administrador> lista = new ArrayList<>();
    private FragmentNotificationsBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button login = root.findViewById(R.id.loginADM_button);
        TextInputEditText senha = root.findViewById(R.id.senhaAdmin);

        login.setOnClickListener(v -> {
            Database db = new Database();
            db.validarCachar(senha.getText().toString(), new SenhaCallback() {
                @Override
                public void onResultado(boolean valido) {
                    if (valido) {
                        Toast.makeText(getContext(), "Senha válida, acesso permitido", Toast.LENGTH_SHORT).show();

                        // TROCA O FRAGMENTO ATUAL PELO RemoverRegistro
//                        FragmentTransaction transaction = requireActivity()
//                                .getSupportFragmentManager()
//                                .beginTransaction();
//                        transaction.replace(R.id.nav_host_fragment_activity_main, new RemoverRegistro());
//                        transaction.addToBackStack(null); // permite voltar com botão de voltar
//                        transaction.commit();
                        NavController navController = NavHostFragment.findNavController(NotificationsFragment.this);
                        navController.navigate(R.id.action_navigation_notifications_to_navigation_remover);


                    } else {
                        Toast.makeText(getContext(), "Senha inválida, acesso negado", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
