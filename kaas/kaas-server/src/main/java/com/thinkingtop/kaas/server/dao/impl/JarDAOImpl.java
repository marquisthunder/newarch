package com.thinkingtop.kaas.server.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

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
		System.out.println("insert into jarinfo(user,jarname) "+"values ('" + info.getUser() + "',  '" +info.getJarName() +"')");
		jdbcTemplate.update("insert into jarinfo(user,jarname) "+"values (' " + info.getUser() + " ',  '" +info.getJarName() +"')");
		
		//jdbcTemplate.update("insert into jarinfo(user,jarname,expired) values(?,?,?)", info.getUser(),info.getJarName(),info.getExpired());
		
	}

	@Override
	public void deleteJarInfo(String name) {
		jdbcTemplate.update("delete from jarinfo where jarname = '"+name+"'");
		
	}

	@Override
	public void updateJarInfo(KaasJarInfo info) {
		
	}

	@Override
	public KaasJarInfo getFirstJar() {
		return jdbcTemplate.queryForObject("select * from jarinfo order by expired limit 1",  new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) {
				KaasJarInfo info = new KaasJarInfo();
				try {
					info.setJarName((String)rs.getString("jarname"));
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return info;
			}
		});
	}

	@Override
	public Set<KaasJarInfo> getFirstJars() {
		// TODO Auto-generated method stub
		return null;
	}
	

	
	
}