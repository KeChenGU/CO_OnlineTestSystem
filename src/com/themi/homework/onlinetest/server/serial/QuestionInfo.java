package com.themi.homework.onlinetest.server.serial;

import java.io.Serializable;

public class QuestionInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3156111561524577299L;
	
	String desc = null;
	
	String A = null;
	
	String B = null;
	
	String C = null;
	
	String D = null;
		
	public QuestionInfo() {
		
	}
	
	public QuestionInfo(String desc, String a, String b, String c, String d) {
		//super();
		this.desc = desc;
		A = a;
		B = b;
		C = c;
		D = d;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getA() {
		return A;
	}

	public void setA(String a) {
		A = a;
	}

	public String getB() {
		return B;
	}

	public void setB(String b) {
		B = b;
	}

	public String getC() {
		return C;
	}

	public void setC(String c) {
		C = c;
	}

	public String getD() {
		return D;
	}

	public void setD(String d) {
		D = d;
	}
	
	
}
