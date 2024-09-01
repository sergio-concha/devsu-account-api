package com.devsu.account.util.utilities;

import com.devsu.account.util.generic.dao.GenericDto;

public class DtoUtils {
	
	private DtoUtils() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean isPersistent(GenericDto<?> dto){
		return dto!=null && dto.getId()!=null;
	}
	
	public static boolean isNull(GenericDto<?> dto){
		return dto==null;
	}
	
	public static boolean isPersistence(GenericDto<?> dto){
		return dto!=null && dto.getId()!=null;
	}
	
	public static boolean isNotPersistence(GenericDto<?> dto) {
		return !isPersistence(dto);
	}
	
	public static boolean isNotNull(GenericDto<?> dto){
		return !isNull(dto);
	}
	
}
