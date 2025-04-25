/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package control;

import control.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import modelo.Tutorado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Cita;
import modelo.Tutor;

/**
 *
 * @author jesus
 */
public class TutorJpaController implements Serializable {

    public TutorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tutor tutor) {
        if (tutor.getTutoradoList() == null) {
            tutor.setTutoradoList(new ArrayList<Tutorado>());
        }
        if (tutor.getCitaList() == null) {
            tutor.setCitaList(new ArrayList<Cita>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tutorado> attachedTutoradoList = new ArrayList<Tutorado>();
            for (Tutorado tutoradoListTutoradoToAttach : tutor.getTutoradoList()) {
                tutoradoListTutoradoToAttach = em.getReference(tutoradoListTutoradoToAttach.getClass(), tutoradoListTutoradoToAttach.getIdTutorado());
                attachedTutoradoList.add(tutoradoListTutoradoToAttach);
            }
            tutor.setTutoradoList(attachedTutoradoList);
            List<Cita> attachedCitaList = new ArrayList<Cita>();
            for (Cita citaListCitaToAttach : tutor.getCitaList()) {
                citaListCitaToAttach = em.getReference(citaListCitaToAttach.getClass(), citaListCitaToAttach.getIdCita());
                attachedCitaList.add(citaListCitaToAttach);
            }
            tutor.setCitaList(attachedCitaList);
            em.persist(tutor);
            for (Tutorado tutoradoListTutorado : tutor.getTutoradoList()) {
                Tutor oldTutorOfTutoradoListTutorado = tutoradoListTutorado.getTutor();
                tutoradoListTutorado.setTutor(tutor);
                tutoradoListTutorado = em.merge(tutoradoListTutorado);
                if (oldTutorOfTutoradoListTutorado != null) {
                    oldTutorOfTutoradoListTutorado.getTutoradoList().remove(tutoradoListTutorado);
                    oldTutorOfTutoradoListTutorado = em.merge(oldTutorOfTutoradoListTutorado);
                }
            }
            for (Cita citaListCita : tutor.getCitaList()) {
                Tutor oldTutorOfCitaListCita = citaListCita.getTutor();
                citaListCita.setTutor(tutor);
                citaListCita = em.merge(citaListCita);
                if (oldTutorOfCitaListCita != null) {
                    oldTutorOfCitaListCita.getCitaList().remove(citaListCita);
                    oldTutorOfCitaListCita = em.merge(oldTutorOfCitaListCita);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tutor tutor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutor persistentTutor = em.find(Tutor.class, tutor.getIdPersona());
            List<Tutorado> tutoradoListOld = persistentTutor.getTutoradoList();
            List<Tutorado> tutoradoListNew = tutor.getTutoradoList();
            List<Cita> citaListOld = persistentTutor.getCitaList();
            List<Cita> citaListNew = tutor.getCitaList();
            List<Tutorado> attachedTutoradoListNew = new ArrayList<Tutorado>();
            for (Tutorado tutoradoListNewTutoradoToAttach : tutoradoListNew) {
                tutoradoListNewTutoradoToAttach = em.getReference(tutoradoListNewTutoradoToAttach.getClass(), tutoradoListNewTutoradoToAttach.getIdTutorado());
                attachedTutoradoListNew.add(tutoradoListNewTutoradoToAttach);
            }
            tutoradoListNew = attachedTutoradoListNew;
            tutor.setTutoradoList(tutoradoListNew);
            List<Cita> attachedCitaListNew = new ArrayList<Cita>();
            for (Cita citaListNewCitaToAttach : citaListNew) {
                citaListNewCitaToAttach = em.getReference(citaListNewCitaToAttach.getClass(), citaListNewCitaToAttach.getIdCita());
                attachedCitaListNew.add(citaListNewCitaToAttach);
            }
            citaListNew = attachedCitaListNew;
            tutor.setCitaList(citaListNew);
            tutor = em.merge(tutor);
            for (Tutorado tutoradoListOldTutorado : tutoradoListOld) {
                if (!tutoradoListNew.contains(tutoradoListOldTutorado)) {
                    tutoradoListOldTutorado.setTutor(null);
                    tutoradoListOldTutorado = em.merge(tutoradoListOldTutorado);
                }
            }
            for (Tutorado tutoradoListNewTutorado : tutoradoListNew) {
                if (!tutoradoListOld.contains(tutoradoListNewTutorado)) {
                    Tutor oldTutorOfTutoradoListNewTutorado = tutoradoListNewTutorado.getTutor();
                    tutoradoListNewTutorado.setTutor(tutor);
                    tutoradoListNewTutorado = em.merge(tutoradoListNewTutorado);
                    if (oldTutorOfTutoradoListNewTutorado != null && !oldTutorOfTutoradoListNewTutorado.equals(tutor)) {
                        oldTutorOfTutoradoListNewTutorado.getTutoradoList().remove(tutoradoListNewTutorado);
                        oldTutorOfTutoradoListNewTutorado = em.merge(oldTutorOfTutoradoListNewTutorado);
                    }
                }
            }
            for (Cita citaListOldCita : citaListOld) {
                if (!citaListNew.contains(citaListOldCita)) {
                    citaListOldCita.setTutor(null);
                    citaListOldCita = em.merge(citaListOldCita);
                }
            }
            for (Cita citaListNewCita : citaListNew) {
                if (!citaListOld.contains(citaListNewCita)) {
                    Tutor oldTutorOfCitaListNewCita = citaListNewCita.getTutor();
                    citaListNewCita.setTutor(tutor);
                    citaListNewCita = em.merge(citaListNewCita);
                    if (oldTutorOfCitaListNewCita != null && !oldTutorOfCitaListNewCita.equals(tutor)) {
                        oldTutorOfCitaListNewCita.getCitaList().remove(citaListNewCita);
                        oldTutorOfCitaListNewCita = em.merge(oldTutorOfCitaListNewCita);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tutor.getIdPersona();
                if (findTutor(id) == null) {
                    throw new NonexistentEntityException("The tutor with id " + id + " no longer exists.");
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
            Tutor tutor;
            try {
                tutor = em.getReference(Tutor.class, id);
                tutor.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tutor with id " + id + " no longer exists.", enfe);
            }
            List<Tutorado> tutoradoList = tutor.getTutoradoList();
            for (Tutorado tutoradoListTutorado : tutoradoList) {
                tutoradoListTutorado.setTutor(null);
                tutoradoListTutorado = em.merge(tutoradoListTutorado);
            }
            List<Cita> citaList = tutor.getCitaList();
            for (Cita citaListCita : citaList) {
                citaListCita.setTutor(null);
                citaListCita = em.merge(citaListCita);
            }
            em.remove(tutor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tutor> findTutorEntities() {
        return findTutorEntities(true, -1, -1);
    }

    public List<Tutor> findTutorEntities(int maxResults, int firstResult) {
        return findTutorEntities(false, maxResults, firstResult);
    }

    private List<Tutor> findTutorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tutor.class));
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

    public Tutor findTutor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tutor.class, id);
        } finally {
            em.close();
        }
    }

    public int getTutorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tutor> rt = cq.from(Tutor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
