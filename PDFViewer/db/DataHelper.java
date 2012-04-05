package db;


import java.io.ByteArrayInputStream;
import com.mplatforma.amr.server.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;



import android.content.Context;
import android.database.Cursor;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
 
public class DataHelper {
 
   private static final String DATABASE_NAME = "AMR_DB.db";
   private static final int DATABASE_VERSION = 6;
   
   private static final String BITMAP_TABLE_NAME = "bitmaps";
   private static final String BOOK_TABLE_NAME = "books";
   private static final String BOOKMARK_TABLE_NAME = "bookmarks";
   private static final String PAGE_TABLE_NAME = "pages";
   private static final String ATTACHMENT_TABLE_NAME = "attachments";
   
   public static final String BITMAP_COLUMN_ID = "bitmap_id";
   public static final String BITMAP_COLUMN_BITMAP = "bitmap";
   public static final String BITMAP_COLUMN_ZOOM = "zoom";
   
   public static final String BOOK_COLUMN_ID = "book_id";
   public static final String BOOK_COLUMN_EXTERNAL_ID = "book_external_id";
   public static final String BOOK_COLUMN_COVER = "cover";
   public static final String BOOK_COLUMN_TITLE = "title";
   public static final String BOOK_COLUMN_PAGES = "number_of_pages";
   public static final String BOOK_COLUMN_BOOK = "book";
   
   public static final String BOOKMARK_COLUMN_ID = "bookmark_id";
   public static final String BOOKMARK_COLUMN_BOOKID = "book_id";
   public static final String BOOKMARK_COLUMN_PAGE = "page_number";
   public static final String BOOKMARK_COLUMN_DESC = "description";
   public static final String BOOKMARK_COLUMN_DATE = "date";
   
   public static final String PAGE_COLUMN_ID = "page_id";
   public static final String PAGE_COLUMN_EXTERNAL_ID = "page_external_id";
   public static final String PAGE_COLUMN_BOOKID = "book_id";
   public static final String PAGE_COLUMN_NUMB = "page_number";
   public static final String PAGE_COLUMN_PNG = "page_png";
   
   private static final String ATTACHMENT_COLUMN_ID = "attachment_id";
   private static final String ATTACHMENT_COLUMN_EXTERNAL_ID = "attachment_external_id";
   private static final String ATTACHMENT_COLUMN_PAGEID = "page_id";
   private static final String ATTACHMENT_COLUMN_NAME = "name";
   private static final String ATTACHMENT_COLUMN_ITSELF = "attachment";
  
    
   public static final String KEY_IMG = "image";
   
   private static final String DATABASE_CREATE = "create table " + BITMAP_TABLE_NAME 
		   + "("+ BITMAP_COLUMN_ID +" integer primary key autoincrement, " 
		   + BITMAP_COLUMN_BITMAP + " blob, "
		   + BITMAP_COLUMN_ZOOM + " double not null);";
   private static final String DATABASE_CREATE2 = 
		    "create table " + BOOK_TABLE_NAME 
		   + "(" + BOOK_COLUMN_ID + " integer primary key autoincrement, "
		   + BOOK_COLUMN_EXTERNAL_ID + " integer not null, "
		   + BOOK_COLUMN_COVER + " blob,"
		   + BOOK_COLUMN_TITLE + " text not null, "
		   + BOOK_COLUMN_PAGES + " ineger not null,"
		   + BOOK_COLUMN_BOOK + " blob );";
   private static final String DATABASE_CREATE3 = 
		    "create table " + BOOKMARK_TABLE_NAME 
		   + "(" + BOOKMARK_COLUMN_ID + " integer primary key autoincrement, "
		   + BOOKMARK_COLUMN_BOOKID + " integer not null, "
		   + BOOKMARK_COLUMN_PAGE + " integer not null, "
		   + BOOKMARK_COLUMN_DESC + " text not null, "
		   + BOOKMARK_COLUMN_DATE + " text not null );";
   private static final String DATABASE_CREATE4 = 
		    "create table " + PAGE_TABLE_NAME 
		   + "(" + PAGE_COLUMN_ID + " integer primary key autoincrement, "
		   + PAGE_COLUMN_EXTERNAL_ID + " integer not null, "
		   + PAGE_COLUMN_BOOKID + " integer not null, "
		   + PAGE_COLUMN_NUMB + " integer not null, "
		   + PAGE_COLUMN_PNG + " blob );";
   private static final String DATABASE_CREATE5 = 
		    "create table " + ATTACHMENT_TABLE_NAME
		   + "(" + ATTACHMENT_COLUMN_ID + " integer primary key autoincrement, "
		   + ATTACHMENT_COLUMN_EXTERNAL_ID + " integer not null, "
		   + ATTACHMENT_COLUMN_PAGEID + " integer not null, "
		   + ATTACHMENT_COLUMN_NAME + " string not null, "
		   //+ ATTACHMENT_COLUMN_LINK + "string not null, "
		   + ATTACHMENT_COLUMN_ITSELF + " blob );";
   			
