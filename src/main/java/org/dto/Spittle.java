package org.dto;

import java.io.Serializable;

public class Spittle implements Serializable {
	
	private static final long serialVersionUID = 7375810698841737901L;
	public String key;

	public Spittle(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
}
