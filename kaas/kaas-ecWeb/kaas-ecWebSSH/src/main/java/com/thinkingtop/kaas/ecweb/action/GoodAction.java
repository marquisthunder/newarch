package com.thinkingtop.kaas.ecweb.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import com.thinkingtop.kaas.ecweb.manage.GoodManage;
import com.thinkingtop.kaas.ecweb.model.Good;

@Component("goodAction")
@Scope("prototype")
public class GoodAction implements Action {
	static Logger logger=Logger.getLogger(GoodAction.class);
	private int id;
	private Good good;
	private List<Good> goods;
	private GoodManage goodManage;
	@Resource(name="goodManage")
	public void setGoodManage(GoodManage goodManage) {
		this.goodManage = goodManage;
	}
	public GoodManage getGoodManage() {
		return goodManage;
	}

	@Override
	public String execute() throws Exception {
		if (id < 1) {
			id = 1;
		}
		
		this.good = goodManage.getGood(id);
		
		String ecommerceName = "jingdong";
		String apiKey = "an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ";
		String endUser = "liming";
		String scheme = "scheme1";
		int outputItemsNum = 2;
		int outputQuantitye = 2;
		
		this.goods = goodManage.getRecommends(ecommerceName, apiKey, endUser, scheme, String.valueOf(id), outputItemsNum, outputQuantitye);
		return "success";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Good> getGoods() {
		return goods;
	}
	public void setGoods(List<Good> goods) {
		this.goods = goods;
	}
	public Good getGood() {
		return good;
	}
	public void setGood(Good good) {
		this.good = good;
	}


}
