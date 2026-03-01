package Classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LagerungRepro {

	private static final String SQL_FIND_ALL = "SELECT id, \"rufname\", \"materialbezeichnung\", \"lagerort\", \"anzahlsoll\", \"anzahl\", \"ablaufdatum\" "
			+ "FROM \"lagerung\" ORDER BY \"rufname\"";

	public List<LagerungDto> findAll() throws SQLException {
		List<LagerungDto> list = new ArrayList<>();

		try (Connection con = DbUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id"); // oder rs.getInt(1)
				String Materialbezeichnung = rs.getString("materialbezeichnung");
				String Lagerort = rs.getString("lagerort");
				String Rufname = rs.getString("rufname");
				int Anzahl = rs.getInt("anzahl");
				int AnzahlSoll = rs.getInt("anzahlsoll");
				Date Ablaufdatum = rs.getDate("ablaufdatum");
				list.add(new LagerungDto(id, Rufname, Materialbezeichnung, Lagerort, Anzahl, AnzahlSoll, Ablaufdatum));
			}
		}
		return list;
	}

	public List<LagerungDto> findAbgelaufenByRufname(String rufname) throws SQLException {

		String SQL_FIND_ALL_EXPIRED = "SELECT \"id\", \"rufname\", \"materialbezeichnung\", \"lagerort\", \"anzahlsoll\", \"anzahl\", \"ablaufdatum\" "
				+ "FROM \"lagerung\" " + "WHERE \"ablaufdatum\" < CURRENT_DATE AND \"rufname\" = ? "
				+ "ORDER BY \"rufname\"";

		List<LagerungDto> list = new ArrayList<>();

		try (Connection con = DbUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL_EXPIRED)) {
			ps.setString(1, rufname);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					int id = rs.getInt("id"); // oder rs.getInt(1)
					String Materialbezeichnung = rs.getString("materialbezeichnung");
					String Lagerort = rs.getString("lagerort");
					String Rufname = rs.getString("rufname");
					int Anzahl = rs.getInt("anzahl");
					int AnzahlSoll = rs.getInt("anzahlsoll");
					Date Ablaufdatum = rs.getDate("ablaufdatum");
					list.add(new LagerungDto(id, Rufname, Materialbezeichnung, Lagerort, Anzahl, AnzahlSoll,
							Ablaufdatum));
				}
			}
		}

		return list;
	}

	public List<LagerungDto> findByRufname(String rufname) throws SQLException {

		String SQL_FIND_ALL_EXPIRED = "SELECT \"id\", \"rufname\", \"materialbezeichnung\", \"lagerort\", \"anzahlsoll\", \"anzahl\", \"ablaufdatum\" "
				+ "FROM \"lagerung\" " + "WHERE \"rufname\" = ? " + "ORDER BY \"rufname\"";

		List<LagerungDto> list = new ArrayList<>();

		try (Connection con = DbUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL_EXPIRED)) {
			ps.setString(1, rufname);

			try (ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					int id = rs.getInt("id"); // oder rs.getInt(1)
					String Materialbezeichnung = rs.getString("materialbezeichnung");
					String Lagerort = rs.getString("lagerort");
					String Rufname = rs.getString("rufname");
					int Anzahl = rs.getInt("anzahl");
					int AnzahlSoll = rs.getInt("anzahlsoll");
					Date Ablaufdatum = rs.getDate("ablaufdatum");
					list.add(new LagerungDto(id, Rufname, Materialbezeichnung, Lagerort, Anzahl, AnzahlSoll,
							Ablaufdatum));
				}
			}
		}

		return list;
	}

	public void updateLagerungFromTableRow(JTable table, int row) throws SQLException {

		int id = (int) table.getValueAt(row, 0);
		int anzahl = Integer.parseInt(table.getValueAt(row, 5).toString());
		java.sql.Date ablaufdatum = (java.sql.Date) table.getValueAt(row, 6);

		String sql = "UPDATE \"Lagerung\" SET " + "\"Anzahl\" = ? " + "\"Ablaufdatum\" = ? " + "WHERE id = ?";

		try (Connection con = DbUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

			ps.setInt(1, anzahl);
			ps.setDate(2, ablaufdatum);
			ps.setInt(3, id);

			ps.executeUpdate();
		}
	}

	public void deleteStorageSet(List<Integer> deletedIds) throws SQLException {
		String deleteSql = "DELETE FROM \"lagerung\" WHERE \"id\" = ?";

		try (Connection con = DbUtil.getConnection(); PreparedStatement ps = con.prepareStatement(deleteSql)) {

			for (Integer id : deletedIds) {
				ps.setInt(1, id);
				ps.executeUpdate();
			}
		}
	}

	public void modifyStorageSet(JTable table, int row, String vehicle) throws SQLException {

		Integer AnzahlSoll;
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Integer id = (Integer) model.getValueAt(row, 0);
		String Materialbezeichnung = (String) model.getValueAt(row, 1);
		String Lagerort = (String) model.getValueAt(row, 2);
		if (model.getValueAt(row, 3) instanceof Integer) {
			AnzahlSoll = (Integer) model.getValueAt(row, 3);
		} else {
			AnzahlSoll = Integer.parseInt((String) model.getValueAt(row, 3));
		}
		try (Connection con = DbUtil.getConnection()) {

			if (id == null) {
				// NEUER DATENSATZ → ID wird automatisch erzeugt
				String insertSql = "INSERT INTO \"lagerung\" (\"rufname\", \"materialbezeichnung\", \"lagerort\", \"anzahlsoll\")"
						+ "VALUES (?, ?, ?, ?) RETURNING \"id\"";

				try (PreparedStatement ps = con.prepareStatement(insertSql)) {
					ps.setString(1, vehicle);
					ps.setString(2, Materialbezeichnung);
					ps.setString(3, Lagerort);
					ps.setInt(4, AnzahlSoll);

					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						int newId = rs.getInt(1);
						model.setValueAt(newId, row, 0);
					}
				}

			} else {
				// BESTEHENDER DATENSATZ → UPDATE statt UPSERT
				String updateSql = "UPDATE \"lagerung\" "
						+ "SET \"lagerort\" = ?, \"rufname\" = ?, \"materialbezeichnung\" = ?, \"anzahlsoll\" = ? WHERE \"id\" = ?";

				try (PreparedStatement ps = con.prepareStatement(updateSql)) {
					ps.setString(1, Lagerort);
					ps.setString(2, vehicle);
					ps.setString(3, Materialbezeichnung);
					ps.setInt(4, AnzahlSoll);
					ps.setInt(5, id);
					ps.executeUpdate();
				}
			}
		}

	}

	public void updateStorageSet(JTable table, int row, String vehicle) throws SQLException {

		DefaultTableModel model = (DefaultTableModel) table.getModel();
		Integer AnzahlSoll;
		Integer Anzahl;
		Integer id = (Integer) model.getValueAt(row, 0);
		String Materialbezeichnung = (String) model.getValueAt(row, 2);
		
		String Lagerort = (String) model.getValueAt(row, 3);
		
		if (model.getValueAt(row, 4) instanceof Integer) {
			AnzahlSoll = (Integer) model.getValueAt(row, 4);
		} else {
			AnzahlSoll = Integer.parseInt((String) model.getValueAt(row, 4));
		}
		
		if (model.getValueAt(row, 5) instanceof Integer) {
			Anzahl = (Integer) model.getValueAt(row, 5);
		} else {
			Anzahl = Integer.parseInt((String) model.getValueAt(row, 5));
		}
		
		
		Object raw = model.getValueAt(row, 6);
		java.sql.Date ablaufdatum = toSqlDate(raw);


		try (Connection con = DbUtil.getConnection()) {

			String updateSql = "UPDATE \"lagerung\" "
					+ "SET \"lagerort\" = ?, \"rufname\" = ?, \"materialbezeichnung\" = ?, \"anzahlsoll\" = ? , \"anzahl\" = ?, \"ablaufdatum\" = ? "
					+ "WHERE \"id\" = ?";

			try (PreparedStatement ps = con.prepareStatement(updateSql)) {
				ps.setString(1, Lagerort);
				ps.setString(2, vehicle);
				ps.setString(3, Materialbezeichnung);
				ps.setInt(4, AnzahlSoll);
				ps.setInt(5, Anzahl);
				ps.setDate(6, ablaufdatum);
				ps.setInt(7, id);
				ps.executeUpdate();
			}
		}
	}
	private java.sql.Date toSqlDate(Object value) {
	    if (value == null) {
	        return null;
	    }

	    if (value instanceof java.sql.Date d) {
	        return d;
	    }

	    if (value instanceof java.util.Date d) {
	        return new java.sql.Date(d.getTime());
	    }

	    if (value instanceof String s && !s.isBlank()) {
	        try {
	            // Eingabeformat: dd-MM-yyyy
	            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
	            sdf.setLenient(false);
	            java.util.Date parsed = sdf.parse(s);
	            return new java.sql.Date(parsed.getTime());
	        } catch (Exception e) {
	            return null;
	        }
	    }

	    return null;
	}

}
