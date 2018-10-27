package p000h.pkg.main;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

/* renamed from: h.pkg.main.Report */
public class Report extends ListActivity {
    static ArrayList<Float> memexp;
    static ArrayList<Float> memowe;
    static ArrayList<Float> memref;
    static float totaldeposit;
    static float totalexpense;
    static float totalowes;
    static float totalrefunds;
    static float unspent;
    private int TID;
    DBAdapter db;
    ArrayList<Float> mamtarr;
    ArrayList<String> memarr;
    private int memcount;
    ArrayList<Integer> midarr;
    TextView nofm;
    TextView tn;
    String tname;
    TextView totdep;
    TextView totexp;

    public Report() {
        this.memarr = new ArrayList();
        this.mamtarr = new ArrayList();
        this.midarr = new ArrayList();
    }

    static {
        memexp = new ArrayList();
        memref = new ArrayList();
        memowe = new ArrayList();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0065R.layout.report);
        this.tn = (TextView) findViewById(C0065R.id.tripnamel);
        this.nofm = (TextView) findViewById(C0065R.id.nofmem);
        this.totdep = (TextView) findViewById(C0065R.id.totdep);
        this.totexp = (TextView) findViewById(C0065R.id.totexp);
        this.db = new DBAdapter(this);
        this.db.open();
        Bundle idata = getIntent().getExtras();
        if (idata != null) {
            this.TID = idata.getInt(DBAdapter.KEY_TID);
        }
        Cursor trip = this.db.getTrip(this.TID);
        if (trip != null && trip.moveToFirst()) {
            this.tname = trip.getString(trip.getColumnIndex(DBAdapter.FLD_TN));
        }
        trip.close();
        this.tn.setText(this.tname.toUpperCase());
        displayList();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(C0065R.menu.export_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case C0065R.id.export:
                if (this.memcount > 0) {
                    Bundle exportExtras = new Bundle();
                    exportExtras.putInt(DBAdapter.KEY_TID, this.TID);
                    exportExtras.putString("TNAME", this.tname);
                    Intent exportIntent = new Intent(this, ExportActivity.class);
                    exportIntent.putExtras(exportExtras);
                    startActivity(exportIntent);
                    return true;
                }
                Toast.makeText(this, "No Content to Export", 1).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onPause() {
        super.onPause();
        this.db.close();
    }

    protected void onResume() {
        super.onResume();
        this.db.open();
        displayList();
    }

    protected void onDestroy() {
        super.onDestroy();
        this.db.close();
    }

    private void displayList() {
        HashMap<String, String> map;
        calculateMemberexpenses();
        ArrayList<HashMap<String, String>> memlist = new ArrayList();
        for (int i = 0; i < this.memcount; i++) {
            map = new HashMap();
            map.put("name", (String) this.memarr.get(i));
            map.put("memamt", String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format(this.mamtarr.get(i)))));
            map.put("spent", String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format(memexp.get(i)))));
            map.put("refund", String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format(memref.get(i)))));
            map.put("owes", String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format(memowe.get(i)))));
            memlist.add(map);
        }
        map = new HashMap();
        map.put("name", "U N S P E N T");
        map.put("memamt", "");
        map.put("spent", "");
        map.put("refund", "");
        map.put("owes", String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) unspent))));
        memlist.add(map);
        map = new HashMap();
        map.put("name", "T O T A L S");
        map.put("memamt", "");
        map.put("spent", String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) totalexpense))));
        map.put("refund", String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) totalrefunds))));
        map.put("owes", String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) (totalowes + unspent)))));
        memlist.add(map);
        setListAdapter(new SimpleAdapter(this, memlist, C0065R.layout.reportlist, new String[]{"name", "memamt", "spent", "refund", "owes"}, new int[]{C0065R.id.memnamer, C0065R.id.depositr, C0065R.id.spentr, C0065R.id.refundr, C0065R.id.owesr}));
    }

    private void calculateMemberexpenses() {
        this.memarr.clear();
        this.mamtarr.clear();
        memexp.clear();
        memref.clear();
        memowe.clear();
        this.midarr.clear();
        Cursor mc = this.db.getAllmem(this.TID);
        this.memcount = mc.getCount();
        this.nofm.setText(String.valueOf(this.memcount));
        float totalucexp;
        Cursor mce;
        float perhead;
        int i;
        int i2;
        float totalcexp;
        Cursor mexp;
        float tmpmexp;
        float tmpdiff;
        if (mc == null || !mc.moveToFirst()) {
            mc.close();
            totalucexp = 0.0f;
            mce = this.db.getAllucexp(this.TID);
            if (mce == null && mce.moveToFirst()) {
                do {
                    totalucexp += mce.getFloat(mce.getColumnIndex(DBAdapter.FLD_EXPAMT));
                } while (mce.moveToNext());
                mce.close();
                perhead = totalucexp / ((float) this.memcount);
                totalexpense = 0.0f;
                totaldeposit = 0.0f;
                totalrefunds = 0.0f;
                totalowes = 0.0f;
                i = 0;
                while (true) {
                    i2 = this.memcount;
                    if (i >= r0) {
                        totalcexp = 0.0f;
                        mexp = this.db.getAllMCexp(((Integer) this.midarr.get(i)).intValue());
                        if (mexp == null) {
                        }
                        mexp.close();
                        tmpmexp = perhead + totalcexp;
                        memexp.add(Float.valueOf(tmpmexp));
                        totalexpense += tmpmexp;
                        totaldeposit = ((Float) this.mamtarr.get(i)).floatValue() + totaldeposit;
                        tmpdiff = ((Float) this.mamtarr.get(i)).floatValue() - ((Float) memexp.get(i)).floatValue();
                        if (tmpdiff < 0.0f) {
                            totalowes += -1.0f * tmpdiff;
                            memowe.add(Float.valueOf(-1.0f * tmpdiff));
                            memref.add(Float.valueOf(0.0f));
                        } else if (tmpdiff != 0.0f) {
                            memref.add(Float.valueOf(tmpdiff));
                            memowe.add(Float.valueOf(tmpdiff));
                        } else {
                            totalrefunds += tmpdiff;
                            memref.add(Float.valueOf(tmpdiff));
                            memowe.add(Float.valueOf(0.0f));
                        }
                        i++;
                    } else {
                        this.totdep.setText(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) totaldeposit))));
                        this.totexp.setText(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) totalexpense))));
                        unspent = totaldeposit - totalexpense;
                        return;
                    }
                }
            }
            mce.close();
            perhead = totalucexp / ((float) this.memcount);
            totalexpense = 0.0f;
            totaldeposit = 0.0f;
            totalrefunds = 0.0f;
            totalowes = 0.0f;
            i = 0;
            while (true) {
                i2 = this.memcount;
                if (i >= r0) {
                    this.totdep.setText(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) totaldeposit))));
                    this.totexp.setText(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) totalexpense))));
                    unspent = totaldeposit - totalexpense;
                    return;
                }
                totalcexp = 0.0f;
                mexp = this.db.getAllMCexp(((Integer) this.midarr.get(i)).intValue());
                if (mexp == null && mexp.moveToFirst()) {
                    do {
                        totalcexp += mexp.getFloat(mexp.getColumnIndex(DBAdapter.FLD_CEAMT));
                    } while (mexp.moveToNext());
                    mexp.close();
                    tmpmexp = perhead + totalcexp;
                    memexp.add(Float.valueOf(tmpmexp));
                    totalexpense += tmpmexp;
                    totaldeposit = ((Float) this.mamtarr.get(i)).floatValue() + totaldeposit;
                    tmpdiff = ((Float) this.mamtarr.get(i)).floatValue() - ((Float) memexp.get(i)).floatValue();
                    if (tmpdiff < 0.0f) {
                        totalowes += -1.0f * tmpdiff;
                        memowe.add(Float.valueOf(-1.0f * tmpdiff));
                        memref.add(Float.valueOf(0.0f));
                    } else if (tmpdiff != 0.0f) {
                        totalrefunds += tmpdiff;
                        memref.add(Float.valueOf(tmpdiff));
                        memowe.add(Float.valueOf(0.0f));
                    } else {
                        memref.add(Float.valueOf(tmpdiff));
                        memowe.add(Float.valueOf(tmpdiff));
                    }
                    i++;
                } else {
                    mexp.close();
                    tmpmexp = perhead + totalcexp;
                    memexp.add(Float.valueOf(tmpmexp));
                    totalexpense += tmpmexp;
                    totaldeposit = ((Float) this.mamtarr.get(i)).floatValue() + totaldeposit;
                    tmpdiff = ((Float) this.mamtarr.get(i)).floatValue() - ((Float) memexp.get(i)).floatValue();
                    if (tmpdiff < 0.0f) {
                        totalowes += -1.0f * tmpdiff;
                        memowe.add(Float.valueOf(-1.0f * tmpdiff));
                        memref.add(Float.valueOf(0.0f));
                    } else if (tmpdiff != 0.0f) {
                        memref.add(Float.valueOf(tmpdiff));
                        memowe.add(Float.valueOf(tmpdiff));
                    } else {
                        totalrefunds += tmpdiff;
                        memref.add(Float.valueOf(tmpdiff));
                        memowe.add(Float.valueOf(0.0f));
                    }
                    i++;
                }
            }
        } else {
            do {
                String MN = mc.getString(mc.getColumnIndex(DBAdapter.FLD_NAME));
                float aramt = mc.getFloat(mc.getColumnIndex(DBAdapter.FLD_MEMAMT));
                int MID = mc.getInt(mc.getColumnIndex(DBAdapter.KEY_MID));
                this.memarr.add(MN);
                this.mamtarr.add(Float.valueOf(aramt));
                this.midarr.add(Integer.valueOf(MID));
            } while (mc.moveToNext());
            mc.close();
            totalucexp = 0.0f;
            mce = this.db.getAllucexp(this.TID);
            if (mce == null) {
            }
            mce.close();
            perhead = totalucexp / ((float) this.memcount);
            totalexpense = 0.0f;
            totaldeposit = 0.0f;
            totalrefunds = 0.0f;
            totalowes = 0.0f;
            i = 0;
            while (true) {
                i2 = this.memcount;
                if (i >= r0) {
                    this.totdep.setText(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) totaldeposit))));
                    this.totexp.setText(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) totalexpense))));
                    unspent = totaldeposit - totalexpense;
                    return;
                }
                totalcexp = 0.0f;
                mexp = this.db.getAllMCexp(((Integer) this.midarr.get(i)).intValue());
                if (mexp == null) {
                }
                mexp.close();
                tmpmexp = perhead + totalcexp;
                memexp.add(Float.valueOf(tmpmexp));
                totalexpense += tmpmexp;
                totaldeposit = ((Float) this.mamtarr.get(i)).floatValue() + totaldeposit;
                tmpdiff = ((Float) this.mamtarr.get(i)).floatValue() - ((Float) memexp.get(i)).floatValue();
                if (tmpdiff < 0.0f) {
                    totalowes += -1.0f * tmpdiff;
                    memowe.add(Float.valueOf(-1.0f * tmpdiff));
                    memref.add(Float.valueOf(0.0f));
                } else if (tmpdiff != 0.0f) {
                    totalrefunds += tmpdiff;
                    memref.add(Float.valueOf(tmpdiff));
                    memowe.add(Float.valueOf(0.0f));
                } else {
                    memref.add(Float.valueOf(tmpdiff));
                    memowe.add(Float.valueOf(tmpdiff));
                }
                i++;
            }
        }
    }

    public static ArrayList<Float> returnmemexp() {
        return memexp;
    }

    public static ArrayList<Float> returnmemref() {
        return memref;
    }

    public static ArrayList<Float> returnmemowe() {
        return memowe;
    }

    public static Bundle returntotalsB() {
        Bundle totals = new Bundle();
        totals.putFloat("totaldep", totaldeposit);
        totals.putFloat("totalexp", totalexpense);
        totals.putFloat("totalref", totalrefunds);
        totals.putFloat("totalowe", totalowes);
        totals.putFloat("unspent", unspent);
        return totals;
    }
}
