package p000h.pkg.main;

import android.app.Activity;
import android.app.ListActivity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.BufferType;
import android.widget.Toast;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/* renamed from: h.pkg.main.EditExpense */
public class EditExpense extends ListActivity {
    ArrayAdapter<Model> adapter;
    String amount;
    int celistcount;
    DBAdapter db;
    float eamt;
    EditText edamt;
    int eid;
    String etyp;
    int etypitem;
    Spinner etypsp;
    ArrayList<String> expcatarr;
    String[] exptyp;
    List<Model> list;
    int listcount;
    ArrayList<Float> memcexp;
    ArrayList<Integer> memmid;
    ArrayList<String> memname;
    int mid;
    public int rdb;
    String soc;
    int tid;

    /* renamed from: h.pkg.main.EditExpense.1 */
    class C00471 implements OnItemSelectedListener {
        C00471() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            EditExpense.this.etypitem = EditExpense.this.etypsp.getSelectedItemPosition();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: h.pkg.main.EditExpense.2 */
    class C00482 implements OnFocusChangeListener {
        C00482() {
        }

        public void onFocusChange(View arg0, boolean arg1) {
            if (!arg1 && EditExpense.this.rdb == 1) {
                int i;
                float selcount = 0.0f;
                float examf = Float.parseFloat(((EditText) arg0).getText().toString());
                EditExpense.this.eamt = examf;
                for (i = 0; i < EditExpense.this.listcount; i++) {
                    if (((Model) EditExpense.this.list.get(i)).selected) {
                        selcount += 1.0f;
                    }
                }
                float exshr = Float.parseFloat(new DecimalFormat("#.##").format((double) (examf / selcount)));
                for (i = 0; i < EditExpense.this.listcount; i++) {
                    Model ele = (Model) EditExpense.this.list.get(i);
                    if (ele.selected) {
                        ele.ceexpamt = String.valueOf(exshr);
                    }
                }
                EditExpense.this.updatelist();
            }
        }
    }

    /* renamed from: h.pkg.main.EditExpense.3 */
    class C00493 implements OnCheckedChangeListener {
        C00493() {
        }

        public void onCheckedChanged(RadioGroup group, int checkedId) {
            if (((RadioButton) EditExpense.this.findViewById(C0065R.id.Shared)).isChecked()) {
                int i;
                EditExpense.this.rdb = 1;
                ((Button) EditExpense.this.findViewById(C0065R.id.Shareeven)).setEnabled(true);
                float selcount = 0.0f;
                float examf = Float.parseFloat(EditExpense.this.edamt.getText().toString());
                EditExpense.this.eamt = examf;
                for (i = 0; i < EditExpense.this.listcount; i++) {
                    if (((Model) EditExpense.this.list.get(i)).selected) {
                        selcount += 1.0f;
                    }
                }
                float exshr = Float.parseFloat(new DecimalFormat("#.##").format((double) (examf / selcount)));
                for (i = 0; i < EditExpense.this.listcount; i++) {
                    Model ele = (Model) EditExpense.this.list.get(i);
                    if (ele.selected) {
                        ele.ceexpamt = String.valueOf(exshr);
                    }
                }
                EditExpense.this.updatelist();
                return;
            }
            EditExpense.this.rdb = 2;
            ((Button) EditExpense.this.findViewById(C0065R.id.Shareeven)).setEnabled(false);
            EditExpense.this.setListAdapter(EditExpense.this.adapter);
        }
    }

    /* renamed from: h.pkg.main.EditExpense.InteractiveArrayAdapter */
    public class InteractiveArrayAdapter extends ArrayAdapter<Model> {
        private final Activity context;
        private final List<Model> list;

        /* renamed from: h.pkg.main.EditExpense.InteractiveArrayAdapter.1 */
        class C00501 implements CompoundButton.OnCheckedChangeListener {
            private final /* synthetic */ ViewHolder val$viewHolder;

