package com.mplatforma.amr;  
  
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
  
public class CopyOfMySimpleArrayAdapter extends ArrayAdapter<String> {  
    private final Activity context;  
    private final String[] names;  
  
    public CopyOfMySimpleArrayAdapter(Activity context, String[] names) {  
        super(context, R.layout.rowlayout, names);  
        this.context = context;  
        this.names = names;  
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
        textView.setText(names[position]);  
        Button button =  (Button) rowView.findViewById(R.id.BtnToClick);
        button.setOnClickListener(BookmarksListener);
        
       
        // Change the icon for Windows and iPhone  
        String s = names[position];  
        
        if(s.equals("Господарський процесуальний кодекс України")) 
            imageView.setImageResource(R.drawable.book1);
            else if(s.equals("Цивільне процесуальне право України"))
            imageView.setImageResource(R.drawable.book2); 
            else if(s.equals("Urbis et Vicus"))
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