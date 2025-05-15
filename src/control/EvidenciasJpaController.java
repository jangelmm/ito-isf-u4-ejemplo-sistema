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
import modelo.Eventos;
import modelo.Evidencias;
import modelo.Talleres;
import modelo.Usuarios;

/**
 *
 * @author jesus
 */
public class EvidenciasJpaController implements Serializable {

    public EvidenciasJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Evidencias evidencias) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Eventos idEvento = evidencias.getIdEvento();
            if (idEvento != null) {
                idEvento = em.getReference(idEvento.getClass(), idEvento.getIdEvento());
                evidencias.setIdEvento(idEvento);
            }
            Talleres idTallerAsociado = evidencias.getIdTallerAsociado();
            if (idTallerAsociado != null) {
                idTallerAsociado = em.getReference(idTallerAsociado.getClass(), idTallerAsociado.getIdTaller());
                evidencias.setIdTallerAsociado(idTallerAsociado);
            }
            Usuarios idUsuarioSubio = evidencias.getIdUsuarioSubio();
            if (idUsuarioSubio != null) {
                idUsuarioSubio = em.getReference(idUsuarioSubio.getClass(), idUsuarioSubio.getIdUsuario());
                evidencias.setIdUsuarioSubio(idUsuarioSubio);
            }
            em.persist(evidencias);
            if (idEvento != null) {
                idEvento.getEvidenciasList().add(evidencias);
                idEvento = em.merge(idEvento);
            }
            if (idTallerAsociado != null) {
                idTallerAsociado.getEvidenciasList().add(evidencias);
                idTallerAsociado = em.merge(idTallerAsociado);
            }
            if (idUsuarioSubio != null) {
                idUsuarioSubio.getEvidenciasList().add(evidencias);
                idUsuarioSubio = em.merge(idUsuarioSubio);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Evidencias evidencias) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evidencias persistentEvidencias = em.find(Evidencias.class, evidencias.getIdEvidencia());
            Eventos idEventoOld = persistentEvidencias.getIdEvento();
            Eventos idEventoNew = evidencias.getIdEvento();
            Talleres idTallerAsociadoOld = persistentEvidencias.getIdTallerAsociado();
            Talleres idTallerAsociadoNew = evidencias.getIdTallerAsociado();
            Usuarios idUsuarioSubioOld = persistentEvidencias.getIdUsuarioSubio();
            Usuarios idUsuarioSubioNew = evidencias.getIdUsuarioSubio();
            if (idEventoNew != null) {
                idEventoNew = em.getReference(idEventoNew.getClass(), idEventoNew.getIdEvento());
                evidencias.setIdEvento(idEventoNew);
            }
            if (idTallerAsociadoNew != null) {
                idTallerAsociadoNew = em.getReference(idTallerAsociadoNew.getClass(), idTallerAsociadoNew.getIdTaller());
                evidencias.setIdTallerAsociado(idTallerAsociadoNew);
            }
            if (idUsuarioSubioNew != null) {
                idUsuarioSubioNew = em.getReference(idUsuarioSubioNew.getClass(), idUsuarioSubioNew.getIdUsuario());
                evidencias.setIdUsuarioSubio(idUsuarioSubioNew);
            }
            evidencias = em.merge(evidencias);
            if (idEventoOld != null && !idEventoOld.equals(idEventoNew)) {
                idEventoOld.getEvidenciasList().remove(evidencias);
                idEventoOld = em.merge(idEventoOld);
            }
            if (idEventoNew != null && !idEventoNew.equals(idEventoOld)) {
                idEventoNew.getEvidenciasList().add(evidencias);
                idEventoNew = em.merge(idEventoNew);
            }
            if (idTallerAsociadoOld != null && !idTallerAsociadoOld.equals(idTallerAsociadoNew)) {
                idTallerAsociadoOld.getEvidenciasList().remove(evidencias);
                idTallerAsociadoOld = em.merge(idTallerAsociadoOld);
            }
            if (idTallerAsociadoNew != null && !idTallerAsociadoNew.equals(idTallerAsociadoOld)) {
                idTallerAsociadoNew.getEvidenciasList().add(evidencias);
                idTallerAsociadoNew = em.merge(idTallerAsociadoNew);
            }
            if (idUsuarioSubioOld != null && !idUsuarioSubioOld.equals(idUsuarioSubioNew)) {
                idUsuarioSubioOld.getEvidenciasList().remove(evidencias);
                idUsuarioSubioOld = em.merge(idUsuarioSubioOld);
            }
            if (idUsuarioSubioNew != null && !idUsuarioSubioNew.equals(idUsuarioSubioOld)) {
                idUsuarioSubioNew.getEvidenciasList().add(evidencias);
                idUsuarioSubioNew = em.merge(idUsuarioSubioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = evidencias.getIdEvidencia();
                if (findEvidencias(id) == null) {
                    throw new NonexistentEntityException("The evidencias with id " + id + " no longer exists.");
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
            Evidencias evidencias;
            try {
                evidencias = em.getReference(Evidencias.class, id);
                evidencias.getIdEvidencia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The evidencias with id " + id + " no longer exists.", enfe);
            }
            Eventos idEvento = evidencias.getIdEvento();
            if (idEvento != null) {
                idEvento.getEvidenciasList().remove(evidencias);
                idEvento = em.merge(idEvento);
            }
            Talleres idTallerAsociado = evidencias.getIdTallerAsociado();
            if (idTallerAsociado != null) {
                idTallerAsociado.getEvidenciasList().remove(evidencias);
                idTallerAsociado = em.merge(idTallerAsociado);
            }
            Usuarios idUsuarioSubio = evidencias.getIdUsuarioSubio();
            if (idUsuarioSubio != null) {
                idUsuarioSubio.getEvidenciasList().remove(evidencias);
                idUsuarioSubio = em.merge(idUsuarioSubio);
            }
            em.remove(evidencias);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Evidencias> findEvidenciasEntities() {
        return findEvidenciasEntities(true, -1, -1);
    }

    public List<Evidencias> findEvidenciasEntities(int maxResults, int firstResult) {
        return findEvidenciasEntities(false, maxResults, firstResult);
    }

    private List<Evidencias> findEvidenciasEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Evidencias.class));
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

    public Evidencias findEvidencias(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Evidencias.class, id);
        } finally {
            em.close();
        }
    }

    public int getEvidenciasCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Evidencias> rt = cq.from(Evidencias.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
