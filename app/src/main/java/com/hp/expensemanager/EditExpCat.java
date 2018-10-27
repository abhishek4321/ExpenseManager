package p000h.pkg.main;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

/* renamed from: h.pkg.main.EditExpCat */
public class EditExpCat extends Activity {
    DBAdapter db;
    Button deecat;
    EditText ecat;
    ArrayList<Integer> ecidarr;
    ArrayList<String> expcatarr;
    Button inecat;
    private int item;
    Spinner sp1;
    TextView tv1;
    TextView tv2;

    /* renamed from: h.pkg.main.EditExpCat.1 */
    class C00421 implements OnItemSelectedListener {
        C00421() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            EditExpCat.this.item = EditExpCat.this.sp1.getSelectedItemPosition();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: h.pkg.main.EditExpCat.2 */
    class C00432 implements OnClickListener {
        C00432() {
        }

        public void onClick(View arg0) {
            String Ecatl = EditExpCat.this.ecat.getText().toString();
            if (Ecatl.equals("")) {
                Toast.makeText(EditExpCat.this, "No Category Entered to insert", 1).show();
                return;
            }
            EditExpCat.this.db.open();
            EditExpCat.this.db.insertExpcat(Ecatl);
            EditExpCat.this.db.close();
            EditExpCat.this.buildExptyp();
            EditExpCat.this.sp1.setAdapter(new ArrayAdapter(EditExpCat.this, 17367048, EditExpCat.this.expcatarr));
            Toast.makeText(EditExpCat.this, "New expense category inserted", 1).show();
            ((InputMethodManager) EditExpCat.this.getSystemService("input_method")).hideSoftInputFromWindow(EditExpCat.this.inecat.getWindowToken(), 0);
        }
    }

    /* renamed from: h.pkg.main.EditExpCat.3 */
    class C00463 implements OnClickListener {

        /* renamed from: h.pkg.main.EditExpCat.3.1 */
        class C00441 implements DialogInterface.OnClickListener {
            private boolean c1;

            C00441() {
            }

            public void onClick(DialogInterface dialog, int which) {
                EditExpCat.this.db.open();
                this.c1 = EditExpCat.this.db.deleteExpenseCat(((Integer) EditExpCat.this.ecidarr.get(EditExpCat.this.item)).intValue());
                EditExpCat.this.db.close();
                if (this.c1) {
                    Toast.makeText(EditExpCat.this.getApplicationContext(), "Expense Category Deleted", 0).show();
                    EditExpCat.this.buildExptyp();
                    EditExpCat.this.sp1.setAdapter(new ArrayAdapter(EditExpCat.this, 17367048, EditExpCat.this.expcatarr));
                }
            }
        }

        /* renamed from: h.pkg.main.EditExpCat.3.2 */
        class C00452 implements DialogInterface.OnClickListener {
            C00452() {
            }

            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        }

        C00463() {
        }

        public void onClick(View arg0) {
            Builder alertDialog = new Builder(EditExpCat.this);
            alertDialog.setTitle("Confirm Delete...");
            alertDialog.setMessage("Are you sure you want delete this expense category?  " + ((String) EditExpCat.this.expcatarr.get(EditExpCat.this.item)));
            alertDialog.setPositiveButton("YES", new C00441());
            alertDialog.setNegativeButton("NO", new C00452());
            alertDialog.show();
        }
    }

    public EditExpCat() {
        this.expcatarr = new ArrayList();
        this.ecidarr = new ArrayList();
        this.item = 0;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0065R.layout.eecl);
        this.ecat = (EditText) findViewById(C0065R.id.edexcat);
        this.tv1 = (TextView) findViewById(C0065R.id.textView1);
        this.tv2 = (TextView) findViewById(C0065R.id.textView2);
        this.sp1 = (Spinner) findViewById(C0065R.id.ecatspin);
        this.inecat = (Button) findViewById(C0065R.id.insecat);
        this.deecat = (Button) findViewById(C0065R.id.delecat);
        this.db = new DBAdapter(this);
        buildExptyp();
        this.sp1 = (Spinner) findViewById(C0065R.id.ecatspin);
        this.sp1.setAdapter(new ArrayAdapter(this, 17367048, this.expcatarr));
        this.sp1.setOnItemSelectedListener(new C00421());
        this.inecat.setOnClickListener(new C00432());
        this.deecat.setOnClickListener(new C00463());
    }

    private void buildExptyp() {
        this.expcatarr.clear();
        this.ecidarr.clear();
        this.db.open();
        Cursor ecat = this.db.getAllExpCat();
        if (ecat == null || !ecat.moveToFirst()) {
            ecat.close();
            this.db.close();
        }
        do {
            String ecatstr = ecat.getString(ecat.getColumnIndex("ECAT"));
            int ecidl = ecat.getInt(ecat.getColumnIndex(DBAdapter.KEY_ECID));
            this.expcatarr.add(ecatstr);
            this.ecidarr.add(Integer.valueOf(ecidl));
        } while (ecat.moveToNext());
        ecat.close();
        this.db.close();
    }
}
