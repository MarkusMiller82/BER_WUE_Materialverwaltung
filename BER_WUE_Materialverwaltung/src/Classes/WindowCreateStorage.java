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

public class WindowCreateStorage extends JFrame {

	private List<Integer> deletedIds = new ArrayList<>();

	public WindowCreateStorage(String rufname) throws SQLException {

		String title = "Fahrzeuglagerort anlegen für" + rufname;
		setTitle(title);
		setSize(1000, 600);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		String[] columnNames = { "id", "Lagerort", "Rufname" };

		DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
		
		public boolean isCellEditable(int row, int column) {
			return column != 0 && column != 3;
		}
	};

		model.setRowCount(0);
		LagerortRepro lagerortRepro = new LagerortRepro();

		List<LagerortDto> listAll = lagerortRepro.findAll(rufname);

		for (LagerortDto dto : listAll) {
			model.addRow(new Object[] { dto.getId(), // Integer oder null
					dto.getLagerort(), 
					dto.getRufname()});
		}

		EditableColumnRenderer renderer = new EditableColumnRenderer();

		JTable tableStorage = new JTable(model);
		TableStyler.applyHoverEffect(tableStorage);
		for (int i = 0; i < tableStorage.getColumnCount(); i++) {
			tableStorage.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}

		tableStorage.getColumnModel().getColumn(0).setMinWidth(0);
		tableStorage.getColumnModel().getColumn(0).setMaxWidth(0);
		tableStorage.getColumnModel().getColumn(0).setWidth(0);
		tableStorage.getColumnModel().getColumn(0).setPreferredWidth(0);
		
		TableStyler.applyCenterAlignment(tableStorage);
		tableStorage.setShowGrid(true);                 
		tableStorage.setGridColor(Color.LIGHT_GRAY);

		JButton addButton = new JButton("Neue Zeile");

		addButton.addActionListener(e -> {
			model.addRow(new Object[] { null, // ID (unsichtbar)
					"",rufname  });
		});

		JScrollPane scrollPane = new JScrollPane(tableStorage);
		add(scrollPane, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton saveMaterialTab = new JButton("Speichern");
		saveMaterialTab.addActionListener(e -> {
			try {
				saveStorage(tableStorage);
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
				deleteStorage(tableStorage);
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

	private void deleteStorage(JTable tableStore) throws SQLException {

		int row = tableStore.getSelectedRow();

		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Bitte eine Zeile auswählen.");
			return;
		}

		DefaultTableModel model = (DefaultTableModel) tableStore.getModel();

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

	private void saveStorage(JTable tableStore) throws SQLException {

		if (tableStore.isEditing()) {
			tableStore.getCellEditor().stopCellEditing();
		}

		List<LagerortDto> list = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) tableStore.getModel();

		LagerortRepro lagerortRepro = new LagerortRepro();

		if (!deletedIds.isEmpty()) {
			lagerortRepro.deleteLagerort(deletedIds);
		}

		for (int row = 0; row < model.getRowCount(); row++) {

			// Mock
			lagerortRepro.modifyLagerort(tableStore, row);

			Integer id = (Integer) model.getValueAt(row, 0);

			String lagerort = (String) model.getValueAt(row, 1);
			String rufname = (String) model.getValueAt(row, 2);
			list.add(new LagerortDto(id, lagerort, rufname));

			System.out.println(id);
			System.out.println(lagerort);
			System.out.println("------");

		}

		deletedIds.clear();
		dispose();

	}

}
