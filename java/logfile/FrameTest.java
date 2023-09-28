package com.ultimate-rad-games;

import javax.swing.*;
import java.awt.*;

/**
 * Test Frame class for LogFile panel.
 */
public class FrameTest extends JFrame {

    private static final int FRAME_WIDTH = 650;
    private static final int FRAME_HEIGHT = 550;

    private static final boolean DEBUG = true;

    public FrameTest(LogFilePanel logFilePanel) {

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        JFrame frame = new JFrame();
        frame.setLayout(new GridBagLayout());

        GridBagConstraints frameConstraints = new GridBagConstraints();
        frameConstraints.gridx = 0;
        frameConstraints.gridy = 0;
        frameConstraints.weighty = 1;
        frameConstraints.anchor = GridBagConstraints.NORTH;
        frameConstraints.insets = new Insets(15, 15, 15, 15);
        frame.add(logFilePanel, frameConstraints);
        frame.setTitle("Outer Frame Border");

        if (DEBUG) { frame.getRootPane().setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.MAGENTA)); }

        /**
         * Faux parameters.
         * frame.setLocationRelativeTo(null);
         * frame.setResizable(false); 
         * frame.setUndecorated(true);
         */
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getHeight() / 2);
        frame.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Main()
     * @param args String[] input args
     */
    public static void main(String[] args) {

        LogFileModel presenter = new LogFileModel();
        LogFilePanel panel = new LogFilePanel(presenter);
        /**
         * Set LogFile data using presentation model.
         */
        presenter.setData(new LogFileTest("Test Subject Log 123456987", "System Root Drive", 99) );
        presenter.setData(new LogFileTest("Test Subject Log 888889999.00001", "Share Drive", 88) );
        presenter.setData(new LogFileTest("Test Short", "Flash Mem", 77) );
        presenter.setData(new LogFileTest("Test Subject Long 645647+564+67489456149+87+44654+56", "The Cloud Somewhere on the Internets", 66) );
        presenter.setData(new LogFileTest("Star Date 82.001247-5858 2077 CyberPunk Space Ranger", "Satellite Array", 55) );
        presenter.setData(new LogFileTest("@#$@#$!@RETEWRGDSFBGRFYWE%$&%$W^&#$#", "NaN", 44) );
        
        new FrameTest(panel);
    }
}
