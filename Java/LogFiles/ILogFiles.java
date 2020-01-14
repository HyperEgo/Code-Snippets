/**
 * Interface for LogFiles class bean.
 */
public interface ILogFiles {

    enum LogFlagType {MARK, UNMARK}
    enum ButtonEnableState {ENABLE, DISABLE}
    enum ButtonClickState {CLICK, NOCLICK}

    String getSubject();
    void setSubject(String sub);

    String getResource();
    void setResource(String res);

    LogFlagType getFlagType();
    void setFlagType(LogFlagType flg);

    int getCounter();
    void setCounter(int count);

    ButtonEnableState getButtonEnableState();
    void setButtonEnableState(ButtonEnableState bstate);

    ButtonClickState getButtonClickState();
    void setButtonClickState(ButtonClickState cstate);
}
