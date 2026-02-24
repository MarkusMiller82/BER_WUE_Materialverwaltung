package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class LagerortRepro {

	public List<LagerortDto> findAll(String rufname) throws SQLException {

		List<LagerortDto> list = new ArrayList<>();

		try (Connection con = DbUtil.getConnection()) {
			String SQL_FIND_ALL = "SELECT id, \"lagerort\", \"rufname\" FROM \"lagerorte\" "
					+ "WHERE \"rufname\" = ? ORDER BY \"lagerort\"";

			PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL);
			ps.setString(1, rufname);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id"); // oder rs.getInt(1)
				String Lagerort = rs.getString("Lagerort"); // oder rs.getString(2)
				list.add(new LagerortDto(id, Lagerort, rufname));
			}

		}
		return list;
	}

	public void modifyLagerort(JTable table, int row) throws SQLException {

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Integer id = (Integer) model.getValueAt(row, 0);
		String lagerort = (String) model.getValueAt(row, 1);
		String rufname = (String) model.getValueAt(row, 2);

		try (Connection con = DbUtil.getConnection()) {

			if (id == null) {
				// NEUER DATENSATZ → ID wird automatisch erzeugt
				String insertSql = "INSERT INTO \"lagerorte\" (\"lagerort\", \"rufname\")"
						+ "VALUES (?, ?) RETURNING \"id\"";

				try (PreparedStatement ps = con.prepareStatement(insertSql)) {
					ps.setString(1, lagerort);
					ps.setString(2, rufname);

					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						int newId = rs.getInt(1);
						model.setValueAt(newId, row, 0);
					}
				}

			} else {
				// BESTEHENDER DATENSATZ → UPDATE statt UPSERT
				String updateSql = "UPDATE \"lagerorte\" " + "SET \"lagerort\" = ?, \"rufname\" = ? WHERE \"id\" = ?";

				try (PreparedStatement ps = con.prepareStatement(updateSql)) {
					ps.setString(1, lagerort);
					ps.setString(2, rufname);
					ps.setInt(3, id);
					ps.executeUpdate();
				}
			}
		}
	}

	public void deleteLagerort(List<Integer> deletedIds) throws SQLException {

		String deleteSql = "DELETE FROM \"lagerorte\" WHERE \"id\" = ?";

		try (Connection con = DbUtil.getConnection(); PreparedStatement ps = con.prepareStatement(deleteSql)) {

			for (Integer id : deletedIds) {
				ps.setInt(1, id);
				ps.executeUpdate();
			}
		}

	}
}
