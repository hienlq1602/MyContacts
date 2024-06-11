package vn.nb.mycontacts.entity;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user", indices = {@Index(value = {"phoneNumber"}, unique = true)})
public class UserInfo {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String phoneNumber;
    private String password;
    private String email;

    public UserInfo( String name, String phoneNumber, String password, String email) {

        this.name = name;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
