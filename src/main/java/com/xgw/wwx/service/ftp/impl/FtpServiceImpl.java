package com.xgw.wwx.service.ftp.impl;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.xgw.wwx.common.util.FtpUtil;
import com.xgw.wwx.service.ftp.FtpService;

@Service
public class FtpServiceImpl implements FtpService {

	@Value("${spring.ftp.host}")
	private String ftpHost;

	@Value("${spring.ftp.port}")
	private int ftpPort;

	@Value("${spring.ftp.username}")
	private String userName;

	@Value("${spring.ftp.password}")
	private String passWord;

	@Value("${spring.ftp.path}")
	private String defaultPath;

	/**
	 *
	 * @param path
	 * 			FTP服务器保存目录
	 * @param fileName
	 *			上传到FTP服务器后的文件名称
	 * @param is
	 *			输入文件流
	 * @return
	 */
	@Override
	public boolean uploadFile(String path, String fileName, InputStream is) {
		return FtpUtil.uploadFile(ftpHost, ftpPort, userName, passWord, path, fileName, is);
	}

	/**
	 * @param remotePath
	 * 				 FTP服务器文件目录
	 * @param fileName
	 * 				文件名称
	 * @param localPath
	 * 				下载后的文件路径
	 * @return
	 */
	@Override
	public boolean downLoadFile(String remotePath, String fileName, String localPath) {
		return FtpUtil.downloadFile(ftpHost, ftpPort, userName, passWord, remotePath, fileName, localPath);
	}

}
