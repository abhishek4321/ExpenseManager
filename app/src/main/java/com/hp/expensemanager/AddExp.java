package p000h.pkg.main;

import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

/* renamed from: h.pkg.main.AddExp */
public class AddExp extends ListActivity {
    ImageButton Add;
    EditText Expamt;
    int TID;
    float amount;
    DBAdapter db;
    ArrayList<Integer> eidarr;
    String etyp;
    ArrayList<Float> examtarr;
    ArrayList<String> expcatarr;
    String[] exptyp;
    String[] exptypnew;
    ArrayList<String> exsocarr;
    ArrayList<String> extyparr;
    int item;
    Spinner sp;

    /* renamed from: h.pkg.main.AddExp.1 */
    class C00321 implements OnItemSelectedListener {
        C00321() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            AddExp.this.item = AddExp.this.sp.getSelectedItemPosition();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: h.pkg.main.AddExp.2 */
    class C00332 implements OnClickListener {
        C00332() {
        }

        public void onClick(View v) {
            AddExp.this.etyp = (String) AddExp.this.expcatarr.get(AddExp.this.item);
            String exp = AddExp.this.Expamt.getText().toString();
            if (exp.equals("")) {
                Toast.makeText(AddExp.this, "No amount entered", 0).show();
                return;
            }
            AddExp.this.amount = Float.parseFloat(exp);
            AddExp.this.insertExp();
            AddExp.this.displayList();
            AddExp.this.Expamt.setText("");
            ((InputMethodManager) AddExp.this.getSystemService("input_method")).hideSoftInputFromWindow(AddExp.this.Expamt.getWindowToken(), 0);
            AddExp.this.Expamt.requestFocus();
        }
    }

    /* renamed from: h.pkg.main.AddExp.3 */
    class C00343 implements DialogInterface.OnClickListener {
        private boolean c1;
        private boolean c2;
        private final /* synthetic */ int val$expid;

        C00343(int i) {
            this.val$expid = i;
        }

        public void onClick(DialogInterface dialog, int which) {
            AddExp.this.db.open();
            this.c1 = AddExp.this.db.deleteExpense(this.val$expid);
            this.c2 = AddExp.this.db.deleteCExpenseEID(this.val$expid);
            AddExp.this.db.close();
            if (this.c1) {
                AddExp.this.displayList();
                Toast.makeText(AddExp.this.getApplicationContext(), "Expense Deleted", 0).show();
            }
        }
    }

    /* renamed from: h.pkg.main.AddExp.4 */
    class C00354 implements DialogInterface.OnClickListener {
        C00354() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    public AddExp() {
        this.exptyp = new String[]{"Transport", "Stay", "Lunch", "Breakfast", "Dinner", "Snacks", "Drinks", "Miscellaneous"};
        this.exptypnew = new String[0];
        this.expcatarr = new ArrayList();
        this.exsocarr = new ArrayList();
        this.extyparr = new ArrayList();
        this.examtarr = new ArrayList();
        this.eidarr = new ArrayList();
    }

    protected void onResume() {
        super.onResume();
        buildExptyp();
        this.sp = (Spinner) findViewById(C0065R.id.spinner1);
        this.sp.setAdapter(new ArrayAdapter(this, 17367048, this.expcatarr));
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0065R.layout.ae);
        this.Expamt = (EditText) findViewById(C0065R.id.expamt);
        this.Add = (ImageButton) findViewById(C0065R.id.Adde);
        this.db = new DBAdapter(this);
        Bundle idata = getIntent().getExtras();
        if (idata != null) {
            this.TID = idata.getInt(DBAdapter.KEY_TID);
        }
        buildExptyp();
        this.sp = (Spinner) findViewById(C0065R.id.spinner1);
        this.sp.setAdapter(new ArrayAdapter(this, 17367048, this.exptypnew));
        this.sp.setOnItemSelectedListener(new C00321());
        displayList();
        this.Add.setOnClickListener(new C00332());
    }

