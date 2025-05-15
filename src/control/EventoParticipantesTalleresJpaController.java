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
import modelo.EventoParticipantesTalleres;
import modelo.Eventos;
import modelo.Talleres;
import modelo.Usuarios;

/**
 *
 * @author jesus
 */
public class EventoParticipantesTalleresJpaController implements Serializable {

    public EventoParticipantesTalleresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(EventoParticipantesTalleres eventoParticipantesTalleres) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Eventos idEvento = eventoParticipantesTalleres.getIdEvento();
            if (idEvento != null) {
                idEvento = em.getReference(idEvento.getClass(), idEvento.getIdEvento());
                eventoParticipantesTalleres.setIdEvento(idEvento);
            }
            Talleres idTallerImpartido = eventoParticipantesTalleres.getIdTallerImpartido();
            if (idTallerImpartido != null) {
                idTallerImpartido = em.getReference(idTallerImpartido.getClass(), idTallerImpartido.getIdTaller());
                eventoParticipantesTalleres.setIdTallerImpartido(idTallerImpartido);
            }
            Usuarios idTallerista = eventoParticipantesTalleres.getIdTallerista();
            if (idTallerista != null) {
                idTallerista = em.getReference(idTallerista.getClass(), idTallerista.getIdUsuario());
                eventoParticipantesTalleres.setIdTallerista(idTallerista);
            }
            em.persist(eventoParticipantesTalleres);
            if (idEvento != null) {
                idEvento.getEventoParticipantesTalleresList().add(eventoParticipantesTalleres);
                idEvento = em.merge(idEvento);
            }
            if (idTallerImpartido != null) {
                idTallerImpartido.getEventoParticipantesTalleresList().add(eventoParticipantesTalleres);
                idTallerImpartido = em.merge(idTallerImpartido);
            }
            if (idTallerista != null) {
                idTallerista.getEventoParticipantesTalleresList().add(eventoParticipantesTalleres);
                idTallerista = em.merge(idTallerista);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(EventoParticipantesTalleres eventoParticipantesTalleres) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            EventoParticipantesTalleres persistentEventoParticipantesTalleres = em.find(EventoParticipantesTalleres.class, eventoParticipantesTalleres.getIdEventoParticipanteTaller());
            Eventos idEventoOld = persistentEventoParticipantesTalleres.getIdEvento();
            Eventos idEventoNew = eventoParticipantesTalleres.getIdEvento();
            Talleres idTallerImpartidoOld = persistentEventoParticipantesTalleres.getIdTallerImpartido();
            Talleres idTallerImpartidoNew = eventoParticipantesTalleres.getIdTallerImpartido();
            Usuarios idTalleristaOld = persistentEventoParticipantesTalleres.getIdTallerista();
            Usuarios idTalleristaNew = eventoParticipantesTalleres.getIdTallerista();
            if (idEventoNew != null) {
                idEventoNew = em.getReference(idEventoNew.getClass(), idEventoNew.getIdEvento());
                eventoParticipantesTalleres.setIdEvento(idEventoNew);
            }
            if (idTallerImpartidoNew != null) {
                idTallerImpartidoNew = em.getReference(idTallerImpartidoNew.getClass(), idTallerImpartidoNew.getIdTaller());
                eventoParticipantesTalleres.setIdTallerImpartido(idTallerImpartidoNew);
            }
            if (idTalleristaNew != null) {
                idTalleristaNew = em.getReference(idTalleristaNew.getClass(), idTalleristaNew.getIdUsuario());
                eventoParticipantesTalleres.setIdTallerista(idTalleristaNew);
            }
            eventoParticipantesTalleres = em.merge(eventoParticipantesTalleres);
            if (idEventoOld != null && !idEventoOld.equals(idEventoNew)) {
                idEventoOld.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleres);
                idEventoOld = em.merge(idEventoOld);
            }
            if (idEventoNew != null && !idEventoNew.equals(idEventoOld)) {
                idEventoNew.getEventoParticipantesTalleresList().add(eventoParticipantesTalleres);
                idEventoNew = em.merge(idEventoNew);
            }
            if (idTallerImpartidoOld != null && !idTallerImpartidoOld.equals(idTallerImpartidoNew)) {
                idTallerImpartidoOld.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleres);
                idTallerImpartidoOld = em.merge(idTallerImpartidoOld);
            }
            if (idTallerImpartidoNew != null && !idTallerImpartidoNew.equals(idTallerImpartidoOld)) {
                idTallerImpartidoNew.getEventoParticipantesTalleresList().add(eventoParticipantesTalleres);
                idTallerImpartidoNew = em.merge(idTallerImpartidoNew);
            }
            if (idTalleristaOld != null && !idTalleristaOld.equals(idTalleristaNew)) {
                idTalleristaOld.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleres);
                idTalleristaOld = em.merge(idTalleristaOld);
            }
            if (idTalleristaNew != null && !idTalleristaNew.equals(idTalleristaOld)) {
                idTalleristaNew.getEventoParticipantesTalleresList().add(eventoParticipantesTalleres);
                idTalleristaNew = em.merge(idTalleristaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = eventoParticipantesTalleres.getIdEventoParticipanteTaller();
                if (findEventoParticipantesTalleres(id) == null) {
                    throw new NonexistentEntityException("The eventoParticipantesTalleres with id " + id + " no longer exists.");
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
            EventoParticipantesTalleres eventoParticipantesTalleres;
            try {
                eventoParticipantesTalleres = em.getReference(EventoParticipantesTalleres.class, id);
                eventoParticipantesTalleres.getIdEventoParticipanteTaller();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventoParticipantesTalleres with id " + id + " no longer exists.", enfe);
            }
            Eventos idEvento = eventoParticipantesTalleres.getIdEvento();
            if (idEvento != null) {
                idEvento.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleres);
                idEvento = em.merge(idEvento);
            }
            Talleres idTallerImpartido = eventoParticipantesTalleres.getIdTallerImpartido();
            if (idTallerImpartido != null) {
                idTallerImpartido.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleres);
                idTallerImpartido = em.merge(idTallerImpartido);
            }
            Usuarios idTallerista = eventoParticipantesTalleres.getIdTallerista();
            if (idTallerista != null) {
                idTallerista.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleres);
                idTallerista = em.merge(idTallerista);
            }
            em.remove(eventoParticipantesTalleres);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<EventoParticipantesTalleres> findEventoParticipantesTalleresEntities() {
        return findEventoParticipantesTalleresEntities(true, -1, -1);
    }

    public List<EventoParticipantesTalleres> findEventoParticipantesTalleresEntities(int maxResults, int firstResult) {
        return findEventoParticipantesTalleresEntities(false, maxResults, firstResult);
    }

    private List<EventoParticipantesTalleres> findEventoParticipantesTalleresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(EventoParticipantesTalleres.class));
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

    public EventoParticipantesTalleres findEventoParticipantesTalleres(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(EventoParticipantesTalleres.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventoParticipantesTalleresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<EventoParticipantesTalleres> rt = cq.from(EventoParticipantesTalleres.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
