package co.mobilemakers.expensesmanager;

/**
 * Created by user on 16/02/2015.
 */
public class EventDescriptionItem {

    private String eventTitle;
    private String friendPay;
    private String total;

    public EventDescriptionItem() {
    }

    public EventDescriptionItem(String eventTitle, String friendPay, String total) {
        this.eventTitle = eventTitle;
        this.friendPay = friendPay;
        this.total = total;
    }

    public String getEventTitle() {
        return eventTitle;
    }

    public void setEventTitle(String eventTitle) {
        this.eventTitle = eventTitle;
    }

    public String getFriendPay() {
        return friendPay;
    }

    public void setFriendPay(String friendPay) {
        this.friendPay = friendPay;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
