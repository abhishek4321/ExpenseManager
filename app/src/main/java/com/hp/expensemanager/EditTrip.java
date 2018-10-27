package com.hp.expensemanager;

import android.app.Activity;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/* renamed from: h.pkg.main.EditTrip */
public class EditTrip extends Activity {
    Button Bup;
    TextView Fd;
    EditText ND;
    TextView Nod;
    EditText TN;
    TextView Tripname;
    private int day;
    //DBAdapter db;
    String frmdt;
    private int month;
    String nofd;
    private DatePicker picker;
    private int tid;
    String trn;
    private int year;

    /* renamed from: h.pkg.main.EditTrip.1 */
    class C00571 implements OnClickListener {
        C00571() {
        }

        public void onClick(View v) {
            EditTrip.this.trn = EditTrip.this.TN.getText().toString();
            EditTrip.this.day = EditTrip.this.picker.getDayOfMonth();
            EditTrip.this.month = EditTrip.this.picker.getMonth() + 1;
            EditTrip.this.year = EditTrip.this.picker.getYear();
            EditTrip.this.frmdt = new StringBuilder(String.valueOf(EditTrip.this.day)).append("-").append(EditTrip.this.month).append("-").append(EditTrip.this.year).toString();
            EditTrip.this.nofd = EditTrip.this.ND.getText().toString();
            if (EditTrip.this.trn.matches("")) {
                Toast.makeText(EditTrip.this, "No Trip Name", 1).show();
            } else if (EditTrip.this.nofd.matches("")) {
                Toast.makeText(EditTrip.this, "Please enter No. of Days", 1).show();
            } else {
     //           EditTrip.this.updateDB();
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(p000h.pkg.main.C0065R.layout.et);
        this.Tripname = (TextView) findViewById(C0065R.id.Tripnameu);
        this.Nod = (TextView) findViewById(C0065R.id.NODTu);
        this.Fd = (TextView) findViewById(C0065R.id.FDu);
        this.Bup = (Button) findViewById(C0065R.id.BUT);
        this.TN = (EditText) findViewById(C0065R.id.TNu);
        this.ND = (EditText) findViewById(C0065R.id.NODu);
        this.picker = (DatePicker) findViewById(C0065R.id.fdpu);
        Bundle idata = getIntent().getExtras();
        if (idata != null) {
            this.tid = idata.getInt(DBAdapter.KEY_TID);
            this.trn = idata.getString(DBAdapter.FLD_TN);
            this.nofd = idata.getString("NOFD");
            this.frmdt = idata.getString(DBAdapter.FLD_FD);
        }*/
        this.TN.setText(this.trn);
        String[] dparms = this.frmdt.split("-");
        this.day = Integer.parseInt(dparms[0]);
        this.month = Integer.parseInt(dparms[1]);
        this.year = Integer.parseInt(dparms[2]);
        this.picker.init(this.year, this.month - 1, this.day, null);
        this.ND.setText(this.nofd);
        this.Bup.setOnClickListener(new C00571());
    }

    /*private void updateDB() {
        try {
            this.db = new p000h.pkg.main.DBAdapter(this);
            this.db.open();
            boolean u = this.db.updateTrip(this.tid, this.trn, this.frmdt, this.nofd);
            this.db.close();*/
      /*      if (u) {
                setResult(-1);
                finish();
                return;
            }
            setResult(0);
            finish();
        } catch (SQLiteException e) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }*/
}
