package vn.nb.mycontacts.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.DateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import vn.nb.mycontacts.MainActivity;
import vn.nb.mycontacts.R;
import vn.nb.mycontacts.UserManager;
import vn.nb.mycontacts.database.DataManager;
import vn.nb.mycontacts.databinding.ActivityContactDetailsBinding;
import vn.nb.mycontacts.entity.Booking;
import vn.nb.mycontacts.entity.Contact;
import vn.nb.mycontacts.entity.Report;

public class ContactDetailsActivity extends AppCompatActivity {

    private ActivityContactDetailsBinding binding;
    private Contact contact;

    private DataManager dataManager;
    private static final int REQUEST_PHONE_CALL = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        dataManager = new DataManager(this);
        contact = getIntent().getParcelableExtra("contact");

        if (contact == null) {
            return;
        }

        loadUI();


        binding.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(ContactDetailsActivity.this, InsertActivity.class);
            intent.putExtra("contact", contact);
            intent.putExtra("key", "update");
            startForResult.launch(intent);
        });


        binding.call.setOnClickListener(v -> {
            if (contact.getActive() == 2){
                return;
            }

            if (ContextCompat.checkSelfPermission(ContactDetailsActivity.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(ContactDetailsActivity.this, new String[]{android.Manifest.permission.CALL_PHONE}, REQUEST_PHONE_CALL);
            } else {
                callPhoneNumber();
            }
        });

        binding.email.setOnClickListener(v -> showSendEmail());
        binding.btnBack.setOnClickListener(v -> finish());
        binding.booking.setOnClickListener(v -> showBooking());

        binding.btnFavourite.setOnClickListener(v -> {
            if (contact.getActive() == 2) {
                Toast.makeText(ContactDetailsActivity.this, "Contact has been locked", Toast.LENGTH_SHORT).show();
                return;
            }
            if (contact.getActive() == 1) {
                binding.btnFavourite.setImageResource(R.drawable.heart);
                contact.setActive(0);
            } else {
                binding.btnFavourite.setImageResource(R.drawable.heart_2);
                contact.setActive(1);
            }
            dataManager.updateContact(contact);
            setResult(Activity.RESULT_OK, intent);
        });

        binding.btnBlock.setOnClickListener(v -> {
            if (contact.getActive() == 2) {
                binding.btnBlock.setText("Block");
                contact.setActive(0);
            } else {
                binding.btnBlock.setText("UnBlock");
                contact.setActive(2);

            }
            dataManager.updateContact(contact);
            setResult(Activity.RESULT_OK, intent);
        });

    }

    @SuppressLint("SetTextI18n")
    private void showSendEmail() {
        if (contact.getActive() == 2){
            return;
        }
        BottomSheetDialog dialog = new BottomSheetDialog(ContactDetailsActivity.this);
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

    @SuppressLint("SetTextI18n")
    private void showBooking() {
        if (contact.getActive() == 2){
            return;
        }
        BottomSheetDialog dialog = new BottomSheetDialog(ContactDetailsActivity.this);
        dialog.setContentView(R.layout.layout_bottom_booking);
        dialog.setCancelable(true);
        dialog.show();


        EditText edtContent = dialog.findViewById(R.id.edtContent);
        EditText edtPlace = dialog.findViewById(R.id.edtPlace);
        EditText edtTitle = dialog.findViewById(R.id.edtTitle);
        TextView date = dialog.findViewById(R.id.tvDate);
        View btnSend = dialog.findViewById(R.id.send);
        View btnCancel = dialog.findViewById(R.id.cancel);
        Calendar calendar = Calendar.getInstance();
        timeInMillis = calendar.getTimeInMillis();
        Objects.requireNonNull(date).setText(dateFormat.format(calendar.getTime()));

        date.setOnClickListener(v -> showDateTimePickerDialog(date));

        Objects.requireNonNull(btnSend).setOnClickListener(v -> {
            String content = Objects.requireNonNull(edtContent).getText().toString().trim();
            String title = Objects.requireNonNull(edtTitle).getText().toString().trim();
            String place = Objects.requireNonNull(edtPlace).getText().toString().trim();
            if (content.isEmpty() || title.isEmpty() || place.isEmpty()) {
                Toast.makeText(this, "Please fill in all information", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            } else {
                Booking booking = new Booking(UserManager.getInstance().getPerson().getId(),
                        contact.getId(),
                        title,
                        content,
                        place,
                        1,
                        timeInMillis);

                dataManager.insertBooking(booking);

                Report report = new Report(UserManager.getInstance().getPerson().getId(), contact.getId(), "Booking",
                        System.currentTimeMillis(),
                        2);

                dataManager.insertReport(report, 1);

                dialog.dismiss();
                intent.putExtra("key", 1);
                setResult(Activity.RESULT_OK, intent);
                finish();

            }


        });
        Objects.requireNonNull(btnCancel).setOnClickListener(v -> {

            dialog.dismiss();
        });
    }

    DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);
    private Intent intent = new Intent();
    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            if (result.getData() != null) {
                Contact tmp = result.getData().getParcelableExtra("contact");
                if (tmp != null) {
                    contact = tmp;
                    setResult(Activity.RESULT_OK, intent);
                    loadUI();
                }
            }


        }
    });

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

    private void loadUI() {
        binding.tvName.setText(contact.getNickName());
        binding.tvPhone.setText(contact.getPhoneNumber());
        binding.tvCompany.setText(contact.getCompany());
        binding.tvNotes.setText(contact.getNotes());
        binding.tvIcon.setText(String.valueOf(contact.getNickName().charAt(0)));

        if (contact.getActive() == 1) {
            binding.btnFavourite.setImageResource(R.drawable.heart_2);
        } else {
            binding.btnFavourite.setImageResource(R.drawable.heart);
        }

        if (contact.getActive() == 2) {
            binding.btnBlock.setText("UnBlock");
        } else {
            binding.btnBlock.setText("Block");

        }
    }

    private long timeInMillis = 0;

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
                    TimePickerDialog timePickerDialog = new TimePickerDialog(ContactDetailsActivity.this,
                            (view1, hourOfDay, minute1) -> {
                                Calendar selectedDateTime = Calendar.getInstance();
                                selectedDateTime.set(selectedYear, selectedMonth, selectedDay, hourOfDay, minute1);
                                timeInMillis = selectedDateTime.getTimeInMillis();
                                v.setText(dateFormat.format(selectedDateTime.getTime()));
                                Log.d("__hahaha", "showDateTimePickerDialog: " + hourOfDay + " " + minute1);
                            }, hour, minute, true);
                    timePickerDialog.show();
                }, year, month, day);
        datePickerDialog.show();
    }
}