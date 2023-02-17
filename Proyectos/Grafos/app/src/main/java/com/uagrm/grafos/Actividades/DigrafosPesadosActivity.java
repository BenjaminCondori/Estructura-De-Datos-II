package com.uagrm.grafos.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.uagrm.grafos.Adapters.AdaptadorDigrafoPesado;
import com.uagrm.grafos.Clases.Portada;
import com.uagrm.grafos.R;

import java.util.ArrayList;
import java.util.List;

public class DigrafosPesadosActivity extends AppCompatActivity {

    RecyclerView recyclerTitulos;
    AdaptadorDigrafoPesado adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digrafos_pesados);
        inicizalizarElementos();
    }

    private void inicizalizarElementos() {
        recyclerTitulos = findViewById(R.id.recycler);
        recyclerTitulos.setLayoutManager(new LinearLayoutManager(this));

        List<Portada> listaDeDigrafosPesados = new ArrayList<>();

        listaDeDigrafosPesados.add(new Portada("Grafo 1", R.drawable.digrafo_pesado_1));
        listaDeDigrafosPesados.add(new Portada("Grafo 2", R.drawable.digrafo_pesado_2));
        listaDeDigrafosPesados.add(new Portada("Grafo 3", R.drawable.digrafo_pesado_3));
        listaDeDigrafosPesados.add(new Portada("Grafo 4", R.drawable.digrafo_pesado_4));

        adaptador = new AdaptadorDigrafoPesado(listaDeDigrafosPesados, this);
        recyclerTitulos.setAdapter(adaptador);

    }

}