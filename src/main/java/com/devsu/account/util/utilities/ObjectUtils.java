package com.devsu.account.util.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

public class ObjectUtils {

	private static final Logger LOG = LogManager.getLogger(ObjectUtils.class);

	private ObjectUtils() {
		throw new IllegalStateException("Utility class");
	}
	
	public static boolean isNull(Object dto){
		return dto==null;
	}

	public static boolean isNotNull(Object dto){
		return !isNull(dto);
	}
	
	public static boolean isEmpty(Collection<?> col) {
		return (col == null || col.isEmpty());
	}
	
	public static boolean isNotEmpty(Collection<?> col) {
		return !isEmpty(col);
	}
	
	public static <T> T defaultIfNull(T object, T defaultValue) {
		return object != null ? object : defaultValue;
	}

	/*
	 * Crea una nueva instancia del tipo del DTO indicado.
	 * Copia el id del DTO original.
	 * @param <T> Tipo del DTO, extiende de {@link GenericDTO}
	 * @param dto DTO original
	 * @return T
	 */
//	@SuppressWarnings("unchecked")
//	public static <T extends GenericDTO<T>> T buildInstanceWithId(GenericDTO<T> dto) {
//		T newDto = null;
//		try {
//			newDto = ((Class<T>) dto.getClass()).getDeclaredConstructor().newInstance();
//			newDto.setId(dto.getId());
//		} catch (Exception e) {
//			throw new IllegalArgumentException(e);
//		}
//		return newDto;
//	}

