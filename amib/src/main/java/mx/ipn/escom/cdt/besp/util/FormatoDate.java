package mx.ipn.escom.cdt.besp.util;
import java.text.SimpleDateFormat;
import java.util.Date;


public final class FormatoDate {
	
	private FormatoDate() {
	}

	public static String fechaFormatoCorto(Date date){
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(date);
	}
}
