package com.mplatforma.amr;

import java.util.Random;

import net.sf.andpdf.pdfviewer.PdfFileSelectActivity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;

public class AMRActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_amr);
        Button btn1 = (Button)findViewById(R.id.button11);
        btn1.setOnClickListener(btn_shelf_listener);
        //btn1.setImageResource(R.drawable.button_shelf);
        Button btn2 = (Button)findViewById(R.id.button12);
        btn2.setOnClickListener(btn_local_listener);
        
//        Button btn3 = (Button)findViewById(R.id.button3);
//        btn3.setOnClickListener(btn_cat_listener);
        //   Drawable dr = getResources().getDrawable((R.drawable.button_shelf));
     
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case 1:
	       return new AlertDialog.Builder(this)
	            .setIcon(R.drawable.icon)
	            .setTitle("Cat ALIVE!")
	            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	            		
	                }
	            })
	            .create(); 
        case 2:
        	 return new AlertDialog.Builder(this)
	            .setIcon(R.drawable.icon)
	            .setTitle("Cat DEAD!")
	            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
	                public void onClick(DialogInterface dialog, int whichButton) {
	            		
	                }
	            })
	            .create(); 
        }        
        return null;
    }
    private OnClickListener btn_shelf_listener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(AMRActivity.this,ShelfActivity.class);
			startActivity(intent);
		}
	};
	 private OnClickListener btn_local_listener = new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(AMRActivity.this,PdfFileSelectActivity.class);
				startActivity(intent);
			}
		};
		
	 private OnClickListener btn_cat_listener = new OnClickListener() {
			private Random ra = new Random();
		 	@Override
			public void onClick(View v) {
		 		double rand = ra.nextDouble();
		 		if(rand > 0.5)
		 		{
		 			showDialog(1);
		 		}else
		 		{
		 			showDialog(2);
		 		}
			}
		};	
}