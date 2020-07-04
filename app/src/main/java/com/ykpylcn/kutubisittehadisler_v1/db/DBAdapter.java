package com.ykpylcn.kutubisittehadisler_v1.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;



import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        Cursor cursor=dbHelper.getReadableDatabase().query(dbHelper.TABLE_NAME_HADISLER,new String[]{Hadis.COLUMN_HADIS,Hadis.COLUMN_KAYNAK,Hadis.COLUMN_ISFAV},Hadis.COLUMN_HADIS_ID+"= ?",new String[]{String.valueOf(hadisNo)},null,null,null);
        Hadis hadis;
        if (cursor.moveToFirst()){

            hadis= new Hadis(hadisNo,"","","","",cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_HADIS)),cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_KAYNAK)),cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_ISFAV)));

        }else
            hadis= new Hadis(hadisNo,"","","","","Hadis Bulunamadi!",cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_KAYNAK)),cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_ISFAV)));
        cursor.close();
        db.close();
        return hadis;
    }
    public Hadis getHadis(long id) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TABLE_NAME_HADISLER,
                new String[]{Hadis.COLUMN_ANAKONU,Hadis.COLUMN_ALTKONU,Hadis.COLUMN_HADIS,Hadis.COLUMN_KAYNAK, Hadis.COLUMN_ISFAV},
                Hadis.COLUMN_HADIS_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        Hadis hadis=null;
        if (cursor.moveToFirst()){

            // prepare note object
            hadis = new Hadis(Integer.parseInt(String.valueOf(id)) ,cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_ANAKONU)),cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_ALTKONU)),"","",cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_HADIS)),cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_KAYNAK)),cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_ISFAV)));

        }

        // close the db connection
        cursor.close();
        db.close();
        return hadis;
    }
    public Hadis getOneHadisRandom(boolean isFav) {
        Hadis hadis =null;
        // Select All Query
        String isfav="";
        if (isFav)
            isfav=" where "+Hadis.COLUMN_ISFAV+"=1";
        String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_NAME_HADISLER+isfav+" ORDER BY RANDOM() LIMIT 1";

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list


        if (cursor.moveToFirst()){

            // prepare note object
            hadis = new Hadis(cursor.getInt(cursor.getColumnIndex(Hadis.COLUMN_HADIS_ID)) ,cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_ANAKONU)),cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_ALTKONU)),"","",cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_HADIS)),cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_KAYNAK)),cursor.getString(cursor.getColumnIndex(Hadis.COLUMN_ISFAV)));

        }

        // close the db connection
        cursor.close();
        db.close();
        return hadis;
    }
    public Notif getNotifByHadisID(long hadisID) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(DBHelper.TABLE_NAME_NOTIF,
                new String[]{Notif.COLUMN_ID,Notif.COLUMN_HADIS_ID,Notif.COLUMN_HOUR,Notif.COLUMN_MINUTE,Notif.COLUMN_IS_DAILY,Notif.COLUMN_SHOW_TYPE,Notif.COLUMN_DATE,Notif.COLUMN_IS_ACTIVE},
                Notif.COLUMN_HADIS_ID + "=?",
                new String[]{String.valueOf(hadisID)}, null, null, null, null);
        Notif notif=null;
        if (cursor.moveToFirst()){

            // prepare note object
            notif = new Notif(cursor.getInt(cursor.getColumnIndex(Notif.COLUMN_ID)),cursor.getInt(cursor.getColumnIndex(Notif.COLUMN_HADIS_ID)),cursor.getInt(cursor.getColumnIndex(Notif.COLUMN_HOUR)),cursor.getInt(cursor.getColumnIndex(Notif.COLUMN_MINUTE)),cursor.getString(cursor.getColumnIndex(Notif.COLUMN_IS_DAILY)),cursor.getInt(cursor.getColumnIndex(Notif.COLUMN_SHOW_TYPE)),cursor.getString(cursor.getColumnIndex(Notif.COLUMN_DATE)),cursor.getString(cursor.getColumnIndex(Notif.COLUMN_IS_ACTIVE)));

        }

        // close the db connection
        cursor.close();
        db.close();
        return notif;
    }
    public void deleteNotif(int hadisID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete(DBHelper.TABLE_NAME_NOTIF, Notif.COLUMN_HADIS_ID + " = ?",
                new String[]{String.valueOf(hadisID)});
        db.close();
    }
    public long insertNotif(Notif notif) {
        // get writable database as we want to write data
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Notif.COLUMN_HADIS_ID, notif.HadisID);
        values.put(Notif.COLUMN_HOUR, notif.Hour);
        values.put(Notif.COLUMN_MINUTE, notif.Minute);
        values.put(Notif.COLUMN_IS_DAILY, notif.IsDaily);
        values.put(Notif.COLUMN_SHOW_TYPE, notif.HadisShowType);
        // insert row
        long id = db.insert(DBHelper.TABLE_NAME_NOTIF, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
    public int updateNotif(Notif notif) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Notif.COLUMN_HOUR, notif.Hour);
        values.put(Notif.COLUMN_MINUTE, notif.Minute);
        values.put(Notif.COLUMN_IS_DAILY, notif.IsDaily);
        values.put(Notif.COLUMN_SHOW_TYPE, notif.HadisShowType);

        // updating row
        return db.update(DBHelper.TABLE_NAME_NOTIF, values, Notif.COLUMN_HADIS_ID + " = ?",
                new String[]{String.valueOf(notif.HadisID)});
    }
    public int getNotifsCount() {
        String countQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME_NOTIF;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();
        cursor.close();
        db.close();

        // return count
        return count;
    }
    public List<Notif> getAllNotifs() {
        List<Notif> notifs = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + DBHelper.TABLE_NAME_NOTIF + " ORDER BY " +
                Notif.COLUMN_DATE + " DESC";

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Notif notif = new Notif(
                        cursor.getInt(cursor.getColumnIndex(Notif.COLUMN_ID)),
                        cursor.getInt(cursor.getColumnIndex(Notif.COLUMN_HADIS_ID)),
                        cursor.getInt(cursor.getColumnIndex(Notif.COLUMN_HOUR)),
                        cursor.getInt(cursor.getColumnIndex(Notif.COLUMN_MINUTE)),
                        cursor.getString(cursor.getColumnIndex(Notif.COLUMN_IS_DAILY)),
                        cursor.getInt(cursor.getColumnIndex(Notif.COLUMN_SHOW_TYPE)),
                        cursor.getString(cursor.getColumnIndex(Notif.COLUMN_DATE)),
                        cursor.getString(cursor.getColumnIndex(Notif.COLUMN_IS_ACTIVE))

                );


                notifs.add(notif);

            } while (cursor.moveToNext());
        }
        cursor.close();
        // close db connection
        db.close();

        // return notes list
        return notifs;
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
    public List<Hadis> getAnaKonular() {
        List<Hadis> hadisList = new ArrayList<Hadis>();
        Hadis hadis=new Hadis();
        hadis.AltKonuSize=0;
        hadis.AnaKonu="KONULAR";

        hadisList.add(hadis);
        // Select All Query
        String selectQuery = "Select AnaKonu,count(AltKonu) as SubCount from "+ dbHelper.TABLE_NAME_HADISLER+" GROUP BY AnaKonu" ;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Hadis hadis1=new Hadis();
                hadis1.AltKonuSize=Integer.parseInt(cursor.getString(1));
                hadis1.AnaKonu=cursor.getString(0);

                // Adding contact to list
                hadisList.add(hadis1);
            } while (cursor.moveToNext());
        }
        cursor.close();
        // return contact list
        return hadisList;
    }
    public List<Hadis> getAltKonular(String anaKonu) {
        List<Hadis> hadisList = new ArrayList<Hadis>();
        Hadis hadis=new Hadis();
        hadis.AltKonuSize=0;
        hadis.AnaKonu="ALT KONULAR";

        hadisList.add(hadis);
        // Select All Query
       anaKonu=anaKonu.replace("'","''");
       // String selectQuery = "Select AnaKonu,count(AltKonu) as SubCount from "+ dbHelper.TABLE_NAME_HADISLER+" GROUP BY AnaKonu" ;
        String selectQuery="Select AltKonu, count(Hadis)  as SubCount from "+ dbHelper.TABLE_NAME_HADISLER+" WHERE AnaKonu='"+anaKonu+"' GROUP BY AltKonu";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Hadis hadis1=new Hadis();
                hadis1.AltKonuSize=Integer.parseInt(cursor.getString(1));
                hadis1.AnaKonu=cursor.getString(0);
                // Adding contact to list
                hadisList.add(hadis1);
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
    public ArrayList<Hadis> getIsFavHadisler(int isFav) {
        ArrayList<Hadis> hadisList = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + dbHelper.TABLE_NAME_HADISLER+" where IsFav="+isFav;

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
    public int getHadisRowIndex(int hadisId) {

        String selectQuery = "SELECT ROWID FROM " + dbHelper.TABLE_NAME_HADISLER+" where ID="+hadisId;

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int rowID=-1;
        // looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            cursor.moveToFirst();
            rowID=Integer.parseInt(cursor.getString(0));
            rowID--;
                // Adding contact to list

        }

        cursor.close();
        // return contact list
        return rowID;
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

    public Hadis getOneRandomKirkHadis() {
        ArrayList<Hadis> dataModels=getKirkHadis();
        Random rand=new Random();
        return dataModels.get(rand.nextInt(dataModels.size()));

    }
    public ArrayList<Hadis> getKirkHadis() {
        ArrayList<Hadis> dataModels= new ArrayList<>();

        dataModels.add(new Hadis(6001,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(6002,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Müslüman, dilinden ve elinden Müslümanların emin olduğu kimsedir. Muhâcir de Allah'ın yasakladığı şeyleri terk edendir.","Buhari, Bedu'l-vahy, 4.","1"));
        dataModels.add(new Hadis(6003,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","İnsanlara merhamet etmeyene Allah merhamet etmez.","Müslim, Fedâil, 66; Tirmizî, Birr, 16","1"));
        dataModels.add(new Hadis(6004,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Hayra vesile olan, hayrı yapan gibidir.","Tirmizî, İlim, 14.","1"));
        dataModels.add(new Hadis(6005,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","İki göz vardır ki, cehennem ateşi onlara dokunmaz: Allah korkusundan ağlayan göz, bir de gecesini Allah yolunda nöbet tutarak geçiren göz.","Tirmizî, Fedâilü'l-Cihâd, 12.","1"));
        dataModels.add(new Hadis(6006,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","(Mü'min) kardeşinle münakaşa etme, onun hoşuna gitmeyecek şakalar yapma ve ona yerine getirmeyeceğin bir söz verme.","Tirmizî, Birr, 58.","1"));
        dataModels.add(new Hadis(6007,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Bizi aldatan bizden değildir.","Müslim, Îman, 164.","1"));
        dataModels.add(new Hadis(6008,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Kim bir kavme benzerse, o da onlardandır.","Ebû Dâvûd, Libâs, 4.","1"));
        dataModels.add(new Hadis(6009,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Allah, her işte ihsanı (güzel davranmayı) emretmiştir.","Müslim, Sayd ve Zebâih, 57.","1"));
        dataModels.add(new Hadis(6010,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Kul bir günah işlediği zaman kalbinde siyah bir nokta oluşur. Bundan vazgeçip tövbe ve istiğfar ettiği zaman kalbi parlar. Günahtan dönmez ve bunu yapmaya devam ederse siyah nokta artırılır ve sonunda tüm kalbini kaplar. Allah'ın (Kitabında) 'Hayır! Doğrusu onların kazanmakta oldukları kalplerini paslandırmıştır.' (Mutaffifîn, 83/14.) diye anlattığı pas işte budur.","Tirmizî, Tefsîru'l-Kur'ân, 83.","1"));
        dataModels.add(new Hadis(6011,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Kim duyulsun diye iyilik yaparsa, Allah (onun bu niyetini herkese) duyurur. Kim gösteriş için iyilik yaparsa, Allah da (onun bu riyakârlığını herkese) gösterir.","Müslim, Zühd, 48.","1"));
        dataModels.add(new Hadis(6012,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Mümin, yeşil ekine benzer. Rüzgâr hangi taraftan eserse onu o tarafa yatırır (fakat yıkılmaz), rüzgâr sakinleştiğinde yine doğrulur. İşte mümin de böyledir; o, bela ve musibetler sebebiyle eğilir (fakat yıkılmaz). Kâfir ise sert ve dimdik selvi ağacına benzer ki Allah onu dilediği zaman (bir defada) söküp devirir.","Buhârî, Tevhid, 31.","1"));
        dataModels.add(new Hadis(6013,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Dua, ibadetin özüdür.","Tirmizî, Deavât, 1","1"));
        dataModels.add(new Hadis(6014,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Sizden birinin, bir lokması düştüğünde onu alsın, temizleyip yesin, şeytana bırakmasın.","Müslim, Eşribe, 136","1"));
        dataModels.add(new Hadis(6015,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","İyilik güzel ahlaktır; günah da içinde tereddüt uyandıran ve halkın bilmesini istemediğin şeydir.","Müslim, el-Birrü ve's Sıle, 2553","1"));
        dataModels.add(new Hadis(6016,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Dünyada garip yahut yolcu ol.","Buhârî, Rikak, babu kavlin Nebiyi, 11/199, 200","1"));
        dataModels.add(new Hadis(6017,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Kendisini ilgilendirmeyen şeyleri (mâlâyâniyi) terk etmesi, kişinin Müslümanlığının güzelliğindendir.","Tirmizî, Zühd, 11.","1"));
        dataModels.add(new Hadis(6018,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","İnsanların Allah'tan en uzak olanı, katı kalpli kimselerdir.","Tirmizî, Zühd,61","1"));
        dataModels.add(new Hadis(6019,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Mizana ilk konacak amel güzel ahlak ve cömertliktir.","İhya C. 3 S. 116","1"));
        dataModels.add(new Hadis(6020,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","İşçiye ücretini, (alnının) teri kurumadan veriniz.","İbn Mâce, Ruhûn, 4.","1"));
        dataModels.add(new Hadis(6021,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Doğru olmayan birşey gördüğünüzde veya işittiğinizde, insanların heybeti, hakkı söylemekten sizi alıkoymasın.","İmam Taberâni, Mu'cemu's-Sağir, 2/185.","1"));
        dataModels.add(new Hadis(6022,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Hiçbiriniz kendisi için istediğini (mü min) kardeşi için istemedikçe (gerçek) iman etmiş olamaz.","Buhari, İman, 7; Müslim, İman, 71.","1"));
        dataModels.add(new Hadis(6023,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Hiçbir baba, çocuğuna, güzel terbiyeden daha üstün bir hediye veremez.","Tirmizi, Birr, 33","1"));
        dataModels.add(new Hadis(6024,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Allah a ve ahiret gününe iman eden kimse, komşusuna eziyet etmesin. Allah a ve ahiret gününe iman eden misafirine ikramda bulunsun. Allah a ve ahiret gününe iman eden kimse, ya hayır söylesin veya sussun.","Buhari, Edeb, 31, 85; Müslim, İman, 74, 75.","1"));
        dataModels.add(new Hadis(6025,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Kim evlenirse imanın yarısını tamamlamış olur; kalan diğer yarısı hakkında ise Allah'tan korksun!","Heysemî, IV, 252","1"));
        dataModels.add(new Hadis(6026,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Nikâh benim sünnetimdir. Kim benim sünnetimle amel etmezse, benden değildir. Evleniniz! Zira ben, diğer ümmetlere karşı sizin çokluğunuz ile iftihar edeceğim. Kimin maddî imkânı varsa, hemen evlensin. Kim maddî imkân bulamazsa, nafile oruç tutsun. Çünkü oruç, onun için şehveti kırıcıdır.","İbn-i Mâce, Nikâh, 1/1846","1"));
        dataModels.add(new Hadis(6027,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Âmâ'ya veya yol sorana yol göstermen, sadakadır. Güçsüz birine yardım etmen, sadakadır. Konuşmakta güçlük çekenin meramını ifade edivermen sadakadır.","İbn Hanbel, V, 152, 169.","1"));
        dataModels.add(new Hadis(6028,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Kimin bir kız çocuğu dünyaya gelir de onu toprağa gömmeden, horlamadan ve üzerine erkek çocuğunu tercih etmeden yetiştirecek olursa Allah Teâlâ o kimseyi cennetine koyacaktır.","(Ahmed, Müsned,(Tah: Muhammed Şakir, Had. no: 1957),c. IV, s. 294)","1"));
        dataModels.add(new Hadis(6029,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Allah'tan korkunuz; çocuklarınız arasında adaletli davranınız.","Müslim, Hibât 13)","1"));
        dataModels.add(new Hadis(6030,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","İslâm, güzel ahlâktır","Kenzü'l-Ummâl, 3/17, HadisNo: 5225","1"));
        dataModels.add(new Hadis(6031,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Bir müslümanın diktiği ağaçtan veya ektiği ekinden insan,\n" +    "hayvan ve kuşların yedikleri şeyler, o müslüman için birer\n" +               "sadakadır.","Buhârî, Edeb, 27; Müslim, Müsâkât, 7, 10.","1"));
        dataModels.add(new Hadis(6032,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Nefsimi elinde bulundurana yemin ederim ki, sizden\n" +              "biriniz ben ona babasından, çocuğundan ve bütün insanlardan\n" +              "daha sevimli olmadıkça iman etmiş olamaz.","Buhârî, Sahih, İman, 8; Müslim, Sahih, İman, 69.","1"));
        dataModels.add(new Hadis(6033,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Ümmetimin içinden bana en şiddetli sevgisi olanlar,\n" +             "benden sonra gelip onlardan biri ehli ve malına karşılık beni\n" +                "görmek arzusunda olan insanlardır.","Müslim, Sahih, Cennet, 4. H.No; 12.","1"));
        dataModels.add(new Hadis(6034,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Ümmetimin fesada uğradığı bir anda benim bir sünnetime\n" +                "yapışan için şehit sevabı vardır.","El-Heysemi, Mecmeu'z-Zevâid, c.1. s.172.","1"));
        dataModels.add(new Hadis(6035,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Kim bana bir defa salavat-ı şerife okursa Allah Teala ona\n" +                "on salat eder.","Müslim, Sahih, Salat, 17. H.No; 408.","1"));
        dataModels.add(new Hadis(6036,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Her kul öldüğü hal üzere dirilir.","Müslim, Cennet 2878","1"));
        dataModels.add(new Hadis(6037,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Ölüyü üç şey takip eder: Ailesi, malı ve ameli. İkisi geri\n" +                "döner; biri kalır: Ailesi ve malı geri döner; ameli ise kalır.","Buhârî, Rikak, 11/315","1"));
        dataModels.add(new Hadis(6038,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Namazda Fatiha suresini okumayan kimsenin namazı\n" +                "yoktur.","Buhârî, Ezân 94; Müslim, Salât 34-37,( 394); Ebu Dâvud, Salât, 131-132 (822)","1"));
        dataModels.add(new Hadis(6039,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Gücünüzün yettiği ibadeti yapın. Zira siz usanmadıkça\n" +                "Allah usanmaz.","Buhârî Teheccüd 3/31; Müslim Müsafirin, 785","1"));
        dataModels.add(new Hadis(6040,"Kirk Kadis","Kırk Kadis","Rivayet Kaynak","Rivayet","Alışveriş yapanlar meclisten ayrılmadıkları sürece\n" +                "muhayyerdirler. Eğer doğru konuşurlarsa alışverişlerinin\n" +                "bereketini görürler. Eğer bazı şeyleri saklarlarsa alışverişlerinin\n" +            "bereketi kaçar.","Buhârî, Buyu', 1532","1"));
return dataModels;

    }
}
