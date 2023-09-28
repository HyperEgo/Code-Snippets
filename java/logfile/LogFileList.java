package com.ultimate-rad-games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Display log file data from presentation model (caught in panel class).
 */
public class LogFileList extends JList<ILogFile> implements PropertyChangeListener {

    private static final String COMMIT = "Commit";

    JButton commitButton;
    PropertyChangeSupport pcs;

    /**
     * Constructor.
     */
    public LogFileList() {

        commitButton = new JButton(COMMIT);
        setModel(new DefaultListModel<ILogFile>());
        setCellRenderer(createListCellRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setLayoutOrientation(JList.VERTICAL);
        setVisibleRowCount(-1);

        pcs = new PropertyChangeSupport(this);

        /**
         * Mouser listener - released
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                super.mouseReleased(mouseEvent);

                if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                    LogFileList list = LogFileList.this;
                    int index = list.locationToIndex(mouseEvent.getPoint());

                    if (index != -1 && list.isSelectedIndex(index)) {
                        ILogFile logger = (ILogFile)list.getModel().getElementAt(index);
                        Rectangle rect = list.getCellBounds(index, index);
                        Point pointWithinCell = new Point(mouseEvent.getX() - rect.x, mouseEvent.getY() - rect.y);
                        Rectangle crossRect = new Rectangle(commitButton.getX(), commitButton.getY(), 
                            commitButton.getWidth(), commitButton.getHeight());  // inconsistent ?

                        if ( crossRect.contains(pointWithinCell) ) {
                            setCommitButtonState(logger, ILogFile.ButtonClickState.NOCLICK);
                            LogFileList.this.repaint();
                        }
                    }
                }
            }
        });

        /**
         * Mouser listener - pressed
         */        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
                if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                    LogFileList list = LogFileList.this;
                    int index = list.locationToIndex(mouseEvent.getPoint());

                    if (index != -1 && list.isSelectedIndex(index)) {
                        ILogFile logger = (ILogFile)list.getModel().getElementAt(index);
                        Rectangle rect = list.getCellBounds(index, index);
                        Point pointWithinCell = new Point(mouseEvent.getX() - rect.x, mouseEvent.getY() - rect.y);
                        /**
                         * Approximate commit button location area for selected row.
                         */
                        Rectangle crossRect = new Rectangle(commitButton.getX(), commitButton.getY(), commitButton.getWidth(), commitButton.getHeight());  //?

                        if ( crossRect.contains(pointWithinCell) ) {
                            setCommitButtonState(logger, ILogFile.ButtonClickState.CLICK);
                            LogFileList.this.repaint();
                        }
                    }
                }
            }
        });
    }

    private void setCommitButtonState(ILogFile logger, ILogFile.ButtonClickState buttonState) { logger.setButtonClickState(buttonState); }

    private JButton getCommitButtonState(ILogFile logger) {
        JButton button = new JButton(COMMIT);
        final Color base = button.getBackground();

        if (logger.getButtonEnableState() == ILogFile.ButtonEnableState.ENABLE) {
            button.setEnabled(true);

            if (logger.getButtonClickState() == ILogFile.ButtonClickState.CLICK) { button.setBackground(Color.LIGHT_GRAY); 
            } else { button.setBackground(base); }
        } else { button.setEnabled(false); }
        return button;
    }

    /**
     * Custom renderer for JList, display ui elements only, no data logic.
     * @return DefaultListCellRenderer object output
     */
    private DefaultListCellRenderer createListCellRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                boolean cellHasFocus) {
                ILogFile logger = (ILogFile)value;
                JPanel panel = getCustomizedPanel(logger);

                if (isSelected) { panel.setBorder( BorderFactory.createLineBorder(Color.BLUE, 2, true) );
                } else { panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2)); }
                return panel;
            }
        };
    }

    /**
     * Custom panel method for renderer.
     * @param data ILogFile object input
     * @return JPanel object output
     */
    private JPanel getCustomizedPanel(ILogFile data) {

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        /**
         * timer countdown
         */
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(1, 1, 1, 1);
        panel.add(createLabelPanel(data.getCounter(), 17, Color.BLACK), c);

        /**
         * subject data
         */
        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(1, 1, 1, 1);
        panel.add(createLabelPanel(data.getSubject(), 75, Color.YELLOW), c);

        /**
         * commit button
         */
        c.gridx = 2;
        c.gridy = 0;
        c.weightx = 1;
        c.anchor = GridBagConstraints.WEST;
        commitButton = new JButton(COMMIT);
        commitButton = getCommitButtonState(data);
        c.insets = new Insets(1,1,1,1);
        panel.add(commitButton, c);

        return panel;
    }

    /**
     * Custom label with panel.
     * @param counter int counter
     * @param labelWidth int label width
     * @param border Color border
     * @return JPanel object output
     */
    private JPanel createLabelPanel(Object counter, int labelWidth, Color border) {
        final int lwidth = labelWidth;

        JPanel p = new JPanel();
        JLabel label = new JLabel(String.valueOf(counter),JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(border));

        Dimension d = label.getPreferredSize();
        label.setPreferredSize(new Dimension(lwidth + 30, d.height));
        p.add(label);

        return p;
    }

    /**
     * Add data to LogFile list, initialize Timer - start count down.
     * Add JList listener to Timer class to catch property.
     * @param logger ILogFile object input
     */
    public void addData(ILogFile logger) {
        DefaultListModel model = (DefaultListModel<ILogFile>)getModel();
        model.addElement(logger);

        TimerListener tcal = new TimerListener(model, model.indexOf(logger));
        tcal.addCustomPropertyChangeListener(LogFileList.this);
    }

    /**
     * Clear list data from LogFile list.
     */
    public void clearData() {
        DefaultListModel model = (DefaultListModel<ILogFile>)getModel();
        model.clear();
    }

    // Panel listeners and property change events
    public void removeCustomPropertyChangeListener(PropertyChangeListener l) { pcs.removePropertyChangeListener(l); }

    public void addCustomPropertyChangeListener(PropertyChangeListener l) { pcs.addPropertyChangeListener(l); }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {  // timer catch event property
        if (evt.getPropertyName().equals(ILogFilePresentationModel.TIMER_PROPERTY)) {
            int indx = (int)evt.getNewValue();

            DefaultListModel model = (DefaultListModel<ILogFile>)getModel();
            ILogFile loggy = (ILogFile)model.getElementAt(indx);
            loggy.setButtonEnableState(ILogFile.ButtonEnableState.DISABLE);
            
            LogFileList.this.repaint();
        }
    }
}
