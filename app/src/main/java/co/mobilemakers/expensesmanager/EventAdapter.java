package co.mobilemakers.expensesmanager;

import android.content.Context;
import android.net.Uri;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by diany_000 on 2/18/2015.
 */
public class EventAdapter extends ArrayAdapter<Event> {

    List<Event> mEvents;
    Context mContext;

    public EventAdapter(Context context, List<Event> events){
        super(context, R.layout.list_item_events, events);
        mEvents = events;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = reuseOrGenerateRowView(convertView, parent);
        displayContentInView(position, rowView);
        return rowView;
    }

    private View reuseOrGenerateRowView(View convertView, ViewGroup parent) {
        View rowView;
        if (convertView != null) {
            rowView = convertView;
        } else {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_item_events, parent, false);
        }
        return rowView;
    }

    private void displayContentInView(int position, View rowView) {
        if (rowView != null) {
            TextView textViewName = (TextView) rowView.findViewById(R.id.text_view_event_name);
            textViewName.setText(mEvents.get(position).getName());
            TextView textViewDescription = (TextView) rowView.findViewById(R.id.text_view_event_description);
            textViewDescription.setText(mEvents.get(position).getDescription());
        }
    }
}
