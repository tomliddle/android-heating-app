package com.tomliddle.heating;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Handles SSH connection and port forwarding.
 */
public class MainActivity extends ActionBarActivity {

	private WebView webview;
	private SshPortForward sshPortForward;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// To view the controls we connect to the html page served by heating server.
		// This avoids having to modify two sets of controls.
		webview  = new WebView(getApplicationContext());
		webview.getSettings().setJavaScriptEnabled(true);
		webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
		webview.setWebViewClient(new WebViewClient());
		webview.setWebChromeClient(new MyWebChromeClient(this));
		setContentView(webview);

		SSHConnect sshConnect = new SSHConnect();
		sshConnect.execute();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		close();
	}

	private void close() {
		if (sshPortForward != null) {
			sshPortForward.disconnect();
			sshPortForward = null;
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private class SSHConnect extends AsyncTask<Integer, Void, Long> {
		protected Long doInBackground(Integer... x) {
			if (sshPortForward == null) {
				sshPortForward = new SshPortForward(getApplicationContext());
			}
			else {
				sshPortForward.ensureConnected();
			}

			return 0l;
		}

		protected void onPostExecute(Long result) {
			webview.loadUrl("http://localhost:8080");
		}
	}
}
