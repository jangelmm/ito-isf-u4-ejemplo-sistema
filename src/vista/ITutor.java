package vista;

import com.formdev.flatlaf.FlatDarkLaf;
import modelo.MTtutor;
import control.AdmDatos;
import control.TutorJpaController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Tutor;

public class ITutor extends javax.swing.JDialog {

//Creamos una conexión a la base de datos
    private Tutor tutor;
    private TutorJpaController cTutor;
    private AdmDatos adm;
    private List<Tutor> tutores;
    private MTtutor  mtt;
    private SpinnerNumberModel msn;
    private final int NMAX = 20;  //El numero de máximo de Numeros de Tarjetas


    public ITutor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        initComponents();
        
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        cTutor = new TutorJpaController(adm.getEnf());
        tutores = cTutor.findTutorEntities();
        mtt = new MTtutor(tutores);
        ttutores.setModel(mtt);
        
        // Cálculo de rango para el Spinner
        int ultimaTarjeta = tutores.get(tutores.size() - 1).getNumTarjeta();
        int min = ultimaTarjeta + 1;
        int max = NMAX;
        int paso = 1;

        msn = new SpinnerNumberModel();
        msn.setMinimum(min);
        msn.setMaximum(max);
        msn.setValue(min);

        JspiNumTarjeta.setModel(msn);
    }
    
    //Pasar datos a la nueva tabla
    
    //public void 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        ttutores = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        JspiNumTarjeta = new javax.swing.JSpinner();
        txtNombre = new javax.swing.JTextField();
        btnAgregarTutor = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtCarrera = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jCLunes = new javax.swing.JCheckBox();
        jCMartes = new javax.swing.JCheckBox();
        jCMiercoles = new javax.swing.JCheckBox();
        jCJueves = new javax.swing.JCheckBox();
        jCViernes = new javax.swing.JCheckBox();
        jCSabado = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jCh7 = new javax.swing.JCheckBox();
        jCh8 = new javax.swing.JCheckBox();
        jCh9 = new javax.swing.JCheckBox();
        jCh10 = new javax.swing.JCheckBox();
        jCh11 = new javax.swing.JCheckBox();
        jCh12 = new javax.swing.JCheckBox();
        jCh13 = new javax.swing.JCheckBox();
        jCh14 = new javax.swing.JCheckBox();
        jCh15 = new javax.swing.JCheckBox();
        jCh16 = new javax.swing.JCheckBox();
        jCh17 = new javax.swing.JCheckBox();
        jCh18 = new javax.swing.JCheckBox();
        jCh19 = new javax.swing.JCheckBox();
        btnCargarTutor = new javax.swing.JButton();
        btnActualizarTutor = new javax.swing.JButton();
        btnEliminarTutor = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        ttutores.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(ttutores);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Tutores");

        jLabel2.setText("No. de Tarjeta:");

        jLabel3.setText("Nombre:");

        btnAgregarTutor.setText("Agregar Tutor");
        btnAgregarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTutorActionPerformed(evt);
            }
        });

        jLabel4.setText("Carrera:");

        jLabel5.setText("Dias:");

        jCLunes.setText("Lunes");

        jCMartes.setText("Martes");

        jCMiercoles.setText("Miercoles");

        jCJueves.setText("Jueves");

        jCViernes.setText("Viernes");

        jCSabado.setText("Sabado");

        jLabel6.setText("Horas:");

        jCh7.setText("7:00-8:00");

        jCh8.setText("8:00-9:00");

        jCh9.setText("9:00-10:00");

        jCh10.setText("10:00-11:00");

        jCh11.setText("11:00-12:00");

        jCh12.setText("12:00-13:00");

        jCh13.setText("13:00-14:00");

        jCh14.setText("14:00-15:00");

        jCh15.setText("15:00-16:00");

        jCh16.setText("16:00-17:00");

        jCh17.setText("17:00-18:00");

        jCh18.setText("18:00-19:00");

        jCh19.setText("19:00-20:00");

        btnCargarTutor.setText("Cargar Tutor");

        btnActualizarTutor.setText("Actualizar Tutor");

        btnEliminarTutor.setText("Eliminar Tutor");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(77, 77, 77)
                        .addComponent(JspiNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jCLunes)
                            .addComponent(jCMartes)
                            .addComponent(jCMiercoles)
                            .addComponent(jCJueves)
                            .addComponent(jCViernes)
                            .addComponent(jCSabado)
                            .addComponent(btnCargarTutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregarTutor, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCh11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCh18))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCh10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCh17))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCh9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jCh16))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(jCh7, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING))))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jCh8)
                                    .addGap(42, 42, 42)
                                    .addComponent(jCh15))
                                .addComponent(jCh14, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jCh12)
                                .addGap(30, 30, 30)
                                .addComponent(jCh19))
                            .addComponent(jCh13)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(40, 40, 40)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(btnActualizarTutor, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                    .addComponent(btnEliminarTutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 42, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 515, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(26, 26, 26))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(JspiNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(txtCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCLunes)
                            .addComponent(jCh7)
                            .addComponent(jCh14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCMartes)
                            .addComponent(jCh8)
                            .addComponent(jCh15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jCMiercoles)
                            .addComponent(jCh9)
                            .addComponent(jCh16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCh10)
                                .addComponent(jCh17))
                            .addComponent(jCJueves))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCViernes)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCh11)
                                .addComponent(jCh18)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCSabado)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jCh12)
                                .addComponent(jCh19)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jCh13)
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregarTutor)
                            .addComponent(btnActualizarTutor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnCargarTutor)
                            .addComponent(btnEliminarTutor))
                        .addGap(0, 12, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTutorActionPerformed
        tutor = new Tutor();
        tutor.setNumTarjeta((Integer) JspiNumTarjeta.getValue());
        tutor.setNombre(txtNombre.getText());
        tutor.setCarrera(txtCarrera.getText());
        tutor.setDias(generarCadenaDias());
        tutor.setHoras(generarIntervalosHorarios());
        
        cTutor.create(tutor);
        tutores.add(tutor);
        mtt.fireTableDataChanged();
    }//GEN-LAST:event_btnAgregarTutorActionPerformed

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
    
    public String generarIntervalosHorarios() {
        List<Integer> horasSeleccionadas = new ArrayList<>();

        // Recoger todas las horas seleccionadas
        if (jCh7.isSelected()) horasSeleccionadas.add(7);
        if (jCh8.isSelected()) horasSeleccionadas.add(8);
        if (jCh9.isSelected()) horasSeleccionadas.add(9);
        if (jCh10.isSelected()) horasSeleccionadas.add(10);
        if (jCh11.isSelected()) horasSeleccionadas.add(11);
        if (jCh12.isSelected()) horasSeleccionadas.add(12);
        if (jCh13.isSelected()) horasSeleccionadas.add(13);
        if (jCh14.isSelected()) horasSeleccionadas.add(14);
        if (jCh15.isSelected()) horasSeleccionadas.add(15);
        if (jCh16.isSelected()) horasSeleccionadas.add(16);
        if (jCh17.isSelected()) horasSeleccionadas.add(17);
        if (jCh18.isSelected()) horasSeleccionadas.add(18);
        if (jCh19.isSelected()) horasSeleccionadas.add(19);

        // Ordenar las horas
        Collections.sort(horasSeleccionadas);

        // Generar intervalos continuos
        StringBuilder resultado = new StringBuilder();
        if (!horasSeleccionadas.isEmpty()) {
            int inicio = horasSeleccionadas.get(0);
            int fin = inicio;

            for (int i = 1; i < horasSeleccionadas.size(); i++) {
                if (horasSeleccionadas.get(i) == fin + 1) {
                    fin = horasSeleccionadas.get(i);
                } else {
                    // Agregar el intervalo actual
                    if (resultado.length() > 0) resultado.append(",");
                    resultado.append(inicio).append("-").append(fin);

                    // Comenzar nuevo intervalo
                    inicio = horasSeleccionadas.get(i);
                    fin = inicio;
                }
            }

            // Agregar el último intervalo
            if (resultado.length() > 0) resultado.append(",");
            resultado.append(inicio).append("-").append(fin);
        }

        // Limitar a 10 caracteres si es necesario
        return resultado.length() <= 10 ? resultado.toString() : resultado.substring(0, 10);
    }
    
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
            java.util.logging.Logger.getLogger(ITutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ITutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ITutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ITutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        UIManager.setLookAndFeel(new FlatDarkLaf());

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ITutor dialog = new ITutor(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner JspiNumTarjeta;
    private javax.swing.JButton btnActualizarTutor;
    private javax.swing.JButton btnAgregarTutor;
    private javax.swing.JButton btnCargarTutor;
    private javax.swing.JButton btnEliminarTutor;
    private javax.swing.JCheckBox jCJueves;
    private javax.swing.JCheckBox jCLunes;
    private javax.swing.JCheckBox jCMartes;
    private javax.swing.JCheckBox jCMiercoles;
    private javax.swing.JCheckBox jCSabado;
    private javax.swing.JCheckBox jCViernes;
    private javax.swing.JCheckBox jCh10;
    private javax.swing.JCheckBox jCh11;
    private javax.swing.JCheckBox jCh12;
    private javax.swing.JCheckBox jCh13;
    private javax.swing.JCheckBox jCh14;
    private javax.swing.JCheckBox jCh15;
    private javax.swing.JCheckBox jCh16;
    private javax.swing.JCheckBox jCh17;
    private javax.swing.JCheckBox jCh18;
    private javax.swing.JCheckBox jCh19;
    private javax.swing.JCheckBox jCh7;
    private javax.swing.JCheckBox jCh8;
    private javax.swing.JCheckBox jCh9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable ttutores;
    private javax.swing.JTextField txtCarrera;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
