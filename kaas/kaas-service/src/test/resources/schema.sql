CREATE TABLE `Kebsite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createData` datetime NOT NULL,
  `kebsiteName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `kebsiteName` (`kebsiteName`)
);
insert into Kebsite (createData,kebsiteName) values (now(),'jingdong');

CREATE TABLE `ExclusiveKey` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createData` datetime NOT NULL,
  `keyString` varchar(255) NOT NULL,
  `state` int(2) NOT NULL DEFAULT '1',
  `kebsiteid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `keyString` (`keyString`),
  KEY `FKDA8BCF0149AFD174` (`kebsiteid`),
  CONSTRAINT `FKDA8BCF0149AFD174` FOREIGN KEY (`kebsiteid`) REFERENCES `Kebsite` (`id`)
);
insert into ExclusiveKey(createData, kebsiteid, keyString, state)values
(now(), 1, 'an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ',2);