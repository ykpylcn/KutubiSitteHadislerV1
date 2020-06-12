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

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;
import com.ykpylcn.kutubisittehadisler_v1.db.HadislerAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.HadislerAdapterTest;
import com.ykpylcn.kutubisittehadisler_v1.ui.Message;
import com.ykpylcn.kutubisittehadisler_v1.ui.search.SearchViewModel;
import com.ykpylcn.kutubisittehadisler_v1.utils.PaginationScrollListener;

import java.util.ArrayList;

public class TestFragment extends Fragment {

    private TestViewModel mViewModel;

    public static TestFragment newInstance() {
        return new TestFragment();
    }
    private RecyclerView recyclerView;
    HadislerAdapterTest adapter;
    LinearLayoutManager   linearLayoutManager;

    ProgressBar progressBar;

    private static final int PAGE_START = 0;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private int TOTAL_PAGES = 3;
    private int currentPage = PAGE_START;

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

        linearLayoutManager = new LinearLayoutManager(App.app_context);
        recyclerView.setLayoutManager(linearLayoutManager);

        //        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //        recyclerView.addItemDecoration(new MyDividerItemDecoration(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, 16));

        adapter = new HadislerAdapterTest(App.app_context);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;

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


        // mocking network delay for API call
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadFirstPage();
            }
        }, 1000);


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
    private void loadFirstPage() {

        ArrayList<Hadis> movies = createMovies(adapter.getItemCount());
        progressBar.setVisibility(View.GONE);
        adapter.addAll(movies);

        if (currentPage <= TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;

    }

    private void loadNextPage() {

        ArrayList<Hadis> movies = createMovies(adapter.getItemCount());

        adapter.removeLoadingFooter();
        isLoading = false;

        adapter.addAll(movies);

        if (currentPage != TOTAL_PAGES) adapter.addLoadingFooter();
        else isLastPage = true;
    }
    public ArrayList<Hadis> createMovies(int itemCount) {
        ArrayList<Hadis> movies = new ArrayList<>();
        for (int i = itemCount; i < itemCount+10; i++) {

            movies.add(App.mArrayListHadisler.get(i));
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

//                if(query.length()>2)
//                    adapter.getFilter().filter(query);
//                else
//                    Message.show("En az uc karakter girmelisiniz!");
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

        progressBar = root.findViewById(R.id.main_progress);
        recyclerView = root.findViewById(R.id.rv_Hadisler_test);

    }

}