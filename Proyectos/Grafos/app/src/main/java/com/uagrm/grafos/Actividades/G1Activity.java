package com.uagrm.grafos.Actividades;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;
import com.uagrm.grafos.Grafos.NoPesados.ExisteCicloEnGrafo;
import com.uagrm.grafos.Grafos.NoPesados.Grafo;
import com.uagrm.grafos.R;

public class G1Activity extends AppCompatActivity {

    private Spinner spinner;
    private Spinner spinnerOrigenes;
    private Spinner spinnerDestinos;
    private TextView titulo;
    private TextView contenido;
    private TextView title_result;
    private TextView resultado;
    private ImageView imagen;

    private int image;
    private Grafo grafo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_g1);

        titulo = (TextView) findViewById(R.id.textView);
        contenido = (TextView) findViewById(R.id.textView2);
        resultado = (TextView) findViewById(R.id.resultado);
        title_result = (TextView) findViewById(R.id.textView_resultado);
        imagen = (ImageView) findViewById(R.id.imageView);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerOrigenes = (Spinner) findViewById(R.id.spinner_origenes);
        spinnerDestinos = (Spinner) findViewById(R.id.spinner_destinos);

        String[] opciones = {
                "Es Conexo", "Existe Ciclo", "Nro De Islas", "Cantidad de Vertices", "Cantidad de Aristas",
                "Eliminar Vertice", "Eliminar Arista",
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
            System.out.println(ex.toString());
            ;
        } catch (ExcepcionAristaYaExiste ex) {
            System.out.println(ex.toString());
            ;
        }

    }

    public void ejecutar(View view) throws ExcepcionAristaYaExiste, NroVerticesInvalidoExcepcion {

        String seleccion = spinner.getSelectedItem().toString();

        if (seleccion.equals("Eliminar Vertice")) {

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

        }
        if (seleccion.equals("Es Conexo")) {

            title_result.setText("Resultado:");
            if (grafo.esConexo()) {
                resultado.setText("El grafo si es conexo.");
            } else {
                resultado.setText("El grafo no es conexo.");
            }

        } else if (seleccion.equals("Existe Ciclo")) {

            ExisteCicloEnGrafo existeCiclo = new ExisteCicloEnGrafo(grafo);
            title_result.setText("Resultado:");
            if (existeCiclo.existeCiclo()) {
                resultado.setText("Si existe ciclo.");
            } else {
                resultado.setText("No existe ciclo.");
            }

        } else if (seleccion.equals("Nro De Islas")) {

            int nroDeIslas = grafo.nroDeIslas();
            title_result.setText("Resultado:");
            resultado.setText("El grafo tiene " + nroDeIslas + " islas.");

        } else if (seleccion.equals("Cantidad de Vertices")) {

            title_result.setText("Resultado:");
            resultado.setText("El grafo tiene " + grafo.cantidadDeVertices() + " vertices.");

        } else if (seleccion.equals("Cantidad de Aristas")) {

            title_result.setText("Resultado:");
            resultado.setText("El grafo tiene " + grafo.cantidadDeAristas() + " aristas.");

        }

    }

    private void crearGrafo(int image) throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste {

        if (image == R.drawable.grafo_1) {
            grafo = new Grafo(7);

            grafo.insertarArista(0, 1);
            grafo.insertarArista(0, 2);
            grafo.insertarArista(0, 5);
            grafo.insertarArista(0, 6);
            grafo.insertarArista(1, 4);
            grafo.insertarArista(3, 6);
            grafo.insertarArista(4, 6);
            grafo.insertarArista(5, 6);

        } else if (image == R.drawable.grafo_2) {
            grafo = new Grafo(4);

            grafo.insertarArista(0, 2);
            grafo.insertarArista(0, 3);
            grafo.insertarArista(1, 2);
            grafo.insertarArista(1, 3);
            grafo.insertarArista(2, 3);
            grafo.insertarArista(3, 3);

        } else if (image == R.drawable.grafo_3) {
            grafo = new Grafo(8);

            grafo.insertarArista(0, 2);
            grafo.insertarArista(0, 5);
            grafo.insertarArista(1, 4);
            grafo.insertarArista(1, 6);
            grafo.insertarArista(2, 5);
            grafo.insertarArista(4, 6);
            grafo.insertarArista(5, 7);

        } else if (image == R.drawable.grafo_4) {
            grafo = new Grafo(7);

            grafo.insertarArista(0, 3);
            grafo.insertarArista(0, 6);
            grafo.insertarArista(1, 5);
            grafo.insertarArista(2, 5);
            grafo.insertarArista(3, 4);

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