   private final Context context;
   private OpenHelper OpenHelper;
   private SQLiteDatabase db; 
   private SQLiteStatement ins_bitmap_smt, ins_bookmark_stmt, ins_book_stmt,
   							ins_attachment_smt, ins_page_smt;
   
   private static final String UPDATE_COVER = "update "
		   + BOOK_TABLE_NAME + " set " 
		   + BOOK_COLUMN_COVER + " = ? "
		   + "where " + BOOK_COLUMN_EXTERNAL_ID + " = ?";
   
   private static final String UPDATE_ATTACHMENT = "update "
		   + ATTACHMENT_TABLE_NAME + " set " 
		   + ATTACHMENT_COLUMN_ITSELF + " = ? "
		   + "where " + ATTACHMENT_COLUMN_ID + " = ?";
   
   private static final String UPDATE_PAGE = "update "
		   + PAGE_TABLE_NAME + " set " 
		   + PAGE_COLUMN_PNG + " = ? "
		   + "where " + PAGE_COLUMN_ID + " = ?";
   
   private static final String UPDATE_BOOK = "update "
		   + BOOK_TABLE_NAME + " set " 
		   + BOOK_COLUMN_BOOK + " = ? "
		   + "where " + BOOK_COLUMN_EXTERNAL_ID + " = ?";
   
   
   private static final String DELETE_ATTACHMENT = "delete * from "
		   + ATTACHMENT_TABLE_NAME
		   + " where " + ATTACHMENT_COLUMN_ID + " = ?";
   
   private static final String DELETE_PAGE = "delete * from "
		   + PAGE_TABLE_NAME 
		   + " where " + PAGE_COLUMN_ID + " = ?";
   
   private static final String DELETE_BOOKMARK = "delete from "
		   + BOOKMARK_TABLE_NAME 
		   + " where " + BOOKMARK_COLUMN_PAGE + " = ? AND "
		    + BOOKMARK_COLUMN_BOOKID + " = ?";
   
   private static final String DELETE_BOOK = "delete * from "
		   + BOOK_TABLE_NAME 
		   + " where " + BOOK_COLUMN_EXTERNAL_ID + " = ?";
   	   
   private static final String INSERT_BITMAP = "insert into "
		   + BITMAP_TABLE_NAME + " ( "+ BITMAP_COLUMN_ID 
		   + " , " + BITMAP_COLUMN_BITMAP 
		   + " , " + BITMAP_COLUMN_ZOOM + " ) values (NULL, ?, ?)";
   
   private static final String INSERT_BOOKMARK = "insert into " 
		   + BOOKMARK_TABLE_NAME + " ( " + BOOKMARK_COLUMN_ID 
		   + " , " + BOOKMARK_COLUMN_BOOKID
		   + " , " + BOOKMARK_COLUMN_PAGE 
		   + " , " + BOOKMARK_COLUMN_DESC 
		   + " , " + BOOKMARK_COLUMN_DATE + " ) values (NULL, ?, ?, ?, ?)";
   
