package com.ringtonerandom;

import java.io.File;
import java.io.FileFilter;
import java.util.Random;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.drm.DrmStore.Action;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	
//	private Button btnOpenReceiver;
//	private Button btnCloseReceiver;
//	private Button btnAddRingtone;
//	private Button btnManageRingtone;
	
	private TextView tvDebugMsg;
	
	private FileFilter filter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		btnOpenReceiver = (Button)findViewById(R.id.startReceiver);
//		btnCloseReceiver = (Button)findViewById(R.id.stopReceiver);
//		btnAddRingtone = (Button)findViewById(R.id.add);
//		btnManageRingtone = (Button)findViewById(R.id.manage);
//		
//		
//		
//		btnOpenReceiver.setOnClickListener(this);
//		btnCloseReceiver.setOnClickListener(this);
//		btnAddRingtone.setOnClickListener(this);
//		btnManageRingtone.setOnClickListener(this);
		tvDebugMsg = (TextView)findViewById(R.id.debugMsg);
		filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getAbsolutePath().endsWith(".mp3");
			}
		};
		
		
		// create folder
		String strFolder = getSDPath() + "/ringtonewillrandom/";
		File folder = new File(strFolder);
		if (!folder.exists()) {	
			folder.mkdirs();
			Toast.makeText(this, "[good] make dirs", Toast.LENGTH_LONG).show();
		}
		
		
		File ringtones[] = folder.listFiles(filter);
		
		String msg = new String();
		for (int i = 0; i < ringtones.length; ++i) {
			msg += ringtones[i].getAbsolutePath() + "\n";
		}
		tvDebugMsg.setText(msg);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		if (v.equals(btnOpenReceiver)) {
//			
//		} else if (v.equals(btnCloseReceiver)) {
//			
//		} else if (v.equals(btnAddRingtone)) {
//			
//		} 
//	}

	
	public String getSDPath() { 
        File sdDir = null; 
        boolean sdCardExist = Environment.getExternalStorageState()   
                            .equals(android.os.Environment.MEDIA_MOUNTED); //判断sd卡是否存在 
        if (sdCardExist) {
        	sdDir = Environment.getExternalStorageDirectory(); //获取跟目录 
        }
        return sdDir.toString();
	}
}
