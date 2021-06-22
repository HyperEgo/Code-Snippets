import javax.swing.*;
import java.awt.*;

/**
 * Test Frame class for LogFiles panel.
 */
public class TestFrame extends JFrame {

    private static final int FRAME_WIDTH = 650;
    private static final int FRAME_HEIGHT = 550;

    private static final boolean DEBUGGER = true;

    public TestFrame(LogFilesPanel logFilesPanel) {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame();
        frame.setLayout(new GridBagLayout());

        GridBagConstraints frameConstraints = new GridBagConstraints();
        frameConstraints.gridx = 0;
        frameConstraints.gridy = 0;
        frameConstraints.weighty = 1;
        frameConstraints.anchor = GridBagConstraints.NORTH;
        frameConstraints.insets = new Insets(15, 15, 15, 15);
        frame.add(logFilesPanel, frameConstraints);
        frame.setTitle("Outer Frame Border");

        if (DEBUGGER) {
            frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.MAGENTA));
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getHeight() / 2);
//        frame.setLocationRelativeTo(null);
//        frame.setResizable(false);
//        frame.setUndecorated(true);
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.pack();
        frame.setVisible(true);

    }  // end TestFrame ctor

    /**
     * Initialize, start Main
     * @param args - String[]
     */
    public static void main(String[] args) {

        LogFilesPresentationModel presenter = new LogFilesPresentationModel();
        LogFilesPanel panel = new LogFilesPanel(presenter);

        /*
        Set LogFile data using presentation model.
         */
        presenter.setData(new TestLogFiles("Test Subject Log 123456987", "System Root Drive", 99) );
        presenter.setData(new TestLogFiles("Test Subject Log 888889999.00001", "Share Drive", 88) );
        presenter.setData(new TestLogFiles("Test Short", "Flash Mem", 77) );
        presenter.setData(new TestLogFiles("Test Subject Long 645647+564+67489456149+87+44654+56", "The Cloud Somewhere on the Internets",66) );
        presenter.setData(new TestLogFiles("Star Date 82.001247-5858 2077 CyberPunk Space Ranger", "Satellite Array",55) );
        presenter.setData(new TestLogFiles("@#$@#$!@RETEWRGDSFBGRFYWE%$&%$W^&#$#", "NaN", 44) );

        new TestFrame(panel);

    }  // end Main

}  // end TestFrame class
