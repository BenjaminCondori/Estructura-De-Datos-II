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

import com.uagrm.grafos.Actividades.DigrafosActivity;
import com.uagrm.grafos.Actividades.DigrafosPesadosActivity;
import com.uagrm.grafos.Actividades.G1Activity;
import com.uagrm.grafos.Actividades.GrafosActivity;
import com.uagrm.grafos.Actividades.GrafosPesadosActivity;
import com.uagrm.grafos.Clases.Portada;
import com.uagrm.grafos.R;

import java.util.List;

public class AdaptadorGrafo extends RecyclerView.Adapter<AdaptadorGrafo.ViewHolder> {

    List<Portada> listaDeGrafos;
    Context context;

    public AdaptadorGrafo(List<Portada> listaDeTitulos, Context context) {
        this.listaDeGrafos = listaDeTitulos;
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
    public void onBindViewHolder(@NonNull AdaptadorGrafo.ViewHolder holder, int position) {
        holder.txtTitulo.setText(listaDeGrafos.get(position).getTitulo());
        holder.imgFoto.setImageResource(listaDeGrafos.get(position).getImagen());

        holder.cardImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, G1Activity.class);
                intent.putExtra("Titulo", listaDeGrafos.get(position).getTitulo());
                intent.putExtra("Imagen", listaDeGrafos.get(position).getImagen());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaDeGrafos.size();
    }

}
