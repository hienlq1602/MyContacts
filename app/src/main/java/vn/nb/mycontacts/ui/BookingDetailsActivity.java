package vn.nb.mycontacts.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

import vn.nb.mycontacts.R;
import vn.nb.mycontacts.UserManager;
import vn.nb.mycontacts.database.DataManager;
import vn.nb.mycontacts.databinding.ActivityBookingDetailsBinding;
import vn.nb.mycontacts.entity.Booking;
import vn.nb.mycontacts.entity.BookingWithContact;
import vn.nb.mycontacts.entity.Contact;
import vn.nb.mycontacts.entity.Report;

public class BookingDetailsActivity extends AppCompatActivity {
    private ActivityBookingDetailsBinding binding;
    private Booking booking;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataManager = new DataManager(this);
        booking = getIntent().getParcelableExtra("booking");
        contact = getIntent().getParcelableExtra("contact");
        if (booking == null || contact == null) {
            return;
        }
        loadUI();
        binding.call.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(BookingDetailsActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(BookingDetailsActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                callPhoneNumber();
            }
        });
        binding.email.setOnClickListener(v -> showSendEmail());

        binding.btnBack.setOnClickListener(v -> finish());

        binding.btnSave.setOnClickListener(v -> save());

        binding.tvDate.setOnClickListener(v -> showDateTimePickerDialog(binding.tvDate));
    }

    private void save() {
        String title = binding.tvTitle.getText().toString();
        String content = binding.tvContent.getText().toString();
        String place = binding.tvPlace.getText().toString();

        if (title.isEmpty() || content.isEmpty() || place.isEmpty()) {
            Toast.makeText(this, "Information cannot be left blank", Toast.LENGTH_SHORT).show();
            return;
        }
        booking.setContent(content);
        booking.setTitle(title);
        booking.setPlace(place);
        dataManager.updateBooking(booking);
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        finish();



    }


    @SuppressLint("SetTextI18n")
    private void showSendEmail() {
        BottomSheetDialog dialog = new BottomSheetDialog(BookingDetailsActivity.this);
        dialog.setContentView(R.layout.layout_bottom_email);
        dialog.setCancelable(true);
        dialog.show();

        TextView tvEmail = dialog.findViewById(R.id.tvEmail);
        EditText edtContent = dialog.findViewById(R.id.edtContent);
        EditText edtTitle = dialog.findViewById(R.id.edtTitle);
        View btnSend = dialog.findViewById(R.id.send);
        View btnCancel = dialog.findViewById(R.id.cancel);

        Objects.requireNonNull(tvEmail).setText("To: " + contact.getEmail());


        Objects.requireNonNull(btnSend).setOnClickListener(v -> {
            String content = Objects.requireNonNull(edtContent).getText().toString().trim();
            String title = Objects.requireNonNull(edtTitle).getText().toString().trim();
            if (!content.isEmpty() || !title.isEmpty()) {
                sendEmail(title, content);
            }
            dialog.dismiss();
        });
        Objects.requireNonNull(btnCancel).setOnClickListener(v -> {

            dialog.dismiss();
        });
    }

    private void sendEmail(String title, String body) {
        dataManager.insertReport(new Report(
                UserManager.getInstance().getPerson().getId(),
                contact.getId(),
                "SendMail",
                System.currentTimeMillis(),
                1
        ), 1);
        Intent mailIntent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:" + contact.getEmail() + "?subject=" + title + "&body=" + body);
        mailIntent.setData(data);
        startActivity(Intent.createChooser(mailIntent, "Send Email"));
    }


    private void loadUI() {
        binding.tvContent.setText(booking.getContent());
        binding.tvTitle.setText(booking.getTitle());
        binding.tvPlace.setText(booking.getPlace());
        binding.tvIcon.setText(String.valueOf(contact.getNickName().charAt(0)));
        binding.tvName.setText(contact.getNickName());


        binding.tvDate.setText(dateFormat.format(booking.getBookingTime()));
    }

    private static final int REQUEST_PHONE_CALL = 1;
    private DataManager dataManager;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PHONE_CALL) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callPhoneNumber();
            } else {
                Toast.makeText(this, "Phone call permission is not granted", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void callPhoneNumber() {
        dataManager.insertReport(new Report(
                UserManager.getInstance().getPerson().getId(),
                contact.getId(),
                "Call",
                System.currentTimeMillis(),
                0
        ), 1);
        String phoneNumber = contact.getPhoneNumber();
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }


    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);

    private void showDateTimePickerDialog(TextView v) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    final int selectedYear = year1;
                    final int selectedMonth = monthOfYear;
                    final int selectedDay = dayOfMonth;

                    TimePickerDialog timePickerDialog = new TimePickerDialog(BookingDetailsActivity.this,
                            (view1, hourOfDay, minute1) -> {

                                Calendar selectedDateTime = Calendar.getInstance();
                                selectedDateTime.set(selectedYear, selectedMonth, selectedDay, hourOfDay, minute1);
                                booking.setBookingTime(selectedDateTime.getTimeInMillis());
                                v.setText(dateFormat.format(selectedDateTime.getTime()));
                            }, hour, minute, true);
                    timePickerDialog.show();
                }, year, month, day);
        datePickerDialog.show();
    }

}