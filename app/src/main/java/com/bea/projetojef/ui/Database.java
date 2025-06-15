package com.bea.projetojef.ui;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bea.projetojef.Administrador;
import com.bea.projetojef.Atendimento;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Database {
    public Database() {

    }
    public void salvarCracha(Context context, TextInputLayout nomeInput, TextInputLayout numeroInput) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        String cracha = numeroInput.getEditText().getText().toString().trim();
        String nome = nomeInput.getEditText().getText().toString().trim();

        if (nome.isEmpty() || cracha.isEmpty()) {
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
                Atendimento registroCracha = new Atendimento();
                registroCracha.setId(idParaSalvar);
                registroCracha.setNome(nome);
                registroCracha.setCracha(cracha);
                registroCracha.setInicio("");
                registroCracha.setTermino("");

                // Salvar o crachá com ID novo
                db.collection("colaborador").document(String.valueOf(idParaSalvar))
                        .set(registroCracha)
                        .addOnSuccessListener(aVoid -> {
                            // Atualizar contador de IDs
                            db.collection("colaborador").document("id_cracha")
                                    .update("id", idParaSalvar);

                            Toast.makeText(context, "Crachá registrado com sucesso!", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                                Toast.makeText(context, "Não foi possível registrar crachá", Toast.LENGTH_SHORT).show();
                                System.out.println(e.getMessage());
                            }
                        );

            } else {
                Toast.makeText(context, "Erro ao gerar ID: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void remover(Context c, Administrador argAdmin) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        if (argAdmin.getDocumentId() == null) {
            Toast.makeText(c, "Não foi possível excluir. documentId nulo.", Toast.LENGTH_SHORT).show();
            return;
        }
        db.collection("colaborador")
                .document(argAdmin.getDocumentId()) // Agora removendo pelo documentId
                .delete()
                .addOnSuccessListener(unused -> {
                    Toast.makeText(c, "Removido com sucesso", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(c, "Erro ao remover: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }


    public void validarCachar(String senhaDigitada, SenhaCallback callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("admin")
                .document("senha")
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String senha = documentSnapshot.getString("senha");
                        if (senha != null && senha.equals(senhaDigitada)) {
                            callback.onResultado(true);
                        } else {
                            callback.onResultado(false);
                        }
                    } else {
                        callback.onResultado(false);
                    }
                })
                .addOnFailureListener(e -> {
                    callback.onResultado(false);
                });
    }

}
