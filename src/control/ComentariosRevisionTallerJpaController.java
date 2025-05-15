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
import modelo.ComentariosRevisionTaller;
import modelo.Talleres;
import modelo.Usuarios;

/**
 *
 * @author jesus
 */
public class ComentariosRevisionTallerJpaController implements Serializable {

    public ComentariosRevisionTallerJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ComentariosRevisionTaller comentariosRevisionTaller) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Talleres idTaller = comentariosRevisionTaller.getIdTaller();
            if (idTaller != null) {
                idTaller = em.getReference(idTaller.getClass(), idTaller.getIdTaller());
                comentariosRevisionTaller.setIdTaller(idTaller);
            }
            Usuarios idUsuarioComentarista = comentariosRevisionTaller.getIdUsuarioComentarista();
            if (idUsuarioComentarista != null) {
                idUsuarioComentarista = em.getReference(idUsuarioComentarista.getClass(), idUsuarioComentarista.getIdUsuario());
                comentariosRevisionTaller.setIdUsuarioComentarista(idUsuarioComentarista);
            }
            em.persist(comentariosRevisionTaller);
            if (idTaller != null) {
                idTaller.getComentariosRevisionTallerList().add(comentariosRevisionTaller);
                idTaller = em.merge(idTaller);
            }
            if (idUsuarioComentarista != null) {
                idUsuarioComentarista.getComentariosRevisionTallerList().add(comentariosRevisionTaller);
                idUsuarioComentarista = em.merge(idUsuarioComentarista);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ComentariosRevisionTaller comentariosRevisionTaller) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ComentariosRevisionTaller persistentComentariosRevisionTaller = em.find(ComentariosRevisionTaller.class, comentariosRevisionTaller.getIdComentario());
            Talleres idTallerOld = persistentComentariosRevisionTaller.getIdTaller();
            Talleres idTallerNew = comentariosRevisionTaller.getIdTaller();
            Usuarios idUsuarioComentaristaOld = persistentComentariosRevisionTaller.getIdUsuarioComentarista();
            Usuarios idUsuarioComentaristaNew = comentariosRevisionTaller.getIdUsuarioComentarista();
            if (idTallerNew != null) {
                idTallerNew = em.getReference(idTallerNew.getClass(), idTallerNew.getIdTaller());
                comentariosRevisionTaller.setIdTaller(idTallerNew);
            }
            if (idUsuarioComentaristaNew != null) {
                idUsuarioComentaristaNew = em.getReference(idUsuarioComentaristaNew.getClass(), idUsuarioComentaristaNew.getIdUsuario());
                comentariosRevisionTaller.setIdUsuarioComentarista(idUsuarioComentaristaNew);
            }
            comentariosRevisionTaller = em.merge(comentariosRevisionTaller);
            if (idTallerOld != null && !idTallerOld.equals(idTallerNew)) {
                idTallerOld.getComentariosRevisionTallerList().remove(comentariosRevisionTaller);
                idTallerOld = em.merge(idTallerOld);
            }
            if (idTallerNew != null && !idTallerNew.equals(idTallerOld)) {
                idTallerNew.getComentariosRevisionTallerList().add(comentariosRevisionTaller);
                idTallerNew = em.merge(idTallerNew);
            }
            if (idUsuarioComentaristaOld != null && !idUsuarioComentaristaOld.equals(idUsuarioComentaristaNew)) {
                idUsuarioComentaristaOld.getComentariosRevisionTallerList().remove(comentariosRevisionTaller);
                idUsuarioComentaristaOld = em.merge(idUsuarioComentaristaOld);
            }
            if (idUsuarioComentaristaNew != null && !idUsuarioComentaristaNew.equals(idUsuarioComentaristaOld)) {
                idUsuarioComentaristaNew.getComentariosRevisionTallerList().add(comentariosRevisionTaller);
                idUsuarioComentaristaNew = em.merge(idUsuarioComentaristaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comentariosRevisionTaller.getIdComentario();
                if (findComentariosRevisionTaller(id) == null) {
                    throw new NonexistentEntityException("The comentariosRevisionTaller with id " + id + " no longer exists.");
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
            ComentariosRevisionTaller comentariosRevisionTaller;
            try {
                comentariosRevisionTaller = em.getReference(ComentariosRevisionTaller.class, id);
                comentariosRevisionTaller.getIdComentario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comentariosRevisionTaller with id " + id + " no longer exists.", enfe);
            }
            Talleres idTaller = comentariosRevisionTaller.getIdTaller();
            if (idTaller != null) {
                idTaller.getComentariosRevisionTallerList().remove(comentariosRevisionTaller);
                idTaller = em.merge(idTaller);
            }
            Usuarios idUsuarioComentarista = comentariosRevisionTaller.getIdUsuarioComentarista();
            if (idUsuarioComentarista != null) {
                idUsuarioComentarista.getComentariosRevisionTallerList().remove(comentariosRevisionTaller);
                idUsuarioComentarista = em.merge(idUsuarioComentarista);
            }
            em.remove(comentariosRevisionTaller);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ComentariosRevisionTaller> findComentariosRevisionTallerEntities() {
        return findComentariosRevisionTallerEntities(true, -1, -1);
    }

    public List<ComentariosRevisionTaller> findComentariosRevisionTallerEntities(int maxResults, int firstResult) {
        return findComentariosRevisionTallerEntities(false, maxResults, firstResult);
    }

    private List<ComentariosRevisionTaller> findComentariosRevisionTallerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ComentariosRevisionTaller.class));
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

    public ComentariosRevisionTaller findComentariosRevisionTaller(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ComentariosRevisionTaller.class, id);
        } finally {
            em.close();
        }
    }

    public int getComentariosRevisionTallerCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ComentariosRevisionTaller> rt = cq.from(ComentariosRevisionTaller.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
