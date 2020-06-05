package com.ykpylcn.kutubisittehadisler_v1.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;
import java.util.List;

public class DBAdapter {
    DBHelper dbHelper;
    Context contex;
    public DBAdapter(Context context)
    {
        contex=context;
        dbHelper=new DBHelper(contex);
    }
    public Hadis getHadis(int hadisNo) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD};
        Cursor cursor=dbHelper.getReadableDatabase().query(dbHelper.TABLE_NAME_HADISLER,new String[]{Hadis.COLUMN_HADIS},Hadis.COLUMN_HADIS_ID+"= ?",new String[]{String.valueOf(hadisNo)},null,null,null);
        Hadis hadis;
        if (cursor != null){
            cursor.moveToFirst();
            hadis= new Hadis(hadisNo,"","","","",cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_HADIS)),"","0");

        }else
            hadis= new Hadis(hadisNo,"","","","","Hadis Bulunamadi!","","0");
        cursor.close();
        db.close();
        return hadis;
    }
    public Hadis getHadis(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TABLE_NAME_HADISLER,
                new String[]{Hadis.COLUMN_HADIS, Hadis.COLUMN_ISFAV},
                Hadis.COLUMN_HADIS_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Hadis hadis=null;
        if (cursor != null){
            cursor.moveToFirst();
            // prepare note object
            hadis = new Hadis(Integer.parseInt(String.valueOf(id)) ,"","","","",cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_HADIS)),"",cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_ISFAV)));

        }

        // close the db connection
        cursor.close();
        db.close();
        return hadis;
    }
    public Boolean CheckHadisBy(int hadisNo) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String[] columns = {myDbHelper.UID,myDbHelper.NAME,myDbHelper.MyPASSWORD};
        Cursor cursor=dbHelper.getReadableDatabase().query(dbHelper.TABLE_NAME_HADISLER,new String[]{"Hadis"},"ID = ?",new String[]{String.valueOf(hadisNo)},null,null,null);
        if (cursor != null){
            cursor.moveToFirst();
            return true;
        }else
            return false;


    }
    public int updateHadisIsFav(long hadisid, boolean isFav) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Hadis.COLUMN_ISFAV, isFav);

        // updating row
        return db.update(DBHelper.TABLE_NAME_HADISLER, values, Hadis.COLUMN_HADIS_ID + " = ?",
                new String[]{String.valueOf(hadisid)});
    }
    public List<Hadis> getAllHadisler() {
        List<Hadis> hadisList = new ArrayList<Hadis>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_NAME_HADISLER;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Hadis hadis = new Hadis(Integer.parseInt(cursor.getString(6)),cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(7));

                // Adding contact to list
                hadisList.add(hadis);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return hadisList;
    }
    public ArrayList<Hadis> getAllHadislerArrList() {
        ArrayList<Hadis> hadisList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_NAME_HADISLER;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Hadis hadis = new Hadis(Integer.parseInt(cursor.getString(6)),cursor.getString(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5),cursor.getString(7));

                // Adding contact to list
                hadisList.add(hadis);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return hadisList;
    }

    public long insertNote(String note,int hadisid) {
        // get writable database as we want to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Note.COLUMN_NOTE, note);
        values.put(Note.COLUMN_HADISNO, hadisid);
        // insert row
        long id = db.insert(DBHelper.TABLE_NAME_NOTE, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public Note getNote(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TABLE_NAME_NOTE,
                new String[]{Note.COLUMN_ID, Note.COLUMN_NOTE, Note.COLUMN_TIMESTAMP,Note.COLUMN_HADISNO},
                Note.COLUMN_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        // prepare note object
        Note note = new Note(
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)),
                cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)),
                cursor.getInt(cursor.getColumnIndex(Note.COLUMN_HADISNO)));

        // close the db connection
        cursor.close();
        db.close();
        return note;
    }
    public Note getNoteByHadisID(long hadisid) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TABLE_NAME_NOTE,
                new String[]{Note.COLUMN_ID, Note.COLUMN_NOTE, Note.COLUMN_TIMESTAMP,Note.COLUMN_HADISNO},
                Note.COLUMN_HADISNO + "=?",
                new String[]{String.valueOf(hadisid)}, null, null, null, null);


        Note note=null;
        if(cursor.moveToFirst()){
            // prepare note object
            note = new Note(
                    cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)),
                    cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)),
                    cursor.getInt(cursor.getColumnIndex(Note.COLUMN_HADISNO)));
        }



        // close the db connection
        cursor.close();
        db.close();
        return note;
    }
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME_NOTE + " ORDER BY " +
                Note.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Note note = new Note();
                note.setId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_ID)));
                note.setNote(cursor.getString(cursor.getColumnIndex(Note.COLUMN_NOTE)));
                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Note.COLUMN_TIMESTAMP)));
                note.setHadisId(cursor.getInt(cursor.getColumnIndex(Note.COLUMN_HADISNO)));
                notes.add(note);

            } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();

        // return notes list
        return notes;
    }
    public int getNotesCount() {
        String countQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME_NOTE;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return count;
    }
    public int updateNote(Note note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE, note.getNote());

        // updating row
        return db.update(DBHelper.TABLE_NAME_NOTE, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
    }
    public int updateNoteByID(String note, int noteid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Note.COLUMN_NOTE, note);

        // updating row
        return db.update(DBHelper.TABLE_NAME_NOTE, values, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(noteid)});
    }
    public void deleteNote(Note note) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME_NOTE, Note.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
    public synchronized void close() {
        if (dbHelper != null)
            dbHelper.close();

    }


}
