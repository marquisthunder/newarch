CREATE TABLE `Kebsite` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `kebsiteName` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
);
insert into Kebsite (kebsiteName) values ('jingdong');

CREATE TABLE `ExclusiveKey` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activation` bit(1) NOT NULL,
  `keyString` varchar(255) NOT NULL,
  `kebsiteid` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKDA8BCF0149AFD174` (`kebsiteid`)
);

insert into ExclusiveKey (activation, kebsiteid, keyString) values (
true, 1, 'an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ');