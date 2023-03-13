package com.themi.homework.onlinetest.server.serial;

import java.io.Serializable;
import java.util.List;

public class PaperInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6088751849427228952L;

	private String name;
	
	private String desc;
	
	private List<QuestionInfo> queList;
	
	public PaperInfo() {
		// TODO Auto-generated constructor stub
	}

	public PaperInfo(String name, String desc, List<QuestionInfo> queList) {
		//super();
		this.name = name;
		this.desc = desc;
		this.queList = queList;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public List<QuestionInfo> getQueList() {
		return queList;
	}

	public void setQueList(List<QuestionInfo> queList) {
		this.queList = queList;
	}
	
}


