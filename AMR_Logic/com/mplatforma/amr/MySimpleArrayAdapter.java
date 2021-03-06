package com.mplatforma.amr;  
  
import java.util.ArrayList;

import android.app.Activity;  
import android.graphics.Color;
import android.view.LayoutInflater;  
import android.view.View;  
import android.view.ViewGroup;  
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;  
import android.widget.Button;
import android.widget.ImageView;  
import android.widget.TextView;  
  
public class MySimpleArrayAdapter extends ArrayAdapter<String> {  
    private final Activity context;
    private final String[] names; 
    //private final ArrayList<String> names;  
    //private final ArrayList images;
  
    //public MySimpleArrayAdapter(Activity context, ArrayList<String> names) { 
    public MySimpleArrayAdapter(Activity context, String[] names) { 
    	super(context, R.layout.rowlayout, names);  
        this.context = context;  
        this.names = names;  
        //this.images = images;
    }  
    OnClickListener BookmarksListener = new OnClickListener() {
        public void onClick(View v) {
            v.setBackgroundColor(Color.GREEN);
        }
    };
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        LayoutInflater inflater = context.getLayoutInflater();  
        View rowView = inflater.inflate(R.layout.rowlayout, null, true);  
        
        TextView textView = (TextView) rowView.findViewById(R.id.label);  
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);  
        //textView.setText(names.get(position));  
        textView.setText(names[position]); 
        Button button =  (Button) rowView.findViewById(R.id.BtnToClick);
        button.setOnClickListener(BookmarksListener);
        
        //String s = names.get(position);
        String s = names[position];
        
        if(s.equals("book1")) 
            imageView.setImageResource(R.drawable.book1);
            else if(s.equals("book2"))
            imageView.setImageResource(R.drawable.book2); 
            else if(s.equals("book3"))
            imageView.setImageResource(R.drawable.book3); 
            else if(s.equals("Естетика Лесі Українки"))
            imageView.setImageResource(R.drawable.book4); 
            else if(s.equals("Вибрані праці з категорії граматики та лінгвотекстології"))
            imageView.setImageResource(R.drawable.book5); 
            else
            imageView.setImageResource(R.drawable.ic_launcher);
  
        return rowView;  
    }  
}  