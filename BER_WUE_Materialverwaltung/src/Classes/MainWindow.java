package Classes;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MainWindow extends JFrame {

	//DB Fehler.
	private final JComboBox<FahrzeugDto> fahrzeugCombo = new JComboBox<>();
	private final FahrzeugRepro FahrzeugRepro = new FahrzeugRepro();
	// Mock
	String[] fahrzeuge = { "RK WÜ 71/70", "RK WÜ 54/46/1", "RK WÜ 54/14/1" };
	//private final JComboBox<String> fahrzeugCombo = new JComboBox<>(fahrzeuge);


	public void startWindow() {
		// Titel des Fensters
		setTitle("Fahrzeugauswahl");
		// Größe des Fensters
		setSize(600, 600);
		// Programm beenden, wenn das Fenster geschlossen wird
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Fenster in der Bildschirmmitte anzeigen
		setLocationRelativeTo(null);

		
		setLayout(new BorderLayout());


		JComboBox<String> comboFahrzeuge = new JComboBox<>(fahrzeuge);

		Dimension pref = fahrzeugCombo.getPreferredSize();
		fahrzeugCombo.setPreferredSize(new Dimension(150, pref.height));

		JButton ButtonChoose = new JButton("Auswahl bestätigen");

		
		ButtonChoose.addActionListener(e -> HandleButtonChoose());

		JPanel contentLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// JPanel contentRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		contentLeft.add(fahrzeugCombo);
		contentLeft.add(ButtonChoose);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		// pack(); // ideale Fenstergröße
		// Fenster sichtbar machen
		
		JButton createMaterial = new JButton("Material bearbeiten");
		createMaterial.addActionListener(e -> {
			createMaterial();
		});
		
		JButton createStoragePoint = new JButton("Fahrzeugplatz bearbeiten");
		createMaterial.addActionListener(e -> {
			createStoragePoint();
		});
		
		JButton createVehicle = new JButton("Fahrzeug bearbeiten");
		createMaterial.addActionListener(e -> {
			createVehicle();
		});
		
		footerPanel.add(createMaterial);
		footerPanel.add(createStoragePoint);
		footerPanel.add(createVehicle);


		add(contentLeft, BorderLayout.NORTH);
		add(footerPanel, BorderLayout.SOUTH);
		setVisible(true);

		// DB Fehler daher mit mock
		// DB
		fahrzeugCombo.setPrototypeDisplayValue(new FahrzeugDto(0, "XXXX"));
		loadFahrzeugeAsync();

	}

	private void createVehicle() {
		
		
	}

	private void createStoragePoint() {
		//FahrzeugDto selected = (FahrzeugDto) fahrzeugCombo.getSelectedItem();
		String selected = (String) fahrzeugCombo.getSelectedItem();
		
	}

	private void createMaterial() {
		
		WindowCreateMaterial CreateMat = new WindowCreateMaterial();
		CreateMat.setVisible(true);
	}

	private void HandleButtonChoose() {
		
		//FahrzeugDto selected = (FahrzeugDto) fahrzeugCombo.getSelectedItem();
		String selected = (String) fahrzeugCombo.getSelectedItem();

		WindowVehChoose winVeh = new WindowVehChoose(selected);
		winVeh.setVisible(true);
	}

	private void loadFahrzeugeAsync() {
		fahrzeugCombo.setEnabled(false);
		fahrzeugCombo.setModel(new DefaultComboBoxModel<>(new FahrzeugDto[] { new FahrzeugDto(-1, "Lade Daten....") }));
		
		new SwingWorker<DefaultComboBoxModel<FahrzeugDto>, Void>() {
			@Override
			protected DefaultComboBoxModel<FahrzeugDto> doInBackground() throws Exception {
				List<FahrzeugDto> Fahrzeuge = FahrzeugRepro.findAll();
				DefaultComboBoxModel<FahrzeugDto> model = new DefaultComboBoxModel<>();
				for (FahrzeugDto c : Fahrzeuge)
					model.addElement(c);
				return model;
			}

			@Override
			protected void done() {
				try {
					fahrzeugCombo.setModel(get());
				} catch (Exception ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(MainWindow.this, "Fehler beim Laden: " + ex.getMessage(), "DB-Fehler",
							JOptionPane.ERROR_MESSAGE);
					//fahrzeugCombo.setModel(
					//		new DefaultComboBoxModel<>(new FahrzeugDto[] { new FahrzeugDto(-1, "<Fehler>") }));
				} finally {
					fahrzeugCombo.setEnabled(true);
				}
			}
		}.execute();
	}

}
