CREATE TABLE `ECommerce` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` datetime NOT NULL,
  `ecommerceName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ecommerceName` (`ecommerceName`)
);
insert into ECommerce (createDate,ecommerceName) values (now(),'jingdong');

CREATE TABLE `ExclusiveKey` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createDate` datetime NOT NULL,
  `keyString` varchar(255) NOT NULL,
  `state` int(2) NOT NULL DEFAULT '1',
  `ecommerceid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `keyString` (`keyString`),
  KEY `FKDA8BCF016A1CB3F6` (`ecommerceid`),
  CONSTRAINT `FKDA8BCF016A1CB3F6` FOREIGN KEY (`ecommerceid`) REFERENCES `ECommerce` (`id`)
);
insert into ExclusiveKey(createDate, ecommerceid, keyString, state)values
(now(), 1, 'an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ',2);

CREATE TABLE `Scheme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `algorithmNames` varchar(255) NOT NULL,
  `createDate` datetime NOT NULL,
  `schemeName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `schemeName` (`schemeName`)
);
insert into Scheme (createDate,algorithmNames, schemeName) values 
(now(),'AlgorithmDefault,AprioriRunner','scheme1');
insert into Scheme (createDate,algorithmNames,schemeName) values 
(now(),'AlgorithmDefault,AprioriRunnerMultiThread','scheme2');

CREATE TABLE `ECommerce_Scheme` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `createDate` datetime NOT NULL,
  `ecommerceid` int(11) NOT NULL,
  `schemeid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ecommerceid` (`ecommerceid`,`schemeid`),
  KEY `FK2247DCA46A1CB3F6` (`ecommerceid`),
  KEY `FK2247DCA4A4515ECA` (`schemeid`),
  CONSTRAINT `FK2247DCA4A4515ECA` FOREIGN KEY (`schemeid`) REFERENCES `Scheme` (`id`),
  CONSTRAINT `FK2247DCA46A1CB3F6` FOREIGN KEY (`ecommerceid`) REFERENCES `ECommerce` (`id`)
);

insert into ECommerce_Scheme (createDate,ecommerceid,schemeid) values (now(),1,1);
insert into ECommerce_Scheme (createDate,ecommerceid,schemeid) values (now(),1,2);

CREATE TABLE `OrderFrequent` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `combination` varchar(255) NOT NULL,
  `frequent` int(11) NOT NULL,
  `itemNum` int(11) NOT NULL,
  `ofType` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `combination` (`combination`)
);

insert into OrderFrequent(combination,frequent,itemNum,ofType) values('2,3,4,5',2,4,'all');
insert into OrderFrequent(combination,frequent,itemNum,ofType) values('4,5,6',2,3,'all');

CREATE TABLE `Rule` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `confidence` double NOT NULL,
  `flag` varchar(255) NOT NULL,
  `products` varchar(255) DEFAULT NULL,
  `recommendation` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `products` (`products`,`recommendation`)
);

INSERT INTO `Rule` (`id`, `products`, `recommendation`, `confidence`, `flag`) VALUES
(1, '2,4', '3', 0.857142857142857, 'general'),
(2, '3,4', '6', 0.363636363636364, 'general'),
(3, '2', '1,6', 0.6, 'general'),
(4, '1,4', '3', 0.666666666666667, 'general'),
(5, '6', '3', 0.777777777777778, 'general'),
(6, '3', '2', 0.6875, 'general'),
(7, '5', '4', 1, 'general'),
(8, '4', '3,5', 0.714285714285714, 'general'),
(9, '1,6', '3', 0.714285714285714, 'general'),
(10, '4', '3', 0.846153846153846, 'general'),
(11, '2,6', '3', 0.714285714285714, 'general'),
(12, '2', '3,4', 0.4, 'general'),
(13, '2', '1,3,6', 0.4, 'general'),
(14, '2,3', '4', 0.545454545454545, 'general'),
(15, '2', '6', 0.466666666666667, 'general'),
(16, '2,3,6', '1', 0.8, 'general'),
(17, '3,4', '2', 0.545454545454545, 'general'),
(18, '3,6', '4', 0.666666666666667, 'general'),
(19, '3', '1', 0.5, 'general'),
(20, '5', '3,4', 1, 'general'),
(21, '3', '4,6', 0.25, 'general'),
(22, '4', '3,6', 0.571428571428571, 'general'),
(23, '1', '3,6', 0.384615384615385, 'general'),
(24, '4', '1,3', 0.307692307692308, 'general'),
(25, '1,6', '2,3', 0.571428571428571, 'general'),
(26, '2,3', '1,6', 0.571428571428571, 'general'),
(27, '4', '6', 0.307692307692308, 'general'),
(28, '4', '5', 0.538461538461538, 'general'),
(29, '1', '2', 0.769230769230769, 'general'),
(30, '1,3,6', '2', 0.8, 'general'),
(31, '1', '4', 0.461538461538462, 'general'),
(32, '1,3', '2', 0.75, 'general'),
(33, '1,3', '2,6', 0.8, 'general'),
(34, '1,2', '3,6', 0.4, 'general'),
(35, '1', '2,3,6', 0.307692307692308, 'general'),
(36, '3', '1,6', 0.454545454545454, 'general'),
(37, '6', '2', 0.777777777777778, 'general'),
(38, '3,4', '5', 0.454545454545454, 'general'),
(39, '4,5', '3', 1, 'general'),
(40, '3', '1,2', 0.375, 'general'),
(41, '2', '1', 0.666666666666667, 'general'),
(42, '1', '2,3', 0.461538461538462, 'general'),
(43, '3,6', '2', 0.714285714285714, 'general'),
(44, '1,2,3', '6', 0.666666666666667, 'general'),
(45, '6', '4', 0.571428571428571, 'general'),
(46, '4,6', '3', 1, 'general'),
(47, '3,6', '1', 0.714285714285714, 'general'),
(48, '1,2', '6', 0.6, 'general'),
(49, '1', '2,6', 0.461538461538462, 'general'),
(50, '4', '2,3', 0.461538461538462, 'general'),
(51, '6', '1,3', 0.555555555555556, 'general'),
(52, '4', '1', 0.461538461538462, 'general'),
(53, '3', '2,4', 0.375, 'general'),
(54, '2,6', '1', 0.857142857142857, 'general'),
(55, '5', '3', 1, 'general'),
(56, '2,6', '1,3', 0.571428571428571, 'general'),
(57, '1,2,6', '3', 0.666666666666667, 'general'),
(58, '3', '1,4', 0.25, 'general'),
(59, '3', '6', 0.4375, 'general'),
(60, '6', '2,3', 0.555555555555556, 'general'),
(61, '1,3', '4', 0.5, 'general'),
(62, '2', '3', 0.733333333333333, 'general'),
(63, '2', '1,3', 0.428571428571429, 'general'),
(64, '2,3', '6', 0.454545454545454, 'general'),
(65, '4', '2', 0.538461538461538, 'general'),
(66, '1,2', '3', 0.6, 'general'),
(67, '6', '1', 0.777777777777778, 'general'),
(68, '2,3', '1', 0.545454545454545, 'general'),
(69, '3', '5', 0.3125, 'general'),
(70, '3,5', '4', 1, 'general'),
(71, '1,6', '2', 0.857142857142857, 'general'),
(72, '3,6', '1,2', 0.571428571428571, 'general'),
(73, '1', '3,4', 0.307692307692308, 'general'),
(74, '3', '4', 0.6875, 'general'),
(75, '1', '3', 0.615384615384615, 'general'),
(76, '6', '1,2,3', 0.444444444444444, 'general'),
(77, '3', '4,5', 0.3125, 'general'),
(78, '3', '2,6', 0.454545454545454, 'general'),
(79, '6', '3,4', 0.571428571428571, 'general'),
(80, '1,3', '6', 0.625, 'general'),
(81, '2', '3,6', 0.333333333333333, 'general'),
(82, '3', '1,2,6', 0.363636363636364, 'general'),
(83, '2', '4', 0.466666666666667, 'general'),
(84, '3,4', '1', 0.363636363636364, 'general'),
(85, '6', '1,2', 0.666666666666667, 'general'),
(86, '1', '6', 0.538461538461538, 'general');