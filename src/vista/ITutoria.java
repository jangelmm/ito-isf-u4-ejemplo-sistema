/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatLightLaf;
import control.AdmDatos;
import control.CitaJpaController;
import control.TutorJpaController; // No se usa directamente aquí, pero es parte del contexto
import control.TutoradoJpaController; // No se usa directamente aquí
import control.TutoriaJpaController;
import control.exceptions.IllegalOrphanException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import modelo.Cita;
import modelo.DatosTablaCitas; // Asumo que esta clase la usas para MTablaCita
import modelo.MTablaCita;
// import modelo.MTtutor; // No se usa en esta clase directamente
import modelo.Tutor;
import modelo.Tutorado;
import modelo.Tutoria;

/**
 *
 * @author jesus
 */
public class ITutoria extends javax.swing.JFrame {

    //<editor-fold defaultstate="collapsed" desc="Variables de instancia y constantes">
    private Tutor tutorSeleccionado; // Renombrado para claridad
    private Cita citaSeleccionada; // Renombrado para claridad

    private final TutorJpaController cTutor;
    private final CitaJpaController cCita;
    private final TutoradoJpaController cTutoradoController; // Para obtener todos los tutorados
    private final TutoriaJpaController cTutoria;
    private final AdmDatos adm;

    private List<Tutor> listaTotalTutores;
    private List<Cita> listaTotalCitas;
    private List<Tutorado> listaTotalTutoradosGeneral; // Lista de TODOS los tutorados del sistema

    private final Map<String, Tutor> tutorMap = new HashMap<>();
    private final Map<String, Cita> citaMap = new HashMap<>();

    private MTablaCita modeloTablaTutoradosEnCita; // Renombrado para claridad
    private List<DatosTablaCitas> datosParaTablaTutorados; // Lista que alimenta MTablaCita

    private final String SELECCIONA_TUTOR_TEXT = "Seleccione Tutor";
    private final String SELECCIONA_CITA_TEXT = "Seleccione Cita";
    //</editor-fold>
    
    public ITutoria() {
        initComponents();
        
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        cTutor = new TutorJpaController(adm.getEnf());
        cCita = new CitaJpaController(adm.getEnf());
        cTutoradoController = new TutoradoJpaController(adm.getEnf());
        cTutoria = new TutoriaJpaController(adm.getEnf());

        // Cargar datos maestros
        listaTotalTutores = cTutor.findTutorEntities();
        listaTotalCitas = cCita.findCitaEntities(); // Cargar todas las citas una vez
        listaTotalTutoradosGeneral = cTutoradoController.findTutoradoEntities();


        datosParaTablaTutorados = new ArrayList<>(); // Inicializar la lista
        modeloTablaTutoradosEnCita = new MTablaCita((ArrayList) datosParaTablaTutorados); // Usar la variable de instancia
        tabTutorados.setModel(modeloTablaTutoradosEnCita); // Nombre original del JTable

        //configurarTablaListener();
        cargarTutoresComboBox();
        actualizarEstadoComponentes(); // Estado inicial de la UI
    }
    

    
    private void actualizarEstadoComponentes() {
        boolean tutorHaSidoAceptado = (tutorSeleccionado != null);
        boolean citaHaSidoAceptada = (citaSeleccionada != null && tutorHaSidoAceptado);

        // Componentes relacionados con la selección de Cita
        jLabel3.setEnabled(tutorHaSidoAceptado); // "Cita:"
        cboCitas.setEnabled(tutorHaSidoAceptado);
        
        //btnAceptarCita.setEnabled(tutorHaSidoAceptado && cboCitas.getItemCount() > 0 && cboCitas.getSelectedIndex() > 0);
        btnAceptarCita.setEnabled(tutorHaSidoAceptado);
        
        // Componentes relacionados con la tabla de Tutorados y registro de Tutoría
        jLabel2.setEnabled(citaHaSidoAceptada); // "Tutorados"
        jScrollPane1.setEnabled(citaHaSidoAceptada); // El scrollpane de la tabla
        tabTutorados.setEnabled(citaHaSidoAceptada);
        btnRegistrarTutoria.setEnabled(citaHaSidoAceptada && tabTutorados.getRowCount() > 0);

        // Si no hay tutor aceptado, limpiar y deshabilitar combobox de citas
        if (!tutorHaSidoAceptado) {
            cboCitas.removeAllItems();
            cboCitas.addItem(SELECCIONA_CITA_TEXT); // Añadir item por defecto
            citaSeleccionada = null; // Resetear cita seleccionada
        }
        // Si no hay cita aceptada, limpiar tabla
        if(!citaHaSidoAceptada){
            datosParaTablaTutorados.clear();
            modeloTablaTutoradosEnCita.fireTableDataChanged();
        }
    }
    
