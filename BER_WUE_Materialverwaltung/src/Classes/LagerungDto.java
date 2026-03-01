package Classes;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

public class LagerungDto {

	private final Integer id;
	private final String rufname;
	private final String Materialbezeichnung;
	private final String Lagerort;
	private final int Anzahl;
	private final int AnzahlSoll;
	private final Date  Ablaufdatum;

	public LagerungDto(Integer id, String rufname, String Materialbezeichnung, String Lagerort, int Anzahl, int AnzahlSoll, Date Ablaufdatum) {
		this.id = id;
		this.rufname = rufname;
		this.Materialbezeichnung = Materialbezeichnung;
		this.Lagerort = Lagerort;
		this.Anzahl = Anzahl;
		this.AnzahlSoll = AnzahlSoll;
		this.Ablaufdatum = Ablaufdatum;
	}

	public Integer getId() {
		return id;
	}

	public String getRufname() {
		return rufname;
	}

	// Wichtig für die Anzeige in der JComboBox
	@Override
	public String toString() {
		return rufname;
	}

	public Date getAblaufdatum() throws ParseException {
		
		if (this.Ablaufdatum != null){
			return Ablaufdatum;
		}else {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
            sdf.setLenient(false);
            java.util.Date parsed = sdf.parse("31.12.9999");
            return new java.sql.Date(parsed.getTime());
			 
		}
	}

	public String getMaterialbezeichnung() {
		return Materialbezeichnung;
	}

	public String getLagerort() {
		return Lagerort;
	}

	public int getAnzahl() {
		return Anzahl;
	}

	public int getAnzahlSoll() {
		return AnzahlSoll;
	}

}
