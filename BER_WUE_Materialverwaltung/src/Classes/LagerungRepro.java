package Classes;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
				+ "FROM \"lagerung\" " + "WHERE \"rufname\" = ? "
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
	public void updateLagerungFromTableRow(JTable table, int row) throws SQLException {

		int id = (int) table.getValueAt(row, 0);
		int anzahl = Integer.parseInt(table.getValueAt(row, 5).toString());
		java.sql.Date ablaufdatum = (java.sql.Date) table.getValueAt(row, 6);

		String sql = "UPDATE \"Lagerung\" SET " + "\"Anzahl\" = ? " + "\"Ablaufdatum\" = ? " + 
				"WHERE id = ?";

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
	
	public void modifyLagerort(JTable table, int row) throws SQLException {

	}

}
