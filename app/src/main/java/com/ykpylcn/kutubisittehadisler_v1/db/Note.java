package com.ykpylcn.kutubisittehadisler_v1.db;

public class Note {


    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String COLUMN_HADISNO = "hadisno";

    private int ID;
    private String Note;
    private String Timestamp;
    private int HadisNo;

    // Create table SQL query
//    public static final String CREATE_TABLE =
//            "CREATE TABLE " + TABLE_NAME + "("
//                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
//                    + COLUMN_NOTE + " TEXT,"
//                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP,"
//                    + COLUMN_HADISNO + " INTEGER"
//                    + ")";

    public Note() {
    }

    public Note(int id, String note, String timestamp, int hadisno) {
        this.ID = id;
        this.Note = note;
        this.Timestamp = timestamp;
        this.HadisNo = hadisno;
    }

    public int getId() {
        return ID;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        this.Note = note;
    }

    public String getTimestamp() {
        return Timestamp;
    }
    public int getHadisNo() {
        return HadisNo;
    }
    public void setId(int id) {
        this.ID = id;
    }
    public void setHadisId(int hadisid) {
        this.HadisNo = hadisid;
    }
    public void setTimestamp(String timestamp) {
        this.Timestamp = timestamp;
    }
}
