package Classes;

import java.awt.Component;
import java.text.SimpleDateFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class DateRenderer extends DefaultTableCellRenderer {

    private final SimpleDateFormat displayFormat = new SimpleDateFormat("dd.MM.yyyy");

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        if (value instanceof java.util.Date d) {
            value = displayFormat.format(d);
        }

        if (value == null) {
            value = ""; // leere Anzeige statt "null"
        }

        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
    }
}
