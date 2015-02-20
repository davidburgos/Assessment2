package co.mobilemakers.expensesmanager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends ActionBarActivity implements ActionBar.TabListener {

    SectionsPagerAdapter mSectionsPagerAdapter;

    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateFriendsTable();
        customizeActionBar();
        testquery();
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
    private void testquery()
    {

        List<Friend>lf = new ArrayList<Friend>();
        DataBaseManager.init(getBaseContext());
        lf = DataBaseManager.getInstance().getAllFriendsOwedMe(1);

    }

    private void customizeActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.action_bar_expenses_manager_title);
        actionBar.setIcon(R.drawable.ic_principal);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    private void populateFriendsTable() {
        DataBaseManager.init(this);
        List<Friend> friends= DataBaseManager.getInstance().getAllFriends();
        if(friends.size()<=0) {
            Friend friend = new Friend();
            friend.setName("david.burgos");
            friend.setEmail("david.burgos@globant.com");
            friend.setPassword("123456");
            Friend friend2 = new Friend();
            friend2.setName("juan.ramirez");
            friend2.setEmail("juan.ramirez@globant.com");
            friend2.setPassword("123456");
            Friend friend3 = new Friend();
            friend3.setName("diana.perez");
            friend3.setEmail("diana.perez@globant.com");
            friend3.setPassword("123456");
            DataBaseManager.getInstance().addFriend(friend);
            DataBaseManager.getInstance().addFriend(friend2);
            DataBaseManager.getInstance().addFriend(friend3);
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