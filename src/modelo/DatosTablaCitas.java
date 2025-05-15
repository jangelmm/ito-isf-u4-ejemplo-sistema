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
    //private JCheckBox asistencia; //boolean
   //    private JCheckBox asistencia;
    private String accion; //String
    //private JCheckBox asistencia; 
    private boolean asistencia; 
    //Voy a llenar un arreglo de datos tutoria, los cuales estarán formados de lo anterior
    
    public DatosTablaCitas(Tutorado tutorado){
        this.tutorado = tutorado;
        //this.asistencia = new JCheckBox();
        this.asistencia = false;
        //this.accion = new JTextField();
        this.accion = new String();
    }

    public Tutorado getTutorado() {
        return tutorado;
    }

    public void setTutorado(Tutorado tutorado) {
        this.tutorado = tutorado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }
    
    
}
