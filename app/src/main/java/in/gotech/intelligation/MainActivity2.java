package in.gotech.intelligation;


import android.support.v4.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.FragmentManager;

import android.graphics.Color;
import android.widget.Toast;

public class MainActivity2 extends Fragment {

    private static final int NUM_ITEMS = 3;

    MyAdapter mAdapter;

    ViewPager mPager;

    SlidingTabLayout tabs;

    public MainActivity2() {

    }

    public static Fragment newInstance(int position) {
        Fragment fragment = new MainActivity2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setContentView(R.layout.activity_main);

        SharedPreferences credentialsSharedPref = getActivity().getSharedPreferences(Login.PREFS_NAME, AppCompatActivity.MODE_PRIVATE);

        Toast.makeText(getActivity(), "Welcome : " + credentialsSharedPref.getString("username", ""), Toast.LENGTH_SHORT).show();

        mAdapter = new MyAdapter(getActivity().getSupportFragmentManager());

        mPager = (ViewPager) getActivity().findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) getActivity().findViewById(R.id.tabs);

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
            switch (position) {
                case 0:
                    return new SummaryFragment();
                case 1:
                    return new StatsFragment();
                default:
                    return new WeatherFragment();
            }
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


