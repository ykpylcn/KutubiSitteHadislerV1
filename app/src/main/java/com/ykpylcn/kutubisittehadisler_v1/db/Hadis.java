package com.ykpylcn.kutubisittehadisler_v1.db;

public class Hadis {
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




}
