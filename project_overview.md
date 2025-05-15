# Estructura del proyecto

```
AcademicPlus
├── lib
│   ├── CopyLibs
│   │   └── org-netbeans-modules-java-j2seproject-copylibstask.jar
│   ├── eclipselink
│   │   ├── jakarta.persistence-2.2.3-doc.zip
│   │   ├── jakarta.persistence-2.2.3.jar
│   │   ├── org.eclipse.persistence.antlr-2.7.12.jar
│   │   ├── org.eclipse.persistence.asm-9.4.0.jar
│   │   ├── org.eclipse.persistence.core-2.7.12.jar
│   │   ├── org.eclipse.persistence.jpa-2.7.12.jar
│   │   ├── org.eclipse.persistence.jpa.jpql-2.7.12.jar
│   │   └── org.eclipse.persistence.moxy-2.7.12.jar
│   ├── eclipselinkmodelgen
│   │   └── org.eclipse.persistence.jpa.modelgen.processor-2.7.12.jar
│   └── nblibraries.properties
├── nbproject
│   ├── private
│   │   └── private.properties
│   ├── build-impl.xml
│   ├── genfiles.properties
│   ├── project.properties
│   └── project.xml
├── src
│   ├── META-INF
│   │   └── persistence.xml
│   ├── control
│   │   ├── exceptions
│   │   │   ├── IllegalOrphanException.java
│   │   │   ├── NonexistentEntityException.java
│   │   │   └── PreexistingEntityException.java
│   │   ├── BitacorasEventosJpaController.java
│   │   ├── ComentariosRevisionTallerJpaController.java
│   │   ├── ConvocatoriasJpaController.java
│   │   ├── EventoParticipantesTalleresJpaController.java
│   │   ├── EventosJpaController.java
│   │   ├── EvidenciasJpaController.java
│   │   ├── NotificacionesJpaController.java
│   │   ├── TalleresJpaController.java
│   │   └── UsuariosJpaController.java
│   ├── modelo
│   │   ├── BitacorasEventos.java
│   │   ├── ComentariosRevisionTaller.java
│   │   ├── Convocatorias.java
│   │   ├── EventoParticipantesTalleres.java
│   │   ├── Eventos.java
│   │   ├── Evidencias.java
│   │   ├── Notificaciones.java
│   │   ├── Talleres.java
│   │   └── Usuarios.java
│   └── vista
├── build.xml
├── manifest.mf
├── script-mysql-academicplus.sql
└── script.py
```

## `script.py`

```python
import os
import argparse

# Directorios a ignorar (incluye caches de Python)
IGNORE_DIRS = {'.web', 'venv', '__pycache__'}

# Extensiones de archivo permitidas
ALLOWED_EXTS = {'.py', '.java', '.cpp', '.c'}

# Archivos específicos a incluir siempre (aunque empiecen con '.'
# o tengan extensión fuera de ALLOWED_EXTS)
INCLUDED_FILES = {'requirements.txt', 'rxconfig.py', '.gitignore'}

# Prefijos para el tree
TREE_PREFIXES = {
    'branch': '├── ',
    'last':   '└── ',
    'indent': '    ',
    'pipe':   '│   '
}


def build_tree(root_path):
    """
    Genera una lista de líneas representando la estructura de directorios,
    ignorando IGNORE_DIRS, pero incluyendo archivos en INCLUDED_FILES.
    """
    tree_lines = []

    def _tree(dir_path, prefix=''):
        entries = sorted(os.listdir(dir_path))
        # Filtrar: ignora los directorios deseados; oculta dot-files salvo INCLUDED_FILES
        entries = [
            e for e in entries
            if e not in IGNORE_DIRS
               and (not e.startswith('.') or e in INCLUDED_FILES)
        ]

        dirs = [e for e in entries if os.path.isdir(os.path.join(dir_path, e))]
        files = [e for e in entries if os.path.isfile(os.path.join(dir_path, e))]
        total = len(dirs) + len(files)

        for idx, name in enumerate(dirs + files):
            path = os.path.join(dir_path, name)
            connector = TREE_PREFIXES['last'] if idx == total - 1 else TREE_PREFIXES['branch']
            tree_lines.append(f"{prefix}{connector}{name}")
            if os.path.isdir(path):
                extension = TREE_PREFIXES['indent'] if idx == total - 1 else TREE_PREFIXES['pipe']
                _tree(path, prefix + extension)

    tree_lines.append(os.path.basename(root_path) or root_path)
    _tree(root_path)
    return tree_lines


def collect_files(root_path):
    """
    Recorre el árbol e incluye:
    - Archivos con extensiones en ALLOWED_EXTS
    - Archivos listados en INCLUDED_FILES (en cualquier carpeta)
    """
    paths = []
    for dirpath, dirnames, filenames in os.walk(root_path):
        # Excluir carpetas no deseadas
        dirnames[:] = [d for d in dirnames if d not in IGNORE_DIRS]

        for fname in sorted(filenames):
            rel = os.path.relpath(os.path.join(dirpath, fname), root_path)
            ext = os.path.splitext(fname)[1]
            if ext in ALLOWED_EXTS or fname in INCLUDED_FILES:
                paths.append(os.path.join(dirpath, fname))

    return paths


def ext_to_lang(ext):
    """Mapea extensión de archivo a lenguaje para Markdown."""
    return {
        '.py': 'python',
        '.java': 'java',
        '.cpp': 'cpp',
        '.c': 'c',
        '.txt': 'text',
        '': 'text'   # Para archivos como .gitignore
    }.get(ext, 'text')


def main():
    parser = argparse.ArgumentParser(
        description="Genera un Markdown con la estructura tipo tree y el código fuente.")
    parser.add_argument(
        'output', nargs='?', default='project_overview.md',
        help='Nombre del archivo Markdown de salida. (default: project_overview.md)')
    args = parser.parse_args()

    root = os.getcwd()
    tree_lines = build_tree(root)
    code_files = collect_files(root)

    with open(args.output, 'w', encoding='utf-8') as md:
        # Título
        md.write("# Estructura del proyecto\n\n")

        # Árbol de directorios
        md.write("```\n")
        md.write("\n".join(tree_lines))
        md.write("\n```\n\n")

        # Contenido de cada archivo
        for path in code_files:
            rel_path = os.path.relpath(path, root)
            ext = os.path.splitext(path)[1]
            lang = ext_to_lang(ext)
            md.write(f"## `{rel_path}`\n\n")
            md.write(f"```{lang}\n")
            try:
                with open(path, 'r', encoding='utf-8') as f:
                    md.write(f.read())
            except Exception as e:
                md.write(f"# Error al leer el archivo: {e}\n")
            md.write("```\n\n")

    print(f"Archivo Markdown generado: {args.output}")


