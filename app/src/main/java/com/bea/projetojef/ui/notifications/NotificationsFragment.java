package com.bea.projetojef.ui.notifications;

import android.content.Intent;
import android.os.Bundle;
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

import com.bea.projetojef.R;
import com.bea.projetojef.databinding.FragmentNotificationsBinding;
import com.bea.projetojef.ui.AdminAdapter;
import com.bea.projetojef.ui.RemoverRegistro;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.ktx.Firebase;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        EditText senha = root.findViewById(R.id.senhaAdmin);
//        AdminAdapter adminAdapter = new AdminAdapter();
//        boolean validaSenha = adminAdapter.validarCachar(senha.toString());
//        if (validaSenha){
//            Toast.makeText(getContext(), "Senha válida, acesso permitido", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(getActivity(), RemoverRegistro.class);
//            startActivity(intent);
//        }else{
//            Toast.makeText(getContext(), "Senha inválida, acesso negado", Toast.LENGTH_SHORT).show();
//        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}