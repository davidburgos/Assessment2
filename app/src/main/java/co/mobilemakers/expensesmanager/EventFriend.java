package co.mobilemakers.expensesmanager;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by user on 18/02/2015.
 */
public class EventFriend {

    public final static String ID ="ID";
    public final static String FRIEND ="FRIEND";
    public final static String EVENT = "EVENT";

    public int getId() {
        return _id;
    }

    @DatabaseField(generatedId = true,columnName = ID)
    private int _id;

    public Friend getmFriend() {
        return mFriend;
    }

    @DatabaseField(foreign = true,foreignAutoRefresh = true,columnName = FRIEND)
    private Friend mFriend;

    @DatabaseField(foreign = true,foreignAutoRefresh = true,columnName = EVENT)
    private Event mEevent;

    public EventFriend(){}

    public EventFriend(Event event,Friend friend){
        this.mEevent =event;
        this.mFriend = friend;
    }



}
