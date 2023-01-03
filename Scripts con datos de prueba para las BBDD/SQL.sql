CREATE DATABASE  IF NOT EXISTS `zeppelinummartinezmeca` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `zeppelinummartinezmeca`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: zeppelinummartinezmeca
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `categoria_restaurante`
--

DROP TABLE IF EXISTS `categoria_restaurante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria_restaurante` (
  `id` int NOT NULL AUTO_INCREMENT,
  `categoria` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria_restaurante`
--

LOCK TABLES `categoria_restaurante` WRITE;
/*!40000 ALTER TABLE `categoria_restaurante` DISABLE KEYS */;
INSERT INTO `categoria_restaurante` VALUES (1,'Asiático'),(2,'Italiano'),(3,'Fast food');
/*!40000 ALTER TABLE `categoria_restaurante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `categoria_restaurante_restaurante`
--

DROP TABLE IF EXISTS `categoria_restaurante_restaurante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categoria_restaurante_restaurante` (
  `categoria_restaurante` int NOT NULL,
  `restaurante` int NOT NULL,
  PRIMARY KEY (`categoria_restaurante`,`restaurante`),
  KEY `FK_categoria_restaurante_restaurante_restaurante` (`restaurante`),
  CONSTRAINT `ctegoriarestauranterestaurantecategoriarestaurante` FOREIGN KEY (`categoria_restaurante`) REFERENCES `categoria_restaurante` (`id`),
  CONSTRAINT `FK_categoria_restaurante_restaurante_restaurante` FOREIGN KEY (`restaurante`) REFERENCES `restaurante` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categoria_restaurante_restaurante`
--

LOCK TABLES `categoria_restaurante_restaurante` WRITE;
/*!40000 ALTER TABLE `categoria_restaurante_restaurante` DISABLE KEYS */;
INSERT INTO `categoria_restaurante_restaurante` VALUES (1,1),(3,1),(1,4),(3,4),(2,5),(3,5);
/*!40000 ALTER TABLE `categoria_restaurante_restaurante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incidencia`
--

DROP TABLE IF EXISTS `incidencia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incidencia` (
  `id` int NOT NULL AUTO_INCREMENT,
  `comentarioCierre` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fechaCierre` datetime DEFAULT NULL,
  `fechaCreacion` datetime DEFAULT NULL,
  `pedido` int DEFAULT NULL,
  `restaurante` int DEFAULT NULL,
  `usuario` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_incidencia_restaurante` (`restaurante`),
  KEY `FK_incidencia_usuario` (`usuario`),
  CONSTRAINT `FK_incidencia_restaurante` FOREIGN KEY (`restaurante`) REFERENCES `restaurante` (`id`),
  CONSTRAINT `FK_incidencia_usuario` FOREIGN KEY (`usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incidencia`
--

LOCK TABLES `incidencia` WRITE;
/*!40000 ALTER TABLE `incidencia` DISABLE KEYS */;
INSERT INTO `incidencia` VALUES (13,'Estamos esperando al rider, su pedido será entregado en la próxima media hora.','Eres muy lento, tengo prisa','2023-01-03 00:00:00','2023-01-03 00:00:00',0,4,2);
/*!40000 ALTER TABLE `incidencia` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `plato`
--

DROP TABLE IF EXISTS `plato`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `plato` (
  `id` int NOT NULL AUTO_INCREMENT,
  `descripcion` longtext,
  `restaurante` int DEFAULT NULL,
  `disponibilidad` tinyint(1) DEFAULT '0',
  `precio` double DEFAULT NULL,
  `titulo` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_plato_restaurante` (`restaurante`),
  CONSTRAINT `FK_plato_restaurante` FOREIGN KEY (`restaurante`) REFERENCES `restaurante` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `plato`
--

LOCK TABLES `plato` WRITE;
/*!40000 ALTER TABLE `plato` DISABLE KEYS */;
INSERT INTO `plato` VALUES (1,'plato de bonito, patatas y cebolla con verduras',1,1,20,'Marmitako de bonito'),(8,'Delicioso montadito de atún',5,1,2.5,'Montadito'),(9,'Deliciosa ensaladilla murciana',5,1,5,'Ensaladilla'),(10,'Delicioso pincho de tortilla',4,1,2.5,'Pincho de tortilla'),(11,'',4,1,5,'Salmón');
/*!40000 ALTER TABLE `plato` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `restaurante`
--

DROP TABLE IF EXISTS `restaurante`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `restaurante` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fecha_alta` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `num_penalizaciones` int DEFAULT NULL,
  `num_valoraciones` int DEFAULT NULL,
  `valoracion_global` double DEFAULT NULL,
  `responsable` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_restaurante_responsable` (`responsable`),
  CONSTRAINT `FK_restaurante_responsable` FOREIGN KEY (`responsable`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `restaurante`
--

LOCK TABLES `restaurante` WRITE;
/*!40000 ALTER TABLE `restaurante` DISABLE KEYS */;
INSERT INTO `restaurante` VALUES (1,'2022-10-20','Puerta de Murcia',0,2,7.5,1),(2,'2022-10-20','Pistatxo',0,2,10,1),(3,'2022-10-20','El Barrilero de Jose',0,4,6.5,1),(4,'2023-01-03','Bar de Jose Antonio',0,0,0,5),(5,'2023-01-03','Restaurante La Catedral',0,0,0,5);
/*!40000 ALTER TABLE `restaurante` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id` int NOT NULL AUTO_INCREMENT,
  `apellidos` varchar(255) DEFAULT NULL,
  `clave` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `fecha_nacimiento` date DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `tipo` varchar(255) DEFAULT NULL,
  `validado` tinyint(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'Palotes','12345','periquita@palotes.es','1990-01-08','Periquita','RESTAURANTE',1),(2,'Legaz','12345','mclg@um.es','1990-01-08','Mari','CLIENTE',1),(3,'admin','admin','admin@um.es',NULL,'admin','ADMIN',1),(4,'Garcia','pepito','pepito@um.es','2000-01-01','Pepito','RIDER',1),(5,'García Ruipérez','jose','joseantonio@um.es','1996-04-17','José Antonio','RESTAURANTE',1);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-01-03 19:35:07
