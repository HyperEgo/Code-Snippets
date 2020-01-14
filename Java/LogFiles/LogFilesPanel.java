import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Main panel to display LogFiles data.
 */
public class LogFilesPanel extends JPanel {

    private LogFilesSubPanel logFilesSubPanel;
    private LogFilesList logFilesList;

    private VideoSubPanel videoSubPanel;

    ILogFilesPresentationModel presenter;

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
        GridBagConstraints ancestorConstraints = new GridBagConstraints();

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
        ancestorConstraints.fill = GridBagConstraints.VERTICAL;
        ancestorConstraints.gridx=0;
        ancestorConstraints.gridy=0;
        ancestorConstraints.insets = new Insets(2,2,2,2);
        add(logFilesSubPanel, ancestorConstraints);

        ancestorConstraints.fill = GridBagConstraints.VERTICAL;
        ancestorConstraints.gridx=1;
        ancestorConstraints.gridy=0;
        ancestorConstraints.insets = new Insets(2,2,2,2);
        add(videoSubPanel, ancestorConstraints);
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
        private static final int SCROLL_WIDTH = 200;
        private static final int SCROLL_HEIGHT = 300;

        private static final int TEST_COUNTER = 22;

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
            GridBagConstraints c = new GridBagConstraints();

            /*
            Initialize components
             */
            logTextField = new JTextField(LogFilesSubPanel.FIELD_WIDTH / 2);
            logTextField.setEditable(true);
            logTextField.setText("Enter Log File # ");

            logList = list;
            final String title = new String("<html> Mark &nbsp &nbsp Log File </html>");
            scrollPane = new JScrollPane(logList);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
            scrollPane.getViewport().setView(list);
            scrollPane.setPreferredSize(new Dimension(LogFilesSubPanel.SCROLL_WIDTH, LogFilesSubPanel.SCROLL_HEIGHT));
            scrollPane.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(1,1,5,1), title,
                    TitledBorder.LEFT, TitledBorder.TOP));

            /*
            Add components to panel
             */
            // add text field
            c.gridx=0;
            c.gridy=0;
            c.weightx=0;
            c.insets = new Insets(1,1,5,1);
            c.anchor = GridBagConstraints.FIRST_LINE_START;
            add(logTextField, c);

            // add timer count down label - test
            c.gridx=0;
            c.gridy=1;
            c.weightx=0;
            c.insets = new Insets(1,1,1,1);
            c.anchor = GridBagConstraints.WEST;
            add(timerLabelCounter(TEST_COUNTER), c);

            // add scrollpane
            c.fill = GridBagConstraints.VERTICAL;
            c.gridx=0;
            c.gridy=2;
            c.weightx=0;
            c.insets = new Insets(1,1,1,1);
            c.anchor = GridBagConstraints.PAGE_START;
            add(scrollPane, c);
        }

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            /*
            catch logger property from pres model, add data to LogFiles list
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

    /**
     * Timer count down for JLabel.
     * @param num - int
     * @return - JPanel
     */
    private JPanel timerLabelCounter(int num) {
        JPanel p = new JPanel();
        JLabel label = new JLabel(String.valueOf(num),JLabel.CENTER);
        label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Dimension d = label.getPreferredSize();
        label.setPreferredSize(new Dimension(d.width + 60, d.height));
        p.add(label);
        TimerActionListener al = new TimerActionListener(num, label);
        return p;
    }

    /**
     * Timer count down class, JLabel.
     */
    private class TimerActionListener implements ActionListener {

        private static final int DELAY_MILLISECONDS = 1000;

        private int counter;
        private javax.swing.Timer timer;
        private JLabel label;

        /**
         * Constructor with int, JLabel arguments.
         * @param ct - int
         * @param la - JLabel
         */
        public TimerActionListener(int ct, JLabel la) {
            counter = ct;
            label = la;
            timer = new javax.swing.Timer(DELAY_MILLISECONDS, this);
            timer.start();
        }

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            --counter;
            label.setText("" + counter);
            if ( counter <= 0 ) timer.stop();
        }
    }
}
