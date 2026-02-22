package Classes;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;

public class WindowVehChoose extends JFrame {

	public WindowVehChoose(String vehicle) {

		setTitle("Fahrzeugübersicht");

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // nur dieses Fenster schließen
		// Größe des Fensters
		setSize(600, 600);

		setLayout(new BorderLayout());
		JLabel vehicleLabel = new JLabel(vehicle);
		vehicleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(vehicleLabel, BorderLayout.NORTH);

		String[] columnNames = { "Rufname", "Materialbezeichnung", "Lagerort", "Ablaufdatum" };
		DefaultTableModel model = new DefaultTableModel(columnNames, 0);

		model.addRow(new Object[] { "RK Wü 71/70", "Verbandspäckchen groß", "Rucksack RTW", "31.01.2026" });
		model.addRow(new Object[] { "RK Wü 71/70", "Verbandspäckchen klein", "Rucksack RTW", "31.01.2026" });
		model.addRow(new Object[] { "RK Wü 71/70", "Akrinor", "Rucksack RTW", "31.01.2026" });

		JTable table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);

		add(scrollPane, BorderLayout.CENTER);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		JButton startCheck = new JButton("Check Starten");
		startCheck.addActionListener(e -> startCheck(vehicle));
		JButton back = new JButton("Schließen");
		back.addActionListener(e -> {
		    dispose();   // schließt das aktuelle Fenster
		});


		footerPanel.add(startCheck);
		footerPanel.add(back);

		add(footerPanel, BorderLayout.SOUTH);


		setVisible(true);

	}
	
	private void startCheck(String vehicle) {
		dispose();
		WindowStartCheck winCheck = new WindowStartCheck(vehicle);
		winCheck.setVisible(true);
		
		
		
	}

}
