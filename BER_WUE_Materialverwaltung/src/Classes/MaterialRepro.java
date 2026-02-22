package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class MaterialRepro {

	private static final String SQL_FIND_ALL = "SELECT id, \"materialbezeichnung\" FROM \"material\" ORDER BY \"materialbezeichnung\"";

	public List<MaterialDto> findAll() throws SQLException {
		List<MaterialDto> list = new ArrayList<>();

		try (Connection con = DbUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id"); // oder rs.getInt(1)
				String Materialbezeichnung = rs.getString("Materialbezeichnung"); // oder rs.getString(2)
				list.add(new MaterialDto(id, Materialbezeichnung));
			}
		}
		return list;
	}

	public void modifyMaterial(JTable table, int row) throws SQLException {

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		Integer id = (Integer) model.getValueAt(row, 0);
		String material = (String) model.getValueAt(row, 1);

		try (Connection con = DbUtil.getConnection()) {

			if (id == null) {
				// NEUER DATENSATZ → ID wird automatisch erzeugt
				String insertSql = "INSERT INTO \"material\" (\"materialbezeichnung\") "
						+ "VALUES (?) RETURNING \"id\"";

				try (PreparedStatement ps = con.prepareStatement(insertSql)) {
					ps.setString(1, material);

					ResultSet rs = ps.executeQuery();
					if (rs.next()) {
						int newId = rs.getInt(1);
						model.setValueAt(newId, row, 0); // ID zurück in JTable
					}
				}

			} else {
				// BESTEHENDER DATENSATZ → UPDATE statt UPSERT
				String updateSql = "UPDATE \"material\" SET \"materialbezeichnung\" = ? " + "WHERE \"id\" = ?";

				try (PreparedStatement ps = con.prepareStatement(updateSql)) {
					ps.setString(1, material);
					ps.setInt(2, id);
					ps.executeUpdate();
				}
			}
		}
	}

	public void deleteMaterial(List<Integer> deletedIds) throws SQLException {

		String deleteSql = "DELETE FROM \"material\" WHERE \"id\" = ?";

		try (Connection con = DbUtil.getConnection(); PreparedStatement ps = con.prepareStatement(deleteSql)) {

			for (Integer id : deletedIds) {
				ps.setInt(1, id);
				ps.executeUpdate();
			}
		}

	}

}
