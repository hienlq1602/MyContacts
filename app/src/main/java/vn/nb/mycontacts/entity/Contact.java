package vn.nb.mycontacts.entity;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contact")
public class Contact implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "contactId")
    private int id;

    private int userId;
    private String nickName;
    private String phoneNumber;
    private String company;
    private String email;

    private String notes;


    private int active;
    private int numberOfCommunication;

    private long timing;


    public Contact( int userId, String nickName, String phoneNumber, String company, String email, String notes, int active, int numberOfCommunication, long timing) {

        this.userId = userId;
        this.nickName = nickName;
        this.phoneNumber = phoneNumber;
        this.company = company;
        this.email = email;

        this.notes = notes;
        this.active = active;
        this.numberOfCommunication = numberOfCommunication;
        this.timing = timing;
    }

    protected Contact(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        nickName = in.readString();
        phoneNumber = in.readString();
        company = in.readString();
        email = in.readString();

        notes = in.readString();
        active = in.readInt();
        numberOfCommunication = in.readInt();
        timing = in.readLong();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeString(nickName);
        dest.writeString(phoneNumber);
        dest.writeString(company);
        dest.writeString(email);

        dest.writeString(notes);
        dest.writeInt(active);
        dest.writeInt(numberOfCommunication);
        dest.writeLong(timing);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel in) {
            return new Contact(in);
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public int getNumberOfCommunication() {
        return numberOfCommunication;
    }

    public void setNumberOfCommunication(int numberOfCommunication) {
        this.numberOfCommunication = numberOfCommunication;
    }

    public long getTiming() {
        return timing;
    }

    public void setTiming(long timing) {
        this.timing = timing;
    }
}
