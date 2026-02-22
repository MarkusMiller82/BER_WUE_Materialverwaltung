package Classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;

public class LagerungRepro {

	private static final String SQL_FIND_ALL = "SELECT id, \"Rufname\", \"Materialbezeichnung\", \"Lagerort\", \"Anzahl\", \"Ablaufdatum\" "
			+ "FROM \"Lagerung\" ORDER BY \"Rufname\"";

	public List<LagerungDto> findAll() throws SQLException {
		List<LagerungDto> list = new ArrayList<>();

		try (Connection con = DbUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id"); // oder rs.getInt(1)
				String Materialbezeichnung = rs.getString("Materialbezeichnung");
				String Lagerort = rs.getString("Lagerort");
				String Rufname = rs.getString("Rufname");
				int Anzahl = rs.getInt("Anzahl");
				int AnzahlSoll = rs.getInt("AnzahlSoll");
				Date Ablaufdatum = rs.getDate("Ablaufdatum");
				list.add(new LagerungDto(id, Rufname, Materialbezeichnung, Lagerort, Anzahl, AnzahlSoll,Ablaufdatum));
			}
		}
		return list;
	}

	public List<LagerungDto> findAbgelaufenByRufname(String rufname) throws SQLException {

		String SQL_FIND_ALL_EXPIRED = "SELECT id, \"Rufname\", \"Materialbezeichnung\", \"Lagerort\", \"Anzahl\", \"Ablaufdatum\" "
				+ "FROM \"Lagerung\" " + "WHERE \"Ablaufdatum\" < CURRENT_DATE and \"Rufname\" = ? "
				+ "ORDER BY \"Rufname\"";

		List<LagerungDto> list = new ArrayList<>();

		try (Connection con = DbUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL_EXPIRED)) {
			ps.setString(1, rufname);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					int id = rs.getInt("id"); // oder rs.getInt(1)
					String Materialbezeichnung = rs.getString("Materialbezeichnung");
					String Lagerort = rs.getString("Lagerort");
					String Rufname = rs.getString("Rufname");
					int Anzahl = rs.getInt("Anzahl");
					int AnzahlSoll = rs.getInt("AnzahlSoll");
					Date Ablaufdatum = rs.getDate("Ablaufdatum");
					list.add(new LagerungDto(id, Rufname, Materialbezeichnung, Lagerort, Anzahl, AnzahlSoll, Ablaufdatum));
				}
			}
		}

		return list;
	}
	
	public void updateLagerungFromTableRow(JTable table, int row) throws SQLException {

	    int id = (int) table.getValueAt(row, 0);
	    String rufname = (String) table.getValueAt(row, 1);
	    String material = (String) table.getValueAt(row, 2);
	    String lagerort = (String) table.getValueAt(row, 3);
	    int anzahlSoll = Integer.parseInt(table.getValueAt(row, 4).toString());
	    int anzahl = Integer.parseInt(table.getValueAt(row, 5).toString());
	    java.sql.Date ablaufdatum = (java.sql.Date) table.getValueAt(row, 6);

	    String sql = 
	        "UPDATE \"Lagerung\" SET " +
	        "\"Anzahl\" = ? " +
	        "\"Ablaufdatum\" = ? " +
	        "\"AnzahlSoll\" = ? " +
	        "WHERE id = ?";

	    try (Connection con = DbUtil.getConnection();
	         PreparedStatement ps = con.prepareStatement(sql)) {

	    	ps.setInt(1, anzahl);
	    	ps.setDate(2, ablaufdatum);
	        ps.setInt(3, anzahlSoll);
	        ps.setInt(5, id);

	        ps.executeUpdate();
	    }
	}

}