    private void buildExptyp() {
        this.expcatarr.clear();
        this.db.open();
        int j = 0;
        Cursor ecat = this.db.getAllExpCat();
        if (ecat.getCount() == 0) {
            ecat.close();
            do {
                this.db.insertExpcat(this.exptyp[j]);
                this.expcatarr.add(this.exptyp[j]);
                j++;
            } while (j < this.exptyp.length);
        } else if (ecat != null && ecat.moveToFirst()) {
            do {
                this.expcatarr.add(ecat.getString(ecat.getColumnIndex("ECAT")));
            } while (ecat.moveToNext());
        }
        ecat.close();
        this.db.close();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0065R.menu.eec_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0065R.id.eec:
                startActivity(new Intent(this, EditExpCat.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void insertExp() {
        try {
            this.db.open();
            this.db.insertexp(this.TID, this.etyp, this.amount, "N");
            this.db.close();
        } catch (SQLiteException e) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }

    private void displayList() {
        this.extyparr.clear();
        this.exsocarr.clear();
        this.examtarr.clear();
        this.eidarr.clear();
        ArrayList<HashMap<String, String>> explist = new ArrayList();
        try {
            this.db.open();
            Cursor c = this.db.getAllexp(this.TID);
            if (c == null || !c.moveToFirst()) {
                c.close();
                this.db.close();
                setListAdapter(new SimpleAdapter(this, explist, C0065R.layout.list_item, new String[]{"name", "expamt"}, new int[]{C0065R.id.name, C0065R.id.amount}));
                registerForContextMenu(getListView());
            }
            do {
                String ET = c.getString(c.getColumnIndex(DBAdapter.FLD_EXPTYP));
                String soc = c.getString(c.getColumnIndex(DBAdapter.FLD_EXPSOC));
                float examt = c.getFloat(c.getColumnIndex(DBAdapter.FLD_EXPAMT));
                int eID = c.getInt(c.getColumnIndex(DBAdapter.KEY_EID));
                this.extyparr.add(ET);
                this.exsocarr.add(soc);
                this.examtarr.add(Float.valueOf(examt));
                this.eidarr.add(Integer.valueOf(eID));
                String amt = String.valueOf(c.getFloat(c.getColumnIndex(DBAdapter.FLD_EXPAMT)));
                HashMap<String, String> map = new HashMap();
                if (soc.equals("N")) {
                    map.put("name", ET);
                } else {
                    map.put("name", new StringBuilder(String.valueOf(ET)).append(" (C)").toString());
                }
                map.put("expamt", amt);
                explist.add(map);
            } while (c.moveToNext());
            c.close();
            this.db.close();
            setListAdapter(new SimpleAdapter(this, explist, C0065R.layout.list_item, new String[]{"name", "expamt"}, new int[]{C0065R.id.name, C0065R.id.amount}));
            registerForContextMenu(getListView());
        } catch (SQLiteException e) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(C0065R.menu.context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case C0065R.id.edit:
                Intent I1 = new Intent(this, EditExpense.class);
                Bundle expinfo = new Bundle();
                expinfo.putInt(DBAdapter.KEY_TID, this.TID);
                expinfo.putInt(DBAdapter.KEY_EID, ((Integer) this.eidarr.get((int) info.id)).intValue());
                expinfo.putString("ETYP", (String) this.extyparr.get((int) info.id));
                expinfo.putString("ESOC", (String) this.exsocarr.get((int) info.id));
                expinfo.putFloat("EXAMT", ((Float) this.examtarr.get((int) info.id)).floatValue());
                I1.putExtras(expinfo);
                startActivityForResult(I1, 1);
                return true;
            case C0065R.id.delete:
                int expid = ((Integer) this.eidarr.get((int) info.id)).intValue();
                Builder alertDialog = new Builder(this);
                alertDialog.setTitle("Confirm Delete...");
                alertDialog.setMessage("Are you sure you want delete this expense?  " + ((String) this.extyparr.get((int) info.id)) + " " + this.examtarr.get((int) info.id));
                alertDialog.setPositiveButton("YES", new C00343(expid));
                alertDialog.setNegativeButton("NO", new C00354());
                alertDialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            displayList();
            Toast.makeText(this, "Update Successful", 0).show();
        }
    }
}
