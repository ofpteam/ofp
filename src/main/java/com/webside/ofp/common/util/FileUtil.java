package com.webside.ofp.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileUtil {

	/**
	 * 复制文件
	 * 
	 * @param fromFile
	 * @param toFile
	 *            <br/>
	 *            2016年12月19日 下午3:31:50
	 * @throws IOException
	 */
	public static void copyFile(File fromFile, File toFile) throws IOException {
		FileInputStream ins = new FileInputStream(fromFile);
		FileOutputStream out = new FileOutputStream(toFile);
		byte[] b = new byte[1024];
		int n = 0;
		while ((n = ins.read(b)) != -1) {
			out.write(b, 0, n);
		}
		ins.close();
		out.close();
	}
}
