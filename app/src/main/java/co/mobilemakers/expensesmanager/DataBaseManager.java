package co.mobilemakers.expensesmanager;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
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


    public Friend getFriendById(String username){

        Friend friend = null;
        try {
            Dao<Friend, Integer> dao = getHelper().getFriendDao();

            if (dao != null){

                QueryBuilder<Friend, Integer> queryBuilder = dao.queryBuilder();
                queryBuilder.where().eq(Friend.EMAIL, username);
                PreparedQuery<Friend> preparedQuery = queryBuilder.prepare();

                List<Friend> friendList = dao.query(preparedQuery);

                if(!friendList.isEmpty()){
                    friend = friendList.get(friendList.size()-1);
                }
            }

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

    public void  addPayment(Payment p){
        try {
            getHelper().getPaymentDao().create(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Invoice> getInvoicesByEventId(int eventId){
        List<Invoice> invoices = new ArrayList<>();
        try{
            Dao<Invoice, Integer> dao = getHelper().getInvoiceDao();

            if (dao != null){
                QueryBuilder<Invoice, Integer> queryBuilder = dao.queryBuilder();
                queryBuilder.where().eq(Invoice.EVENT_ID, eventId);
                PreparedQuery<Invoice> preparedQuery = queryBuilder.prepare();
                invoices= dao.query(preparedQuery);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return invoices;
    }

    public ForeignCollection<Payment> getEmptyForeignCollection()
    {
        ForeignCollection<Payment> payments = null;
        try {
            payments = getHelper().getInvoiceDao().getEmptyForeignCollection("PAYMENTS");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return payments;
    }

    public List<Friend> getFriendsByEventId(int eventId)
    {
        List<Friend> friendList = new ArrayList<>();
        Dao<EventFriend, Integer> dao = getHelper().getEventFriendDao();
        Dao<Friend, Integer> dao2 = getHelper().getFriendDao();

        try {
            if (dao != null){
                QueryBuilder<EventFriend, Integer> queryBuilder = dao.queryBuilder();
                queryBuilder.where().eq(EventFriend.EVENT, eventId);
                PreparedQuery<EventFriend> preparedQuery = queryBuilder.prepare();
                List<EventFriend> eventFriendList = dao.query(preparedQuery);
                if(!eventFriendList.isEmpty()){
                    for(EventFriend ev : eventFriendList) {
                        friendList.add(dao2.queryForId(ev.getId()));
                    }
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return  friendList;
    }

}
