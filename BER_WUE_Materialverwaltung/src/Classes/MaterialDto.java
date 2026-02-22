package Classes;

public class MaterialDto {
	
	private final Integer id;
	private final String Materialbezeichnung;

	public MaterialDto(Integer id, String Materialbezeichnung) {
		this.id = id;
		this.Materialbezeichnung = Materialbezeichnung;
	}

	public Integer getId() {
		return id;
	}

	public String getMaterialbezeichnung() {
		return Materialbezeichnung;
	}

	// Wichtig für die Anzeige in der JComboBox
	@Override
	public String toString() {
		return Materialbezeichnung;
	}


}
