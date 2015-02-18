package co.mobilemakers.expensesmanager;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by diany_000 on 2/17/2015.
 */
public class Invoice {

    public final static String ID ="ID";
    public final static String NAME = "NAME";
    public final static String PRICE = "PRICE";
    public final static String EVENT_ID = "EVENT_ID";
    public final static String FRIEND_ID = "FRIENDS_ID";

    @DatabaseField(generatedId = true, columnName = ID)private int _id;
    @DatabaseField(columnName = NAME)private String mName;
    @DatabaseField(columnName = PRICE)private int mPrice;
    @DatabaseField(columnName = EVENT_ID)private int mEventId;
    @DatabaseField(columnName = FRIEND_ID)private int mFriendId;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getPrice() {
        return mPrice;
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public int getEventId() {
        return mEventId;
    }

    public void setEventId(int eventId) {
        mEventId = eventId;
    }

    public int getFriendId() {
        return mFriendId;
    }

    public void setFriendId(int friendId) {
        mFriendId = friendId;
    }
}
