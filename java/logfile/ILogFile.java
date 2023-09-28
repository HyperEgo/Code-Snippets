package com.ultimate-rad-games;

/**
 * Interface for LogFile bean.
 */
public interface ILogFile {

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
