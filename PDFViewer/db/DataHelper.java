package db;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;

import com.mplatforma.amr.R;

import android.content.ContentValues;
import android.content.Context;
//import android.database.Cursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
 
//import java.util.ArrayList;
//import java.util.List;
 
public class DataHelper {
 
   private static final String DATABASE_NAME = "AMR_DB.db";
   private static final int DATABASE_VERSION = 1;
   
   private static final String BITMAP_TABLE_NAME = "bitmaps";
   private static final String BOOK_TABLE_NAME = "books";
   private static final String BOOKMARK_TABLE_NAME = "bookmarks";
   
   public static final String BITMAP_COLUMN_ID = "_id";
   public static final String BITMAP_COLUMN_BITMAP = "bitmap";
   public static final String BITMAP_COLUMN_ZOOM = "zoom";
   
   public static final String BOOK_COLUMN_ID = "_id";
   public static final String BOOK_COLUMN_COVER = "cover";
   public static final String BOOK_COLUMN_TITLE = "title";
   
   public static final String BOOKMARK_COLUMN_ID = "_id";
   public static final String BOOKMARK_COLUMN_BOOKID = "_id_book";
   public static final String BOOKMARK_COLUMN_PAGE = "page_number";
   public static final String BOOKMARK_COLUMN_DESC = "description";
   public static final String BOOKMARK_COLUMN_DATE = "date_";
   
   public static final String KEY_IMG = "image";
   
   private static final String DATABASE_CREATE = "create table " + BITMAP_TABLE_NAME 
		   + "("+ BITMAP_COLUMN_ID +" integer primary key autoincrement, " 
		   + BITMAP_COLUMN_BITMAP + " blob not null, "
		   + BITMAP_COLUMN_ZOOM + " double not null);";
   private static final String DATABASE_CREATE2 = 
		    "create table " + BOOK_TABLE_NAME 
		   + "(" + BOOK_COLUMN_ID + " integer primary key autoincrement, "
		   //+ BOOK_COLUMN_COVER + "integer not null,"
		   + BOOK_COLUMN_TITLE + " text not null);";
   private static final String DATABASE_CREATE3 = 
		    "create table " + BOOKMARK_TABLE_NAME 
		   + "(" + BOOKMARK_COLUMN_ID + " integer primary key autoincrement, "
		   + BOOKMARK_COLUMN_BOOKID + " integer not null, "
		   + BOOKMARK_COLUMN_PAGE + " integer not null, "
		   + BOOKMARK_COLUMN_DESC + " text not null, "
		   + BOOKMARK_COLUMN_DATE + " text not null);";
   			
   private Context context;
   private OpenHelper OpenHelper;
   private SQLiteDatabase db; 
   private SQLiteStatement ins_bitmap_smt,ins_bookmark_stmt, ins_book_stmt;
   
   private static final String INSERT_BITMAP = "insert into "
		   + BITMAP_TABLE_NAME + "("+ BITMAP_COLUMN_ID + "," + BITMAP_COLUMN_BITMAP + "," 
		   + BITMAP_COLUMN_ZOOM + ") values (NULL, ?, ?)";
   
   private static final String INSERT_BOOKMARK = "insert into " 
		   + BOOKMARK_TABLE_NAME + " ("+ BOOKMARK_COLUMN_ID +","+ BOOKMARK_COLUMN_BOOKID
		   + "," + BOOKMARK_COLUMN_PAGE + "," + BOOKMARK_COLUMN_DESC + "," 
		   + BOOKMARK_COLUMN_DATE + ") values (NULL, ?, ?, ?, ?)";
   
   public static final String INSERT_BOOK = "insert into " 
		   + BOOK_TABLE_NAME + "(" + BOOK_COLUMN_ID 
		   //+ "," + BOOK_COLUMN_COVER 
		   //+ "," + BOOK_COLUMN_TITLE + ") values (NULL, ?, ?)";
   		   + "," + BOOK_COLUMN_TITLE + ") values (NULL, ?)";
 
   public DataHelper(Context context) {
	   this.context = context;
       OpenHelper openHelper = new OpenHelper(this.context);
       this.db = openHelper.getWritableDatabase();
       int version = db.getVersion();
     //  openHelper.onUpgrade(db, version, version+1);
       this.ins_bitmap_smt = this.db.compileStatement(INSERT_BITMAP);
       this.ins_book_stmt = this.db.compileStatement(INSERT_BOOK);
       this.ins_bookmark_stmt = this.db.compileStatement(INSERT_BOOKMARK);
   }
   /*public void open() {
	   OpenHelper = new OpenHelper(context);
	   db = OpenHelper.getWritableDatabase();
   }*/
   public long insert_bookmark(int id_book, int page, String desc) {
	   this.ins_bookmark_stmt.bindLong(1,  id_book);
       this.ins_bookmark_stmt.bindLong(2, page);
       this.ins_bookmark_stmt.bindString(3, desc);
       Date date = new Date();
       this.ins_bookmark_stmt.bindString(4, date.toLocaleString());
       return this.ins_bookmark_stmt.executeInsert();
   }
   
   public long insert_bitmap(byte[] bMapArray, double zoom) {
	   this.ins_bitmap_smt.bindBlob(1,  bMapArray);
	   this.ins_bitmap_smt.bindDouble(2, zoom);
	   return this.ins_bitmap_smt.executeInsert();
   }
   
