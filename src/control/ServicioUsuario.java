/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import modelo.Usuarios;

/**
 *
 * @author Diego Garcia
 */
public class ServicioUsuario {
    private final EntityManagerFactory emf;

    public ServicioUsuario() {
        this.emf = Conexion.getEMF();
    }

    public Usuarios validarUsuario(String username, String password) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Usuarios> query = em.createQuery(
                "SELECT u FROM Usuarios u WHERE (u.nombre = :username OR u.correo = :username) AND u.contrasenaHash = :password", 
                Usuarios.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            em.close();
        }
    }
}
