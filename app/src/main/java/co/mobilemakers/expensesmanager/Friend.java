package co.mobilemakers.expensesmanager;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by diany_000 on 2/17/2015.
 */
public class Friend {

    public final static String ID ="ID";
    public final static String NAME = "NAME";
    public final static String EMAIL = "EMAIL";
    public final static String PASSWORD = "PASSWORD";

    @DatabaseField(generatedId = true, columnName = ID)private int _id;
    @DatabaseField(columnName = NAME)private String mName;
    @DatabaseField(columnName = EMAIL)private String mEmail;
    @DatabaseField(columnName = PASSWORD)private String mPassword;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }
}
