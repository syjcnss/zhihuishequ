package com.ovu.lido.util;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class WidgetHelper {

	private static final String TAG = "WidgetHelper";
	private static final int MAX_W = 960;
	private static final int MAX_H = 960;

	private static String getFilePathFromUri(Context context, Uri uri) {
		String path = uri.getPath();
		if ("file".equals(uri.getScheme())) {
			return path;
		}
		String[] projection = { MediaStore.Images.Media.DATA };
		Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
		try {
			if (cursor.moveToFirst()) {
				int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				path = cursor.getString(columnIndex);
			}
		} finally {
			cursor.close();
		}
		return path;
	}

	public static Bitmap decodeUriAsBitmap(Context context, Uri uri) {
		BitmapFactory.Options opts = new BitmapFactory.Options();
		String path = getFilePathFromUri(context, uri);
		opts.inJustDecodeBounds = true;// 不分配内存，只取出图片的属性
		BitmapFactory.decodeFile(path, opts);
		int outX = opts.outWidth;
		int outY = opts.outHeight;
		// int sampleSize = 1;
		if (opts.outHeight <= MAX_H && opts.outWidth <= MAX_W) {
			LogUtil.i(TAG, "图片原尺寸小于目标尺寸");
		} else {
			int h = opts.outWidth * MAX_H / MAX_W;
			if (h > opts.outHeight) {
				outX = MAX_W;
				outY = outX * opts.outHeight / opts.outWidth;
				// sampleSize = opts.outWidth / MAX_W;
			} else {
				outY = MAX_H;
				outX = outY * opts.outWidth / opts.outHeight;
				// sampleSize = opts.outHeight /MAX_H;
			}
		}
		opts.inPreferredConfig = Bitmap.Config.RGB_565;
		opts.inPurgeable = true;// 设置图片可以被回收
		// opts.inSampleSize = sampleSize;// 按比例缩放
		opts.inInputShareable = true;// 设置解码位图的尺寸信息
		opts.inJustDecodeBounds = false;
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(context.getContentResolver().openInputStream(uri), null, opts);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		LogUtil.i(TAG, "out:w=" + outX + " & h=" + outY);
		Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, outX, outY, false);
		if (newBitmap == bitmap) {
			LogUtil.i(TAG, "没有缩放，直接返回原图");
		} else {
			bitmap.recycle();
		}
		return newBitmap;
	}

	/**
	 * 显示裁剪后的图片
	 * 
	 * @param context
	 * @param uri
	 */
	public static String showChooseImg(final Context context, Uri uri) {
		Bitmap bitmap = decodeUriAsBitmap(context, uri);
		if (bitmap == null) {
			ToastUtil.show(context, "获取图片失败");
			return null;
		}
		final String savePath = ViewHelper.getFileSavePath(context) + System.currentTimeMillis() + ".jpg";
		// 保存文件到sd卡中
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(savePath);
			bitmap.compress(CompressFormat.JPEG, 80, fos);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return savePath;
	}

}
