package vn.nb.mycontacts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import vn.nb.mycontacts.R;
import vn.nb.mycontacts.UserManager;
import vn.nb.mycontacts.database.DataManager;
import vn.nb.mycontacts.databinding.ActivityInsertBinding;
import vn.nb.mycontacts.entity.Contact;

public class InsertActivity extends AppCompatActivity {

    private ActivityInsertBinding binding;
    private DataManager dataManager;

    private boolean isUpdate;
    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInsertBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataManager = new DataManager(this);

        String key = getIntent().getStringExtra("key");
        if (key == null) {
            return;
        }
        if (key.equals("insert")) {
            isUpdate = false;
        } else {
            isUpdate = true;
            contact = getIntent().getParcelableExtra("contact");
            loadUI();
        }

        binding.btnSave.setOnClickListener(v -> save());
        binding.btnBack.setOnClickListener(v -> finish());

    }

    private void loadUI() {
        binding.edtName.setText(contact.getNickName());
        binding.edtPhoneNumber.setText(contact.getPhoneNumber());
        binding.edtCompany.setText(contact.getCompany());
        binding.edtEmail.setText(contact.getEmail());
        binding.edtNotes.setText(contact.getNotes());
    }

    private void save() {
        String name = binding.edtName.getText().toString().trim();
        String phone = binding.edtPhoneNumber.getText().toString().trim();
        String company = binding.edtCompany.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();
        String notes = binding.edtNotes.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || company.isEmpty() || email.isEmpty() ) {
            Toast.makeText(this, "Please fill in all information before saving", Toast.LENGTH_SHORT).show();
        } else {
            if (isUpdate) {
                contact.setNickName(name);
                contact.setPhoneNumber(phone);
                contact.setCompany(company);
                contact.setEmail(email);
                contact.setNotes(notes);
                dataManager.updateContact(contact);
            } else {

                dataManager.insertContact(new Contact(UserManager.getInstance().getPerson().getId(), name, phone, company, email, notes, 0, 0, System.currentTimeMillis()));

            }
            Intent intent = new Intent();
            intent.putExtra("contact", contact);
            setResult(Activity.RESULT_OK, intent);
            finish();

        }

    }
}