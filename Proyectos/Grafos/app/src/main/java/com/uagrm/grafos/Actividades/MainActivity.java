package com.uagrm.grafos.Actividades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.uagrm.grafos.Adapters.Adaptador;
import com.uagrm.grafos.Clases.Portada;
import com.uagrm.grafos.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerTitulos;
    Adaptador adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inicizalizarElementos();
    }

    private void inicizalizarElementos() {
        recyclerTitulos = findViewById(R.id.recycler);
        recyclerTitulos.setLayoutManager(new LinearLayoutManager(this));

        List<Portada>listaDeTitulos = new ArrayList<>();

        listaDeTitulos.add(new Portada("Grafos No Dirigidos", R.drawable.grafo));
        listaDeTitulos.add(new Portada("Grafos Dirigidos", R.drawable.digrafo));
        listaDeTitulos.add(new Portada("Grafos No Dirigidos Pesados", R.drawable.grafo_pesado));
        listaDeTitulos.add(new Portada("Grafos Dirigidos Pesados", R.drawable.digrafo_pesado));

        adaptador = new Adaptador(listaDeTitulos, this);
        recyclerTitulos.setAdapter(adaptador);

    }

}