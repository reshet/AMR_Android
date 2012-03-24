package com.mplatforma.amr;
import java.util.ArrayList;

import db.Book;
import db.Bookmark;
import db.DataHelper;
import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ExpandableListView.ExpandableListContextMenuInfo;

//import com.example.android.apis.R;

public class ShelfBooksActivity extends ExpandableListActivity {
	ExpandableListView epView;
    ExpandableListAdapter mAdapter;
    DataHelper dh;
    private String[] books;    
    private String[][] bookmarks;
    private String[][] pages;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up our adapter
        mAdapter = new MyExpandableListAdapter();
        this.dh = new DataHelper(this);
        this.dh.deleteAll();
        dh.insert_book("book1");
        dh.insert_book("book2");
        dh.insert_book("book3");
        
        ArrayList<Book> bookslist = new ArrayList<Book>();
        bookslist = dh.getAllBooks();
        dh.insert_bookmark(bookslist.get(0).getId(), 24, "First, book1");
        dh.insert_bookmark(bookslist.get(1).getId(), 12, "Second, book2");
        dh.insert_bookmark(bookslist.get(1).getId(), 1, "Third, book2");
        dh.insert_bookmark(bookslist.get(0).getId(), 2, "Forth, book1");
        dh.insert_bookmark(bookslist.get(0).getId(), 2, "Fifth, book1");
        dh.insert_bookmark(bookslist.get(0).getId(), 3, "Sixth, book1");
        dh.insert_bookmark(bookslist.get(1).getId(), 6, "Seventh, book2");
       
        books = new String[bookslist.size()];
        bookmarks = new String[bookslist.size()][];
        pages = new String[bookslist.size()][];
        
        for (int i=0; i<bookslist.size(); i++){
        	books[i] = bookslist.get(i).getTitle();
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
        menu.setHeaderTitle("Sample menu");
        menu.add(0, 0, 0, R.string.hello);
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

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 64);

            TextView textView = new TextView(ShelfBooksActivity.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(53, 0, 0, 0);
            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
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

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            TextView textView = getGenericView();
            textView.setText(getGroup(groupPosition).toString());
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }
}