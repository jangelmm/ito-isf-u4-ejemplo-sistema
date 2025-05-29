/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import control.ComentariosRevisionTallerJpaController;
import control.Conexion;
import control.ConvocatoriasJpaController;
import control.EventoParticipantesTalleresJpaController;
import control.EventosJpaController;
import control.EvidenciasJpaController;
import control.TalleresJpaController;
import control.UsuariosJpaController;
import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.BitacorasEventos;
import modelo.ComentariosRevisionTaller;
import modelo.Convocatorias;
import modelo.EventoParticipantesTalleres;
import modelo.Eventos;
import modelo.Evidencias;
import modelo.Talleres;
import modelo.Usuarios;


import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Element; // Para alineación
import com.lowagie.text.Phrase; // Para celdas de tabla
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException; 

/**
 *
 * @author Diego Garcia
 */
public class VentanaPrincipal extends javax.swing.JFrame {
    
    private Usuarios usuarioActual;
    private List<Convocatorias> listaConvocatoriasCargadas;
    
    private List<Talleres> listaTalleresCargados; // ¡Asegúrate de que esta línea exista!
    private List<Eventos> listaEventosCargados; // ¡Asegúrate de que esta línea exista!
    
    private Eventos eventoContextoEvidencias;
    private Talleres tallerContextoEvidencias;
    private File archivoEvidenciaSeleccionado; // Para guardar el archivo elegido por JFileChooser
    
    private List<EventoParticipantesTalleres> ofertasDeTalleresDisponibles;
    private List<EventoParticipantesTalleres> misInscripcionesEnTalleres;
    private EventoParticipantesTalleresJpaController eptController;
    
    
    private List<Evidencias> listaEvidenciasCargadasDialogo; // ¡AÑADIR ESTA LÍNEA!
    
    private List<Usuarios> listaInstructoresCargados; // Para cboInstructorParaAsignar
    private List<EventoParticipantesTalleres> listaAsignacionesActuales; 
    
