package com.ultimate-rad-games;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class CellRender {

    DefaultListModel default = new DefaultListModel<Object>();
    Jlist jlist = new JList();
    jlist.setModel(default);
    jlist.setCellRenderer(createListCellRenderer());

    private DefaultListCellRenderer createListCellRenderer() {
        return new DefaultListCellRenderer() {
          @Override
          public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected
          boolean cellHasFocus) {
              JPanel panel = (Item)value;
              JPanel base = new JPanel();
              if(isSelected) {
                  panel.setBackground(list.getBackground());
                  panel.setForeground(list.getForeground());
                  panel.setBorder(BorderFactory.createLineBorder(Color.WHITE, 3, true));
              }
              else {
                  panel.setBackground(list.getBackground());
                  panel.setForeground(list.getForeground());
                  panel.setBorder(base.getBorder());
              }
          }
        };
    }

    jlist.addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if(!e.getValueIsAdjusting()) {
                ListType source = (ListType)e.getSource();
                ObjectType objType = (ObjectType)source.getSelectedValue();
                if(objType != null) { /* add funct */ }
            }
        }
    }

    jlist.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mousePressed(mouseEvent mouseEvent) {
            super.mousePressed(mouseEvent);
            if(SwingUtilities.isLeftMouseButton(mouseEvent)) {                
                int index = jlist.locationToIndex(mouseEvent.getPoint());

                if(index != -1 && jlist.isSelectionIndex(index)) {
                    ObjectType objType = (objType)jlist.getModel().getElementAt(index);
                    Rectangle rect = jlist.getCellBounds(index, index);
                    Point isPointWithinCell = new Point(mouseEvent.getX(), - rect.x, mouseEvent.getY() - rect.y);
                    Rectangle crossRect = new Rectangle(renderer.someButton.getX(), renderer.someButton.getY(),
                            renderer.someButton.getWidth(), renderer.someButton.getHeight());

                    if(crossRect.contains(isPointWithinCell)) {
                        JOptionPane.showMessageDialog(null, "Item clicked", "Pane title", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
            }
        }
    }
}
