package com.mplatforma.amr;

import java.util.ArrayList;

import javax.crypto.spec.PSource;

import com.mplatforma.amr.server.AttachmentDTO;
import com.mplatforma.amr.server.BookDTO;
import com.mplatforma.amr.server.PageDTO;


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

public class AttachmentsListActivity extends ListActivity {
    /** Called when the activity is first created. */
    private DataHelper dh;
   // private final Activity context;  
    String[] desc;
    int[] ids;

    ArrayList<AttachmentDTO> atts = new ArrayList<AttachmentDTO>();
    private int page_id = -1;
	@Override
    public void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);      
       this.dh = new DataHelper(this);
       int toPage = getIntent().getIntExtra("PAGE_ID",-1);
       
       if(toPage!= -1)
       {
    	   page_id = toPage;
	       atts = dh.getAttachments(page_id);
	       
	       
	       desc = new String[atts.size()];
	       ids = new int[atts.size()];
	       for(int i=0; i<atts.size(); i++){
	    	   desc[i] = atts.get(i).getName();
	    	   ids[i] = atts.get(i).getId();
	       }
	       BaseAdapter mAdapter = new myAdapter(this);  
	       setListAdapter(mAdapter);  
	    }      
	}
    public void onListItemClick (ListView parent, View v, int position, long id) { 
    	
    	super.onListItemClick(parent, v, position, id);
    	int pos = position;
    	Intent intent = new Intent(this,VideoPlayer.class)
		.putExtra("ATT_ID", atts.get(pos).getId());
		//.putExtra("ATT_LOCAL_ID", atts.get(pos).get);
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
    	      return desc[position] + " (" + ids[position] + ")";  
    	    }  
    	          
    	    public View getView(int position, View convertView, ViewGroup parent) {   
    	      if (convertView == null)  
    	        convertView = mLayoutInflater.inflate(R.layout.item, null);  
    	               
    	      TextView sign = (TextView)convertView.findViewById(R.id.Sign);  
    	      sign.setText(desc[position]);  
    	        return convertView;
    	    } 
    	    
    	  }   
    	  	      
}