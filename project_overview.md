# Estructura del Proyecto: tutorias

## Árbol de Directorios

```
tutorias/
├── lib
│   ├── CopyLibs
│   │   └── org-netbeans-modules-java-j2seproject-copylibstask.jar
│   ├── absolutelayout
│   │   └── AbsoluteLayout.jar
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
│   ├── DualListTransfer.jar
│   ├── dual-list-transfer.jar
│   ├── flatlaf-demo-3.6.jar
│   ├── jcalendar-1.4.jar
│   ├── mysql-connector-java-8.0.17.jar
│   └── nblibraries.properties
├── nbproject
│   ├── private
│   │   ├── config.properties
│   │   ├── private.properties
│   │   └── private.xml
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
│   │   ├── AdmDatos.java
│   │   ├── CitaJpaController.java
│   │   ├── Conexion.java
│   │   ├── TutorJpaController.java
│   │   ├── TutoradoJpaController.java
│   │   └── TutoriaJpaController.java
│   ├── modelo
│   │   ├── Cita.java
│   │   ├── DatosTablaCitas.java
│   │   ├── MTablaCita.java
│   │   ├── MTcita.java
│   │   ├── MTtutor.java
│   │   ├── MTtutorado.java
│   │   ├── MTtutoria.java
│   │   ├── Tutor.java
│   │   ├── Tutorado.java
│   │   └── Tutoria.java
│   └── vista
│       ├── IMenuTutorias.java
│       ├── IPCita.form
│       ├── IPCita.java
│       ├── IPTutorado.form
│       ├── IPTutorado.java
│       ├── IPTutoria.form
│       ├── IPTutoria.java
│       ├── ITutor.form
│       ├── ITutor.java
│       ├── ITutorado.form
│       ├── ITutorado.java
│       ├── ITutoria.form
│       └── ITutoria.java
├── test
├── .gitignore
├── build.xml
├── manifest.mf
├── project_overview.md
└── script.py
```

## Contenido de Archivos

### `.gitignore`

```text
/dist/
/nbproject/private/
/build/

```

### `project_overview.md`

```markdown

```

### `script.py`

```python
import os
import argparse
from pathlib import Path # Usaremos pathlib para un manejo de rutas más moderno

# Directorios a ignorar (incluye caches de Python, VCS, y otros comunes)
IGNORE_DIRS = {
    # Control de versiones
    '.git', '.hg', '.svn',
    # Entornos virtuales de Python
    'venv', '.venv', 'env', '.env', 'ENV',
    # Caches y artefactos de Python
    '__pycache__', '.pytest_cache', '.mypy_cache', 'build', 'dist', '*.egg-info',
    # Node.js
    'node_modules',
    # Archivos de sistema operativo
    '.DS_Store', 'Thumbs.db',
    # Específicos del usuario original
    '.web',
    # Otros comunes
    '.vscode', '.idea',
}

# Extensiones de archivo cuyo contenido se incluirá
ALLOWED_EXTS = {'.py', '.java', '.cpp', '.c', '.js', '.ts', '.md', '.txt', '.json', '.yaml', '.yml', '.html', '.css', '.sh', '.rb', '.go'}

# Archivos específicos a incluir siempre (aunque empiecen con '.'
# o tengan extensión fuera de ALLOWED_EXTS), su contenido será extraído.
# Y se mostrarán en el árbol incluso si empiezan con '.'
INCLUDED_FILES = {'requirements.txt', 'rxconfig.py', '.gitignore', 'Dockerfile', 'docker-compose.yml', 'README.md'}

# Prefijos para el tree
TREE_PREFIXES = {
    'branch': '├── ',
    'last':   '└── ',
    'indent': '    ', # Cuatro espacios para la indentación
    'pipe':   '│   '
}


def is_likely_binary(filepath, block_size=1024):
    """
    Detecta si un archivo es probablemente binario o no es texto legible.
    Devuelve True si el archivo parece binario o si no se puede leer.
    """
    try:
        with open(filepath, 'rb') as f:
            block = f.read(block_size)
        if not block:  # Archivo vacío
            return False
        # Heurística común: presencia de byte nulo.
        # Una prueba más robusta podría implicar comprobar un umbral de caracteres no imprimibles.
        return b'\0' in block
    except IOError: # Captura FileNotFoundError, PermissionError, etc.
        return True # Si no se puede abrir/leer, trátalo como problemático


def build_tree(root_path_obj: Path):
    """
    Genera una lista de líneas representando la estructura de directorios.
    - Omite directorios en IGNORE_DIRS.
    - Muestra archivos que no comiencen con '.' o estén en INCLUDED_FILES.
    - Ordena directorios primero, luego archivos, ambos alfabéticamente.
    """
    tree_lines = []

    def _tree(current_dir_obj: Path, prefix=''):
        # Manejo de errores al listar el directorio
        try:
            # Obtener todos los elementos, manejar enlaces simbólicos con cuidado si es necesario
            # Path.iterdir() no falla en enlaces rotos por defecto, pero os.path.isdir/isfile sí lo haría.
            # Usaremos os.path.isdir/isfile para consistencia con el chequeo de enlaces.
            raw_entry_names = sorted(os.listdir(current_dir_obj))
        except OSError as e:
            tree_lines.append(f"{prefix}{TREE_PREFIXES['branch']} [Error al leer directorio: {current_dir_obj.name} - {e.strerror}]")
            return

        current_level_dirs = []
        current_level_files = []

        for name in raw_entry_names:
            path_obj = current_dir_obj / name
            
            # Chequeo básico para evitar problemas con enlaces rotos al determinar tipo
            try:
                is_dir = path_obj.is_dir()
            except OSError: # p.ej. Enlace simbólico roto al llamar a stat()
                continue 


            if is_dir:
                if name not in IGNORE_DIRS:
                    current_level_dirs.append(name)
            else: # Es un archivo (o enlace a archivo)
                if name not in IGNORE_DIRS and \
                   (not name.startswith('.') or name in INCLUDED_FILES):
                    current_level_files.append(name)
        
        # Combinar: directorios primero, luego archivos. Ambos ya están ordenados.
        items_to_display = current_level_dirs + current_level_files
        
        total_items = len(items_to_display)
        for idx, name in enumerate(items_to_display):
            item_path_obj = current_dir_obj / name
            is_dir_item = item_path_obj.is_dir() # Re-evaluar o usar 'name in current_level_dirs'

            connector = TREE_PREFIXES['last'] if idx == total_items - 1 else TREE_PREFIXES['branch']
            tree_lines.append(f"{prefix}{connector}{name}")

            if is_dir_item:
                extension = TREE_PREFIXES['indent'] if idx == total_items - 1 else TREE_PREFIXES['pipe']
                _tree(item_path_obj, prefix + extension)

    # Añadir el nombre del directorio raíz al inicio del árbol
    tree_lines.append(root_path_obj.name + ("/" if root_path_obj.is_dir() else ""))
    _tree(root_path_obj)
    return tree_lines


def collect_files(root_path_obj: Path):
    """
    Recorre el árbol de directorios y recolecta rutas de archivos para incluir su contenido.
    - Omite directorios en IGNORE_DIRS.
    - Incluye archivos con extensiones en ALLOWED_EXTS.
    - Incluye archivos listados en INCLUDED_FILES.
    Devuelve una lista de objetos Path.
    """
    paths_to_collect = []
    for dirpath, dirnames, filenames in os.walk(root_path_obj, topdown=True):
        # Excluir directorios no deseados para no descender en ellos
        dirnames[:] = [d for d in dirnames if d not in IGNORE_DIRS]

        for fname in sorted(filenames):
            # Comprobar si el propio nombre del archivo está en IGNORE_DIRS (menos común pero posible)
            if fname in IGNORE_DIRS:
                continue

            file_path_obj = Path(dirpath) / fname
            ext = file_path_obj.suffix.lower() # Obtener extensión con pathlib

            if ext in ALLOWED_EXTS or fname in INCLUDED_FILES:
                paths_to_collect.append(file_path_obj)
                
    return sorted(paths_to_collect) # Ordenar la lista final de archivos


def ext_to_lang(ext: str):
    """Mapea extensión de archivo a lenguaje para bloques de código Markdown."""
    # Normalizar: quitar el punto y convertir a minúsculas
    norm_ext = ext.lstrip('.').lower()
    return {
        'py': 'python',
        'java': 'java',
        'cpp': 'cpp',
        'c': 'c',
        'js': 'javascript',
        'ts': 'typescript',
        'md': 'markdown',
        'txt': 'text',
        'json': 'json',
        'yaml': 'yaml',
        'yml': 'yaml',
        'html': 'html',
        'css': 'css',
        'sh': 'bash', # o 'shell'
        'rb': 'ruby',
        'go': 'go',
        'gitignore': 'text', # Caso especial para .gitignore
        'dockerfile': 'dockerfile',
        # Añade más mapeos según sea necesario
    }.get(norm_ext, 'text') # Default a 'text' si no se encuentra


def main():
    parser = argparse.ArgumentParser(
        description="Genera un archivo Markdown con la estructura de directorios tipo 'tree' y el código fuente de archivos seleccionados de un proyecto.",
        formatter_class=argparse.RawTextHelpFormatter
    )
    parser.add_argument(
        'project_root', nargs='?', default=os.getcwd(),
        help='Ruta al directorio raíz del proyecto. (default: directorio actual)'
    )
    parser.add_argument(
        '-o', '--output', default='project_overview.md',
        help='Nombre del archivo Markdown de salida. (default: project_overview.md)'
    )
    args = parser.parse_args()

    root_path = Path(args.project_root).resolve() # Usar Path y resolver a ruta absoluta

    if not root_path.is_dir():
        print(f"Error: La ruta especificada '{args.project_root}' no es un directorio o no existe.")
        return

    print(f"Analizando proyecto en: {root_path}")
    print(f"Generando resumen en: {Path(args.output).resolve()}")

    tree_lines = build_tree(root_path)
    code_files_paths = collect_files(root_path)

    try:
        with open(args.output, 'w', encoding='utf-8') as md:
            md.write(f"# Estructura del Proyecto: {root_path.name}\n\n")

            md.write("## Árbol de Directorios\n\n")
            md.write("```\n")
            md.write("\n".join(tree_lines))
            md.write("\n```\n\n")

            if not code_files_paths:
                md.write("## Contenido de Archivos\n\n")
                md.write("No se encontraron archivos para incluir según los criterios definidos (ALLOWED_EXTS, INCLUDED_FILES).\n")
            else:
                md.write("## Contenido de Archivos\n\n")
                for path_obj in code_files_paths:
                    # Crear ruta relativa desde la raíz del proyecto para mostrarla
                    try:
                        rel_path_str = str(path_obj.relative_to(root_path))
                    except ValueError: # Si path_obj no está bajo root_path (no debería pasar con collect_files)
                        rel_path_str = str(path_obj)

                    # Determinar el lenguaje para el bloque de código
                    # Usar el nombre del archivo si la extensión está vacía (ej. Dockerfile)
                    lang_key = path_obj.name if not path_obj.suffix else path_obj.suffix
                    lang = ext_to_lang(lang_key)
                    
                    md.write(f"### `{rel_path_str}`\n\n")
                    
                    if is_likely_binary(path_obj):
                        md.write(f"```\n[Contenido de archivo binario o no legible omitido: {path_obj.name}]\n```\n\n")
                        continue
                    
                    md.write(f"```{lang}\n")
                    try:
                        with open(path_obj, 'r', encoding='utf-8', errors='replace') as f_content:
                            md.write(f_content.read())
                    except Exception as e:
                        md.write(f"\n# Error al leer el archivo: {e}\n")
                    md.write("\n```\n\n")

        print(f"Archivo Markdown generado exitosamente: {Path(args.output).resolve()}")

    except IOError as e:
        print(f"Error al escribir el archivo de salida '{args.output}': {e}")
    except Exception as e:
        print(f"Ocurrió un error inesperado: {e}")


if __name__ == '__main__':
    main()
```

### `src\control\AdmDatos.java`

```java
//Este se usa en lugar de conexión

package control;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
            
public class AdmDatos {
    protected static EntityManagerFactory enf;
    public static EntityManagerFactory getEnf(){
        if(enf == null)
            enf = Persistence.createEntityManagerFactory("tutoriasPU");
        
        return enf;
    }
}

```

### `src\control\CitaJpaController.java`

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
import modelo.Tutor;
import modelo.Tutoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Cita;

/**
 *
 * @author jesus
 */
public class CitaJpaController implements Serializable {

    public CitaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cita cita) {
        if (cita.getTutoriaList() == null) {
            cita.setTutoriaList(new ArrayList<Tutoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutor tutor = cita.getTutor();
            if (tutor != null) {
                tutor = em.getReference(tutor.getClass(), tutor.getIdPersona());
                cita.setTutor(tutor);
            }
            List<Tutoria> attachedTutoriaList = new ArrayList<Tutoria>();
            for (Tutoria tutoriaListTutoriaToAttach : cita.getTutoriaList()) {
                tutoriaListTutoriaToAttach = em.getReference(tutoriaListTutoriaToAttach.getClass(), tutoriaListTutoriaToAttach.getIdTutoria());
                attachedTutoriaList.add(tutoriaListTutoriaToAttach);
            }
            cita.setTutoriaList(attachedTutoriaList);
            em.persist(cita);
            if (tutor != null) {
                tutor.getCitaList().add(cita);
                tutor = em.merge(tutor);
            }
            for (Tutoria tutoriaListTutoria : cita.getTutoriaList()) {
                Cita oldIdCitaOfTutoriaListTutoria = tutoriaListTutoria.getIdCita();
                tutoriaListTutoria.setIdCita(cita);
                tutoriaListTutoria = em.merge(tutoriaListTutoria);
                if (oldIdCitaOfTutoriaListTutoria != null) {
                    oldIdCitaOfTutoriaListTutoria.getTutoriaList().remove(tutoriaListTutoria);
                    oldIdCitaOfTutoriaListTutoria = em.merge(oldIdCitaOfTutoriaListTutoria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cita cita) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cita persistentCita = em.find(Cita.class, cita.getIdCita());
            Tutor tutorOld = persistentCita.getTutor();
            Tutor tutorNew = cita.getTutor();
            List<Tutoria> tutoriaListOld = persistentCita.getTutoriaList();
            List<Tutoria> tutoriaListNew = cita.getTutoriaList();
            List<String> illegalOrphanMessages = null;
            for (Tutoria tutoriaListOldTutoria : tutoriaListOld) {
                if (!tutoriaListNew.contains(tutoriaListOldTutoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tutoria " + tutoriaListOldTutoria + " since its idCita field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tutorNew != null) {
                tutorNew = em.getReference(tutorNew.getClass(), tutorNew.getIdPersona());
                cita.setTutor(tutorNew);
            }
            List<Tutoria> attachedTutoriaListNew = new ArrayList<Tutoria>();
            for (Tutoria tutoriaListNewTutoriaToAttach : tutoriaListNew) {
                tutoriaListNewTutoriaToAttach = em.getReference(tutoriaListNewTutoriaToAttach.getClass(), tutoriaListNewTutoriaToAttach.getIdTutoria());
                attachedTutoriaListNew.add(tutoriaListNewTutoriaToAttach);
            }
            tutoriaListNew = attachedTutoriaListNew;
            cita.setTutoriaList(tutoriaListNew);
            cita = em.merge(cita);
            if (tutorOld != null && !tutorOld.equals(tutorNew)) {
                tutorOld.getCitaList().remove(cita);
                tutorOld = em.merge(tutorOld);
            }
            if (tutorNew != null && !tutorNew.equals(tutorOld)) {
                tutorNew.getCitaList().add(cita);
                tutorNew = em.merge(tutorNew);
            }
            for (Tutoria tutoriaListNewTutoria : tutoriaListNew) {
                if (!tutoriaListOld.contains(tutoriaListNewTutoria)) {
                    Cita oldIdCitaOfTutoriaListNewTutoria = tutoriaListNewTutoria.getIdCita();
                    tutoriaListNewTutoria.setIdCita(cita);
                    tutoriaListNewTutoria = em.merge(tutoriaListNewTutoria);
                    if (oldIdCitaOfTutoriaListNewTutoria != null && !oldIdCitaOfTutoriaListNewTutoria.equals(cita)) {
                        oldIdCitaOfTutoriaListNewTutoria.getTutoriaList().remove(tutoriaListNewTutoria);
                        oldIdCitaOfTutoriaListNewTutoria = em.merge(oldIdCitaOfTutoriaListNewTutoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cita.getIdCita();
                if (findCita(id) == null) {
                    throw new NonexistentEntityException("The cita with id " + id + " no longer exists.");
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
            Cita cita;
            try {
                cita = em.getReference(Cita.class, id);
                cita.getIdCita();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cita with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Tutoria> tutoriaListOrphanCheck = cita.getTutoriaList();
            for (Tutoria tutoriaListOrphanCheckTutoria : tutoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Cita (" + cita + ") cannot be destroyed since the Tutoria " + tutoriaListOrphanCheckTutoria + " in its tutoriaList field has a non-nullable idCita field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tutor tutor = cita.getTutor();
            if (tutor != null) {
                tutor.getCitaList().remove(cita);
                tutor = em.merge(tutor);
            }
            em.remove(cita);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cita> findCitaEntities() {
        return findCitaEntities(true, -1, -1);
    }

    public List<Cita> findCitaEntities(int maxResults, int firstResult) {
        return findCitaEntities(false, maxResults, firstResult);
    }

    private List<Cita> findCitaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cita.class));
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

    public Cita findCita(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cita.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cita> rt = cq.from(Cita.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

```

### `src\control\Conexion.java`

```java
package control;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Conexion <T>{
    private T control;
    private CitaJpaController cCitas;
    private TutorJpaController cTutor;
    private TutoradoJpaController cTutorado;
    
    //Agrega más de ser necesarios
    
    EntityManagerFactory emf;
    
    public Conexion(){
        emf = Persistence.createEntityManagerFactory("tutoriasPU"); //Compila el objeto que tenemos
        if (control instanceof TutorJpaController){
            control = (T) (TutorJpaController) new TutorJpaController(emf);
        }
        else if (control instanceof CitaJpaController) {
            control = (T) (CitaJpaController) new CitaJpaController(emf);
        }
        else if (control instanceof TutoradoJpaController) {
            control = (T) (TutoradoJpaController) new TutoradoJpaController(emf);
        }
        else if (control instanceof TutoriaJpaController) {
            control = (T) (TutoriaJpaController) new TutoriaJpaController(emf);
        }
    }
    
    public T getControl(){
        return control;
    }
    
}

```

### `src\control\exceptions\IllegalOrphanException.java`

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

### `src\control\exceptions\NonexistentEntityException.java`

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

### `src\control\exceptions\PreexistingEntityException.java`

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

### `src\control\TutoradoJpaController.java`

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
import modelo.Tutor;
import modelo.Tutoria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Tutorado;

/**
 *
 * @author jesus
 */
public class TutoradoJpaController implements Serializable {

