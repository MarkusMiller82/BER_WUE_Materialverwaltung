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

public class WindowCreateMaterial extends JFrame {

	public WindowCreateMaterial() {

		setTitle("Material bearbeiten");
		setSize(1000, 600);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		String[] columnNames = { "id", "Materialbezeichnung" };

		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		// mock
		model.addRow(new Object[] { 1, "Verbandspäckchen groß" });
		model.addRow(new Object[] { 2, "Verbandspäckchen klein" });
		model.addRow(new Object[] { 3, "Akrinor" });

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

		footerPanel.add(addButton);
		footerPanel.add(saveMaterialTab);
		footerPanel.add(back);

		add(footerPanel, BorderLayout.SOUTH);

		setVisible(true);

	}

	private void saveMaterial(JTable tableMaterial) throws SQLException {
		if (tableMaterial.isEditing()) {
			tableMaterial.getCellEditor().stopCellEditing();
		}

		List<MaterialDto> list = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) tableMaterial.getModel();

		MaterialRepro materialRepro = new MaterialRepro();

		for (int row = 0; row < model.getRowCount(); row++) {

			// Mock
			// materialRepro.modifyMaterial(tableMaterial, row);

			Integer id = (Integer) model.getValueAt(row, 0);
			String bezeichnung = (String) model.getValueAt(row, 1);
			list.add(new MaterialDto(id, bezeichnung));

			System.out.println(id);
			System.out.println(bezeichnung);
			System.out.println("------");

		}

		dispose();

	}

}
