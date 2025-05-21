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
import modelo.ComentariosRevisionTaller;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import modelo.Notificaciones;
import modelo.Evidencias;
import modelo.Talleres;
import modelo.Eventos;
import modelo.BitacorasEventos;
import modelo.Convocatorias;
import modelo.EventoParticipantesTalleres;
import modelo.Usuarios;

/**
 *
 * @author jesus
 */
public class UsuariosJpaController implements Serializable {

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    // Metodo para buscar usuarios por usuario y contrasena
    public Usuarios validarUsuario(String username, String password) {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Usuarios> query = em.createQuery(
                "SELECT u FROM Usuarios u WHERE (u.nombre = :username OR u.correo = :username) AND u.contrasenaHash = :password", Usuarios.class);
            query.setParameter("username", username);
            query.setParameter("password", password);
            return query.getSingleResult(); // Si no encuentra lanza excepción
        } catch (NoResultException e) {
            return null; // No coincide
        } finally {
            em.close();
        }
    }    
    
    public void create(Usuarios usuarios) {
        if (usuarios.getComentariosRevisionTallerList() == null) {
            usuarios.setComentariosRevisionTallerList(new ArrayList<ComentariosRevisionTaller>());
        }
        if (usuarios.getNotificacionesList() == null) {
            usuarios.setNotificacionesList(new ArrayList<Notificaciones>());
        }
        if (usuarios.getEvidenciasList() == null) {
            usuarios.setEvidenciasList(new ArrayList<Evidencias>());
        }
        if (usuarios.getTalleresList() == null) {
            usuarios.setTalleresList(new ArrayList<Talleres>());
        }
        if (usuarios.getEventosList() == null) {
            usuarios.setEventosList(new ArrayList<Eventos>());
        }
        if (usuarios.getBitacorasEventosList() == null) {
            usuarios.setBitacorasEventosList(new ArrayList<BitacorasEventos>());
        }
        if (usuarios.getConvocatoriasList() == null) {
            usuarios.setConvocatoriasList(new ArrayList<Convocatorias>());
        }
        if (usuarios.getEventoParticipantesTalleresList() == null) {
            usuarios.setEventoParticipantesTalleresList(new ArrayList<EventoParticipantesTalleres>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<ComentariosRevisionTaller> attachedComentariosRevisionTallerList = new ArrayList<ComentariosRevisionTaller>();
            for (ComentariosRevisionTaller comentariosRevisionTallerListComentariosRevisionTallerToAttach : usuarios.getComentariosRevisionTallerList()) {
                comentariosRevisionTallerListComentariosRevisionTallerToAttach = em.getReference(comentariosRevisionTallerListComentariosRevisionTallerToAttach.getClass(), comentariosRevisionTallerListComentariosRevisionTallerToAttach.getIdComentario());
                attachedComentariosRevisionTallerList.add(comentariosRevisionTallerListComentariosRevisionTallerToAttach);
            }
            usuarios.setComentariosRevisionTallerList(attachedComentariosRevisionTallerList);
            List<Notificaciones> attachedNotificacionesList = new ArrayList<Notificaciones>();
            for (Notificaciones notificacionesListNotificacionesToAttach : usuarios.getNotificacionesList()) {
                notificacionesListNotificacionesToAttach = em.getReference(notificacionesListNotificacionesToAttach.getClass(), notificacionesListNotificacionesToAttach.getIdNotificacion());
                attachedNotificacionesList.add(notificacionesListNotificacionesToAttach);
            }
            usuarios.setNotificacionesList(attachedNotificacionesList);
            List<Evidencias> attachedEvidenciasList = new ArrayList<Evidencias>();
            for (Evidencias evidenciasListEvidenciasToAttach : usuarios.getEvidenciasList()) {
                evidenciasListEvidenciasToAttach = em.getReference(evidenciasListEvidenciasToAttach.getClass(), evidenciasListEvidenciasToAttach.getIdEvidencia());
                attachedEvidenciasList.add(evidenciasListEvidenciasToAttach);
            }
            usuarios.setEvidenciasList(attachedEvidenciasList);
            List<Talleres> attachedTalleresList = new ArrayList<Talleres>();
            for (Talleres talleresListTalleresToAttach : usuarios.getTalleresList()) {
                talleresListTalleresToAttach = em.getReference(talleresListTalleresToAttach.getClass(), talleresListTalleresToAttach.getIdTaller());
                attachedTalleresList.add(talleresListTalleresToAttach);
            }
            usuarios.setTalleresList(attachedTalleresList);
            List<Eventos> attachedEventosList = new ArrayList<Eventos>();
            for (Eventos eventosListEventosToAttach : usuarios.getEventosList()) {
                eventosListEventosToAttach = em.getReference(eventosListEventosToAttach.getClass(), eventosListEventosToAttach.getIdEvento());
                attachedEventosList.add(eventosListEventosToAttach);
            }
            usuarios.setEventosList(attachedEventosList);
            List<BitacorasEventos> attachedBitacorasEventosList = new ArrayList<BitacorasEventos>();
            for (BitacorasEventos bitacorasEventosListBitacorasEventosToAttach : usuarios.getBitacorasEventosList()) {
                bitacorasEventosListBitacorasEventosToAttach = em.getReference(bitacorasEventosListBitacorasEventosToAttach.getClass(), bitacorasEventosListBitacorasEventosToAttach.getIdBitacora());
                attachedBitacorasEventosList.add(bitacorasEventosListBitacorasEventosToAttach);
            }
            usuarios.setBitacorasEventosList(attachedBitacorasEventosList);
            List<Convocatorias> attachedConvocatoriasList = new ArrayList<Convocatorias>();
            for (Convocatorias convocatoriasListConvocatoriasToAttach : usuarios.getConvocatoriasList()) {
                convocatoriasListConvocatoriasToAttach = em.getReference(convocatoriasListConvocatoriasToAttach.getClass(), convocatoriasListConvocatoriasToAttach.getIdConvocatoria());
                attachedConvocatoriasList.add(convocatoriasListConvocatoriasToAttach);
            }
            usuarios.setConvocatoriasList(attachedConvocatoriasList);
            List<EventoParticipantesTalleres> attachedEventoParticipantesTalleresList = new ArrayList<EventoParticipantesTalleres>();
            for (EventoParticipantesTalleres eventoParticipantesTalleresListEventoParticipantesTalleresToAttach : usuarios.getEventoParticipantesTalleresList()) {
                eventoParticipantesTalleresListEventoParticipantesTalleresToAttach = em.getReference(eventoParticipantesTalleresListEventoParticipantesTalleresToAttach.getClass(), eventoParticipantesTalleresListEventoParticipantesTalleresToAttach.getIdEventoParticipanteTaller());
                attachedEventoParticipantesTalleresList.add(eventoParticipantesTalleresListEventoParticipantesTalleresToAttach);
            }
            usuarios.setEventoParticipantesTalleresList(attachedEventoParticipantesTalleresList);
            em.persist(usuarios);
            for (ComentariosRevisionTaller comentariosRevisionTallerListComentariosRevisionTaller : usuarios.getComentariosRevisionTallerList()) {
                Usuarios oldIdUsuarioComentaristaOfComentariosRevisionTallerListComentariosRevisionTaller = comentariosRevisionTallerListComentariosRevisionTaller.getIdUsuarioComentarista();
                comentariosRevisionTallerListComentariosRevisionTaller.setIdUsuarioComentarista(usuarios);
                comentariosRevisionTallerListComentariosRevisionTaller = em.merge(comentariosRevisionTallerListComentariosRevisionTaller);
                if (oldIdUsuarioComentaristaOfComentariosRevisionTallerListComentariosRevisionTaller != null) {
                    oldIdUsuarioComentaristaOfComentariosRevisionTallerListComentariosRevisionTaller.getComentariosRevisionTallerList().remove(comentariosRevisionTallerListComentariosRevisionTaller);
                    oldIdUsuarioComentaristaOfComentariosRevisionTallerListComentariosRevisionTaller = em.merge(oldIdUsuarioComentaristaOfComentariosRevisionTallerListComentariosRevisionTaller);
                }
            }
            for (Notificaciones notificacionesListNotificaciones : usuarios.getNotificacionesList()) {
                Usuarios oldIdUsuarioDestinatarioOfNotificacionesListNotificaciones = notificacionesListNotificaciones.getIdUsuarioDestinatario();
                notificacionesListNotificaciones.setIdUsuarioDestinatario(usuarios);
                notificacionesListNotificaciones = em.merge(notificacionesListNotificaciones);
                if (oldIdUsuarioDestinatarioOfNotificacionesListNotificaciones != null) {
                    oldIdUsuarioDestinatarioOfNotificacionesListNotificaciones.getNotificacionesList().remove(notificacionesListNotificaciones);
                    oldIdUsuarioDestinatarioOfNotificacionesListNotificaciones = em.merge(oldIdUsuarioDestinatarioOfNotificacionesListNotificaciones);
                }
            }
            for (Evidencias evidenciasListEvidencias : usuarios.getEvidenciasList()) {
                Usuarios oldIdUsuarioSubioOfEvidenciasListEvidencias = evidenciasListEvidencias.getIdUsuarioSubio();
                evidenciasListEvidencias.setIdUsuarioSubio(usuarios);
                evidenciasListEvidencias = em.merge(evidenciasListEvidencias);
                if (oldIdUsuarioSubioOfEvidenciasListEvidencias != null) {
                    oldIdUsuarioSubioOfEvidenciasListEvidencias.getEvidenciasList().remove(evidenciasListEvidencias);
                    oldIdUsuarioSubioOfEvidenciasListEvidencias = em.merge(oldIdUsuarioSubioOfEvidenciasListEvidencias);
                }
            }
            for (Talleres talleresListTalleres : usuarios.getTalleresList()) {
                Usuarios oldIdUsuarioProponenteOfTalleresListTalleres = talleresListTalleres.getIdUsuarioProponente();
                talleresListTalleres.setIdUsuarioProponente(usuarios);
                talleresListTalleres = em.merge(talleresListTalleres);
                if (oldIdUsuarioProponenteOfTalleresListTalleres != null) {
                    oldIdUsuarioProponenteOfTalleresListTalleres.getTalleresList().remove(talleresListTalleres);
                    oldIdUsuarioProponenteOfTalleresListTalleres = em.merge(oldIdUsuarioProponenteOfTalleresListTalleres);
                }
            }
            for (Eventos eventosListEventos : usuarios.getEventosList()) {
                Usuarios oldIdDocenteResponsableOfEventosListEventos = eventosListEventos.getIdDocenteResponsable();
                eventosListEventos.setIdDocenteResponsable(usuarios);
                eventosListEventos = em.merge(eventosListEventos);
                if (oldIdDocenteResponsableOfEventosListEventos != null) {
                    oldIdDocenteResponsableOfEventosListEventos.getEventosList().remove(eventosListEventos);
                    oldIdDocenteResponsableOfEventosListEventos = em.merge(oldIdDocenteResponsableOfEventosListEventos);
                }
            }
            for (BitacorasEventos bitacorasEventosListBitacorasEventos : usuarios.getBitacorasEventosList()) {
                Usuarios oldIdUsuarioRegistraOfBitacorasEventosListBitacorasEventos = bitacorasEventosListBitacorasEventos.getIdUsuarioRegistra();
                bitacorasEventosListBitacorasEventos.setIdUsuarioRegistra(usuarios);
                bitacorasEventosListBitacorasEventos = em.merge(bitacorasEventosListBitacorasEventos);
                if (oldIdUsuarioRegistraOfBitacorasEventosListBitacorasEventos != null) {
                    oldIdUsuarioRegistraOfBitacorasEventosListBitacorasEventos.getBitacorasEventosList().remove(bitacorasEventosListBitacorasEventos);
                    oldIdUsuarioRegistraOfBitacorasEventosListBitacorasEventos = em.merge(oldIdUsuarioRegistraOfBitacorasEventosListBitacorasEventos);
                }
            }
            for (Convocatorias convocatoriasListConvocatorias : usuarios.getConvocatoriasList()) {
                Usuarios oldIdUsuarioPublicaOfConvocatoriasListConvocatorias = convocatoriasListConvocatorias.getIdUsuarioPublica();
                convocatoriasListConvocatorias.setIdUsuarioPublica(usuarios);
                convocatoriasListConvocatorias = em.merge(convocatoriasListConvocatorias);
                if (oldIdUsuarioPublicaOfConvocatoriasListConvocatorias != null) {
                    oldIdUsuarioPublicaOfConvocatoriasListConvocatorias.getConvocatoriasList().remove(convocatoriasListConvocatorias);
                    oldIdUsuarioPublicaOfConvocatoriasListConvocatorias = em.merge(oldIdUsuarioPublicaOfConvocatoriasListConvocatorias);
                }
            }
            for (EventoParticipantesTalleres eventoParticipantesTalleresListEventoParticipantesTalleres : usuarios.getEventoParticipantesTalleresList()) {
                Usuarios oldIdTalleristaOfEventoParticipantesTalleresListEventoParticipantesTalleres = eventoParticipantesTalleresListEventoParticipantesTalleres.getIdTallerista();
                eventoParticipantesTalleresListEventoParticipantesTalleres.setIdTallerista(usuarios);
                eventoParticipantesTalleresListEventoParticipantesTalleres = em.merge(eventoParticipantesTalleresListEventoParticipantesTalleres);
                if (oldIdTalleristaOfEventoParticipantesTalleresListEventoParticipantesTalleres != null) {
                    oldIdTalleristaOfEventoParticipantesTalleresListEventoParticipantesTalleres.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleresListEventoParticipantesTalleres);
                    oldIdTalleristaOfEventoParticipantesTalleresListEventoParticipantesTalleres = em.merge(oldIdTalleristaOfEventoParticipantesTalleresListEventoParticipantesTalleres);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuarios usuarios) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuarios persistentUsuarios = em.find(Usuarios.class, usuarios.getIdUsuario());
            List<ComentariosRevisionTaller> comentariosRevisionTallerListOld = persistentUsuarios.getComentariosRevisionTallerList();
            List<ComentariosRevisionTaller> comentariosRevisionTallerListNew = usuarios.getComentariosRevisionTallerList();
            List<Notificaciones> notificacionesListOld = persistentUsuarios.getNotificacionesList();
            List<Notificaciones> notificacionesListNew = usuarios.getNotificacionesList();
            List<Evidencias> evidenciasListOld = persistentUsuarios.getEvidenciasList();
            List<Evidencias> evidenciasListNew = usuarios.getEvidenciasList();
            List<Talleres> talleresListOld = persistentUsuarios.getTalleresList();
            List<Talleres> talleresListNew = usuarios.getTalleresList();
            List<Eventos> eventosListOld = persistentUsuarios.getEventosList();
            List<Eventos> eventosListNew = usuarios.getEventosList();
            List<BitacorasEventos> bitacorasEventosListOld = persistentUsuarios.getBitacorasEventosList();
            List<BitacorasEventos> bitacorasEventosListNew = usuarios.getBitacorasEventosList();
            List<Convocatorias> convocatoriasListOld = persistentUsuarios.getConvocatoriasList();
            List<Convocatorias> convocatoriasListNew = usuarios.getConvocatoriasList();
            List<EventoParticipantesTalleres> eventoParticipantesTalleresListOld = persistentUsuarios.getEventoParticipantesTalleresList();
            List<EventoParticipantesTalleres> eventoParticipantesTalleresListNew = usuarios.getEventoParticipantesTalleresList();
            List<String> illegalOrphanMessages = null;
            for (ComentariosRevisionTaller comentariosRevisionTallerListOldComentariosRevisionTaller : comentariosRevisionTallerListOld) {
                if (!comentariosRevisionTallerListNew.contains(comentariosRevisionTallerListOldComentariosRevisionTaller)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain ComentariosRevisionTaller " + comentariosRevisionTallerListOldComentariosRevisionTaller + " since its idUsuarioComentarista field is not nullable.");
                }
            }
            for (Notificaciones notificacionesListOldNotificaciones : notificacionesListOld) {
                if (!notificacionesListNew.contains(notificacionesListOldNotificaciones)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Notificaciones " + notificacionesListOldNotificaciones + " since its idUsuarioDestinatario field is not nullable.");
                }
            }
            for (Evidencias evidenciasListOldEvidencias : evidenciasListOld) {
                if (!evidenciasListNew.contains(evidenciasListOldEvidencias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Evidencias " + evidenciasListOldEvidencias + " since its idUsuarioSubio field is not nullable.");
                }
            }
            for (Talleres talleresListOldTalleres : talleresListOld) {
                if (!talleresListNew.contains(talleresListOldTalleres)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Talleres " + talleresListOldTalleres + " since its idUsuarioProponente field is not nullable.");
                }
            }
            for (BitacorasEventos bitacorasEventosListOldBitacorasEventos : bitacorasEventosListOld) {
                if (!bitacorasEventosListNew.contains(bitacorasEventosListOldBitacorasEventos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain BitacorasEventos " + bitacorasEventosListOldBitacorasEventos + " since its idUsuarioRegistra field is not nullable.");
                }
            }
            for (Convocatorias convocatoriasListOldConvocatorias : convocatoriasListOld) {
                if (!convocatoriasListNew.contains(convocatoriasListOldConvocatorias)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Convocatorias " + convocatoriasListOldConvocatorias + " since its idUsuarioPublica field is not nullable.");
                }
            }
            for (EventoParticipantesTalleres eventoParticipantesTalleresListOldEventoParticipantesTalleres : eventoParticipantesTalleresListOld) {
                if (!eventoParticipantesTalleresListNew.contains(eventoParticipantesTalleresListOldEventoParticipantesTalleres)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain EventoParticipantesTalleres " + eventoParticipantesTalleresListOldEventoParticipantesTalleres + " since its idTallerista field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<ComentariosRevisionTaller> attachedComentariosRevisionTallerListNew = new ArrayList<ComentariosRevisionTaller>();
            for (ComentariosRevisionTaller comentariosRevisionTallerListNewComentariosRevisionTallerToAttach : comentariosRevisionTallerListNew) {
                comentariosRevisionTallerListNewComentariosRevisionTallerToAttach = em.getReference(comentariosRevisionTallerListNewComentariosRevisionTallerToAttach.getClass(), comentariosRevisionTallerListNewComentariosRevisionTallerToAttach.getIdComentario());
                attachedComentariosRevisionTallerListNew.add(comentariosRevisionTallerListNewComentariosRevisionTallerToAttach);
            }
            comentariosRevisionTallerListNew = attachedComentariosRevisionTallerListNew;
            usuarios.setComentariosRevisionTallerList(comentariosRevisionTallerListNew);
            List<Notificaciones> attachedNotificacionesListNew = new ArrayList<Notificaciones>();
            for (Notificaciones notificacionesListNewNotificacionesToAttach : notificacionesListNew) {
                notificacionesListNewNotificacionesToAttach = em.getReference(notificacionesListNewNotificacionesToAttach.getClass(), notificacionesListNewNotificacionesToAttach.getIdNotificacion());
                attachedNotificacionesListNew.add(notificacionesListNewNotificacionesToAttach);
            }
            notificacionesListNew = attachedNotificacionesListNew;
            usuarios.setNotificacionesList(notificacionesListNew);
            List<Evidencias> attachedEvidenciasListNew = new ArrayList<Evidencias>();
            for (Evidencias evidenciasListNewEvidenciasToAttach : evidenciasListNew) {
                evidenciasListNewEvidenciasToAttach = em.getReference(evidenciasListNewEvidenciasToAttach.getClass(), evidenciasListNewEvidenciasToAttach.getIdEvidencia());
                attachedEvidenciasListNew.add(evidenciasListNewEvidenciasToAttach);
            }
            evidenciasListNew = attachedEvidenciasListNew;
            usuarios.setEvidenciasList(evidenciasListNew);
            List<Talleres> attachedTalleresListNew = new ArrayList<Talleres>();
            for (Talleres talleresListNewTalleresToAttach : talleresListNew) {
                talleresListNewTalleresToAttach = em.getReference(talleresListNewTalleresToAttach.getClass(), talleresListNewTalleresToAttach.getIdTaller());
                attachedTalleresListNew.add(talleresListNewTalleresToAttach);
            }
            talleresListNew = attachedTalleresListNew;
            usuarios.setTalleresList(talleresListNew);
            List<Eventos> attachedEventosListNew = new ArrayList<Eventos>();
            for (Eventos eventosListNewEventosToAttach : eventosListNew) {
                eventosListNewEventosToAttach = em.getReference(eventosListNewEventosToAttach.getClass(), eventosListNewEventosToAttach.getIdEvento());
                attachedEventosListNew.add(eventosListNewEventosToAttach);
            }
            eventosListNew = attachedEventosListNew;
            usuarios.setEventosList(eventosListNew);
            List<BitacorasEventos> attachedBitacorasEventosListNew = new ArrayList<BitacorasEventos>();
            for (BitacorasEventos bitacorasEventosListNewBitacorasEventosToAttach : bitacorasEventosListNew) {
                bitacorasEventosListNewBitacorasEventosToAttach = em.getReference(bitacorasEventosListNewBitacorasEventosToAttach.getClass(), bitacorasEventosListNewBitacorasEventosToAttach.getIdBitacora());
                attachedBitacorasEventosListNew.add(bitacorasEventosListNewBitacorasEventosToAttach);
            }
            bitacorasEventosListNew = attachedBitacorasEventosListNew;
            usuarios.setBitacorasEventosList(bitacorasEventosListNew);
            List<Convocatorias> attachedConvocatoriasListNew = new ArrayList<Convocatorias>();
            for (Convocatorias convocatoriasListNewConvocatoriasToAttach : convocatoriasListNew) {
                convocatoriasListNewConvocatoriasToAttach = em.getReference(convocatoriasListNewConvocatoriasToAttach.getClass(), convocatoriasListNewConvocatoriasToAttach.getIdConvocatoria());
                attachedConvocatoriasListNew.add(convocatoriasListNewConvocatoriasToAttach);
            }
            convocatoriasListNew = attachedConvocatoriasListNew;
            usuarios.setConvocatoriasList(convocatoriasListNew);
            List<EventoParticipantesTalleres> attachedEventoParticipantesTalleresListNew = new ArrayList<EventoParticipantesTalleres>();
            for (EventoParticipantesTalleres eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach : eventoParticipantesTalleresListNew) {
                eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach = em.getReference(eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach.getClass(), eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach.getIdEventoParticipanteTaller());
                attachedEventoParticipantesTalleresListNew.add(eventoParticipantesTalleresListNewEventoParticipantesTalleresToAttach);
            }
            eventoParticipantesTalleresListNew = attachedEventoParticipantesTalleresListNew;
            usuarios.setEventoParticipantesTalleresList(eventoParticipantesTalleresListNew);
            usuarios = em.merge(usuarios);
            for (ComentariosRevisionTaller comentariosRevisionTallerListNewComentariosRevisionTaller : comentariosRevisionTallerListNew) {
                if (!comentariosRevisionTallerListOld.contains(comentariosRevisionTallerListNewComentariosRevisionTaller)) {
                    Usuarios oldIdUsuarioComentaristaOfComentariosRevisionTallerListNewComentariosRevisionTaller = comentariosRevisionTallerListNewComentariosRevisionTaller.getIdUsuarioComentarista();
                    comentariosRevisionTallerListNewComentariosRevisionTaller.setIdUsuarioComentarista(usuarios);
                    comentariosRevisionTallerListNewComentariosRevisionTaller = em.merge(comentariosRevisionTallerListNewComentariosRevisionTaller);
                    if (oldIdUsuarioComentaristaOfComentariosRevisionTallerListNewComentariosRevisionTaller != null && !oldIdUsuarioComentaristaOfComentariosRevisionTallerListNewComentariosRevisionTaller.equals(usuarios)) {
                        oldIdUsuarioComentaristaOfComentariosRevisionTallerListNewComentariosRevisionTaller.getComentariosRevisionTallerList().remove(comentariosRevisionTallerListNewComentariosRevisionTaller);
                        oldIdUsuarioComentaristaOfComentariosRevisionTallerListNewComentariosRevisionTaller = em.merge(oldIdUsuarioComentaristaOfComentariosRevisionTallerListNewComentariosRevisionTaller);
                    }
                }
            }
            for (Notificaciones notificacionesListNewNotificaciones : notificacionesListNew) {
                if (!notificacionesListOld.contains(notificacionesListNewNotificaciones)) {
                    Usuarios oldIdUsuarioDestinatarioOfNotificacionesListNewNotificaciones = notificacionesListNewNotificaciones.getIdUsuarioDestinatario();
                    notificacionesListNewNotificaciones.setIdUsuarioDestinatario(usuarios);
                    notificacionesListNewNotificaciones = em.merge(notificacionesListNewNotificaciones);
                    if (oldIdUsuarioDestinatarioOfNotificacionesListNewNotificaciones != null && !oldIdUsuarioDestinatarioOfNotificacionesListNewNotificaciones.equals(usuarios)) {
                        oldIdUsuarioDestinatarioOfNotificacionesListNewNotificaciones.getNotificacionesList().remove(notificacionesListNewNotificaciones);
                        oldIdUsuarioDestinatarioOfNotificacionesListNewNotificaciones = em.merge(oldIdUsuarioDestinatarioOfNotificacionesListNewNotificaciones);
                    }
                }
            }
            for (Evidencias evidenciasListNewEvidencias : evidenciasListNew) {
                if (!evidenciasListOld.contains(evidenciasListNewEvidencias)) {
                    Usuarios oldIdUsuarioSubioOfEvidenciasListNewEvidencias = evidenciasListNewEvidencias.getIdUsuarioSubio();
                    evidenciasListNewEvidencias.setIdUsuarioSubio(usuarios);
                    evidenciasListNewEvidencias = em.merge(evidenciasListNewEvidencias);
                    if (oldIdUsuarioSubioOfEvidenciasListNewEvidencias != null && !oldIdUsuarioSubioOfEvidenciasListNewEvidencias.equals(usuarios)) {
                        oldIdUsuarioSubioOfEvidenciasListNewEvidencias.getEvidenciasList().remove(evidenciasListNewEvidencias);
                        oldIdUsuarioSubioOfEvidenciasListNewEvidencias = em.merge(oldIdUsuarioSubioOfEvidenciasListNewEvidencias);
                    }
                }
            }
            for (Talleres talleresListNewTalleres : talleresListNew) {
                if (!talleresListOld.contains(talleresListNewTalleres)) {
                    Usuarios oldIdUsuarioProponenteOfTalleresListNewTalleres = talleresListNewTalleres.getIdUsuarioProponente();
                    talleresListNewTalleres.setIdUsuarioProponente(usuarios);
                    talleresListNewTalleres = em.merge(talleresListNewTalleres);
                    if (oldIdUsuarioProponenteOfTalleresListNewTalleres != null && !oldIdUsuarioProponenteOfTalleresListNewTalleres.equals(usuarios)) {
                        oldIdUsuarioProponenteOfTalleresListNewTalleres.getTalleresList().remove(talleresListNewTalleres);
                        oldIdUsuarioProponenteOfTalleresListNewTalleres = em.merge(oldIdUsuarioProponenteOfTalleresListNewTalleres);
                    }
                }
            }
            for (Eventos eventosListOldEventos : eventosListOld) {
                if (!eventosListNew.contains(eventosListOldEventos)) {
                    eventosListOldEventos.setIdDocenteResponsable(null);
                    eventosListOldEventos = em.merge(eventosListOldEventos);
                }
            }
            for (Eventos eventosListNewEventos : eventosListNew) {
                if (!eventosListOld.contains(eventosListNewEventos)) {
                    Usuarios oldIdDocenteResponsableOfEventosListNewEventos = eventosListNewEventos.getIdDocenteResponsable();
                    eventosListNewEventos.setIdDocenteResponsable(usuarios);
                    eventosListNewEventos = em.merge(eventosListNewEventos);
                    if (oldIdDocenteResponsableOfEventosListNewEventos != null && !oldIdDocenteResponsableOfEventosListNewEventos.equals(usuarios)) {
                        oldIdDocenteResponsableOfEventosListNewEventos.getEventosList().remove(eventosListNewEventos);
                        oldIdDocenteResponsableOfEventosListNewEventos = em.merge(oldIdDocenteResponsableOfEventosListNewEventos);
                    }
                }
            }
            for (BitacorasEventos bitacorasEventosListNewBitacorasEventos : bitacorasEventosListNew) {
                if (!bitacorasEventosListOld.contains(bitacorasEventosListNewBitacorasEventos)) {
                    Usuarios oldIdUsuarioRegistraOfBitacorasEventosListNewBitacorasEventos = bitacorasEventosListNewBitacorasEventos.getIdUsuarioRegistra();
                    bitacorasEventosListNewBitacorasEventos.setIdUsuarioRegistra(usuarios);
                    bitacorasEventosListNewBitacorasEventos = em.merge(bitacorasEventosListNewBitacorasEventos);
                    if (oldIdUsuarioRegistraOfBitacorasEventosListNewBitacorasEventos != null && !oldIdUsuarioRegistraOfBitacorasEventosListNewBitacorasEventos.equals(usuarios)) {
                        oldIdUsuarioRegistraOfBitacorasEventosListNewBitacorasEventos.getBitacorasEventosList().remove(bitacorasEventosListNewBitacorasEventos);
                        oldIdUsuarioRegistraOfBitacorasEventosListNewBitacorasEventos = em.merge(oldIdUsuarioRegistraOfBitacorasEventosListNewBitacorasEventos);
                    }
                }
            }
            for (Convocatorias convocatoriasListNewConvocatorias : convocatoriasListNew) {
                if (!convocatoriasListOld.contains(convocatoriasListNewConvocatorias)) {
                    Usuarios oldIdUsuarioPublicaOfConvocatoriasListNewConvocatorias = convocatoriasListNewConvocatorias.getIdUsuarioPublica();
                    convocatoriasListNewConvocatorias.setIdUsuarioPublica(usuarios);
                    convocatoriasListNewConvocatorias = em.merge(convocatoriasListNewConvocatorias);
                    if (oldIdUsuarioPublicaOfConvocatoriasListNewConvocatorias != null && !oldIdUsuarioPublicaOfConvocatoriasListNewConvocatorias.equals(usuarios)) {
                        oldIdUsuarioPublicaOfConvocatoriasListNewConvocatorias.getConvocatoriasList().remove(convocatoriasListNewConvocatorias);
                        oldIdUsuarioPublicaOfConvocatoriasListNewConvocatorias = em.merge(oldIdUsuarioPublicaOfConvocatoriasListNewConvocatorias);
                    }
                }
            }
            for (EventoParticipantesTalleres eventoParticipantesTalleresListNewEventoParticipantesTalleres : eventoParticipantesTalleresListNew) {
                if (!eventoParticipantesTalleresListOld.contains(eventoParticipantesTalleresListNewEventoParticipantesTalleres)) {
                    Usuarios oldIdTalleristaOfEventoParticipantesTalleresListNewEventoParticipantesTalleres = eventoParticipantesTalleresListNewEventoParticipantesTalleres.getIdTallerista();
                    eventoParticipantesTalleresListNewEventoParticipantesTalleres.setIdTallerista(usuarios);
                    eventoParticipantesTalleresListNewEventoParticipantesTalleres = em.merge(eventoParticipantesTalleresListNewEventoParticipantesTalleres);
                    if (oldIdTalleristaOfEventoParticipantesTalleresListNewEventoParticipantesTalleres != null && !oldIdTalleristaOfEventoParticipantesTalleresListNewEventoParticipantesTalleres.equals(usuarios)) {
                        oldIdTalleristaOfEventoParticipantesTalleresListNewEventoParticipantesTalleres.getEventoParticipantesTalleresList().remove(eventoParticipantesTalleresListNewEventoParticipantesTalleres);
                        oldIdTalleristaOfEventoParticipantesTalleresListNewEventoParticipantesTalleres = em.merge(oldIdTalleristaOfEventoParticipantesTalleresListNewEventoParticipantesTalleres);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuarios.getIdUsuario();
                if (findUsuarios(id) == null) {
                    throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.");
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
            Usuarios usuarios;
            try {
                usuarios = em.getReference(Usuarios.class, id);
                usuarios.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuarios with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<ComentariosRevisionTaller> comentariosRevisionTallerListOrphanCheck = usuarios.getComentariosRevisionTallerList();
            for (ComentariosRevisionTaller comentariosRevisionTallerListOrphanCheckComentariosRevisionTaller : comentariosRevisionTallerListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the ComentariosRevisionTaller " + comentariosRevisionTallerListOrphanCheckComentariosRevisionTaller + " in its comentariosRevisionTallerList field has a non-nullable idUsuarioComentarista field.");
            }
            List<Notificaciones> notificacionesListOrphanCheck = usuarios.getNotificacionesList();
            for (Notificaciones notificacionesListOrphanCheckNotificaciones : notificacionesListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Notificaciones " + notificacionesListOrphanCheckNotificaciones + " in its notificacionesList field has a non-nullable idUsuarioDestinatario field.");
            }
            List<Evidencias> evidenciasListOrphanCheck = usuarios.getEvidenciasList();
            for (Evidencias evidenciasListOrphanCheckEvidencias : evidenciasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Evidencias " + evidenciasListOrphanCheckEvidencias + " in its evidenciasList field has a non-nullable idUsuarioSubio field.");
            }
            List<Talleres> talleresListOrphanCheck = usuarios.getTalleresList();
            for (Talleres talleresListOrphanCheckTalleres : talleresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Talleres " + talleresListOrphanCheckTalleres + " in its talleresList field has a non-nullable idUsuarioProponente field.");
            }
            List<BitacorasEventos> bitacorasEventosListOrphanCheck = usuarios.getBitacorasEventosList();
            for (BitacorasEventos bitacorasEventosListOrphanCheckBitacorasEventos : bitacorasEventosListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the BitacorasEventos " + bitacorasEventosListOrphanCheckBitacorasEventos + " in its bitacorasEventosList field has a non-nullable idUsuarioRegistra field.");
            }
            List<Convocatorias> convocatoriasListOrphanCheck = usuarios.getConvocatoriasList();
            for (Convocatorias convocatoriasListOrphanCheckConvocatorias : convocatoriasListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the Convocatorias " + convocatoriasListOrphanCheckConvocatorias + " in its convocatoriasList field has a non-nullable idUsuarioPublica field.");
            }
            List<EventoParticipantesTalleres> eventoParticipantesTalleresListOrphanCheck = usuarios.getEventoParticipantesTalleresList();
            for (EventoParticipantesTalleres eventoParticipantesTalleresListOrphanCheckEventoParticipantesTalleres : eventoParticipantesTalleresListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Usuarios (" + usuarios + ") cannot be destroyed since the EventoParticipantesTalleres " + eventoParticipantesTalleresListOrphanCheckEventoParticipantesTalleres + " in its eventoParticipantesTalleresList field has a non-nullable idTallerista field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            List<Eventos> eventosList = usuarios.getEventosList();
            for (Eventos eventosListEventos : eventosList) {
                eventosListEventos.setIdDocenteResponsable(null);
                eventosListEventos = em.merge(eventosListEventos);
            }
            em.remove(usuarios);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuarios> findUsuariosEntities() {
        return findUsuariosEntities(true, -1, -1);
    }

    public List<Usuarios> findUsuariosEntities(int maxResults, int firstResult) {
        return findUsuariosEntities(false, maxResults, firstResult);
    }

    private List<Usuarios> findUsuariosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuarios.class));
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

    public Usuarios findUsuarios(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuarios.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuariosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuarios> rt = cq.from(Usuarios.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
