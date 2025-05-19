package vista;

import com.formdev.flatlaf.FlatLightLaf;
import modelo.MTtutor;
import control.AdmDatos;
import control.TutorJpaController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Tutor;
import java.lang.Exception;

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

        jCh15 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        ttutores = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        JspiNumTarjeta = new javax.swing.JSpinner();
        txtNombre = new javax.swing.JTextField();
        btnAgregarTutor = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
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
        jCh16 = new javax.swing.JCheckBox();
        jCh17 = new javax.swing.JCheckBox();
        jCh18 = new javax.swing.JCheckBox();
        jCh19 = new javax.swing.JCheckBox();
        btnLimpiarDatos = new javax.swing.JButton();
        btnActualizarTutor = new javax.swing.JButton();
        btnEliminarTutor = new javax.swing.JButton();
        cboCarrera = new javax.swing.JComboBox<>();

        jCh15.setText("15:00-16:00");

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
        ttutores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ttutores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ttutoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ttutores);
        ttutores.getAccessibleContext().setAccessibleName("");
        ttutores.getAccessibleContext().setAccessibleDescription("");

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

        jCh16.setText("16:00-17:00");

        jCh17.setText("17:00-18:00");

        jCh18.setText("18:00-19:00");

        jCh19.setText("19:00-20:00");

        btnLimpiarDatos.setText("Limpiar Datos");
        btnLimpiarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarDatosActionPerformed(evt);
            }
        });

        btnActualizarTutor.setText("Actualizar Tutor");
        btnActualizarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTutorActionPerformed(evt);
            }
        });

        btnEliminarTutor.setText("Eliminar Tutor");
        btnEliminarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTutorActionPerformed(evt);
            }
        });

        cboCarrera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ingeniería Electrónica", "Ingeniería Civil", "Ingeniería Mecánica", "Ingeniería Industrial", "Ingeniería Química", "Ingeniería Electrica", "Ingeniería en Gestión Empresarial", "Ingeniería en Sis. Computacionales", "Licenciatura en Administración", "Contador Público" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLimpiarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregarTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnActualizarTutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(627, 627, 627))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jCLunes)
                                    .addComponent(jCMartes)
                                    .addComponent(jCMiercoles)
                                    .addComponent(jCJueves)
                                    .addComponent(jCViernes)
                                    .addComponent(jCSabado))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cboCarrera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(JspiNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(174, 174, 174))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jCh12)
                                                    .addComponent(jCh8)
                                                    .addComponent(jCh11)
                                                    .addComponent(jCh10)
                                                    .addComponent(jCh9)
                                                    .addComponent(jCh7)
                                                    .addComponent(jCh13))
                                                .addGap(26, 26, 26)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jCh19)
                                                    .addComponent(jCh14)
                                                    .addComponent(jCh18, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jCh17, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jCh16, javax.swing.GroupLayout.Alignment.LEADING))))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(256, 256, 256)
                        .addComponent(jScrollPane1)))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel3)
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
                                .addComponent(jCSabado))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(JspiNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCh7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh12))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCh14)
                                        .addGap(32, 32, 32)
                                        .addComponent(jCh16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh19)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCh13)))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregarTutor)
                            .addComponent(btnActualizarTutor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLimpiarDatos)
                            .addComponent(btnEliminarTutor))))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTutorActionPerformed
        tutor = new Tutor();
        tutor.setNumTarjeta((Integer) JspiNumTarjeta.getValue());
        tutor.setNombre(txtNombre.getText());
        tutor.setCarrera(cboCarrera.getSelectedItem().toString());
        tutor.setDias(generarCadenaDias());
        tutor.setHoras(generarIntervalosHorarios());
        
        cTutor.create(tutor);
        tutores.add(tutor);
        mtt.fireTableDataChanged();
    }//GEN-LAST:event_btnAgregarTutorActionPerformed

    private void btnLimpiarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarDatosActionPerformed
        limpiarDatos();
    }//GEN-LAST:event_btnLimpiarDatosActionPerformed

    private void ttutoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ttutoresMouseClicked
        int fila = ttutores.getSelectedRow();
        
        //Agregar los Valores
        JspiNumTarjeta.setValue(ttutores.getValueAt(fila, 0)); 
        txtNombre.setText(ttutores.getValueAt(fila, 1).toString());
        cboCarrera.setSelectedItem(ttutores.getValueAt(fila, 2).toString());
        marcarDiasDesdeCadena(ttutores.getValueAt(fila, 3).toString());
        marcarHorasDesdeCadena(ttutores.getValueAt(fila, 4).toString());
        //tutoradorDetutor(ttutores.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_ttutoresMouseClicked

    private void btnActualizarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTutorActionPerformed
        int fila = ttutores.getSelectedRow();
        
        if(fila == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un tutor de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            Tutor tutor = tutores.get(fila);
            
            tutor.setNumTarjeta((Integer) JspiNumTarjeta.getValue());
            tutor.setNombre(txtNombre.getText().trim());
            tutor.setCarrera(cboCarrera.getSelectedItem().toString());
            tutor.setDias(generarCadenaDias());
            tutor.setHoras(generarIntervalosHorarios());
            //tutor.
            
            cTutor.edit(tutor);
            
            mtt.fireTableCellUpdated(fila, fila);
            
            JOptionPane.showMessageDialog(this, "Tutor actualizado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarDatos();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Error al actualizar tutor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnActualizarTutorActionPerformed

    private void btnEliminarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTutorActionPerformed
        int fila = ttutores.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un tutor de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este tutor?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Tutor tutor = tutores.get(fila);

            // Usar el ID correcto (id_persona)
            cTutor.destroy(tutor.getIdPersona());  // Cambio clave aquí

            // Actualizar la lista desde la base de datos
            tutores = cTutor.findTutorEntities();
            mtt = new MTtutor(tutores);
            ttutores.setModel(mtt);

            limpiarDatos();

            JOptionPane.showMessageDialog(this,
                    "Tutor eliminado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar tutor: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnEliminarTutorActionPerformed

    public void tutoradorDetutor(List<Tutor> tutores) {
        ;
    }
    
    public void marcarHorasDesdeCadena(String intervalo) {
        // Limpiar primero todas las horas
        jCh7.setSelected(false);
        jCh8.setSelected(false);
        jCh9.setSelected(false);
        jCh10.setSelected(false);
        jCh11.setSelected(false);
        jCh12.setSelected(false);
        jCh13.setSelected(false);
        jCh14.setSelected(false);
        jCh15.setSelected(false);
        jCh16.setSelected(false);
        jCh17.setSelected(false);
        jCh18.setSelected(false);
        jCh19.setSelected(false);

        if (intervalo == null || intervalo.isEmpty()) {
            return;
        }

        try {
            String[] partes = intervalo.split("-");
            int horaInicio = Integer.parseInt(partes[0].trim());
            int horaFin = Integer.parseInt(partes[1].trim());

            // Validar rango correcto (7-19 para checkboxes existentes)
            if (horaInicio > horaFin || horaInicio < 7 || horaFin > 20) {
                return;
            }

            // Marcar cada hora en el intervalo
            for (int hora = horaInicio; hora < horaFin; hora++) {
                switch (hora) {
                    case 7: jCh7.setSelected(true); break;
                    case 8: jCh8.setSelected(true); break;
                    case 9: jCh9.setSelected(true); break;
                    case 10: jCh10.setSelected(true); break;
                    case 11: jCh11.setSelected(true); break;
                    case 12: jCh12.setSelected(true); break;
                    case 13: jCh13.setSelected(true); break;
                    case 14: jCh14.setSelected(true); break;
                    case 15: jCh15.setSelected(true); break;
                    case 16: jCh16.setSelected(true); break;
                    case 17: jCh17.setSelected(true); break;
                    case 18: jCh18.setSelected(true); break;
                    case 19: jCh19.setSelected(true); break;
                }
            }

        } catch (Exception e) {
            System.err.println("Formato de intervalo inválido: " + intervalo);
        }
    }
    public void marcarDiasDesdeCadena(String cadenaDias) {
        // Limpiar primero todas las selecciones
        jCLunes.setSelected(false);
        jCMartes.setSelected(false);
        jCMiercoles.setSelected(false);
        jCJueves.setSelected(false);
        jCViernes.setSelected(false);
        jCSabado.setSelected(false);

        if (cadenaDias == null || cadenaDias.isEmpty()) {
            return;
        }

        // Separar los días usando el guión como delimitador
        String[] dias = cadenaDias.split("-");

        for (String dia : dias) {
            switch (dia.trim().toUpperCase()) {
                case "L":
                    jCLunes.setSelected(true);
                    break;
                case "M":
                    jCMartes.setSelected(true);
                    break;
                case "X":
                    jCMiercoles.setSelected(true);
                    break;
                case "J":
                    jCJueves.setSelected(true);
                    break;
                case "V":
                    jCViernes.setSelected(true);
                    break;
                case "S":
                    jCSabado.setSelected(true);
                    break;
            }
        }
    }
    public SpinnerNumberModel modeloSpinner(){
        // Cálculo de rango para el Spinner
        int ultimaTarjeta = tutores.get(tutores.size() - 1).getNumTarjeta();
        int min = ultimaTarjeta + 1;
        int max = NMAX;
        int paso = 1;

        msn = new SpinnerNumberModel();
        msn.setMinimum(min);
        msn.setMaximum(max);
        msn.setValue(min);
        
        return msn;
    }
    public void limpiarDatos(){
        cboCarrera.setSelectedIndex(0);
        txtNombre.setText(null);
        
        JspiNumTarjeta.setModel(modeloSpinner());
        
        // Limpiar días de la semana
        jCLunes.setSelected(false);
        jCMartes.setSelected(false);
        jCMiercoles.setSelected(false);
        jCJueves.setSelected(false);
        jCViernes.setSelected(false);
        jCSabado.setSelected(false);

        // Limpiar horas
        jCh7.setSelected(false);
        jCh8.setSelected(false);
        jCh9.setSelected(false);
        jCh10.setSelected(false);
        jCh11.setSelected(false);
        jCh12.setSelected(false);
        jCh13.setSelected(false);
        jCh14.setSelected(false);
        jCh15.setSelected(false);
        jCh16.setSelected(false);
        jCh17.setSelected(false);
        jCh18.setSelected(false);
        jCh19.setSelected(false);     
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

        if (horasSeleccionadas.isEmpty()) {
            return "";
        }

        // Calcular mínimo y máximo
        int min = Collections.min(horasSeleccionadas);
        int max = Collections.max(horasSeleccionadas);

        // Crear intervalo único (hora final = max + 1)
        String intervalo = min + "-" + (max + 1);

        return intervalo.length() <= 10 ? intervalo : intervalo.substring(0, 10);
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
        
        UIManager.setLookAndFeel(new FlatLightLaf());

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
    private javax.swing.JButton btnEliminarTutor;
    private javax.swing.JButton btnLimpiarDatos;
    private javax.swing.JComboBox<String> cboCarrera;
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
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
