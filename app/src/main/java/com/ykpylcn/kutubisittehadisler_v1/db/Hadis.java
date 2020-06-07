package com.ykpylcn.kutubisittehadisler_v1.db;

public class Hadis {
    public static final String COLUMN_HADIS_ID = "ID";
    public static final String COLUMN_HADIS = "Hadis";
    public static final String COLUMN_KAYNAK = "Kaynak";
    public static final String COLUMN_ISFAV = "IsFav";


    private int ID;
    private String AnaKonu, AltKonu, RivayetKaynak,Rivayet,Hadis,Kaynak;
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
        if (IsFav.contains("1"))
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
    public void setIsFav(boolean isFav){
        this.IsFav=isFav;
    }




}
