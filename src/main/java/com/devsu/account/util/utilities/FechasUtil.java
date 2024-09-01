package com.devsu.account.util.utilities;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import javax.xml.datatype.XMLGregorianCalendar;

import org.apache.commons.lang3.StringUtils;


public class FechasUtil {
	
	public static final String PATTERN_DATE= "dd/MM/yyyy";
	public static final String PATTERN_TIMESTAMP = "dd/MM/yyyy HH:mm:ss";

	private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN_TIMESTAMP, new Locale("es_ES")); 
	private static DateTimeFormatter formatoDdMmYyyy = DateTimeFormatter.ofPattern(PATTERN_DATE, new Locale("es_ES"));
	private static DateTimeFormatter formatoYyyy = DateTimeFormatter.ofPattern("yyyy", new Locale("es_ES"));
	private static DateTimeFormatter formatoMm = DateTimeFormatter.ofPattern("MM", new Locale("es_ES"));
	private static DateTimeFormatter formatoMmmm = DateTimeFormatter.ofPattern("MMMM", new Locale("es_ES"));
	private static DateTimeFormatter formatoDd = DateTimeFormatter.ofPattern("dd", new Locale("es_ES"));
	
	
	private FechasUtil() {
		throw new IllegalStateException("FechasUtil class");
	}

	public static LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
	    return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
	}
	
	public static String getDiaString(){
		LocalDate now = LocalDate.now();
		return formatoDdMmYyyy.format(now);
	}
	
	public static String getDiaHoraString(){
		LocalDateTime now = LocalDateTime.now();
		
		return dtf.format(now);
	}
	
	public static String getFechaFormato(Date fecha){
		if(fecha == null) {
			return null;
		}
		LocalDate date = convertToLocalDateViaSqlDate(fecha);
		return formatoDdMmYyyy.format(date);
	}
	
	public static Date getDateTimeFormat(String fecha){
		if(fecha == null) {
			return null;
		}
		return getFechaFormato(fecha, PATTERN_TIMESTAMP);
	}
	
	public static Date getFechaFormato(String fecha){
		if(fecha == null) {
			return null;
		}
		return getFechaFormato(fecha, PATTERN_DATE);
	}
	
	public static Date getFechaFormato(String fecha, String pattern){
		if(fecha == null) {
			return null;
		}
		
		return getFechaFormato(fecha, new SimpleDateFormat(pattern));
	}

	public static Date getFechaFormato(String date, SimpleDateFormat dateFormat) {
		if (StringUtils.isEmpty(date) || dateFormat == null) {
			return null;
		}
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String getAnioEnCurso(){
		LocalDate now = LocalDate.now();
		return formatoYyyy.format(now);
	}
	
	public static String getMesEnCurso(){
		LocalDate now = LocalDate.now();
		return formatoMm.format(now);
	}
	
	public static String getDiaEnCurso(){
		LocalDate now = LocalDate.now();
		return formatoDd.format(now);
	}
	
	public static Calendar getCurrentCalendar(){
		return Calendar.getInstance();
	}

	public static Date getCurrentDate(){
		return getCurrentCalendar().getTime();
	}
	
	public static Date getCurrentDateFormat() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(PATTERN_DATE);
		LocalDate fecha1 = LocalDate.now();
		// Convierte Date a String
		String fechaString = fecha1.format(dtf);
		// convierte String a Date
		return getFechaFormato(fechaString, PATTERN_DATE);
		
	}
		
	public static int getCurrentYear(){
		return getCurrentCalendar().get(Calendar.YEAR);
	}
	
	public static int getCurrentMonth(){
		//se suma 1 porque se cuentan los meses desde 0 a 11 
		return getCurrentCalendar().get(Calendar.MONTH)+1;
	}
	
	public static int getCurrentDayOfYear(){
		return getCurrentCalendar().get(Calendar.DAY_OF_YEAR);
	}
	
	public static int getCurrentDayOfMonth(){
		return getCurrentCalendar().get(Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 
	 * @return Dia del mes
	 */
	public static int getCurrentDayOfWeek(){
		return getCurrentCalendar().get(Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 
	 * @return Cantidad de dias de la semana en el mes. <br/>
	 * 		Ejemplo: si es el segundo miercoles del mes devuelve 2
	 */
	public static int getCurrentDayOfWeekInMonth(){
		return getCurrentCalendar().get(Calendar.DAY_OF_WEEK_IN_MONTH);
	}
	
	public static String getFechaFormatoMascar(Date fecha, SimpleDateFormat sdf){
		if(fecha == null) {
			return null;
		}
		return sdf.format(fecha);
	}
	
	/**
	 * @param 
	 * @return Devuelve el nombre del mes.<br/>
	 * 		Ejemplo: si el mes es 4 devuelve ABRIL
	 */
	public static String getMonthName(Date fecha) {
		LocalDate date = convertToLocalDateViaSqlDate(fecha);
		return formatoMmmm.format(date);
	}
	
	public static Date getPrimerDiaDelMes() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR),
		cal.get(Calendar.MONTH),
		cal.getActualMinimum(Calendar.DAY_OF_MONTH),
		cal.getMinimum(Calendar.HOUR_OF_DAY),
		cal.getMinimum(Calendar.MINUTE),
		cal.getMinimum(Calendar.SECOND));
		return cal.getTime();
	}

	public static Date getUltimoDiaDelMes() {
		Calendar cal = Calendar.getInstance();
		cal.set(cal.get(Calendar.YEAR),
		cal.get(Calendar.MONTH),
		cal.getActualMaximum(Calendar.DAY_OF_MONTH),
		cal.getMaximum(Calendar.HOUR_OF_DAY),
		cal.getMaximum(Calendar.MINUTE),
		cal.getMaximum(Calendar.SECOND));
		return cal.getTime();
	}
	
	public static Integer getAnioCursoDif(Integer dif){
		LocalDate now = LocalDate.now();
		String anio =  formatoYyyy.format(now);
		Integer anioResult = Integer.parseInt(anio);
		anioResult = anioResult - dif ;
		
		return anioResult;
	}
	
	public static String getTextoMes(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		String result = "";
		
		int month = calendar.get(Calendar.MONTH);
		
		switch(month){
		  case 0:
		    {
		      result="Enero";
		      break;
		    }
		  case 1:
		    {
		      result="Febrero";
		      break;
		    }
		  case 2:
		    {
		      result="Marzo";
		      break;
		    }
		  case 3:
		    {
		      result="Abril";
		      break;
		    }
		  case 4:
		    {
		      result="Mayo";
		      break;
		    }
		  case 5:
		    {
		      result="Junio";
		      break;
		    }
		  case 6:
		    {
		      result="Julio";
		      break;
		    }
		  case 7:
		    {
		      result="Agosto";
		      break;
		    }
		  case 8:
		    {
		      result="Septiembre";
		      break;
		    }
		  case 9:
		    {
		      result="Octubre";
		      break;
		    }
		  case 10:
		    {
		      result="Noviembre";
		      break;
		    }
		  case 11:
		    {
		      result="Diciembre";
		      break;
		    }
		  default:
		    {
		      result="Error";
		      break;
		    }
		}
		
		return result;
	}
	
	/**
	 * Obtiene fecha en algun formato, por ejemplo: dd/LL/yyyy 
	 */
	public static String getDateInString(Date date, String formato) {
		
		DateFormat df = new SimpleDateFormat(formato);
 
        //Formatted to String value
        return df.format(date);
	}
	
	public static String convertXMLGregorianCalendarToUtilString(XMLGregorianCalendar xmlGregorianCalendar, String format){
		DateFormat df = new SimpleDateFormat(format);
		 
        GregorianCalendar gCalendar = xmlGregorianCalendar.toGregorianCalendar();
 
        //Converted to date object
        Date date = gCalendar.getTime();
 
        //Formatted to String value
        return df.format(date);
	}
	

	public static Date convertXMLGregorianCalendarToUtilDate(XMLGregorianCalendar xmlGregorianCalendar){
		Date carDate =  new Date();
	    if(xmlGregorianCalendar!=null){
	    	XMLGregorianCalendar calendar = xmlGregorianCalendar;
	        carDate = new Date(calendar.toGregorianCalendar().getTimeInMillis());
	    }
	    return carDate;
	}

}
