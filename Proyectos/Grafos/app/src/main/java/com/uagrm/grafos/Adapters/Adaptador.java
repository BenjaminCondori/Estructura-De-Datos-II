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
import com.uagrm.grafos.Actividades.GrafosActivity;
import com.uagrm.grafos.Actividades.GrafosPesadosActivity;
import com.uagrm.grafos.Clases.Portada;
import com.uagrm.grafos.R;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {

    List<Portada> listaDeTitulos;
    Context context;

    public Adaptador(List<Portada> listaDeTitulos, Context context) {
        this.listaDeTitulos = listaDeTitulos;
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
    public void onBindViewHolder(@NonNull Adaptador.ViewHolder holder, int position) {
        holder.txtTitulo.setText(listaDeTitulos.get(position).getTitulo());
        holder.imgFoto.setImageResource(listaDeTitulos.get(position).getImagen());

        holder.cardImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position) {
                    case 0:
                        context.startActivity(new Intent(context, GrafosActivity.class));
                        break;
                    case 1:
                        context.startActivity(new Intent(context, DigrafosActivity.class));
                        break;
                    case 2:
                        context.startActivity(new Intent(context, GrafosPesadosActivity.class));
                        break;
                    case 3:
                        context.startActivity(new Intent(context, DigrafosPesadosActivity.class));
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaDeTitulos.size();
    }

}
