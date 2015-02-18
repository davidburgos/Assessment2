package co.mobilemakers.expensesmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class FriendsFragment extends ListFragment {

    List<String> mFriendsArray;
    List<String> mFriendsIdArray;
    List<Friend> mFriends;
    ArrayAdapter<String> mAdapterFriends;
    public static String FRIEND_NAME = "FRIEND_NAME";
    public static String FRIEND_ID = "FRIEND_ID";
    private final static String LOG_TAG =   FriendsFragment.class.getSimpleName();


    public FriendsFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFriendsArray = new ArrayList<String>();
        populateFriendArray();
        mAdapterFriends = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_friends, R.id.text_view_friend, mFriendsArray);
        setListAdapter(mAdapterFriends);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                String friendName = (String)parent.getItemAtPosition(position);
                intent.putExtra(FRIEND_NAME, friendName);
                intent.putExtra(FRIEND_ID,id);
                Activity activity = getActivity();
                activity.setResult(Activity.RESULT_OK,intent);
                activity.finish();
            }
        });
    }

    private void populateFriendArray() {
        DataBaseManager.init(getActivity());
        mFriends = DataBaseManager.getInstance().getAllFriends();
        for(Friend fd: mFriends) {
            mFriendsArray.add(fd.getName());
            Log.d(LOG_TAG, Integer.toString(fd.getId()));
        }
    }


}
