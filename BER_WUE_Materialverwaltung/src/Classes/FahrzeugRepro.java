package Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class FahrzeugRepro {

	private static final String SQL_FIND_ALL = "SELECT id, \"rufname\", \"bezeichnung\", \"typ\" FROM \"fahrzeuge\" ORDER BY \"rufname\"";

	public List<FahrzeugDto> findAll() throws SQLException {
		List<FahrzeugDto> list = new ArrayList<>();

		try (Connection con = DbUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id"); // oder rs.getInt(1)
				String Rufname = rs.getString("rufname"); // oder rs.getString(2)
				String Bezeichnung = rs.getString("bezeichnung");
				String typ = rs.getString("typ");
				list.add(new FahrzeugDto(id, Rufname, Bezeichnung, typ));
			}
		}
		return list;
	}

	public void modifyVehicle(JTable table, int row) throws SQLException {

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Integer id = (Integer) model.getValueAt(row, 0);
		String rufname = (String) model.getValueAt(row, 1);
		String bezeichnung = (String) model.getValueAt(row, 2);
		String typ = (String) model.getValueAt(row, 3);

		try (Connection con = DbUtil.getConnection()) {

			if (id == null) {
				// NEUER DATENSATZ → ID wird automatisch erzeugt
				String insertSql = "INSERT INTO \"fahrzeuge\" (\"rufname\", \"bezeichnung\", \"typ\")"
						+ "VALUES (?, ?, ?) RETURNING \"id\"";

				try (PreparedStatement ps = con.prepareStatement(insertSql)) {
					ps.setString(1, rufname);
					ps.setString(2, bezeichnung);
					ps.setString(3, typ);

					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						int newId = rs.getInt(1);
						model.setValueAt(newId, row, 0);
					}
				}

			} else {
				// BESTEHENDER DATENSATZ → UPDATE statt UPSERT
				String updateSql = "UPDATE \"fahrzeuge\" " + "SET \"rufname\" = ?, " + "    \"bezeichnung\" = ?, "
						+ "    \"typ\" = ? " + "WHERE \"id\" = ?";

				try (PreparedStatement ps = con.prepareStatement(updateSql)) {
					ps.setString(1, rufname);
					ps.setString(2, bezeichnung);
					ps.setString(3, typ);
					ps.setInt(4, id);
					ps.executeUpdate();
				}
			}
		}
	}

	public void deleteVehicle(List<Integer> deletedIds) throws SQLException {

		String deleteSql = "DELETE FROM \"fahrzeuge\" WHERE \"id\" = ?";

		try (Connection con = DbUtil.getConnection(); PreparedStatement ps = con.prepareStatement(deleteSql)) {

			for (Integer id : deletedIds) {
				ps.setInt(1, id);
				ps.executeUpdate();
			}
		}

	}

}
