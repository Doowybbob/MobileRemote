package com.doowybbob.MRApp;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Remote extends ActionBarActivity {
	
	boolean connected;
	ClientThread ct;
	int startX, startY;
	int counter;
	String DEFAULT_IP_KEY = "com.doowybbob.MRClient.ip_key";
	String DEFAULT_PORT_KEY = "com.doowybbob.MRClient.port_key";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_remote);
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		
		counter = 0;
		if (this.connected == false) {
			if (prefs.contains(DEFAULT_IP_KEY)){
				if (prefs.contains(DEFAULT_PORT_KEY))
					connect(prefs.getString(DEFAULT_IP_KEY, ""), prefs.getInt(DEFAULT_PORT_KEY, 0));
			}
			else {
				DialogFragment fragment = new ConnectDialog();
				fragment.show(getFragmentManager(), "connect");
			}
		}
		
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.remote, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.action_settings:
			openSettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	private void openSettings() {
		Intent intent = new Intent(Remote.this, SettingsActivity.class);
		Remote.this.startActivity(intent);
	}
	
	@Override
	protected void onStop() {
		if (connected) {
			ct.add("exit");
			connected = false;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int dX, dY;
		//Log.i("Remote", event.toString());
		if (event.getAction() == MotionEvent.ACTION_DOWN){
			startX = (int)event.getX();
			startY = (int)event.getY();
		}
		else if (event.getAction() == MotionEvent.ACTION_MOVE){
			counter++;
			dX = ((int)event.getX() - startX)/3;
			dY = ((int)event.getY() - startY)/3;
			if ((counter % 4) == 0) {
				Log.i("Remote", "mm:" + dX + ":" + dY);
				ct.add("mm:" + dX + ":" + dY);
			}
		}
		
		return true;
	}
	
	protected void connect (String ipAddr, int port) {
		try {
			ct = new ClientThread(ipAddr, port);
			ct.start();
			Context context = getApplicationContext();
			CharSequence text = "Running!";
			int duration = Toast.LENGTH_SHORT;

			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
			
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	
		if (ct.client != null) {
			connected = true;
		}
	}
	
	protected void setDefault (String ipAddr, int port) {
		SharedPreferences prefs = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor editor = prefs.edit();
		editor.putString(DEFAULT_IP_KEY, ipAddr);
		editor.putInt(DEFAULT_PORT_KEY, port);
		editor.commit();
	}
	
	public void leftClick(View v) {
		ct.add("lc");
	}
	
	public void rightClick(View v) {
		ct.add("rc");
	}
	
	public void writeText (View v) {
		EditText text = (EditText)findViewById(R.id.text);
		ct.add("wr");
		ct.add(text.getText().toString());
	}
}