    private void cargarTutoresComboBox() {
        cboTutores.removeAllItems();
        cboTutores.addItem(SELECCIONA_TUTOR_TEXT);
        tutorMap.clear();

        for (Tutor t : listaTotalTutores) {
            cboTutores.addItem(t.getNombre());
            tutorMap.put(t.getNombre(), t);
        }
        // Resetear selección si es necesario
        tutorSeleccionado = null;
        citaSeleccionada = null;
    }
    
    private void cargarCitasComboBox(Tutor tutor) {
        cboCitas.removeAllItems();
        cboCitas.addItem(SELECCIONA_CITA_TEXT);
        citaMap.clear();
        citaSeleccionada = null;

        if (tutor == null) {
            actualizarEstadoComponentes();
            return;
        }

        System.out.println("Cargando citas para tutor: " + tutor.getNombre()); // DEBUG
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy 'a las' HH:mm");
        boolean hayCitasDisponibles = false;
        for (Cita c : listaTotalCitas) {
            if (c.getTutor() != null && c.getTutor().equals(tutor) && !"Realizada".equalsIgnoreCase(c.getEstado()) && !"Cancelada".equalsIgnoreCase(c.getEstado())) {
                String displayText = String.format("%s (%s)", sdf.format(c.getFecha()), c.getAsunto());
                cboCitas.addItem(displayText);
                citaMap.put(displayText, c);
                hayCitasDisponibles = true;
            }
        }
        System.out.println("Items en cboCitas después de cargar: " + cboCitas.getItemCount()); // DEBUG
        if (!hayCitasDisponibles && cboCitas.getItemCount() <=1 ) { // Si solo queda el "Seleccione Cita"
            cboCitas.addItem("No hay citas pendientes para este tutor");
             System.out.println("Añadido 'No hay citas...' Items: " + cboCitas.getItemCount()); // DEBUG
        }
        actualizarEstadoComponentes();
    }
    
    private void popularTablaTutorados(Tutor tutorDeLaCita) {
        datosParaTablaTutorados.clear();

        if (tutorDeLaCita != null) {
            for (Tutorado unTutorado : listaTotalTutoradosGeneral) {
                if (unTutorado.getTutor() != null && unTutorado.getTutor().equals(tutorDeLaCita)) {
                    datosParaTablaTutorados.add(new DatosTablaCitas(unTutorado));
                }
            }
        }
        
        // En lugar de asignar una nueva instancia al modelo, actualiza su lista interna y notifica.
        // O, si MTablaCita tiene un método para actualizar su lista interna y notificar:
        modeloTablaTutoradosEnCita.actualizarListaDatos((ArrayList<DatosTablaCitas>) datosParaTablaTutorados);


        // Re-configurar el editor de ComboBox para la columna "Acción"
        // Esto es importante porque el modelo se está actualizando significativamente.
        if (tabTutorados.getColumnCount() > 2) { // Columna "Acción" es la tercera (índice 2)
            TableColumn accionesColumn = tabTutorados.getColumnModel().getColumn(2);
            JComboBox<String> comboBoxEditor = new JComboBox<>(new String[]{"Sin acción", "Asesoría", "Trámite", "Atención Médica / Psicológica"});
            accionesColumn.setCellEditor(new DefaultCellEditor(comboBoxEditor));
            System.out.println("Editor de JComboBox para columna 'Acción' re-configurado.");
        } else {
            System.out.println("Advertencia: La tabla no tiene suficientes columnas para configurar el editor de 'Acción'. Columnas: " + tabTutorados.getColumnCount());
        }
        actualizarEstadoComponentes(); // Actualizar estado de botones, etc.
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabTutorados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        cboTutores = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboCitas = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnAceptarTutor = new javax.swing.JButton();
        btnAceptarCita = new javax.swing.JButton();
        btnRegistrarTutoria = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabTutorados.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tabTutorados);

        jLabel1.setText("Tutor:");

