package Classes;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class MainWindow extends JFrame {

	// DB Fehler.
	private final JComboBox<FahrzeugDto> fahrzeugCombo = new JComboBox<>();
	private final FahrzeugRepro FahrzeugRepro = new FahrzeugRepro();
	// Mock
	String[] fahrzeuge = { "RK WÜ 71/70", "RK WÜ 54/46/1", "RK WÜ 54/14/1" };
	// private final JComboBox<String> fahrzeugCombo = new JComboBox<>(fahrzeuge);

	public void startWindow() {
		// Titel des Fensters
		setTitle("Fahrzeugauswahl");
		// Größe des Fensters
		setSize(800, 600);
		// Programm beenden, wenn das Fenster geschlossen wird
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Fenster in der Bildschirmmitte anzeigen
		setLocationRelativeTo(null);

		setLayout(new BorderLayout());

		Dimension pref = fahrzeugCombo.getPreferredSize();
		fahrzeugCombo.setPreferredSize(new Dimension(150, pref.height));

		JButton ButtonChoose = new JButton("Auswahl bestätigen");

		ButtonChoose.addActionListener(e -> {
			try {
				HandleButtonChoose();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		JPanel contentLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		// JPanel contentRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		contentLeft.add(fahrzeugCombo);
		contentLeft.add(ButtonChoose);

		JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

		// pack(); // ideale Fenstergröße
		// Fenster sichtbar machen

		JButton createMaterial = new JButton("Material bearbeiten");
		createMaterial.addActionListener(e -> {
			try {
				createMaterial();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		JButton createStoragePoint = new JButton("Fahrzeugplatz bearbeiten");
		createStoragePoint.addActionListener(e -> {
			try {
				createStoragePoint();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		JButton createVehicle = new JButton("Fahrzeug bearbeiten");
		createVehicle.addActionListener(e -> {
			try {
				createVehicle();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});

		JButton createCheckPoints = new JButton("Stammsatz für Check erzeugen");
		createVehicle.addActionListener(e -> {
			createCheckPoints();
		});

		footerPanel.add(createMaterial);
		footerPanel.add(createStoragePoint);
		footerPanel.add(createVehicle);
		footerPanel.add(createCheckPoints);

		add(contentLeft, BorderLayout.NORTH);
		add(footerPanel, BorderLayout.SOUTH);
		setVisible(true);

		// DB Fehler daher mit mock
		// DB
		fahrzeugCombo.setPrototypeDisplayValue(new FahrzeugDto(0, "XXXX", "", ""));
		loadFahrzeugeAsync();

	}

	private void createCheckPoints() {
		FahrzeugDto selected = (FahrzeugDto) fahrzeugCombo.getSelectedItem();

		if (selected.getRufname() == null || selected.getRufname().isBlank()) {
			JOptionPane.showMessageDialog(null, "Der Rufname darf nicht leer sein.", "Eingabefehler",
					JOptionPane.ERROR_MESSAGE);
		} else {
			// View Rufen dafür
		}

	}

	private void createVehicle() throws SQLException {
		WindowCreateVehicle CreateVeh = new WindowCreateVehicle();

		CreateVeh.setVisible(true);

	}

	private void createStoragePoint() throws SQLException {
		FahrzeugDto selected = (FahrzeugDto) fahrzeugCombo.getSelectedItem();

		if (selected.getRufname() == null || selected.getRufname().isBlank()) {
			JOptionPane.showMessageDialog(null, "Der Rufname darf nicht leer sein.", "Eingabefehler",
					JOptionPane.ERROR_MESSAGE);
		} else {
			WindowCreateStorage createStore = new WindowCreateStorage(selected.getRufname());
			createStore.setVisible(true);
		}
	}

	private void createMaterial() throws SQLException {

		WindowCreateMaterial CreateMat = new WindowCreateMaterial();
		CreateMat.setVisible(true);
	}

	private void HandleButtonChoose() throws SQLException {

		FahrzeugDto selected = (FahrzeugDto) fahrzeugCombo.getSelectedItem();
		// String selected = (String) fahrzeugCombo.getSelectedItem();

		String rufname = selected.getRufname();

		WindowVehChoose winVeh = new WindowVehChoose(rufname);
		winVeh.setVisible(true);
	}

	private void loadFahrzeugeAsync() {
		fahrzeugCombo.setEnabled(false);
		fahrzeugCombo.setModel(
				new DefaultComboBoxModel<>(new FahrzeugDto[] { new FahrzeugDto(-1, "Lade Daten....", "", "") }));

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
					// fahrzeugCombo.setModel(
					// new DefaultComboBoxModel<>(new FahrzeugDto[] { new FahrzeugDto(-1,
					// "<Fehler>") }));
				} finally {
					fahrzeugCombo.setEnabled(true);
				}
			}
		}.execute();
	}

}
