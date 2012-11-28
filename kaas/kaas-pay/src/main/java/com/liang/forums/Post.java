package com.liang.forums;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Post {
	private int id;
	private int pid; //private Article parent
	private int rootId;
	private String title;
	private String content;
	private Date post_date;
	private boolean isLeaf;
	//private int grade;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public int getRootId() {
		return rootId;
	}
	public void setRootId(int rootId) {
		this.rootId = rootId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCont() {
		return content;
	}
	public void setCont(String cont) {
		this.content = cont;
	}
	public String getPdate() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(post_date);
	}
	public void setPdate(Date pdate) {
		this.post_date = pdate;
	}
	public boolean isLeaf() {
		return isLeaf;
	}
	public void setLeaf(boolean isLeaf) {
		this.isLeaf = isLeaf;
	}

	
	public void initFromRs(ResultSet rs) {
		try {
			setId(rs.getInt("id"));
			setPid(rs.getInt("pid"));
			setRootId(rs.getInt("root_id"));
			setTitle(rs.getString("title"));
			setLeaf(rs.getInt("isleaf") == 1 ? true : false);
			setPdate(rs.getTimestamp("post_date"));
			setCont(rs.getString("content"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
