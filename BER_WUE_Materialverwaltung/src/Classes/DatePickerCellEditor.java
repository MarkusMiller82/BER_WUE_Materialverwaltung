package Classes;

import com.github.lgooddatepicker.components.DatePicker;
import com.github.lgooddatepicker.components.DatePickerSettings;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.sql.Date;
import java.time.LocalDate;

public class DatePickerCellEditor extends AbstractCellEditor implements TableCellEditor {

    private final DatePicker picker;

    public DatePickerCellEditor() {
        DatePickerSettings settings = new DatePickerSettings();
        settings.setFormatForDatesCommonEra("dd.MM.yyyy");
        picker = new DatePicker(settings);
    }

    @Override
    public Object getCellEditorValue() {
        LocalDate ld = picker.getDate();
        return (ld != null) ? Date.valueOf(ld) : null;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table, Object value, boolean isSelected, int row, int column) {

        if (value instanceof java.sql.Date sqlDate) {
            picker.setDate(sqlDate.toLocalDate());   // ✔️ richtige Umwandlung
        } else {
            picker.setDate(null);
        }

        return picker;
    }
}