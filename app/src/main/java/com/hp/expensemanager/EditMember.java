package p000h.pkg.main;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

/* renamed from: h.pkg.main.EditMember */
public class EditMember extends Activity {
    String Name;
    float amt;
    DBAdapter db;
    EditText mamt;
    int mid;
    EditText mn;
    float qad;
    EditText qmamt;
    float qsu;

    /* renamed from: h.pkg.main.EditMember.1 */
    class C00531 implements OnTouchListener {
        C00531() {
        }

        public boolean onTouch(View arg0, MotionEvent arg1) {
            ((InputMethodManager) arg0.getContext().getSystemService("input_method")).hideSoftInputFromWindow(arg0.getWindowToken(), 0);
            return false;
        }
    }

    /* renamed from: h.pkg.main.EditMember.2 */
    class C00542 implements OnClickListener {
        C00542() {
        }

        public void onClick(View arg0) {
            EditMember.this.Name = EditMember.this.mn.getText().toString();
            String samt = EditMember.this.mamt.getText().toString();
            if (EditMember.this.Name.equals("") || samt.equals("")) {
                Toast.makeText(EditMember.this, "Enter correct Name and Amount", 500).show();
                return;
            }
            EditMember.this.amt = Float.parseFloat(samt);
            EditMember.this.db.open();
            EditMember.this.db.updateMember(EditMember.this.mid, EditMember.this.Name, EditMember.this.amt);
            EditMember.this.db.close();
            EditMember.this.setResult(-1);
            EditMember.this.finish();
        }
    }

    /* renamed from: h.pkg.main.EditMember.3 */
    class C00553 implements OnClickListener {
        C00553() {
        }

        public void onClick(View arg0) {
            if (EditMember.this.qmamt.getText().toString().equals("")) {
                Toast.makeText(EditMember.this.getBaseContext(), "No amount to update", 1).show();
                return;
            }
            EditMember.this.qad = Float.parseFloat(EditMember.this.qmamt.getText().toString());
            EditMember editMember = EditMember.this;
            editMember.amt += EditMember.this.qad;
            EditMember.this.mamt.setText(EditMember.this.amt);
        }
    }

    /* renamed from: h.pkg.main.EditMember.4 */
    class C00564 implements OnClickListener {
        C00564() {
        }

        public void onClick(View arg0) {
            if (EditMember.this.qmamt.getText().toString().equals("")) {
                Toast.makeText(EditMember.this.getBaseContext(), "No amount to update", 1).show();
                return;
            }
            EditMember.this.qsu = Float.parseFloat(EditMember.this.qmamt.getText().toString());
            EditMember editMember = EditMember.this;
            editMember.qsu *= -1.0f;
            editMember = EditMember.this;
            editMember.amt += EditMember.this.qsu;
            EditMember.this.mamt.setText(EditMember.this.amt);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0065R.layout.em);
        LinearLayout emll = (LinearLayout) findViewById(C0065R.id.emll);
        Bundle idata = getIntent().getExtras();
        this.db = new DBAdapter(this);
        String amount = null;
        if (idata != null) {
            this.mid = idata.getInt(DBAdapter.KEY_MID);
            this.Name = idata.getString("Name");
            this.amt = idata.getFloat("amt");
            amount = this.amt;
        }
        this.mn = (EditText) findViewById(C0065R.id.mname);
        this.mamt = (EditText) findViewById(C0065R.id.mamt);
        this.qmamt = (EditText) findViewById(C0065R.id.qmamt);
        Button update = (Button) findViewById(C0065R.id.update);
        Button qadd = (Button) findViewById(C0065R.id.qadd);
        Button qsub = (Button) findViewById(C0065R.id.qsub);
        this.mn.setText(this.Name);
        this.mamt.setText(amount);
        emll.setOnTouchListener(new C00531());
        update.setOnClickListener(new C00542());
        qadd.setOnClickListener(new C00553());
        qsub.setOnClickListener(new C00564());
    }
}
