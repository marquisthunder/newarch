CREATE TABLE `ECommerce` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
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
  `ecommerceid` bigint(20) NOT NULL,
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
  `schemeName` varchar(255) NOT NULL,
  `ecommerceid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `schemeName` (`schemeName`),
  KEY `FK934621C56A1CB3F6` (`ecommerceid`),
  CONSTRAINT `FK934621C56A1CB3F6` FOREIGN KEY (`ecommerceid`) REFERENCES `ECommerce` (`id`)
);
insert into Scheme (algorithmNames, ecommerceid, schemeName) values 
('AlgorithmDefault,AprioriRunner', 1, 'scheme1');
insert into Scheme (algorithmNames, ecommerceid, schemeName) values 
('AlgorithmDefault,AprioriRunner', 1, 'scheme2');