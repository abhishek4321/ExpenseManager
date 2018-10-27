package p000h.pkg.main;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

/* renamed from: h.pkg.main.TriptabActivity */
public class TriptabActivity extends TabActivity {
    int TID;

    /* renamed from: h.pkg.main.TriptabActivity.1 */
    class C00661 implements OnTabChangeListener {
        private final /* synthetic */ TabHost val$tabHost;

        C00661(TabHost tabHost) {
            this.val$tabHost = tabHost;
        }

        public void onTabChanged(String tabId) {
            for (int i = 0; i < this.val$tabHost.getTabWidget().getChildCount(); i++) {
                this.val$tabHost.getTabWidget().getChildAt(i).setBackgroundColor(Color.parseColor("#000000"));
            }
            this.val$tabHost.getTabWidget().getChildAt(this.val$tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#848684"));
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setContentView(C0065R.layout.triptab);
        Bundle idata = getIntent().getExtras();
        if (idata != null) {
            this.TID = idata.getInt(DBAdapter.KEY_TID);
        }
        Resources res = getResources();
        TabHost tabHost = getTabHost();
        Intent intent = new Intent().setClass(this, AddMember.class);
        Bundle Data = new Bundle();
        Data.putInt(DBAdapter.KEY_TID, this.TID);
        intent.putExtras(Data);
        tabHost.addTab(tabHost.newTabSpec("addmem").setIndicator("Members", res.getDrawable(C0065R.drawable.tab_members)).setContent(intent));
        intent = new Intent().setClass(this, AddExp.class);
        Data = new Bundle();
        Data.putInt(DBAdapter.KEY_TID, this.TID);
        intent.putExtras(Data);
        tabHost.addTab(tabHost.newTabSpec("addexp").setIndicator("Expenses", res.getDrawable(C0065R.drawable.tab_expenses)).setContent(intent));
        intent = new Intent().setClass(this, Report.class);
        Data = new Bundle();
        Data.putInt(DBAdapter.KEY_TID, this.TID);
        intent.putExtras(Data);
        tabHost.addTab(tabHost.newTabSpec("report").setIndicator("Reports", res.getDrawable(C0065R.drawable.tab_reports)).setContent(intent));
        tabHost.setCurrentTab(0);
        tabHost.getTabWidget().getChildAt(tabHost.getCurrentTab()).setBackgroundColor(Color.parseColor("#848684"));
        tabHost.setOnTabChangedListener(new C00661(tabHost));
    }
}
