/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import control.AdmDatos;
import control.CitaJpaController;
import control.TutorJpaController;
import control.TutoradoJpaController;
import control.TutoriaJpaController;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import modelo.Cita;
import modelo.DatosTablaCitas;
import modelo.MTablaCita;
import modelo.MTtutor;
import modelo.Tutor;
import modelo.Tutorado;
import modelo.Tutoria;

/**
 *
 * @author jesus
 */
public class ITutoria extends javax.swing.JFrame {

    private Tutor tutor;
    private TutorJpaController cTutor;
    private List<Tutor> tutores;
    private Tutorado tutorado;
    private Cita cita;
    private List<Cita> citas;
    private TutoradoJpaController cTutorado;
    private AdmDatos adm;
    private List<Tutorado> tutorados;
    private List<Tutorado> tutorados_a;
    private Map<String, Tutor> tutor_nom = new HashMap<>();
    private Map<String, Cita> cita_nom = new HashMap<>();
    private MTtutor mtt;
    private MTablaCita modTablaCita;
    private ArrayList<DatosTablaCitas> datosCitas;
    private CitaJpaController cCita;
    private final String SELECCIONA = "Selecciona Tutor";
    private final String SELECCIONADO = "Tutor Seleccionado";
    private Tutoria tutoria;
    private TutoriaJpaController cTutoria;
    
    // Añade estas variables adicionales
    private Map<String, Tutor> tutorMap = new HashMap<>();
    private Map<String, Cita> citaMap = new HashMap<>();
    private MTablaCita tableModel;
    private List<Tutorado> currentTutorados = new ArrayList<>();
    
            
    public ITutoria() {
        initComponents();
        adm = new AdmDatos();
        cTutor = new TutorJpaController(adm.getEnf());
        tutores = cTutor.findTutorEntities();
        cTutorado = new TutoradoJpaController(adm.getEnf());
        tutorados = cTutorado.findTutoradoEntities();
        cCita = new CitaJpaController(adm.getEnf());
        citas = cCita.findCitaEntities();
        cTutoria = new TutoriaJpaController(adm.getEnf());
        cargarTutores();
    }
    public void cargarTutores(){
        cboTutores.removeAllItems();
        cboTutores.addItem(SELECCIONA);
        tutorMap.clear();

        for(Tutor t : tutores) {
            cboTutores.addItem(t.getNombre());
            tutorMap.put(t.getNombre(), t);
        }
    }
    public void cargarCitas(Tutor tutor) {
        cboCitas.removeAllItems();
        citaMap.clear();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for(Cita c : citas) {
            // Corregir la verificación del tutor y estado
            if(c.getTutor().equals(tutor) && !"Realizada".equals(c.getEstado())) {
                String fecha = sdf.format(c.getFecha());
                cboCitas.addItem(fecha);
                citaMap.put(fecha, c);
            }
        }
    }
    
    private void actualizarTabla() {
        tableModel = new MTablaCita((ArrayList) currentTutorados);
        jTable1.setModel(tableModel);

        // Configurar combo box de acciones
        TableColumn accionesColumn = jTable1.getColumnModel().getColumn(2);
        JComboBox<String> comboBox = new JComboBox<>(new String[]{"Sin acción", "Seguimiento", "Recomendación", "Aprobado"});
        accionesColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
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

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTable1);

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
        String selectedFecha = (String) cboCitas.getSelectedItem();
        if (selectedFecha == null || selectedFecha.equals(SELECCIONA)) {
            JOptionPane.showMessageDialog(this, "Selecciona una cita válida.");
            return;
        }

        cita = citaMap.get(selectedFecha);
        if (cita == null) {
            JOptionPane.showMessageDialog(this, "Cita no encontrada.");
            return;
        }

        // Obtener tutorados asociados al tutor
        currentTutorados = new ArrayList<>();
        for (Tutorado t : tutorados) {
            if (t.getTutor() != null && t.getTutor().equals(tutor)) {
                currentTutorados.add(t);
            }
        }

        // Actualizar modelo de tabla
        datosCitas = new ArrayList<>();
        for (Tutorado t : currentTutorados) {
            datosCitas.add(new DatosTablaCitas(t));
        }

        modTablaCita = new MTablaCita(datosCitas);
        jTable1.setModel(modTablaCita);

        // Configurar combo de acciones
        TableColumn accionesColumn = jTable1.getColumnModel().getColumn(2);
        JComboBox<String> comboBox = new JComboBox<>(
            new String[]{"Sin acción", "Seguimiento", "Recomendación", "Aprobado"}
        );
        accionesColumn.setCellEditor(new DefaultCellEditor(comboBox));
    }//GEN-LAST:event_btnAceptarCitaActionPerformed

    private void btnRegistrarTutoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarTutoriaActionPerformed
        if (tutor == null || cita == null || currentTutorados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete todos los campos primero.");
            return;
        }

        // Asegurar que la cita tiene el tutor asignado
        cita.setTutor(tutor);  // Asumiendo que Cita tiene un campo Tutor con setTutor()

        TableModel model = jTable1.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            String nombre = (String) model.getValueAt(i, 0);
            String accion = (String) model.getValueAt(i, 2);

            Tutorado tutoradoActual = null;
            for (Tutorado t : currentTutorados) {
                if (t.getNombre().equals(nombre)) {
                    tutoradoActual = t;
                    break;
                }
            }

            if (tutoradoActual != null) {
                Tutoria nuevaTutoria = new Tutoria();
                nuevaTutoria.setIdCita(cita);  // Método correcto
                nuevaTutoria.setIdTutorado(tutoradoActual);  // Método correcto
                nuevaTutoria.setAcciones(accion);  // Método correcto

                try {
                    cTutoria.create(nuevaTutoria);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al guardar tutoría: " + ex.getMessage());
                }
            }
        }

        // Actualizar estado de la cita
        cita.setEstado("Realizada");
        try {
            cCita.edit(cita);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar cita: " + ex.getMessage());
        }

        JOptionPane.showMessageDialog(this, "Tutoría registrada exitosamente!");
        limpiarCampos();
    }//GEN-LAST:event_btnRegistrarTutoriaActionPerformed

    private void btnAceptarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarTutorActionPerformed
        if (cboTutores.getSelectedItem().equals(SELECCIONA)) {
            JOptionPane.showMessageDialog(this, "Selecciona un tutor válido.");
            return;
        }

        String nombreTutor = (String) cboTutores.getSelectedItem();
        tutor = tutorMap.get(nombreTutor);

        if (tutor != null) {
            cargarCitas(tutor);
        } else {
            JOptionPane.showMessageDialog(this, "Tutor no encontrado.");
        }
    }//GEN-LAST:event_btnAceptarTutorActionPerformed

    // Método auxiliar para limpiar campos después de guardar
    private void limpiarCampos() {
        cboTutores.setSelectedIndex(0);
        cboCitas.removeAllItems();
        currentTutorados.clear();
        datosCitas.clear();
        modTablaCita = new MTablaCita(new ArrayList<>());
        jTable1.setModel(modTablaCita);
        tutor = null;
        cita = null;
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
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

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
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
