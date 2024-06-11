package vn.nb.mycontacts.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import vn.nb.mycontacts.entity.UserInfo;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(UserInfo userInfo);

    @Query("SELECT * FROM user WHERE phoneNumber =:tk AND password =:pass")
    UserInfo getUser(String tk,String pass);

    @Query("SELECT * FROM user WHERE phoneNumber =:tk")
    UserInfo getUser(String tk);
}