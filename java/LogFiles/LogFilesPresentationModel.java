import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * LogFiles presentation model class.
 */
public class LogFilesPresentationModel implements ILogFilesPresentationModel {

    PropertyChangeSupport pcs;
    ILogFiles loggerFiler;

    /**
     * Constructor.
     */
    public LogFilesPresentationModel() {
        pcs = new PropertyChangeSupport(this);
        loggerFiler = new DefaultLogFiles();
    }

    @Override
    public void setData(ILogFiles logFiles) {
        /*
         * Check for null, save old value,
         * update local value, send data and fire event.
         */
        ILogFiles checkNullLogs = checkNull(logFiles);
        ILogFiles oldValue = loggerFiler;
        loggerFiler = checkNullLogs;
        pcs.firePropertyChange(ILogFilesPresentationModel.LOGGER_PROPERTY, oldValue, loggerFiler);
    }

    @Override
    public ILogFiles getData() {
        return loggerFiler;
    }

    @Override
    public void addCustomPropertyListener(PropertyChangeListener l) {
        pcs.addPropertyChangeListener(l);
    }

    @Override
    public void removeCustomPropertyListener(PropertyChangeListener l) {
        pcs.removePropertyChangeListener(l);
    }

    /**
     * Check null value.
     * @param check - ILogFiles
     * @return - ILogFiles
     */
    private ILogFiles checkNull(ILogFiles check) {
        if (check == null) {
            return new DefaultLogFiles();
        }
        else {
            return checkNullParts(check);
        }
    }

    /**
     * Check null object parts.
     * @param parts - ILogFiles
     * @return - ILogFiles
     */
    private ILogFiles checkNullParts(ILogFiles parts) {
        ILogFiles defaultLogs = new DefaultLogFiles();
        if (parts.getSubject().equals(null)) {
            parts.setSubject(defaultLogs.getSubject());
        }
        if (parts.getResource().equals(null)) {
            parts.setResource(defaultLogs.getResource());
        }
        return parts;
    }

    /**
     * Default LogFiles class.
     */
    private class DefaultLogFiles implements ILogFiles {

        private String subject;
        private String resource;

        /**
         * Constructor.
         */
        public DefaultLogFiles() {
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

        private String getDefaultSubject() {
            return new String("DEFAULT Log File Subject Line 112233.4455");
        }

        private String getDefaultResource() {
            return new String("DEFAULT Resource Location REMOTE SERVER");
        }
    }
}
