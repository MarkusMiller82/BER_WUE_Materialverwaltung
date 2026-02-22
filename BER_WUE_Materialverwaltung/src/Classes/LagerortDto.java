package Classes;

public class LagerortDto {

	private final int id;
	private final String Lagerort;

	public LagerortDto(int id, String Lagerort) {
		this.id = id;
		this.Lagerort = Lagerort;
	}

	public int getId() {
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
	
}
