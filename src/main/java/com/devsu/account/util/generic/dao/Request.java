package com.devsu.account.util.generic.dao;

import com.devsu.account.util.utilities.DaoRequestUtil;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Request implements Serializable {

	@Serial
	private static final long serialVersionUID = -7283212560993012718L;

	private transient Map<String, Object> parameters = new HashMap<>();

	public <D> D getParam(String idField, Class<D> classOfT) {
		Object paramValue = this.parameters.get(idField);
		return DaoRequestUtil.getValue(paramValue, classOfT);
	}
}
