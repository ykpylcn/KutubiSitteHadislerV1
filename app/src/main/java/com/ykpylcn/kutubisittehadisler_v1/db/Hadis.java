package com.ykpylcn.kutubisittehadisler_v1.db;

import android.text.Spannable;

public class Hadis {
    public static final String COLUMN_HADIS_ID = "ID";
    public static final String COLUMN_HADIS = "Hadis";
    public static final String COLUMN_KAYNAK = "Kaynak";
    public static final String COLUMN_ISFAV = "IsFav";
    public static final String COLUMN_ANAKONU = "AnaKonu";

    private  Spannable spannableHadis;

    private int ID;
    private String  RivayetKaynak,AltKonu,Rivayet,Hadis,Kaynak;
    public int AltKonuSize=0;
    public String AnaKonu;
    private boolean IsFav=false;
    public Hadis() {

    }
    public Hadis(int HadisNo, String AnaKonu,String AltKonu,String RivayetKaynak,String Rivayet,String Hadis,String Kaynak,String IsFav) {
        this.ID=HadisNo;
        this.AnaKonu=AnaKonu;
        this.AltKonu=AltKonu;
        this.RivayetKaynak=RivayetKaynak;
        this.Rivayet=Rivayet;
        this.Hadis=Hadis;
        this.Kaynak=Kaynak;
        if (IsFav.trim().contains("1"))
            this.IsFav=true;
    }
    public int getHadisNo(){
        return this.ID;
    }

    public String getAnaKonu(){
        return this.AnaKonu;
    }

    public String getAltKonu(){
        return this.AltKonu;
    }

    public String getARivayetKaynak(){
        return this.RivayetKaynak;
    }

    public String getRivayet(){
        return this.Rivayet;
    }

    public String getHadis(){
        return this.Hadis;
    }
    public String getKaynak(){
        return this.Kaynak;
    }
    public boolean getIsFav(){
        return this.IsFav;
    }
    public String getIsFavStr(){
        if(this.IsFav)
            return "1";
        else
            return "0";
    }

    public void setIsFav(boolean isFav){
        this.IsFav=isFav;
    }
    public int getAltKonuSize(){
        return AltKonuSize;
    }
    @Override
    public String toString() {
        return AnaKonu;
    }


    public void setHadisBySearch(Spannable spannable) {
        this.spannableHadis=spannable;
    }
    public Spannable getHadisBySearch(){
        return this.spannableHadis;
    }
}
