package com.pfa.ideationmemories;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class TooltipEnabledCellRenderer extends DefaultTableCellRenderer {
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
	{
		JLabel c= (JLabel)super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		c.setToolTipText(c.getText());
		return c;
	}
}
