package vn.nb.mycontacts.ui;

import android.util.Patterns;

public class Constant {

    public static final long START_1 = 1704042002000L;
    public static final long END_1 = 1711904399000L;


    public static final long START_2 = 1711904402000L;
    public static final long END_2 = 1719766799000L;
    public static final long START_3 = 1719766802000L;
    public static final long END_3 = 1727715599000L;
    public static final long START_4 = 1727715602000L;
    public static final long END_4 = 1735664399000L;

    public static boolean isValidEmail(String email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
