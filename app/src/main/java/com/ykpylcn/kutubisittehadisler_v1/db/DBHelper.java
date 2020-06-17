package com.ykpylcn.kutubisittehadisler_v1.db;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.preference.PreferenceManager;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG ="DBHelper" ;
    private static String DB_NAME = "DBHadislerV1.db";
    public static String TABLE_NAME_HADISLER = "kutubisitte";
    public static String TABLE_NAME_NOTIF = "notifications";
    public static String TABLE_NAME_NOTE = "notes";
    private static String DB_PATH = "";
    private static final int DB_VERSION = 2;
    private static final String SP_KEY_DB_VER = "db_ver";
    private Context mContext;

    private SQLiteDatabase mDataBase;

    private boolean mNeedUpdate = false;

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version, @Nullable DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DBHelper(@Nullable Context context) {

        super(context, DB_NAME,null, DB_VERSION);
//        if (android.os.Build.VERSION.SDK_INT >= 17)
//            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
//        else
//            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        DB_PATH=context.getDatabasePath(DB_NAME).getAbsolutePath();
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    private void copyDataBase() {
        if (checkDataBase()) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
            int dbVersion = prefs.getInt(SP_KEY_DB_VER, 1);
            if (DB_VERSION != dbVersion) {
                File dbFile = mContext.getDatabasePath(DB_NAME);
                if (!dbFile.delete()) {
                    //Log.w(TAG, "Unable to update database");
                }
            }
        }
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }

    }
    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH);
        return dbFile.exists();
    }
    private void copyDBFile() throws IOException {
        InputStream mInput = mContext.getAssets().open(DB_NAME);
        //InputStream mInput = mContext.getResources().openRawResource(R.raw.info);
        OutputStream mOutput = new FileOutputStream(DB_PATH );
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(SP_KEY_DB_VER, DB_VERSION);
        editor.commit();
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }
    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH , null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }

    @Override
    public synchronized void close() {
        if (mDataBase != null)
            mDataBase.close();
        super.close();
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create notes table
        // db.execSQL(Note.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            mNeedUpdate = true;
            // Drop older table if existed
//            db.execSQL("DROP TABLE IF EXISTS " + Note.TABLE_NAME);
//
//            // Create tables again
//            onCreate(db);
        }

    }

}
