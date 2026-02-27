package Classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class WindowVehChoose extends JFrame {

	public WindowVehChoose(String vehicle) throws SQLException, ParseException {

		setTitle("Fahrzeugübersicht");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // nur dieses Fenster schließen
		// Größe des Fensters
		setSize(600, 600);

		setLayout(new BorderLayout());
		JLabel vehicleLabel = new JLabel(vehicle);
		vehicleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(vehicleLabel, BorderLayout.NORTH);

		String[] columnNames = { "ID", "Rufname", "Materialbezeichnung", "Lagerort", "Ablaufdatum" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		
		LagerungRepro lagerungRepro = new LagerungRepro( );
		
		List<LagerungDto> listAll = lagerungRepro.findAbgelaufenByRufname(vehicle);

		for (LagerungDto dto : listAll) {
			model.addRow(new Object[] { dto.getId(), // Integer oder null
					dto.getRufname(), dto.getMaterialbezeichnung(), dto.getLagerort(), dto.getAblaufdatum() });
		}
		
		
		/*
		 * model.addRow(new Object[] { "RK Wü 71/70", "Verbandspäckchen groß",
		 * "Rucksack RTW", "31.01.2026" }); model.addRow(new Object[] { "RK Wü 71/70",
		 * "Verbandspäckchen klein", "Rucksack RTW", "31.01.2026" }); model.addRow(new
		 * Object[] { "RK Wü 71/70", "Akrinor", "Rucksack RTW", "31.01.2026" });
		 */

		JTable table = new JTable(model);
		TableStyler.applyHoverEffect(table);
		JScrollPane scrollPane = new JScrollPane(table);
		
		table.removeColumn(table.getColumnModel().getColumn(0)); // ID verstecken
		table.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer());

		add(scrollPane, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton startCheck = new JButton("Check Starten");
		startCheck.addActionListener(e -> {
			try {
				startCheck(vehicle);
			} catch (SQLException | ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		JButton back = new JButton("Schließen");
		back.addActionListener(e -> {
		    dispose();   // schließt das aktuelle Fenster
		});

		
		footerPanel.add(startCheck);
		footerPanel.add(back);

		add(footerPanel, BorderLayout.SOUTH);


		setVisible(true);

	}
	

	private void startCheck(String vehicle) throws SQLException, ParseException {
		dispose();
		WindowStartCheck winCheck = new WindowStartCheck(vehicle);
		winCheck.setVisible(true);
		
	}

}
