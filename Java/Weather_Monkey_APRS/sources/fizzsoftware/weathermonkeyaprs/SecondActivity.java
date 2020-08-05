package fizzsoftware.weathermonkeyaprs;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class SecondActivity extends Activity {
    private static final int MENU_ITEM1 = 0;
    private static final int MENU_ITEM2 = 1;
    private static final String STATE_ADD_IN_PROGRESS = "shelves.add.inprogress";
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;
    public static int TYPE_WIFI = 1;
    MyClientTask mt;
    ProgressBar progress;
    UserData userz;

    public class MyClientTask extends AsyncTask<Void, String, Void> {
        private BufferedReader in;
        private String input;
        private BufferedWriter out;
        private String output;
        private String serverName;
        private int serverPort;
        private Socket socket;

        public MyClientTask() {
        }

        public void setServerName(String addr) {
            this.serverName = addr;
        }

        public void setServerPort(int port) {
            this.serverPort = port;
        }

        public void setOutputString(String str) {
            this.output = str;
        }

        /* access modifiers changed from: 0000 */
        public String getServerName() {
            return this.serverName;
        }

        /* access modifiers changed from: 0000 */
        public int getServerPort() {
            return this.serverPort;
        }

        /* access modifiers changed from: 0000 */
        public String getOutputString() {
            return this.output;
        }

        /* access modifiers changed from: protected */
        /* JADX WARNING: Code restructure failed: missing block: B:12:?, code lost:
            r5.socket.close();
         */
        public Void doInBackground(Void... parms) {
            try {
                this.socket = new Socket(getServerName(), getServerPort());
                this.in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
                this.out = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
                this.out.write(getOutputString());
                this.out.flush();
                while (true) {
                    String readLine = this.in.readLine();
                    this.input = readLine;
                    if (readLine == null) {
                        break;
                    }
                    TimeUnit.MILLISECONDS.sleep(555);
                    if (isCancelled()) {
                        break;
                    }
                    publishProgress(new String[]{this.input});
                }
            } catch (UnknownHostException e) {
                new Thread(new Runnable() {
                    public void run() {
                        Toast.makeText(SecondActivity.this.getBaseContext(), "Unknown Host Exception", 0).show();
                    }
                });
                e.printStackTrace();
            } catch (IOException e2) {
                new Thread(new Runnable() {
                    public void run() {
                        Toast.makeText(SecondActivity.this.getBaseContext(), "I/O Exception", 0).show();
                    }
                });
                e2.printStackTrace();
            } catch (InterruptedException e3) {
                new Thread(new Runnable() {
                    public void run() {
                        Toast.makeText(SecondActivity.this.getBaseContext(), "Interrupted Exception", 0).show();
                    }
                });
                e3.printStackTrace();
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Void result) {
            Toast.makeText(SecondActivity.this, "Restart thread", 0).show();
            Log.d("LOG_TAG", "End - close");
            SecondActivity.this.progress.setVisibility(4);
            super.onPostExecute(result);
        }

        /* access modifiers changed from: protected */
        public void onProgressUpdate(String... progress) {
            SecondActivity.this.filterView(SecondActivity.this.filterBrackets(Arrays.toString(progress)));
            super.onProgressUpdate(progress);
        }

        /* access modifiers changed from: protected */
        public void onCancelled(Void aVoid) {
            SecondActivity.this.progress.setVisibility(4);
            Log.d("LOG_TAG", "OnCancelled fired");
        }
    }

    public static int getConnectivityStatus(Context context) {
        NetworkInfo activeNetwork = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetwork != null) {
            if (activeNetwork.getType() == 1) {
                return TYPE_WIFI;
            }
            if (activeNetwork.getType() == 0) {
                return TYPE_MOBILE;
            }
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = getConnectivityStatus(context);
        if (conn == TYPE_WIFI) {
            return "Wifi enabled";
        }
        if (conn == TYPE_MOBILE) {
            return "Mobile data enabled";
        }
        if (conn == TYPE_NOT_CONNECTED) {
            return "Not connected to Internet";
        }
        return null;
    }

    public void singleAlert(String title, String message, Drawable icon) {
        Builder builder = new Builder(this);
        builder.setTitle(title).setIcon(icon).setMessage(message).setCancelable(false).setPositiveButton("OK", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.create().show();
    }

    public void goMainActivity(UserData userB) {
        userB.setActFlag(true);
        Intent i = new Intent(getApplicationContext(), main.class);
        i.putExtra("userB", userB);
        setResult(-1, i);
        finish();
    }

    public String filterBrackets(String s) {
        if (s.charAt(0) != '[' || s.charAt(s.length() - 1) != ']') {
            return s;
        }
        int indexOfOpenBracket = s.indexOf("[");
        return s.substring(indexOfOpenBracket + 1, s.lastIndexOf("]"));
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        this.userz = (UserData) getIntent().getSerializableExtra("user");
        this.progress = (ProgressBar) findViewById(R.id.progBar);
        this.progress.setVisibility(4);
    }

    public void onConnect(View v) {
        Button btnConnect = (Button) findViewById(R.id.btnConnect);
        switch (v.getId()) {
            case R.id.btnBack /*2131492963*/:
                cancelTask();
                this.progress.setVisibility(4);
                goMainActivity(this.userz);
                return;
            case R.id.btnConnect /*2131492984*/:
                Toast.makeText(this, getConnectivityStatusString(this), 0).show();
                cancelTask();
                clearFields();
                this.mt = new MyClientTask();
                this.mt.setOutputString(this.userz.getOutput());
                this.mt.setServerName(this.userz.getServerName());
                this.mt.setServerPort(this.userz.getServerPort());
                this.mt.execute(new Void[0]);
                btnConnect.setEnabled(false);
                this.progress.setVisibility(0);
                return;
            case R.id.btnCancel /*2131492985*/:
                cancelTask();
                btnConnect.setEnabled(true);
                this.progress.setVisibility(4);
                return;
            default:
                return;
        }
    }

    private void cancelTask() {
        if (this.mt != null) {
            Log.d("LOG_TAG", "cancel result " + this.mt.cancel(false));
        }
    }

    /* access modifiers changed from: 0000 */
    public void clearFields() {
        TextView txtTime = (TextView) findViewById(R.id.txtTime);
        TextView txtTemp = (TextView) findViewById(R.id.txtTemp);
        TextView txtWGust = (TextView) findViewById(R.id.txtWGust);
        TextView txtWSpeed = (TextView) findViewById(R.id.txtWSpeed);
        TextView txtWDirection = (TextView) findViewById(R.id.txtWDirection);
        TextView txtBPressure = (TextView) findViewById(R.id.txtBPressure);
        TextView txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        TextView txtRainHour = (TextView) findViewById(R.id.txtRainHour);
        TextView txtRainNight = (TextView) findViewById(R.id.txtRainNight);
        ((TextView) findViewById(R.id.txtReport)).setText("");
        txtTime.setText("");
        txtTemp.setText("");
        txtWGust.setText("");
        txtWSpeed.setText("");
        txtWDirection.setText("");
        txtBPressure.setText("");
        txtHumidity.setText("");
        txtRainHour.setText("");
        txtRainNight.setText("");
    }

    /* access modifiers changed from: 0000 */
    public void filterView(String s) {
        TextView txtReport = (TextView) findViewById(R.id.txtReport);
        TextView txtTime = (TextView) findViewById(R.id.txtTime);
        TextView txtTemp = (TextView) findViewById(R.id.txtTemp);
        TextView txtWGust = (TextView) findViewById(R.id.txtWGust);
        TextView txtWSpeed = (TextView) findViewById(R.id.txtWSpeed);
        TextView txtWDirection = (TextView) findViewById(R.id.txtWDirection);
        TextView txtBPressure = (TextView) findViewById(R.id.txtBPressure);
        TextView txtHumidity = (TextView) findViewById(R.id.txtHumidity);
        TextView txtRainHour = (TextView) findViewById(R.id.txtRainHour);
        TextView txtRainNight = (TextView) findViewById(R.id.txtRainNight);
        try {
            if (s.contains(":@") && s.contains(">")) {
                clearFields();
                txtReport.setText(s.substring(0, s.indexOf(">")));
                int j = s.indexOf("/");
                char let1 = s.charAt(j - 1);
                char let2 = s.charAt(j + 9);
                String u = s.substring(s.indexOf(":@") + 2, s.indexOf(":@") + 4);
                txtTime.setText(u + " " + let1 + "  |  " + s.substring(j + 1, j + 3) + " " + let2);
                txtWGust.setText(s.substring(s.indexOf("g") + 1, s.indexOf("g") + 4) + " mph");
                txtTemp.setText(s.substring(s.indexOf("t") + 1, s.indexOf("t") + 4) + " F");
                txtRainHour.setText(s.substring(s.indexOf("r") + 1, s.indexOf("r") + 4) + " 1/100 inch");
                txtRainNight.setText(s.substring(s.indexOf("p") + 1, s.indexOf("p") + 4) + " 1/100 inch");
                txtHumidity.setText(s.substring(s.indexOf("h") + 1, s.indexOf("h") + 3) + " %");
                txtBPressure.setText(s.substring(s.indexOf("b") + 1, s.indexOf("b") + 6) + " millibars / hPascal");
            }
            if (s.contains(":_") && s.contains(">")) {
                clearFields();
                txtReport.setText(s.substring(0, s.indexOf(">")));
                int j2 = s.indexOf(":_");
                txtTime.setText("Date " + s.substring(j2 + 2, j2 + 4) + "/" + s.substring(j2 + 4, j2 + 6) + " Time " + s.substring(j2 + 6, j2 + 8) + ":" + s.substring(j2 + 8, j2 + 10) + " (24hr)");
                txtWGust.setText(s.substring(s.indexOf("g") + 1, s.indexOf("g") + 4) + " mph");
                txtTemp.setText(s.substring(s.indexOf("t") + 1, s.indexOf("t") + 4) + " F");
                txtRainHour.setText(s.substring(s.indexOf("r") + 1, s.indexOf("r") + 4) + " 1/100 inch");
                txtRainNight.setText(s.substring(s.indexOf("p") + 1, s.indexOf("p") + 4) + " 1/100 inch");
                txtHumidity.setText(s.substring(s.indexOf("h") + 1, s.indexOf("h") + 3) + " %");
                txtBPressure.setText(s.substring(s.indexOf("b") + 1, s.indexOf("b") + 6) + " millibars / hPascal");
                txtWDirection.setText(s.substring(s.indexOf("c") + 1, s.indexOf("c") + 4) + " degrees");
                txtWSpeed.setText(s.substring(s.indexOf("s") + 1, s.indexOf("s") + 4) + " mph");
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Parsing oddity", 0).show();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, 0, 0, "Help");
        menu.add(0, 1, 0, "About");
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Drawable draw = ContextCompat.getDrawable(getBaseContext(), R.drawable.help_circle_su);
                singleAlert("Help", getResources().getString(R.string.help_alert1), draw);
                break;
            case 1:
                Drawable draw2 = ContextCompat.getDrawable(getBaseContext(), R.drawable.duck_inquiry_su);
                singleAlert("About", getResources().getString(R.string.about_alert1), draw2);
                break;
            case R.id.action_settings /*2131492993*/:
                Drawable draw3 = ContextCompat.getDrawable(getBaseContext(), R.drawable.settings_green_su);
                singleAlert("Settings", getResources().getString(R.string.settings_alert1), draw3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        MyClientTask task = this.mt;
        if (task != null && task.getStatus() != Status.FINISHED) {
            task.cancel(true);
            savedInstanceState.putBoolean(STATE_ADD_IN_PROGRESS, true);
            this.mt = null;
        }
    }

    /* access modifiers changed from: protected */
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.getBoolean(STATE_ADD_IN_PROGRESS)) {
            this.mt = (MyClientTask) new MyClientTask().execute(new Void[0]);
        }
    }
}
