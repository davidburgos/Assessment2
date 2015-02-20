package co.mobilemakers.expensesmanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;


public class EventFragment extends ListFragment {

    public final static Integer REQUEST_CODE_CREATE_EVENT = 1;
    public final static String EVENT_NAME = "EVENT_NAME";
    public final static String EVENT_ID = "EVENT_ID";
    private final static String LOG_TAG = EventFragment.class.getSimpleName();

    ArrayList<Event> mEvents;
    EventAdapter mAdapterEvents;

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
        populateEvents();
        addItemClickListener();
    }

    private void populateEvents() {
        mEvents = new ArrayList<>();
        DataBaseManager.init(getActivity());
        mEvents = (ArrayList)DataBaseManager.getInstance().getAllEvents();
        mAdapterEvents = new EventAdapter(getActivity(), mEvents);
        setEmptyText(getString(R.string.text_view_empty_event_list));
        setListAdapter(mAdapterEvents);
    }

    private void addItemClickListener() {
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), EventDescriptionActivity.class);
                Event event = (Event)parent.getItemAtPosition(position);
                intent.putExtra(EVENT_ID,event.getId());
                intent.putExtra(EVENT_NAME, event.getName());
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
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == REQUEST_CODE_CREATE_EVENT){
                populateEvents();
                mAdapterEvents.notifyDataSetChanged();
            }
        }
    }
}
