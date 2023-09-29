import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * LogFile presentation model.
 */
public class LogFileModel implements ILogFileModel {

    PropertyChangeSupport pcs;
    ILogFile loggerFiler;

    public LogFileModel() {
        this.pcs = new PropertyChangeSupport(this);
        this.loggerFiler = new DefaultLog();
    }

    @Override
    public void setData(ILogFile logFile) {
        ILogFile isNullLog = isNull(logFile);
        ILogFile oldValue = loggerFiler;

        this.loggerFiler = isNullLog;
        this.pcs.firePropertyChange(ILogFileModel.LOGGER_PROPERTY, oldValue, loggerFiler);
    }

    @Override
    public ILogFile getData() { return this.loggerFiler; }

    @Override
    public void addCustomPropertyListener(PropertyChangeListener l) { this.pcs.addPropertyChangeListener(l); }

    @Override
    public void removeCustomPropertyListener(PropertyChangeListener l) { this.pcs.removePropertyChangeListener(l); }

    /**
     * Check null value.
     * @param check ILogFile object input
     * @return ILogFile object output
     */
    private ILogFile isNull(ILogFile check) {
        if (check == null) { return new DefaultLog(); }
        else { return isNullParts(check); }
    }

    /**
     * Check null object parts.
     * @param parts ILogFile object input
     * @return ILogFile object output
     */
    private ILogFile isNullParts(ILogFile parts) {
        ILogFile defLog = new DefaultLog();
        if (parts.getSubject().equals(null)) {
            parts.setSubject(defLog.getSubject());
        }
        if (parts.getResource().equals(null)) {
            parts.setResource(defLog.getResource());
        }
        return parts;
    }

    private class DefaultLog implements ILogFile {

        private String subject;
        private String resource;

        public DefaultLog() {
            subject = getDefaultSubject();
            resource = getDefaultResource();
        }

        @Override
        public String getSubject() { return getDefaultSubject(); }

        @Override
        public void setSubject(String sub) { subject = sub; }

        @Override
        public String getResource() { return getDefaultResource(); }

        @Override
        public void setResource(String res) { resource = res; }

        @Override
        public int getCounter() { return 0; }

        @Override
        public void setCounter(int count) { }

        @Override
        public ButtonEnableState getButtonEnableState() { return ButtonEnableState.ENABLE; }

        @Override
        public void setButtonEnableState(ButtonEnableState bstate) { }

        @Override
        public ButtonClickState getButtonClickState() { return ButtonClickState.NOCLICK; }

        @Override
        public void setButtonClickState(ButtonClickState cstate) { }

        private String getDefaultSubject() { return new String("DEFAULT Log File Subject Line 112233.4455"); }

        private String getDefaultResource() { return new String("DEFAULT Resource Location REMOTE SERVER"); }
    }
}
