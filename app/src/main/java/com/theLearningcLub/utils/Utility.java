package com.theLearningcLub.utils;


import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;

public class Utility {
	// convert from bitmap to byte array
	public static byte[] getBytes(Bitmap bitmap) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(CompressFormat.PNG, 0, stream);
		return stream.toByteArray();
	}

	// convert from byte array to bitmap
	public static Bitmap getPhoto(byte[] image) {
		return BitmapFactory.decodeByteArray(image, 0, image.length);
	}
	public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
	public static boolean checkPermission(final Context context)
	{
		int currentAPIVersion = Build.VERSION.SDK_INT;
		if(currentAPIVersion>= Build.VERSION_CODES.JELLY_BEAN_MR2)
		{
			if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {

					String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
					ActivityCompat.requestPermissions((Activity) context, permissions, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
					System.out.println(" IF READ_EXTERNAL_STORAGE");

				} else {

					System.out.println(" ELSE READ_EXTERNAL_STORAGE");

					String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
					ActivityCompat.requestPermissions((Activity) context, permissions, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);

					CommonFunction.showToastSingle((Activity) context,"Please allow the permissions to upload profile picture");
//					CustomAlertdialog.createDialog((Activity) context,"Please allow the permissions to upload profile picture");
				}
				return false;
			}
			else if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
				if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)) {
					System.out.println(" else if  READ_EXTERNAL_STORAGE ");
					String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
					ActivityCompat.requestPermissions((Activity) context, permissions, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);


				} else {
					System.out.println(" else if   else READ_EXTERNAL_STORAGE ");
					String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE};
					ActivityCompat.requestPermissions((Activity) context, permissions, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
					//	ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
				}
				return false;
			}
			else {
				return true;
			}

		} else {
			return true;
		}
	}


 }
