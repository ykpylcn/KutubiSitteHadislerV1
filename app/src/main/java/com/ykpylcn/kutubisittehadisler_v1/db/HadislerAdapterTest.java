package com.ykpylcn.kutubisittehadisler_v1.db;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class HadislerAdapterTest extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    private ArrayList<Hadis> hadisList;
    private ArrayList<Hadis> filteredHadisList;
    private Context context;
//    private CustomItemClickListener customItemClickListener;

    public HadislerAdapterTest(Context context) {
        this.context = context;
        this.filteredHadisList = new ArrayList<>();
        setHasStableIds(true);
//        this.customItemClickListener = customItemClickListener;
    }

    public ArrayList<Hadis> getMovies() {
        return filteredHadisList;
    }

    public void setMovies(ArrayList<Hadis> movies) {
        this.filteredHadisList = movies;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hadisler_list_row, viewGroup, false);
//        final MyViewHolder myViewHolder = new MyViewHolder(view);
//        return myViewHolder;

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
        Hadis movie = filteredHadisList.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final HadisViewHolder movieVH = (HadisViewHolder) holder;
                String sBuild="";
                if(filteredHadisList.get(position).getHadis().length()>90)
                    sBuild=filteredHadisList.get(position).getHadis().substring(0,90)+"...";
                else
                    sBuild=filteredHadisList.get(position).getHadis();

                movieVH.tv_hadis.setText(sBuild);
                final boolean[] arrowP = {true};
                final String finalSBuild = sBuild;
                movieVH.tv_hadis.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(arrowP[0]){
                            movieVH.tv_hadis.setText(filteredHadisList.get(position).getHadis());
                            movieVH.tv_hadis.setBackgroundResource(R.color.secondaryLightColor2);
                            arrowP[0] =false;
                        }
                        else{
                            movieVH.tv_hadis.setText(finalSBuild);
//                    holder.tv_hadis.setBackgroundResource(R.color.);
                            arrowP[0] =true;
                        }

                    }

                });
                movieVH.tv_hadis.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        SharedPreferences refindexVP=App.app_context.getSharedPreferences("refindexVP",0);

                        SharedPreferences.Editor editor=refindexVP.edit();
                        editor.putInt("refindexVPkey",App.DbAdapter.getHadisRowIndex(filteredHadisList.get(position).getHadisNo()));
                        editor.commit();
                        Context context = v.getContext();
                        context.startActivity(new Intent(context, MainActivity.class));



                        return false;
                    }
                });
                movieVH.tv_kaynak.setText(filteredHadisList.get(position).getHadisNo()+"::"+filteredHadisList.get(position).getKaynak());
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
        return (position == filteredHadisList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    @Override
    public long getItemId(int position) {
        return filteredHadisList.get(position).getHadisNo();
    }

    @Override
    public int getItemCount() {
        if(filteredHadisList!=null)
            if(filteredHadisList.isEmpty())
                return 0;
            else
                return filteredHadisList.size()-1;
        return 0;
    }


//    @Override
//    public Filter getFilter() {
//        return new Filter() {
//            @Override
//            protected FilterResults performFiltering(CharSequence charSequence) {
//
//                String searchString = charSequence.toString();
//                boolean isAna=false;
//                boolean isAlt=false;
//                if(charSequence.toString().startsWith("@1/")){
//
//                    isAna=true;
//                    searchString=searchString.substring(3);
//                }
//                else if(charSequence.toString().startsWith("@2/"))
//                {
//                    isAlt=true;
//                    searchString=searchString.substring(3);
//                }
//
//                if (searchString.isEmpty()) {
//
//                    filteredHadisList = hadisList;
//
//                } else {
//
//                    ArrayList<Hadis> tempFilteredList = new ArrayList<>();
//
//                    for (Hadis hadis : hadisList) {
//
//                        if(isAna && hadis.getAnaKonu().contains(searchString))
//                            tempFilteredList.add(hadis);
//                        else if(isAlt && hadis.getAltKonu().contains(searchString))
//                            tempFilteredList.add(hadis);
//                        else{
//                            if (hadis.getHadis().toLowerCase().contains(searchString)) {
//
//                                tempFilteredList.add(hadis);
//                            }
//                        }
//                    }
//
//                    filteredHadisList = tempFilteredList;
//
//                }
//
//                FilterResults filterResults = new FilterResults();
//                filterResults.values = filteredHadisList;
//
//                return filterResults;
//            }
//
//            @Override
//            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
//                filteredHadisList = (ArrayList<Hadis>) filterResults.values;
//                notifyDataSetChanged();
//            }
//        };
//    }
    /**
     * Main list's content ViewHolder
     */

    public class HadisViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_hadis;
        private TextView tv_kaynak;
        private ImageView img_isfav;

        public HadisViewHolder(View view) {
            super(view);
            tv_hadis = (TextView)view.findViewById(R.id.tv_hadis);

            tv_kaynak = (TextView)view.findViewById(R.id.tv_kaynak);
            img_isfav = (ImageView) view.findViewById(R.id.img_isfav);
        }
    }
     /*
   Helpers
   _________________________________________________________________________________________________
    */

    public void add(Hadis mc) {
        filteredHadisList.add(mc);
        notifyItemInserted(filteredHadisList.size() - 1);
    }

    public void addAll(List<Hadis> mcList) {
        for (Hadis mc : mcList) {
            add(mc);
        }
    }

    public void remove(Hadis city) {
        int position = filteredHadisList.indexOf(city);
        if (position > -1) {
            filteredHadisList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear() {
        isLoadingAdded = false;
        while (getItemCount() > 0) {
            remove(getItem(0));
        }
    }

    public boolean isEmpty() {
        return getItemCount() == 0;
    }


    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Hadis());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = filteredHadisList.size() - 1;
        Hadis item = getItem(position);

        if (item != null) {
            filteredHadisList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Hadis getItem(int position) {
        return filteredHadisList.get(position);
    }

}
