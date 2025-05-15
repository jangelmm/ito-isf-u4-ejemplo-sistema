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
import modelo.BitacorasEventos;
import modelo.Eventos;
import modelo.Usuarios;

/**
 *
 * @author jesus
 */
public class BitacorasEventosJpaController implements Serializable {

    public BitacorasEventosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(BitacorasEventos bitacorasEventos) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Eventos idEvento = bitacorasEventos.getIdEvento();
            if (idEvento != null) {
                idEvento = em.getReference(idEvento.getClass(), idEvento.getIdEvento());
                bitacorasEventos.setIdEvento(idEvento);
            }
            Usuarios idUsuarioRegistra = bitacorasEventos.getIdUsuarioRegistra();
            if (idUsuarioRegistra != null) {
                idUsuarioRegistra = em.getReference(idUsuarioRegistra.getClass(), idUsuarioRegistra.getIdUsuario());
                bitacorasEventos.setIdUsuarioRegistra(idUsuarioRegistra);
            }
            em.persist(bitacorasEventos);
            if (idEvento != null) {
                idEvento.getBitacorasEventosList().add(bitacorasEventos);
                idEvento = em.merge(idEvento);
            }
            if (idUsuarioRegistra != null) {
                idUsuarioRegistra.getBitacorasEventosList().add(bitacorasEventos);
                idUsuarioRegistra = em.merge(idUsuarioRegistra);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(BitacorasEventos bitacorasEventos) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            BitacorasEventos persistentBitacorasEventos = em.find(BitacorasEventos.class, bitacorasEventos.getIdBitacora());
            Eventos idEventoOld = persistentBitacorasEventos.getIdEvento();
            Eventos idEventoNew = bitacorasEventos.getIdEvento();
            Usuarios idUsuarioRegistraOld = persistentBitacorasEventos.getIdUsuarioRegistra();
            Usuarios idUsuarioRegistraNew = bitacorasEventos.getIdUsuarioRegistra();
            if (idEventoNew != null) {
                idEventoNew = em.getReference(idEventoNew.getClass(), idEventoNew.getIdEvento());
                bitacorasEventos.setIdEvento(idEventoNew);
            }
            if (idUsuarioRegistraNew != null) {
                idUsuarioRegistraNew = em.getReference(idUsuarioRegistraNew.getClass(), idUsuarioRegistraNew.getIdUsuario());
                bitacorasEventos.setIdUsuarioRegistra(idUsuarioRegistraNew);
            }
            bitacorasEventos = em.merge(bitacorasEventos);
            if (idEventoOld != null && !idEventoOld.equals(idEventoNew)) {
                idEventoOld.getBitacorasEventosList().remove(bitacorasEventos);
                idEventoOld = em.merge(idEventoOld);
            }
            if (idEventoNew != null && !idEventoNew.equals(idEventoOld)) {
                idEventoNew.getBitacorasEventosList().add(bitacorasEventos);
                idEventoNew = em.merge(idEventoNew);
            }
            if (idUsuarioRegistraOld != null && !idUsuarioRegistraOld.equals(idUsuarioRegistraNew)) {
                idUsuarioRegistraOld.getBitacorasEventosList().remove(bitacorasEventos);
                idUsuarioRegistraOld = em.merge(idUsuarioRegistraOld);
            }
            if (idUsuarioRegistraNew != null && !idUsuarioRegistraNew.equals(idUsuarioRegistraOld)) {
                idUsuarioRegistraNew.getBitacorasEventosList().add(bitacorasEventos);
                idUsuarioRegistraNew = em.merge(idUsuarioRegistraNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = bitacorasEventos.getIdBitacora();
                if (findBitacorasEventos(id) == null) {
                    throw new NonexistentEntityException("The bitacorasEventos with id " + id + " no longer exists.");
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
            BitacorasEventos bitacorasEventos;
            try {
                bitacorasEventos = em.getReference(BitacorasEventos.class, id);
                bitacorasEventos.getIdBitacora();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The bitacorasEventos with id " + id + " no longer exists.", enfe);
            }
            Eventos idEvento = bitacorasEventos.getIdEvento();
            if (idEvento != null) {
                idEvento.getBitacorasEventosList().remove(bitacorasEventos);
                idEvento = em.merge(idEvento);
            }
            Usuarios idUsuarioRegistra = bitacorasEventos.getIdUsuarioRegistra();
            if (idUsuarioRegistra != null) {
                idUsuarioRegistra.getBitacorasEventosList().remove(bitacorasEventos);
                idUsuarioRegistra = em.merge(idUsuarioRegistra);
            }
            em.remove(bitacorasEventos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<BitacorasEventos> findBitacorasEventosEntities() {
        return findBitacorasEventosEntities(true, -1, -1);
    }

    public List<BitacorasEventos> findBitacorasEventosEntities(int maxResults, int firstResult) {
        return findBitacorasEventosEntities(false, maxResults, firstResult);
    }

    private List<BitacorasEventos> findBitacorasEventosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(BitacorasEventos.class));
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

    public BitacorasEventos findBitacorasEventos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(BitacorasEventos.class, id);
        } finally {
            em.close();
        }
    }

    public int getBitacorasEventosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<BitacorasEventos> rt = cq.from(BitacorasEventos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