if __name__ == '__main__':
    main()
```

## `src\control\BitacorasEventosJpaController.java`

```java
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
```

## `src\control\ComentariosRevisionTallerJpaController.java`

```java
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
```

## `src\control\ConvocatoriasJpaController.java`

```java
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
```

## `src\control\EventoParticipantesTalleresJpaController.java`

```java
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
```

## `src\control\EventosJpaController.java`

```java
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
```

## `src\control\EvidenciasJpaController.java`

```java
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
```

## `src\control\NotificacionesJpaController.java`

```java
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
```

## `src\control\TalleresJpaController.java`

```java
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
```

## `src\control\UsuariosJpaController.java`

```java
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
```

## `src\control\exceptions\IllegalOrphanException.java`

```java
package control.exceptions;

import java.util.ArrayList;
import java.util.List;

public class IllegalOrphanException extends Exception {
    private List<String> messages;
    public IllegalOrphanException(List<String> messages) {
        super((messages != null && messages.size() > 0 ? messages.get(0) : null));
        if (messages == null) {
            this.messages = new ArrayList<String>();
        }
        else {
            this.messages = messages;
        }
    }
    public List<String> getMessages() {
        return messages;
    }
}
```

## `src\control\exceptions\NonexistentEntityException.java`

```java
package control.exceptions;

public class NonexistentEntityException extends Exception {
    public NonexistentEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public NonexistentEntityException(String message) {
        super(message);
    }
}
```

## `src\control\exceptions\PreexistingEntityException.java`

```java
package control.exceptions;

public class PreexistingEntityException extends Exception {
    public PreexistingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
    public PreexistingEntityException(String message) {
        super(message);
    }
}
```

## `src\modelo\BitacorasEventos.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "bitacoras_eventos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BitacorasEventos.findAll", query = "SELECT b FROM BitacorasEventos b"),
    @NamedQuery(name = "BitacorasEventos.findByIdBitacora", query = "SELECT b FROM BitacorasEventos b WHERE b.idBitacora = :idBitacora"),
    @NamedQuery(name = "BitacorasEventos.findByFechaHoraEntrada", query = "SELECT b FROM BitacorasEventos b WHERE b.fechaHoraEntrada = :fechaHoraEntrada")})
public class BitacorasEventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_bitacora")
    private Integer idBitacora;
    @Basic(optional = false)
    @Lob
    @Column(name = "observacion")
    private String observacion;
    @Column(name = "fecha_hora_entrada")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaHoraEntrada;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    @ManyToOne(optional = false)
    private Eventos idEvento;
    @JoinColumn(name = "id_usuario_registra", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioRegistra;

    public BitacorasEventos() {
    }

    public BitacorasEventos(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public BitacorasEventos(Integer idBitacora, String observacion) {
        this.idBitacora = idBitacora;
        this.observacion = observacion;
    }

    public Integer getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(Integer idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Date getFechaHoraEntrada() {
        return fechaHoraEntrada;
    }

    public void setFechaHoraEntrada(Date fechaHoraEntrada) {
        this.fechaHoraEntrada = fechaHoraEntrada;
    }

    public Eventos getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Eventos idEvento) {
        this.idEvento = idEvento;
    }

    public Usuarios getIdUsuarioRegistra() {
        return idUsuarioRegistra;
    }

    public void setIdUsuarioRegistra(Usuarios idUsuarioRegistra) {
        this.idUsuarioRegistra = idUsuarioRegistra;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idBitacora != null ? idBitacora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BitacorasEventos)) {
            return false;
        }
        BitacorasEventos other = (BitacorasEventos) object;
        if ((this.idBitacora == null && other.idBitacora != null) || (this.idBitacora != null && !this.idBitacora.equals(other.idBitacora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.BitacorasEventos[ idBitacora=" + idBitacora + " ]";
    }
    
}
```

## `src\modelo\ComentariosRevisionTaller.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "comentarios_revision_taller")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComentariosRevisionTaller.findAll", query = "SELECT c FROM ComentariosRevisionTaller c"),
    @NamedQuery(name = "ComentariosRevisionTaller.findByIdComentario", query = "SELECT c FROM ComentariosRevisionTaller c WHERE c.idComentario = :idComentario"),
    @NamedQuery(name = "ComentariosRevisionTaller.findByFechaComentario", query = "SELECT c FROM ComentariosRevisionTaller c WHERE c.fechaComentario = :fechaComentario")})
