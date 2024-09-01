package com.devsu.account.util.utilities;

import java.math.BigDecimal;
import java.util.List;

public class DaoRequestUtil {
	
	private DaoRequestUtil() {}

	@SuppressWarnings("unchecked")
	public static <D> D getValue(Object paramValue, Class<D> classOfT) {
		if (paramValue != null && !classOfT.isInstance(paramValue)) {
			if (classOfT.equals(String.class)) {
				paramValue = StringUtil.parseString(paramValue);

			} else if (classOfT.equals(Boolean.class)) {
				paramValue = ObjectUtils.parseBoolean(paramValue);

			} else if (classOfT.equals(Long.class)) {
				paramValue = ObjectUtils.parseLong(paramValue);

			} else if (classOfT.equals(List.class)) {
				paramValue = DefaultHashMap.parseArrayList(paramValue);

			} else if (classOfT.equals(Integer.class)) {
				paramValue = Integer.valueOf(StringUtil.parseString(paramValue));

			} else if (classOfT.equals(BigDecimal.class)) {
				paramValue = new BigDecimal(String.valueOf(paramValue));
			}
		}

		return (D) paramValue;
	}

}
