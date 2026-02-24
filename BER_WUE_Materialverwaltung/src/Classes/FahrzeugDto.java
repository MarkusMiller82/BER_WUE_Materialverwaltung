package Classes;

public class FahrzeugDto {


private final Integer id;
    private final String rufname;
    private final String bezeichnung;
    private final String typ;

    public FahrzeugDto(Integer id, String rufname, String bezeichnung, String typ) {
        this.id = id;
        this.rufname = rufname;
        this.bezeichnung = bezeichnung;
        this.typ = typ;
    }

    public Integer getId() { return id; }
    public String getRufname() { return rufname; }

    // Wichtig für die Anzeige in der JComboBox
    @Override
    public String toString() {
        return rufname;
    }

	public String getBezeichnung() {
		return bezeichnung;
	}

	public String getTyp() {
		return typ;
	}
}

