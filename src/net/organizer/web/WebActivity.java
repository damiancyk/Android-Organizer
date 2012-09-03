package net.organizer.web;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import net.organizer.R;

public class WebActivity extends Activity {

	private WebView webView;
 
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.web);

		webView = (WebView) findViewById(R.id.webViewId);
		webView.setWebViewClient(new WebViewClient());
	
//		webView.loadUrl("http://localhost:8080/Organizer");
//		webView.loadUrl("http://10.0.2.2:8080/Organizer/login");
		webView.loadUrl("http://192.168.0.2:8080/Organizer");
		
		WebSettings webSettings = webView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
	}

}