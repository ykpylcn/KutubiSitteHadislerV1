package com.ykpylcn.kutubisittehadisler_v1.ui.gallery;


import android.content.Context;
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


import com.ykpylcn.kutubisittehadisler_v1.R;
import com.ykpylcn.kutubisittehadisler_v1.db.DBAdapter;
import com.ykpylcn.kutubisittehadisler_v1.db.Hadis;
import com.ykpylcn.kutubisittehadisler_v1.ui.Message;

import java.util.List;

public class GalleryFragment extends Fragment {

    private GalleryViewModel galleryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        galleryViewModel =
                ViewModelProviders.of(this).get(GalleryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Context context=getActivity().getApplicationContext();
                DBAdapter dbAdapter=new DBAdapter(context);
                Message.show(context,"basladi");
                List<Hadis> list=dbAdapter.getAllHadisler();
                Message.show(context,"bitti");
                textView.setText(list.get(14).getHadis());


            }
        });

        return root;
    }
}
