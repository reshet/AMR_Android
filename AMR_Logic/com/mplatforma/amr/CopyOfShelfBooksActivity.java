package com.mplatforma.amr;

import java.util.ArrayList;
import java.util.List;

import db.Book;
import db.DataHelper;
import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentValues;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

public class CopyOfShelfBooksActivity extends ListActivity {
    /** Called when the activity is first created. */
	private DataHelper dh;
	@Override    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //специально создаю нашу бд и ложу туда книги, потом они будут браться с сервера
        this.dh = new DataHelper(this);
        this.dh.deleteAll();
        dh.insert_book("book1");
        dh.insert_book("book2");
        
        ArrayList<Book> books = new ArrayList<Book>();
        books.addAll(this.dh.getAllBooks());        
       
        int[] images = new int[3];
        images[0] = R.drawable.book1;
        images[1] = R.drawable.book2;
        images[2] = R.drawable.book3;

        String[] array_of_books = new String[books.size()];
        for(int i=0; i<books.size(); i++){
        	array_of_books[i] = books.get(i).getTitle();
        }
        ArrayAdapter<String> adapter = new MySimpleArrayAdapter(this, array_of_books);
        setListAdapter(adapter);          
    }
}