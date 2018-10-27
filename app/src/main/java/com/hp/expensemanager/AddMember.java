package p000h.pkg.main;

import android.app.AlertDialog;
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
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;

/* renamed from: h.pkg.main.AddMember */
public class AddMember extends ListActivity {
    ImageButton Add;
    ListView f0L;
    int MID;
    EditText Name;
    int TID;
    float amount;
    ArrayList<Float> amtarr;
    float aramt;
   // DBAdapter db;
    EditText memamt;
    ArrayList<String> memarr;
    String memname;
    ArrayList<Integer> midarr;

    /* renamed from: h.pkg.main.AddMember.1 */
    class C00361 implements OnClickListener {
        C00361() {
        }

        public void onClick(View v) {
            AddMember.this.memname = AddMember.this.Name.getText().toString();
            String amt = AddMember.this.memamt.getText().toString();
            if (AddMember.this.memname.equals("")) {
                Toast.makeText(AddMember.this, "No Name Entered", 0).show();
            } else if (amt.equals("")) {
                Toast.makeText(AddMember.this, "No amount Entered", 0).show();
            } else {
                AddMember.this.amount = Float.parseFloat(amt);
                AddMember.this.insertMem();
                AddMember.this.displayList();
                AddMember.this.Name.setText("");
                AddMember.this.memamt.setText("");
                ((InputMethodManager) AddMember.this.getSystemService("input_method")).hideSoftInputFromWindow(AddMember.this.memamt.getWindowToken(), 0);
                AddMember.this.Name.requestFocus();
            }
        }
    }

    /* renamed from: h.pkg.main.AddMember.2 */
    class C00382 implements DialogInterface.OnClickListener {
        private boolean c1;
        private boolean c2;
        private final /* synthetic */ int val$memid;

        /* renamed from: h.pkg.main.AddMember.2.1 */
        class C00371 implements DialogInterface.OnClickListener {
            C00371() {
            }

            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(AddMember.this.getApplicationContext(), "Adjust Custom Expenses for this member to delete", 0).show();
            }
        }

        C00382(int i) {
            this.val$memid = i;
        }

     /   public void onClick(DialogInterface dialog, int which) {
            AddMember.this.db.open();
            Cursor mc = AddMember.this.db.getAllMCexp(this.val$memid);
            AddMember.this.db.close();
            String soc = "N";
            if (mc.moveToFirst()) {
                do {
                    String cesell = mc.getString(mc.getColumnIndex(DBAdapter.FLD_CESEL));
                    if (cesell.equals("S") || cesell.equals("C")) {
                        soc = "Y";
                    }
                } while (mc.moveToNext());
            }
            mc.close();
            if (soc.equals("N")) {
                AddMember.this.db.open();
                this.c1 = AddMember.this.db.deleteMember(this.val$memid);
                this.c2 = AddMember.this.db.deleteCExpense(this.val$memid);
                AddMember.this.db.close();
                if (this.c1) {
                    AddMember.this.displayList();
                    Toast.makeText(AddMember.this.getApplicationContext(), "Member Deleted", 0).show();
                    return;
                }
                return;
            }
            AlertDialog alertDialog1 = new Builder(AddMember.this).create();
            alertDialog1.setTitle("Member Delele Failed");
            alertDialog1.setMessage("Member part of Customized Expenses. Adjust there.");
            alertDialog1.setButton("OK", new C00371());
            alertDialog1.show();
        }
    }

    /* renamed from: h.pkg.main.AddMember.3 */
    class C00393 implements DialogInterface.OnClickListener {
        C00393() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    public AddMember() {
        this.memarr = new ArrayList();
        this.amtarr = new ArrayList();
        this.midarr = new ArrayList();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0065R.layout.ap);
        this.Name = (EditText) findViewById(C0065R.id.memname);
        this.memamt = (EditText) findViewById(C0065R.id.memamt);
        TextView mnl = (TextView) findViewById(C0065R.id.mnlabel);
        TextView amtl = (TextView) findViewById(C0065R.id.amtlabel);
        this.Add = (ImageButton) findViewById(C0065R.id.Add);
        this.db = new DBAdapter(this);
        Bundle idata = getIntent().getExtras();
        if (idata != null) {
            this.TID = idata.getInt(DBAdapter.KEY_TID);
        }
        displayList();
        this.Add.setOnClickListener(new C00361());
    }

    private void insertMem() {
        try {
            this.db.open();
            this.db.insertmem(this.TID, this.memname, this.amount, "Y");
            this.db.close();
        } catch (SQLiteException e) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }

    private void displayList() {
        this.memarr.clear();
        this.amtarr.clear();
        this.midarr.clear();
        ArrayList<HashMap<String, String>> memlist = new ArrayList();
        try {
            this.db.open();
            Cursor c = this.db.getAllmem(this.TID);
            this.db.close();
            if (c == null || !c.moveToFirst()) {
                c.close();
                setListAdapter(new SimpleAdapter(this, memlist, C0065R.layout.list_item, new String[]{"name", "memamt"}, new int[]{C0065R.id.name, C0065R.id.amount}));
                registerForContextMenu(getListView());
            }
            do {
                String MN = c.getString(c.getColumnIndex(DBAdapter.FLD_NAME));
                this.aramt = c.getFloat(c.getColumnIndex(DBAdapter.FLD_MEMAMT));
                this.MID = c.getInt(c.getColumnIndex(DBAdapter.KEY_MID));
                String amt = String.valueOf(c.getFloat(c.getColumnIndex(DBAdapter.FLD_MEMAMT)));
                Toast.makeText(this, new StringBuilder(String.valueOf(MN)).append(" ").append(amt).toString(), 1);
                HashMap<String, String> map = new HashMap();
                this.memarr.add(MN);
                this.amtarr.add(Float.valueOf(this.aramt));
                this.midarr.add(Integer.valueOf(this.MID));
                map.put("name", MN);
                map.put("memamt", amt);
                memlist.add(map);
            } while (c.moveToNext());
            c.close();
            setListAdapter(new SimpleAdapter(this, memlist, C0065R.layout.list_item, new String[]{"name", "memamt"}, new int[]{C0065R.id.name, C0065R.id.amount}));
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
                Intent I1 = new Intent(this, EditMember.class);
                Bundle meminfo = new Bundle();
                meminfo.putInt(DBAdapter.KEY_MID, ((Integer) this.midarr.get((int) info.id)).intValue());
                meminfo.putString("Name", (String) this.memarr.get((int) info.id));
                meminfo.putFloat("amt", ((Float) this.amtarr.get((int) info.id)).floatValue());
                I1.putExtras(meminfo);
                startActivityForResult(I1, 1);
                return true;
            case C0065R.id.delete:
                int memid = ((Integer) this.midarr.get((int) info.id)).intValue();
                Builder alertDialog = new Builder(this);
                alertDialog.setTitle("Confirm Delete...");
                alertDialog.setMessage("Are you sure you want delete this member ? " + ((String) this.memarr.get((int) info.id)));
                alertDialog.setPositiveButton("YES", new C00382(memid));
                alertDialog.setNegativeButton("NO", new C00393());
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
