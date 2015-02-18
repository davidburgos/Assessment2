package co.mobilemakers.expensesmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;

import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();


    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO first run CreateFriends methods.
        //CreateFriends();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }

    private void CreateFriends() {

        DataBaseManager.init(this);
        Friend friend = new Friend();
        friend.setName("Juan Ramirez");
        friend.setEmail("juan.ramirez@globant.com");
        friend.setPassword("123456");

        Friend friend2 = new Friend();
        friend2.setName("David Burgos");
        friend2.setEmail("david.burgos@globant.com");
        friend2.setPassword("123456");

        Friend friend3 = new Friend();
        friend3.setName("Diana Perez");
        friend3.setEmail("diana.perez@globant.com");
        friend3.setPassword("123456");

        DataBaseManager.getInstance().addFriend(friend);
        DataBaseManager.getInstance().addFriend(friend2);
        DataBaseManager.getInstance().addFriend(friend3);
        Log.d(LOG_TAG, "Friends Created");
        final List<Friend> friendLists = DataBaseManager.getInstance().getAllFriends();
         if(friendLists != null) {
            for (Friend fr : friendLists) {
                Log.d(LOG_TAG, fr.getName());
                Log.d(LOG_TAG, fr.getEmail());
                Log.d(LOG_TAG, fr.getPassword());
            }
        }
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = null;

            switch (position){
                case 0:
                    fragment = new EventFragment();
                    break;
                case 1:
                    fragment = new SummaryExpensesFragment();
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {

            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_event_fragment).toUpperCase(l);
                case 1:
                    return getString(R.string.title_summary_expenses).toUpperCase(l);
            }
            return null;
        }
    }
}
