package vn.nb.mycontacts.interfaces;

import vn.nb.mycontacts.entity.Booking;
import vn.nb.mycontacts.entity.BookingWithContact;

public interface OnBookingListener {
    void onItemClick(BookingWithContact bookingWithContact,int p);
    void onChecked(BookingWithContact bookingWithContact,int p,boolean checked);
}
