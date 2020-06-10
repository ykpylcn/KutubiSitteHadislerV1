package com.ykpylcn.kutubisittehadisler_v1.ui.search;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.SearchView;
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
import com.ykpylcn.kutubisittehadisler_v1.db.HadislerSpinnerAdapter;
import com.ykpylcn.kutubisittehadisler_v1.ui.Message;
import com.ykpylcn.kutubisittehadisler_v1.utils.RecyclerTouchListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private HadislerAdapter hadislerAdapter;
    private Spinner spinAnaKonu, spinAltKonu;
//    private ArrayList<Hadis> hadisList;
    private SearchViewModel searchViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_search_hadisler, container, false);

        searchViewModel.getHadisler().observe(getViewLifecycleOwner(), new Observer<ArrayList<Hadis>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onChanged(@Nullable ArrayList<Hadis> s) {

                GetHadisler(s);
                GetAnaKonuSpinler(s);

                setHasOptionsMenu(true);
//                toggleEmptyHadisler();

            }
        });
        Init(root);





        return root;
    }

    private void Init(View root){

        recyclerView = root.findViewById(R.id.rv_Hadisler);
        spinAnaKonu=root.findViewById(R.id.spinnerAnaKonu);
        spinAltKonu=root.findViewById(R.id.spinnerAltKonu);
    }


//    @RequiresApi(api = Build.VERSION_CODES.N)
    private void GetAnaKonuSpinler(final ArrayList<Hadis> hadislerAll){

        List<Hadis> hadislist=App.DbAdapter.getAnaKonular();

        spinAnaKonu.setAdapter(new HadislerSpinnerAdapter(App.app_context,R.layout.spinner_hadisler_list_row,hadislist));
        spinAnaKonu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    Hadis hadis = (Hadis) parent.getSelectedItem();
                    hadislerAdapter.getFilter().filter("@1/"+hadis.getAnaKonu());
                    DisplayAltKonular(hadis);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

//        HashMap<String, List<Hadis>> hashMap = new HashMap<String, List<Hadis>>();
//        for(Hadis hadis : hadisler) {
//            if(!hashMap.containsKey(hadis.getAnaKonu())){
//                List<Hadis> list = new ArrayList<Hadis>();
//                list.add(hadis);
//
//                hashMap.put(hadis.getAnaKonu(), list);
//            } else {
//                hashMap.get(hadis.getAnaKonu()).add(hadis);
//            }
//        }


//        Map<Object, List<Object>> studlistGrouped =
//                hadisler.stream().collect(Collectors.groupingBy(new Function<Object, Object>() {
//                    @Override
//                    public Object apply(Object w) {
//                        Hadis h=(Hadis)w;
//                        return h.getAnaKonu();
//                    }
//                }));



    }
    private void DisplayAltKonular(Hadis hadis) {

        List<Hadis> hadislist=App.DbAdapter.getAltKonular(hadis.getAnaKonu());

        spinAltKonu.setAdapter(new HadislerSpinnerAdapter(App.app_context,R.layout.spinner_hadisler_list_row,hadislist));
        spinAltKonu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                Hadis hadis = (Hadis) parent.getSelectedItem();
                hadislerAdapter.getFilter().filter("@2/"+hadis.getAnaKonu());
            }}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate( R.menu.options_menu, menu);

        final MenuItem myActionMenuItem = menu.findItem( R.id.search);
        final SearchView searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        searchView.setFocusable(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if(query.length()>2)
                    hadislerAdapter.getFilter().filter(query);
                else
                    Message.show("En az uc karakter girmelisiniz!");
//                toggleEmptyHadisler();

//                if( ! searchView.isIconified()) {
//                    searchView.setIconified(true);
//                }
//                myActionMenuItem.collapseActionView();
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                // UserFeedback.show( "SearchOnQueryTextChanged: " + s);

                return false;
            }
        });
        return;

    }
//    private void toggleEmptyHadisler() {
//        // you can check notesList.size() > 0
//
//        if (hadislerAdapter.getItemCount() > 0) {
//            noHadisView.setVisibility(View.GONE);
//        }
//         else {
//            noHadisView.setVisibility(View.VISIBLE);
//        }
//    }
}