            C00501(ViewHolder viewHolder) {
                this.val$viewHolder = viewHolder;
            }

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                EditExpense.this.edamt.clearFocus();
                Model elementc = (Model) this.val$viewHolder.checkbox.getTag();
                elementc.setSelected(buttonView.isChecked());
                if (buttonView.isChecked() && EditExpense.this.rdb == 2) {
                    this.val$viewHolder.ceamount.setEnabled(true);
                } else if (EditExpense.this.rdb == 1) {
                    selcount = 0.0f;
                    examf = EditExpense.this.eamt;
                    for (i = 0; i < InteractiveArrayAdapter.this.list.size(); i++) {
                        if (((Model) InteractiveArrayAdapter.this.list.get(i)).selected) {
                            selcount += 1.0f;
                        }
                    }
                    if (selcount == 0.0f) {
                        elementc.setSelected(true);
                        Toast.makeText(EditExpense.this, "Select one member atleast", 1).show();
                    } else {
                        float exshr = Float.parseFloat(new DecimalFormat("#.##").format((double) (examf / selcount)));
                        for (i = 0; i < InteractiveArrayAdapter.this.list.size(); i++) {
                            ele = (Model) InteractiveArrayAdapter.this.list.get(i);
                            if (ele.selected) {
                                ele.ceexpamt = String.valueOf(exshr);
                            } else {
                                ele.ceexpamt = "";
                            }
                        }
                        if (buttonView.isChecked()) {
                            this.val$viewHolder.ceamount.setText(String.valueOf(exshr), BufferType.NORMAL);
                        } else {
                            this.val$viewHolder.ceamount.setText("", BufferType.NORMAL);
                        }
                    }
                } else {
                    selcount = 0.0f;
                    examf = EditExpense.this.eamt;
                    for (i = 0; i < InteractiveArrayAdapter.this.list.size(); i++) {
                        if (((Model) InteractiveArrayAdapter.this.list.get(i)).selected) {
                            selcount += 1.0f;
                        }
                    }
                    if (selcount == 0.0f) {
                        elementc.setSelected(true);
                        Toast.makeText(EditExpense.this, "Select one member atleast", 1).show();
                    } else {
                        for (i = 0; i < InteractiveArrayAdapter.this.list.size(); i++) {
                            ele = (Model) InteractiveArrayAdapter.this.list.get(i);
                            if (!ele.selected) {
                                ele.ceexpamt = "";
                            }
                        }
                        this.val$viewHolder.ceamount.setText("", BufferType.NORMAL);
                    }
                }
                EditExpense.this.updatelist();
            }
        }

        /* renamed from: h.pkg.main.EditExpense.InteractiveArrayAdapter.2 */
        class C00512 implements OnFocusChangeListener {
            private final /* synthetic */ ViewHolder val$viewHolder;

