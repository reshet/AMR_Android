package com.mplatforma.amr.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
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
import android.os.Environment;
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
   // private String base_url = "http://192.168.114.102:8080/AMR_Facade/";
    //private String base_url = "http://10.0.128.158:8080/AMR_Facade/";
    private String base_url = "http://mplatforma.com:8080/AMR_Facade/";
     public static String base_atts_path = Environment.getExternalStorageDirectory()+"/amr_attachs/";
    //private String base_url = "http://109.251.134.144:8080/AMR_Facade/";
	public void serverGetBook(final Handler mHandler,final int book_id)
    {
	
    	new Thread()
    	{
    		 final static int DONE = 0;
             final static int RUNNING = 1;
             
            
             int mState;
             int total;
             boolean done = false;
             @Override
             public void run() {
                 mState = RUNNING;   
                // total = maxBarValue;
                 
                 
                     
                         // Control speed of update (but precision of delay not guaranteed)
                        // Thread.sleep(100);
                         if(!done)
                         {
                        	String url = base_url+"downloadBinary";
                        	DefaultHttpClient cl = new DefaultHttpClient();
                        	if(!url.endsWith("?"))
                     	        url += "?";

                     	    List<NameValuePair> params = new LinkedList<NameValuePair>();

                     	    if (book_id != 0){
                     	        params.add(new BasicNameValuePair("id", String.valueOf(book_id)));
                     	      //  params.add(new BasicNameValuePair("type", "pdf"));
                     	        params.add(new BasicNameValuePair("type", "zip"));
                     	    }	
                     	  
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
                     				
                     				long total_length = paramHttpResponse.getEntity().getContentLength(); 
                     				long loaded_length = 0;
                     				//respStream.r
                     				ByteArrayOutputStream str = new ByteArrayOutputStream();
                					byte [] buff = new byte[120000];
//                					for (int c = jFile.read(); c != -1; c = jFile.read()) { 
//                			            str.write(c); 
//                			          } 
                					for (int c = respStream.read(buff); c > -1; c = respStream.read(buff)) { 
                			            str.write(buff,0,c); 
                			            loaded_length+=c;
                			            
                			            int loaded_percent = (int) (((double)loaded_length/total_length)*100);
                			            
                			            Message msg = mHandler.obtainMessage();
                	                     Bundle b = new Bundle();
                	                     b.putInt("loaded", loaded_percent);
                	                     msg.setData(b);
                	                     mHandler.sendMessage(msg);
                			          } 
                					
                					
                					
                					byte [] arr = str.toByteArray();
                     				
                     				//byte [] arr = IOUtils.toByteArray(respStream);
                     				
                     				
                     				
                     				//if (dh.)
                     				//dh.insert_book(new BookDTO(1,"Book from inet)",new ArrayList<PageDTO>()));
                     				new ZipDecopmosed().unpack(book_id, dh, arr);
                     				//dh.update_book(book_id, arr);
//                     				Message m = page_hdlr.obtainMessage();
//                     				Bundle b=   new Bundle();
//                     				b.putString("value", "downloaded");
//                     				m.setData(b);
//                     				page_hdlr.sendMessage(m);
//                     				done = true;
                     				return null;
                     			}
                     		};
                     		try {
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
                    	
                     
                     // Send message (with current value of  total as data) to Handler on UI thread
                     // so that it can update the progress bar.
                     
                   
                     
                     //total--;    // Count down
               }
             
             
             // Set current state of thread (use state=ProgressThread.DONE to stop thread)
             public void setState(int state) {
                 mState = state;
             }
    	}.start();
        	// The URL for making the GET request
    	
    }
	
	public void serverGetAttachment(final int att_id,final int server_att_id)
    {
    	
    	// Create a new RestTemplate instance
    	RestTemplate restTemplate = new RestTemplate();

    	// The URL for making the GET request
    	String url = base_url+"downloadAttachment";
    	DefaultHttpClient cl = new DefaultHttpClient();
		if(!url.endsWith("?"))
	        url += "?";

	    List<NameValuePair> params = new LinkedList<NameValuePair>();

	    if (att_id != 0){
	        params.add(new BasicNameValuePair("id", String.valueOf(server_att_id)));
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
				
				
				File ff = new File(base_atts_path);
				ff.mkdirs();
				File f = new File(base_atts_path,String.valueOf(att_id)+".mp4");
				if(!f.exists())
		    		try {
		    			f.createNewFile();
		    			//f.
		    			FileOutputStream fos = new FileOutputStream(f);
						try{
					     fos.write(arr);
			             fos.flush();
		    			}
		    	         finally{
		    	              fos.close();
		    	         } 
			    	}catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				//dh.update_attachment(att_id, arr);
				
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
	
	
	 private class ProgressLoadPDFThread extends Thread {	
         
         // Class constants defining state of the thread
         final static int DONE = 0;
         final static int RUNNING = 1;
         
         Handler mHandler;
         int mState;
         int total;
         HttpGet getMethod;
         ResponseHandler<String> resp_h;
     
         // Constructor with an argument that specifies Handler on main thread
         // to which messages will be sent by this thread.
         
         ProgressLoadPDFThread(Handler h,HttpGet getMethod,ResponseHandler<String> resp_h) {
             mHandler = h;
             this.getMethod = getMethod;
             this.resp_h = resp_h;
         }
         
         // Override the run() method that will be invoked automatically when 
         // the Thread starts.  Do the work required to update the progress bar on this
         // thread but send a message to the Handler on the main UI thread to actually
         // change the visual representation of the progress. In this example we count
         // the index total down to zero, so the horizontal progress bar will start full and
         // count down.
         
         @Override
         public void run() {
             mState = RUNNING;   
            // total = maxBarValue;
             boolean done = false;
             while (mState == RUNNING) {
                 // The method Thread.sleep throws an InterruptedException if Thread.interrupt() 
                 // were to be issued while thread is sleeping; the exception must be caught.
                 try {
                     // Control speed of update (but precision of delay not guaranteed)
                     Thread.sleep(100);
                     if(!done)
                     {
                    	 
                     }
                	 
                 } catch (InterruptedException e) {
                     Log.e("ERROR", "Thread was Interrupted");
                 }
                 
                 // Send message (with current value of  total as data) to Handler on UI thread
                 // so that it can update the progress bar.
                 
                 Message msg = mHandler.obtainMessage();
                 Bundle b = new Bundle();
                 b.putInt("total", total);
                 msg.setData(b);
                 mHandler.sendMessage(msg);
                 
                 total--;    // Count down
             }
         }
         
         // Set current state of thread (use state=ProgressThread.DONE to stop thread)
         public void setState(int state) {
             mState = state;
         }
     }
}
