package com.huenei.service.impl;

import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.huenei.domain.Employee;
import com.huenei.domain.ResponseData;
import com.huenei.service.RestapiService;
import com.huenei.service.SftpService;
import com.huenei.util.ConstanstHuaneiEmployees;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@Service
@PropertySource("classpath:/application.properties")
public class RestapiServiceImpl implements RestapiService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestapiServiceImpl.class);
	
	private SftpService sftpService;
	private RestTemplate restTemplate;
	private String endPoint;
	private HttpHeaders headers = new HttpHeaders();

	@Autowired
	public RestapiServiceImpl(RestTemplate restTemplate, SftpService sftpService, @Value("${api.endpoint}") String endpoint) {
		this.restTemplate = restTemplate;
		this.sftpService = sftpService;
		this.endPoint = endpoint;
	}

	@Override
	public ResponseEntity<Object> generateCsv() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		String file = System.getProperty("java.io.tmpdir") + ConstanstHuaneiEmployees.FILENAME;		
		try (Writer writer = Files.newBufferedWriter(Paths.get(file))) {
			StatefulBeanToCsv<Employee> beanToCsv = new StatefulBeanToCsvBuilder<Employee>(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			beanToCsv.write(getEmployees());			
			writer.close();
			this.sftpService.putFile(file);
			return new ResponseEntity<>("ok", HttpStatus.OK);
		} catch (SftpException e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		} catch (JSchException e) {
			LOGGER.error(e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		}
	}

	private List<Employee> getEmployees() {
		ResponseData rs = new ResponseData();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		ResponseEntity<ResponseData> response = null;
		try {
			response = restTemplate.exchange(endPoint, HttpMethod.GET, null, ResponseData.class);
			rs = response.getBody();
			LOGGER.info(rs.toString());
		} catch (HttpStatusCodeException e) {
			LOGGER.error(e.getMessage());
		}
		return rs.getData();
	}

}
