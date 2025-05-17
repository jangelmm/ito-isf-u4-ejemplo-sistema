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