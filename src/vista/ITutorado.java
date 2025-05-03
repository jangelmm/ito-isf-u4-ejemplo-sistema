/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatDarkLaf;
import control.AdmDatos;
import control.TutorJpaController;
import control.TutoradoJpaController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.MTtutorado;
import modelo.Tutor;
import modelo.Tutorado;

public class ITutorado extends javax.swing.JFrame {

    private Tutorado tutorado;
    private TutoradoJpaController cTutorado;
    private AdmDatos adm;
    private List<Tutorado> tutores;
    private MTtutorado  mtt;
    
    public ITutorado() {
        initComponents();
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        cTutorado = new TutoradoJpaController(adm.getEnf());
        tutores = cTutorado.findTutoradoEntities();
        mtt = new MTtutorado(tutores);
        tablaTutorados.setModel(mtt);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCLunes = new javax.swing.JCheckBox();
        jCMartes = new javax.swing.JCheckBox();
        jCMiercoles = new javax.swing.JCheckBox();
        jCJueves = new javax.swing.JCheckBox();
        jCViernes = new javax.swing.JCheckBox();
        jCSabado = new javax.swing.JCheckBox();
        btnAgregarTutorado = new javax.swing.JButton();
        btnLimpiarDatos = new javax.swing.JButton();
        btnActualizarTutorado = new javax.swing.JButton();
        btnEliminarTutor = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTutorados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtNumeroControl = new javax.swing.JTextField();
        cobGenero = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jDfecha = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtNumTarjeta = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("No. de Control");

        jLabel3.setText("Nombre:");

        jLabel4.setText("Genero");

        jLabel5.setText("Dias:");

        jCLunes.setText("Lunes");

        jCMartes.setText("Martes");

        jCMiercoles.setText("Miercoles");

        jCJueves.setText("Jueves");

        jCViernes.setText("Viernes");

        jCSabado.setText("Sabado");

        btnAgregarTutorado.setText("Agregar Tutorado");
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

        btnActualizarTutorado.setText("Actualizar Tutorado");
        btnActualizarTutorado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTutoradoActionPerformed(evt);
            }
        });

        btnEliminarTutor.setText("Eliminar Tutorado");
        btnEliminarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTutorActionPerformed(evt);
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
        jLabel1.setText("Tutorados");

        cobGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino", "Otro" }));

        jLabel6.setText("F. de Nacimiento:");

        jLabel7.setText("Num. de Tarjeta del Tutor:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(100, 100, 100))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtNumeroControl)
                        .addComponent(cobGenero, 0, 192, Short.MAX_VALUE))
                    .addComponent(jDfecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addComponent(txtNumTarjeta, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)
                                        .addComponent(jCLunes)
                                        .addComponent(jCMartes)
                                        .addComponent(jCMiercoles)
                                        .addComponent(jCJueves)
                                        .addComponent(jCViernes)
                                        .addComponent(jCSabado)
                                        .addComponent(btnAgregarTutorado, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                        .addComponent(btnLimpiarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(58, 58, 58)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(btnActualizarTutorado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnEliminarTutor, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addContainerGap(600, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNumeroControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(cobGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel6)
                        .addGap(7, 7, 7)
                        .addComponent(jDfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(16, 16, 16)
                        .addComponent(txtNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jLabel1)
                    .addGap(49, 49, 49)
                    .addComponent(jLabel2)
                    .addGap(24, 24, 24)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(jLabel4)
                    .addGap(30, 30, 30)
                    .addComponent(jLabel5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jCLunes)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCMartes)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCMiercoles)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCJueves)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCViernes)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCSabado)
                    .addGap(40, 40, 40)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregarTutorado)
                        .addComponent(btnActualizarTutorado))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLimpiarDatos)
                        .addComponent(btnEliminarTutor))
                    .addGap(9, 9, 9)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarTutoradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTutoradoActionPerformed
        try {
        // Validar campos obligatorios
        if (txtNumeroControl.getText().trim().isEmpty() || 
            txtNombre.getText().trim().isEmpty() || 
            jDfecha.getDate() == null) {
            
            JOptionPane.showMessageDialog(this, 
                "Número de control, nombre y fecha son obligatorios", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        tutorado = new Tutorado();
        
        tutorado.setNc(txtNumeroControl.getText().trim());
        tutorado.setNombre(txtNombre.getText().trim());
        tutorado.setGenero(obtenerGenero());
        tutorado.setDias(generarCadenaDias());
        tutorado.setFechaNacimiento(jDfecha.getDate());
        
        // Obtener tutor por número de tarjeta
        TutorJpaController cTutor = new TutorJpaController(adm.getEnf());
        Tutor tutor = cTutor.findTutor(Integer.parseInt(txtNumTarjeta.getText()));
        
        if(tutor == null) {
            JOptionPane.showMessageDialog(this, 
                "No existe un tutor con ese número de tarjeta", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        tutorado.setTutor(tutor);
        
        cTutorado.create(tutorado);
        tutores.add(tutorado);
        mtt.fireTableDataChanged();
        limpiarDatos();
        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear tutorado: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAgregarTutoradoActionPerformed

    private char obtenerGenero(){
        char opcion = 'O';
            
        String mensaje = cobGenero.getSelectedItem().toString();
        
        if(mensaje.equals("Masculino")){
            opcion = 'M';
        }
        if(mensaje.equals("Femenino")){
            opcion = 'F';
        }
        if(mensaje.equals("Otro")){
            opcion = 'O';
        }
        
        return opcion;
    }
    
    private void btnLimpiarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarDatosActionPerformed
        
    }//GEN-LAST:event_btnLimpiarDatosActionPerformed

    private void btnActualizarTutoradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTutoradoActionPerformed
        
    }//GEN-LAST:event_btnActualizarTutoradoActionPerformed

    private void btnEliminarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTutorActionPerformed
        
    }//GEN-LAST:event_btnEliminarTutorActionPerformed

    private void tablaTutoradosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTutoradosMouseClicked
        limpiarDatos();
    }//GEN-LAST:event_tablaTutoradosMouseClicked
    
    private void limpiarDatos() {
        txtNumeroControl.setText("");
        txtNombre.setText("");
        cobGenero.setSelectedIndex(0);
        jDfecha.setDate(null);
        txtNumTarjeta.setText("");

        jCLunes.setSelected(false);
        jCMartes.setSelected(false);
        jCMiercoles.setSelected(false);
        jCJueves.setSelected(false);
        jCViernes.setSelected(false);
        jCSabado.setSelected(false);
    }
    public String generarCadenaDias() {
        StringBuilder dias = new StringBuilder();

        if (jCLunes.isSelected()) dias.append("L-");
        if (jCMartes.isSelected()) dias.append("M-");
        if (jCMiercoles.isSelected()) dias.append("X-");
        if (jCJueves.isSelected()) dias.append("J-");
        if (jCViernes.isSelected()) dias.append("V-");
        if (jCSabado.isSelected()) dias.append("S-");

        // Eliminar el último guión si hay elementos
        if (dias.length() > 0) {
            dias.deleteCharAt(dias.length() - 1);
        }

        return dias.toString();
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
            java.util.logging.Logger.getLogger(ITutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ITutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ITutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ITutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        UIManager.setLookAndFeel(new FlatDarkLaf());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ITutorado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarTutorado;
    private javax.swing.JButton btnAgregarTutorado;
    private javax.swing.JButton btnEliminarTutor;
    private javax.swing.JButton btnLimpiarDatos;
    private javax.swing.JComboBox<String> cobGenero;
    private javax.swing.JCheckBox jCJueves;
    private javax.swing.JCheckBox jCLunes;
    private javax.swing.JCheckBox jCMartes;
    private javax.swing.JCheckBox jCMiercoles;
    private javax.swing.JCheckBox jCSabado;
    private javax.swing.JCheckBox jCViernes;
    private com.toedter.calendar.JDateChooser jDfecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaTutorados;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumTarjeta;
    private javax.swing.JTextField txtNumeroControl;
    // End of variables declaration//GEN-END:variables
}
