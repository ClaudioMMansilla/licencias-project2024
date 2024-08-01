package envasadoAPP;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;


public class calendarJuliano {

	public static void main(String[] args) {

		int vidaUtil = 6;

		LocalDate hoy = LocalDate.now();
		int dayOfYear = hoy.getDayOfYear();
		LocalDate vencimiento = LocalDate.now().plusMonths(vidaUtil);

		LocalDate fechaHoy2 = LocalDate.now();
		String formatoFechaHoy2 = fechaHoy2.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));

		System.out.println("Fecha Local: "+ hoy);
		System.out.println("Dia del a�o: "+ dayOfYear);
		System.out.println("Vencimiento: "+ vencimiento);


		String formattedDateHoy = hoy.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
		System.out.println("Fecha hoy (formateado): " + formattedDateHoy);

		String formattedDateVenc = vencimiento.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
			System.out.println("Vencimiento (formateado): " + formattedDateVenc);
	}
}

