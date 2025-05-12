/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 *
 * @author jesus
 */
public class DatosTablaCitas {
    private Tutorado tutorado;
    private JCheckBox asistencia;
    private JTextField accion;
    
    //Voy a llenar un arreglo de datos tutoria, los cuales estarán formados de lo anterior
    
    public DatosTablaCitas(Tutorado tutorado){
        this.tutorado = tutorado;
        this.asistencia = new JCheckBox();
        this.accion = new JTextField();
    }

    public Tutorado getTutorado() {
        return tutorado;
    }

    public void setTutorado(Tutorado tutorado) {
        this.tutorado = tutorado;
    }

    public JCheckBox getAsistencia() {
        return asistencia;
    }

    public void setAsistencia(JCheckBox asistencia) {
        this.asistencia = asistencia;
    }

    public JTextField getAccion() {
        return accion;
    }

    public void setAccion(JTextField accion) {
        this.accion = accion;
    }
    
    
}
