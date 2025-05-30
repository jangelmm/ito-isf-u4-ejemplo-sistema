-- MySQL dump 10.13  Distrib 8.0.41, for Win64 (x86_64)
--
-- Host: localhost    Database: tutorias
-- ------------------------------------------------------
-- Server version	9.2.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cita`
--

DROP TABLE IF EXISTS `cita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cita` (
  `id_cita` int NOT NULL AUTO_INCREMENT,
  `fecha` date DEFAULT NULL,
  `hora` int DEFAULT NULL,
  `asunto` varchar(50) DEFAULT NULL,
  `estado` varchar(20) NOT NULL DEFAULT 'PENDIENTE',
  `id_tutor` int DEFAULT NULL,
  PRIMARY KEY (`id_cita`),
  KEY `fk_cita_tutor` (`id_tutor`),
  CONSTRAINT `fk_cita_tutor` FOREIGN KEY (`id_tutor`) REFERENCES `tutor` (`id_persona`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cita`
--

LOCK TABLES `cita` WRITE;
/*!40000 ALTER TABLE `cita` DISABLE KEYS */;
INSERT INTO `cita` VALUES (1,'2025-05-05',10,'Repasar Álgebra','REALIZADA',1),(2,'2025-05-06',14,'Práctica de Cálculo','REALIZADA',2),(3,'2025-05-07',8,'Tarea de Física','REALIZADA',3),(4,'2025-05-08',12,'Bases de datos','CANCELADA',4),(5,'2025-05-09',16,'Reacciones químicas','REALIZADA',5),(6,'2025-05-10',14,'Proyecto final','PENDIENTE',1),(7,'2025-05-11',10,'Examen diagnóstico','REALIZADA',2),(8,'2025-05-12',11,'Modelado UML','PENDIENTE',3),(9,'2025-05-13',15,'Diseño de interfaces','PENDIENTE',6),(10,'2025-05-14',13,'Análisis de algoritmos','PENDIENTE',7),(11,'2025-05-15',9,'Práctica de red','PENDIENTE',8),(12,'2025-05-16',17,'Desarrollo web','REALIZADA',9),(13,'2025-05-17',10,'Estructuras de datos','PENDIENTE',10),(15,'2025-05-19',11,'Repasar Álgebra','REALIZADA',1),(16,'2025-05-19',11,'Repasar Programación','REALIZADA',2),(17,'2025-05-19',12,'Desarrollo Web','PENDIENTE',3),(18,'2025-05-27',12,'Prueba del proyecto','PENDIENTE',2);
/*!40000 ALTER TABLE `cita` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutor`
--

DROP TABLE IF EXISTS `tutor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutor` (
  `id_persona` int NOT NULL AUTO_INCREMENT,
  `num_tarjeta` int DEFAULT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `carrera` varchar(50) DEFAULT NULL,
  `dias` varchar(10) DEFAULT NULL,
  `horas` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_persona`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutor`
--

LOCK TABLES `tutor` WRITE;
/*!40000 ALTER TABLE `tutor` DISABLE KEYS */;
INSERT INTO `tutor` VALUES (1,1,'Luis Pérez','Ingeniería Electrónica','L-M-V','10-12'),(2,2,'Ana Gómez','Ingeniería Química','M-J-V','14-16'),(3,3,'Carlos Ramírez','Ingeniería Mecánica','L-M-X','08-10'),(4,4,'Laura Martinez','Ingeniería en Sis. Computacionales','L-J-V','9-14'),(5,5,'Miguel Torres','Ingeniería Química','M-X-V','16-18'),(6,6,'Verónica Díaz','Ingeniería Mecánica','L-M-X-V','9-11'),(7,7,'Jorge Hernández','Ingeniería Electrica','M-J-S','15-17'),(8,8,'Patricia López','Ingeniería Electrica','L-X-V','11-13'),(9,9,'Diego Sánchez','Ingeniería en Gestión Empresarial','M-J-V','13-15'),(10,10,'Mariana Ortiz','Licenciatura en Administración','L-M-V','10-12'),(11,11,'Angel Martinez','Contador Público','L-M-V','7-12'),(14,12,'Jesus Angel Mendoza','Ingeniería en Sis. Computacionales','L-M-X-J-V','7-11');
/*!40000 ALTER TABLE `tutor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutorado`
--

DROP TABLE IF EXISTS `tutorado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutorado` (
  `id_tutorado` int NOT NULL AUTO_INCREMENT,
  `nc` varchar(8) DEFAULT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `genero` char(1) DEFAULT NULL,
  `dias` varchar(10) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `id_tutor` int DEFAULT NULL,
  PRIMARY KEY (`id_tutorado`),
  KEY `fk_tutorado_tutor` (`id_tutor`),
  CONSTRAINT `fk_tutorado_tutor` FOREIGN KEY (`id_tutor`) REFERENCES `tutor` (`id_persona`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutorado`
--

LOCK TABLES `tutorado` WRITE;
/*!40000 ALTER TABLE `tutorado` DISABLE KEYS */;
INSERT INTO `tutorado` VALUES (1,'20191234','María Ruiz','F','L-M-V','2000-05-10',1),(2,'20192345','José López','M','M-J','2001-11-22',2),(3,'20193456','Sofía Medina','F','L-X','1999-07-05',3),(4,'20194567','Andrés Salas','M','M-J','2002-02-15',4),(5,'20195678','Daniela Pérez','F','L-X-V','2003-08-30',5),(6,'20196789','Brenda Flores','F','M-J','2001-01-20',2),(7,'20197890','Eduardo Cruz','M','L-M-V','2000-12-01',6),(8,'20198901','Ximena Rojas','F','L-J-V','2002-07-14',7),(9,'20200012','Fernando Gil','M','M-X','1998-03-22',8),(10,'20201123','Carla Vega','F','L-M-V','2001-09-09',4),(12,'20202234','Ana Gómez','F','L-J','2002-04-18',1),(13,'20203345','Carlos Méndez','M','M-X-V','2000-09-12',5),(14,'20204456','Lucía Torres','F','L-V','2001-07-25',1),(15,'20205567','Ricardo Castro','M','M-J','2003-01-05',3),(16,'20206678','Gabriela Núñez','F','L-M-X','1999-11-30',7),(17,'20207789','Oscar Herrera','M','X-J','2002-08-14',14),(18,'20208890','Patricia Ríos','F','L-M-J-V','2000-05-22',2),(19,'20209901','Javier Medina','M','M','2001-12-08',6),(20,'20210012','Adriana Vargas','F','L-J','2003-03-17',14),(21,'20211123','Roberto Campos','M','M-V','1998-10-11',4),(23,'22161152','Jesus Angel Martinez','M','L-M-X','2004-02-07',9),(24,'21000000','Juan Perez','M','L-M-X-J','2004-05-19',7),(25,'34567213','Luisa Perez','F','L-M-X-J-V','2003-02-06',7),(26,'22152611','Carlos Vallarta','M','M-X-J-V-S','2000-02-22',NULL),(27,'21000000','Juan Perez','M','L-M-X-J','2004-05-19',9),(28,'22185423','Josue Ramiraz','M','L-X-S','2005-09-28',NULL),(29,'22187632','Carlos Sanchez','M','L-M-V-S','2002-09-16',NULL),(30,'22198456','Virginia Lopez','M','L-J-S','1999-10-20',NULL);
/*!40000 ALTER TABLE `tutorado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tutoria`
--

DROP TABLE IF EXISTS `tutoria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tutoria` (
  `id_tutoria` int NOT NULL AUTO_INCREMENT,
  `acciones` text NOT NULL,
  `id_cita` int NOT NULL,
  `id_tutorado` int NOT NULL,
  PRIMARY KEY (`id_tutoria`),
  KEY `idx_tutoria_cita` (`id_cita`),
  KEY `idx_tutoria_tutorado` (`id_tutorado`),
  CONSTRAINT `fk_tutoria_cita` FOREIGN KEY (`id_cita`) REFERENCES `cita` (`id_cita`) ON DELETE CASCADE,
  CONSTRAINT `fk_tutoria_tutorado` FOREIGN KEY (`id_tutorado`) REFERENCES `tutorado` (`id_tutorado`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tutoria`
--

LOCK TABLES `tutoria` WRITE;
/*!40000 ALTER TABLE `tutoria` DISABLE KEYS */;
INSERT INTO `tutoria` VALUES (1,'Enviar recursos de álgebra lineal',1,1),(2,'Compartir ejercicios de cálculo',2,2),(3,'Explicar ley de Newton',3,3),(4,'Revisar modelo relacional y consultas',4,4),(5,'Resolver problemas de química orgánica',5,5),(6,'Planeación del proyecto integrador',6,6),(7,'Aplicar test de conocimientos previos',7,7),(8,'Guía de diagramas de clases',8,8),(9,'Revisión de prototipo UI',9,9),(10,'Comparar listas enlazadas y árboles',10,10),(11,'Introducción a redes TCP/IP',11,1),(12,'Buenas prácticas de Java',12,2),(13,'Uso de Git y GitHub',13,3),(15,'Seguimiento',4,4),(16,'Aprobado',4,10),(17,'Asistencia registrada.',1,1),(18,'Seguimiento',1,12),(19,'Aprobado',1,14),(20,'Seguimiento',7,2),(21,'Recomendación',7,6),(22,'Seguimiento',7,18),(23,'Seguimiento',3,3),(24,'Aprobado',3,15),(25,'Trámite',12,23),(26,'Atención Médica / Psicológica',12,27),(27,'Asesoría',2,2),(28,'Trámite',2,6),(29,'Atención Médica / Psicológica',2,18),(30,'Asesoría',1,1),(31,'Trámite',1,12),(32,'Asesoría',1,14),(33,'Asistencia registrada.',16,2),(34,'Asistencia registrada.',16,6),(35,'Asistencia registrada.',16,18),(36,'Asistencia registrada.',16,2),(37,'Asistencia registrada.',16,6),(38,'Asistencia registrada.',16,18),(39,'Asistencia registrada.',15,1),(40,'Asistencia registrada.',15,12),(41,'Asistencia registrada.',15,14),(42,'Asistencia registrada.',16,2),(43,'Asistencia registrada.',16,6),(44,'Asistencia registrada.',16,18);
/*!40000 ALTER TABLE `tutoria` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-30 10:28:04
