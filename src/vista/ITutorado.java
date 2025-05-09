/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatLightLaf;
import control.AdmDatos;
import control.TutorJpaController;
import control.TutoradoJpaController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        //dlistTutorados.setEnabled(false);
        dlistTutorados.setVisible(false);
        
    }
    
    public void cargarTutores(){
        cboTutores.removeAllItems();
        cboTutores.addItem("Seleccione Tutor");
        
        for(Tutor dtutor : tutores){
            cboTutores.addItem(dtutor.getNombre());
        }
    }
    
    public void cargarTutorado(){
        // Ya no se inicializan mTutoradosDisponibles ni mTutoradosSeleccionados aquí
        tutorado_nom.clear(); // Limpiar el mapa anterior

        List<String> nombresDisponibles = new ArrayList<>();
        for (Tutorado dTutorado : tutorados) {
            if (dTutorado.getTutor() == null) { // Solo tutorados sin tutor asignado
                String nombre = dTutorado.getNombre();
                nombresDisponibles.add(nombre);
                tutorado_nom.put(nombre, dTutorado); // Mapear nombre a objeto Tutorado
            }
        }
        // Poblar el componente DualListTransfer
        dlistTutorados.setElementosDisponibles(nombresDisponibles);
        // Asegurar que la lista de seleccionados en DualListTransfer esté vacía al recargar
        dlistTutorados.setElementosSeleccionadosIniciales(new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane1 = new javax.swing.JTabbedPane();
        btnAceptar = new javax.swing.JPanel();
        cboTutores = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnAceptarSeleccion = new javax.swing.JButton();
        btnAceptarTutor = new javax.swing.JButton();
        dlistTutorados = new elements.DualListTransfer();
        panel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cboTutores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Seleccionar Tutor:");

        jLabel2.setText("Selección de Tutorados");

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
                        .addGap(226, 226, 226)
                        .addComponent(btnAceptarSeleccion, javax.swing.GroupLayout.DEFAULT_SIZE, 86, Short.MAX_VALUE)
                        .addGap(233, 233, 233))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAceptarLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAceptarLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(209, 209, 209))
                    .addComponent(dlistTutorados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(dlistTutorados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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

    private void btnAceptarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarTutorActionPerformed
        if (cboTutores.getSelectedIndex() <= 0) { // "Seleccione Tutor" está en el índice 0
            JOptionPane.showMessageDialog(this, "Seleccione un Tutor válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (JOptionPane.showConfirmDialog(this, "Seleccionar al tutor: " + cboTutores.getSelectedItem() + "?",
                "Confirmar Tutor", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            tutor = tutores.get(cboTutores.getSelectedIndex() - 1); // -1 porque "Seleccione Tutor" es el primer item
            cboTutores.setEnabled(false);
            btnAceptarTutor.setText("Tutor Confirmado");
            btnAceptarTutor.setEnabled(false);

            // Habilitar el componente DualListTransfer para la selección de tutorados
            //dlistTutorados.setEnabled(true);
            dlistTutorados.setVisible(true);
        }
    }//GEN-LAST:event_btnAceptarTutorActionPerformed

    private void btnAceptarSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarSeleccionActionPerformed
        if (tutor == null) {
            JOptionPane.showMessageDialog(this, "Primero debe confirmar un Tutor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> nombresSeleccionados = dlistTutorados.getElementosSeleccionados();

        if (nombresSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ha seleccionado ningún tutorado para asignar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmados = 0;
        for (String nombreTutorado : nombresSeleccionados) {
            Tutorado t = tutorado_nom.get(nombreTutorado); // Usar el nombre como clave

            if (t != null) {
                t.setTutor(tutor); // Asignar el tutor seleccionado
                try {
                    cTutorado.edit(t); // Guardar cambios en la BD
                    confirmados++;
                } catch (Exception ex) {
                    Logger.getLogger(ITutorado.class.getName()).log(Level.SEVERE, "Error al asignar tutor a: " + nombreTutorado, ex);
                    JOptionPane.showMessageDialog(this, "Error al asignar tutor a: " + nombreTutorado + "\n" + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                 Logger.getLogger(ITutorado.class.getName()).log(Level.WARNING, "No se encontró el objeto Tutorado para el nombre: " + nombreTutorado);
            }
        }
        
        if (confirmados > 0) {
             JOptionPane.showMessageDialog(this, confirmados + " tutorado(s) asignado(s) correctamente al tutor: " + tutor.getNombre(), "Asignación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo completar la asignación de tutorados.", "Error de Asignación", JOptionPane.ERROR_MESSAGE);
        }

        tutorados = cTutorado.findTutoradoEntities(); // Recargar lista de tutorados desde la BD
        cargarTutorado(); // Esto refrescará dlistTutorados (disponibles y limpiará seleccionados)

        // Resetear selección de tutor para una nueva asignación
        cboTutores.setEnabled(true);
        cboTutores.setSelectedIndex(0);
        btnAceptarTutor.setText("Confirmar Tutor");
        btnAceptarTutor.setEnabled(true);
        dlistTutorados.setEnabled(false); // Deshabilitar hasta que se confirme un nuevo tutor
        tutor = null; // Limpiar el tutor seleccionado
    }//GEN-LAST:event_btnAceptarSeleccionActionPerformed

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
    private javax.swing.JComboBox<String> cboTutores;
    private elements.DualListTransfer dlistTutorados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel panel2;
    private javax.swing.JTabbedPane tabbedPane1;
    // End of variables declaration//GEN-END:variables
}
