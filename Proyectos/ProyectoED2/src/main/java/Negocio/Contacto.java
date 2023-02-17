package Negocio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringJoiner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class Contacto {
    
    private String nombre;
    private int telefono;
    File contactos;

    public Contacto() {
    }

    public Contacto(String nombre, int telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }
    
    public void crearArchivoContacto(String nombre) {
        try {
            contactos = new File("Contactos" + nombre + ".txt");
            if (contactos.createNewFile()) {
                System.out.println("Archivo creado " + contactos.getName());
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear el Archivo");
        }
    }
   
    public void agregarContacto(String usuario) {
        try {
            String archivo = "Contactos" + usuario + ".txt";
            FileWriter escritura = new FileWriter(archivo, true);
            escritura.write(getNombre() + "," + getTelefono() + "\r\n");
            escritura.close();
        } catch (IOException e) {
            System.out.println("Ocurrió un error al agregar el contacto");
            System.out.println(e.toString());
        }
    }
    
    public void eliminarContacto(JTable tablaContactos, Integer telefono, String usuario) {
        DefaultTableModel modelo = (DefaultTableModel) tablaContactos.getModel();
        String archivo = "Contactos" + usuario + ".txt";
        
        // Eliminación visual de la tabla
        for (int i = 0; i < modelo.getRowCount(); i++) {
            if (Integer.parseInt(modelo.getValueAt(i, 1).toString()) == telefono) {
                modelo.removeRow(i);
                break;
            }
        }
        
        // Limpieza del archivo .txt
        try {
            PrintWriter writer = new PrintWriter(archivo);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error::eliminarContacto:Ocurrió un error al limpiar el archivo");
            System.out.println(e.toString());
        }
        
        // Creación de los nuevos registros después de la eliminación
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(archivo)))) {
            for (int row = 0; row < tablaContactos.getRowCount(); row++) {
                StringJoiner joiner = new StringJoiner(",");
                for (int col = 0; col < tablaContactos.getColumnCount(); col++) {
                    Object obj = tablaContactos.getValueAt(row, col);
                    String value = (obj == null)? "null": obj.toString();
                    joiner.add(value);
                }
                bw.write(joiner.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error::eliminarContacto:Ocurrió un error");
            System.out.println(e.toString());
        }
        
    }
    
    public void editarContacto(JTable tablaContactos, String usuario) {
        // Limpieza del archivo .txt
        String archivo = "Contactos" + usuario + ".txt";
        try {
            PrintWriter writer = new PrintWriter(archivo);
            writer.print("");
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error::editarContacto:Ocurrió un error al limpiar el archivo");
            System.out.println(e.toString());
        }
        
        // Creación de los nuevos registros después de la eliminación
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File(archivo)))) {
            for (int row = 0; row < tablaContactos.getRowCount(); row++) {
                StringJoiner joiner = new StringJoiner(",");
                for (int col = 0; col < tablaContactos.getColumnCount(); col++) {
                    Object obj = tablaContactos.getValueAt(row, col);
                    String value = (obj == null)? "null": obj.toString();
                    joiner.add(value);
                }
                bw.write(joiner.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error::editarContacto:Ocurrió un error");
            System.out.println(e.toString());
        }
    }
 
}
