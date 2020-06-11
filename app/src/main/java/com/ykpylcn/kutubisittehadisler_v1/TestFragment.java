package com.ykpylcn.kutubisittehadisler_v1;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;
import com.ykpylcn.kutubisittehadisler_v1.db.HadislerAdapter;
import com.ykpylcn.kutubisittehadisler_v1.ui.Message;
import com.ykpylcn.kutubisittehadisler_v1.ui.search.SearchViewModel;

import java.util.ArrayList;

public class TestFragment extends Fragment {

    private TestViewModel mViewModel;

    public static TestFragment newInstance() {
        return new TestFragment();
    }
    private RecyclerView recyclerView;
    private HadislerAdapter hadislerAdapter;
    private TestViewModel testViewModel;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        testViewModel =
                ViewModelProviders.of(this).get(TestViewModel.class);

        final View root = inflater.inflate(R.layout.test_fragment, container, false);

        testViewModel.getHadisler().observe(getViewLifecycleOwner(), new Observer<ArrayList<Hadis>>() {
            @Override
            public void onChanged(@Nullable ArrayList<Hadis> s) {

                GetHadisler(s);


                setHasOptionsMenu(true);
//                toggleEmptyHadisler();

            }
        });
        Init(root);





        return root;
    }
    private boolean loading = true;
    private void GetHadisler(final ArrayList<Hadis> hadisler){

        final RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(App.app_context);
        recyclerView.setLayoutManager(mLayoutManager);
        //        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, 16));

        hadislerAdapter = new HadislerAdapter(App.app_context, hadisler);
        recyclerView.setAdapter(hadislerAdapter);

        final int[] pastVisiblesItems = new int[1];
        final int[] visibleItemCount = new int[1];
        final int[] totalItemCount = new int[1];

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount[0] = mLayoutManager.getChildCount();
                    totalItemCount[0] = mLayoutManager.getItemCount();
                    pastVisiblesItems[0] = ((LinearLayoutManager) mLayoutManager).findFirstVisibleItemPosition();

                    if (loading) {
                        if ((visibleItemCount[0] + pastVisiblesItems[0]) >= totalItemCount[0]) {
                            loading = false;

                            // Do pagination.. i.e. fetch new data
                        }
                    }
                }
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

                return true;
            }
        });
        return;

    }
    private void Init(View root){

        recyclerView = root.findViewById(R.id.rv_Hadisler_test);

    }

}