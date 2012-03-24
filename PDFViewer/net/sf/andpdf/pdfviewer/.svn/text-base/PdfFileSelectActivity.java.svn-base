package net.sf.andpdf.pdfviewer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.mplatforma.amr.R;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

/**
 * U:\Android\android-sdk-windows-1.5_r1\tools\adb push u:\Android\simple_T.pdf /data/test.pdf
 * @author ferenc.hechler
 */
public class PdfFileSelectActivity extends ListActivity {

	private static final String TAG = "PDFVIEWER";
	
	public final static String PREFS_NAME = "PDFViewerPrefs";
	public static final String PREFS_PDFFILENAME = "pdffilename";
	public static final String PREFS_USEFONTSUBSTITUTION = "usefontsubstitution";
	public static final String PREFS_KEEPCACHES = "keepcaches";
	public final static String DEFAULTPDFFILENAME = "/mnt/sdcard/Robinson_Crusoe_BT.pdf";
	public static final boolean DEFAULTSHOWIMAGES = true;
	public static final boolean DEFAULTANTIALIAS = true;
	public static final boolean DEFAULTUSEFONTSUBSTITUTION = false;
	public static final boolean DEFAULTKEEPCACHES = false;
	
    public static final String EXTRA_PDFFILENAME = "net.sf.andpdf.extra.PDFFILENAME";
    public static final String EXTRA_SHOWIMAGES = "net.sf.andpdf.extra.SHOWIMAGES";
    public static final String EXTRA_ANTIALIAS = "net.sf.andpdf.extra.ANTIALIAS";
    public static final String EXTRA_USEFONTSUBSTITUTION = "net.sf.andpdf.extra.USEFONTSUBSTITUTION";
    public static final String EXTRA_KEEPCACHES = "net.sf.andpdf.extra.KEEPCACHES";
	
	private EditText mFilename;
	//private EditText mOutput;
	//private CheckBox mUseFontSubstitution;
	//private CheckBox mKeepCaches;
	private Button mBrowse;
	private Button mShow;
	private Button mExit;

	private SimplePersistence persist; 
	
	//Add by Bob
	public static final int BROSWER_ID = Menu.FIRST;
	
	private TextView mPath;
	private String rootPath = "/";
	
