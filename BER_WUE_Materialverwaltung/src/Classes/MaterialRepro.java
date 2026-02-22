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

	private static final String SQL_FIND_ALL = "SELECT id, \"Materialbezeichnung\" FROM \"Material\" ORDER BY \"Materialbezeichnung\"";

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

		String sql =
		        "INSERT INTO \"Material\" (\"ID\", \"Materialbezeichnung\") " +
		        "VALUES (?, ?) " +
		        "ON CONFLICT (\"ID\") DO UPDATE SET " +
		        "\"Materialbezeichnung\" = EXCLUDED.\"Materialbezeichnung\"";

		    try (Connection con = DbUtil.getConnection();
		         PreparedStatement ps = con.prepareStatement(sql)) {

		        
		            int  id = (int) model.getValueAt(row, 0);
		            String material = (String) model.getValueAt(row, 1);

		            // ID kann null sein → dann INSERT ohne ID
		            if (id == 0) {
		                ps.setNull(1, java.sql.Types.INTEGER);
		            } else {
		                ps.setInt(1, (int) id);
		            }

		            ps.setString(2, material);

		            ps.executeUpdate();
		        }
		    
	}

	
}
