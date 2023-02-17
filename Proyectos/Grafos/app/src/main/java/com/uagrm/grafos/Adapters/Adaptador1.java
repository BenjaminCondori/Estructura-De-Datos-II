package com.uagrm.grafos.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.uagrm.grafos.Clases.Portada;
import com.uagrm.grafos.R;

import java.util.List;

public class Adaptador1 extends RecyclerView.Adapter<Adaptador1.ViewHolder> {

    private List<Portada> listaDeTitulos;
    private Context context;

    public Adaptador1(List<Portada> listaDeTitulos, Context context) {
        this.listaDeTitulos = listaDeTitulos;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgFoto;
        private TextView txtTitulo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Enlazar los elementos de la tarjeta de imagen
            imgFoto = itemView.findViewById(R.id.imagen);
            txtTitulo = itemView.findViewById(R.id.titulo);

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.imagen_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptador1.ViewHolder holder, int position) {
        holder.txtTitulo.setText(listaDeTitulos.get(position).getTitulo());
        Glide.with(context)
                .load(listaDeTitulos.get(position).getImagen())
                .centerCrop()
                .placeholder(R.drawable.ic_image_24)
                .into(holder.imgFoto);
    }

    @Override
    public int getItemCount() {
        return listaDeTitulos.size();
    }

}
