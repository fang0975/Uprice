package com.example.user.uprice;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.MapFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/12/1.
 */

public class TabFragment extends Fragment {

    ContentPagerAdapter contentPagerAdapter;

    List<Fragment> fragmentList;

    TabLayout tabLayout;

    ViewPager viewPager;

    View myView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        myView = inflater.inflate(R.layout.viewpager_layout, container , false);
        initView();
        setView();
        getActivity().setTitle("加油站");
        return myView;
    }

    private void initView(){
        tabLayout = myView.findViewById(R.id.pager_tabs);
        viewPager= myView.findViewById(R.id.viewpager);
    }

    private void setView() {
        fragmentList= new ArrayList<>();
        fragmentList.add(new GasStationFragment());
        fragmentList.add(new MapFragment());
        contentPagerAdapter = new ContentPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(contentPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {

            }
        });
        tabLayout.addTab(tabLayout.newTab().setText("Page one"));
        tabLayout.addTab(tabLayout.newTab().setText("Page two"));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    private class ContentPagerAdapter extends FragmentPagerAdapter {

        private FragmentManager fragmentManager;

        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
            this.fragmentManager = fm;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @NonNull
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            this.fragmentManager.beginTransaction().show(fragment).commit();
            return fragment;
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Fragment fragment = fragmentList.get(position);
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }
}
