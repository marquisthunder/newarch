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