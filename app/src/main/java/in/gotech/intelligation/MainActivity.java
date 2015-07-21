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

public class MainActivity extends AppCompatActivity {

    private static final int NUM_ITEMS = 3;

    MyAdapter mAdapter;

    ViewPager mPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAdapter = new MyAdapter(getSupportFragmentManager());

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        // Specify that tabs should be displayed in the action bar.
        final ActionBar actionBar = getActionBar();

//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create a tab listener that is called when the user changes tabs.
        ActionBar.TabListener tabListener = new ActionBar.TabListener() {

            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                // When the tab is selected, switch to the
                // corresponding page in the ViewPager.
                mPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // hide the given tab
            }

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {
                // probably ignore this event
            }
        };
//        actionBar.addTab(actionBar.newTab().setText("Summary").setTabListener(tabListener));
//        actionBar.addTab(actionBar.newTab().setText("Statistics").setTabListener(tabListener));
//        actionBar.addTab(actionBar.newTab().setText("Weather").setTabListener(tabListener));



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
    }


}


