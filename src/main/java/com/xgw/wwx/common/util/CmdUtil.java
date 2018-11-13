package com.xgw.wwx.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 命令行执行工具类
 *
 * @author zj
 */
public class CmdUtil {

	public static Logger logger = LoggerFactory.getLogger(CmdUtil.class);

	/**
	 * 执行操作命令
	 *
	 * @param command
	 *            命令
	 * @param currentDirectory
	 *            执行目录
	 * @return
	 */
	public static String executeCommand(String command, File currentDirectory) {
		logger.info("start executeCommand[command={}]", command);
		String respContent = "";
		InputStream input = null;
		try {
			ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "/bin/bash", "-c", "\"\"" + command + "\"\"" });
			processBuilder.redirectErrorStream(true);
			Process p = processBuilder.directory(currentDirectory).start();
			input = p.getInputStream();
			respContent = IOUtils.toString(input, "UTF-8");
			logger.info("executeCommand result[respContent={}]", respContent);
		} catch (Exception e) {
			logger.error("executeCommand error[command=" + command + "]", e);
		} finally {
			IOUtils.closeQuietly(input);
		}
		logger.info("finish executeCommand[command={}]", command);
		return respContent;
	}

	/**
	 * 执行操作命令
	 *
	 * @param command
	 * @return
	 */
	public static String executeCommand(String command) {
		return executeCommand(command, getCurrentDirectoryFile());
	}

	/**
	 * 获取当前目录
	 *
	 * @return
	 */
	public static File getCurrentDirectoryFile() {
		Map<String, String> env = System.getenv();
		String[] environment = new String[env.size()];
		int e = 0;
		for (Object name : env.keySet()) {
			environment[e] = name.toString() + "=" + env.get(name).toString();
			e++;
		}
		String currentDirectory = ".";
		File currentDirectoryFile = new File(currentDirectory);
		return currentDirectoryFile;
	}

	public static List<String> execute(String command, File currentDirectory) {
		logger.info("start executeCommand[command={}]", command);
		BufferedReader br = null;
		List<String> results = null;
		try {
			results = new ArrayList<String>();
			//执行命令
			ProcessBuilder processBuilder = new ProcessBuilder(new String[] { "/bin/bash", "-c", "\"\"" + command + "\"\"" });
			processBuilder.redirectErrorStream(true);
			Process p = processBuilder.directory(currentDirectory).start();
			//读取
			br = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line = null;
			while ((line = br.readLine()) != null) {
				results.add(line);
			}
		} catch (Exception e) {
			logger.error("executeCommand error[command=" + command + "]", e);
		} finally {
			IOUtils.closeQuietly(br);
		}
		logger.info("finish executeCommand[command={}]", command);
		return results;
	}

}
