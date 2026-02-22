package Classes;

public class FahrzeugDto {


private final int id;
    private final String rufname;

    public FahrzeugDto(int id, String rufname) {
        this.id = id;
        this.rufname = rufname;
    }

    public int getId() { return id; }
    public String getRufname() { return rufname; }

    // Wichtig für die Anzeige in der JComboBox
    @Override
    public String toString() {
        return rufname;
    }
}

