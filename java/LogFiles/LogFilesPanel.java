import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Main panel to display LogFiles data.
 */
public class LogFilesPanel extends JPanel {

    private LogFilesSubPanel logFilesSubPanel;
    private LogFilesList logFilesList;

    private VideoSubPanel videoSubPanel;

    private ILogFilesPresentationModel presenter;

    private static final boolean DEBUGGER = true;

    /**
     * Constructor with presentation model argument.
     * @param pm - ILogFilesPresentationModel
     */
    public LogFilesPanel(ILogFilesPresentationModel pm) {
        setLayout(new GridBagLayout());
        setPanelDebugBorder(this, DEBUGGER, Color.ORANGE);
        presenter = pm;

        initComponents(this);
    }

    /**
     * Initialize components.
     * @param panel - JPanel
     */
    private void initComponents(LogFilesPanel panel) {

        videoSubPanel = new VideoSubPanel();

        /*
        Initialize logFilesList
         */
        logFilesList = new LogFilesList();
        logFilesList.addCustomPropertyChangeListener(videoSubPanel);

        /*
        Initialize logFilesSubPanel
         */
        logFilesSubPanel = new LogFilesSubPanel(logFilesList);
        presenter.addCustomPropertyListener(logFilesSubPanel);

        /*
        Add components to panel
         */
        GridBagConstraints panelConstraints = new GridBagConstraints();

        panelConstraints.fill = GridBagConstraints.VERTICAL;
        panelConstraints.gridx=0;
        panelConstraints.gridy=0;
        panelConstraints.insets = new Insets(2,2,2,2);
        panel.add(logFilesSubPanel, panelConstraints);

        panelConstraints.fill = GridBagConstraints.VERTICAL;
        panelConstraints.gridx=1;
        panelConstraints.gridy=0;
        panelConstraints.insets = new Insets(2,2,2,2);
        panel.add(videoSubPanel, panelConstraints);
    }

    /**
     * Debugger method, sets JPanel color border.
     * @param panel - JPanel
     * @param debug - boolean
     * @param border - Color
     */
    private void setPanelDebugBorder(JPanel panel, boolean debug, Color border) {
        final int BORDER_WIDTH = 1;
        if (debug) {
            panel.setBorder(BorderFactory.createMatteBorder(BORDER_WIDTH, BORDER_WIDTH,
                    BORDER_WIDTH, BORDER_WIDTH, border));
        }
    }

    /**
     * Sub panel, displays LogFiles data in JList.
     */
    private class LogFilesSubPanel extends JPanel implements PropertyChangeListener {

        private static final int FIELD_WIDTH = 20;
        private static final int SCROLL_WIDTH = 350;
        private static final int SCROLL_HEIGHT = 250;

        private JTextField logTextField;
        private LogFilesList logList;
        private JScrollPane scrollPane;

        /**
         * Constructor with JList argument.
         * @param list - LogFiles JList
         */
        public LogFilesSubPanel(LogFilesList list) {
            setLayout(new GridBagLayout());
            setPanelDebugBorder(LogFilesSubPanel.this, DEBUGGER, Color.BLUE);

            /*
            Initialize components
             */
            logTextField = new JTextField(LogFilesSubPanel.FIELD_WIDTH / 2);
            logTextField.setEditable(true);
            logTextField.setText("Enter Log File # ");

            logList = list;
            final String title = new String("<html> JList with Custom Renderer + CountDown Timer </html>");
            scrollPane = new JScrollPane(logList);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.getViewport().setView(list);
            scrollPane.setPreferredSize(new Dimension(LogFilesSubPanel.SCROLL_WIDTH, LogFilesSubPanel.SCROLL_HEIGHT));
            scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(1,1,5,1), title,
                    TitledBorder.LEFT, TitledBorder.TOP));

            GridBagConstraints c = new GridBagConstraints();

            // Add scrollpane to panel
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx=0;
            c.gridy=0;
            c.weightx=0;
            c.insets = new Insets(1,1,1,1);
            c.anchor = GridBagConstraints.PAGE_START;
            add(scrollPane, c);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            /**
             * Add data to JList in presentation model, caught here.
             */
            if (evt.getPropertyName().equals(ILogFilesPresentationModel.LOGGER_PROPERTY)) {
                ILogFiles logger = (ILogFiles)evt.getNewValue();
                logList.addData(logger);
            }
        }
    }

    /**
     * Sub panel, LogFiles video play back features.
     */
    private class VideoSubPanel extends JPanel implements PropertyChangeListener {

        private JLabel previewLabel;

        /**
         * Constructor.
         */
        public VideoSubPanel() {
            setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            setPanelDebugBorder(VideoSubPanel.this, DEBUGGER, Color.GREEN);
            previewLabel = new JLabel("Preview video area ...");
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx=0;
            c.gridy=0;
            c.weighty=0;
            c.anchor = GridBagConstraints.PAGE_START;
            c.insets = new Insets(1,1,1,1);
            add(previewLabel, c);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals(ILogFilesPresentationModel.CONTENTS_PROPERTY)) {
                ILogFiles logger = (ILogFiles)evt.getNewValue();
                VideoSubPanel vPanel = VideoSubPanel.this;
                vPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(1,1,5,1), logger.getSubject(),
                        TitledBorder.LEFT, TitledBorder.TOP));
            }
        }
    }
}
