package com.mplatforma.amr.server;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.web.client.RestTemplate;

import com.mplatforma.amr.R;

import db.DataHelper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

public class ServerConnector {
	
	private DataHelper dh;
	public ServerConnector(DataHelper dh)
	{
		this.dh = dh;
	}
	final Handler page_hdlr = new Handler()
    {
    	public void handleMessage(Message m)
    	{
    		String randvalue = m.getData().getString("value");
    		
    		//TextView ed =(TextView)findViewById(R.id.txt_view);
    		//ed.setText(randvalue);
    		//Log.d("catdebug.log","value: "+randvalue);
    	}
    };
    private String base_url = "http://192.168.114.102:8080/AMR_Facade/";
    //private String base_url = "http://109.251.134.144:8080/AMR_Facade/";
	public void serverGetBook(final int book_id)
    {
    	
    	// Create a new RestTemplate instance
    	RestTemplate restTemplate = new RestTemplate();

    	// The URL for making the GET request
    	String url = base_url+"downloadBinary";
    	//String url = "http://mplatforma.com:8080/AMR_Facade/resources/entity.customer/login";

    	// Instantiate the HTTP GET request, expecting an array of 
    	// Product objects in response
    	//Product[] products = restTemplate.getForObject(url, Product[].class);
    	
    	
    	DefaultHttpClient cl = new DefaultHttpClient();
		//HttpGet getMethod = new HttpGet("http://178.63.43.214:3010/get_true_random");
		if(!url.endsWith("?"))
	        url += "?";

	    List<NameValuePair> params = new LinkedList<NameValuePair>();

	    if (book_id != 0){
	        params.add(new BasicNameValuePair("id", String.valueOf(book_id)));
	      //  params.add(new BasicNameValuePair("type", "pdf"));
	        params.add(new BasicNameValuePair("type", "zip"));
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
				
				
				byte [] arr = IOUtils.toByteArray(respStream);
				//if (dh.)
				//dh.insert_book(new BookDTO(1,"Book from inet)",new ArrayList<PageDTO>()));
				new ZipDecopmosed().unpack(book_id, dh, arr);
				//dh.update_book(book_id, arr);
				Message m = page_hdlr.obtainMessage();
				Bundle b=   new Bundle();
				b.putString("value", "downloaded");
				m.setData(b);
				page_hdlr.sendMessage(m);
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
