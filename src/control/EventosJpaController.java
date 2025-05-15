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
import modelo.Convocatorias;
import modelo.Usuarios;
import modelo.Notificaciones;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Evidencias;
import modelo.BitacorasEventos;
import modelo.EventoParticipantesTalleres;
import modelo.Eventos;

/**
 *
 * @author jesus
 */
public class EventosJpaController implements Serializable {

    public EventosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Eventos eventos) {
        if (eventos.getNotificacionesList() == null) {
            eventos.setNotificacionesList(new ArrayList<Notificaciones>());
        }
        if (eventos.getEvidenciasList() == null) {
            eventos.setEvidenciasList(new ArrayList<Evidencias>());
        }
        if (eventos.getBitacorasEventosList() == null) {
            eventos.setBitacorasEventosList(new ArrayList<BitacorasEventos>());
        }
        if (eventos.getEventoParticipantesTalleresList() == null) {
            eventos.setEventoParticipantesTalleresList(new ArrayList<EventoParticipantesTalleres>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatorias idConvocatoriaOrigen = eventos.getIdConvocatoriaOrigen();
            if (idConvocatoriaOrigen != null) {
                idConvocatoriaOrigen = em.getReference(idConvocatoriaOrigen.getClass(), idConvocatoriaOrigen.getIdConvocatoria());
                eventos.setIdConvocatoriaOrigen(idConvocatoriaOrigen);
            }
            Usuarios idDocenteResponsable = eventos.getIdDocenteResponsable();
            if (idDocenteResponsable != null) {
                idDocenteResponsable = em.getReference(idDocenteResponsable.getClass(), idDocenteResponsable.getIdUsuario());
                eventos.setIdDocenteResponsable(idDocenteResponsable);
            }
            List<Notificaciones> attachedNotificacionesList = new ArrayList<Notificaciones>();
            for (Notificaciones notificacionesListNotificacionesToAttach : eventos.getNotificacionesList()) {
                notificacionesListNotificacionesToAttach = em.getReference(notificacionesListNotificacionesToAttach.getClass(), notificacionesListNotificacionesToAttach.getIdNotificacion());
                attachedNotificacionesList.add(notificacionesListNotificacionesToAttach);
            }
            eventos.setNotificacionesList(attachedNotificacionesList);
            List<Evidencias> attachedEvidenciasList = new ArrayList<Evidencias>();
            for (Evidencias evidenciasListEvidenciasToAttach : eventos.getEvidenciasList()) {
                evidenciasListEvidenciasToAttach = em.getReference(evidenciasListEvidenciasToAttach.getClass(), evidenciasListEvidenciasToAttach.getIdEvidencia());
                attachedEvidenciasList.add(evidenciasListEvidenciasToAttach);
            }
            eventos.setEvidenciasList(attachedEvidenciasList);
            List<BitacorasEventos> attachedBitacorasEventosList = new ArrayList<BitacorasEventos>();
            for (BitacorasEventos bitacorasEventosListBitacorasEventosToAttach : eventos.getBitacorasEventosList()) {
                bitacorasEventosListBitacorasEventosToAttach = em.getReference(bitacorasEventosListBitacorasEventosToAttach.getClass(), bitacorasEventosListBitacorasEventosToAttach.getIdBitacora());
                attachedBitacorasEventosList.add(bitacorasEventosListBitacorasEventosToAttach);
            }
            eventos.setBitacorasEventosList(attachedBitacorasEventosList);
            List<EventoParticipantesTalleres> attachedEventoParticipantesTalleresList = new ArrayList<EventoParticipantesTalleres>();
            for (EventoParticipantesTalleres eventoParticipantesTalleresListEventoParticipantesTalleresToAttach : eventos.getEventoParticipantesTalleresList()) {
                eventoParticipantesTalleresListEventoParticipantesTalleresToAttach = em.getReference(eventoParticipantesTalleresListEventoParticipantesTalleresToAttach.getClass(), eventoParticipantesTalleresListEventoParticipantesTalleresToAttach.getIdEventoParticipanteTaller());
                attachedEventoParticipantesTalleresList.add(eventoParticipantesTalleresListEventoParticipantesTalleresToAttach);
            }
            eventos.setEventoParticipantesTalleresList(attachedEventoParticipantesTalleresList);
            em.persist(eventos);
            if (idConvocatoriaOrigen != null) {
                idConvocatoriaOrigen.getEventosList().add(eventos);
                idConvocatoriaOrigen = em.merge(idConvocatoriaOrigen);
            }
            if (idDocenteResponsable != null) {
                idDocenteResponsable.getEventosList().add(eventos);
                idDocenteResponsable = em.merge(idDocenteResponsable);
            }
            for (Notificaciones notificacionesListNotificaciones : eventos.getNotificacionesList()) {
                Eventos oldIdEventoRelacionadoOfNotificacionesListNotificaciones = notificacionesListNotificaciones.getIdEventoRelacionado();
                notificacionesListNotificaciones.setIdEventoRelacionado(eventos);
                notificacionesListNotificaciones = em.merge(notificacionesListNotificaciones);
                if (oldIdEventoRelacionadoOfNotificacionesListNotificaciones != null) {
                    oldIdEventoRelacionadoOfNotificacionesListNotificaciones.getNotificacionesList().remove(notificacionesListNotificaciones);
                    oldIdEventoRelacionadoOfNotificacionesListNotificaciones = em.merge(oldIdEventoRelacionadoOfNotificacionesListNotificaciones);
                }
            }
            for (Evidencias evidenciasListEvidencias : eventos.getEvidenciasList()) {
                Eventos oldIdEventoOfEvidenciasListEvidencias = evidenciasListEvidencias.getIdEvento();
                evidenciasListEvidencias.setIdEvento(eventos);
                evidenciasListEvidencias = em.merge(evidenciasListEvidencias);
                if (oldIdEventoOfEvidenciasListEvidencias != null) {
                    oldIdEventoOfEvidenciasListEvidencias.getEvidenciasList().remove(evidenciasListEvidencias);
                    oldIdEventoOfEvidenciasListEvidencias = em.merge(oldIdEventoOfEvidenciasListEvidencias);
                }
            }
            for (BitacorasEventos bitacorasEventosListBitacorasEventos : eventos.getBitacorasEventosList()) {
                Eventos oldIdEventoOfBitacorasEventosListBitacorasEventos = bitacorasEventosListBitacorasEventos.getIdEvento();
                bitacorasEventosListBitacorasEventos.setIdEvento(eventos);
                bitacorasEventosListBitacorasEventos = em.merge(bitacorasEventosListBitacorasEventos);
                if (oldIdEventoOfBitacorasEventosListBitacorasEventos != null) {
                    oldIdEventoOfBitacorasEventosListBitacorasEventos.getBitacorasEventosList().remove(bitacorasEventosListBitacorasEventos);
                    oldIdEventoOfBitacorasEventosListBitacorasEventos = em.merge(oldIdEventoOfBitacorasEventosListBitacorasEventos);
                }
            }
            for (EventoParticipantesTalleres eventoParticipantesTalleresListEventoParticipantesTalleres : eventos.getEventoParticipantesTalleresList()) {
                Eventos oldIdEventoOfEventoParticipantesTalleresListEventoParticipantesTalleres = eventoParticipantesTalleresListEventoParticipantesTalleres.getIdEvento();
                eventoParticipantesTalleresListEventoParticipantesTalleres.setIdEvento(eventos);
                eventoParticipantesTalleresListEventoParticipantesTalleres = em.merge(eventoParticipantesTalleresListEventoParticipantesTalleres);
                if (oldIdEventoOfEventoParticipantesTalleresListEventoParticipantesTalleres != null) {
                    oldIdEventoOfEventoParticipantesTalleresListEventoParticipantesTalleres.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleresListEventoParticipantesTalleres);
                    oldIdEventoOfEventoParticipantesTalleresListEventoParticipantesTalleres = em.merge(oldIdEventoOfEventoParticipantesTalleresListEventoParticipantesTalleres);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Eventos eventos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Eventos persistentEventos = em.find(Eventos.class, eventos.getIdEvento());
            Convocatorias idConvocatoriaOrigenOld = persistentEventos.getIdConvocatoriaOrigen();
            Convocatorias idConvocatoriaOrigenNew = eventos.getIdConvocatoriaOrigen();
            Usuarios idDocenteResponsableOld = persistentEventos.getIdDocenteResponsable();
            Usuarios idDocenteResponsableNew = eventos.getIdDocenteResponsable();
            List<Notificaciones> notificacionesListOld = persistentEventos.getNotificacionesList();
            List<Notificaciones> notificacionesListNew = eventos.getNotificacionesList();
            List<Evidencias> evidenciasListOld = persistentEventos.getEvidenciasList();
            List<Evidencias> evidenciasListNew = eventos.getEvidenciasList();
            List<BitacorasEventos> bitacorasEventosListOld = persistentEventos.getBitacorasEventosList();
            List<BitacorasEventos> bitacorasEventosListNew = eventos.getBitacorasEventosList();
            List<EventoParticipantesTalleres> eventoParticipantesTalleresListOld = persistentEventos.getEventoParticipantesTalleresList();
            List<EventoParticipantesTalleres> eventoParticipantesTalleresListNew = eventos.getEventoParticipantesTalleresList();
            List<String> illegalOrphanMessages = null;
            for (Evidencias evidenciasListOldEvidencias : evidenciasListOld) {
                if (!evidenciasListNew.contains(evidenciasListOldEvidencias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evidencias " + evidenciasListOldEvidencias + " since its idEvento field is not nullable.");
                }
            }
            for (BitacorasEventos bitacorasEventosListOldBitacorasEventos : bitacorasEventosListOld) {
                if (!bitacorasEventosListNew.contains(bitacorasEventosListOldBitacorasEventos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BitacorasEventos " + bitacorasEventosListOldBitacorasEventos + " since its idEvento field is not nullable.");
                }
            }
            for (EventoParticipantesTalleres eventoParticipantesTalleresListOldEventoParticipantesTalleres : eventoParticipantesTalleresListOld) {
                if (!eventoParticipantesTalleresListNew.contains(eventoParticipantesTalleresListOldEventoParticipantesTalleres)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventoParticipantesTalleres " + eventoParticipantesTalleresListOldEventoParticipantesTalleres + " since its idEvento field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idConvocatoriaOrigenNew != null) {
                idConvocatoriaOrigenNew = em.getReference(idConvocatoriaOrigenNew.getClass(), idConvocatoriaOrigenNew.getIdConvocatoria());
                eventos.setIdConvocatoriaOrigen(idConvocatoriaOrigenNew);
            }
            if (idDocenteResponsableNew != null) {
                idDocenteResponsableNew = em.getReference(idDocenteResponsableNew.getClass(), idDocenteResponsableNew.getIdUsuario());
                eventos.setIdDocenteResponsable(idDocenteResponsableNew);
            }
            List<Notificaciones> attachedNotificacionesListNew = new ArrayList<Notificaciones>();
            for (Notificaciones notificacionesListNewNotificacionesToAttach : notificacionesListNew) {
                notificacionesListNewNotificacionesToAttach = em.getReference(notificacionesListNewNotificacionesToAttach.getClass(), notificacionesListNewNotificacionesToAttach.getIdNotificacion());
                attachedNotificacionesListNew.add(notificacionesListNewNotificacionesToAttach);
            }
            notificacionesListNew = attachedNotificacionesListNew;
            eventos.setNotificacionesList(notificacionesListNew);
            List<Evidencias> attachedEvidenciasListNew = new ArrayList<Evidencias>();
            for (Evidencias evidenciasListNewEvidenciasToAttach : evidenciasListNew) {
                evidenciasListNewEvidenciasToAttach = em.getReference(evidenciasListNewEvidenciasToAttach.getClass(), evidenciasListNewEvidenciasToAttach.getIdEvidencia());
                attachedEvidenciasListNew.add(evidenciasListNewEvidenciasToAttach);
            }
            evidenciasListNew = attachedEvidenciasListNew;
            eventos.setEvidenciasList(evidenciasListNew);
            List<BitacorasEventos> attachedBitacorasEventosListNew = new ArrayList<BitacorasEventos>();
            for (BitacorasEventos bitacorasEventosListNewBitacorasEventosToAttach : bitacorasEventosListNew) {
                bitacorasEventosListNewBitacorasEventosToAttach = em.getReference(bitacorasEventosListNewBitacorasEventosToAttach.getClass(), bitacorasEventosListNewBitacorasEventosToAttach.getIdBitacora());
                attachedBitacorasEventosListNew.add(bitacorasEventosListNewBitacorasEventosToAttach);
            }
            bitacorasEventosListNew = attachedBitacorasEventosListNew;
            eventos.setBitacorasEventosList(bitacorasEventosListNew);
            List<EventoParticipantesTalleres> attachedEventoParticipantesTalleresListNew = new ArrayList<EventoParticipantesTalleres>();
            for (EventoParticipantesTalleres eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach : eventoParticipantesTalleresListNew) {
                eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach = em.getReference(eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach.getClass(), eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach.getIdEventoParticipanteTaller());
                attachedEventoParticipantesTalleresListNew.add(eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach);
            }
            eventoParticipantesTalleresListNew = attachedEventoParticipantesTalleresListNew;
            eventos.setEventoParticipantesTalleresList(eventoParticipantesTalleresListNew);
            eventos = em.merge(eventos);
            if (idConvocatoriaOrigenOld != null && !idConvocatoriaOrigenOld.equals(idConvocatoriaOrigenNew)) {
                idConvocatoriaOrigenOld.getEventosList().remove(eventos);
                idConvocatoriaOrigenOld = em.merge(idConvocatoriaOrigenOld);
            }
            if (idConvocatoriaOrigenNew != null && !idConvocatoriaOrigenNew.equals(idConvocatoriaOrigenOld)) {
                idConvocatoriaOrigenNew.getEventosList().add(eventos);
                idConvocatoriaOrigenNew = em.merge(idConvocatoriaOrigenNew);
            }
            if (idDocenteResponsableOld != null && !idDocenteResponsableOld.equals(idDocenteResponsableNew)) {
                idDocenteResponsableOld.getEventosList().remove(eventos);
                idDocenteResponsableOld = em.merge(idDocenteResponsableOld);
            }
            if (idDocenteResponsableNew != null && !idDocenteResponsableNew.equals(idDocenteResponsableOld)) {
                idDocenteResponsableNew.getEventosList().add(eventos);
                idDocenteResponsableNew = em.merge(idDocenteResponsableNew);
            }
            for (Notificaciones notificacionesListOldNotificaciones : notificacionesListOld) {
                if (!notificacionesListNew.contains(notificacionesListOldNotificaciones)) {
                    notificacionesListOldNotificaciones.setIdEventoRelacionado(null);
                    notificacionesListOldNotificaciones = em.merge(notificacionesListOldNotificaciones);
                }
            }
            for (Notificaciones notificacionesListNewNotificaciones : notificacionesListNew) {
                if (!notificacionesListOld.contains(notificacionesListNewNotificaciones)) {
                    Eventos oldIdEventoRelacionadoOfNotificacionesListNewNotificaciones = notificacionesListNewNotificaciones.getIdEventoRelacionado();
                    notificacionesListNewNotificaciones.setIdEventoRelacionado(eventos);
                    notificacionesListNewNotificaciones = em.merge(notificacionesListNewNotificaciones);
                    if (oldIdEventoRelacionadoOfNotificacionesListNewNotificaciones != null && !oldIdEventoRelacionadoOfNotificacionesListNewNotificaciones.equals(eventos)) {
                        oldIdEventoRelacionadoOfNotificacionesListNewNotificaciones.getNotificacionesList().remove(notificacionesListNewNotificaciones);
                        oldIdEventoRelacionadoOfNotificacionesListNewNotificaciones = em.merge(oldIdEventoRelacionadoOfNotificacionesListNewNotificaciones);
                    }
                }
            }
            for (Evidencias evidenciasListNewEvidencias : evidenciasListNew) {
                if (!evidenciasListOld.contains(evidenciasListNewEvidencias)) {
                    Eventos oldIdEventoOfEvidenciasListNewEvidencias = evidenciasListNewEvidencias.getIdEvento();
                    evidenciasListNewEvidencias.setIdEvento(eventos);
                    evidenciasListNewEvidencias = em.merge(evidenciasListNewEvidencias);
                    if (oldIdEventoOfEvidenciasListNewEvidencias != null && !oldIdEventoOfEvidenciasListNewEvidencias.equals(eventos)) {
                        oldIdEventoOfEvidenciasListNewEvidencias.getEvidenciasList().remove(evidenciasListNewEvidencias);
                        oldIdEventoOfEvidenciasListNewEvidencias = em.merge(oldIdEventoOfEvidenciasListNewEvidencias);
                    }
                }
            }
            for (BitacorasEventos bitacorasEventosListNewBitacorasEventos : bitacorasEventosListNew) {
                if (!bitacorasEventosListOld.contains(bitacorasEventosListNewBitacorasEventos)) {
                    Eventos oldIdEventoOfBitacorasEventosListNewBitacorasEventos = bitacorasEventosListNewBitacorasEventos.getIdEvento();
                    bitacorasEventosListNewBitacorasEventos.setIdEvento(eventos);
                    bitacorasEventosListNewBitacorasEventos = em.merge(bitacorasEventosListNewBitacorasEventos);
                    if (oldIdEventoOfBitacorasEventosListNewBitacorasEventos != null && !oldIdEventoOfBitacorasEventosListNewBitacorasEventos.equals(eventos)) {
                        oldIdEventoOfBitacorasEventosListNewBitacorasEventos.getBitacorasEventosList().remove(bitacorasEventosListNewBitacorasEventos);
                        oldIdEventoOfBitacorasEventosListNewBitacorasEventos = em.merge(oldIdEventoOfBitacorasEventosListNewBitacorasEventos);
                    }
                }
            }
            for (EventoParticipantesTalleres eventoParticipantesTalleresListNewEventoParticipantesTalleres : eventoParticipantesTalleresListNew) {
                if (!eventoParticipantesTalleresListOld.contains(eventoParticipantesTalleresListNewEventoParticipantesTalleres)) {
                    Eventos oldIdEventoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres = eventoParticipantesTalleresListNewEventoParticipantesTalleres.getIdEvento();
                    eventoParticipantesTalleresListNewEventoParticipantesTalleres.setIdEvento(eventos);
                    eventoParticipantesTalleresListNewEventoParticipantesTalleres = em.merge(eventoParticipantesTalleresListNewEventoParticipantesTalleres);
                    if (oldIdEventoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres != null && !oldIdEventoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres.equals(eventos)) {
                        oldIdEventoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleresListNewEventoParticipantesTalleres);
                        oldIdEventoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres = em.merge(oldIdEventoOfEventoParticipantesTalleresListNewEventoParticipantesTalleres);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = eventos.getIdEvento();
                if (findEventos(id) == null) {
                    throw new NonexistentEntityException("The eventos with id " + id + " no longer exists.");
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
            Eventos eventos;
            try {
                eventos = em.getReference(Eventos.class, id);
                eventos.getIdEvento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The eventos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Evidencias> evidenciasListOrphanCheck = eventos.getEvidenciasList();
            for (Evidencias evidenciasListOrphanCheckEvidencias : evidenciasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the Evidencias " + evidenciasListOrphanCheckEvidencias + " in its evidenciasList field has a non-nullable idEvento field.");
            }
            List<BitacorasEventos> bitacorasEventosListOrphanCheck = eventos.getBitacorasEventosList();
            for (BitacorasEventos bitacorasEventosListOrphanCheckBitacorasEventos : bitacorasEventosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the BitacorasEventos " + bitacorasEventosListOrphanCheckBitacorasEventos + " in its bitacorasEventosList field has a non-nullable idEvento field.");
            }
            List<EventoParticipantesTalleres> eventoParticipantesTalleresListOrphanCheck = eventos.getEventoParticipantesTalleresList();
            for (EventoParticipantesTalleres eventoParticipantesTalleresListOrphanCheckEventoParticipantesTalleres : eventoParticipantesTalleresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Eventos (" + eventos + ") cannot be destroyed since the EventoParticipantesTalleres " + eventoParticipantesTalleresListOrphanCheckEventoParticipantesTalleres + " in its eventoParticipantesTalleresList field has a non-nullable idEvento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Convocatorias idConvocatoriaOrigen = eventos.getIdConvocatoriaOrigen();
            if (idConvocatoriaOrigen != null) {
                idConvocatoriaOrigen.getEventosList().remove(eventos);
                idConvocatoriaOrigen = em.merge(idConvocatoriaOrigen);
            }
            Usuarios idDocenteResponsable = eventos.getIdDocenteResponsable();
            if (idDocenteResponsable != null) {
                idDocenteResponsable.getEventosList().remove(eventos);
                idDocenteResponsable = em.merge(idDocenteResponsable);
            }
            List<Notificaciones> notificacionesList = eventos.getNotificacionesList();
            for (Notificaciones notificacionesListNotificaciones : notificacionesList) {
                notificacionesListNotificaciones.setIdEventoRelacionado(null);
                notificacionesListNotificaciones = em.merge(notificacionesListNotificaciones);
            }
            em.remove(eventos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Eventos> findEventosEntities() {
        return findEventosEntities(true, -1, -1);
    }

    public List<Eventos> findEventosEntities(int maxResults, int firstResult) {
        return findEventosEntities(false, maxResults, firstResult);
    }

    private List<Eventos> findEventosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Eventos.class));
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

    public Eventos findEventos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Eventos.class, id);
        } finally {
            em.close();
        }
    }

    public int getEventosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Eventos> rt = cq.from(Eventos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
