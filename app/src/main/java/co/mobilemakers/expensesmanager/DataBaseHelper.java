package co.mobilemakers.expensesmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by user on 17/02/2015.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {

    private final static String DATABASE_NAME = "expenses_manager.db";
    private final static int DATABASE_VERSION = 1;
    private final static String LOG_TAG = DataBaseHelper.class.getSimpleName();

    // the DAO object we use to access the SimpleData table
    private Dao<Event, Integer> eventDao = null;
    private Dao<Friend, Integer> friendDao = null;
    private Dao<EventFriend,Integer> eventFriendDao= null;
    private Dao<Payment, Integer> paymentDao = null;
    private Dao<Invoice,Integer> invoiceDao= null;

    public DataBaseHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(LOG_TAG, "CREATING DATABASE.");
            TableUtils.createTable(connectionSource, Event.class);
            TableUtils.createTable(connectionSource, Friend.class);
            TableUtils.createTable(connectionSource, EventFriend.class);
            TableUtils.createTable(connectionSource, Payment.class);
            TableUtils.createTable(connectionSource, Payment.class);
            TableUtils.createTable(connectionSource, Invoice.class);
        } catch (SQLException e) {
            Log.i(LOG_TAG,"ERROR CREATING DATABASE",e);
            throw new RuntimeException(e);
        }
    }

    public Dao<Payment, Integer> getPaymentDao(){
        if(null == paymentDao){
            try {
                paymentDao = getDao(Payment.class);
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return paymentDao;
    }

    public Dao<Friend, Integer> getFriendDao() {
        if (null == friendDao) {
            try {
                friendDao = getDao(Friend.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return friendDao;
    }

    public Dao<Event, Integer> getEventDao() {
        if (null == eventDao) {
            try {
                eventDao = getDao(Event.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eventDao;
    }

    public Dao<EventFriend, Integer> getEventFriendDao() {
        if (null == eventFriendDao) {
            try {
                eventFriendDao = getDao(EventFriend.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eventFriendDao;
    }

    public Dao<Invoice, Integer> getInvoiceDao() {
        if (null == invoiceDao) {
            try {
                invoiceDao = getDao(Invoice.class);
            }catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return invoiceDao;
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
