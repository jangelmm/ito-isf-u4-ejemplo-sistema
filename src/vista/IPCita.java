/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatDarkLaf;
import control.AdmDatos;
import control.CitaJpaController;
import control.TutorJpaController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Cita;
import modelo.MTcita;
import modelo.Tutor;
import modelo.Tutorado;


public class IPCita extends javax.swing.JFrame {

    private Cita cita;
    private CitaJpaController cCita;
    private AdmDatos adm;
    private List<Cita> citas;
    private MTcita mtc;
    
    public IPCita() {
        initComponents();
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        cCita = new CitaJpaController(adm.getEnf());
        citas = cCita.findCitaEntities();
        mtc = new MTcita(citas);
        tablaCitas.setModel(mtc);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnAgregarCita = new javax.swing.JButton();
        btnLimpiarDatos = new javax.swing.JButton();
        btnActualizarCita = new javax.swing.JButton();
        btnEliminarCita = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCitas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jDfecha = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAsunto = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        cboEstado = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtNumTargetaTutor = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel5.setText("Hora:");

        btnAgregarCita.setText("Agregar Cita");
        btnAgregarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCitaActionPerformed(evt);
            }
        });

        btnLimpiarDatos.setText("Limpiar Datos");
        btnLimpiarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarDatosActionPerformed(evt);
            }
        });

        btnActualizarCita.setText("Actualizar Cita");
        btnActualizarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarCitaActionPerformed(evt);
            }
        });

        btnEliminarCita.setText("Eliminar Cita");
        btnEliminarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCitaActionPerformed(evt);
            }
        });

        tablaCitas.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaCitasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCitas);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Citas");

        jLabel6.setText("Fecha de Cita:");

        jLabel7.setText("Asunto");

        txtAsunto.setColumns(20);
        txtAsunto.setRows(5);
        jScrollPane2.setViewportView(txtAsunto);

        jLabel2.setText("Asunto");

        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PENDIENTE", "COMPLETADA", "CANCELADA" }));

        jLabel3.setText("Num. Tarjeta de Tutor");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDfecha, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(txtHora)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboEstado, 0, 181, Short.MAX_VALUE)
                            .addComponent(txtNumTargetaTutor))))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnAgregarCita, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                .addComponent(btnLimpiarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                            .addGap(58, 58, 58)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnActualizarCita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminarCita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addContainerGap(598, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(cboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNumTargetaTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 91, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jLabel1)
                    .addGap(393, 393, 393)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregarCita)
                        .addComponent(btnActualizarCita))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLimpiarDatos)
                        .addComponent(btnEliminarCita))
                    .addGap(9, 9, 9)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void tablaCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCitasMouseClicked

        int fila = tablaCitas.getSelectedRow();
        if(fila == -1) return;
        
        Cita cita = citas.get(fila);
        
        jDfecha.setDate(cita.getFecha());
        txtHora.setText(String.valueOf(cita.getHora()));
        txtAsunto.setText(cita.getAsunto());
        cboEstado.setSelectedItem(cita.getEstado());
        txtNumTargetaTutor.setText(
            cita.getTutor() != null ? 
            String.valueOf(cita.getTutor().getNumTarjeta()) : ""
        );
    }//GEN-LAST:event_tablaCitasMouseClicked

    private void btnEliminarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCitaActionPerformed
        int fila = tablaCitas.getSelectedRow();
        
        if(fila == -1){
            JOptionPane.showMessageDialog(this, 
                "Seleccione una cita", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar esta cita?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        
        if(confirmacion != JOptionPane.YES_OPTION) return;
        
        try {
            Cita cita = citas.get(fila);
            cCita.destroy(cita.getIdCita());
            citas.remove(fila);
            mtc.fireTableRowsDeleted(fila, fila);
            limpiarDatos();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al eliminar: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnEliminarCitaActionPerformed

    private void btnActualizarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarCitaActionPerformed
        int fila = tablaCitas.getSelectedRow();
        
        if(fila == -1){
            JOptionPane.showMessageDialog(this, 
                "Seleccione una cita de la tabla", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Cita citaSeleccionada = citas.get(fila);
            
            // Validar campos
            if (jDfecha.getDate() == null || 
                txtHora.getText().trim().isEmpty() || 
                txtAsunto.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(this, 
                    "Campos obligatorios faltantes", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Actualizar datos
            citaSeleccionada.setFecha(jDfecha.getDate());
            citaSeleccionada.setHora(Integer.parseInt(txtHora.getText().trim()));
            citaSeleccionada.setAsunto(txtAsunto.getText().trim());
            citaSeleccionada.setEstado(cboEstado.getSelectedItem().toString());
            
            // Actualizar tutor
            TutorJpaController cTutor = new TutorJpaController(adm.getEnf());
            Tutor tutor = cTutor.findTutor(
                Integer.parseInt(txtNumTargetaTutor.getText().trim())
            );
            
            if(tutor == null) {
                JOptionPane.showMessageDialog(this, 
                    "Tutor no encontrado", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            citaSeleccionada.setTutor(tutor);
            
            cCita.edit(citaSeleccionada);
            mtc.fireTableRowsUpdated(fila, fila);
            limpiarDatos();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnActualizarCitaActionPerformed

    private void btnLimpiarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarDatosActionPerformed
        limpiarDatos();
    }//GEN-LAST:event_btnLimpiarDatosActionPerformed

    private void btnAgregarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCitaActionPerformed
        try {
            // Validar campos obligatorios
            if (jDfecha.getDate() == null || 
                txtHora.getText().trim().isEmpty() || 
                txtAsunto.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this, 
                    "Fecha, hora y asunto son campos obligatorios", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar formato de hora
            int hora;
            try {
                hora = Integer.parseInt(txtHora.getText().trim());
                if(hora < 0 || hora > 23) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Hora inválida (debe ser entre 0 y 23)", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Buscar tutor por número de tarjeta
            TutorJpaController cTutor = new TutorJpaController(adm.getEnf());
            Tutor tutor = cTutor.findTutor(
                Integer.parseInt(txtNumTargetaTutor.getText().trim())
            );

            if(tutor == null) {
                JOptionPane.showMessageDialog(this, 
                    "No existe un tutor con ese número de tarjeta", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear nueva cita (¡Aquí debe estar primero!)
            cita = new Cita();
            cita.setFecha(jDfecha.getDate());
            cita.setHora(hora); // Usar la variable ya validada
            cita.setAsunto(txtAsunto.getText().trim());
            cita.setEstado(cboEstado.getSelectedItem().toString());
            cita.setTutor(tutor);

            cCita.create(cita);
            citas.add(cita);
            mtc.fireTableDataChanged();
            limpiarDatos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear cita: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAgregarCitaActionPerformed

    private void limpiarDatos() {
        jDfecha.setDate(null);
        txtHora.setText("");
        txtAsunto.setText("");
        cboEstado.setSelectedIndex(0);
        txtNumTargetaTutor.setText("");
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
            java.util.logging.Logger.getLogger(IPCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IPCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IPCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IPCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        UIManager.setLookAndFeel(new FlatDarkLaf());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IPCita().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarCita;
    private javax.swing.JButton btnAgregarCita;
    private javax.swing.JButton btnEliminarCita;
    private javax.swing.JButton btnLimpiarDatos;
    private javax.swing.JComboBox<String> cboEstado;
    private com.toedter.calendar.JDateChooser jDfecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaCitas;
    private javax.swing.JTextArea txtAsunto;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtNumTargetaTutor;
    // End of variables declaration//GEN-END:variables
}
