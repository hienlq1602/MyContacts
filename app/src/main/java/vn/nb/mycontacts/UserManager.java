package vn.nb.mycontacts;

import android.content.Context;

import vn.nb.mycontacts.database.DataManager;
import vn.nb.mycontacts.entity.Contact;
import vn.nb.mycontacts.entity.UserInfo;

public class UserManager {
    private static UserManager instance;
    private UserInfo userInfo;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    public UserInfo getPerson() {
        return userInfo;
    }

    public void loadUser(UserInfo info) {
        userInfo = info;
    }

}
