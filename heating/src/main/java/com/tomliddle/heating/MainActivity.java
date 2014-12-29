package com.tomliddle.heating;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;


public class MainActivity extends ActionBarActivity {

	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SSHConnect sshConnect = new SSHConnect();
		sshConnect.execute();
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

	private class SSHConnect extends AsyncTask<Integer, Void, Void> {
		protected Void doInBackground(Integer... x) {
			SshPortForward sshPortForward = new SshPortForward(getApplicationContext());

			return null;
		}

		protected void onProgressUpdate(Integer... progress) {
			//setProgressPercent(progress[0]);
		}

		protected void onPostExecute(Long result) {
			WebView webview = new WebView(getApplicationContext());
			webview.getSettings().setJavaScriptEnabled(true);
			webview.getSettings().setAllowUniversalAccessFromFileURLs(true);
			setContentView(webview);
			webview.loadUrl("http://localhost:8080/heating");
		}
	}
}
