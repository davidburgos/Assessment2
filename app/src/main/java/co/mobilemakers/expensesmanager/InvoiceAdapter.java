package co.mobilemakers.expensesmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 16/02/2015.
 */
public class InvoiceAdapter extends ArrayAdapter<Invoice> {

    List<Invoice> mInvoices;
    Context mContext;
    public int total = 0;

    public InvoiceAdapter(Context context, List<Invoice> invoices) {
        super(context, R.layout.list_item_invoices, invoices);
        mContext = context;
        mInvoices = invoices;
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
            rowView = inflater.inflate(R.layout.list_item_invoices, parent, false);
        }
        return rowView;
    }

    private void displayContentInView(int position, View rowView) {
        if (rowView != null) {
            TextView text_view_title = (TextView) rowView.findViewById(R.id.text_view_thing_to_pay);
            text_view_title.setText(mInvoices.get(position).getName());
            TextView text_view_friend = (TextView) rowView.findViewById(R.id.text_view_friend_who_pay);
            int friendId = mInvoices.get(position).getFriendId();
            DataBaseManager.init(getContext());
            Friend friend = DataBaseManager.getInstance().getFriendById(friendId);
            text_view_friend.setText(friend.getName() + " paid $" + mInvoices.get(position).getPrice());

            //TODO get how many people are in the event to divide the price
            TextView text_view_total = (TextView) rowView.findViewById(R.id.text_view_you_owe);
            text_view_total.setText("Divide total");
            total += 1000;
        }
    }

}
