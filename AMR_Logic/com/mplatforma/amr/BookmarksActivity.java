package com.mplatforma.amr;

import java.util.ArrayList;

import com.mplatforma.amr.server.BookDTO;
import com.mplatforma.amr.server.PageDTO;


import net.sf.andpdf.pdfviewer.PdfViewerActivity;
import net.sf.andpdf.pdfviewer.PdfViewerICE2Activity;

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

public class BookmarksActivity extends ListActivity {
    /** Called when the activity is first created. */
    private DataHelper dh;
   // private final Activity context;  
    String[] desc;
    String[] page;
    int[] pages;
    public static final String EXTRA_PDFFILENAME = "net.sf.andpdf.extra.PDFFILENAME";
	public static final String EXTRA_USEFONTSUBSTITUTION = "net.sf.andpdf.extra.USEFONTSUBSTITUTION";
	public static final String EXTRA_KEEPCACHES = "net.sf.andpdf.extra.KEEPCACHES";
	    public static final String EXTRA_PDF_ID = "my.pdf_id";
	    public static final String EXTRA_SHOWIMAGES = "net.sf.andpdf.extra.SHOWIMAGES";
	    public static final String EXTRA_ANTIALIAS = "net.sf.andpdf.extra.ANTIALIAS";
	private long book_id = 0;
	@Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);      
       this.dh = new DataHelper(this);
       book_id = getIntent().getIntExtra("BOOK_ID",-1);
       ArrayList<Bookmark> bookmarks = new ArrayList<Bookmark>();
       
       bookmarks = dh.getBookmarks(new Book((int)book_id,0,null,null,0,null));
       
       desc = new String[bookmarks.size()];
       page = new String[bookmarks.size()];
       pages = new int[bookmarks.size()];
       for(int i=0; i<bookmarks.size(); i++){
    	   desc[i] = bookmarks.get(i).getDesc();
    	   page[i] = "page " + bookmarks.get(i).getPage()+" ";  
    	   pages[i] = bookmarks.get(i).getPage();  
       }
       BaseAdapter mAdapter = new myAdapter(this);  
       setListAdapter(mAdapter);  
              
	}
    public void onListItemClick (ListView parent, View v, int position, long id) { 
    	
    	super.onListItemClick(parent, v, position, id);
    	
    	openBook((int)book_id, pages[position]);
    }  
    
    private void openBook(int id,int page_id)
    {
    	boolean useFontSubstitution = false;
    	boolean keepCaches = true;
  	
    	Intent intent = new Intent(BookmarksActivity.this, PdfViewerICE2Activity.class)
		.putExtra(EXTRA_PDF_ID, id)
		.putExtra(EXTRA_USEFONTSUBSTITUTION, useFontSubstitution)
		.putExtra(EXTRA_KEEPCACHES, keepCaches)
    	.putExtra("topage", page_id);
    	
    	this.finish();
    	startActivity(intent);
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
    	      date.setText(page[position]);    

    	        return convertView;
    	    } 
    	    
    	  }   
   	      
}