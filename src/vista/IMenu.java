/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;

import control.AdmDatos;
import control.CitaJpaController;
import control.TutorJpaController;
import control.TutoradoJpaController;
import control.TutoriaJpaController;
import modelo.Cita;
import modelo.DatosTablaCitas;
import modelo.MTablaCita;
import modelo.MTtutor;
import modelo.Tutor;
import modelo.Tutorado;
import modelo.Tutoria;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.MTcita;
import modelo.MTtutorado;
import modelo.MTtutoria;


/**
 *
 * @author Hp EliteBook
 */
public class IMenu extends javax.swing.JFrame {
private Tutor tutor;
private TutorJpaController cTutor;
private List<Tutor> tutores;
private Tutorado tutorado;
private TutoradoJpaController cTutorado;
private List<Tutorado> tutorados;
private MTtutor mtt;
private DefaultListModel mestudiantes, mtutorados;
private Map<String,Tutorado> tutorado_nom = new HashMap<>();
private final String SELECCIONA = "Selecciona Tutor";
private final String SELECCIONADO  = "Tutor Seleccionado";
private String[] Carreras = {"Sistemas","Civil","Administracion", "Electronica"};
private SpinnerNumberModel snm;
private int max = 50; //Regla del negocioy
private int min = 0;
private Cita cita;
private List<Cita> citas;
private AdmDatos adm;
private List<Tutorado> tutorados_s;
private Map<String,Tutor> tutor_nom = new HashMap<>();
private Map<Integer,Cita> cita_horas = new HashMap<>();
private MTablaCita modTabCita;
private ArrayList<DatosTablaCitas> datosCitas;
private CitaJpaController cCita;
private Tutoria tutoria;
private TutoriaJpaController cTutoria;
private List<Tutoria> tutorias;
private Date fecha;
private ImageIcon iconMiniatura;


private MTcita modeloTablaVerCitas; // O el nombre de tu nuevo TableModel, ej: MTVerCitas
private List<Cita> citasDelTutorado; // Para almacenar las citas encontradas

private MTtutor modeloTablaAdmTutores; // Ya tienes 'mtt', puedes renombrarlo o usarlo.
private MTtutorado modeloTablaAdmEstudiantes;
private MTcita modeloTablaAdmCitas;
private MTtutoria modeloTablaAdmTutorias;

