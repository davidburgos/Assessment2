package co.mobilemakers.expensesmanager;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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


public class EventDescriptionActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_description);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new EventDescriptionFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class EventDescriptionFragment extends Fragment {
        public final String LOG_TAG = getClass().getSimpleName();

        public static final int REQUEST_CODE = 0;
        public static String EVENT_NAME = "EVENT_NAME";
        private String eventName;

        List<EventDescriptionItem> eventDescriptionList = new ArrayList<>();
        ItemListAdapter mAdapter;

        public EventDescriptionFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_event_description, container, false);
            setHasOptionsMenu(true);
            SetActionBarTitle();
            TextView textViewTotal = (TextView)rootView.findViewById(R.id.text_view_sum_invoices);

/* TODO remove fake data. */
            EventDescriptionItem event1 = new EventDescriptionItem("Yacht Rent","David Burgos paid $30.000","You Owe $10.000");
            eventDescriptionList.add(event1);
            EventDescriptionItem event2 = new EventDescriptionItem("Rent Dive Gear","Diana Perez paid $15.000","You Owe $5.000");
            eventDescriptionList.add(event2);
            EventDescriptionItem event3 = new EventDescriptionItem("Snacks","Diana Perez paid $210.000","You Owe $70.000");
            eventDescriptionList.add(event3);
 /*end TODO*/

            mAdapter = new ItemListAdapter(getActivity(),eventDescriptionList);
            ListView listView = (ListView)rootView.findViewById(R.id.list_view_invoices);
            listView.setAdapter(mAdapter);
            Log.d("total->",Integer.toString(mAdapter.total));
            textViewTotal.setText("Total Invoices: $"+Integer.toString(mAdapter.total));
            return rootView;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int menuId = item.getItemId();
            Boolean handled=false;
            switch (menuId){
                case R.id.add_Item:
                    /*Intent intent = new Intent(getActivity(),NewInvoiceActivity.class);
                    intent.putExtra(EVENT_NAME,eventName);
                    startActivityForResult(intent, REQUEST_CODE);*/
                    mAdapter.add(new EventDescriptionItem("Beers Added","Juan Ramirez Paid $200","You Owe $70.000") );
                    Log.d(LOG_TAG,"CLICK ON ADD BUTTON");
                    handled = true;
                    break;
            }
            if(!handled)
            {
                handled = super.onOptionsItemSelected(item);
            }

            return handled;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == REQUEST_CODE)
            {
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.d(LOG_TAG,"regreso de NewInvoiceActivity");
                        //data.getStringExtra(EditContactActivity.FIRSTNAME)
                        //data.getStringExtra(EditContactActivity.FIRSTNAME)
                        mAdapter.add(new EventDescriptionItem("Beers Added","Juan Ramirez Paid $200","You Owe $70.000") );
                        break;
                }

            }
        }

        private void SetActionBarTitle() {

            String eventTitle = "DefaultTitle";
            eventTitle = getActivity().getIntent().getStringExtra(EVENT_NAME);
            eventName = eventTitle;
            getActivity().setTitle(eventTitle);
        }

        @Override
        public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
            super.onCreateOptionsMenu(menu, inflater);
            inflater.inflate(R.menu.menu_event_description_fragment,menu);
        }
    }
}
