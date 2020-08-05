package fizzsoftware.weathermonkeyaprs;

import java.io.Serializable;

public class UserData implements Serializable {
    private boolean actFlag;
    private String callSign;
    private String output;
    private int radioId;
    private boolean serverFlag;
    private String serverName;
    private int serverPort;

    UserData() {
        setServerName("blank");
        setServerPort(0);
        setServerFlag(false);
        setActFlag(false);
        setCallSign("blank");
        setOutput("blank");
        setRadioId(100);
    }

    public String getServerName() {
        return this.serverName;
    }

    public int getServerPort() {
        return this.serverPort;
    }

    public boolean getServerFlag() {
        return this.serverFlag;
    }

    public boolean getActFlag() {
        return this.actFlag;
    }

    public String getCallSign() {
        return this.callSign;
    }

    public String getOutput() {
        return this.output;
    }

    public int getRadioId() {
        return this.radioId;
    }

    public void setServerName(String name) {
        this.serverName = name;
    }

    public void setServerPort(int port) {
        this.serverPort = port;
    }

    public void setServerFlag(boolean flag) {
        this.serverFlag = flag;
    }

    public void setActFlag(boolean flag) {
        this.actFlag = flag;
    }

    public void setCallSign(String call) {
        this.callSign = call;
    }

    public void setOutput(String out) {
        this.output = out;
    }

    public void setRadioId(int b) {
        this.radioId = b;
    }
}