        cboTutores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setText("Tutorados");

        jLabel3.setText("Cita:");

        cboCitas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Realizando una Tutoria");

        btnAceptarTutor.setText("Aceptar");
        btnAceptarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarTutorActionPerformed(evt);
            }
        });

        btnAceptarCita.setText("Aceptar");
        btnAceptarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarCitaActionPerformed(evt);
            }
        });

        btnRegistrarTutoria.setText("Registrar Tutoria");
        btnRegistrarTutoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarTutoriaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(jLabel2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(47, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cboTutores, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cboCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAceptarTutor)
                            .addComponent(btnAceptarCita))
                        .addGap(25, 25, 25))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRegistrarTutoria)
                        .addGap(162, 162, 162))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel4)
                .addGap(27, 27, 27)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cboTutores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnAceptarTutor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboCitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAceptarCita)))
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegistrarTutoria)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarCitaActionPerformed
        if (cboCitas.getSelectedIndex() <= 0 || SELECCIONA_CITA_TEXT.equals(cboCitas.getSelectedItem()) || cboCitas.getItemCount() <= 1 || "No hay citas pendientes para este tutor".equals(cboCitas.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita válida.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            citaSeleccionada = null;
            popularTablaTutorados(null); // Limpiar tabla y actualizar componentes
            return;
        }

        String citaDisplayText = (String) cboCitas.getSelectedItem();
        citaSeleccionada = citaMap.get(citaDisplayText);

        if (citaSeleccionada != null) {
            if(citaSeleccionada.getTutor() == null){
                 JOptionPane.showMessageDialog(this, "La cita seleccionada no tiene un tutor asignado.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
                 citaSeleccionada = null;
                 popularTablaTutorados(null);
                 return;
            }
            // JOptionPane.showMessageDialog(this, "Cita del " + citaSeleccionada.getFecha() + " seleccionada.");
            popularTablaTutorados(citaSeleccionada.getTutor()); // Cargar tutorados del tutor de la cita
        } else {
            JOptionPane.showMessageDialog(this, "Cita no encontrada en el mapa.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            citaSeleccionada = null;
            popularTablaTutorados(null);
        }
        // El estado de los componentes se actualiza en popularTablaTutorados
    }//GEN-LAST:event_btnAceptarCitaActionPerformed

    private void btnRegistrarTutoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarTutoriaActionPerformed
        if (tutorSeleccionado == null || citaSeleccionada == null || datosParaTablaTutorados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe tener un tutor y una cita aceptados, y tutorados listados para registrar la tutoría.", "Información Incompleta", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (citaSeleccionada.getTutor() == null || !citaSeleccionada.getTutor().equals(tutorSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Inconsistencia: La cita procesada no pertenece al tutor seleccionado activamente. Abortando.", "Error Crítico", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tutoriasRegistradasExitosamente = 0;
        List<Tutoria> tutoriasCreadasEnEstaOperacion = new ArrayList<>(); // Para potencialmente añadirlas a la cita en memoria si fuera necesario

        for (int i = 0; i < modeloTablaTutoradosEnCita.getRowCount(); i++) {
            DatosTablaCitas datosFila = datosParaTablaTutorados.get(i);
            Tutorado tutoradoDeLaFila = datosFila.getTutorado();

            Boolean asistencia = (Boolean) modeloTablaTutoradosEnCita.getValueAt(i, 1);
            String accion = (String) modeloTablaTutoradosEnCita.getValueAt(i, 2);

            if (tutoradoDeLaFila != null && asistencia != null && asistencia) {
                Tutoria nuevaTutoria = new Tutoria();
                nuevaTutoria.setIdCita(citaSeleccionada); // Establece la relación
                nuevaTutoria.setIdTutorado(tutoradoDeLaFila);
                nuevaTutoria.setAcciones((accion == null || accion.equals("Sin acción") || accion.trim().isEmpty()) ? "Asistencia registrada." : accion);

                try {
                    cTutoria.create(nuevaTutoria); // Persiste la nueva tutoría
                    tutoriasCreadasEnEstaOperacion.add(nuevaTutoria);
                    tutoriasRegistradasExitosamente++;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al guardar tutoría para " + tutoradoDeLaFila.getNombre() + ": " + ex.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace(); // Siempre es bueno tener el stack trace para desarrollo
                }
            }
        }

        if (tutoriasRegistradasExitosamente > 0) {
            try {
                // ---- INICIO DE LA MODIFICACIÓN IMPORTANTE ----
                // Volver a obtener la instancia más reciente de Cita desde la BD
                // antes de modificar y guardar.
                Cita citaParaActualizar = cCita.findCita(citaSeleccionada.getIdCita());

                if (citaParaActualizar != null) {
                    citaParaActualizar.setEstado("Realizada");

                    cCita.edit(citaParaActualizar); // Editar la instancia fresca

                    // Actualizar la lista maestra de citas para que la UI refleje el cambio de estado
                    // si se vuelve a cargar esta cita en algún momento.
                    int indexEnListaMaestra = listaTotalCitas.indexOf(citaSeleccionada); // Busca por referencia o equals
                    if (indexEnListaMaestra != -1) {
                        listaTotalCitas.set(indexEnListaMaestra, citaParaActualizar); // Reemplazar con la instancia actualizada
                    }
                    citaSeleccionada = citaParaActualizar; // Asegurar que nuestra variable de instancia también está actualizada

                    JOptionPane.showMessageDialog(this, tutoriasRegistradasExitosamente + " tutoría(s) registrada(s) exitosamente. La cita ha sido marcada como 'Realizada'.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                     JOptionPane.showMessageDialog(this, "Error: No se pudo recargar la cita para actualizar su estado.", "Error de Actualización", JOptionPane.ERROR_MESSAGE);
                }
                // ---- FIN DE LA MODIFICACIÓN IMPORTANTE ----

            } catch (IllegalOrphanException ioe) {
                JOptionPane.showMessageDialog(this, "Error de relación al actualizar cita (IllegalOrphanException): " +
                    ioe.getMessage() + (ioe.getMessages() != null ? "\nDetalles: " + String.join(", ", ioe.getMessages()) : ""),
                    "Error de Datos", JOptionPane.ERROR_MESSAGE);
                ioe.printStackTrace();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, tutoriasRegistradasExitosamente +
                    " tutoría(s) registrada(s), pero hubo un error al actualizar el estado de la cita: " + ex.getMessage(),
                    "Error Parcial al Actualizar Cita", JOptionPane.WARNING_MESSAGE);
                ex.printStackTrace();
            }
        } else if (tutoriasRegistradasExitosamente == 0 && modeloTablaTutoradosEnCita.getRowCount() > 0) {
            // Si había tutorados en la tabla pero ninguno tuvo asistencia marcada.
            boolean algunaAsistenciaMarcada = false;
             for (int i = 0; i < modeloTablaTutoradosEnCita.getRowCount(); i++) {
                Boolean asistencia = (Boolean) modeloTablaTutoradosEnCita.getValueAt(i, 1);
                if (asistencia != null && asistencia) {
                    algunaAsistenciaMarcada = true;
                    break;
                }
            }
            if(!algunaAsistenciaMarcada && modeloTablaTutoradosEnCita.getRowCount() > 0){
                 JOptionPane.showMessageDialog(this, "No se marcaron asistencias. La cita no se marcará como 'Realizada'.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Este caso es si hubo asistencias pero algo falló y no se registró ninguna tutoría.
                 JOptionPane.showMessageDialog(this, "No se registraron tutorías. Verifique la asistencia y las acciones de los tutorados.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } else { // No había tutorados en la tabla para empezar, o ningún error/registro previo.
             JOptionPane.showMessageDialog(this, "No había tutorados con asistencia marcada para registrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }

        limpiarCamposYInterfaz();
    }//GEN-LAST:event_btnRegistrarTutoriaActionPerformed

    private void btnAceptarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarTutorActionPerformed
        if (cboTutores.getSelectedIndex() <= 0 || SELECCIONA_TUTOR_TEXT.equals(cboTutores.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "Seleccione un tutor válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            tutorSeleccionado = null; // Asegurar que no hay tutor seleccionado
            cargarCitasComboBox(null); // Limpiar y deshabilitar combo de citas
            return;
        }

        String nombreTutor = (String) cboTutores.getSelectedItem();
        tutorSeleccionado = tutorMap.get(nombreTutor);

        if (tutorSeleccionado != null) {
            // JOptionPane.showMessageDialog(this, "Tutor '" + tutorSeleccionado.getNombre() + "' seleccionado.");
            cargarCitasComboBox(tutorSeleccionado); // Esto ya llama a actualizarEstadoComponentes
        } else {
            JOptionPane.showMessageDialog(this, "Tutor no encontrado en el mapa.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            tutorSeleccionado = null;
            cargarCitasComboBox(null);
        }
        // El estado de los componentes se actualiza en cargarCitasComboBox
    }//GEN-LAST:event_btnAceptarTutorActionPerformed

    private void cboTutoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTutoresActionPerformed
        // Este evento se dispara cuando cambia la selección.
        // La lógica principal de "aceptar tutor" está en el botón,
        // pero podríamos resetear la cita si el tutor cambia aquí ANTES de aceptar.
        if (cboTutores.getSelectedIndex() > 0 && tutorSeleccionado != null) {
            // Si ya había un tutor seleccionado y el usuario cambia el combo SIN presionar "Aceptar Tutor" de nuevo.
            // Podríamos optar por limpiar la selección de cita y tabla.
             String nombreTutorSeleccionadoEnCombo = (String) cboTutores.getSelectedItem();
             if(nombreTutorSeleccionadoEnCombo != null && !nombreTutorSeleccionadoEnCombo.equals(tutorSeleccionado.getNombre())){
                 // El tutor en el combo es diferente al "aceptado".
                 // Esto sugiere que el usuario cambió el tutor pero no ha confirmado.
                 // Podríamos forzar a que presione "Aceptar Tutor" o manejarlo aquí.
                 // Por ahora, la lógica de btnAceptarTutor es la que actualiza 'tutorSeleccionado'.
             }
        }
         // Si se selecciona "Seleccione Tutor", limpiar la selección de tutor y cita
        if (cboTutores.getSelectedIndex() <= 0) {
            tutorSeleccionado = null;
            citaSeleccionada = null; // también resetea la cita
            cargarCitasComboBox(null); // esto limpiará cboCitas y actualizará estados
            datosParaTablaTutorados.clear();
            modeloTablaTutoradosEnCita.fireTableDataChanged();
            actualizarEstadoComponentes();
        }
    }//GEN-LAST:event_cboTutoresActionPerformed
    
    private void cboCitasActionPerformed(java.awt.event.ActionEvent evt) {
        // Si se selecciona "Seleccione Cita" o no hay citas válidas,
        // limpiar la selección de cita y actualizar componentes.
        if (cboCitas.getSelectedIndex() <= 0 ||
            SELECCIONA_CITA_TEXT.equals(cboCitas.getSelectedItem()) ||
            "No hay citas pendientes para este tutor".equals(cboCitas.getSelectedItem())) {

            citaSeleccionada = null; // Anular la cita seleccionada
            // Limpiar la tabla si la cita ya no es válida
            datosParaTablaTutorados.clear();
            if (modeloTablaTutoradosEnCita != null) { // Asegurarse que el modelo no es null
                 modeloTablaTutoradosEnCita.fireTableDataChanged();
            }
        } else {
            // Una cita válida ha sido seleccionada en el ComboBox.
            // No establecemos 'citaSeleccionada' aquí, eso lo hace btnAceptarCita.
            // La acción aquí es principalmente para actualizar el estado de los botones.
        }
        actualizarEstadoComponentes(); // Asegura que el estado de btnAceptarCita se reevalúe
    }


    private void limpiarCamposYInterfaz() {
        // Resetear variables de estado
        tutorSeleccionado = null;
        citaSeleccionada = null;

        // Limpiar ComboBoxes y recargar el de tutores
        cargarTutoresComboBox(); // Esto pondrá "Seleccione Tutor"
        cboCitas.removeAllItems();
        cboCitas.addItem(SELECCIONA_CITA_TEXT);


        // Limpiar la tabla
        datosParaTablaTutorados.clear();
        modeloTablaTutoradosEnCita.fireTableDataChanged();

        // Actualizar el estado de habilitación de los componentes
        actualizarEstadoComponentes();
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
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        UIManager.setLookAndFeel(new FlatLightLaf());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ITutoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptarCita;
    private javax.swing.JButton btnAceptarTutor;
    private javax.swing.JButton btnRegistrarTutoria;
    private javax.swing.JComboBox<String> cboCitas;
    private javax.swing.JComboBox<String> cboTutores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabTutorados;
    // End of variables declaration//GEN-END:variables
}
