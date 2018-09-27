package com.xgw.wwx.common.util;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.Connection;

/**
 * Created by EX-ZHONGJUN001 on 2018/4/8.
 */
public class MybatisScriptRunnerUtil {

	private static final Logger logger = LoggerFactory.getLogger(MybatisScriptRunnerUtil.class);

	/**
	 * 执行本地sql
	 */
	public static void executeWithFilePath(String filePath) {
		try {
			Connection conn = JdbcUtil.getConnection();
			ScriptRunner runner = new ScriptRunner(conn);
			runner.runScript(new FileReader(filePath));
		} catch (FileNotFoundException e) {
			logger.error("-- 本地文件不存在 --", e);
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
			ScriptRunner runner = new ScriptRunner(conn);
			InputStream is = MybatisScriptRunnerUtil.class.getResourceAsStream(filePath);
			InputStreamReader isr = new InputStreamReader(is);
			runner.runScript(isr);
		} catch (Exception e) {
			logger.error("-- executeWithClassPath error --", e);
		}
	}

}
