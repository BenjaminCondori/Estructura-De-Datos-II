package Utilidades;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class RenderTabla extends DefaultTableCellRenderer {
    
    DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
    
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof JButton) {
            JButton boton = (JButton)value;
            return boton;
        }
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
        table.getTableHeader().setReorderingAllowed(false);
        return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