    /**
     * Creates new form IMenuTutorias
     */
    public IMenu() {
        initComponents();
        
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        cTutor = new TutorJpaController(AdmDatos.getEnf());
        tutores = cTutor.findTutorEntities();
        cTutorado = new TutoradoJpaController(AdmDatos.getEnf());
        tutorados = cTutorado.findTutoradoEntities();
        cCita = new CitaJpaController(AdmDatos.getEnf());
        citas = cCita.findCitaEntities();
        cTutoria =  new TutoriaJpaController(AdmDatos.getEnf());
        // Inicializacion de Agregar Tutor
        mtt = new MTtutor(tutores);
        tabAdmTutores.setModel(mtt);

        // Inicializacion de Crear Tutoria
        //FechaActual.setText("Fecha: " + getFecha());
        //cargarTutoresCita();   
        //Inicializacion de Asignar Tutor
        //cargarTutores();
        cargarEstudiantes();
        //estudiantes.setModel(mestudiantes);
        //estudiantes.setSelectionMode(1);
        //tutoradoss.setModel(mtutorados);
        iconMiniatura = redimensionarImagen("imagenes/icono.png", 350, 500); // NUEVA LÍNEA (relativa al paquete 'vista')
        //ImagenTutor.setIcon(iconMiniatura);
       
        citasDelTutorado = new ArrayList<>(); // Inicializa la lista
        modeloTablaVerCitas = new MTcita(citasDelTutorado); // Usa tu TableModel para citas (MTcita o uno nuevo)
        tabVerCitasCitas.setModel(modeloTablaVerCitas);
        
        modeloTablaAdmTutores = new MTtutor(this.tutores); // 'this.tutores' es tu List<Tutor>
        tabAdmTutores.setModel(modeloTablaAdmTutores);

        modeloTablaAdmEstudiantes = new MTtutorado(this.tutorados); // 'this.tutorados' es tu List<Tutorado>
        tabAdmEstudiantes.setModel(modeloTablaAdmEstudiantes);

        modeloTablaAdmCitas = new MTcita(this.citas); // 'this.citas' es tu List<Cita>
        tabAdmCitas.setModel(modeloTablaAdmCitas);

        this.tutorias = cTutoria.findTutoriaEntities(); // Asegúrate de que 'tutorias' se carga
        modeloTablaAdmTutorias = new MTtutoria(this.tutorias); // 'this.tutorias' es tu List<Tutoria>
        tabAdmTutorias.setModel(modeloTablaAdmTutorias);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Menu = new javax.swing.JTabbedPane();
        panelTutorados = new javax.swing.JPanel();
        tabVerCitas = new javax.swing.JTabbedPane();
        panelVerCitas = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        txtVerCitasNumControl = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        btnVerCitasBuscar = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        tabVerCitasCitas = new javax.swing.JTable();
        jSeparator1 = new javax.swing.JSeparator();
        panelTutores = new javax.swing.JPanel();
        tabAdministrador = new javax.swing.JTabbedPane();
        panelAdmControl = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabAdmTutores = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabAdmEstudiantes = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tabAdmCitas = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        tabAdmTutorias = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        btnAdmTutor = new javax.swing.JButton();
        btnAdmRealizarTutoria = new javax.swing.JButton();
        btnAdmCitas = new javax.swing.JButton();
        btnAdmTutorados = new javax.swing.JButton();
        btnAdmAsignar = new javax.swing.JButton();
        tabTutor = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        btnAdmCitas1 = new javax.swing.JButton();
        btnAdmRealizarTutoria1 = new javax.swing.JButton();
        panelEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Menu.setTabPlacement(javax.swing.JTabbedPane.LEFT);

        panelVerCitas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel12.setText("Buscar Citas");

        txtVerCitasNumControl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtVerCitasNumControlActionPerformed(evt);
            }
        });

        jLabel21.setText("Ingrese No Control:");

        btnVerCitasBuscar.setText("Buscar");
        btnVerCitasBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerCitasBuscarActionPerformed(evt);
            }
        });

        tabVerCitasCitas.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane8.setViewportView(tabVerCitasCitas);

        javax.swing.GroupLayout panelVerCitasLayout = new javax.swing.GroupLayout(panelVerCitas);
        panelVerCitas.setLayout(panelVerCitasLayout);
        panelVerCitasLayout.setHorizontalGroup(
            panelVerCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVerCitasLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(panelVerCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jSeparator1)
                    .addComponent(jScrollPane8)
                    .addGroup(panelVerCitasLayout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addGap(45, 45, 45)
                        .addComponent(txtVerCitasNumControl, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnVerCitasBuscar))
                    .addGroup(panelVerCitasLayout.createSequentialGroup()
                        .addGap(326, 326, 326)
                        .addComponent(jLabel12)
                        .addGap(409, 409, 409)))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        panelVerCitasLayout.setVerticalGroup(
            panelVerCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelVerCitasLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jLabel12)
                .addGap(33, 33, 33)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addGroup(panelVerCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(txtVerCitasNumControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnVerCitasBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(155, Short.MAX_VALUE))
        );

        tabVerCitas.addTab("VerCitas", panelVerCitas);

        javax.swing.GroupLayout panelTutoradosLayout = new javax.swing.GroupLayout(panelTutorados);
        panelTutorados.setLayout(panelTutoradosLayout);
        panelTutoradosLayout.setHorizontalGroup(
            panelTutoradosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTutoradosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabVerCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelTutoradosLayout.setVerticalGroup(
            panelTutoradosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabVerCitas)
        );

        Menu.addTab("Tutorado", panelTutorados);

        tabAdministrador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabAdministradorMouseClicked(evt);
            }
        });

        tabAdmTutores.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tabAdmTutores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane1.setViewportView(tabAdmTutores);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Control de Registrados");

        jLabel4.setText("Tutores Registrados");

        tabAdmEstudiantes.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tabAdmEstudiantes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane5.setViewportView(tabAdmEstudiantes);

        jLabel5.setText("Estudiantes Registrados");

        tabAdmCitas.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tabAdmCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane6.setViewportView(tabAdmCitas);

        jLabel6.setText("Citas Creadas");

        tabAdmTutorias.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        tabAdmTutorias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane7.setViewportView(tabAdmTutorias);

        jLabel11.setText("Tutorias Creadas");

        btnAdmTutor.setText("Modificar Tutores");
        btnAdmTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdmTutorActionPerformed(evt);
            }
        });

        btnAdmRealizarTutoria.setText("Realizar una Tutoria");
        btnAdmRealizarTutoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdmRealizarTutoriaActionPerformed(evt);
            }
        });

        btnAdmCitas.setText("Realizar una Cita");
        btnAdmCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdmCitasActionPerformed(evt);
            }
        });

        btnAdmTutorados.setText("Modificar Tutorados");
        btnAdmTutorados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdmTutoradosActionPerformed(evt);
            }
        });

        btnAdmAsignar.setText("Asignar Tutorados");
        btnAdmAsignar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdmAsignarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelAdmControlLayout = new javax.swing.GroupLayout(panelAdmControl);
        panelAdmControl.setLayout(panelAdmControlLayout);
        panelAdmControlLayout.setHorizontalGroup(
            panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdmControlLayout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(panelAdmControlLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdmRealizarTutoria))
                    .addGroup(panelAdmControlLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdmCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
                    .addGroup(panelAdmControlLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdmAsignar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAdmTutorados, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane5)
                    .addComponent(jScrollPane6)
                    .addComponent(jScrollPane7)
                    .addGroup(panelAdmControlLayout.createSequentialGroup()
                        .addGroup(panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAdmControlLayout.createSequentialGroup()
                                .addGap(314, 314, 314)
                                .addComponent(jLabel2))
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAdmTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelAdmControlLayout.setVerticalGroup(
            panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdmControlLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelAdmControlLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(1, 1, 1)
                        .addComponent(jLabel4))
                    .addComponent(btnAdmTutor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5)
                    .addGroup(panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAdmTutorados)
                        .addComponent(btnAdmAsignar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(btnAdmCitas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAdmControlLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAdmRealizarTutoria))
                .addContainerGap(135, Short.MAX_VALUE))
        );

        tabAdministrador.addTab("Control", panelAdmControl);

        javax.swing.GroupLayout panelTutoresLayout = new javax.swing.GroupLayout(panelTutores);
        panelTutores.setLayout(panelTutoresLayout);
        panelTutoresLayout.setHorizontalGroup(
            panelTutoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(tabAdministrador)
        );
        panelTutoresLayout.setVerticalGroup(
            panelTutoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTutoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabAdministrador)
                .addContainerGap())
        );

        Menu.addTab("Administrador", panelTutores);

        btnAdmCitas1.setText("Realizar una Cita");
        btnAdmCitas1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdmCitas1ActionPerformed(evt);
            }
        });

        btnAdmRealizarTutoria1.setText("Realizar una Tutoria");
        btnAdmRealizarTutoria1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAdmRealizarTutoria1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdmCitas1, javax.swing.GroupLayout.DEFAULT_SIZE, 139, Short.MAX_VALUE)
                    .addComponent(btnAdmRealizarTutoria1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(736, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(btnAdmCitas1)
                .addGap(26, 26, 26)
                .addComponent(btnAdmRealizarTutoria1)
                .addContainerGap(542, Short.MAX_VALUE))
        );

        tabTutor.addTab("Opciones", jPanel1);

        Menu.addTab("Tutor", tabTutor);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sistema de Tutorias");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
        panelEncabezado.setLayout(panelEncabezadoLayout);
        panelEncabezadoLayout.setHorizontalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 570, Short.MAX_VALUE)
                .addComponent(jLabel7)
                .addGap(109, 109, 109))
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addGroup(panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelEncabezadoLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel7))
                    .addGroup(panelEncabezadoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 1002, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Menu))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tabAdministradorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabAdministradorMouseClicked

    }//GEN-LAST:event_tabAdministradorMouseClicked

    private void btnAdmAsignarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdmAsignarActionPerformed
        ITutorado ventana = new ITutorado();
        ventana.setVisible(true);
    }//GEN-LAST:event_btnAdmAsignarActionPerformed

    private void btnAdmTutoradosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdmTutoradosActionPerformed
        IPTutorado ventana = new IPTutorado();
        ventana.setVisible(true);
    }//GEN-LAST:event_btnAdmTutoradosActionPerformed

    private void btnAdmCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdmCitasActionPerformed
        IPCita ventana = new IPCita();
        ventana.setVisible(true);

    }//GEN-LAST:event_btnAdmCitasActionPerformed

    private void btnAdmRealizarTutoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdmRealizarTutoriaActionPerformed
        ITutoria ventana = new ITutoria();
        ventana.setVisible(true);

    }//GEN-LAST:event_btnAdmRealizarTutoriaActionPerformed

    private void btnAdmTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdmTutorActionPerformed

        ITutor principal = new ITutor(this, rootPaneCheckingEnabled);
        principal.setVisible(true);

    }//GEN-LAST:event_btnAdmTutorActionPerformed

    private void btnVerCitasBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerCitasBuscarActionPerformed
        String numControl = txtVerCitasNumControl.getText().trim();

        if (numControl.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, ingrese un número de control.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 1. Buscar el Tutorado por número de control
        Tutorado tutoradoEncontrado = null;
        // 'tutorados' es tu lista List<Tutorado> listaTotalTutoradosGeneral que cargas al inicio
        for (Tutorado tdo : tutorados) {
            if (tdo.getNc() != null && tdo.getNc().equalsIgnoreCase(numControl)) {
                tutoradoEncontrado = tdo;
                break;
            }
        }

        if (tutoradoEncontrado == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un tutorado con el número de control: " + numControl, "No Encontrado", JOptionPane.INFORMATION_MESSAGE);
            // Limpiar la tabla si no se encuentra el tutorado
            citasDelTutorado.clear();
            modeloTablaVerCitas.fireTableDataChanged(); // Notificar al modelo para que actualice la vista
            return;
        }

        // 2. Filtrar la lista 'citas' (this.citas, que contiene todas las citas del sistema)
        // para encontrar aquellas que pertenezcan a las tutorías del tutoradoEncontrado.
        // Esto es un poco indirecto ya que la Cita no está directamente ligada al Tutorado,
        // sino a través de una Tutoria.
        // Primero, necesitas obtener la lista de Tutorias para el tutoradoEncontrado.

        List<Tutoria> tutoriasDelTutorado = tutoradoEncontrado.getTutoriaList(); // Asumiendo que Tutorado tiene getTutoriaList()

        citasDelTutorado.clear(); // Limpiar resultados anteriores

        if (tutoriasDelTutorado != null && !tutoriasDelTutorado.isEmpty()) {
            for (Tutoria tutoria : tutoriasDelTutorado) {
                if (tutoria.getIdCita() != null) {
                    // Comprobar si la cita ya está en la lista para evitar duplicados si es posible
                    // (aunque cada tutoría debería tener una cita única o la cita debería ser la misma instancia).
                    if (!citasDelTutorado.contains(tutoria.getIdCita())) {
                        citasDelTutorado.add(tutoria.getIdCita());
                    }
                }
            }
        }

        if (citasDelTutorado.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El tutorado " + tutoradoEncontrado.getNombre() + " no tiene citas registradas a través de tutorías.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }

        // 3. Actualizar el modelo de la tabla
        // Si MTcita (o tu modelo) tiene un método para actualizar sus datos, úsalo.
        // Por ejemplo, si MTcita tuviera un método setCitas(List<Cita> nuevasCitas):
        // modeloTablaVerCitas.setCitas(citasDelTutorado);
        // Si no, necesitas re-crear el modelo o modificar su lista interna y notificar.
        // La forma más simple si MTcita toma la lista en el constructor y no la modifica internamente:
        modeloTablaVerCitas = new MTcita(citasDelTutorado); // Re-crear con la nueva lista filtrada
        tabVerCitasCitas.setModel(modeloTablaVerCitas);
        // O si tienes un método en MTcita como actualizarListaDatos:
        // ((MTcita)modeloTablaVerCitas).actualizarListaDatos(citasDelTutorado); // Necesitarías un cast y el método en MTcita

        // Para que el JTable se repinte correctamente si la estructura no cambió pero los datos sí:
        // modeloTablaVerCitas.fireTableDataChanged(); // Esto ya lo haría el constructor o actualizarListaDatos
    }//GEN-LAST:event_btnVerCitasBuscarActionPerformed

    private void txtVerCitasNumControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVerCitasNumControlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVerCitasNumControlActionPerformed

    private void btnAdmCitas1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdmCitas1ActionPerformed
        IPCita ventana = new IPCita();
        ventana.setVisible(true);
    }//GEN-LAST:event_btnAdmCitas1ActionPerformed

    private void btnAdmRealizarTutoria1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAdmRealizarTutoria1ActionPerformed
        ITutoria ventana = new ITutoria();
        ventana.setVisible(true);
    }//GEN-LAST:event_btnAdmRealizarTutoria1ActionPerformed
    public String getFecha(){
        fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(fecha);
    }
    
    

    public void cargarEstudiantes(){
        mestudiantes = new DefaultListModel();
        mtutorados = new DefaultListModel();
        mestudiantes.clear();
        for(Tutorado dtutorado : tutorados) 
            if(dtutorado.getTutor() == null){
                mestudiantes.addElement(dtutorado.getNombre());
                tutorado_nom.put(dtutorado.getNombre(),dtutorado);
            }
    }

    
 
    public ImageIcon redimensionarImagen(String ruta, int ancho, int alto) {
        // 'nombreArchivoImagen' ahora sería solo "imagenes/icono.png"
        java.net.URL imgUrl = getClass().getResource("imagenes/icono.png");
        if (imgUrl == null) {
            System.err.println("No se pudo encontrar el recurso de imagen: " + "imagenes/icono.png" + " (relativo a " + getClass().getPackage().getName() + ")");
            // Opcionalmente, devuelve un icono por defecto o null y maneja el error
            return null; // O un icono por defecto
        }
        ImageIcon icono = new ImageIcon(imgUrl);
        Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
            
  
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
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
            java.util.logging.Logger.getLogger(IMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        
        UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane Menu;
    private javax.swing.JButton btnAdmAsignar;
    private javax.swing.JButton btnAdmCitas;
    private javax.swing.JButton btnAdmCitas1;
    private javax.swing.JButton btnAdmRealizarTutoria;
    private javax.swing.JButton btnAdmRealizarTutoria1;
    private javax.swing.JButton btnAdmTutor;
    private javax.swing.JButton btnAdmTutorados;
    private javax.swing.JButton btnVerCitasBuscar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel panelAdmControl;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JPanel panelTutorados;
    private javax.swing.JPanel panelTutores;
    private javax.swing.JPanel panelVerCitas;
    private javax.swing.JTable tabAdmCitas;
    private javax.swing.JTable tabAdmEstudiantes;
    private javax.swing.JTable tabAdmTutores;
    private javax.swing.JTable tabAdmTutorias;
    private javax.swing.JTabbedPane tabAdministrador;
    private javax.swing.JTabbedPane tabTutor;
    private javax.swing.JTabbedPane tabVerCitas;
    private javax.swing.JTable tabVerCitasCitas;
    private javax.swing.JTextField txtVerCitasNumControl;
    // End of variables declaration//GEN-END:variables
}
