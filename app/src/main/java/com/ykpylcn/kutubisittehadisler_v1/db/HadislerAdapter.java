package com.ykpylcn.kutubisittehadisler_v1.db;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.MainActivity;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.SplashScreen;
import com.ykpylcn.kutubisittehadisler_v1.ui.Message;

import java.util.ArrayList;

public class HadislerAdapter extends RecyclerView.Adapter<HadislerAdapter.MyViewHolder> implements Filterable {
    private ArrayList<Hadis> hadisList;
    private ArrayList<Hadis> filteredHadisList;
    private Context context;
//    private CustomItemClickListener customItemClickListener;

    public HadislerAdapter(Context context,ArrayList<Hadis> hadislerArrayList) {
        this.context = context;
        this.hadisList = hadislerArrayList;
        this.filteredHadisList = hadislerArrayList;
        setHasStableIds(true);
//        this.customItemClickListener = customItemClickListener;
    }
//    public int GetIndexID(int hadisid){
//        for (int i=0;i<hadisList.size();i++){
//
//            if (hadisList.get(i).getHadisNo()==hadisid) {
//                return i;
//            }
//        }
//        return -1;
//    }
    @NonNull
    @Override
    public HadislerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hadisler_list_row, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // for click item listener
//                //customItemClickListener.onItemClick(filteredUserList.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
//            }
//        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        String sBuild="";
        if(filteredHadisList.get(position).getHadis().length()>90)
            sBuild=filteredHadisList.get(position).getHadis().substring(0,90)+"...";
        else
            sBuild=filteredHadisList.get(position).getHadis();

        holder.tv_hadis.setText(sBuild);
        final boolean[] arrowP = {true};
        final String finalSBuild = sBuild;
        holder.tv_hadis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(arrowP[0]){
                    holder.tv_hadis.setText(filteredHadisList.get(position).getHadis());
                    holder.tv_hadis.setBackgroundResource(R.color.secondaryLightColor2);
                    arrowP[0] =false;
                }
                else{
                    holder.tv_hadis.setText(finalSBuild);
//                    holder.tv_hadis.setBackgroundResource(R.color.);
                    arrowP[0] =true;
                }

            }

        });
        holder.tv_hadis.setOnLongClickListener(new View.OnLongClickListener() {
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
//        holder.tv_hadis.setMovementMethod(new ScrollingMovementMethod());
        holder.tv_kaynak.setText(filteredHadisList.get(position).getKaynak());
        final Hadis hadis=App.DbAdapter.getHadis(filteredHadisList.get(position).getHadisNo());
        if(hadis.getIsFav()){
           holder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_24);

        }else
            holder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
        holder.img_isfav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(hadis.getIsFav()){
                    App.DbAdapter.updateHadisIsFav(filteredHadisList.get(position).getHadisNo(),false);
                    holder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_border_24);
                    hadis.setIsFav(false);
                }
                else{
                    App.DbAdapter.updateHadisIsFav(filteredHadisList.get(position).getHadisNo(),true);
                    holder.img_isfav.setImageResource(R.drawable.ic_baseline_favorite_24);
                    hadis.setIsFav(true);

                }

                filteredHadisList.set(position,hadis);

                //notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
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
                return filteredHadisList.size();
        return 0;
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

                    for (Hadis hadis : hadisList) {

                        if(isAna && hadis.getAnaKonu().contains(searchString))
                            tempFilteredList.add(hadis);
                        else if(isAlt && hadis.getAltKonu().contains(searchString))
                            tempFilteredList.add(hadis);
                        else{
                            if (hadis.getHadis().toLowerCase().contains(searchString.toLowerCase())) {

                                tempFilteredList.add(hadis);
                            }
                        }
                    }

                    filteredHadisList = tempFilteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredHadisList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredHadisList = (ArrayList<Hadis>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_hadis;
        private TextView tv_kaynak;
        private ImageView img_isfav;

        public MyViewHolder(View view) {
            super(view);
            tv_hadis = (TextView)view.findViewById(R.id.tv_hadis);

            tv_kaynak = (TextView)view.findViewById(R.id.tv_kaynak);
            img_isfav = (ImageView) view.findViewById(R.id.img_isfav);
        }
    }
}
