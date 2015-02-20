package co.mobilemakers.expensesmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class EventDescriptionFragment extends ListFragment {
    public final String LOG_TAG = getClass().getSimpleName();

    public static final int REQUEST_CODE = 0;
    public static String EVENT_NAME = "EVENT_NAME";
    public static String EVENT_ID = "EVENT_ID";
    private String eventName;
    private int eventId;

    List<Invoice> mEventInvoices = new ArrayList<>();
    InvoiceAdapter mAdapter;
    TextView mTextViewTotal;

    public EventDescriptionFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_event_description, container, false);
        setHasOptionsMenu(true);
        eventName = (String)getActivity().getIntent().getExtras().get(EventFragment.EVENT_NAME);
        eventId = getActivity().getIntent().getExtras().getInt(EventFragment.EVENT_ID);
        return rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        populateInvoices();
        DataBaseManager.init(getActivity());
        DataBaseManager.getInstance().getAllFriendsOwedMe(LoginActivity.user.getFriend().getId());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int menuId = item.getItemId();
        Boolean handled=false;
        switch (menuId){
            case R.id.add_Item:
                Intent intent = new Intent(getActivity(),NewInvoiceActivity.class);
                intent.putExtra(EVENT_NAME,eventName);
                intent.putExtra(EVENT_ID,eventId);
                startActivityForResult(intent, REQUEST_CODE);
                handled = true;
                break;
        }
        if(!handled)
        {
            handled = super.onOptionsItemSelected(item);
        }

        return handled;
    }

    private void populateInvoices() {
        mEventInvoices = new ArrayList<>();
        DataBaseManager.init(getActivity());
        mEventInvoices = (ArrayList)DataBaseManager.getInstance().getInvoicesByEventId(eventId);
        mAdapter = new InvoiceAdapter(getActivity(), mEventInvoices);
        setListAdapter(mAdapter);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE)
        {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    populateInvoices();
                    mAdapter.notifyDataSetChanged();
                    break;
            }

        }
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_event_description_fragment,menu);
    }


}
