package com.bea.projetojef.ui;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.bea.projetojef.Cracha;

public class Database {
    public Database() {

    }
    public void salvarCracha(Context context, TextInputLayout nomeInput, TextInputLayout numeroInput) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String nome = nomeInput.getEditText().getText().toString().trim();
        String numeroStr = numeroInput.getEditText().getText().toString().trim();

        if (nome.isEmpty() || numeroStr.isEmpty()) {
            Toast.makeText(context, "Bixo leso, coloca todos os campos", Toast.LENGTH_SHORT).show();
            return;
        }

        db.collection("colaborador").document("id_cracha").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                int novoId = 1;

                DocumentSnapshot doc = task.getResult();
                if (doc.exists()) {
                    Long ultimoId = doc.getLong("id");
                    if (ultimoId != null) {
                        novoId = ultimoId.intValue() + 1;
                    }
                }
                final int idParaSalvar = novoId;

                // Criar novo crachá
                Cracha cracha = new Cracha();
                cracha.setId(idParaSalvar);
                cracha.setNome(nome);
                cracha.setInicio("");
                cracha.setTermino("");

                // Salvar o crachá com ID novo
                db.collection("colaborador").document(String.valueOf(idParaSalvar))
                        .set(cracha)
                        .addOnSuccessListener(aVoid -> {
                            // Atualizar contador de IDs
                            db.collection("colaborador").document("id_cracha")
                                    .update("id", idParaSalvar);

                            Toast.makeText(context, "Salvo Desgraçaa!!!!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(context, "Nao funfou: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                        );

            } else {
                Toast.makeText(context, "Erro ao gerar ID: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
