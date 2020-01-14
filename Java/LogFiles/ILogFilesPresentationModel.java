import java.beans.PropertyChangeListener;

/**
 * Interface for LogFiles presentation model class.
 */
public interface ILogFilesPresentationModel {

    String LOGGER_PROPERTY = "logger";
    String FLAGGED_PROPERTY = "flagged";
    String CONTENTS_PROPERTY = "contents";
    String TIMER_PROPERTY = "timer";

    void setData(ILogFiles logFiles);
    ILogFiles getData();

    void addCustomPropertyListener(PropertyChangeListener l);
    void removeCustomPropertyListener(PropertyChangeListener l);
}
