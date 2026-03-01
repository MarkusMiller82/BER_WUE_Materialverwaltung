package Classes;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class WindowCreateVehicle extends JFrame {

	private List<Integer> deletedIds = new ArrayList<>();

	public WindowCreateVehicle() throws SQLException {

		setTitle("Fahrzeug bearbeiten");
		setSize(1000, 600);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		String[] columnNames = { "id", "Rufname", "Bezeichnung", "Typ" };

		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		model.setRowCount(0);
		FahrzeugRepro fahrzeugrepro = new FahrzeugRepro();

		List<FahrzeugDto> listAll = fahrzeugrepro.findAll();

		for (FahrzeugDto dto : listAll) {
			model.addRow(new Object[] { dto.getId(), // Integer oder null
					dto.getRufname(), dto.getBezeichnung(), dto.getTyp() });
		}

		EditableColumnRenderer renderer = new EditableColumnRenderer();

		JTable tableVehicle = new JTable(model);
		TableStyler.applyHoverEffect(tableVehicle);
		for (int i = 0; i < tableVehicle.getColumnCount(); i++) {
			tableVehicle.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		tableVehicle.getColumnModel().getColumn(0).setMinWidth(0);
		tableVehicle.getColumnModel().getColumn(0).setMaxWidth(0);
		tableVehicle.getColumnModel().getColumn(0).setWidth(0);
		tableVehicle.getColumnModel().getColumn(0).setPreferredWidth(0);
		
		TableStyler.applyCenterAlignment(tableVehicle);
		tableVehicle.setShowGrid(true);                 
		tableVehicle.setGridColor(Color.LIGHT_GRAY);

		JButton addButton = new JButton("Neue Zeile");

		addButton.addActionListener(e -> {
			model.addRow(new Object[] { null, // ID (unsichtbar)
					"", "", "" });
		});

		JScrollPane scrollPane = new JScrollPane(tableVehicle);
		add(scrollPane, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton saveMaterialTab = new JButton("Speichern");
		saveMaterialTab.addActionListener(e -> {
			try {
				saveVehicle(tableVehicle);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		JButton back = new JButton("Schließen");
		back.addActionListener(e -> {
			dispose(); // schließt das aktuelle Fenster
		});

		JButton deleteButton = new JButton("Löschen");
		deleteButton.addActionListener(e -> {
			try {
				deleteVehicle(tableVehicle);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		footerPanel.add(addButton);
		footerPanel.add(deleteButton);
		footerPanel.add(saveMaterialTab);
		footerPanel.add(back);

		add(footerPanel, BorderLayout.SOUTH);

		setVisible(true);

	}

	private void deleteVehicle(JTable tableVehicle) throws SQLException {

		int row = tableVehicle.getSelectedRow();

		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Bitte eine Zeile auswählen.");
			return;
		}

		DefaultTableModel model = (DefaultTableModel) tableVehicle.getModel();

		// ID aus der Tabelle holen
		Object idObj = model.getValueAt(row, 0);
		Integer id = (idObj == null) ? null : (Integer) idObj;

		// Wenn die Zeile eine echte DB-ID hat → merken
		if (id != null) {
			deletedIds.add(id);
		}

		// Zeile aus der Tabelle entfernen
		model.removeRow(row);
	}

	private void saveVehicle(JTable tableVehicle) throws SQLException {

		if (tableVehicle.isEditing()) {
			tableVehicle.getCellEditor().stopCellEditing();
		}

		List<FahrzeugDto> list = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) tableVehicle.getModel();

		FahrzeugRepro fahrzeugRepro = new FahrzeugRepro();

		if (!deletedIds.isEmpty()) {
			fahrzeugRepro.deleteVehicle(deletedIds);
		}

		for (int row = 0; row < model.getRowCount(); row++) {

			// Mock
			fahrzeugRepro.modifyVehicle(tableVehicle, row);

			Integer id = (Integer) model.getValueAt(row, 0);
			String bezeichnung = (String) model.getValueAt(row, 2);
			String typ = (String) model.getValueAt(row, 3);
			String rufname = (String) model.getValueAt(row, 1);
			list.add(new FahrzeugDto(id, rufname, bezeichnung, typ));

			System.out.println(id);
			System.out.println(bezeichnung);
			System.out.println("------");

		}

		deletedIds.clear();
		dispose();

	}

}
