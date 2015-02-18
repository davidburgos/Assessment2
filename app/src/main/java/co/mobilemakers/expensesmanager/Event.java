package co.mobilemakers.expensesmanager;

import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;

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
    @DatabaseField(columnName = FRIENDS_ID)private int[] mFriends;

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

    public int[] getFriends() {
        return mFriends;
    }

    public void setFriends(int[] friends) {
        mFriends = friends;
    }
}
