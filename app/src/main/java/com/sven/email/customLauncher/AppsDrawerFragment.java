package com.sven.email.customLauncher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sven.email.R;

public class AppsDrawerFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    GridLayoutManager layoutManager;

    RecyclerView recyclerRecentView;
    RecyclerView.Adapter recentAdapter;

    public AppsDrawerFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.custom_app_drawer,container,false);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerRecentView = view.findViewById(R.id.appDrawer_recylerView_recent);
        recentAdapter = new AppsDrawerRecentAdapter(getContext());
        layoutManager = new GridLayoutManager (getContext(), 4);
        recyclerRecentView.setLayoutManager(layoutManager);
        recyclerRecentView.setAdapter(recentAdapter);

        recyclerView = view.findViewById(R.id.appDrawer_recylerView);
        adapter = new AppsDrawerAdapter(getContext());
        layoutManager = new GridLayoutManager (getContext(), 4);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



    }

}