public class ComentariosRevisionTaller implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_comentario")
    private Integer idComentario;
    @Basic(optional = false)
    @Lob
    @Column(name = "comentario")
    private String comentario;
    @Column(name = "fecha_comentario")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaComentario;
    @JoinColumn(name = "id_taller", referencedColumnName = "id_taller")
    @ManyToOne(optional = false)
    private Talleres idTaller;
    @JoinColumn(name = "id_usuario_comentarista", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioComentarista;

    public ComentariosRevisionTaller() {
    }

    public ComentariosRevisionTaller(Integer idComentario) {
        this.idComentario = idComentario;
    }

    public ComentariosRevisionTaller(Integer idComentario, String comentario) {
        this.idComentario = idComentario;
        this.comentario = comentario;
    }

    public Integer getIdComentario() {
        return idComentario;
    }

    public void setIdComentario(Integer idComentario) {
        this.idComentario = idComentario;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFechaComentario() {
        return fechaComentario;
    }

    public void setFechaComentario(Date fechaComentario) {
        this.fechaComentario = fechaComentario;
    }

    public Talleres getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(Talleres idTaller) {
        this.idTaller = idTaller;
    }

    public Usuarios getIdUsuarioComentarista() {
        return idUsuarioComentarista;
    }

    public void setIdUsuarioComentarista(Usuarios idUsuarioComentarista) {
        this.idUsuarioComentarista = idUsuarioComentarista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idComentario != null ? idComentario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComentariosRevisionTaller)) {
            return false;
        }
        ComentariosRevisionTaller other = (ComentariosRevisionTaller) object;
        if ((this.idComentario == null && other.idComentario != null) || (this.idComentario != null && !this.idComentario.equals(other.idComentario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.ComentariosRevisionTaller[ idComentario=" + idComentario + " ]";
    }
    
}
```

## `src\modelo\Convocatorias.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "convocatorias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Convocatorias.findAll", query = "SELECT c FROM Convocatorias c"),
    @NamedQuery(name = "Convocatorias.findByIdConvocatoria", query = "SELECT c FROM Convocatorias c WHERE c.idConvocatoria = :idConvocatoria"),
    @NamedQuery(name = "Convocatorias.findByTitulo", query = "SELECT c FROM Convocatorias c WHERE c.titulo = :titulo"),
    @NamedQuery(name = "Convocatorias.findByFechaPublicacion", query = "SELECT c FROM Convocatorias c WHERE c.fechaPublicacion = :fechaPublicacion"),
    @NamedQuery(name = "Convocatorias.findByFechaLimitePropuestas", query = "SELECT c FROM Convocatorias c WHERE c.fechaLimitePropuestas = :fechaLimitePropuestas"),
    @NamedQuery(name = "Convocatorias.findByDocumentoAdjuntoRuta", query = "SELECT c FROM Convocatorias c WHERE c.documentoAdjuntoRuta = :documentoAdjuntoRuta"),
    @NamedQuery(name = "Convocatorias.findByFechaCreacion", query = "SELECT c FROM Convocatorias c WHERE c.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Convocatorias.findByUltimaModificacion", query = "SELECT c FROM Convocatorias c WHERE c.ultimaModificacion = :ultimaModificacion")})
public class Convocatorias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_convocatoria")
    private Integer idConvocatoria;
    @Basic(optional = false)
    @Column(name = "titulo")
    private String titulo;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Basic(optional = false)
    @Column(name = "fecha_publicacion")
    @Temporal(TemporalType.DATE)
    private Date fechaPublicacion;
    @Column(name = "fecha_limite_propuestas")
    @Temporal(TemporalType.DATE)
    private Date fechaLimitePropuestas;
    @Column(name = "documento_adjunto_ruta")
    private String documentoAdjuntoRuta;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "ultima_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "idConvocatoriaRelacionada")
    private List<Notificaciones> notificacionesList;
    @OneToMany(mappedBy = "idConvocatoriaOrigen")
    private List<Eventos> eventosList;
    @JoinColumn(name = "id_usuario_publica", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioPublica;

    public Convocatorias() {
    }

    public Convocatorias(Integer idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    public Convocatorias(Integer idConvocatoria, String titulo, String descripcion, Date fechaPublicacion) {
        this.idConvocatoria = idConvocatoria;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaPublicacion = fechaPublicacion;
    }

    public Integer getIdConvocatoria() {
        return idConvocatoria;
    }

    public void setIdConvocatoria(Integer idConvocatoria) {
        this.idConvocatoria = idConvocatoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaLimitePropuestas() {
        return fechaLimitePropuestas;
    }

    public void setFechaLimitePropuestas(Date fechaLimitePropuestas) {
        this.fechaLimitePropuestas = fechaLimitePropuestas;
    }

    public String getDocumentoAdjuntoRuta() {
        return documentoAdjuntoRuta;
    }

    public void setDocumentoAdjuntoRuta(String documentoAdjuntoRuta) {
        this.documentoAdjuntoRuta = documentoAdjuntoRuta;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    @XmlTransient
    public List<Notificaciones> getNotificacionesList() {
        return notificacionesList;
    }

    public void setNotificacionesList(List<Notificaciones> notificacionesList) {
        this.notificacionesList = notificacionesList;
    }

    @XmlTransient
    public List<Eventos> getEventosList() {
        return eventosList;
    }

    public void setEventosList(List<Eventos> eventosList) {
        this.eventosList = eventosList;
    }

    public Usuarios getIdUsuarioPublica() {
        return idUsuarioPublica;
    }

    public void setIdUsuarioPublica(Usuarios idUsuarioPublica) {
        this.idUsuarioPublica = idUsuarioPublica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idConvocatoria != null ? idConvocatoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Convocatorias)) {
            return false;
        }
        Convocatorias other = (Convocatorias) object;
        if ((this.idConvocatoria == null && other.idConvocatoria != null) || (this.idConvocatoria != null && !this.idConvocatoria.equals(other.idConvocatoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Convocatorias[ idConvocatoria=" + idConvocatoria + " ]";
    }
    
}
```

## `src\modelo\EventoParticipantesTalleres.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "evento_participantes_talleres")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "EventoParticipantesTalleres.findAll", query = "SELECT e FROM EventoParticipantesTalleres e"),
    @NamedQuery(name = "EventoParticipantesTalleres.findByIdEventoParticipanteTaller", query = "SELECT e FROM EventoParticipantesTalleres e WHERE e.idEventoParticipanteTaller = :idEventoParticipanteTaller"),
    @NamedQuery(name = "EventoParticipantesTalleres.findByRolParticipante", query = "SELECT e FROM EventoParticipantesTalleres e WHERE e.rolParticipante = :rolParticipante")})
