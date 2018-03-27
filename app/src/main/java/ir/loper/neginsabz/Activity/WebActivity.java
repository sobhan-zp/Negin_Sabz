package ir.loper.neginsabz.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;

import ir.loper.neginsabz.R;

public class WebActivity extends AppCompatActivity {


    private WebView webview;
    private String postUrl = "http://neginsabzco.com";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);


        webview =(WebView)findViewById(R.id.webView);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);




        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(postUrl);
        webview.setHorizontalScrollBarEnabled(false);





       /* webview.setWebViewClient(new WebViewClient());
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setDomStorageEnabled(true);
        webview.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
        webview.loadUrl("http://neginsabzco.com");
*/
    }
}
