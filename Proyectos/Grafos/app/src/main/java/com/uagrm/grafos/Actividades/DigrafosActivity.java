package com.uagrm.grafos.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.uagrm.grafos.Adapters.AdaptadorDigrafo;
import com.uagrm.grafos.Clases.Portada;
import com.uagrm.grafos.R;

import java.util.ArrayList;
import java.util.List;

public class DigrafosActivity extends AppCompatActivity {

    RecyclerView recyclerTitulos;
    AdaptadorDigrafo adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digrafos);
        inicizalizarElementos();
    }

    private void inicizalizarElementos() {
        recyclerTitulos = findViewById(R.id.recycler);
        recyclerTitulos.setLayoutManager(new LinearLayoutManager(this));

        List<Portada> listaDeDigrafos = new ArrayList<>();

        listaDeDigrafos.add(new Portada("Grafo 1", R.drawable.digrafo_1));
        listaDeDigrafos.add(new Portada("Grafo 2", R.drawable.digrafo_2));
        listaDeDigrafos.add(new Portada("Grafo 3", R.drawable.digrafo_3));
        listaDeDigrafos.add(new Portada("Grafo 4", R.drawable.digrafo_4));

        adaptador = new AdaptadorDigrafo(listaDeDigrafos, this);
        recyclerTitulos.setAdapter(adaptador);

    }

}