public class EventoParticipantesTalleres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_evento_participante_taller")
    private Integer idEventoParticipanteTaller;
    @Column(name = "rol_participante")
    private String rolParticipante;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    @ManyToOne(optional = false)
    private Eventos idEvento;
    @JoinColumn(name = "id_taller_impartido", referencedColumnName = "id_taller")
    @ManyToOne(optional = false)
    private Talleres idTallerImpartido;
    @JoinColumn(name = "id_tallerista", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idTallerista;

    public EventoParticipantesTalleres() {
    }

    public EventoParticipantesTalleres(Integer idEventoParticipanteTaller) {
        this.idEventoParticipanteTaller = idEventoParticipanteTaller;
    }

    public Integer getIdEventoParticipanteTaller() {
        return idEventoParticipanteTaller;
    }

    public void setIdEventoParticipanteTaller(Integer idEventoParticipanteTaller) {
        this.idEventoParticipanteTaller = idEventoParticipanteTaller;
    }

    public String getRolParticipante() {
        return rolParticipante;
    }

    public void setRolParticipante(String rolParticipante) {
        this.rolParticipante = rolParticipante;
    }

    public Eventos getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Eventos idEvento) {
        this.idEvento = idEvento;
    }

    public Talleres getIdTallerImpartido() {
        return idTallerImpartido;
    }

    public void setIdTallerImpartido(Talleres idTallerImpartido) {
        this.idTallerImpartido = idTallerImpartido;
    }

    public Usuarios getIdTallerista() {
        return idTallerista;
    }

    public void setIdTallerista(Usuarios idTallerista) {
        this.idTallerista = idTallerista;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEventoParticipanteTaller != null ? idEventoParticipanteTaller.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof EventoParticipantesTalleres)) {
            return false;
        }
        EventoParticipantesTalleres other = (EventoParticipantesTalleres) object;
        if ((this.idEventoParticipanteTaller == null && other.idEventoParticipanteTaller != null) || (this.idEventoParticipanteTaller != null && !this.idEventoParticipanteTaller.equals(other.idEventoParticipanteTaller))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.EventoParticipantesTalleres[ idEventoParticipanteTaller=" + idEventoParticipanteTaller + " ]";
    }
    
}
```

## `src\modelo\Eventos.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "eventos")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Eventos.findAll", query = "SELECT e FROM Eventos e"),
    @NamedQuery(name = "Eventos.findByIdEvento", query = "SELECT e FROM Eventos e WHERE e.idEvento = :idEvento"),
    @NamedQuery(name = "Eventos.findByNombre", query = "SELECT e FROM Eventos e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Eventos.findByFechaEvento", query = "SELECT e FROM Eventos e WHERE e.fechaEvento = :fechaEvento"),
    @NamedQuery(name = "Eventos.findByHoraInicioEvento", query = "SELECT e FROM Eventos e WHERE e.horaInicioEvento = :horaInicioEvento"),
    @NamedQuery(name = "Eventos.findByHoraFinEvento", query = "SELECT e FROM Eventos e WHERE e.horaFinEvento = :horaFinEvento"),
    @NamedQuery(name = "Eventos.findByLugarEvento", query = "SELECT e FROM Eventos e WHERE e.lugarEvento = :lugarEvento"),
    @NamedQuery(name = "Eventos.findByEstadoEvento", query = "SELECT e FROM Eventos e WHERE e.estadoEvento = :estadoEvento"),
    @NamedQuery(name = "Eventos.findByFechaCreacion", query = "SELECT e FROM Eventos e WHERE e.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Eventos.findByUltimaModificacion", query = "SELECT e FROM Eventos e WHERE e.ultimaModificacion = :ultimaModificacion")})
