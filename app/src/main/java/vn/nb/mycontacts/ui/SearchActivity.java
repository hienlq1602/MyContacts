package vn.nb.mycontacts.ui;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import vn.nb.mycontacts.R;
import vn.nb.mycontacts.adapter.ContactAdapter;
import vn.nb.mycontacts.database.DataManager;
import vn.nb.mycontacts.databinding.ActivitySearchBinding;
import vn.nb.mycontacts.entity.Contact;
import vn.nb.mycontacts.interfaces.OnContactListener;

public class SearchActivity extends AppCompatActivity {

    private ActivitySearchBinding binding;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataManager = new DataManager(this);
        initList();


        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s != null && s.length() > 0) {
                    contactAdapter.addItem(dataManager.searchContact(s.toString()));
                } else {
                    contactAdapter.addItem(dataManager.getAllContact());
                }
            }
        });

    }


    private ContactAdapter contactAdapter;

    private void initList() {
        contactAdapter = new ContactAdapter();
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setAdapter(contactAdapter);
        contactAdapter.addItem(dataManager.getAllContact());

        contactAdapter.setOnContactListener((contact, p) -> {
            Intent intent = new Intent(SearchActivity.this, ContactDetailsActivity.class);
            intent.putExtra("contact", contact);
            startForResult.launch(intent);
        });

    }

    private Intent intent = new Intent();
    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    contactAdapter.addItem(dataManager.getAllContact());
                    binding.edtSearch.setText("");
                    setResult(Activity.RESULT_OK, intent);
                }
            });
}