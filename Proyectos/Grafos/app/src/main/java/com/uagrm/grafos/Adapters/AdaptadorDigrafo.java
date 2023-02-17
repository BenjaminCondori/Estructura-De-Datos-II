package com.uagrm.grafos.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.uagrm.grafos.Actividades.D1Activity;
import com.uagrm.grafos.Clases.Portada;
import com.uagrm.grafos.R;

import java.util.List;

public class AdaptadorDigrafo extends RecyclerView.Adapter<AdaptadorDigrafo.ViewHolder> {

    List<Portada> listaDeDigrafos;
    Context context;

    public AdaptadorDigrafo(List<Portada> listaDeTitulos, Context context) {
        this.listaDeDigrafos = listaDeTitulos;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cardImagen;
        private ImageView imgFoto;
        private TextView txtTitulo;
        private Context context;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            // Enlazar los elementos de la tarjeta de imagen
            cardImagen = itemView.findViewById(R.id.card_view);
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
    public void onBindViewHolder(@NonNull AdaptadorDigrafo.ViewHolder holder, int position) {
        holder.txtTitulo.setText(listaDeDigrafos.get(position).getTitulo());
        holder.imgFoto.setImageResource(listaDeDigrafos.get(position).getImagen());

        holder.cardImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, D1Activity.class);
                intent.putExtra("Titulo", listaDeDigrafos.get(position).getTitulo());
                intent.putExtra("Imagen", listaDeDigrafos.get(position).getImagen());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaDeDigrafos.size();
    }

}
