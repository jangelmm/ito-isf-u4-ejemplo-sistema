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
    private Tutor tutorSeleccionado; // Sigue siendo útil para saber con qué tutor se está trabajando una vez se selecciona una cita
    private Cita citaSeleccionada;

    private final CitaJpaController cCita;
    private final TutoradoJpaController cTutoradoController;
    private final TutoriaJpaController cTutoria;
    private final AdmDatos adm;

    private List<Cita> listaTotalCitas; // Cargar todas las citas al inicio
    private List<Tutorado> listaTotalTutoradosGeneral;

    // citaMap ahora usará un String que puede incluir el nombre del tutor y la fecha para ser único
    private final Map<String, Cita> citaMap = new HashMap<>();

    private MTablaCita modeloTablaTutoradosEnCita;
    private List<DatosTablaCitas> datosParaTablaTutorados;

    private final String SELECCIONA_CITA_TEXT = "Seleccione Cita de Hoy"; // O un texto similar
    private final String NO_HAY_CITAS_TEXT = "No hay citas para esta fecha";
    
    private boolean uiInicializada = false; // Nueva bandera
    
    //</editor-fold>
    
    public ITutoria() {
        initComponents();
        setLocationRelativeTo(null);

        // 2. Controladores y datos maestros
        adm = new AdmDatos();
        cCita = new CitaJpaController(adm.getEnf());
        cTutoradoController = new TutoradoJpaController(adm.getEnf());
        cTutoria = new TutoriaJpaController(adm.getEnf());
        listaTotalCitas = cCita.findCitaEntities();
        listaTotalTutoradosGeneral = cTutoradoController.findTutoradoEntities();

        // 3. Modelo de la tabla y asignación a JTable
        datosParaTablaTutorados = new ArrayList<>();
        modeloTablaTutoradosEnCita = new MTablaCita((ArrayList<DatosTablaCitas>) datosParaTablaTutorados); // Aquí ya no necesitas el cast (ArrayList) si datosParaTablaTutorados es ArrayList
        tabTutorados.setModel(modeloTablaTutoradosEnCita);

        // 4. Configuración inicial de otros componentes UI
        cboCitas.removeAllItems();
        cboCitas.addItem(SELECCIONA_CITA_TEXT);
        dateSeleccionarFecha.setDate(null);

        // 5. Añadir Listeners DESPUÉS de que todo lo referenciado por ellos esté inicializado
        dateSeleccionarFecha.getDateEditor().addPropertyChangeListener(evt -> {
            if (uiInicializada && "date".equals(evt.getPropertyName())) {
                dateSeleccionarFechaPropertyChange(evt);
            }
        });
        // Considera si necesitas action listener para cboCitas aquí, o si btnAceptarCita es suficiente.
        // cboCitas.addActionListener(evt -> cboCitasActionPerformed(evt));


        // 6. Establecer estado inicial de la UI
        actualizarEstadoComponentes();

        // 7. Marcar UI como completamente inicializada
        uiInicializada = true;
    }
    

    
    private void actualizarEstadoComponentes() {
        boolean fechaHaSidoSeleccionada = (dateSeleccionarFecha.getDate() != null);

        boolean citaValidaSeleccionableEnCombo = false;
        if (fechaHaSidoSeleccionada && cboCitas.getItemCount() > 0 && cboCitas.getSelectedIndex() > 0) {
            Object selectedItemObj = cboCitas.getSelectedItem();
            if (selectedItemObj != null) {
                String selectedItemText = selectedItemObj.toString();
                if (!SELECCIONA_CITA_TEXT.equals(selectedItemText) && !NO_HAY_CITAS_TEXT.equals(selectedItemText)) {
                    citaValidaSeleccionableEnCombo = true;
                }
            }
        }

        // Una cita está "confirmada" si this.citaSeleccionada y this.tutorSeleccionado tienen valor
        // (lo que ocurre DESPUÉS de presionar btnAceptarCita)
        boolean citaHaSidoConfirmada = (this.citaSeleccionada != null && this.tutorSeleccionado != null);

        // Habilitar ComboBox de Citas si hay fecha
        cboCitas.setEnabled(fechaHaSidoSeleccionada);
        jLabel3.setEnabled(fechaHaSidoSeleccionada); // Etiqueta "Cita:"

        // Habilitar botón AceptarCita si hay una cita válida seleccionable en el combo
        btnAceptarCita.setEnabled(dateSeleccionarFecha.getDate() != null);

        // Componentes que dependen de que una cita haya sido confirmada
        jLabel2.setEnabled(citaHaSidoConfirmada); // "Tutorados"
        jScrollPane1.setEnabled(citaHaSidoConfirmada);
        tabTutorados.setEnabled(citaHaSidoConfirmada);
        // Considerar si el rowCount de la tabla es > 0 para habilitar registrar.
        // Si la tabla está vacía porque el tutor no tiene tutorados, igual se debe poder registrar
        // la tutoría (aunque no haya acciones individuales). Esto depende de tu lógica de negocio.
        // Por ahora, lo mantendremos así:
        btnRegistrarTutoria.setEnabled(citaHaSidoConfirmada && tabTutorados.getRowCount() > 0);


        // Lógica de limpieza si los prerrequisitos no se cumplen
        if (!fechaHaSidoSeleccionada) {
            // Si no hay fecha, cboCitas ya debería estar reseteado por dateSeleccionarFechaPropertyChange
            // pero podemos asegurarlo.
            if (cboCitas.getItemCount() == 0 || !SELECCIONA_CITA_TEXT.equals(cboCitas.getItemAt(0))) {
                cboCitas.removeAllItems();
                cboCitas.addItem(SELECCIONA_CITA_TEXT);
            } else if (cboCitas.getSelectedIndex() !=0 && cboCitas.getItemCount() > 0) {
                 cboCitas.setSelectedIndex(0);
            }
            // Si no hay fecha, no puede haber cita ni tutor seleccionado confirmados
            this.citaSeleccionada = null;
            this.tutorSeleccionado = null;
        }

        if (!citaHaSidoConfirmada) {
            // Si no hay cita confirmada, la tabla de tutorados debe estar vacía.
            // popularTablaTutorados(null) ya se encarga de limpiar 'datosParaTablaTutorados'
            // y llamar a actualizarListaDatos en el modelo.
            // Así que esta limpieza aquí podría ser redundante si popularTablaTutorados(null) se llama correctamente.
            if (datosParaTablaTutorados != null && !datosParaTablaTutorados.isEmpty()) {
                datosParaTablaTutorados.clear();
                 if (modeloTablaTutoradosEnCita != null) { // Comprobar si el modelo está inicializado
                    modeloTablaTutoradosEnCita.actualizarListaDatos((ArrayList<DatosTablaCitas>) datosParaTablaTutorados);
                }
            }
        }
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
        
        SimpleDateFormat sdfFechaUnicamente = new SimpleDateFormat("dd/MM/yyyy");
        boolean hayCitasDisponibles = false;

        for (Cita c : listaTotalCitas) {
            // Comprueba que la cita pertenezca al tutor seleccionado Y que su estado sea "PENDIENTE"
            if (c.getTutor() != null && c.getTutor().equals(tutor) && "PENDIENTE".equalsIgnoreCase(c.getEstado())) {
                // ... el resto de la lógica para formatear displayText y añadir a cboCitas y citaMap permanece igual ...
                String fechaFormateada = (c.getFecha() != null) ? sdfFechaUnicamente.format(c.getFecha()) : "Fecha N/A";
                String horaFormateada = (c.getHora() != null) ? String.format("%02d:00", c.getHora()) : "Hora N/A";
                String displayText = String.format("%s a las %s (%s)", fechaFormateada, horaFormateada, c.getAsunto());

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
        if (datosParaTablaTutorados == null) { // Asegurar que la lista de datos exista
            datosParaTablaTutorados = new ArrayList<>();
        }
        datosParaTablaTutorados.clear();

        if (tutorDeLaCita != null) {
            for (Tutorado unTutorado : listaTotalTutoradosGeneral) {
                if (unTutorado.getTutor() != null && unTutorado.getTutor().equals(tutorDeLaCita)) {
                    datosParaTablaTutorados.add(new DatosTablaCitas(unTutorado));
                }
            }
        }

        // Comprobación crucial ANTES de usar el modelo
        if (modeloTablaTutoradosEnCita != null) {
            modeloTablaTutoradosEnCita.actualizarListaDatos((ArrayList<DatosTablaCitas>) datosParaTablaTutorados); // No necesitas el cast si datosParaTablaTutorados es ArrayList
        } else {
            // Esto indica un problema de inicialización más profundo si ocurre fuera del arranque inicial.
            System.err.println("ERROR: modeloTablaTutoradosEnCita es null en popularTablaTutorados. La tabla no se actualizará.");
            // Podrías intentar re-inicializarlo como último recurso, pero es mejor encontrar la causa raíz.
            // modeloTablaTutoradosEnCita = new MTablaCita(datosParaTablaTutorados);
            // tabTutorados.setModel(modeloTablaTutoradosEnCita);
        }

        // La reconfiguración del editor solo si la tabla y el modelo existen
        if (tabTutorados != null && tabTutorados.getColumnCount() > 2 && modeloTablaTutoradosEnCita != null) {
            TableColumn accionesColumn = tabTutorados.getColumnModel().getColumn(2);
            JComboBox<String> comboBoxEditor = new JComboBox<>(new String[]{"Sin acción", "Asesoría", "Trámite", "Atención Médica / Psicológica"});
            accionesColumn.setCellEditor(new DefaultCellEditor(comboBoxEditor));
            // System.out.println("Editor de JComboBox para columna 'Acción' re-configurado.");
        } else if (tabTutorados != null && modeloTablaTutoradosEnCita != null) { // Modelo existe pero no columnas suficientes
            // System.out.println("Advertencia: La tabla no tiene suficientes columnas para configurar el editor de 'Acción'. Columnas: " + tabTutorados.getColumnCount());
        }

        if (uiInicializada) { // Solo llama si la UI está lista
            actualizarEstadoComponentes();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabTutorados = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboCitas = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnAceptarCita = new javax.swing.JButton();
        btnRegistrarTutoria = new javax.swing.JButton();
        dateSeleccionarFecha = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();

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

        jLabel2.setText("Tutorados");

        jLabel3.setText("Cita:");

        cboCitas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Realizando una Tutoria");

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

        dateSeleccionarFecha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateSeleccionarFechaPropertyChange(evt);
            }
        });

        jLabel5.setText("Fecha:");

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
                .addContainerGap(50, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRegistrarTutoria)
                        .addGap(162, 162, 162))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateSeleccionarFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboCitas, 0, 200, Short.MAX_VALUE))
                        .addGap(49, 49, 49)
                        .addComponent(btnAceptarCita)
                        .addGap(25, 25, 25))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel4)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateSeleccionarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
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
        if (dateSeleccionarFecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Primero debe seleccionar una fecha.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Valida que no sea el placeholder o el mensaje "No hay citas..."
        if (cboCitas.getSelectedIndex() <= 0 || 
            SELECCIONA_CITA_TEXT.equals(cboCitas.getSelectedItem()) || 
            NO_HAY_CITAS_TEXT.equals(cboCitas.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita válida de la lista.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            this.citaSeleccionada = null;
            this.tutorSeleccionado = null;
            popularTablaTutorados(null); // Limpia la tabla
            // actualizarEstadoComponentes(); // popularTablaTutorados ya lo llama
            return;
        }

        String citaDisplayText = (String) cboCitas.getSelectedItem();
        Cita citaDelCombo = citaMap.get(citaDisplayText);

        if (citaDelCombo != null) {
            if (citaDelCombo.getTutor() == null) {
                JOptionPane.showMessageDialog(this, "La cita seleccionada no tiene un tutor asignado. No se puede proceder.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
                this.citaSeleccionada = null;
                this.tutorSeleccionado = null;
                popularTablaTutorados(null);
                return;
            }
            this.citaSeleccionada = citaDelCombo;
            this.tutorSeleccionado = this.citaSeleccionada.getTutor(); // Establecer el tutor basado en la cita

            popularTablaTutorados(this.tutorSeleccionado); // Popular tabla con tutorados de este tutor
        } else {
            JOptionPane.showMessageDialog(this, "Cita seleccionada no encontrada en el mapa. Intente de nuevo.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            this.citaSeleccionada = null;
            this.tutorSeleccionado = null;
            popularTablaTutorados(null);
        }
        // actualizarEstadoComponentes(); // popularTablaTutorados ya lo llama// Actualizar el estado de la UI
    }//GEN-LAST:event_btnAceptarCitaActionPerformed

    private void btnRegistrarTutoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarTutoriaActionPerformed
        if (citaSeleccionada == null || citaSeleccionada.getTutor() == null) {
            JOptionPane.showMessageDialog(this, "Debe tener una cita válida aceptada (con tutor asignado).", "Información Incompleta", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // No es necesario verificar datosParaTablaTutorados.isEmpty() aquí si el modelo
        // maneja una fila de totales, porque getRowCount() sería al menos 1.
        // Es mejor verificar si hay datos *reales* de tutorados.
        if (datosParaTablaTutorados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay tutorados listados para esta cita para registrar.", "Información Incompleta", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (this.tutorSeleccionado != null && !citaSeleccionada.getTutor().equals(this.tutorSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Inconsistencia de datos del tutor. Por favor, re-seleccione la cita.", "Error de Consistencia", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tutoriasRegistradasExitosamente = 0;

        // Iterar SOLAMENTE sobre las filas de datos, NO sobre la fila de totales.
        // El tamaño de 'datosParaTablaTutorados' es el número de filas de datos reales.
        for (int i = 0; i < datosParaTablaTutorados.size(); i++) { // <<--- CAMBIO IMPORTANTE AQUÍ
            DatosTablaCitas datosFila = datosParaTablaTutorados.get(i); // Ahora 'i' siempre será un índice válido para datosParaTablaTutorados
            Tutorado tutoradoDeLaFila = datosFila.getTutorado();

            // Obtener valores de la tabla (modelo) para la fila 'i' (que corresponde a una fila de datos)
            Boolean asistencia = (Boolean) modeloTablaTutoradosEnCita.getValueAt(i, 1);
            String accion = (String) modeloTablaTutoradosEnCita.getValueAt(i, 2);

            if (tutoradoDeLaFila != null && asistencia != null && asistencia) {
                Tutoria nuevaTutoria = new Tutoria();
                nuevaTutoria.setIdCita(citaSeleccionada);
                nuevaTutoria.setIdTutorado(tutoradoDeLaFila);
                nuevaTutoria.setAcciones((accion == null || accion.equals("Sin acción") || accion.trim().isEmpty()) ? "Asistencia registrada." : accion);

                try {
                    cTutoria.create(nuevaTutoria);
                    tutoriasRegistradasExitosamente++;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al guardar tutoría para " + tutoradoDeLaFila.getNombre() + ": " + ex.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }

        // ... (resto del método para actualizar estado de cita y limpiar, como lo tenías)
        if (tutoriasRegistradasExitosamente > 0) {
            try {
                Cita citaParaActualizar = cCita.findCita(citaSeleccionada.getIdCita());
                if (citaParaActualizar != null) {
                    citaParaActualizar.setEstado("REALIZADA");
                    cCita.edit(citaParaActualizar);
                    int indexEnListaMaestra = listaTotalCitas.indexOf(citaSeleccionada);
                    if (indexEnListaMaestra != -1) {
                        listaTotalCitas.set(indexEnListaMaestra, citaParaActualizar);
                    }
                    citaSeleccionada = citaParaActualizar;
                    JOptionPane.showMessageDialog(this, tutoriasRegistradasExitosamente + " tutoría(s) registrada(s) exitosamente. La cita ha sido marcada como 'REALIZADA'.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error: No se pudo recargar la cita para actualizar su estado.", "Error de Actualización", JOptionPane.ERROR_MESSAGE);
                }
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
        } else if (!datosParaTablaTutorados.isEmpty()) { // Se modificó esta condición para ser más precisa
            boolean algunaAsistenciaMarcada = false;
            for (int i = 0; i < datosParaTablaTutorados.size(); i++) { // Iterar sobre datos reales
                Boolean asistencia = (Boolean) modeloTablaTutoradosEnCita.getValueAt(i, 1);
                if (asistencia != null && asistencia) {
                    algunaAsistenciaMarcada = true;
                    break;
                }
            }
            if(!algunaAsistenciaMarcada){
                 JOptionPane.showMessageDialog(this, "No se marcaron asistencias. La cita no se marcará como 'REALIZADA'.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "No se registraron tutorías aunque hubo asistencias marcadas (posible error previo). Verifique.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } else { // datosParaTablaTutorados está vacío
             JOptionPane.showMessageDialog(this, "No hay tutorados en la lista para registrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        limpiarCamposYInterfaz();
    }//GEN-LAST:event_btnRegistrarTutoriaActionPerformed

    private void cboTutoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTutoresActionPerformed
        
    }//GEN-LAST:event_cboTutoresActionPerformed

    private void dateSeleccionarFechaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateSeleccionarFechaPropertyChange
        // El listener ya filtra por evt.getPropertyName() == "date"
        java.util.Date fechaSeleccionada = dateSeleccionarFecha.getDate();

        // Limpiar selecciones previas y tabla independientemente de si hay nueva fecha o no.
        // Esto asegura que al cambiar la fecha (incluso a null), la tabla se limpie.
        this.citaSeleccionada = null;
        this.tutorSeleccionado = null; // El tutor se deriva de la cita
        popularTablaTutorados(null); // Limpia la tabla

        if (fechaSeleccionada != null) {
            cargarCitasDelDia(fechaSeleccionada); // Carga nuevas citas y actualiza UI
        } else {
            // Si la fecha se borra, limpiar cboCitas
            cboCitas.removeAllItems();
            cboCitas.addItem(SELECCIONA_CITA_TEXT);
            citaMap.clear();
            actualizarEstadoComponentes(); // Actualiza la UI
        }
    }//GEN-LAST:event_dateSeleccionarFechaPropertyChange
    
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
        tutorSeleccionado = null;
        citaSeleccionada = null;

        // Desactivar temporalmente el listener del JDateChooser para evitar llamadas recursivas o prematuras
        // Esto es una técnica común si el setDate(null) dispara el evento y causa problemas.
        boolean listenerWasActive = uiInicializada;
        uiInicializada = false; // Desactivar temporalmente el procesamiento del evento
        dateSeleccionarFecha.setDate(null);
        uiInicializada = listenerWasActive; // Restaurar estado del listener

        cboCitas.removeAllItems();
        cboCitas.addItem(SELECCIONA_CITA_TEXT);
        citaMap.clear();

        if(datosParaTablaTutorados == null) datosParaTablaTutorados = new ArrayList<>();
        datosParaTablaTutorados.clear();

        if(modeloTablaTutoradosEnCita != null) { // Comprobar nulidad
            modeloTablaTutoradosEnCita.actualizarListaDatos((ArrayList<DatosTablaCitas>) datosParaTablaTutorados); 
        } else {
            // Si el modelo es null aquí, hay un problema de inicialización.
            // Como mínimo, la lista de datos está vacía.
            System.err.println("ADVERTENCIA: modeloTablaTutoradosEnCita es null en limpiarCamposYInterfaz.");
        }
        actualizarEstadoComponentes();
    }
    
    private void cargarCitasDelDia(java.util.Date fechaSeleccionada) {
        cboCitas.removeAllItems();
        cboCitas.addItem(SELECCIONA_CITA_TEXT); // Placeholder inicial
        citaMap.clear();

        // Cuando se carga por fecha, la cita y el tutor seleccionados previamente deben resetearse
        // hasta que el usuario explícitamente acepte una nueva cita.
        this.citaSeleccionada = null; 
        this.tutorSeleccionado = null;

        if (fechaSeleccionada == null) {
            actualizarEstadoComponentes(); // Actualiza la UI (ej. deshabilita btnAceptarCita)
            return;
        }

        SimpleDateFormat sdfCompararFecha = new SimpleDateFormat("yyyyMMdd");
        String fechaSeleccionadaStr = sdfCompararFecha.format(fechaSeleccionada);
        boolean hayCitasDisponibles = false;

        // System.out.println("Filtrando citas para la fecha: " + fechaSeleccionadaStr);

        for (Cita c : listaTotalCitas) {
            if (c.getFecha() != null && "PENDIENTE".equalsIgnoreCase(c.getEstado())) {
                String fechaCitaStr = sdfCompararFecha.format(c.getFecha());
                if (fechaCitaStr.equals(fechaSeleccionadaStr)) {
                    String tutorNombre = (c.getTutor() != null && c.getTutor().getNombre() != null) ? c.getTutor().getNombre() : "Tutor Desconocido";
                    String horaFormateada = (c.getHora() != null) ? String.format("%02d:00", c.getHora()) : "Hora N/A";
                    String asunto = (c.getAsunto() != null && !c.getAsunto().isEmpty()) ? c.getAsunto() : "Sin Asunto";

                    String displayText = String.format("Tutor: %s - %s (%s)", tutorNombre, horaFormateada, asunto);

                    cboCitas.addItem(displayText);
                    citaMap.put(displayText, c);
                    hayCitasDisponibles = true;
                }
            }
        }

        if (!hayCitasDisponibles) { // Si no se añadió ninguna cita real
            // Y solo está el item "Seleccione Cita..."
            if (cboCitas.getItemCount() <= 1) {
                 cboCitas.addItem(NO_HAY_CITAS_TEXT);
            }
        }

        if (cboCitas.getItemCount() > 0) {
            cboCitas.setSelectedIndex(0); // Dejar "Seleccione Cita..." por defecto
        }
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
    private javax.swing.JButton btnRegistrarTutoria;
    private javax.swing.JComboBox<String> cboCitas;
    private com.toedter.calendar.JDateChooser dateSeleccionarFecha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabTutorados;
    // End of variables declaration//GEN-END:variables
}
