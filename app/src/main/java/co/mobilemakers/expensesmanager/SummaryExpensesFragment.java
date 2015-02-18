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

public class SummaryExpensesFragment extends Fragment {
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
        private String[] groups = {getString(R.string.text_view_title_you_owe), getString(R.string.text_view_title_owed_to_you)};

        private String[][] children = {
                {"Arnold", "Barry", "Chuck", "David"},
                {"Ace", "Bandit", "Cha-Cha", "Deuce"}
        };

        public SavedTabsListAdapter() {
            inflater = LayoutInflater.from(mContext);
        }

        @Override
        public int getGroupCount() {
            return groups.length;
        }

        @Override
        public int getChildrenCount(int i) {
            return children[i].length;
        }

        @Override
        public Object getGroup(int i) {
            return groups[i];
        }

        @Override
        public Object getChild(int i, int i1) {
            return children[i][i1];
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
            textViewSubTitle.setText("$0");//TODO: don't forget calculate this
            return convertView;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            TextView textView = new TextView(SummaryExpensesFragment.this.getActivity());
            textView.setText(getChild(i, i1).toString());
            return textView;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return true;
        }

    }
}
