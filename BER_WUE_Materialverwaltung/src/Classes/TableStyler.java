package Classes;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.*;

public class TableStyler {

    public static void applyHoverEffect(JTable table) {

        final int[] hoverRow = { -1 };

        // Hover erkennen
        table.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != hoverRow[0]) {
                    hoverRow[0] = row;
                    table.repaint();
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoverRow[0] = -1;
                table.repaint();
            }
        });

        // Renderer ohne Zebra-Streifen
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(new Color(51, 153, 255)); // Auswahl
                    c.setForeground(Color.WHITE);
                } 
                else if (row == hoverRow[0]) {
                    c.setBackground(new Color(230, 240, 255)); // Hover
                    c.setForeground(Color.BLACK);
                } 
                else {
                    c.setBackground(Color.WHITE); // Standard
                    c.setForeground(Color.BLACK);
                }

                return c;
            }
        });
    }
}
