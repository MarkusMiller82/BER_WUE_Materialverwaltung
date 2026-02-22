package Classes;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

class EditableColumnRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Prüfen, ob die Zelle editierbar ist
        boolean editable = table.getModel().isCellEditable(row, column);

        if (!isSelected) {
            if (editable) {
                c.setBackground(new Color(230, 255, 230)); // hellgrün
            } else {
                c.setBackground(Color.WHITE); // Standard
            }
        }

        return c;
    }
}
