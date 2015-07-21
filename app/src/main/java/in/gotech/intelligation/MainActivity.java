package in.gotech.intelligation;


import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentManager;
import android.view.Window;
import android.support.v4.view.PagerTabStrip;

import in.gotech.intelligation.SlidingTabLayout;

import android.graphics.Color;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_ITEMS = 3;

    MyAdapter mAdapter;

    ViewPager mPager;

    SlidingTabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });


        tabs.setDistributeEvenly(true);

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(mPager);

    }

    public static class MainFragment extends Fragment {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            Bundle args = getArguments();
            int fragmentNumber = args.getInt("fragmentNumber");
            if (fragmentNumber == 0) {
                return inflater.inflate(
                        R.layout.summary, container, false);
            } else if (fragmentNumber == 1) {
                return inflater.inflate(
                        R.layout.stats, container, false);
            } else {
                return inflater.inflate(
                        R.layout.weather, container, false);
            }
        }
    }

    public class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new MainFragment();
            Bundle args = new Bundle();
            args.putInt("fragmentNumber", position);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Summary";
                case 1:
                    return "Statistics";
                case 2:
                    return "Weather";
            }
            return "";
        }
    }


}


