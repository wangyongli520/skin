package com.frame.ls9_skin.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.frame.ls9_skin.R;
import com.frame.ls9_skin.adapter.MyRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： Created by 180727a
 * 时间:  2019/7/15
 */
public class MusicFragment extends Fragment{
    private View mView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       mView = inflater.inflate(R.layout.fragment_music,container,false);
        RecyclerView rc = mView.findViewById(R.id.rc);
        rc.setLayoutManager(new LinearLayoutManager(getContext()));
        rc.setAdapter(new MyRecyclerAdapter(getData()));
        return mView;
    }


    private List<String> getData(){
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            list.add("item "+(i+1)+"行");
        }
        return list;
    }
}
