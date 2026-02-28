package Classes;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

public class WindowVehChoose extends JFrame {

	public WindowVehChoose(String vehicle) throws SQLException, ParseException {

		String title = "Fahrzeugübersicht " + vehicle;
		setTitle(title);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // nur dieses Fenster schließen
		// Größe des Fensters
		setSize(1000, 600);

		JPanel main = new JPanel(new BorderLayout());
		
		String[] columnNames = { "ID", "Rufname", "Materialbezeichnung", "Lagerort", "Ablaufdatum" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		String[] columnNamesMissing = { "ID", "Rufname", "Materialbezeichnung", "Lagerort", "Anzahl Fehl" };
		DefaultTableModel modelMissing = new DefaultTableModel(columnNamesMissing, 0);

		LagerungRepro lagerungRepro = new LagerungRepro();

		List<LagerungDto> listAll = lagerungRepro.findAbgelaufenByRufname(vehicle);

		for (LagerungDto dto : listAll) {
			model.addRow(new Object[] { dto.getId(), // Integer oder null
					dto.getRufname(), dto.getMaterialbezeichnung(), dto.getLagerort(), dto.getAblaufdatum() });
		}

		List<LagerungDto> listAllMissing = lagerungRepro.findByRufname(vehicle);

		for (LagerungDto dtoMiss : listAllMissing) {
			if (dtoMiss.getAnzahlSoll() > dtoMiss.getAnzahl()) {
				Integer Missing = dtoMiss.getAnzahlSoll() - dtoMiss.getAnzahl();
				modelMissing.addRow(new Object[] { dtoMiss.getId(), // Integer oder null
						dtoMiss.getRufname(), dtoMiss.getMaterialbezeichnung(), dtoMiss.getLagerort(), Missing });
			}

		}

		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
		
		center.add(Box.createVerticalStrut(10)); // Abstand
		
		JLabel TabAbgelaufen = new JLabel("Abgelaufendes Material");

		center.add(TabAbgelaufen);
		center.add(Box.createVerticalStrut(10));

		JTable table = new JTable(model);
		TableStyler.applyHoverEffect(table);
		JScrollPane scrollPane = new JScrollPane(table);
		
		TableStyler.applyCenterAlignment(table);

		table.removeColumn(table.getColumnModel().getColumn(0)); // ID verstecken
		table.getColumnModel().getColumn(3).setCellRenderer(new DateRenderer());
		
		table.setShowGrid(true);                 
		table.setGridColor(Color.LIGHT_GRAY);

		center.add(scrollPane);

		center.add(Box.createVerticalStrut(20)); // Abstand zwischen den Blöcken

		JLabel TabMissing = new JLabel("Fehlendes Material");
		center.add(TabMissing);

		center.add(Box.createVerticalStrut(10)); // Abstand

		JTable tableMissing = new JTable(modelMissing);
		TableStyler.applyHoverEffect(table);
		JScrollPane scrollPaneMissing = new JScrollPane(tableMissing);

		tableMissing.removeColumn(tableMissing.getColumnModel().getColumn(0)); // ID verstecken
		TableStyler.applyCenterAlignment(tableMissing);
		

		tableMissing.setShowGrid(true);                 
		tableMissing.setGridColor(Color.LIGHT_GRAY); 


		center.add(scrollPaneMissing);
		
		main.add(center, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

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
			dispose(); // schließt das aktuelle Fenster
		});

		footerPanel.add(startCheck);
		footerPanel.add(back);
		JButton printBtn = PrintUtils.createPrintButtonForPanel(main);
		footerPanel.add(printBtn);
		
		add(main);
		add(footerPanel, BorderLayout.SOUTH);

		setVisible(true);

	}

	private void startCheck(String vehicle) throws SQLException, ParseException {
		dispose();
		WindowStartCheck winCheck = new WindowStartCheck(vehicle);
		winCheck.setVisible(true);

	}

}
