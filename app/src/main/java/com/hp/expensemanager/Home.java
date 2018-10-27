package p000h.pkg.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/* renamed from: h.pkg.main.Home */
public class Home extends Activity {

    /* renamed from: h.pkg.main.Home.1 */
    class C00611 implements OnClickListener {
        C00611() {
        }

        public void onClick(View arg0) {
            Intent Create = new Intent(Home.this, Create.class);
            Create.setFlags(67108864);
            Home.this.startActivity(Create);
        }
    }

    /* renamed from: h.pkg.main.Home.2 */
    class C00622 implements OnClickListener {
        C00622() {
        }

        public void onClick(View arg0) {
            Intent Manage = new Intent(Home.this, Manage.class);
            Manage.setFlags(67108864);
            Home.this.startActivity(Manage);
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        setRequestedOrientation(1);
        setContentView(C0065R.layout.main);
        Button Manage = (Button) findViewById(C0065R.id.Manage);
        ((Button) findViewById(C0065R.id.Create)).setOnClickListener(new C00611());
        Manage.setOnClickListener(new C00622());
    }
}