public class Eventos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_evento")
    private Integer idEvento;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Lob
    @Column(name = "descripcion_publica")
    private String descripcionPublica;
    @Basic(optional = false)
    @Column(name = "fecha_evento")
    @Temporal(TemporalType.DATE)
    private Date fechaEvento;
    @Column(name = "hora_inicio_evento")
    @Temporal(TemporalType.TIME)
    private Date horaInicioEvento;
    @Column(name = "hora_fin_evento")
    @Temporal(TemporalType.TIME)
    private Date horaFinEvento;
    @Column(name = "lugar_evento")
    private String lugarEvento;
    @Basic(optional = false)
    @Column(name = "estado_evento")
    private String estadoEvento;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "ultima_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(mappedBy = "idEventoRelacionado")
    private List<Notificaciones> notificacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento")
    private List<Evidencias> evidenciasList;
    @JoinColumn(name = "id_convocatoria_origen", referencedColumnName = "id_convocatoria")
    @ManyToOne
    private Convocatorias idConvocatoriaOrigen;
    @JoinColumn(name = "id_docente_responsable", referencedColumnName = "id_usuario")
    @ManyToOne
    private Usuarios idDocenteResponsable;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento")
    private List<BitacorasEventos> bitacorasEventosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idEvento")
    private List<EventoParticipantesTalleres> eventoParticipantesTalleresList;

    public Eventos() {
    }

    public Eventos(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public Eventos(Integer idEvento, String nombre, Date fechaEvento, String estadoEvento) {
        this.idEvento = idEvento;
        this.nombre = nombre;
        this.fechaEvento = fechaEvento;
        this.estadoEvento = estadoEvento;
    }

    public Integer getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Integer idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionPublica() {
        return descripcionPublica;
    }

    public void setDescripcionPublica(String descripcionPublica) {
        this.descripcionPublica = descripcionPublica;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public Date getHoraInicioEvento() {
        return horaInicioEvento;
    }

    public void setHoraInicioEvento(Date horaInicioEvento) {
        this.horaInicioEvento = horaInicioEvento;
    }

    public Date getHoraFinEvento() {
        return horaFinEvento;
    }

    public void setHoraFinEvento(Date horaFinEvento) {
        this.horaFinEvento = horaFinEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

    public String getEstadoEvento() {
        return estadoEvento;
    }

    public void setEstadoEvento(String estadoEvento) {
        this.estadoEvento = estadoEvento;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    @XmlTransient
    public List<Notificaciones> getNotificacionesList() {
        return notificacionesList;
    }

    public void setNotificacionesList(List<Notificaciones> notificacionesList) {
        this.notificacionesList = notificacionesList;
    }

    @XmlTransient
    public List<Evidencias> getEvidenciasList() {
        return evidenciasList;
    }

    public void setEvidenciasList(List<Evidencias> evidenciasList) {
        this.evidenciasList = evidenciasList;
    }

    public Convocatorias getIdConvocatoriaOrigen() {
        return idConvocatoriaOrigen;
    }

    public void setIdConvocatoriaOrigen(Convocatorias idConvocatoriaOrigen) {
        this.idConvocatoriaOrigen = idConvocatoriaOrigen;
    }

    public Usuarios getIdDocenteResponsable() {
        return idDocenteResponsable;
    }

    public void setIdDocenteResponsable(Usuarios idDocenteResponsable) {
        this.idDocenteResponsable = idDocenteResponsable;
    }

    @XmlTransient
    public List<BitacorasEventos> getBitacorasEventosList() {
        return bitacorasEventosList;
    }

    public void setBitacorasEventosList(List<BitacorasEventos> bitacorasEventosList) {
        this.bitacorasEventosList = bitacorasEventosList;
    }

    @XmlTransient
    public List<EventoParticipantesTalleres> getEventoParticipantesTalleresList() {
        return eventoParticipantesTalleresList;
    }

    public void setEventoParticipantesTalleresList(List<EventoParticipantesTalleres> eventoParticipantesTalleresList) {
        this.eventoParticipantesTalleresList = eventoParticipantesTalleresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvento != null ? idEvento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Eventos)) {
            return false;
        }
        Eventos other = (Eventos) object;
        if ((this.idEvento == null && other.idEvento != null) || (this.idEvento != null && !this.idEvento.equals(other.idEvento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Eventos[ idEvento=" + idEvento + " ]";
    }
    
}
```

## `src\modelo\Evidencias.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "evidencias")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Evidencias.findAll", query = "SELECT e FROM Evidencias e"),
    @NamedQuery(name = "Evidencias.findByIdEvidencia", query = "SELECT e FROM Evidencias e WHERE e.idEvidencia = :idEvidencia"),
    @NamedQuery(name = "Evidencias.findByTipoEvidencia", query = "SELECT e FROM Evidencias e WHERE e.tipoEvidencia = :tipoEvidencia"),
    @NamedQuery(name = "Evidencias.findByRutaArchivo", query = "SELECT e FROM Evidencias e WHERE e.rutaArchivo = :rutaArchivo"),
    @NamedQuery(name = "Evidencias.findByFechaSubida", query = "SELECT e FROM Evidencias e WHERE e.fechaSubida = :fechaSubida")})
public class Evidencias implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_evidencia")
    private Integer idEvidencia;
    @Basic(optional = false)
    @Column(name = "tipo_evidencia")
    private String tipoEvidencia;
    @Basic(optional = false)
    @Column(name = "ruta_archivo")
    private String rutaArchivo;
    @Lob
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha_subida")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaSubida;
    @JoinColumn(name = "id_evento", referencedColumnName = "id_evento")
    @ManyToOne(optional = false)
    private Eventos idEvento;
    @JoinColumn(name = "id_taller_asociado", referencedColumnName = "id_taller")
    @ManyToOne
    private Talleres idTallerAsociado;
    @JoinColumn(name = "id_usuario_subio", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioSubio;

    public Evidencias() {
    }

    public Evidencias(Integer idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    public Evidencias(Integer idEvidencia, String tipoEvidencia, String rutaArchivo) {
        this.idEvidencia = idEvidencia;
        this.tipoEvidencia = tipoEvidencia;
        this.rutaArchivo = rutaArchivo;
    }

    public Integer getIdEvidencia() {
        return idEvidencia;
    }

    public void setIdEvidencia(Integer idEvidencia) {
        this.idEvidencia = idEvidencia;
    }

    public String getTipoEvidencia() {
        return tipoEvidencia;
    }

    public void setTipoEvidencia(String tipoEvidencia) {
        this.tipoEvidencia = tipoEvidencia;
    }

    public String getRutaArchivo() {
        return rutaArchivo;
    }

    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaSubida() {
        return fechaSubida;
    }

    public void setFechaSubida(Date fechaSubida) {
        this.fechaSubida = fechaSubida;
    }

    public Eventos getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(Eventos idEvento) {
        this.idEvento = idEvento;
    }

    public Talleres getIdTallerAsociado() {
        return idTallerAsociado;
    }

    public void setIdTallerAsociado(Talleres idTallerAsociado) {
        this.idTallerAsociado = idTallerAsociado;
    }

    public Usuarios getIdUsuarioSubio() {
        return idUsuarioSubio;
    }

    public void setIdUsuarioSubio(Usuarios idUsuarioSubio) {
        this.idUsuarioSubio = idUsuarioSubio;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idEvidencia != null ? idEvidencia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Evidencias)) {
            return false;
        }
        Evidencias other = (Evidencias) object;
        if ((this.idEvidencia == null && other.idEvidencia != null) || (this.idEvidencia != null && !this.idEvidencia.equals(other.idEvidencia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Evidencias[ idEvidencia=" + idEvidencia + " ]";
    }
    
}
```

## `src\modelo\Notificaciones.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "notificaciones")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Notificaciones.findAll", query = "SELECT n FROM Notificaciones n"),
    @NamedQuery(name = "Notificaciones.findByIdNotificacion", query = "SELECT n FROM Notificaciones n WHERE n.idNotificacion = :idNotificacion"),
    @NamedQuery(name = "Notificaciones.findByTipoNotificacion", query = "SELECT n FROM Notificaciones n WHERE n.tipoNotificacion = :tipoNotificacion"),
    @NamedQuery(name = "Notificaciones.findByLeida", query = "SELECT n FROM Notificaciones n WHERE n.leida = :leida"),
    @NamedQuery(name = "Notificaciones.findByFechaCreacion", query = "SELECT n FROM Notificaciones n WHERE n.fechaCreacion = :fechaCreacion")})
