package Classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class FahrzeugRepro {

	private static final String SQL_FIND_ALL = "SELECT id, \"rufname\" FROM \"fahrzeuge\" ORDER BY \"rufname\"";

	public List<FahrzeugDto> findAll() throws SQLException {
		List<FahrzeugDto> list = new ArrayList<>();

		try (Connection con = DbUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id"); // oder rs.getInt(1)
				String Rufname = rs.getString("Rufname"); // oder rs.getString(2)
				list.add(new FahrzeugDto(id, Rufname));
			}
		}
		return list;
	}

	// Beispiel: Suche, Filter, etc.
	public List<FahrzeugDto> searchByPrefix(String prefix) throws SQLException {
		String sql = "SELECT id, rufname FROM Fahrzeuge WHERE Rufname LIKE ? ORDER BY Rufname";
		List<FahrzeugDto> list = new ArrayList<>();
		try (Connection con = DbUtil.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, prefix + "%");
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					list.add(new FahrzeugDto(rs.getInt("id"), rs.getString("name")));
				}
			}
		}
		return list;
	}
}
