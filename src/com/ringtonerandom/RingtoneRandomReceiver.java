package com.ringtonerandom;

import java.io.File;
import java.io.FileFilter;
import java.util.Random;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

public class RingtoneRandomReceiver extends BroadcastReceiver {

	private FileFilter filter;
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
	
		filter = new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return pathname.getAbsolutePath().endsWith(".mp3");
			}
		};
		
		String strFolder = getSDPath() + "/ringtonewillrandom/";
		File folder = new File(strFolder);

		File ringtones[] = folder.listFiles(filter);

		if (ringtones.length == 0) {
			Toast.makeText(context, "no ringtones", Toast.LENGTH_LONG).show();
			return;
		}
		
		int n = new Random().nextInt() % ringtones.length; 
		
		File file = ringtones[n];
		
		ContentValues values = new ContentValues();  
		values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());  
		values.put(MediaStore.MediaColumns.MIME_TYPE, "audio/mp3");  

		values.put(MediaStore.Audio.Media.IS_RINGTONE, true);  
		values.put(MediaStore.Audio.Media.IS_NOTIFICATION, false);  
		values.put(MediaStore.Audio.Media.IS_ALARM, false);  
		values.put(MediaStore.Audio.Media.IS_MUSIC, false);  
    
		Uri uri = MediaStore.Audio.Media.getContentUriForPath(file.getAbsolutePath());  
		Uri newUri = context.getContentResolver().insert(uri, values);  
		RingtoneManager.setActualDefaultRingtoneUri(context, RingtoneManager.TYPE_RINGTONE, newUri);
	}


	public String getSDPath() { 
        File sdDir = null; 
        boolean sdCardExist = Environment.getExternalStorageState()   
                            .equals(android.os.Environment.MEDIA_MOUNTED); //�ж�sd���Ƿ���� 
        if (sdCardExist) {
        	sdDir = Environment.getExternalStorageDirectory(); //��ȡ��Ŀ¼ 
        }
        return sdDir.toString();
	}
}
