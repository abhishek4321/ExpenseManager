package p000h.pkg.main;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.tapit.adview.AdView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import jxl.CellView;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

/* renamed from: h.pkg.main.ExportActivity */
public class ExportActivity extends Activity {
    int TID;
    private AdView bannerAd;
    DBAdapter db;
    Button email;
    Spinner emftyp;
    Spinner exftyp;
    Button export;
    int fityp;
    String[] ftyp;
    ArrayList<Float> memexp;
    ArrayList<Float> memowe;
    ArrayList<Float> memref;
    int nl;
    TextView status;
    private WritableCellFormat times;
    private WritableCellFormat timesBoldUnderline;
    String tname;
    int tnl;
    private float totaldeposit;
    private float totalexpense;
    private float totalowes;
    private float totalrefunds;
    private float unspent;

    /* renamed from: h.pkg.main.ExportActivity.1 */
    class C00581 implements OnItemSelectedListener {
        C00581() {
        }

        public void onItemSelected(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
            ExportActivity.this.fityp = ExportActivity.this.exftyp.getSelectedItemPosition();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    }

    /* renamed from: h.pkg.main.ExportActivity.2 */
    class C00592 implements OnClickListener {
        C00592() {
        }

        public void onClick(View arg0) {
            String Fnamexls = "Report-" + ExportActivity.this.tname + ".xls";
            String Fnamehtml = "Report-" + ExportActivity.this.tname + ".htm";
            File directory = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/TripManager").toString());
            directory.mkdirs();
            ExportActivity.this.db.open();
            if (ExportActivity.this.fityp == 0) {
                File file = new File(directory, Fnamexls);
                WorkbookSettings wbSettings = new WorkbookSettings();
                wbSettings.setLocale(new Locale("en", "EN"));
                try {
                    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
                    workbook.createSheet("Report", 0);
                    workbook.setColourRGB(Colour.LIGHT_TURQUOISE2, 141, 180, 226);
                    workbook.setColourRGB(Colour.BRIGHT_GREEN, 155, 187, 89);
                    workbook.setColourRGB(Colour.ROSE, 218, 150, 148);
                    workbook.setColourRGB(Colour.DARK_GREEN, 73, 69, 41);
                    ExportActivity.this.createExcel(workbook.getSheet(0));
                    workbook.write();
                    workbook.close();
                    ExportActivity.this.status.setText("Excel Report " + Fnamexls + " exported to sdcard/TripManager folder");
                    return;
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (WriteException e2) {
                    e2.printStackTrace();
                    return;
                }
            }
            FileOutputStream fOut = null;
            try {
                fOut = new FileOutputStream(new File(directory, Fnamehtml));
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
            }
            OutputStreamWriter osw = new OutputStreamWriter(fOut);
            try {
                osw.write(ExportActivity.this.createHTML());
                osw.flush();
                osw.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
            ExportActivity.this.status.setText("HTML Report " + Fnamehtml + " exported to sdcard/TripManager folder");
        }
    }

    /* renamed from: h.pkg.main.ExportActivity.3 */
    class C00603 implements OnClickListener {
        C00603() {
        }

        public void onClick(View arg0) {
            String Fnamexls = "Report-" + ExportActivity.this.tname + ".xls";
            String Fnamehtml = "Report-" + ExportActivity.this.tname + ".htm";
            String sdfile = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/TripManager/").toString();
            File directory = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/TripManager").toString());
            directory.mkdirs();
            ExportActivity.this.db.open();
            if (ExportActivity.this.fityp == 0) {
                File file = new File(directory, Fnamexls);
                WorkbookSettings wbSettings = new WorkbookSettings();
                wbSettings.setLocale(new Locale("en", "EN"));
                try {
                    WritableWorkbook workbook = Workbook.createWorkbook(file, wbSettings);
                    workbook.createSheet("Report", 0);
                    workbook.setColourRGB(Colour.LIGHT_TURQUOISE2, 141, 180, 226);
                    workbook.setColourRGB(Colour.BRIGHT_GREEN, 155, 187, 89);
                    workbook.setColourRGB(Colour.ROSE, 218, 150, 148);
                    workbook.setColourRGB(Colour.DARK_GREEN, 73, 69, 41);
                    WritableSheet excelSheet = workbook.getSheet(0);
                    ExportActivity.this.createExcel(excelSheet);
                    workbook.write();
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e2) {
                    e2.printStackTrace();
                }
            } else {
                FileOutputStream fOut = null;
                try {
                    fOut = new FileOutputStream(new File(directory, Fnamehtml));
                } catch (FileNotFoundException e3) {
                    e3.printStackTrace();
                }
                OutputStreamWriter osw = new OutputStreamWriter(fOut);
                try {
                    osw.write(ExportActivity.this.createHTML());
                    osw.flush();
                    osw.close();
                } catch (IOException e4) {
                    e4.printStackTrace();
                }
            }
            Intent emailIntent = new Intent("android.intent.action.SEND");
            String address = "hamdy.ghanem@gmail.com";
            String subject = "Report - " + ExportActivity.this.tname;
            String emailtext = "Please check the attached report for " + ExportActivity.this.tname;
            emailIntent.setType("plain/text");
            emailIntent.putExtra("android.intent.extra.SUBJECT", subject);
            if (ExportActivity.this.fityp == 0) {
                emailIntent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + sdfile + Fnamexls));
            } else {
                emailIntent.putExtra("android.intent.extra.STREAM", Uri.parse("file://" + sdfile + Fnamehtml));
            }
            emailIntent.putExtra("android.intent.extra.TEXT", emailtext);
            ExportActivity.this.startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        }
    }

