package com.uagrm.grafos.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaNoExiste;
import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;
import com.uagrm.grafos.Grafos.Pesados.Dijkstra;
import com.uagrm.grafos.Grafos.Pesados.DijkstraATodos;
import com.uagrm.grafos.Grafos.Pesados.Floyd;
import com.uagrm.grafos.Grafos.Pesados.GrafoPesado;
import com.uagrm.grafos.Grafos.Pesados.Kruskal;
import com.uagrm.grafos.Grafos.Pesados.Prim;
import com.uagrm.grafos.R;

public class GP1Activity extends AppCompatActivity {

    private Spinner spinner;
    private Spinner spinnerOrigenes;
    private Spinner spinnerDestinos;
    private TextView titulo;
    private TextView contenido;
    private TextView title_result;
    private TextView resultado;
    private ImageView imagen;

    private int image;
    private GrafoPesado grafo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gp1);

        titulo = (TextView) findViewById(R.id.textView);
        contenido = (TextView) findViewById(R.id.textView2);
        resultado = (TextView) findViewById(R.id.resultado);
        title_result = (TextView) findViewById(R.id.textView_resultado);
        imagen = (ImageView) findViewById(R.id.imageView);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerOrigenes = (Spinner) findViewById(R.id.spinner_origenes);
        spinnerDestinos = (Spinner) findViewById(R.id.spinner_destinos);

        String[] opciones = {
                "Dijkstra", "Dijkstra a Todos", "Prim", "Peso",
                "Cantidad de Vertices", "Cantidad de Aristas", "Eliminar Vertice", "Eliminar Arista"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, opciones);
        spinner.setAdapter(adapter);

        String title = "";
        image = 0;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            title = extras.getString("Titulo");
            image = extras.getInt("Imagen");
        }

        titulo.setText(title);
        imagen.setImageResource(image);

        try {
            crearGrafo(image);
            contenido.setText(grafo.toString());
            cargarVerticesEnSpinners();
        } catch (NroVerticesInvalidoExcepcion ex) {
            System.out.println(ex.toString());;
        } catch (ExcepcionAristaYaExiste ex) {
            System.out.println(ex.toString());;
        }

    }

    public void ejecutar(View view) throws ExcepcionAristaNoExiste, ExcepcionAristaYaExiste, NroVerticesInvalidoExcepcion {

        String seleccion = spinner.getSelectedItem().toString();

        if (seleccion.equals("Dijkstra")) {

            Dijkstra dijkstra = new Dijkstra(grafo);
            int posOrigen = Integer.parseInt(spinnerOrigenes.getSelectedItem().toString());
            int posDestino = Integer.parseInt(spinnerDestinos.getSelectedItem().toString());
            dijkstra.caminoMasCorto(posOrigen, posDestino);
            title_result.setText("Resultado:");
            resultado.setText(dijkstra.toString(posDestino));

        } else if (seleccion.equals("Dijkstra a Todos")) {

            DijkstraATodos dijkstra = new DijkstraATodos(grafo);
            int posOrigen = Integer.parseInt(spinnerOrigenes.getSelectedItem().toString());
            dijkstra.caminosMasCortos(posOrigen);
            title_result.setText("Resultado:");
            resultado.setText(dijkstra.toString(posOrigen));

        } else if (seleccion.equals("Peso")) {

            int posOrigen = Integer.parseInt(spinnerOrigenes.getSelectedItem().toString());
            int posDestino = Integer.parseInt(spinnerDestinos.getSelectedItem().toString());

            if (grafo.existeAdyacencia(posOrigen, posDestino)) {
                int peso = (int)grafo.peso(posOrigen, posDestino);
                title_result.setText("Resultado:");
                resultado.setText("El peso entre ambos vertices es " + peso);
            } else {
                Toast.makeText(this, "No existe la arista", Toast.LENGTH_LONG).show();
            }

        } else if (seleccion.equals("Cantidad de Vertices")) {

            title_result.setText("Resultado:");
            resultado.setText("El grafo tiene " + grafo.cantidadDeVertices() + " vertices.");

        } else if (seleccion.equals("Cantidad de Aristas")) {

            title_result.setText("Resultado:");
            resultado.setText("El grafo tiene " + grafo.cantidadDeAristas() + " aristas.");

        } else if (seleccion.equals("Eliminar Vertice")) {

            int posDeOrigen = Integer.parseInt(spinnerOrigenes.getSelectedItem().toString());
            grafo.eliminarVertice(posDeOrigen);
            contenido.setText(grafo.toString());
            Toast.makeText(this, "Se eliminó el vertice", Toast.LENGTH_LONG).show();
            cargarVerticesEnSpinners();

        } else if (seleccion.equals("Eliminar Arista")) {

            int posDeOrigen = Integer.parseInt(spinnerOrigenes.getSelectedItem().toString());
            int posDeDestino = Integer.parseInt(spinnerDestinos.getSelectedItem().toString());

            if (grafo.existeAdyacencia(posDeOrigen, posDeDestino)) {
                grafo.eliminarArista(posDeOrigen, posDeDestino);
                contenido.setText(grafo.toString());
                Toast.makeText(this, "Se eliminó la arista", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "No existe la arista", Toast.LENGTH_LONG).show();
            }

        } else if (seleccion.equals("Prim")) {

            Prim p = new Prim(grafo);
            GrafoPesado aux = p.prim();
            contenido.setText(aux.toString());

        } 

    }

    private void crearGrafo(int image) throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste {

        if (image == R.drawable.grafo_pesado_1) {
            grafo = new GrafoPesado(8);

            grafo.insertarArista(0, 1, 2);
            grafo.insertarArista(0, 2, 10);
            grafo.insertarArista(0, 5, 2);
            grafo.insertarArista(0, 6, 12);
            grafo.insertarArista(0, 7, 6);
            grafo.insertarArista(1, 5, 7);
            grafo.insertarArista(2, 4, 13);
            grafo.insertarArista(2, 6, 3);
            grafo.insertarArista(2, 7, 9);
            grafo.insertarArista(3, 4, 5);
            grafo.insertarArista(3, 7, 3);
            grafo.insertarArista(4, 7, 8);
            grafo.insertarArista(5, 6, 11);

        } else if (image == R.drawable.grafo_pesado_2) {
            grafo = new GrafoPesado(9);

            grafo.insertarArista(0, 4,3);
            grafo.insertarArista(0, 7,5);
            grafo.insertarArista(1, 3,4);
            grafo.insertarArista(1, 6,9);
            grafo.insertarArista(1, 8,6);
            grafo.insertarArista(2, 5,20);
            grafo.insertarArista(2, 7,3);
            grafo.insertarArista(2, 8,2);
            grafo.insertarArista(3, 5,15);
            grafo.insertarArista(3, 6,25);
            grafo.insertarArista(3, 8,8);
            grafo.insertarArista(4, 7,11);
            grafo.insertarArista(4, 8,10);
            grafo.insertarArista(5, 7,1);

        } else if (image == R.drawable.grafo_pesado_3) {
            grafo = new GrafoPesado(8);

            grafo.insertarArista(0,2,3);
            grafo.insertarArista(0,4,9);
            grafo.insertarArista(0,5,2);
            grafo.insertarArista(0,6,10);
            grafo.insertarArista(0,7,5);
            grafo.insertarArista(1,6,12);
            grafo.insertarArista(1,7,4);
            grafo.insertarArista(2,4,1);
            grafo.insertarArista(2,7,6);
            grafo.insertarArista(3,5,8);
            grafo.insertarArista(3,6,5);
            grafo.insertarArista(6,7,7);

        } else if (image == R.drawable.grafo_pesado_4) {
            grafo = new GrafoPesado(10);

            grafo.insertarArista(0, 1,4);
            grafo.insertarArista(0, 5,7);
            grafo.insertarArista(1, 4,8);
            grafo.insertarArista(1, 5,5);
            grafo.insertarArista(2, 3,12);
            grafo.insertarArista(2, 7,6);
            grafo.insertarArista(2, 8,2);
            grafo.insertarArista(3, 6,4);
            grafo.insertarArista(3, 7,4);
            grafo.insertarArista(3, 8,1);
            grafo.insertarArista(4, 7,3);
            grafo.insertarArista(5, 9,15);
            grafo.insertarArista(6, 9,9);
            grafo.insertarArista(7, 9,2);

        }

    }

    private void cargarVerticesEnSpinners() {
        String[] vertices = new String[grafo.cantidadDeVertices()];

        for (int i = 0; i < grafo.cantidadDeVertices(); i++) {
            vertices[i] = String.valueOf(i);
        }

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item, vertices);
        spinnerOrigenes.setAdapter(adapter1);
        spinnerDestinos.setAdapter(adapter1);
    }


}