package com.xgw.wwx.common.util;

import java.io.File;

public class FileUtil {

	public static void createFile(File file) {
		try {
			if (!file.getParentFile().exists()) {
				file.getParentFile().mkdirs();
			}
			if (file.exists()) {
				file.delete();
			}
			file.createNewFile();
		} catch (Exception e) {
			System.out.println("-- create file error --");
		}

	}

}
