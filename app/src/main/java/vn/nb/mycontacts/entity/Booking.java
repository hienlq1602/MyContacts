package vn.nb.mycontacts.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "booking", foreignKeys = @ForeignKey(entity = Contact.class, parentColumns = "contactId", childColumns = "contactId", onDelete = ForeignKey.CASCADE))
public class Booking implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    private int id;
    private int userId;

    private int contactId;
    private String title;
    private String content;
    private String place;

    private int bookingActive;
    private long bookingTime;

    public Booking(int userId, int contactId, String title, String content, String place, int bookingActive, long bookingTime) {

        this.userId = userId;
        this.contactId = contactId;
        this.title = title;
        this.content = content;
        this.place = place;
        this.bookingActive = bookingActive;
        this.bookingTime = bookingTime;
    }

    protected Booking(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        contactId = in.readInt();
        title = in.readString();
        content = in.readString();
        place = in.readString();
        bookingActive = in.readInt();
        bookingTime = in.readLong();
    }

    public static final Creator<Booking> CREATOR = new Creator<Booking>() {
        @Override
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        @Override
        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getContactId() {
        return contactId;
    }

    public void setContactId(int contactId) {
        this.contactId = contactId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getBookingActive() {
        return bookingActive;
    }

    public void setBookingActive(int bookingActive) {
        this.bookingActive = bookingActive;
    }

    public long getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(long bookingTime) {
        this.bookingTime = bookingTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeInt(contactId);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(place);
        dest.writeInt(bookingActive);
        dest.writeLong(bookingTime);
    }
}