   public static final String INSERT_BOOK = "insert into " 
		   + BOOK_TABLE_NAME + " ( " + BOOK_COLUMN_ID 
		   + " , " + BOOK_COLUMN_EXTERNAL_ID 
		   + " , " + BOOK_COLUMN_COVER 
		   + " , " + BOOK_COLUMN_TITLE 
		   + " , " + BOOK_COLUMN_PAGES 
		   + " , " + BOOK_COLUMN_BOOK +" ) values (NULL, ?, NULL, ?, ?, NULL)";
   
   public static final String INSERT_PAGE = "insert into " 
		   + PAGE_TABLE_NAME + " ( " + PAGE_COLUMN_ID
		   + " , " + PAGE_COLUMN_EXTERNAL_ID 
		   + " , " + PAGE_COLUMN_BOOKID 
		   + " , " + PAGE_COLUMN_NUMB
		   + " , " + PAGE_COLUMN_PNG +" ) values (NULL, ?, ?, ?, NULL)";
   
   public static final String INSERT_ATTACHMENT = "insert into " 
		   + ATTACHMENT_TABLE_NAME + " ( " + ATTACHMENT_COLUMN_ID
		   + " , " + ATTACHMENT_COLUMN_PAGEID 
		   + " , " + ATTACHMENT_COLUMN_EXTERNAL_ID
		   + " , " + ATTACHMENT_COLUMN_NAME
		   + " , " + ATTACHMENT_COLUMN_ITSELF +" ) values (NULL, ?, ?, ?, NULL)";
 
   public DataHelper(Context context) {
	   this.context = context;
       OpenHelper openHelper = new OpenHelper(this.context);
       this.db = openHelper.getWritableDatabase();
       int version = db.getVersion();
       this.ins_bitmap_smt = this.db.compileStatement(INSERT_BITMAP);
       this.ins_book_stmt = this.db.compileStatement(INSERT_BOOK);
       this.ins_bookmark_stmt = this.db.compileStatement(INSERT_BOOKMARK);
       this.ins_page_smt = this.db.compileStatement(INSERT_PAGE);
       this.ins_attachment_smt = this.db.compileStatement(INSERT_ATTACHMENT);
   }
   
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
  // public long insert_book(BookDTO book, byte[] bMapArray){
   public long insert_book(BookDTO book){
	   this.ins_book_stmt.bindLong(1, book.getId());
	   this.ins_book_stmt.bindString(2, book.getName());
	   //this.ins_book_stmt.bindBlob(3, bMapArray);
	   //this.ins_book_stmt.bindBlob(3, null);
	   this.ins_book_stmt.bindLong(3, book.getPageCollection().size());
	  // this.ins_book_stmt.bindBlob(5, null);
	   long out = this.ins_book_stmt.executeInsert();	   
	   insert_page(book);
	   return out;
   }
   public long insert_page(BookDTO book){
	   int i = 0;
	   int book_id =this.getBookID(book);
	   long out = 0;
	   for (PageDTO page : book.getPageCollection()) {
		   this.ins_page_smt.bindLong(1, page.getId());
		   this.ins_page_smt.bindLong(2, book_id);
		   this.ins_page_smt.bindLong(3, i+1);
		   //this.ins_page_smt.bindBlob(3, null);
		   out = this.ins_page_smt.executeInsert();
		   i++;
		   insert_attachment(page);
	   }	   
	   return out;
   }
   public long insert_attachment(PageDTO page){
	      int page_id = this.getPageID(page);
		   for (AttachmentDTO attachment : page.getAttachmentCollection()){
			   this.ins_attachment_smt.bindLong(1, page_id);
			   this.ins_attachment_smt.bindLong(2, attachment.getId());
			   this.ins_attachment_smt.bindString(3, attachment.getName());
			 //  this.ins_attachment_smt.bindBlob(4, null);
			   this.ins_attachment_smt.executeInsert();
		   }
	   return 0;
   }
   
