package com.ykpylcn.kutubisittehadisler_v1.ui.search;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.Spinner;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.ykpylcn.kutubisittehadisler_v1.utils.PaginationScrollListener;

import java.util.ArrayList;
import java.util.List;


public class SearchFragment extends Fragment {
    private RecyclerView recyclerView;
    HadislerAdapter adapter;
    LinearLayoutManager   linearLayoutManager;

    ProgressBar progressBar;
    private Spinner spinAnaKonu, spinAltKonu;
    private boolean isSearched = false;
    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int pageLimit = 10;
    private int TOTAL_PAGES =10;
    private int currentPage = PAGE_START;
    private SearchViewModel searchViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        searchViewModel =
                ViewModelProviders.of(this).get(SearchViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_search_hadisler, container, false);

        searchViewModel.getHadisler().observe(getViewLifecycleOwner(), new Observer<ArrayList<Hadis>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Hadis> s) {



            }
        });

        Init(root);
        GetHadisler();
        GetAnaKonuSpinler();
        // mocking network delay for API call
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFirstPage();
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);
//        progressBar.setVisibility(View.GONE);
        setHasOptionsMenu(true);






        return root;
    }

    private void GetHadisler(){

        linearLayoutManager = new LinearLayoutManager(App.app_context);
        recyclerView.setLayoutManager(linearLayoutManager);

        //        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, 16));

        adapter = new HadislerAdapter(App.app_context);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                if(isSearched)
                    TOTAL_PAGES= App.filteredListHadisler.size()/pageLimit;
                else
                    TOTAL_PAGES= App.mArrayListHadisler.size()/pageLimit;
                isLoading = true;
                currentPage += 1;
//                progressBar.setVisibility(View.VISIBLE);
                // mocking network delay for API call
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        loadNextPage();
                    }
                }, 1000);
            }

            @Override
            public int getTotalPageCount() {
                return TOTAL_PAGES;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });





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
    private void GetAnaKonuSpinler(){

        List<Hadis> hadislist=App.DbAdapter.getAnaKonular();

        spinAnaKonu.setAdapter(new HadislerSpinnerAdapter(App.app_context,R.layout.spinner_hadisler_list_row,hadislist));
        spinAnaKonu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    Hadis hadis = (Hadis) parent.getSelectedItem();
                    currentPage = PAGE_START;
                    isLoading = false;
                    isLastPage = false;
                    adapter.clear();
                    isSearched=true;
                    adapter.getFilter().filter("@1/"+hadis.getAnaKonu());
                    DisplayAltKonular(hadis);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    private void DisplayAltKonular(Hadis hadis) {

        List<Hadis> hadislist=App.DbAdapter.getAltKonular(hadis.getAnaKonu());

        spinAltKonu.setAdapter(new HadislerSpinnerAdapter(App.app_context,R.layout.spinner_hadisler_list_row,hadislist));
        spinAltKonu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position>0){
                    Hadis hadis = (Hadis) parent.getSelectedItem();
                    currentPage = PAGE_START;
                    isLoading = false;
                    isLastPage = false;
                    adapter.clear();
                    isSearched=true;
                    adapter.getFilter().filter("@2/"+hadis.getAnaKonu());
                }}
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void loadFirstPage() {

        ArrayList<Hadis> movies = CreateHadisler(adapter.getItemCount());

        adapter.addAll(movies);
//        progressBar.setVisibility(View.GONE);
        if (currentPage <= TOTAL_PAGES)
            adapter.addLoadingFooter();
        else
            isLastPage = true;

    }

    private void loadNextPage() {


        ArrayList<Hadis> movies = CreateHadisler(adapter.getItemCount()-1);
//        progressBar.setVisibility(View.GONE);
        adapter.removeLoadingFooter();
        isLoading = false;

        adapter.addAll(movies);

        if (currentPage != TOTAL_PAGES){
            if(TOTAL_PAGES>=currentPage)
                adapter.addLoadingFooter();
        }
        else
            isLastPage = true;
    }
    public ArrayList<Hadis> CreateHadisler(int itemCount) {
        ArrayList<Hadis> movies = new ArrayList<>();

        for (int i = itemCount; i < itemCount+pageLimit; i++) {

            if(!isSearched) {
                if (App.mArrayListHadisler.get(i) != null)
                    movies.add(App.mArrayListHadisler.get(i));
                else
                    return movies;
            }else {
                if (App.filteredListHadisler.size()>i){
                    if(App.filteredListHadisler.get(i) != null)
                        movies.add(App.filteredListHadisler.get(i));
                }
                else{
                    isLastPage=true;
                    return movies;
                }


            }


        }

        return movies;
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

                Filter(query);
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

                Filter(s);
                return true;
            }
        });


    }
    private void Filter(String s){
        if(s.length()>2)
            if(adapter!=null){

                spinAltKonu.setSelection(0);
                spinAnaKonu.setSelection(0);
                currentPage = PAGE_START;
                isLoading = false;
                isLastPage = false;
                adapter.clear();
                isSearched=true;

                adapter.getFilter().filter(s);

            }

            else
                Message.show("En az uc karakter girmelisiniz!");
    }
    private void Init(View root){

        progressBar = root.findViewById(R.id.main_progress);
        recyclerView = root.findViewById(R.id.rv_Hadisler);
        spinAnaKonu=root.findViewById(R.id.spinnerAnaKonu);
        spinAltKonu=root.findViewById(R.id.spinnerAltKonu);
    }

}