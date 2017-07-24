package com.webside.ofp.bean;

import java.util.List;

public class OfpTreeBean {
	private String text;
	private int id;
	private List<OfpTreeBean> nodes;
	
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<OfpTreeBean> getNodes() {
		return nodes;
	}
	public void setNodes(List<OfpTreeBean> nodes) {
		this.nodes = nodes;
	}
}
