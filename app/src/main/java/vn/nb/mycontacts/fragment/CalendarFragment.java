package vn.nb.mycontacts.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.nb.mycontacts.R;
import vn.nb.mycontacts.adapter.BookingAdapter;
import vn.nb.mycontacts.database.DataManager;
import vn.nb.mycontacts.databinding.FragmentCalendarBinding;
import vn.nb.mycontacts.entity.BookingWithContact;
import vn.nb.mycontacts.interfaces.OnBookingListener;
import vn.nb.mycontacts.ui.BookingDetailsActivity;

public class CalendarFragment extends Fragment {

    private FragmentCalendarBinding binding;

    private DataManager dataManager;

    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCalendarBinding.inflate(getLayoutInflater());
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataManager = new DataManager(requireActivity());
        initList();
    }

    BookingAdapter bookingAdapter = new BookingAdapter();

    private void initList() {

        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setAdapter(bookingAdapter);
        bookingAdapter.addItem(dataManager.getBooking());

        bookingAdapter.setOnBookingListener(new OnBookingListener() {
            @Override
            public void onItemClick(BookingWithContact bookingWithContact, int p) {
                Intent intent = new Intent(requireActivity(), BookingDetailsActivity.class);
                intent.putExtra("booking", bookingWithContact.booking);
                intent.putExtra("contact", bookingWithContact.contact);
                requireActivity().startActivity(intent);
            }

            @Override
            public void onChecked(BookingWithContact bookingWithContact, int p, boolean checked) {

                if (checked) {
                    bookingWithContact.booking.setBookingActive(1);
                } else {
                    bookingWithContact.booking.setBookingActive(0);
                }

                dataManager.updateBooking(bookingWithContact.booking);
                bookingAdapter.updateItem(bookingWithContact, p);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        bookingAdapter.addItem(dataManager.getBooking());
    }
}