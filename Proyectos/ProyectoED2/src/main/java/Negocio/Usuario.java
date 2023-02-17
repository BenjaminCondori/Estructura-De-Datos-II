package Negocio;

import java.awt.HeadlessException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private String user;
    private String password;
    private String nombre;
    private String email;
    private static final String ARCHIVO = "Usuarios.txt";
    File usuarios;

    public Usuario() {

    }

    public Usuario(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public Usuario(String user, String password, String nombre, String email) {
        this.user = user;
        this.password = password;
        this.nombre = nombre;
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Crea el archivo de "Usuarios"
    public void crearArchivoUsuario() {
        try {
            usuarios = new File(ARCHIVO);
            if (usuarios.createNewFile()) {
                System.out.println("Archivo creado " + usuarios.getName());
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al crear el Archivo");
        }
    }

    // Agrega los datos del nuevo usuario en el archivo de "Usuarios"
    public void agregarUsuario() {
        try {
            FileWriter escritura = new FileWriter(ARCHIVO, true);
            escritura.write(getUser() + "," + getPassword() + "," + getNombre() + ","
                    + getEmail() + "\r\n");
            escritura.close();
        } catch (IOException e) {
            System.out.println("Ocurrió un error al agregar el usuario.");
            System.out.println(e.toString());
        }
    }

    // Elimina los datos de un usuario en el archivo de "Usuarios"
    public void eliminarUsuario(String usuario) {
        String[] filas;
        List<String> lista = new ArrayList<>();
        try {
            FileReader archivo = new FileReader(ARCHIVO);
            BufferedReader lectura = new BufferedReader(archivo);

            String linea = lectura.readLine();
            while (linea != null) {
                filas = linea.split(",");
                if (!filas[0].equals(usuario)) {
                    lista.add(linea);
                }
                linea = lectura.readLine();
            }
            archivo.close();

            // Limpiamos el archivo .txt
            PrintWriter writer = new PrintWriter(ARCHIVO);
            writer.print("");
            writer.close();

            // Escribimos los datos de los usuarios que no fueron eliminados
            FileWriter fw = new FileWriter(ARCHIVO);
            BufferedWriter escritura = new BufferedWriter(fw);
            for (int i = 0; i < lista.size(); i++) {
                escritura.write(lista.get(i));
                escritura.flush();
                escritura.newLine();
            }
            escritura.close();
            eliminarArchivoContacto(usuario);
        } catch (IOException e) {
            System.out.println("Error::eliminarUsuario:Ocurrió un error");
            System.out.println(e.toString());
        }
    }

    // Elimina el archivo de contactos del usuario indicado
    private void eliminarArchivoContacto(String usuario) {
        try {
            String archivo = "Contactos" + usuario + ".txt";
            File contactos = new File(archivo);
            contactos.delete();
        } catch (HeadlessException e) {
            System.out.println("Ocurrió un error al eliminar el archivo de contacto.");
            System.out.println(e.toString());
        }
    }

    // Edita los datos de un usuario en el archivo de "Usuarios"
    public void editarUsuario(String[] usuario, boolean cambioContraseña) {
        String[] filas;
        List<String> lista = new ArrayList<>();
        try {
            FileReader archivo = new FileReader(ARCHIVO);
            BufferedReader lectura = new BufferedReader(archivo);

            String linea = lectura.readLine();
            while (linea != null) {
                filas = linea.split(",");
                if (cambioContraseña) {
                    if (filas[0].equals(usuario[0])) {
                        filas[1] = usuario[1];
                        linea = String.join(",", filas);
                    }
                } else {
                    if (filas[0].equals(usuario[0])) {
                        filas[0] = usuario[2];
                        filas[2] = usuario[1];
                        filas[3] = usuario[3];
                        linea = String.join(",", filas);
                    }
                }
                lista.add(linea);
                linea = lectura.readLine();
            }
            archivo.close();

            // Limpia el archivo .txt
            PrintWriter writer = new PrintWriter(ARCHIVO);
            writer.print("");
            writer.close();

            // Escribimos los usuarios nuevamente con los datos actualizados
            FileWriter fw = new FileWriter(ARCHIVO);
            BufferedWriter escritura = new BufferedWriter(fw);
            for (int i = 0; i < lista.size(); i++) {
                escritura.write(lista.get(i));
                escritura.flush();
                escritura.newLine();
            }
            escritura.close();
            
            if (cambioContraseña == false) {
                if (!usuario[0].equals(usuario[2])) {
                    File archivoDelUsuario = new File("Contactos" + usuario[0] + ".txt");
                    File nuevoArchivo = new File("Contactos" + usuario[2] + ".txt");
                    archivoDelUsuario.renameTo(nuevoArchivo); 
                }
            }        
        } catch (IOException e) {
            System.out.println("Error::editarUsuario:Ocurrió un error");
            System.out.println(e.toString());
        }

    }

    // Obtiene los datos del nombre, email
    public String[] obtenerDatosExtras(String password) {
        String[] filas;
        try {
            FileReader archivo = new FileReader(ARCHIVO);
            BufferedReader lectura = new BufferedReader(archivo);

            String linea = lectura.readLine();
            while (linea != null) {
                filas = linea.split(",");
                if (filas[1].equals(password)) {
                    return filas;
                }
                linea = lectura.readLine();
            }
            archivo.close();
        } catch (IOException e) {
            System.out.println(e);
        }
        return null;
    }

}
