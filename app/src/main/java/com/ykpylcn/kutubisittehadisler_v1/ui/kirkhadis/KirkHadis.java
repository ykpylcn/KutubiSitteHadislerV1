package com.ykpylcn.kutubisittehadisler_v1.ui.kirkhadis;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;
import com.ykpylcn.kutubisittehadisler_v1.db.KirkHadisAdapter;
import com.ykpylcn.kutubisittehadisler_v1.ui.hadisler.HadislerViewModel;

import java.util.ArrayList;

public class KirkHadis extends Fragment {

    private KirkHadisViewModel mViewModel;
    ListView listView;
    private static KirkHadisAdapter adapter;

    public static KirkHadis newInstance() {
        return new KirkHadis();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(KirkHadisViewModel.class);

        View view= inflater.inflate(R.layout.kirk_hadis_fragment, container, false);
        listView=view.findViewById(R.id.list_kirk_hadis);
        mViewModel.getHadisler().observe(getViewLifecycleOwner(), new Observer<ArrayList<Hadis>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Hadis> s) {

                adapter= new KirkHadisAdapter(s,getActivity().getApplicationContext());

                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Hadis dataModel= s.get(position);

                        Snackbar.make(view, dataModel.getAnaKonu()+"\n"+dataModel.getHadis()+" API: "+dataModel.getHadisNo(), Snackbar.LENGTH_LONG)
                                .setAction("No action", null).show();
                    }
                });

            }
        });
        return view;
    }


}