/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatLightLaf;
import control.AdmDatos;
import control.TutorJpaController;
import control.TutoradoJpaController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.SelectionModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.MTtutor;
import modelo.Tutor;
import modelo.Tutorado;

/**
 *
 * @author jesus
 */
public class ITutorado extends javax.swing.JFrame {

    private Tutor tutor;
    private TutorJpaController cTutor;
    private AdmDatos adm;
    private List<Tutor> tutores;
    
    private Tutorado tutorado;
    private List<Tutorado> tutorados;
    private TutoradoJpaController cTutorado;

    private MTtutor mtt; //Inecesario
    
    //Modelo del Combo Box y Listas
    private DefaultListModel mTutoradosDisponibles;
    private DefaultListModel mTutoradosSeleccionados;
    
    private Map<String, Tutorado> tutorado_nom = new HashMap<>();
    
    public ITutorado() {
        initComponents();
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        
        cTutor = new TutorJpaController(adm.getEnf());
        tutores = cTutor.findTutorEntities();
        cTutorado = new TutoradoJpaController(adm.getEnf());
        tutorados = cTutorado.findTutoradoEntities();
        
        cargarTutores();
        cargarTutorado();
        
        lisTutoradosDisponibles.setModel(mTutoradosDisponibles);
        lisTutoradosDisponibles.setSelectionMode(1);
        
        lisTutoradosSeleccionados.setModel(mTutoradosSeleccionados);
        
        btnMarcar.setEnabled(false);
        btnDesmarcar.setEnabled(false);
        
    }
    
    public void cargarTutores(){
        cboTutores.removeAllItems();
        cboTutores.addItem("Seleccione Tutor");
        
        for(Tutor dtutor : tutores){
            cboTutores.addItem(dtutor.getNombre());
        }
    }
    
    public void cargarTutorado(){
        mTutoradosDisponibles = new DefaultListModel();
        mTutoradosSeleccionados = new DefaultListModel();
        tutorado_nom.clear(); // Limpiar el mapa anterior

        for(Tutorado dTutorado : tutorados){
            if(dTutorado.getTutor() == null){
                String nombre = dTutorado.getNombre();
                mTutoradosDisponibles.addElement(nombre);
                tutorado_nom.put(nombre, dTutorado); // Usar nombre como clave
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane1 = new javax.swing.JTabbedPane();
        btnAceptar = new javax.swing.JPanel();
        cboTutores = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lisTutoradosDisponibles = new javax.swing.JList<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        lisTutoradosSeleccionados = new javax.swing.JList<>();
        btnMarcar = new javax.swing.JButton();
        btnDesmarcar = new javax.swing.JButton();
        btnAceptarSeleccion = new javax.swing.JButton();
        btnAceptarTutor = new javax.swing.JButton();
        panel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cboTutores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Seleccionar Tutor:");

        jLabel2.setText("Selección de Tutorados");

        lisTutoradosDisponibles.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(lisTutoradosDisponibles);

        lisTutoradosSeleccionados.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(lisTutoradosSeleccionados);

        btnMarcar.setText("-->");
        btnMarcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMarcarActionPerformed(evt);
                btnMarcarActionPerformed1(evt);
            }
        });

