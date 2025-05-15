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
import modelo.Convocatorias;
import modelo.Eventos;
import modelo.Notificaciones;
import modelo.Talleres;
import modelo.Usuarios;

/**
 *
 * @author jesus
 */
public class NotificacionesJpaController implements Serializable {

    public NotificacionesJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notificaciones notificaciones) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatorias idConvocatoriaRelacionada = notificaciones.getIdConvocatoriaRelacionada();
            if (idConvocatoriaRelacionada != null) {
                idConvocatoriaRelacionada = em.getReference(idConvocatoriaRelacionada.getClass(), idConvocatoriaRelacionada.getIdConvocatoria());
                notificaciones.setIdConvocatoriaRelacionada(idConvocatoriaRelacionada);
            }
            Eventos idEventoRelacionado = notificaciones.getIdEventoRelacionado();
            if (idEventoRelacionado != null) {
                idEventoRelacionado = em.getReference(idEventoRelacionado.getClass(), idEventoRelacionado.getIdEvento());
                notificaciones.setIdEventoRelacionado(idEventoRelacionado);
            }
            Talleres idTallerRelacionado = notificaciones.getIdTallerRelacionado();
            if (idTallerRelacionado != null) {
                idTallerRelacionado = em.getReference(idTallerRelacionado.getClass(), idTallerRelacionado.getIdTaller());
                notificaciones.setIdTallerRelacionado(idTallerRelacionado);
            }
            Usuarios idUsuarioDestinatario = notificaciones.getIdUsuarioDestinatario();
            if (idUsuarioDestinatario != null) {
                idUsuarioDestinatario = em.getReference(idUsuarioDestinatario.getClass(), idUsuarioDestinatario.getIdUsuario());
                notificaciones.setIdUsuarioDestinatario(idUsuarioDestinatario);
            }
            em.persist(notificaciones);
            if (idConvocatoriaRelacionada != null) {
                idConvocatoriaRelacionada.getNotificacionesList().add(notificaciones);
                idConvocatoriaRelacionada = em.merge(idConvocatoriaRelacionada);
            }
            if (idEventoRelacionado != null) {
                idEventoRelacionado.getNotificacionesList().add(notificaciones);
                idEventoRelacionado = em.merge(idEventoRelacionado);
            }
            if (idTallerRelacionado != null) {
                idTallerRelacionado.getNotificacionesList().add(notificaciones);
                idTallerRelacionado = em.merge(idTallerRelacionado);
            }
            if (idUsuarioDestinatario != null) {
                idUsuarioDestinatario.getNotificacionesList().add(notificaciones);
                idUsuarioDestinatario = em.merge(idUsuarioDestinatario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notificaciones notificaciones) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notificaciones persistentNotificaciones = em.find(Notificaciones.class, notificaciones.getIdNotificacion());
            Convocatorias idConvocatoriaRelacionadaOld = persistentNotificaciones.getIdConvocatoriaRelacionada();
            Convocatorias idConvocatoriaRelacionadaNew = notificaciones.getIdConvocatoriaRelacionada();
            Eventos idEventoRelacionadoOld = persistentNotificaciones.getIdEventoRelacionado();
            Eventos idEventoRelacionadoNew = notificaciones.getIdEventoRelacionado();
            Talleres idTallerRelacionadoOld = persistentNotificaciones.getIdTallerRelacionado();
            Talleres idTallerRelacionadoNew = notificaciones.getIdTallerRelacionado();
            Usuarios idUsuarioDestinatarioOld = persistentNotificaciones.getIdUsuarioDestinatario();
            Usuarios idUsuarioDestinatarioNew = notificaciones.getIdUsuarioDestinatario();
            if (idConvocatoriaRelacionadaNew != null) {
                idConvocatoriaRelacionadaNew = em.getReference(idConvocatoriaRelacionadaNew.getClass(), idConvocatoriaRelacionadaNew.getIdConvocatoria());
                notificaciones.setIdConvocatoriaRelacionada(idConvocatoriaRelacionadaNew);
            }
            if (idEventoRelacionadoNew != null) {
                idEventoRelacionadoNew = em.getReference(idEventoRelacionadoNew.getClass(), idEventoRelacionadoNew.getIdEvento());
                notificaciones.setIdEventoRelacionado(idEventoRelacionadoNew);
            }
            if (idTallerRelacionadoNew != null) {
                idTallerRelacionadoNew = em.getReference(idTallerRelacionadoNew.getClass(), idTallerRelacionadoNew.getIdTaller());
                notificaciones.setIdTallerRelacionado(idTallerRelacionadoNew);
            }
            if (idUsuarioDestinatarioNew != null) {
                idUsuarioDestinatarioNew = em.getReference(idUsuarioDestinatarioNew.getClass(), idUsuarioDestinatarioNew.getIdUsuario());
                notificaciones.setIdUsuarioDestinatario(idUsuarioDestinatarioNew);
            }
            notificaciones = em.merge(notificaciones);
            if (idConvocatoriaRelacionadaOld != null && !idConvocatoriaRelacionadaOld.equals(idConvocatoriaRelacionadaNew)) {
                idConvocatoriaRelacionadaOld.getNotificacionesList().remove(notificaciones);
                idConvocatoriaRelacionadaOld = em.merge(idConvocatoriaRelacionadaOld);
            }
            if (idConvocatoriaRelacionadaNew != null && !idConvocatoriaRelacionadaNew.equals(idConvocatoriaRelacionadaOld)) {
                idConvocatoriaRelacionadaNew.getNotificacionesList().add(notificaciones);
                idConvocatoriaRelacionadaNew = em.merge(idConvocatoriaRelacionadaNew);
            }
            if (idEventoRelacionadoOld != null && !idEventoRelacionadoOld.equals(idEventoRelacionadoNew)) {
                idEventoRelacionadoOld.getNotificacionesList().remove(notificaciones);
                idEventoRelacionadoOld = em.merge(idEventoRelacionadoOld);
            }
            if (idEventoRelacionadoNew != null && !idEventoRelacionadoNew.equals(idEventoRelacionadoOld)) {
                idEventoRelacionadoNew.getNotificacionesList().add(notificaciones);
                idEventoRelacionadoNew = em.merge(idEventoRelacionadoNew);
            }
            if (idTallerRelacionadoOld != null && !idTallerRelacionadoOld.equals(idTallerRelacionadoNew)) {
                idTallerRelacionadoOld.getNotificacionesList().remove(notificaciones);
                idTallerRelacionadoOld = em.merge(idTallerRelacionadoOld);
            }
            if (idTallerRelacionadoNew != null && !idTallerRelacionadoNew.equals(idTallerRelacionadoOld)) {
                idTallerRelacionadoNew.getNotificacionesList().add(notificaciones);
                idTallerRelacionadoNew = em.merge(idTallerRelacionadoNew);
            }
            if (idUsuarioDestinatarioOld != null && !idUsuarioDestinatarioOld.equals(idUsuarioDestinatarioNew)) {
                idUsuarioDestinatarioOld.getNotificacionesList().remove(notificaciones);
                idUsuarioDestinatarioOld = em.merge(idUsuarioDestinatarioOld);
            }
            if (idUsuarioDestinatarioNew != null && !idUsuarioDestinatarioNew.equals(idUsuarioDestinatarioOld)) {
                idUsuarioDestinatarioNew.getNotificacionesList().add(notificaciones);
                idUsuarioDestinatarioNew = em.merge(idUsuarioDestinatarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notificaciones.getIdNotificacion();
                if (findNotificaciones(id) == null) {
                    throw new NonexistentEntityException("The notificaciones with id " + id + " no longer exists.");
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
            Notificaciones notificaciones;
            try {
                notificaciones = em.getReference(Notificaciones.class, id);
                notificaciones.getIdNotificacion();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notificaciones with id " + id + " no longer exists.", enfe);
            }
            Convocatorias idConvocatoriaRelacionada = notificaciones.getIdConvocatoriaRelacionada();
            if (idConvocatoriaRelacionada != null) {
                idConvocatoriaRelacionada.getNotificacionesList().remove(notificaciones);
                idConvocatoriaRelacionada = em.merge(idConvocatoriaRelacionada);
            }
            Eventos idEventoRelacionado = notificaciones.getIdEventoRelacionado();
            if (idEventoRelacionado != null) {
                idEventoRelacionado.getNotificacionesList().remove(notificaciones);
                idEventoRelacionado = em.merge(idEventoRelacionado);
            }
            Talleres idTallerRelacionado = notificaciones.getIdTallerRelacionado();
            if (idTallerRelacionado != null) {
                idTallerRelacionado.getNotificacionesList().remove(notificaciones);
                idTallerRelacionado = em.merge(idTallerRelacionado);
            }
            Usuarios idUsuarioDestinatario = notificaciones.getIdUsuarioDestinatario();
            if (idUsuarioDestinatario != null) {
                idUsuarioDestinatario.getNotificacionesList().remove(notificaciones);
                idUsuarioDestinatario = em.merge(idUsuarioDestinatario);
            }
            em.remove(notificaciones);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notificaciones> findNotificacionesEntities() {
        return findNotificacionesEntities(true, -1, -1);
    }

    public List<Notificaciones> findNotificacionesEntities(int maxResults, int firstResult) {
        return findNotificacionesEntities(false, maxResults, firstResult);
    }

    private List<Notificaciones> findNotificacionesEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notificaciones.class));
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

    public Notificaciones findNotificaciones(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notificaciones.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotificacionesCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notificaciones> rt = cq.from(Notificaciones.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
