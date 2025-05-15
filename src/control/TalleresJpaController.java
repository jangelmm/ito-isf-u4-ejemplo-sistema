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
import modelo.Usuarios;
import modelo.ComentariosRevisionTaller;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Notificaciones;
import modelo.Evidencias;
import modelo.EventoParticipantesTalleres;
import modelo.Talleres;

/**
 *
 * @author jesus
 */
public class TalleresJpaController implements Serializable {

    public TalleresJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Talleres talleres) {
        if (talleres.getComentariosRevisionTallerList() == null) {
            talleres.setComentariosRevisionTallerList(new ArrayList<ComentariosRevisionTaller>());
        }
        if (talleres.getNotificacionesList() == null) {
            talleres.setNotificacionesList(new ArrayList<Notificaciones>());
        }
        if (talleres.getEvidenciasList() == null) {
            talleres.setEvidenciasList(new ArrayList<Evidencias>());
        }
        if (talleres.getEventoParticipantesTalleresList() == null) {
            talleres.setEventoParticipantesTalleresList(new ArrayList<EventoParticipantesTalleres>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios idUsuarioProponente = talleres.getIdUsuarioProponente();
            if (idUsuarioProponente != null) {
                idUsuarioProponente = em.getReference(idUsuarioProponente.getClass(), idUsuarioProponente.getIdUsuario());
                talleres.setIdUsuarioProponente(idUsuarioProponente);
            }
            List<ComentariosRevisionTaller> attachedComentariosRevisionTallerList = new ArrayList<ComentariosRevisionTaller>();
            for (ComentariosRevisionTaller comentariosRevisionTallerListComentariosRevisionTallerToAttach : talleres.getComentariosRevisionTallerList()) {
                comentariosRevisionTallerListComentariosRevisionTallerToAttach = em.getReference(comentariosRevisionTallerListComentariosRevisionTallerToAttach.getClass(), comentariosRevisionTallerListComentariosRevisionTallerToAttach.getIdComentario());
                attachedComentariosRevisionTallerList.add(comentariosRevisionTallerListComentariosRevisionTallerToAttach);
            }
            talleres.setComentariosRevisionTallerList(attachedComentariosRevisionTallerList);
            List<Notificaciones> attachedNotificacionesList = new ArrayList<Notificaciones>();
            for (Notificaciones notificacionesListNotificacionesToAttach : talleres.getNotificacionesList()) {
                notificacionesListNotificacionesToAttach = em.getReference(notificacionesListNotificacionesToAttach.getClass(), notificacionesListNotificacionesToAttach.getIdNotificacion());
                attachedNotificacionesList.add(notificacionesListNotificacionesToAttach);
            }
            talleres.setNotificacionesList(attachedNotificacionesList);
            List<Evidencias> attachedEvidenciasList = new ArrayList<Evidencias>();
            for (Evidencias evidenciasListEvidenciasToAttach : talleres.getEvidenciasList()) {
                evidenciasListEvidenciasToAttach = em.getReference(evidenciasListEvidenciasToAttach.getClass(), evidenciasListEvidenciasToAttach.getIdEvidencia());
                attachedEvidenciasList.add(evidenciasListEvidenciasToAttach);
            }
            talleres.setEvidenciasList(attachedEvidenciasList);
            List<EventoParticipantesTalleres> attachedEventoParticipantesTalleresList = new ArrayList<EventoParticipantesTalleres>();
            for (EventoParticipantesTalleres eventoParticipantesTalleresListEventoParticipantesTalleresToAttach : talleres.getEventoParticipantesTalleresList()) {
                eventoParticipantesTalleresListEventoParticipantesTalleresToAttach = em.getReference(eventoParticipantesTalleresListEventoParticipantesTalleresToAttach.getClass(), eventoParticipantesTalleresListEventoParticipantesTalleresToAttach.getIdEventoParticipanteTaller());
                attachedEventoParticipantesTalleresList.add(eventoParticipantesTalleresListEventoParticipantesTalleresToAttach);
            }
            talleres.setEventoParticipantesTalleresList(attachedEventoParticipantesTalleresList);
            em.persist(talleres);
            if (idUsuarioProponente != null) {
                idUsuarioProponente.getTalleresList().add(talleres);
                idUsuarioProponente = em.merge(idUsuarioProponente);
            }
            for (ComentariosRevisionTaller comentariosRevisionTallerListComentariosRevisionTaller : talleres.getComentariosRevisionTallerList()) {
                Talleres oldIdTallerOfComentariosRevisionTallerListComentariosRevisionTaller = comentariosRevisionTallerListComentariosRevisionTaller.getIdTaller();
                comentariosRevisionTallerListComentariosRevisionTaller.setIdTaller(talleres);
                comentariosRevisionTallerListComentariosRevisionTaller = em.merge(comentariosRevisionTallerListComentariosRevisionTaller);
                if (oldIdTallerOfComentariosRevisionTallerListComentariosRevisionTaller != null) {
                    oldIdTallerOfComentariosRevisionTallerListComentariosRevisionTaller.getComentariosRevisionTallerList().remove(comentariosRevisionTallerListComentariosRevisionTaller);
                    oldIdTallerOfComentariosRevisionTallerListComentariosRevisionTaller = em.merge(oldIdTallerOfComentariosRevisionTallerListComentariosRevisionTaller);
                }
            }
            for (Notificaciones notificacionesListNotificaciones : talleres.getNotificacionesList()) {
                Talleres oldIdTallerRelacionadoOfNotificacionesListNotificaciones = notificacionesListNotificaciones.getIdTallerRelacionado();
                notificacionesListNotificaciones.setIdTallerRelacionado(talleres);
                notificacionesListNotificaciones = em.merge(notificacionesListNotificaciones);
                if (oldIdTallerRelacionadoOfNotificacionesListNotificaciones != null) {
                    oldIdTallerRelacionadoOfNotificacionesListNotificaciones.getNotificacionesList().remove(notificacionesListNotificaciones);
                    oldIdTallerRelacionadoOfNotificacionesListNotificaciones = em.merge(oldIdTallerRelacionadoOfNotificacionesListNotificaciones);
                }
            }
            for (Evidencias evidenciasListEvidencias : talleres.getEvidenciasList()) {
                Talleres oldIdTallerAsociadoOfEvidenciasListEvidencias = evidenciasListEvidencias.getIdTallerAsociado();
                evidenciasListEvidencias.setIdTallerAsociado(talleres);
                evidenciasListEvidencias = em.merge(evidenciasListEvidencias);
                if (oldIdTallerAsociadoOfEvidenciasListEvidencias != null) {
                    oldIdTallerAsociadoOfEvidenciasListEvidencias.getEvidenciasList().remove(evidenciasListEvidencias);
                    oldIdTallerAsociadoOfEvidenciasListEvidencias = em.merge(oldIdTallerAsociadoOfEvidenciasListEvidencias);
                }
            }
            for (EventoParticipantesTalleres eventoParticipantesTalleresListEventoParticipantesTalleres : talleres.getEventoParticipantesTalleresList()) {
                Talleres oldIdTallerImpartidoOfEventoParticipantesTalleresListEventoParticipantesTalleres = eventoParticipantesTalleresListEventoParticipantesTalleres.getIdTallerImpartido();
                eventoParticipantesTalleresListEventoParticipantesTalleres.setIdTallerImpartido(talleres);
                eventoParticipantesTalleresListEventoParticipantesTalleres = em.merge(eventoParticipantesTalleresListEventoParticipantesTalleres);
                if (oldIdTallerImpartidoOfEventoParticipantesTalleresListEventoParticipantesTalleres != null) {
                    oldIdTallerImpartidoOfEventoParticipantesTalleresListEventoParticipantesTalleres.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleresListEventoParticipantesTalleres);
                    oldIdTallerImpartidoOfEventoParticipantesTalleresListEventoParticipantesTalleres = em.merge(oldIdTallerImpartidoOfEventoParticipantesTalleresListEventoParticipantesTalleres);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Talleres talleres) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Talleres persistentTalleres = em.find(Talleres.class, talleres.getIdTaller());
            Usuarios idUsuarioProponenteOld = persistentTalleres.getIdUsuarioProponente();
            Usuarios idUsuarioProponenteNew = talleres.getIdUsuarioProponente();
            List<ComentariosRevisionTaller> comentariosRevisionTallerListOld = persistentTalleres.getComentariosRevisionTallerList();
            List<ComentariosRevisionTaller> comentariosRevisionTallerListNew = talleres.getComentariosRevisionTallerList();
            List<Notificaciones> notificacionesListOld = persistentTalleres.getNotificacionesList();
            List<Notificaciones> notificacionesListNew = talleres.getNotificacionesList();
            List<Evidencias> evidenciasListOld = persistentTalleres.getEvidenciasList();
            List<Evidencias> evidenciasListNew = talleres.getEvidenciasList();
            List<EventoParticipantesTalleres> eventoParticipantesTalleresListOld = persistentTalleres.getEventoParticipantesTalleresList();
            List<EventoParticipantesTalleres> eventoParticipantesTalleresListNew = talleres.getEventoParticipantesTalleresList();
            List<String> illegalOrphanMessages = null;
            for (ComentariosRevisionTaller comentariosRevisionTallerListOldComentariosRevisionTaller : comentariosRevisionTallerListOld) {
                if (!comentariosRevisionTallerListNew.contains(comentariosRevisionTallerListOldComentariosRevisionTaller)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ComentariosRevisionTaller " + comentariosRevisionTallerListOldComentariosRevisionTaller + " since its idTaller field is not nullable.");
                }
            }
            for (EventoParticipantesTalleres eventoParticipantesTalleresListOldEventoParticipantesTalleres : eventoParticipantesTalleresListOld) {
                if (!eventoParticipantesTalleresListNew.contains(eventoParticipantesTalleresListOldEventoParticipantesTalleres)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventoParticipantesTalleres " + eventoParticipantesTalleresListOldEventoParticipantesTalleres + " since its idTallerImpartido field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idUsuarioProponenteNew != null) {
                idUsuarioProponenteNew = em.getReference(idUsuarioProponenteNew.getClass(), idUsuarioProponenteNew.getIdUsuario());
                talleres.setIdUsuarioProponente(idUsuarioProponenteNew);
            }
            List<ComentariosRevisionTaller> attachedComentariosRevisionTallerListNew = new ArrayList<ComentariosRevisionTaller>();
            for (ComentariosRevisionTaller comentariosRevisionTallerListNewComentariosRevisionTallerToAttach : comentariosRevisionTallerListNew) {
                comentariosRevisionTallerListNewComentariosRevisionTallerToAttach = em.getReference(comentariosRevisionTallerListNewComentariosRevisionTallerToAttach.getClass(), comentariosRevisionTallerListNewComentariosRevisionTallerToAttach.getIdComentario());
                attachedComentariosRevisionTallerListNew.add(comentariosRevisionTallerListNewComentariosRevisionTallerToAttach);
            }
            comentariosRevisionTallerListNew = attachedComentariosRevisionTallerListNew;
            talleres.setComentariosRevisionTallerList(comentariosRevisionTallerListNew);
            List<Notificaciones> attachedNotificacionesListNew = new ArrayList<Notificaciones>();
            for (Notificaciones notificacionesListNewNotificacionesToAttach : notificacionesListNew) {
                notificacionesListNewNotificacionesToAttach = em.getReference(notificacionesListNewNotificacionesToAttach.getClass(), notificacionesListNewNotificacionesToAttach.getIdNotificacion());
                attachedNotificacionesListNew.add(notificacionesListNewNotificacionesToAttach);
            }
            notificacionesListNew = attachedNotificacionesListNew;
            talleres.setNotificacionesList(notificacionesListNew);
            List<Evidencias> attachedEvidenciasListNew = new ArrayList<Evidencias>();
            for (Evidencias evidenciasListNewEvidenciasToAttach : evidenciasListNew) {
                evidenciasListNewEvidenciasToAttach = em.getReference(evidenciasListNewEvidenciasToAttach.getClass(), evidenciasListNewEvidenciasToAttach.getIdEvidencia());
                attachedEvidenciasListNew.add(evidenciasListNewEvidenciasToAttach);
            }
            evidenciasListNew = attachedEvidenciasListNew;
            talleres.setEvidenciasList(evidenciasListNew);
            List<EventoParticipantesTalleres> attachedEventoParticipantesTalleresListNew = new ArrayList<EventoParticipantesTalleres>();
            for (EventoParticipantesTalleres eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach : eventoParticipantesTalleresListNew) {
                eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach = em.getReference(eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach.getClass(), eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach.getIdEventoParticipanteTaller());
                attachedEventoParticipantesTalleresListNew.add(eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach);
            }
            eventoParticipantesTalleresListNew = attachedEventoParticipantesTalleresListNew;
            talleres.setEventoParticipantesTalleresList(eventoParticipantesTalleresListNew);
            talleres = em.merge(talleres);
            if (idUsuarioProponenteOld != null && !idUsuarioProponenteOld.equals(idUsuarioProponenteNew)) {
                idUsuarioProponenteOld.getTalleresList().remove(talleres);
                idUsuarioProponenteOld = em.merge(idUsuarioProponenteOld);
            }
            if (idUsuarioProponenteNew != null && !idUsuarioProponenteNew.equals(idUsuarioProponenteOld)) {
                idUsuarioProponenteNew.getTalleresList().add(talleres);
                idUsuarioProponenteNew = em.merge(idUsuarioProponenteNew);
            }
            for (ComentariosRevisionTaller comentariosRevisionTallerListNewComentariosRevisionTaller : comentariosRevisionTallerListNew) {
                if (!comentariosRevisionTallerListOld.contains(comentariosRevisionTallerListNewComentariosRevisionTaller)) {
                    Talleres oldIdTallerOfComentariosRevisionTallerListNewComentariosRevisionTaller = comentariosRevisionTallerListNewComentariosRevisionTaller.getIdTaller();
                    comentariosRevisionTallerListNewComentariosRevisionTaller.setIdTaller(talleres);
                    comentariosRevisionTallerListNewComentariosRevisionTaller = em.merge(comentariosRevisionTallerListNewComentariosRevisionTaller);
                    if (oldIdTallerOfComentariosRevisionTallerListNewComentariosRevisionTaller != null && !oldIdTallerOfComentariosRevisionTallerListNewComentariosRevisionTaller.equals(talleres)) {
                        oldIdTallerOfComentariosRevisionTallerListNewComentariosRevisionTaller.getComentariosRevisionTallerList().remove(comentariosRevisionTallerListNewComentariosRevisionTaller);
                        oldIdTallerOfComentariosRevisionTallerListNewComentariosRevisionTaller = em.merge(oldIdTallerOfComentariosRevisionTallerListNewComentariosRevisionTaller);
                    }
                }
            }
            for (Notificaciones notificacionesListOldNotificaciones : notificacionesListOld) {
                if (!notificacionesListNew.contains(notificacionesListOldNotificaciones)) {
                    notificacionesListOldNotificaciones.setIdTallerRelacionado(null);
                    notificacionesListOldNotificaciones = em.merge(notificacionesListOldNotificaciones);
                }
            }
            for (Notificaciones notificacionesListNewNotificaciones : notificacionesListNew) {
                if (!notificacionesListOld.contains(notificacionesListNewNotificaciones)) {
                    Talleres oldIdTallerRelacionadoOfNotificacionesListNewNotificaciones = notificacionesListNewNotificaciones.getIdTallerRelacionado();
                    notificacionesListNewNotificaciones.setIdTallerRelacionado(talleres);
                    notificacionesListNewNotificaciones = em.merge(notificacionesListNewNotificaciones);
                    if (oldIdTallerRelacionadoOfNotificacionesListNewNotificaciones != null && !oldIdTallerRelacionadoOfNotificacionesListNewNotificaciones.equals(talleres)) {
                        oldIdTallerRelacionadoOfNotificacionesListNewNotificaciones.getNotificacionesList().remove(notificacionesListNewNotificaciones);
                        oldIdTallerRelacionadoOfNotificacionesListNewNotificaciones = em.merge(oldIdTallerRelacionadoOfNotificacionesListNewNotificaciones);
                    }
                }
            }
            for (Evidencias evidenciasListOldEvidencias : evidenciasListOld) {
                if (!evidenciasListNew.contains(evidenciasListOldEvidencias)) {
                    evidenciasListOldEvidencias.setIdTallerAsociado(null);
                    evidenciasListOldEvidencias = em.merge(evidenciasListOldEvidencias);
                }
            }
            for (Evidencias evidenciasListNewEvidencias : evidenciasListNew) {
                if (!evidenciasListOld.contains(evidenciasListNewEvidencias)) {
                    Talleres oldIdTallerAsociadoOfEvidenciasListNewEvidencias = evidenciasListNewEvidencias.getIdTallerAsociado();
                    evidenciasListNewEvidencias.setIdTallerAsociado(talleres);
                    evidenciasListNewEvidencias = em.merge(evidenciasListNewEvidencias);
                    if (oldIdTallerAsociadoOfEvidenciasListNewEvidencias != null && !oldIdTallerAsociadoOfEvidenciasListNewEvidencias.equals(talleres)) {
                        oldIdTallerAsociadoOfEvidenciasListNewEvidencias.getEvidenciasList().remove(evidenciasListNewEvidencias);
                        oldIdTallerAsociadoOfEvidenciasListNewEvidencias = em.merge(oldIdTallerAsociadoOfEvidenciasListNewEvidencias);
                    }
                }
            }
            for (EventoParticipantesTalleres eventoParticipantesTalleresListNewEventoParticipantesTalleres : eventoParticipantesTalleresListNew) {
                if (!eventoParticipantesTalleresListOld.contains(eventoParticipantesTalleresListNewEventoParticipantesTalleres)) {
                    Talleres oldIdTallerImpartidoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres = eventoParticipantesTalleresListNewEventoParticipantesTalleres.getIdTallerImpartido();
                    eventoParticipantesTalleresListNewEventoParticipantesTalleres.setIdTallerImpartido(talleres);
                    eventoParticipantesTalleresListNewEventoParticipantesTalleres = em.merge(eventoParticipantesTalleresListNewEventoParticipantesTalleres);
                    if (oldIdTallerImpartidoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres != null && !oldIdTallerImpartidoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres.equals(talleres)) {
                        oldIdTallerImpartidoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleresListNewEventoParticipantesTalleres);
                        oldIdTallerImpartidoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres = em.merge(oldIdTallerImpartidoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = talleres.getIdTaller();
                if (findTalleres(id) == null) {
                    throw new NonexistentEntityException("The talleres with id " + id + " no longer exists.");
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
            Talleres talleres;
            try {
                talleres = em.getReference(Talleres.class, id);
                talleres.getIdTaller();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The talleres with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ComentariosRevisionTaller> comentariosRevisionTallerListOrphanCheck = talleres.getComentariosRevisionTallerList();
            for (ComentariosRevisionTaller comentariosRevisionTallerListOrphanCheckComentariosRevisionTaller : comentariosRevisionTallerListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Talleres (" + talleres + ") cannot be destroyed since the ComentariosRevisionTaller " + comentariosRevisionTallerListOrphanCheckComentariosRevisionTaller + " in its comentariosRevisionTallerList field has a non-nullable idTaller field.");
            }
            List<EventoParticipantesTalleres> eventoParticipantesTalleresListOrphanCheck = talleres.getEventoParticipantesTalleresList();
            for (EventoParticipantesTalleres eventoParticipantesTalleresListOrphanCheckEventoParticipantesTalleres : eventoParticipantesTalleresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Talleres (" + talleres + ") cannot be destroyed since the EventoParticipantesTalleres " + eventoParticipantesTalleresListOrphanCheckEventoParticipantesTalleres + " in its eventoParticipantesTalleresList field has a non-nullable idTallerImpartido field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Usuarios idUsuarioProponente = talleres.getIdUsuarioProponente();
            if (idUsuarioProponente != null) {
                idUsuarioProponente.getTalleresList().remove(talleres);
                idUsuarioProponente = em.merge(idUsuarioProponente);
            }
            List<Notificaciones> notificacionesList = talleres.getNotificacionesList();
            for (Notificaciones notificacionesListNotificaciones : notificacionesList) {
                notificacionesListNotificaciones.setIdTallerRelacionado(null);
                notificacionesListNotificaciones = em.merge(notificacionesListNotificaciones);
            }
            List<Evidencias> evidenciasList = talleres.getEvidenciasList();
            for (Evidencias evidenciasListEvidencias : evidenciasList) {
                evidenciasListEvidencias.setIdTallerAsociado(null);
                evidenciasListEvidencias = em.merge(evidenciasListEvidencias);
            }
            em.remove(talleres);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Talleres> findTalleresEntities() {
        return findTalleresEntities(true, -1, -1);
    }

    public List<Talleres> findTalleresEntities(int maxResults, int firstResult) {
        return findTalleresEntities(false, maxResults, firstResult);
    }

    private List<Talleres> findTalleresEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Talleres.class));
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

    public Talleres findTalleres(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Talleres.class, id);
        } finally {
            em.close();
        }
    }

    public int getTalleresCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Talleres> rt = cq.from(Talleres.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
