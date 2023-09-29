public class LogFileTest implements ILogFile {

    private String subject;
    private String resource;

    private int counter;

    private ButtonEnableState buttonEnableState;
    private ButtonClickState buttonClickState;

    /**
     * Constructor.
     * @param sub String substring
     * @param res String resource
     * @param ct int counter
     */
    public LogFileTest(String sub, String res, int ct) {
        this.subject = sub;
        this.resource = res;
        this.counter = ct;
        this.buttonEnableState = ButtonEnableState.ENABLE;
        this.buttonClickState = ButtonClickState.NOCLICK;
    }

    @Override
    public String getSubject() { return this.subject; }

    @Override
    public void setSubject(String sub) { this.subject = sub; }

    @Override
    public String getResource() { return this.resource; }

    @Override
    public void setResource(String res) { this.resource = res; }

    @Override
    public int getCounter() { return this.counter; }

    @Override
    public void setCounter(int count) { this.counter = count; }

    @Override
    public ButtonEnableState getButtonEnableState() { return this.buttonEnableState; }

    @Override
    public void setButtonEnableState(ButtonEnableState bstate) { this.buttonEnableState = bstate; }

    @Override
    public ButtonClickState getButtonClickState() { return this.buttonClickState; }

    @Override
    public void setButtonClickState(ButtonClickState cstate) { this.buttonClickState = cstate; }
}
