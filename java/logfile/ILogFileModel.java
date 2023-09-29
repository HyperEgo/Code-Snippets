import java.beans.PropertyChangeListener;

/**
 * Interface for LogFile presentation model.
 */
public interface ILogFileModel {

    String LOGGER_PROPERTY = "LOGGER_PROPERTY";
    String CONTENTS_PROPERTY = "CONTENTS_PROPERTY";
    String TIMER_PROPERTY = "TIMER_PROPERTY";

    void setData(ILogFile logFile);
    ILogFile getData();

    void addCustomPropertyListener(PropertyChangeListener l);
    void removeCustomPropertyListener(PropertyChangeListener l);
}
