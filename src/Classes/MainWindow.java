package Classes;

import javax.swing.*;
import java.awt.*;

public class MainWindow extends JFrame {

	public void startWindow() {
		// Titel des Fensters
		setTitle("Fahrzeugauswahl");

		// Größe des Fensters
		setSize(600, 600);

		// Programm beenden, wenn das Fenster geschlossen wird
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Fenster in der Bildschirmmitte anzeigen
		setLocationRelativeTo(null);

		// Mock
		String[] fahrzeuge = { "RK WÜ 71/70", "RK WÜ 54/46/1", "RK WÜ 54/14/1" };

		JComboBox<String> comboFahrzeuge = new JComboBox<>(fahrzeuge);

		Dimension pref = comboFahrzeuge.getPreferredSize();
		comboFahrzeuge.setPreferredSize(new Dimension(150, pref.height));

		JButton ButtonChoose = new JButton("Auswahl bestätigen");


		int index = comboFahrzeuge.getSelectedIndex();
		String veh = fahrzeuge[index];
		
		ButtonChoose.addActionListener(e -> HandleButtonChoose(veh));

		JPanel contentLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// JPanel contentRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		contentLeft.add(comboFahrzeuge);
		contentLeft.add(ButtonChoose);

		add(contentLeft);

		// pack(); // ideale Fenstergröße
		// Fenster sichtbar machen
		setVisible(true);

	}

	private void HandleButtonChoose(String selected) {

		WindowVehChoose winVeh = new WindowVehChoose(selected);
		winVeh.setVisible(true);
	}

}