   public void update_cover(int id, byte[] bMapArray){
	   Object[] bindArgs = new Object[] {bMapArray,id };
	   try{
		   this.db.execSQL(UPDATE_COVER, bindArgs);
	   }catch(SQLException e){
		   Log.e("Error update", e.toString());
	   }
   }
   
   public void update_attachment(int id, byte[] bMapArray){
	   Object[] bindArgs = new Object[] {bMapArray,id};
	   try{
		  this.db.execSQL(UPDATE_ATTACHMENT, bindArgs);
	   }catch(SQLException e){
		   Log.e("Error update", e.toString());
	   }
   }
  
   public void update_page(int id, byte[] bMapArray){
	   Object[] bindArgs = new Object[] {bMapArray,id};
	   try{
		   this.db.execSQL(UPDATE_PAGE, bindArgs);
	   }catch(SQLException e){
		   Log.e("Error update", e.toString());
	   }
   }
   public void update_book(int id, byte[] bMapArray){
	   Object[] bindArgs = new Object[] {bMapArray,id};
	   try{
		   this.db.execSQL(UPDATE_BOOK, bindArgs);
		   //OpenHelper.getWritableDatabase().execSQL(UPDATE_BOOK, bindArgs);
	   }catch(SQLException e){
		   Log.e("Error update", e.toString());
	   }
   }
   
   
   public void delete_attachment(int id){
	   Object[] bindArgs = new Object[] {id};
	   try{
		  this.db.execSQL(DELETE_ATTACHMENT, bindArgs);
	   }catch(SQLException e){
		   Log.e("Error update", e.toString());
	   }
   }
   public void delete_bookmark(int page_id,int book_id){
	   Object[] bindArgs = new Object[] {page_id,book_id};
	   try{
		  this.db.execSQL(DELETE_BOOKMARK, bindArgs);
	   }catch(SQLException e){
		   Log.e("Error update", e.toString());
	   }
   }
  
   public void delete_page(int id){
	   Object[] bindArgs = new Object[] {id};
	   try{
		   this.db.execSQL(DELETE_PAGE, bindArgs);
	   }catch(SQLException e){
		   Log.e("Error update", e.toString());
	   }
   }
   public void delete_book(int id){
	   Object[] bindArgs = new Object[] {id};
	   try{
		   this.db.execSQL(DELETE_BOOK, bindArgs);
		   //OpenHelper.getWritableDatabase().execSQL(UPDATE_BOOK, bindArgs);
	   }catch(SQLException e){
		   Log.e("Error update", e.toString());
	   }
   }
   
