package p000h.pkg.main;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/* renamed from: h.pkg.main.Create */
public class Create extends Activity {
    Button Bcr;
    TextView Fd;
    EditText ND;
    TextView Nod;
    EditText TN;
    TextView Tripname;
    private int day;
    DBAdapter db;
    String frmdt;
    private int month;
    String nofd;
    private DatePicker picker;
    String trn;
    private int year;

    /* renamed from: h.pkg.main.Create.1 */
    class C00401 implements OnTouchListener {
        C00401() {
        }

        public boolean onTouch(View arg0, MotionEvent arg1) {
            ((InputMethodManager) arg0.getContext().getSystemService("input_method")).hideSoftInputFromWindow(arg0.getWindowToken(), 0);
            return false;
        }
    }

    /* renamed from: h.pkg.main.Create.2 */
    class C00412 implements OnClickListener {
        C00412() {
        }

        public void onClick(View v) {
            Create.this.trn = Create.this.TN.getText().toString();
            Create.this.day = Create.this.picker.getDayOfMonth();
            Create.this.month = Create.this.picker.getMonth() + 1;
            Create.this.year = Create.this.picker.getYear();
            Create.this.frmdt = new StringBuilder(String.valueOf(Create.this.day)).append("-").append(Create.this.month).append("-").append(Create.this.year).toString();
            Create.this.nofd = Create.this.ND.getText().toString();
            if (Create.this.trn.matches("")) {
                Toast.makeText(Create.this, "No Trip Name", 1);
            } else if (Create.this.nofd.matches("")) {
                Toast.makeText(Create.this, "Please enter No. of Days", 1);
            } else {
                Create.this.createDB();
                Intent Home = new Intent(Create.this, Home.class);
                Home.setFlags(67108864);
                Create.this.startActivity(Home);
            }
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(1);
        setContentView(C0065R.layout.create);
        LinearLayout cll = (LinearLayout) findViewById(C0065R.id.createll);
        this.Tripname = (TextView) findViewById(C0065R.id.Tripname);
        this.Nod = (TextView) findViewById(C0065R.id.NODT);
        this.Fd = (TextView) findViewById(C0065R.id.FD);
        this.Bcr = (Button) findViewById(C0065R.id.BCT);
        this.Tripname.setTextColor(Color.parseColor("#FFFFFF"));
        this.TN = (EditText) findViewById(C0065R.id.TN);
        this.ND = (EditText) findViewById(C0065R.id.NOD);
        this.picker = (DatePicker) findViewById(C0065R.id.fdp);
        cll.setOnTouchListener(new C00401());
        this.Bcr.setOnClickListener(new C00412());
    }

    private void createDB() {
        try {
            this.db = new DBAdapter(this);
            this.db.open();
            Toast.makeText(this, "Use Manage Trip Now to View and Edit the Trip details", 1).show();
            this.db.insertTrip(this.trn, this.frmdt, this.nofd);
            this.db.close();
        } catch (SQLiteException e) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }
}