public class Notificaciones implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_notificacion")
    private Integer idNotificacion;
    @Basic(optional = false)
    @Lob
    @Column(name = "mensaje")
    private String mensaje;
    @Basic(optional = false)
    @Column(name = "tipo_notificacion")
    private String tipoNotificacion;
    @Column(name = "leida")
    private Boolean leida;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @JoinColumn(name = "id_convocatoria_relacionada", referencedColumnName = "id_convocatoria")
    @ManyToOne
    private Convocatorias idConvocatoriaRelacionada;
    @JoinColumn(name = "id_evento_relacionado", referencedColumnName = "id_evento")
    @ManyToOne
    private Eventos idEventoRelacionado;
    @JoinColumn(name = "id_taller_relacionado", referencedColumnName = "id_taller")
    @ManyToOne
    private Talleres idTallerRelacionado;
    @JoinColumn(name = "id_usuario_destinatario", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioDestinatario;

    public Notificaciones() {
    }

    public Notificaciones(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public Notificaciones(Integer idNotificacion, String mensaje, String tipoNotificacion) {
        this.idNotificacion = idNotificacion;
        this.mensaje = mensaje;
        this.tipoNotificacion = tipoNotificacion;
    }

    public Integer getIdNotificacion() {
        return idNotificacion;
    }

    public void setIdNotificacion(Integer idNotificacion) {
        this.idNotificacion = idNotificacion;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getTipoNotificacion() {
        return tipoNotificacion;
    }

    public void setTipoNotificacion(String tipoNotificacion) {
        this.tipoNotificacion = tipoNotificacion;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Convocatorias getIdConvocatoriaRelacionada() {
        return idConvocatoriaRelacionada;
    }

    public void setIdConvocatoriaRelacionada(Convocatorias idConvocatoriaRelacionada) {
        this.idConvocatoriaRelacionada = idConvocatoriaRelacionada;
    }

    public Eventos getIdEventoRelacionado() {
        return idEventoRelacionado;
    }

    public void setIdEventoRelacionado(Eventos idEventoRelacionado) {
        this.idEventoRelacionado = idEventoRelacionado;
    }

    public Talleres getIdTallerRelacionado() {
        return idTallerRelacionado;
    }

    public void setIdTallerRelacionado(Talleres idTallerRelacionado) {
        this.idTallerRelacionado = idTallerRelacionado;
    }

    public Usuarios getIdUsuarioDestinatario() {
        return idUsuarioDestinatario;
    }

    public void setIdUsuarioDestinatario(Usuarios idUsuarioDestinatario) {
        this.idUsuarioDestinatario = idUsuarioDestinatario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotificacion != null ? idNotificacion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notificaciones)) {
            return false;
        }
        Notificaciones other = (Notificaciones) object;
        if ((this.idNotificacion == null && other.idNotificacion != null) || (this.idNotificacion != null && !this.idNotificacion.equals(other.idNotificacion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Notificaciones[ idNotificacion=" + idNotificacion + " ]";
    }
    
}
```

## `src\modelo\Talleres.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "talleres")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Talleres.findAll", query = "SELECT t FROM Talleres t"),
    @NamedQuery(name = "Talleres.findByIdTaller", query = "SELECT t FROM Talleres t WHERE t.idTaller = :idTaller"),
    @NamedQuery(name = "Talleres.findByNombre", query = "SELECT t FROM Talleres t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Talleres.findByManualRuta", query = "SELECT t FROM Talleres t WHERE t.manualRuta = :manualRuta"),
    @NamedQuery(name = "Talleres.findByEstado", query = "SELECT t FROM Talleres t WHERE t.estado = :estado"),
    @NamedQuery(name = "Talleres.findByFechaCreacion", query = "SELECT t FROM Talleres t WHERE t.fechaCreacion = :fechaCreacion"),
    @NamedQuery(name = "Talleres.findByUltimaModificacion", query = "SELECT t FROM Talleres t WHERE t.ultimaModificacion = :ultimaModificacion")})
public class Talleres implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_taller")
    private Integer idTaller;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Lob
    @Column(name = "descripcion_publica")
    private String descripcionPublica;
    @Lob
    @Column(name = "detalles_internos")
    private String detallesInternos;
    @Basic(optional = false)
    @Lob
    @Column(name = "requisitos_materiales")
    private String requisitosMateriales;
    @Column(name = "manual_ruta")
    private String manualRuta;
    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @Column(name = "fecha_creacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;
    @Column(name = "ultima_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTaller")
    private List<ComentariosRevisionTaller> comentariosRevisionTallerList;
    @OneToMany(mappedBy = "idTallerRelacionado")
    private List<Notificaciones> notificacionesList;
    @OneToMany(mappedBy = "idTallerAsociado")
    private List<Evidencias> evidenciasList;
    @JoinColumn(name = "id_usuario_proponente", referencedColumnName = "id_usuario")
    @ManyToOne(optional = false)
    private Usuarios idUsuarioProponente;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTallerImpartido")
    private List<EventoParticipantesTalleres> eventoParticipantesTalleresList;

    public Talleres() {
    }

    public Talleres(Integer idTaller) {
        this.idTaller = idTaller;
    }

    public Talleres(Integer idTaller, String nombre, String descripcionPublica, String requisitosMateriales, String estado) {
        this.idTaller = idTaller;
        this.nombre = nombre;
        this.descripcionPublica = descripcionPublica;
        this.requisitosMateriales = requisitosMateriales;
        this.estado = estado;
    }

    public Integer getIdTaller() {
        return idTaller;
    }

    public void setIdTaller(Integer idTaller) {
        this.idTaller = idTaller;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcionPublica() {
        return descripcionPublica;
    }

    public void setDescripcionPublica(String descripcionPublica) {
        this.descripcionPublica = descripcionPublica;
    }

    public String getDetallesInternos() {
        return detallesInternos;
    }

    public void setDetallesInternos(String detallesInternos) {
        this.detallesInternos = detallesInternos;
    }

    public String getRequisitosMateriales() {
        return requisitosMateriales;
    }

    public void setRequisitosMateriales(String requisitosMateriales) {
        this.requisitosMateriales = requisitosMateriales;
    }

    public String getManualRuta() {
        return manualRuta;
    }

    public void setManualRuta(String manualRuta) {
        this.manualRuta = manualRuta;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Date getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    @XmlTransient
    public List<ComentariosRevisionTaller> getComentariosRevisionTallerList() {
        return comentariosRevisionTallerList;
    }

    public void setComentariosRevisionTallerList(List<ComentariosRevisionTaller> comentariosRevisionTallerList) {
        this.comentariosRevisionTallerList = comentariosRevisionTallerList;
    }

    @XmlTransient
    public List<Notificaciones> getNotificacionesList() {
        return notificacionesList;
    }

    public void setNotificacionesList(List<Notificaciones> notificacionesList) {
        this.notificacionesList = notificacionesList;
    }

    @XmlTransient
    public List<Evidencias> getEvidenciasList() {
        return evidenciasList;
    }

    public void setEvidenciasList(List<Evidencias> evidenciasList) {
        this.evidenciasList = evidenciasList;
    }

    public Usuarios getIdUsuarioProponente() {
        return idUsuarioProponente;
    }

    public void setIdUsuarioProponente(Usuarios idUsuarioProponente) {
        this.idUsuarioProponente = idUsuarioProponente;
    }

    @XmlTransient
    public List<EventoParticipantesTalleres> getEventoParticipantesTalleresList() {
        return eventoParticipantesTalleresList;
    }

    public void setEventoParticipantesTalleresList(List<EventoParticipantesTalleres> eventoParticipantesTalleresList) {
        this.eventoParticipantesTalleresList = eventoParticipantesTalleresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTaller != null ? idTaller.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Talleres)) {
            return false;
        }
        Talleres other = (Talleres) object;
        if ((this.idTaller == null && other.idTaller != null) || (this.idTaller != null && !this.idTaller.equals(other.idTaller))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Talleres[ idTaller=" + idTaller + " ]";
    }
    
}
```

## `src\modelo\Usuarios.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "usuarios")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usuarios.findAll", query = "SELECT u FROM Usuarios u"),
    @NamedQuery(name = "Usuarios.findByIdUsuario", query = "SELECT u FROM Usuarios u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuarios.findByNombre", query = "SELECT u FROM Usuarios u WHERE u.nombre = :nombre"),
    @NamedQuery(name = "Usuarios.findByCorreo", query = "SELECT u FROM Usuarios u WHERE u.correo = :correo"),
    @NamedQuery(name = "Usuarios.findByContrasenaHash", query = "SELECT u FROM Usuarios u WHERE u.contrasenaHash = :contrasenaHash"),
    @NamedQuery(name = "Usuarios.findByRol", query = "SELECT u FROM Usuarios u WHERE u.rol = :rol"),
    @NamedQuery(name = "Usuarios.findByNumeroControl", query = "SELECT u FROM Usuarios u WHERE u.numeroControl = :numeroControl"),
    @NamedQuery(name = "Usuarios.findByFechaRegistro", query = "SELECT u FROM Usuarios u WHERE u.fechaRegistro = :fechaRegistro"),
    @NamedQuery(name = "Usuarios.findByUltimaModificacion", query = "SELECT u FROM Usuarios u WHERE u.ultimaModificacion = :ultimaModificacion"),
    @NamedQuery(name = "Usuarios.findByActivo", query = "SELECT u FROM Usuarios u WHERE u.activo = :activo")})
