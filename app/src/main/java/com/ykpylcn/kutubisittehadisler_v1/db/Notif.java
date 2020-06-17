package com.ykpylcn.kutubisittehadisler_v1.db;

public class Notif {
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_HADIS_ID = "HadisID";
    public static final String COLUMN_HOUR = "Hour";
    public static final String COLUMN_MINUTE = "Minute";
    public static final String COLUMN_IS_DAILY = "IsDaily";
    public static final String COLUMN_SHOW_TYPE = "HadisShowType";
    public static final String COLUMN_DATE = "Date";
    public static final String COLUMN_IS_ACTIVE = "Active";


    public int id,HadisID,Hour,Minute,HadisShowType;
    public String Date;
    public boolean IsDaily=true;
    public boolean Active=true;
    public Notif() {
    }
    public Notif(int id,int HadisID,int Hour,int Minute, String IsDaily,int HadisShowType,String Date,String Active) {
        this.id=id;
        this.HadisID=HadisID;
        this.Hour=Hour;
        this.Minute=Minute;
        if (IsDaily.trim().contains("0"))
            this.IsDaily=false;
        this.HadisShowType=HadisShowType;
        this.Date=Date;
        if (Active.trim().contains("0"))
            this.Active=false;
    }
}
