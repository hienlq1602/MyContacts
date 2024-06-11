package vn.nb.mycontacts.entity;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class ContactWithTotalBookings {
    @Embedded
    private Contact contact;

    @Relation(
            parentColumn = "contactId",
            entityColumn = "contactId"
    )
    private List<Booking> bookings;

    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }
}