        btnDesmarcar.setText("<--");
        btnDesmarcar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDesmarcarActionPerformed(evt);
            }
        });

        btnAceptarSeleccion.setText("Aceptar");
        btnAceptarSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarSeleccionActionPerformed(evt);
            }
        });

        btnAceptarTutor.setText("AceptarTutor");
        btnAceptarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarTutorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnAceptarLayout = new javax.swing.GroupLayout(btnAceptar);
        btnAceptar.setLayout(btnAceptarLayout);
        btnAceptarLayout.setHorizontalGroup(
            btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAceptarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnAceptarLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cboTutores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAceptarTutor)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAceptarLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnMarcar, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                            .addComponent(btnDesmarcar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAceptarSeleccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAceptarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2)
                .addGap(209, 209, 209))
        );
        btnAceptarLayout.setVerticalGroup(
            btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAceptarLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboTutores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptarTutor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGroup(btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(btnAceptarLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(btnAceptarLayout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(btnMarcar)
                        .addGap(36, 36, 36)
                        .addComponent(btnDesmarcar)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addComponent(btnAceptarSeleccion)
                .addGap(15, 15, 15))
        );

        tabbedPane1.addTab("Asignar Tutor", btnAceptar);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 557, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 295, Short.MAX_VALUE)
        );

        tabbedPane1.addTab("Registrar Tutorado", panel2);

        getContentPane().add(tabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 330));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnMarcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnMarcarActionPerformed

    private void btnMarcarActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMarcarActionPerformed1
        // TODO add your handling code here:
        int indEstudiantesSeleccionados[] = lisTutoradosDisponibles.getSelectedIndices();
        
        for(int i = indEstudiantesSeleccionados.length - 1; i >= 0; i--){
            mTutoradosSeleccionados.addElement(mTutoradosDisponibles.get(indEstudiantesSeleccionados[i]));
            mTutoradosDisponibles.remove(indEstudiantesSeleccionados[i]);
        }
    }//GEN-LAST:event_btnMarcarActionPerformed1

    private void btnAceptarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarTutorActionPerformed
        if(cboTutores.getSelectedItem().equals("Seleccione Tutor")){
            JOptionPane.showMessageDialog(this, "Seleccione Tutor", "Adevertencia", JOptionPane.INFORMATION_MESSAGE);
        }
        else{
            if(JOptionPane.showConfirmDialog(this, "Seleccione al tutor " + cboTutores.getSelectedItem() + "?") == 0){
                tutor = tutores.get(cboTutores.getSelectedIndex()-1);
                cboTutores.setEnabled(false);
                btnAceptarTutor.setText("Seleccionado");
                btnAceptarTutor.setEnabled(false);
                
                btnMarcar.setEnabled(true);
                btnDesmarcar.setEnabled(true);
                
                if(!mTutoradosDisponibles.isEmpty()){
                    btnMarcar.setEnabled(true);
                }
            }
        }
    }//GEN-LAST:event_btnAceptarTutorActionPerformed

    private void btnAceptarSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarSeleccionActionPerformed
        // Iterar sobre los elementos del modelo, no por índices
        for(int i = 0; i < mTutoradosSeleccionados.getSize(); i++){
            String nombreTutorado = (String) mTutoradosSeleccionados.getElementAt(i);
            Tutorado t = tutorado_nom.get(nombreTutorado); // Usar el nombre como clave

            if(t != null){
                t.setTutor(tutor);
                try{
                    cTutorado.edit(t);
                } 
                catch (Exception ex){
                    Logger.getLogger(ITutorado.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        // Actualizar la lista de disponibles
        cargarTutorado();
        JOptionPane.showMessageDialog(this, "Tutorados asignados correctamente");
    }//GEN-LAST:event_btnAceptarSeleccionActionPerformed

    private void btnDesmarcarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDesmarcarActionPerformed
        int[] indices = lisTutoradosSeleccionados.getSelectedIndices();

        for(int i = indices.length - 1; i >= 0; i--){
            String nombre = (String) mTutoradosSeleccionados.getElementAt(indices[i]);
            mTutoradosDisponibles.addElement(nombre);
            mTutoradosSeleccionados.remove(indices[i]);
        }
    }//GEN-LAST:event_btnDesmarcarActionPerformed

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
        UIManager.setLookAndFeel(new FlatLightLaf());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ITutorado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAceptar;
    private javax.swing.JButton btnAceptarSeleccion;
    private javax.swing.JButton btnAceptarTutor;
    private javax.swing.JButton btnDesmarcar;
    private javax.swing.JButton btnMarcar;
    private javax.swing.JComboBox<String> cboTutores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList<String> lisTutoradosDisponibles;
    private javax.swing.JList<String> lisTutoradosSeleccionados;
    private javax.swing.JPanel panel2;
    private javax.swing.JTabbedPane tabbedPane1;
    // End of variables declaration//GEN-END:variables
}
