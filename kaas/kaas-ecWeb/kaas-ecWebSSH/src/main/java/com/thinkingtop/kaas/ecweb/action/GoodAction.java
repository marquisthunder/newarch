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
public class GoodAction  implements Action {
	static Logger logger=Logger.getLogger(GoodAction.class);
	private int id;
	private GoodManage goodManage;
	@Resource(name="goodManage")
	public void setGoodManage(GoodManage goodManage) {
		this.goodManage = goodManage;
	}
	public GoodManage getGoodManage() {
		return goodManage;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	@Override
	public String execute() throws Exception {
		if (id < 1) {
			id = 1;
		}
		HttpServletRequest request = ServletActionContext.getRequest();
		Good good = goodManage.getGood(id);
		request.setAttribute("good",good);
		
		String ecommerceName = "jingdong";
		String apiKey = "an2mZW9iLtGdQ~aobA13+V46_vy$2^D4%8+0mQ17nysq6NPC+2uiJnS$v256t$o4MY_2w1b%%tYNdxQ";
		String endUser = "liming";
		String scheme = "scheme1";
		int outputItemsNum = 2;
		int outputQuantitye = 3;
		
		List<Good> goods = goodManage.getRecommends(ecommerceName, apiKey, endUser, scheme, String.valueOf(id), outputItemsNum, outputQuantitye);
		request.setAttribute("goods",goods);
		return "success";
	}


}
