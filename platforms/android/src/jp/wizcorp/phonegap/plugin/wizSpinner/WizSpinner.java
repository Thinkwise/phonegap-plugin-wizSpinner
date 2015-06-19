package jp.wizcorp.phonegap.plugin.wizSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

public class WizSpinner {

	private static ProgressDialog pd;
	private static String TAG = "WizSpinner";
	public static boolean isVisible = false;

	public static void show(Activity activity, JSONArray data) {
		// Create and show the spinner
		JSONObject dataObj = null;
		String text = "Loading...";
		try {
			dataObj = data.getJSONObject(0);
		} catch (JSONException e) {
			// Failed to get any data
			dataObj = null;
		}
		if (dataObj != null ) {
			if ( dataObj.has("labelText")) {
				// "labelText" is deprecated in v3.0 iOS. "label" is the correct
				// property to be used on both Android and iOS. Show info log.
				Log.i(TAG, "\"labelText\" is deprecated in v3.0. Use \"label\"");
				try {
					text = (String) dataObj.get("labelText");
				} catch (JSONException e) {
					// Failed for some reason
				}
			} else if ( dataObj.has("label")) {
				try {
					text = (String) dataObj.get("label");
				} catch (JSONException e) {
					// Failed for some reason
				}
			}
		}
		
		final String labelText = text;
		final Activity _ctx = activity;
		
		if ( !isVisible ) {
			Log.i(TAG, "[display spinner] ******* ");
			
			activity.runOnUiThread(
				new Runnable() {
					public void run() {
						pd = ProgressDialog.show(_ctx, null, labelText, true, false);
					}
				}
			);
			
			isVisible = true;
		} else {
			// Update the text when the loader is already visible.
			activity.runOnUiThread(
				new Runnable() {
					public void run() {
						pd.setMessage(labelText);
					}
				}
			);
		}
	}
	
	public static void hide(Activity activity) {
		// Hide the spinner
		if ( isVisible ) {
			Log.i("wizSpinner", "[Hiding spinner] ******* ");
			
			activity.runOnUiThread(
				new Runnable() {
					public void run() {
						pd.dismiss();
					}
				}
			);
			isVisible = false;
		}
	}
}
