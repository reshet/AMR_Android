package com.mplatforma.amr;  
  
import java.util.ArrayList;

import net.sf.andpdf.pdfviewer.PdfFileSelectActivity;
import net.sf.andpdf.pdfviewer.PdfViewerActivity;

import db.Bookmark;
import db.DataHelper;

import android.app.Activity;  
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;  
import android.widget.Button;
import android.widget.ImageView;  
import android.widget.TextView;  
  
public class BookmarksArrayAdapter extends ArrayAdapter<String> {  
    private final Activity context;  
    private final ArrayList<Bookmark> bms;  
   // private DataHelper dh;
  
    public BookmarksArrayAdapter(Activity context, ArrayList<Bookmark> bookmarks) {  
        super(context, R.layout.bookmark_layout,new String[bookmarks.size()]);   
        this.context = context;  
        this.bms = bookmarks;
    }      
    OnClickListener BookmarksListener = new OnClickListener() {
    	public static final String EXTRA_PDFFILENAME = "net.sf.andpdf.extra.PDFFILENAME";
    	public static final String EXTRA_USEFONTSUBSTITUTION = "net.sf.andpdf.extra.USEFONTSUBSTITUTION";
   	    public static final String EXTRA_KEEPCACHES = "net.sf.andpdf.extra.KEEPCACHES";
   	    public void onClick(View v) {
    	    	
    	    	Intent intent = new Intent(context,PdfViewerActivity.class)
    			.putExtra(EXTRA_PDFFILENAME, "/mnt/sdcard/down/HPL-2004-76 (1).pdf")
    			.putExtra(EXTRA_USEFONTSUBSTITUTION, false)
    			.putExtra(EXTRA_KEEPCACHES, false)
    			.putExtra("topage", 4);
    			context.startActivity(intent);
        }
    };
    @Override  
    public View getView(final int position, View convertView, ViewGroup parent) {  
        LayoutInflater inflater = context.getLayoutInflater();  
        View rowView = inflater.inflate(R.layout.bookmark_layout, null, true);  
        
        TextView textView = (TextView) rowView.findViewById(R.id.bm_desc);  
        textView.setText(bms.get(position).getDesc());  
        textView.setOnClickListener(new OnClickListener() {
            	public static final String EXTRA_PDFFILENAME = "net.sf.andpdf.extra.PDFFILENAME";
           	 	public static final String EXTRA_USEFONTSUBSTITUTION = "net.sf.andpdf.extra.USEFONTSUBSTITUTION";
           	    public static final String EXTRA_KEEPCACHES = "net.sf.andpdf.extra.KEEPCACHES";
           	    @Override
    			public void onClick(View v) {
           	    	Intent intent = new Intent(context,PdfViewerActivity.class)
        			.putExtra(EXTRA_PDFFILENAME, "/mnt/sdcard/down/HPL-2004-76 (1).pdf")
        			.putExtra(EXTRA_USEFONTSUBSTITUTION, false)
        			.putExtra(EXTRA_KEEPCACHES, false)
        			.putExtra("topage", bms.get(position).getPage());
        			context.startActivity(intent);
    			}
        });
        
        Button button =  (Button) rowView.findViewById(R.id.GO_btn);
        button.setVisibility(0);
        button.setText("Стор."+bms.get(position).getPage());
        button.setOnClickListener(new OnClickListener() {
        	public static final String EXTRA_PDFFILENAME = "net.sf.andpdf.extra.PDFFILENAME";
       	 	public static final String EXTRA_USEFONTSUBSTITUTION = "net.sf.andpdf.extra.USEFONTSUBSTITUTION";
       	    public static final String EXTRA_KEEPCACHES = "net.sf.andpdf.extra.KEEPCACHES";
       	    @Override
			public void onClick(View v) {
       	    	Intent intent = new Intent(context,PdfViewerActivity.class)
    			.putExtra(EXTRA_PDFFILENAME, "/mnt/sdcard/down/HPL-2004-76 (1).pdf")
    			.putExtra(EXTRA_USEFONTSUBSTITUTION, false)
    			.putExtra(EXTRA_KEEPCACHES, false)
    			.putExtra("topage", bms.get(position).getPage());
    			context.startActivity(intent);
			}
		});
        return rowView;  
    }  
}  