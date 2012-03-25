package com.mplatforma.amr;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.DefaultedHttpParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransport;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mplatforma.amr.server.BookDTO;
import com.mplatforma.amr.server.PageDTO;

import db.Book;
import db.DataHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

//HELLO TANYA
public class AccountActivity extends Activity {
    /** Called when the activity is first created. */
	DataHelper dh;
	
	private OnClickListener auth_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//serverAuthSpring("kostya.personal@gmail.com","kassiopeya");
			serverGetBookList("kostya.personal@gmail.com","kassiopeya");
			//serverGetPageBroot(100,3);
			//serverAuthSpring(username, password)
		}
	};
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.account);
        Button b = (Button)findViewById(R.id.button_auth);
        b.setOnClickListener((android.view.View.OnClickListener) auth_listener);
        dh = new DataHelper(this);
        // checkPermission("com.mplatforma.amr", "http://localhost:8080/", Intent.FLAG_GRANT_READ_URI_PERMISSION);
        // Drawable dr = getResources().getDrawable(R.drawable.shelf);
       // dr.get
       // BitmapDrawable bdr = new BitmapDrawable();
      //  this.setWallpaper(.getBitmap());
    }
    
   
    final Handler hdlr = new Handler()
    {
    	public void handleMessage(Message m)
    	{
    		String randvalue = m.getData().getString("value");
    		
    		TextView ed =(TextView)findViewById(R.id.txt_view);
    		ed.setText(randvalue);
    		Log.d("catdebug.log","value: "+randvalue);
    	}
    };
    final Handler bl_hdlr = new Handler()
    {
    	public void handleMessage(Message m)
    	{
    		String randvalue = m.getData().getString("value");
    		
    		Gson gson = new Gson();
    		JSONArray arr = null;
       		TextView ed =(TextView)findViewById(R.id.txt_view);
    		ed.setText(randvalue);
    		Log.d("catdebug.log","value: "+randvalue);
			try {
				arr = new JSONArray(randvalue);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    		List<BookDTO> books = new ArrayList<BookDTO>();
    		for(int i = 0; i < arr.length();i++)
    		{
    			BookDTO b;
				try {
					b = gson.fromJson((String) arr.get(i).toString(), BookDTO.class);
					books.add(b);
				} catch (JsonSyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
    			
    		}
    		int bb = 2;
    		bb++;
    		//List<Book> books = gson.fromJson(randvalue, List.class);
    		FillDatabase(books);
    	}
    };
    
    private void FillDatabase(List<BookDTO> books)
    {
    	ArrayList<Book> local_books = dh.getAllBooks();
    	ArrayList<Integer> ext_local_ids = new ArrayList<Integer>();
    	ArrayList<Integer> ext_remote_ids = new ArrayList<Integer>();
    	for(Book b:local_books)
    	{
    		ext_local_ids.add(b.getExid());
    	}
    	for(BookDTO book:books)
    	{
    		ext_remote_ids.add(book.getId());
    	}
    	
    	int i = 0;
    	for(Book b:local_books)
    	{
    		if(!ext_remote_ids.contains(b.getExid()))
    		{
    			//DELETE BOOK AND ALL CACHES AND ALL ATTS.
    			dh.delete_book_deep(b.getId());
    		}
    		i++;
    	}
    	i=0;
    	for(BookDTO book:books)
    	{
    		if(!ext_local_ids.contains(book.getId()))
    		{
    			//ADD LOCALLY BOOK META.
    			dh.insert_book(book);
    		}
    	}
    	
    	
    
    	
    }
    
    
    private void serverAuthSpring(String username,String password)
    {
    	
    	// Create a new RestTemplate instance
    	RestTemplate restTemplate = new RestTemplate();

    	// The URL for making the GET request
    	String url = "http://192.168.112.108:8080/AMR_Facade/resources/entity.customer/login";
    	//String url = "http://mplatforma.com:8080/AMR_Facade/resources/entity.customer/login";

    	// Instantiate the HTTP GET request, expecting an array of 
    	// Product objects in response
    	//Product[] products = restTemplate.getForObject(url, Product[].class);
    	
    	
    	DefaultHttpClient cl = new DefaultHttpClient();
		//HttpGet getMethod = new HttpGet("http://178.63.43.214:3010/get_true_random");
		if(!url.endsWith("?"))
	        url += "?";

	    List<NameValuePair> params = new LinkedList<NameValuePair>();

	    if (username != null && password != null){
	        params.add(new BasicNameValuePair("username", username));
	        params.add(new BasicNameValuePair("password", password));
	    }
	   
	    //params.add(new BasicNameValuePair("user", agent.uniqueId));

	    String paramString = URLEncodedUtils.format(params, "utf-8");

	    url += paramString;
	    
	    HttpGet getMethod = new HttpGet(url);
		//getMethod.setParams()
		final ResponseHandler<String> reps_hdlr = new ResponseHandler<String>() {
			@Override
			public String handleResponse(HttpResponse paramHttpResponse)
					throws ClientProtocolException, IOException {
				String respMsg="";
				Log.d("catdebug.log","gor resp: "+paramHttpResponse.getStatusLine().toString());
				InputStream respStream = paramHttpResponse.getEntity().getContent();
				Scanner sc = new Scanner(respStream);
				while(sc.hasNextLine())
					respMsg += sc.nextLine();
				//Log.d("catdebug.log","resp value: "+respMsg);
				//Message m = hdlr.obtainMessage();
				Message m = hdlr.obtainMessage();
				Bundle b=   new Bundle();
				b.putString("value", respMsg);
				m.setData(b);
				hdlr.sendMessage(m);
				return null;
			}
		};
		try {
			//for(int i = 0; i < 100;i++)
				cl.execute(getMethod,reps_hdlr);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String response="";
    }
    
    
    private void serverGetBookList(String username,String password)
    {
    	
    	// The URL for making the GET request
    	String url = "http://192.168.114.102:8080/AMR_Facade/resources/entity.customer/booklist";
    	
    	DefaultHttpClient cl = new DefaultHttpClient();
		if(!url.endsWith("?"))
	        url += "?";
	    List<NameValuePair> params = new LinkedList<NameValuePair>();
	    if (username != null && password != null){
	        params.add(new BasicNameValuePair("username", username));
	        params.add(new BasicNameValuePair("password", password));
	    }
	   
	    //params.add(new BasicNameValuePair("user", agent.uniqueId));

	    String paramString = URLEncodedUtils.format(params, "utf-8");

	    url += paramString;
	    
	    HttpGet getMethod = new HttpGet(url);
		//getMethod.setParams()
		final ResponseHandler<String> reps_hdlr = new ResponseHandler<String>() {
			@Override
			public String handleResponse(HttpResponse paramHttpResponse)
					throws ClientProtocolException, IOException {
				String respMsg="";
				Log.d("catdebug.log","gor resp: "+paramHttpResponse.getStatusLine().toString());
				InputStream respStream = paramHttpResponse.getEntity().getContent();
				Scanner sc = new Scanner(respStream);
				while(sc.hasNextLine())
					respMsg += sc.nextLine();
				//Log.d("catdebug.log","resp value: "+respMsg);
				//Message m = hdlr.obtainMessage();
				Message m = bl_hdlr.obtainMessage();
				Bundle b=   new Bundle();
				b.putString("value", respMsg);
				m.setData(b);
				bl_hdlr.sendMessage(m);
				return null;
			}
		};
		try {
			//for(int i = 0; i < 100;i++)
				cl.execute(getMethod,reps_hdlr);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String response="";
    }
    
    
}