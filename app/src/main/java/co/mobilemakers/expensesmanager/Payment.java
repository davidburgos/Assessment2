package co.mobilemakers.expensesmanager;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by diany_000 on 2/17/2015.
 */
public class Payment {

    public final static String ID ="ID";
    public final static String INVOICE= "INVOICE";
    public final static String FRIEND_ID = "FRIEND_ID";
    public final static String PRICE_TO_PAY = "PRICE_TO_PAY";
    public final static String IS_PAY = "IS_PAY";

    @DatabaseField(generatedId = true, columnName = ID)private int _id;
    @DatabaseField(columnName = FRIEND_ID)private int mFriendId;
    @DatabaseField(columnName = PRICE_TO_PAY)private int mPriceToPay;
    @DatabaseField(columnName = IS_PAY)private boolean mIsPay;
    @DatabaseField(columnName = INVOICE,
                    foreign = true,
                    foreignAutoRefresh = true,
                    foreignAutoCreate = true)private Invoice mInvoice;

    public int getInvoiceId() {
        return mInvoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        mInvoiceId = invoiceId;
    }

    public int getFriendId() {
        return mFriendId;
    }

    public void setFriendId(int friendId) {
        mFriendId = friendId;
    }

    public int getPriceToPay() {
        return mPriceToPay;
    }

    public void setPriceToPay(int priceToPay) {
        mPriceToPay = priceToPay;
    }

    public boolean isPay() {
        return mIsPay;
    }

    public void setPay(boolean isPay) {
        mIsPay = isPay;
    }
}