   //public void insert_book(int img, String txt){
   public long insert_book(String txt){
	   this.ins_book_stmt.bindString(1, txt);
	   return this.ins_book_stmt.executeInsert();
	   //ContentValues cv = new ContentValues();
	   //cv.put(BOOK_COLUMN_TITLE, txt);
	   //cv.put(BOOK_COLUMN_COVER, img);
	   //db.insert(BOOK_TABLE_NAME, null, cv);	   
   }

   public Bitmap get(int page){
	  
	   Bitmap bi = null;	   
	   Cursor c = this.db.rawQuery("SELECT * FROM " +BITMAP_TABLE_NAME + ";", null);
	   if(c.getCount() > 0){
		   int count = 0;
		   while (count != page)
		   {
			   c.moveToNext();
			   ++count;
		   }
		   ByteArrayInputStream inputStream = new ByteArrayInputStream(c.getBlob(1));
		   bi = BitmapFactory.decodeStream(inputStream);   
		   if (c != null && !c.isClosed())
		         c.close();
	  	  
	   }	   
	return bi;	   
   }
   
   public ArrayList<Bookmark> getAllBookmarks(){
	   ArrayList<Bookmark> arr = new ArrayList<Bookmark>();
	   Cursor c = this.db.rawQuery("SELECT * FROM " + BOOKMARK_TABLE_NAME + ";", null);
	   c.moveToFirst();
	   while(!c.isAfterLast()){
		   Bookmark b = new Bookmark(c.getInt(0), c.getInt(1), c.getInt(2), c.getString(3), c.getString(4));
		   arr.add(b);
		   c.moveToNext();	   
	   }
	   c.close();  
	   return arr;
   }
   public ArrayList<Bookmark> getBookmarks(Book b){
	   ArrayList<Bookmark> arr = new ArrayList<Bookmark>();
	   Cursor c = this.db.rawQuery("SELECT * FROM " + BOOKMARK_TABLE_NAME + " WHERE _id_book = " 
			   + b.getId()+" ;", null);
	   c.moveToFirst();
	   while(!c.isAfterLast()){
		   Bookmark bm = new Bookmark(c.getInt(0), c.getInt(1), c.getInt(2), c.getString(3), c.getString(4));
		   arr.add(bm);
		   c.moveToNext();	   
	   }
	   c.close();  
	   return arr;
   }
   public Book getBook(Bookmark b){
	   Book book = null;
	   Cursor c = this.db.rawQuery(" SELECT * FROM " + BOOK_TABLE_NAME + " WHERE _id = "
			   + b.getId_book() + " ; ", null);
	   if (!c.isAfterLast()){
		   c.moveToFirst();
		   book = new Book(c.getInt(0), c.getString(1));
	   }
	   c.close();
	   return book;
   }
   
   public ArrayList<Book> getAllBooks(){
	   ArrayList<Book> books = new ArrayList<Book>();
	   Cursor c = this.db.rawQuery("SELECT * FROM " + BOOK_TABLE_NAME + ";", null);
	   c.moveToFirst();
	   while(!c.isAfterLast()){
		   Book b = new Book(c.getInt(0), c.getString(1));
		   books.add(b);
		   c.moveToNext();	   
	   }
	   c.close();	  
	   return books;
   }
   
   public double getZoom(int page){
		  
	   double zoom = 0;
	   Cursor c = this.db.rawQuery("SELECT * FROM " +BITMAP_TABLE_NAME + ";", null);
	   if(c.getCount() > 0){
		   int count = 0;
		   while (count != page)
		   {
			   c.moveToNext();
			   ++count;
		   }
		   zoom = c.getDouble(2);
		   if (c != null && !c.isClosed())
		         c.close();
	  	  
	   }
	return zoom;
	   
   }
 
   public void deleteAll() {
      this.db.delete(BITMAP_TABLE_NAME, null, null);
      this.db.delete(BOOK_TABLE_NAME, null, null);
      this.db.delete(BOOKMARK_TABLE_NAME, null, null);
   }
 
  /* public List<byte[]> selectAll() {
      List<byte[]> list = new ArrayList<byte[]>();
      Cursor cursor = this.db.query(TABLE_NAME, new String(byte[][]) {}, 
        null, null, null, null, "name desc");
      if (cursor.moveToFirst()) {
         do {
            list.add(cursor.getBlob([0])); 
         } while (cursor.moveToNext());
      }
      if (cursor != null && !cursor.isClosed()) {
         cursor.close();
      }
      return list;
   }
   */
 
   private static class OpenHelper extends SQLiteOpenHelper {
 
      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
      }
 
      @Override
      public void onCreate(SQLiteDatabase db) {
    	  db.execSQL(DATABASE_CREATE);
    	  db.execSQL(DATABASE_CREATE2);
    	  db.execSQL(DATABASE_CREATE3);    	  
    	  int d = 2;
      }
 
      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
         db.execSQL("DROP TABLE IF EXISTS " + BITMAP_TABLE_NAME);
         db.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE_NAME);
         db.execSQL("DROP TABLE IF EXISTS " + BOOKMARK_TABLE_NAME);
         onCreate(db);
      }
   }
}