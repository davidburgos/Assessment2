package co.mobilemakers.expensesmanager;

import android.content.Context;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by user on 17/02/2015.
 */
public class DataBaseManager {

    static private DataBaseManager instance;
    static public  void init (Context context){
        if( instance == null)
        {
            instance = new DataBaseManager(context);
        }
    }

    static public DataBaseManager getInstance(){
        return  instance;
    }

    private DataBaseHelper helper;
    private DataBaseManager(Context context){
        helper = new DataBaseHelper(context);
    }

    private DataBaseHelper getHelper(){
        return helper;
    }

    public Friend getFriendById(int id)
    {
        Friend friend = null;
        try {
            friend = getHelper().getFriendDao().queryForId(id);
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return  friend;
    }

    public List<Friend> getAllFriends()
    {
        List<Friend> friendList = null;
        try {
            friendList = getHelper().getFriendDao().queryForAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return  friendList;
    }

    public void addFriend(Friend f) {
        try {
            getHelper().getFriendDao().create(f);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Event> getAllEvents()
    {
        List<Event> eventList = null;
        try {
            eventList = getHelper().getEventDao().queryForAll();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return  eventList;
    }

    public void  addEvent(Event e){
        try {
            getHelper().getEventDao().create(e);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }


    public void  addEventFriend(EventFriend e){
        try {
            getHelper().getEventFriendDao().create(e);
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

    public void  addInvoice(Invoice i){
        try {
            getHelper().getInvoiceDao().create(i);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
