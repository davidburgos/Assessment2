package co.mobilemakers.expensesmanager;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SummaryExpensesFragment extends Fragment
    {

        public static class FriendAndPrice{

            private int mPrice;
            private Friend mFriend;

            public FriendAndPrice() {
            }

            public int getPrice() {
                return mPrice;
            }

            public void setPrice(int price) {
                mPrice = price;
            }

            public Friend getFriend() {
                return mFriend;
            }

            public void setFriend(Friend friend) {
                mFriend = friend;
            }
        }

        private Context mContext;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            mContext = getActivity().getBaseContext();
            View v = inflater.inflate(R.layout.fragment_summary_expenses, null);
            ExpandableListView elv = (ExpandableListView) v.findViewById(R.id.list);
            elv.setAdapter(new SavedTabsListAdapter());
            return v;
        }

        public class SavedTabsListAdapter extends BaseExpandableListAdapter {

            private LayoutInflater inflater;
            private String[] groups = { getString(R.string.text_view_title_you_owe),getString(R.string.text_view_title_owed_to_you)};

            private List<FriendAndPrice> FriendsIOwe = new ArrayList<>();
            private List<Friend> FriendsOweMe = new ArrayList<>();

            private int mAmountIOwe = 0, mAmountOwedToMe = 0;

            public SavedTabsListAdapter()
            {
                inflater = LayoutInflater.from(mContext);

                DataBaseManager.init(getActivity());
                DataBaseManager dataBaseManager = DataBaseManager.getInstance();
                FriendsIOwe  = dataBaseManager.getAllFriendsIOwe(LoginActivity.user.getFriend().getId());
                FriendsOweMe = dataBaseManager.getAllFriends();

                if(FriendsIOwe != null){
                    for(FriendAndPrice friend:FriendsIOwe){
                          mAmountIOwe += friend.getPrice();
                    }
                }

                if(FriendsOweMe != null){
                    for(Friend friend:FriendsOweMe){
                        mAmountOwedToMe += friend.getId(); //todo:calculate correct value
                    }
                }

            }

            @Override
            public int getChildrenCount(int i) {
                int result = 0;
                switch (i){
                    case 0:
                        result = FriendsIOwe.size();
                        break;
                    case 1:
                        result = FriendsOweMe.size();
                        break;
                }
                return result;
            }

            @Override
            public Object getChild(int i, int i1) {
                Object result = null;
                switch (i){
                    case 0:
                        result = FriendsIOwe.get(i1);
                        break;
                    case 1:
                        result = FriendsOweMe.get(i1).getName();
                        break;
                }
                return result;
            }

            @Override
            public int getGroupCount() {
                return groups.length;
            }

            @Override
            public Object getGroup(int i) {
                return groups[i];
            }

            @Override
            public long getGroupId(int i) {
                return i;
            }

            @Override
            public long getChildId(int i, int i1) {
                return i1;
            }

            @Override
            public boolean hasStableIds() {
                return true;
            }

            @Override
            public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

                View convertView = inflater.inflate(R.layout.grouprow, viewGroup, false);
                TextView textViewTitle = (TextView) convertView.findViewById(R.id.text_view_group_title);
                TextView textViewSubTitle = (TextView) convertView.findViewById(R.id.text_view_group_subtitle);
                textViewTitle.setText(getGroup(i).toString());
                textViewSubTitle.setText(i==0?
                        String.format(getString(R.string.text_view_amount),mAmountIOwe):
                        String.format(getString(R.string.text_view_amount),mAmountOwedToMe));
                return convertView;
            }

            @Override
            public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
                View convertView = inflater.inflate(R.layout.childrow, viewGroup, false);
                TextView textViewTitle = (TextView) convertView.findViewById(R.id.text_view_child_title);
                TextView textViewSubTitle = (TextView) convertView.findViewById(R.id.text_view_child_subtitle);

                FriendAndPrice friend = (FriendAndPrice)getChild(i, i1);
                if(friend != null){
                    if (friend.getFriend() != null) {
                        textViewTitle.setText(friend.getFriend().getName());
                        textViewSubTitle.setText(String.format(getString(R.string.text_view_amount),friend.getPrice()));
                    }else{
                        textViewTitle.setText("<empty>");
                        textViewSubTitle.setText("<empty>");
                    }
                }

                return convertView;
            }

            @Override
            public boolean isChildSelectable(int i, int i1) {
                return true;
            }

        }
    }
