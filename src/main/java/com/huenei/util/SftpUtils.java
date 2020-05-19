package com.huenei.util;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

@Service
@PropertySource("classpath:/application.properties")
public class SftpUtils {
	
	private String remoteFtp;
	private String userFtp;
	private String passFtp;
	

	public SftpUtils(@Value("${sftp.client.host}") String remoteFtp,
			@Value("${sftp.client.username}") String userFtp, @Value("${sftp.client.password}") String passFtp) {
		this.remoteFtp = remoteFtp;
		this.userFtp= userFtp;
		this.passFtp = passFtp;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(SftpUtils.class);

	public ChannelSftp setupJsch() throws JSchException {
		LOGGER.info("Init Connection SFTP");
		JSch jsch = new JSch();
		Properties config = new Properties();
		config.put("StrictHostKeyChecking", "no");
		jsch.setKnownHosts("/Users/NB-KMIRANDA/.ssh/known_hosts");
		Session jschSession = jsch.getSession(userFtp, remoteFtp);
		jschSession.setConfig(config);
		jschSession.setPassword(passFtp);
		jschSession.connect();
		return (ChannelSftp) jschSession.openChannel("sftp");
	}

	public void putFile(String localFile) throws SftpException, JSchException {
		LOGGER.info("Init Put file to SFTP");
		ChannelSftp channelSftp = setupJsch();
		channelSftp.connect();
		String remoteDir = "home/sftp/";
		channelSftp.put(localFile, remoteDir + "file.csv");

		channelSftp.exit();
		LOGGER.info("Finish Connection SFTP");
	}
	
}
