-- Base de Datos: club_matematicas_db
-- Script de Creación de Tablas
-- Fecha: 2025-05-15

-- --- Creación de la Base de Datos ---

CREATE DATABASE academicplus;
USE academicplus;

-- --- Tabla Usuarios ---
CREATE TABLE IF NOT EXISTS `Usuarios` (
    `id_usuario` INT AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(150) NOT NULL,
    `correo` VARCHAR(100) NOT NULL UNIQUE,
    `contrasena_hash` VARCHAR(255) NOT NULL,
    `rol` ENUM('ADMINISTRADOR','DOCENTE','TALLERISTA') NOT NULL,
    `numero_control` VARCHAR(30) NULL UNIQUE COMMENT 'Para alumnos talleristas, puede ser NULL para docentes/admins',
    `fecha_registro` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `ultima_modificacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `activo` BOOLEAN DEFAULT TRUE COMMENT 'Para desactivar usuarios en lugar de borrar'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --- Tabla Talleres ---
CREATE TABLE IF NOT EXISTS `Talleres` (
    `id_taller` INT AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(200) NOT NULL,
    `descripcion_publica` TEXT NOT NULL COMMENT 'Descripción para el público general',
    `detalles_internos` TEXT NULL COMMENT 'Notas o detalles para el equipo y talleristas',
    `requisitos_materiales` TEXT NOT NULL,
    `manual_ruta` VARCHAR(255) NULL COMMENT 'Ruta al archivo del manual del taller',
    `estado` ENUM('PENDIENTE_PROPUESTA','EN_REVISION_DOCENTE','REQUIERE_MODIFICACION','APROBADO','RECHAZADO','ARCHIVADO') NOT NULL,
    `id_usuario_proponente` INT NOT NULL,
    `fecha_creacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `ultima_modificacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`id_usuario_proponente`) REFERENCES `Usuarios`(`id_usuario`) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX `idx_taller_nombre` (`nombre`),
    INDEX `idx_taller_estado` (`estado`),
    INDEX `idx_taller_proponente` (`id_usuario_proponente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --- Tabla Comentarios_Revision_Taller ---
CREATE TABLE IF NOT EXISTS `Comentarios_Revision_Taller` (
    `id_comentario` INT AUTO_INCREMENT PRIMARY KEY,
    `id_taller` INT NOT NULL,
    `id_usuario_comentarista` INT NOT NULL COMMENT 'Docente que comenta',
    `comentario` TEXT NOT NULL,
    `fecha_comentario` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (`id_taller`) REFERENCES `Talleres`(`id_taller`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`id_usuario_comentarista`) REFERENCES `Usuarios`(`id_usuario`) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX `idx_comentario_taller` (`id_taller`),
    INDEX `idx_comentario_usuario` (`id_usuario_comentarista`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --- Tabla Convocatorias ---
CREATE TABLE IF NOT EXISTS `Convocatorias` (
    `id_convocatoria` INT AUTO_INCREMENT PRIMARY KEY,
    `titulo` VARCHAR(255) NOT NULL,
    `descripcion` TEXT NOT NULL,
    `fecha_publicacion` DATE NOT NULL,
    `fecha_limite_propuestas` DATE NULL COMMENT 'Fecha límite para que talleristas propongan talleres o se inscriban',
    `documento_adjunto_ruta` VARCHAR(255) NULL COMMENT 'Ruta al PDF de la convocatoria oficial',
    `id_usuario_publica` INT NOT NULL COMMENT 'Admin o Docente que publica',
    `fecha_creacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `ultima_modificacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`id_usuario_publica`) REFERENCES `Usuarios`(`id_usuario`) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX `idx_convocatoria_titulo` (`titulo`),
    INDEX `idx_convocatoria_publicador` (`id_usuario_publica`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --- Tabla Eventos ---
CREATE TABLE IF NOT EXISTS `Eventos` (
    `id_evento` INT AUTO_INCREMENT PRIMARY KEY,
    `nombre` VARCHAR(255) NOT NULL,
    `descripcion_publica` TEXT NULL COMMENT 'Descripción del evento para el público',
    `fecha_evento` DATE NOT NULL,
    `hora_inicio_evento` TIME NULL,
    `hora_fin_evento` TIME NULL,
    `lugar_evento` VARCHAR(255) NULL,
    `estado_evento` ENUM('PLANIFICADO','CONFIRMADO','EN_CURSO','FINALIZADO','CANCELADO','POSPUESTO') NOT NULL,
    `id_convocatoria_origen` INT NULL,
    `id_docente_responsable` INT NULL COMMENT 'Docente a cargo del evento',
    `fecha_creacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `ultima_modificacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (`id_convocatoria_origen`) REFERENCES `Convocatorias`(`id_convocatoria`) ON DELETE SET NULL ON UPDATE CASCADE,
    FOREIGN KEY (`id_docente_responsable`) REFERENCES `Usuarios`(`id_usuario`) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX `idx_evento_nombre` (`nombre`),
    INDEX `idx_evento_fecha` (`fecha_evento`),
    INDEX `idx_evento_estado` (`estado_evento`),
    INDEX `idx_evento_convocatoria` (`id_convocatoria_origen`),
    INDEX `idx_evento_docente_resp` (`id_docente_responsable`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --- Tabla Evento_Participantes_Talleres (Relación Muchos-a-Muchos entre Eventos, Talleristas y Talleres) ---
CREATE TABLE IF NOT EXISTS `Evento_Participantes_Talleres` (
    `id_evento_participante_taller` INT AUTO_INCREMENT PRIMARY KEY,
    `id_evento` INT NOT NULL,
    `id_tallerista` INT NOT NULL COMMENT 'FK a Usuarios con rol TALLERISTA',
    `id_taller_impartido` INT NOT NULL COMMENT 'FK a Talleres, el taller específico que imparte',
    `rol_participante` VARCHAR(150) NULL COMMENT 'Ej: Expositor Principal, Co-expositor, Apoyo Logístico',
    FOREIGN KEY (`id_evento`) REFERENCES `Eventos`(`id_evento`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`id_tallerista`) REFERENCES `Usuarios`(`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE, -- O RESTRICT si no se debe borrar el usuario si participa
    FOREIGN KEY (`id_taller_impartido`) REFERENCES `Talleres`(`id_taller`) ON DELETE RESTRICT ON UPDATE CASCADE,
    UNIQUE KEY `uq_evento_tallerista_taller` (`id_evento`, `id_tallerista`, `id_taller_impartido`),
    INDEX `idx_ept_evento` (`id_evento`),
    INDEX `idx_ept_tallerista` (`id_tallerista`),
    INDEX `idx_ept_taller_impartido` (`id_taller_impartido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --- Tabla Evidencias ---
CREATE TABLE IF NOT EXISTS `Evidencias` (
    `id_evidencia` INT AUTO_INCREMENT PRIMARY KEY,
    `tipo_evidencia` ENUM('FOTO','VIDEO','DOCUMENTO_GENERAL','LISTA_ASISTENCIA','RECONOCIMIENTO_PDF','OTRO') NOT NULL,
    `ruta_archivo` VARCHAR(255) NOT NULL COMMENT 'Ruta al archivo de evidencia',
    `descripcion` TEXT NULL COMMENT 'Descripción o pie de foto/video',
    `fecha_subida` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `id_evento` INT NOT NULL,
    `id_usuario_subio` INT NOT NULL,
    `id_taller_asociado` INT NULL COMMENT 'FK a Talleres, si la evidencia es de un taller específico dentro del evento',
    FOREIGN KEY (`id_evento`) REFERENCES `Eventos`(`id_evento`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`id_usuario_subio`) REFERENCES `Usuarios`(`id_usuario`) ON DELETE RESTRICT ON UPDATE CASCADE,
    FOREIGN KEY (`id_taller_asociado`) REFERENCES `Talleres`(`id_taller`) ON DELETE SET NULL ON UPDATE CASCADE,
    INDEX `idx_evidencia_tipo` (`tipo_evidencia`),
    INDEX `idx_evidencia_evento` (`id_evento`),
    INDEX `idx_evidencia_usuario_subio` (`id_usuario_subio`),
    INDEX `idx_evidencia_taller_asociado` (`id_taller_asociado`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --- Tabla Bitacoras_Eventos ---
CREATE TABLE IF NOT EXISTS `Bitacoras_Eventos` (
    `id_bitacora` INT AUTO_INCREMENT PRIMARY KEY,
    `observacion` TEXT NOT NULL COMMENT 'Detalles, incidentes, resumen del evento',
    `fecha_hora_entrada` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `id_evento` INT NOT NULL,
    `id_usuario_registra` INT NOT NULL COMMENT 'Docente o Admin que registra',
    FOREIGN KEY (`id_evento`) REFERENCES `Eventos`(`id_evento`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`id_usuario_registra`) REFERENCES `Usuarios`(`id_usuario`) ON DELETE RESTRICT ON UPDATE CASCADE,
    INDEX `idx_bitacora_evento` (`id_evento`),
    INDEX `idx_bitacora_usuario_registra` (`id_usuario_registra`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --- Tabla Notificaciones ---
CREATE TABLE IF NOT EXISTS `Notificaciones` (
    `id_notificacion` INT AUTO_INCREMENT PRIMARY KEY,
    `mensaje` TEXT NOT NULL,
    `tipo_notificacion` ENUM(
        'NUEVA_CONVOCATORIA',
        'RECORDATORIO_CONVOCATORIA',
        'PROPUESTA_TALLER_RECIBIDA',       -- Para Docentes
        'TALLER_ESTADO_ACTUALIZADO',    -- Para Tallerista proponente
        'COMENTARIO_NUEVO_TALLER',      -- Para Tallerista proponente
        'EVENTO_PROXIMO',
        'EVENTO_CANCELADO',
        'EVENTO_ACTUALIZADO',
        'ASIGNACION_EVENTO_TALLERISTA', -- Para Tallerista asignado
        'NUEVA_EVIDENCIA_EVENTO',       -- Opcional, para interesados en un evento
        'OTRO'
    ) NOT NULL,
    `leida` BOOLEAN DEFAULT FALSE,
    `fecha_creacion` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `id_usuario_destinatario` INT NOT NULL,
    `id_evento_relacionado` INT NULL,
    `id_taller_relacionado` INT NULL,
    `id_convocatoria_relacionada` INT NULL,
    FOREIGN KEY (`id_usuario_destinatario`) REFERENCES `Usuarios`(`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`id_evento_relacionado`) REFERENCES `Eventos`(`id_evento`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`id_taller_relacionado`) REFERENCES `Talleres`(`id_taller`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`id_convocatoria_relacionada`) REFERENCES `Convocatorias`(`id_convocatoria`) ON DELETE CASCADE ON UPDATE CASCADE,
    INDEX `idx_notificacion_destinatario_leida` (`id_usuario_destinatario`, `leida`),
    INDEX `idx_notificacion_evento` (`id_evento_relacionado`),
    INDEX `idx_notificacion_taller` (`id_taller_relacionado`),
    INDEX `idx_notificacion_convocatoria` (`id_convocatoria_relacionada`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --- Comentarios Adicionales ---
-- Considerar añadir una tabla para 'Materiales_Requeridos_Por_Taller' si la lista de materiales es muy compleja y necesita estructura,
-- en lugar de solo un campo TEXT en la tabla 'Talleres'. Por ahora, 'requisitos_materiales' en 'Talleres' es un TEXT.

-- La gestión de almacenamiento de archivos (manuales, evidencias, adjuntos) se realizará a nivel de aplicación,
-- esta base de datos solo almacena las rutas a dichos archivos.

-- Asegurar que la aplicación maneje la lógica de roles y permisos adecuadamente.

-- Se han usado ON DELETE RESTRICT en algunas FK para prevenir borrados accidentales de usuarios que tienen registros importantes asociados.
-- En otros casos (como Evento_Participantes_Talleres o Notificaciones), ON DELETE CASCADE puede ser apropiado si al borrar el principal se desea limpiar las asociaciones.
-- Esto debe ser revisado según las reglas de negocio finales. Por defecto, he sido conservador con RESTRICT o SET NULL donde aplica.