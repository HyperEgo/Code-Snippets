package com.ultimate-rad-games;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Main panel to display LogFile data.
 */
public class LogFilePanel extends JPanel {

    private LogFileSubPanel logFileSubPanel;
    private LogFileList logFileList;

    private VideoSubPanel videoSubPanel;

    private ILogFileModel presenter;

    private static final boolean DEBUG = true;

    /**
     * Constructor with presentation model argument.
     * @param pm ILogFileModel object input
     */
    public LogFilePanel(ILogFileModel pm) {
        setLayout(new GridBagLayout());
        setPanelDebugBorder(this, DEBUG, Color.ORANGE);
        presenter = pm;
        initComponents(this);
    }

    /**
     * Initialize components.
     * @param panel JPanel object input
     */
    private void initComponents(LogFilePanel panel) {
        videoSubPanel = new VideoSubPanel();
        logFileList = new LogFileList();
        logFileList.addCustomPropertyChangeListener(videoSubPanel);

        logFileSubPanel = new LogFileSubPanel(logFileList);
        presenter.addCustomPropertyListener(logFileSubPanel);

        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.fill = GridBagConstraints.VERTICAL;
        panelConstraints.gridx=0;
        panelConstraints.gridy=0;
        panelConstraints.insets = new Insets(2, 2, 2, 2);
        panel.add(logFileSubPanel, panelConstraints);

        panelConstraints.fill = GridBagConstraints.VERTICAL;
        panelConstraints.gridx = 1;
        panelConstraints.gridy = 0;
        panelConstraints.insets = new Insets(2, 2, 2, 2);
        panel.add(videoSubPanel, panelConstraints);
    }

    /**
     * Debugger method, sets JPanel color border.
     * @param panel JPanel panel input
     * @param debug boolean debugger
     * @param border Color object input
     */
    private void setPanelDebugBorder(JPanel panel, boolean debug, Color border) {
        final int BORDER_WIDTH = 1;
        if (debug) {
            panel.setBorder(BorderFactory.createMatteBorder(BORDER_WIDTH, BORDER_WIDTH,
                    BORDER_WIDTH, BORDER_WIDTH, border));
        }
    }

    /**
     * Sub panel, displays LogFile data in JList.
     */
    private class LogFileSubPanel extends JPanel implements PropertyChangeListener {

        private static final int FIELD_WIDTH = 20;
        private static final int SCROLL_WIDTH = 350;
        private static final int SCROLL_HEIGHT = 250;

        private JTextField logTextField;
        private LogFileList logList;
        private JScrollPane scrollPane;

        /**
         * Constructor with JList argument.
         * @param list LogFile list input
         */
        public LogFileSubPanel(LogFileList list) {
            setLayout(new GridBagLayout());
            setPanelDebugBorder(LogFileSubPanel.this, DEBUG, Color.BLUE);

            logTextField = new JTextField(LogFileSubPanel.FIELD_WIDTH / 2);
            logTextField.setEditable(true);
            logTextField.setText("Enter Log File # ");

            logList = list;
            final String title = new String("<html> JList with Custom Renderer + CountDown Timer </html>");
            scrollPane = new JScrollPane(logList);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.getViewport().setView(list);
            scrollPane.setPreferredSize(new Dimension(LogFileSubPanel.SCROLL_WIDTH, LogFileSubPanel.SCROLL_HEIGHT));
            scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(1,1,5,1), title,
                    TitledBorder.LEFT, TitledBorder.TOP));

            GridBagConstraints c = new GridBagConstraints();
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 0;
            c.gridy = 0;
            c.weightx = 0;
            c.insets = new Insets(1, 1, 1, 1);
            c.anchor = GridBagConstraints.PAGE_START;
            add(scrollPane, c);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {  // add logger to list
            if (evt.getPropertyName().equals(ILogFileModel.LOGGER_PROPERTY)) {
                ILogFile logger = (ILogFile)evt.getNewValue();
                logList.addData(logger);
            }
        }
    }

    /**
     * Sub panel, LogFile video play back features.
     */
    private class VideoSubPanel extends JPanel implements PropertyChangeListener {

        private JLabel previewLabel;

        public VideoSubPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            setPanelDebugBorder(VideoSubPanel.this, DEBUG, Color.GREEN);
            previewLabel = new JLabel("Preview video area ...");
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx = 0;
            c.gridy = 0;
            c.weighty = 0;
            c.anchor = GridBagConstraints.PAGE_START;
            c.insets = new Insets(1,1,1,1);
            add(previewLabel, c);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {

            if (evt.getPropertyName().equals(ILogFileModel.CONTENTS_PROPERTY)) {
                ILogFile logger = (ILogFile)evt.getNewValue();
                VideoSubPanel vPanel = VideoSubPanel.this;
                vPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(1,1,5,1), logger.getSubject(),
                    TitledBorder.LEFT, TitledBorder.TOP));
            }
        }
    }
}
