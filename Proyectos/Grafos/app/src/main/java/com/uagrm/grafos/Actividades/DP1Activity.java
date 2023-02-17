package com.uagrm.grafos.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaNoExiste;
import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;
import com.uagrm.grafos.Grafos.Pesados.DigrafoPesado;
import com.uagrm.grafos.Grafos.Pesados.Dijkstra;
import com.uagrm.grafos.Grafos.Pesados.DijkstraATodos;
import com.uagrm.grafos.Grafos.Pesados.Floyd;
import com.uagrm.grafos.Grafos.Pesados.GrafoPesado;
import com.uagrm.grafos.Grafos.Pesados.Kruskal;
import com.uagrm.grafos.Grafos.Pesados.Prim;
import com.uagrm.grafos.R;

public class DP1Activity extends AppCompatActivity {

    private Spinner spinner;
    private Spinner spinnerOrigenes;
    private Spinner spinnerDestinos;
    private TextView titulo;
    private TextView contenido;
    private TextView title_result;
    private TextView resultado;
    private ImageView imagen;

    private int image;
    private DigrafoPesado grafo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dp1);

        titulo = (TextView) findViewById(R.id.textView);
        contenido = (TextView) findViewById(R.id.textView2);
        resultado = (TextView) findViewById(R.id.resultado);
        title_result = (TextView) findViewById(R.id.textView_resultado);
        imagen = (ImageView) findViewById(R.id.imageView);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerOrigenes = (Spinner) findViewById(R.id.spinner_origenes);
        spinnerDestinos = (Spinner) findViewById(R.id.spinner_destinos);

        String[] opciones = {
                "Dijkstra", "Dijkstra a Todos", "Floyd", "Peso",
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

        }  else if (seleccion.equals("Floyd")) {

            Floyd f = new Floyd(grafo);

            int posDeOrigen = Integer.parseInt(spinnerOrigenes.getSelectedItem().toString());
            int posDeDestino = Integer.parseInt(spinnerDestinos.getSelectedItem().toString());

            String camino = f.mostrarCamino(posDeOrigen, posDeDestino);
            title_result.setText("Resultado:");
            resultado.setText(camino);

        }

    }

    private void crearGrafo(int image) throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste {

        if (image == R.drawable.digrafo_pesado_1) {
            grafo = new DigrafoPesado(5);

            grafo.insertarArista(0, 1,5);
            grafo.insertarArista(0, 3,9);
            grafo.insertarArista(1, 4,83);
            grafo.insertarArista(2, 0,25);
            grafo.insertarArista(2, 3,33);
            grafo.insertarArista(3, 1,60);
            grafo.insertarArista(4, 1,10);

        } else if (image == R.drawable.digrafo_pesado_2) {
            grafo = new DigrafoPesado(6);

            grafo.insertarArista(0,1,3);
            grafo.insertarArista(0,2,10);
            grafo.insertarArista(1,3,8);
            grafo.insertarArista(2,1,1);
            grafo.insertarArista(2,4,6);
            grafo.insertarArista(3,4,4);
            grafo.insertarArista(3,5,15);
            grafo.insertarArista(4,1,20);
            grafo.insertarArista(4,5,2);

        } else if (image == R.drawable.digrafo_pesado_3) {
            grafo = new DigrafoPesado(11);

            grafo.insertarArista(0,1,5);
            grafo.insertarArista(0,2,10);
            grafo.insertarArista(0,3,3);
            grafo.insertarArista(1,5,25);
            grafo.insertarArista(2,1,2);
            grafo.insertarArista(2,3,15);
            grafo.insertarArista(3,5,30);
            grafo.insertarArista(4,1,15);
            grafo.insertarArista(4,5,4);
            grafo.insertarArista(4,7,3);
            grafo.insertarArista(4,8,15);
            grafo.insertarArista(5,6,17);
            grafo.insertarArista(6,3,12);
            grafo.insertarArista(6,8,9);
            grafo.insertarArista(6,9,8);
            grafo.insertarArista(7,8,50);
            grafo.insertarArista(7,10,6);
            grafo.insertarArista(8,9,23);
            grafo.insertarArista(8,10,8);
            grafo.insertarArista(9,10,2);

        } else if (image == R.drawable.digrafo_pesado_4) {
            grafo = new DigrafoPesado(11);

            grafo.insertarArista(0,3,13);
            grafo.insertarArista(0,4,3);
            grafo.insertarArista(1,0,1);
            grafo.insertarArista(2,0,25);
            grafo.insertarArista(2,1,2);
            grafo.insertarArista(2,5,30);
            grafo.insertarArista(4,3,12);
            grafo.insertarArista(4,6,17);
            grafo.insertarArista(4,9,8);
            grafo.insertarArista(5,1,5);
            grafo.insertarArista(5,6,4);
            grafo.insertarArista(5,8,14);
            grafo.insertarArista(6,2,11);
            grafo.insertarArista(6,7,9);
            grafo.insertarArista(7,5,15);
            grafo.insertarArista(8,7,50);
            grafo.insertarArista(8,10,6);
            grafo.insertarArista(9,7,23);
            grafo.insertarArista(10,9,33);

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