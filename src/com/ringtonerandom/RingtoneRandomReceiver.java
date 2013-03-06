package com.ringtonerandom;

import java.io.File;
import java.io.FileFilter;
import java.util.Random;

import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class RingtoneRandomReceiver extends BroadcastReceiver {

//	private FileFilter filter;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	
//		filter = new FileFilter() {
//			@Override
//			public boolean accept(File pathname) {
//				// TODO Auto-generated method stub
//				return pathname.getAbsolutePath().endsWith(".mp3");
//			}
//		};
		
		String strFolder = getSDPath() + "/ringtonewillrandom/";
		File folder = new File(strFolder);

		File ringtones[] = folder.listFiles();

		if (ringtones.length == 0) {
			Toast.makeText(context, "no ringtones", Toast.LENGTH_LONG).show();
			return;
		}
		
		int n = new Random().nextInt(ringtones.length); 
		
		File file = ringtones[n];
		
		ContentValues values = new ContentValues();  
		values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());  
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/*");  

		values.put(MediaStore.Audio.Media.IS_RINGTONE, true);  
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);  
		values.put(MediaStore.Audio.Media.IS_ALARM, false);  
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);  
    
		Uri uri = MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath());  
		// Uri newUri = context.getContentResolver().insert(uri, values);  
		Uri newUri = null;
		
		Cursor cursor = context.getContentResolver().query(
				uri, 
				null, 
				MediaStore.MediaColumns.DATA + "=? and 1=" + MediaStore.Audio.Media.IS_RINGTONE, 
				new String[]{file.getAbsolutePath()}, 
				null);
		
		if (cursor.getCount() > 0 && cursor.moveToFirst()) {
			long mediaId = cursor.getInt(cursor.getColumnIndex("_id"));
			newUri = ContentUris.withAppendedId(uri, mediaId);
		} else {
			newUri = context.getContentResolver().insert(uri, values);  
		}
		
		RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, newUri);
		Log.i("change to: ", newUri.toString());
	}


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
