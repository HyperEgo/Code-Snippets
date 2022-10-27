import java.beans.PropertyChangeListener;

/**
 * Interface for LogFiles presentation model class.
 */
public interface ILogFilesPresentationModel {

    String LOGGER_PROPERTY = "LOGGER_PROPERTY";
    String CONTENTS_PROPERTY = "CONTENTS_PROPERTY";
    String TIMER_PROPERTY = "TIMER_PROPERTY";

    void setData(ILogFiles logFiles);
    ILogFiles getData();

    void addCustomPropertyListener(PropertyChangeListener l);
    void removeCustomPropertyListener(PropertyChangeListener l);
}
