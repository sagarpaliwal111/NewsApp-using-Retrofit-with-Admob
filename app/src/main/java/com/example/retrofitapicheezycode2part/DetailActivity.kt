package com.example.retrofitapicheezycode2part

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import java.util.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val url = intent.getStringExtra("URL")
        if (url != null) {
            findViewById<WebView>(R.id.webView).settings.javaScriptEnabled = true
            findViewById<WebView>(R.id.webView).settings.userAgentString ="Mozilla/5.0 (iPhone; U; CPU like Mac OS X; en) AppleWebKit/420+ (KHTML, like Gecko) Version/3.0 Mobile/1A543a Safari/419.3)"
            findViewById<WebView>(R.id.webView).webViewClient = object : WebViewClient(){
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)
                    findViewById<ProgressBar>(R.id.progressBar).visibility= View.GONE
                    findViewById<WebView>(R.id.webView).visibility=View.VISIBLE
                }
            }
            findViewById<WebView>(R.id.webView).loadUrl(url)
        }

    }
}