package com.ykpylcn.kutubisittehadisler_v1.ui.kirkhadis;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;

import java.util.ArrayList;

public class KirkHadisViewModel extends AndroidViewModel {

    private MutableLiveData<ArrayList<Hadis>> mListKirkHadis;

    public KirkHadisViewModel(Application application) {
        super(application);
        if(mListKirkHadis==null){
            loadHadisler(application);
        }
    }
    public void loadHadisler(Context context){
        mListKirkHadis= new MutableLiveData<>();
        ArrayList<Hadis> dataModels= new ArrayList<>();

        dataModels.add(new Hadis(1,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(2,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(3,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(4,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(5,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(6,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(7,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(8,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(9,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(10,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(11,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(12,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(13,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(14,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(15,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(16,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(17,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(18,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(19,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(20,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(21,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(22,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(23,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(24,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(25,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(26,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(27,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(28,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(29,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(30,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(31,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(32,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(33,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(34,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(35,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(36,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(37,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(38,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(39,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(40,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası\n" +
                "onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));



        mListKirkHadis.setValue(dataModels);
    }
    public LiveData<ArrayList<Hadis>> getHadisler() {
        return mListKirkHadis;
    }
}