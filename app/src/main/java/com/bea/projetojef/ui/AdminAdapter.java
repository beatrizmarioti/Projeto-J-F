package com.bea.projetojef.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class AdminAdapter {
    private String senha;
    private Database db = new Database();

    public AdminAdapter(String senha) {
        this.senha = senha;
    }
    public AdminAdapter(){

    }
    @NonNull
    @Override
    public AdminAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ///vincula a tela com o cardView
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notas,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminAdapter.ViewHolder holder, int position) {
        holder.titulo.setText(listaNotas.get(position).getTitulo());
        holder.descricao.setText(listaNotas.get(position).getDescricao());
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                //remover nota
                fireBase.remover(listaNotas.get(position),v.getContext());
//                listaNotas.remove(position);
//                notifyItemRemoved(position);
//                Toast.makeText(v.getContext(), "Nota deletada com sucesso", Toast.LENGTH_SHORT).show();
                return false;
            }
        });

    }

//    @Override
//    public int getItemCount() {
//        return listaNotas.size();
//    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        //indica quais os componetes do cardView que vamos usar
        TextView titulo, descricao;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titulo = itemView.findViewById(R.id.Titulo);
            descricao = itemView.findViewById(R.id.descricao);

        }
    }
}
