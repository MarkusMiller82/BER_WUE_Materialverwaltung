package Classes;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LagerortRepro {
	
	private static final String SQL_FIND_ALL = "SELECT id, \"Lagerort\" FROM \"Lagerorte\" ORDER BY \"Lagerort\"";

	public List<LagerortDto> findAll() throws SQLException {
		List<LagerortDto> list = new ArrayList<>();

		try (Connection con = DbUtil.getConnection();
				PreparedStatement ps = con.prepareStatement(SQL_FIND_ALL);
				ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				int id = rs.getInt("id"); // oder rs.getInt(1)
				String Lagerort = rs.getString("Lagerort"); // oder rs.getString(2)
				list.add(new LagerortDto(id, Lagerort));
			}
		}
		return list;
	}


}
