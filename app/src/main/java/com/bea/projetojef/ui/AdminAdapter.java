package com.bea.projetojef.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bea.projetojef.Administrador;
import com.bea.projetojef.AdministradorHelo;
import com.bea.projetojef.R;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class AdminAdapter extends RecyclerView.Adapter<AdminAdapter.ViewHolder> {
    private final List<Administrador> lista;
    private Database db = new Database();

    private FirebaseFirestore database = FirebaseFirestore.getInstance();



    public AdminAdapter(List<Administrador> lista) {
        this.lista = lista;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nome, numCracha, inicio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.nome);
            numCracha = itemView.findViewById(R.id.cracha);
            inicio = itemView.findViewById(R.id.inicio3);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.busca_remover, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Administrador adm = lista.get(position);

        holder.nome.setText(adm.getNome());
        holder.numCracha.setText(String.valueOf(adm.getCracha()));
        holder.inicio.setText(adm.getInicio());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                db.remover(v.getContext(), lista.get(holder.getAdapterPosition()));
                return false;
            }
        });

    }


    @Override
    public int getItemCount() {
        return lista.size();
    }
}