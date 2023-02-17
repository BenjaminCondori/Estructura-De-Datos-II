package Presentacion;

import Arboles.AVL;
import Arboles.ArbolB;
import Arboles.ArbolBinario;
import Arboles.ArbolMVias;
import Arboles.IArbolBusqueda;
import Excepciones.ClaveNoExisteException;
import Excepciones.ExcepcionOrdenInvalido;
import Utilidades.Mover;
import Utilidades.RenderTabla;
import Negocio.Contacto;
import Negocio.Usuario;
import Alertas.AlertError;
import Alertas.AlertInformation;
import Alertas.AlertSuccess;
import Alertas.AlertWarning;
import Alertas.AlertWarningSalir;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class Principal extends javax.swing.JFrame {

    IArbolBusqueda<String, String> usuarios;
    IArbolBusqueda<Integer, String> contactos;

    DefaultTableModel modelo;
    TableRowSorter<DefaultTableModel> sorter;
    String[] contactoEditar;
    String[] usuarioActual;

    int xMouse, yMouse;
    static final String ICONO_VER = "src/main/resources/Imagenes/ojo.png";
    static final String ICONO_OCULTAR = "src/main/resources/Imagenes/ocultar.png";
    static String tipoArbol;

    public Principal() throws ExcepcionOrdenInvalido {
        initComponents();
        deshabilitarBotones();
        setBackground(new Color(1.0f, 1.0f, 1.0f, 0.0f));
        this.setLocationRelativeTo(null);

        switch (tipoArbol) {
            case "ABB" ->
                this.usuarios = new ArbolBinario<>();
            case "AVL" ->
                this.usuarios = new AVL<>();
            case "AMV" ->
                this.usuarios = new ArbolMVias<>(4);
            default -> {
                System.out.println("Tipo de árbol inválido, eligiendo ArbolBinario");
                this.usuarios = new ArbolBinario<>();
            }
        }

        cargarUsuarios();
        System.out.println("Usuarios: " + usuarios.recorridoPorNiveles());
    }

    @Override
    public Image getIconImage() {
        Image icono = Toolkit.getDefaultToolkit().
                getImage(ClassLoader.getSystemResource("Imagenes/icon.png"));
        return icono;
    }

    // Crea la tabla donde se muestran los contactos
    private void crearTabla() {
        modelo = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        modelo.addColumn("NOMBRE");
        modelo.addColumn("TELEFONO");
        tabla.setModel(modelo);
        tabla.setDefaultRenderer(Object.class, new RenderTabla());
        sorter = new TableRowSorter<>(modelo);
        tabla.setRowSorter(sorter);
    }

    // Carga los datos del usuario que incia sesión.
    private void cargarDatos(String usuario) {
        try {
            crearTabla();
            cargarContacto(usuario);
            habilitarBotones();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /*
     Carga la tabla y el árbol denomidado "contactos" con los datos obtenidos 
     del archivo "Contactos.txt" correspondiende al usuario actual.
     */
    private void cargarContacto(String usuario) {
        String filas[];
        try {
            FileReader archivo = new FileReader("Contactos" + usuario + ".txt");
            BufferedReader lectura = new BufferedReader(archivo);

            String linea = lectura.readLine();
            while (linea != null) {
                filas = linea.split(",");
                contactos.insertar(Integer.parseInt(filas[1]), filas[0]);
                modelo.addRow(filas);
                linea = lectura.readLine();
            }
            archivo.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    /*
     Lee el archivo "Usuarios.txt" y obtiene la contraseña, el nombre de usuario
     de cada usuario que haya sido registrado. 
     Y lo inserta en árbol denomiado "usuarios".
     */
    private void cargarUsuarios() {
        String filas[];
        try {
            FileReader archivo = new FileReader("Usuarios.txt");
            BufferedReader lectura = new BufferedReader(archivo);

            String linea = lectura.readLine();
            while (linea != null) {
                filas = linea.split(",");
                usuarios.insertar(filas[1], filas[0]);
                linea = lectura.readLine();
            }
            archivo.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    // Verifica si un nombre de usuario ya existe.
    private boolean existeUsuario(String usuario) {
        List<String> lista = usuarios.recorridoPostOrden();
        for (int i = 0; i < lista.size(); i++) {
            if (usuarios.buscar(lista.get(i)).equals(usuario)) {
                return true;
            }
        }
        return false;
    }

    // Llena los datos del usuario actual en el panel_perfil.
    private void llenarDatosUsuario(String user, String password) {
        Usuario usuario = new Usuario();
        String[] datos = usuario.obtenerDatosExtras(password);
        label_perfil.setText(user);
        label_nombre.setText(datos[2]);
        label_nombreUsuario.setText(user);
        label_email.setText(datos[3]);
        label_cantContactos.setText(String.valueOf(tabla.getRowCount()));
    }

    public void mostrarInicioSesion() {
        btn_verContraseña.setIcon(new ImageIcon(ICONO_VER));
        btn_verContraseña1.setIcon(new ImageIcon(ICONO_VER));
        txt_usuario.setText("");
        txt_password.setText("");
        txt_password.setEchoChar('\u25cf');
        txt_password1.setEchoChar('\u25cf');
        label_perfil.setText("Admin");
        usuarioActual = null;
        tabla.setModel(new DefaultTableModel());
        deshabilitarBotones();
        mostrarPanel(panel_inicioSesion);
    }

    private void limpiarCampoDeContraseña(JButton boton, JPasswordField password) {
        boton.setIcon(new ImageIcon(ICONO_VER));
        password.setEchoChar('\u25cf');
    }

    private void limpiarCamposCambioContraseña() {
        txt_antiguaContraseña.setText("");
        txt_nuevaContraseña.setText("");
        txt_confirmarContraseña.setText("");
        limpiarCampoDeContraseña(btn_ver1, txt_antiguaContraseña);
        limpiarCampoDeContraseña(btn_ver2, txt_nuevaContraseña);
        limpiarCampoDeContraseña(btn_ver3, txt_confirmarContraseña);
    }

    private void mostrarContraseña(JButton boton, JPasswordField password) {
        URL pathIcon = this.getClass().getClassLoader().getResource("Imagenes/ojo.png");
        if (boton.getIcon().toString().equals(ICONO_VER) || boton.getIcon().toString().equals(pathIcon.toString())) {
            boton.setIcon(new ImageIcon(ICONO_OCULTAR));
            password.setEchoChar((char) 0);
        } else {
            boton.setIcon(new ImageIcon(ICONO_VER));
            password.setEchoChar('\u25cf');
        }
    }

    // Limpia los JTextField ingresados por parametro
    private void limpiar(JTextField text1, JTextField text2) {
        text1.setText("");
        text2.setText("");
    }

    // Deshabilita algunos botones cuando no se haya iniciado sesión.
    private void deshabilitarBotones() {
        btn_guardar.setEnabled(false);
        btn_buscar.setEnabled(false);
        btn_editar.setEnabled(false);
        btn_eliminar.setEnabled(false);
    }

    // Habilita los botones deshabilitados cuando se haya iniciado sesión.
    private void habilitarBotones() {
        btn_guardar.setEnabled(true);
        btn_buscar.setEnabled(true);
        btn_editar.setEnabled(true);
        btn_eliminar.setEnabled(true);
    }

    private void mostrarCuadroDialogo(String titulo, String mensaje, int tipo) {
        switch (tipo) {
            case 1 -> {
                AlertError alerta = new AlertError(this, true);
                AlertError.titulo.setText(titulo);
                AlertError.mensaje.setText(mensaje);
                alerta.setVisible(true);
            }
            case 2 -> {
                AlertSuccess alerta = new AlertSuccess(this, true);
                AlertSuccess.titulo.setText(titulo);
                AlertSuccess.mensaje.setText(mensaje);
                alerta.setVisible(true);
            }
            case 3 -> {
                AlertWarning alerta = new AlertWarning(this, true);
                AlertWarning.titulo.setText(titulo);
                AlertWarning.mensaje.setText(mensaje);
                alerta.setVisible(true);
            }
            case 4 -> {
                AlertWarningSalir alerta = new AlertWarningSalir(this, true);
                AlertWarningSalir.titulo.setText(titulo);
                alerta.setVisible(true);
            }
            case 5 -> {
                AlertInformation alerta = new AlertInformation(this, true);
                AlertInformation.titulo.setText(titulo);
                AlertInformation.mensaje.setText(mensaje);
                alerta.setVisible(true);
            }
        }
    }

    // Muestra el panel indicado por parametro
    private void mostrarPanel(JPanel panel) {
        Mover mover = new Mover();
        mover.hidePanel(panel_inicioSesion, panel_contactos, panel_añadir,
                panel_registro, panel_editar, panel_usuario, panel_editarUsuario,
                panel_cambiarContraseña);
        panel.setVisible(true);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelRound1 = new button.PanelRound();
        btn_minimizar = new button.MyButton();
        btn_cerrar = new button.MyButton();
        PanelRound2 = new button.PanelRound();
        btn_añadir = new button.MyButton();
        btn_usuario = new button.MyButton();
        btn_contactos = new button.MyButton();
        btn_salir = new button.MyButton();
        jLabel10 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        panel_imagen = new button.PanelRound();
        rSPanelCircleImage1 = new rojerusan.RSPanelCircleImage();
        label_perfil = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        card_panel = new javax.swing.JPanel();
        panel_inicioSesion = new javax.swing.JPanel();
        rSFotoSquareResize1 = new rojerusan.RSFotoSquareResize();
        panel_login = new button.PanelRound();
        btn_ingresar = new button.MyButton();
        txt_usuario = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        label_crear = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        contraseña = new javax.swing.JPanel();
        txt_password = new javax.swing.JPasswordField();
        btn_verContraseña = new button.MyButton();
        panel_registro = new javax.swing.JPanel();
        rSFotoSquareResize2 = new rojerusan.RSFotoSquareResize();
        panel_login1 = new button.PanelRound();
        btn_registrarse = new button.MyButton();
        txt_email = new javax.swing.JTextField();
        txt_nombreReg = new javax.swing.JTextField();
        txt_usuario1 = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        label_regresar = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        contraseña1 = new javax.swing.JPanel();
        txt_password1 = new javax.swing.JPasswordField();
        btn_verContraseña1 = new button.MyButton();
        panel_contactos = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabla = new rojerusan.RSTableMetro();
        btn_buscar = new button.MyButton();
        btn_editar = new button.MyButton();
        btn_eliminar = new button.MyButton();
        txt_busqueda = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        panel_editar = new javax.swing.JPanel();
        panel_edit = new button.PanelRound();
        txt_nombre1 = new javax.swing.JTextField();
        txt_telefono1 = new javax.swing.JTextField();
        btn_cancelar = new button.MyButton();
        btn_guardarCambios = new button.MyButton();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        panel_añadir = new javax.swing.JPanel();
        panel_agregar = new button.PanelRound();
        txt_telefono = new javax.swing.JTextField();
        txt_nombre = new javax.swing.JTextField();
        btn_guardar = new button.MyButton();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        panel_editarUsuario = new javax.swing.JPanel();
        panel_editUser = new button.PanelRound();
        rSFotoSquareResize4 = new rojerusan.RSFotoSquareResize();
        btn_Cancelar = new button.MyButton();
        btn_GuardarCambios = new button.MyButton();
        jSeparator2 = new javax.swing.JSeparator();
        txt_editarNombreUsuario = new javax.swing.JTextField();
        txt_editarNombre = new javax.swing.JTextField();
        txt_editarEmail = new javax.swing.JTextField();
        jLabel41 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        panel_usuario = new javax.swing.JPanel();
        panel_perfil = new button.PanelRound();
        jSeparator1 = new javax.swing.JSeparator();
        rSFotoSquareResize3 = new rojerusan.RSFotoSquareResize();
        btn_cerrarSesion = new button.MyButton();
        btn_editarUsuario = new button.MyButton();
        lbl_cambiarContraseña = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        lbl_contactos = new javax.swing.JLabel();
        label_email = new javax.swing.JLabel();
        label_nombreUsuario = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        lbl_eliminar = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        label_nombre = new javax.swing.JLabel();
        label_cantContactos = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        panel_cambiarContraseña = new javax.swing.JPanel();
        panel_cambiarPassword = new button.PanelRound();
        btn_actualizarContraseña = new button.MyButton();
        btn_cancelarContraseña = new button.MyButton();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        contraseña_antigua = new javax.swing.JPanel();
        txt_antiguaContraseña = new javax.swing.JPasswordField();
        btn_ver1 = new button.MyButton();
        nueva_contraseña = new javax.swing.JPanel();
        txt_nuevaContraseña = new javax.swing.JPasswordField();
        btn_ver2 = new button.MyButton();
        confirmar_contraseña = new javax.swing.JPanel();
        txt_confirmarContraseña = new javax.swing.JPasswordField();
        btn_ver3 = new button.MyButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setIconImage(getIconImage());
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelRound1.setBackground(new java.awt.Color(35, 35, 35));
        panelRound1.setRoundBottomLeft(25);
        panelRound1.setRoundBottomRight(25);
        panelRound1.setRoundTopLeft(25);
        panelRound1.setRoundTopRight(25);
        panelRound1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                panelRound1MouseDragged(evt);
            }
        });
        panelRound1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                panelRound1MousePressed(evt);
            }
        });
        panelRound1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_minimizar.setBackground(new java.awt.Color(35, 35, 35));
        btn_minimizar.setForeground(new java.awt.Color(255, 255, 255));
        btn_minimizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/minimizar.png"))); // NOI18N
        btn_minimizar.setBorderColor(new java.awt.Color(35, 35, 35));
        btn_minimizar.setColor(new java.awt.Color(35, 35, 35));
        btn_minimizar.setColorClick(new java.awt.Color(56, 56, 56));
        btn_minimizar.setColorOver(new java.awt.Color(102, 102, 102));
        btn_minimizar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_minimizar.setRadius(15);
        btn_minimizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_minimizarActionPerformed(evt);
            }
        });
        panelRound1.add(btn_minimizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1138, 14, 30, 30));

        btn_cerrar.setBackground(new java.awt.Color(35, 35, 35));
        btn_cerrar.setForeground(new java.awt.Color(255, 255, 255));
        btn_cerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/cerrar.png"))); // NOI18N
        btn_cerrar.setBorderColor(new java.awt.Color(35, 35, 35));
        btn_cerrar.setColor(new java.awt.Color(35, 35, 35));
        btn_cerrar.setColorClick(new java.awt.Color(193, 51, 0));
        btn_cerrar.setColorOver(new java.awt.Color(204, 51, 0));
        btn_cerrar.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btn_cerrar.setRadius(15);
        btn_cerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarActionPerformed(evt);
            }
        });
        panelRound1.add(btn_cerrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(1167, 14, 30, 30));

        PanelRound2.setBackground(new java.awt.Color(27, 27, 27));
        PanelRound2.setRoundBottomLeft(25);
        PanelRound2.setRoundTopLeft(25);
        PanelRound2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_añadir.setBackground(new java.awt.Color(27, 27, 27));
        btn_añadir.setForeground(new java.awt.Color(255, 255, 255));
        btn_añadir.setCursor(new Cursor(HAND_CURSOR));
        btn_añadir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregar_contacto.png"))); // NOI18N
        btn_añadir.setText("       AGREGAR   ");
        btn_añadir.setBorderColor(new java.awt.Color(27, 27, 27));
        btn_añadir.setColor(new java.awt.Color(27, 27, 27));
        btn_añadir.setColorClick(new java.awt.Color(102, 102, 102));
        btn_añadir.setColorOver(new java.awt.Color(56, 56, 56));
        btn_añadir.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btn_añadir.setRadius(15);
        btn_añadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_añadirActionPerformed(evt);
            }
        });
        PanelRound2.add(btn_añadir, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, 230, 43));

        btn_usuario.setBackground(new java.awt.Color(27, 27, 27));
        btn_usuario.setForeground(new java.awt.Color(255, 255, 255));
        btn_usuario.setCursor(new Cursor(HAND_CURSOR));
        btn_usuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario.png"))); // NOI18N
        btn_usuario.setText("       USUARIO   ");
        btn_usuario.setBorderColor(new java.awt.Color(27, 27, 27));
        btn_usuario.setColor(new java.awt.Color(27, 27, 27));
        btn_usuario.setColorClick(new java.awt.Color(102, 102, 102));
        btn_usuario.setColorOver(new java.awt.Color(56, 56, 56));
        btn_usuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btn_usuario.setRadius(15);
        btn_usuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_usuarioActionPerformed(evt);
            }
        });
        PanelRound2.add(btn_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 230, 43));

        btn_contactos.setBackground(new java.awt.Color(27, 27, 27));
        btn_contactos.setForeground(new java.awt.Color(255, 255, 255));
        btn_contactos.setCursor(new Cursor(HAND_CURSOR));
        btn_contactos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/contactos.png"))); // NOI18N
        btn_contactos.setText("      CONTACTOS");
        btn_contactos.setBorderColor(new java.awt.Color(27, 27, 27));
        btn_contactos.setColor(new java.awt.Color(27, 27, 27));
        btn_contactos.setColorClick(new java.awt.Color(102, 102, 102));
        btn_contactos.setColorOver(new java.awt.Color(56, 56, 56));
        btn_contactos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        btn_contactos.setRadius(15);
        btn_contactos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_contactosActionPerformed(evt);
            }
        });
        PanelRound2.add(btn_contactos, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 335, 230, 43));

        btn_salir.setBackground(new java.awt.Color(45, 235, 201));
        btn_salir.setForeground(new java.awt.Color(0, 0, 0));
        btn_salir.setCursor(new Cursor(HAND_CURSOR));
        btn_salir.setText("SALIR");
        btn_salir.setBorderColor(new java.awt.Color(27, 27, 27));
        btn_salir.setColor(new java.awt.Color(45, 235, 201));
        btn_salir.setColorClick(new java.awt.Color(255, 51, 0));
        btn_salir.setColorOver(new java.awt.Color(204, 51, 0));
        btn_salir.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_salir.setRadius(15);
        btn_salir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_salirMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_salirMouseExited(evt);
            }
        });
        btn_salir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salirActionPerformed(evt);
            }
        });
        PanelRound2.add(btn_salir, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 445, 230, 40));

        jLabel10.setBackground(new java.awt.Color(204, 204, 204));
        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("AGENDA");
        PanelRound2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 65, 220, 20));

        jLabel15.setBackground(new java.awt.Color(204, 204, 204));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agenda-de-contactos.png"))); // NOI18N
        PanelRound2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 135, 200, 130));

        jLabel4.setBackground(new java.awt.Color(204, 204, 204));
        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("TELEFÓNICA");
        PanelRound2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 220, 20));

        panel_imagen.setBackground(new java.awt.Color(56, 56, 56));
        panel_imagen.setRoundBottomLeft(20);
        panel_imagen.setRoundBottomRight(20);
        panel_imagen.setRoundTopLeft(20);
        panel_imagen.setRoundTopRight(20);
        panel_imagen.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rSPanelCircleImage1.setColorBorde(new java.awt.Color(45, 235, 201));
        rSPanelCircleImage1.setImagen(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario (1).png"))); // NOI18N

        javax.swing.GroupLayout rSPanelCircleImage1Layout = new javax.swing.GroupLayout(rSPanelCircleImage1);
        rSPanelCircleImage1.setLayout(rSPanelCircleImage1Layout);
        rSPanelCircleImage1Layout.setHorizontalGroup(
            rSPanelCircleImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );
        rSPanelCircleImage1Layout.setVerticalGroup(
            rSPanelCircleImage1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 110, Short.MAX_VALUE)
        );

        panel_imagen.add(rSPanelCircleImage1, new org.netbeans.lib.awtextra.AbsoluteConstraints(6, 10, 110, 110));

        label_perfil.setBackground(new java.awt.Color(153, 153, 153));
        label_perfil.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        label_perfil.setForeground(new java.awt.Color(255, 255, 255));
        label_perfil.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_perfil.setText("Admin");
        panel_imagen.add(label_perfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 80, 116, 20));

        jLabel3.setBackground(new java.awt.Color(204, 204, 204));
        jLabel3.setFont(new java.awt.Font("Segoe UI Semibold", 0, 15)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(204, 204, 204));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Usuario:");
        jLabel3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel_imagen.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 57, 80, 20));

        PanelRound2.add(panel_imagen, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 540, 230, 130));

        panelRound1.add(PanelRound2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 700));

        card_panel.setBackground(new java.awt.Color(35, 35, 35));
        card_panel.setLayout(new java.awt.CardLayout());

        panel_inicioSesion.setBackground(new java.awt.Color(35, 35, 35));
        panel_inicioSesion.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rSFotoSquareResize1.setBackground(new java.awt.Color(35, 35, 35));
        rSFotoSquareResize1.setBorder(null);
        rSFotoSquareResize1.setImagenDefault(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario (4).png"))); // NOI18N
        panel_inicioSesion.add(rSFotoSquareResize1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 320, 450));

        panel_login.setBackground(new java.awt.Color(27, 27, 27));
        panel_login.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(45, 235, 201)));
        panel_login.setRoundBottomLeft(15);
        panel_login.setRoundBottomRight(15);
        panel_login.setRoundTopLeft(15);
        panel_login.setRoundTopRight(15);
        panel_login.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_ingresar.setBackground(new java.awt.Color(45, 235, 201));
        btn_ingresar.setForeground(new java.awt.Color(0, 0, 0));
        btn_ingresar.setCursor(new Cursor(HAND_CURSOR));
        btn_ingresar.setText("INICIAR  SESIÓN");
        btn_ingresar.setBorderColor(new java.awt.Color(27, 27, 27));
        btn_ingresar.setColor(new java.awt.Color(45, 235, 201));
        btn_ingresar.setColorClick(new java.awt.Color(45, 235, 201));
        btn_ingresar.setColorOver(new java.awt.Color(100, 205, 187));
        btn_ingresar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_ingresar.setRadius(15);
        btn_ingresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ingresarActionPerformed(evt);
            }
        });
        panel_login.add(btn_ingresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 270, 40));

        txt_usuario.setBackground(new java.awt.Color(56, 56, 56));
        txt_usuario.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_usuario.setForeground(new java.awt.Color(204, 204, 204));
        txt_usuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_usuario.setBorder(null);
        panel_login.add(txt_usuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 270, 33));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(153, 153, 153));
        jLabel14.setText("¿No tienes una cuenta?");
        panel_login.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 460, -1, -1));

        label_crear.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        label_crear.setForeground(new java.awt.Color(153, 153, 153));
        label_crear.setText("Cree una.");
        label_crear.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_crear.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_crearMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_crearMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_crearMouseExited(evt);
            }
        });
        panel_login.add(label_crear, new org.netbeans.lib.awtextra.AbsoluteConstraints(247, 460, -1, -1));

        jLabel8.setBackground(new java.awt.Color(153, 153, 153));
        jLabel8.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Usuario:");
        panel_login.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 238, 70, 20));

        jLabel7.setBackground(new java.awt.Color(153, 153, 153));
        jLabel7.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Contraseña:");
        panel_login.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(75, 298, 110, 20));

        jLabel6.setBackground(new java.awt.Color(204, 204, 204));
        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 27)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Inicio de Sesión");
        panel_login.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, 270, 40));

        jLabel5.setBackground(new java.awt.Color(204, 204, 204));
        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/locked.png"))); // NOI18N
        jLabel5.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel_login.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 87, 270, 140));

        contraseña.setBackground(new java.awt.Color(56, 56, 56));
        contraseña.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_password.setBackground(new java.awt.Color(56, 56, 56));
        txt_password.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_password.setForeground(new java.awt.Color(204, 204, 204));
        txt_password.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_password.setBorder(null);
        txt_password.setEchoChar('\u25cf');
        contraseña.add(txt_password, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 235, 33));

        btn_verContraseña.setBorder(null);
        btn_verContraseña.setCursor(new Cursor(HAND_CURSOR));
        btn_verContraseña.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ojo.png"))); // NOI18N
        btn_verContraseña.setBorderColor(new java.awt.Color(56, 56, 56));
        btn_verContraseña.setBorderPainted(false);
        btn_verContraseña.setColor(new java.awt.Color(56, 56, 56));
        btn_verContraseña.setColorClick(new java.awt.Color(56, 56, 56));
        btn_verContraseña.setColorOver(new java.awt.Color(56, 56, 56));
        btn_verContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_verContraseñaActionPerformed(evt);
            }
        });
        contraseña.add(btn_verContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(237, 0, 33, 33));

        panel_login.add(contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 320, 270, 33));

        panel_inicioSesion.add(panel_login, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 420, 520));

        card_panel.add(panel_inicioSesion, "card2");

        panel_registro.setBackground(new java.awt.Color(35, 35, 35));
        panel_registro.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rSFotoSquareResize2.setBackground(new java.awt.Color(35, 35, 35));
        rSFotoSquareResize2.setBorder(null);
        rSFotoSquareResize2.setImagenDefault(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario (4).png"))); // NOI18N
        panel_registro.add(rSFotoSquareResize2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 320, 450));

        panel_login1.setBackground(new java.awt.Color(27, 27, 27));
        panel_login1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(45, 235, 201)));
        panel_login1.setRoundBottomLeft(15);
        panel_login1.setRoundBottomRight(15);
        panel_login1.setRoundTopLeft(15);
        panel_login1.setRoundTopRight(15);
        panel_login1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_registrarse.setBackground(new java.awt.Color(45, 235, 201));
        btn_registrarse.setForeground(new java.awt.Color(0, 0, 0));
        btn_registrarse.setCursor(new Cursor(HAND_CURSOR));
        btn_registrarse.setText("REGISTRARSE");
        btn_registrarse.setBorderColor(new java.awt.Color(27, 27, 27));
        btn_registrarse.setColor(new java.awt.Color(45, 235, 201));
        btn_registrarse.setColorClick(new java.awt.Color(45, 235, 201));
        btn_registrarse.setColorOver(new java.awt.Color(100, 205, 187));
        btn_registrarse.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_registrarse.setRadius(15);
        btn_registrarse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_registrarseActionPerformed(evt);
            }
        });
        panel_login1.add(btn_registrarse, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 270, 40));

        txt_email.setBackground(new java.awt.Color(56, 56, 56));
        txt_email.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_email.setForeground(new java.awt.Color(204, 204, 204));
        txt_email.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_email.setBorder(null);
        txt_email.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_emailKeyTyped(evt);
            }
        });
        panel_login1.add(txt_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 350, 270, 33));

        txt_nombreReg.setBackground(new java.awt.Color(56, 56, 56));
        txt_nombreReg.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_nombreReg.setForeground(new java.awt.Color(204, 204, 204));
        txt_nombreReg.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nombreReg.setBorder(null);
        txt_nombreReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nombreRegKeyTyped(evt);
            }
        });
        panel_login1.add(txt_nombreReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, 270, 33));

        txt_usuario1.setBackground(new java.awt.Color(56, 56, 56));
        txt_usuario1.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_usuario1.setForeground(new java.awt.Color(204, 204, 204));
        txt_usuario1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_usuario1.setBorder(null);
        txt_usuario1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_usuario1KeyTyped(evt);
            }
        });
        panel_login1.add(txt_usuario1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 270, 33));

        jLabel36.setBackground(new java.awt.Color(153, 153, 153));
        jLabel36.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(255, 255, 255));
        jLabel36.setText("Nombre:");
        panel_login1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 148, 70, 20));

        jLabel35.setBackground(new java.awt.Color(153, 153, 153));
        jLabel35.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(255, 255, 255));
        jLabel35.setText("Nombre de Usuario:");
        panel_login1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 208, 170, 20));

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(153, 153, 153));
        jLabel29.setText("¿Ya tienes una cuenta?");
        panel_login1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(97, 460, -1, -1));

        label_regresar.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        label_regresar.setForeground(new java.awt.Color(153, 153, 153));
        label_regresar.setText("Inicia Sesión.");
        label_regresar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        label_regresar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                label_regresarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                label_regresarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                label_regresarMouseExited(evt);
            }
        });
        panel_login1.add(label_regresar, new org.netbeans.lib.awtextra.AbsoluteConstraints(237, 460, -1, -1));

        jLabel28.setBackground(new java.awt.Color(153, 153, 153));
        jLabel28.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("E-mail:");
        panel_login1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 328, 70, 20));

        jLabel27.setBackground(new java.awt.Color(153, 153, 153));
        jLabel27.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(255, 255, 255));
        jLabel27.setText("Contraseña:");
        panel_login1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(72, 268, 100, 20));

        jLabel26.setBackground(new java.awt.Color(204, 204, 204));
        jLabel26.setFont(new java.awt.Font("Segoe UI", 1, 27)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(255, 255, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Registro de Usuario");
        panel_login1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 270, 40));

        jLabel12.setBackground(new java.awt.Color(204, 204, 204));
        jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/agregar_usuario.png"))); // NOI18N
        panel_login1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 65, 270, 80));

        contraseña1.setBackground(new java.awt.Color(56, 56, 56));
        contraseña1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_password1.setBackground(new java.awt.Color(56, 56, 56));
        txt_password1.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_password1.setForeground(new java.awt.Color(204, 204, 204));
        txt_password1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_password1.setBorder(null);
        txt_password1.setEchoChar('\u25cf');
        txt_password1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_password1KeyTyped(evt);
            }
        });
        contraseña1.add(txt_password1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 235, 33));

        btn_verContraseña1.setBorder(null);
        btn_verContraseña1.setCursor(new Cursor(HAND_CURSOR));
        btn_verContraseña1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ojo.png"))); // NOI18N
        btn_verContraseña1.setBorderColor(new java.awt.Color(56, 56, 56));
        btn_verContraseña1.setBorderPainted(false);
        btn_verContraseña1.setColor(new java.awt.Color(56, 56, 56));
        btn_verContraseña1.setColorClick(new java.awt.Color(56, 56, 56));
        btn_verContraseña1.setColorOver(new java.awt.Color(56, 56, 56));
        btn_verContraseña1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_verContraseña1ActionPerformed(evt);
            }
        });
        contraseña1.add(btn_verContraseña1, new org.netbeans.lib.awtextra.AbsoluteConstraints(237, 0, 33, 33));

        panel_login1.add(contraseña1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 270, 33));

        panel_registro.add(panel_login1, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 40, 420, 520));

        card_panel.add(panel_registro, "card3");

        panel_contactos.setBackground(new java.awt.Color(35, 35, 35));
        panel_contactos.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tabla.setAutoCreateRowSorter(true);
        tabla.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tabla.setAltoHead(35);
        tabla.setColorBackgoundHead(new java.awt.Color(65, 65, 65));
        tabla.setColorFilasForeground1(new java.awt.Color(88, 88, 88));
        tabla.setColorFilasForeground2(new java.awt.Color(88, 88, 88));
        tabla.setColorSelBackgound(new java.awt.Color(153, 153, 153));
        tabla.setFont(new java.awt.Font("Roboto", 0, 15)); // NOI18N
        tabla.setFuenteFilas(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tabla.setFuenteFilasSelect(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        tabla.setFuenteHead(new java.awt.Font("Dialog", 1, 16)); // NOI18N
        tabla.setGrosorBordeFilas(0);
        tabla.setGrosorBordeHead(0);
        tabla.setMultipleSeleccion(false);
        tabla.setRowHeight(25);
        tabla.setSelectionBackground(new java.awt.Color(102, 102, 102));
        tabla.setShowGrid(true);
        jScrollPane3.setViewportView(tabla);

        panel_contactos.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 135, 670, 410));

        btn_buscar.setBackground(new java.awt.Color(45, 235, 201));
        btn_buscar.setForeground(new java.awt.Color(0, 0, 0));
        btn_buscar.setCursor(new Cursor(HAND_CURSOR));
        btn_buscar.setText("BUSCAR");
        btn_buscar.setBorderColor(new java.awt.Color(35, 35, 35));
        btn_buscar.setBorderPainted(false);
        btn_buscar.setColor(new java.awt.Color(45, 235, 201));
        btn_buscar.setColorClick(new java.awt.Color(45, 235, 201));
        btn_buscar.setColorOver(new java.awt.Color(100, 205, 187));
        btn_buscar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_buscar.setRadius(15);
        btn_buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_buscarActionPerformed(evt);
            }
        });
        panel_contactos.add(btn_buscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 75, 180, 38));

        btn_editar.setForeground(new java.awt.Color(0, 0, 0));
        btn_editar.setCursor(new Cursor(HAND_CURSOR));
        btn_editar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lapiz_negro.png"))); // NOI18N
        btn_editar.setText("    EDITAR  ");
        btn_editar.setBorderColor(new java.awt.Color(35, 35, 35));
        btn_editar.setColor(new java.awt.Color(45, 235, 201));
        btn_editar.setColorClick(new java.awt.Color(28, 45, 120));
        btn_editar.setColorOver(new java.awt.Color(42, 68, 181));
        btn_editar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_editar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lapiz_blanco.png"))); // NOI18N
        btn_editar.setRadius(15);
        btn_editar.setRolloverEnabled(true);
        btn_editar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lapiz_blanco.png"))); // NOI18N
        btn_editar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_editarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_editarMouseExited(evt);
            }
        });
        btn_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarActionPerformed(evt);
            }
        });
        panel_contactos.add(btn_editar, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 560, 180, 38));

        btn_eliminar.setForeground(new java.awt.Color(0, 0, 0));
        btn_eliminar.setCursor(new Cursor(HAND_CURSOR));
        btn_eliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar_negro.png"))); // NOI18N
        btn_eliminar.setText("   ELIMINAR");
        btn_eliminar.setBorderColor(new java.awt.Color(35, 35, 35));
        btn_eliminar.setColor(new java.awt.Color(45, 235, 201));
        btn_eliminar.setColorClick(new java.awt.Color(255, 51, 0));
        btn_eliminar.setColorOver(new java.awt.Color(204, 51, 0));
        btn_eliminar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_eliminar.setOver(true);
        btn_eliminar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar_blanco.png"))); // NOI18N
        btn_eliminar.setRadius(15);
        btn_eliminar.setRolloverEnabled(true);
        btn_eliminar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/eliminar_blanco.png"))); // NOI18N
        btn_eliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_eliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_eliminarMouseExited(evt);
            }
        });
        btn_eliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_eliminarActionPerformed(evt);
            }
        });
        panel_contactos.add(btn_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 560, 180, 38));

        txt_busqueda.setBackground(new java.awt.Color(56, 56, 56));
        txt_busqueda.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_busqueda.setForeground(new java.awt.Color(204, 204, 204));
        txt_busqueda.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_busqueda.setBorder(null);
        txt_busqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_busquedaKeyReleased(evt);
            }
        });
        panel_contactos.add(txt_busqueda, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 78, 290, 33));

        jLabel21.setBackground(new java.awt.Color(153, 153, 153));
        jLabel21.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Buscar contacto:");
        panel_contactos.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 84, 150, 20));

        jLabel20.setBackground(new java.awt.Color(204, 204, 204));
        jLabel20.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Lista de Contactos Registrados");
        panel_contactos.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 670, 40));

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/lupa.png"))); // NOI18N
        panel_contactos.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 75, 30, 40));

        card_panel.add(panel_contactos, "card4");

        panel_editar.setBackground(new java.awt.Color(35, 35, 35));
        panel_editar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_edit.setBackground(new java.awt.Color(35, 35, 35));
        panel_edit.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(45, 235, 201)));
        panel_edit.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_nombre1.setBackground(new java.awt.Color(56, 56, 56));
        txt_nombre1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_nombre1.setForeground(new java.awt.Color(204, 204, 204));
        txt_nombre1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nombre1.setBorder(null);
        panel_edit.add(txt_nombre1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 310, 33));

        txt_telefono1.setBackground(new java.awt.Color(56, 56, 56));
        txt_telefono1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_telefono1.setForeground(new java.awt.Color(204, 204, 204));
        txt_telefono1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telefono1.setBorder(null);
        txt_telefono1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefono1KeyTyped(evt);
            }
        });
        panel_edit.add(txt_telefono1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 310, 33));

        btn_cancelar.setForeground(new java.awt.Color(0, 0, 0));
        btn_cancelar.setCursor(new Cursor(HAND_CURSOR));
        btn_cancelar.setText("CANCELAR");
        btn_cancelar.setBorderColor(new java.awt.Color(35, 35, 35));
        btn_cancelar.setColor(new java.awt.Color(45, 235, 201));
        btn_cancelar.setColorClick(new java.awt.Color(255, 51, 0));
        btn_cancelar.setColorOver(new java.awt.Color(204, 51, 0));
        btn_cancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_cancelar.setRadius(15);
        btn_cancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_cancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_cancelarMouseExited(evt);
            }
        });
        btn_cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarActionPerformed(evt);
            }
        });
        panel_edit.add(btn_cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, 310, 40));

        btn_guardarCambios.setBackground(new java.awt.Color(45, 235, 201));
        btn_guardarCambios.setForeground(new java.awt.Color(0, 0, 0));
        btn_guardarCambios.setCursor(new Cursor(HAND_CURSOR));
        btn_guardarCambios.setText("GUARDAR CAMBIOS");
        btn_guardarCambios.setBorderColor(new java.awt.Color(35, 35, 35));
        btn_guardarCambios.setColor(new java.awt.Color(45, 235, 201));
        btn_guardarCambios.setColorClick(new java.awt.Color(28, 45, 120));
        btn_guardarCambios.setColorOver(new java.awt.Color(42, 68, 181));
        btn_guardarCambios.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_guardarCambios.setRadius(15);
        btn_guardarCambios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_guardarCambiosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_guardarCambiosMouseExited(evt);
            }
        });
        btn_guardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarCambiosActionPerformed(evt);
            }
        });
        panel_edit.add(btn_guardarCambios, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 445, 310, 40));

        jLabel22.setBackground(new java.awt.Color(204, 204, 204));
        jLabel22.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/registro-de-llamadas (2).png"))); // NOI18N
        panel_edit.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 310, 120));

        jLabel23.setBackground(new java.awt.Color(204, 204, 204));
        jLabel23.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(255, 255, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("Editar Contacto");
        panel_edit.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 310, 40));

        jLabel24.setBackground(new java.awt.Color(153, 153, 153));
        jLabel24.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(255, 255, 255));
        jLabel24.setText("Número de Telefono:");
        panel_edit.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 345, 210, 20));

        jLabel25.setBackground(new java.awt.Color(153, 153, 153));
        jLabel25.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(255, 255, 255));
        jLabel25.setText("Nombre del Contacto:");
        panel_edit.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 265, 200, 20));

        panel_editar.add(panel_edit, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 530, 580));

        card_panel.add(panel_editar, "card6");

        panel_añadir.setBackground(new java.awt.Color(35, 35, 35));
        panel_añadir.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_agregar.setBackground(new java.awt.Color(35, 35, 35));
        panel_agregar.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(45, 235, 201)));
        panel_agregar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_telefono.setBackground(new java.awt.Color(56, 56, 56));
        txt_telefono.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_telefono.setForeground(new java.awt.Color(204, 204, 204));
        txt_telefono.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_telefono.setBorder(null);
        txt_telefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_telefonoKeyTyped(evt);
            }
        });
        panel_agregar.add(txt_telefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 370, 310, 33));

        txt_nombre.setBackground(new java.awt.Color(56, 56, 56));
        txt_nombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        txt_nombre.setForeground(new java.awt.Color(204, 204, 204));
        txt_nombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nombre.setBorder(null);
        panel_agregar.add(txt_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 290, 310, 33));

        btn_guardar.setBackground(new java.awt.Color(45, 235, 201));
        btn_guardar.setForeground(new java.awt.Color(0, 0, 0));
        btn_guardar.setCursor(new Cursor(HAND_CURSOR));
        btn_guardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar_negro.png"))); // NOI18N
        btn_guardar.setText("   GUARDAR");
        btn_guardar.setBorderColor(new java.awt.Color(35, 35, 35));
        btn_guardar.setColor(new java.awt.Color(45, 235, 201));
        btn_guardar.setColorClick(new java.awt.Color(28, 45, 120));
        btn_guardar.setColorOver(new java.awt.Color(42, 68, 181));
        btn_guardar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        btn_guardar.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar_blanco.png"))); // NOI18N
        btn_guardar.setRadius(15);
        btn_guardar.setRolloverEnabled(true);
        btn_guardar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/guardar_blanco.png"))); // NOI18N
        btn_guardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_guardarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_guardarMouseExited(evt);
            }
        });
        btn_guardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_guardarActionPerformed(evt);
            }
        });
        panel_agregar.add(btn_guardar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 460, 310, 40));

        jLabel16.setBackground(new java.awt.Color(153, 153, 153));
        jLabel16.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Nombre del Contacto:");
        panel_agregar.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 265, 200, 20));

        jLabel17.setBackground(new java.awt.Color(153, 153, 153));
        jLabel17.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Número de Telefono:");
        panel_agregar.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 345, 210, 20));

        jLabel18.setBackground(new java.awt.Color(204, 204, 204));
        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Registro de Contacto");
        panel_agregar.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 60, 310, 40));

        jLabel19.setBackground(new java.awt.Color(204, 204, 204));
        jLabel19.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/registro-de-llamadas (2).png"))); // NOI18N
        panel_agregar.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 310, 120));

        panel_añadir.add(panel_agregar, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 530, 580));

        card_panel.add(panel_añadir, "card5");

        panel_editarUsuario.setBackground(new java.awt.Color(35, 35, 35));
        panel_editarUsuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_editUser.setBackground(new java.awt.Color(27, 27, 27));
        panel_editUser.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(45, 235, 201)));
        panel_editUser.setRoundBottomLeft(15);
        panel_editUser.setRoundBottomRight(15);
        panel_editUser.setRoundTopLeft(15);
        panel_editUser.setRoundTopRight(15);
        panel_editUser.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        rSFotoSquareResize4.setBackground(new java.awt.Color(27, 27, 27));
        rSFotoSquareResize4.setBorder(null);
        rSFotoSquareResize4.setImagenDefault(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario (4).png"))); // NOI18N
        panel_editUser.add(rSFotoSquareResize4, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 210, 210));

        btn_Cancelar.setForeground(new java.awt.Color(0, 0, 0));
        btn_Cancelar.setCursor(new Cursor(HAND_CURSOR));
        btn_Cancelar.setText("CANCELAR");
        btn_Cancelar.setBorderColor(new java.awt.Color(27, 27, 27));
        btn_Cancelar.setColor(new java.awt.Color(45, 235, 201));
        btn_Cancelar.setColorClick(new java.awt.Color(255, 51, 0));
        btn_Cancelar.setColorOver(new java.awt.Color(204, 51, 0));
        btn_Cancelar.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_Cancelar.setRadius(15);
        btn_Cancelar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_CancelarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_CancelarMouseExited(evt);
            }
        });
        btn_Cancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_CancelarActionPerformed(evt);
            }
        });
        panel_editUser.add(btn_Cancelar, new org.netbeans.lib.awtextra.AbsoluteConstraints(457, 465, 230, 40));

        btn_GuardarCambios.setBackground(new java.awt.Color(45, 235, 201));
        btn_GuardarCambios.setForeground(new java.awt.Color(0, 0, 0));
        btn_GuardarCambios.setCursor(new Cursor(HAND_CURSOR));
        btn_GuardarCambios.setText("GUARDAR CAMBIOS");
        btn_GuardarCambios.setBorderColor(new java.awt.Color(27, 27, 27));
        btn_GuardarCambios.setColor(new java.awt.Color(45, 235, 201));
        btn_GuardarCambios.setColorClick(new java.awt.Color(28, 45, 120));
        btn_GuardarCambios.setColorOver(new java.awt.Color(42, 68, 181));
        btn_GuardarCambios.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_GuardarCambios.setRadius(15);
        btn_GuardarCambios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_GuardarCambiosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_GuardarCambiosMouseExited(evt);
            }
        });
        btn_GuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_GuardarCambiosActionPerformed(evt);
            }
        });
        panel_editUser.add(btn_GuardarCambios, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 465, 230, 40));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        panel_editUser.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 60, 10, 370));

        txt_editarNombreUsuario.setBackground(new java.awt.Color(56, 56, 56));
        txt_editarNombreUsuario.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_editarNombreUsuario.setForeground(new java.awt.Color(204, 204, 204));
        txt_editarNombreUsuario.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_editarNombreUsuario.setBorder(null);
        txt_editarNombreUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_editarNombreUsuarioKeyTyped(evt);
            }
        });
        panel_editUser.add(txt_editarNombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(402, 250, 288, 33));

        txt_editarNombre.setBackground(new java.awt.Color(56, 56, 56));
        txt_editarNombre.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_editarNombre.setForeground(new java.awt.Color(204, 204, 204));
        txt_editarNombre.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_editarNombre.setBorder(null);
        txt_editarNombre.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_editarNombreKeyTyped(evt);
            }
        });
        panel_editUser.add(txt_editarNombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(402, 170, 288, 33));

        txt_editarEmail.setBackground(new java.awt.Color(56, 56, 56));
        txt_editarEmail.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_editarEmail.setForeground(new java.awt.Color(204, 204, 204));
        txt_editarEmail.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_editarEmail.setBorder(null);
        txt_editarEmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_editarEmailKeyTyped(evt);
            }
        });
        panel_editUser.add(txt_editarEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(402, 330, 288, 33));

        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/email.png"))); // NOI18N
        jLabel41.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel_editUser.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 330, 33, 33));

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario.png"))); // NOI18N
        jLabel31.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel_editUser.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 251, 33, 33));

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/identificacion.png"))); // NOI18N
        panel_editUser.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, 33, 33));

        jLabel37.setBackground(new java.awt.Color(153, 153, 153));
        jLabel37.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(153, 153, 153));
        jLabel37.setText("Nombre de Usuario:");
        panel_editUser.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 220, 170, 20));

        jLabel40.setBackground(new java.awt.Color(153, 153, 153));
        jLabel40.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(153, 153, 153));
        jLabel40.setText("Nombre:");
        panel_editUser.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 140, 70, 20));

        jLabel33.setBackground(new java.awt.Color(153, 153, 153));
        jLabel33.setFont(new java.awt.Font("Segoe UI Semibold", 0, 16)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(153, 153, 153));
        jLabel33.setText("E-mail:");
        panel_editUser.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 90, 20));

        jLabel32.setBackground(new java.awt.Color(204, 204, 204));
        jLabel32.setFont(new java.awt.Font("Segoe UI", 1, 27)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(255, 255, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("Editar Perfil de Usuario");
        panel_editUser.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 70, 340, 40));

        panel_editarUsuario.add(panel_editUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 770, 560));

        card_panel.add(panel_editarUsuario, "card8");

        panel_usuario.setBackground(new java.awt.Color(35, 35, 35));
        panel_usuario.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_perfil.setBackground(new java.awt.Color(27, 27, 27));
        panel_perfil.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(45, 235, 201)));
        panel_perfil.setRoundBottomLeft(15);
        panel_perfil.setRoundBottomRight(15);
        panel_perfil.setRoundTopLeft(15);
        panel_perfil.setRoundTopRight(15);
        panel_perfil.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        panel_perfil.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(325, 60, 10, 370));

        rSFotoSquareResize3.setBackground(new java.awt.Color(27, 27, 27));
        rSFotoSquareResize3.setBorder(null);
        rSFotoSquareResize3.setImagenDefault(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario (4).png"))); // NOI18N
        panel_perfil.add(rSFotoSquareResize3, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 210, 210));

        btn_cerrarSesion.setForeground(new java.awt.Color(0, 0, 0));
        btn_cerrarSesion.setCursor(new Cursor(HAND_CURSOR));
        btn_cerrarSesion.setText("CERRAR SESIÓN");
        btn_cerrarSesion.setBorderColor(new java.awt.Color(27, 27, 27));
        btn_cerrarSesion.setColor(new java.awt.Color(45, 235, 201));
        btn_cerrarSesion.setColorClick(new java.awt.Color(255, 51, 0));
        btn_cerrarSesion.setColorOver(new java.awt.Color(204, 51, 0));
        btn_cerrarSesion.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_cerrarSesion.setRadius(15);
        btn_cerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_cerrarSesionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_cerrarSesionMouseExited(evt);
            }
        });
        btn_cerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cerrarSesionActionPerformed(evt);
            }
        });
        panel_perfil.add(btn_cerrarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(457, 465, 230, 40));

        btn_editarUsuario.setBackground(new java.awt.Color(45, 235, 201));
        btn_editarUsuario.setForeground(new java.awt.Color(0, 0, 0));
        btn_editarUsuario.setCursor(new Cursor(HAND_CURSOR));
        btn_editarUsuario.setText("EDITAR");
        btn_editarUsuario.setBorderColor(new java.awt.Color(27, 27, 27));
        btn_editarUsuario.setColor(new java.awt.Color(45, 235, 201));
        btn_editarUsuario.setColorClick(new java.awt.Color(28, 45, 120));
        btn_editarUsuario.setColorOver(new java.awt.Color(42, 68, 181));
        btn_editarUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_editarUsuario.setRadius(15);
        btn_editarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_editarUsuarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_editarUsuarioMouseExited(evt);
            }
        });
        btn_editarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarUsuarioActionPerformed(evt);
            }
        });
        panel_perfil.add(btn_editarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 465, 230, 40));

        lbl_cambiarContraseña.setBackground(new java.awt.Color(153, 153, 153));
        lbl_cambiarContraseña.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_cambiarContraseña.setForeground(new java.awt.Color(255, 255, 255));
        lbl_cambiarContraseña.setText("Cambiar contraseña");
        lbl_cambiarContraseña.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_cambiarContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_cambiarContraseñaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_cambiarContraseñaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_cambiarContraseñaMouseExited(evt);
            }
        });
        panel_perfil.add(lbl_cambiarContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 375, 170, 20));

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/email.png"))); // NOI18N
        jLabel9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel_perfil.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 325, 33, 33));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/usuario.png"))); // NOI18N
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel_perfil.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 255, 33, 33));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/identificacion.png"))); // NOI18N
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panel_perfil.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 184, 33, 33));

        lbl_contactos.setBackground(new java.awt.Color(153, 153, 153));
        lbl_contactos.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_contactos.setForeground(new java.awt.Color(255, 255, 255));
        lbl_contactos.setText("Contactos:");
        lbl_contactos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_contactos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_contactosMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_contactosMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_contactosMouseExited(evt);
            }
        });
        panel_perfil.add(lbl_contactos, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 350, 90, 20));

        label_email.setBackground(new java.awt.Color(153, 153, 153));
        label_email.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        label_email.setForeground(new java.awt.Color(255, 255, 255));
        label_email.setText("E-mail");
        panel_perfil.add(label_email, new org.netbeans.lib.awtextra.AbsoluteConstraints(402, 323, 290, 30));

        label_nombreUsuario.setBackground(new java.awt.Color(153, 153, 153));
        label_nombreUsuario.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        label_nombreUsuario.setForeground(new java.awt.Color(255, 255, 255));
        label_nombreUsuario.setText("Nombre de usuario");
        panel_perfil.add(label_nombreUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(402, 253, 290, 30));

        jLabel34.setBackground(new java.awt.Color(153, 153, 153));
        jLabel34.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(153, 153, 153));
        jLabel34.setText("E-mail:");
        panel_perfil.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, 70, 20));

        jLabel42.setBackground(new java.awt.Color(153, 153, 153));
        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(153, 153, 153));
        jLabel42.setText("Nombre de Usuario:");
        panel_perfil.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 230, 180, 20));

        lbl_eliminar.setBackground(new java.awt.Color(153, 153, 153));
        lbl_eliminar.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        lbl_eliminar.setForeground(new java.awt.Color(255, 255, 255));
        lbl_eliminar.setText("Eliminar cuenta");
        lbl_eliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lbl_eliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lbl_eliminarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lbl_eliminarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lbl_eliminarMouseExited(evt);
            }
        });
        panel_perfil.add(lbl_eliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 400, 170, 20));

        jLabel38.setBackground(new java.awt.Color(153, 153, 153));
        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(153, 153, 153));
        jLabel38.setText("Nombre:");
        panel_perfil.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 160, -1, 20));

        label_nombre.setBackground(new java.awt.Color(153, 153, 153));
        label_nombre.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        label_nombre.setForeground(new java.awt.Color(255, 255, 255));
        label_nombre.setText("Nombre");
        panel_perfil.add(label_nombre, new org.netbeans.lib.awtextra.AbsoluteConstraints(402, 183, 290, 30));

        label_cantContactos.setBackground(new java.awt.Color(10, 85, 21));
        label_cantContactos.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        label_cantContactos.setForeground(new java.awt.Color(255, 255, 255));
        label_cantContactos.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        label_cantContactos.setText("20");
        label_cantContactos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        label_cantContactos.setOpaque(true);
        panel_perfil.add(label_cantContactos, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 345, 40, 30));

        jLabel30.setBackground(new java.awt.Color(204, 204, 204));
        jLabel30.setFont(new java.awt.Font("Segoe UI", 1, 27)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setText("Perfil de Usuario");
        panel_perfil.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 330, 40));

        panel_usuario.add(panel_perfil, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 20, 770, 560));

        card_panel.add(panel_usuario, "card7");

        panel_cambiarContraseña.setBackground(new java.awt.Color(35, 35, 35));
        panel_cambiarContraseña.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panel_cambiarPassword.setBackground(new java.awt.Color(35, 35, 35));
        panel_cambiarPassword.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(45, 235, 201)));
        panel_cambiarPassword.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_actualizarContraseña.setBackground(new java.awt.Color(45, 235, 201));
        btn_actualizarContraseña.setForeground(new java.awt.Color(0, 0, 0));
        btn_actualizarContraseña.setCursor(new Cursor(HAND_CURSOR));
        btn_actualizarContraseña.setText("ACTUALIZAR CONTRASEÑA");
        btn_actualizarContraseña.setBorderColor(new java.awt.Color(35, 35, 35));
        btn_actualizarContraseña.setColor(new java.awt.Color(45, 235, 201));
        btn_actualizarContraseña.setColorClick(new java.awt.Color(28, 45, 120));
        btn_actualizarContraseña.setColorOver(new java.awt.Color(42, 68, 181));
        btn_actualizarContraseña.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_actualizarContraseña.setRadius(15);
        btn_actualizarContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_actualizarContraseñaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_actualizarContraseñaMouseExited(evt);
            }
        });
        btn_actualizarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_actualizarContraseñaActionPerformed(evt);
            }
        });
        panel_cambiarPassword.add(btn_actualizarContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 445, 310, 40));

        btn_cancelarContraseña.setForeground(new java.awt.Color(0, 0, 0));
        btn_cancelarContraseña.setCursor(new Cursor(HAND_CURSOR));
        btn_cancelarContraseña.setText("CANCELAR");
        btn_cancelarContraseña.setBorderColor(new java.awt.Color(35, 35, 35));
        btn_cancelarContraseña.setColor(new java.awt.Color(45, 235, 201));
        btn_cancelarContraseña.setColorClick(new java.awt.Color(255, 51, 0));
        btn_cancelarContraseña.setColorOver(new java.awt.Color(204, 51, 0));
        btn_cancelarContraseña.setFont(new java.awt.Font("Segoe UI Semibold", 0, 17)); // NOI18N
        btn_cancelarContraseña.setRadius(15);
        btn_cancelarContraseña.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_cancelarContraseñaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_cancelarContraseñaMouseExited(evt);
            }
        });
        btn_cancelarContraseña.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_cancelarContraseñaActionPerformed(evt);
            }
        });
        panel_cambiarPassword.add(btn_cancelarContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 490, 310, 40));

        jLabel46.setBackground(new java.awt.Color(153, 153, 153));
        jLabel46.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(255, 255, 255));
        jLabel46.setText("Confirmar Contraseña:");
        panel_cambiarPassword.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 350, 210, 20));

        jLabel47.setBackground(new java.awt.Color(204, 204, 204));
        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(255, 255, 255));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/actualizar.png"))); // NOI18N
        panel_cambiarPassword.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, 310, -1));

        jLabel45.setBackground(new java.awt.Color(153, 153, 153));
        jLabel45.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(255, 255, 255));
        jLabel45.setText("Contraseña Actual:");
        panel_cambiarPassword.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 227, 200, -1));

        jLabel44.setBackground(new java.awt.Color(153, 153, 153));
        jLabel44.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel44.setForeground(new java.awt.Color(255, 255, 255));
        jLabel44.setText("Nueva Contraseña:");
        panel_cambiarPassword.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(112, 290, 210, 20));

        jLabel43.setBackground(new java.awt.Color(204, 204, 204));
        jLabel43.setFont(new java.awt.Font("Segoe UI", 1, 30)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(255, 255, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("Cambiar Contraseña");
        panel_cambiarPassword.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 45, 310, 40));

        jLabel39.setBackground(new java.awt.Color(204, 204, 204));
        jLabel39.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/locked1.png"))); // NOI18N
        panel_cambiarPassword.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 120, 310, -1));

        contraseña_antigua.setBackground(new java.awt.Color(56, 56, 56));
        contraseña_antigua.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_antiguaContraseña.setBackground(new java.awt.Color(56, 56, 56));
        txt_antiguaContraseña.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_antiguaContraseña.setForeground(new java.awt.Color(204, 204, 204));
        txt_antiguaContraseña.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_antiguaContraseña.setBorder(null);
        txt_antiguaContraseña.setEchoChar('\u25cf');
        contraseña_antigua.add(txt_antiguaContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 273, 33));

        btn_ver1.setBorder(null);
        btn_ver1.setCursor(new Cursor(HAND_CURSOR));
        btn_ver1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ojo.png"))); // NOI18N
        btn_ver1.setBorderColor(new java.awt.Color(56, 56, 56));
        btn_ver1.setBorderPainted(false);
        btn_ver1.setColor(new java.awt.Color(56, 56, 56));
        btn_ver1.setColorClick(new java.awt.Color(56, 56, 56));
        btn_ver1.setColorOver(new java.awt.Color(56, 56, 56));
        btn_ver1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver1ActionPerformed(evt);
            }
        });
        contraseña_antigua.add(btn_ver1, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 0, 35, 33));

        panel_cambiarPassword.add(contraseña_antigua, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 255, 310, 33));

        nueva_contraseña.setBackground(new java.awt.Color(56, 56, 56));
        nueva_contraseña.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_nuevaContraseña.setBackground(new java.awt.Color(56, 56, 56));
        txt_nuevaContraseña.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_nuevaContraseña.setForeground(new java.awt.Color(204, 204, 204));
        txt_nuevaContraseña.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_nuevaContraseña.setBorder(null);
        txt_nuevaContraseña.setEchoChar('\u25cf');
        txt_nuevaContraseña.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_nuevaContraseñaKeyTyped(evt);
            }
        });
        nueva_contraseña.add(txt_nuevaContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 273, 33));

        btn_ver2.setBorder(null);
        btn_ver2.setCursor(new Cursor(HAND_CURSOR));
        btn_ver2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ojo.png"))); // NOI18N
        btn_ver2.setBorderColor(new java.awt.Color(56, 56, 56));
        btn_ver2.setBorderPainted(false);
        btn_ver2.setColor(new java.awt.Color(56, 56, 56));
        btn_ver2.setColorClick(new java.awt.Color(56, 56, 56));
        btn_ver2.setColorOver(new java.awt.Color(56, 56, 56));
        btn_ver2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver2ActionPerformed(evt);
            }
        });
        nueva_contraseña.add(btn_ver2, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 0, 35, 33));

        panel_cambiarPassword.add(nueva_contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 314, 310, 33));

        confirmar_contraseña.setBackground(new java.awt.Color(56, 56, 56));
        confirmar_contraseña.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_confirmarContraseña.setBackground(new java.awt.Color(56, 56, 56));
        txt_confirmarContraseña.setFont(new java.awt.Font("Segoe UI", 0, 17)); // NOI18N
        txt_confirmarContraseña.setForeground(new java.awt.Color(204, 204, 204));
        txt_confirmarContraseña.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_confirmarContraseña.setBorder(null);
        txt_confirmarContraseña.setEchoChar('\u25cf');
        confirmar_contraseña.add(txt_confirmarContraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 273, 33));

        btn_ver3.setBorder(null);
        btn_ver3.setCursor(new Cursor(HAND_CURSOR));
        btn_ver3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagenes/ojo.png"))); // NOI18N
        btn_ver3.setBorderColor(new java.awt.Color(56, 56, 56));
        btn_ver3.setBorderPainted(false);
        btn_ver3.setColor(new java.awt.Color(56, 56, 56));
        btn_ver3.setColorClick(new java.awt.Color(56, 56, 56));
        btn_ver3.setColorOver(new java.awt.Color(56, 56, 56));
        btn_ver3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ver3ActionPerformed(evt);
            }
        });
        confirmar_contraseña.add(btn_ver3, new org.netbeans.lib.awtextra.AbsoluteConstraints(275, 0, 35, 33));

        panel_cambiarPassword.add(confirmar_contraseña, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 374, 310, 33));

        panel_cambiarContraseña.add(panel_cambiarPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 530, 580));

        card_panel.add(panel_cambiarContraseña, "card10");

        panelRound1.add(card_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 50, 910, 640));

        getContentPane().add(panelRound1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1220, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void panelRound1MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound1MousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_panelRound1MousePressed

    private void panelRound1MouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_panelRound1MouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_panelRound1MouseDragged

    private void btn_ver3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver3ActionPerformed
        mostrarContraseña(btn_ver3, txt_confirmarContraseña);
    }//GEN-LAST:event_btn_ver3ActionPerformed

    private void btn_ver2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver2ActionPerformed
        mostrarContraseña(btn_ver2, txt_nuevaContraseña);
    }//GEN-LAST:event_btn_ver2ActionPerformed

    private void btn_ver1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ver1ActionPerformed
        mostrarContraseña(btn_ver1, txt_antiguaContraseña);
    }//GEN-LAST:event_btn_ver1ActionPerformed

    private void txt_nuevaContraseñaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nuevaContraseñaKeyTyped
        char caracter = evt.getKeyChar();
        if (caracter == ' ') {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_nuevaContraseñaKeyTyped

    private void btn_actualizarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_actualizarContraseñaActionPerformed
        if (!txt_antiguaContraseña.getText().equals("")
                && !txt_nuevaContraseña.getText().equals("")
                && !txt_confirmarContraseña.getText().equals("")) {

            char[] contraseña = txt_antiguaContraseña.getPassword();
            String antiguaContraseña = new String(contraseña);
            contraseña = txt_nuevaContraseña.getPassword();
            String nuevaContraseña = new String(contraseña);
            contraseña = txt_confirmarContraseña.getPassword();
            String confirmarContraseña = new String(contraseña);

            if (usuarioActual[1].equals(antiguaContraseña)
                    && !usuarioActual[1].equals(nuevaContraseña)
                    && nuevaContraseña.equals(confirmarContraseña)
                    && !usuarios.existe(nuevaContraseña)) {

                try {
                    usuarios.eliminar(usuarioActual[1]);
                    usuarioActual[1] = nuevaContraseña;

                    Usuario usuario = new Usuario();
                    limpiarCamposCambioContraseña();
                    usuario.editarUsuario(usuarioActual, true);
                    usuarios.insertar(usuarioActual[1], usuarioActual[0]);

                    System.out.println("Usuarios: " + usuarios.recorridoPorNiveles());
                    mostrarPanel(panel_usuario);

                    String mensaje = "Cambio de contraseña realizado con éxito";
                    mostrarCuadroDialogo("SUCCESS", mensaje, 2);
                } catch (ClaveNoExisteException ex) {
                    String titulo = "ALERTA DE ERROR";
                    String mensaje = "No se logro cambiar la contraseña";
                    mostrarCuadroDialogo(titulo, mensaje, 1);
                }
            } else {
                if (!usuarioActual[1].equals(antiguaContraseña)) {
                    String titulo = "CONTRASEÑA INCORRECTA";
                    String mensaje = "Verifique que su contraseña este bien escrita";
                    mostrarCuadroDialogo(titulo, mensaje, 1);
                } else if (usuarioActual[1].equals(nuevaContraseña)) {
                    String titulo = "CONTRASEÑA IDÉNTICA";
                    String mensaje = "<html><center>La nueva contraseña no puede ser la misma,<p> "
                            + "escriba una nueva contraseña</center></html>";
                    mostrarCuadroDialogo(titulo, mensaje, 1);
                } else if (!nuevaContraseña.equals(confirmarContraseña)) {
                    String titulo = "CONFIRMACIÓN INCORRECTA";
                    String mensaje = "<html><center>Verifique que la contraseña de confirmación<p> "
                            + "sea igual a la nueva contraseña</center></html>";
                    mostrarCuadroDialogo(titulo, mensaje, 1);
                } else {
                    String titulo = "CONTRASEÑA NO VÁLIDA";
                    String mensaje = "<html><center>La contraseña introducida no es válida,<p>"
                            + "introduzca una nueva contraseña</center></html>";
                    mostrarCuadroDialogo(titulo, mensaje, 1);
                }
            }
        } else {
            String titulo = "DATOS NO VÁLIDOS";
            String mensaje = "Complete todos los campos vacíos";
            mostrarCuadroDialogo(titulo, mensaje, 1);
        }
    }//GEN-LAST:event_btn_actualizarContraseñaActionPerformed

    private void btn_cancelarContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarContraseñaActionPerformed
        limpiarCamposCambioContraseña();
        mostrarPanel(panel_usuario);
    }//GEN-LAST:event_btn_cancelarContraseñaActionPerformed

    private void btn_cancelarContraseñaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cancelarContraseñaMouseExited
        btn_cancelarContraseña.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_cancelarContraseñaMouseExited

    private void btn_cancelarContraseñaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cancelarContraseñaMouseEntered
        btn_cancelarContraseña.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_cancelarContraseñaMouseEntered

    private void lbl_cambiarContraseñaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cambiarContraseñaMouseExited
        lbl_cambiarContraseña.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lbl_cambiarContraseñaMouseExited

    private void lbl_cambiarContraseñaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cambiarContraseñaMouseEntered
        lbl_cambiarContraseña.setForeground(new Color(53, 171, 255));
    }//GEN-LAST:event_lbl_cambiarContraseñaMouseEntered

    private void lbl_cambiarContraseñaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_cambiarContraseñaMouseClicked
        mostrarPanel(panel_cambiarContraseña);
    }//GEN-LAST:event_lbl_cambiarContraseñaMouseClicked

    private void lbl_contactosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_contactosMouseExited
        lbl_contactos.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lbl_contactosMouseExited

    private void lbl_contactosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_contactosMouseEntered
        lbl_contactos.setForeground(new Color(53, 171, 255));
    }//GEN-LAST:event_lbl_contactosMouseEntered

    private void lbl_contactosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_contactosMouseClicked
        mostrarPanel(panel_contactos);
    }//GEN-LAST:event_lbl_contactosMouseClicked

    private void btn_cerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarSesionActionPerformed
        mostrarInicioSesion();
        System.out.println("Usuarios: " + usuarios.recorridoPorNiveles());
    }//GEN-LAST:event_btn_cerrarSesionActionPerformed

    private void btn_cerrarSesionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cerrarSesionMouseExited
        btn_cerrarSesion.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_cerrarSesionMouseExited

    private void btn_cerrarSesionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cerrarSesionMouseEntered
        btn_cerrarSesion.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_cerrarSesionMouseEntered

    private void lbl_eliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_eliminarMouseExited
        lbl_eliminar.setForeground(new Color(255, 255, 255));
    }//GEN-LAST:event_lbl_eliminarMouseExited

    private void lbl_eliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_eliminarMouseEntered
        lbl_eliminar.setForeground(new Color(53, 171, 255));
    }//GEN-LAST:event_lbl_eliminarMouseEntered

    private void lbl_eliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lbl_eliminarMouseClicked
        String titulo = "ELIMINAR CUENTA DE USUARIO";
        String mensaje = "<html><center>¿Estás seguro de eliminar tu cuenta de usuario?"
                + "<p>Se eliminarán todos tus contactos</center></html>";
        mostrarCuadroDialogo(titulo, mensaje, 3);

        if (AlertWarning.YES_OPTION) {
            try {
                Usuario usuario = new Usuario();
                usuario.eliminarUsuario(usuarioActual[0]);
                usuarios.eliminar(usuarioActual[1]);

                System.out.println("Usuarios: " + usuarios.recorridoPorNiveles());
                mostrarInicioSesion();

                mensaje = "Usuario eliminado con éxito";
                mostrarCuadroDialogo("SUCCESS", mensaje, 2);
            } catch (ClaveNoExisteException ex) {
                titulo = "ALERTA DE ERROR";
                mensaje = "No se logro eliminar el usuario";
                mostrarCuadroDialogo(titulo, mensaje, 1);
            }
        }
    }//GEN-LAST:event_lbl_eliminarMouseClicked

    private void btn_editarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarUsuarioActionPerformed
        txt_editarNombre.setText(label_nombre.getText());
        txt_editarNombreUsuario.setText(label_nombreUsuario.getText());
        txt_editarEmail.setText(label_email.getText());
        mostrarPanel(panel_editarUsuario);
    }//GEN-LAST:event_btn_editarUsuarioActionPerformed

    private void btn_editarUsuarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_editarUsuarioMouseExited
        btn_editarUsuario.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_editarUsuarioMouseExited

    private void btn_editarUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_editarUsuarioMouseEntered
        btn_editarUsuario.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_editarUsuarioMouseEntered

    private void txt_telefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefonoKeyTyped
        char caracter = evt.getKeyChar();
        if (Character.isLetter(caracter)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_telefonoKeyTyped

    private void btn_guardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarActionPerformed
        if (!txt_nombre.getText().isEmpty() && !txt_telefono.getText().isEmpty()) {

            String nombre = txt_nombre.getText().trim();
            Integer telefono = Integer.parseInt(txt_telefono.getText().trim());

            Contacto contacto = new Contacto(nombre, telefono);

            if (!contactos.existe(contacto.getTelefono())) {
                contactos.insertar(contacto.getTelefono(), contacto.getNombre());
                contacto.agregarContacto(usuarioActual[0]);

                System.out.println("Contactos: " + contactos.recorridoPorNiveles());

                int cant = Integer.parseInt(label_cantContactos.getText());
                label_cantContactos.setText(String.valueOf(cant + 1));
                limpiar(txt_nombre, txt_telefono);

                String mensaje = "Contacto guardado con éxito";
                mostrarCuadroDialogo("SUCCESS", mensaje, 2);
            } else {
                String titulo = "Oops...";
                String mensaje = "Ya existe un contacto con el mismo número";
                mostrarCuadroDialogo(titulo, mensaje, 1);
            }
        } else {
            String titulo = "DATOS NO VÁLIDOS";
            String mensaje = "Complete todos los campos vacíos";
            mostrarCuadroDialogo(titulo, mensaje, 1);
        }
    }//GEN-LAST:event_btn_guardarActionPerformed

    private void btn_guardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_guardarCambiosActionPerformed
        if (!txt_nombre1.getText().isEmpty() && !txt_telefono1.getText().isEmpty()) {

            String nombre = txt_nombre1.getText().trim();
            Integer telefono = Integer.parseInt(txt_telefono1.getText());
            boolean editar = true;

            if (contactos.existe(telefono)) {
                if (telefono == Integer.parseInt(contactoEditar[1])) {
                    contactos.insertar(telefono, nombre);
                } else {
                    editar = false;
                }
            } else {
                try {
                    contactos.eliminar(Integer.parseInt(contactoEditar[1]));
                    contactos.insertar(telefono, nombre);
                } catch (ClaveNoExisteException ex) {
                    System.out.println("Error::guardarCambios:no se pudo eliminar el contacto");
                }
            }

            if (editar) {
                System.out.println("Contactos: " + contactos.recorridoPorNiveles());

                int row = tabla.getSelectedRow();
                tabla.setValueAt(nombre, row, 0);
                tabla.setValueAt(String.valueOf(telefono), row, 1);

                Contacto contacto = new Contacto();
                contacto.editarContacto(tabla, usuarioActual[0]);

                limpiar(txt_nombre1, txt_telefono1);
                mostrarPanel(panel_contactos);

                String mensaje = "Contacto editado con éxito";
                mostrarCuadroDialogo("SUCCESS", mensaje, 2);
            } else {
                String titulo = "Oops...";
                String mensaje = "Ya existe un contacto con el mismo número";
                mostrarCuadroDialogo(titulo, mensaje, 1);
            }
        } else {
            String titulo = "DATOS NO VÁLIDOS";
            String mensaje = "Complete todos los campos vacíos";
            mostrarCuadroDialogo(titulo, mensaje, 1);
        }
    }//GEN-LAST:event_btn_guardarCambiosActionPerformed

    private void txt_telefono1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefono1KeyTyped
        char caracter = evt.getKeyChar();
        if (Character.isLetter(caracter)) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_telefono1KeyTyped

    private void btn_cancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cancelarActionPerformed
        mostrarPanel(panel_contactos);
        limpiar(txt_nombre1, txt_telefono1);
    }//GEN-LAST:event_btn_cancelarActionPerformed

    private void btn_cancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cancelarMouseExited
        btn_cancelar.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_cancelarMouseExited

    private void btn_cancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_cancelarMouseEntered
        btn_cancelar.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_cancelarMouseEntered

    private void btn_eliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_eliminarActionPerformed
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            String titulo = "ELIMINAR CONTACTO";
            String mensaje = "<html><center>¿Estás seguro de eliminar el contacto?"
                    + "<p>Se quitará este contacto de tu lista de contactos</center></html>";
            mostrarCuadroDialogo(titulo, mensaje, 3);

            if (AlertWarning.YES_OPTION) {

                Integer telefono = Integer.parseInt(tabla.getValueAt(fila, 1).toString());

                try {
                    Contacto contacto = new Contacto();
                    contacto.eliminarContacto(tabla, telefono, usuarioActual[0]);
                    contactos.eliminar(telefono);

                    mensaje = "Contacto eliminado con éxito";
                    mostrarCuadroDialogo("SUCCESS", mensaje, 2);

                    System.out.println("Contactos: " + contactos.recorridoPorNiveles());

                    int cant = Integer.parseInt(label_cantContactos.getText());
                    label_cantContactos.setText(String.valueOf(cant - 1));
                } catch (ClaveNoExisteException ex) {
                    titulo = "ALERTA DE ERROR";
                    mensaje = "No se logro eliminar el contacto";
                    mostrarCuadroDialogo(titulo, mensaje, 1);
                }
            }
        } else {
            String titulo = "MENSAJE";
            String mensaje = "Seleccione el contacto a eliminar";
            mostrarCuadroDialogo(titulo, mensaje, 5);
        }
    }//GEN-LAST:event_btn_eliminarActionPerformed

    private void btn_eliminarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_eliminarMouseExited
        btn_eliminar.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_eliminarMouseExited

    private void btn_eliminarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_eliminarMouseEntered
        btn_eliminar.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_eliminarMouseEntered

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {

            String nombre = tabla.getValueAt(fila, 0).toString();
            String telefono = tabla.getValueAt(fila, 1).toString();

            contactoEditar = new String[]{nombre, telefono};

            txt_nombre1.setText(nombre);
            txt_telefono1.setText(String.valueOf(telefono));

            mostrarPanel(panel_editar);
        } else {
            String titulo = "MENSAJE";
            String mensaje = "Seleccione el contacto a editar";
            mostrarCuadroDialogo(titulo, mensaje, 5);
        }
    }//GEN-LAST:event_btn_editarActionPerformed

    private void btn_editarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_editarMouseExited
        btn_editar.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_editarMouseExited

    private void btn_editarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_editarMouseEntered
        btn_editar.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_editarMouseEntered

    private void btn_buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_buscarActionPerformed
        if (!txt_busqueda.getText().isEmpty()) {
            sorter.setRowFilter(RowFilter.regexFilter(txt_busqueda.getText()));
        }
    }//GEN-LAST:event_btn_buscarActionPerformed

    private void txt_busquedaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_busquedaKeyReleased
        if (usuarioActual != null) {
            sorter.setRowFilter(RowFilter.regexFilter(txt_busqueda.getText()));
        }
    }//GEN-LAST:event_txt_busquedaKeyReleased

    private void btn_verContraseña1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_verContraseña1ActionPerformed
        mostrarContraseña(btn_verContraseña1, txt_password1);
    }//GEN-LAST:event_btn_verContraseña1ActionPerformed

    private void txt_emailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_emailKeyTyped
        char caracter = evt.getKeyChar();
        if (caracter == ' ' || txt_email.getText().length() >= 30) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_emailKeyTyped

    private void txt_nombreRegKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_nombreRegKeyTyped
        if (txt_nombreReg.getText().length() >= 30) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_nombreRegKeyTyped

    private void label_regresarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_regresarMouseExited
        label_regresar.setForeground(new Color(153, 153, 153));
    }//GEN-LAST:event_label_regresarMouseExited

    private void label_regresarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_regresarMouseEntered
        label_regresar.setForeground(Color.green);
    }//GEN-LAST:event_label_regresarMouseEntered

    private void label_regresarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_regresarMouseClicked
        mostrarPanel(panel_inicioSesion);
        limpiar(txt_usuario1, txt_password1);
        limpiar(txt_nombreReg, txt_email);
        limpiarCampoDeContraseña(btn_verContraseña, txt_password);
        limpiarCampoDeContraseña(btn_verContraseña1, txt_password1);
    }//GEN-LAST:event_label_regresarMouseClicked

    private void txt_password1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_password1KeyTyped
        char caracter = evt.getKeyChar();
        if (caracter == ' ') {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_password1KeyTyped

    private void txt_usuario1KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_usuario1KeyTyped
        char caracter = evt.getKeyChar();
        if (caracter == ' ' || txt_usuario1.getText().length() == 12) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_usuario1KeyTyped

    private void btn_registrarseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_registrarseActionPerformed
        if (!txt_usuario1.getText().isEmpty() && !txt_password1.getText().equals("")
                && !txt_nombreReg.getText().isEmpty() && !txt_email.getText().isEmpty()) {

            String user = txt_usuario1.getText().trim();
            char[] arrayC = txt_password1.getPassword();
            String password = new String(arrayC);
            String nombre = txt_nombreReg.getText().trim();
            String email = txt_email.getText().trim();

            Usuario usuario = new Usuario(user, password, nombre, email);

            if (!usuarios.existe(password) && !existeUsuario(user)) {
                usuarios.insertar(usuario.getPassword(), usuario.getUser());
                usuario.crearArchivoUsuario();

                limpiar(txt_usuario1, txt_password1);
                limpiar(txt_nombreReg, txt_email);
                usuario.agregarUsuario();

                Contacto contacto = new Contacto();
                contacto.crearArchivoContacto(user);

                System.out.println("Usuarios: " + usuarios.recorridoPorNiveles());
                mostrarPanel(panel_inicioSesion);

                String mensaje = "Usuario registrado con éxito";
                mostrarCuadroDialogo("SUCCESS", mensaje, 2);
            } else {
                String titulo = "Oops...";
                String mensaje = "<html><center>El nombre de usuario ya existe<p> "
                        + "o la contraseña no es válida</center></html>";
                mostrarCuadroDialogo(titulo, mensaje, 1);
            }
        } else {
            String titulo = "DATOS NO VÁLIDOS";
            String mensaje = "Complete todos los campos vacíos";
            mostrarCuadroDialogo(titulo, mensaje, 1);
        }
    }//GEN-LAST:event_btn_registrarseActionPerformed

    private void btn_verContraseñaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_verContraseñaActionPerformed
        mostrarContraseña(btn_verContraseña, txt_password);
    }//GEN-LAST:event_btn_verContraseñaActionPerformed

    private void label_crearMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_crearMouseExited
        label_crear.setForeground(new Color(153, 153, 153));
    }//GEN-LAST:event_label_crearMouseExited

    private void label_crearMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_crearMouseEntered
        label_crear.setForeground(Color.green);
    }//GEN-LAST:event_label_crearMouseEntered

    private void label_crearMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_label_crearMouseClicked
        mostrarPanel(panel_registro);
        limpiar(txt_usuario, txt_password);
        limpiarCampoDeContraseña(btn_verContraseña, txt_password);
        limpiarCampoDeContraseña(btn_verContraseña1, txt_password1);
    }//GEN-LAST:event_label_crearMouseClicked

    private void btn_ingresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ingresarActionPerformed
        if (!txt_usuario.getText().isEmpty() && !txt_password.getText().equals("")) {

            String user = txt_usuario.getText().trim();
            char[] arrayC = txt_password.getPassword();
            String password = new String(arrayC);

            if (usuarios.existe(password) && usuarios.buscar(password).equals(user)) {

                switch (tipoArbol) {
                    case "ABB" ->
                        this.contactos = new ArbolBinario<>();
                    case "AVL" ->
                        this.contactos = new AVL<>();
                    case "AMV" -> {
                        try {
                            this.contactos = new ArbolMVias<>(4);
                        } catch (ExcepcionOrdenInvalido ex) {
                            System.out.println(ex.toString());
                        }
                    }
                    default -> {
                        System.out.println("Tipo de árbol inválido, eligiendo ArbolBinario");
                        this.contactos = new ArbolBinario<>();
                    }
                }

                cargarDatos(user);

                usuarioActual = new String[]{user, password};
                llenarDatosUsuario(user, password);
                mostrarPanel(panel_usuario);

                System.out.println("Contactos: " + contactos.recorridoPorNiveles());
            } else {
                String titulo = "Oops...";
                String mensaje = "<html><center>El nombre de usuario o contraseña<p> "
                        + "que ingresó no son válidos</center></html>";
                mostrarCuadroDialogo(titulo, mensaje, 1);
            }
        } else {
            String titulo = "DATOS NO VÁLIDOS";
            String mensaje = "Complete todos los campos vacíos";
            mostrarCuadroDialogo(titulo, mensaje, 1);
        }
    }//GEN-LAST:event_btn_ingresarActionPerformed

    private void btn_salirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salirActionPerformed
        String titulo = "¿Estás seguro de salir?";
        mostrarCuadroDialogo(titulo, "", 4);
    }//GEN-LAST:event_btn_salirActionPerformed

    private void btn_salirMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salirMouseExited
        btn_salir.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_salirMouseExited

    private void btn_salirMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salirMouseEntered
        btn_salir.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_salirMouseEntered

    private void btn_contactosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_contactosActionPerformed
        mostrarPanel(panel_contactos);
        if (usuarioActual != null) {
            cargarDatos(usuarioActual[0]);
        }
        txt_busqueda.setText("");
        limpiar(txt_nombre, txt_telefono);
    }//GEN-LAST:event_btn_contactosActionPerformed

    private void btn_usuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_usuarioActionPerformed
        if (usuarioActual != null) {
            mostrarPanel(panel_usuario);
        } else {
            if (panel_registro.isVisible()) {
                mostrarPanel(panel_registro);
            } else {
                mostrarPanel(panel_inicioSesion);
            }
        }
        limpiar(txt_nombre, txt_telefono);
    }//GEN-LAST:event_btn_usuarioActionPerformed

    private void btn_añadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_añadirActionPerformed
        mostrarPanel(panel_añadir);
    }//GEN-LAST:event_btn_añadirActionPerformed

    private void btn_cerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cerrarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btn_cerrarActionPerformed

    private void btn_minimizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_minimizarActionPerformed
        this.setExtendedState(1);
    }//GEN-LAST:event_btn_minimizarActionPerformed

    private void txt_editarNombreUsuarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_editarNombreUsuarioKeyTyped
        char caracter = evt.getKeyChar();
        if (caracter == ' ' || txt_editarNombreUsuario.getText().length() >= 12) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_editarNombreUsuarioKeyTyped

    private void txt_editarNombreKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_editarNombreKeyTyped
        if (txt_editarNombre.getText().length() >= 30) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_editarNombreKeyTyped

    private void txt_editarEmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_editarEmailKeyTyped
        char caracter = evt.getKeyChar();
        if (caracter == ' ' || txt_editarEmail.getText().length() >= 30) {
            getToolkit().beep();
            evt.consume();
        }
    }//GEN-LAST:event_txt_editarEmailKeyTyped

    private void btn_CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_CancelarActionPerformed
        mostrarPanel(panel_usuario);
    }//GEN-LAST:event_btn_CancelarActionPerformed

    private void btn_CancelarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CancelarMouseExited
        btn_Cancelar.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_CancelarMouseExited

    private void btn_CancelarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_CancelarMouseEntered
        btn_Cancelar.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_CancelarMouseEntered

    private void btn_GuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_GuardarCambiosActionPerformed
        if (!txt_editarNombre.getText().isEmpty() && !txt_editarNombreUsuario.getText().isEmpty()
                && !txt_editarEmail.getText().isEmpty()) {

            String nombre = txt_editarNombre.getText().trim();
            String nombreUsuario = txt_editarNombreUsuario.getText().trim();
            String email = txt_editarEmail.getText().trim();

            if (!existeUsuario(nombreUsuario) || usuarioActual[0].equals(nombreUsuario)) {
                Usuario usuario = new Usuario();
                usuario.editarUsuario(new String[]{usuarioActual[0], nombre, nombreUsuario, email}, false);

                usuarios.insertar(usuarioActual[1], nombreUsuario);
                usuarioActual[0] = nombreUsuario;
                llenarDatosUsuario(usuarioActual[0], usuarioActual[1]);

                System.out.println("Usuarios: " + usuarios.recorridoPorNiveles());
                mostrarPanel(panel_usuario);

                String mensaje = "<html><center>Cambios en el perfil de usuario<p>"
                        + "realizados con éxito</center></html>";
                mostrarCuadroDialogo("SUCCESS", mensaje, 2);
            } else {
                String titulo = "Oops...";
                String mensaje = "El nombre de usuario no es válido";
                mostrarCuadroDialogo(titulo, mensaje, 1);
            }
        } else {
            String titulo = "DATOS NO VÁLIDOS";
            String mensaje = "Complete todos los campos vacíos";
            mostrarCuadroDialogo(titulo, mensaje, 1);
        }
    }//GEN-LAST:event_btn_GuardarCambiosActionPerformed

    private void btn_guardarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_guardarMouseEntered
        btn_guardar.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_guardarMouseEntered

    private void btn_guardarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_guardarMouseExited
        btn_guardar.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_guardarMouseExited

    private void btn_GuardarCambiosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_GuardarCambiosMouseEntered
        btn_GuardarCambios.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_GuardarCambiosMouseEntered

    private void btn_GuardarCambiosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_GuardarCambiosMouseExited
        btn_GuardarCambios.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_GuardarCambiosMouseExited

    private void btn_guardarCambiosMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_guardarCambiosMouseEntered
        btn_guardarCambios.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_guardarCambiosMouseEntered

    private void btn_guardarCambiosMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_guardarCambiosMouseExited
        btn_guardarCambios.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_guardarCambiosMouseExited

    private void btn_actualizarContraseñaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_actualizarContraseñaMouseEntered
        btn_actualizarContraseña.setForeground(Color.WHITE);
    }//GEN-LAST:event_btn_actualizarContraseñaMouseEntered

    private void btn_actualizarContraseñaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_actualizarContraseñaMouseExited
        btn_actualizarContraseña.setForeground(Color.BLACK);
    }//GEN-LAST:event_btn_actualizarContraseñaMouseExited

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        Scanner entrada = new Scanner(System.in);
        System.out.println("Elija un tipo de árbol(ABB, AVL, AMV): ");
        tipoArbol = entrada.nextLine();
        tipoArbol = tipoArbol.toUpperCase();

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new Principal().setVisible(true);
                } catch (ExcepcionOrdenInvalido ex) {
                    Logger.getLogger(Principal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private button.PanelRound PanelRound2;
    private button.MyButton btn_Cancelar;
    private button.MyButton btn_GuardarCambios;
    private button.MyButton btn_actualizarContraseña;
    private button.MyButton btn_añadir;
    private button.MyButton btn_buscar;
    private button.MyButton btn_cancelar;
    private button.MyButton btn_cancelarContraseña;
    private button.MyButton btn_cerrar;
    private button.MyButton btn_cerrarSesion;
    private button.MyButton btn_contactos;
    private button.MyButton btn_editar;
    private button.MyButton btn_editarUsuario;
    private button.MyButton btn_eliminar;
    private button.MyButton btn_guardar;
    private button.MyButton btn_guardarCambios;
    private button.MyButton btn_ingresar;
    private button.MyButton btn_minimizar;
    private button.MyButton btn_registrarse;
    private button.MyButton btn_salir;
    private button.MyButton btn_usuario;
    private button.MyButton btn_ver1;
    private button.MyButton btn_ver2;
    private button.MyButton btn_ver3;
    private button.MyButton btn_verContraseña;
    private button.MyButton btn_verContraseña1;
    private javax.swing.JPanel card_panel;
    private javax.swing.JPanel confirmar_contraseña;
    private javax.swing.JPanel contraseña;
    private javax.swing.JPanel contraseña1;
    private javax.swing.JPanel contraseña_antigua;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel label_cantContactos;
    private javax.swing.JLabel label_crear;
    private javax.swing.JLabel label_email;
    private javax.swing.JLabel label_nombre;
    private javax.swing.JLabel label_nombreUsuario;
    private javax.swing.JLabel label_perfil;
    private javax.swing.JLabel label_regresar;
    private javax.swing.JLabel lbl_cambiarContraseña;
    private javax.swing.JLabel lbl_contactos;
    private javax.swing.JLabel lbl_eliminar;
    private javax.swing.JPanel nueva_contraseña;
    private button.PanelRound panelRound1;
    private button.PanelRound panel_agregar;
    private javax.swing.JPanel panel_añadir;
    private javax.swing.JPanel panel_cambiarContraseña;
    private button.PanelRound panel_cambiarPassword;
    private javax.swing.JPanel panel_contactos;
    private button.PanelRound panel_edit;
    private button.PanelRound panel_editUser;
    private javax.swing.JPanel panel_editar;
    private javax.swing.JPanel panel_editarUsuario;
    private button.PanelRound panel_imagen;
    private javax.swing.JPanel panel_inicioSesion;
    private button.PanelRound panel_login;
    private button.PanelRound panel_login1;
    private button.PanelRound panel_perfil;
    private javax.swing.JPanel panel_registro;
    private javax.swing.JPanel panel_usuario;
    private rojerusan.RSFotoSquareResize rSFotoSquareResize1;
    private rojerusan.RSFotoSquareResize rSFotoSquareResize2;
    private rojerusan.RSFotoSquareResize rSFotoSquareResize3;
    private rojerusan.RSFotoSquareResize rSFotoSquareResize4;
    private rojerusan.RSPanelCircleImage rSPanelCircleImage1;
    private rojerusan.RSTableMetro tabla;
    private javax.swing.JPasswordField txt_antiguaContraseña;
    private javax.swing.JTextField txt_busqueda;
    private javax.swing.JPasswordField txt_confirmarContraseña;
    private javax.swing.JTextField txt_editarEmail;
    private javax.swing.JTextField txt_editarNombre;
    private javax.swing.JTextField txt_editarNombreUsuario;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_nombre;
    private javax.swing.JTextField txt_nombre1;
    private javax.swing.JTextField txt_nombreReg;
    private javax.swing.JPasswordField txt_nuevaContraseña;
    private javax.swing.JPasswordField txt_password;
    private javax.swing.JPasswordField txt_password1;
    private javax.swing.JTextField txt_telefono;
    private javax.swing.JTextField txt_telefono1;
    private javax.swing.JTextField txt_usuario;
    private javax.swing.JTextField txt_usuario1;
    // End of variables declaration//GEN-END:variables
}
