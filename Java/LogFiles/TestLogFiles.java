/**
 * Test class for LogFiles bean.
 */
public class TestLogFiles implements ILogFiles {

    private String subject;
    private String resource;
    private LogFlagType type;
    private int counter;
    private ButtonEnableState buttonEnableState;
    private ButtonClickState buttonClickState;

    /**
     * Constructor with String, LogFlagType, int arguments.
     * @param sub - String
     * @param res - String
     * @param ty - LogFlagType
     * @param ct - int
     */
    public TestLogFiles(String sub, String res, LogFlagType ty, int ct) {
        subject = sub;
        resource = res;
        type = ty;
        counter = ct;
        buttonEnableState = ButtonEnableState.ENABLE;
        buttonClickState = ButtonClickState.NOCLICK;
    }

    @Override
    public String getSubject() { return subject; }

    @Override
    public void setSubject(String sub) { subject = sub; }

    @Override
    public String getResource() { return resource; }

    @Override
    public void setResource(String res) { resource = res; }

    @Override
    public LogFlagType getFlagType() { return type; }

    @Override
    public void setFlagType(LogFlagType flg) {
        type = flg;
    }

    @Override
    public int getCounter() {
        return counter;
    }

    @Override
    public void setCounter(int count) {
        counter = count;
    }

    @Override
    public ButtonEnableState getButtonEnableState() { return buttonEnableState; }

    @Override
    public void setButtonEnableState(ButtonEnableState bstate) { buttonEnableState = bstate; }

    @Override
    public ButtonClickState getButtonClickState() { return buttonClickState; }

    @Override
    public void setButtonClickState(ButtonClickState cstate) { buttonClickState = cstate; }
}
