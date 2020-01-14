import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Display LogFiles data, list format.
 * Includes UI elements: checkbox, button, count down timer.
 */
public class LogFilesList extends JList<ILogFiles> implements PropertyChangeListener {

    private static final String COMMIT = "Commit";

    JCheckBox checkbox;
    JButton commitButton;
    PropertyChangeSupport pcs;

    /**
     * Constructor.
     */
    public LogFilesList() {

        checkbox = new JCheckBox();
        commitButton = new JButton(COMMIT);
        setModel(new DefaultListModel<ILogFiles>());
        setCellRenderer(createListCellRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        setLayoutOrientation(JList.VERTICAL);
        setVisibleRowCount(-1);

        pcs = new PropertyChangeSupport(this);

        // mouse listener - released
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent mouseEvent) {
                super.mouseReleased(mouseEvent);
                if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                    LogFilesList list = LogFilesList.this;
                    int index = list.locationToIndex(mouseEvent.getPoint());
                    if (index != -1 && list.isSelectedIndex(index)) {
                        ILogFiles logger = (ILogFiles)list.getModel().getElementAt(index);
                        Rectangle rect = list.getCellBounds(index, index);
                        Point pointWithinCell = new Point(mouseEvent.getX() - rect.x, mouseEvent.getY() - rect.y);
                        Rectangle crossRect = new Rectangle(commitButton.getX(), commitButton.getY(), commitButton.getWidth(), commitButton.getHeight());
                        if ( crossRect.contains(pointWithinCell) ) {
                            setCommitButtonState(logger, ILogFiles.ButtonClickState.NOCLICK);
                            LogFilesList.this.repaint();
                        }
                    }
                }
            }
        });

        // mouse listener - pressed
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                super.mousePressed(mouseEvent);
                if (SwingUtilities.isLeftMouseButton(mouseEvent)) {
                    LogFilesList list = LogFilesList.this;
                    int index = list.locationToIndex(mouseEvent.getPoint());
                    if (index != -1 && list.isSelectedIndex(index)) {
                        ILogFiles logger = (ILogFiles)list.getModel().getElementAt(index);
                        Rectangle rect = list.getCellBounds(index, index);
                        Point pointWithinCell = new Point(mouseEvent.getX() - rect.x, mouseEvent.getY() - rect.y);

                        // Approximate checkbox area for selected list row.
//                        Rectangle crossRect = new Rectangle(checkbox.getX(), checkbox.getY(), checkbox.getWidth(), checkbox.getHeight());

                        // Approximate commit button area for selected row.
                        Rectangle crossRect = new Rectangle(commitButton.getX(), commitButton.getY(), commitButton.getWidth(), commitButton.getHeight());

                        if ( crossRect.contains(pointWithinCell) ) {
                            /*
                            Toggle area for checkbox.
                            Click area for commit button.
                             */
//                            pcs.firePropertyChange(ILogFilesPresentationModel.FLAGGED_PROPERTY, null, logger);
//                            setCheckBoxState(logger);
                            setCommitButtonState(logger, ILogFiles.ButtonClickState.CLICK);
                            LogFilesList.this.repaint();
                        }
                        else {
                            /*
                            Non-checkbox area (e.g. subject).
                             */
//                            pcs.firePropertyChange(ILogFilesPresentationModel.CONTENTS_PROPERTY, null, logger);
                        }
                    }
                }
            }
        });
    }

    private void setCheckBoxState(ILogFiles logState) {
        if (logState.getFlagType() == ILogFiles.LogFlagType.MARK) {
            logState.setFlagType(ILogFiles.LogFlagType.UNMARK);
        }
        else {
            logState.setFlagType(ILogFiles.LogFlagType.MARK);
        }
    }

    private JCheckBox getCheckBoxState(ILogFiles logCheck) {
        JCheckBox boxer = new JCheckBox();
        if (logCheck.getFlagType() == ILogFiles.LogFlagType.MARK) {
            boxer.setSelected(true);
            return boxer;
        }
        else {
            boxer.setSelected(false);
            return boxer;
        }
    }

    private void setCommitButtonState(ILogFiles logger, ILogFiles.ButtonClickState buttonState) {
        logger.setButtonClickState(buttonState);
    }

    private JButton getCommitButtonState(ILogFiles logger) {
        JButton button = new JButton(COMMIT);
        final Color base = button.getBackground();
        if (logger.getButtonEnableState() == ILogFiles.ButtonEnableState.ENABLE) {
            button.setEnabled(true);
            if (logger.getButtonClickState() == ILogFiles.ButtonClickState.CLICK) {
                button.setBackground(Color.LIGHT_GRAY);
            }
            else {
                button.setBackground(base);
            }
        }
        else {
            button.setEnabled(false);
        }
        return button;
    }

    /**
     * Custom renderer for JList, display ui elements only, no data logic.
     * @return - DefaultListCellRenderer
     */
    private DefaultListCellRenderer createListCellRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                ILogFiles logger = (ILogFiles)value;
                JPanel panel = getCustomizedPanel(logger);

                if (isSelected) {
                    panel.setBorder( BorderFactory.createLineBorder(Color.BLUE, 2, true) );
                }
                else {
                    panel.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
                }
                return panel;
            }
        };
    }

    /**
     * Custom panel method for renderer.
     * @param data - ILogFiles
     * @return - JPanel
     */
    private JPanel getCustomizedPanel(ILogFiles data) {

        // panel
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        // check box
        /*
        c.gridx=0;
        c.gridy=0;
        c.weightx=0;
        c.anchor = GridBagConstraints.WEST;
        checkbox = new JCheckBox();
        checkbox = getCheckBoxState(data);
        c.insets = new Insets(1,1,1,5);
        panel.add(checkbox, c);
        */

        // timer count down
        c.gridx=0;
        c.gridy=0;
        c.weightx=1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(1,1,1,1);
        panel.add(createLabelPanel(data.getCounter()), c);

        // commit button
        c.gridx=1;
        c.gridy=0;
        c.weightx=1;
        c.anchor = GridBagConstraints.WEST;
        commitButton = new JButton(COMMIT);
        commitButton = getCommitButtonState(data);
        c.insets = new Insets(1,1,1,1);
        panel.add(commitButton, c);

        // log data subject
        /*
        c.gridx=2;
        c.gridy=0;
        c.weightx=1;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(1,1,1,1);
        panel.add(new JLabel(data.getSubject()), c);
        */

        return panel;
    }

    /**
     * Create panel with label for input integer.
     * @param num
     * @return
     */
    private JPanel createLabelPanel(int num) {
        final int swidth = 17;  // static width
        JPanel p = new JPanel();
        JLabel label = new JLabel(String.valueOf(num),JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Dimension d = label.getPreferredSize();
        label.setPreferredSize(new Dimension(swidth + 30, d.height));
        p.add(label);
        return p;
    }

    /**
     * Add data to LogFiles list, initialize Timer - start count down.
     * Add JList listener to Timer class to catch property.
     * @param logger - ILogFiles
     */
    public void addData(ILogFiles logger) {
        DefaultListModel model = (DefaultListModel<ILogFiles>)getModel();
        model.addElement(logger);
        TimerCountActionListener tcal = new TimerCountActionListener(model, model.indexOf(logger));
        tcal.addCustomPropertyChangeListener(LogFilesList.this);
    }

    /**
     * Clear list data from LogFiles list.
     */
    public void clearData() {
        DefaultListModel model = (DefaultListModel<ILogFiles>)getModel();
        model.clear();
    }

    public void removeCustomPropertyChangeListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    public void addCustomPropertyChangeListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        /*
        Catch timer property, disable button after timer expires.
         */
        if (evt.getPropertyName().equals(ILogFilesPresentationModel.TIMER_PROPERTY)) {
            int indx = (int)evt.getNewValue();
            DefaultListModel model = (DefaultListModel<ILogFiles>)getModel();
            ILogFiles loggy = (ILogFiles)model.getElementAt(indx);
            loggy.setButtonEnableState(ILogFiles.ButtonEnableState.DISABLE);
            LogFilesList.this.repaint();
        }
    }

    /**
     * Simulate mouse click programmatically (not used).
     * Reference only.
     * @param x - int
     * @param y - int
     * @throws AWTException
     */
    public static void roboClick(int x, int y) throws AWTException {
        Robot bot = new Robot();
        bot.mouseMove(x, y);
        bot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
        bot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
    }

}
