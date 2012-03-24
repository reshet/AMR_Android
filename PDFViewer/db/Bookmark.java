package db;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

public class Bookmark implements Serializable {
	private int id_book;
	private int page;
	private String desc;
	private int id;
	private Date date;	
	static DateComparator dateComparator = new Bookmark.DateComparator();
	
	public static DateComparator getDateComparator() {
        return dateComparator;
  }
	public Bookmark(int id, int id_book,int page, String desc, String date)
	{
		this.id = id;
		this.desc = desc;
		this.id_book = id_book;
		this.page = page;
		Regular r = new Regular();
		if (r.check(date))
			this.date = r.find(date);
	    else
	    	this.date = null;
	}
	public int getID(){
		return this.id;
	}
	public int getId_book() {
		return id_book;
	}
	public void setId_book(int id_book) {
		this.id_book = id_book;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public Date getDate(){
		return this.date;
	}
	
	static class DateComparator implements Comparator<Bookmark> {
		@Override
		public int compare(Bookmark b1, Bookmark b2) {
			return (b1.getDate().compareTo(b2.getDate()));
		}
	}
}
