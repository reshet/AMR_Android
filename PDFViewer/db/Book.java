package db;

import java.io.Serializable;

public class Book implements Serializable{
	private int id;	
	private int exid;
	private String title;
	private int numb;
	private byte[] cover= null;
	private byte[] book=null;

	public Book(int id, int exid, byte[] cover, String title, int numb, byte[] book)
	{
		this.id = id;
		this.exid = exid;
		this.title = title;
		this.cover = cover;
		//if (cover!=null)System.arraycopy(cover, 0, this.cover, 0, cover.length);
		this.numb = numb;
		//if (book!=null)System.arraycopy(book, 0, this.book, 0, book.length);
	}
	public int getId(){
		return this.id;
	}
	public int getExid(){
		return this.exid;
	}
	public String getTitle() {
		return this.title;
	}
	public byte[] getCover(){
		return this.cover;
	}
	public int getNumb(){
		return this.numb;
	}
	public byte[] getBook(){
		return this.book;
	}
}