	/**
	 * Crea una nueva instancia del tipo del objeto indicado.
	 * Copia los campos indicados del objeto original.
	 * @param <T> Tipo del objeto
	 * @param obj objeto original
	 * @param fields String...
	 * @return T
	 */
	@SuppressWarnings("unchecked")
	public static <T> T buildInstanceWithFields(T obj, String... fields) {
		T newDto = null;
		try {
			newDto = ((Class<T>) obj.getClass()).getDeclaredConstructor().newInstance();
			for (String field : fields) {
				Field f = getField(obj.getClass(), field);
				f.setAccessible(true);
				f.set(newDto, f.get(obj));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newDto;
	}
	
	/**
	 * Crea una nueva instancia del tipo del objeto indicado.
	 * Copia los campos indicados del objeto original.
	 * @param <D> Tipo del objeto destino
	 * @param <T> Tipo del objeto original
	 * @param clazz clase del objeto destino
	 * @param obj objeto original
	 * @param fields String...
	 * @return D
	 */
	public static <D, T> D convertWithFields(Class<D> clazz, T obj, String... fields) {
		D newDto = null;
		try {
			newDto = clazz.getDeclaredConstructor().newInstance();
			for (String field : fields) {
				Field oField = obj.getClass().getDeclaredField(field);
				Field dField = getField(clazz, field);
				oField.setAccessible(true);
				dField.setAccessible(true);
				dField.set(newDto, oField.get(obj));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newDto;
	}

	
	/**
	 * Crea una nueva instancia del tipo del objeto indicado.
	 * Copia todos los campos posibles del objeto original
	 * (si se llaman igual y si son de tipos compatibles),
	 * exceptuando los indicados (opcional).
	 * @param <D> Tipo del objeto destino
	 * @param <T> Tipo del objeto original
	 * @param clazz clase del objeto destino
	 * @param obj objeto original
	 * @param excludedFields String... Campos a excluir
	 * @return D
	 */
	public static <D, T> D convertWithAllFields(Class<D> clazz, T obj, String... excludedFields) {
		/*D newDto = null;
		try {
			newDto = clazz.newInstance();
			List<Field> oFields = getAllFields(obj.getClass());
			for (Field oField : oFields) {
				if (Modifier.isStatic(oField.getModifiers())) // 
				try {
					Field dField = getField(clazz, oField.getName());
					if (Modifier.isStatic(dField.getModifiers())) // no copiar campos estaticos
						continue;
					if (!dField.getType().isAssignableFrom(oField.getType())) // no copiar tipos no compatibles
						continue;
					oField.setAccessible(true);
					dField.setAccessible(true);
					dField.set(newDto, oField.get(obj));
				} catch (NoSuchFieldException e) {
					continue;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newDto;*/
		D newDto = null;
		List<String> excludedFieldNames = Arrays.asList(excludedFields);
		try {
			newDto = clazz.getDeclaredConstructor().newInstance();
			List<Field> oFields = getAllFields(obj.getClass());
			for (Field oField : oFields) {
				if (excludedFieldNames.contains(oField.getName())) // no copiar campos excluidos
					continue;
				if (Modifier.isStatic(oField.getModifiers())) // no copiar campos estaticos
					continue;
				try {
					Field dField = getField(clazz, oField.getName());
					if (Modifier.isStatic(dField.getModifiers())) // no copiar campos estaticos
						continue;
					if (!dField.getType().isAssignableFrom(oField.getType())) // no copiar tipos no compatibles
						continue;
					oField.setAccessible(true);
					dField.setAccessible(true);
					if(oField.get(obj) instanceof Set){
						continue;
					}
					dField.set(newDto, oField.get(obj));
				} catch (NoSuchFieldException e) {
					continue;
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return newDto;
	}
	
	/**
     * Return the set of fields declared at all level of class hierarchy
     * @see <a href="http://stackoverflow.com/questions/3567372/access-to-private-inherited-fields-via-reflection-in-java">...</a>
     */
	private static List<Field> getAllFields(Class<?> clazz) {
		return getAllFieldsRec(clazz, new ArrayList<>());
	}

	private static List<Field> getAllFieldsRec(Class<?> clazz, List<Field> list) {
		Class<?> superClazz = clazz.getSuperclass();
		if (superClazz != null) {
			getAllFieldsRec(superClazz, list);
		}
		list.addAll(Arrays.asList(clazz.getDeclaredFields()));
		return list;
	}
	
	/**
	 * Returns a Field object that reflects the specified declared field of the class or interface represented by the Class object provided
	 * or its superclasses or superinterfaces.
	 * @param clazz
	 * @param name String that specifies the simple name of the desired field
	 * @return
	 * @throws NoSuchFieldException
	 */
	public static Field getField(Class<?> clazz, String name) throws NoSuchFieldException {
		try {
			return clazz.getDeclaredField(name);
		} catch (Exception e) {
			Class<?> superClazz = clazz.getSuperclass();
			if (superClazz != null) {
				return getField(superClazz, name);
			}
		}
        throw new NoSuchFieldException(name);
	}
	
//	public static <T> T getObjectWithField(T obj, String field, String value) {
//		T comprobante = obj;
//		Field f;
//		LOG.info("{}: {}", field, value);
//
//		if ("".equals(value) || value == null) {
//			return comprobante;
//		}
//
//		try {
//			f = ObjectUtils.getField(comprobante.getClass(), field);
//			f.setAccessible(true);
//			//f.set(comprobante, value);
//
//			if(f.getType() != null && (f.getType().getTypeName().contains("Long") || f.getType().getTypeName().contains("long")) ) {
//    			f.set(comprobante, Long.parseLong(value));
//			} else if (f.getType() != null && (f.getType().getTypeName().contains("Date")) ) {
//				f.set(comprobante, FechasUtil.getFechaFormato(value));
//			} else if (f.getType() != null && (f.getType().getTypeName().contains("BigDecimal")) ) {
//				f.set(comprobante, new BigDecimal(value));
//			} else if (f.getType() != null && (f.getType().getTypeName().contains("EstadoProceso")) ) {
//				f.set(comprobante, value.equals("AUTORIZADO") ? GenericDTO.EstadoProceso.AUTORIZADO : GenericDTO.EstadoProceso.NO_AUTORIZADO);
//			} else if (f.getType() != null && (f.getType().getTypeName().contains("TipoImpuestoIva")) ) {
//				f.set(comprobante, StringValuedEnumReflect.getEnumFromValue(GenericDTO.TipoImpuestoIva.class, value));
//			} else if (f.getType() != null && (f.getType().getTypeName().contains("EstadoSiNo")) ) {
//				f.set(comprobante, value.equals("SI") ? GenericDTO.EstadoSiNo.SI : GenericDTO.EstadoSiNo.NO);
//			} else {
//				f.set(comprobante, value);
//			}
//
//		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
//			LOG.error(e.getMessage());
//		}
//
//		return comprobante;
//	}

	public static Boolean parseBoolean(Object value) {
		if(value instanceof String valString) {
			return Boolean.parseBoolean(valString);

		} else if (value instanceof Boolean valBoolean) {
			return valBoolean;
		}
		return false;
	}

	public static Long parseLong(Object value) {
		if (value instanceof Long valLong)
			return valLong;

		else if (value instanceof Integer valInteger) {
			return valInteger.longValue();

		} else if (value instanceof String valString) {
			return Long.parseLong(valString);
		}
		return null;
	}
}
