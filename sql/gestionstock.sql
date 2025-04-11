-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 11, 2025 at 11:19 PM
-- Server version: 10.4.25-MariaDB
-- PHP Version: 7.4.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `gestionstock`
--

-- --------------------------------------------------------

--
-- Table structure for table `categorie`
--

CREATE TABLE `categorie` (
  `id` int(11) NOT NULL,
  `nomC` varchar(90) DEFAULT NULL,
  `code` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `categorie`
--

INSERT INTO `categorie` (`id`, `nomC`, `code`) VALUES
(65, 'Cat1', 'Code1'),
(66, 'Cat2', 'Code2');

-- --------------------------------------------------------

--
-- Table structure for table `entree_stock`
--

CREATE TABLE `entree_stock` (
  `id_entree` int(11) NOT NULL,
  `code_produit` varchar(20) NOT NULL,
  `quantite` int(11) NOT NULL,
  `date_reception` date NOT NULL,
  `numero_facture` varchar(50) DEFAULT NULL,
  `date_creation` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `produit`
--

CREATE TABLE `produit` (
  `id` int(11) NOT NULL,
  `code` varchar(10) DEFAULT NULL,
  `nomP` varchar(50) DEFAULT NULL,
  `prix` float DEFAULT NULL,
  `categorie` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `sortie_stock`
--

CREATE TABLE `sortie_stock` (
  `id_sortie` int(11) NOT NULL,
  `code_produit` varchar(20) NOT NULL,
  `quantite` int(11) NOT NULL,
  `date_sortie` date NOT NULL,
  `destination` varchar(100) DEFAULT NULL,
  `date_creation` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `nom` varchar(30) DEFAULT NULL,
  `prenom` varchar(30) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(40) DEFAULT NULL,
  `tel` char(10) DEFAULT NULL,
  `adress` varchar(60) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `nom`, `prenom`, `email`, `password`, `tel`, `adress`) VALUES
(4, 'test', 'test', 'test@gmail.com', 'test', '0940493', 'test'),
(9, NULL, NULL, NULL, NULL, NULL, NULL),
(18, NULL, NULL, NULL, NULL, NULL, NULL),
(51, 'imane', 'chatar', 'imane@gmail.com', 'imane', '0608495168', 'sidi slimane'),
(195, 'chatar', 'imane', 'imane02@gmail.com', 'imane', '0709293126', 'kenitra'),
(196, 'chatar', 'imane', 'imane03@gmail.com', 'imane', '0908020329', 'kenitra'),
(241, 'Dupont', 'Jean', 'test.user@example.com', 'password123', '0123456789', '123 Rue Test'),
(242, 'imane', 'imane', 'test1@gmail.com', 'tesy', '09080706', 'kenitra');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `categorie`
--
ALTER TABLE `categorie`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `entree_stock`
--
ALTER TABLE `entree_stock`
  ADD PRIMARY KEY (`id_entree`),
  ADD KEY `fk_code` (`code_produit`);

--
-- Indexes for table `produit`
--
ALTER TABLE `produit`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `code` (`code`),
  ADD KEY `fk_produit` (`categorie`);

--
-- Indexes for table `sortie_stock`
--
ALTER TABLE `sortie_stock`
  ADD PRIMARY KEY (`id_sortie`),
  ADD KEY `fkSortie_code` (`code_produit`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `categorie`
--
ALTER TABLE `categorie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=67;

--
-- AUTO_INCREMENT for table `entree_stock`
--
ALTER TABLE `entree_stock`
  MODIFY `id_entree` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `produit`
--
ALTER TABLE `produit`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=605;

--
-- AUTO_INCREMENT for table `sortie_stock`
--
ALTER TABLE `sortie_stock`
  MODIFY `id_sortie` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=243;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `entree_stock`
--
ALTER TABLE `entree_stock`
  ADD CONSTRAINT `fk_code` FOREIGN KEY (`code_produit`) REFERENCES `produit` (`code`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `produit`
--
ALTER TABLE `produit`
  ADD CONSTRAINT `fk_produit` FOREIGN KEY (`categorie`) REFERENCES `categorie` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `sortie_stock`
--
ALTER TABLE `sortie_stock`
  ADD CONSTRAINT `fkSortie_code` FOREIGN KEY (`code_produit`) REFERENCES `produit` (`code`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
