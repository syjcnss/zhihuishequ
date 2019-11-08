package com.ovu.lido.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterOutputStream;

/**
 * 压缩工具包
 */
public class ZipUtil {

	/**
	 * ZLib压缩数据
	 * 
	 * @param bContent
	 * @return
	 * @throws IOException
	 */
	public static byte[] zip(byte[] bContent) throws IOException {
		byte[] data;
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		DeflaterOutputStream zOut = new DeflaterOutputStream(bOut); // 压缩级别,缺省为1级
		zOut.write(bContent);
		zOut.flush();
		zOut.close();
		data = bOut.toByteArray();
		bOut.close();
		return data;
	}

	/**
	 * ZLib解压数据
	 * 
	 * @param bContent
	 * @return
	 * @throws IOException
	 */
	public static byte[] unzip(byte[] bContent) throws IOException {
		ByteArrayOutputStream bOut = new ByteArrayOutputStream();
		InflaterOutputStream zOut = new InflaterOutputStream(bOut);
		zOut.write(bContent);
		zOut.finish();
		zOut.close();
		return bOut.toByteArray();
	}

}