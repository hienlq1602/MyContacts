package vn.nb.mycontacts.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Relation;

public class BookingWithContact implements Parcelable {
    @Embedded
    public Booking booking;

    @Relation(parentColumn = "contactId", entityColumn = "contactId")
    public Contact contact;

    protected BookingWithContact(Parcel in) {
        booking = in.readParcelable(Booking.class.getClassLoader());
        contact = in.readParcelable(Contact.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(booking, flags);
        dest.writeParcelable(contact, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BookingWithContact> CREATOR = new Creator<BookingWithContact>() {
        @Override
        public BookingWithContact createFromParcel(Parcel in) {
            return new BookingWithContact(in);
        }

        @Override
        public BookingWithContact[] newArray(int size) {
            return new BookingWithContact[size];
        }
    };

    public BookingWithContact(Booking booking, Contact contact) {
        this.booking = booking;
        this.contact = contact;
    }
}
