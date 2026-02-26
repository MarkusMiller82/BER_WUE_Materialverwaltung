package Classes;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

public class WindowmaintainVehicleMat extends JFrame {
	
	private List<Integer> deletedIds = new ArrayList<>();

	public WindowmaintainVehicleMat(String vehicle) throws SQLException {

		String title = "Fahrzeugmaterial pflegen für " + vehicle;
		setTitle(title);

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // nur dieses Fenster schließen
		// Größe des Fensters
		setSize(1000, 600);

		setLayout(new BorderLayout());
		JLabel vehicleLabel = new JLabel(vehicle);
		vehicleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(vehicleLabel, BorderLayout.NORTH);

		String[] columnNames = { "ID", "Materialbezeichnung", "Lagerort", "Anzahl Soll" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);
		
		EditableColumnRenderer renderer = new EditableColumnRenderer();
		
		JTable table = new JTable(model);
		TableStyler.applyHoverEffect(table);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		JScrollPane scrollPane = new JScrollPane(table);
		
		table.removeColumn(table.getColumnModel().getColumn(0)); // ID verstecken


		LagerungRepro lagerungRepro = new LagerungRepro();

		List<LagerungDto> listAll = lagerungRepro.findByRufname(vehicle);

		for (LagerungDto dto : listAll) {
			model.addRow(new Object[] { dto.getId(), // Integer oder null
					dto.getMaterialbezeichnung(), dto.getLagerort(), dto.getAnzahlSoll() });
		}
		
		MaterialRepro materialrepro = new MaterialRepro();

		List<MaterialDto> listAllMaterial = materialrepro.findAll();
		JComboBox<String> comboMat = new JComboBox<>();
		
		for (MaterialDto dto : listAllMaterial) {
			String s = dto.getMaterialbezeichnung();
			comboMat.addItem(s);
		}

		TableColumn col = table.getColumnModel().getColumn(0);
		col.setCellEditor(new DefaultCellEditor(comboMat));

		LagerortRepro Lagerortrepro = new LagerortRepro();

		List<LagerortDto> listAllStorage = Lagerortrepro.findAll(vehicle);
		JComboBox<String> comboStore = new JComboBox<>();
		
		for (LagerortDto dto : listAllStorage) {
			String s = dto.getLagerort();
			comboStore.addItem(s);
		}

		TableColumn colStore = table.getColumnModel().getColumn(1);
		colStore.setCellEditor(new DefaultCellEditor(comboStore));

		add(scrollPane, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton back = new JButton("Schließen");
		back.addActionListener(e -> {
			dispose(); 
		});

		JButton save = new JButton("Speichern");
		back.addActionListener(e -> {
			try {
				save(table);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		});
		JButton addButton = new JButton("Neue Zeile");

		addButton.addActionListener(e -> {
			model.addRow(new Object[] { null, // ID (unsichtbar)
					"", "", ""  });
		});
		
		JButton deleteButton = new JButton("Löschen");
		deleteButton.addActionListener(e -> {
			try {
				delete(table);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		
		footerPanel.add(addButton);
		footerPanel.add(deleteButton);
		footerPanel.add(save);
		footerPanel.add(back);

		add(footerPanel, BorderLayout.SOUTH);

		setVisible(true);
	}

	private void delete(JTable table) throws SQLException {

		int row = table.getSelectedRow();

		if (row == -1) {
			JOptionPane.showMessageDialog(null, "Bitte eine Zeile auswählen.");
			return;
		}

		DefaultTableModel model = (DefaultTableModel) table.getModel();

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
	
	private void save(JTable table) throws SQLException {

		if (table.isEditing()) {
			table.getCellEditor().stopCellEditing();
		}

		List<LagerungDto> list = new ArrayList<>();
		DefaultTableModel model = (DefaultTableModel) table.getModel();

		LagerungRepro lagerungepro = new LagerungRepro();

		if (!deletedIds.isEmpty()) {
			lagerungepro.deleteStorageSet(deletedIds);
		}

		for (int row = 0; row < model.getRowCount(); row++) {

			/*
			 * // Mock lagerungepro.modifyStorageSet(table, row);
			 * 
			 * Integer id = (Integer) model.getValueAt(row, 0); String bezeichnung =
			 * (String) model.getValueAt(row, 2); String typ = (String)
			 * model.getValueAt(row, 3); String rufname = (String) model.getValueAt(row, 1);
			 * list.add(new LagerortDto(id, rufname, bezeichnung, typ));
			 * 
			 * System.out.println(id); System.out.println(bezeichnung);
			 * System.out.println("------");
			 */

		}

		deletedIds.clear();
		dispose();

	}
}
