package Classes;

public class LagerortDto {

	private final Integer id;
	private final String Lagerort;
	private final String rufname;

	public LagerortDto(Integer id, String Lagerort, String rufname) {
		this.id = id;
		this.Lagerort = Lagerort;
		this.rufname = rufname;
	}

	public Integer getId() {
		return id;
	}

	public String getLagerort() {
		return Lagerort;
	}

	// Wichtig für die Anzeige in der JComboBox
	@Override
	public String toString() {
		return Lagerort;
	}

	public String getRufname() {
		return rufname;
	}
	
}
