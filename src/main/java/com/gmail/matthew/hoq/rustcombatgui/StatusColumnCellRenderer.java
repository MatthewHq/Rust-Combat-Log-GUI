package com.gmail.matthew.hoq.rustcombatgui;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import java.awt.Component;
import java.awt.Color;

//https://stackoverflow.com/questions/5673430/java-jtable-change-cell-color
public class StatusColumnCellRenderer extends DefaultTableCellRenderer {

  @Override
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
      int row, int col) {

    // Cells are by default rendered as a JLabel.
    JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

    // Get the status for the current row.
    // TableModel tableModel = table.getModel();
    // System.out.println(l.getText() + " WILL BE PARSED TO INT");
    try {
      float x = Float.parseFloat(l.getText());
      if (x < 10) {
        l.setBackground(Color.decode("#FF9985"));
      } else if (x < 20) {
        l.setBackground(Color.decode("#FFAD85"));
      } else if (x < 30) {
        l.setBackground(Color.decode("#FFC285"));
      } else if (x < 40) {
        l.setBackground(Color.decode("#FFD685"));
      } else if (x < 50) {
        l.setBackground(Color.decode("#FFEB85"));
      } else if (x < 60) {
        l.setBackground(Color.decode("#FFFF85"));
      } else if (x < 70) {
        l.setBackground(Color.decode("#EBFF85"));
      } else if (x < 80) {
        l.setBackground(Color.decode("#D6FF85"));
      } else if (x < 90) {
        l.setBackground(Color.decode("#C2FF85"));
      }else if (x < 101) {
        l.setBackground(Color.decode("#ADFF85"));
      }else if (x < 130) {
        l.setBackground(Color.decode("#85FFFF"));
      }
    } catch (Exception e) {
      // e.printStackTrace();
      // System.out.println("EMPTY STRING ");
    }

    // Return the JLabel which renders the cell.
    return l;

  }

}
