package io.relayr.core.activity;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import io.relayr.core.api.Relayr_ApiCall;
import io.relayr.core.api.Relayr_ApiConnector;
import io.relayr.core.error.Relayr_Exception;
import io.relayr.core.settings.Relayr_SDKStatus;

public class Relayr_LoginActivity extends Relayr_Activity {

	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        mWebView = new WebView(this);
        setContentView(mWebView);

		mWebView.setWebChromeClient(new WebChromeClient());
		mWebView.setVerticalScrollBarEnabled(false);

		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAppCacheEnabled(true);
		webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);

		mWebView.setVisibility(View.VISIBLE);

		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				Log.d("Login_Activity", "Webview opening: " + url);
				String accessCode = getAccessCode(url);
				if (accessCode != null) {
					Log.d("Relayr_LoginActivity", "onPageStarted access code: " + accessCode);
					try {
						Relayr_SDKStatus.synchronizeTokenInfo(accessCode);
					} catch (Exception e) {
						Log.d("Relayr_LoginActivity", "Error: " + e.getMessage());
					}
					finish();
				}
			}
		});

		loadWebViewContent();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig){
	    super.onConfigurationChanged(newConfig);
	}

	private void loadWebViewContent() {
		try {
			Object[] parameters = {};
            String url = (String) Relayr_ApiConnector.doCall(Relayr_ApiCall.UserAuthorization, parameters);
			mWebView.loadUrl(url);
		} catch (Relayr_Exception e) {
			e.printStackTrace();
		}
	}

	private String getAccessCode(String url) {
		String codeParam = "?code=";
		if (url.contains(codeParam)) {
			int tokenPosition = url.indexOf(codeParam);
			String code = url.substring(tokenPosition + codeParam.length());
			Log.d("Login_Activity", "Access code: " + code);
			return code;
		} else {
			return null;
		}
	}

}
