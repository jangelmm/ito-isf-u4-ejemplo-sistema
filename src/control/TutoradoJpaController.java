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
import modelo.Tutor;
import modelo.Tutorado;

/**
 *
 * @author jesus
 */
public class TutoradoJpaController implements Serializable {

    public TutoradoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tutorado tutorado) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutor tutor = tutorado.getTutor();
            if (tutor != null) {
                tutor = em.getReference(tutor.getClass(), tutor.getIdPersona());
                tutorado.setTutor(tutor);
            }
            em.persist(tutorado);
            if (tutor != null) {
                tutor.getTutoradoList().add(tutorado);
                tutor = em.merge(tutor);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tutorado tutorado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutorado persistentTutorado = em.find(Tutorado.class, tutorado.getIdTutorado());
            Tutor tutorOld = persistentTutorado.getTutor();
            Tutor tutorNew = tutorado.getTutor();
            if (tutorNew != null) {
                tutorNew = em.getReference(tutorNew.getClass(), tutorNew.getIdPersona());
                tutorado.setTutor(tutorNew);
            }
            tutorado = em.merge(tutorado);
            if (tutorOld != null && !tutorOld.equals(tutorNew)) {
                tutorOld.getTutoradoList().remove(tutorado);
                tutorOld = em.merge(tutorOld);
            }
            if (tutorNew != null && !tutorNew.equals(tutorOld)) {
                tutorNew.getTutoradoList().add(tutorado);
                tutorNew = em.merge(tutorNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tutorado.getIdTutorado();
                if (findTutorado(id) == null) {
                    throw new NonexistentEntityException("The tutorado with id " + id + " no longer exists.");
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
            Tutorado tutorado;
            try {
                tutorado = em.getReference(Tutorado.class, id);
                tutorado.getIdTutorado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tutorado with id " + id + " no longer exists.", enfe);
            }
            Tutor tutor = tutorado.getTutor();
            if (tutor != null) {
                tutor.getTutoradoList().remove(tutorado);
                tutor = em.merge(tutor);
            }
            em.remove(tutorado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tutorado> findTutoradoEntities() {
        return findTutoradoEntities(true, -1, -1);
    }

    public List<Tutorado> findTutoradoEntities(int maxResults, int firstResult) {
        return findTutoradoEntities(false, maxResults, firstResult);
    }

    private List<Tutorado> findTutoradoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tutorado.class));
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

    public Tutorado findTutorado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tutorado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTutoradoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tutorado> rt = cq.from(Tutorado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
