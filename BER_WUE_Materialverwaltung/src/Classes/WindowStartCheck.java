package Classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class WindowStartCheck extends JFrame {

	public WindowStartCheck(String vehicle) {

		String title = "Fahzeugmaterialcheck" + vehicle;
		setTitle(title);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Größe des Fensters
		setSize(1000, 600);

		setLayout(new BorderLayout());

		String[] columnNames = { "id", "Rufname", "Materialbezeichnung", "Lagerort", "Anzahl Soll", "Anzahl",
				"Ablaufdatum" };

		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return column != 0 && column != 1 && column != 2 && column != 3 && column != 4;
			}
		};

		// mock
		model.addRow(
				new Object[] { 1 , "RK Wü 71/70", "Verbandspäckchen groß", "Rucksack RTW", 10, 5, Date.valueOf("2026-02-21") });
		model.addRow(
				new Object[] { 2 , "RK Wü 71/70", "Verbandspäckchen klein", "Rucksack RTW", 10, 3, Date.valueOf("2026-02-21") });
		model.addRow(new Object[] { 3 , "RK Wü 71/70", "Akrinor", "Rucksack RTW", 3, 0, Date.valueOf("2026-02-21") });

		EditableColumnRenderer renderer = new EditableColumnRenderer();

		JTable tableCheck = new JTable(model);
		TableStyler.applyHoverEffect(tableCheck);
		for (int i = 0; i < tableCheck.getColumnCount(); i++) {
			tableCheck.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		
		tableCheck.getColumnModel().getColumn(0).setMinWidth(0);
		tableCheck.getColumnModel().getColumn(0).setMaxWidth(0);
		tableCheck.getColumnModel().getColumn(0).setWidth(0);
		tableCheck.getColumnModel().getColumn(0).setPreferredWidth(0);

		JScrollPane scrollPane = new JScrollPane(tableCheck);
		add(scrollPane, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton startCheck = new JButton("Speichern");
		startCheck.addActionListener(e -> {
			try {
				saveCheck(tableCheck);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		JButton back = new JButton("Schließen");
		back.addActionListener(e -> {
			dispose(); // schließt das aktuelle Fenster
		});

		footerPanel.add(startCheck);
		footerPanel.add(back);

		add(footerPanel, BorderLayout.SOUTH);

		setVisible(true);

	}

	private void saveCheck(JTable tableCheck) throws SQLException {
		List<LagerungDto> list = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) tableCheck.getModel();

		LagerungRepro lagerungRepro = new LagerungRepro();

		for (int row = 0; row < model.getRowCount(); row++) {

			// Mock
			// lagerungRepro.updateLagerungFromTableRow(tableCheck, row);

			int id = (int) model.getValueAt(row, 0);
			String rufname = (String) model.getValueAt(row, 1);
			String bezeichnung = (String) model.getValueAt(row, 2);
			String lagerort = (String) model.getValueAt(row, 3);
			int anzahlSoll = (int) model.getValueAt(row, 4);
			int anzahl = (int) model.getValueAt(row, 5);
			Date ablaufdatum = (Date) model.getValueAt(row, 6);

			list.add(new LagerungDto(id, rufname, bezeichnung, lagerort, anzahl, anzahlSoll, ablaufdatum));

			System.out.println(anzahl);
			System.out.println(ablaufdatum);
			System.out.println("------");

		}
		
		dispose();

	}
}
