package com.devsu.account.util.generic.dao;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@Data
public class Response implements Serializable {

	@Serial
	private static final long serialVersionUID = -255326865684431617L;

	private transient Map<String, Object> data = new LinkedHashMap<>();
	private transient Map<String, Object> error = new LinkedHashMap<>();

	public void putItem(String idField, Object value) {
		this.data.put(idField, value);
	}

	public void putError(String idField, Object value) {
		this.error.put(idField, value);
	}

	@SuppressWarnings("unchecked")
	public <T> T getItem(String idField) {
		return (T) this.data.get(idField);
	}

	@SuppressWarnings("unchecked")
	public <T> T getError(String idField) {
		return (T) this.error.get(idField);
	}
}

