package com.ykpylcn.kutubisittehadisler_v1.db;

import android.content.Context;
import android.text.method.ScrollingMovementMethod;
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
import com.ykpylcn.kutubisittehadisler_v1.R;
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
//        this.customItemClickListener = customItemClickListener;
    }

    @NonNull
    @Override
    public HadislerAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hadisler_list_row, viewGroup, false);
        final MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // for click item listener
                //customItemClickListener.onItemClick(filteredUserList.get(myViewHolder.getAdapterPosition()),myViewHolder.getAdapterPosition());
            }
        });
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tv_hadis.setText(filteredHadisList.get(position).getHadis());
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
    public int getItemCount() {
        return filteredHadisList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String searchString = charSequence.toString();

                if (searchString.isEmpty()) {

                    filteredHadisList = hadisList;

                } else {

                    ArrayList<Hadis> tempFilteredList = new ArrayList<>();

                    for (Hadis hadis : hadisList) {

                        // search for user name
                        if (hadis.getHadis().toLowerCase().contains(searchString)) {

                            tempFilteredList.add(hadis);
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
