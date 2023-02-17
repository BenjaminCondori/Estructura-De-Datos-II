package com.uagrm.grafos.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.uagrm.grafos.Adapters.AdaptadorGrafoPesado;
import com.uagrm.grafos.Clases.Portada;
import com.uagrm.grafos.R;

import java.util.ArrayList;
import java.util.List;

public class GrafosPesadosActivity extends AppCompatActivity {

    RecyclerView recyclerTitulos;
    AdaptadorGrafoPesado adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafos_pesados);
        inicizalizarElementos();
    }

    private void inicizalizarElementos() {
        recyclerTitulos = findViewById(R.id.recycler);
        recyclerTitulos.setLayoutManager(new LinearLayoutManager(this));

        List<Portada> listaDeGrafosPesados = new ArrayList<>();

        listaDeGrafosPesados.add(new Portada("Grafo 1", R.drawable.grafo_pesado_1));
        listaDeGrafosPesados.add(new Portada("Grafo 2", R.drawable.grafo_pesado_2));
        listaDeGrafosPesados.add(new Portada("Grafo 3", R.drawable.grafo_pesado_3));
        listaDeGrafosPesados.add(new Portada("Grafo 4", R.drawable.grafo_pesado_4));

        adaptador = new AdaptadorGrafoPesado(listaDeGrafosPesados, this);
        recyclerTitulos.setAdapter(adaptador);

    }

}