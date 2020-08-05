package fizzsoftware.moneytracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;

public class main extends Activity {
    public static final String FILENAME = "money.save";
    private static final int MENU_ITEM1 = 0;
    private static final int MENU_ITEM2 = 1;
    private Button btnAdd;
    /* access modifiers changed from: private */
    public Button btnDel;
    /* access modifiers changed from: private */
    public Button btnDelLast;
    /* access modifiers changed from: private */
    public ArrayAdapter<Dayz> dayArrayAdapter;
    /* access modifiers changed from: private */
    public ArrayList<Dayz> dayList;
    private ListView listz;
    /* access modifiers changed from: private */
    public TextView textView2;
    /* access modifiers changed from: private */
    public TextView textView4;

    public boolean writeData() {
        try {
            ObjectOutputStream objout = new ObjectOutputStream(openFileOutput(FILENAME, 0));
            objout.writeObject(this.dayList);
            objout.close();
            return true;
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Data write", 0).show();
            return false;
        }
    }

    public boolean readData() {
        try {
            this.dayList = (ArrayList) new ObjectInputStream(openFileInput(FILENAME)).readObject();
            return true;
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), "Data read", 0).show();
            return false;
        }
    }

    public void singleAlert(String title, String message, Drawable icon) {
        Builder builder = new Builder(this);
        builder.setTitle(title).setIcon(icon).setMessage(message).setCancelable(false).setPositiveButton("OK", new OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.create().show();
    }

    public String getDateTotal() {
        DecimalFormat f = new DecimalFormat();
        f.setMinimumFractionDigits(2);
        double sum = 0.0d;
        Iterator i$ = this.dayList.iterator();
        while (i$.hasNext()) {
            sum += ((Dayz) i$.next()).getPurchaseTotal();
        }
        return "$ " + f.format(sum);
    }

    public void displayPurchase(final Dayz date) {
        this.textView4 = (TextView) findViewById(R.id.textView4);
        View pView = LayoutInflater.from(this).inflate(R.layout.nest_list, null);
        final ListView listItems = (ListView) pView.findViewById(R.id.nestyList);
        listItems.setAdapter(new ArrayAdapter(this, R.layout.simple_textview, date.getPurchaseList()));
        String tmpTit = date.getDateTitle();
        Builder alertDialog = new Builder(this);
        alertDialog.setIcon(R.drawable.money_bag_brown_64);
        alertDialog.setTitle("Purchase List \n" + tmpTit);
        alertDialog.setView(pView);
        alertDialog.setNegativeButton("Done", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                ((BaseAdapter) listItems.getAdapter()).notifyDataSetChanged();
                main.this.textView4.setText(main.this.getDateTotal());
                main.this.dayArrayAdapter.notifyDataSetChanged();
            }
        });
        alertDialog.setPositiveButton("Add", new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
        alert.getButton(-1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View promptsView = LayoutInflater.from(main.this.getBaseContext()).inflate(R.layout.alert_dialog_text, null);
                Builder alertDia = new Builder(main.this);
                alertDia.setIcon(R.drawable.add_money_64);
                alertDia.setTitle("\tAdd Purchase");
                alertDia.setView(promptsView);
                alertDia.setCancelable(false);
                final EditText inTitlez = (EditText) promptsView.findViewById(R.id.ALERT_DIALOG_EDIT_TEXT1);
                final EditText inPricez = (EditText) promptsView.findViewById(R.id.ALERT_DIALOG_EDIT_TEXT2);
                alertDia.setNegativeButton("Cancel", new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDia.setPositiveButton("Ok", new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        date.addPurchase(inTitlez.getText().toString(), inPricez.getText().toString());
                        ((BaseAdapter) listItems.getAdapter()).notifyDataSetChanged();
                    }
                });
                alertDia.create().show();
            }
        });
        listItems.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final int pos = i;
                View promptsView = LayoutInflater.from(main.this.getBaseContext()).inflate(R.layout.alert_edit_text, null);
                Builder alertDia = new Builder(main.this);
                alertDia.setIcon(R.drawable.edit_money_64);
                alertDia.setTitle("\tEdit Purchase");
                alertDia.setView(promptsView);
                alertDia.setCancelable(false);
                final EditText inTitle = (EditText) promptsView.findViewById(R.id.ALERT_DIALOG_EDIT_TEXT1);
                final EditText inPrice = (EditText) promptsView.findViewById(R.id.ALERT_DIALOG_EDIT_TEXT2);
                inTitle.setText(date.getPurchaseNodeName(pos));
                inPrice.setText(date.getPurchaseNodePrice(pos));
                alertDia.setNegativeButton("Cancel", new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDia.setPositiveButton("Ok", new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sTitle = inTitle.getText().toString();
                        String sPrice = inPrice.getText().toString();
                        date.setPurchaseNodeName(sTitle, pos);
                        date.setPurchaseNodePrice(sPrice, pos);
                        ((BaseAdapter) listItems.getAdapter()).notifyDataSetChanged();
                    }
                });
                alertDia.create().show();
            }
        });
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.textView2 = (TextView) findViewById(R.id.textView2);
        this.textView4 = (TextView) findViewById(R.id.textView4);
        this.listz = (ListView) findViewById(R.id.listviewz);
        if (!readData()) {
            this.dayList = new ArrayList<>();
        }
        this.textView2.setText(Integer.toString(this.dayList.size()));
        this.textView4.setText(getDateTotal());
        this.dayArrayAdapter = new ArrayAdapter<>(this, R.layout.simple_textview, this.dayList);
        this.listz.setAdapter(this.dayArrayAdapter);
        this.listz.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                main.this.displayPurchase((Dayz) adapterView.getItemAtPosition(i));
            }
        });
        this.listz.setOnItemLongClickListener(new OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Dayz dia = (Dayz) adapterView.getItemAtPosition(i);
                final int pos = i;
                View promptsView = LayoutInflater.from(main.this.getBaseContext()).inflate(R.layout.alert_course, null);
                Builder alertDia = new Builder(main.this);
                alertDia.setIcon(R.drawable.wallet_closed_edit);
                alertDia.setTitle("\tEdit Item");
                alertDia.setView(promptsView);
                alertDia.setCancelable(false);
                final EditText inDay = (EditText) promptsView.findViewById(R.id.ALERT_DIALOG_EDIT_TEXT1);
                inDay.setText(dia.getDateTitle());
                alertDia.setNegativeButton("Cancel", new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });
                alertDia.setPositiveButton("Ok", new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String gDay = inDay.getText().toString();
                        if (gDay.isEmpty()) {
                            gDay = "Blank Date";
                        }
                        dia.setDateTitle(gDay);
                        main.this.dayList.set(pos, dia);
                        main.this.dayArrayAdapter.notifyDataSetChanged();
                    }
                });
                alertDia.create().show();
                return true;
            }
        });
        this.btnAdd = (Button) findViewById(R.id.btnAdd);
        this.btnAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                main.this.btnDelLast.setEnabled(true);
                main.this.btnDel.setEnabled(true);
                main.this.dayList.add(new Dayz());
                main.this.textView2.setText(Integer.toString(main.this.dayList.size()));
                main.this.dayArrayAdapter.notifyDataSetChanged();
                if (!main.this.dayList.isEmpty() && main.this.dayList.size() > 0) {
                    main.this.displayPurchase((Dayz) main.this.dayList.get(main.this.dayList.size() - 1));
                }
            }
        });
        this.btnDelLast = (Button) findViewById(R.id.btnDelLast);
        this.btnDelLast.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (main.this.dayList.size() <= 0) {
                    Toast.makeText(main.this.getBaseContext(), "List empty", 0).show();
                    main.this.btnDelLast.setEnabled(false);
                    return;
                }
                main.this.dayList.remove(main.this.dayList.size() - 1);
                main.this.textView2.setText(Integer.toString(main.this.dayList.size()));
                main.this.textView4.setText(main.this.getDateTotal());
                main.this.dayArrayAdapter.notifyDataSetChanged();
            }
        });
        this.btnDel = (Button) findViewById(R.id.btnDel);
        this.btnDel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View promptsView = LayoutInflater.from(main.this.getBaseContext()).inflate(R.layout.delete_layout, null);
                Builder alertDia = new Builder(main.this);
                alertDia.setView(promptsView);
                alertDia.setCancelable(false);
                alertDia.setNegativeButton("Cancel", new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                alertDia.setPositiveButton("Ok", new OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (main.this.dayList.size() <= 0) {
                            Toast.makeText(main.this.getBaseContext(), "List empty", 0).show();
                            main.this.btnDel.setEnabled(false);
                            dialogInterface.dismiss();
                            return;
                        }
                        main.this.dayList.clear();
                        main.this.textView2.setText(Integer.toString(main.this.dayList.size()));
                        main.this.textView4.setText(main.this.getDateTotal());
                        main.this.dayArrayAdapter.notifyDataSetChanged();
                    }
                });
                alertDia.create().show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        menu.add(0, 0, 0, "Help");
        menu.add(0, 1, 0, "About");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Drawable draw = ContextCompat.getDrawable(getBaseContext(), R.drawable.help_circle_75);
                singleAlert("Help", getResources().getString(R.string.help_alert), draw);
                break;
            case 1:
                Drawable draw2 = ContextCompat.getDrawable(getBaseContext(), R.drawable.duck_inquiry_75);
                singleAlert("About", getResources().getString(R.string.about_alert), draw2);
                break;
            case R.id.action_settings /*2131492957*/:
                Drawable draw3 = ContextCompat.getDrawable(getBaseContext(), R.drawable.settings_green_75);
                singleAlert("Settings", getResources().getString(R.string.settings_alert), draw3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        writeData();
    }

    public void onPause() {
        super.onPause();
        writeData();
    }
}
