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

        dataModels.add(new Hadis(1,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her doğan, İslâm fıtratı üzerine doğar. Sonra, anne-babası onu Hristiyan, Yahudi veya Mecusi yapar.","Buhârî, cenâiz 92; Ebû Dâvut, sünne 17; Tirmizî, kader 5","1"));
        dataModels.add(new Hadis(2,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Müslüman, dilinden ve elinden Müslümanların emin olduğu kimsedir. Muhâcir de Allah'ın yasakladığı şeyleri terk edendir.","Buhari, Bedu'l-vahy, 4.","1"));
        dataModels.add(new Hadis(3,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","İnsanlara merhamet etmeyene Allah merhamet etmez.","Müslim, Fedâil, 66; Tirmizî, Birr, 16","1"));
        dataModels.add(new Hadis(4,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Hayra vesile olan, hayrı yapan gibidir.","Tirmizî, İlim, 14.","1"));
        dataModels.add(new Hadis(5,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","İki göz vardır ki, cehennem ateşi onlara dokunmaz: Allah korkusundan ağlayan göz, bir de gecesini Allah yolunda nöbet tutarak geçiren göz.","Tirmizî, Fedâilü'l-Cihâd, 12.","1"));
        dataModels.add(new Hadis(6,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","(Mü'min) kardeşinle münakaşa etme, onun hoşuna gitmeyecek şakalar yapma ve ona yerine getirmeyeceğin bir söz verme.","Tirmizî, Birr, 58.","1"));
        dataModels.add(new Hadis(7,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Bizi aldatan bizden değildir.","Müslim, Îman, 164.","1"));
        dataModels.add(new Hadis(8,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Kim bir kavme benzerse, o da onlardandır.","Ebû Dâvûd, Libâs, 4.","1"));
        dataModels.add(new Hadis(9,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Allah, her işte ihsanı (güzel davranmayı) emretmiştir.","Müslim, Sayd ve Zebâih, 57.","1"));
        dataModels.add(new Hadis(10,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Kul bir günah işlediği zaman kalbinde siyah bir nokta oluşur. Bundan vazgeçip tövbe ve istiğfar ettiği zaman kalbi parlar. Günahtan dönmez ve bunu yapmaya devam ederse siyah nokta artırılır ve sonunda tüm kalbini kaplar. Allah'ın (Kitabında) 'Hayır! Doğrusu onların kazanmakta oldukları kalplerini paslandırmıştır.' (Mutaffifîn, 83/14.) diye anlattığı pas işte budur.","Tirmizî, Tefsîru'l-Kur'ân, 83.","1"));
        dataModels.add(new Hadis(11,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Kim duyulsun diye iyilik yaparsa, Allah (onun bu niyetini herkese) duyurur. Kim gösteriş için iyilik yaparsa, Allah da (onun bu riyakârlığını herkese) gösterir.","Müslim, Zühd, 48.","1"));
        dataModels.add(new Hadis(12,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Mümin, yeşil ekine benzer. Rüzgâr hangi taraftan eserse onu o tarafa yatırır (fakat yıkılmaz), rüzgâr sakinleştiğinde yine doğrulur. İşte mümin de böyledir; o, bela ve musibetler sebebiyle eğilir (fakat yıkılmaz). Kâfir ise sert ve dimdik selvi ağacına benzer ki Allah onu dilediği zaman (bir defada) söküp devirir.","Buhârî, Tevhid, 31.","1"));
        dataModels.add(new Hadis(13,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Dua, ibadetin özüdür.","Tirmizî, Deavât, 1","1"));
        dataModels.add(new Hadis(14,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Sizden birinin, bir lokması düştüğünde onu alsın, temizleyip yesin, şeytana bırakmasın.","Müslim, Eşribe, 136","1"));
        dataModels.add(new Hadis(15,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","İyilik güzel ahlaktır; günah da içinde tereddüt uyandıran ve halkın bilmesini istemediğin şeydir.","Müslim, el-Birrü ve's Sıle, 2553","1"));
        dataModels.add(new Hadis(16,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Dünyada garip yahut yolcu ol.","Buhârî, Rikak, babu kavlin Nebiyi, 11/199, 200","1"));
        dataModels.add(new Hadis(17,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Kendisini ilgilendirmeyen şeyleri (mâlâyâniyi) terk etmesi, kişinin Müslümanlığının güzelliğindendir.","Tirmizî, Zühd, 11.","1"));
        dataModels.add(new Hadis(18,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","İnsanların Allah'tan en uzak olanı, katı kalpli kimselerdir.","Tirmizî, Zühd,61","1"));
        dataModels.add(new Hadis(19,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Mizana ilk konacak amel güzel ahlak ve cömertliktir.","İhya C. 3 S. 116","1"));
        dataModels.add(new Hadis(20,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","İşçiye ücretini, (alnının) teri kurumadan veriniz.","İbn Mâce, Ruhûn, 4.","1"));
        dataModels.add(new Hadis(21,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Doğru olmayan birşey gördüğünüzde veya işittiğinizde, insanların heybeti, hakkı söylemekten sizi alıkoymasın.","İmam Taberâni, Mu'cemu's-Sağir, 2/185.","1"));
        dataModels.add(new Hadis(22,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Hiçbiriniz kendisi için istediğini (mü min) kardeşi için istemedikçe (gerçek) iman etmiş olamaz.","Buhari, İman, 7; Müslim, İman, 71.","1"));
        dataModels.add(new Hadis(23,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Hiçbir baba, çocuğuna, güzel terbiyeden daha üstün bir hediye veremez.","Tirmizi, Birr, 33","1"));
        dataModels.add(new Hadis(24,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Allah a ve ahiret gününe iman eden kimse, komşusuna eziyet etmesin. Allah a ve ahiret gününe iman eden misafirine ikramda bulunsun. Allah a ve ahiret gününe iman eden kimse, ya hayır söylesin veya sussun.","Buhari, Edeb, 31, 85; Müslim, İman, 74, 75.","1"));
        dataModels.add(new Hadis(25,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Kim evlenirse imanın yarısını tamamlamış olur; kalan diğer yarısı hakkında ise Allah'tan korksun!","Heysemî, IV, 252","1"));
        dataModels.add(new Hadis(26,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Nikâh benim sünnetimdir. Kim benim sünnetimle amel etmezse, benden değildir. Evleniniz! Zira ben, diğer ümmetlere karşı sizin çokluğunuz ile iftihar edeceğim. Kimin maddî imkânı varsa, hemen evlensin. Kim maddî imkân bulamazsa, nafile oruç tutsun. Çünkü oruç, onun için şehveti kırıcıdır.","İbn-i Mâce, Nikâh, 1/1846","1"));
        dataModels.add(new Hadis(27,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Âmâ'ya veya yol sorana yol göstermen, sadakadır. Güçsüz birine yardım etmen, sadakadır. Konuşmakta güçlük çekenin meramını ifade edivermen sadakadır.","İbn Hanbel, V, 152, 169.","1"));
        dataModels.add(new Hadis(28,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Kimin bir kız çocuğu dünyaya gelir de onu toprağa gömmeden, horlamadan ve üzerine erkek çocuğunu tercih etmeden yetiştirecek olursa Allah Teâlâ o kimseyi cennetine koyacaktır.","(Ahmed, Müsned,(Tah: Muhammed Şakir, Had. no: 1957),c. IV, s. 294)","1"));
        dataModels.add(new Hadis(29,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Allah'tan korkunuz; çocuklarınız arasında adaletli davranınız.","Müslim, Hibât 13)","1"));
        dataModels.add(new Hadis(30,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","İslâm, güzel ahlâktır","Kenzü'l-Ummâl, 3/17, HadisNo: 5225","1"));
        dataModels.add(new Hadis(31,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Bir müslümanın diktiği ağaçtan veya ektiği ekinden insan,\n" +    "hayvan ve kuşların yedikleri şeyler, o müslüman için birer\n" +               "sadakadır.","Buhârî, Edeb, 27; Müslim, Müsâkât, 7, 10.","1"));
        dataModels.add(new Hadis(32,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Nefsimi elinde bulundurana yemin ederim ki, sizden\n" +              "biriniz ben ona babasından, çocuğundan ve bütün insanlardan\n" +              "daha sevimli olmadıkça iman etmiş olamaz.","Buhârî, Sahih, İman, 8; Müslim, Sahih, İman, 69.","1"));
        dataModels.add(new Hadis(33,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Ümmetimin içinden bana en şiddetli sevgisi olanlar,\n" +             "benden sonra gelip onlardan biri ehli ve malına karşılık beni\n" +                "görmek arzusunda olan insanlardır.","Müslim, Sahih, Cennet, 4. H.No; 12.","1"));
        dataModels.add(new Hadis(34,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Ümmetimin fesada uğradığı bir anda benim bir sünnetime\n" +                "yapışan için şehit sevabı vardır.","El-Heysemi, Mecmeu'z-Zevâid, c.1. s.172.","1"));
        dataModels.add(new Hadis(35,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Kim bana bir defa salavat-ı şerife okursa Allah Teala ona\n" +                "on salat eder.","Müslim, Sahih, Salat, 17. H.No; 408.","1"));
        dataModels.add(new Hadis(36,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Her kul öldüğü hal üzere dirilir.","Müslim, Cennet 2878","1"));
        dataModels.add(new Hadis(37,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Ölüyü üç şey takip eder: Ailesi, malı ve ameli. İkisi geri\n" +                "döner; biri kalır: Ailesi ve malı geri döner; ameli ise kalır.","Buhârî, Rikak, 11/315","1"));
        dataModels.add(new Hadis(38,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Namazda Fatiha suresini okumayan kimsenin namazı\n" +                "yoktur.","Buhârî, Ezân 94; Müslim, Salât 34-37,( 394); Ebu Dâvud, Salât, 131-132 (822)","1"));
        dataModels.add(new Hadis(39,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Gücünüzün yettiği ibadeti yapın. Zira siz usanmadıkça\n" +                "Allah usanmaz.","Buhârî Teheccüd 3/31; Müslim Müsafirin, 785","1"));
        dataModels.add(new Hadis(40,"Kirk Kadis","Alt Konu","Rivayet Kaynak","Rivayet","Alışveriş yapanlar meclisten ayrılmadıkları sürece\n" +                "muhayyerdirler. Eğer doğru konuşurlarsa alışverişlerinin\n" +                "bereketini görürler. Eğer bazı şeyleri saklarlarsa alışverişlerinin\n" +            "bereketi kaçar.","Buhârî, Buyu', 1532","1"));



        mListKirkHadis.setValue(dataModels);
    }
    public LiveData<ArrayList<Hadis>> getHadisler() {
        return mListKirkHadis;
    }
}