            C00512(ViewHolder viewHolder) {
                this.val$viewHolder = viewHolder;
            }

            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    this.val$viewHolder.ceamount.setFocusable(true);
                    ((Model) this.val$viewHolder.ceamount.getTag()).setCeamt(((EditText) v).getText().toString());
                }
            }
        }

        /* renamed from: h.pkg.main.EditExpense.InteractiveArrayAdapter.3 */
        class C00523 implements OnTouchListener {
            C00523() {
            }

            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (EditExpense.this.edamt.isFocused()) {
                    EditExpense.this.edamt.clearFocus();
                }
                ((InputMethodManager) arg0.getContext().getSystemService("input_method")).hideSoftInputFromWindow(arg0.getWindowToken(), 0);
                return false;
            }
        }

        /* renamed from: h.pkg.main.EditExpense.InteractiveArrayAdapter.ViewHolder */
        class ViewHolder {
            protected EditText ceamount;
            protected CheckBox checkbox;
            protected TextView memnames;

            ViewHolder() {
            }
        }

        public InteractiveArrayAdapter(Activity context, List<Model> list) {
            super(context, C0065R.layout.listview, list);
            this.context = context;
            this.list = list;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                view = this.context.getLayoutInflater().inflate(C0065R.layout.listview, null);
                ViewHolder viewHolder = new ViewHolder();
                viewHolder.memnames = (TextView) view.findViewById(C0065R.id.memname1);
                viewHolder.ceamount = (EditText) view.findViewById(C0065R.id.expamt1);
                viewHolder.checkbox = (CheckBox) view.findViewById(C0065R.id.chkbox1);
                viewHolder.checkbox.setOnCheckedChangeListener(new C00501(viewHolder));
                viewHolder.ceamount.setOnFocusChangeListener(new C00512(viewHolder));
                viewHolder.memnames.setOnTouchListener(new C00523());
                view.setTag(viewHolder);
                viewHolder.memnames.setTag(this.list.get(position));
                viewHolder.checkbox.setTag(this.list.get(position));
                viewHolder.ceamount.setTag(this.list.get(position));
            } else {
                view = convertView;
                ((ViewHolder) view.getTag()).checkbox.setTag(this.list.get(position));
                ((ViewHolder) view.getTag()).memnames.setTag(this.list.get(position));
                ((ViewHolder) view.getTag()).ceamount.setTag(this.list.get(position));
            }
            ViewHolder holder = (ViewHolder) view.getTag();
            holder.memnames.setText(((Model) this.list.get(position)).getName());
            holder.ceamount.setText(((Model) this.list.get(position)).getCeamt());
            holder.checkbox.setChecked(((Model) this.list.get(position)).isSelected());
            if (EditExpense.this.rdb == 1) {
                holder.ceamount.setEnabled(false);
            } else if (holder.checkbox.isChecked()) {
                holder.ceamount.setEnabled(true);
            } else {
                holder.ceamount.setEnabled(false);
            }
            return view;
        }
    }

    public EditExpense() {
        this.rdb = 1;
        this.exptyp = new String[]{"Transport", "Stay", "Lunch", "Breakfast", "Dinner", "Snacks", "Drinks", "Miscellaneous"};
        this.expcatarr = new ArrayList();
        this.memmid = new ArrayList();
        this.memname = new ArrayList();
        this.memcexp = new ArrayList();
        this.list = new ArrayList();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0065R.layout.eexp);
        this.etypsp = (Spinner) findViewById(C0065R.id.ExpTyp);
        this.edamt = (EditText) findViewById(C0065R.id.Examt);
        Bundle idata = getIntent().getExtras();
        this.db = new DBAdapter(this);
        this.db.open();
        if (idata != null) {
            this.eid = idata.getInt(DBAdapter.KEY_EID);
            this.tid = idata.getInt(DBAdapter.KEY_TID);
            this.etyp = idata.getString("ETYP");
            this.soc = idata.getString("ESOC");
            this.eamt = idata.getFloat("EXAMT");
            this.amount = this.eamt;
            this.edamt.setText(this.amount);
        }
        buildExptyp();
        this.etypsp.setAdapter(new ArrayAdapter(this, 17367048, this.expcatarr));
        for (int typ = 0; typ < this.expcatarr.size(); typ++) {
            if (((String) this.expcatarr.get(typ)).equals(this.etyp)) {
                this.etypsp.setSelection(typ);
            }
        }
        this.etypsp.setOnItemSelectedListener(new C00471());
        Cursor c = this.db.getAllmem(this.tid);
        this.listcount = c.getCount();
        Cursor ce = this.db.getAllCexp(this.tid, this.eid);
        this.celistcount = ce.getCount();
        if (this.listcount > this.celistcount && !this.soc.equals("N")) {
            Cursor cexp = this.db.getAllexp(this.tid);
            if (c == null || !c.moveToFirst()) {
                cexp.close();
            } else {
                do {
                    int midl = c.getInt(c.getColumnIndex(DBAdapter.KEY_MID));
                    String memsocl = c.getString(c.getColumnIndex(DBAdapter.FLD_MEMSOC));
                    if (cexp != null && cexp.moveToFirst()) {
                        do {
                            int eidl = cexp.getInt(cexp.getColumnIndex(DBAdapter.KEY_EID));
                            if (!cexp.getString(cexp.getColumnIndex(DBAdapter.FLD_EXPSOC)).equals("N")) {
                                this.db.insertCexp(this.tid, eidl, midl, 0.0f, "N");
                            }
                        } while (cexp.moveToNext());
                    }
                } while (c.moveToNext());
                cexp.close();
            }
        }
        c.close();
        ce.close();
        if (this.soc.equals("N")) {
            c = this.db.getAllmem(this.tid);
            this.listcount = c.getCount();
            float evenshareamt = Float.parseFloat(new DecimalFormat("#.##").format((double) (this.eamt / ((float) c.getCount()))));
            if (c == null || !c.moveToFirst()) {
                c.close();
            } else {
                do {
                    String MN = c.getString(c.getColumnIndex(DBAdapter.FLD_NAME));
                    this.mid = c.getInt(c.getColumnIndex(DBAdapter.KEY_MID));
                    this.list.add(get(MN, String.valueOf(evenshareamt), true));
                    this.memmid.add(Integer.valueOf(this.mid));
                } while (c.moveToNext());
                c.close();
            }
        } else {
            if (this.soc.equals("S")) {
                this.rdb = 1;
            } else {
                this.rdb = 2;
            }
            c = this.db.getAllmem(this.tid);
            ce = this.db.getAllCexp(this.tid, this.eid);
            if (c != null && c.moveToFirst() && ce.moveToFirst()) {
                do {
                    String memname = c.getString(c.getColumnIndex(DBAdapter.FLD_NAME));
                    float mceamt = ce.getFloat(ce.getColumnIndex(DBAdapter.FLD_CEAMT));
                    String mcesel = ce.getString(ce.getColumnIndex(DBAdapter.FLD_CESEL));
                    this.mid = c.getInt(c.getColumnIndex(DBAdapter.KEY_MID));
                    if (mcesel.equals("N")) {
                        this.list.add(get(memname, String.valueOf(mceamt), false));
                    } else {
                        this.list.add(get(memname, String.valueOf(mceamt), true));
                    }
                    this.memmid.add(Integer.valueOf(this.mid));
                    ce.moveToNext();
                } while (c.moveToNext());
                c.close();
                ce.close();
            } else {
                c.close();
                ce.close();
            }
        }
        this.edamt.setOnFocusChangeListener(new C00482());
        this.adapter = new InteractiveArrayAdapter(this, this.list);
        setListAdapter(this.adapter);
        RadioGroup radioGroup = (RadioGroup) findViewById(C0065R.id.radioGroup1);
        RadioButton rbs = (RadioButton) findViewById(C0065R.id.Shared);
        RadioButton rbc = (RadioButton) findViewById(C0065R.id.Custom);
        if (this.rdb == 1) {
            rbs.setChecked(true);
        } else {
            rbc.setChecked(true);
        }
        radioGroup.setOnCheckedChangeListener(new C00493());
    }

    private void buildExptyp() {
        this.expcatarr.clear();
        Cursor ecat = this.db.getAllExpCat();
        if (ecat == null || !ecat.moveToFirst()) {
            ecat.close();
        }
        do {
            String ecatstr = ecat.getString(ecat.getColumnIndex("ECAT"));
            int ecidl = ecat.getInt(ecat.getColumnIndex(DBAdapter.KEY_ECID));
            this.expcatarr.add(ecatstr);
        } while (ecat.moveToNext());
        ecat.close();
    }

    public void expUpdate(View V) {
        if (this.edamt.getText().toString().equals("")) {
            Toast.makeText(this, "No Expense amount entered", 1).show();
            return;
        }
        ((LinearLayout) findViewById(C0065R.id.eexpll)).requestFocus();
        String sw;
        int i;
        Model ele;
        float tmpamt;
        float edcamt;
        float sumceamt;
        if (this.soc.equals("N")) {
            sw = "";
            if (this.rdb != 2) {
                sw = "S";
                for (i = 0; i < this.list.size(); i++) {
                    ele = (Model) this.list.get(i);
                    if (ele.ceexpamt.equals("")) {
                        tmpamt = 0.0f;
                    } else {
                        tmpamt = Float.parseFloat(ele.ceexpamt);
                    }
                    if (ele.selected) {
                        this.db.insertCexp(this.tid, this.eid, ((Integer) this.memmid.get(i)).intValue(), tmpamt, "S");
                        this.db.memSwupdt(((Integer) this.memmid.get(i)).intValue(), "S");
                    } else {
                        this.db.insertCexp(this.tid, this.eid, ((Integer) this.memmid.get(i)).intValue(), tmpamt, "N");
                        this.db.memSwupdt(((Integer) this.memmid.get(i)).intValue(), "N");
                    }
                }
            } else if (this.edamt.getText().toString().equals("")) {
                Toast.makeText(this, "No Expense amount entered", 1).show();
                return;
            } else {
                edcamt = Float.parseFloat(this.edamt.getText().toString());
                sumceamt = 0.0f;
                sw = "C";
                for (i = 0; i < this.list.size(); i++) {
                    ele = (Model) this.list.get(i);
                    if (ele.selected) {
                        if (ele.ceexpamt.equals("")) {
                            sumceamt = (float) (((double) sumceamt) + 0.0d);
                        } else {
                            sumceamt += Float.parseFloat(ele.ceexpamt);
                        }
                    }
                }
                if (edcamt == sumceamt) {
                    for (i = 0; i < this.list.size(); i++) {
                        ele = (Model) this.list.get(i);
                        if (ele.ceexpamt.equals("")) {
                            tmpamt = 0.0f;
                        } else {
                            tmpamt = Float.parseFloat(ele.ceexpamt);
                        }
                        if (ele.selected) {
                            this.db.insertCexp(this.tid, this.eid, ((Integer) this.memmid.get(i)).intValue(), tmpamt, "C");
                            this.db.memSwupdt(((Integer) this.memmid.get(i)).intValue(), "C");
                        } else {
                            this.db.insertCexp(this.tid, this.eid, ((Integer) this.memmid.get(i)).intValue(), tmpamt, "N");
                            this.db.memSwupdt(((Integer) this.memmid.get(i)).intValue(), "N");
                        }
                    }
                } else {
                    Toast.makeText(this, "Amount not shared properly", 1).show();
                    return;
                }
            }
            this.db.updateExpense(this.tid, this.eid, Float.parseFloat(this.edamt.getText().toString()), (String) this.expcatarr.get(this.etypitem), sw);
            this.db.close();
            setResult(-1);
            finish();
            return;
        }
        sw = "";
        if (this.rdb != 2) {
            sw = "S";
            for (i = 0; i < this.list.size(); i++) {
                ele = (Model) this.list.get(i);
                if (ele.ceexpamt.equals("")) {
                    tmpamt = 0.0f;
                } else {
                    tmpamt = Float.parseFloat(ele.ceexpamt);
                }
                if (ele.selected) {
                    this.db.updateCExpense(((Integer) this.memmid.get(i)).intValue(), this.eid, tmpamt, "S");
                    this.db.memSwupdt(((Integer) this.memmid.get(i)).intValue(), "S");
                } else {
                    this.db.updateCExpense(((Integer) this.memmid.get(i)).intValue(), this.eid, tmpamt, "N");
                    this.db.memSwupdt(((Integer) this.memmid.get(i)).intValue(), "N");
                }
            }
        } else if (this.edamt.getText().toString().equals("")) {
            Toast.makeText(this, "No Expense amount entered", 1).show();
            return;
        } else {
            edcamt = Float.parseFloat(this.edamt.getText().toString());
            sumceamt = 0.0f;
            sw = "C";
            for (i = 0; i < this.list.size(); i++) {
                ele = (Model) this.list.get(i);
                if (ele.selected) {
                    if (ele.ceexpamt.equals("")) {
                        sumceamt += 0.0f;
                    } else {
                        sumceamt += Float.parseFloat(ele.ceexpamt);
                    }
                }
            }
            if (edcamt == sumceamt) {
                for (i = 0; i < this.list.size(); i++) {
                    ele = (Model) this.list.get(i);
                    if (ele.ceexpamt.equals("")) {
                        tmpamt = 0.0f;
                    } else {
                        tmpamt = Float.parseFloat(ele.ceexpamt);
                    }
                    if (ele.selected) {
                        this.db.updateCExpense(((Integer) this.memmid.get(i)).intValue(), this.eid, tmpamt, "C");
                        this.db.memSwupdt(((Integer) this.memmid.get(i)).intValue(), "C");
                    } else {
                        this.db.updateCExpense(((Integer) this.memmid.get(i)).intValue(), this.eid, tmpamt, "N");
                        this.db.memSwupdt(((Integer) this.memmid.get(i)).intValue(), "N");
                    }
                }
            } else {
                Toast.makeText(this, "Amount not shared properly " + edcamt + " " + sumceamt, 1).show();
                return;
            }
        }
        this.db.updateExpense(this.tid, this.eid, Float.parseFloat(this.edamt.getText().toString()), (String) this.expcatarr.get(this.etypitem), sw);
        this.db.close();
        setResult(-1);
        finish();
    }

    public void shareEven(View V) {
        if (this.edamt.getText().toString().equals("")) {
            Toast.makeText(this, "No Expense amount entered", 1).show();
            return;
        }
        ((InputMethodManager) V.getContext().getSystemService("input_method")).hideSoftInputFromWindow(V.getWindowToken(), 0);
        if (this.edamt.isFocused()) {
            this.edamt.clearFocus();
        }
    }

    public void updatelist() {
        this.adapter.notifyDataSetChanged();
    }

    private Model get(String memn, String ceam, boolean sel) {
        return new Model(memn, ceam, sel);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 4 && event.getRepeatCount() == 0) {
            this.db.close();
        }
        return super.onKeyDown(keyCode, event);
    }
}
