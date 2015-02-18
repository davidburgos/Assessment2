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

        } catch (SQLException e) {
            Log.i(LOG_TAG,"ERROR CREATING DATABASE",e);
            throw new RuntimeException(e);
        }
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


    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }
}