    public TutoradoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tutorado tutorado) {
        if (tutorado.getTutoriaList() == null) {
            tutorado.setTutoriaList(new ArrayList<Tutoria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutor tutor = tutorado.getTutor();
            if (tutor != null) {
                tutor = em.getReference(tutor.getClass(), tutor.getIdPersona());
                tutorado.setTutor(tutor);
            }
            List<Tutoria> attachedTutoriaList = new ArrayList<Tutoria>();
            for (Tutoria tutoriaListTutoriaToAttach : tutorado.getTutoriaList()) {
                tutoriaListTutoriaToAttach = em.getReference(tutoriaListTutoriaToAttach.getClass(), tutoriaListTutoriaToAttach.getIdTutoria());
                attachedTutoriaList.add(tutoriaListTutoriaToAttach);
            }
            tutorado.setTutoriaList(attachedTutoriaList);
            em.persist(tutorado);
            if (tutor != null) {
                tutor.getTutoradoList().add(tutorado);
                tutor = em.merge(tutor);
            }
            for (Tutoria tutoriaListTutoria : tutorado.getTutoriaList()) {
                Tutorado oldIdTutoradoOfTutoriaListTutoria = tutoriaListTutoria.getIdTutorado();
                tutoriaListTutoria.setIdTutorado(tutorado);
                tutoriaListTutoria = em.merge(tutoriaListTutoria);
                if (oldIdTutoradoOfTutoriaListTutoria != null) {
                    oldIdTutoradoOfTutoriaListTutoria.getTutoriaList().remove(tutoriaListTutoria);
                    oldIdTutoradoOfTutoriaListTutoria = em.merge(oldIdTutoradoOfTutoriaListTutoria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tutorado tutorado) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutorado persistentTutorado = em.find(Tutorado.class, tutorado.getIdTutorado());
            Tutor tutorOld = persistentTutorado.getTutor();
            Tutor tutorNew = tutorado.getTutor();
            List<Tutoria> tutoriaListOld = persistentTutorado.getTutoriaList();
            List<Tutoria> tutoriaListNew = tutorado.getTutoriaList();
            List<String> illegalOrphanMessages = null;
            for (Tutoria tutoriaListOldTutoria : tutoriaListOld) {
                if (!tutoriaListNew.contains(tutoriaListOldTutoria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Tutoria " + tutoriaListOldTutoria + " since its idTutorado field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (tutorNew != null) {
                tutorNew = em.getReference(tutorNew.getClass(), tutorNew.getIdPersona());
                tutorado.setTutor(tutorNew);
            }
            List<Tutoria> attachedTutoriaListNew = new ArrayList<Tutoria>();
            for (Tutoria tutoriaListNewTutoriaToAttach : tutoriaListNew) {
                tutoriaListNewTutoriaToAttach = em.getReference(tutoriaListNewTutoriaToAttach.getClass(), tutoriaListNewTutoriaToAttach.getIdTutoria());
                attachedTutoriaListNew.add(tutoriaListNewTutoriaToAttach);
            }
            tutoriaListNew = attachedTutoriaListNew;
            tutorado.setTutoriaList(tutoriaListNew);
            tutorado = em.merge(tutorado);
            if (tutorOld != null && !tutorOld.equals(tutorNew)) {
                tutorOld.getTutoradoList().remove(tutorado);
                tutorOld = em.merge(tutorOld);
            }
            if (tutorNew != null && !tutorNew.equals(tutorOld)) {
                tutorNew.getTutoradoList().add(tutorado);
                tutorNew = em.merge(tutorNew);
            }
            for (Tutoria tutoriaListNewTutoria : tutoriaListNew) {
                if (!tutoriaListOld.contains(tutoriaListNewTutoria)) {
                    Tutorado oldIdTutoradoOfTutoriaListNewTutoria = tutoriaListNewTutoria.getIdTutorado();
                    tutoriaListNewTutoria.setIdTutorado(tutorado);
                    tutoriaListNewTutoria = em.merge(tutoriaListNewTutoria);
                    if (oldIdTutoradoOfTutoriaListNewTutoria != null && !oldIdTutoradoOfTutoriaListNewTutoria.equals(tutorado)) {
                        oldIdTutoradoOfTutoriaListNewTutoria.getTutoriaList().remove(tutoriaListNewTutoria);
                        oldIdTutoradoOfTutoriaListNewTutoria = em.merge(oldIdTutoradoOfTutoriaListNewTutoria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tutorado.getIdTutorado();
                if (findTutorado(id) == null) {
                    throw new NonexistentEntityException("The tutorado with id " + id + " no longer exists.");
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
            Tutorado tutorado;
            try {
                tutorado = em.getReference(Tutorado.class, id);
                tutorado.getIdTutorado();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tutorado with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Tutoria> tutoriaListOrphanCheck = tutorado.getTutoriaList();
            for (Tutoria tutoriaListOrphanCheckTutoria : tutoriaListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Tutorado (" + tutorado + ") cannot be destroyed since the Tutoria " + tutoriaListOrphanCheckTutoria + " in its tutoriaList field has a non-nullable idTutorado field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Tutor tutor = tutorado.getTutor();
            if (tutor != null) {
                tutor.getTutoradoList().remove(tutorado);
                tutor = em.merge(tutor);
            }
            em.remove(tutorado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tutorado> findTutoradoEntities() {
        return findTutoradoEntities(true, -1, -1);
    }

    public List<Tutorado> findTutoradoEntities(int maxResults, int firstResult) {
        return findTutoradoEntities(false, maxResults, firstResult);
    }

    private List<Tutorado> findTutoradoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tutorado.class));
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

    public Tutorado findTutorado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tutorado.class, id);
        } finally {
            em.close();
        }
    }

    public int getTutoradoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tutorado> rt = cq.from(Tutorado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

```

### `src\control\TutoriaJpaController.java`

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
import modelo.Cita;
import modelo.Tutorado;
import modelo.Tutoria;

/**
 *
 * @author jesus
 */
public class TutoriaJpaController implements Serializable {

    public TutoriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tutoria tutoria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cita idCita = tutoria.getIdCita();
            if (idCita != null) {
                idCita = em.getReference(idCita.getClass(), idCita.getIdCita());
                tutoria.setIdCita(idCita);
            }
            Tutorado idTutorado = tutoria.getIdTutorado();
            if (idTutorado != null) {
                idTutorado = em.getReference(idTutorado.getClass(), idTutorado.getIdTutorado());
                tutoria.setIdTutorado(idTutorado);
            }
            em.persist(tutoria);
            if (idCita != null) {
                idCita.getTutoriaList().add(tutoria);
                idCita = em.merge(idCita);
            }
            if (idTutorado != null) {
                idTutorado.getTutoriaList().add(tutoria);
                idTutorado = em.merge(idTutorado);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tutoria tutoria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutoria persistentTutoria = em.find(Tutoria.class, tutoria.getIdTutoria());
            Cita idCitaOld = persistentTutoria.getIdCita();
            Cita idCitaNew = tutoria.getIdCita();
            Tutorado idTutoradoOld = persistentTutoria.getIdTutorado();
            Tutorado idTutoradoNew = tutoria.getIdTutorado();
            if (idCitaNew != null) {
                idCitaNew = em.getReference(idCitaNew.getClass(), idCitaNew.getIdCita());
                tutoria.setIdCita(idCitaNew);
            }
            if (idTutoradoNew != null) {
                idTutoradoNew = em.getReference(idTutoradoNew.getClass(), idTutoradoNew.getIdTutorado());
                tutoria.setIdTutorado(idTutoradoNew);
            }
            tutoria = em.merge(tutoria);
            if (idCitaOld != null && !idCitaOld.equals(idCitaNew)) {
                idCitaOld.getTutoriaList().remove(tutoria);
                idCitaOld = em.merge(idCitaOld);
            }
            if (idCitaNew != null && !idCitaNew.equals(idCitaOld)) {
                idCitaNew.getTutoriaList().add(tutoria);
                idCitaNew = em.merge(idCitaNew);
            }
            if (idTutoradoOld != null && !idTutoradoOld.equals(idTutoradoNew)) {
                idTutoradoOld.getTutoriaList().remove(tutoria);
                idTutoradoOld = em.merge(idTutoradoOld);
            }
            if (idTutoradoNew != null && !idTutoradoNew.equals(idTutoradoOld)) {
                idTutoradoNew.getTutoriaList().add(tutoria);
                idTutoradoNew = em.merge(idTutoradoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tutoria.getIdTutoria();
                if (findTutoria(id) == null) {
                    throw new NonexistentEntityException("The tutoria with id " + id + " no longer exists.");
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
            Tutoria tutoria;
            try {
                tutoria = em.getReference(Tutoria.class, id);
                tutoria.getIdTutoria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tutoria with id " + id + " no longer exists.", enfe);
            }
            Cita idCita = tutoria.getIdCita();
            if (idCita != null) {
                idCita.getTutoriaList().remove(tutoria);
                idCita = em.merge(idCita);
            }
            Tutorado idTutorado = tutoria.getIdTutorado();
            if (idTutorado != null) {
                idTutorado.getTutoriaList().remove(tutoria);
                idTutorado = em.merge(idTutorado);
            }
            em.remove(tutoria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tutoria> findTutoriaEntities() {
        return findTutoriaEntities(true, -1, -1);
    }

    public List<Tutoria> findTutoriaEntities(int maxResults, int firstResult) {
        return findTutoriaEntities(false, maxResults, firstResult);
    }

    private List<Tutoria> findTutoriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tutoria.class));
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

    public Tutoria findTutoria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tutoria.class, id);
        } finally {
            em.close();
        }
    }

    public int getTutoriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tutoria> rt = cq.from(Tutoria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

```

### `src\control\TutorJpaController.java`

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
import modelo.Tutorado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import modelo.Cita;
import modelo.Tutor;

/**
 *
 * @author jesus
 */
public class TutorJpaController implements Serializable {

    public TutorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tutor tutor) {
        if (tutor.getTutoradoList() == null) {
            tutor.setTutoradoList(new ArrayList<Tutorado>());
        }
        if (tutor.getCitaList() == null) {
            tutor.setCitaList(new ArrayList<Cita>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Tutorado> attachedTutoradoList = new ArrayList<Tutorado>();
            for (Tutorado tutoradoListTutoradoToAttach : tutor.getTutoradoList()) {
                tutoradoListTutoradoToAttach = em.getReference(tutoradoListTutoradoToAttach.getClass(), tutoradoListTutoradoToAttach.getIdTutorado());
                attachedTutoradoList.add(tutoradoListTutoradoToAttach);
            }
            tutor.setTutoradoList(attachedTutoradoList);
            List<Cita> attachedCitaList = new ArrayList<Cita>();
            for (Cita citaListCitaToAttach : tutor.getCitaList()) {
                citaListCitaToAttach = em.getReference(citaListCitaToAttach.getClass(), citaListCitaToAttach.getIdCita());
                attachedCitaList.add(citaListCitaToAttach);
            }
            tutor.setCitaList(attachedCitaList);
            em.persist(tutor);
            for (Tutorado tutoradoListTutorado : tutor.getTutoradoList()) {
                Tutor oldTutorOfTutoradoListTutorado = tutoradoListTutorado.getTutor();
                tutoradoListTutorado.setTutor(tutor);
                tutoradoListTutorado = em.merge(tutoradoListTutorado);
                if (oldTutorOfTutoradoListTutorado != null) {
                    oldTutorOfTutoradoListTutorado.getTutoradoList().remove(tutoradoListTutorado);
                    oldTutorOfTutoradoListTutorado = em.merge(oldTutorOfTutoradoListTutorado);
                }
            }
            for (Cita citaListCita : tutor.getCitaList()) {
                Tutor oldTutorOfCitaListCita = citaListCita.getTutor();
                citaListCita.setTutor(tutor);
                citaListCita = em.merge(citaListCita);
                if (oldTutorOfCitaListCita != null) {
                    oldTutorOfCitaListCita.getCitaList().remove(citaListCita);
                    oldTutorOfCitaListCita = em.merge(oldTutorOfCitaListCita);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tutor tutor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tutor persistentTutor = em.find(Tutor.class, tutor.getIdPersona());
            List<Tutorado> tutoradoListOld = persistentTutor.getTutoradoList();
            List<Tutorado> tutoradoListNew = tutor.getTutoradoList();
            List<Cita> citaListOld = persistentTutor.getCitaList();
            List<Cita> citaListNew = tutor.getCitaList();
            List<Tutorado> attachedTutoradoListNew = new ArrayList<Tutorado>();
            for (Tutorado tutoradoListNewTutoradoToAttach : tutoradoListNew) {
                tutoradoListNewTutoradoToAttach = em.getReference(tutoradoListNewTutoradoToAttach.getClass(), tutoradoListNewTutoradoToAttach.getIdTutorado());
                attachedTutoradoListNew.add(tutoradoListNewTutoradoToAttach);
            }
            tutoradoListNew = attachedTutoradoListNew;
            tutor.setTutoradoList(tutoradoListNew);
            List<Cita> attachedCitaListNew = new ArrayList<Cita>();
            for (Cita citaListNewCitaToAttach : citaListNew) {
                citaListNewCitaToAttach = em.getReference(citaListNewCitaToAttach.getClass(), citaListNewCitaToAttach.getIdCita());
                attachedCitaListNew.add(citaListNewCitaToAttach);
            }
            citaListNew = attachedCitaListNew;
            tutor.setCitaList(citaListNew);
            tutor = em.merge(tutor);
            for (Tutorado tutoradoListOldTutorado : tutoradoListOld) {
                if (!tutoradoListNew.contains(tutoradoListOldTutorado)) {
                    tutoradoListOldTutorado.setTutor(null);
                    tutoradoListOldTutorado = em.merge(tutoradoListOldTutorado);
                }
            }
            for (Tutorado tutoradoListNewTutorado : tutoradoListNew) {
                if (!tutoradoListOld.contains(tutoradoListNewTutorado)) {
                    Tutor oldTutorOfTutoradoListNewTutorado = tutoradoListNewTutorado.getTutor();
                    tutoradoListNewTutorado.setTutor(tutor);
                    tutoradoListNewTutorado = em.merge(tutoradoListNewTutorado);
                    if (oldTutorOfTutoradoListNewTutorado != null && !oldTutorOfTutoradoListNewTutorado.equals(tutor)) {
                        oldTutorOfTutoradoListNewTutorado.getTutoradoList().remove(tutoradoListNewTutorado);
                        oldTutorOfTutoradoListNewTutorado = em.merge(oldTutorOfTutoradoListNewTutorado);
                    }
                }
            }
            for (Cita citaListOldCita : citaListOld) {
                if (!citaListNew.contains(citaListOldCita)) {
                    citaListOldCita.setTutor(null);
                    citaListOldCita = em.merge(citaListOldCita);
                }
            }
            for (Cita citaListNewCita : citaListNew) {
                if (!citaListOld.contains(citaListNewCita)) {
                    Tutor oldTutorOfCitaListNewCita = citaListNewCita.getTutor();
                    citaListNewCita.setTutor(tutor);
                    citaListNewCita = em.merge(citaListNewCita);
                    if (oldTutorOfCitaListNewCita != null && !oldTutorOfCitaListNewCita.equals(tutor)) {
                        oldTutorOfCitaListNewCita.getCitaList().remove(citaListNewCita);
                        oldTutorOfCitaListNewCita = em.merge(oldTutorOfCitaListNewCita);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tutor.getIdPersona();
                if (findTutor(id) == null) {
                    throw new NonexistentEntityException("The tutor with id " + id + " no longer exists.");
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
            Tutor tutor;
            try {
                tutor = em.getReference(Tutor.class, id);
                tutor.getIdPersona();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tutor with id " + id + " no longer exists.", enfe);
            }
            List<Tutorado> tutoradoList = tutor.getTutoradoList();
            for (Tutorado tutoradoListTutorado : tutoradoList) {
                tutoradoListTutorado.setTutor(null);
                tutoradoListTutorado = em.merge(tutoradoListTutorado);
            }
            List<Cita> citaList = tutor.getCitaList();
            for (Cita citaListCita : citaList) {
                citaListCita.setTutor(null);
                citaListCita = em.merge(citaListCita);
            }
            em.remove(tutor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tutor> findTutorEntities() {
        return findTutorEntities(true, -1, -1);
    }

    public List<Tutor> findTutorEntities(int maxResults, int firstResult) {
        return findTutorEntities(false, maxResults, firstResult);
    }

    private List<Tutor> findTutorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tutor.class));
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

    public Tutor findTutor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tutor.class, id);
        } finally {
            em.close();
        }
    }

    public int getTutorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tutor> rt = cq.from(Tutor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}

```

### `src\modelo\Cita.java`

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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "cita")
@NamedQueries({
    @NamedQuery(name = "Cita.findAll", query = "SELECT c FROM Cita c"),
    @NamedQuery(name = "Cita.findByIdCita", query = "SELECT c FROM Cita c WHERE c.idCita = :idCita"),
    @NamedQuery(name = "Cita.findByFecha", query = "SELECT c FROM Cita c WHERE c.fecha = :fecha"),
    @NamedQuery(name = "Cita.findByHora", query = "SELECT c FROM Cita c WHERE c.hora = :hora"),
    @NamedQuery(name = "Cita.findByAsunto", query = "SELECT c FROM Cita c WHERE c.asunto = :asunto")})
public class Cita implements Serializable {

    @Basic(optional = false)
    @Column(name = "estado")
    private String estado;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idCita")
    private List<Tutoria> tutoriaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cita")
    private Integer idCita;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    private Date fecha;
    @Column(name = "hora")
    private Integer hora;
    @Column(name = "asunto")
    private String asunto;
    @JoinColumns({
        @JoinColumn(name = "id_tutor", referencedColumnName = "id_persona"),
        @JoinColumn(name = "id_tutor", referencedColumnName = "id_persona")})
    @ManyToOne
    private Tutor tutor;

    public Cita() {
    }

    public Cita(Integer idCita) {
        this.idCita = idCita;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Integer getHora() {
        return hora;
    }

    public void setHora(Integer hora) {
        this.hora = hora;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCita != null ? idCita.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cita)) {
            return false;
        }
        Cita other = (Cita) object;
        if ((this.idCita == null && other.idCita != null) || (this.idCita != null && !this.idCita.equals(other.idCita))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Cita[ idCita=" + idCita + " ]";
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public List<Tutoria> getTutoriaList() {
        return tutoriaList;
    }

    public void setTutoriaList(List<Tutoria> tutoriaList) {
        this.tutoriaList = tutoriaList;
    }
    
}

```

### `src\modelo\DatosTablaCitas.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 *
 * @author jesus
 */
public class DatosTablaCitas {
    private Tutorado tutorado;
    //private JCheckBox asistencia; //boolean
   //    private JCheckBox asistencia;
    private String accion; //String
    //private JCheckBox asistencia; 
    private boolean asistencia; 
    //Voy a llenar un arreglo de datos tutoria, los cuales estarán formados de lo anterior
    
    public DatosTablaCitas(Tutorado tutorado){
        this.tutorado = tutorado;
        //this.asistencia = new JCheckBox();
        this.asistencia = false;
        //this.accion = new JTextField();
        this.accion = new String();
    }

    public Tutorado getTutorado() {
        return tutorado;
    }

    public void setTutorado(Tutorado tutorado) {
        this.tutorado = tutorado;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }
    public boolean getAsistencia(){
        return asistencia;
    }
}

```

### `src\modelo\MTablaCita.java`

```java
package modelo;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial") // Añade esto si no tienes serialVersionUID y quieres quitar el warning
public class MTablaCita extends AbstractTableModel {

    private ArrayList<DatosTablaCitas> datosCitas;
    private final String[] encabezados = {"Tutorado", "Asistencia", "Acción"};
    private final Class<?>[] clasesColumnas = {String.class, Boolean.class, String.class};

    public MTablaCita(ArrayList<DatosTablaCitas> listaDatosCitas) {
        this.datosCitas = (listaDatosCitas != null) ? listaDatosCitas : new ArrayList<>();
    }

    @Override
    public String getColumnName(int column) {
        return encabezados[column];
    }

    @Override
    public int getRowCount() {
        // Una fila extra para los totales
        return datosCitas.size() + 1;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        // Para la última fila, si quieres mostrar texto, asegúrate que la columna pueda manejar String.
        // Como la columna "Acción" (índice 2) ya es String, está bien.
        return clasesColumnas[columnIndex];
    }

    /**
     * Método para calcular el total de asistencias.
     */
    public int calcularTotalAsistencias() {
        int totalAsistencias = 0;
        for (DatosTablaCitas dato : datosCitas) {
            if (dato.getAsistencia()) { // Asume que getAsistencia() devuelve boolean
                totalAsistencias++;
            }
        }
        return totalAsistencias;
    }

    @Override
    // Dentro de MTablaCita.java
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex == datosCitas.size()) { // Fila de totales
            switch (columnIndex) {
                case 0: // Columna "Tutorado"
                    return "Total Asistencias:"; // Etiqueta
                case 1: // Columna "Asistencia"
                    // Para la fila de totales, esta columna no debe mostrar un checkbox activo.
                    // Devolver null o un String vacío si la columna espera Boolean pero no quieres un checkbox.
                    // O, si quieres que esté vacía y no muestre el renderer de Boolean,
                    // podrías necesitar un renderer personalizado para esta celda específica,
                    // o simplemente devolver un String vacío y asegurar que getColumnClass
                    // puede manejarlo (aunque getColumnClass se define por columna, no por celda).
                    // Lo más simple es devolver null para que el renderer de Boolean muestre una celda vacía/desmarcada.
                    return null; // O false, si quieres un checkbox desmarcado
                case 2: // Columna "Acción"
                    return "" + calcularTotalAsistencias(); // Mostrar el conteo como String aquí
                default:
                    return "";
            }
        }

        // Si no es la fila de totales, es una fila de datos normal
        if (rowIndex < 0 || rowIndex >= datosCitas.size()) {
            return null;
        }

        DatosTablaCitas fila = datosCitas.get(rowIndex);
        switch (columnIndex) {
            case 0: // Tutorado
                return (fila.getTutorado() != null) ? fila.getTutorado().getNombre() : "N/A";
            case 1: // Asistencia
                return fila.getAsistencia();
            case 2: // Acción
                return fila.getAccion();
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // La fila de totales no es editable
        if (rowIndex == datosCitas.size()) {
            return false;
        }

        // Lógica de editabilidad para las filas de datos (como la tenías antes)
        if (rowIndex < 0 || rowIndex >= datosCitas.size()) {
            return false;
        }
        if (columnIndex == 1) { // Columna "Asistencia"
            return true;
        }
        if (columnIndex == 2) { // Columna "Acción"
            Boolean asistenciaMarcada = datosCitas.get(rowIndex).getAsistencia();
            return asistenciaMarcada != null && asistenciaMarcada;
        }
        return false;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        // No permitir edición en la fila de totales
        if (rowIndex == datosCitas.size()) {
            return;
        }

        if (rowIndex < 0 || rowIndex >= datosCitas.size()) {
            return;
        }

        DatosTablaCitas fila = datosCitas.get(rowIndex);
        boolean asistenciaCambioEstado = false;

        if (columnIndex == 1) { // Cambió la "Asistencia"
            if (aValue instanceof Boolean) {
                boolean nuevaAsistencia = (Boolean) aValue;
                if (fila.getAsistencia() != nuevaAsistencia) {
                    fila.setAsistencia(nuevaAsistencia);
                    fireTableCellUpdated(rowIndex, columnIndex);
                    asistenciaCambioEstado = true;

                    if (!nuevaAsistencia) {
                        String valorPorDefectoAccion = "Sin acción";
                        if (!valorPorDefectoAccion.equals(fila.getAccion())) {
                            fila.setAccion(valorPorDefectoAccion);
                            fireTableCellUpdated(rowIndex, 2);
                        }
                    }
                    // Notificar que la fila de totales necesita ser actualizada
                    fireTableCellUpdated(datosCitas.size(), 0); // Para la etiqueta "Total Asistencias:"
                    fireTableCellUpdated(datosCitas.size(), 1); // Para el valor del conteo
                }
            }
        } else if (columnIndex == 2) { // Cambió la "Acción"
            if (aValue instanceof String) {
                if (!aValue.equals(fila.getAccion())) {
                    fila.setAccion((String) aValue);
                    fireTableCellUpdated(rowIndex, columnIndex);
                }
            }
        }

        if (asistenciaCambioEstado) {
        fireTableRowsUpdated(rowIndex, rowIndex); 
        fireTableCellUpdated(datosCitas.size(), 0); // Etiqueta en col 0
        fireTableCellUpdated(datosCitas.size(), 2); // Conteo en col 2
    }
    }

    public void actualizarListaDatos(ArrayList<DatosTablaCitas> nuevosDatos) {
        this.datosCitas = (nuevosDatos != null) ? nuevosDatos : new ArrayList<>();
        fireTableDataChanged(); // Esto repintará toda la tabla, incluyendo la fila de totales
    }
}
```

### `src\modelo\MTcita.java`

```java
package modelo;

import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Cita;

public class MTcita extends AbstractTableModel {
    
    private List<Cita> citas;
    private final String[] encabezados = {
        "ID Cita", 
        "Fecha", 
        "Hora", 
        "Asunto", 
        "Estado", 
        "Tutor"
    };
    
    public MTcita(List<Cita> citas) {
        this.citas = citas;
    }

    @Override
    public int getRowCount() {
        return citas != null ? citas.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Cita cita = citas.get(rowIndex);
        
        switch (columnIndex) {
            case 0:
                return cita.getIdCita();
            case 1:
                return formatFecha(cita.getFecha());
            case 2:
                return formatHora(cita.getHora());
            case 3:
                return cita.getAsunto();
            case 4:
                return cita.getEstado();
            case 5:
                return getNumTargeta(cita);
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return encabezados[column];
    }
    
    // Métodos auxiliares
    private String formatFecha(java.util.Date fecha) {
        if(fecha == null) return "N/A";
        return new SimpleDateFormat("dd/MM/yyyy").format(fecha);
    }
    
    private String formatHora(Integer hora) {
        if(hora == null) return "N/A";
        return String.format("%02d:00", hora); // Formato 14 → "14:00"
    }
    
    private int getNumTargeta(Cita cita) {
        if(cita.getTutor() == null) return 0;
        return cita.getTutor().getNumTarjeta();
    }
}
```

### `src\modelo\MTtutor.java`

```java
package modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Tutor;

public class MTtutor extends AbstractTableModel{
    
    private List<Tutor> lt;
    private String encabezados[] = {"No. de Tarjeta", " Nombre ", " Carrera ", " Dias ", " Horario ", "No. de tutorados"};
    
    public MTtutor(List<Tutor> tutores){
        lt = tutores;
    }

    @Override
    public int getRowCount() {
        if(lt != null)
            return lt.size();
        return 0;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return lt.get(rowIndex).getNumTarjeta();
            case 1:
                return lt.get(rowIndex).getNombre();
            case 2:
                return lt.get(rowIndex).getCarrera();
            case 3:
                return lt.get(rowIndex).getDias();
            case 4:
                return lt.get(rowIndex).getHoras();
            case 5:
                return lt.get(rowIndex).getNumeroDeTutorados();
            default:
                return null;
        }
        
    }
    @Override
    public String getColumnName(int column) {
        return encabezados[column];
    }
}

```

### `src\modelo\MTtutorado.java`

```java
package modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Tutorado;

public class MTtutorado extends AbstractTableModel {
    
    private List<Tutorado> lt;
    private String[] encabezados = {"Matrícula", "Nombre", "Género", "Días", 
                                  "Fecha Nacimiento", "Tutor Asignado"};
    
    public MTtutorado(List<Tutorado> tutorados) {
        lt = tutorados;
    }

    @Override
    public int getRowCount() {
        return lt != null ? lt.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tutorado tutorado = lt.get(rowIndex);
        switch (columnIndex) {
            case 0: return tutorado.getNc();
            case 1: return tutorado.getNombre();
            case 2: return tutorado.getGenero();
            case 3: return tutorado.getDias();
            case 4: return tutorado.getFechaNacimiento();
            case 5: return (tutorado.getTutor() != null) ? 
                          tutorado.getTutor().getNumTarjeta() : "Sin tutor";
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return encabezados[column];
    }
}
```

### `src\modelo\MTtutoria.java`

```java
package modelo;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelo.Tutoria;

public class MTtutoria extends AbstractTableModel {
    
    private List<Tutoria> tutorias;
    private final String[] encabezados = {
        "ID Tutoria", 
        "Acciones", 
        "Cita (Asunto)", 
        "Tutorado"
    };
    
    public MTtutoria(List<Tutoria> tutorias) {
        this.tutorias = tutorias;
    }

    @Override
    public int getRowCount() {
        return tutorias != null ? tutorias.size() : 0;
    }

    @Override
    public int getColumnCount() {
        return encabezados.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Tutoria tutoria = tutorias.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return tutoria.getIdTutoria();
            case 1: return tutoria.getAcciones();
            case 2: return obtenerAsuntoCita(tutoria);
            case 3: return obtenerNombreTutorado(tutoria);
            default: return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return encabezados[column];
    }
    
    // Métodos auxiliares para obtener datos relacionados
    private String obtenerAsuntoCita(Tutoria tutoria) {
        if(tutoria.getIdCita() == null) return "N/A";
        return tutoria.getIdCita().getAsunto();
    }
    
    private String obtenerNombreTutorado(Tutoria tutoria) {
        if(tutoria.getIdTutorado() == null) return "N/A";
        return tutoria.getIdTutorado().getNombre();
    }
    public void setTutores(List<Tutoria> nuevasTutorias) {
        this.tutorias = nuevasTutorias;
        fireTableDataChanged();
    }
}
```

### `src\modelo\Tutor.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "tutor")
@NamedQueries({
    @NamedQuery(name = "Tutor.findAll", query = "SELECT t FROM Tutor t"),
    @NamedQuery(name = "Tutor.findByIdPersona", query = "SELECT t FROM Tutor t WHERE t.idPersona = :idPersona"),
    @NamedQuery(name = "Tutor.findByNumTarjeta", query = "SELECT t FROM Tutor t WHERE t.numTarjeta = :numTarjeta"),
    @NamedQuery(name = "Tutor.findByNombre", query = "SELECT t FROM Tutor t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tutor.findByCarrera", query = "SELECT t FROM Tutor t WHERE t.carrera = :carrera"),
    @NamedQuery(name = "Tutor.findByDias", query = "SELECT t FROM Tutor t WHERE t.dias = :dias"),
    @NamedQuery(name = "Tutor.findByHoras", query = "SELECT t FROM Tutor t WHERE t.horas = :horas")})
public class Tutor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_persona")
    private Integer idPersona;
    @Column(name = "num_tarjeta")
    private Integer numTarjeta;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "carrera")
    private String carrera;
    @Column(name = "dias")
    private String dias;
    @Column(name = "horas")
    private String horas;
    @OneToMany(mappedBy = "tutor")
    private List<Tutorado> tutoradoList;
    @OneToMany(mappedBy = "tutor")
    private List<Cita> citaList;

    public Tutor() {
    }

    public Tutor(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Integer idPersona) {
        this.idPersona = idPersona;
    }

    public Integer getNumTarjeta() {
        return numTarjeta;
    }

    public void setNumTarjeta(Integer numTarjeta) {
        this.numTarjeta = numTarjeta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public String getHoras() {
        return horas;
    }

    public void setHoras(String horas) {
        this.horas = horas;
    }
    
    // Metodo en clase tutor que regresa el Num de tutorados
    public int getNumeroDeTutorados() {
        if (tutoradoList != null) {
            return tutoradoList.size();
        } else {
            return 0;
        }
    }
    
    // Regresa la lista de tutorados que tiene el tutor
    public List<Tutorado> getTutoradoList() {
        return tutoradoList;
    }

    public void setTutoradoList(List<Tutorado> tutoradoList) {
        this.tutoradoList = tutoradoList;
    }

    public List<Cita> getCitaList() {
        return citaList;
    }

    public void setCitaList(List<Cita> citaList) {
        this.citaList = citaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPersona != null ? idPersona.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tutor)) {
            return false;
        }
        Tutor other = (Tutor) object;
        if ((this.idPersona == null && other.idPersona != null) || (this.idPersona != null && !this.idPersona.equals(other.idPersona))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Tutor[ idPersona=" + idPersona + " ]";
    }
    
}

```

### `src\modelo\Tutorado.java`

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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "tutorado")
@NamedQueries({
    @NamedQuery(name = "Tutorado.findAll", query = "SELECT t FROM Tutorado t"),
    @NamedQuery(name = "Tutorado.findByIdTutorado", query = "SELECT t FROM Tutorado t WHERE t.idTutorado = :idTutorado"),
    @NamedQuery(name = "Tutorado.findByNc", query = "SELECT t FROM Tutorado t WHERE t.nc = :nc"),
    @NamedQuery(name = "Tutorado.findByNombre", query = "SELECT t FROM Tutorado t WHERE t.nombre = :nombre"),
    @NamedQuery(name = "Tutorado.findByGenero", query = "SELECT t FROM Tutorado t WHERE t.genero = :genero"),
    @NamedQuery(name = "Tutorado.findByDias", query = "SELECT t FROM Tutorado t WHERE t.dias = :dias"),
    @NamedQuery(name = "Tutorado.findByFechaNacimiento", query = "SELECT t FROM Tutorado t WHERE t.fechaNacimiento = :fechaNacimiento")})
public class Tutorado implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idTutorado")
    private List<Tutoria> tutoriaList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tutorado")
    private Integer idTutorado;
    @Column(name = "nc")
    private String nc;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "genero")
    private Character genero;
    @Column(name = "dias")
    private String dias;
    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaNacimiento;
    @JoinColumns({
        @JoinColumn(name = "id_tutor", referencedColumnName = "id_persona"),
        @JoinColumn(name = "id_tutor", referencedColumnName = "id_persona")})
    @ManyToOne
    private Tutor tutor;

    public Tutorado() {
    }

    public Tutorado(Integer idTutorado) {
        this.idTutorado = idTutorado;
    }

    public Integer getIdTutorado() {
        return idTutorado;
    }

    public void setIdTutorado(Integer idTutorado) {
        this.idTutorado = idTutorado;
    }

    public String getNc() {
        return nc;
    }

    public void setNc(String nc) {
        this.nc = nc;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Character getGenero() {
        return genero;
    }

    public void setGenero(Character genero) {
        this.genero = genero;
    }

    public String getDias() {
        return dias;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTutorado != null ? idTutorado.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tutorado)) {
            return false;
        }
        Tutorado other = (Tutorado) object;
        if ((this.idTutorado == null && other.idTutorado != null) || (this.idTutorado != null && !this.idTutorado.equals(other.idTutorado))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Tutorado[ idTutorado=" + idTutorado + " ]";
    }

    public List<Tutoria> getTutoriaList() {
        return tutoriaList;
    }

    public void setTutoriaList(List<Tutoria> tutoriaList) {
        this.tutoriaList = tutoriaList;
    }
    
}

```

### `src\modelo\Tutoria.java`

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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author jesus
 */
@Entity
@Table(name = "tutoria")
@NamedQueries({
    @NamedQuery(name = "Tutoria.findAll", query = "SELECT t FROM Tutoria t"),
    @NamedQuery(name = "Tutoria.findByIdTutoria", query = "SELECT t FROM Tutoria t WHERE t.idTutoria = :idTutoria")})
public class Tutoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_tutoria")
    private Integer idTutoria;
    @Basic(optional = false)
    @Lob
    @Column(name = "acciones")
    private String acciones;
    @JoinColumn(name = "id_cita", referencedColumnName = "id_cita")
    @ManyToOne(optional = false)
    private Cita idCita;
    @JoinColumn(name = "id_tutorado", referencedColumnName = "id_tutorado")
    @ManyToOne(optional = false)
    private Tutorado idTutorado;

    public Tutoria() {
    }

    public Tutoria(Integer idTutoria) {
        this.idTutoria = idTutoria;
    }

    public Tutoria(Integer idTutoria, String acciones) {
        this.idTutoria = idTutoria;
        this.acciones = acciones;
    }

    public Integer getIdTutoria() {
        return idTutoria;
    }

    public void setIdTutoria(Integer idTutoria) {
        this.idTutoria = idTutoria;
    }

    public String getAcciones() {
        return acciones;
    }

    public void setAcciones(String acciones) {
        this.acciones = acciones;
    }

    public Cita getIdCita() {
        return idCita;
    }

    public void setIdCita(Cita idCita) {
        this.idCita = idCita;
    }

    public Tutorado getIdTutorado() {
        return idTutorado;
    }

    public void setIdTutorado(Tutorado idTutorado) {
        this.idTutorado = idTutorado;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idTutoria != null ? idTutoria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tutoria)) {
            return false;
        }
        Tutoria other = (Tutoria) object;
        if ((this.idTutoria == null && other.idTutoria != null) || (this.idTutoria != null && !this.idTutoria.equals(other.idTutoria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.Tutoria[ idTutoria=" + idTutoria + " ]";
    }
    
}

```

### `src\vista\IMenuTutorias.java`

```java
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tutorias;

import com.formdev.flatlaf.themes.FlatMacDarkLaf;

import Control.AdmDatos;
import Control.CitaJpaController;
import Control.TutorJpaController;
import Control.TutoradoJpaController;
import Control.TutoriaJpaController;
import Modelo.Cita;
import Modelo.DatosTablaCitas;
import Modelo.MTablaCita;
import Modelo.MTtutor;
import Modelo.Tutor;
import Modelo.Tutorado;
import Modelo.Tutoria;
import static Modelo.Tutoria_.cita;
import java.awt.Image;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;

/**
 *
 * @author Hp EliteBook
 */
public class IMenuTutorias extends javax.swing.JFrame {
private Tutor tutor;
private TutorJpaController cTutor;
private List<Tutor> tutores;
private Tutorado tutorado;
private TutoradoJpaController cTutorado;
private List<Tutorado> tutorados;
private MTtutor mtt;
private DefaultListModel mestudiantes, mtutorados;
private Map<String,Tutorado> tutorado_nom = new HashMap<>();
private final String SELECCIONA = "Selecciona Tutor";
private final String SELECCIONADO  = "Tutor Seleccionado";
private String[] Carreras = {"Sistemas","Civil","Administracion", "Electronica"};
private SpinnerNumberModel snm;
private int max = 50; //Regla del negocioy
private int min = 0;
private Cita cita;
private List<Cita> citas;
private AdmDatos adm;
private List<Tutorado> tutorados_s;
private Map<String,Tutor> tutor_nom = new HashMap<>();
private Map<Integer,Cita> cita_horas = new HashMap<>();
private MTablaCita modTabCita;
private ArrayList<DatosTablaCitas> datosCitas;
private CitaJpaController cCita;
private Tutoria tutoria;
private TutoriaJpaController cTutoria;
private List<Tutoria> tutorias;
private Date fecha;
private ImageIcon iconMiniatura;

    /**
     * Creates new form IMenuTutorias
     */
    public IMenuTutorias() {
        initComponents();
        adm = new AdmDatos();
        cTutor = new TutorJpaController(AdmDatos.getEntityManagerFactory());
        tutores = cTutor.findTutorEntities();
        cTutorado = new TutoradoJpaController(AdmDatos.getEntityManagerFactory());
        tutorados = cTutorado.findTutoradoEntities();
        cCita = new CitaJpaController(AdmDatos.getEntityManagerFactory());
        citas = cCita.findCitaEntities();
        cTutoria =  new TutoriaJpaController(AdmDatos.getEntityManagerFactory());
        // Inicializacion de Agregar Tutor
        mtt = new MTtutor(tutores);
        ttutores.setModel(mtt);
        configurarComboBox();
        configurarJSpinner();
        // Inicializacion de Crear Tutoria
        FechaActual.setText("Fecha: " + getFecha());
        cargarTutoresCita();   
        //Inicializacion de Asignar Tutor
        cargarTutores();
        cargarEstudiantes();
        estudiantes.setModel(mestudiantes);
        estudiantes.setSelectionMode(1);
        tutoradoss.setModel(mtutorados);
        iconMiniatura = redimensionarImagen("/tutorias/Tutor.jpeg", 350, 500);
        ImagenTutor.setIcon(iconMiniatura);
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Menu = new javax.swing.JTabbedPane();
        PTutorados = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        PTutores = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        PInicio = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ttutores = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        ttutores1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        ttutores2 = new javax.swing.JTable();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        ttutores3 = new javax.swing.JTable();
        jLabel11 = new javax.swing.JLabel();
        AgregarTutor = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        dn = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        ddh = new javax.swing.JTextField();
        dnt = new javax.swing.JSpinner();
        cbCarreras = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        ImagenTutor = new javax.swing.JLabel();
        AsignarTutor = new javax.swing.JPanel();
        moverTut = new javax.swing.JButton();
        asignarTutorado = new javax.swing.JButton();
        aceptarTutor = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        bct = new javax.swing.JButton();
        LTutores = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        estudiantes = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        tutoradoss = new javax.swing.JList<>();
        moverEst = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        PCitas = new javax.swing.JPanel();
        jTabbedPane3 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        PTutorias = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        RegistroTutoria = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        ltutorados = new javax.swing.JTable();
        jLabel20 = new javax.swing.JLabel();
        ListaTutor = new javax.swing.JComboBox<>();
        Aceptar = new javax.swing.JButton();
        FechaActual = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Menu.setBackground(new java.awt.Color(102, 0, 0));
        Menu.setTabPlacement(javax.swing.JTabbedPane.LEFT);
        Menu.setFont(new java.awt.Font("Roboto", 0, 24)); // NOI18N

        jPanel4.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N

        jLabel12.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel12.setText("Buscar Citas");

        jTextField1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jTextField1.setText("jTextField1");
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel21.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jLabel21.setText("Ingrese No Control");

        jButton2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        jButton2.setText("Buscar");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane8.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(200, 200, 200)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel21)
                                .addGap(44, 44, 44)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(48, 48, 48)
                        .addComponent(jButton2))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(289, 289, 289)
                        .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(333, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2)
                    .addComponent(jLabel21))
                .addGap(113, 113, 113)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(211, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("VerCitas", jPanel4);

        javax.swing.GroupLayout PTutoradosLayout = new javax.swing.GroupLayout(PTutorados);
        PTutorados.setLayout(PTutoradosLayout);
        PTutoradosLayout.setHorizontalGroup(
            PTutoradosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PTutoradosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1))
        );
        PTutoradosLayout.setVerticalGroup(
            PTutoradosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        Menu.addTab("Tutorado", PTutorados);

        jTabbedPane2.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N

        ttutores.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ttutores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane1.setViewportView(ttutores);

        jLabel2.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel2.setText("Control de Registrados");

        jLabel3.setFont(new java.awt.Font("Dialog", 3, 18)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Instituto Tecnológico de Oaxaca");

        jLabel4.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel4.setText("Tutores Registrados");

        ttutores1.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ttutores1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane5.setViewportView(ttutores1);

        jLabel5.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel5.setText("Estudiantes Registrados");

        ttutores2.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ttutores2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane6.setViewportView(ttutores2);

        jLabel6.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel6.setText("Citas Creadas");

        ttutores3.setFont(new java.awt.Font("Dialog", 0, 14)); // NOI18N
        ttutores3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        jScrollPane7.setViewportView(ttutores3);

        jLabel11.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel11.setText("Tutorias Creadas");

        javax.swing.GroupLayout PInicioLayout = new javax.swing.GroupLayout(PInicio);
        PInicio.setLayout(PInicioLayout);
        PInicioLayout.setHorizontalGroup(
            PInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PInicioLayout.createSequentialGroup()
                .addGap(130, 130, 130)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addGap(157, 157, 157))
            .addGroup(PInicioLayout.createSequentialGroup()
                .addGroup(PInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PInicioLayout.createSequentialGroup()
                        .addGap(747, 747, 747)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PInicioLayout.createSequentialGroup()
                        .addGap(404, 404, 404)
                        .addComponent(jLabel2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PInicioLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(PInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PInicioLayout.createSequentialGroup()
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PInicioLayout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PInicioLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(190, 190, 190))
            .addGroup(PInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(PInicioLayout.createSequentialGroup()
                    .addGap(198, 198, 198)
                    .addComponent(jLabel11)
                    .addContainerGap(703, Short.MAX_VALUE)))
        );
        PInicioLayout.setVerticalGroup(
            PInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PInicioLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel2)
                .addGap(56, 56, 56)
                .addGroup(PInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(PInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 123, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addGap(48, 48, 48)
                .addGroup(PInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PInicioLayout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3))
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
            .addGroup(PInicioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PInicioLayout.createSequentialGroup()
                    .addContainerGap(439, Short.MAX_VALUE)
                    .addComponent(jLabel11)
                    .addGap(226, 226, 226)))
        );

        jTabbedPane2.addTab("Control", PInicio);

        jLabel7.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel7.setText("No Targeta:");

        jLabel8.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel8.setText("Nombre:");

        dn.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        dn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dnActionPerformed(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel9.setText("Carrera");

        jLabel10.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel10.setText("Dias/Horas:");

        ddh.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        ddh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ddhActionPerformed(evt);
            }
        });

        dnt.setFont(new java.awt.Font("Times New Roman", 1, 18)); // NOI18N

        cbCarreras.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        cbCarreras.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbCarreras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbCarrerasActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jButton1.setText("Agregar Registro");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel15.setText("Tutores");

        javax.swing.GroupLayout AgregarTutorLayout = new javax.swing.GroupLayout(AgregarTutor);
        AgregarTutor.setLayout(AgregarTutorLayout);
        AgregarTutorLayout.setHorizontalGroup(
            AgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AgregarTutorLayout.createSequentialGroup()
                .addGap(464, 464, 464)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(AgregarTutorLayout.createSequentialGroup()
                .addGroup(AgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AgregarTutorLayout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(AgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel10)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addGap(35, 35, 35)
                        .addGroup(AgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cbCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ddh, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dn, javax.swing.GroupLayout.PREFERRED_SIZE, 465, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dnt, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(AgregarTutorLayout.createSequentialGroup()
                        .addGap(160, 160, 160)
                        .addComponent(jButton1)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ImagenTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50))
        );
        AgregarTutorLayout.setVerticalGroup(
            AgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AgregarTutorLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addGroup(AgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AgregarTutorLayout.createSequentialGroup()
                        .addGroup(AgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(dnt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(AgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(dn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(56, 56, 56)
                        .addGroup(AgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(ddh, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(54, 54, 54)
                        .addGroup(AgregarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cbCarreras, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9))
                        .addGap(89, 89, 89)
                        .addComponent(jButton1))
                    .addComponent(ImagenTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 501, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Agregar Tutor", AgregarTutor);

        moverTut.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        moverTut.setText("< Mover Estudiante");
        moverTut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moverTutActionPerformed(evt);
            }
        });

        asignarTutorado.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        asignarTutorado.setText("Asignar Tutorado");
        asignarTutorado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asignarTutoradoActionPerformed(evt);
            }
        });

        aceptarTutor.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        aceptarTutor.setText("Aceptar Tutor");
        aceptarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aceptarTutorActionPerformed(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel16.setText("Estudiantes Disponibles");

        bct.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        bct.setText("Cambiar Tutor");
        bct.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bctActionPerformed(evt);
            }
        });

        LTutores.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        LTutores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel17.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel17.setText("Selecciona Tutor:");

        jLabel18.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel18.setText("Estudiantes Asignados");

        estudiantes.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        estudiantes.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(estudiantes);

        tutoradoss.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        tutoradoss.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(tutoradoss);

        moverEst.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        moverEst.setText("Mover Estudiante >");
        moverEst.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                moverEstActionPerformed(evt);
            }
        });

        jLabel19.setFont(new java.awt.Font("Dialog", 1, 36)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Asignar Tutor");

        javax.swing.GroupLayout AsignarTutorLayout = new javax.swing.GroupLayout(AsignarTutor);
        AsignarTutor.setLayout(AsignarTutorLayout);
        AsignarTutorLayout.setHorizontalGroup(
            AsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AsignarTutorLayout.createSequentialGroup()
                .addGroup(AsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(AsignarTutorLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addGroup(AsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(AsignarTutorLayout.createSequentialGroup()
                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LTutores, javax.swing.GroupLayout.PREFERRED_SIZE, 364, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(AsignarTutorLayout.createSequentialGroup()
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(61, 61, 61)
                                .addGroup(AsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(moverTut)
                                    .addComponent(moverEst)
                                    .addGroup(AsignarTutorLayout.createSequentialGroup()
                                        .addGap(11, 11, 11)
                                        .addComponent(asignarTutorado)))
                                .addGap(14, 14, 14)))
                        .addGap(18, 18, 18)
                        .addGroup(AsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(AsignarTutorLayout.createSequentialGroup()
                                .addComponent(aceptarTutor)
                                .addGap(35, 35, 35)
                                .addComponent(bct))
                            .addGroup(AsignarTutorLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(AsignarTutorLayout.createSequentialGroup()
                        .addGap(399, 399, 399)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(AsignarTutorLayout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addComponent(jLabel16)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(145, 145, 145))
        );
        AsignarTutorLayout.setVerticalGroup(
            AsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AsignarTutorLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jLabel19)
                .addGap(61, 61, 61)
                .addGroup(AsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LTutores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(aceptarTutor)
                    .addComponent(bct)
                    .addComponent(jLabel17))
                .addGap(98, 98, 98)
                .addGroup(AsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(jLabel18))
                .addGap(31, 31, 31)
                .addGroup(AsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(AsignarTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, AsignarTutorLayout.createSequentialGroup()
                            .addComponent(moverTut)
                            .addGap(38, 38, 38)
                            .addComponent(moverEst)
                            .addGap(57, 57, 57))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(asignarTutorado)
                .addGap(60, 60, 60))
        );

        jTabbedPane2.addTab("Asignar Tutor", AsignarTutor);

        javax.swing.GroupLayout PTutoresLayout = new javax.swing.GroupLayout(PTutores);
        PTutores.setLayout(PTutoresLayout);
        PTutoresLayout.setHorizontalGroup(
            PTutoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        PTutoresLayout.setVerticalGroup(
            PTutoresLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PTutoresLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2)
                .addContainerGap())
        );

        Menu.addTab("Administrador", PTutores);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1084, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 603, Short.MAX_VALUE)
        );

        jTabbedPane3.addTab("Crear Cita", jPanel1);

        javax.swing.GroupLayout PCitasLayout = new javax.swing.GroupLayout(PCitas);
        PCitas.setLayout(PCitasLayout);
        PCitasLayout.setHorizontalGroup(
            PCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PCitasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3)
                .addContainerGap())
        );
        PCitasLayout.setVerticalGroup(
            PCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PCitasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 629, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(108, Short.MAX_VALUE))
        );

        Menu.addTab("Cita", PCitas);

        jLabel13.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel13.setText("Registro de Asistencia");

        jLabel14.setFont(new java.awt.Font("Times New Roman", 1, 36)); // NOI18N
        jLabel14.setText("Registro de Tutoria");

        RegistroTutoria.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        RegistroTutoria.setText("Registrar Tutoria");

        ltutorados.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        ltutorados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(ltutorados);

        jLabel20.setFont(new java.awt.Font("Dialog", 1, 24)); // NOI18N
        jLabel20.setText("Tutor");

        ListaTutor.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        ListaTutor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        ListaTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ListaTutorActionPerformed(evt);
            }
        });

        Aceptar.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        Aceptar.setText("Aceptar");
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });

        FechaActual.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        FechaActual.setText("Fecha: ");

        javax.swing.GroupLayout PTutoriasLayout = new javax.swing.GroupLayout(PTutorias);
        PTutorias.setLayout(PTutoriasLayout);
        PTutoriasLayout.setHorizontalGroup(
            PTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PTutoriasLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(PTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PTutoriasLayout.createSequentialGroup()
                        .addComponent(RegistroTutoria)
                        .addGap(432, 432, 432))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PTutoriasLayout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(422, 422, 422))))
            .addGroup(PTutoriasLayout.createSequentialGroup()
                .addGroup(PTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PTutoriasLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane2))
                    .addGroup(PTutoriasLayout.createSequentialGroup()
                        .addGroup(PTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PTutoriasLayout.createSequentialGroup()
                                .addGap(190, 190, 190)
                                .addComponent(jLabel20)
                                .addGap(68, 68, 68)
                                .addComponent(ListaTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 422, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(102, 102, 102)
                                .addComponent(Aceptar))
                            .addGroup(PTutoriasLayout.createSequentialGroup()
                                .addGap(439, 439, 439)
                                .addComponent(FechaActual))
                            .addGroup(PTutoriasLayout.createSequentialGroup()
                                .addGap(402, 402, 402)
                                .addComponent(jLabel14)))
                        .addGap(0, 150, Short.MAX_VALUE)))
                .addContainerGap())
        );
        PTutoriasLayout.setVerticalGroup(
            PTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PTutoriasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(FechaActual)
                .addGap(35, 35, 35)
                .addGroup(PTutoriasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(ListaTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Aceptar))
                .addGap(53, 53, 53)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 289, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(RegistroTutoria)
                .addGap(94, 94, 94))
        );

        Menu.addTab("Tutoria", PTutorias);

        jPanel5.setBackground(new java.awt.Color(204, 153, 0));

        jLabel1.setFont(new java.awt.Font("Dubai Light", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 0, 0));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Sistema de Tutorias");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(349, 349, 349)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 499, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Menu, javax.swing.GroupLayout.PREFERRED_SIZE, 1268, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Menu)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public String getFecha(){
        fecha = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        return formato.format(fecha);
    }
    
    public void cargarTutoresCita(){
        ListaTutor.removeAllItems();
        tutor_nom.clear();
        cita_horas.clear();        
        ListaTutor.addItem(SELECCIONA);
        int nc = 1;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String ff = formato.format(fecha);
        citas.sort(Comparator.comparing((Cita c) -> c.getIdtutor().getNombre())
            .thenComparing(Cita::getHora));
        for(Cita dCita: citas){
            if(ff.equals(formato.format(dCita.getFecha())) && dCita.getEstado().equals("0")){
                ListaTutor.addItem(dCita.getIdtutor().getNombre() + " - Hora: " + dCita.getHora() +":00");
                tutor_nom.put(dCita.getIdtutor().getNombre() + " - Hora: " + dCita.getHora() +":00",dCita.getIdtutor());
                cita_horas.put(nc++,dCita);
            }
        }
    }
    public void cargarTutores(){
        LTutores.removeAllItems();
        LTutores.addItem("Selecciona Tutor");
        for(Tutor dtutor: tutores) LTutores.addItem(dtutor.getNombre());
    }
    public void cargarEstudiantes(){
        mestudiantes = new DefaultListModel();
        mtutorados = new DefaultListModel();
        mestudiantes.clear();
        for(Tutorado dtutorado : tutorados) 
            if(dtutorado.getIdtutor() == null){
                mestudiantes.addElement(dtutorado.getNombre());
                tutorado_nom.put(dtutorado.getNombre(),dtutorado);
            }
    }
    public void configurarComboBox(){
        cbCarreras.removeAllItems();
        for(int x = 0; x < Carreras.length; x++ )cbCarreras.addItem(Carreras[x]);  
    }
    
    public void configurarJSpinner(){
        //int inicio = tutor.getTutoradoCollection()
        min = cTutor.getTutorCount()+1;
        snm = new SpinnerNumberModel(min, min, max, 1);    
        dnt.setModel(snm);  
    }
    
    private void dnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dnActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_dnActionPerformed

    private void ddhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ddhActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ddhActionPerformed

    private void cbCarrerasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbCarrerasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbCarrerasActionPerformed
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        tutor = new Tutor();
        tutor.setNumtar( (int) dnt.getValue());
        tutor.setNombre( dn.getText());
        tutor.setCarrera((String) cbCarreras.getSelectedItem());
        tutor.setDiashoras(ddh.getText());
        cTutor.create(tutor);
        tutores.add(tutor);
        mtt.fireTableDataChanged();
        cargarTutores();
    }//GEN-LAST:event_jButton1ActionPerformed

    public ImageIcon redimensionarImagen(String ruta, int ancho, int alto) {
        ImageIcon icono = new ImageIcon(getClass().getResource(ruta)); // o ruta absoluta
        Image imagen = icono.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(imagen);
    }
    
    private void moverTutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moverTutActionPerformed
        // TODO add your handling code here:
        int iest_sel[] = tutoradoss.getSelectedIndices();
        for (int x = iest_sel.length-1; x >= 0; x--){
            mestudiantes.addElement(mtutorados.get(iest_sel[x]));
            mtutorados.remove(iest_sel[x]);
        }
        moverTut.setEnabled(!mtutorados.isEmpty());
        moverEst.setEnabled(!mestudiantes.isEmpty());
    }//GEN-LAST:event_moverTutActionPerformed

    private void asignarTutoradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asignarTutoradoActionPerformed
        // TODO add your handling code here:
        if(JOptionPane.showConfirmDialog(this,"Confirma la asignacion de tutor " + LTutores.getSelectedItem() + "?") == 0){
            for(int nts = 0; nts < mtutorados.size(); nts++){
                Tutorado t = tutorado_nom.get(mtutorados.get(nts));
                t.setIdtutor(tutor);
                try{
                    cTutorado.edit(t);
                } catch (Exception ex){
                    Logger.getLogger(ITutorado.class.getName()).log(Level.SEVERE,null,ex);
                }
            }
        }
        
    }//GEN-LAST:event_asignarTutoradoActionPerformed
        
    public void cargarTutoresTutoria(){
        ListaTutor.removeAllItems();
        tutor_nom.clear();
        cita_horas.clear();        
        ListaTutor.addItem(SELECCIONA);
        int nc = 1;
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String ff = formato.format(fecha);
        citas.sort(Comparator.comparing((Cita c) -> c.getIdtutor().getNombre())
            .thenComparing(Cita::getHora));
        for(Cita dCita: citas){
            if(ff.equals(formato.format(dCita.getFecha())) && dCita.getEstado().equals("0")){
                ListaTutor.addItem(dCita.getIdtutor().getNombre() + " - Hora: " + dCita.getHora() +":00");
                tutor_nom.put(dCita.getIdtutor().getNombre() + " - Hora: " + dCita.getHora() +":00",dCita.getIdtutor());
                cita_horas.put(nc++,dCita);
            }
        }
    }
    private void aceptarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aceptarTutorActionPerformed
        // TODO add your handling code here:
        if(LTutores.getSelectedItem().equals(SELECCIONA))
        JOptionPane.showMessageDialog(this,"Selecciona un Tutor");
        else
        if(JOptionPane.showConfirmDialog(this,"Confirme Selecciona al tutor " + LTutores.getSelectedItem()+"?") == 0){
            tutor = tutores.get(LTutores.getSelectedIndex()-1);
            LTutores.setEnabled(false);
            jLabel17.setText(SELECCIONADO);
            aceptarTutor.setEnabled(false);
            if(!mestudiantes.isEmpty()) moverEst.setEnabled(true);
        }
    }//GEN-LAST:event_aceptarTutorActionPerformed

    private void bctActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bctActionPerformed
        // TODO add your handling code here:
        LTutores.setEnabled(true);
        jLabel1.setText(SELECCIONA);
        aceptarTutor.setEnabled(true);
    }//GEN-LAST:event_bctActionPerformed

    private void moverEstActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_moverEstActionPerformed
        // TODO add your handling code here:
        int iest_sel[] = estudiantes.getSelectedIndices();
        for (int x = iest_sel.length-1; x >= 0; x--){
            mtutorados.addElement(mestudiantes.get(iest_sel[x]));
            mestudiantes.remove(iest_sel[x]);
        }
        moverEst.setEnabled(!mestudiantes.isEmpty());
        moverTut.setEnabled(!mtutorados.isEmpty());
    }//GEN-LAST:event_moverEstActionPerformed

    private void ListaTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ListaTutorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ListaTutorActionPerformed

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed
        // TODO add your handling code here:
        if(!(ListaTutor.getSelectedItem().equals(SELECCIONA))){

            cita = cita_horas.get(ListaTutor.getSelectedIndex());

            tutorados_s = new ArrayList<>();
            for (Tutorado dt: tutorados)
            if(dt.getIdtutor()!= null && dt.getIdtutor().equals(cita.getIdtutor())) tutorados_s.add(dt);

            datosCitas = new ArrayList<>();
            for(Tutorado dt: tutorados_s){
                DatosTablaCitas dtc = new DatosTablaCitas(dt);
                datosCitas.add(dtc);
            }
            modTabCita = new MTablaCita(datosCitas);
            ltutorados.setModel(modTabCita);
        } else JOptionPane.showMessageDialog(this,"Seleccione un Tutor - Hola");
    }//GEN-LAST:event_AceptarActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IMenuTutorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IMenuTutorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IMenuTutorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IMenuTutorias.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        try {
            FlatMacDarkLaf.setup();
        } catch( Exception ex ) {
            System.err.println( "Failed to initialize LaF" );
        }

        
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IMenuTutorias().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aceptar;
    private javax.swing.JPanel AgregarTutor;
    private javax.swing.JPanel AsignarTutor;
    private javax.swing.JLabel FechaActual;
    private javax.swing.JLabel ImagenTutor;
    private javax.swing.JComboBox<String> LTutores;
    private javax.swing.JComboBox<String> ListaTutor;
    private javax.swing.JTabbedPane Menu;
    private javax.swing.JPanel PCitas;
    private javax.swing.JPanel PInicio;
    private javax.swing.JPanel PTutorados;
    private javax.swing.JPanel PTutores;
    private javax.swing.JPanel PTutorias;
    private javax.swing.JButton RegistroTutoria;
    private javax.swing.JButton aceptarTutor;
    private javax.swing.JButton asignarTutorado;
    private javax.swing.JButton bct;
    private javax.swing.JComboBox<String> cbCarreras;
    private javax.swing.JTextField ddh;
    private javax.swing.JTextField dn;
    private javax.swing.JSpinner dnt;
    private javax.swing.JList<String> estudiantes;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JTabbedPane jTabbedPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable ltutorados;
    private javax.swing.JButton moverEst;
    private javax.swing.JButton moverTut;
    private javax.swing.JTable ttutores;
    private javax.swing.JTable ttutores1;
    private javax.swing.JTable ttutores2;
    private javax.swing.JTable ttutores3;
    private javax.swing.JList<String> tutoradoss;
    // End of variables declaration//GEN-END:variables
}