public class Usuarios implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_usuario")
    private Integer idUsuario;
    @Basic(optional = false)
    @Column(name = "nombre")
    private String nombre;
    @Basic(optional = false)
    @Column(name = "correo")
    private String correo;
    @Basic(optional = false)
    @Column(name = "contrasena_hash")
    private String contrasenaHash;
    @Basic(optional = false)
    @Column(name = "rol")
    private String rol;
    @Column(name = "numero_control")
    private String numeroControl;
    @Column(name = "fecha_registro")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaRegistro;
    @Column(name = "ultima_modificacion")
    @Temporal(TemporalType.TIMESTAMP)
    private Date ultimaModificacion;
    @Column(name = "activo")
    private Boolean activo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioComentarista")
    private List<ComentariosRevisionTaller> comentariosRevisionTallerList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioDestinatario")
    private List<Notificaciones> notificacionesList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioSubio")
    private List<Evidencias> evidenciasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioProponente")
    private List<Talleres> talleresList;
    @OneToMany(mappedBy = "idDocenteResponsable")
    private List<Eventos> eventosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioRegistra")
    private List<BitacorasEventos> bitacorasEventosList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuarioPublica")
    private List<Convocatorias> convocatoriasList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTallerista")
    private List<EventoParticipantesTalleres> eventoParticipantesTalleresList;

    public Usuarios() {
    }

    public Usuarios(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Usuarios(Integer idUsuario, String nombre, String correo, String contrasenaHash, String rol) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasenaHash = contrasenaHash;
        this.rol = rol;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNumeroControl() {
        return numeroControl;
    }

    public void setNumeroControl(String numeroControl) {
        this.numeroControl = numeroControl;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getUltimaModificacion() {
        return ultimaModificacion;
    }

    public void setUltimaModificacion(Date ultimaModificacion) {
        this.ultimaModificacion = ultimaModificacion;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    @XmlTransient
    public List<ComentariosRevisionTaller> getComentariosRevisionTallerList() {
        return comentariosRevisionTallerList;
    }

    public void setComentariosRevisionTallerList(List<ComentariosRevisionTaller> comentariosRevisionTallerList) {
        this.comentariosRevisionTallerList = comentariosRevisionTallerList;
    }

    @XmlTransient
    public List<Notificaciones> getNotificacionesList() {
        return notificacionesList;
    }

    public void setNotificacionesList(List<Notificaciones> notificacionesList) {
        this.notificacionesList = notificacionesList;
    }

    @XmlTransient
    public List<Evidencias> getEvidenciasList() {
        return evidenciasList;
    }

    public void setEvidenciasList(List<Evidencias> evidenciasList) {
        this.evidenciasList = evidenciasList;
    }

    @XmlTransient
    public List<Talleres> getTalleresList() {
        return talleresList;
    }

    public void setTalleresList(List<Talleres> talleresList) {
        this.talleresList = talleresList;
    }

    @XmlTransient
    public List<Eventos> getEventosList() {
        return eventosList;
    }

    public void setEventosList(List<Eventos> eventosList) {
        this.eventosList = eventosList;
    }

    @XmlTransient
    public List<BitacorasEventos> getBitacorasEventosList() {
        return bitacorasEventosList;
    }

    public void setBitacorasEventosList(List<BitacorasEventos> bitacorasEventosList) {
        this.bitacorasEventosList = bitacorasEventosList;
    }

    @XmlTransient
    public List<Convocatorias> getConvocatoriasList() {
        return convocatoriasList;
    }

    public void setConvocatoriasList(List<Convocatorias> convocatoriasList) {
        this.convocatoriasList = convocatoriasList;
    }

    @XmlTransient
    public List<EventoParticipantesTalleres> getEventoParticipantesTalleresList() {
        return eventoParticipantesTalleresList;
    }

    public void setEventoParticipantesTalleresList(List<EventoParticipantesTalleres> eventoParticipantesTalleresList) {
        this.eventoParticipantesTalleresList = eventoParticipantesTalleresList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuarios)) {
            return false;
        }
        Usuarios other = (Usuarios) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Usuarios[ idUsuario=" + idUsuario + " ]";
    }
    
}
```

