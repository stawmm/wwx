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

	@Override
	public boolean uploadFile(String path, String fileName, InputStream is) {
		return FtpUtil.uploadFile(ftpHost, ftpPort, userName, passWord, path, fileName, is);
	}

	@Override
	public boolean downLoadFile(String remotePath, String fileName, String localPath) {
		return FtpUtil.downloadFile(ftpHost, ftpPort, userName, passWord, remotePath, fileName, localPath);
	}

}
