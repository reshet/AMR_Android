package com.mplatforma.amr;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

public class ShelfActivity extends TabActivity implements TabHost.TabContentFactory {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  setContentView(R.layout.shelf_main);
        final TabHost tabHost = getTabHost();
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Books").setContent(new Intent(this,ShelfBooksActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Bookmarks").setContent(new Intent(this,ShelfBookmarksActivity.class)));
        tabHost.addTab(tabHost.newTabSpec("tab3").setIndicator("Account").setContent(new Intent(this,AccountActivity.class)));
        // Drawable dr = getResources().getDrawable(R.drawable.shelf);
       // dr.get
       // BitmapDrawable bdr = new BitmapDrawable();
      //  this.setWallpaper(.getBitmap());
    }

	@Override
	public View createTabContent(String tag) {
		final TextView tv = new TextView(this);
		tv.setText("Content for tab "+tag);
		return tv;
	}
}