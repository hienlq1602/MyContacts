package vn.nb.mycontacts.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "report", foreignKeys = @ForeignKey(entity = Contact.class, parentColumns = "contactId", childColumns = "contactId", onDelete = ForeignKey.CASCADE))
public class Report {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "reportId")
    private int id;

    private int userId;
    private int contactId;

    private String description;
    private long timeStamp;

    private int type;

    public Report(int userId, int contactId, String description, long timeStamp, int type) {

        this.userId = userId;
        this.contactId = contactId;
        this.description = description;
        this.timeStamp = timeStamp;
        this.type = type;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
