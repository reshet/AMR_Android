package com.mplatforma.amr;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;

import com.mplatforma.amr.server.ServerConnector;

import db.DataHelper;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.widget.MediaController;
import android.widget.VideoView;

public class VideoPlayer extends Activity {
  private VideoView video;
  private MediaController ctlr;
  DataHelper dh;
  @Override
  public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    getWindow().setFormat(PixelFormat.TRANSLUCENT);
    setContentView(R.layout.main_video);
    dh = new DataHelper(this);
     //ATT_ID is server-side id.
    int att_id = getIntent().getIntExtra("ATT_ID",-1);
    int att_server_id = dh.getAttachmentServerID(att_id);
    if(att_id!=-1)
    {
    	//byte[] arr = dh.getAttacmentContents(att_id);
    	File f = new File(ServerConnector.base_atts_path,String.valueOf(att_id)+".mp4");
    	if(!f.exists())
    	{
    		ServerConnector c = new ServerConnector(dh);
    		c.serverGetAttachment(att_id,att_server_id);
    	    //arr = dh.getAttacmentContents(att_id);
        }
    	
    	//File f = new File(ServerConnector.base_atts_path,String.valueOf(att_id)+".mp4");
    	
    	
    		  //  File clip=new File(Environment.getExternalStorageDirectory(),"Himera.mp4");
    		   // File clip=new File(Environment.getExternalStorageDirectory(),"IronMan2.flv");
    		    
    		    if (f.exists()) {
    		      video=(VideoView)findViewById(R.id.video);
    		      //Uri.
    		      //Uri uri=Uri.parse("http://192.168.112.108:8080/AMR_Facade/downloadAttachment?id=1");
    		      video.setVideoPath(f.getAbsolutePath());
    		     // video.setVideoURI(uri);
    		      
    		      ctlr=new MediaController(this);
    		      ctlr.setMediaPlayer(video);
    		      video.setMediaController(ctlr);
    		      video.requestFocus();
    		      video.start();
    		    }
    }
  }
}