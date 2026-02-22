package Classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Date;
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

public class WindowCreateMaterial extends JFrame {

	private List<Integer> deletedIds = new ArrayList<>();

	public WindowCreateMaterial() throws SQLException {

		setTitle("Material bearbeiten");
		setSize(1000, 600);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		String[] columnNames = { "id", "Materialbezeichnung" };

		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		// mock
		/*
		 * model.addRow(new Object[] { 1, "Verbandspäckchen groß" }); model.addRow(new
		 * Object[] { 2, "Verbandspäckchen klein" }); model.addRow(new Object[] { 3,
		 * "Akrinor" });
		 */

		model.setRowCount(0);
		MaterialRepro materialrepro = new MaterialRepro();

		List<MaterialDto> listAll = materialrepro.findAll();

		for (MaterialDto dto : listAll) {
			model.addRow(new Object[] { dto.getId(), // Integer oder null
					dto.getMaterialbezeichnung() // String
			});
		}

		EditableColumnRenderer renderer = new EditableColumnRenderer();

		JTable tableMaterial = new JTable(model);
		for (int i = 0; i < tableMaterial.getColumnCount(); i++) {
			tableMaterial.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		tableMaterial.getColumnModel().getColumn(0).setMinWidth(0);
		tableMaterial.getColumnModel().getColumn(0).setMaxWidth(0);
		tableMaterial.getColumnModel().getColumn(0).setWidth(0);
		tableMaterial.getColumnModel().getColumn(0).setPreferredWidth(0);

		JButton addButton = new JButton("Neue Zeile");

		addButton.addActionListener(e -> {
			model.addRow(new Object[] { null, // ID (unsichtbar)
					"", // Materialbezeichnung
			});
		});

		JScrollPane scrollPane = new JScrollPane(tableMaterial);
		add(scrollPane, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton saveMaterialTab = new JButton("Speichern");
		saveMaterialTab.addActionListener(e -> {
			try {
				saveMaterial(tableMaterial);
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
			deleteMaterial(tableMaterial);
		});

		footerPanel.add(addButton);
		footerPanel.add(deleteButton);
		footerPanel.add(saveMaterialTab);
		footerPanel.add(back);

		add(footerPanel, BorderLayout.SOUTH);

		setVisible(true);

	}

	private void deleteMaterial(JTable tableMaterial) {
		int row = tableMaterial.getSelectedRow();

		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Bitte eine Zeile auswählen.");
			return;
		}

		DefaultTableModel model = (DefaultTableModel) tableMaterial.getModel();

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

	private void saveMaterial(JTable tableMaterial) throws SQLException {
		if (tableMaterial.isEditing()) {
			tableMaterial.getCellEditor().stopCellEditing();
		}

		List<MaterialDto> list = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) tableMaterial.getModel();

		MaterialRepro materialRepro = new MaterialRepro();

		if (!deletedIds.isEmpty()) {
			materialRepro.deleteMaterial(deletedIds);
		}

		for (int row = 0; row < model.getRowCount(); row++) {

			// Mock
			materialRepro.modifyMaterial(tableMaterial, row);

			Integer id = (Integer) model.getValueAt(row, 0);
			String bezeichnung = (String) model.getValueAt(row, 1);
			list.add(new MaterialDto(id, bezeichnung));

			System.out.println(id);
			System.out.println(bezeichnung);
			System.out.println("------");

		}

		deletedIds.clear();
		dispose();

	}

}
