/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import control.exceptions.IllegalOrphanException;
import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Tutor;
import modelo.Tutoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
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
        if (tutorado.getTutoriaList() == null) {
            tutorado.setTutoriaList(new ArrayList<Tutoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutor tutor = tutorado.getTutor();
            if (tutor != null) {
                tutor = em.getReference(tutor.getClass(), tutor.getIdPersona());
                tutorado.setTutor(tutor);
            }
            List<Tutoria> attachedTutoriaList = new ArrayList<Tutoria>();
            for (Tutoria tutoriaListTutoriaToAttach : tutorado.getTutoriaList()) {
                tutoriaListTutoriaToAttach = em.getReference(tutoriaListTutoriaToAttach.getClass(), tutoriaListTutoriaToAttach.getIdTutoria());
                attachedTutoriaList.add(tutoriaListTutoriaToAttach);
            }
            tutorado.setTutoriaList(attachedTutoriaList);
            em.persist(tutorado);
            if (tutor != null) {
                tutor.getTutoradoList().add(tutorado);
                tutor = em.merge(tutor);
            }
            for (Tutoria tutoriaListTutoria : tutorado.getTutoriaList()) {
                Tutorado oldIdTutoradoOfTutoriaListTutoria = tutoriaListTutoria.getIdTutorado();
                tutoriaListTutoria.setIdTutorado(tutorado);
                tutoriaListTutoria = em.merge(tutoriaListTutoria);
                if (oldIdTutoradoOfTutoriaListTutoria != null) {
                    oldIdTutoradoOfTutoriaListTutoria.getTutoriaList().remove(tutoriaListTutoria);
                    oldIdTutoradoOfTutoriaListTutoria = em.merge(oldIdTutoradoOfTutoriaListTutoria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tutorado tutorado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutorado persistentTutorado = em.find(Tutorado.class, tutorado.getIdTutorado());
            Tutor tutorOld = persistentTutorado.getTutor();
            Tutor tutorNew = tutorado.getTutor();
            List<Tutoria> tutoriaListOld = persistentTutorado.getTutoriaList();
            List<Tutoria> tutoriaListNew = tutorado.getTutoriaList();
            List<String> illegalOrphanMessages = null;
            for (Tutoria tutoriaListOldTutoria : tutoriaListOld) {
                if (!tutoriaListNew.contains(tutoriaListOldTutoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tutoria " + tutoriaListOldTutoria + " since its idTutorado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tutorNew != null) {
                tutorNew = em.getReference(tutorNew.getClass(), tutorNew.getIdPersona());
                tutorado.setTutor(tutorNew);
            }
            List<Tutoria> attachedTutoriaListNew = new ArrayList<Tutoria>();
            for (Tutoria tutoriaListNewTutoriaToAttach : tutoriaListNew) {
                tutoriaListNewTutoriaToAttach = em.getReference(tutoriaListNewTutoriaToAttach.getClass(), tutoriaListNewTutoriaToAttach.getIdTutoria());
                attachedTutoriaListNew.add(tutoriaListNewTutoriaToAttach);
            }
            tutoriaListNew = attachedTutoriaListNew;
            tutorado.setTutoriaList(tutoriaListNew);
            tutorado = em.merge(tutorado);
            if (tutorOld != null && !tutorOld.equals(tutorNew)) {
                tutorOld.getTutoradoList().remove(tutorado);
                tutorOld = em.merge(tutorOld);
            }
            if (tutorNew != null && !tutorNew.equals(tutorOld)) {
                tutorNew.getTutoradoList().add(tutorado);
                tutorNew = em.merge(tutorNew);
            }
            for (Tutoria tutoriaListNewTutoria : tutoriaListNew) {
                if (!tutoriaListOld.contains(tutoriaListNewTutoria)) {
                    Tutorado oldIdTutoradoOfTutoriaListNewTutoria = tutoriaListNewTutoria.getIdTutorado();
                    tutoriaListNewTutoria.setIdTutorado(tutorado);
                    tutoriaListNewTutoria = em.merge(tutoriaListNewTutoria);
                    if (oldIdTutoradoOfTutoriaListNewTutoria != null && !oldIdTutoradoOfTutoriaListNewTutoria.equals(tutorado)) {
                        oldIdTutoradoOfTutoriaListNewTutoria.getTutoriaList().remove(tutoriaListNewTutoria);
                        oldIdTutoradoOfTutoriaListNewTutoria = em.merge(oldIdTutoradoOfTutoriaListNewTutoria);
                    }
                }
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
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
            List<String> illegalOrphanMessages = null;
            List<Tutoria> tutoriaListOrphanCheck = tutorado.getTutoriaList();
            for (Tutoria tutoriaListOrphanCheckTutoria : tutoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tutorado (" + tutorado + ") cannot be destroyed since the Tutoria " + tutoriaListOrphanCheckTutoria + " in its tutoriaList field has a non-nullable idTutorado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
