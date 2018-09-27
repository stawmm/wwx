package com.xgw.wwx.service.ftp;

import java.io.InputStream;

public interface FtpService {

	public boolean uploadFile(String path, String fileName, InputStream is);

	public boolean downLoadFile(String remotePath, String fileName, String localPath);

}
