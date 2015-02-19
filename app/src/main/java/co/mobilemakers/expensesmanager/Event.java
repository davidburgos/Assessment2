package co.mobilemakers.expensesmanager;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by diany_000 on 2/17/2015.
 */
public class Event {


    public final static String ID ="ID";
    public final static String NAME = "NAME";
    public final static String DESCRIPTION = "DESCRIPTION";
    public final static String FRIENDS_ID = "FRIENDS_ID";

    @DatabaseField(generatedId = true, columnName = ID)private int _id;
    @DatabaseField(columnName = NAME)private String mName;
    @DatabaseField(columnName = DESCRIPTION)private String mDescription;
    @ForeignCollectionField(columnName = FRIENDS_ID)private ForeignCollection<Friend> mFriends;

    public int getId() {
        return _id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public List<Friend> getFriends() {
        ArrayList<Friend> friendList = new ArrayList<Friend>();
        for(Friend friend: mFriends){
            friendList.add(friend);
        }
        return friendList;
    }

    public void setFriends(ForeignCollection<Friend> friends) {
        mFriends = friends;
    }
}