   public void delete_book_deep(int id){
	  Book b = getBook(id);
	  if(b!=null)
	  {
		  List<Integer> pageids = getBookPageIDs(id);
		  for(Integer idpage:pageids)
		  {
			  delete_page(idpage);
			  List<AttachmentDTO> atts = getAttachments(idpage);
			  for(AttachmentDTO att:atts)
			  {
				  delete_attachment(att.getId());
			  }
		  }
		  delete_book(id);
	  }
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
	   }
	   if (c != null && !c.isClosed())
	         c.close();
	return bi;	   
   }
   
   public Bitmap getPage(int page_id){
		  
	   Bitmap bi = null;	   
	   Cursor c = this.db.rawQuery("SELECT "+PAGE_COLUMN_PNG+" FROM " +PAGE_TABLE_NAME + " WHERE " + PAGE_COLUMN_ID + " = "  + page_id + " ; ", null);
	   if (!c.isAfterLast()){
		   c.moveToFirst();
		   byte [] ar = c.getBlob(0);
		   if(ar != null)
		   {
			   ByteArrayInputStream inputStream = new ByteArrayInputStream(ar);
			   	bi = BitmapFactory.decodeStream(inputStream);   
		   }
	   }
	   if (c != null && !c.isClosed()) c.close();   
	return bi;	   
   }
   
   public Book getBook(int book_id){
		  
	   Book b = null;	   
	   Cursor c = this.db.rawQuery("SELECT * FROM " +BOOK_TABLE_NAME + " WHERE " + BOOK_COLUMN_ID + " = "  + book_id + " ; ", null);
	   if (!c.isAfterLast()){
		   c.moveToFirst();
		   b = new Book(c.getInt(0), c.getInt(1), c.getBlob(2), c.getString(3), c.getInt(4), c.getBlob(5)); 
	   }
	   if (c != null && !c.isClosed()) c.close();   
	return b;	   
   }
   
   public int getBookID(BookDTO b){
	   int id = 0;
	   Cursor c = this.db.rawQuery(" SELECT " + BOOK_COLUMN_ID + " FROM " + BOOK_TABLE_NAME + 
			   " WHERE " + BOOK_COLUMN_EXTERNAL_ID + " = "  + b.getId() + " ; ", null);
	   if (!c.isAfterLast()){
		   c.moveToFirst();
		   id = c.getInt(0);
	   }
	   c.close();
	   return id;
   }
   
   public int getBookExtID(int id_local){
	   int id = 0;
	   Cursor c = this.db.rawQuery(" SELECT " + BOOK_COLUMN_EXTERNAL_ID + " FROM " + BOOK_TABLE_NAME + 
			   " WHERE " + BOOK_COLUMN_ID + " = "  +id_local + " ; ", null);
	   c.moveToFirst();
	   if (!c.isAfterLast()){
		   //c.moveToFirst();
		   id = c.getInt(0);
	   }
	   c.close();
	   return id;
   }
   
   public byte[] getBookContents(int id){
	   byte [] arr = null;
	   Cursor c = this.db.rawQuery(" SELECT " + BOOK_COLUMN_BOOK + " FROM " + BOOK_TABLE_NAME + 
			   " WHERE " + BOOK_COLUMN_ID + " = "  + id + " ; ", null);
	   c.moveToFirst();
	   if (!c.isAfterLast()){
		   //c.moveToFirst();
		   arr = c.getBlob(0);
	   }
	   c.close();
	   return arr;
   }
   public byte[] getAttacmentContents(int id){
	   byte [] arr = null;
	   Cursor c = this.db.rawQuery(" SELECT " + ATTACHMENT_COLUMN_ITSELF + " FROM " + ATTACHMENT_TABLE_NAME + 
			   " WHERE " + ATTACHMENT_COLUMN_ID + " = "  + id + " ; ", null);
	   if (!c.isAfterLast()){
		   c.moveToFirst();
		   arr = c.getBlob(0);
	   }
	   c.close();
	   return arr;
   }
   public int getPageID(PageDTO page){
	   int id = 0;
	   Cursor c = this.db.rawQuery(" SELECT " + PAGE_COLUMN_ID + " FROM " + PAGE_TABLE_NAME + 
			   " WHERE " + PAGE_COLUMN_EXTERNAL_ID + " = "  + page.getId() + " ; ", null);
	   if (!c.isAfterLast()){
		   c.moveToFirst();
		   id = c.getInt(0);
	   }
	   c.close();
	   return id;
   }
   public int getAttachmentServerID(int local_id){
	   int id = 0;
	   Cursor c = this.db.rawQuery(" SELECT " + ATTACHMENT_COLUMN_EXTERNAL_ID + " FROM " + ATTACHMENT_TABLE_NAME + 
			   " WHERE " + ATTACHMENT_COLUMN_ID + " = "  + local_id + " ; ", null);
	   if (!c.isAfterLast()){
		   c.moveToFirst();
		   id = c.getInt(0);
	   }
	   c.close();
	   return id;
   }
   public List<Integer> getBookPageIDs(int book_id){
	   List<Integer> lst = new ArrayList<Integer>();
	   int id = 0;
	   Cursor c = this.db.rawQuery(" SELECT " + PAGE_COLUMN_ID + " FROM " + PAGE_TABLE_NAME + 
			   " WHERE " + PAGE_COLUMN_BOOKID + " = "  + book_id + " ; ", null);
	   c.moveToFirst();
	   while (!c.isAfterLast()){
		   id = c.getInt(0);
		   c.moveToNext();	   
		   lst.add(id);
	   }
	   c.close();
	   return lst;
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
	   Cursor c = this.db.rawQuery("SELECT * FROM " + BOOKMARK_TABLE_NAME + " WHERE "+BOOK_COLUMN_ID+" = " 
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
   public ArrayList<AttachmentDTO> getAttachments(int page_id){
	   ArrayList<AttachmentDTO> arr = new ArrayList<AttachmentDTO>();
	   Cursor c = this.db.rawQuery("SELECT " +ATTACHMENT_COLUMN_ID+ ", "+ATTACHMENT_COLUMN_NAME+" FROM " + ATTACHMENT_TABLE_NAME + " WHERE "+ATTACHMENT_COLUMN_PAGEID+" = " + page_id+" ;", null);
	   c.moveToFirst();
	   while(!c.isAfterLast()){
		   AttachmentDTO dot = new AttachmentDTO(c.getInt(0),c.getString(1));
		   arr.add(dot);
		   c.moveToNext();	   
	   }
	   c.close();  
	   return arr;
   }
   public Book getBook(Bookmark b){
	   Book book = null;
	   Cursor c = this.db.rawQuery(" SELECT * FROM " + BOOK_TABLE_NAME + " WHERE "+BOOK_COLUMN_ID+" = "
			   + b.getId_book() + " ; ", null);
	   if (!c.isAfterLast()){
		   c.moveToFirst();
		   book = new Book(c.getInt(0), c.getInt(1), c.getBlob(2), c.getString(3), c.getInt(4), c.getBlob(5));
	   }
	   c.close();
	   return book;
   }
   
   public ArrayList<Book> getAllBooks(){
	   ArrayList<Book> books = new ArrayList<Book>();
	   Cursor c = this.db.rawQuery("SELECT * FROM " + BOOK_TABLE_NAME + ";", null);
	   c.moveToFirst();
	   while(!c.isAfterLast()){
		   Book b = new Book(c.getInt(0), c.getInt(1), c.getBlob(2), c.getString(3), c.getInt(4), c.getBlob(5));
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
	
	  	  
	   }
	   if (c != null && !c.isClosed())
	         c.close();
	return zoom;
	   
   }
 
   public void deleteAll() {
      this.db.delete(BITMAP_TABLE_NAME, null, null);
      this.db.delete(BOOK_TABLE_NAME, null, null);
      this.db.delete(BOOKMARK_TABLE_NAME, null, null);
      this.db.delete(PAGE_TABLE_NAME, null, null);
      this.db.delete(ATTACHMENT_TABLE_NAME, null, null);
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
	   private final Context context;
      OpenHelper(Context context) {
         super(context, DATABASE_NAME, null, DATABASE_VERSION);
         this.context = context;
      }
 
      @Override
      public void onCreate(SQLiteDatabase db) {
    	  db.execSQL(DATABASE_CREATE);
    	  db.execSQL(DATABASE_CREATE2);
    	  db.execSQL(DATABASE_CREATE3); 
    	  db.execSQL(DATABASE_CREATE4);
    	  db.execSQL(DATABASE_CREATE5); 
    	  //int d = 2;
      }
 
      @Override
      public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         Log.w("Example", "Upgrading database, this will drop tables and recreate.");
         db.execSQL("DROP TABLE IF EXISTS " + BITMAP_TABLE_NAME);
         db.execSQL("DROP TABLE IF EXISTS " + BOOK_TABLE_NAME);
         db.execSQL("DROP TABLE IF EXISTS " + BOOKMARK_TABLE_NAME);
         db.execSQL("DROP TABLE IF EXISTS " + PAGE_TABLE_NAME);
         db.execSQL("DROP TABLE IF EXISTS " + ATTACHMENT_TABLE_NAME);
         onCreate(db);
      }
   }
}