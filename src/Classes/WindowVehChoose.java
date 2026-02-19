package Classes;

import javax.swing.*;
import java.awt.*;

public class WindowVehChoose extends JFrame {

	public WindowVehChoose(String vehicle) {
		

setTitle("Fahrzeugübersicht");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // nur dieses Fenster schließen
		// Größe des Fensters
		setSize(600, 600);

		JPanel contentLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
		contentLeft.add(new JLabel(vehicle), BorderLayout.CENTER);
        
		add(contentLeft);
        
        setVisible(true);

	}

}
