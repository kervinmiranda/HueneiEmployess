package com.huenei.service;

import java.io.IOException;
import java.util.List;

import org.springframework.http.ResponseEntity;

import com.huenei.domain.Employee;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public interface RestapiService {

	ResponseEntity<Object> generateCsv() throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException;

}
