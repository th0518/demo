package com.example.fool.entity;

import java.io.Serializable;

import android.graphics.Bitmap;

public class ArticleEntity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String location;
	private String url;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public ArticleEntity() {
		super();
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "ArticleEntity [name=" + name + ", location=" + location
				+ ", url=" + url + "]";
	}
	public ArticleEntity(String name, String location, String url) {
		super();
		this.name = name;
		this.location = location;
		this.url = url;
	}
	
	
}
