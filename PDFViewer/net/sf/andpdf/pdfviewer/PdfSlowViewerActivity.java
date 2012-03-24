package net.sf.andpdf.pdfviewer;

import android.os.Bundle;


/**
 * @author ferenc.hechler
 */
public class PdfSlowViewerActivity extends PdfViewerActivity {

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	getIntent().putExtra(PdfFileSelectActivity.EXTRA_USEFONTSUBSTITUTION, false);
        super.onCreate(savedInstanceState);
    }

}