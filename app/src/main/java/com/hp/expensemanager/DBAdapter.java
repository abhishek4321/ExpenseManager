package p000h.pkg.main;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* renamed from: h.pkg.main.DBAdapter */
public class DBAdapter {
    private static final String CETB_CREATE = "CREATE  TABLE  IF NOT EXISTS CETB (CEID INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL, CETID INTEGER, CEEID INTEGER, CEMID INTEGER, CEAMT FLOAT, CESEL TEXT,UNIQUE(CEEID, CEMID) ON CONFLICT IGNORE)";
    private static final String CEXP_TABLE = "CETB";
    private static final String DATABASE_NAME = "TRIPDB";
    private static final int DATABASE_VERSION = 1;
    private static final String EXPCTB_CREATE = "CREATE  TABLE  IF NOT EXISTS EXPCTB (ECID INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL , ECAT TEXT, UNIQUE(ECAT) ON CONFLICT IGNORE)";
    private static final String EXPC_TABLE = "EXPCTB";
    private static final String EXPTB_CREATE = "CREATE  TABLE  IF NOT EXISTS EXPTB (EID INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL, TID INTEGER, EXPTYP TEXT, EXPAMT FLOAT, EXPSOC TEXT)";
    private static final String EXP_TABLE = "EXPTB";
    public static final String FLD_CEAMT = "CEAMT";
    public static final String FLD_CEEID = "CEEID";
    public static final String FLD_CEMID = "CEMID";
    public static final String FLD_CESEL = "CESEL";
    public static final String FLD_CETID = "CETID";
    private static final String FLD_ECAT = "ECAT";
    public static final String FLD_EXPAMT = "EXPAMT";
    public static final String FLD_EXPCSW = "EXPCSW";
    public static final String FLD_EXPSOC = "EXPSOC";
    public static final String FLD_EXPTYP = "EXPTYP";
    public static final String FLD_FD = "FD";
    public static final String FLD_MEMAMT = "MEMAMT";
    public static final String FLD_MEMSOC = "MEMSOC";
    public static final String FLD_NAME = "NAME";
    public static final String FLD_NOD = "NOD";
    public static final String FLD_TN = "TN";
    public static final String KEY_CEID = "CEID";
    public static final String KEY_ECID = "ECID";
    public static final String KEY_EID = "EID";
    public static final String KEY_MID = "MID";
    public static final String KEY_TID = "TID";
    private static final String MEMTB_CREATE = "CREATE  TABLE  IF NOT EXISTS MEMTB (MID INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL, TID INTEGER, NAME TEXT, MEMAMT FLOAT, MEMSOC TEXT)";
    private static final String MEM_TABLE = "MEMTB";
    private static final String TRIPTB_CREATE = "CREATE  TABLE  IF NOT EXISTS TRIPTB (TID INTEGER PRIMARY KEY AUTOINCREMENT  NOT NULL , TN TEXT, FD TEXT, NOD TEXT)";
    private static final String TRIP_TABLE = "TRIPTB";
    private DatabaseHelper DBHelper;
    private final Context context;
    private SQLiteDatabase db;

