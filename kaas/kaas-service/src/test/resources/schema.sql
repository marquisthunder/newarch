CREATE TABLE `Website` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createData` datetime NOT NULL,
  `websiteName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `websiteName` (`websiteName`)
);
insert into Website (createData,websiteName) values (now(),'jingdong');

CREATE TABLE `ExclusiveKey` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `createData` datetime NOT NULL,
  `keyString` varchar(255) NOT NULL,
  `state` int(2) NOT NULL DEFAULT '1',
  `websiteid` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `keyString` (`keyString`),
  KEY `FKDA8BCF0149AFD174` (`websiteid`),
  CONSTRAINT `FKDA8BCF0149AFD174` FOREIGN KEY (`websiteid`) REFERENCES `Website` (`id`)
);
insert into ExclusiveKey(createData, websiteid, keyString, state)values
(now(), 1, 'an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ',2);