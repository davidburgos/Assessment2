package co.mobilemakers.expensesmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class FriendsFragment extends ListFragment {

    String[] mFriends;
    ArrayAdapter<String> mAdapterFriends;
    public static String FRIEND_NAME = "FRIEND_NAME";


    public FriendsFragment() {
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //TODO
        mFriends = getResources().getStringArray(R.array.friends);
        mAdapterFriends = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_friends, R.id.text_view_friend, mFriends);
        setListAdapter(mAdapterFriends);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                String friendName = (String)parent.getItemAtPosition(position);
                intent.putExtra(FRIEND_NAME, friendName);
                Activity activity = getActivity();
                activity.setResult(Activity.RESULT_OK,intent);
                activity.finish();
            }
        });
    }


}
