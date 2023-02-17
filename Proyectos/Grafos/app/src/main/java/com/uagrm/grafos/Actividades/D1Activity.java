package com.uagrm.grafos.Actividades;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.uagrm.grafos.Grafos.Excepciones.ExcepcionAristaYaExiste;
import com.uagrm.grafos.Grafos.Excepciones.NroVerticesInvalidoExcepcion;
import com.uagrm.grafos.Grafos.NoPesados.Digrafo;
import com.uagrm.grafos.Grafos.NoPesados.ExisteCicloEnGrafo;
import com.uagrm.grafos.Grafos.NoPesados.MostrarIslasEnDigrafo;
import com.uagrm.grafos.R;

public class D1Activity extends AppCompatActivity {

    private Spinner spinner;
    private Spinner spinnerOrigenes;
    private Spinner spinnerDestinos;
    private TextView titulo;
    private TextView contenido;
    private TextView title_result;
    private TextView resultado;
    private ImageView imagen;

    private int image;
    private Digrafo grafo;
    private boolean cambio = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d1);

        titulo = (TextView) findViewById(R.id.textView);
        contenido = (TextView) findViewById(R.id.textView2);
        resultado = (TextView) findViewById(R.id.resultado);
        title_result = (TextView) findViewById(R.id.textView_resultado);
        imagen = (ImageView) findViewById(R.id.imageView);
        spinner = (Spinner) findViewById(R.id.spinner);
        spinnerOrigenes = (Spinner) findViewById(R.id.spinner_origenes);
        spinnerDestinos = (Spinner) findViewById(R.id.spinner_destinos);

        String[] opciones = {
                "Es Fuertemente Conexo", "Es Debilmente Conexo", "Existe Ciclo", "Nro De Islas",
                "Mostrar Islas", "Invertir Aristas", "Cantidad de Vertices", "Cantidad de Aristas",
                "Eliminar Vertice", "Eliminar Arista"
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

        } else if (seleccion.equals("Es Fuertemente Conexo")) {

            title_result.setText("Resultado:");
            if (grafo.esFuertementeConexo()) {
                resultado.setText("El grafo si es fuertemente conexo.");
            } else {
                resultado.setText("El grafo no es fuertemente conexo.");
            }

        } else if (seleccion.equals("Es Debilmente Conexo")) {

            title_result.setText("Resultado:");
            if (grafo.esDebilmenteConexo()) {
                resultado.setText("El grafo si es debilmente conexo.");
            } else {
                resultado.setText("El grafo no es debilmente conexo.");
            }

        } else if (seleccion.equals("Existe Ciclo")) {

            title_result.setText("Resultado:");
            if (grafo.existeCiclo()) {
                resultado.setText("Si existe ciclo.");
            } else {
                resultado.setText("No existe ciclo.");
            }

        } else if (seleccion.equals("Nro De Islas")) {

            int nroDeIslas = grafo.nroDeIslas();
            title_result.setText("Resultado:");
            resultado.setText("El grafo tiene " + nroDeIslas + " islas.");

        } else if (seleccion.equals("Invertir Aristas")) {

            Digrafo grafoAux = grafo.invertirAristas();
            grafo = grafoAux;
            contenido.setText(grafo.toString());

            if (image == R.drawable.digrafo_1) {
                if (!cambio) {
                    imagen.setImageResource(R.drawable.digrafo_1_invertido);
                    cambio = true;
                } else {
                    imagen.setImageResource(R.drawable.digrafo_1);
                    cambio = false;
                }
            } else if (image == R.drawable.digrafo_2) {
                if (!cambio) {
                    imagen.setImageResource(R.drawable.digrafo_2_invertido);
                    cambio = true;
                } else {
                    imagen.setImageResource(R.drawable.digrafo_2);
                    cambio = false;
                }
            } else if (image == R.drawable.digrafo_3) {
                if (!cambio) {
                    imagen.setImageResource(R.drawable.digrafo_3_invertido);
                    cambio = true;
                } else {
                    imagen.setImageResource(R.drawable.digrafo_3);
                    cambio = false;
                }
            } else if (image == R.drawable.digrafo_4) {
                if (!cambio) {
                    imagen.setImageResource(R.drawable.digrafo_4_invertido);
                    cambio = true;
                } else {
                    imagen.setImageResource(R.drawable.digrafo_4);
                    cambio = false;
                }
            }

        } else if (seleccion.equals("Mostrar Islas")) {

            MostrarIslasEnDigrafo mostrarIslas = new MostrarIslasEnDigrafo(grafo);
            title_result.setText("Resultado:");
            resultado.setText(mostrarIslas.obtenerIslas());

        } else if (seleccion.equals("Cantidad de Vertices")) {

            title_result.setText("Resultado:");
            resultado.setText("El grafo tiene " + grafo.cantidadDeVertices() + " vertices.");

        } else if (seleccion.equals("Cantidad de Aristas")) {

            title_result.setText("Resultado:");
            resultado.setText("El grafo tiene " + grafo.cantidadDeAristas() + " aristas.");

        }

    }

    private void crearGrafo(int image) throws NroVerticesInvalidoExcepcion, ExcepcionAristaYaExiste {

        if (image == R.drawable.digrafo_1) {
            grafo = new Digrafo(5);

            grafo.insertarArista(0,3);
            grafo.insertarArista(0,4);
            grafo.insertarArista(2,0);
            grafo.insertarArista(2,1);
            grafo.insertarArista(3,2);
            grafo.insertarArista(4,1);

        } else if (image == R.drawable.digrafo_2) {
            grafo = new Digrafo(8);

            grafo.insertarArista(0,2);
            grafo.insertarArista(0,5);
            grafo.insertarArista(1,0);
            grafo.insertarArista(1,3);
            grafo.insertarArista(1,4);
            grafo.insertarArista(2,5);
            grafo.insertarArista(3,4);
            grafo.insertarArista(3,7);
            grafo.insertarArista(4,2);
            grafo.insertarArista(4,6);
            grafo.insertarArista(5,1);
            grafo.insertarArista(5,4);
            grafo.insertarArista(6,3);
            grafo.insertarArista(6,7);
            grafo.insertarArista(7,6);

        } else if (image == R.drawable.digrafo_3) {
            grafo = new Digrafo(8);

            grafo.insertarArista(0,2);
            grafo.insertarArista(1,4);
            grafo.insertarArista(3,5);
            grafo.insertarArista(3,6);
            grafo.insertarArista(4,1);
            grafo.insertarArista(5,6);
            grafo.insertarArista(6,6);
            grafo.insertarArista(7,0);
            grafo.insertarArista(7,2);

        } else if (image == R.drawable.digrafo_4) {
            grafo = new Digrafo(7);

            grafo.insertarArista(0, 5);
            grafo.insertarArista(2, 6);
            grafo.insertarArista(3, 4);
            grafo.insertarArista(4, 0);
            grafo.insertarArista(4, 1);

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