    /* renamed from: h.pkg.main.DBAdapter.DatabaseHelper */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context) {
            super(context, DBAdapter.DATABASE_NAME, null, DBAdapter.DATABASE_VERSION);
        }

        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(DBAdapter.TRIPTB_CREATE);
                db.execSQL(DBAdapter.MEMTB_CREATE);
                db.execSQL(DBAdapter.EXPTB_CREATE);
                db.execSQL(DBAdapter.CETB_CREATE);
                db.execSQL(DBAdapter.EXPCTB_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onCreate(db);
        }
    }

    public DBAdapter(Context ctx) {
        this.context = ctx;
        this.DBHelper = new DatabaseHelper(this.context);
    }

    public DBAdapter open() throws SQLException {
        this.db = this.DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        this.DBHelper.close();
    }

    public long insertTrip(String tripname, String date, String nod) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(FLD_TN, tripname);
        initialValues.put(FLD_FD, date);
        initialValues.put(FLD_NOD, nod);
        return this.db.insert(TRIP_TABLE, null, initialValues);
    }

    public long insertmem(int TID, String name, float amt, String soc) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TID, Integer.valueOf(TID));
        initialValues.put(FLD_NAME, name);
        initialValues.put(FLD_MEMAMT, Float.valueOf(amt));
        initialValues.put(FLD_MEMSOC, soc);
        return this.db.insert(MEM_TABLE, null, initialValues);
    }

    public long insertexp(int TID, String exptyp, float amt, String soc) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_TID, Integer.valueOf(TID));
        initialValues.put(FLD_EXPTYP, exptyp);
        initialValues.put(FLD_EXPAMT, Float.valueOf(amt));
        initialValues.put(FLD_EXPSOC, soc);
        return this.db.insert(EXP_TABLE, null, initialValues);
    }

    public long insertCexp(int TID, int EID, int MID, float amt, String selected) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(FLD_CETID, Integer.valueOf(TID));
        initialValues.put(FLD_CEEID, Integer.valueOf(EID));
        initialValues.put(FLD_CEMID, Integer.valueOf(MID));
        initialValues.put(FLD_CEAMT, Float.valueOf(amt));
        initialValues.put(FLD_CESEL, selected);
        return this.db.insert(CEXP_TABLE, null, initialValues);
    }

    public long insertExpcat(String ecat) {
        ContentValues initialValues = new ContentValues();
        initialValues.put(FLD_ECAT, ecat);
        return this.db.insert(EXPC_TABLE, null, initialValues);
    }

    public boolean deleteTrip(int tid) {
        int ret = this.db.delete(CEXP_TABLE, "CETID=" + tid, null);
        ret = this.db.delete(EXP_TABLE, "TID=" + tid, null);
        ret = this.db.delete(MEM_TABLE, "TID=" + tid, null);
        if (this.db.delete(TRIP_TABLE, "TID=" + tid, null) > 0) {
            return true;
        }
        return false;
    }

    public boolean deleteMember(int mid) {
        return this.db.delete(MEM_TABLE, new StringBuilder("MID=").append(mid).toString(), null) > 0;
    }

    public boolean deleteExpense(int eid) {
        return this.db.delete(EXP_TABLE, new StringBuilder("EID=").append(eid).toString(), null) > 0;
    }

    public boolean deleteCExpense(int mid) {
        return this.db.delete(CEXP_TABLE, new StringBuilder("CEMID=").append(mid).toString(), null) > 0;
    }

    public boolean deleteCExpenseEID(int eid) {
        return this.db.delete(CEXP_TABLE, new StringBuilder("CEEID=").append(eid).toString(), null) > 0;
    }

    public boolean deleteExpenseCat(int ecid) {
        return this.db.delete(EXPC_TABLE, new StringBuilder("ECID=").append(ecid).toString(), null) > 0;
    }

    public Cursor getAllExpCat() {
        return this.db.query(EXPC_TABLE, new String[]{KEY_ECID, FLD_ECAT}, null, null, null, null, null);
    }

    public Cursor getAlltrips() {
        return this.db.query(TRIP_TABLE, new String[]{KEY_TID, FLD_TN, FLD_FD, FLD_NOD}, null, null, null, null, null);
    }

    public Cursor getTrip(int TID) {
        return this.db.query(TRIP_TABLE, new String[]{KEY_TID, FLD_TN, FLD_FD, FLD_NOD}, "TID=" + TID, null, null, null, null);
    }

    public Cursor getAllmem(int TID) throws SQLException {
        Cursor mCursor = this.db.query(false, MEM_TABLE, new String[]{KEY_MID, KEY_TID, FLD_NAME, FLD_MEMAMT, FLD_MEMSOC}, "TID=" + TID, null, null, null, KEY_MID, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getMem(int MID) throws SQLException {
        Cursor mCursor = this.db.query(false, MEM_TABLE, new String[]{KEY_MID, KEY_TID, FLD_NAME, FLD_MEMAMT, FLD_MEMSOC}, "MID=" + MID, null, null, null, KEY_MID, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getAllexp(int TID) throws SQLException {
        Cursor mCursor = this.db.query(false, EXP_TABLE, new String[]{KEY_EID, KEY_TID, FLD_EXPTYP, FLD_EXPAMT, FLD_EXPSOC}, "TID=" + TID, null, null, null, KEY_EID, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getAllucexp(int TID) throws SQLException {
        String lTID = String.valueOf(TID);
        Cursor mCursor = this.db.query(false, EXP_TABLE, new String[]{KEY_EID, KEY_TID, FLD_EXPTYP, FLD_EXPAMT, FLD_EXPSOC}, "TID = ? AND EXPSOC = ? ", new String[]{lTID, "N"}, null, null, KEY_EID, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getAllCexp(int TID, int EID) throws SQLException {
        String lTID = String.valueOf(TID);
        String lEID = String.valueOf(EID);
        Cursor mCursor = this.db.query(false, CEXP_TABLE, new String[]{KEY_CEID, FLD_CETID, FLD_CEEID, FLD_CEMID, FLD_CEAMT, FLD_CESEL}, "CEEID = ? AND CETID = ? ", new String[]{lEID, lTID}, null, null, FLD_CEMID, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getAllMCexp(int MID) throws SQLException {
        Cursor mCursor = this.db.query(false, CEXP_TABLE, new String[]{KEY_CEID, FLD_CETID, FLD_CEEID, FLD_CEMID, FLD_CEAMT, FLD_CESEL}, "CEMID = " + MID, null, null, null, FLD_CEMID, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getAllCustomexp(int TID) throws SQLException {
        Cursor mCursor = this.db.query(false, CEXP_TABLE, new String[]{KEY_CEID, FLD_CETID, FLD_CEEID, FLD_CEMID, FLD_CEAMT, FLD_CESEL}, "CETID = " + TID, null, null, null, "CEEID ASC, CEMID ASC", null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public boolean updateMember(int MID, String name, float amount) {
        ContentValues args = new ContentValues();
        args.put(FLD_NAME, name);
        args.put(FLD_MEMAMT, Float.valueOf(amount));
        return this.db.update(MEM_TABLE, args, new StringBuilder("MID=").append(MID).toString(), null) > 0;
    }

    public boolean updateExpense(int TID, int EID, float amount, String exptyp, String SORC) {
        String lTID = String.valueOf(TID);
        String lEID = String.valueOf(EID);
        ContentValues args = new ContentValues();
        args.put(FLD_EXPAMT, Float.valueOf(amount));
        args.put(FLD_EXPTYP, exptyp);
        args.put(FLD_EXPSOC, SORC);
        if (this.db.update(EXP_TABLE, args, "EID = ? AND TID = ? ", new String[]{lEID, lTID}) > 0) {
            return true;
        }
        return false;
    }

    public boolean updateCExpense(int MID, int EID, float amount, String sel) {
        String lMID = String.valueOf(MID);
        String lEID = String.valueOf(EID);
        ContentValues args = new ContentValues();
        args.put(FLD_CEAMT, Float.valueOf(amount));
        args.put(FLD_CESEL, sel);
        if (this.db.update(CEXP_TABLE, args, "CEEID = ? AND CEMID = ? ", new String[]{lEID, lMID}) > 0) {
            return true;
        }
        return false;
    }

    public boolean memSwupdt(int MID, String soc) {
        ContentValues args = new ContentValues();
        args.put(FLD_MEMSOC, soc);
        return this.db.update(MEM_TABLE, args, new StringBuilder("MID=").append(MID).toString(), null) > 0;
    }

    public boolean updateTrip(int tid, String trn, String frmdt, String nofd) {
        ContentValues args = new ContentValues();
        args.put(FLD_TN, trn);
        args.put(FLD_FD, frmdt);
        args.put(FLD_NOD, nofd);
        return this.db.update(TRIP_TABLE, args, new StringBuilder("TID=").append(tid).toString(), null) > 0;
    }
}
