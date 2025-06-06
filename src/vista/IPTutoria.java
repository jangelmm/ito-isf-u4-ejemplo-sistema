/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatDarkLaf;
import control.AdmDatos;
import control.CitaJpaController;
import control.TutoradoJpaController;
import control.TutoriaJpaController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Cita;
import modelo.MTtutoria;
import modelo.Tutorado;
import modelo.Tutoria;

/**
 *
 * @author jesus
 */
public class IPTutoria extends javax.swing.JFrame {

    private Tutoria tutoria;
    private TutoriaJpaController cTutoria;
    private AdmDatos adm;
    private List<Tutoria> tutorias;
    private MTtutoria mtt;
    
    public IPTutoria() {
        initComponents();
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        cTutoria = new TutoriaJpaController(adm.getEnf());
        tutorias = cTutoria.findTutoriaEntities();
        mtt = new MTtutoria(tutorias);
        tablaTutorados.setModel(mtt);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIdTutorado = new javax.swing.JTextField();
        btnAgregarTutorado = new javax.swing.JButton();
        btnLimpiarDatos = new javax.swing.JButton();
        btnActualizarCita = new javax.swing.JButton();
        btnEliminarCita = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTutorados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtIdCita = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("ID cita");

        jLabel3.setText("ID tutorado:");

        btnAgregarTutorado.setText("Agregar Tutoria");
        btnAgregarTutorado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTutoradoActionPerformed(evt);
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

        tablaTutorados.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaTutorados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTutoradosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaTutorados);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Tutoria");

        jLabel4.setText("Acciones:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdTutorado, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdCita, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarTutorado, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLimpiarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnActualizarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3))
                    .addContainerGap(872, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtIdCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdTutorado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminarCita)
                            .addComponent(btnAgregarTutorado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLimpiarDatos, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnActualizarCita, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(29, 29, 29))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jLabel1)
                    .addGap(49, 49, 49)
                    .addComponent(jLabel2)
                    .addGap(27, 27, 27)
                    .addComponent(jLabel3)
                    .addContainerGap(343, Short.MAX_VALUE)))
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

    private void btnAgregarTutoradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTutoradoActionPerformed
        try {
            // Validar campos requeridos
            if (txtIdCita.getText().isEmpty() || txtIdTutorado.getText().isEmpty() || jTextArea1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear nueva tutoría
            tutoria = new Tutoria();
            tutoria.setAcciones(jTextArea1.getText());

            // Obtener entidades relacionadas
            // Actualizar entidades relacionadas
            int idCita = Integer.parseInt(txtIdCita.getText());
            int idTutorado = Integer.parseInt(txtIdTutorado.getText());


            CitaJpaController cCita = new CitaJpaController(adm.getEnf());
            Cita cita = cCita.findCita(idCita);
            
            TutoradoJpaController cTutorado = new TutoradoJpaController(adm.getEnf());
            Tutorado tutorado = cTutorado.findTutorado(idTutorado);

            if(cita == null || tutorado == null) {
                JOptionPane.showMessageDialog(this, "Cita o Tutorado no encontrados", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tutoria.setIdCita(cita);
            tutoria.setIdTutorado(tutorado);

            // Persistir en BD
            cTutoria.create(tutoria);

            // Actualizar tabla
            tutorias.add(tutoria);
            mtt.fireTableDataChanged();
            limpiarDatos();

            JOptionPane.showMessageDialog(this, "Tutoría creada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear tutoría: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAgregarTutoradoActionPerformed

    private void btnLimpiarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarDatosActionPerformed
        limpiarDatos();
    }//GEN-LAST:event_btnLimpiarDatosActionPerformed

    private void btnActualizarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarCitaActionPerformed
        int fila = tablaTutorados.getSelectedRow();

        if(fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una tutoría de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Tutoria tutoriaSeleccionada = tutorias.get(fila);

            // Validar campos
            if (txtIdCita.getText().isEmpty() || txtIdTutorado.getText().isEmpty() || jTextArea1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar entidades relacionadas
            int idCita = Integer.parseInt(txtIdCita.getText());
            int idTutorado = Integer.parseInt(txtIdTutorado.getText());


            CitaJpaController cCita = new CitaJpaController(adm.getEnf());
            Cita cita = cCita.findCita(idCita);
            
            TutoradoJpaController cTutorado = new TutoradoJpaController(adm.getEnf());
            Tutorado tutorado = cTutorado.findTutorado(idTutorado);
            

            if(cita == null || tutorado == null) {
                JOptionPane.showMessageDialog(this, "Cita o Tutorado no encontrados", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar datos
            tutoriaSeleccionada.setAcciones(jTextArea1.getText());
            tutoriaSeleccionada.setIdCita(cita);
            tutoriaSeleccionada.setIdTutorado(tutorado);

            // Persistir cambios
            cTutoria.edit(tutoriaSeleccionada);

            // Actualizar tabla
            mtt.fireTableRowsUpdated(fila, fila);
            limpiarDatos();

            JOptionPane.showMessageDialog(this, "Tutoría actualizada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar tutoría: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnActualizarCitaActionPerformed

    private void btnEliminarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCitaActionPerformed
        int fila = tablaTutorados.getSelectedRow();

        if(fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una tutoría de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar esta tutoría?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );

        if(confirmacion != JOptionPane.YES_OPTION) return;

        try {
            Tutoria tutoria = tutorias.get(fila);
            cTutoria.destroy(tutoria.getIdTutoria());
            tutorias.remove(fila);
            mtt.fireTableRowsDeleted(fila, fila);
            limpiarDatos();

            JOptionPane.showMessageDialog(this, "Tutoría eliminada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar tutoría: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnEliminarCitaActionPerformed

    private void tablaTutoradosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTutoradosMouseClicked
        int fila = tablaTutorados.getSelectedRow();
        if(fila == -1) return;

        Tutoria tutoria = tutorias.get(fila);

        // Mostrar datos en los campos
        txtIdCita.setText(String.valueOf(tutoria.getIdCita().getIdCita()));
        txtIdTutorado.setText(String.valueOf(tutoria.getIdTutorado().getIdTutorado()));
        jTextArea1.setText(tutoria.getAcciones());
    }//GEN-LAST:event_tablaTutoradosMouseClicked
    
    private void limpiarDatos() {
        txtIdCita.setText("");
        txtIdTutorado.setText("");
        jTextArea1.setText("");
        tablaTutorados.clearSelection();
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
            java.util.logging.Logger.getLogger(IPTutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IPTutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IPTutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IPTutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        UIManager.setLookAndFeel(new FlatDarkLaf());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IPTutoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarCita;
    private javax.swing.JButton btnAgregarTutorado;
    private javax.swing.JButton btnEliminarCita;
    private javax.swing.JButton btnLimpiarDatos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTable tablaTutorados;
    private javax.swing.JTextField txtIdCita;
    private javax.swing.JTextField txtIdTutorado;
    // End of variables declaration//GEN-END:variables
}
