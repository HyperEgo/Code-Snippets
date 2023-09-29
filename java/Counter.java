package com.ultimate-rad-games;

import java.util.Properties;

import java.lang.Math;

/**
 * Counter interacts with iterated objects to provide step tracking.
 */
public class Counter {

    private static final String HDR = "Counter::";
    private static final int BASE = 1;

    private Properties cfg = null;    

    /**
     * Notch marker for step tracking inside loop iterations.
     * Count values are gauged with external loop, assumes standard clock time ticks.
     * <p>Enumeration is designed for use with positive counting loop increments.</p>
     */
    private enum PeriodType {
        SECOND(BASE),
        MINUTE(60 * PeriodType.SECOND.getValue()),
        HOUR(60 * PeriodType.MINUTE.getValue()),
        DAY(24 * PeriodType.HOUR.getValue()),
        WEEK(7 * PeriodType.DAY.getValue());

        private int period;

        /**
         * Public access constructor.
         * @param pd Time period interval value.
         */
        PeriodType(final int pd) { this.period = pd; }

        /**
         * Get time interval value.
         * @return Integer time period value.
         */
        public int getValue() { return this.period; }

        /**
         * Set time interval value.
         * @param pd Time period interval value.
         */
        public void setValue(final int pd) { this.period = pd; }
    }

    private final int EQUALIZER = 1000;
    private final int REMAINDER = 0;
    private final int DEFAULT = PeriodType.HOUR.getValue();

    private int count;
    private int step;
    private int divisor;

    /**
     * Public access constructor.
     * @param reference Integer external value to gauge loop increment.
     * @param prop Properties object for configuration file parameter input.
     */    
    public Counter(final int reference, final Properties prop) {
        this.cfg = prop;        
        this.count = 0;
        this.step = BASE * Math.abs(reference) / EQUALIZER;
        this.divisor = configureMode();
    }

    /**
     * Periodicity selector, parses String descriptor to integer value.
     * @param segment String time interval descriptor.
     * @return Integer time value.
     */
    private int selectPeriod(final String segment) {
        int value = 0;        
        for (PeriodType type : PeriodType.values()) {
            if (segment.equalsIgnoreCase(type.name())) {
                value = type.getValue();                
                break;
            }
        }
        return value;
    }

    /**
     * Configures count interval, manages property file parameter input.
     * @return Integer aggregate count interval value.
     */
    private int configureMode() {

        final String DEBUG = HDR + "configureMode()::";

        String period = cfg.getProperty("period", "MINUTE");
        String delay = cfg.getProperty("delay", "15");
        /**
         * Configure properties file or
         * hard-code period and delay values.
         * e.g. String period = PeriodType.MINUTE; int delay = 15; (15 min)
         */
        System.out.println(DEBUG + "period = " + period + " ::delay = " + delay);

        final int PERIOD_PROPERTY = selectPeriod(period);
        final int DELAY_PROPERTY = Integer.parseInt(delay);

        return Math.abs(PERIOD_PROPERTY * DELAY_PROPERTY);
    }    

    /**
     * Evaluates count condition.
     * @return boolean value, describes count interval condition.
     */
    public boolean evaluateMode() {

        if (this.divisor == 0 || this.divisor >= Integer.MAX_VALUE) { this.divisor = DEFAULT; }

        return (delay() && this.count % this.divisor == REMAINDER);
    }

    /**
     * Executes delay.
     * @return boolean value, manages delay condition.
     */
    private boolean delay() { return this.count != 0; }

    /**
     * Get count value.
     * @return Integer count value.
     */
    public int getCount() { return this.count; }

    /**
     * Get step value.
     * @return Integer step value.
     */
    public int getStep() { return this.step; }

    /**
     * Set step value.
     * @param stp Integer set step value.
     */
    public void setStep(final int stp) { this.step = stp; }

    /**
     * Increments count based on step value.
     */
    public void increment() { this.count = this.count + this.step; }

    /**
     * Clear count value.
     */
    public void clear() { this.count = 0; }
}
