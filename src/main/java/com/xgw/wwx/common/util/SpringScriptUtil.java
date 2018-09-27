package com.xgw.wwx.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.EncodedResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import java.sql.Connection;

/**
 * Created by EX-ZHONGJUN001 on 2018/4/8.
 */
public class SpringScriptUtil {

	private static final Logger logger = LoggerFactory.getLogger(SpringScriptUtil.class);

	/**
	 * 执行本地sql
	 */
	public static void executeWithFilePath(String filePath) {
		try {
			Connection conn = JdbcUtil.getConnection();
			FileSystemResource rc = new FileSystemResource(filePath);
			EncodedResource er = new EncodedResource(rc, "UTF-8");
			ScriptUtils.executeSqlScript(conn, er);
		} catch (Exception e) {
			logger.error("-- executeWithFilePath error --", e);
		}
	}

	/**
	 * 执行classpath文件sql
	 */
	public static void executeWithClassPath(String filePath) {
		try {
			Connection conn = JdbcUtil.getConnection();
			ClassPathResource rc = new ClassPathResource(filePath);
			EncodedResource er = new EncodedResource(rc, "UTF-8");
			ScriptUtils.executeSqlScript(conn, er);
		} catch (Exception e) {
			logger.error("-- executeWithClassPath error --", e);
		}
	}

}
