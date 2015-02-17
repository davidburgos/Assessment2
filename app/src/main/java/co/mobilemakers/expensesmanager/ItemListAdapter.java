package co.mobilemakers.expensesmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 16/02/2015.
 */
public class ItemListAdapter extends ArrayAdapter<EventDescriptionItem> {

    public  List<EventDescriptionItem> mEventList;
    private  Context mContext;
    public  int total = 0;

    public  ItemListAdapter(Context context, List<EventDescriptionItem> eventList)
    {
        super(context,R.layout.item_list_view_invoices,eventList);
        mContext = context;
        mEventList=eventList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        if (convertView !=null)
        {
            rowView = convertView;
        }
        else
        {  LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.item_list_view_invoices,parent,false);
        }
        if (rowView !=null)
        {
            TextView text_view_title = (TextView)rowView.findViewById(R.id.text_view_thing_to_pay);
            text_view_title.setText(mEventList.get(position).getEventTitle());

            TextView text_view_friend = (TextView)rowView.findViewById(R.id.text_view_friend_to_pay);
            text_view_friend.setText(mEventList.get(position).getFriendPay());

            TextView text_view_total = (TextView)rowView.findViewById(R.id.text_view_you_owe);
            text_view_total.setText(mEventList.get(position).getTotal());
            total+=1000;
        }
        return rowView;
    }
}
