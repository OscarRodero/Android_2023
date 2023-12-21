-- phpMyAdmin SQL Dump
-- version 5.1.1deb5ubuntu1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 09-10-2023 a las 13:08:16
-- Versión del servidor: 8.0.34-0ubuntu0.22.04.1
-- Versión de PHP: 8.1.2-1ubuntu2.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `AS_API_bd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `personas`
--

CREATE TABLE `personas` (
  `dni` varchar(10) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `nombre` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `clave` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `tfno` varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `personas`
--

INSERT INTO `personas` (`dni`, `nombre`, `clave`, `tfno`) VALUES
('10J', 'Manuel', '1234', '5558592'),
('11K', 'Eduardo', '1234', '2723832'),
('12L', 'Marco', '1234', '555425423'),
('13M', 'Alvaro O', '1234', '555 29223'),
('14N', 'Jose G', '1234', '555 4589645'),
('16A', 'Lorenzo', '1234', '555 28383'),
('17B', 'Maria', '1234', '555 29392'),
('19T', 'Airon', '1234', '555 23939'),
('1A', 'Oscar', '123', '5554547'),
('200RE', 'Antornio', '1234', '555425423'),
('20G', 'Adrian', '123', '555 291039'),
('2B', 'Alfonso', '123', '5559752'),
('3C', 'Alvaro R', '234', '3282392'),
('4D', 'Ramon', '1324', '2382932'),
('5E', 'Sergio', '1234', '2389392'),
('6F', 'Raul', '12', '555564894'),
('7G', 'David', '1234', '55512447');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `roles`
--

CREATE TABLE `roles` (
  `id` int NOT NULL,
  `descripcion` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `roles`
--

INSERT INTO `roles` (`id`, `descripcion`) VALUES
(1, 'Administrador'),
(2, 'Usuario');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rolesasignados`
--

CREATE TABLE `rolesasignados` (
  `idra` int NOT NULL,
  `dniRol` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `idRol` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `rolesasignados`
--

INSERT INTO `rolesasignados` (`idra`, `dniRol`, `idRol`) VALUES
(1, '10J', 1),
(2, '10J', 2),
(3, '11K', 2),
(4, '2B', 1),
(5, '1A', 1),
(6, '1A', 2),
(7, '3C', 2),
(8, '4D', 2),
(9, '5E', 2),
(10, '6F', 2),
(11, '6F', 1),
(12, '7G', 2),
(13, '8H', 2),
(14, '9I', 2),
(15, '12L', 2),
(16, '13M', 2),
(17, '14N', 2),
(18, '16A', 2),
(19, '17B', 2),
(20, '19T', 2),
(21, '20G', 2);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `personas`
--
ALTER TABLE `personas`
  ADD PRIMARY KEY (`dni`);

--
-- Indices de la tabla `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `rolesasignados`
--
ALTER TABLE `rolesasignados`
  ADD PRIMARY KEY (`idra`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `rolesasignados`
--
ALTER TABLE `rolesasignados`
  MODIFY `idra` int NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
