package Classes;

import java.sql.Date;
import java.time.LocalDate;

public class LagerungDto {

	private final int id;
	private final String rufname;
	private final String Materialbezeichnung;
	private final String Lagerort;
	private final int Anzahl;
	private final int AnzahlSoll;
	private final Date  Ablaufdatum;

	public LagerungDto(int id, String rufname, String Materialbezeichnung, String Lagerort, int Anzahl, int AnzahlSoll, Date Ablaufdatum) {
		this.id = id;
		this.rufname = rufname;
		this.Materialbezeichnung = Materialbezeichnung;
		this.Lagerort = Lagerort;
		this.Anzahl = Anzahl;
		this.AnzahlSoll = AnzahlSoll;
		this.Ablaufdatum = Ablaufdatum;
	}

	public int getId() {
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

	public Date getAblaufdatum() {
		return Ablaufdatum;
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
