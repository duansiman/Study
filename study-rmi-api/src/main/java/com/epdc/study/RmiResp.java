package com.epdc.study;

import java.io.Serializable;

public class RmiResp implements Serializable {

	private String value;

	public RmiResp(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