```

### `src\vista\IPCita.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import control.AdmDatos;
import control.CitaJpaController;
import control.TutorJpaController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Cita;
import modelo.MTcita;
import modelo.Tutor;
import modelo.Tutorado;


public class IPCita extends javax.swing.JFrame {

    private Cita cita;
    private CitaJpaController cCita;
    private AdmDatos adm;
    private List<Cita> citas;
    private MTcita mtc;
    
    public IPCita() {
        initComponents();
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        cCita = new CitaJpaController(adm.getEnf());
        citas = cCita.findCitaEntities();
        mtc = new MTcita(citas);
        tablaCitas.setModel(mtc);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        btnAgregarCita = new javax.swing.JButton();
        btnLimpiarDatos = new javax.swing.JButton();
        btnActualizarCita = new javax.swing.JButton();
        btnEliminarCita = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaCitas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jDfecha = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtHora = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtAsunto = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        cboEstado = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        txtNumTargetaTutor = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel5.setText("Hora:");

        btnAgregarCita.setText("Agregar Cita");
        btnAgregarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarCitaActionPerformed(evt);
            }
        });

        btnLimpiarDatos.setText("Limpiar Datos");
        btnLimpiarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarDatosActionPerformed(evt);
            }
        });

        btnActualizarCita.setText("Actualizar Cita");
        btnActualizarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarCitaActionPerformed(evt);
            }
        });

        btnEliminarCita.setText("Eliminar Cita");
        btnEliminarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCitaActionPerformed(evt);
            }
        });

        tablaCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaCitas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaCitasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaCitas);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Citas");

        jLabel6.setText("Fecha de Cita:");

        jLabel7.setText("Asunto");

        txtAsunto.setColumns(20);
        txtAsunto.setRows(5);
        jScrollPane2.setViewportView(txtAsunto);

        jLabel2.setText("Asunto");

        cboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "REALIZADA", "CANCELADA", "PENDIENTE" }));

        jLabel3.setText("Num. Tarjeta de Tutor");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jDfecha, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                            .addComponent(txtHora)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3))
                        .addGap(35, 35, 35)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cboEstado, 0, 181, Short.MAX_VALUE)
                            .addComponent(txtNumTargetaTutor))))
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnAgregarCita, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                .addComponent(btnLimpiarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                            .addGap(58, 58, 58)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(btnActualizarCita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminarCita, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addContainerGap(598, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(65, 65, 65)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jDfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(txtHora, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(cboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(22, 22, 22)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtNumTargetaTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 91, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jLabel1)
                    .addGap(393, 393, 393)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregarCita)
                        .addComponent(btnActualizarCita))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLimpiarDatos)
                        .addComponent(btnEliminarCita))
                    .addGap(9, 9, 9)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    private void tablaCitasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaCitasMouseClicked

        int fila = tablaCitas.getSelectedRow();
        if(fila == -1) return;
        
        Cita cita = citas.get(fila);
        
        jDfecha.setDate(cita.getFecha());
        txtHora.setText(String.valueOf(cita.getHora()));
        txtAsunto.setText(cita.getAsunto());
        cboEstado.setSelectedItem(cita.getEstado());
        txtNumTargetaTutor.setText(
            cita.getTutor() != null ? 
            String.valueOf(cita.getTutor().getNumTarjeta()) : ""
        );
    }//GEN-LAST:event_tablaCitasMouseClicked

    private void btnEliminarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCitaActionPerformed
        int fila = tablaCitas.getSelectedRow();
        
        if(fila == -1){
            JOptionPane.showMessageDialog(this, 
                "Seleccione una cita", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar esta cita?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION);
        
        if(confirmacion != JOptionPane.YES_OPTION) return;
        
        try {
            Cita cita = citas.get(fila);
            cCita.destroy(cita.getIdCita());
            citas.remove(fila);
            mtc.fireTableRowsDeleted(fila, fila);
            limpiarDatos();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al eliminar: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnEliminarCitaActionPerformed

    private void btnActualizarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarCitaActionPerformed
        int fila = tablaCitas.getSelectedRow();
        
        if(fila == -1){
            JOptionPane.showMessageDialog(this, 
                "Seleccione una cita de la tabla", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            Cita citaSeleccionada = citas.get(fila);
            
            // Validar campos
            if (jDfecha.getDate() == null || 
                txtHora.getText().trim().isEmpty() || 
                txtAsunto.getText().trim().isEmpty()) {
                
                JOptionPane.showMessageDialog(this, 
                    "Campos obligatorios faltantes", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Actualizar datos
            citaSeleccionada.setFecha(jDfecha.getDate());
            citaSeleccionada.setHora(Integer.parseInt(txtHora.getText().trim()));
            citaSeleccionada.setAsunto(txtAsunto.getText().trim());
            citaSeleccionada.setEstado(cboEstado.getSelectedItem().toString());
            
            // Actualizar tutor
            TutorJpaController cTutor = new TutorJpaController(adm.getEnf());
            Tutor tutor = cTutor.findTutor(
                Integer.parseInt(txtNumTargetaTutor.getText().trim())
            );
            
            if(tutor == null) {
                JOptionPane.showMessageDialog(this, 
                    "Tutor no encontrado", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            citaSeleccionada.setTutor(tutor);
            
            cCita.edit(citaSeleccionada);
            mtc.fireTableRowsUpdated(fila, fila);
            limpiarDatos();
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnActualizarCitaActionPerformed

    private void btnLimpiarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarDatosActionPerformed
        limpiarDatos();
    }//GEN-LAST:event_btnLimpiarDatosActionPerformed

    private void btnAgregarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarCitaActionPerformed
        try {
            // Validar campos obligatorios
            if (jDfecha.getDate() == null || 
                txtHora.getText().trim().isEmpty() || 
                txtAsunto.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(this, 
                    "Fecha, hora y asunto son campos obligatorios", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Validar formato de hora
            int hora;
            try {
                hora = Integer.parseInt(txtHora.getText().trim());
                if(hora < 0 || hora > 23) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Hora inválida (debe ser entre 0 y 23)", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Buscar tutor por número de tarjeta
            TutorJpaController cTutor = new TutorJpaController(adm.getEnf());
            Tutor tutor = cTutor.findTutor(
                Integer.parseInt(txtNumTargetaTutor.getText().trim())
            );

            if(tutor == null) {
                JOptionPane.showMessageDialog(this, 
                    "No existe un tutor con ese número de tarjeta", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear nueva cita (¡Aquí debe estar primero!)
            cita = new Cita();
            cita.setFecha(jDfecha.getDate());
            cita.setHora(hora); // Usar la variable ya validada
            cita.setAsunto(txtAsunto.getText().trim());
            cita.setEstado(cboEstado.getSelectedItem().toString());
            cita.setTutor(tutor);

            cCita.create(cita);
            citas.add(cita);
            mtc.fireTableDataChanged();
            limpiarDatos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear cita: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAgregarCitaActionPerformed

    private void limpiarDatos() {
        jDfecha.setDate(null);
        txtHora.setText("");
        txtAsunto.setText("");
        cboEstado.setSelectedIndex(0);
        txtNumTargetaTutor.setText("");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IPCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IPCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IPCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IPCita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //UIManager.setLookAndFeel(new FlatDarkLaf());
        UIManager.setLookAndFeel(new FlatLightLaf());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IPCita().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarCita;
    private javax.swing.JButton btnAgregarCita;
    private javax.swing.JButton btnEliminarCita;
    private javax.swing.JButton btnLimpiarDatos;
    private javax.swing.JComboBox<String> cboEstado;
    private com.toedter.calendar.JDateChooser jDfecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tablaCitas;
    private javax.swing.JTextArea txtAsunto;
    private javax.swing.JTextField txtHora;
    private javax.swing.JTextField txtNumTargetaTutor;
    // End of variables declaration//GEN-END:variables
}

```

### `src\vista\IPTutorado.java`

```java

package vista;

import com.formdev.flatlaf.FlatDarkLaf;
import control.AdmDatos;
import control.TutorJpaController;
import control.TutoradoJpaController;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.MTtutorado;
import modelo.Tutor;
import modelo.Tutorado;

public class IPTutorado extends javax.swing.JFrame {

    private Tutorado tutorado;
    private TutoradoJpaController cTutorado;
    private AdmDatos adm;
    private List<Tutorado> tutores;
    private MTtutorado  mtt;
    
    public IPTutorado() {
        initComponents();
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        cTutorado = new TutoradoJpaController(adm.getEnf());
        tutores = cTutorado.findTutoradoEntities();
        mtt = new MTtutorado(tutores);
        tablaTutorados.setModel(mtt);
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCLunes = new javax.swing.JCheckBox();
        jCMartes = new javax.swing.JCheckBox();
        jCMiercoles = new javax.swing.JCheckBox();
        jCJueves = new javax.swing.JCheckBox();
        jCViernes = new javax.swing.JCheckBox();
        jCSabado = new javax.swing.JCheckBox();
        btnAgregarTutorado = new javax.swing.JButton();
        btnLimpiarDatos = new javax.swing.JButton();
        btnActualizarTutorado = new javax.swing.JButton();
        btnEliminarTutor = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTutorados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtNumeroControl = new javax.swing.JTextField();
        cobGenero = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jDfecha = new com.toedter.calendar.JDateChooser();
        jLabel7 = new javax.swing.JLabel();
        txtNumTarjeta = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("No. de Control");

        jLabel3.setText("Nombre:");

        jLabel4.setText("Genero");

        jLabel5.setText("Dias:");

        jCLunes.setText("Lunes");

        jCMartes.setText("Martes");

        jCMiercoles.setText("Miercoles");

        jCJueves.setText("Jueves");

        jCViernes.setText("Viernes");

        jCSabado.setText("Sabado");

        btnAgregarTutorado.setText("Agregar Tutorado");
        btnAgregarTutorado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTutoradoActionPerformed(evt);
            }
        });

        btnLimpiarDatos.setText("Limpiar Datos");
        btnLimpiarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarDatosActionPerformed(evt);
            }
        });

        btnActualizarTutorado.setText("Actualizar Tutorado");
        btnActualizarTutorado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTutoradoActionPerformed(evt);
            }
        });

        btnEliminarTutor.setText("Eliminar Tutorado");
        btnEliminarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTutorActionPerformed(evt);
            }
        });

        tablaTutorados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaTutorados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTutoradosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaTutorados);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Tutorados");

        cobGenero.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Masculino", "Femenino", "Otro" }));

        jLabel6.setText("F. de Nacimiento:");

        jLabel7.setText("Num. de Tarjeta del Tutor:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(175, 175, 175)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(100, 100, 100))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(txtNumeroControl)
                        .addComponent(cobGenero, 0, 192, Short.MAX_VALUE))
                    .addComponent(jDfecha, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7)
                    .addComponent(txtNumTarjeta, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 567, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5)
                                        .addComponent(jCLunes)
                                        .addComponent(jCMartes)
                                        .addComponent(jCMiercoles)
                                        .addComponent(jCJueves)
                                        .addComponent(jCViernes)
                                        .addComponent(jCSabado)
                                        .addComponent(btnAgregarTutorado, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE)
                                        .addComponent(btnLimpiarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(58, 58, 58)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(btnActualizarTutorado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(btnEliminarTutor, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                    .addContainerGap(600, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtNumeroControl, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(62, 62, 62)
                        .addComponent(cobGenero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(24, 24, 24)
                        .addComponent(jLabel6)
                        .addGap(7, 7, 7)
                        .addComponent(jDfecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                        .addComponent(jLabel7)
                        .addGap(16, 16, 16)
                        .addComponent(txtNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108))))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jLabel1)
                    .addGap(49, 49, 49)
                    .addComponent(jLabel2)
                    .addGap(24, 24, 24)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(jLabel4)
                    .addGap(30, 30, 30)
                    .addComponent(jLabel5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jCLunes)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCMartes)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCMiercoles)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCJueves)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCViernes)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCSabado)
                    .addGap(40, 40, 40)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnAgregarTutorado)
                        .addComponent(btnActualizarTutorado))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnLimpiarDatos)
                        .addComponent(btnEliminarTutor))
                    .addGap(9, 9, 9)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarTutoradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTutoradoActionPerformed
        try {
        // Validar campos obligatorios
        if (txtNumeroControl.getText().trim().isEmpty() || 
            txtNombre.getText().trim().isEmpty() || 
            jDfecha.getDate() == null) {
            
            JOptionPane.showMessageDialog(this, 
                "Número de control, nombre y fecha son obligatorios", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        tutorado = new Tutorado();
        
        tutorado.setNc(txtNumeroControl.getText().trim());
        tutorado.setNombre(txtNombre.getText().trim());
        tutorado.setGenero(obtenerGenero());
        tutorado.setDias(generarCadenaDias());
        tutorado.setFechaNacimiento(jDfecha.getDate());
        
        // Obtener tutor por número de tarjeta
        
        if(!txtNumTarjeta.getText().trim().equals("")){
            TutorJpaController cTutor = new TutorJpaController(adm.getEnf());
            Tutor tutor = cTutor.findTutor(Integer.parseInt(txtNumTarjeta.getText()));

            if(tutor == null) {
                JOptionPane.showMessageDialog(this, 
                    "No existe un tutor con ese número de tarjeta", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            tutorado.setTutor(tutor);
        }
        
        cTutorado.create(tutorado);
        tutores.add(tutorado);
        mtt.fireTableDataChanged();
        limpiarDatos();
        
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al crear tutorado: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAgregarTutoradoActionPerformed

    private void btnLimpiarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarDatosActionPerformed
        limpiarDatos();
    }//GEN-LAST:event_btnLimpiarDatosActionPerformed

    private void btnActualizarTutoradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTutoradoActionPerformed
        int fila = tablaTutorados.getSelectedRow();
    
        if(fila == -1){
            JOptionPane.showMessageDialog(this, 
                "Seleccione un tutorado de la tabla", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Tutorado tutoradoSeleccionado = tutores.get(fila);

            // Validar campos
            if (txtNumeroControl.getText().trim().isEmpty() || 
                txtNombre.getText().trim().isEmpty() || 
                jDfecha.getDate() == null) {

                JOptionPane.showMessageDialog(this, 
                    "Campos obligatorios faltantes", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar datos
            tutoradoSeleccionado.setNc(txtNumeroControl.getText().trim());
            tutoradoSeleccionado.setNombre(txtNombre.getText().trim());
            tutoradoSeleccionado.setGenero(obtenerGenero());
            tutoradoSeleccionado.setDias(generarCadenaDias());
            tutoradoSeleccionado.setFechaNacimiento(jDfecha.getDate());

            // Actualizar tutor
            TutorJpaController cTutor = new TutorJpaController(adm.getEnf());
            Tutor tutor = cTutor.findTutor(Integer.parseInt(txtNumTarjeta.getText()));

            if(tutor == null) {
                JOptionPane.showMessageDialog(this, 
                    "Tutor no encontrado", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            tutoradoSeleccionado.setTutor(tutor);

            cTutorado.edit(tutoradoSeleccionado);
            mtt.fireTableRowsUpdated(fila, fila);
            limpiarDatos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al actualizar: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnActualizarTutoradoActionPerformed

    private void btnEliminarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTutorActionPerformed
        int fila = tablaTutorados.getSelectedRow();
    
        if(fila == -1){
            JOptionPane.showMessageDialog(this, 
                "Seleccione un tutorado", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar este tutorado?", 
            "Confirmar", 
            JOptionPane.YES_NO_OPTION);

        if(confirmacion != JOptionPane.YES_OPTION) return;

        try {
            Tutorado tutorado = tutores.get(fila);
            cTutorado.destroy(tutorado.getIdTutorado());
            tutores.remove(fila);
            mtt.fireTableRowsDeleted(fila, fila);
            limpiarDatos();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, 
                "Error al eliminar: " + ex.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnEliminarTutorActionPerformed

    private void tablaTutoradosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTutoradosMouseClicked
        
        int fila = tablaTutorados.getSelectedRow();
        
        txtNumeroControl.setText(tablaTutorados.getValueAt(fila, 0).toString());
        txtNombre.setText(tablaTutorados.getValueAt(fila, 1).toString());
        cobGenero.setSelectedIndex(construirGenero());
        marcarDiasDesdeCadena(tablaTutorados.getValueAt(fila, 3).toString());
        jDfecha.setDate((Date) tablaTutorados.getValueAt(fila, 4));
        txtNumTarjeta.setText(tablaTutorados.getValueAt(fila, 5).toString());
        
    }//GEN-LAST:event_tablaTutoradosMouseClicked
    
    private void limpiarDatos() {
        txtNumeroControl.setText("");
        txtNombre.setText("");
        cobGenero.setSelectedIndex(0);
        jDfecha.setDate(null);
        txtNumTarjeta.setText("");

        jCLunes.setSelected(false);
        jCMartes.setSelected(false);
        jCMiercoles.setSelected(false);
        jCJueves.setSelected(false);
        jCViernes.setSelected(false);
        jCSabado.setSelected(false);
    }
    public String generarCadenaDias() {
        StringBuilder dias = new StringBuilder();

        if (jCLunes.isSelected()) dias.append("L-");
        if (jCMartes.isSelected()) dias.append("M-");
        if (jCMiercoles.isSelected()) dias.append("X-");
        if (jCJueves.isSelected()) dias.append("J-");
        if (jCViernes.isSelected()) dias.append("V-");
        if (jCSabado.isSelected()) dias.append("S-");

        // Eliminar el último guión si hay elementos
        if (dias.length() > 0) {
            dias.deleteCharAt(dias.length() - 1);
        }

        return dias.toString();
    }
    private char obtenerGenero(){
        char opcion = 'O';
            
        String mensaje = cobGenero.getSelectedItem().toString();
        
        if(mensaje.equals("Masculino")){
            opcion = 'M';
        }
        if(mensaje.equals("Femenino")){
            opcion = 'F';
        }
        if(mensaje.equals("Otro")){
            opcion = 'O';
        }
        
        return opcion;
    }
    private int construirGenero(){
        int opcion = 2;
        
        int fila = tablaTutorados.getSelectedRow();
        char c = (char) tablaTutorados.getValueAt(fila, 2);
        
        switch (c){
            case 'M': opcion = 0;
                break;
            case 'F': opcion = 1;
                break;
            case 'O': opcion = 2;
                break;
            default: opcion = 2;
        }
        
        return opcion;
    }
    public void marcarDiasDesdeCadena(String cadenaDias) {
        // Limpiar primero todas las selecciones
        jCLunes.setSelected(false);
        jCMartes.setSelected(false);
        jCMiercoles.setSelected(false);
        jCJueves.setSelected(false);
        jCViernes.setSelected(false);
        jCSabado.setSelected(false);

        if (cadenaDias == null || cadenaDias.isEmpty()) {
            return;
        }

        // Separar los días usando el guión como delimitador
        String[] dias = cadenaDias.split("-");

        for (String dia : dias) {
            switch (dia.trim().toUpperCase()) {
                case "L":
                    jCLunes.setSelected(true);
                    break;
                case "M":
                    jCMartes.setSelected(true);
                    break;
                case "X":
                    jCMiercoles.setSelected(true);
                    break;
                case "J":
                    jCJueves.setSelected(true);
                    break;
                case "V":
                    jCViernes.setSelected(true);
                    break;
                case "S":
                    jCSabado.setSelected(true);
                    break;
            }
        }
    }

    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IPTutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IPTutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IPTutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IPTutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        UIManager.setLookAndFeel(new FlatDarkLaf());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IPTutorado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarTutorado;
    private javax.swing.JButton btnAgregarTutorado;
    private javax.swing.JButton btnEliminarTutor;
    private javax.swing.JButton btnLimpiarDatos;
    private javax.swing.JComboBox<String> cobGenero;
    private javax.swing.JCheckBox jCJueves;
    private javax.swing.JCheckBox jCLunes;
    private javax.swing.JCheckBox jCMartes;
    private javax.swing.JCheckBox jCMiercoles;
    private javax.swing.JCheckBox jCSabado;
    private javax.swing.JCheckBox jCViernes;
    private com.toedter.calendar.JDateChooser jDfecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaTutorados;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtNumTarjeta;
    private javax.swing.JTextField txtNumeroControl;
    // End of variables declaration//GEN-END:variables
}

```

### `src\vista\IPTutoria.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatDarkLaf;
import control.AdmDatos;
import control.CitaJpaController;
import control.TutoradoJpaController;
import control.TutoriaJpaController;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Cita;
import modelo.MTtutoria;
import modelo.Tutorado;
import modelo.Tutoria;

/**
 *
 * @author jesus
 */
public class IPTutoria extends javax.swing.JFrame {

    private Tutoria tutoria;
    private TutoriaJpaController cTutoria;
    private AdmDatos adm;
    private List<Tutoria> tutorias;
    private MTtutoria mtt;
    
    public IPTutoria() {
        initComponents();
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        cTutoria = new TutoriaJpaController(adm.getEnf());
        tutorias = cTutoria.findTutoriaEntities();
        mtt = new MTtutoria(tutorias);
        tablaTutorados.setModel(mtt);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtIdTutorado = new javax.swing.JTextField();
        btnAgregarTutorado = new javax.swing.JButton();
        btnLimpiarDatos = new javax.swing.JButton();
        btnActualizarCita = new javax.swing.JButton();
        btnEliminarCita = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaTutorados = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtIdCita = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setText("ID cita");

        jLabel3.setText("ID tutorado:");

        btnAgregarTutorado.setText("Agregar Tutoria");
        btnAgregarTutorado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTutoradoActionPerformed(evt);
            }
        });

        btnLimpiarDatos.setText("Limpiar Datos");
        btnLimpiarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarDatosActionPerformed(evt);
            }
        });

        btnActualizarCita.setText("Actualizar Cita");
        btnActualizarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarCitaActionPerformed(evt);
            }
        });

        btnEliminarCita.setText("Eliminar Cita");
        btnEliminarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarCitaActionPerformed(evt);
            }
        });

        tablaTutorados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaTutorados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaTutoradosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tablaTutorados);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Tutoria");

        jLabel4.setText("Acciones:");

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdTutorado, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdCita, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnAgregarTutorado, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnEliminarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btnLimpiarDatos, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnActualizarCita, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 566, Short.MAX_VALUE)
                .addGap(14, 14, 14))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3))
                    .addContainerGap(872, Short.MAX_VALUE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtIdCita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtIdTutorado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminarCita)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnAgregarTutorado)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnLimpiarDatos, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnActualizarCita, javax.swing.GroupLayout.Alignment.TRAILING))))
                .addGap(29, 29, 29))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addGap(8, 8, 8)
                    .addComponent(jLabel1)
                    .addGap(49, 49, 49)
                    .addComponent(jLabel2)
                    .addGap(27, 27, 27)
                    .addComponent(jLabel3)
                    .addContainerGap(343, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarTutoradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTutoradoActionPerformed
        try {
            // Validar campos requeridos
            if (txtIdCita.getText().isEmpty() || txtIdTutorado.getText().isEmpty() || jTextArea1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear nueva tutoría
            tutoria = new Tutoria();
            tutoria.setAcciones(jTextArea1.getText());

            // Obtener entidades relacionadas
            // Actualizar entidades relacionadas
            int idCita = Integer.parseInt(txtIdCita.getText());
            int idTutorado = Integer.parseInt(txtIdTutorado.getText());


            CitaJpaController cCita = new CitaJpaController(adm.getEnf());
            Cita cita = cCita.findCita(idCita);
            
            TutoradoJpaController cTutorado = new TutoradoJpaController(adm.getEnf());
            Tutorado tutorado = cTutorado.findTutorado(idTutorado);

            if(cita == null || tutorado == null) {
                JOptionPane.showMessageDialog(this, "Cita o Tutorado no encontrados", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            tutoria.setIdCita(cita);
            tutoria.setIdTutorado(tutorado);

            // Persistir en BD
            cTutoria.create(tutoria);

            // Actualizar tabla
            tutorias.add(tutoria);
            mtt.fireTableDataChanged();
            limpiarDatos();

            JOptionPane.showMessageDialog(this, "Tutoría creada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear tutoría: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnAgregarTutoradoActionPerformed

    private void btnLimpiarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarDatosActionPerformed
        limpiarDatos();
    }//GEN-LAST:event_btnLimpiarDatosActionPerformed

    private void btnActualizarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarCitaActionPerformed
        int fila = tablaTutorados.getSelectedRow();

        if(fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una tutoría de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Tutoria tutoriaSeleccionada = tutorias.get(fila);

            // Validar campos
            if (txtIdCita.getText().isEmpty() || txtIdTutorado.getText().isEmpty() || jTextArea1.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar entidades relacionadas
            int idCita = Integer.parseInt(txtIdCita.getText());
            int idTutorado = Integer.parseInt(txtIdTutorado.getText());


            CitaJpaController cCita = new CitaJpaController(adm.getEnf());
            Cita cita = cCita.findCita(idCita);
            
            TutoradoJpaController cTutorado = new TutoradoJpaController(adm.getEnf());
            Tutorado tutorado = cTutorado.findTutorado(idTutorado);
            

            if(cita == null || tutorado == null) {
                JOptionPane.showMessageDialog(this, "Cita o Tutorado no encontrados", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar datos
            tutoriaSeleccionada.setAcciones(jTextArea1.getText());
            tutoriaSeleccionada.setIdCita(cita);
            tutoriaSeleccionada.setIdTutorado(tutorado);

            // Persistir cambios
            cTutoria.edit(tutoriaSeleccionada);

            // Actualizar tabla
            mtt.fireTableRowsUpdated(fila, fila);
            limpiarDatos();

            JOptionPane.showMessageDialog(this, "Tutoría actualizada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar tutoría: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnActualizarCitaActionPerformed

    private void btnEliminarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarCitaActionPerformed
        int fila = tablaTutorados.getSelectedRow();

        if(fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una tutoría de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(
            this, 
            "¿Está seguro de eliminar esta tutoría?", 
            "Confirmar eliminación", 
            JOptionPane.YES_NO_OPTION
        );

        if(confirmacion != JOptionPane.YES_OPTION) return;

        try {
            Tutoria tutoria = tutorias.get(fila);
            cTutoria.destroy(tutoria.getIdTutoria());
            tutorias.remove(fila);
            mtt.fireTableRowsDeleted(fila, fila);
            limpiarDatos();

            JOptionPane.showMessageDialog(this, "Tutoría eliminada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar tutoría: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnEliminarCitaActionPerformed

    private void tablaTutoradosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaTutoradosMouseClicked
        int fila = tablaTutorados.getSelectedRow();
        if(fila == -1) return;

        Tutoria tutoria = tutorias.get(fila);

        // Mostrar datos en los campos
        txtIdCita.setText(String.valueOf(tutoria.getIdCita().getIdCita()));
        txtIdTutorado.setText(String.valueOf(tutoria.getIdTutorado().getIdTutorado()));
        jTextArea1.setText(tutoria.getAcciones());
    }//GEN-LAST:event_tablaTutoradosMouseClicked
    
    private void limpiarDatos() {
        txtIdCita.setText("");
        txtIdTutorado.setText("");
        jTextArea1.setText("");
        tablaTutorados.clearSelection();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IPTutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IPTutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IPTutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IPTutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        UIManager.setLookAndFeel(new FlatDarkLaf());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IPTutoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizarCita;
    private javax.swing.JButton btnAgregarTutorado;
    private javax.swing.JButton btnEliminarCita;
    private javax.swing.JButton btnLimpiarDatos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTable tablaTutorados;
    private javax.swing.JTextField txtIdCita;
    private javax.swing.JTextField txtIdTutorado;
    // End of variables declaration//GEN-END:variables
}

```

### `src\vista\ITutor.java`

```java
package vista;

import com.formdev.flatlaf.FlatLightLaf;
import modelo.MTtutor;
import control.AdmDatos;
import control.TutorJpaController;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.Tutor;
import java.lang.Exception;

public class ITutor extends javax.swing.JDialog {

//Creamos una conexión a la base de datos
    private Tutor tutor;
    private TutorJpaController cTutor;
    private AdmDatos adm;
    private List<Tutor> tutores;
    private MTtutor  mtt;
    private SpinnerNumberModel msn;
    private final int NMAX = 20;  //El numero de máximo de Numeros de Tarjetas

    public ITutor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        
        initComponents();
        
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        cTutor = new TutorJpaController(adm.getEnf());
        tutores = cTutor.findTutorEntities();
        mtt = new MTtutor(tutores);
        ttutores.setModel(mtt);
        
        // Cálculo de rango para el Spinner
        int ultimaTarjeta = tutores.get(tutores.size() - 1).getNumTarjeta();
        int min = ultimaTarjeta + 1;
        int max = NMAX;
        int paso = 1;

        msn = new SpinnerNumberModel();
        msn.setMinimum(min);
        msn.setMaximum(max);
        msn.setValue(min);

        JspiNumTarjeta.setModel(msn);
    }
    
    //Pasar datos a la nueva tabla
    
    //public void 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCh15 = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        ttutores = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        JspiNumTarjeta = new javax.swing.JSpinner();
        txtNombre = new javax.swing.JTextField();
        btnAgregarTutor = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jCLunes = new javax.swing.JCheckBox();
        jCMartes = new javax.swing.JCheckBox();
        jCMiercoles = new javax.swing.JCheckBox();
        jCJueves = new javax.swing.JCheckBox();
        jCViernes = new javax.swing.JCheckBox();
        jCSabado = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jCh7 = new javax.swing.JCheckBox();
        jCh8 = new javax.swing.JCheckBox();
        jCh9 = new javax.swing.JCheckBox();
        jCh10 = new javax.swing.JCheckBox();
        jCh11 = new javax.swing.JCheckBox();
        jCh12 = new javax.swing.JCheckBox();
        jCh13 = new javax.swing.JCheckBox();
        jCh14 = new javax.swing.JCheckBox();
        jCh16 = new javax.swing.JCheckBox();
        jCh17 = new javax.swing.JCheckBox();
        jCh18 = new javax.swing.JCheckBox();
        jCh19 = new javax.swing.JCheckBox();
        btnLimpiarDatos = new javax.swing.JButton();
        btnActualizarTutor = new javax.swing.JButton();
        btnEliminarTutor = new javax.swing.JButton();
        cboCarrera = new javax.swing.JComboBox<>();

        jCh15.setText("15:00-16:00");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        ttutores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        ttutores.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        ttutores.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ttutoresMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ttutores);
        ttutores.getAccessibleContext().setAccessibleName("");
        ttutores.getAccessibleContext().setAccessibleDescription("");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Tutores");

        jLabel2.setText("No. de Tarjeta:");

        jLabel3.setText("Nombre:");

        btnAgregarTutor.setText("Agregar Tutor");
        btnAgregarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarTutorActionPerformed(evt);
            }
        });

        jLabel4.setText("Carrera:");

        jLabel5.setText("Dias:");

        jCLunes.setText("Lunes");

        jCMartes.setText("Martes");

        jCMiercoles.setText("Miercoles");

        jCJueves.setText("Jueves");

        jCViernes.setText("Viernes");

        jCSabado.setText("Sabado");

        jLabel6.setText("Horas:");

        jCh7.setText("7:00-8:00");

        jCh8.setText("8:00-9:00");

        jCh9.setText("9:00-10:00");

        jCh10.setText("10:00-11:00");

        jCh11.setText("11:00-12:00");

        jCh12.setText("12:00-13:00");

        jCh13.setText("13:00-14:00");

        jCh14.setText("14:00-15:00");

        jCh16.setText("16:00-17:00");

        jCh17.setText("17:00-18:00");

        jCh18.setText("18:00-19:00");

        jCh19.setText("19:00-20:00");

        btnLimpiarDatos.setText("Limpiar Datos");
        btnLimpiarDatos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarDatosActionPerformed(evt);
            }
        });

        btnActualizarTutor.setText("Actualizar Tutor");
        btnActualizarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarTutorActionPerformed(evt);
            }
        });

        btnEliminarTutor.setText("Eliminar Tutor");
        btnEliminarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarTutorActionPerformed(evt);
            }
        });

        cboCarrera.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ingeniería Electrónica", "Ingeniería Civil", "Ingeniería Mecánica", "Ingeniería Industrial", "Ingeniería Química", "Ingeniería Electrica", "Ingeniería en Gestión Empresarial", "Ingeniería en Sis. Computacionales", "Licenciatura en Administración", "Contador Público" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnLimpiarDatos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAgregarTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnActualizarTutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarTutor, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(627, 627, 627))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jCLunes)
                                    .addComponent(jCMartes)
                                    .addComponent(jCMiercoles)
                                    .addComponent(jCJueves)
                                    .addComponent(jCViernes)
                                    .addComponent(jCSabado))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cboCarrera, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(JspiNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel6)
                                                .addGap(174, 174, 174))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jCh12)
                                                    .addComponent(jCh8)
                                                    .addComponent(jCh11)
                                                    .addComponent(jCh10)
                                                    .addComponent(jCh9)
                                                    .addComponent(jCh7)
                                                    .addComponent(jCh13))
                                                .addGap(26, 26, 26)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(jCh19)
                                                    .addComponent(jCh14)
                                                    .addComponent(jCh18, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jCh17, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jCh16, javax.swing.GroupLayout.Alignment.LEADING))))))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(256, 256, 256)
                        .addComponent(jScrollPane1)))
                .addGap(6, 6, 6))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGap(14, 14, 14))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel3)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addGap(30, 30, 30)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jCLunes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCMartes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCMiercoles)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCJueves)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCViernes)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCSabado))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(JspiNumTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(21, 21, 21)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cboCarrera, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(27, 27, 27)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCh7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh12))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jCh14)
                                        .addGap(32, 32, 32)
                                        .addComponent(jCh16)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh17)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh18)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jCh19)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jCh13)))
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAgregarTutor)
                            .addComponent(btnActualizarTutor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnLimpiarDatos)
                            .addComponent(btnEliminarTutor))))
                .addGap(31, 31, 31))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAgregarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarTutorActionPerformed
        tutor = new Tutor();
        tutor.setNumTarjeta((Integer) JspiNumTarjeta.getValue());
        tutor.setNombre(txtNombre.getText());
        tutor.setCarrera(cboCarrera.getSelectedItem().toString());
        tutor.setDias(generarCadenaDias());
        tutor.setHoras(generarIntervalosHorarios());
        
        cTutor.create(tutor);
        tutores.add(tutor);
        mtt.fireTableDataChanged();
    }//GEN-LAST:event_btnAgregarTutorActionPerformed

    private void btnLimpiarDatosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarDatosActionPerformed
        limpiarDatos();
    }//GEN-LAST:event_btnLimpiarDatosActionPerformed

    private void ttutoresMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ttutoresMouseClicked
        int fila = ttutores.getSelectedRow();
        
        //Agregar los Valores
        JspiNumTarjeta.setValue(ttutores.getValueAt(fila, 0)); 
        txtNombre.setText(ttutores.getValueAt(fila, 1).toString());
        cboCarrera.setSelectedItem(ttutores.getValueAt(fila, 2).toString());
        marcarDiasDesdeCadena(ttutores.getValueAt(fila, 3).toString());
        marcarHorasDesdeCadena(ttutores.getValueAt(fila, 4).toString());
        //tutoradorDetutor(ttutores.getValueAt(fila, 5).toString());
    }//GEN-LAST:event_ttutoresMouseClicked

    private void btnActualizarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarTutorActionPerformed
        int fila = ttutores.getSelectedRow();
        
        if(fila == -1){
            JOptionPane.showMessageDialog(this, "Seleccione un tutor de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        try{
            Tutor tutor = tutores.get(fila);
            
            tutor.setNumTarjeta((Integer) JspiNumTarjeta.getValue());
            tutor.setNombre(txtNombre.getText().trim());
            tutor.setCarrera(cboCarrera.getSelectedItem().toString());
            tutor.setDias(generarCadenaDias());
            tutor.setHoras(generarIntervalosHorarios());
            //tutor.
            
            cTutor.edit(tutor);
            
            mtt.fireTableCellUpdated(fila, fila);
            
            JOptionPane.showMessageDialog(this, "Tutor actualizado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
            limpiarDatos();
        }
        catch(Exception ex){
            JOptionPane.showMessageDialog(this, "Error al actualizar tutor: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnActualizarTutorActionPerformed

    private void btnEliminarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarTutorActionPerformed
        int fila = ttutores.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un tutor de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int confirmacion = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar este tutor?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            Tutor tutor = tutores.get(fila);

            // Usar el ID correcto (id_persona)
            cTutor.destroy(tutor.getIdPersona());  // Cambio clave aquí

            // Actualizar la lista desde la base de datos
            tutores = cTutor.findTutorEntities();
            mtt = new MTtutor(tutores);
            ttutores.setModel(mtt);

            limpiarDatos();

            JOptionPane.showMessageDialog(this,
                    "Tutor eliminado correctamente",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Error al eliminar tutor: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnEliminarTutorActionPerformed

    public void tutoradorDetutor(List<Tutor> tutores) {
        ;
    }
    
    public void marcarHorasDesdeCadena(String intervalo) {
        // Limpiar primero todas las horas
        jCh7.setSelected(false);
        jCh8.setSelected(false);
        jCh9.setSelected(false);
        jCh10.setSelected(false);
        jCh11.setSelected(false);
        jCh12.setSelected(false);
        jCh13.setSelected(false);
        jCh14.setSelected(false);
        jCh15.setSelected(false);
        jCh16.setSelected(false);
        jCh17.setSelected(false);
        jCh18.setSelected(false);
        jCh19.setSelected(false);

        if (intervalo == null || intervalo.isEmpty()) {
            return;
        }

        try {
            String[] partes = intervalo.split("-");
            int horaInicio = Integer.parseInt(partes[0].trim());
            int horaFin = Integer.parseInt(partes[1].trim());

            // Validar rango correcto (7-19 para checkboxes existentes)
            if (horaInicio > horaFin || horaInicio < 7 || horaFin > 20) {
                return;
            }

            // Marcar cada hora en el intervalo
            for (int hora = horaInicio; hora < horaFin; hora++) {
                switch (hora) {
                    case 7: jCh7.setSelected(true); break;
                    case 8: jCh8.setSelected(true); break;
                    case 9: jCh9.setSelected(true); break;
                    case 10: jCh10.setSelected(true); break;
                    case 11: jCh11.setSelected(true); break;
                    case 12: jCh12.setSelected(true); break;
                    case 13: jCh13.setSelected(true); break;
                    case 14: jCh14.setSelected(true); break;
                    case 15: jCh15.setSelected(true); break;
                    case 16: jCh16.setSelected(true); break;
                    case 17: jCh17.setSelected(true); break;
                    case 18: jCh18.setSelected(true); break;
                    case 19: jCh19.setSelected(true); break;
                }
            }

        } catch (Exception e) {
            System.err.println("Formato de intervalo inválido: " + intervalo);
        }
    }
    public void marcarDiasDesdeCadena(String cadenaDias) {
        // Limpiar primero todas las selecciones
        jCLunes.setSelected(false);
        jCMartes.setSelected(false);
        jCMiercoles.setSelected(false);
        jCJueves.setSelected(false);
        jCViernes.setSelected(false);
        jCSabado.setSelected(false);

        if (cadenaDias == null || cadenaDias.isEmpty()) {
            return;
        }

        // Separar los días usando el guión como delimitador
        String[] dias = cadenaDias.split("-");

        for (String dia : dias) {
            switch (dia.trim().toUpperCase()) {
                case "L":
                    jCLunes.setSelected(true);
                    break;
                case "M":
                    jCMartes.setSelected(true);
                    break;
                case "X":
                    jCMiercoles.setSelected(true);
                    break;
                case "J":
                    jCJueves.setSelected(true);
                    break;
                case "V":
                    jCViernes.setSelected(true);
                    break;
                case "S":
                    jCSabado.setSelected(true);
                    break;
            }
        }
    }
    public SpinnerNumberModel modeloSpinner(){
        // Cálculo de rango para el Spinner
        int ultimaTarjeta = tutores.get(tutores.size() - 1).getNumTarjeta();
        int min = ultimaTarjeta + 1;
        int max = NMAX;
        int paso = 1;

        msn = new SpinnerNumberModel();
        msn.setMinimum(min);
        msn.setMaximum(max);
        msn.setValue(min);
        
        return msn;
    }
    public void limpiarDatos(){
        cboCarrera.setSelectedIndex(0);
        txtNombre.setText(null);
        
        JspiNumTarjeta.setModel(modeloSpinner());
        
        // Limpiar días de la semana
        jCLunes.setSelected(false);
        jCMartes.setSelected(false);
        jCMiercoles.setSelected(false);
        jCJueves.setSelected(false);
        jCViernes.setSelected(false);
        jCSabado.setSelected(false);

        // Limpiar horas
        jCh7.setSelected(false);
        jCh8.setSelected(false);
        jCh9.setSelected(false);
        jCh10.setSelected(false);
        jCh11.setSelected(false);
        jCh12.setSelected(false);
        jCh13.setSelected(false);
        jCh14.setSelected(false);
        jCh15.setSelected(false);
        jCh16.setSelected(false);
        jCh17.setSelected(false);
        jCh18.setSelected(false);
        jCh19.setSelected(false);     
    }
    public String generarCadenaDias() {
        StringBuilder dias = new StringBuilder();

        if (jCLunes.isSelected()) dias.append("L-");
        if (jCMartes.isSelected()) dias.append("M-");
        if (jCMiercoles.isSelected()) dias.append("X-");
        if (jCJueves.isSelected()) dias.append("J-");
        if (jCViernes.isSelected()) dias.append("V-");
        if (jCSabado.isSelected()) dias.append("S-");

        // Eliminar el último guión si hay elementos
        if (dias.length() > 0) {
            dias.deleteCharAt(dias.length() - 1);
        }

        return dias.toString();
    }
    public String generarIntervalosHorarios() {
        List<Integer> horasSeleccionadas = new ArrayList<>();

        // Recoger todas las horas seleccionadas
        if (jCh7.isSelected()) horasSeleccionadas.add(7);
        if (jCh8.isSelected()) horasSeleccionadas.add(8);
        if (jCh9.isSelected()) horasSeleccionadas.add(9);
        if (jCh10.isSelected()) horasSeleccionadas.add(10);
        if (jCh11.isSelected()) horasSeleccionadas.add(11);
        if (jCh12.isSelected()) horasSeleccionadas.add(12);
        if (jCh13.isSelected()) horasSeleccionadas.add(13);
        if (jCh14.isSelected()) horasSeleccionadas.add(14);
        if (jCh15.isSelected()) horasSeleccionadas.add(15);
        if (jCh16.isSelected()) horasSeleccionadas.add(16);
        if (jCh17.isSelected()) horasSeleccionadas.add(17);
        if (jCh18.isSelected()) horasSeleccionadas.add(18);
        if (jCh19.isSelected()) horasSeleccionadas.add(19);

        if (horasSeleccionadas.isEmpty()) {
            return "";
        }

        // Calcular mínimo y máximo
        int min = Collections.min(horasSeleccionadas);
        int max = Collections.max(horasSeleccionadas);

        // Crear intervalo único (hora final = max + 1)
        String intervalo = min + "-" + (max + 1);

        return intervalo.length() <= 10 ? intervalo : intervalo.substring(0, 10);
    }
    
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ITutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ITutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ITutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ITutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        
        UIManager.setLookAndFeel(new FlatLightLaf());

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ITutor dialog = new ITutor(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner JspiNumTarjeta;
    private javax.swing.JButton btnActualizarTutor;
    private javax.swing.JButton btnAgregarTutor;
    private javax.swing.JButton btnEliminarTutor;
    private javax.swing.JButton btnLimpiarDatos;
    private javax.swing.JComboBox<String> cboCarrera;
    private javax.swing.JCheckBox jCJueves;
    private javax.swing.JCheckBox jCLunes;
    private javax.swing.JCheckBox jCMartes;
    private javax.swing.JCheckBox jCMiercoles;
    private javax.swing.JCheckBox jCSabado;
    private javax.swing.JCheckBox jCViernes;
    private javax.swing.JCheckBox jCh10;
    private javax.swing.JCheckBox jCh11;
    private javax.swing.JCheckBox jCh12;
    private javax.swing.JCheckBox jCh13;
    private javax.swing.JCheckBox jCh14;
    private javax.swing.JCheckBox jCh15;
    private javax.swing.JCheckBox jCh16;
    private javax.swing.JCheckBox jCh17;
    private javax.swing.JCheckBox jCh18;
    private javax.swing.JCheckBox jCh19;
    private javax.swing.JCheckBox jCh7;
    private javax.swing.JCheckBox jCh8;
    private javax.swing.JCheckBox jCh9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable ttutores;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}

```

### `src\vista\ITutorado.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatLightLaf;
import control.AdmDatos;
import control.TutorJpaController;
import control.TutoradoJpaController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import modelo.MTtutor;
import modelo.Tutor;
import modelo.Tutorado;

/**
 *
 * @author jesus
 */
public class ITutorado extends javax.swing.JFrame {

    private Tutor tutor;
    private TutorJpaController cTutor;
    private AdmDatos adm;
    private List<Tutor> tutores;
    
    private Tutorado tutorado;
    private List<Tutorado> tutorados;
    private TutoradoJpaController cTutorado;
    
    private Map<String, Tutorado> tutorado_nom = new HashMap<>();
    
    public ITutorado() {
        initComponents();
        setLocationRelativeTo(null);
        
        adm = new AdmDatos();
        
        cTutor = new TutorJpaController(adm.getEnf());
        tutores = cTutor.findTutorEntities();
        cTutorado = new TutoradoJpaController(adm.getEnf());
        tutorados = cTutorado.findTutoradoEntities();
        
        cargarTutores();
        cargarTutorado();
        
        //dlistTutorados.setEnabled(false);
        dlistTutorados.setVisible(false);
        
    }
    
    public void cargarTutores(){
        cboTutores.removeAllItems();
        cboTutores.addItem("Seleccione Tutor");
        
        for(Tutor dtutor : tutores){
            cboTutores.addItem(dtutor.getNombre());
        }
    }
    
    public void cargarTutorado(){
        // Ya no se inicializan mTutoradosDisponibles ni mTutoradosSeleccionados aquí
        tutorado_nom.clear(); // Limpiar el mapa anterior

        List<String> nombresDisponibles = new ArrayList<>();
        for (Tutorado dTutorado : tutorados) {
            if (dTutorado.getTutor() == null) { // Solo tutorados sin tutor asignado
                String nombre = dTutorado.getNombre();
                nombresDisponibles.add(nombre);
                tutorado_nom.put(nombre, dTutorado); // Mapear nombre a objeto Tutorado
            }
        }
        // Poblar el componente DualListTransfer
        dlistTutorados.setElementosDisponibles(nombresDisponibles);
        // Asegurar que la lista de seleccionados en DualListTransfer esté vacía al recargar
        dlistTutorados.setElementosSeleccionadosIniciales(new ArrayList<>());
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabbedPane1 = new javax.swing.JTabbedPane();
        btnAceptar = new javax.swing.JPanel();
        cboTutores = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnAceptarSeleccion = new javax.swing.JButton();
        btnAceptarTutor = new javax.swing.JButton();
        dlistTutorados = new elements.DualListTransfer();
        panel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cboTutores.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Seleccionar Tutor:");

        jLabel2.setText("Selección de Tutorados");

        btnAceptarSeleccion.setText("Aceptar");
        btnAceptarSeleccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarSeleccionActionPerformed(evt);
            }
        });

        btnAceptarTutor.setText("AceptarTutor");
        btnAceptarTutor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarTutorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout btnAceptarLayout = new javax.swing.GroupLayout(btnAceptar);
        btnAceptar.setLayout(btnAceptarLayout);
        btnAceptarLayout.setHorizontalGroup(
            btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAceptarLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(cboTutores, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(btnAceptarTutor)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAceptarLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addGroup(btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, btnAceptarLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(209, 209, 209))
                    .addComponent(dlistTutorados, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(btnAceptarLayout.createSequentialGroup()
                .addGap(247, 247, 247)
                .addComponent(btnAceptarSeleccion, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        btnAceptarLayout.setVerticalGroup(
            btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(btnAceptarLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(btnAceptarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cboTutores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAceptarTutor))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addComponent(dlistTutorados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnAceptarSeleccion)
                .addGap(15, 15, 15))
        );

        tabbedPane1.addTab("Asignar Tutor", btnAceptar);

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 557, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 305, Short.MAX_VALUE)
        );

        tabbedPane1.addTab("Registrar Tutorado", panel2);

        getContentPane().add(tabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 340));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarTutorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarTutorActionPerformed
        if (cboTutores.getSelectedIndex() <= 0) { // "Seleccione Tutor" está en el índice 0
            JOptionPane.showMessageDialog(this, "Seleccione un Tutor válido.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (JOptionPane.showConfirmDialog(this, "Seleccionar al tutor: " + cboTutores.getSelectedItem() + "?",
                "Confirmar Tutor", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {

            tutor = tutores.get(cboTutores.getSelectedIndex() - 1); // -1 porque "Seleccione Tutor" es el primer item
            cboTutores.setEnabled(false);
            btnAceptarTutor.setText("Tutor Confirmado");
            btnAceptarTutor.setEnabled(false);

            // Habilitar el componente DualListTransfer para la selección de tutorados
            //dlistTutorados.setEnabled(true);
            dlistTutorados.setVisible(true);
        }
    }//GEN-LAST:event_btnAceptarTutorActionPerformed

    private void btnAceptarSeleccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarSeleccionActionPerformed
        if (tutor == null) {
            JOptionPane.showMessageDialog(this, "Primero debe confirmar un Tutor.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<String> nombresSeleccionados = dlistTutorados.getElementosSeleccionados();

        if (nombresSeleccionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No ha seleccionado ningún tutorado para asignar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmados = 0;
        for (String nombreTutorado : nombresSeleccionados) {
            Tutorado t = tutorado_nom.get(nombreTutorado); // Usar el nombre como clave

            if (t != null) {
                t.setTutor(tutor); // Asignar el tutor seleccionado
                try {
                    cTutorado.edit(t); // Guardar cambios en la BD
                    confirmados++;
                } catch (Exception ex) {
                    Logger.getLogger(ITutorado.class.getName()).log(Level.SEVERE, "Error al asignar tutor a: " + nombreTutorado, ex);
                    JOptionPane.showMessageDialog(this, "Error al asignar tutor a: " + nombreTutorado + "\n" + ex.getMessage(), "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                 Logger.getLogger(ITutorado.class.getName()).log(Level.WARNING, "No se encontró el objeto Tutorado para el nombre: " + nombreTutorado);
            }
        }
        
        if (confirmados > 0) {
             JOptionPane.showMessageDialog(this, confirmados + " tutorado(s) asignado(s) correctamente al tutor: " + tutor.getNombre(), "Asignación Exitosa", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo completar la asignación de tutorados.", "Error de Asignación", JOptionPane.ERROR_MESSAGE);
        }

        tutorados = cTutorado.findTutoradoEntities(); // Recargar lista de tutorados desde la BD
        cargarTutorado(); // Esto refrescará dlistTutorados (disponibles y limpiará seleccionados)

        // Resetear selección de tutor para una nueva asignación
        cboTutores.setEnabled(true);
        cboTutores.setSelectedIndex(0);
        btnAceptarTutor.setText("Confirmar Tutor");
        btnAceptarTutor.setEnabled(true);
        dlistTutorados.setEnabled(false); // Deshabilitar hasta que se confirme un nuevo tutor
        tutor = null; // Limpiar el tutor seleccionado
    }//GEN-LAST:event_btnAceptarSeleccionActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ITutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ITutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ITutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ITutorado.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        UIManager.setLookAndFeel(new FlatLightLaf());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ITutorado().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnAceptar;
    private javax.swing.JButton btnAceptarSeleccion;
    private javax.swing.JButton btnAceptarTutor;
    private javax.swing.JComboBox<String> cboTutores;
    private elements.DualListTransfer dlistTutorados;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel panel2;
    private javax.swing.JTabbedPane tabbedPane1;
    // End of variables declaration//GEN-END:variables
}

```

### `src\vista\ITutoria.java`

```java
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import com.formdev.flatlaf.FlatLightLaf;
import control.AdmDatos;
import control.CitaJpaController;
import control.TutorJpaController; // No se usa directamente aquí, pero es parte del contexto
import control.TutoradoJpaController; // No se usa directamente aquí
import control.TutoriaJpaController;
import control.exceptions.IllegalOrphanException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import modelo.Cita;
import modelo.DatosTablaCitas; // Asumo que esta clase la usas para MTablaCita
import modelo.MTablaCita;
// import modelo.MTtutor; // No se usa en esta clase directamente
import modelo.Tutor;
import modelo.Tutorado;
import modelo.Tutoria;

/**
 *
 * @author jesus
 */
public class ITutoria extends javax.swing.JFrame {

    //<editor-fold defaultstate="collapsed" desc="Variables de instancia y constantes">
    private Tutor tutorSeleccionado; // Sigue siendo útil para saber con qué tutor se está trabajando una vez se selecciona una cita
    private Cita citaSeleccionada;

    private final CitaJpaController cCita;
    private final TutoradoJpaController cTutoradoController;
    private final TutoriaJpaController cTutoria;
    private final AdmDatos adm;

    private List<Cita> listaTotalCitas; // Cargar todas las citas al inicio
    private List<Tutorado> listaTotalTutoradosGeneral;

    // citaMap ahora usará un String que puede incluir el nombre del tutor y la fecha para ser único
    private final Map<String, Cita> citaMap = new HashMap<>();

    private MTablaCita modeloTablaTutoradosEnCita;
    private List<DatosTablaCitas> datosParaTablaTutorados;

    private final String SELECCIONA_CITA_TEXT = "Seleccione Cita de Hoy"; // O un texto similar
    private final String NO_HAY_CITAS_TEXT = "No hay citas para esta fecha";
    
    private boolean uiInicializada = false; // Nueva bandera
    
    //</editor-fold>
    
    public ITutoria() {
        initComponents();
        setLocationRelativeTo(null);

        // 2. Controladores y datos maestros
        adm = new AdmDatos();
        cCita = new CitaJpaController(adm.getEnf());
        cTutoradoController = new TutoradoJpaController(adm.getEnf());
        cTutoria = new TutoriaJpaController(adm.getEnf());
        listaTotalCitas = cCita.findCitaEntities();
        listaTotalTutoradosGeneral = cTutoradoController.findTutoradoEntities();

        // 3. Modelo de la tabla y asignación a JTable
        datosParaTablaTutorados = new ArrayList<>();
        modeloTablaTutoradosEnCita = new MTablaCita((ArrayList<DatosTablaCitas>) datosParaTablaTutorados); // Aquí ya no necesitas el cast (ArrayList) si datosParaTablaTutorados es ArrayList
        tabTutorados.setModel(modeloTablaTutoradosEnCita);

        // 4. Configuración inicial de otros componentes UI
        cboCitas.removeAllItems();
        cboCitas.addItem(SELECCIONA_CITA_TEXT);
        dateSeleccionarFecha.setDate(null);

        // 5. Añadir Listeners DESPUÉS de que todo lo referenciado por ellos esté inicializado
        dateSeleccionarFecha.getDateEditor().addPropertyChangeListener(evt -> {
            if (uiInicializada && "date".equals(evt.getPropertyName())) {
                dateSeleccionarFechaPropertyChange(evt);
            }
        });
        // Considera si necesitas action listener para cboCitas aquí, o si btnAceptarCita es suficiente.
        // cboCitas.addActionListener(evt -> cboCitasActionPerformed(evt));


        // 6. Establecer estado inicial de la UI
        actualizarEstadoComponentes();

        // 7. Marcar UI como completamente inicializada
        uiInicializada = true;
    }
    

    
    private void actualizarEstadoComponentes() {
        boolean fechaHaSidoSeleccionada = (dateSeleccionarFecha.getDate() != null);

        boolean citaValidaSeleccionableEnCombo = false;
        if (fechaHaSidoSeleccionada && cboCitas.getItemCount() > 0 && cboCitas.getSelectedIndex() > 0) {
            Object selectedItemObj = cboCitas.getSelectedItem();
            if (selectedItemObj != null) {
                String selectedItemText = selectedItemObj.toString();
                if (!SELECCIONA_CITA_TEXT.equals(selectedItemText) && !NO_HAY_CITAS_TEXT.equals(selectedItemText)) {
                    citaValidaSeleccionableEnCombo = true;
                }
            }
        }

        // Una cita está "confirmada" si this.citaSeleccionada y this.tutorSeleccionado tienen valor
        // (lo que ocurre DESPUÉS de presionar btnAceptarCita)
        boolean citaHaSidoConfirmada = (this.citaSeleccionada != null && this.tutorSeleccionado != null);

        // Habilitar ComboBox de Citas si hay fecha
        cboCitas.setEnabled(fechaHaSidoSeleccionada);
        jLabel3.setEnabled(fechaHaSidoSeleccionada); // Etiqueta "Cita:"

        // Habilitar botón AceptarCita si hay una cita válida seleccionable en el combo
        btnAceptarCita.setEnabled(dateSeleccionarFecha.getDate() != null);

        // Componentes que dependen de que una cita haya sido confirmada
        jLabel2.setEnabled(citaHaSidoConfirmada); // "Tutorados"
        jScrollPane1.setEnabled(citaHaSidoConfirmada);
        tabTutorados.setEnabled(citaHaSidoConfirmada);
        // Considerar si el rowCount de la tabla es > 0 para habilitar registrar.
        // Si la tabla está vacía porque el tutor no tiene tutorados, igual se debe poder registrar
        // la tutoría (aunque no haya acciones individuales). Esto depende de tu lógica de negocio.
        // Por ahora, lo mantendremos así:
        btnRegistrarTutoria.setEnabled(citaHaSidoConfirmada && tabTutorados.getRowCount() > 0);


        // Lógica de limpieza si los prerrequisitos no se cumplen
        if (!fechaHaSidoSeleccionada) {
            // Si no hay fecha, cboCitas ya debería estar reseteado por dateSeleccionarFechaPropertyChange
            // pero podemos asegurarlo.
            if (cboCitas.getItemCount() == 0 || !SELECCIONA_CITA_TEXT.equals(cboCitas.getItemAt(0))) {
                cboCitas.removeAllItems();
                cboCitas.addItem(SELECCIONA_CITA_TEXT);
            } else if (cboCitas.getSelectedIndex() !=0 && cboCitas.getItemCount() > 0) {
                 cboCitas.setSelectedIndex(0);
            }
            // Si no hay fecha, no puede haber cita ni tutor seleccionado confirmados
            this.citaSeleccionada = null;
            this.tutorSeleccionado = null;
        }

        if (!citaHaSidoConfirmada) {
            // Si no hay cita confirmada, la tabla de tutorados debe estar vacía.
            // popularTablaTutorados(null) ya se encarga de limpiar 'datosParaTablaTutorados'
            // y llamar a actualizarListaDatos en el modelo.
            // Así que esta limpieza aquí podría ser redundante si popularTablaTutorados(null) se llama correctamente.
            if (datosParaTablaTutorados != null && !datosParaTablaTutorados.isEmpty()) {
                datosParaTablaTutorados.clear();
                 if (modeloTablaTutoradosEnCita != null) { // Comprobar si el modelo está inicializado
                    modeloTablaTutoradosEnCita.actualizarListaDatos((ArrayList<DatosTablaCitas>) datosParaTablaTutorados);
                }
            }
        }
    }
    
    private void cargarCitasComboBox(Tutor tutor) {
        cboCitas.removeAllItems();
        cboCitas.addItem(SELECCIONA_CITA_TEXT);
        citaMap.clear();
        citaSeleccionada = null;

        if (tutor == null) {
            actualizarEstadoComponentes();
            return;
        }

        System.out.println("Cargando citas para tutor: " + tutor.getNombre()); // DEBUG
        
        SimpleDateFormat sdfFechaUnicamente = new SimpleDateFormat("dd/MM/yyyy");
        boolean hayCitasDisponibles = false;

        for (Cita c : listaTotalCitas) {
            // Comprueba que la cita pertenezca al tutor seleccionado Y que su estado sea "PENDIENTE"
            if (c.getTutor() != null && c.getTutor().equals(tutor) && "PENDIENTE".equalsIgnoreCase(c.getEstado())) {
                // ... el resto de la lógica para formatear displayText y añadir a cboCitas y citaMap permanece igual ...
                String fechaFormateada = (c.getFecha() != null) ? sdfFechaUnicamente.format(c.getFecha()) : "Fecha N/A";
                String horaFormateada = (c.getHora() != null) ? String.format("%02d:00", c.getHora()) : "Hora N/A";
                String displayText = String.format("%s a las %s (%s)", fechaFormateada, horaFormateada, c.getAsunto());

                cboCitas.addItem(displayText);
                citaMap.put(displayText, c);
                hayCitasDisponibles = true;
            }
        }
        
        System.out.println("Items en cboCitas después de cargar: " + cboCitas.getItemCount()); // DEBUG
        if (!hayCitasDisponibles && cboCitas.getItemCount() <=1 ) { // Si solo queda el "Seleccione Cita"
            cboCitas.addItem("No hay citas pendientes para este tutor");
             System.out.println("Añadido 'No hay citas...' Items: " + cboCitas.getItemCount()); // DEBUG
        }
        actualizarEstadoComponentes();
    }
    
    private void popularTablaTutorados(Tutor tutorDeLaCita) {
        if (datosParaTablaTutorados == null) { // Asegurar que la lista de datos exista
            datosParaTablaTutorados = new ArrayList<>();
        }
        datosParaTablaTutorados.clear();

        if (tutorDeLaCita != null) {
            for (Tutorado unTutorado : listaTotalTutoradosGeneral) {
                if (unTutorado.getTutor() != null && unTutorado.getTutor().equals(tutorDeLaCita)) {
                    datosParaTablaTutorados.add(new DatosTablaCitas(unTutorado));
                }
            }
        }

        // Comprobación crucial ANTES de usar el modelo
        if (modeloTablaTutoradosEnCita != null) {
            modeloTablaTutoradosEnCita.actualizarListaDatos((ArrayList<DatosTablaCitas>) datosParaTablaTutorados); // No necesitas el cast si datosParaTablaTutorados es ArrayList
        } else {
            // Esto indica un problema de inicialización más profundo si ocurre fuera del arranque inicial.
            System.err.println("ERROR: modeloTablaTutoradosEnCita es null en popularTablaTutorados. La tabla no se actualizará.");
            // Podrías intentar re-inicializarlo como último recurso, pero es mejor encontrar la causa raíz.
            // modeloTablaTutoradosEnCita = new MTablaCita(datosParaTablaTutorados);
            // tabTutorados.setModel(modeloTablaTutoradosEnCita);
        }

        // La reconfiguración del editor solo si la tabla y el modelo existen
        if (tabTutorados != null && tabTutorados.getColumnCount() > 2 && modeloTablaTutoradosEnCita != null) {
            TableColumn accionesColumn = tabTutorados.getColumnModel().getColumn(2);
            JComboBox<String> comboBoxEditor = new JComboBox<>(new String[]{"Sin acción", "Asesoría", "Trámite", "Atención Médica / Psicológica"});
            accionesColumn.setCellEditor(new DefaultCellEditor(comboBoxEditor));
            // System.out.println("Editor de JComboBox para columna 'Acción' re-configurado.");
        } else if (tabTutorados != null && modeloTablaTutoradosEnCita != null) { // Modelo existe pero no columnas suficientes
            // System.out.println("Advertencia: La tabla no tiene suficientes columnas para configurar el editor de 'Acción'. Columnas: " + tabTutorados.getColumnCount());
        }

        if (uiInicializada) { // Solo llama si la UI está lista
            actualizarEstadoComponentes();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabTutorados = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        cboCitas = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        btnAceptarCita = new javax.swing.JButton();
        btnRegistrarTutoria = new javax.swing.JButton();
        dateSeleccionarFecha = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tabTutorados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tabTutorados);

        jLabel2.setText("Tutorados");

        jLabel3.setText("Tutor / Hora");

        cboCitas.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("Realizando una Tutoria");

        btnAceptarCita.setText("Aceptar");
        btnAceptarCita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarCitaActionPerformed(evt);
            }
        });

        btnRegistrarTutoria.setText("Registrar Tutoria");
        btnRegistrarTutoria.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarTutoriaActionPerformed(evt);
            }
        });

        dateSeleccionarFecha.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                dateSeleccionarFechaPropertyChange(evt);
            }
        });

        jLabel5.setText("Fecha:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel4))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(200, 200, 200)
                                .addComponent(jLabel2)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(btnRegistrarTutoria)
                        .addGap(162, 162, 162))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(28, 28, 28)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(dateSeleccionarFecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(cboCitas, 0, 200, Short.MAX_VALUE))
                        .addGap(49, 49, 49)
                        .addComponent(btnAceptarCita)
                        .addGap(25, 25, 25))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel4)
                .addGap(22, 22, 22)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateSeleccionarFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cboCitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAceptarCita)))
                .addGap(29, 29, 29)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnRegistrarTutoria)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAceptarCitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarCitaActionPerformed
        if (dateSeleccionarFecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Primero debe seleccionar una fecha.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Valida que no sea el placeholder o el mensaje "No hay citas..."
        if (cboCitas.getSelectedIndex() <= 0 || 
            SELECCIONA_CITA_TEXT.equals(cboCitas.getSelectedItem()) || 
            NO_HAY_CITAS_TEXT.equals(cboCitas.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "Seleccione una cita válida de la lista.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            this.citaSeleccionada = null;
            this.tutorSeleccionado = null;
            popularTablaTutorados(null); // Limpia la tabla
            // actualizarEstadoComponentes(); // popularTablaTutorados ya lo llama
            return;
        }

        String citaDisplayText = (String) cboCitas.getSelectedItem();
        Cita citaDelCombo = citaMap.get(citaDisplayText);

        if (citaDelCombo != null) {
            if (citaDelCombo.getTutor() == null) {
                JOptionPane.showMessageDialog(this, "La cita seleccionada no tiene un tutor asignado. No se puede proceder.", "Error de Datos", JOptionPane.ERROR_MESSAGE);
                this.citaSeleccionada = null;
                this.tutorSeleccionado = null;
                popularTablaTutorados(null);
                return;
            }
            this.citaSeleccionada = citaDelCombo;
            this.tutorSeleccionado = this.citaSeleccionada.getTutor(); // Establecer el tutor basado en la cita

            popularTablaTutorados(this.tutorSeleccionado); // Popular tabla con tutorados de este tutor
        } else {
            JOptionPane.showMessageDialog(this, "Cita seleccionada no encontrada en el mapa. Intente de nuevo.", "Error Interno", JOptionPane.ERROR_MESSAGE);
            this.citaSeleccionada = null;
            this.tutorSeleccionado = null;
            popularTablaTutorados(null);
        }
        // actualizarEstadoComponentes(); // popularTablaTutorados ya lo llama// Actualizar el estado de la UI
    }//GEN-LAST:event_btnAceptarCitaActionPerformed

    private void btnRegistrarTutoriaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarTutoriaActionPerformed
        if (citaSeleccionada == null || citaSeleccionada.getTutor() == null) {
            JOptionPane.showMessageDialog(this, "Debe tener una cita válida aceptada (con tutor asignado).", "Información Incompleta", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // No es necesario verificar datosParaTablaTutorados.isEmpty() aquí si el modelo
        // maneja una fila de totales, porque getRowCount() sería al menos 1.
        // Es mejor verificar si hay datos *reales* de tutorados.
        if (datosParaTablaTutorados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay tutorados listados para esta cita para registrar.", "Información Incompleta", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (this.tutorSeleccionado != null && !citaSeleccionada.getTutor().equals(this.tutorSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Inconsistencia de datos del tutor. Por favor, re-seleccione la cita.", "Error de Consistencia", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int tutoriasRegistradasExitosamente = 0;

        // Iterar SOLAMENTE sobre las filas de datos, NO sobre la fila de totales.
        // El tamaño de 'datosParaTablaTutorados' es el número de filas de datos reales.
        for (int i = 0; i < datosParaTablaTutorados.size(); i++) { // <<--- CAMBIO IMPORTANTE AQUÍ
            DatosTablaCitas datosFila = datosParaTablaTutorados.get(i); // Ahora 'i' siempre será un índice válido para datosParaTablaTutorados
            Tutorado tutoradoDeLaFila = datosFila.getTutorado();

            // Obtener valores de la tabla (modelo) para la fila 'i' (que corresponde a una fila de datos)
            Boolean asistencia = (Boolean) modeloTablaTutoradosEnCita.getValueAt(i, 1);
            String accion = (String) modeloTablaTutoradosEnCita.getValueAt(i, 2);

            if (tutoradoDeLaFila != null && asistencia != null && asistencia) {
                Tutoria nuevaTutoria = new Tutoria();
                nuevaTutoria.setIdCita(citaSeleccionada);
                nuevaTutoria.setIdTutorado(tutoradoDeLaFila);
                nuevaTutoria.setAcciones((accion == null || accion.equals("Sin acción") || accion.trim().isEmpty()) ? "Asistencia registrada." : accion);

                try {
                    cTutoria.create(nuevaTutoria);
                    tutoriasRegistradasExitosamente++;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al guardar tutoría para " + tutoradoDeLaFila.getNombre() + ": " + ex.getMessage(), "Error de Registro", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            }
        }

        // ... (resto del método para actualizar estado de cita y limpiar, como lo tenías)
        if (tutoriasRegistradasExitosamente > 0) {
            try {
                Cita citaParaActualizar = cCita.findCita(citaSeleccionada.getIdCita());
                if (citaParaActualizar != null) {
                    citaParaActualizar.setEstado("REALIZADA");
                    cCita.edit(citaParaActualizar);
                    int indexEnListaMaestra = listaTotalCitas.indexOf(citaSeleccionada);
                    if (indexEnListaMaestra != -1) {
                        listaTotalCitas.set(indexEnListaMaestra, citaParaActualizar);
                    }
                    citaSeleccionada = citaParaActualizar;
                    JOptionPane.showMessageDialog(this, tutoriasRegistradasExitosamente + " tutoría(s) registrada(s) exitosamente. La cita ha sido marcada como 'REALIZADA'.", "Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error: No se pudo recargar la cita para actualizar su estado.", "Error de Actualización", JOptionPane.ERROR_MESSAGE);
                }
            } catch (IllegalOrphanException ioe) {
                JOptionPane.showMessageDialog(this, "Error de relación al actualizar cita (IllegalOrphanException): " +
                    ioe.getMessage() + (ioe.getMessages() != null ? "\nDetalles: " + String.join(", ", ioe.getMessages()) : ""),
                    "Error de Datos", JOptionPane.ERROR_MESSAGE);
                ioe.printStackTrace();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, tutoriasRegistradasExitosamente +
                    " tutoría(s) registrada(s), pero hubo un error al actualizar el estado de la cita: " + ex.getMessage(),
                    "Error Parcial al Actualizar Cita", JOptionPane.WARNING_MESSAGE);
                ex.printStackTrace();
            }
        } else if (!datosParaTablaTutorados.isEmpty()) { // Se modificó esta condición para ser más precisa
            boolean algunaAsistenciaMarcada = false;
            for (int i = 0; i < datosParaTablaTutorados.size(); i++) { // Iterar sobre datos reales
                Boolean asistencia = (Boolean) modeloTablaTutoradosEnCita.getValueAt(i, 1);
                if (asistencia != null && asistencia) {
                    algunaAsistenciaMarcada = true;
                    break;
                }
            }
            if(!algunaAsistenciaMarcada){
                 JOptionPane.showMessageDialog(this, "No se marcaron asistencias. La cita no se marcará como 'REALIZADA'.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                 JOptionPane.showMessageDialog(this, "No se registraron tutorías aunque hubo asistencias marcadas (posible error previo). Verifique.", "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } else { // datosParaTablaTutorados está vacío
             JOptionPane.showMessageDialog(this, "No hay tutorados en la lista para registrar.", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
        limpiarCamposYInterfaz();
    }//GEN-LAST:event_btnRegistrarTutoriaActionPerformed

    private void cboTutoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboTutoresActionPerformed
        
    }//GEN-LAST:event_cboTutoresActionPerformed

    private void dateSeleccionarFechaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_dateSeleccionarFechaPropertyChange
        // El listener ya filtra por evt.getPropertyName() == "date"
        java.util.Date fechaSeleccionada = dateSeleccionarFecha.getDate();

        // Limpiar selecciones previas y tabla independientemente de si hay nueva fecha o no.
        // Esto asegura que al cambiar la fecha (incluso a null), la tabla se limpie.
        this.citaSeleccionada = null;
        this.tutorSeleccionado = null; // El tutor se deriva de la cita
        popularTablaTutorados(null); // Limpia la tabla

        if (fechaSeleccionada != null) {
            cargarCitasDelDia(fechaSeleccionada); // Carga nuevas citas y actualiza UI
        } else {
            // Si la fecha se borra, limpiar cboCitas
            cboCitas.removeAllItems();
            cboCitas.addItem(SELECCIONA_CITA_TEXT);
            citaMap.clear();
            actualizarEstadoComponentes(); // Actualiza la UI
        }
    }//GEN-LAST:event_dateSeleccionarFechaPropertyChange
    
    private void cboCitasActionPerformed(java.awt.event.ActionEvent evt) {
        // Si se selecciona "Seleccione Cita" o no hay citas válidas,
        // limpiar la selección de cita y actualizar componentes.
        if (cboCitas.getSelectedIndex() <= 0 ||
            SELECCIONA_CITA_TEXT.equals(cboCitas.getSelectedItem()) ||
            "No hay citas pendientes para este tutor".equals(cboCitas.getSelectedItem())) {

            citaSeleccionada = null; // Anular la cita seleccionada
            // Limpiar la tabla si la cita ya no es válida
            datosParaTablaTutorados.clear();
            if (modeloTablaTutoradosEnCita != null) { // Asegurarse que el modelo no es null
                 modeloTablaTutoradosEnCita.fireTableDataChanged();
            }
        } else {
            // Una cita válida ha sido seleccionada en el ComboBox.
            // No establecemos 'citaSeleccionada' aquí, eso lo hace btnAceptarCita.
            // La acción aquí es principalmente para actualizar el estado de los botones.
        }
        actualizarEstadoComponentes(); // Asegura que el estado de btnAceptarCita se reevalúe
    }


    private void limpiarCamposYInterfaz() {
        tutorSeleccionado = null;
        citaSeleccionada = null;

        // Desactivar temporalmente el listener del JDateChooser para evitar llamadas recursivas o prematuras
        // Esto es una técnica común si el setDate(null) dispara el evento y causa problemas.
        boolean listenerWasActive = uiInicializada;
        uiInicializada = false; // Desactivar temporalmente el procesamiento del evento
        dateSeleccionarFecha.setDate(null);
        uiInicializada = listenerWasActive; // Restaurar estado del listener

        cboCitas.removeAllItems();
        cboCitas.addItem(SELECCIONA_CITA_TEXT);
        citaMap.clear();

        if(datosParaTablaTutorados == null) datosParaTablaTutorados = new ArrayList<>();
        datosParaTablaTutorados.clear();

        if(modeloTablaTutoradosEnCita != null) { // Comprobar nulidad
            modeloTablaTutoradosEnCita.actualizarListaDatos((ArrayList<DatosTablaCitas>) datosParaTablaTutorados); 
        } else {
            // Si el modelo es null aquí, hay un problema de inicialización.
            // Como mínimo, la lista de datos está vacía.
            System.err.println("ADVERTENCIA: modeloTablaTutoradosEnCita es null en limpiarCamposYInterfaz.");
        }
        actualizarEstadoComponentes();
    }
    
    private void cargarCitasDelDia(java.util.Date fechaSeleccionada) {
        cboCitas.removeAllItems();
        cboCitas.addItem(SELECCIONA_CITA_TEXT); // Placeholder inicial
        citaMap.clear();

        // Cuando se carga por fecha, la cita y el tutor seleccionados previamente deben resetearse
        // hasta que el usuario explícitamente acepte una nueva cita.
        this.citaSeleccionada = null; 
        this.tutorSeleccionado = null;

        if (fechaSeleccionada == null) {
            actualizarEstadoComponentes(); // Actualiza la UI (ej. deshabilita btnAceptarCita)
            return;
        }

        SimpleDateFormat sdfCompararFecha = new SimpleDateFormat("yyyyMMdd");
        String fechaSeleccionadaStr = sdfCompararFecha.format(fechaSeleccionada);
        boolean hayCitasDisponibles = false;

        // System.out.println("Filtrando citas para la fecha: " + fechaSeleccionadaStr);

        for (Cita c : listaTotalCitas) {
            if (c.getFecha() != null && "PENDIENTE".equalsIgnoreCase(c.getEstado())) {
                String fechaCitaStr = sdfCompararFecha.format(c.getFecha());
                if (fechaCitaStr.equals(fechaSeleccionadaStr)) {
                    String tutorNombre = (c.getTutor() != null && c.getTutor().getNombre() != null) ? c.getTutor().getNombre() : "Tutor Desconocido";
                    String horaFormateada = (c.getHora() != null) ? String.format("%02d:00", c.getHora()) : "Hora N/A";
                    String asunto = (c.getAsunto() != null && !c.getAsunto().isEmpty()) ? c.getAsunto() : "Sin Asunto";

                    String displayText = String.format("Tutor: %s - %s (%s)", tutorNombre, horaFormateada, asunto);

                    cboCitas.addItem(displayText);
                    citaMap.put(displayText, c);
                    hayCitasDisponibles = true;
                }
            }
        }

        if (!hayCitasDisponibles) { // Si no se añadió ninguna cita real
            // Y solo está el item "Seleccione Cita..."
            if (cboCitas.getItemCount() <= 1) {
                 cboCitas.addItem(NO_HAY_CITAS_TEXT);
            }
        }

        if (cboCitas.getItemCount() > 0) {
            cboCitas.setSelectedIndex(0); // Dejar "Seleccione Cita..." por defecto
        }
        actualizarEstadoComponentes();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) throws UnsupportedLookAndFeelException {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ITutoria.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        UIManager.setLookAndFeel(new FlatLightLaf());
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ITutoria().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptarCita;
    private javax.swing.JButton btnRegistrarTutoria;
    private javax.swing.JComboBox<String> cboCitas;
    private com.toedter.calendar.JDateChooser dateSeleccionarFecha;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabTutorados;
    // End of variables declaration//GEN-END:variables
}

```

