/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import control.AdmDatos;
import control.TutorJpaController;
import control.TutoradoJpaController;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Tutor;
import modelo.Tutorado;



public class ILogin extends javax.swing.JFrame {

    private AdmDatos adm; // Ya lo tienes si esta clase es similar a IMenu o ITutoria
    private TutorJpaController cTutor;
    private TutoradoJpaController cTutorado;
    
    public ILogin() {
        initComponents();
        setLocationRelativeTo(null);
        
        adm = new AdmDatos(); // O obtén la instancia si es un singleton
        cTutor = new TutorJpaController(adm.getEnf()); // O AdmDatos.getEnf()
        cTutorado = new TutoradoJpaController(adm.getEnf());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        cboTipoUsuario = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnDocumentacion = new javax.swing.JToggleButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Tutorias");
        setResizable(false);

        jLabel1.setText("Tipo de Usuario:");

        cboTipoUsuario.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Tutorado", "Tutor", "Coordinador" }));

        jLabel2.setText("Usuario:");

        jLabel3.setText("Contraseña:");

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/iniciar_sesion.png"))); // NOI18N
        btnAceptar.setText("Iniciar Sesión");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnDocumentacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/ayuda.png"))); // NOI18N
        btnDocumentacion.setText("Documentación");
        btnDocumentacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDocumentacionActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("Iniciar Sesión");

        jLabel5.setText("Sistema de Tutorias");

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario_muestra.png"))); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel6)
                                .addGap(48, 48, 48)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(cboTipoUsuario, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtUsuario)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(219, 219, 219)
                                .addComponent(jLabel5))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(26, 26, 26)
                                .addComponent(btnAceptar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(58, 58, 58)
                                .addComponent(btnDocumentacion)))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 534, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(11, 11, 11)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(cboTipoUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAceptar)
                            .addComponent(btnCancelar)
                            .addComponent(btnDocumentacion))
                        .addContainerGap(20, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        String usuarioIngresado = txtUsuario.getText();
        String contrasenaIngresada = new String(txtPassword.getPassword());
        String tipoUsuarioSeleccionado = cboTipoUsuario.getSelectedItem().toString();

        if (usuarioIngresado.isEmpty() || contrasenaIngresada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El usuario y la contraseña no pueden estar vacíos.", "Error de Entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean loginExitoso = false;
        String nombreUsuarioLogueado = ""; // Para mostrar un saludo o pasar a la siguiente ventana

        if (tipoUsuarioSeleccionado.equals("Coordinador")) {
            if (usuarioIngresado.equals("Admin") && contrasenaIngresada.equals("Admin")) { // Usuario Admin hardcodeado
                loginExitoso = true;
                nombreUsuarioLogueado = "Administrador";
                // Abrir IMenu
                IMenu ventanaMenu = new IMenu(); // Asumiendo que IMenu es tu ventana principal para el coordinador
                ventanaMenu.setLocationRelativeTo(null);
                ventanaMenu.setVisible(true);
                this.dispose(); // Cierra la ventana de login actual
                return; // Salir del método después de abrir la nueva ventana
            }
        } else if (tipoUsuarioSeleccionado.equals("Tutor")) {
            
            try {

                List<Tutor> todosLosTutores = cTutor.findTutorEntities(); // No es lo más eficiente para muchos tutores
                Tutor tutorEncontrado = null;
                for (Tutor t : todosLosTutores) {
                    // Cambia 't.getNumTarjeta().toString()' al campo que uses como nombre de usuario para Tutor
                    if (t.getNombre() != null && t.getNombre().equals(usuarioIngresado)) { 
                        tutorEncontrado = t;
                        System.out.println("No se encontro el tutor");
                        break;
                    }
                }
                
                if (tutorEncontrado != null) {
                    // Asume que Tutor tiene getContrasena()
                    if (tutorEncontrado.getNumTarjeta().toString() != null && tutorEncontrado.getNumTarjeta().toString().equals(contrasenaIngresada)) {
                        loginExitoso = true;
                        nombreUsuarioLogueado = tutorEncontrado.getNombre();
                        JOptionPane.showMessageDialog(this, "Bienvenido Tutor: " + nombreUsuarioLogueado, "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
                        
                        // Abrir IMenu
                        IMenu ventanaMenu = new IMenu(tutorEncontrado);
                        ventanaMenu.setLocationRelativeTo(null);ventanaMenu.setSize(1000, 400);
                        ventanaMenu.setVisible(true);
                        this.dispose(); // Cierra la ventana de login actual
                        return; // Salir del método después de abrir la nueva ventana
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "El usuario para Tutor debe ser un número de tarjeta válido.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al verificar Tutor: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }

        } else if (tipoUsuarioSeleccionado.equals("Tutorado")) {
            try {
                List<Tutorado> todosLosTutorados = cTutorado.findTutoradoEntities(); // No es lo más eficiente
                Tutorado tutoradoEncontrado = null;
                for (Tutorado tdo : todosLosTutorados) {
                    if (tdo.getNombre() != null && tdo.getNombre().equals(usuarioIngresado)) {
                        tutoradoEncontrado = tdo;
                        break;
                    }
                }

                if (tutoradoEncontrado != null) {
                    // Asume que Tutorado tiene getContrasena()
                    if (tutoradoEncontrado.getNc() != null && tutoradoEncontrado.getNc().equals(contrasenaIngresada)) {
                        loginExitoso = true;
                        nombreUsuarioLogueado = tutoradoEncontrado.getNombre();
                        // Aquí abrirías la interfaz específica para Tutorado
                        JOptionPane.showMessageDialog(this, "Bienvenido Tutorado: " + nombreUsuarioLogueado, "Login Exitoso", JOptionPane.INFORMATION_MESSAGE);
                        
                        IMenu ventanaMenu = new IMenu(tutoradoEncontrado);
                        ventanaMenu.setLocationRelativeTo(null);
                        ventanaMenu.setVisible(true);
                        this.dispose(); // Cierra la ventana de login actual
                        return; // Salir del método después de abrir la nueva ventana
                        
                    }
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al verificar Tutorado: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        if (loginExitoso) {
            System.out.println("Login exitoso para: " + nombreUsuarioLogueado);

        } else if (!tipoUsuarioSeleccionado.equals("Coordinador")) { // No mostrar si ya se manejó el admin y falló
            JOptionPane.showMessageDialog(this, "Usuario o Contraseña incorrectos para el tipo " + tipoUsuarioSeleccionado + ".", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        } else if (tipoUsuarioSeleccionado.equals("Coordinador") && !(usuarioIngresado.equals("Admin") && contrasenaIngresada.equals("Admin"))){
            // Este caso es para cuando se selecciona Coordinador pero el user/pass no es Admin/Admin
             JOptionPane.showMessageDialog(this, "Usuario o Contraseña de Coordinador incorrectos.", "Error de Autenticación", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void btnDocumentacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDocumentacionActionPerformed
        // TODO add your handling code here:
        try {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/jangelmm/ito-isf-u4-ejemplo-sistema.git"));
            } catch (IOException ex) {
                
            }
        } catch (URISyntaxException ex) {
            
        }
    }//GEN-LAST:event_btnDocumentacionActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        System.exit(0);
    }//GEN-LAST:event_btnCancelarActionPerformed

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
            java.util.logging.Logger.getLogger(ILogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ILogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ILogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ILogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ILogin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JToggleButton btnDocumentacion;
    private javax.swing.JComboBox<String> cboTipoUsuario;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
