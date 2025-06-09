package com.bea.projetojef;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AtendimentoAdapter extends RecyclerView.Adapter<AtendimentoAdapter.ViewHolder> {

    private final List<Atendimento> lista;
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Formatação de data/hora para exibir no layout
    private static final SimpleDateFormat FORMATADOR =
            new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

    public AtendimentoAdapter(List<Atendimento> lista) {
        this.lista = lista;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, inicio, termino, labelTermino;
        Button botaoTerminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome);
            inicio = itemView.findViewById(R.id.cracha);
            termino = itemView.findViewById(R.id.termino);
            labelTermino = itemView.findViewById(R.id.label_termino);
            botaoTerminar = itemView.findViewById(R.id.botao_terminar);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.busca, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Atendimento atendimento = lista.get(position);

        // Preenche os campos do card com os dados
        holder.nome.setText(atendimento.getNome());
        holder.inicio.setText(atendimento.getInicio());

        // Verifica se já há término registrado
        boolean terminou =
                atendimento.getTermino() != null && !atendimento.getTermino().isEmpty();

        if (terminou) {
            holder.termino.setVisibility(View.VISIBLE);
            holder.labelTermino.setVisibility(View.VISIBLE);
            holder.botaoTerminar.setVisibility(View.GONE);
        } else {
            holder.termino.setVisibility(View.GONE);
            holder.labelTermino.setVisibility(View.GONE);
            holder.botaoTerminar.setVisibility(View.VISIBLE);
        }


        if (terminou) {
            holder.termino.setText(atendimento.getTermino());
        }
        holder.botaoTerminar.setOnClickListener(v -> {
            if (atendimento.getId() <= 0) {
                Toast.makeText(v.getContext(),
                        "ID do colaborador inválido.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Pega a hora atual e salva no objeto
            String dataHora = FORMATADOR.format(new Date());
            atendimento.setTermino(dataHora);

            // Atualiza o campo "termino" no Firestore
            db.collection("colaborador")
                    .document(String.valueOf(atendimento.getId()))
                    .update("termino", dataHora)
                    .addOnSuccessListener(unused -> {
                        Toast.makeText(v.getContext(),
                                "Término registrado", Toast.LENGTH_SHORT).show();

                        int pos = holder.getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION) {
                            notifyItemChanged(pos);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(v.getContext(),
                                "Erro ao atualizar", Toast.LENGTH_SHORT).show();
                    });
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }
}
