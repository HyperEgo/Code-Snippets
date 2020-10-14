/**
 * Test class for LogFiles bean.
 */
public class TestLogFiles implements ILogFiles {

    private String subject;
    private String resource;
    private int counter;
    private ButtonEnableState buttonEnableState;
    private ButtonClickState buttonClickState;

    /**
     * Constructor with args.
     * @param sub - String
     * @param res - String
     * @param ct - int
     */
    public TestLogFiles(String sub, String res, int ct) {
        subject = sub;
        resource = res;
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
