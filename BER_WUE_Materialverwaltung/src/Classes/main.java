package Classes;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import com.formdev.flatlaf.FlatLightLaf;


public class main {

	public static void main(String[] args)  {
		
		FlatLightLaf.setup();
		
		UIManager.put("TableHeader.background", new java.awt.Color(230, 230, 230));
        UIManager.put("TableHeader.foreground", java.awt.Color.BLACK);
        UIManager.put("TableHeader.font", new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 13));


	    // 2. GUI starten
	    SwingUtilities.invokeLater(() -> {
	    	MainWindow window = new MainWindow();

			window.startWindow();
	    });

	}

}
