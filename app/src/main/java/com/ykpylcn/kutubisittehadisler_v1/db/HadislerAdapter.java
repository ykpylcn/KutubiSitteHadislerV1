package com.ykpylcn.kutubisittehadisler_v1.db;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.MainActivity;
import com.ykpylcn.kutubisittehadisler_v1.R;

import java.util.ArrayList;
import java.util.List;

public class HadislerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable  {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    private ArrayList<Hadis> hadisList;
    private ArrayList<Hadis> filteredHadisList;
    private Context context;
//    private CustomItemClickListener customItemClickListener;

    public HadislerAdapter(Context context) {
        this.context = context;
        this.hadisList = new ArrayList<>();
        this.filteredHadisList=new ArrayList<>();
        setHasStableIds(true);
//        this.customItemClickListener = customItemClickListener;
    }
    public HadislerAdapter(Context context,ArrayList<Hadis> hadislerArrayList) {
        this.context = context;
        this.hadisList = hadislerArrayList;
        this.filteredHadisList = hadislerArrayList;
        setHasStableIds(true);
//        this.customItemClickListener = customItemClickListener;
    }
    public ArrayList<Hadis> getHadisler() {
        return hadisList;
    }
    public void setHadisler(ArrayList<Hadis> hadisler) {
        this.hadisList = hadisler;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;
            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {

        final Hadis hadisCurrent=hadisList.get(position);
        if(hadisCurrent==null)
            return;
        switch (getItemViewType(position)) {
            case ITEM:
                final HadisViewHolder viewHolder = (HadisViewHolder) holder;
                String sBuild="";
                if(hadisCurrent.getHadis().length()>90)
                    sBuild=hadisCurrent.getHadis().substring(0,90)+"...";
                else
                    sBuild=hadisCurrent.getHadis();

                if (hadisCurrent.getHadisBySearch()!=null)
                    viewHolder.tv_hadis.setText(hadisCurrent.getHadisBySearch());
                else
                     viewHolder.tv_hadis.setText(sBuild);

                final boolean[] arrowP = {true};
                final String finalSBuild = sBuild;
                viewHolder.tv_hadis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (hadisCurrent.getHadisBySearch()==null)
                        if(arrowP[0]){
                            viewHolder.tv_hadis.setText(hadisCurrent.getHadis());
                            viewHolder.tv_hadis.setBackgroundResource(R.color.secondaryLightColor2);
                            arrowP[0] =false;
                        }
                        else{
                            viewHolder.tv_hadis.setText(finalSBuild);
//                    holder.tv_hadis.setBackgroundResource(R.color.);
                            arrowP[0] =true;
                        }

                    }

                });
                viewHolder.tv_hadis.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        SharedPreferences refindexVP=App.app_context.getSharedPreferences("refindexVP",0);

                        SharedPreferences.Editor editor=refindexVP.edit();
                        editor.putInt("refindexVPkey",App.DbAdapter.getHadisRowIndex(hadisCurrent.getHadisNo()));
                        editor.commit();
                        Context context = v.getContext();
                        context.startActivity(new Intent(context, MainActivity.class));

                        return false;
                    }
                });
                viewHolder.tv_kaynak.setText(hadisCurrent.getHadisNo()+": "+hadisCurrent.getKaynak());
                viewHolder.tv_hadisAltKonu.setText(hadisCurrent.getAltKonu());

                final Hadis hadisDB=App.DbAdapter.getHadis(hadisCurrent.getHadisNo());
                if(hadisDB.getIsFav()){
                    viewHolder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_24);

                }else
                    viewHolder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                viewHolder.img_isfav.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if(hadisDB.getIsFav()){
                            App.DbAdapter.updateHadisIsFav(hadisCurrent.getHadisNo(),false);
                            viewHolder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                            hadisDB.setIsFav(false);
                        }
                        else{
                            App.DbAdapter.updateHadisIsFav(hadisCurrent.getHadisNo(),true);
                            viewHolder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_24);
                            hadisDB.setIsFav(true);

                        }

                        hadisList.set(position,hadisDB);

                        //notifyItemChanged(position);
                    }
                });
                break;
            case LOADING:
