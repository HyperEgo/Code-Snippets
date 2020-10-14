/**
 * Interface for LogFiles bean.
 */
public interface ILogFiles {

    enum ButtonEnableState {ENABLE, DISABLE}
    enum ButtonClickState {CLICK, NOCLICK}

    String getSubject();
    void setSubject(String sub);

    String getResource();
    void setResource(String res);

    int getCounter();
    void setCounter(int count);

    ButtonEnableState getButtonEnableState();
    void setButtonEnableState(ButtonEnableState bstate);

    ButtonClickState getButtonClickState();
    void setButtonClickState(ButtonClickState cstate);
}
