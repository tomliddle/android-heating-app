package com.tomliddle.heating;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Extended WebChromeClient to allow javascript confirmation dialogue to work in the webview.
 */
final class MyWebChromeClient extends WebChromeClient {

	private Context context;

	public MyWebChromeClient(Context context) {
		this.context = context;
	}
	@Override
	public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
		AlertDialog.Builder dlg=new AlertDialog.Builder(context);
		dlg.setMessage(message);
		dlg.setTitle("Confirm Action");
		dlg.setCancelable(false);
		dlg.setPositiveButton(android.R.string.ok,new AlertDialog.OnClickListener(){
					public void onClick(    DialogInterface dialog,    int which){
						result.confirm();
					}
				}
		);
		dlg.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
						result.cancel();
					}});
		try {
			dlg.create();
			dlg.show();
		}
		catch (Exception e) {}
		return true;
	}
}
