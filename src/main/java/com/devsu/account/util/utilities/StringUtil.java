package com.devsu.account.util.utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
	
	private static final String PATTERN_EMAIL =
			"[a-zA-Z0-9]+[.[a-zA-Z0-9_-]+]*@[a-z0-9][\\w\\.-]*[a-z0-9]\\.[a-z][a-z\\.]*[a-z]$";
	
	private static final Pattern PATTERN = Pattern.compile(PATTERN_EMAIL);

	public static boolean validateEmail(String email) {
		Matcher m = PATTERN.matcher(email);
		return m.matches();
	}

	public static String obtenerTipoEmision(String valorCombo) {
		if ("NORMAL".equalsIgnoreCase(valorCombo)) {
			return "1";
		}
		if ("INDISPONIBILIDAD DE SISTEMA".equalsIgnoreCase(valorCombo)) {
			return "2";
		}
		return null;
	}

	public static String obtenerNumeroTipoEmision(String tipoEmision) {
		String tipoEmisionStr = null;
		
		if ("1".equalsIgnoreCase(tipoEmision)) {
			tipoEmisionStr = "NORMAL";
		}
		if ("3".equalsIgnoreCase(tipoEmision)) {
			tipoEmisionStr = "BAJA CONECTIVIDAD";
		}
		if ("2".equalsIgnoreCase(tipoEmision)) {
			tipoEmisionStr = "INDISPONIBILIDAD DE SISTEMA";
		}
		
		return tipoEmisionStr;
	}

	public static String quitarEnters(String cadenConEnters) {
		String cadenaSinEnters = null;
		for (int x = 0; x < cadenConEnters.length(); x++) {
			if (cadenConEnters.charAt(x) == '\t') {
				cadenaSinEnters = cadenaSinEnters + cadenConEnters.charAt(x);
			}
		}
		return cadenaSinEnters;
	}

	public static boolean validarExpresionRegular(String patron, String valor) {
		if ((patron != null) && (valor != null)) {
			Pattern pattern = Pattern.compile(patron);
			Matcher matcher = pattern.matcher(valor);
			return matcher.matches();
		}
		return false;
	}

	public static String obtenerDocumentoModificado(String codDoc) {
		String docMod = null;
		
		if ("01".equals(codDoc)) {
			docMod = "FACTURA";
		}
		if ("04".equals(codDoc)) {
			docMod = "NOTA DE CRÉDITO";
		}
		if ("05".equals(codDoc)) {
			docMod = "NOTA DE DÉBITO";
		}
		if ("06".equals(codDoc)) {
			docMod = "GUÍA REMISIÓN";
		}
		if ("07".equals(codDoc)) {
			docMod = "COMPROBANTE DE RETENCIÖN";
		}
		return docMod;
	}
	
	public static String replaceValues(String texto, String... values) {
		int index = 0;
		String newText = texto;
		
		for (String val : values) {
			val = (val == null || "".equals(val)) ? "" : val;
			newText = replaceValue(newText, val, index);
			index++;
		}
		
		return newText;
	}
	
	public static boolean isNotEmpty (String value) {
		return (value != null && !value.isEmpty());
	}
	
	public static String replaceValue(String texto, String value, int index) {
		 return texto.replace("{"+index+"}", value);
	}
	
	public static String defaultValue(String value, String valueDefault) {
		if(value == null || "".equals(value)) {
			return valueDefault;
		}
		
		return value;
	}
	
	public static String lpad(String str, int width) {
        return String.format("%0" + width + "d", Integer.valueOf(str));
	}
	
	public static String trim(String value) {
		if(isNotEmpty(value)) {
			return value.trim();
		}
		return "";
	}
	
	public static String concatWithSpaceOrSymbol(String text, String... values) {
		String defaultText = isNotEmpty(text) ? text : " ";
		
		StringBuilder valueConcated = new StringBuilder();
		
		for(String value : values) {
			if(isNotEmpty(value)) {
				valueConcated.append(value);
				valueConcated.append(defaultText);
			}
		}
		
		
		return trim(valueConcated.toString());
	}
	
	public static String parseString(Object value) {
		return value == null ? null : String.valueOf(value);
	}
	
}