    public VentanaPrincipal(Usuarios u) {
        initComponents();
        
        setLocationRelativeTo(null);
        
        this.usuarioActual = u;
        lblBienvenida.setText("!Bienvenido, " + u.getNombre() + "!");
        lblNombre.setText("Nombre: " + u.getNombre());
        lblCorreo.setText("Correo: " + u.getCorreo());
        lblRol.setText("Rol : " + u.getRol());
        lblNumControl.setText("Número de control: " + u.getNumeroControl());
        
        this.listaTalleresCargados = new ArrayList<>(); // Inicializar
        this.listaEventosCargados = new ArrayList<>();   // Inicializar
        this.listaEvidenciasCargadasDialogo = new ArrayList<>(); // Inicializar
        
        this.eptController = new EventoParticipantesTalleresJpaController(Conexion.getEMF());
        this.ofertasDeTalleresDisponibles = new ArrayList<>();
        this.misInscripcionesEnTalleres = new ArrayList<>();
        
        this.listaInstructoresCargados = new ArrayList<>();
        this.listaAsignacionesActuales = new ArrayList<>();
        
        
        // Manipulacion del DialogGestionUusarios
        cargarUsuariosEnTabla();
        seleccionarUsuarios();
        // Manipulacion del DialogGestionEventos
        cargarEventosEnTabla();
        seleccionarEventos();
        
        //Manipulación de Talleres
        cargarPonentesEnComboBox();
        //cargarEventosAsociadosEnComboBox();
        cargarTalleresEnTabla();
        
        //Manipulación de Eventos
        cargarConvocatoriasEnTabla();
        
        fileChooserElegirArchivo.setVisible(false);
       
        cargarEventosParaAsignacionComboBox();
        cargarTalleresParaAsignacionComboBox();
        cargarInstructoresParaAsignacionComboBox();
        cargarTablaDeAsignaciones();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        DialogGestionUsuarios = new javax.swing.JDialog();
        jPanel1 = new javax.swing.JPanel();
        encabezado = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cboURol = new javax.swing.JComboBox<>();
        txtUID = new javax.swing.JTextField();
        txtUEmail = new javax.swing.JTextField();
        btnAgregar = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        txtUNumControl = new javax.swing.JTextField();
        txtUNombre = new javax.swing.JTextField();
        passContrasena = new javax.swing.JPasswordField();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jSeparator9 = new javax.swing.JSeparator();
        encabezado1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ttUsuarios = new javax.swing.JTable();
        jSeparator8 = new javax.swing.JSeparator();
        DialogGestionEventos = new javax.swing.JDialog();
        encabezado2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        ttEventos = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        encabezado4 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtENombre = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        txtLugar = new javax.swing.JTextField();
        btnECrear = new javax.swing.JButton();
        btnEModificar = new javax.swing.JButton();
        btnEEliminar = new javax.swing.JButton();
        btnELimpiar = new javax.swing.JButton();
        cboEstado = new javax.swing.JComboBox<>();
        jLabel21 = new javax.swing.JLabel();
        calendario = new com.toedter.calendar.JCalendar();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtEDescripcion = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtEInicio = new javax.swing.JTextField();
        txtEFin = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtEID = new javax.swing.JTextField();
        btnEventoSubirEvidencia = new javax.swing.JButton();
        btnGenerarReporte = new javax.swing.JButton();
        jSeparator11 = new javax.swing.JSeparator();
        jSeparator13 = new javax.swing.JSeparator();
        jSeparator10 = new javax.swing.JSeparator();
        DialogGestionTalleres = new javax.swing.JDialog();
        txtNombreTaller6 = new javax.swing.JLabel();
        txtFieldNombreTaller = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtDescripcionTaller = new javax.swing.JTextArea();
        cboPonente = new javax.swing.JComboBox<>();
        txtMaterialReq = new javax.swing.JLabel();
        txtFieldMaterial_Req = new javax.swing.JTextField();
        TITULO4 = new javax.swing.JLabel();
        TITULO5 = new javax.swing.JLabel();
        jScrollPane9 = new javax.swing.JScrollPane();
        tblTalleres = new javax.swing.JTable();
        cboEstadoTaller = new javax.swing.JLabel();
        cboEstadoTaller2 = new javax.swing.JComboBox<>();
        txtManualRuta = new javax.swing.JTextField();
        btnActualizarTaller = new javax.swing.JButton();
        btnAgregarTaller = new javax.swing.JButton();
        btnElliminarTaller = new javax.swing.JButton();
        txtFecha_Hora = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        btnLimpiarTalleres = new javax.swing.JButton();
        DialogGestionConvocatorias = new javax.swing.JDialog();
        TITULO = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblConvocatorias = new javax.swing.JTable();
        txtNombreTaller = new javax.swing.JLabel();
        txtFieldTituloConvocatoria = new javax.swing.JTextField();
        txtNombreTaller2 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        taDescripcionConvocatoria = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        btnPublicar = new javax.swing.JButton();
        txtNombreTaller1 = new javax.swing.JLabel();
        btnEliminarConvocatoria = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        TITULO1 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        dateChooserFechaLimite = new com.toedter.calendar.JDateChooser();
        dateChooserFechaInscripcion = new com.toedter.calendar.JDateChooser();
        btnLimpiarConvocatorias = new javax.swing.JButton();
        jSeparator12 = new javax.swing.JSeparator();
        DialogInscripcionEventoTaller = new javax.swing.JDialog();
        btnGuardarAsignacion = new javax.swing.JButton();
        lblEventoAsignar = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        tblListaAsignaciones = new javax.swing.JTable();
        TITULO7 = new javax.swing.JLabel();
        btnEliminarAsignacion = new javax.swing.JToggleButton();
        cboEventoParaAsignar = new javax.swing.JComboBox<>();
        lblTallerAsignar = new javax.swing.JLabel();
        cboTallerParaAsignar = new javax.swing.JComboBox<>();
        lblInstructorAsignar = new javax.swing.JLabel();
        cboInstructorParaAsignar = new javax.swing.JComboBox<>();
        lblRolAsignar = new javax.swing.JLabel();
        txtRolAsignadoEnDialog = new javax.swing.JTextField();
        btnLimpiarFormAsignacion = new javax.swing.JButton();
        lblTablaAsignaciones = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        DialogGestionEvidencias = new javax.swing.JDialog();
        jLabel14 = new javax.swing.JLabel();
        TITULO2 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblEvidencias = new javax.swing.JTable();
        txtNombreTaller5 = new javax.swing.JLabel();
        TITULO3 = new javax.swing.JLabel();
        txtDescripción = new javax.swing.JTextField();
        fileChooserElegirArchivo = new javax.swing.JFileChooser();
        btnSubirEvidencia = new javax.swing.JButton();
        btnDescargarEvidencia = new javax.swing.JButton();
        btnEliminarEvidencia = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        cobTipoEvidencia = new javax.swing.JComboBox<>();
        SeleccionarArchivoEvidencia = new javax.swing.JButton();
        panelGeneral = new javax.swing.JPanel();
        panelEncabezado = new javax.swing.JPanel();
        lblBienvenida = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel24 = new javax.swing.JLabel();
        panelEstatus = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblCorreo = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        lblNumControl = new javax.swing.JLabel();
        menuBarraPrincipal = new javax.swing.JMenuBar();
        menuGestion = new javax.swing.JMenu();
        opcionUsuarios = new javax.swing.JMenuItem();
        opcionEventos = new javax.swing.JMenuItem();
        opcionTalleres = new javax.swing.JMenuItem();
        opcionConvocatorias = new javax.swing.JMenuItem();
        menuInscripcion = new javax.swing.JMenu();
        opcionInscripcion = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        opcionAyuda = new javax.swing.JMenuItem();
        menuSalir = new javax.swing.JMenu();
        opcionCerrarSesion = new javax.swing.JMenuItem();
        opcionSalir = new javax.swing.JMenuItem();

        DialogGestionUsuarios.setTitle("Gestión de usuarios");
        DialogGestionUsuarios.setResizable(false);
        DialogGestionUsuarios.setSize(new java.awt.Dimension(1000, 500));

        jPanel1.setToolTipText("");

        encabezado.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        encabezado.setText("Datos del usuario");

        jLabel2.setText("ID:");

        jLabel4.setText("Nombre:");

        jLabel6.setText("Contraseña:");

        jLabel7.setText("Núm. de Control:");

        jLabel8.setText("Rol:");

        cboURol.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "DOCENTE", "TALLERISTA", "ADMINISTRADOR" }));
        cboURol.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboURolActionPerformed(evt);
            }
        });

        txtUID.setEditable(false);

        txtUEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUEmailActionPerformed(evt);
            }
        });

        btnAgregar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/agregar_usuario.png"))); // NOI18N
        btnAgregar.setText("Agregar");
        btnAgregar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarActionPerformed(evt);
            }
        });

        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/girar-flecha.png"))); // NOI18N
        btnActualizar.setText("Actualizar");
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/papelera.png"))); // NOI18N
        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clean.png"))); // NOI18N
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        txtUNumControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUNumControlActionPerformed(evt);
            }
        });

        txtUNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUNombreActionPerformed(evt);
            }
        });

        jLabel9.setText("Email:");

        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario_muestra.png"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(encabezado))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(jLabel12)
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(43, 43, 43)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(passContrasena)
                            .addComponent(txtUNombre)
                            .addComponent(txtUNumControl)
                            .addComponent(cboURol, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtUEmail)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(txtUID, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)))))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(encabezado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jSeparator9, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUNombre))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtUEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(passContrasena, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(cboURol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtUNumControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(43, 43, 43)))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLimpiar)
                            .addComponent(btnActualizar)
                            .addComponent(btnAgregar)
                            .addComponent(btnEliminar))
                        .addGap(15, 15, 15))))
        );

        encabezado1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        encabezado1.setText("Lista de usuarios");

        ttUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Email", "Rol", "Núm. de Control", "Contraseña"
            }
        ));
        jScrollPane1.setViewportView(ttUsuarios);

        javax.swing.GroupLayout DialogGestionUsuariosLayout = new javax.swing.GroupLayout(DialogGestionUsuarios.getContentPane());
        DialogGestionUsuarios.getContentPane().setLayout(DialogGestionUsuariosLayout);
        DialogGestionUsuariosLayout.setHorizontalGroup(
            DialogGestionUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogGestionUsuariosLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addGroup(DialogGestionUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogGestionUsuariosLayout.createSequentialGroup()
                        .addComponent(encabezado1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(DialogGestionUsuariosLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 964, Short.MAX_VALUE)
                        .addGap(15, 15, 15))
                    .addGroup(DialogGestionUsuariosLayout.createSequentialGroup()
                        .addGroup(DialogGestionUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 953, Short.MAX_VALUE)
                            .addComponent(jSeparator8))
                        .addContainerGap(26, Short.MAX_VALUE))))
        );
        DialogGestionUsuariosLayout.setVerticalGroup(
            DialogGestionUsuariosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogGestionUsuariosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator8, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(encabezado1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        jPanel1.getAccessibleContext().setAccessibleName("");

        DialogGestionEventos.setTitle("Gestión de eventos");
        DialogGestionEventos.setResizable(false);

        encabezado2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        encabezado2.setText("Lista de eventos");

        ttEventos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Descripción", "Fecha", "Hora de inicio", "Hora de fin", "Lugar", "Estado"
            }
        ));
        jScrollPane2.setViewportView(ttEventos);

        jPanel3.setToolTipText("");

        encabezado4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        encabezado4.setText("Datos del evento");

        jLabel15.setText("Nombre:");

        jLabel16.setText("Descripción:");

        jLabel17.setText("Fecha:");

        txtENombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtENombreActionPerformed(evt);
            }
        });

        jLabel20.setText("Lugar:");

        btnECrear.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        btnECrear.setText("Crear");
        btnECrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnECrearActionPerformed(evt);
            }
        });

        btnEModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/girar-flecha.png"))); // NOI18N
        btnEModificar.setText("Modificar");
        btnEModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEModificarActionPerformed(evt);
            }
        });

        btnEEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/papelera.png"))); // NOI18N
        btnEEliminar.setText("Eliminar");
        btnEEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEEliminarActionPerformed(evt);
            }
        });

        btnELimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clean.png"))); // NOI18N
        btnELimpiar.setText("Limpiar");
        btnELimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnELimpiarActionPerformed(evt);
            }
        });

        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PLANIFICADO", "CONFIRMADO", "EN_CURSO", "FINALIZADO", "CANCELADO", "POSPUESTO" }));
        cboEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstadoActionPerformed(evt);
            }
        });

        jLabel21.setText("Estado:");

        txtEDescripcion.setColumns(20);
        txtEDescripcion.setRows(5);
        jScrollPane3.setViewportView(txtEDescripcion);

        jLabel3.setText("Hora de fin:");

        jLabel5.setText("Hora de inicio:");

        jLabel10.setText("ID:");

        txtEID.setEditable(false);

        btnEventoSubirEvidencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/up.png"))); // NOI18N
        btnEventoSubirEvidencia.setText("Subir Evidencia");
        btnEventoSubirEvidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEventoSubirEvidenciaActionPerformed(evt);
            }
        });

        btnGenerarReporte.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/exportar_pdf.png"))); // NOI18N
        btnGenerarReporte.setText("Generar Reporte");
        btnGenerarReporte.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGenerarReporteActionPerformed(evt);
            }
        });

        jSeparator13.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(131, 131, 131)
                        .addComponent(btnECrear, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnELimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(btnEventoSubirEvidencia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGenerarReporte, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 12, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator11, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtEID, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtLugar)
                                            .addComponent(txtENombre, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(cboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(48, 48, 48)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtEInicio)
                                            .addComponent(txtEFin)
                                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(79, 79, 79)))))
                .addContainerGap())
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(encabezado4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(encabezado4)
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtEID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtENombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtLugar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtEFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(calendario, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jSeparator11, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnEEliminar)
                            .addComponent(btnELimpiar)
                            .addComponent(btnEventoSubirEvidencia)
                            .addComponent(btnGenerarReporte)
                            .addComponent(btnEModificar)
                            .addComponent(btnECrear))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jSeparator13, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );

        javax.swing.GroupLayout DialogGestionEventosLayout = new javax.swing.GroupLayout(DialogGestionEventos.getContentPane());
        DialogGestionEventos.getContentPane().setLayout(DialogGestionEventosLayout);
        DialogGestionEventosLayout.setHorizontalGroup(
            DialogGestionEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogGestionEventosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(DialogGestionEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogGestionEventosLayout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(DialogGestionEventosLayout.createSequentialGroup()
                        .addGroup(DialogGestionEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jSeparator10)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DialogGestionEventosLayout.createSequentialGroup()
                                .addComponent(encabezado2)
                                .addGap(374, 374, 374))
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 970, Short.MAX_VALUE))
                        .addGap(14, 14, 14))))
        );
        DialogGestionEventosLayout.setVerticalGroup(
            DialogGestionEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogGestionEventosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(3, 3, 3)
                .addComponent(jSeparator10, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(5, 5, 5)
                .addComponent(encabezado2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        DialogGestionTalleres.setTitle("Gestión de talleres");
        DialogGestionTalleres.setResizable(false);

        txtNombreTaller6.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        txtNombreTaller6.setText("Nombre del Taller: ");

        txtFieldNombreTaller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldNombreTallerActionPerformed(evt);
            }
        });

        jLabel18.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel18.setText("Descripción:");

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel19.setText("Ponente/Instructor:");

        txtDescripcionTaller.setColumns(20);
        txtDescripcionTaller.setRows(5);
        jScrollPane8.setViewportView(txtDescripcionTaller);

        txtMaterialReq.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        txtMaterialReq.setText("Material Requerido:");

        txtFieldMaterial_Req.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldMaterial_ReqActionPerformed(evt);
            }
        });

        TITULO4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TITULO4.setText("Talleres Registrados");

        TITULO5.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TITULO5.setText("Gestión de Talleres");

        tblTalleres.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Descripción", "Usuario"
            }
        ));
        tblTalleres.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTalleresMouseClicked(evt);
            }
        });
        jScrollPane9.setViewportView(tblTalleres);

        cboEstadoTaller.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        cboEstadoTaller.setText("Estado del Taller:");

        cboEstadoTaller2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "APROBADO", "EN_REVISION_DOCENTE", "REQUIERE_MODIFICACION", "PENDIENTE_PROPUESTA" }));
        cboEstadoTaller2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboEstadoTaller2ActionPerformed(evt);
            }
        });

        txtManualRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtManualRutaActionPerformed(evt);
            }
        });

        btnActualizarTaller.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/girar-flecha.png"))); // NOI18N
        btnActualizarTaller.setText("Actualizar Taller");
        btnActualizarTaller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTallerActionPerformed(evt);
            }
        });

        btnAgregarTaller.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        btnAgregarTaller.setText("Agregar Taller");
        btnAgregarTaller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTallerActionPerformed(evt);
            }
        });

        btnElliminarTaller.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/papelera.png"))); // NOI18N
        btnElliminarTaller.setText("Eliminar Taller");
        btnElliminarTaller.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnElliminarTallerActionPerformed(evt);
            }
        });

        txtFecha_Hora.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        txtFecha_Hora.setText("Ruta de Anexos:");

        btnLimpiarTalleres.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clean.png"))); // NOI18N
        btnLimpiarTalleres.setText("Limpiar");
        btnLimpiarTalleres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarTalleresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DialogGestionTalleresLayout = new javax.swing.GroupLayout(DialogGestionTalleres.getContentPane());
        DialogGestionTalleres.getContentPane().setLayout(DialogGestionTalleresLayout);
        DialogGestionTalleresLayout.setHorizontalGroup(
            DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogGestionTalleresLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 966, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(TITULO4)
                                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 966, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                        .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TITULO5)
                            .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                                .addGap(39, 39, 39)
                                .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                                        .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtNombreTaller6, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtFecha_Hora, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtManualRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtFieldNombreTaller, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                                        .addComponent(txtMaterialReq, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtFieldMaterial_Req, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                .addComponent(cboEstadoTaller, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboPonente, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboEstadoTaller2, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(29, 29, 29)))
                .addGap(18, 18, 18))
            .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                .addGap(167, 167, 167)
                .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator2)
                    .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                        .addComponent(btnAgregarTaller, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(43, 43, 43)
                        .addComponent(btnActualizarTaller)
                        .addGap(43, 43, 43)
                        .addComponent(btnLimpiarTalleres, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnElliminarTaller, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(174, Short.MAX_VALUE))
        );
        DialogGestionTalleresLayout.setVerticalGroup(
            DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TITULO5)
                .addGap(25, 25, 25)
                .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                        .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                                .addComponent(cboPonente, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18))
                            .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(51, 51, 51)
                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(23, 23, 23)))
                        .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(cboEstadoTaller, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboEstadoTaller2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)))
                    .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                        .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txtFecha_Hora, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtManualRuta, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(DialogGestionTalleresLayout.createSequentialGroup()
                                .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txtFieldNombreTaller, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtNombreTaller6, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(76, 76, 76)))
                        .addGap(23, 23, 23)
                        .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtFieldMaterial_Req, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtMaterialReq, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DialogGestionTalleresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAgregarTaller)
                    .addComponent(btnElliminarTaller)
                    .addComponent(btnLimpiarTalleres)
                    .addComponent(btnActualizarTaller))
                .addGap(18, 18, 18)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TITULO4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27))
        );

        DialogGestionConvocatorias.setTitle("Gestión de convocatorias");
        DialogGestionConvocatorias.setResizable(false);

        TITULO.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TITULO.setText("Gestión de Convocatorias");

        tblConvocatorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Titulo", "Descripción", "Fecha de Publicación", "Fecha Límite"
            }
        ));
        tblConvocatorias.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblConvocatoriasMouseClicked(evt);
            }
        });
        jScrollPane4.setViewportView(tblConvocatorias);

        txtNombreTaller.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        txtNombreTaller.setText("Título de la convocatoria:");

        txtFieldTituloConvocatoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFieldTituloConvocatoriaActionPerformed(evt);
            }
        });

        txtNombreTaller2.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        txtNombreTaller2.setText("Fecha de inscripción/publicación:");

        taDescripcionConvocatoria.setColumns(20);
        taDescripcionConvocatoria.setRows(5);
        jScrollPane5.setViewportView(taDescripcionConvocatoria);

        jLabel11.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel11.setText("Descripción de la convocatoria:");

        btnPublicar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        btnPublicar.setText("Publicar/Guardar");
        btnPublicar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPublicarActionPerformed(evt);
            }
        });

        txtNombreTaller1.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        txtNombreTaller1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        txtNombreTaller1.setText("Fecha límite:");

        btnEliminarConvocatoria.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/papelera.png"))); // NOI18N
        btnEliminarConvocatoria.setText("Eliminar Convocatoria");
        btnEliminarConvocatoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarConvocatoriaActionPerformed(evt);
            }
        });

        btnModificar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/girar-flecha.png"))); // NOI18N
        btnModificar.setText("Modificar");
        btnModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarActionPerformed(evt);
            }
        });

        TITULO1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TITULO1.setText("Lista de Convocatorias Existentes");

        btnLimpiarConvocatorias.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clean.png"))); // NOI18N
        btnLimpiarConvocatorias.setText("Limpiar");
        btnLimpiarConvocatorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarConvocatoriasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DialogGestionConvocatoriasLayout = new javax.swing.GroupLayout(DialogGestionConvocatorias.getContentPane());
        DialogGestionConvocatorias.getContentPane().setLayout(DialogGestionConvocatoriasLayout);
        DialogGestionConvocatoriasLayout.setHorizontalGroup(
            DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogGestionConvocatoriasLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DialogGestionConvocatoriasLayout.createSequentialGroup()
                        .addGap(96, 96, 96)
                        .addComponent(btnPublicar, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiarConvocatorias, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminarConvocatoria, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jSeparator12, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogGestionConvocatoriasLayout.createSequentialGroup()
                        .addGroup(DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNombreTaller, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)
                            .addComponent(txtFieldTituloConvocatoria)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtNombreTaller1, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNombreTaller2))
                        .addGap(18, 18, 18)
                        .addGroup(DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateChooserFechaInscripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(dateChooserFechaLimite, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(71, 71, 71))
            .addGroup(DialogGestionConvocatoriasLayout.createSequentialGroup()
                .addGroup(DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogGestionConvocatoriasLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(TITULO))
                    .addGroup(DialogGestionConvocatoriasLayout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TITULO1)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 950, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        DialogGestionConvocatoriasLayout.setVerticalGroup(
            DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogGestionConvocatoriasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(TITULO)
                .addGap(18, 18, 18)
                .addComponent(txtNombreTaller)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(DialogGestionConvocatoriasLayout.createSequentialGroup()
                        .addGroup(DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(dateChooserFechaInscripcion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombreTaller2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(93, 93, 93)
                        .addGroup(DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateChooserFechaLimite, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txtNombreTaller1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DialogGestionConvocatoriasLayout.createSequentialGroup()
                        .addComponent(txtFieldTituloConvocatoria, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jSeparator12, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DialogGestionConvocatoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPublicar)
                    .addComponent(btnModificar)
                    .addComponent(btnLimpiarConvocatorias)
                    .addComponent(btnEliminarConvocatoria))
                .addGap(18, 18, 18)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TITULO1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        DialogInscripcionEventoTaller.setResizable(false);

        btnGuardarAsignacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/guardar.png"))); // NOI18N
        btnGuardarAsignacion.setText("Guardar Asignación");
        btnGuardarAsignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarAsignacionActionPerformed(evt);
            }
        });

        lblEventoAsignar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblEventoAsignar.setText("Seleccionar Evento");

        tblListaAsignaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane11.setViewportView(tblListaAsignaciones);

        TITULO7.setText("Eventos Disponibles:");

        btnEliminarAsignacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/papelera.png"))); // NOI18N
        btnEliminarAsignacion.setText("Eliminar Asignación Seleccionada");
        btnEliminarAsignacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarAsignacionActionPerformed(evt);
            }
        });

        lblTallerAsignar.setText("Seleccionar Taller:");

        lblInstructorAsignar.setText("Seleccionar Instructor/Ponente:");

        lblRolAsignar.setText("Rol Asignado:");

        txtRolAsignadoEnDialog.setText("INSTRUCTOR");
        txtRolAsignadoEnDialog.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtRolAsignadoEnDialogActionPerformed(evt);
            }
        });

        btnLimpiarFormAsignacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/clean.png"))); // NOI18N
        btnLimpiarFormAsignacion.setText("Limpiar Asignación");

        lblTablaAsignaciones.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTablaAsignaciones.setText("Asignaciones Existentes de Instructores a Talleres/Eventos");

        javax.swing.GroupLayout DialogInscripcionEventoTallerLayout = new javax.swing.GroupLayout(DialogInscripcionEventoTaller.getContentPane());
        DialogInscripcionEventoTaller.getContentPane().setLayout(DialogInscripcionEventoTallerLayout);
        DialogInscripcionEventoTallerLayout.setHorizontalGroup(
            DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogInscripcionEventoTallerLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TITULO7, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblInstructorAsignar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogInscripcionEventoTallerLayout.createSequentialGroup()
                        .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboEventoParaAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboInstructorParaAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                        .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblRolAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTallerAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtRolAsignadoEnDialog)
                            .addComponent(cboTallerParaAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator4))
                .addGap(57, 57, 57))
            .addGroup(DialogInscripcionEventoTallerLayout.createSequentialGroup()
                .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogInscripcionEventoTallerLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblEventoAsignar))
                    .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, DialogInscripcionEventoTallerLayout.createSequentialGroup()
                            .addGap(21, 21, 21)
                            .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(lblTablaAsignaciones)
                                .addComponent(jScrollPane11, javax.swing.GroupLayout.DEFAULT_SIZE, 954, Short.MAX_VALUE)
                                .addComponent(jSeparator7)))
                        .addGroup(DialogInscripcionEventoTallerLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(btnEliminarAsignacion)))
                    .addGroup(DialogInscripcionEventoTallerLayout.createSequentialGroup()
                        .addGap(332, 332, 332)
                        .addComponent(btnGuardarAsignacion)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiarFormAsignacion)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DialogInscripcionEventoTallerLayout.setVerticalGroup(
            DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DialogInscripcionEventoTallerLayout.createSequentialGroup()
                .addGap(7, 7, 7)
                .addComponent(lblEventoAsignar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogInscripcionEventoTallerLayout.createSequentialGroup()
                        .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTallerAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboTallerParaAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(28, 28, 28)
                        .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtRolAsignadoEnDialog, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblRolAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(DialogInscripcionEventoTallerLayout.createSequentialGroup()
                        .addComponent(TITULO7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(lblInstructorAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(DialogInscripcionEventoTallerLayout.createSequentialGroup()
                        .addComponent(cboEventoParaAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(cboInstructorParaAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(DialogInscripcionEventoTallerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardarAsignacion)
                    .addComponent(btnLimpiarFormAsignacion))
                .addGap(18, 18, 18)
                .addComponent(jSeparator7, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTablaAsignaciones)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnEliminarAsignacion)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        DialogGestionEvidencias.setTitle("Gestión de evidencias");
        DialogGestionEvidencias.setResizable(false);

        jLabel14.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        jLabel14.setText("Tipo de Evidencia:");

        TITULO2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TITULO2.setText("Gestión de Evidencias para Evento");

        tblEvidencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nombre del archivo", "Tipo de evidencia", "Decripción", "Fecha de subida"
            }
        ));
        jScrollPane6.setViewportView(tblEvidencias);

        txtNombreTaller5.setFont(new java.awt.Font("Segoe UI Emoji", 0, 12)); // NOI18N
        txtNombreTaller5.setText("Descripción:");

        TITULO3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        TITULO3.setText("Evidencias Subidas");

        txtDescripción.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDescripciónActionPerformed(evt);
            }
        });

        fileChooserElegirArchivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileChooserElegirArchivoActionPerformed(evt);
            }
        });

        btnSubirEvidencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/up.png"))); // NOI18N
        btnSubirEvidencia.setText("Subir Evidencia");
        btnSubirEvidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubirEvidenciaActionPerformed(evt);
            }
        });

        btnDescargarEvidencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/download.png"))); // NOI18N
        btnDescargarEvidencia.setText("Descargar Evidencia");
        btnDescargarEvidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDescargarEvidenciaActionPerformed(evt);
            }
        });

        btnEliminarEvidencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/papelera.png"))); // NOI18N
        btnEliminarEvidencia.setText("Eliminar Evidencia");
        btnEliminarEvidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarEvidenciaActionPerformed(evt);
            }
        });

        cobTipoEvidencia.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "FOTO", "VIDEO", "DOCUMENTO_GENERAL", "LISTA_ASISTENCIA", "RECONOCIMIENTO_PDF", "OTRO" }));

        SeleccionarArchivoEvidencia.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/add.png"))); // NOI18N
        SeleccionarArchivoEvidencia.setText("Seleccionar Archivo");
        SeleccionarArchivoEvidencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SeleccionarArchivoEvidenciaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout DialogGestionEvidenciasLayout = new javax.swing.GroupLayout(DialogGestionEvidencias.getContentPane());
        DialogGestionEvidencias.getContentPane().setLayout(DialogGestionEvidenciasLayout);
        DialogGestionEvidenciasLayout.setHorizontalGroup(
            DialogGestionEvidenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogGestionEvidenciasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(DialogGestionEvidenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogGestionEvidenciasLayout.createSequentialGroup()
                        .addComponent(TITULO2)
                        .addContainerGap(766, Short.MAX_VALUE))
                    .addGroup(DialogGestionEvidenciasLayout.createSequentialGroup()
                        .addGap(43, 43, 43)
                        .addComponent(txtNombreTaller5, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtDescripción, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel14)
                        .addGap(12, 12, 12)
                        .addComponent(cobTipoEvidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(111, 111, 111))
                    .addGroup(DialogGestionEvidenciasLayout.createSequentialGroup()
                        .addGap(99, 99, 99)
                        .addComponent(SeleccionarArchivoEvidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(btnSubirEvidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(63, 63, 63)
                        .addComponent(btnDescargarEvidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52)
                        .addComponent(btnEliminarEvidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(DialogGestionEvidenciasLayout.createSequentialGroup()
                .addGroup(DialogGestionEvidenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(DialogGestionEvidenciasLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(DialogGestionEvidenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TITULO3)
                            .addComponent(jSeparator6)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 965, Short.MAX_VALUE)))
                    .addGroup(DialogGestionEvidenciasLayout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(fileChooserElegirArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 622, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        DialogGestionEvidenciasLayout.setVerticalGroup(
            DialogGestionEvidenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DialogGestionEvidenciasLayout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(TITULO2)
                .addGap(34, 34, 34)
                .addGroup(DialogGestionEvidenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNombreTaller5, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtDescripción, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cobTipoEvidencia, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(TITULO3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(DialogGestionEvidenciasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDescargarEvidencia)
                    .addComponent(btnSubirEvidencia)
                    .addComponent(btnEliminarEvidencia)
                    .addComponent(SeleccionarArchivoEvidencia))
                .addGap(18, 18, 18)
                .addComponent(fileChooserElegirArchivo, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AcademicPlus - Ventana Principal");

        panelGeneral.setLayout(new java.awt.GridLayout(2, 0));

        panelEncabezado.setMinimumSize(new java.awt.Dimension(20, 40));
        panelEncabezado.setLayout(new java.awt.GridLayout(3, 0));

        lblBienvenida.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblBienvenida.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblBienvenida.setText("Bienvenido al Sistema");
        lblBienvenida.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        panelEncabezado.add(lblBienvenida);
        panelEncabezado.add(jSeparator1);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario_muestra.png"))); // NOI18N
        jLabel24.setText("AcademicPlus");
        panelEncabezado.add(jLabel24);

        panelGeneral.add(panelEncabezado);

        panelEstatus.setLayout(new java.awt.GridLayout(5, 1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Estatus del usuario");
        panelEstatus.add(jLabel1);

        lblNombre.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNombre.setText("Nombre: ");
        panelEstatus.add(lblNombre);

        lblCorreo.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblCorreo.setText("Correo: ");
        lblCorreo.setPreferredSize(new java.awt.Dimension(50, 16));
        panelEstatus.add(lblCorreo);

        lblRol.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblRol.setText("Rol: ");
        panelEstatus.add(lblRol);

        lblNumControl.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblNumControl.setText("Número de control: ");
        panelEstatus.add(lblNumControl);

        panelGeneral.add(panelEstatus);

        menuGestion.setText("Gestión");

        opcionUsuarios.setText("Usuarios");
        opcionUsuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionUsuariosActionPerformed(evt);
            }
        });
        menuGestion.add(opcionUsuarios);

        opcionEventos.setText("Eventos");
        opcionEventos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionEventosActionPerformed(evt);
            }
        });
        menuGestion.add(opcionEventos);

        opcionTalleres.setText("Talleres");
        opcionTalleres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionTalleresActionPerformed(evt);
            }
        });
        menuGestion.add(opcionTalleres);

        opcionConvocatorias.setText("Convocatorias");
        opcionConvocatorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionConvocatoriasActionPerformed(evt);
            }
        });
        menuGestion.add(opcionConvocatorias);

        menuBarraPrincipal.add(menuGestion);

        menuInscripcion.setText("Inscripción");

        opcionInscripcion.setText("Realizar una inscripción");
        opcionInscripcion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionInscripcionActionPerformed(evt);
            }
        });
        menuInscripcion.add(opcionInscripcion);

        menuBarraPrincipal.add(menuInscripcion);

        menuAyuda.setText("Ayuda");

        opcionAyuda.setText("Manual y documentación");
        opcionAyuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionAyudaActionPerformed(evt);
            }
        });
        menuAyuda.add(opcionAyuda);

        menuBarraPrincipal.add(menuAyuda);

        menuSalir.setText("Salir");

        opcionCerrarSesion.setText("Cerrar sesión");
        opcionCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionCerrarSesionActionPerformed(evt);
            }
        });
        menuSalir.add(opcionCerrarSesion);

        opcionSalir.setText("Salir");
        opcionSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                opcionSalirActionPerformed(evt);
            }
        });
        menuSalir.add(opcionSalir);

        menuBarraPrincipal.add(menuSalir);

        setJMenuBar(menuBarraPrincipal);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGeneral, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelGeneral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void opcionInscripcionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionInscripcionActionPerformed
        DialogInscripcionEventoTaller.setVisible(true);
        DialogInscripcionEventoTaller.setSize(1000, 500);
        DialogInscripcionEventoTaller.setLocationRelativeTo(this);
    }//GEN-LAST:event_opcionInscripcionActionPerformed

    private void opcionUsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionUsuariosActionPerformed
        // TODO add your handling code here:
        DialogGestionUsuarios.setVisible(true);
        DialogGestionUsuarios.setSize(1000,600);
        DialogGestionUsuarios.setLocationRelativeTo(this);
    }//GEN-LAST:event_opcionUsuariosActionPerformed

    private void opcionCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionCerrarSesionActionPerformed
        // TODO add your handling code here:
        VentanaLogin login = new VentanaLogin();
        login.setVisible(true);
        login.setLocationRelativeTo(null);
        dispose();
    }//GEN-LAST:event_opcionCerrarSesionActionPerformed

    private void opcionSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionSalirActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_opcionSalirActionPerformed

    private void opcionAyudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionAyudaActionPerformed
        // TODO add your handling code here:
        try {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/jangelmm/AcademicPlus.git"));
            } catch (IOException ex) {
                Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(VentanaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_opcionAyudaActionPerformed

    private void cboURolActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboURolActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboURolActionPerformed

    private void txtUNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUNombreActionPerformed

    private void opcionEventosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionEventosActionPerformed
        // TODO add your handling code here:
        DialogGestionEventos.setVisible(true);
        DialogGestionEventos.setSize(1000, 600);
        DialogGestionEventos.setLocationRelativeTo(this);
    }//GEN-LAST:event_opcionEventosActionPerformed

    private void cboEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEstadoActionPerformed

    private void txtENombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtENombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtENombreActionPerformed

    private void txtUEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUEmailActionPerformed

    private void txtUNumControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUNumControlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUNumControlActionPerformed

    private void btnAgregarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarActionPerformed
        // TODO add your handling code here:
        // Crear una instancia de la entidad
        Usuarios nuevo = new Usuarios();
        nuevo.setNombre(txtUNombre.getText());
        nuevo.setCorreo(txtUEmail.getText());
        nuevo.setContrasenaHash(new String(passContrasena.getPassword()));
        nuevo.setRol(cboURol.getSelectedItem().toString());
        nuevo.setNumeroControl(txtUNumControl.getText());

        try {
            UsuariosJpaController controller = new UsuariosJpaController(Conexion.getEMF());
            controller.create(nuevo); // Método de instancia, no static
            cargarUsuariosEnTabla(); // Refresca la tabla
            limpiarCamposUsuario();  // Limpia los campos del formulario
            JOptionPane.showMessageDialog(this, "Usuario insertado exitosamente");
        } catch (Exception e) {
            System.err.println("Error al insertar el usuario: " + e.getMessage());
        }
    }//GEN-LAST:event_btnAgregarActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        // TODO add your handling code here:
        if (txtUID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un usuario para modificar.");
            return;
        }

        try {
            int id = Integer.parseInt(txtUID.getText());

            // Crear controlador JPA
            UsuariosJpaController controller = new UsuariosJpaController(Conexion.getEMF());
            
            // Obtener el usuario desde la base de datos
            Usuarios usuarioExistente = controller.findUsuarios(id);

            if (usuarioExistente != null) {
                // Actualizar los valores
                usuarioExistente.setNombre(txtUNombre.getText());
                usuarioExistente.setCorreo(txtUEmail.getText());
                usuarioExistente.setRol(cboURol.getSelectedItem().toString());
                usuarioExistente.setNumeroControl(txtUNumControl.getText());
                
                String contrasena = "";
                for (char e : passContrasena.getPassword()) {
                    contrasena += e;
                }
                
                usuarioExistente.setContrasenaHash(contrasena);
                
                // Guardar los cambios
                controller.edit(usuarioExistente);

                cargarUsuariosEnTabla();
                limpiarCamposUsuario();
                JOptionPane.showMessageDialog(this, "Usuario modificado exitosamente");
            } else {
                JOptionPane.showMessageDialog(this, "El usuario no fue encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        if (txtUID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(DialogGestionUsuarios, "Por favor, seleccione un usuario de la tabla para eliminar.", "Ningún Usuario Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int idUsuarioAEliminar;
        try {
            idUsuarioAEliminar = Integer.parseInt(txtUID.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(DialogGestionUsuarios, "El ID del usuario no es válido.", "Error de ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int idUsuarioGenerico = 1; // ID del usuario genérico (sistema)
        if (idUsuarioAEliminar == idUsuarioGenerico) {
            JOptionPane.showMessageDialog(DialogGestionUsuarios, "No se puede eliminar el usuario genérico del sistema.", "Operación no permitida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (usuarioActual != null && usuarioActual.getIdUsuario() == idUsuarioAEliminar) {
            JOptionPane.showMessageDialog(DialogGestionUsuarios, "No puedes eliminar el usuario con el que has iniciado sesión.", "Operación no permitida", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(DialogGestionUsuarios,
            "¿Está seguro de que desea eliminar este usuario?\nTodas sus referencias serán reasignadas al usuario genérico del sistema.",
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            EntityManagerFactory emf = Conexion.getEMF();
            EntityManager em = emf.createEntityManager();

            try {
                em.getTransaction().begin();

                // Obtener la entidad del usuario genérico
                Usuarios usuarioGenericoEntidad = em.find(Usuarios.class, idUsuarioGenerico);
                if (usuarioGenericoEntidad == null) {
                    JOptionPane.showMessageDialog(DialogGestionUsuarios, "No se encontró el usuario genérico con ID: " + idUsuarioGenerico, "Error Crítico", JOptionPane.ERROR_MESSAGE);
                    if (em.getTransaction().isActive()) {
                        em.getTransaction().rollback();
                    }
                    return;
                }

                // 1. Reasignar ComentariosRevisionTaller
                em.createQuery("UPDATE ComentariosRevisionTaller c SET c.idUsuarioComentarista = :nuevoUsuario WHERE c.idUsuarioComentarista.idUsuario = :actualId")
                  .setParameter("nuevoUsuario", usuarioGenericoEntidad)
                  .setParameter("actualId", idUsuarioAEliminar)
                  .executeUpdate();

                // 2. Reasignar Notificaciones
                em.createQuery("UPDATE Notificaciones n SET n.idUsuarioDestinatario = :nuevoUsuario WHERE n.idUsuarioDestinatario.idUsuario = :actualId")
                  .setParameter("nuevoUsuario", usuarioGenericoEntidad)
                  .setParameter("actualId", idUsuarioAEliminar)
                  .executeUpdate();

                // 3. Reasignar Evidencias
                em.createQuery("UPDATE Evidencias e SET e.idUsuarioSubio = :nuevoUsuario WHERE e.idUsuarioSubio.idUsuario = :actualId")
                  .setParameter("nuevoUsuario", usuarioGenericoEntidad)
                  .setParameter("actualId", idUsuarioAEliminar)
                  .executeUpdate();

                // 4. Reasignar Talleres (como proponente)
                em.createQuery("UPDATE Talleres t SET t.idUsuarioProponente = :nuevoUsuario WHERE t.idUsuarioProponente.idUsuario = :actualId")
                  .setParameter("nuevoUsuario", usuarioGenericoEntidad)
                  .setParameter("actualId", idUsuarioAEliminar)
                  .executeUpdate();

                // 5. Reasignar Eventos (como docente responsable)
                // Nota: Si idDocenteResponsable es opcional y prefieres ponerlo a null en lugar de reasignar,
                // la consulta sería: UPDATE Eventos ev SET ev.idDocenteResponsable = NULL WHERE ev.idDocenteResponsable.idUsuario = :actualId
                // Pero para mantener la consistencia de reasignar:
                em.createQuery("UPDATE Eventos ev SET ev.idDocenteResponsable = :nuevoUsuario WHERE ev.idDocenteResponsable.idUsuario = :actualId")
                  .setParameter("nuevoUsuario", usuarioGenericoEntidad)
                  .setParameter("actualId", idUsuarioAEliminar)
                  .executeUpdate();

                // 6. Reasignar BitacorasEventos
                em.createQuery("UPDATE BitacorasEventos be SET be.idUsuarioRegistra = :nuevoUsuario WHERE be.idUsuarioRegistra.idUsuario = :actualId")
                  .setParameter("nuevoUsuario", usuarioGenericoEntidad)
                  .setParameter("actualId", idUsuarioAEliminar)
                  .executeUpdate();

                // 7. Reasignar Convocatorias
                em.createQuery("UPDATE Convocatorias conv SET conv.idUsuarioPublica = :nuevoUsuario WHERE conv.idUsuarioPublica.idUsuario = :actualId")
                  .setParameter("nuevoUsuario", usuarioGenericoEntidad)
                  .setParameter("actualId", idUsuarioAEliminar)
                  .executeUpdate();

                // 8. Reasignar EventoParticipantesTalleres (como tallerista/participante)
                em.createQuery("UPDATE EventoParticipantesTalleres ept SET ept.idTallerista = :nuevoUsuario WHERE ept.idTallerista.idUsuario = :actualId")
                  .setParameter("nuevoUsuario", usuarioGenericoEntidad)
                  .setParameter("actualId", idUsuarioAEliminar)
                  .executeUpdate();

                // Finalmente, eliminar el usuario
                Usuarios usuarioAEliminarObj = em.find(Usuarios.class, idUsuarioAEliminar);
                if (usuarioAEliminarObj != null) {
                    em.remove(usuarioAEliminarObj);
                } else {
                    JOptionPane.showMessageDialog(DialogGestionUsuarios, "El usuario a eliminar no fue encontrado (ID: " + idUsuarioAEliminar + "). La reasignación de referencias pudo haberse realizado parcialmente.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    if (em.getTransaction().isActive()) {
                        // Considera si quieres hacer commit de las reasignaciones o rollback si el usuario no se encuentra.
                        // Si el objetivo es limpiar referencias incluso si el usuario ya fue borrado por otro medio, haz commit.
                        // Si es un error que el usuario no exista, haz rollback.
                        // Por seguridad, si el usuario no está, pero las FKs podrían apuntar a él, hacer commit de las reasignaciones es más seguro para la integridad.
                        // Sin embargo, si el usuario *debería* estar y no está, es un estado inconsistente.
                        // Optaremos por rollback en este caso si el usuario no se encuentra para eliminar.
                        em.getTransaction().rollback();
                    }
                    return;
                }

                em.getTransaction().commit();

                cargarUsuariosEnTabla(); // Refresca la tabla
                limpiarCamposUsuario();  // Limpia los campos del formulario
                JOptionPane.showMessageDialog(DialogGestionUsuarios, "Usuario eliminado exitosamente y sus registros han sido reasignados.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception e) { // Captura más genérica para errores de JPQL o de BD
                if (em.getTransaction().isActive()) {
                    em.getTransaction().rollback();
                }
                System.err.println("Error detallado al eliminar el usuario: " + e.toString());
                e.printStackTrace(); 
                JOptionPane.showMessageDialog(DialogGestionUsuarios, "Error al eliminar el usuario: " + e.getMessage(), "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
            } finally {
                if (em != null && em.isOpen()) {
                    em.close(); 
                }
            }
        }

    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        // TODO add your handling code here:
        limpiarCamposUsuario();
    }//GEN-LAST:event_btnLimpiarActionPerformed
    private void limpiarCamposUsuario() {
        txtUID.setText("");
        txtUNombre.setText("");
        txtUEmail.setText("");
        cboURol.setSelectedIndex(0);
        txtUNumControl.setText("");
    }
    
    private void btnELimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnELimpiarActionPerformed
        // TODO add your handling code here:
        limpiarCamposEventos();
    }//GEN-LAST:event_btnELimpiarActionPerformed
    private void limpiarCamposEventos() {
        txtEID.setText("");
        txtENombre.setText("");
        txtEDescripcion.setText("");
        txtLugar.setText("");
        cboEstado.setSelectedIndex(0);
        calendario.setDate(new java.util.Date()); // Establece la fecha actual
        txtEInicio.setText("");
        txtEFin.setText("");
    }
    
    private void btnECrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnECrearActionPerformed
        // TODO add your handling code here:
        // Crear una instancia de la entidad
        Eventos nuevo = new Eventos();
        nuevo.setNombre(txtENombre.getText());
        nuevo.setDescripcionPublica(txtEDescripcion.getText());
        nuevo.setLugarEvento(txtLugar.getText());
        nuevo.setEstadoEvento(cboEstado.getSelectedItem().toString());
        nuevo.setFechaEvento(calendario.getDate());

        // Convertir las horas de tipo String (HH:mm) a Time
        try {
            // Obtener las horas como String desde los JTextField
            String horaInicioStr = txtEInicio.getText();  // Formato: "HH:mm" (ejemplo "10:00")
            String horaFinStr = txtEFin.getText();        // Formato: "HH:mm" (ejemplo "12:00")

            // Añadir ":00" para hacer que el formato sea "HH:mm:ss" (ejemplo "10:00:00")
            //horaInicioStr += ":00";
            //horaFinStr += ":00";

            // Convertir el String a Time
            Time horaInicio = Time.valueOf(horaInicioStr);  // Convierte a Time
            Time horaFin = Time.valueOf(horaFinStr);        // Convierte a Time

            // Asignar las horas al objeto evento
            nuevo.setHoraInicioEvento(horaInicio);
            nuevo.setHoraFinEvento(horaFin);

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Error al formatear la hora. Asegúrese de que la hora esté en formato HH:mm.");
            return; // Si hay un error, salir del método
        }
        
        try {
            EventosJpaController controller = new EventosJpaController(Conexion.getEMF());
            controller.create(nuevo); // Método de instancia, no static
            cargarEventosEnTabla(); // Refresca la tabla
            limpiarCamposEventos();  // Limpia los campos del formulario
            JOptionPane.showMessageDialog(this, "Evento insertado exitosamente");
        } catch (Exception e) {
            System.err.println("Error al insertar el evento: " + e.getMessage());
        }
    }//GEN-LAST:event_btnECrearActionPerformed

    private void btnEModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEModificarActionPerformed
        // TODO add your handling code here:
        // Verificar si se ha seleccionado un evento
        if (txtEID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un evento para modificar.");
            return;
        }

        try {
            // Obtener el ID del evento desde el campo de texto
            int id = Integer.parseInt(txtEID.getText());

            // Crear el controlador JPA para eventos
            EventosJpaController controller = new EventosJpaController(Conexion.getEMF());

            // Obtener el evento desde la base de datos
            Eventos eventoExistente = controller.findEventos(id);

            if (eventoExistente != null) {
                // Actualizar los valores con los nuevos datos ingresados en el formulario
                eventoExistente.setNombre(txtENombre.getText());
                eventoExistente.setDescripcionPublica(txtEDescripcion.getText());
                eventoExistente.setLugarEvento(txtLugar.getText());
                eventoExistente.setEstadoEvento(cboEstado.getSelectedItem().toString());

                // Obtener la fecha del JCalendar y actualizar
                eventoExistente.setFechaEvento(calendario.getDate());

                // Convertir las horas de tipo String (HH:mm) a Time
                try {
                    String horaInicioStr = txtEInicio.getText(); // Formato: "HH:mm"
                    String horaFinStr = txtEFin.getText();       // Formato: "HH:mm"

                    // Añadir ":00" para convertir a "HH:mm:ss"
                    horaInicioStr += ":00";
                    horaFinStr += ":00";

                    // Convertir a tipo Time
                    Time horaInicio = Time.valueOf(horaInicioStr);
                    Time horaFin = Time.valueOf(horaFinStr);

                    // Actualizar las horas del evento
                    eventoExistente.setHoraInicioEvento(horaInicio);
                    eventoExistente.setHoraFinEvento(horaFin);

                } catch (IllegalArgumentException e) {
                    JOptionPane.showMessageDialog(this, "Error al formatear las horas. Asegúrese de que las horas estén en formato HH:mm.");
                    return; // Si hay error en el formato de horas, salir del método
                }
                controller.edit(eventoExistente);
                cargarEventosEnTabla();
                limpiarCamposEventos();
                // Mensaje de éxito
                JOptionPane.showMessageDialog(this, "Evento modificado exitosamente");
            } else {
                // Si no se encuentra el evento en la base de datos
                JOptionPane.showMessageDialog(this, "El evento no fue encontrado.");
            }
        } catch (Exception e) {
            System.err.println("Error al actualizar evento: " + e.getMessage());
        }
    }//GEN-LAST:event_btnEModificarActionPerformed

    private void txtFieldTituloConvocatoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldTituloConvocatoriaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFieldTituloConvocatoriaActionPerformed

    private void txtDescripciónActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDescripciónActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripciónActionPerformed

    private void opcionTalleresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionTalleresActionPerformed
        DialogGestionTalleres.pack();
        DialogGestionTalleres.setVisible(true);
        DialogGestionTalleres.setSize(1000, 600);
        DialogGestionTalleres.setLocationRelativeTo(this);
    }//GEN-LAST:event_opcionTalleresActionPerformed

    private void opcionConvocatoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_opcionConvocatoriasActionPerformed
        
        DialogGestionConvocatorias.setVisible(true);
        DialogGestionConvocatorias.setSize(1000,600);
        DialogGestionConvocatorias.setLocationRelativeTo(this);
    }//GEN-LAST:event_opcionConvocatoriasActionPerformed

    private void btnEEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEEliminarActionPerformed
        if (txtEID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(DialogGestionEventos, "Por favor, seleccione un evento de la tabla para eliminar.", "Ningún Evento Seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int eventoId;
        try {
            eventoId = Integer.parseInt(txtEID.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(DialogGestionEventos, "El ID del evento no es válido.", "Error de ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(DialogGestionEventos,
            "¿Está seguro de que desea eliminar este evento?\nTODOS los datos asociados (evidencias, bitácoras, participantes) también serán eliminados permanentemente.",
            "Confirmar Eliminación Definitiva", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            EntityManagerFactory emf = Conexion.getEMF();
            EntityManager em = emf.createEntityManager(); // Usaremos este EM para la transacción manual
            EventosJpaController eventosController = new EventosJpaController(emf);

            try {
                em.getTransaction().begin();

                Eventos eventoAEliminar = em.find(Eventos.class, eventoId); // Obtener el evento dentro de la transacción

                if (eventoAEliminar == null) {
                    throw new NonexistentEntityException("El evento con ID " + eventoId + " no existe.");
                }

                // 1. Eliminar Evidencias asociadas
                if (eventoAEliminar.getEvidenciasList() != null && !eventoAEliminar.getEvidenciasList().isEmpty()) {
                    EvidenciasJpaController evidenciasController = new EvidenciasJpaController(emf);
                    // Crear una copia para evitar ConcurrentModificationException si el controlador modifica la lista original
                    List<Evidencias> evidenciasCopia = new ArrayList<>(eventoAEliminar.getEvidenciasList());
                    for (Evidencias evidencia : evidenciasCopia) {
                        // Se recomienda que el controller.destroy() se llame fuera de la transacción principal
                        // o que el controller use el mismo EntityManager si se pasa como argumento.
                        // Para simplicidad, si el controller.destroy abre su propia tx, esto es problemático.
                        // Es mejor usar em.remove() directamente aquí si es seguro y CascadeType.ALL lo permite.
                        // O, si EvidenciasJpaController.destroy() es transaccional, llamarlo fuera o manejar tx anidadas (complejo).
                        // La forma más segura es em.remove() si estamos seguros de las cascadas o dependencias.
                        // Dado que EvidenciasJpaController.destroy maneja sus propias relaciones:
                        // Aquí usaremos em.remove(em.merge(evidencia)) para asegurar que la entidad está manejada por el EM actual.
                        Evidencias evManaged = em.merge(evidencia); // Asegurar que la entidad esté gestionada por 'em'
                        em.remove(evManaged);
                    }
                    // eventoAEliminar.getEvidenciasList().clear(); // Opcional, para reflejar en el objeto Java
                }

                // 2. Eliminar BitacorasEventos asociadas
                if (eventoAEliminar.getBitacorasEventosList() != null && !eventoAEliminar.getBitacorasEventosList().isEmpty()) {
                    List<BitacorasEventos> bitacorasCopia = new ArrayList<>(eventoAEliminar.getBitacorasEventosList());
                    for (BitacorasEventos bitacora : bitacorasCopia) {
                        BitacorasEventos bManaged = em.merge(bitacora);
                        em.remove(bManaged);
                    }
                    // eventoAEliminar.getBitacorasEventosList().clear();
                }

                // 3. Eliminar EventoParticipantesTalleres asociados
                if (eventoAEliminar.getEventoParticipantesTalleresList() != null && !eventoAEliminar.getEventoParticipantesTalleresList().isEmpty()) {
                    List<EventoParticipantesTalleres> participantesCopia = new ArrayList<>(eventoAEliminar.getEventoParticipantesTalleresList());
                    for (EventoParticipantesTalleres participante : participantesCopia) {
                         EventoParticipantesTalleres eptManaged = em.merge(participante);
                         em.remove(eptManaged);
                    }
                    // eventoAEliminar.getEventoParticipantesTalleresList().clear();
                }

                // El controlador de eventos también maneja las notificaciones estableciendo FKs a NULL,
                // y desvincula de ConvocatoriaOrigen y DocenteResponsable.
                // No es necesario hacerlo manualmente aquí si se va a llamar a eventosController.destroy()
                // PERO, dado que el controller.destroy() hace su propio chequeo de huérfanos,
                // y ya estamos gestionando los hijos aquí, podemos eliminar el evento directamente con el EntityManager.

                em.remove(eventoAEliminar); // Eliminar el evento principal

                em.getTransaction().commit(); // Confirmar todos los cambios (eliminación de hijos y del padre)

                cargarEventosEnTabla(); 
                limpiarCamposEventos();  
                JOptionPane.showMessageDialog(DialogGestionEventos, "Evento y todos sus datos asociados eliminados exitosamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);

            } catch (NonexistentEntityException ex) {
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                JOptionPane.showMessageDialog(DialogGestionEventos, "El evento que intenta eliminar ya no existe.", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception e) { // Captura otras posibles excepciones
                if (em.getTransaction().isActive()) em.getTransaction().rollback();
                JOptionPane.showMessageDialog(DialogGestionEventos, "Ocurrió un error al intentar eliminar el evento: " + e.getMessage(), "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            } finally {
                if (em != null && em.isOpen()) {
                    em.close();
                }
            }
        }
    }//GEN-LAST:event_btnEEliminarActionPerformed

    private void tblConvocatoriasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblConvocatoriasMouseClicked
        int filaSeleccionada = tblConvocatorias.getSelectedRow();

        if (filaSeleccionada != -1 && listaConvocatoriasCargadas != null && filaSeleccionada < listaConvocatoriasCargadas.size()) {
            Convocatorias convocatoriaSeleccionada = listaConvocatoriasCargadas.get(filaSeleccionada);

            // Suponiendo que tienes txtIdConvocatoriaDialogo para el ID
            // if(txtIdConvocatoriaDialogo != null) {
            //     txtIdConvocatoriaDialogo.setText(String.valueOf(convocatoriaSeleccionada.getIdConvocatoria()));
            // }

            txtFieldTituloConvocatoria.setText(convocatoriaSeleccionada.getTitulo());
            taDescripcionConvocatoria.setText(convocatoriaSeleccionada.getDescripcion());

            if (dateChooserFechaInscripcion != null) { // Nombre de tu JDateChooser
                dateChooserFechaInscripcion.setDate(convocatoriaSeleccionada.getFechaPublicacion());
            }
            if (dateChooserFechaLimite != null) { // Nombre de tu JDateChooser
                dateChooserFechaLimite.setDate(convocatoriaSeleccionada.getFechaLimitePropuestas());
            }

            // El campo documentoAdjuntoRuta no está en tu UI, así que no se carga.
            // Los campos idUsuarioPublica, fechaCreacion, ultimaModificacion son de sistema.
        }
    }//GEN-LAST:event_tblConvocatoriasMouseClicked

    private void btnPublicarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPublicarActionPerformed
        String titulo = txtFieldTituloConvocatoria.getText().trim();
        String descripcion = taDescripcionConvocatoria.getText().trim();
        java.util.Date fechaPublicacion = null;
        if (dateChooserFechaInscripcion != null) {
            fechaPublicacion = dateChooserFechaInscripcion.getDate();
        }
        java.util.Date fechaLimite = null;
        if (dateChooserFechaLimite != null) {
            fechaLimite = dateChooserFechaLimite.getDate();
        }

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "El título de la convocatoria es obligatorio.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            txtFieldTituloConvocatoria.requestFocus();
            return;
        }
        if (fechaPublicacion == null) {
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "La fecha de publicación/inscripción es obligatoria.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Puedes añadir más validaciones (ej. fecha límite > fecha publicación)

        Convocatorias nuevaConvocatoria = new Convocatorias();
        nuevaConvocatoria.setTitulo(titulo);
        nuevaConvocatoria.setDescripcion(descripcion);
        nuevaConvocatoria.setFechaPublicacion(fechaPublicacion);
        nuevaConvocatoria.setFechaLimitePropuestas(fechaLimite); // Puede ser null si es opcional

        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "Error: No se ha identificado al usuario actual para publicar la convocatoria.", "Error de Usuario", JOptionPane.ERROR_MESSAGE);
            return;
        }
        nuevaConvocatoria.setIdUsuarioPublica(usuarioActual);

        java.util.Date ahora = new java.util.Date();
        nuevaConvocatoria.setFechaCreacion(ahora);
        nuevaConvocatoria.setUltimaModificacion(ahora);
        // El campo documentoAdjuntoRuta no se está manejando desde esta UI.

        try {
            ConvocatoriasJpaController controller = new ConvocatoriasJpaController(Conexion.getEMF());
            controller.create(nuevaConvocatoria);
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "Convocatoria '" + nuevaConvocatoria.getTitulo() + "' publicada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cargarConvocatoriasEnTabla();
            limpiarCamposFormularioConvocatoria();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "Error al publicar la convocatoria: " + e.getMessage(), "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnPublicarActionPerformed

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        // --- Obtener el ID de la convocatoria seleccionada ---
        int filaSeleccionada = tblConvocatorias.getSelectedRow();
        String idConvocatoriaStr;

        if (filaSeleccionada != -1) {
            Object idObjeto = tblConvocatorias.getValueAt(filaSeleccionada, 0); // Asumiendo ID en columna 0
            if (idObjeto == null) {
                JOptionPane.showMessageDialog(DialogGestionConvocatorias, "No se pudo obtener el ID de la convocatoria seleccionada de la tabla.", "Error de ID", JOptionPane.WARNING_MESSAGE);
                return;
            }
            idConvocatoriaStr = idObjeto.toString();
        } else {
            // Alternativa: si tienes un txtIdConvocatoriaDialogo no editable
            // if (txtIdConvocatoriaDialogo != null && !txtIdConvocatoriaDialogo.getText().isEmpty()) {
            //     idConvocatoriaStr = txtIdConvocatoriaDialogo.getText();
            // } else {
                JOptionPane.showMessageDialog(DialogGestionConvocatorias, "Por favor, seleccione una convocatoria de la tabla para modificar.", "Convocatoria no seleccionada", JOptionPane.WARNING_MESSAGE);
                return;
            // }
        }
        // --- Fin de la obtención del ID ---

        int idConvocatoria;
        try {
            idConvocatoria = Integer.parseInt(idConvocatoriaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "El ID de la convocatoria no es válido.", "Error de ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String titulo = txtFieldTituloConvocatoria.getText().trim();
        String descripcion = taDescripcionConvocatoria.getText().trim();
        java.util.Date fechaPublicacion = (dateChooserFechaInscripcion != null) ? dateChooserFechaInscripcion.getDate() : null;
        java.util.Date fechaLimite = (dateChooserFechaLimite != null) ? dateChooserFechaLimite.getDate() : null;

        if (titulo.isEmpty()) {
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "El título de la convocatoria es obligatorio.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            txtFieldTituloConvocatoria.requestFocus();
            return;
        }
        if (fechaPublicacion == null) {
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "La fecha de publicación/inscripción es obligatoria.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ConvocatoriasJpaController controller = new ConvocatoriasJpaController(Conexion.getEMF());
        Convocatorias convocatoriaExistente = controller.findConvocatorias(idConvocatoria);

        if (convocatoriaExistente == null) {
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "No se encontró la convocatoria con ID: " + idConvocatoria, "Convocatoria No Encontrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        convocatoriaExistente.setTitulo(titulo);
        convocatoriaExistente.setDescripcion(descripcion);
        convocatoriaExistente.setFechaPublicacion(fechaPublicacion);
        convocatoriaExistente.setFechaLimitePropuestas(fechaLimite);
        convocatoriaExistente.setUltimaModificacion(new java.util.Date());
        // idUsuarioPublica no se debería cambiar en una modificación, usualmente.
        // documentoAdjuntoRuta no se maneja aquí.

        try {
            controller.edit(convocatoriaExistente);
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "Convocatoria '" + convocatoriaExistente.getTitulo() + "' actualizada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cargarConvocatoriasEnTabla();
            limpiarCamposFormularioConvocatoria();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "Error al actualizar la convocatoria: " + e.getMessage(), "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnEliminarConvocatoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarConvocatoriaActionPerformed
        // --- Obtener el ID de la convocatoria seleccionada ---
        int filaSeleccionada = tblConvocatorias.getSelectedRow();
        String idConvocatoriaStr;

        if (filaSeleccionada != -1) {
            Object idObjeto = tblConvocatorias.getValueAt(filaSeleccionada, 0); // Asumiendo ID en columna 0
            if (idObjeto == null) {
                JOptionPane.showMessageDialog(DialogGestionConvocatorias, "No se pudo obtener el ID de la convocatoria seleccionada de la tabla.", "Error de ID", JOptionPane.WARNING_MESSAGE);
                return;
            }
            idConvocatoriaStr = idObjeto.toString();
        } else {
            // Alternativa: si tienes un txtIdConvocatoriaDialogo no editable
            // if (txtIdConvocatoriaDialogo != null && !txtIdConvocatoriaDialogo.getText().isEmpty()) {
            //     idConvocatoriaStr = txtIdConvocatoriaDialogo.getText();
            // } else {
                JOptionPane.showMessageDialog(DialogGestionConvocatorias, "Por favor, seleccione una convocatoria de la tabla para eliminar.", "Convocatoria no seleccionada", JOptionPane.WARNING_MESSAGE);
                return;
            // }
        }
        // --- Fin de la obtención del ID ---

        int idConvocatoria;
        try {
            idConvocatoria = Integer.parseInt(idConvocatoriaStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(DialogGestionConvocatorias, "El ID de la convocatoria no es válido.", "Error de ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(DialogGestionConvocatorias,
            "¿Está seguro de que desea eliminar la convocatoria con ID: " + idConvocatoria + "?\nLas notificaciones y eventos asociados podrían desvincularse.",
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            ConvocatoriasJpaController controller = new ConvocatoriasJpaController(Conexion.getEMF());
            try {
                // El ConvocatoriasJpaController.destroy() ya maneja la desvinculación de Notificaciones y Eventos
                // poniendo sus claves foráneas a NULL.
                controller.destroy(idConvocatoria); 

                cargarConvocatoriasEnTabla();
                limpiarCamposFormularioConvocatoria();
                JOptionPane.showMessageDialog(DialogGestionConvocatorias, "Convocatoria eliminada exitosamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);

            } catch (NonexistentEntityException ex) {
                JOptionPane.showMessageDialog(DialogGestionConvocatorias, "La convocatoria que intenta eliminar ya no existe.", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception e) { 
                JOptionPane.showMessageDialog(DialogGestionConvocatorias, "Ocurrió un error al intentar eliminar la convocatoria: " + e.getMessage(), "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnEliminarConvocatoriaActionPerformed

    private void btnLimpiarConvocatoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarConvocatoriasActionPerformed
        limpiarCamposFormularioConvocatoria();
    }//GEN-LAST:event_btnLimpiarConvocatoriasActionPerformed

    private void btnEventoSubirEvidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEventoSubirEvidenciaActionPerformed
        int filaSeleccionada = ttEventos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(DialogGestionEventos, "Seleccione un evento de la tabla.", "Evento no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Null check para listaEventosCargados
        if (this.listaEventosCargados == null || filaSeleccionada >= this.listaEventosCargados.size()) {
             JOptionPane.showMessageDialog(DialogGestionEventos, "Error: No se pueden obtener los datos del evento (lista no cargada o índice fuera de rango).", "Error de Datos", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Eventos eventoSeleccionado = this.listaEventosCargados.get(filaSeleccionada); 

        abrirDialogoEvidencias(eventoSeleccionado, null); 
    }//GEN-LAST:event_btnEventoSubirEvidenciaActionPerformed

    private void btnSubirEvidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubirEvidenciaActionPerformed
        if (this.eventoContextoEvidencias == null) {
            JOptionPane.showMessageDialog(DialogGestionEvidencias, "Error: Contexto de Evento no establecido.", "Error de Contexto", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (this.archivoEvidenciaSeleccionado == null || !this.archivoEvidenciaSeleccionado.exists()) {
            JOptionPane.showMessageDialog(DialogGestionEvidencias, "Por favor, seleccione un archivo válido primero.", "Archivo no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String descripcion = txtDescripción.getText().trim(); // txtDescripción es tu JTextField

        if (cobTipoEvidencia.getSelectedItem() == null || cobTipoEvidencia.getSelectedItem().toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(DialogGestionEvidencias, "Por favor, seleccione un tipo de evidencia.", "Tipo no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String tipoEvidencia = cobTipoEvidencia.getSelectedItem().toString();

        // --- INICIO: Lógica de Guardado de Archivo Físico (DEBES ADAPTAR ESTA PARTE) ---
        String nombreArchivoOriginal = this.archivoEvidenciaSeleccionado.getName();
        // Define un directorio base para tus evidencias. ESTO ES SOLO UN EJEMPLO.
        // Deberías hacerlo configurable o usar una ruta más robusta.
        String directorioBaseEvidencias = "C:/academicplus_evidencias/"; // ¡EJEMPLO! CAMBIA ESTO

        // Crear subdirectorios por evento (opcional pero recomendado)
        File directorioEvento = new File(directorioBaseEvidencias + "evento_" + this.eventoContextoEvidencias.getIdEvento());
        if (!directorioEvento.exists()) {
            directorioEvento.mkdirs(); // Crea el directorio si no existe, incluyendo padres
        }

        // Crear un nombre de archivo único o usar el original (cuidado con colisiones)
        // Para este ejemplo, usaremos el original dentro del subdirectorio del evento.
        String rutaDestinoEnServidor = directorioEvento.getAbsolutePath() + File.separator + nombreArchivoOriginal;
        Path pathDestino = Paths.get(rutaDestinoEnServidor);
        Path pathFuente = this.archivoEvidenciaSeleccionado.toPath();

        try {
            Files.copy(pathFuente, pathDestino, StandardCopyOption.REPLACE_EXISTING); // Copia el archivo
            System.out.println("Archivo copiado a: " + rutaDestinoEnServidor);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(DialogGestionEvidencias, "Error al copiar el archivo al servidor: " + ex.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
            return; // No continuar si el archivo no se pudo copiar
        }
        // --- FIN: Lógica de Guardado de Archivo Físico ---

        Evidencias nuevaEvidencia = new Evidencias();
        nuevaEvidencia.setDescripcion(descripcion);
        nuevaEvidencia.setRutaArchivo(rutaDestinoEnServidor); // Guarda la ruta donde se copió el archivo
        nuevaEvidencia.setTipoEvidencia(tipoEvidencia);
        nuevaEvidencia.setIdEvento(this.eventoContextoEvidencias);
        if (this.tallerContextoEvidencias != null) {
            nuevaEvidencia.setIdTallerAsociado(this.tallerContextoEvidencias);
        }
        nuevaEvidencia.setIdUsuarioSubio(this.usuarioActual);
        nuevaEvidencia.setFechaSubida(new Date());

        try {
            EvidenciasJpaController controller = new EvidenciasJpaController(Conexion.getEMF());
            controller.create(nuevaEvidencia);
            JOptionPane.showMessageDialog(DialogGestionEvidencias, "Evidencia subida exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cargarEvidenciasEnTabla();
            limpiarCamposFormularioEvidencia();
            // this.archivoEvidenciaSeleccionado = null; // Ya se limpia en limpiarCamposFormularioEvidencia
            // if (txtRutaArchivoEvidenciaDisplay != null) txtRutaArchivoEvidenciaDisplay.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(DialogGestionEvidencias, "Error al guardar la evidencia en la BD: " + e.getMessage(), "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            // Considerar eliminar el archivo físico si la inserción en BD falla, para evitar archivos huérfanos.
            // try { Files.deleteIfExists(pathDestino); } catch (IOException ioex) { /* log error */ }
        }
    }//GEN-LAST:event_btnSubirEvidenciaActionPerformed

    private void btnDescargarEvidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDescargarEvidenciaActionPerformed
        int filaSeleccionada = tblEvidencias.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(DialogGestionEvidencias, "Seleccione una evidencia de la tabla para descargar.", "Evidencia no seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (this.listaEvidenciasCargadasDialogo != null && filaSeleccionada < this.listaEvidenciasCargadasDialogo.size()) {
            Evidencias evidenciaSeleccionada = this.listaEvidenciasCargadasDialogo.get(filaSeleccionada);
            String rutaArchivoAlmacenado = evidenciaSeleccionada.getRutaArchivo(); // Esta es la ruta donde está guardado en tu sistema

            try {
                File archivoEnServidor = new File(rutaArchivoAlmacenado);
                if (archivoEnServidor.exists()) {
                    JFileChooser fileSaver = new JFileChooser();
                    fileSaver.setSelectedFile(new File(archivoEnServidor.getName())); // Sugerir nombre original del archivo
                    fileSaver.setDialogTitle("Guardar evidencia como...");

                    int resultado = fileSaver.showSaveDialog(DialogGestionEvidencias);
                    if (resultado == JFileChooser.APPROVE_OPTION) {
                        File archivoDestinoUsuario = fileSaver.getSelectedFile();
                        // Implementar la lógica de copia del archivo desde 'archivoEnServidor' a 'archivoDestinoUsuario'
                        // Ejemplo:
                        // java.nio.file.Files.copy(archivoEnServidor.toPath(), archivoDestinoUsuario.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
                        JOptionPane.showMessageDialog(DialogGestionEvidencias, "Archivo descargado exitosamente en: " + archivoDestinoUsuario.getAbsolutePath(), "Descarga Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(DialogGestionEvidencias, "El archivo de la evidencia no fue encontrado en el servidor: " + rutaArchivoAlmacenado, "Archivo No Encontrado", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception e) { // Capturar IOException y otras
                JOptionPane.showMessageDialog(DialogGestionEvidencias, "Error al intentar descargar el archivo: " + e.getMessage(), "Error de Archivo", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(DialogGestionEvidencias, "No se pudo obtener la información de la evidencia seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnDescargarEvidenciaActionPerformed

    private void btnEliminarEvidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarEvidenciaActionPerformed
        int filaSeleccionada = tblEvidencias.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(DialogGestionEvidencias, "Seleccione una evidencia de la tabla para eliminar.", "Evidencia no seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (this.listaEvidenciasCargadasDialogo == null || filaSeleccionada >= this.listaEvidenciasCargadasDialogo.size()) {
             JOptionPane.showMessageDialog(DialogGestionEvidencias, "Error al obtener datos de la evidencia.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Evidencias evidenciaSeleccionada = this.listaEvidenciasCargadasDialogo.get(filaSeleccionada);
        int idEvidencia = evidenciaSeleccionada.getIdEvidencia();
        String rutaArchivoFisico = evidenciaSeleccionada.getRutaArchivo();

        int confirm = JOptionPane.showConfirmDialog(DialogGestionEvidencias,
            "¿Está seguro de que desea eliminar esta evidencia?\nEl archivo asociado ("+ new File(rutaArchivoFisico).getName() +") también será eliminado permanentemente.",
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            EvidenciasJpaController controller = new EvidenciasJpaController(Conexion.getEMF());
            try {
                controller.destroy(idEvidencia); // Eliminar de la BD

                // Eliminar el archivo físico del servidor/disco
                File archivoAEliminar = new File(rutaArchivoFisico);
                if (archivoAEliminar.exists()) {
                    if (!archivoAEliminar.delete()) {
                        // No bloquea la eliminación de la BD, pero informa al usuario
                        JOptionPane.showMessageDialog(DialogGestionEvidencias, "Registro de evidencia eliminado de la BD, pero no se pudo borrar el archivo físico del servidor: " + rutaArchivoFisico + ". Contacte al administrador.", "Advertencia de Archivo", JOptionPane.WARNING_MESSAGE);
                    }
                } else {
                     JOptionPane.showMessageDialog(DialogGestionEvidencias, "Registro de evidencia eliminado de la BD, pero el archivo físico no se encontró en el servidor para ser borrado: " + rutaArchivoFisico, "Advertencia de Archivo", JOptionPane.WARNING_MESSAGE);
                }

                cargarEvidenciasEnTabla(); 
                limpiarCamposFormularioEvidencia();
                JOptionPane.showMessageDialog(DialogGestionEvidencias, "Evidencia eliminada exitosamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);

            } catch (NonexistentEntityException ex) {
                JOptionPane.showMessageDialog(DialogGestionEvidencias, "La evidencia que intenta eliminar ya no existe.", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                 cargarEvidenciasEnTabla(); 
            } catch (Exception e) {
                JOptionPane.showMessageDialog(DialogGestionEvidencias, "Ocurrió un error al eliminar la evidencia: " + e.getMessage(), "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnEliminarEvidenciaActionPerformed

    private void fileChooserElegirArchivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileChooserElegirArchivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileChooserElegirArchivoActionPerformed

    private void SeleccionarArchivoEvidenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SeleccionarArchivoEvidenciaActionPerformed
        // fileChooserElegirArchivo es tu componente JFileChooser ya declarado e inicializado
        if (fileChooserElegirArchivo == null) { // Doble chequeo por si acaso
            fileChooserElegirArchivo = new JFileChooser(); // Debería estar inicializado en initComponents
        }

        fileChooserElegirArchivo.setSize(500, 250);
        fileChooserElegirArchivo.setVisible(true);
        int resultado = fileChooserElegirArchivo.showOpenDialog(DialogGestionEvidencias); // Mostrar el diálogo para abrir archivo

        if (resultado == JFileChooser.APPROVE_OPTION) {
            this.archivoEvidenciaSeleccionado = fileChooserElegirArchivo.getSelectedFile();

            // Opcional: Mostrar la ruta en un JTextField (si lo tienes en tu UI)
            // Si tienes un JTextField llamado txtRutaArchivoEvidenciaDisplay:
            // if (txtRutaArchivoEvidenciaDisplay != null) {
                //     txtRutaArchivoEvidenciaDisplay.setText(this.archivoEvidenciaSeleccionado.getAbsolutePath());
                // } else {
                //     System.out.println("Archivo seleccionado (ruta no visible en UI): " + this.archivoEvidenciaSeleccionado.getAbsolutePath());
                // }
            // Como no tienes txtRutaArchivoEvidenciaDisplay, puedes mostrarlo en consola o en un JOptionPane por ahora:
            System.out.println("Archivo seleccionado: " + this.archivoEvidenciaSeleccionado.getAbsolutePath());
            // Opcionalmente, podrías poner el nombre del archivo en el txtDescripción si es relevante:
            // if (txtDescripción.getText().isEmpty()) {
                //    txtDescripción.setText(this.archivoEvidenciaSeleccionado.getName());
                // }

        } else { // Si el usuario cancela o cierra el diálogo
            this.archivoEvidenciaSeleccionado = null;
            // if (txtRutaArchivoEvidenciaDisplay != null) {
                //     txtRutaArchivoEvidenciaDisplay.setText("");
                // }
            System.out.println("Selección de archivo cancelada.");
        }
    }//GEN-LAST:event_SeleccionarArchivoEvidenciaActionPerformed

    private void btnGuardarAsignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarAsignacionActionPerformed
        Eventos eventoSeleccionado = (Eventos) cboEventoParaAsignar.getSelectedItem();
        Talleres tallerSeleccionado = (Talleres) cboTallerParaAsignar.getSelectedItem();
        Usuarios instructorSeleccionado = (Usuarios) cboInstructorParaAsignar.getSelectedItem();
        String rolAsignado = txtRolAsignadoEnDialog.getText().trim(); // Nombre de tu JTextField para el rol

        if (eventoSeleccionado == null || tallerSeleccionado == null || instructorSeleccionado == null) {
            JOptionPane.showMessageDialog(DialogInscripcionEventoTaller, "Debe seleccionar un Evento, Taller e Instructor.", "Datos Incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (rolAsignado.isEmpty()) {
            JOptionPane.showMessageDialog(DialogInscripcionEventoTaller, "El rol asignado no puede estar vacío (ej. INSTRUCTOR, PONENTE).", "Rol Requerido", JOptionPane.WARNING_MESSAGE);
            txtRolAsignadoEnDialog.requestFocus();
            return;
        }

        // Opcional: Verificar si ya existe esta asignación exacta (Evento, Taller, Instructor)
        // La BD tiene un UNIQUE constraint, pero una verificación previa es más amigable.
        // for (EventoParticipantesTalleres ept : listaAsignacionesActuales) {
        //     if (ept.getIdEvento().equals(eventoSeleccionado) &&
        //         ept.getIdTallerImpartido().equals(tallerSeleccionado) &&
        //         ept.getIdTallerista().equals(instructorSeleccionado)) {
        //         JOptionPane.showMessageDialog(DialogInscripcionEventoTaller, "Este instructor ya está asignado a este taller en este evento.", "Asignación Duplicada", JOptionPane.WARNING_MESSAGE);
        //         return;
        //     }
        // }

        EventoParticipantesTalleres nuevaAsignacion = new EventoParticipantesTalleres();
        nuevaAsignacion.setIdEvento(eventoSeleccionado);
        nuevaAsignacion.setIdTallerImpartido(tallerSeleccionado);
        nuevaAsignacion.setIdTallerista(instructorSeleccionado);
        nuevaAsignacion.setRolParticipante(rolAsignado.toUpperCase()); // Guardar en mayúsculas por consistencia

        try {
            eptController.create(nuevaAsignacion);
            JOptionPane.showMessageDialog(DialogInscripcionEventoTaller, 
                "Instructor '" + instructorSeleccionado.getNombre() + "' asignado al taller '" + tallerSeleccionado.getNombre() + 
                "' en el evento '" + eventoSeleccionado.getNombre() + "' con rol '" + rolAsignado + "'.", 
                "Asignación Exitosa", JOptionPane.INFORMATION_MESSAGE);

            cargarTablaDeAsignaciones(); // Refrescar la tabla
            limpiarFormularioDeAsignacion(); // Limpiar los campos del formulario de asignación
        } catch (Exception e) {
            // Podría ser javax.persistence.RollbackException si viola la UNIQUE KEY
            JOptionPane.showMessageDialog(DialogInscripcionEventoTaller, "Error al guardar la asignación: " + e.getMessage(), "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnGuardarAsignacionActionPerformed

    private void btnEliminarAsignacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarAsignacionActionPerformed
        int filaSeleccionada = tblListaAsignaciones.getSelectedRow(); // Usa el nombre correcto de tu JTable
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(DialogInscripcionEventoTaller, "Seleccione una asignación de la tabla para eliminar.", "Asignación no seleccionada", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (this.listaAsignacionesActuales == null || filaSeleccionada >= this.listaAsignacionesActuales.size()) {
            JOptionPane.showMessageDialog(DialogInscripcionEventoTaller, "Error al obtener datos de la asignación.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        EventoParticipantesTalleres asignacionAEliminar = this.listaAsignacionesActuales.get(filaSeleccionada);

        int confirm = JOptionPane.showConfirmDialog(DialogInscripcionEventoTaller,
            "¿Está seguro de que desea eliminar esta asignación?\n" +
            "Evento: " + asignacionAEliminar.getIdEvento().getNombre() + "\n" +
            "Taller: " + asignacionAEliminar.getIdTallerImpartido().getNombre() + "\n" +
            "Instructor: " + asignacionAEliminar.getIdTallerista().getNombre(),
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                eptController.destroy(asignacionAEliminar.getIdEventoParticipanteTaller());
                JOptionPane.showMessageDialog(DialogInscripcionEventoTaller, "Asignación eliminada exitosamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);
                cargarTablaDeAsignaciones();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(DialogInscripcionEventoTaller, "Error al eliminar la asignación: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnEliminarAsignacionActionPerformed

    private void txtRolAsignadoEnDialogActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtRolAsignadoEnDialogActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtRolAsignadoEnDialogActionPerformed

    private void btnGenerarReporteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGenerarReporteActionPerformed
        // 1. Obtener el Evento seleccionado
        int filaSeleccionada = ttEventos.getSelectedRow(); // Asumiendo que ttEventos es tu tabla de eventos
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(DialogGestionEventos, "Por favor, seleccione un evento de la tabla para generar el reporte.", "Evento no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Eventos eventoSeleccionado = null;
        if (this.listaEventosCargados != null && filaSeleccionada < this.listaEventosCargados.size()) {
            eventoSeleccionado = this.listaEventosCargados.get(filaSeleccionada);
        }

        if (eventoSeleccionado == null) {
            JOptionPane.showMessageDialog(DialogGestionEventos, "No se pudo obtener la información del evento seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 2. Preguntar al usuario dónde guardar el archivo PDF
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Guardar Reporte PDF del Evento");
        // Sugerir un nombre de archivo
        String nombreArchivoSugerido = "Reporte_Evento_" + eventoSeleccionado.getNombre().replaceAll("[^a-zA-Z0-9.-]", "_") + ".pdf";
        fileChooser.setSelectedFile(new File(nombreArchivoSugerido));
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos PDF (*.pdf)", "pdf"));

        int userSelection = fileChooser.showSaveDialog(DialogGestionEventos); // Padre del diálogo

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File archivoParaGuardar = fileChooser.getSelectedFile();
            String rutaDestino = archivoParaGuardar.getAbsolutePath();
            // Asegurarse de que el archivo termine con .pdf
            if (!rutaDestino.toLowerCase().endsWith(".pdf")) {
                rutaDestino += ".pdf";
            }

            try {
                // 3. Obtener datos adicionales (Talleres y Participantes del Evento)
                // eptController ya debería estar inicializado en el constructor de VentanaPrincipal
                if (eptController == null) {
                     eptController = new EventoParticipantesTalleresJpaController(Conexion.getEMF());
                }
                List<EventoParticipantesTalleres> todasLasParticipaciones = eptController.findEventoParticipantesTalleresEntities();

                Map<Talleres, List<Usuarios>> talleresConInstructores = new HashMap<>();
                List<Usuarios> participantesDelEvento = new ArrayList<>(); // Lista general de participantes
                Set<Integer> idsTalleresDelEvento = new HashSet<>();

                if (todasLasParticipaciones != null) {
                    for (EventoParticipantesTalleres ept : todasLasParticipaciones) {
                        if (ept.getIdEvento() != null && ept.getIdEvento().equals(eventoSeleccionado)) {
                            Talleres taller = ept.getIdTallerImpartido();
                            Usuarios usuario = ept.getIdTallerista();
                            String rol = ept.getRolParticipante();

                            if (taller != null && usuario != null && rol != null) {
                                 idsTalleresDelEvento.add(taller.getIdTaller()); // Guardar IDs de talleres
                                if (rol.equalsIgnoreCase("INSTRUCTOR") || rol.equalsIgnoreCase("PONENTE")) {
                                    talleresConInstructores.computeIfAbsent(taller, k -> new ArrayList<>()).add(usuario);
                                } else if (rol.equalsIgnoreCase("PARTICIPANTE") || rol.equalsIgnoreCase("ASISTENTE_EVENTO")) {
                                    // Añadir a la lista general de participantes del evento (podrías querer refinar esto por taller)
                                    if (!participantesDelEvento.contains(usuario)) { // Evitar duplicados si un usuario participa en varios talleres
                                        participantesDelEvento.add(usuario);
                                    }
                                }
                            }
                        }
                    }
                }

                // Obtener los objetos Talleres completos si solo tenemos los IDs (o si talleresConInstructores.keySet() no es suficiente)
                List<Talleres> talleresDelEvento = new ArrayList<>();
                if (!idsTalleresDelEvento.isEmpty()) {
                    TalleresJpaController tallerCtrl = new TalleresJpaController(Conexion.getEMF());
                    for(Integer idTaller : idsTalleresDelEvento) {
                        Talleres t = tallerCtrl.findTalleres(idTaller);
                        if (t != null) talleresDelEvento.add(t);
                    }
                }


                // 4. Llamar al método que genera el PDF
                generarDocumentoPDFEvento(rutaDestino, eventoSeleccionado, talleresDelEvento, talleresConInstructores, participantesDelEvento);

                JOptionPane.showMessageDialog(DialogGestionEventos, "Reporte PDF generado exitosamente en:\n" + rutaDestino, "Reporte Generado", JOptionPane.INFORMATION_MESSAGE);

                // Opcional: Abrir el archivo PDF generado
                if (Desktop.isDesktopSupported()) {
                    try {
                        Desktop.getDesktop().open(new File(rutaDestino));
                    } catch (IOException ex) {
                        System.err.println("Error al abrir el PDF: " + ex.getMessage());
                    }
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(DialogGestionEventos, "Error al generar el reporte PDF: " + e.getMessage(), "Error de Generación", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnGenerarReporteActionPerformed

    private void btnLimpiarTalleresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarTalleresActionPerformed
        limpiarCamposFormularioTaller();
    }//GEN-LAST:event_btnLimpiarTalleresActionPerformed

    private void btnElliminarTallerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnElliminarTallerActionPerformed
        // --- Obtener el ID del taller seleccionado ---
        // Usaremos la misma lógica que en btnActualizarTallerActionPerformed para obtener el ID.
        // Opción B: Obtener el ID directamente de la tabla (menos robusto si hay ediciones sin reseleccionar)
        int filaSeleccionada = tblTalleres.getSelectedRow();
        String idTallerStr;

        if (filaSeleccionada != -1) {
            // Asumiendo que la columna 0 de tblTalleres contiene el ID
            Object idObjeto = tblTalleres.getValueAt(filaSeleccionada, 0);
            if (idObjeto == null) {
                JOptionPane.showMessageDialog(DialogGestionTalleres, "No se pudo obtener el ID del taller seleccionado de la tabla.", "ID No Encontrado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            idTallerStr = idObjeto.toString();
        } else {
            // Opción A: Si usas un JTextField no editable como txtIdTallerDialogo que se llena con tblTalleresMouseClicked
            // if (txtIdTallerDialogo != null && !txtIdTallerDialogo.getText().isEmpty()) {
                //     idTallerStr = txtIdTallerDialogo.getText();
                // } else {
                JOptionPane.showMessageDialog(DialogGestionTalleres, "Por favor, seleccione un taller de la tabla para eliminar.", "Taller no seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
                // }
        }
        // --- Fin de la obtención del ID ---

        int idTaller;
        try {
            idTaller = Integer.parseInt(idTallerStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(DialogGestionTalleres, "El ID del taller no es válido.", "Error de ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(DialogGestionTalleres,
            "¿Está seguro de que desea eliminar el taller con ID: " + idTaller + "?\nEsta acción podría eliminar también comentarios y participaciones asociadas.",
            "Confirmar Eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            TalleresJpaController controller = new TalleresJpaController(Conexion.getEMF());
            try {
                controller.destroy(idTaller); // El método destroy del JpaController maneja las relaciones

                cargarTalleresEnTabla();       // Actualiza la tabla de talleres
                limpiarCamposFormularioTaller(); // Limpia los campos del formulario
                JOptionPane.showMessageDialog(DialogGestionTalleres, "Taller eliminado exitosamente.", "Eliminación Exitosa", JOptionPane.INFORMATION_MESSAGE);

            } catch (IllegalOrphanException ex) {
                // Esta excepción es lanzada por el JpaController si encuentra "huérfanos ilegales".
                // Por ejemplo, si un ComentarioRevisionTaller tiene su campo idTaller como non-nullable
                // y CascadeType.ALL no está funcionando como se espera o la comprobación del controller es muy estricta.
                System.err.println("Detalles de IllegalOrphanException: " + ex.getMessages());
                ex.printStackTrace();
                JOptionPane.showMessageDialog(DialogGestionTalleres,
                    "No se puede eliminar el taller porque tiene registros asociados que no pueden ser eliminados o desvinculados automáticamente.\nDetalle: " + ex.getMessage(),
                    "Error de Eliminación (Dependencias)",
                    JOptionPane.ERROR_MESSAGE);
            } catch (NonexistentEntityException ex) {
                JOptionPane.showMessageDialog(DialogGestionTalleres, "El taller que intenta eliminar ya no existe.", "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            } catch (Exception e) { // Captura otras posibles excepciones
                JOptionPane.showMessageDialog(DialogGestionTalleres, "Ocurrió un error al intentar eliminar el taller: " + e.getMessage(), "Error de Eliminación", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnElliminarTallerActionPerformed

    private void btnAgregarTallerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTallerActionPerformed
        // 1. Validar campos obligatorios
        String nombreTaller = txtFieldNombreTaller.getText().trim();
        if (nombreTaller.isEmpty()) {
            JOptionPane.showMessageDialog(DialogGestionTalleres, "El nombre del taller es obligatorio.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            txtFieldNombreTaller.requestFocus();
            return;
        }

        // Validación para el ponente
        if (cboPonente.getSelectedItem() == null || !(cboPonente.getSelectedItem() instanceof Usuarios)) {
            // Podrías tener un item placeholder como "Seleccione un ponente..." que sea un String o un Usuario nulo.
            // Si el item es null o no es una instancia de Usuario, entonces no es válido.
            Object itemPonente = cboPonente.getSelectedItem();
            if (itemPonente == null || itemPonente.toString().equals("-- Seleccione Ponente --")) { // Ejemplo de placeholder
                JOptionPane.showMessageDialog(DialogGestionTalleres, "Debe seleccionar un ponente válido.", "Ponente no seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // Si el primer item es un Usuario válido y está seleccionado, esta condición no se cumple.
            // Si es un String placeholder y se selecciona, !(itemPonente instanceof Usuarios) será true.
        }

        // Validación para el estado del taller
        // Asumiendo que cboEstadoTaller2 es tu JComboBox para el estado
        if (cboEstadoTaller2.getSelectedItem() == null || cboEstadoTaller2.getSelectedItem().toString().trim().isEmpty() || cboEstadoTaller2.getSelectedItem().toString().equals("-- Seleccione Estado --")) { // Ejemplo de placeholder
            JOptionPane.showMessageDialog(DialogGestionTalleres, "Debe seleccionar un estado para el taller.", "Estado no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Crear y poblar el objeto Talleres
        Talleres nuevoTaller = new Talleres();
        nuevoTaller.setNombre(nombreTaller);

        // Campos que existen en la entidad Talleres
        // Asumiendo que txtDescripcionTaller es tu JTextArea. Si es taDescripcionTaller, usa ese nombre.
        if (txtDescripcionTaller != null) {
            nuevoTaller.setDescripcionPublica(txtDescripcionTaller.getText().trim());
        } else if (txtDescripcionTaller != null) { // Nombre del componente en tu diseño de VentanaPrincipal
            nuevoTaller.setDescripcionPublica(txtDescripcionTaller.getText().trim());
        }

        nuevoTaller.setRequisitosMateriales(txtFieldMaterial_Req.getText().trim());

        // Nuevo campo para la ruta del manual
        if (txtManualRuta != null) { // Asegúrate que txtManualRuta existe en tu UI
            nuevoTaller.setManualRuta(txtManualRuta.getText().trim());
        }

        nuevoTaller.setEstado((String) cboEstadoTaller2.getSelectedItem()); // Usar el nombre correcto del JComboBox de estado

        Usuarios ponenteSeleccionado = (Usuarios) cboPonente.getSelectedItem();
        nuevoTaller.setIdUsuarioProponente(ponenteSeleccionado);

        // Establecer fechas de creación y modificación
        Date fechaActual = new Date();
        nuevoTaller.setFechaCreacion(fechaActual);
        nuevoTaller.setUltimaModificacion(fechaActual);

        // Los campos como Cupo, Fecha del Taller, Duración, Evento Asociado directo
        // han sido omitidos ya que indicaste que eliminaste los que no servían
        // y estos no tenían correspondencia directa en la entidad Talleres.

        // 3. Persistir el taller
        try {
            TalleresJpaController controller = new TalleresJpaController(Conexion.getEMF());
            controller.create(nuevoTaller);

            JOptionPane.showMessageDialog(DialogGestionTalleres, "Taller '" + nuevoTaller.getNombre() + "' agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cargarTalleresEnTabla(); // Refresca la tabla de talleres
            limpiarCamposFormularioTaller(); // Limpia los campos del formulario

        } catch (Exception e) {
            JOptionPane.showMessageDialog(DialogGestionTalleres, "Error al guardar el taller: " + e.getMessage(), "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnAgregarTallerActionPerformed

    private void btnActualizarTallerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTallerActionPerformed
        // 0. Verificar si se ha seleccionado un taller (y por lo tanto, hay un ID)
        // Asumiré que tienes un JTextField no editable llamado txtIdTallerDialogo para el ID.
        // Si no tienes un campo para el ID, deberás obtener el ID del taller seleccionado de alguna otra manera.
        String idTallerStr = ""; // Ejemplo: txtIdTallerDialogo.getText();

        // --- Obtener el ID del taller seleccionado ---
        // Opción A: Desde un JTextField no editable (recomendado)
        // if (txtIdTallerDialogo != null && !txtIdTallerDialogo.getText().isEmpty()) {
            //     idTallerStr = txtIdTallerDialogo.getText();
            // } else {
            //     JOptionPane.showMessageDialog(DialogGestionTalleres, "No hay un taller seleccionado para actualizar. Por favor, seleccione un taller de la tabla.", "Taller no seleccionado", JOptionPane.WARNING_MESSAGE);
            //     return;
            // }

        // Opción B: Si no tienes un campo ID y dependes de la selección de la tabla directamente (menos robusto si el usuario edita campos sin reseleccionar)
        int filaSeleccionada = tblTalleres.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(DialogGestionTalleres, "Por favor, seleccione un taller de la tabla para actualizar.", "Taller no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Asumiendo que la columna 0 de tblTalleres contiene el ID
        Object idObjeto = tblTalleres.getValueAt(filaSeleccionada, 0);
        if (idObjeto == null) {
            JOptionPane.showMessageDialog(DialogGestionTalleres, "No se pudo obtener el ID del taller seleccionado de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        idTallerStr = idObjeto.toString();
        // --- Fin de la obtención del ID ---

        int idTaller;
        try {
            idTaller = Integer.parseInt(idTallerStr);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(DialogGestionTalleres, "El ID del taller no es válido.", "Error de ID", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // 1. Validar campos obligatorios del formulario
        String nombreTaller = txtFieldNombreTaller.getText().trim();
        if (nombreTaller.isEmpty()) {
            JOptionPane.showMessageDialog(DialogGestionTalleres, "El nombre del taller es obligatorio.", "Campo Vacío", JOptionPane.WARNING_MESSAGE);
            txtFieldNombreTaller.requestFocus();
            return;
        }

        if (cboPonente.getSelectedItem() == null || !(cboPonente.getSelectedItem() instanceof Usuarios)) {
            Object itemPonente = cboPonente.getSelectedItem();
            if (itemPonente == null || (itemPonente instanceof String && itemPonente.toString().startsWith("--"))) { // Asumiendo placeholder
                JOptionPane.showMessageDialog(DialogGestionTalleres, "Debe seleccionar un ponente válido.", "Ponente no seleccionado", JOptionPane.WARNING_MESSAGE);
                return;
            }
        }

        if (cboEstadoTaller2.getSelectedItem() == null || cboEstadoTaller2.getSelectedItem().toString().trim().isEmpty() || (cboEstadoTaller2.getSelectedItem() instanceof String && cboEstadoTaller2.getSelectedItem().toString().startsWith("--"))) { // Asumiendo placeholder
            JOptionPane.showMessageDialog(DialogGestionTalleres, "Debe seleccionar un estado para el taller.", "Estado no seleccionado", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 2. Obtener la entidad Talleres existente y actualizarla
        TalleresJpaController controller = new TalleresJpaController(Conexion.getEMF());
        Talleres tallerExistente = controller.findTalleres(idTaller);

        if (tallerExistente == null) {
            JOptionPane.showMessageDialog(DialogGestionTalleres, "No se encontró el taller con ID: " + idTaller + " en la base de datos.", "Taller No Encontrado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Actualizar los campos del objeto tallerExistente
        tallerExistente.setNombre(nombreTaller);

        // Usar el nombre correcto de tu JTextArea
        if (txtDescripcionTaller != null) {
            tallerExistente.setDescripcionPublica(txtDescripcionTaller.getText().trim());
        } else if (txtDescripcionTaller != null) {
            tallerExistente.setDescripcionPublica(txtDescripcionTaller.getText().trim());
        }

        tallerExistente.setRequisitosMateriales(txtFieldMaterial_Req.getText().trim());

        if (txtManualRuta != null) {
            tallerExistente.setManualRuta(txtManualRuta.getText().trim());
        }

        tallerExistente.setEstado((String) cboEstadoTaller2.getSelectedItem()); // Usa el nombre correcto de tu JComboBox de estado

        Usuarios ponenteSeleccionado = (Usuarios) cboPonente.getSelectedItem();
        tallerExistente.setIdUsuarioProponente(ponenteSeleccionado);

        tallerExistente.setUltimaModificacion(new Date()); // Actualizar fecha de modificación

        // 3. Persistir los cambios
        try {
            controller.edit(tallerExistente);
            JOptionPane.showMessageDialog(DialogGestionTalleres, "Taller '" + tallerExistente.getNombre() + "' actualizado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            cargarTalleresEnTabla(); // Refresca la tabla
            limpiarCamposFormularioTaller(); // Limpia los campos del formulario

        } catch (Exception e) {
            JOptionPane.showMessageDialog(DialogGestionTalleres, "Error al actualizar el taller: " + e.getMessage(), "Error de Persistencia", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnActualizarTallerActionPerformed

    private void txtManualRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtManualRutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtManualRutaActionPerformed

    private void cboEstadoTaller2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboEstadoTaller2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cboEstadoTaller2ActionPerformed

    private void tblTalleresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTalleresMouseClicked

        int filaSeleccionada = tblTalleres.getSelectedRow();

        if (filaSeleccionada != -1) {
            try {
                // Asumimos que la columna 0 de tblTalleres contiene el ID del Taller
                Object idObjeto = tblTalleres.getValueAt(filaSeleccionada, 0);
                if (idObjeto == null) {
                    JOptionPane.showMessageDialog(DialogGestionTalleres, "No se pudo obtener el ID del taller seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int idTaller = Integer.parseInt(idObjeto.toString());

                // Opcional: Si tienes un JTextField para el ID del taller en el formulario:
                // if (txtIdTallerDialog != null) {
                    //    txtIdTallerDialog.setText(String.valueOf(idTaller));
                    // }

                TalleresJpaController controller = new TalleresJpaController(Conexion.getEMF());
                Talleres tallerSeleccionado = controller.findTalleres(idTaller);

                if (tallerSeleccionado != null) {
                    // --- Poblar campos que SÍ existen en la entidad Talleres ---
                    txtFieldNombreTaller.setText(tallerSeleccionado.getNombre());

                    // JTextArea descripción
                    if (txtDescripcionTaller != null) {
                        txtDescripcionTaller.setText(tallerSeleccionado.getDescripcionPublica());
                    }
                    // else if (taDescripcionTaller != null) { // Si el nombre del componente es diferente
                        //    taDescripcionTaller.setText(tallerSeleccionado.getDescripcionPublica());
                        // }

                    txtFieldMaterial_Req.setText(tallerSeleccionado.getRequisitosMateriales());

                    // Nuevo campo: Ruta del Manual
                    if (txtManualRuta != null) { // Asegúrate que txtManualRuta existe en tu UI
                        txtManualRuta.setText(tallerSeleccionado.getManualRuta());
                    }

                    // ComboBox Ponente (Usuarios)
                    if (cboPonente != null && tallerSeleccionado.getIdUsuarioProponente() != null) {
                        Usuarios ponenteDelTaller = tallerSeleccionado.getIdUsuarioProponente();
                        DefaultComboBoxModel<Usuarios> modeloPonente = (DefaultComboBoxModel<Usuarios>) cboPonente.getModel();
                        boolean ponenteEncontrado = false;
                        for (int i = 0; i < modeloPonente.getSize(); i++) {
                            Usuarios usuarioEnCombo = modeloPonente.getElementAt(i);
                            if (usuarioEnCombo != null && usuarioEnCombo.getIdUsuario().equals(ponenteDelTaller.getIdUsuario())) {
                                cboPonente.setSelectedIndex(i);
                                ponenteEncontrado = true;
                                break;
                            }
                        }
                        if (!ponenteEncontrado) {
                            cboPonente.setSelectedItem(null); // O el índice de un item placeholder
                        }
                    } else if (cboPonente != null) {
                        cboPonente.setSelectedItem(null); // O el índice de un item placeholder
                    }

                    // ComboBox Estado Taller (Strings)
                    if (cboEstadoTaller2 != null) {
                        cboEstadoTaller2.setSelectedItem(tallerSeleccionado.getEstado());
                    }
                    // else if (cboEventoAsociado2 != null) { // Si el nombre del componente es diferente
                        //    cboEventoAsociado2.setSelectedItem(tallerSeleccionado.getEstado());
                        // }

                } else {
                    JOptionPane.showMessageDialog(DialogGestionTalleres, "No se encontró el taller con ID: " + idTaller, "Taller No Encontrado", JOptionPane.WARNING_MESSAGE);
                    limpiarCamposFormularioTaller(); // Limpiar campos si no se encuentra
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(DialogGestionTalleres, "El ID del taller en la tabla no es un número válido.", "Error de ID en Tabla", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
                limpiarCamposFormularioTaller();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(DialogGestionTalleres, "Error al cargar datos del taller: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                limpiarCamposFormularioTaller();
            }
        } else {
            // Opcional: Limpiar los campos si no se selecciona ninguna fila válida
            // limpiarCamposFormularioTaller();
        }
    }//GEN-LAST:event_tblTalleresMouseClicked

    private void txtFieldMaterial_ReqActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldMaterial_ReqActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFieldMaterial_ReqActionPerformed

    private void txtFieldNombreTallerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFieldNombreTallerActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFieldNombreTallerActionPerformed
    
    // -------------------------------------------------------------------------
    // Manipulacion de DialogGestionUsuarios
    private void cargarUsuariosEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) ttUsuarios.getModel();
        modelo.setRowCount(0); // Limpiar la tabla

        try {
            UsuariosJpaController controller = new UsuariosJpaController(Conexion.getEMF());
            List<Usuarios> listaUsuarios = controller.findUsuariosEntities();

            for (Usuarios u : listaUsuarios) {
                Object[] fila = new Object[]{
                    u.getIdUsuario(),
                    u.getNombre(),
                    u.getCorreo(),
                    u.getRol(),
                    u.getNumeroControl(),
                    u.getContrasenaHash()
                };
                modelo.addRow(fila);
            }
        } catch (Exception e) {
            System.err.println("Error al cargar los usuarios: " + e.getMessage());
        }
    }
    private void seleccionarUsuarios() {    // Muestra en elementos filas de la tabla
        ttUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                limpiarCamposUsuario();
                int selectedRow = ttUsuarios.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) ttUsuarios.getModel();
                    
                    txtUID.setText(String.valueOf((int) model.getValueAt(selectedRow, 0)));
                    txtUNombre.setText(model.getValueAt(selectedRow, 1).toString());
                    txtUEmail.setText(model.getValueAt(selectedRow, 2).toString());
                    cboURol.setSelectedItem(model.getValueAt(selectedRow, 3).toString());
                    txtUNumControl.setText(model.getValueAt(selectedRow, 4).toString());
                    passContrasena.setText(model.getValueAt(selectedRow, 5).toString());
                }
            }
        });
    }
    
    // -------------------------------------------------------------------------
    // Manipulacion de DialogGestionEventos
    // En VentanaPrincipal.java
    private void cargarEventosEnTabla() {
        DefaultTableModel modelo2 = (DefaultTableModel) ttEventos.getModel();
        modelo2.setRowCount(0); 

        try {
            EventosJpaController controller = new EventosJpaController(Conexion.getEMF());
            // ASIGNAR LA LISTA A LA VARIABLE DE INSTANCIA:
            this.listaEventosCargados = controller.findEventosEntities(); 

            SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");

            if (this.listaEventosCargados != null) {
                for (Eventos e : this.listaEventosCargados) {
                    String fechaFormateada = e.getFechaEvento() != null ? formatoFecha.format(e.getFechaEvento()) : "N/A";
                    String horaInicioFormateada = e.getHoraInicioEvento() != null ? formatoHora.format(e.getHoraInicioEvento()) : "N/A";
                    String horaFinFormateada = e.getHoraFinEvento() != null ? formatoHora.format(e.getHoraFinEvento()) : "N/A";

                    Object[] fila = new Object[]{
                        e.getIdEvento(),
                        e.getNombre(),
                        e.getDescripcionPublica(),
                        fechaFormateada,
                        horaInicioFormateada,
                        horaFinFormateada,
                        e.getLugarEvento(),
                        e.getEstadoEvento()
                    };
                    modelo2.addRow(fila);
                }
            } else {
                // Si el controlador devuelve null, inicializa la lista como vacía.
                this.listaEventosCargados = new ArrayList<>();
                System.err.println("La lista de eventos cargados desde la BD es null.");
            }
        } catch (Exception e) {
            // También inicializa en caso de excepción para evitar NPEs.
            this.listaEventosCargados = new ArrayList<>();
            System.err.println("Error al cargar los eventos: " + e.getMessage());
            e.printStackTrace(); // Para ver el error completo en la consola
        }
    }
    private void seleccionarEventos() {    // Muestra en elementos filas de la tabla
        ttEventos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                limpiarCamposEventos();
                int selectedRow = ttEventos.getSelectedRow();
                if (selectedRow != -1) {
                    DefaultTableModel model = (DefaultTableModel) ttEventos.getModel();
                    
                    txtEID.setText(String.valueOf(model.getValueAt(selectedRow, 0)));
                    txtENombre.setText(model.getValueAt(selectedRow, 1).toString());
                    txtEDescripcion.setText(model.getValueAt(selectedRow, 2).toString());
                    // **Fecha**: Convertir la fecha en el formato 'yyyy-MM-dd'
                    String fechaStr = model.getValueAt(selectedRow, 3).toString(); // Columna 3 = fecha
                    // **Fecha en JCalendar**: Convertir la fecha al tipo java.util.Date
                    try {
                        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date fecha = formatoFecha.parse(fechaStr); // Convertir fecha en String a Date
                        calendario.setDate(fecha); // Establecer fecha en el JCalendar
                    } catch (Exception ex) {
                        System.err.println("Error al convertir la fecha: " + ex.getMessage());
                    }
                    
                    // **Hora Inicio**: Convertir hora en formato 'HH:mm:ss'
                    String horaInicioStr = model.getValueAt(selectedRow, 4).toString(); // Columna 4 = hora inicio
                    if (horaInicioStr != null && !horaInicioStr.isEmpty()) {
                        txtEInicio.setText(horaInicioStr); // Mostrar en el campo txtEInicio
                    }
                    // **Hora Fin**: Convertir hora en formato 'HH:mm:ss'
                    String horaFinStr = model.getValueAt(selectedRow, 5).toString(); // Columna 5 = hora fin
                    if (horaFinStr != null && !horaFinStr.isEmpty()) {
                        txtEFin.setText(horaFinStr); // Mostrar en el campo txtEFin
                    }
                    txtLugar.setText(model.getValueAt(selectedRow, 6).toString());
                    cboEstado.setSelectedItem(model.getValueAt(selectedRow, 7).toString());                    
                }
            }
        });
    }
    

    //--------------------------------------------------------------------------
    //Manipulación de DialogGestionTalleres
    private void cargarPonentesEnComboBox() {
        DefaultComboBoxModel<Usuarios> modeloPonentes = new DefaultComboBoxModel<>();
        cboPonente.setModel(modeloPonentes); // Asignar el modelo primero para limpiar

        try {
            UsuariosJpaController controller = new UsuariosJpaController(Conexion.getEMF());
            List<Usuarios> listaUsuarios = controller.findUsuariosEntities();

            modeloPonentes.addElement(null); // Opción para "Ninguno" o "Seleccionar ponente"
                                            // Si usas null, asegúrate de manejarlo.
                                            // Alternativamente, crea un objeto Usuarios placeholder.

            for (Usuarios usuario : listaUsuarios) {
                // Opcional: Filtrar por rol, por ejemplo, si solo quieres "TALLERISTA" o "DOCENTE"
                // if (usuario.getRol().equals("TALLERISTA") || usuario.getRol().equals("DOCENTE")) {
                //     modeloPonentes.addElement(usuario);
                // }
                modeloPonentes.addElement(usuario); // Añade el objeto Usuario directamente
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(DialogGestionTalleres, "Error al cargar los ponentes: " + e.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    private void cargarEventosAsociadosEnComboBox() {
        DefaultComboBoxModel<Eventos> modeloEventos = new DefaultComboBoxModel<>();

        try {
            EventosJpaController controller = new EventosJpaController(Conexion.getEMF());
            List<Eventos> listaEventos = controller.findEventosEntities();

            modeloEventos.addElement(null); // Opción para "Ninguno" o "Seleccionar evento"

            for (Eventos evento : listaEventos) {
                // Opcional: Podrías filtrar eventos por estado, por ejemplo, solo eventos "PLANIFICADO" o "CONFIRMADO"
                // if (evento.getEstadoEvento().equals("PLANIFICADO") || evento.getEstadoEvento().equals("CONFIRMADO")) {
                //     modeloEventos.addElement(evento);
                // }
                modeloEventos.addElement(evento); // Añade el objeto Evento directamente
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(DialogGestionTalleres, "Error al cargar los eventos: " + e.getMessage(), "Error de Carga", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    // Método de ejemplo para limpiar campos (debes crearlo o adaptarlo)
    private void limpiarCamposFormularioTaller() {
        txtFieldNombreTaller.setText("");
        txtDescripcionTaller.setText(""); // Suponiendo que es tu JTextArea
        txtFieldMaterial_Req.setText("");
        txtManualRuta.setText("");
        

        if (cboPonente.getItemCount() > 0) {
            cboPonente.setSelectedIndex(0); // O -1 si el primer elemento no es un placeholder válido
        }
        
        if (cboEstadoTaller2.getItemCount() > 0) { // Suponiendo que cboEstadoTaller2 es el JComboBox de estado
            cboEstadoTaller2.setSelectedIndex(0);
        }
    }

    // Método de ejemplo para cargar la tabla (debes crearlo o adaptarlo)
    // En VentanaPrincipal.java
    private void cargarTalleresEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblTalleres.getModel();
        modelo.setRowCount(0); 

        TalleresJpaController controller = new TalleresJpaController(Conexion.getEMF());
        // ASIGNAR LA LISTA A LA VARIABLE DE INSTANCIA:
        this.listaTalleresCargados = controller.findTalleresEntities(); 

        if (this.listaTalleresCargados != null) {
            for (Talleres taller : this.listaTalleresCargados) {
                Object[] row = new Object[]{
                    taller.getIdTaller(),
                    taller.getNombre(),
                    // Las columnas de tu tabla deben coincidir. El código que tenías:
                    // taller.getDescripcionPublica(), // Esta columna no está en el DefaultTableModel de tu initComponents
                    taller.getIdUsuarioProponente() != null ? taller.getIdUsuarioProponente().getNombre() : "N/A",
                    taller.getEstado()
                };
                modelo.addRow(row);
            }
        } else {
            // Si el controlador devuelve null, inicializa la lista como vacía para evitar NPEs futuros.
            this.listaTalleresCargados = new ArrayList<>();
            System.err.println("La lista de talleres cargados desde la BD es null.");
        }
    }

    //Manipilación de DialogGestionEventos
    private void cargarConvocatoriasEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblConvocatorias.getModel();
        modelo.setRowCount(0); // Limpiar tabla existente

        ConvocatoriasJpaController controller = new ConvocatoriasJpaController(Conexion.getEMF());
        this.listaConvocatoriasCargadas = controller.findConvocatoriasEntities();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Formato para mostrar fechas

        if (this.listaConvocatoriasCargadas != null) {
            for (Convocatorias conv : this.listaConvocatoriasCargadas) {
                Object[] fila = new Object[]{
                    conv.getIdConvocatoria(),
                    conv.getTitulo(),
                    conv.getFechaPublicacion() != null ? sdf.format(conv.getFechaPublicacion()) : "N/A",
                    conv.getFechaLimitePropuestas() != null ? sdf.format(conv.getFechaLimitePropuestas()) : "N/A",
                    conv.getIdUsuarioPublica() != null ? conv.getIdUsuarioPublica().getNombre() : "N/A"
                };
                modelo.addRow(fila);
            }
        }
    }
        private void limpiarCamposFormularioConvocatoria() {
        // if (txtIdConvocatoriaDialogo != null) {
        //     txtIdConvocatoriaDialogo.setText("");
        // }
        txtFieldTituloConvocatoria.setText("");
        taDescripcionConvocatoria.setText("");
        if (dateChooserFechaInscripcion != null) {
            dateChooserFechaInscripcion.setDate(null);
        }
        if (dateChooserFechaLimite != null) {
            dateChooserFechaLimite.setDate(null);
        }
        // Limpia cualquier otro campo que hayas añadido para la convocatoria
    }
    
    //Manipulavion de DialogGestionEvidencias
    private void abrirDialogoEvidencias(Eventos eventoCtx, Talleres tallerCtx) {
        if (eventoCtx == null) {
            JOptionPane.showMessageDialog(this, "Se requiere un Evento de contexto para gestionar evidencias.", "Error de Contexto", JOptionPane.ERROR_MESSAGE);
            return;
        }
        this.eventoContextoEvidencias = eventoCtx;
        this.tallerContextoEvidencias = tallerCtx;

        // Actualizar título del diálogo (TITULO2 es el JLabel en DialogGestionEvidencias)
        String tituloDialogo = "Gestión de Evidencias para: Evento - " + eventoCtx.getNombre();
        if (tallerCtx != null) {
            tituloDialogo += " (Taller: " + tallerCtx.getNombre() + ")";
        }
        TITULO2.setText(tituloDialogo); // Asumiendo que TITULO2 es tu JLabel para el título

        poblarComboTipoEvidencia(); // Llena el ComboBox con los tipos de evidencia
        cargarEvidenciasEnTabla();  // Carga las evidencias existentes para el contexto
        limpiarCamposFormularioEvidencia(); // Limpia el formulario de subida

        DialogGestionEvidencias.pack();
        DialogGestionEvidencias.setLocationRelativeTo(null); // O el diálogo padre si es modal a otro diálogo
        DialogGestionEvidencias.setSize(1000, 500);
        DialogGestionEvidencias.setVisible(true);
    }
        
    private void poblarComboTipoEvidencia() {
        // Los valores ENUM de tu base de datos para Evidencias.tipo_evidencia
        String[] tipos = {"FOTO", "VIDEO", "DOCUMENTO_GENERAL", "LISTA_ASISTENCIA", "RECONOCIMIENTO_PDF", "OTRO"};
        // 'cobTipoEvidencia' es el nombre de tu JComboBox en DialogGestionEvidencias
        cobTipoEvidencia.setModel(new DefaultComboBoxModel<>(tipos));
    }
    
    private void limpiarCamposFormularioEvidencia() {
        // Limpiar el campo de texto para la descripción/título de la evidencia.
        // Tu VentanaPrincipal.java declara 'txtDescripción' como JTextField.
        if (txtDescripción != null) {
            txtDescripción.setText("");
        }
        // El 'else if (taDescripcionEvidencia != null)' no es necesario si 'txtDescripción'
        // es el único componente que usas para la entrada de texto descriptivo.

        // Resetear el ComboBox del tipo de evidencia al primer ítem (o a un índice por defecto).
        // Asumimos que el primer ítem es una opción válida o un placeholder como "Seleccione tipo".
        if (cobTipoEvidencia != null && cobTipoEvidencia.getItemCount() > 0) {
            cobTipoEvidencia.setSelectedIndex(0);
        }

        // Resetear la variable que almacena el archivo seleccionado por el JFileChooser.
        this.archivoEvidenciaSeleccionado = null;

        // Limpiar el JTextField que muestra la ruta del archivo seleccionado.
        // NOTA: No tienes un JTextField llamado 'txtRutaArchivoEvidenciaDisplay'
        // declarado en tu VentanaPrincipal.java. Si añades este componente a tu GUI
        // para mostrar la ruta del archivo, esta línea funcionará. De lo contrario,
        // puedes eliminarla o mantenerla comentada.
        /*
        if (txtRutaArchivoEvidenciaDisplay != null) {
            txtRutaArchivoEvidenciaDisplay.setText("");
        }
        */

        // Quitar cualquier selección en la tabla de evidencias.
        if (tblEvidencias != null) {
            tblEvidencias.clearSelection();
        }

        // Opcional: Si el JFileChooser mantiene el último directorio abierto,
        // podrías querer resetearlo a un directorio por defecto, aunque usualmente
        // no es necesario para simplemente limpiar el formulario.
        // if (fileChooserElegirArchivo != null) {
        //     fileChooserElegirArchivo.setSelectedFile(null); // Limpia la selección interna del chooser
        // }
    }
    
    // En VentanaPrincipal.java
    private void cargarEvidenciasEnTabla() {
        DefaultTableModel modelo = (DefaultTableModel) tblEvidencias.getModel();
        modelo.setRowCount(0);

        if (this.listaEvidenciasCargadasDialogo == null) { // Inicializar si es null
            this.listaEvidenciasCargadasDialogo = new ArrayList<>();
        }
        this.listaEvidenciasCargadasDialogo.clear(); // Limpiar antes de cargar

        if (this.eventoContextoEvidencias == null) {
            // No hay contexto de evento, la tabla permanece vacía.
            // Puedes mostrar un mensaje si lo deseas.
            return;
        }

        EvidenciasJpaController controller = new EvidenciasJpaController(Conexion.getEMF());
        List<Evidencias> todasLasEvidenciasDelSistema = controller.findEvidenciasEntities();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Evidencias evidencia : todasLasEvidenciasDelSistema) {
            boolean perteneceAlEvento = evidencia.getIdEvento() != null && 
                                      evidencia.getIdEvento().getIdEvento().equals(this.eventoContextoEvidencias.getIdEvento());

            boolean cargarEstaEvidencia = false;

            if (perteneceAlEvento) {
                if (this.tallerContextoEvidencias != null) { // Contexto específico de un Taller (dentro del Evento)
                    if (evidencia.getIdTallerAsociado() != null &&
                        evidencia.getIdTallerAsociado().getIdTaller().equals(this.tallerContextoEvidencias.getIdTaller())) {
                        cargarEstaEvidencia = true;
                    }
                } else { // Contexto solo de un Evento (evidencias generales del evento, no de un taller específico)
                    if (evidencia.getIdTallerAsociado() == null) { 
                        cargarEstaEvidencia = true;
                    }
                }
            }

            if (cargarEstaEvidencia) {
                this.listaEvidenciasCargadasDialogo.add(evidencia); // Añadir a la lista de respaldo
                Object[] fila = new Object[]{
                    // Considera añadir una columna para el ID de la evidencia si facilita las cosas, aunque no sea visible.
                    // Por ahora, basaremos la obtención del objeto en el índice de listaEvidenciasCargadasDialogo.
                    evidencia.getRutaArchivo() != null ? new java.io.File(evidencia.getRutaArchivo()).getName() : "N/A",
                    evidencia.getTipoEvidencia(),
                    evidencia.getDescripcion(),
                    evidencia.getFechaSubida() != null ? sdf.format(evidencia.getFechaSubida()) : "N/A",
                    evidencia.getIdUsuarioSubio() != null ? evidencia.getIdUsuarioSubio().getNombre() : "N/A"
                };
                modelo.addRow(fila);
            }
        }
    }
    
    //Dialog
    private void opcionInscripcionActionPerformed() {                                                
        if (usuarioActual == null) {
            JOptionPane.showMessageDialog(this, "Error: No hay un usuario activo.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // Ajustar el título del diálogo si es un JLabel, ej. TITULO6
        //if (TITULO6 != null) TITULO6.setText("Asignar Instructor a Taller en Evento");

        // Cargar datos para los ComboBoxes de asignación
        cargarEventosParaAsignacionComboBox();    // Nuevo método
        cargarTalleresParaAsignacionComboBox();   // Nuevo método
        cargarInstructoresParaAsignacionComboBox(); // Nuevo método

        txtRolAsignadoEnDialog.setText("INSTRUCTOR"); // Valor por defecto para el rol

        cargarTablaDeAsignaciones(); // Nuevo método para llenar tblListaAsignaciones

        limpiarFormularioDeAsignacion(); // Nuevo método para limpiar selecciones iniciales

        DialogInscripcionEventoTaller.pack();
        DialogInscripcionEventoTaller.setLocationRelativeTo(this);
        DialogInscripcionEventoTaller.setVisible(true);
    }
    
    private void cargarEventosParaAsignacionComboBox() {
    DefaultComboBoxModel<Eventos> modelo = new DefaultComboBoxModel<>();
    // Asume que tu JComboBox se llama cboEventoParaAsignar
    cboEventoParaAsignar.setModel(modelo); 
    modelo.addElement(null); // Opción para "Seleccionar Evento"
    try {
        // listaEventosCargados ya se llena en el constructor o al abrir DialogGestionEventos
        // Si no, necesitas un EventosJpaController aquí.
        // Asumiremos que listaEventosCargados está actualizada.
        if (this.listaEventosCargados != null) {
            for (Eventos evento : this.listaEventosCargados) {
                // Filtrar por estados relevantes si es necesario
                if (evento.getEstadoEvento() != null &&
                    (evento.getEstadoEvento().equalsIgnoreCase("PLANIFICADO") ||
                     evento.getEstadoEvento().equalsIgnoreCase("CONFIRMADO") ||
                     evento.getEstadoEvento().equalsIgnoreCase("EN_CURSO"))) {
                    modelo.addElement(evento);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void cargarTalleresParaAsignacionComboBox() {
    DefaultComboBoxModel<Talleres> modelo = new DefaultComboBoxModel<>();
    // Asume que tu JComboBox se llama cboTallerParaAsignar
    cboTallerParaAsignar.setModel(modelo);
    modelo.addElement(null); // Opción para "Seleccionar Taller"
    try {
        // listaTalleresCargados ya se llena en el constructor o al abrir DialogGestionTalleres
        // Asumiremos que está actualizada.
        if (this.listaTalleresCargados != null) {
            for (Talleres taller : this.listaTalleresCargados) {
                 // Filtrar por estados relevantes si es necesario, ej. "APROBADO"
                if (taller.getEstado() != null && taller.getEstado().equalsIgnoreCase("APROBADO")) {
                    modelo.addElement(taller);
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    private void cargarInstructoresParaAsignacionComboBox() {
        DefaultComboBoxModel<Usuarios> modelo = new DefaultComboBoxModel<>();
        // Asume que tu JComboBox se llama cboInstructorParaAsignar
        cboInstructorParaAsignar.setModel(modelo);
        modelo.addElement(null); // Opción para "Seleccionar Instructor"
        this.listaInstructoresCargados.clear(); // Limpiar lista de respaldo
        try {
            UsuariosJpaController usuariosCtrl = new UsuariosJpaController(Conexion.getEMF());
            List<Usuarios> todosLosUsuarios = usuariosCtrl.findUsuariosEntities();
            if (todosLosUsuarios != null) {
                for (Usuarios usr : todosLosUsuarios) {
                    if (usr.getRol() != null && 
                        (usr.getRol().equalsIgnoreCase("DOCENTE") || usr.getRol().equalsIgnoreCase("TALLERISTA"))) {
                        modelo.addElement(usr);
                        this.listaInstructoresCargados.add(usr); // Guardar para referencia si es necesario
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void cargarTablaDeAsignaciones() {
        // Asume que tu JTable se llama tblListaAsignaciones (antes tblTalleresDisponibles)
        String[] encabezados = {"ID Asig.", "Evento", "Taller", "Instructor/Ponente", "Rol Asignado"};
        DefaultTableModel modeloTabla = new DefaultTableModel(null, encabezados);
        tblListaAsignaciones.setModel(modeloTabla); // Usa el nombre correcto de tu JTable

        if (this.listaAsignacionesActuales == null) {
            this.listaAsignacionesActuales = new ArrayList<>();
        }
        this.listaAsignacionesActuales.clear();

        try {
            List<EventoParticipantesTalleres> todasLasAsignaciones = eptController.findEventoParticipantesTalleresEntities();
            if (todasLasAsignaciones != null) {
                for (EventoParticipantesTalleres asignacion : todasLasAsignaciones) {
                    // Mostrar todas las asignaciones o filtrar por roles de instructor/ponente
                     if (asignacion.getIdEvento() != null && asignacion.getIdTallerImpartido() != null && asignacion.getIdTallerista() != null) {
                        this.listaAsignacionesActuales.add(asignacion);
                        Object[] fila = new Object[]{
                            asignacion.getIdEventoParticipanteTaller(),
                            asignacion.getIdEvento().getNombre(),
                            asignacion.getIdTallerImpartido().getNombre(),
                            asignacion.getIdTallerista().getNombre(),
                            asignacion.getRolParticipante()
                        };
                        modeloTabla.addRow(fila);
                    }
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(DialogInscripcionEventoTaller, "Error al cargar asignaciones: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void limpiarFormularioDeAsignacion() {
        if (cboEventoParaAsignar.getItemCount() > 0) cboEventoParaAsignar.setSelectedIndex(0); // O setSelectedItem(null)
        if (cboTallerParaAsignar.getItemCount() > 0) cboTallerParaAsignar.setSelectedIndex(0);
        if (cboInstructorParaAsignar.getItemCount() > 0) cboInstructorParaAsignar.setSelectedIndex(0);
        txtRolAsignadoEnDialog.setText("INSTRUCTOR"); // Valor por defecto
        if (tblListaAsignaciones != null) tblListaAsignaciones.clearSelection();
    }
    
    //ReportePDF
    private void generarDocumentoPDFEvento(String filePath, Eventos evento,
                                         List<Talleres> talleresDelEvento,
                                         Map<Talleres, List<Usuarios>> instructoresPorTaller,
                                         List<Usuarios> participantes) throws DocumentException, FileNotFoundException {

        Document document = new Document(); // Usa com.lowagie.text.Document

        try {
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // --- Definición de Fuentes ---
            Font fuenteTituloPrincipal = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.BOLD);
            Font fuenteSubtituloSeccion = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font fuenteNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);
            Font fuenteEncabezadoTabla = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10); // <--- DEFINICIÓN
            Font fuenteCelda = FontFactory.getFont(FontFactory.HELVETICA, 9);                // <--- DEFINICIÓN

            // --- Título del Reporte ---
            Paragraph tituloReporte = new Paragraph("Reporte del Evento: " + (evento.getNombre() != null ? evento.getNombre() : "N/A"), fuenteTituloPrincipal);
            tituloReporte.setAlignment(Element.ALIGN_CENTER);
            document.add(tituloReporte);
            document.add(new Paragraph("\n")); 

            // --- Detalles del Evento ---
            document.add(new Paragraph("Detalles del Evento:", fuenteSubtituloSeccion));
            document.add(new Paragraph("Fecha: " + (evento.getFechaEvento() != null ? new SimpleDateFormat("dd/MM/yyyy").format(evento.getFechaEvento()) : "N/A"), fuenteNormal));
            document.add(new Paragraph("Lugar: " + (evento.getLugarEvento() != null ? evento.getLugarEvento() : "N/A"), fuenteNormal));
            document.add(new Paragraph("Estado: " + (evento.getEstadoEvento() != null ? evento.getEstadoEvento() : "N/A"), fuenteNormal));
            document.add(new Paragraph("Descripción: " + (evento.getDescripcionPublica() != null ? evento.getDescripcionPublica() : ""), fuenteNormal));
            document.add(new Paragraph("\n"));

            // --- Sección de Talleres ---
            document.add(new Paragraph("Talleres Ofrecidos en el Evento:", fuenteSubtituloSeccion));
            if (talleresDelEvento == null || talleresDelEvento.isEmpty()) {
                document.add(new Paragraph("  No hay talleres registrados para este evento.", fuenteNormal));
            } else {
                PdfPTable tablaTalleres = new PdfPTable(3); 
                tablaTalleres.setWidthPercentage(100); 
                tablaTalleres.setSpacingBefore(10f);
                tablaTalleres.setSpacingAfter(10f);

                tablaTalleres.addCell(new PdfPCell(new Phrase("Nombre del Taller", fuenteEncabezadoTabla)));
                tablaTalleres.addCell(new PdfPCell(new Phrase("Descripción Breve", fuenteEncabezadoTabla)));
                tablaTalleres.addCell(new PdfPCell(new Phrase("Instructor(es)", fuenteEncabezadoTabla)));

                for (Talleres taller : talleresDelEvento) {
                    tablaTalleres.addCell(new Phrase(taller.getNombre() != null ? taller.getNombre() : "N/A", fuenteCelda));
                    tablaTalleres.addCell(new Phrase(taller.getDescripcionPublica() != null ? taller.getDescripcionPublica() : "N/A", fuenteCelda));

                    StringBuilder nombresInstructores = new StringBuilder();
                    List<Usuarios> instructores = instructoresPorTaller.get(taller);
                    if (instructores != null && !instructores.isEmpty()) {
                        for (int i = 0; i < instructores.size(); i++) {
                            nombresInstructores.append(instructores.get(i).getNombre());
                            if (i < instructores.size() - 1) {
                                nombresInstructores.append(",\n"); // Salto de línea para múltiples instructores
                            }
                        }
                    } else {
                        nombresInstructores.append("No asignado(s)");
                    }
                    tablaTalleres.addCell(new Phrase(nombresInstructores.toString(), fuenteCelda));
                }
                document.add(tablaTalleres);
            }
            document.add(new Paragraph("\n"));

            // --- Sección de Participantes ---
            document.add(new Paragraph("Participantes del Evento/Talleres:", fuenteSubtituloSeccion));
            if (participantes == null || participantes.isEmpty()) {
                document.add(new Paragraph("  No hay participantes registrados.", fuenteNormal));
            } else {
                PdfPTable tablaParticipantes = new PdfPTable(3); 
                tablaParticipantes.setWidthPercentage(100);
                tablaParticipantes.setSpacingBefore(10f);

                tablaParticipantes.addCell(new PdfPCell(new Phrase("Nombre Participante", fuenteEncabezadoTabla)));
                tablaParticipantes.addCell(new PdfPCell(new Phrase("Correo", fuenteEncabezadoTabla)));
                tablaParticipantes.addCell(new PdfPCell(new Phrase("No. Control", fuenteEncabezadoTabla)));

                Set<Integer> idsParticipantesUnicos = new HashSet<>(); 
                for (Usuarios p : participantes) {
                    if (p != null && p.getIdUsuario() != null && idsParticipantesUnicos.add(p.getIdUsuario())) { 
                         tablaParticipantes.addCell(new Phrase(p.getNombre() != null ? p.getNombre() : "N/A", fuenteCelda));
                         tablaParticipantes.addCell(new Phrase(p.getCorreo() != null ? p.getCorreo() : "N/A", fuenteCelda));
                         tablaParticipantes.addCell(new Phrase(p.getNumeroControl() != null ? p.getNumeroControl() : "N/A", fuenteCelda));
                    }
                }
                document.add(tablaParticipantes);
            }

        } catch (DocumentException | FileNotFoundException de) {
            System.err.println("Error de Documento/Archivo al generar PDF: " + de.getMessage());
            de.printStackTrace();
            throw de; 
        } catch (Exception e) { // Captura general por si algo más falla
            System.err.println("Error general al generar PDF: " + e.getMessage());
            e.printStackTrace();
            // Envolver en DocumentException o una excepción personalizada si prefieres
            // para mantener la firma del método, o cambiar la firma del método a 'throws Exception'.
            // Aquí relanzo como RuntimeException para no cambiar la firma, pero puedes ajustarlo.
            throw new RuntimeException("Error inesperado durante la generación del PDF", e);
        } finally {
            if (document != null && document.isOpen()) {
                document.close(); // ¡Muy importante cerrar el documento!
            }
        }
    }
        /**
     * @param args the command line arguments
     */
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
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(VentanaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new VentanaPrincipal(null).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog DialogGestionConvocatorias;
    private javax.swing.JDialog DialogGestionEventos;
    private javax.swing.JDialog DialogGestionEvidencias;
    private javax.swing.JDialog DialogGestionTalleres;
    private javax.swing.JDialog DialogGestionUsuarios;
    private javax.swing.JDialog DialogInscripcionEventoTaller;
    private javax.swing.JButton SeleccionarArchivoEvidencia;
    private javax.swing.JLabel TITULO;
    private javax.swing.JLabel TITULO1;
    private javax.swing.JLabel TITULO2;
    private javax.swing.JLabel TITULO3;
    private javax.swing.JLabel TITULO4;
    private javax.swing.JLabel TITULO5;
    private javax.swing.JLabel TITULO7;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnActualizarTaller;
    private javax.swing.JButton btnAgregar;
    private javax.swing.JButton btnAgregarTaller;
    private javax.swing.JButton btnDescargarEvidencia;
    private javax.swing.JButton btnECrear;
    private javax.swing.JButton btnEEliminar;
    private javax.swing.JButton btnELimpiar;
    private javax.swing.JButton btnEModificar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JToggleButton btnEliminarAsignacion;
    private javax.swing.JButton btnEliminarConvocatoria;
    private javax.swing.JButton btnEliminarEvidencia;
    private javax.swing.JButton btnElliminarTaller;
    private javax.swing.JButton btnEventoSubirEvidencia;
    private javax.swing.JButton btnGenerarReporte;
    private javax.swing.JButton btnGuardarAsignacion;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnLimpiarConvocatorias;
    private javax.swing.JButton btnLimpiarFormAsignacion;
    private javax.swing.JButton btnLimpiarTalleres;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnPublicar;
    private javax.swing.JButton btnSubirEvidencia;
    private com.toedter.calendar.JCalendar calendario;
    private javax.swing.JComboBox<String> cboEstado;
    private javax.swing.JLabel cboEstadoTaller;
    private javax.swing.JComboBox<String> cboEstadoTaller2;
    private javax.swing.JComboBox<Eventos> cboEventoParaAsignar;
    private javax.swing.JComboBox<Usuarios> cboInstructorParaAsignar;
    private javax.swing.JComboBox<modelo.Usuarios> cboPonente;
    private javax.swing.JComboBox<Talleres> cboTallerParaAsignar;
    private javax.swing.JComboBox<String> cboURol;
    private javax.swing.JComboBox<String> cobTipoEvidencia;
    private com.toedter.calendar.JDateChooser dateChooserFechaInscripcion;
    private com.toedter.calendar.JDateChooser dateChooserFechaLimite;
    private javax.swing.JLabel encabezado;
    private javax.swing.JLabel encabezado1;
    private javax.swing.JLabel encabezado2;
    private javax.swing.JLabel encabezado4;
    private javax.swing.JFileChooser fileChooserElegirArchivo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator10;
    private javax.swing.JSeparator jSeparator11;
    private javax.swing.JSeparator jSeparator12;
    private javax.swing.JSeparator jSeparator13;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JSeparator jSeparator9;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblEventoAsignar;
    private javax.swing.JLabel lblInstructorAsignar;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblNumControl;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblRolAsignar;
    private javax.swing.JLabel lblTablaAsignaciones;
    private javax.swing.JLabel lblTallerAsignar;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenuBar menuBarraPrincipal;
    private javax.swing.JMenu menuGestion;
    private javax.swing.JMenu menuInscripcion;
    private javax.swing.JMenu menuSalir;
    private javax.swing.JMenuItem opcionAyuda;
    private javax.swing.JMenuItem opcionCerrarSesion;
    private javax.swing.JMenuItem opcionConvocatorias;
    private javax.swing.JMenuItem opcionEventos;
    private javax.swing.JMenuItem opcionInscripcion;
    private javax.swing.JMenuItem opcionSalir;
    private javax.swing.JMenuItem opcionTalleres;
    private javax.swing.JMenuItem opcionUsuarios;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JPanel panelEstatus;
    private javax.swing.JPanel panelGeneral;
    private javax.swing.JPasswordField passContrasena;
    private javax.swing.JTextArea taDescripcionConvocatoria;
    private javax.swing.JTable tblConvocatorias;
    private javax.swing.JTable tblEvidencias;
    private javax.swing.JTable tblListaAsignaciones;
    private javax.swing.JTable tblTalleres;
    private javax.swing.JTable ttEventos;
    private javax.swing.JTable ttUsuarios;
    private javax.swing.JTextArea txtDescripcionTaller;
    private javax.swing.JTextField txtDescripción;
    private javax.swing.JTextArea txtEDescripcion;
    private javax.swing.JTextField txtEFin;
    private javax.swing.JTextField txtEID;
    private javax.swing.JTextField txtEInicio;
    private javax.swing.JTextField txtENombre;
    private javax.swing.JLabel txtFecha_Hora;
    private javax.swing.JTextField txtFieldMaterial_Req;
    private javax.swing.JTextField txtFieldNombreTaller;
    private javax.swing.JTextField txtFieldTituloConvocatoria;
    private javax.swing.JTextField txtLugar;
    private javax.swing.JTextField txtManualRuta;
    private javax.swing.JLabel txtMaterialReq;
    private javax.swing.JLabel txtNombreTaller;
    private javax.swing.JLabel txtNombreTaller1;
    private javax.swing.JLabel txtNombreTaller2;
    private javax.swing.JLabel txtNombreTaller5;
    private javax.swing.JLabel txtNombreTaller6;
    private javax.swing.JTextField txtRolAsignadoEnDialog;
    private javax.swing.JTextField txtUEmail;
    private javax.swing.JTextField txtUID;
    private javax.swing.JTextField txtUNombre;
    private javax.swing.JTextField txtUNumControl;
    // End of variables declaration//GEN-END:variables
}
