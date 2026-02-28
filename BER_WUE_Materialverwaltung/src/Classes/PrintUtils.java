package Classes;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;

public class PrintUtils {

    /** Erzeugt einen Button, der das übergebene Panel (inkl. Labels, Tabellen etc.) druckt. */
    public static JButton createPrintButtonForPanel(JPanel panel) {
        JButton btn = new JButton("Übersicht drucken");
        btn.addActionListener(e -> {
            PrinterJob job = PrinterJob.getPrinterJob();
            job.setJobName("Übersicht drucken");
            job.setPrintable(new Printable() {
                @Override
                public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
                    if (pageIndex > 0) return NO_SUCH_PAGE;

                    Graphics2D g2 = (Graphics2D) g;
                    // Auf den druckbaren Bereich verschieben
                    g2.translate(pf.getImageableX(), pf.getImageableY());

                    // Inhalt proportional skalieren, sodass er auf die Seite passt
                    double scaleX = pf.getImageableWidth() / panel.getWidth();
                    double scaleY = pf.getImageableHeight() / panel.getHeight();
                    double scale = Math.min(scaleX, scaleY);
                    if (scale < 1.0) {
                        g2.scale(scale, scale);
                    }

                    // Panel zeichnen
                    panel.printAll(g2);
                    return PAGE_EXISTS;
                }
            });

            if (job.printDialog()) {
                try {
                    job.print();
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(panel, "Drucken fehlgeschlagen: " + ex.getMessage(),
                            "Druckfehler", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return btn;
    }
}