    public ExportActivity() {
        this.ftyp = new String[]{"Excel", "HTML"};
        this.memexp = new ArrayList();
        this.memref = new ArrayList();
        this.memowe = new ArrayList();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C0065R.layout.export);
        this.db = new DBAdapter(this);
        setupBannerAd();
        Bundle input = getIntent().getExtras();
        this.memexp = Report.returnmemexp();
        this.memref = Report.returnmemref();
        this.memowe = Report.returnmemowe();
        Bundle totals = Report.returntotalsB();
        this.totaldeposit = totals.getFloat("totaldep");
        this.totalexpense = totals.getFloat("totalexp");
        this.totalrefunds = totals.getFloat("totalref");
        this.totalowes = totals.getFloat("totalowe");
        this.unspent = totals.getFloat("unspent");
        this.tname = input.getString("TNAME");
        this.TID = input.getInt(DBAdapter.KEY_TID);
        this.exftyp = (Spinner) findViewById(C0065R.id.exftyp);
        this.export = (Button) findViewById(C0065R.id.exportB);
        this.email = (Button) findViewById(C0065R.id.emailB);
        this.status = (TextView) findViewById(C0065R.id.info1);
        this.status.setText("");
        this.exftyp.setAdapter(new ArrayAdapter(this, 17367048, this.ftyp));
        this.exftyp.setOnItemSelectedListener(new C00581());
        this.export.setOnClickListener(new C00592());
        this.email.setOnClickListener(new C00603());
    }

    private void setupBannerAd() {
        this.bannerAd = (AdView) findViewById(C0065R.id.bannerAd);
        this.bannerAd.setBackgroundColor(0);
    }

