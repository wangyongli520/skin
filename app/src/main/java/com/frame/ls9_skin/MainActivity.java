package com.frame.ls9_skin;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.frame.ls9_skin.adapter.MyViewPagerAdapter;
import com.frame.ls9_skin.fragment.AudioFragment;
import com.frame.ls9_skin.fragment.MusicFragment;
import com.frame.ls9_skin.fragment.VideoFragment;
import com.frame.ls9_skin.view.MyTabLayout;
import com.frame.skin_core.SkinManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyTabLayout tab = findViewById(R.id.tab);
        ViewPager vp = findViewById(R.id.vp);

        List<String> titles = new ArrayList<>();
        titles.add("音乐");
        titles.add("音频");
        titles.add("视频");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MusicFragment());
        fragments.add(new AudioFragment());
        fragments.add(new VideoFragment());
        MyViewPagerAdapter adapter = new MyViewPagerAdapter(getSupportFragmentManager(), titles, fragments);
        vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
    }

    /**
     * 个性换肤
     * @param view
     */
    public void skinSelect(View view) {
        startActivity(new Intent(this,SkinActivity.class));
    }
}
