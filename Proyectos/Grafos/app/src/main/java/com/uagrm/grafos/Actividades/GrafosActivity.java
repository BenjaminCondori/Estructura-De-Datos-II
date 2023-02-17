package com.uagrm.grafos.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.uagrm.grafos.Adapters.AdaptadorGrafo;
import com.uagrm.grafos.Clases.Portada;
import com.uagrm.grafos.R;

import java.util.ArrayList;
import java.util.List;

public class GrafosActivity extends AppCompatActivity {

    RecyclerView recyclerTitulos;
    AdaptadorGrafo adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafos);
        inicizalizarElementos();
    }

    private void inicizalizarElementos() {
        recyclerTitulos = findViewById(R.id.recycler);
        recyclerTitulos.setLayoutManager(new LinearLayoutManager(this));

        List<Portada> listaDeGrafos = new ArrayList<>();

        listaDeGrafos.add(new Portada("Grafo 1", R.drawable.grafo_1));
        listaDeGrafos.add(new Portada("Grafo 2", R.drawable.grafo_2));
        listaDeGrafos.add(new Portada("Grafo 3", R.drawable.grafo_3));
        listaDeGrafos.add(new Portada("Grafo 4", R.drawable.grafo_4));

        adaptador = new AdaptadorGrafo(listaDeGrafos, this);
        recyclerTitulos.setAdapter(adaptador);

    }

}