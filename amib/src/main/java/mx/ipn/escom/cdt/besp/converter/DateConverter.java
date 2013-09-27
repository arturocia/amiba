package mx.ipn.escom.cdt.besp.converter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.util.StrutsTypeConverter;

public class DateConverter extends StrutsTypeConverter {
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private Logger logger = Logger.getLogger(DateConverter.class);

	@SuppressWarnings("rawtypes")
	// Se suprimen debido a que la definición de la función no permite
	// parametrizar
	@Override
	public Object convertFromString(Map arg0, String[] dateS, Class arg2) {
		SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
		try {
			return (Date) formatter.parse(dateS[0]);
		} catch (ParseException e) {
			logger.warn("No se pudo parsear la cadena " + dateS[0]);
			return null;
		}

	}

	@SuppressWarnings("rawtypes")
	// Se suprimen debido a que la definición de la función no permite
	// parametrizar
	@Override
	public String convertToString(Map arg0, Object date) {
		SimpleDateFormat dateformat = new SimpleDateFormat(DATE_FORMAT);
		return dateformat.format(date);
	}
}