//                Do nothing
                break;
        }
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.hadisler_list_row, parent, false);
        viewHolder = new HadisViewHolder(v1);
        return viewHolder;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();
                boolean isAna=false;
                boolean isAlt=false;
                if(charSequence.toString().startsWith("@1/")){

                    isAna=true;
                    searchString=searchString.substring(3);
                }
                else if(charSequence.toString().startsWith("@2/"))
                {
                    isAlt=true;
                    searchString=searchString.substring(3);
                }

                if (searchString.isEmpty()) {

                    filteredHadisList = hadisList;

                } else {

                    ArrayList<Hadis> tempFilteredList = new ArrayList<>();

                    for (Hadis hadis : App.mArrayListHadisler) {

                        if(isAna && hadis.getAnaKonu().contains(searchString)){
                            tempFilteredList.add(hadis);
                        }
                        else if(isAlt && hadis.getAltKonu().contains(searchString)){
                            tempFilteredList.add(hadis);
                        }

                        else{
                            String body=hadis.getHadis().toLowerCase();
                            String search=searchString.toLowerCase();
                            if (body.contains(search)) {

                                Spannable spannable = new SpannableString(body);
                                spannable.setSpan(new ForegroundColorSpan(Color.RED), body.indexOf(search), body.indexOf(search) + search.length(),     Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                hadis.setHadisBySearch(spannable);
                                tempFilteredList.add(hadis);
                            }
                        }
//                        filteredHadisList = tempFilteredList;
                    }



                    filteredHadisList=tempFilteredList;
//                    hadisList=(ArrayList<Hadis>)tempFilteredList.subList(0, 10);


                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredHadisList;


                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                hadisList.clear();
                App.filteredListHadisler=  (ArrayList<Hadis>) filterResults.values;
                if(App.filteredListHadisler.size()>10){
                    hadisList.addAll(App.filteredListHadisler.subList(0,10));
                    addLoadingFooter();
                }
                else
                    hadisList.addAll(App.filteredListHadisler);
                notifyDataSetChanged();
            }
        };
    }

    protected class LoadingVH extends RecyclerView.ViewHolder {

        public LoadingVH(View itemView) {
            super(itemView);
        }
    }

//    @Override
//    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
//
//
//
//
//
//
//
////        final Hadis hadis=App.DbAdapter.getHadis(filteredHadisList.get(position).getHadisNo());
////        if(hadis.getIsFav()){
////           holder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_24);
////
////        }else
////            holder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
////        holder.img_isfav.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////
////                if(hadis.getIsFav()){
////                    App.DbAdapter.updateHadisIsFav(filteredHadisList.get(position).getHadisNo(),false);
////                    holder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
////                    hadis.setIsFav(false);
////                }
////                else{
////                    App.DbAdapter.updateHadisIsFav(filteredHadisList.get(position).getHadisNo(),true);
////                    holder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_24);
////                    hadis.setIsFav(true);
////
////                }
////
////                filteredHadisList.set(position,hadis);
////
////                //notifyItemChanged(position);
////            }
////        });
//    }

    //    @Override
//    public int getItemViewType(int position) {
//        return super.getItemViewType(position);
//    }
    @Override
    public int getItemViewType(int position) {
        return (position == hadisList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @Override
    public long getItemId(int position) {
        return hadisList.get(position).getHadisNo();
    }

    @Override
    public int getItemCount() {
        if(hadisList!=null)
            if(hadisList.isEmpty())
                return 0;
            else
                return hadisList.size();
        return 0;
    }



    public class HadisViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_hadis;
        private TextView tv_kaynak;
        private ImageView img_isfav;
        private TextView tv_hadisAltKonu;

        public HadisViewHolder(View view) {
            super(view);
            tv_hadis = (TextView)view.findViewById(R.id.tv_hadis);
            tv_hadisAltKonu= (TextView)view.findViewById(R.id.tv_hadisAltKonu);
            tv_kaynak = (TextView)view.findViewById(R.id.tv_kaynak);
            img_isfav = (ImageView) view.findViewById(R.id.img_isfav);
        }
    }
     /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Hadis mc) {
        if(mc!=null){
            if(hadisList==null)
                hadisList=new ArrayList<>();
            hadisList.add(mc);
            notifyItemInserted(hadisList.size() - 1);
        }
    }

    public void addAll(List<Hadis> mcList) {
        for (Hadis mc : mcList) {
            add(mc);
        }
    }

    public void remove(Hadis city) {
        int position = hadisList.indexOf(city);
        if (position > -1) {
            hadisList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

//    public boolean isEmpty() {
//        return getItemCount() == 0;
//    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Hadis());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = hadisList.size() - 1;
        Hadis item = getItem(position);

        if (item != null) {
            hadisList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Hadis getItem(int position) {
        return hadisList.get(position);
    }

}
