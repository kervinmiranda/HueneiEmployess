package com.huenei.domain;

import java.util.ArrayList;

public class ResponseData {

	private String status;
	private ArrayList<Employee> data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public ArrayList<Employee> getData() {
		return data;
	}

	public void setData(ArrayList<Employee> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResponseService {status=" + status + ", data=" + data + "}";
	}

}
