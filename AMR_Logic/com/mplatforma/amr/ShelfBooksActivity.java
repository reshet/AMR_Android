package com.mplatforma.amr;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.io.IOUtils;

import net.sf.andpdf.pdfviewer.PdfFileSelectActivity;
import net.sf.andpdf.pdfviewer.PdfViewerActivity;
import net.sf.andpdf.pdfviewer.PdfViewerICE2Activity;
import net.sf.andpdf.pdfviewer.PdfViewerICEActivity;

import com.mplatforma.amr.server.AttachmentDTO;
import com.mplatforma.amr.server.BookDTO;
import com.mplatforma.amr.server.PageDTO;
import com.mplatforma.amr.server.ServerConnector;

import db.Book;
import db.Bookmark;
import db.DataHelper;
import android.app.ExpandableListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

//import com.example.android.apis.R;

public class ShelfBooksActivity extends ExpandableListActivity {
	   public static final String EXTRA_PDFFILENAME = "net.sf.andpdf.extra.PDFFILENAME";
	    public static final String EXTRA_PDF_ID = "my.pdf_id";
	    public static final String EXTRA_SHOWIMAGES = "net.sf.andpdf.extra.SHOWIMAGES";
	    public static final String EXTRA_ANTIALIAS = "net.sf.andpdf.extra.ANTIALIAS";
	    public static final String EXTRA_USEFONTSUBSTITUTION = "net.sf.andpdf.extra.USEFONTSUBSTITUTION";
	    public static final String EXTRA_KEEPCACHES = "net.sf.andpdf.extra.KEEPCACHES";
	
	
	
	ExpandableListView epView;
    ExpandableListAdapter mAdapter;
    DataHelper dh;
    private String[] books;
    private Integer[] books_ids;
    
    private String[][] bookmarks;
    private String[][] pages;
    ArrayList<Book> bookslist = new ArrayList<Book>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up our adapter
        mAdapter = new MyExpandableListAdapter();
        this.dh = new DataHelper(this);
      //  this.dh.deleteAll();
        
        //SAMPLE
//	        ArrayList<PageDTO> dtos = new ArrayList<PageDTO>();
//	        Collection<AttachmentDTO> atts = new ArrayList<AttachmentDTO>();
//	        atts.add(new AttachmentDTO(1,"attachment 1"));
//	        atts.add(new AttachmentDTO(2,"attachment 2"));
//	        for(int i = 0; i < 50;i++)
//	        {
//	        	PageDTO dt = new PageDTO(i, atts);
//	        	dtos.add(dt);
//	        }
//        dh.insert_book(new BookDTO(1,"Book 1)",dtos));
//        dh.insert_book(new BookDTO(2,"Book 2)",new ArrayList<PageDTO>()));
//        dh.insert_book(new BookDTO(3,"Book 3)",new ArrayList<PageDTO>()));
//        
        bookslist = dh.getAllBooks();
//        dh.insert_bookmark(bookslist.get(0).getId(), 24, "First, book1");
//        dh.insert_bookmark(bookslist.get(1).getId(), 12, "Second, book2");
//        dh.insert_bookmark(bookslist.get(1).getId(), 1, "Third, book2");
//        dh.insert_bookmark(bookslist.get(0).getId(), 2, "Forth, book1");
//        dh.insert_bookmark(bookslist.get(0).getId(), 2, "Fifth, book1");
//        dh.insert_bookmark(bookslist.get(0).getId(), 3, "Sixth, book1");
//        dh.insert_bookmark(bookslist.get(1).getId(), 6, "Seventh, book2");
       
        books = new String[bookslist.size()];
        books_ids = new Integer[bookslist.size()];
        
        bookmarks = new String[bookslist.size()][];
        pages = new String[bookslist.size()][];
        