	private List<String> items = null;
	private List<String> paths = null;

	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.pdf_file_select);
        
        mFilename = (EditText) findViewById(R.id.filename);
        //mOutput = (EditText) findViewById(R.id.output);
        //mUseFontSubstitution = (CheckBox) findViewById(R.id.cbUseFontSubstitution);
        //mKeepCaches = (CheckBox) findViewById(R.id.cbKeepCaches);
       	mBrowse = (Button) findViewById(R.id.btBrowse);
       	mShow = (Button) findViewById(R.id.btShow);
    	mExit = (Button) findViewById(R.id.btExit);
 
        mBrowse.setOnClickListener(BrowseFileListener);
        mShow.setOnClickListener(ShowPdfListener);
        mExit.setOnClickListener(ExitListener);

        // load persisted values
    	persist = new SimplePersistence(this, PREFS_NAME);
    	String pdffilename = persist.getString(PREFS_PDFFILENAME, DEFAULTPDFFILENAME);
    	boolean useFontSubstitution = persist.getBoolean(PREFS_USEFONTSUBSTITUTION, DEFAULTUSEFONTSUBSTITUTION);
    	boolean keepCaches = persist.getBoolean(PREFS_KEEPCACHES, DEFAULTKEEPCACHES);
        mFilename.setText(pdffilename);
      //  mUseFontSubstitution.setChecked(useFontSubstitution);        
      //  mKeepCaches.setChecked(keepCaches);        

    }
    protected void setFileSelectView(int layoutResId){
		mFilename = (EditText) findViewById(R.id.filename);
      //  mOutput = (EditText) findViewById(R.id.output);
       // mUseFontSubstitution = (CheckBox) findViewById(R.id.cbUseFontSubstitution);
       // mKeepCaches = (CheckBox) findViewById(R.id.cbKeepCaches);
    	mBrowse = (Button) findViewById(R.id.btBrowse);
    	mShow = (Button) findViewById(R.id.btShow);
    	mExit = (Button) findViewById(R.id.btExit);

        mBrowse.setOnClickListener(BrowseFileListener);
        mShow.setOnClickListener(ShowPdfListener);
        mExit.setOnClickListener(ExitListener);
	}
    
    @Override
    protected void onStop() {
    	super.onStop();
    	persistValues();
    }

	private void persistValues() {
    	String pdffilename = mFilename.getText().toString();
    	boolean useFontSubstitution = false;
    	boolean keepCaches = true;
    	persist.putString(PREFS_PDFFILENAME, pdffilename);
    	persist.putBoolean(PREFS_USEFONTSUBSTITUTION, useFontSubstitution);
    	persist.putBoolean(PREFS_KEEPCACHES, keepCaches);
    	persist.commit();
	}

    private void showText(String text) {
    	Log.i(TAG, text);
    //	mOutput.setText(text);
	}
    
    OnClickListener ExitListener = new OnClickListener() {
        public void onClick(View v) {
            finish();
        }
    };
    
    OnClickListener ShowPdfListener = new OnClickListener() {
        public void onClick(View v) {
        	persistValues();
        	String pdffilename = mFilename.getText().toString();
//        	boolean useFontSubstitution = mUseFontSubstitution.isChecked();
//        	boolean keepCaches = mKeepCaches.isChecked();
        	boolean useFontSubstitution = false;
        	boolean keepCaches = true;
////        	
//        	
        	Intent intent = new Intent(PdfFileSelectActivity.this, PdfViewerActivity.class)
			.putExtra(EXTRA_PDFFILENAME, pdffilename)
			.putExtra(EXTRA_USEFONTSUBSTITUTION, useFontSubstitution)
			.putExtra(EXTRA_KEEPCACHES, keepCaches);
	    	startActivity(intent);
//	    	startActivityForResult(intent, 1);
        }
    };

    OnClickListener BrowseFileListener = new OnClickListener() {
        public void onClick(View v) {
			setContentView(R.layout.file_explorer);
			mPath = (TextView) findViewById(R.id.mPath);
			String dir = rootPath;
			try {
				File parent = new File(mFilename.getText().toString()).getParentFile();
				if (parent.exists())
					dir = parent.getAbsolutePath();
			}
			catch (Exception ignore) {}
			getFileDir(dir);
        }
    };

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, BROSWER_ID, 0, "Browser...");
		return true;		
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()){
		case BROSWER_ID: 
			{
				setContentView(R.layout.file_explorer);
				mPath = (TextView) findViewById(R.id.mPath);
				getFileDir(rootPath);
			}
			break;		
		default: 
			; 
			break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/** Get the file structure */
	private void getFileDir(String filePath) {
		mPath.setText(filePath);
		items = new ArrayList<String>();
		paths = new ArrayList<String>();
		File f = new File(filePath);
		File[] files = f.listFiles();
		if (!filePath.equals(rootPath)) {
			items.add("back2root");
			paths.add(rootPath);
			items.add("back2up");
			paths.add(f.getParent());
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			items.add(file.getName());
			paths.add(file.getPath());
		}
		setListAdapter(new MyAdapter(this, items, paths));
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		File file = new File(paths.get(position));
		String fName = file.getName();
		if (file.isDirectory())
			getFileDir(paths.get(position));
		else if (fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase().equals("pdf"))
			updateFileSelected(file);
		else
			;//Do nothing
	}
	
	private void updateFileSelected(File file) {
		setContentView(R.layout.pdf_file_select);
		mFilename = (EditText) findViewById(R.id.filename);
		mFilename.setText(file.getAbsolutePath());
	//	mUseFontSubstitution = (CheckBox) findViewById(R.id.cbUseFontSubstitution);
	//	mKeepCaches = (CheckBox) findViewById(R.id.cbKeepCaches);
    	mBrowse = (Button) findViewById(R.id.btBrowse);
    	mShow = (Button) findViewById(R.id.btShow);
    	mExit = (Button) findViewById(R.id.btExit);

        mBrowse.setOnClickListener(BrowseFileListener);
        mShow.setOnClickListener(ShowPdfListener);
        mExit.setOnClickListener(ExitListener);
		
	    // load persisted values
    	persist = new SimplePersistence(this, PREFS_NAME);
    	boolean useFontSubstitution = persist.getBoolean(PREFS_USEFONTSUBSTITUTION, DEFAULTUSEFONTSUBSTITUTION);
    	boolean keepCaches = persist.getBoolean(PREFS_KEEPCACHES, DEFAULTKEEPCACHES);
       // mUseFontSubstitution.setChecked(useFontSubstitution);
        //mKeepCaches.setChecked(keepCaches);
	}
	
	
}