    private String createHTML() {
        int i;
        InputStream is = getResources().openRawResource(C0065R.raw.part);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String part1 = "";
        while (true) {
            String str = br.readLine();
            if (str == null) {
                break;
            }
            try {
                part1 = new StringBuilder(String.valueOf(part1)).append(str).toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        is.close();
        br.close();
        String tripname = "<h1> Report - " + this.tname + "</h1>";
        String part3 = "<table><tr><th bgcolor=\"#000000\"><div align=\"center\"><span class=\"style7\">Members</span></div></th>";
        String memnames1 = "<th bgcolor=\"#000000\"><div align=\"center\"><span class=\"style7\">";
        String memnames2 = "</span></div></th>";
        String part5 = "</tr><tr bgcolor=\"#66CCFF\"><td>Amount Given >></td>";
        String part7 = "</tr><tr><td bgcolor=\"#000000\"><div align=\"center\"><span class=\"style7\">Expenses</span></div></td>";
        String mn1 = "<td bgcolor=\"#000000\"><div align=\"center\"><span class=\"style7\">";
        String mn2 = "</span></div></td>";
        String part9 = "</tr>";
        String exp1 = "<tr bgcolor=\"#66CCFF\">";
        String exp2 = "</tr>";
        int memcount = 0;
        String part4 = "";
        String part6 = "";
        String part8 = "";
        Cursor c = this.db.getAllmem(this.TID);
        if (c != null) {
            memcount = c.getCount();
            if (c.moveToFirst()) {
                i = 2;
                do {
                    String MN = c.getString(c.getColumnIndex(DBAdapter.FLD_NAME));
                    float aramt = c.getFloat(c.getColumnIndex(DBAdapter.FLD_MEMAMT));
                    part4 = new StringBuilder(String.valueOf(part4)).append(memnames1).append(MN).append(memnames2).toString();
                    part6 = new StringBuilder(String.valueOf(part6)).append("<td>").append(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) aramt)))).append("</td>").toString();
                    part8 = new StringBuilder(String.valueOf(part8)).append(mn1).append(MN).append(mn2).toString();
                    i++;
                } while (c.moveToNext());
            }
        }
        c.close();
        Cursor c1 = this.db.getAllexp(this.TID);
        String part10 = null;
        if (c1 != null) {
            int expcount = c1.getCount();
            if (c1.moveToFirst()) {
                do {
                    String Exptyp = c1.getString(c1.getColumnIndex(DBAdapter.FLD_EXPTYP));
                    String Expsoc = c1.getString(c1.getColumnIndex(DBAdapter.FLD_EXPSOC));
                    float expamt = c1.getFloat(c1.getColumnIndex(DBAdapter.FLD_EXPAMT));
                    int eid = c1.getInt(c1.getColumnIndex(DBAdapter.KEY_EID));
                    part10 = new StringBuilder(String.valueOf(part10)).append(exp1).append("<td>").append(Exptyp).append("</td>").toString();
                    if (Expsoc.equals("N")) {
                        float perhexp = expamt / ((float) memcount);
                        for (i = 0; i < memcount; i++) {
                            part10 = new StringBuilder(String.valueOf(part10)).append("<td>").append(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) perhexp)))).append("</td>").toString();
                        }
                    } else {
                        Cursor c2 = this.db.getAllCexp(this.TID, eid);
                        if (c2 != null && c2.moveToFirst()) {
                            for (i = 0; i < memcount; i++) {
                                part10 = new StringBuilder(String.valueOf(part10)).append("<td>").append(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) c2.getFloat(c2.getColumnIndex(DBAdapter.FLD_CEAMT)))))).append("</td>").toString();
                            }
                        }
                        c2.close();
                    }
                    part10 = new StringBuilder(String.valueOf(part10)).append(exp2).toString();
                } while (c1.moveToNext());
                c1.close();
            }
        }
        String part11 = "<tr bgcolor=\"#9999FF\"><td><span class=\"style9\">Per Head Expense</span></td>";
        String part12 = "<tr bgcolor=\"#00FFCC\"><td>Member Refund Amount</td>";
        String part13 = "<tr bgcolor=\"#FF9999\"><td>Amount Owed by Member</td>";
        for (i = 0; i < memcount; i++) {
            part11 = new StringBuilder(String.valueOf(part11)).append("<td><span class=\"style9\">").append(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format(this.memexp.get(i))))).append("</td>").toString();
            part12 = new StringBuilder(String.valueOf(part12)).append("<td>").append(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format(this.memref.get(i))))).append("</td>").toString();
            part13 = new StringBuilder(String.valueOf(part13)).append("<td>").append(String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format(this.memowe.get(i))))).append("</td>").toString();
        }
        part11 = new StringBuilder(String.valueOf(part11)).append("</tr>").toString();
        part12 = new StringBuilder(String.valueOf(part12)).append("</tr>").toString();
        part13 = new StringBuilder(String.valueOf(part13)).append("</tr></table>").toString();
        String part14 = "<h2>Totals </h2><table width=\"200\" border=\"1\"><tr bgcolor=\"#000000\"><th scope=\"col\"><span class=\"style10\">Total</span></th><th scope=\"col\"><span class=\"style10\">Amount</span></th></tr><tr><td bgcolor=\"#66CCFF\">Total Deposit</td><td bgcolor=\"#66CCFF\">" + String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) this.totaldeposit))) + "</td></tr><tr><td bgcolor=\"#66CCFF\">Total Expense</td><td bgcolor=\"#66CCFF\">" + String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) this.totalexpense))) + "</td></tr><tr><td bgcolor=\"#66CCFF\">Unspent</td><td bgcolor=\"#66CCFF\">" + String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) this.unspent))) + "</td></tr><tr><td bgcolor=\"#66CCFF\">&nbsp;</td><td bgcolor=\"#66CCFF\">&nbsp;</td></tr>" + "<tr><td bgcolor=\"#66CCFF\">Total Refunds</td><td bgcolor=\"#66CCFF\">" + String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) this.totalrefunds))) + "</td></tr><tr><td bgcolor=\"#66CCFF\">Total Owed + Unspent</td><td bgcolor=\"#66CCFF\">" + String.valueOf(Float.parseFloat(new DecimalFormat("#.##").format((double) (this.totalowes + this.unspent)))) + "</td></tr></table></body></html>";
        return new StringBuilder(String.valueOf(part1)).append(tripname).append(part3).append(part4).append(part5).append(part6).append(part7).append(part8).append(part9).append(part10).append(part11).append(part12).append(part13).append(part14).toString();
    }

    private void createExcel(WritableSheet sheet) throws WriteException {
        int i;
        WritableFont white = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.WHITE);
        WritableCellFormat writableCellFormat = new WritableCellFormat(white);
        writableCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        writableCellFormat.setAlignment(Alignment.LEFT);
        writableCellFormat.setBackground(Colour.PALETTE_BLACK);
        writableCellFormat = new WritableCellFormat(white);
        writableCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        writableCellFormat.setAlignment(Alignment.LEFT);
        writableCellFormat.setBackground(Colour.DARK_GREEN);
        WritableFont black = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.BLACK);
        WritableCellFormat BOB = new WritableCellFormat(black);
        BOB.setBorder(Border.ALL, BorderLineStyle.THIN);
        BOB.setAlignment(Alignment.LEFT);
        BOB.setBackground(Colour.LIGHT_TURQUOISE2);
        writableCellFormat = new WritableCellFormat(black);
        writableCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        writableCellFormat.setAlignment(Alignment.LEFT);
        writableCellFormat.setBackground(Colour.GRAY_50);
        writableCellFormat = new WritableCellFormat(black);
        writableCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        writableCellFormat.setAlignment(Alignment.LEFT);
        writableCellFormat.setBackground(Colour.BRIGHT_GREEN);
        writableCellFormat = new WritableCellFormat(black);
        writableCellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        writableCellFormat.setAlignment(Alignment.LEFT);
        writableCellFormat.setBackground(Colour.ROSE);
        addCaption(sheet, 1, 1, "MEMBERS >> ", writableCellFormat);
        addCaption(sheet, 1, 2, "Amount Given >>", BOB);
        addCaption(sheet, 1, 3, "Expenses ", writableCellFormat);
        int memcount = 0;
        this.nl = 0;
        Cursor c = this.db.getAllmem(this.TID);
        if (c != null) {
            memcount = c.getCount();
            if (c.moveToFirst()) {
                i = 2;
                do {
                    String MN = c.getString(c.getColumnIndex(DBAdapter.FLD_NAME));
                    float aramt = c.getFloat(c.getColumnIndex(DBAdapter.FLD_MEMAMT));
                    this.tnl = MN.length();
                    if (this.tnl > this.nl) {
                        this.nl = this.tnl;
                    }
                    addCaption(sheet, i, 1, MN, writableCellFormat);
                    addNumber(sheet, i, 2, Float.valueOf(aramt), BOB);
                    addCaption(sheet, i, 3, MN, writableCellFormat);
                    i++;
                } while (c.moveToNext());
            }
        }
        c.close();
        Cursor c1 = this.db.getAllexp(this.TID);
        int row = 0;
        if (c1 != null) {
            int expcount = c1.getCount();
            if (c1.moveToFirst()) {
                row = 4;
                do {
                    WritableCellFormat lwcf = BOB;
                    if (row % 2 == 0) {
                        lwcf = writableCellFormat;
                    }
                    String Exptyp = c1.getString(c1.getColumnIndex(DBAdapter.FLD_EXPTYP));
                    String Expsoc = c1.getString(c1.getColumnIndex(DBAdapter.FLD_EXPSOC));
                    float expamt = c1.getFloat(c1.getColumnIndex(DBAdapter.FLD_EXPAMT));
                    int eid = c1.getInt(c1.getColumnIndex(DBAdapter.KEY_EID));
                    addCaption(sheet, 1, row, Exptyp, lwcf);
                    if (Expsoc.equals("N")) {
                        float perhexp = expamt / ((float) memcount);
                        for (i = 2; i < memcount + 2; i++) {
                            addNumber(sheet, i, row, Float.valueOf(perhexp), lwcf);
                        }
                    } else {
                        Cursor c2 = this.db.getAllCexp(this.TID, eid);
                        if (c2 != null && c2.moveToFirst()) {
                            for (i = 2; i < memcount + 2; i++) {
                                WritableSheet writableSheet = sheet;
                                int i2 = i;
                                int i3 = row;
                                addNumber(writableSheet, i2, i3, Float.valueOf(c2.getFloat(c2.getColumnIndex(DBAdapter.FLD_CEAMT))), lwcf);
                            }
                        }
                        c2.close();
                    }
                    row++;
                } while (c1.moveToNext());
                c1.close();
            }
        }
        addCaption(sheet, 1, row, "Per Head Expense", writableCellFormat);
        addCaption(sheet, 1, row + 1, "Refund Member Amount", writableCellFormat);
        addCaption(sheet, 1, row + 2, "Amount Owed by Member", writableCellFormat);
        i = 2;
        int j = 0;
        while (i < memcount + 2) {
            addNumber(sheet, i, row, (Float) this.memexp.get(j), writableCellFormat);
            addNumber(sheet, i, row + 1, (Float) this.memref.get(j), writableCellFormat);
            addNumber(sheet, i, row + 2, (Float) this.memowe.get(j), writableCellFormat);
            i++;
            j++;
        }
        addCaption(sheet, 1, row + 4, "Totals", writableCellFormat);
        addCaption(sheet, 2, row + 4, "Amounts", writableCellFormat);
        addCaption(sheet, 1, row + 5, "Total Amount Given", BOB);
        addNumber(sheet, 2, row + 5, Float.valueOf(this.totaldeposit), BOB);
        addCaption(sheet, 1, row + 6, "Total Expenses", BOB);
        addNumber(sheet, 2, row + 6, Float.valueOf(this.totalexpense), BOB);
        addCaption(sheet, 1, row + 7, "Unspent", BOB);
        addNumber(sheet, 2, row + 7, Float.valueOf(this.unspent), BOB);
        addCaption(sheet, 1, row + 9, "Total Refunds", BOB);
        addNumber(sheet, 2, row + 9, Float.valueOf(this.totalrefunds), BOB);
        addCaption(sheet, 1, row + 10, "Total Owed + Unspent", BOB);
        addNumber(sheet, 2, row + 10, Float.valueOf(this.totalowes + this.unspent), BOB);
        this.db.close();
        CellView cf = new CellView();
        CellView cf1 = new CellView();
        cf.setSize(8400);
        this.nl = (this.nl + ((this.nl / 2) + (this.nl / 4))) * 256;
        cf1.setSize((int) (((double) this.nl) * 0.75d));
        sheet.setColumnView(1, cf);
        for (i = 2; i < memcount + 2; i++) {
            sheet.setColumnView(i, cf1);
        }
    }

    private void addCaption(WritableSheet sheet, int column, int row, String s, WritableCellFormat wcf) throws RowsExceededException, WriteException {
        sheet.addCell(new Label(column, row, s, wcf));
    }

    private void addNumber(WritableSheet sheet, int column, int row, Float flt, WritableCellFormat wcf) throws WriteException, RowsExceededException {
        sheet.addCell(new Number(column, row, (double) flt.floatValue(), wcf));
    }
}
