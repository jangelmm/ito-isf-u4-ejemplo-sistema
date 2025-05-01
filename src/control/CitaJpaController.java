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
import modelo.Cita;

/**
 *
 * @author jesus
 */
public class CitaJpaController implements Serializable {

    public CitaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cita cita) {
        if (cita.getTutoriaList() == null) {
            cita.setTutoriaList(new ArrayList<Tutoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutor tutor = cita.getTutor();
            if (tutor != null) {
                tutor = em.getReference(tutor.getClass(), tutor.getIdPersona());
                cita.setTutor(tutor);
            }
            List<Tutoria> attachedTutoriaList = new ArrayList<Tutoria>();
            for (Tutoria tutoriaListTutoriaToAttach : cita.getTutoriaList()) {
                tutoriaListTutoriaToAttach = em.getReference(tutoriaListTutoriaToAttach.getClass(), tutoriaListTutoriaToAttach.getIdTutoria());
                attachedTutoriaList.add(tutoriaListTutoriaToAttach);
            }
            cita.setTutoriaList(attachedTutoriaList);
            em.persist(cita);
            if (tutor != null) {
                tutor.getCitaList().add(cita);
                tutor = em.merge(tutor);
            }
            for (Tutoria tutoriaListTutoria : cita.getTutoriaList()) {
                Cita oldIdCitaOfTutoriaListTutoria = tutoriaListTutoria.getIdCita();
                tutoriaListTutoria.setIdCita(cita);
                tutoriaListTutoria = em.merge(tutoriaListTutoria);
                if (oldIdCitaOfTutoriaListTutoria != null) {
                    oldIdCitaOfTutoriaListTutoria.getTutoriaList().remove(tutoriaListTutoria);
                    oldIdCitaOfTutoriaListTutoria = em.merge(oldIdCitaOfTutoriaListTutoria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cita cita) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cita persistentCita = em.find(Cita.class, cita.getIdCita());
            Tutor tutorOld = persistentCita.getTutor();
            Tutor tutorNew = cita.getTutor();
            List<Tutoria> tutoriaListOld = persistentCita.getTutoriaList();
            List<Tutoria> tutoriaListNew = cita.getTutoriaList();
            List<String> illegalOrphanMessages = null;
            for (Tutoria tutoriaListOldTutoria : tutoriaListOld) {
                if (!tutoriaListNew.contains(tutoriaListOldTutoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tutoria " + tutoriaListOldTutoria + " since its idCita field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tutorNew != null) {
                tutorNew = em.getReference(tutorNew.getClass(), tutorNew.getIdPersona());
                cita.setTutor(tutorNew);
            }
            List<Tutoria> attachedTutoriaListNew = new ArrayList<Tutoria>();
            for (Tutoria tutoriaListNewTutoriaToAttach : tutoriaListNew) {
                tutoriaListNewTutoriaToAttach = em.getReference(tutoriaListNewTutoriaToAttach.getClass(), tutoriaListNewTutoriaToAttach.getIdTutoria());
                attachedTutoriaListNew.add(tutoriaListNewTutoriaToAttach);
            }
            tutoriaListNew = attachedTutoriaListNew;
            cita.setTutoriaList(tutoriaListNew);
            cita = em.merge(cita);
            if (tutorOld != null && !tutorOld.equals(tutorNew)) {
                tutorOld.getCitaList().remove(cita);
                tutorOld = em.merge(tutorOld);
            }
            if (tutorNew != null && !tutorNew.equals(tutorOld)) {
                tutorNew.getCitaList().add(cita);
                tutorNew = em.merge(tutorNew);
            }
            for (Tutoria tutoriaListNewTutoria : tutoriaListNew) {
                if (!tutoriaListOld.contains(tutoriaListNewTutoria)) {
                    Cita oldIdCitaOfTutoriaListNewTutoria = tutoriaListNewTutoria.getIdCita();
                    tutoriaListNewTutoria.setIdCita(cita);
                    tutoriaListNewTutoria = em.merge(tutoriaListNewTutoria);
                    if (oldIdCitaOfTutoriaListNewTutoria != null && !oldIdCitaOfTutoriaListNewTutoria.equals(cita)) {
                        oldIdCitaOfTutoriaListNewTutoria.getTutoriaList().remove(tutoriaListNewTutoria);
                        oldIdCitaOfTutoriaListNewTutoria = em.merge(oldIdCitaOfTutoriaListNewTutoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cita.getIdCita();
                if (findCita(id) == null) {
                    throw new NonexistentEntityException("The cita with id " + id + " no longer exists.");
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
            Cita cita;
            try {
                cita = em.getReference(Cita.class, id);
                cita.getIdCita();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cita with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Tutoria> tutoriaListOrphanCheck = cita.getTutoriaList();
            for (Tutoria tutoriaListOrphanCheckTutoria : tutoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cita (" + cita + ") cannot be destroyed since the Tutoria " + tutoriaListOrphanCheckTutoria + " in its tutoriaList field has a non-nullable idCita field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tutor tutor = cita.getTutor();
            if (tutor != null) {
                tutor.getCitaList().remove(cita);
                tutor = em.merge(tutor);
            }
            em.remove(cita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cita> findCitaEntities() {
        return findCitaEntities(true, -1, -1);
    }

    public List<Cita> findCitaEntities(int maxResults, int firstResult) {
        return findCitaEntities(false, maxResults, firstResult);
    }

    private List<Cita> findCitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cita.class));
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

    public Cita findCita(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cita.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cita> rt = cq.from(Cita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
