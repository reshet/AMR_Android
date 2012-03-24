package com.mplatforma.amr;

import java.util.ArrayList;

import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import db.Book;
import db.Bookmark;
import db.DataHelper;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ShelfBookmarksActivity extends ListActivity {
    /** Called when the activity is first created. */
    private DataHelper dh;
   // private final Activity context;  
    String[] desc;
    String[] page;
    String[] book;
    public static final String EXTRA_PDFFILENAME = "net.sf.andpdf.extra.PDFFILENAME";
	public static final String EXTRA_USEFONTSUBSTITUTION = "net.sf.andpdf.extra.USEFONTSUBSTITUTION";
	public static final String EXTRA_KEEPCACHES = "net.sf.andpdf.extra.KEEPCACHES";
    
	@Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);      
       this.dh = new DataHelper(this);
       this.dh.deleteAll();
       dh.insert_book("book1");
       dh.insert_book("book2");
       ArrayList<Book> books = new ArrayList<Book>();
       books.addAll(this.dh.getAllBooks());
       dh.insert_bookmark(books.get(0).getId(), 24, "First, book1");
       dh.insert_bookmark(books.get(1).getId(), 12, "Second, book2");
       dh.insert_bookmark(books.get(1).getId(), 1, "Third, book2");
       dh.insert_bookmark(books.get(0).getId(), 2, "Forth, book1");
       dh.insert_bookmark(books.get(0).getId(), 2, "Fifth, book1");
       dh.insert_bookmark(books.get(0).getId(), 3, "Sixth, book1");
       dh.insert_bookmark(books.get(1).getId(), 6, "Seventh, book2");
       
       ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();
       bookmarks.addAll(dh.getAllBookmarks());
       
       
       desc = new String[bookmarks.size()];
       page = new String[bookmarks.size()];
       book = new String[bookmarks.size()];
       for(int i=0; i<bookmarks.size(); i++){
    	   desc[i] = bookmarks.get(i).getDesc();
    	   page[i] = "page " + bookmarks.get(i).getPage()+" ";  
    	   Book b = dh.getBook(bookmarks.get(i));
    	   book[i] = b.getTitle();
       }
       // Почему-то не работает компаратор... вылетает... посмотри
       //Collections.sort(bookmarks, Bookmark.getDateComparator());
       BaseAdapter mAdapter = new myAdapter(this);  
       setListAdapter(mAdapter);  
              
	}
    public void onListItemClick (ListView parent, View v, int position, long id) { 
    	
    	super.onListItemClick(parent, v, position, id);
    	Intent intent = new Intent(this,PdfViewerActivity.class)
		.putExtra(EXTRA_PDFFILENAME, "/mnt/sdcard/down/HPL-2004-76 (1).pdf")
		.putExtra(EXTRA_USEFONTSUBSTITUTION, false)
		.putExtra(EXTRA_KEEPCACHES, false)
		.putExtra("topage", 4);
    	startActivityForResult(intent,1); 
    }  
    	      
    	  public class myAdapter extends BaseAdapter { 
    	    private LayoutInflater mLayoutInflater;  
    	          
    	    public myAdapter (Context ctx) {  
    	      mLayoutInflater = LayoutInflater.from(ctx);  
    	    }  
    	          
    	    public int getCount () {  
    	      return desc.length;  
    	    }  
    	          
    	    public Object getItem (int position) {  
    	      return position;  
    	    }  
    	          
    	    public long getItemId (int position) {  
    	      return position;  
    	    }  
    	          
    	    public String getString (int position) {  
    	      return desc[position] + " (" + page[position] + ")";  
    	    }  
    	          
    	    public View getView(int position, View convertView, ViewGroup parent) {   
    	      if (convertView == null)  
    	        convertView = mLayoutInflater.inflate(R.layout.item, null);  
    	               
    	      TextView sign = (TextView)convertView.findViewById(R.id.Sign);  
    	      sign.setText(desc[position]);  
    	  
    	      TextView date = (TextView)convertView.findViewById(R.id.Date);  
    	      date.setText(book[position]+", "+page[position]);    

    	        return convertView;
    	    } 
    	    
    	  }   
   	      
}