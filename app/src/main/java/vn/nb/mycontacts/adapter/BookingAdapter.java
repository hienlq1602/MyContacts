package vn.nb.mycontacts.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.nb.mycontacts.R;
import vn.nb.mycontacts.entity.BookingWithContact;
import vn.nb.mycontacts.interfaces.OnBookingListener;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.VH> {
    private List<BookingWithContact> bookingWithContactList = new ArrayList<>();

    private OnBookingListener onBookingListener;

    public void setOnBookingListener(OnBookingListener onBookingListener) {
        this.onBookingListener = onBookingListener;
    }

    @NonNull
    @Override
    public BookingAdapter.VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_booking, parent, false));
    }

    private DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);


    @Override
    public void onBindViewHolder(@NonNull BookingAdapter.VH holder, int position) {
        BookingWithContact bookingWithContact = bookingWithContactList.get(holder.getAdapterPosition());
        holder.tvTitle.setText(bookingWithContact.booking.getTitle());
        holder.tvContent.setText(bookingWithContact.booking.getContent());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(bookingWithContact.booking.getBookingTime());
        holder.tvDate.setText(dateFormat.format(calendar.getTime()));

        if (bookingWithContact.booking.getBookingTime() > System.currentTimeMillis()) {
            holder.switchCheck.setChecked(bookingWithContact.booking.getBookingActive() == 1);
            holder.switchCheck.setEnabled(true);
        } else {
            holder.switchCheck.setChecked(false);
            holder.switchCheck.setEnabled(false);
        }


        holder.switchCheck.setOnCheckedChangeListener((buttonView, isChecked) -> {
            onBookingListener.onChecked(bookingWithContact, holder.getAdapterPosition(), isChecked);

        });
        holder.itemView.setOnClickListener(v -> onBookingListener.onItemClick(bookingWithContact, holder.getAdapterPosition()));


    }

    @Override
    public int getItemCount() {
        return bookingWithContactList.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void addItem(List<BookingWithContact> booking) {
        this.bookingWithContactList = booking;
        notifyDataSetChanged();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateItem(BookingWithContact bookingWithContact, int p) {
        this.bookingWithContactList.set(p, bookingWithContact);
    }

    static class VH extends RecyclerView.ViewHolder {

        private TextView tvTitle, tvContent, tvDate;
        private SwitchCompat switchCheck;

        public VH(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvContent = itemView.findViewById(R.id.tvContent);
            tvDate = itemView.findViewById(R.id.tvDate);
            switchCheck = itemView.findViewById(R.id.switchCheck);
        }
    }
}
