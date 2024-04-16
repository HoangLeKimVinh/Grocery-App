-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 15, 2023 at 07:30 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `grocerydata`
--

-- --------------------------------------------------------

--
-- Table structure for table `chitietdonhang`
--

CREATE TABLE `chitietdonhang` (
  `id` int(11) NOT NULL,
  `iddonhang` int(11) NOT NULL,
  `idsp` int(11) NOT NULL,
  `soluong` int(11) NOT NULL,
  `gia` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chitietdonhang`
--

INSERT INTO `chitietdonhang` (`id`, `iddonhang`, `idsp`, `soluong`, `gia`) VALUES
(14, 86, 8, 2, '44000'),
(15, 87, 7, 2, '42000'),
(16, 87, 6, 2, '40000'),
(17, 88, 8, 1, '22000'),
(18, 89, 8, 1, '22000'),
(19, 89, 9, 3, '23000'),
(20, 90, 8, 1, '22000'),
(21, 91, 7, 1, '21000'),
(22, 92, 9, 1, '23000'),
(23, 92, 7, 1, '21000');

-- --------------------------------------------------------

--
-- Table structure for table `donhang`
--

CREATE TABLE `donhang` (
  `id` int(11) NOT NULL,
  `iduser` int(11) NOT NULL,
  `diachi` varchar(255) NOT NULL,
  `soluong` int(11) NOT NULL,
  `tongtien` varchar(255) NOT NULL,
  `sodienthoai` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `donhang`
--

INSERT INTO `donhang` (`id`, `iduser`, `diachi`, `soluong`, `tongtien`, `sodienthoai`, `email`) VALUES
(86, 3, 'kiet 626 truong chinh', 2, '88000', '0855234245', 'kimbii.2003@gmail.com'),
(87, 3, '87 Dien Bien Phu', 4, '164000', '0855234245', 'kimbii.2003@gmail.com'),
(88, 3, '123 truong chinh', 1, '22000', '0855234245', 'kimbii.2003@gmail.com'),
(89, 3, '123 Nguyen Luong Bang', 4, '91000', '0855234245', 'kimbii.2003@gmail.com'),
(90, 3, '123 ngo thi nham', 1, '22000', '0855234245', 'kimbii.2003@gmail.com'),
(91, 6, '626 truong chinh', 1, '21000', '0855234248', 'vinhhlk.21ad@vku.udn.cn'),
(92, 7, '326 Nguyen Luong Bang', 2, '44000', '0855234248', 'kimbii.5820033@gmail.com');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_category`
--

CREATE TABLE `tbl_category` (
  `id` int(11) NOT NULL,
  `category_name` varchar(255) NOT NULL,
  `category_image` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_category`
--

INSERT INTO `tbl_category` (`id`, `category_name`, `category_image`) VALUES
(2, 'Rau tươi', 'https://www.bigbasketco.com/wp-content/uploads/good-l-corp-what-americas-best-grocery-stores-have-in-common-1024x768.jpg'),
(3, 'Trái cây', 'https://foodtank.com/wp-content/uploads/2021/09/gemma-stpjHJGqZyw-unsplash.jpg'),
(6, 'Nước uống', 'https://drinkies.vn/wp-content/uploads/2021/09/nuoc-ngot-co-ga.jpg'),
(7, 'Sữa', 'https://static.kinhtedothi.vn/w960/images/upload/2022/08/03/sua1.jpg'),
(8, 'Hương liệu', 'https://cdn.tgdd.vn/2020/12/content/11-800x500-5.jpg'),
(9, 'Thịt', 'https://upload.wikimedia.org/wikipedia/commons/3/30/Grocery_store_in_Rome.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_product`
--

CREATE TABLE `tbl_product` (
  `id` int(11) NOT NULL,
  `product_name` varchar(255) NOT NULL,
  `product_img` varchar(255) NOT NULL,
  `product_price` varchar(100) NOT NULL,
  `product_des` varchar(255) NOT NULL,
  `category_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_product`
--

INSERT INTO `tbl_product` (`id`, `product_name`, `product_img`, `product_price`, `product_des`, `category_id`) VALUES
(1, 'Xoài', 'https://product.hstatic.net/200000325223/product/xoai-01_e1334c093fb64fa7afc4b505b7d0586a.png', '20000', 'xoài ngon, ngọt', 3),
(2, 'Dưa hấu', 'https://t3.gstatic.com/licensed-image?q=tbn:ANd9GcRKo5mWOlF8xPviHgJFlo0cexqTPmvZhqxOoGQ_OlPZ3hlNU-0Te4WlGR0peNOXWzbe', '30000', 'Dưa hấu ngon', 3),
(3, 'Thanh long', 'https://cdn.tgdd.vn/Files/2017/05/23/985304/nhung-cong-dung-tuyet-voi-cua-thanh-long-ban-nen-biet-202109171144120943.jpg', '10000', 'Thanh long miền tây', 3),
(4, 'Rau xà lách tươi', 'https://vinmec-prod.s3.amazonaws.com/images/20210106_041321_793265_hat-giong-rau-xa-la.max-1800x1800.jpg', '5000', 'Rau xà lách tươi', 2),
(5, 'Chuối', 'https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2021/4/11/897757/Chuoi-Giup-Tang-Can-.jpg', '20000', 'Chuối ngon', 3),
(6, 'Táo Mĩ', 'https://media.vov.vn/sites/default/files/styles/large/public/2022-01/2_199.jpg', '20000', 'Táo ngon', 3),
(7, 'Cam', 'https://cardocorz.vn/image/data/10-loai-trai-cay-tot-cho-tim-mach-2.jpg', '21000', 'Cam ngon', 3),
(8, 'Bơ', 'https://vnn-imgs-f.vgcloud.vn/2018/06/09/16/cach-don-gian-phan-biet-trai-cay-chua-hoa-chat-1.jpg', '22000', 'bơ ngon', 3),
(9, 'Dâu tây', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5cJhs3PKI68W1pUodjbznrqxeSKvj3Qq6LQ&usqp=CAU', '23000', 'dâu ngon', 3);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE `tbl_user` (
  `id` int(11) NOT NULL,
  `UserName` varchar(255) NOT NULL,
  `Password` varchar(255) NOT NULL,
  `Email` varchar(255) NOT NULL,
  `Mobile` varchar(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`id`, `UserName`, `Password`, `Email`, `Mobile`) VALUES
(3, 'Kim Vinh', 'abc123', 'kimbii.2003@gmail.com', '0855234245'),
(4, 'Kimbii', '1234', 'hoangle582003@gmail.com', '0934522112'),
(5, 'Hoang Le  Kim Vinh', '123', 'kimbii.582003@gmail.com', '5258581155'),
(6, 'Hoang Le Kim Vinh', '123', 'vinhhlk.21ad@vku.udn.cn', '0855234248'),
(7, 'Kim Vinh', '123', 'kimbii.5820033@gmail.com', '0855234248');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `donhang`
--
ALTER TABLE `donhang`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_category`
--
ALTER TABLE `tbl_category`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_product`
--
ALTER TABLE `tbl_product`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `chitietdonhang`
--
ALTER TABLE `chitietdonhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT for table `donhang`
--
ALTER TABLE `donhang`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=93;

--
-- AUTO_INCREMENT for table `tbl_category`
--
ALTER TABLE `tbl_category`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `tbl_product`
--
ALTER TABLE `tbl_product`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
