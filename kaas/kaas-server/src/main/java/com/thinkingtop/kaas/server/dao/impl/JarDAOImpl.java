package com.thinkingtop.kaas.server.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.thinkingtop.kaas.server.dao.JarDAO;
import com.thinkingtop.kaas.server.model.KaasJarInfo;

public class JarDAOImpl implements JarDAO {
 
	private static final Logger logger = LoggerFactory.getLogger(JarDAOImpl.class.getName());
	
	/*JdbcTemplate*/
	private JdbcTemplate jdbcTemplate;

	/* set to inject the proxy class of jdbcTemplate*/
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void addJarInfo(KaasJarInfo info) {
		/*
		 * 
CREATE TABLE `jarinfo` (
  `id` int(11) NOT NULL auto_increment,
  `user` varchar(11) collate utf8_bin default NULL,
  `jarname` varchar(20) collate utf8_bin NOT NULL,
  `lastmodified` datetime default NULL,
  `expired` datetime default NULL,
  PRIMARY KEY  (`id`),
  KEY `jarinfo_expired` (`expired`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_bin AUTO_INCREMENT=10 ;
		 */
		/*
		 * sql : insert into jarinfo(user,jarname,lastmodified,expired) values ('my', '新建文本文档 (2).txt', '2012-12-1 22:41','2012-12-1 22:41');
		 */
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String str = "insert into jarinfo(user,jarname,lastmodified,expired) "+"values ('" + info.getUser() + "','" +info.getJarName() +"', '"+sdf.format(info.getLastModified())+"','"+sdf.format(info.getExpired())+"')";
		logger.info(str);
		jdbcTemplate.update("insert into jarinfo(user,jarname,lastmodified,expired) "+"values ('" + info.getUser() + "',  '" +info.getJarName() +"', '"+sdf.format(info.getLastModified())+"','"+sdf.format(info.getExpired())+"')");
		/*jdbcTemplate.update("insert into jarinfo(user,jarname,lastmodified,expired) values('?','?','?','?')", 
				info.getUser(),info.getJarName(),sdf.format(info.getLastModified()),sdf.format(info.getExpired()));*/
	}

	@Override
	public void deleteJarInfo(String name) {
		jdbcTemplate.update("delete from jarinfo where jarname = '"+name+"'");
		
	}

	@Override
	public void updateJarInfo(KaasJarInfo info) {
		
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public KaasJarInfo getFirstJar() {
		return (KaasJarInfo)jdbcTemplate.queryForObject("select * from jarinfo order by expired limit 1",  new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) {
				KaasJarInfo info = new KaasJarInfo();
				try {
					info.setJarName((String)rs.getString("jarname"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return info;
			}
		});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<KaasJarInfo> getFirstJars() {
		List<KaasJarInfo> list = new ArrayList<KaasJarInfo>();
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		list = (ArrayList<KaasJarInfo>)jdbcTemplate.query("select * from jarinfo where expired = (select min(expired) from jarinfo)",  new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) {
				KaasJarInfo info = new KaasJarInfo();
				try {
					info.setJarName((String)rs.getString("jarname"));
					info.setUser((String)rs.getString("user"));
					try {
						info.setLastModified(sdf.parse(rs.getString("lastmodified")));
						info.setLastModified(sdf.parse(rs.getString("expired")));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return info;
			}
		});
		return list;
	}
	
}