/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Cita;
import modelo.Tutorado;
import modelo.Tutoria;

/**
 *
 * @author jesus
 */
public class TutoriaJpaController implements Serializable {

    public TutoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tutoria tutoria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cita idCita = tutoria.getIdCita();
            if (idCita != null) {
                idCita = em.getReference(idCita.getClass(), idCita.getIdCita());
                tutoria.setIdCita(idCita);
            }
            Tutorado idTutorado = tutoria.getIdTutorado();
            if (idTutorado != null) {
                idTutorado = em.getReference(idTutorado.getClass(), idTutorado.getIdTutorado());
                tutoria.setIdTutorado(idTutorado);
            }
            em.persist(tutoria);
            if (idCita != null) {
                idCita.getTutoriaList().add(tutoria);
                idCita = em.merge(idCita);
            }
            if (idTutorado != null) {
                idTutorado.getTutoriaList().add(tutoria);
                idTutorado = em.merge(idTutorado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tutoria tutoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutoria persistentTutoria = em.find(Tutoria.class, tutoria.getIdTutoria());
            Cita idCitaOld = persistentTutoria.getIdCita();
            Cita idCitaNew = tutoria.getIdCita();
            Tutorado idTutoradoOld = persistentTutoria.getIdTutorado();
            Tutorado idTutoradoNew = tutoria.getIdTutorado();
            if (idCitaNew != null) {
                idCitaNew = em.getReference(idCitaNew.getClass(), idCitaNew.getIdCita());
                tutoria.setIdCita(idCitaNew);
            }
            if (idTutoradoNew != null) {
                idTutoradoNew = em.getReference(idTutoradoNew.getClass(), idTutoradoNew.getIdTutorado());
                tutoria.setIdTutorado(idTutoradoNew);
            }
            tutoria = em.merge(tutoria);
            if (idCitaOld != null && !idCitaOld.equals(idCitaNew)) {
                idCitaOld.getTutoriaList().remove(tutoria);
                idCitaOld = em.merge(idCitaOld);
            }
            if (idCitaNew != null && !idCitaNew.equals(idCitaOld)) {
                idCitaNew.getTutoriaList().add(tutoria);
                idCitaNew = em.merge(idCitaNew);
            }
            if (idTutoradoOld != null && !idTutoradoOld.equals(idTutoradoNew)) {
                idTutoradoOld.getTutoriaList().remove(tutoria);
                idTutoradoOld = em.merge(idTutoradoOld);
            }
            if (idTutoradoNew != null && !idTutoradoNew.equals(idTutoradoOld)) {
                idTutoradoNew.getTutoriaList().add(tutoria);
                idTutoradoNew = em.merge(idTutoradoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tutoria.getIdTutoria();
                if (findTutoria(id) == null) {
                    throw new NonexistentEntityException("The tutoria with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutoria tutoria;
            try {
                tutoria = em.getReference(Tutoria.class, id);
                tutoria.getIdTutoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tutoria with id " + id + " no longer exists.", enfe);
            }
            Cita idCita = tutoria.getIdCita();
            if (idCita != null) {
                idCita.getTutoriaList().remove(tutoria);
                idCita = em.merge(idCita);
            }
            Tutorado idTutorado = tutoria.getIdTutorado();
            if (idTutorado != null) {
                idTutorado.getTutoriaList().remove(tutoria);
                idTutorado = em.merge(idTutorado);
            }
            em.remove(tutoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tutoria> findTutoriaEntities() {
        return findTutoriaEntities(true, -1, -1);
    }

    public List<Tutoria> findTutoriaEntities(int maxResults, int firstResult) {
        return findTutoriaEntities(false, maxResults, firstResult);
    }

    private List<Tutoria> findTutoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tutoria.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Tutoria findTutoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tutoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getTutoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tutoria> rt = cq.from(Tutoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
