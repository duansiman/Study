package com.epdc.study;

import java.io.Serializable;

public class RmiParam implements Serializable {

	private String value;

	public RmiParam(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
