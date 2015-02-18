package co.mobilemakers.expensesmanager;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by user on 18/02/2015.
 */
public class EventFriend {

    public final static String ID ="ID";
    public final static String FRIEND ="FRIEND";
    public final static String EVENT = "EVENT";

    @DatabaseField(generatedId = true,columnName = ID)
    private int _id;

    @DatabaseField(foreign = true,columnName = FRIEND)
    private Friend mFriend;

    @DatabaseField(foreign = true,columnName = EVENT)
    private Event mEevent;

   public EventFriend(){}

   public EventFriend(Event event,Friend friend){
        this.mEevent =event;
        this.mFriend = friend;
   }



}