        for (int i=0; i<bookslist.size(); i++){
        	books[i] = bookslist.get(i).getTitle();
        	books_ids[i] = bookslist.get(i).getId();
        	ArrayList<Bookmark> bookmarkslist = new ArrayList<Bookmark>();
        	bookmarkslist.addAll(dh.getBookmarks(bookslist.get(i)));
        	bookmarks[i] = new String [bookmarkslist.size()];
        	for (int j=0; j<bookmarkslist.size(); j++){
        		bookmarks[i][j]=bookmarkslist.get(j).getDesc();
        	}
        }        
        
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
        /*
        epView.setAdapter(mAdapter);
        epView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent,
                View v, int groupPosition, int childPosition,
                long id) {
            if (groupPosition == 0 && childPosition == 0) {
                
            } 
            return false;
        }
        });*/

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Завантажуємо книгу...");
        menu.add(0, 0, 0, R.string.hello);
        menu.add(0, 0, 0, menuInfo.toString());
        
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

        String title = ((TextView) info.targetView).getText().toString();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition); 
            //Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos,
            //        Toast.LENGTH_SHORT).show();
            return true;
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            //Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
    
    private void openBook(int id)
    {
    	
    	
    	boolean useFontSubstitution = false;
    	boolean keepCaches = true;
////    	
//    	
    	Intent intent = new Intent(ShelfBooksActivity.this, PdfViewerICE2Activity.class)
		.putExtra(EXTRA_PDF_ID, id)
		.putExtra(EXTRA_USEFONTSUBSTITUTION, useFontSubstitution)
		.putExtra(EXTRA_KEEPCACHES, keepCaches);
    	startActivity(intent);
    }

    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids. 
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     *
     */
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
        // Sample data set.  children[i] contains the children (String[]) for groups[i].


        public Object getChild(int groupPosition, int childPosition) {
            return bookmarks[groupPosition][childPosition];
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
            return bookmarks[groupPosition].length;
        }

        public LinearLayout getGenericView(String txt) {
             //Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 64);

            
//            TextView textView = new TextView(ShelfBooksActivity.this);
//            textView.setLayoutParams(lp);
//            // Center the text vertically
//            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
//            // Set the text starting position
//            textView.setPadding(53, 0, 0, 0);
//            
//            
            
//            LayoutInflater inflater = ShelfBooksActivity.this.getLayoutInflater();  
//            View rowView = inflater.inflate(R.layout.rowlayout, null, true);  
//            
            LinearLayout l = new LinearLayout(ShelfBooksActivity.this);
            l.setLayoutParams(lp);
            l.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            l.setPadding(53, 0, 0, 0);
            
            TextView textView2 = new TextView(ShelfBooksActivity.this);  
            ImageView imageView = new ImageView(ShelfBooksActivity.this);
           
            //imageView.setImageBitmap()
            textView2.setText(txt);  
           // textView2.setText(getGroup(groupPosition).toString()); 
            Button button =  new Button(ShelfBooksActivity.this);
            button.setText("Відкрити");
            l.addView(imageView);
            l.addView(textView2);
            l.addView(button);
            return l;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            TextView textView = new TextView(ShelfBooksActivity.this);
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        public Object getGroup(int groupPosition) {
            return books[groupPosition];
        }

        public int getGroupCount() {
            return books.length;
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        @Override
        public View getGroupView(final int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
        	
        	AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 64);
        	
        	 LayoutInflater inflater = ShelfBooksActivity.this.getLayoutInflater();  
             View rowView = inflater.inflate(R.layout.book_item_layout, null, true);  
             rowView.setLayoutParams(lp);
             TextView textView = (TextView) rowView.findViewById(R.id.label);  
             ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);  
             Book b =bookslist.get(groupPosition);
             if(b!=null){
            	 
             byte [] ar = b.getCover();
                     if(ar != null)
          		   {
          			   ByteArrayInputStream inputStream = new ByteArrayInputStream(ar);
          			   Bitmap	bi = BitmapFactory.decodeStream(inputStream);   
          			   //imageView.
          			   imageView.setImageBitmap(bi);
          		   }
             }
             //textView.setText(names.get(position));  
             
             Button button =  (Button) rowView.findViewById(R.id.BtnToClick);
             
             button.setOnClickListener(new OnClickListener() {
                 public void onClick(View v) {
                	openBook(books_ids[groupPosition]);
                     //v.setBackgroundColor(Color.GREEN);
                 }
             });
             
             //String s = names.get(position);
             String s = getGroup(groupPosition).toString();
             textView.setText(s); 
             imageView.setImageResource(R.drawable.book1);
             
            
            return rowView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }
}