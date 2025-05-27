/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Diego Garcia
 */
public class Conexion {
    private static final EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("AcademicPlusPU");

    public static EntityManagerFactory getEMF() {
        return emf;
    }
}
