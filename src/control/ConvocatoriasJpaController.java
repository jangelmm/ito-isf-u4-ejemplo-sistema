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
import modelo.Usuarios;
import modelo.Notificaciones;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Convocatorias;
import modelo.Eventos;

/**
 *
 * @author jesus
 */
public class ConvocatoriasJpaController implements Serializable {

    public ConvocatoriasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Convocatorias convocatorias) {
        if (convocatorias.getNotificacionesList() == null) {
            convocatorias.setNotificacionesList(new ArrayList<Notificaciones>());
        }
        if (convocatorias.getEventosList() == null) {
            convocatorias.setEventosList(new ArrayList<Eventos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios idUsuarioPublica = convocatorias.getIdUsuarioPublica();
            if (idUsuarioPublica != null) {
                idUsuarioPublica = em.getReference(idUsuarioPublica.getClass(), idUsuarioPublica.getIdUsuario());
                convocatorias.setIdUsuarioPublica(idUsuarioPublica);
            }
            List<Notificaciones> attachedNotificacionesList = new ArrayList<Notificaciones>();
            for (Notificaciones notificacionesListNotificacionesToAttach : convocatorias.getNotificacionesList()) {
                notificacionesListNotificacionesToAttach = em.getReference(notificacionesListNotificacionesToAttach.getClass(), notificacionesListNotificacionesToAttach.getIdNotificacion());
                attachedNotificacionesList.add(notificacionesListNotificacionesToAttach);
            }
            convocatorias.setNotificacionesList(attachedNotificacionesList);
            List<Eventos> attachedEventosList = new ArrayList<Eventos>();
            for (Eventos eventosListEventosToAttach : convocatorias.getEventosList()) {
                eventosListEventosToAttach = em.getReference(eventosListEventosToAttach.getClass(), eventosListEventosToAttach.getIdEvento());
                attachedEventosList.add(eventosListEventosToAttach);
            }
            convocatorias.setEventosList(attachedEventosList);
            em.persist(convocatorias);
            if (idUsuarioPublica != null) {
                idUsuarioPublica.getConvocatoriasList().add(convocatorias);
                idUsuarioPublica = em.merge(idUsuarioPublica);
            }
            for (Notificaciones notificacionesListNotificaciones : convocatorias.getNotificacionesList()) {
                Convocatorias oldIdConvocatoriaRelacionadaOfNotificacionesListNotificaciones = notificacionesListNotificaciones.getIdConvocatoriaRelacionada();
                notificacionesListNotificaciones.setIdConvocatoriaRelacionada(convocatorias);
                notificacionesListNotificaciones = em.merge(notificacionesListNotificaciones);
                if (oldIdConvocatoriaRelacionadaOfNotificacionesListNotificaciones != null) {
                    oldIdConvocatoriaRelacionadaOfNotificacionesListNotificaciones.getNotificacionesList().remove(notificacionesListNotificaciones);
                    oldIdConvocatoriaRelacionadaOfNotificacionesListNotificaciones = em.merge(oldIdConvocatoriaRelacionadaOfNotificacionesListNotificaciones);
                }
            }
            for (Eventos eventosListEventos : convocatorias.getEventosList()) {
                Convocatorias oldIdConvocatoriaOrigenOfEventosListEventos = eventosListEventos.getIdConvocatoriaOrigen();
                eventosListEventos.setIdConvocatoriaOrigen(convocatorias);
                eventosListEventos = em.merge(eventosListEventos);
                if (oldIdConvocatoriaOrigenOfEventosListEventos != null) {
                    oldIdConvocatoriaOrigenOfEventosListEventos.getEventosList().remove(eventosListEventos);
                    oldIdConvocatoriaOrigenOfEventosListEventos = em.merge(oldIdConvocatoriaOrigenOfEventosListEventos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Convocatorias convocatorias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatorias persistentConvocatorias = em.find(Convocatorias.class, convocatorias.getIdConvocatoria());
            Usuarios idUsuarioPublicaOld = persistentConvocatorias.getIdUsuarioPublica();
            Usuarios idUsuarioPublicaNew = convocatorias.getIdUsuarioPublica();
            List<Notificaciones> notificacionesListOld = persistentConvocatorias.getNotificacionesList();
            List<Notificaciones> notificacionesListNew = convocatorias.getNotificacionesList();
            List<Eventos> eventosListOld = persistentConvocatorias.getEventosList();
            List<Eventos> eventosListNew = convocatorias.getEventosList();
            if (idUsuarioPublicaNew != null) {
                idUsuarioPublicaNew = em.getReference(idUsuarioPublicaNew.getClass(), idUsuarioPublicaNew.getIdUsuario());
                convocatorias.setIdUsuarioPublica(idUsuarioPublicaNew);
            }
            List<Notificaciones> attachedNotificacionesListNew = new ArrayList<Notificaciones>();
            for (Notificaciones notificacionesListNewNotificacionesToAttach : notificacionesListNew) {
                notificacionesListNewNotificacionesToAttach = em.getReference(notificacionesListNewNotificacionesToAttach.getClass(), notificacionesListNewNotificacionesToAttach.getIdNotificacion());
                attachedNotificacionesListNew.add(notificacionesListNewNotificacionesToAttach);
            }
            notificacionesListNew = attachedNotificacionesListNew;
            convocatorias.setNotificacionesList(notificacionesListNew);
            List<Eventos> attachedEventosListNew = new ArrayList<Eventos>();
            for (Eventos eventosListNewEventosToAttach : eventosListNew) {
                eventosListNewEventosToAttach = em.getReference(eventosListNewEventosToAttach.getClass(), eventosListNewEventosToAttach.getIdEvento());
                attachedEventosListNew.add(eventosListNewEventosToAttach);
            }
            eventosListNew = attachedEventosListNew;
            convocatorias.setEventosList(eventosListNew);
            convocatorias = em.merge(convocatorias);
            if (idUsuarioPublicaOld != null && !idUsuarioPublicaOld.equals(idUsuarioPublicaNew)) {
                idUsuarioPublicaOld.getConvocatoriasList().remove(convocatorias);
                idUsuarioPublicaOld = em.merge(idUsuarioPublicaOld);
            }
            if (idUsuarioPublicaNew != null && !idUsuarioPublicaNew.equals(idUsuarioPublicaOld)) {
                idUsuarioPublicaNew.getConvocatoriasList().add(convocatorias);
                idUsuarioPublicaNew = em.merge(idUsuarioPublicaNew);
            }
            for (Notificaciones notificacionesListOldNotificaciones : notificacionesListOld) {
                if (!notificacionesListNew.contains(notificacionesListOldNotificaciones)) {
                    notificacionesListOldNotificaciones.setIdConvocatoriaRelacionada(null);
                    notificacionesListOldNotificaciones = em.merge(notificacionesListOldNotificaciones);
                }
            }
            for (Notificaciones notificacionesListNewNotificaciones : notificacionesListNew) {
                if (!notificacionesListOld.contains(notificacionesListNewNotificaciones)) {
                    Convocatorias oldIdConvocatoriaRelacionadaOfNotificacionesListNewNotificaciones = notificacionesListNewNotificaciones.getIdConvocatoriaRelacionada();
                    notificacionesListNewNotificaciones.setIdConvocatoriaRelacionada(convocatorias);
                    notificacionesListNewNotificaciones = em.merge(notificacionesListNewNotificaciones);
                    if (oldIdConvocatoriaRelacionadaOfNotificacionesListNewNotificaciones != null && !oldIdConvocatoriaRelacionadaOfNotificacionesListNewNotificaciones.equals(convocatorias)) {
                        oldIdConvocatoriaRelacionadaOfNotificacionesListNewNotificaciones.getNotificacionesList().remove(notificacionesListNewNotificaciones);
                        oldIdConvocatoriaRelacionadaOfNotificacionesListNewNotificaciones = em.merge(oldIdConvocatoriaRelacionadaOfNotificacionesListNewNotificaciones);
                    }
                }
            }
            for (Eventos eventosListOldEventos : eventosListOld) {
                if (!eventosListNew.contains(eventosListOldEventos)) {
                    eventosListOldEventos.setIdConvocatoriaOrigen(null);
                    eventosListOldEventos = em.merge(eventosListOldEventos);
                }
            }
            for (Eventos eventosListNewEventos : eventosListNew) {
                if (!eventosListOld.contains(eventosListNewEventos)) {
                    Convocatorias oldIdConvocatoriaOrigenOfEventosListNewEventos = eventosListNewEventos.getIdConvocatoriaOrigen();
                    eventosListNewEventos.setIdConvocatoriaOrigen(convocatorias);
                    eventosListNewEventos = em.merge(eventosListNewEventos);
                    if (oldIdConvocatoriaOrigenOfEventosListNewEventos != null && !oldIdConvocatoriaOrigenOfEventosListNewEventos.equals(convocatorias)) {
                        oldIdConvocatoriaOrigenOfEventosListNewEventos.getEventosList().remove(eventosListNewEventos);
                        oldIdConvocatoriaOrigenOfEventosListNewEventos = em.merge(oldIdConvocatoriaOrigenOfEventosListNewEventos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = convocatorias.getIdConvocatoria();
                if (findConvocatorias(id) == null) {
                    throw new NonexistentEntityException("The convocatorias with id " + id + " no longer exists.");
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
            Convocatorias convocatorias;
            try {
                convocatorias = em.getReference(Convocatorias.class, id);
                convocatorias.getIdConvocatoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The convocatorias with id " + id + " no longer exists.", enfe);
            }
            Usuarios idUsuarioPublica = convocatorias.getIdUsuarioPublica();
            if (idUsuarioPublica != null) {
                idUsuarioPublica.getConvocatoriasList().remove(convocatorias);
                idUsuarioPublica = em.merge(idUsuarioPublica);
            }
            List<Notificaciones> notificacionesList = convocatorias.getNotificacionesList();
            for (Notificaciones notificacionesListNotificaciones : notificacionesList) {
                notificacionesListNotificaciones.setIdConvocatoriaRelacionada(null);
                notificacionesListNotificaciones = em.merge(notificacionesListNotificaciones);
            }
            List<Eventos> eventosList = convocatorias.getEventosList();
            for (Eventos eventosListEventos : eventosList) {
                eventosListEventos.setIdConvocatoriaOrigen(null);
                eventosListEventos = em.merge(eventosListEventos);
            }
            em.remove(convocatorias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Convocatorias> findConvocatoriasEntities() {
        return findConvocatoriasEntities(true, -1, -1);
    }

    public List<Convocatorias> findConvocatoriasEntities(int maxResults, int firstResult) {
        return findConvocatoriasEntities(false, maxResults, firstResult);
    }

    private List<Convocatorias> findConvocatoriasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Convocatorias.class));
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

    public Convocatorias findConvocatorias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Convocatorias.class, id);
        } finally {
            em.close();
        }
    }

    public int getConvocatoriasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Convocatorias> rt = cq.from(Convocatorias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
