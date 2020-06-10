package com.ykpylcn.kutubisittehadisler_v1.ui.favoriler;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ykpylcn.kutubisittehadisler_v1.App;
import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;
import com.ykpylcn.kutubisittehadisler_v1.db.HadislerAdapter;

import java.util.ArrayList;

public class SakliHadislerFragment extends Fragment {

    private SakliHadislerViewModel sakliHadislerViewModel;
    private RecyclerView recyclerView;
    private HadislerAdapter hadislerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        sakliHadislerViewModel =
                ViewModelProviders.of(this).get(SakliHadislerViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sakli_hadisler, container, false);

        sakliHadislerViewModel.getHadisler().observe(getViewLifecycleOwner(), new Observer<ArrayList<Hadis>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Hadis> s) {

                GetHadisler(s);
            }
        });
        Init(root);
        return root;
    }
    private void GetHadisler(final ArrayList<Hadis> hadisler){

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(App.app_context);
        recyclerView.setLayoutManager(mLayoutManager);
        //        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, 16));

        hadislerAdapter = new HadislerAdapter(App.app_context, hadisler);
        recyclerView.setAdapter(hadislerAdapter);


//        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(App.app_context,
//                recyclerView, new RecyclerTouchListener.ClickListener() {
//            @Override
//            public void onClick(View view, final int position) {
////                Message.show(hadisler.get(position).getHadis());
//            }
//
//            @Override
//            public void onLongClick(View view, int position) {
//
//             //   Message.show(context,hadisler.get(position).getHadis()+" : ");
//
//                //showActionsDialog(position);
//            }
//        }));

    }
    private void Init(View root){

        recyclerView = root.findViewById(R.id.rv_Hadisler);
//        spinAnaKonu=root.findViewById(R.id.spinnerAnaKonu);
//        spinAltKonu=root.findViewById(R.id.spinnerAltKonu);
    }
}
