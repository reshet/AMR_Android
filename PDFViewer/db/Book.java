package db;

import java.io.Serializable;

public class Book implements Serializable{
	private int id;	
	private String title;
	/*
	private int cover;
	public Book(int id, int cover, String title)
	{
		this.id = id;
		this.cover = cover;
		this.title = title;
	}
	public int getCover() {
		return this.cover;
	}
	public void setCover(int cover) {
		this.cover = cover;
	}*/
	public Book(int id, String title)
	{
		this.id = id;
		this.title = title;
	}
	public int getId(){
		return this.id;
	}
	public String getTitle() {
		return this.title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}