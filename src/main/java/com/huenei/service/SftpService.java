package com.huenei.service;

import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

public interface SftpService {
	
	void putFile(String localFile) throws SftpException, JSchException;
	
}
