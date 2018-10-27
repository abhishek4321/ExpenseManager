package p000h.pkg.main;

import android.app.AlertDialog.Builder;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

/* renamed from: h.pkg.main.Manage */
public class Manage extends ListActivity {
    DBAdapter db;
    ArrayList<String> fd;
    ArrayList<String> nofd;
    ArrayList<Integer> tid;
    ArrayList<String> tns;

    /* renamed from: h.pkg.main.Manage.1 */
    class C00631 implements OnClickListener {
        private boolean c1;
        private final /* synthetic */ int val$tidl;

        C00631(int i) {
            this.val$tidl = i;
        }

        public void onClick(DialogInterface dialog, int which) {
            Manage.this.db.open();
            this.c1 = Manage.this.db.deleteTrip(this.val$tidl);
            Manage.this.db.close();
            if (this.c1) {
                Manage.this.displayList();
                Toast.makeText(Manage.this.getApplicationContext(), "Trip Deleted", 0).show();
            }
        }
    }

    /* renamed from: h.pkg.main.Manage.2 */
    class C00642 implements OnClickListener {
        C00642() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }

    public Manage() {
        this.tid = new ArrayList();
        this.tns = new ArrayList();
        this.nofd = new ArrayList();
        this.fd = new ArrayList();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.db = new DBAdapter(this);
        displayList();
    }

    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Toast.makeText(this, "You have chosen the trip:  " + getListAdapter().getItem(position).toString(), 1).show();
        Intent tab = new Intent(this, TriptabActivity.class);
        Bundle data = new Bundle();
        data.putInt(DBAdapter.KEY_TID, ((Integer) this.tid.get(position)).intValue());
        tab.putExtras(data);
        startActivity(tab);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(C0065R.menu.context_menu, menu);
    }

    public boolean onContextItemSelected(MenuItem item) {
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case C0065R.id.edit:
                Intent I1 = new Intent(this, EditTrip.class);
                Bundle meminfo = new Bundle();
                meminfo.putInt(DBAdapter.KEY_TID, ((Integer) this.tid.get((int) info.id)).intValue());
                meminfo.putString(DBAdapter.FLD_TN, (String) this.tns.get((int) info.id));
                meminfo.putString("NOFD", (String) this.nofd.get((int) info.id));
                meminfo.putString(DBAdapter.FLD_FD, (String) this.fd.get((int) info.id));
                I1.putExtras(meminfo);
                startActivityForResult(I1, 1);
                return true;
            case C0065R.id.delete:
                int tidl = ((Integer) this.tid.get((int) info.id)).intValue();
                Builder alertDialog = new Builder(this);
                alertDialog.setTitle("Confirm Delete...");
                alertDialog.setMessage("Are you sure you want delete this Trip? All member and expense data will be deleted." + ((String) this.tns.get((int) info.id)));
                alertDialog.setPositiveButton("YES", new C00631(tidl));
                alertDialog.setNegativeButton("NO", new C00642());
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

    private void displayList() {
        this.tns.clear();
        this.nofd.clear();
        this.fd.clear();
        this.tid.clear();
        try {
            this.db.open();
            Cursor c = this.db.getAlltrips();
            if (c == null || !c.moveToFirst()) {
                c.close();
                setListAdapter(new ArrayAdapter(this, 17367043, this.tns));
                registerForContextMenu(getListView());
                this.db.close();
            }
            do {
                String tn = c.getString(c.getColumnIndex(DBAdapter.FLD_TN));
                String nfd = c.getString(c.getColumnIndex(DBAdapter.FLD_NOD));
                String fdl = c.getString(c.getColumnIndex(DBAdapter.FLD_FD));
                Integer id = Integer.valueOf(c.getInt(c.getColumnIndex(DBAdapter.KEY_TID)));
                this.tns.add(tn);
                this.nofd.add(nfd);
                this.fd.add(fdl);
                this.tid.add(id);
            } while (c.moveToNext());
            c.close();
            setListAdapter(new ArrayAdapter(this, 17367043, this.tns));
            registerForContextMenu(getListView());
            this.db.close();
        } catch (SQLiteException e) {
            Log.e(getClass().getSimpleName(), "Could not create or Open the database");
        }
    }
}
