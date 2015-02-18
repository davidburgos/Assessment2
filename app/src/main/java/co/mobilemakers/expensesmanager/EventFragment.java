package co.mobilemakers.expensesmanager;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class EventFragment extends ListFragment {

    public final static Integer REQUEST_CODE_CREATE_EVENT = 1;
    public final static Integer REQUEST_CODE_EVENT = 2;
    public final static String EVENT_NAME = "EVENT_NAME";
    ArrayList<String> mEvents;
    ArrayAdapter<String> mAdapterEvents;

    public EventFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(
            Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_event_fragment, menu);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEvents = new ArrayList<>();
        mAdapterEvents = new ArrayAdapter<String>(getActivity(),
                R.layout.list_item_events, R.id.text_view_event_name, mEvents);
        setListAdapter(mAdapterEvents);
        addItemClickListener();
    }

    private void addItemClickListener() {
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventDescriptionActivity.class);
                String eventName = (String)parent.getItemAtPosition(position);
                intent.putExtra(EVENT_NAME, eventName);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.menu_event_fragment_add:
                Intent intent = new Intent(getActivity(), CreateEventActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CREATE_EVENT );
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == getActivity().RESULT_OK){
            if(requestCode == REQUEST_CODE_CREATE_EVENT){
                String eventName = (String)data.getExtras().get("EventName");
                String eventDesc = (String)data.getExtras().get("EventDescription");
                mEvents.add(eventName);
                mAdapterEvents.notifyDataSetChanged();
            }
        }
    }
}
