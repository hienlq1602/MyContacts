package vn.nb.mycontacts.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import vn.nb.mycontacts.fragment.CalendarFragment;
import vn.nb.mycontacts.fragment.ContactsFragment;
import vn.nb.mycontacts.fragment.UserFragment;
import vn.nb.mycontacts.fragment.ReportFragment;

public class PagerAdapter extends FragmentStateAdapter {
    public PagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fm;
        switch (position) {

            case 0:
                fm = ContactsFragment.newInstance();
                break;
            case 1:
                fm = CalendarFragment.newInstance();
                break;
            case 2:
                fm = ReportFragment.newInstance();
                break;
            case 3:
                fm = UserFragment.newInstance();
                break;
            default:
                fm = new Fragment();
                break;
        }
        return fm;
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
