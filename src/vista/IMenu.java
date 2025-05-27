/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

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
        configurarComboBox();
        configurarJSpinner();
        // Inicializacion de Crear Tutoria
        FechaActual.setText("Fecha: " + getFecha());
        cargarTutoresCita();   
        //Inicializacion de Asignar Tutor
        cargarTutores();
        cargarEstudiantes();
        estudiantes.setModel(mestudiantes);
        estudiantes.setSelectionMode(1);
        tutoradoss.setModel(mtutorados);
        iconMiniatura = redimensionarImagen("imagenes/icono.png", 350, 500); // NUEVA LÍNEA (relativa al paquete 'vista')
        ImagenTutor.setIcon(iconMiniatura);
       
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
        panelAdmAgregarTutor = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dn = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ddh = new javax.swing.JTextField();
        dnt = new javax.swing.JSpinner();
        cbCarreras = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        ImagenTutor = new javax.swing.JLabel();
        panelAdmAsignarTutor = new javax.swing.JPanel();
        moverTut = new javax.swing.JButton();
        asignarTutorado = new javax.swing.JButton();
        aceptarTutor = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        bct = new javax.swing.JButton();
        LTutores = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        estudiantes = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tutoradoss = new javax.swing.JList<>();
        moverEst = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        panelCitas = new javax.swing.JPanel();
        tabCitaCrear = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        panelTutorias = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        RegistroTutoria = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ltutorados = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        ListaTutor = new javax.swing.JComboBox<>();
        Aceptar = new javax.swing.JButton();
        FechaActual = new javax.swing.JLabel();
        panelEncabezado = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1100, 750));

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
                    .addComponent(jScrollPane8)
                    .addGroup(panelVerCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(panelVerCitasLayout.createSequentialGroup()
                            .addComponent(jLabel21)
                            .addGap(45, 45, 45)
                            .addComponent(txtVerCitasNumControl, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnVerCitasBuscar))
                        .addComponent(jSeparator1)
                        .addGroup(panelVerCitasLayout.createSequentialGroup()
                            .addGap(326, 326, 326)
                            .addComponent(jLabel12)
                            .addGap(409, 409, 409))))
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
                .addContainerGap(18, Short.MAX_VALUE))
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

        javax.swing.GroupLayout panelAdmControlLayout = new javax.swing.GroupLayout(panelAdmControl);
        panelAdmControl.setLayout(panelAdmControlLayout);
        panelAdmControlLayout.setHorizontalGroup(
            panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdmControlLayout.createSequentialGroup()
                .addGroup(panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAdmControlLayout.createSequentialGroup()
                        .addGap(341, 341, 341)
                        .addComponent(jLabel2))
                    .addGroup(panelAdmControlLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel11)
                            .addComponent(jLabel6)
                            .addComponent(jLabel4)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 858, Short.MAX_VALUE)
                            .addComponent(jLabel5)
                            .addComponent(jScrollPane5)
                            .addComponent(jScrollPane6)
                            .addComponent(jScrollPane7))))
                .addContainerGap(129, Short.MAX_VALUE))
        );
        panelAdmControlLayout.setVerticalGroup(
            panelAdmControlLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdmControlLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel2)
                .addGap(1, 1, 1)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(167, Short.MAX_VALUE))
        );

        tabAdministrador.addTab("Control", panelAdmControl);

        jLabel7.setText("No Targeta:");

        jLabel8.setText("Nombre:");

        dn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dnActionPerformed(evt);
            }
        });

        jLabel9.setText("Carrera");

        jLabel10.setText("Dias/Horas:");

        ddh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ddhActionPerformed(evt);
            }
        });

        cbCarreras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCarreras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCarrerasActionPerformed(evt);
            }
        });

        jButton1.setText("Agregar Registro");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel15.setText("Tutores");

        javax.swing.GroupLayout panelAdmAgregarTutorLayout = new javax.swing.GroupLayout(panelAdmAgregarTutor);
        panelAdmAgregarTutor.setLayout(panelAdmAgregarTutorLayout);
        panelAdmAgregarTutorLayout.setHorizontalGroup(
            panelAdmAgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdmAgregarTutorLayout.createSequentialGroup()
                .addGap(464, 464, 464)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelAdmAgregarTutorLayout.createSequentialGroup()
                .addGroup(panelAdmAgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAdmAgregarTutorLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(panelAdmAgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(35, 35, 35)
                        .addGroup(panelAdmAgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ddh, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dn, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dnt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(panelAdmAgregarTutorLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ImagenTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        panelAdmAgregarTutorLayout.setVerticalGroup(
            panelAdmAgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAdmAgregarTutorLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(panelAdmAgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAdmAgregarTutorLayout.createSequentialGroup()
                        .addGroup(panelAdmAgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(dnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(panelAdmAgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(dn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(panelAdmAgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(ddh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addGroup(panelAdmAgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(89, 89, 89)
                        .addComponent(jButton1))
                    .addComponent(ImagenTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabAdministrador.addTab("Agregar Tutor", panelAdmAgregarTutor);

        moverTut.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        moverTut.setText("< Mover Estudiante");
        moverTut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moverTutActionPerformed(evt);
            }
        });

        asignarTutorado.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        asignarTutorado.setText("Asignar Tutorado");
        asignarTutorado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asignarTutoradoActionPerformed(evt);
            }
        });

        aceptarTutor.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        aceptarTutor.setText("Aceptar Tutor");
        aceptarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarTutorActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel16.setText("Estudiantes Disponibles");

        bct.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        bct.setText("Cambiar Tutor");
        bct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bctActionPerformed(evt);
            }
        });

        LTutores.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        LTutores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel17.setText("Selecciona Tutor:");

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel18.setText("Estudiantes Asignados");

        estudiantes.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        estudiantes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(estudiantes);

        tutoradoss.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        tutoradoss.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(tutoradoss);

        moverEst.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        moverEst.setText("Mover Estudiante >");
        moverEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moverEstActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Asignar Tutor");

        javax.swing.GroupLayout panelAdmAsignarTutorLayout = new javax.swing.GroupLayout(panelAdmAsignarTutor);
        panelAdmAsignarTutor.setLayout(panelAdmAsignarTutorLayout);
        panelAdmAsignarTutorLayout.setHorizontalGroup(
            panelAdmAsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdmAsignarTutorLayout.createSequentialGroup()
                .addGroup(panelAdmAsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAdmAsignarTutorLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(panelAdmAsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(panelAdmAsignarTutorLayout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LTutores, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(panelAdmAsignarTutorLayout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addGroup(panelAdmAsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(moverTut)
                                    .addComponent(moverEst)
                                    .addGroup(panelAdmAsignarTutorLayout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(asignarTutorado)))
                                .addGap(14, 14, 14)))
                        .addGap(18, 18, 18)
                        .addGroup(panelAdmAsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelAdmAsignarTutorLayout.createSequentialGroup()
                                .addComponent(aceptarTutor)
                                .addGap(35, 35, 35)
                                .addComponent(bct))
                            .addGroup(panelAdmAsignarTutorLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(panelAdmAsignarTutorLayout.createSequentialGroup()
                        .addGap(399, 399, 399)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(panelAdmAsignarTutorLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(145, 145, 145))
        );
        panelAdmAsignarTutorLayout.setVerticalGroup(
            panelAdmAsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAdmAsignarTutorLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel19)
                .addGap(61, 61, 61)
                .addGroup(panelAdmAsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LTutores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aceptarTutor)
                    .addComponent(bct)
                    .addComponent(jLabel17))
                .addGap(98, 98, 98)
                .addGroup(panelAdmAsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18))
                .addGap(31, 31, 31)
                .addGroup(panelAdmAsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelAdmAsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAdmAsignarTutorLayout.createSequentialGroup()
                            .addComponent(moverTut)
                            .addGap(38, 38, 38)
                            .addComponent(moverEst)
                            .addGap(57, 57, 57))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(asignarTutorado)
                .addGap(60, 60, 60))
        );

        tabAdministrador.addTab("Asignar Tutor", panelAdmAsignarTutor);

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 877, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 603, Short.MAX_VALUE)
        );

        tabCitaCrear.addTab("Crear Cita", jPanel1);

        javax.swing.GroupLayout panelCitasLayout = new javax.swing.GroupLayout(panelCitas);
        panelCitas.setLayout(panelCitasLayout);
        panelCitasLayout.setHorizontalGroup(
            panelCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCitasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabCitaCrear)
                .addContainerGap())
        );
        panelCitasLayout.setVerticalGroup(
            panelCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCitasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tabCitaCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Menu.addTab("Cita", panelCitas);

        jLabel13.setText("Registro de Asistencia");

        jLabel14.setText("Registro de Tutoria");

        RegistroTutoria.setText("Registrar Tutoria");

        ltutorados.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        ltutorados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(ltutorados);

        jLabel20.setText("Tutor");

        ListaTutor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ListaTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaTutorActionPerformed(evt);
            }
        });

        Aceptar.setText("Aceptar");
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });

        FechaActual.setText("Fecha: ");

        javax.swing.GroupLayout panelTutoriasLayout = new javax.swing.GroupLayout(panelTutorias);
        panelTutorias.setLayout(panelTutoriasLayout);
        panelTutoriasLayout.setHorizontalGroup(
            panelTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTutoriasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTutoriasLayout.createSequentialGroup()
                        .addComponent(RegistroTutoria)
                        .addGap(432, 432, 432))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTutoriasLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(422, 422, 422))))
            .addGroup(panelTutoriasLayout.createSequentialGroup()
                .addGroup(panelTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelTutoriasLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane2))
                    .addGroup(panelTutoriasLayout.createSequentialGroup()
                        .addGroup(panelTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelTutoriasLayout.createSequentialGroup()
                                .addGap(190, 190, 190)
                                .addComponent(jLabel20)
                                .addGap(68, 68, 68)
                                .addComponent(ListaTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(102, 102, 102)
                                .addComponent(Aceptar))
                            .addGroup(panelTutoriasLayout.createSequentialGroup()
                                .addGap(439, 439, 439)
                                .addComponent(FechaActual))
                            .addGroup(panelTutoriasLayout.createSequentialGroup()
                                .addGap(402, 402, 402)
                                .addComponent(jLabel14)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelTutoriasLayout.setVerticalGroup(
            panelTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTutoriasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FechaActual)
                .addGap(35, 35, 35)
                .addGroup(panelTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(ListaTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Aceptar))
                .addGap(53, 53, 53)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(RegistroTutoria)
                .addGap(94, 94, 94))
        );

        Menu.addTab("Tutoria", panelTutorias);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sistema de Tutorias");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout panelEncabezadoLayout = new javax.swing.GroupLayout(panelEncabezado);
        panelEncabezado.setLayout(panelEncabezadoLayout);
        panelEncabezadoLayout.setHorizontalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(701, Short.MAX_VALUE))
        );
        panelEncabezadoLayout.setVerticalGroup(
            panelEncabezadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelEncabezadoLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelEncabezado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 538, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public String getFecha(){
        fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(fecha);
    }
    
    public void cargarTutoresCita(){
        ListaTutor.removeAllItems();
        tutor_nom.clear();
        cita_horas.clear();        
        ListaTutor.addItem(SELECCIONA);
        int nc = 1;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String ff = formato.format(fecha);
        citas.sort(Comparator.comparing((Cita c) -> c.getTutor().getNombre())
            .thenComparing(Cita::getHora));
        for(Cita dCita: citas){
            if(ff.equals(formato.format(dCita.getFecha())) && dCita.getEstado().equals("0")){
                ListaTutor.addItem(dCita.getTutor().getNombre() + " - Hora: " + dCita.getHora() +":00");
                tutor_nom.put(dCita.getTutor().getNombre() + " - Hora: " + dCita.getHora() +":00",dCita.getTutor());
                cita_horas.put(nc++,dCita);
            }
        }
    }
    public void cargarTutores(){
        LTutores.removeAllItems();
        LTutores.addItem("Selecciona Tutor");
        for(Tutor dtutor: tutores) LTutores.addItem(dtutor.getNombre());
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
    public void configurarComboBox(){
        cbCarreras.removeAllItems();
        String[] opcionesCarrera = {"Ingeniería Electrónica", "Ingeniería Civil", /* ... todas tus carreras ... */};
        for(String carrera : opcionesCarrera) {
            cbCarreras.addItem(carrera);  
        }
    }
    
    public void configurarJSpinner(){
        //int inicio = tutor.getTutoradoCollection()
        min = cTutor.getTutorCount()+1;
        snm = new SpinnerNumberModel(min, min, max, 1);    
        dnt.setModel(snm);  
    }
    
    private void dnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dnActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_dnActionPerformed

    private void ddhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ddhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ddhActionPerformed

    private void cbCarrerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCarrerasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCarrerasActionPerformed
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // Validaciones básicas (puedes añadir más)
        if (dn.getText().trim().isEmpty() || ddh.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nombre y Días/Horas son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        tutor = new Tutor(); // Crea una nueva instancia

        // NumTarjeta: Si es autoincremental en BD, no lo establezcas aquí.
        // Si no, obtén el valor del spinner.
        tutor.setNumTarjeta((Integer) dnt.getValue()); // Asumiendo que numTarjeta no es el PK autoincremental.
                                                      // Si idPersona es el PK autoincremental, numTarjeta es un campo normal.
        tutor.setNombre(dn.getText().trim());
        tutor.setCarrera((String) cbCarreras.getSelectedItem());

        // El campo 'ddh' parece combinar días y horas. Necesitas una forma consistente de almacenarlo.
        // La entidad Tutor tiene campos separados 'dias' y 'horas'.
        // Deberás decidir cómo parsear 'ddh.getText()' en esos dos campos o modificar la entidad/UI.
        // Ejemplo simple (necesitarás mejor lógica de parseo):
        String diasHorasInput = ddh.getText().trim();
        // Lógica simple para separar:
        String[] partesDH = diasHorasInput.split(";"); // Suponiendo formato "L-M-X;08-12"
        String diasTutor = "";
        String horasTutor = "";
        if (partesDH.length > 0) diasTutor = partesDH[0].trim();
        if (partesDH.length > 1) horasTutor = partesDH[1].trim();

        tutor.setDias(diasTutor);   // Asignar los días parseados
        tutor.setHoras(horasTutor); // Asignar las horas parseadas

        try {
            cTutor.create(tutor);
            tutores.add(tutor); // Añadir a la lista en memoria
            if (mtt != null) { // mtt es el modelo de la tabla ttutores
                mtt.fireTableDataChanged(); // Notificar a la tabla en la pestaña "Control"
            }
            // cargarTutores(); // Vuelve a cargar el ComboBox LTutores en la pestaña "Asignar Tutor"
            JOptionPane.showMessageDialog(this, "Tutor agregado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // limpiarCamposAgregarTutor(); // Un método para limpiar los campos de esta pestaña
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al agregar tutor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
    
    private void moverTutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moverTutActionPerformed
        // TODO add your handling code here:
        int iest_sel[] = tutoradoss.getSelectedIndices();
        for (int x = iest_sel.length-1; x >= 0; x--){
            mestudiantes.addElement(mtutorados.get(iest_sel[x]));
            mtutorados.remove(iest_sel[x]);
        }
        moverTut.setEnabled(!mtutorados.isEmpty());
        moverEst.setEnabled(!mestudiantes.isEmpty());
    }//GEN-LAST:event_moverTutActionPerformed

    private void asignarTutoradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asignarTutoradoActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(this,"Confirma la asignacion de tutor " + LTutores.getSelectedItem() + "?") == 0){
            for(int nts = 0; nts < mtutorados.size(); nts++){
                Tutorado t = tutorado_nom.get(mtutorados.get(nts));
                t.setTutor(tutor);
                try{
                    cTutorado.edit(t);
                } catch (Exception ex){
                    Logger.getLogger(ITutorado.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        }
        
    }//GEN-LAST:event_asignarTutoradoActionPerformed
        
    public void cargarTutoresTutoria(){
        ListaTutor.removeAllItems();
        tutor_nom.clear();
        cita_horas.clear();        
        ListaTutor.addItem(SELECCIONA);
        int nc = 1;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String ff = formato.format(fecha);
        citas.sort(Comparator.comparing((Cita c) -> c.getTutor().getNombre())
            .thenComparing(Cita::getHora));
        for(Cita dCita: citas){
            if(ff.equals(formato.format(dCita.getFecha())) && dCita.getEstado().equals("0")){
                ListaTutor.addItem(dCita.getTutor().getNombre() + " - Hora: " + dCita.getHora() +":00");
                tutor_nom.put(dCita.getTutor().getNombre() + " - Hora: " + dCita.getHora() +":00",dCita.getTutor());
                cita_horas.put(nc++,dCita);
            }
        }
    }
    private void aceptarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarTutorActionPerformed
        // TODO add your handling code here:
        if(LTutores.getSelectedItem().equals(SELECCIONA))
        JOptionPane.showMessageDialog(this,"Selecciona un Tutor");
        else
        if(JOptionPane.showConfirmDialog(this,"Confirme Selecciona al tutor " + LTutores.getSelectedItem()+"?") == 0){
            tutor = tutores.get(LTutores.getSelectedIndex()-1);
            LTutores.setEnabled(false);
            jLabel17.setText(SELECCIONADO);
            aceptarTutor.setEnabled(false);
            if(!mestudiantes.isEmpty()) moverEst.setEnabled(true);
        }
    }//GEN-LAST:event_aceptarTutorActionPerformed

    private void bctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bctActionPerformed
        // TODO add your handling code here:
        LTutores.setEnabled(true);
        jLabel1.setText(SELECCIONA);
        aceptarTutor.setEnabled(true);
    }//GEN-LAST:event_bctActionPerformed

    private void moverEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moverEstActionPerformed
        // TODO add your handling code here:
        int iest_sel[] = estudiantes.getSelectedIndices();
        for (int x = iest_sel.length-1; x >= 0; x--){
            mtutorados.addElement(mestudiantes.get(iest_sel[x]));
            mestudiantes.remove(iest_sel[x]);
        }
        moverEst.setEnabled(!mestudiantes.isEmpty());
        moverTut.setEnabled(!mtutorados.isEmpty());
    }//GEN-LAST:event_moverEstActionPerformed

    private void ListaTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListaTutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ListaTutorActionPerformed

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed
        // TODO add your handling code here:
        if(!(ListaTutor.getSelectedItem().equals(SELECCIONA))){

            cita = cita_horas.get(ListaTutor.getSelectedIndex());

            tutorados_s = new ArrayList<>();
            for (Tutorado dt: tutorados)
            if(dt.getTutor()!= null && dt.getTutor().equals(cita.getTutor())) tutorados_s.add(dt);

            datosCitas = new ArrayList<>();
            for(Tutorado dt: tutorados_s){
                DatosTablaCitas dtc = new DatosTablaCitas(dt);
                datosCitas.add(dtc);
            }
            modTabCita = new MTablaCita(datosCitas);
            ltutorados.setModel(modTabCita);
        } else JOptionPane.showMessageDialog(this,"Seleccione un Tutor - Hola");
    }//GEN-LAST:event_AceptarActionPerformed

    private void txtVerCitasNumControlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtVerCitasNumControlActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtVerCitasNumControlActionPerformed

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
            java.util.logging.Logger.getLogger(IMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            FlatMacDarkLaf.setup();
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aceptar;
    private javax.swing.JLabel FechaActual;
    private javax.swing.JLabel ImagenTutor;
    private javax.swing.JComboBox<String> LTutores;
    private javax.swing.JComboBox<String> ListaTutor;
    private javax.swing.JTabbedPane Menu;
    private javax.swing.JButton RegistroTutoria;
    private javax.swing.JButton aceptarTutor;
    private javax.swing.JButton asignarTutorado;
    private javax.swing.JButton bct;
    private javax.swing.JButton btnVerCitasBuscar;
    private javax.swing.JComboBox<String> cbCarreras;
    private javax.swing.JTextField ddh;
    private javax.swing.JTextField dn;
    private javax.swing.JSpinner dnt;
    private javax.swing.JList<String> estudiantes;
    private javax.swing.JButton jButton1;
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
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTable ltutorados;
    private javax.swing.JButton moverEst;
    private javax.swing.JButton moverTut;
    private javax.swing.JPanel panelAdmAgregarTutor;
    private javax.swing.JPanel panelAdmAsignarTutor;
    private javax.swing.JPanel panelAdmControl;
    private javax.swing.JPanel panelCitas;
    private javax.swing.JPanel panelEncabezado;
    private javax.swing.JPanel panelTutorados;
    private javax.swing.JPanel panelTutores;
    private javax.swing.JPanel panelTutorias;
    private javax.swing.JPanel panelVerCitas;
    private javax.swing.JTable tabAdmCitas;
    private javax.swing.JTable tabAdmEstudiantes;
    private javax.swing.JTable tabAdmTutores;
    private javax.swing.JTable tabAdmTutorias;
    private javax.swing.JTabbedPane tabAdministrador;
    private javax.swing.JTabbedPane tabCitaCrear;
    private javax.swing.JTabbedPane tabVerCitas;
    private javax.swing.JTable tabVerCitasCitas;
    private javax.swing.JList<String> tutoradoss;
    private javax.swing.JTextField txtVerCitasNumControl;
    // End of variables declaration//GEN-END:variables
}
