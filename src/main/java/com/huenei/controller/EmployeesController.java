package com.huenei.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.huenei.service.RestapiService;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;


@RestController("employeeController")
@RequestMapping("/api/employees")
public class EmployeesController {
	
	@Autowired	
	private RestapiService restApiService;
	
	@PostMapping
	public ResponseEntity<Object> insertar() throws CsvDataTypeMismatchException, CsvRequiredFieldEmptyException, IOException {
		return restApiService.generateCsv();
	}	

}
