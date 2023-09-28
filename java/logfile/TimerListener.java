package com.ultimate-rad-games;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Timer count down class for DefaultListModel,
 * notify via prop change event when timer expires.
 */
public class TimerListener implements ActionListener {

    private static final int DELAY_MILLISECONDS = 1000;

    private DefaultListModel<ILogFile> model;
    private ILogFile logger;
    private int index;
    private int counter;
    private javax.swing.Timer timer;

    private PropertyChangeSupport pcs;

    /**
     * Constructor with DefaultListModel, int arguments.
     * @param md DefaultListModel object list input
     * @param ind int index location
     */
    public TimerListener(DefaultListModel<ILogFile> md, int ind) {
        pcs = new PropertyChangeSupport(this);
        model = md;
        index = ind;
        logger = md.get(index);
        counter = logger.getCounter();
        timer = new javax.swing.Timer(DELAY_MILLISECONDS, this);
        startTimer();
    }

    public void startTimer() { timer.start(); }

    public void stopTimer() { timer.stop(); }

    public void removeCustomPropertyChangeListener(PropertyChangeListener l) { pcs.removePropertyChangeListener(l); }

    public void addCustomPropertyChangeListener(PropertyChangeListener l) { pcs.addPropertyChangeListener(l); }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        /*
         * Check timer count down value.
         * If zero, notify listeners.
         * Else not zero, decrement value, set model.
         */
        if ( counter <= 0 ) {
            stopTimer();
            pcs.firePropertyChange(ILogFileModel.TIMER_PROPERTY, null, index);
        }
        else {
            logger.setCounter(--counter);
            model.set(index, logger);
        }
    }
}
