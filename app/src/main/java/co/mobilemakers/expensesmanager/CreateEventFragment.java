package co.mobilemakers.expensesmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import com.j256.ormlite.dao.ForeignCollection;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class CreateEventFragment extends ListFragment {

    final static Integer REQUEST_CODE_FRIENDS = 0;

    EditText mEditTextEventName;
    EditText mEditTextEventDescription;
    Button mButtonCreateEvent;
    Button mButtonAddFriend;
    ArrayList<String> mFriends,mFriendsId;
    ArrayAdapter<String> mAdapterFriends;


    public CreateEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_event, container, false);
        wireUpViews(rootView);
        prepareAddFriendButton(rootView);
        prepareCreateEventButton(rootView);
        addTextWatcher();
        return rootView;
    }

    private void addTextWatcher() {
        DataTextWatcher dataTextWatcher = new DataTextWatcher(mEditTextEventName, mButtonCreateEvent);
        mEditTextEventName.addTextChangedListener(dataTextWatcher);
    }

    private void prepareCreateEventButton(View rootView) {
        mButtonCreateEvent = (Button)rootView.findViewById(R.id.button_create_event);
        mButtonCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createEvent();
                Activity activity = getActivity();
                activity.setResult(Activity.RESULT_OK, null);
                activity.finish();
            }
        });
    }

    private void createEvent() {
        DataBaseManager.init(getActivity());
        Event event = new Event();
        event.setName(mEditTextEventName.getText().toString());
        event.setDescription(mEditTextEventDescription.getText().toString());
        DataBaseManager.getInstance().addEvent(event);
        for(int i = 0; i<mFriends.size();i++)
        {
            Friend friend = DataBaseManager.getInstance().getFriendById(mFriends.get(i));
            DataBaseManager.getInstance().addEventFriend(new EventFriend(event,friend));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mFriends = new ArrayList<>();
        mFriendsId = new ArrayList<>();
        mAdapterFriends = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_friends, R.id.text_view_friend,mFriends);
        setListAdapter(mAdapterFriends);
    }

    private void prepareAddFriendButton(View rootView) {
        mButtonAddFriend = (Button)rootView.findViewById(R.id.button_add_friend_event);
        mButtonAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FriendsActivity.class);
                startActivityForResult(intent, REQUEST_CODE_FRIENDS);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            if(requestCode == REQUEST_CODE_FRIENDS){
                String friendName = (String)data.getExtras().get(FriendsFragment.FRIEND_NAME);
                mFriends.add(friendName);
                mAdapterFriends.notifyDataSetChanged();
            }
        }
    }

    private void wireUpViews(View rootView) {
        mEditTextEventName = (EditText)rootView.findViewById(R.id.edit_text_event_name);
        mEditTextEventDescription = (EditText)rootView.findViewById(R.id.edit_text_event_description);
    }


}
