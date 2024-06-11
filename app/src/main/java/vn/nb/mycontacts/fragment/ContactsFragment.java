package vn.nb.mycontacts.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Objects;

import vn.nb.mycontacts.MainActivity;
import vn.nb.mycontacts.R;
import vn.nb.mycontacts.UserManager;
import vn.nb.mycontacts.adapter.ContactAdapter;
import vn.nb.mycontacts.database.DataManager;
import vn.nb.mycontacts.databinding.FragmentContactsBinding;
import vn.nb.mycontacts.ui.ContactDetailsActivity;
import vn.nb.mycontacts.ui.InsertActivity;
import vn.nb.mycontacts.ui.SearchActivity;

public class ContactsFragment extends Fragment {

    private FragmentContactsBinding binding;


    public static ContactsFragment newInstance() {

        return new ContactsFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentContactsBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataManager = new DataManager(requireActivity());

        initList();
        loadUI();

        binding.btnCreate.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), InsertActivity.class);
            intent.putExtra("key", "insert");
            startForResult.launch(intent);
        });
        binding.search.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), SearchActivity.class);
            startForResult.launch(intent);
        });

        binding.btnFilter.setOnClickListener(v -> showMenu());


    }


    private void showMenu() {
        BottomSheetDialog dialog = new BottomSheetDialog(requireActivity());
        dialog.setContentView(R.layout.layout_bottom_menu);
        dialog.setCancelable(true);
        dialog.show();

        View btnAz = dialog.findViewById(R.id.tvAZ);
        View btnZa = dialog.findViewById(R.id.tvZA);
        View btnBlock = dialog.findViewById(R.id.tvBlock);
        View tvFavourite = dialog.findViewById(R.id.tvFavourite);
        View btnCancel = dialog.findViewById(R.id.cancel);

        Objects.requireNonNull(btnAz).setOnClickListener(v -> {
            contactAdapter.addItem(dataManager.getContactBySort(0));
            dialog.dismiss();

        });
        Objects.requireNonNull(btnZa).setOnClickListener(v -> {
            contactAdapter.addItem(dataManager.getContactBySort(1));
            dialog.dismiss();
        });
        Objects.requireNonNull(btnBlock).setOnClickListener(v -> {
            contactAdapter.addItem(dataManager.getContactBySort(2));
            dialog.dismiss();
        });
        Objects.requireNonNull(tvFavourite).setOnClickListener(v -> {
            contactAdapter.addItem(dataManager.getContactBySort(3));
            dialog.dismiss();
        });

        Objects.requireNonNull(btnCancel).setOnClickListener(v -> dialog.dismiss());
    }

    private void loadUI() {
        binding.tvName.setText(UserManager.getInstance().getPerson().getName());
        binding.tvPhone.setText(UserManager.getInstance().getPerson().getPhoneNumber());
        binding.tvIcon.setText(String.valueOf(UserManager.getInstance().getPerson().getName().charAt(0)));
    }


    private ContactAdapter contactAdapter;
    private DataManager dataManager;

    private void initList() {
        contactAdapter = new ContactAdapter();
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false));
        binding.recyclerview.setHasFixedSize(true);
        binding.recyclerview.setAdapter(contactAdapter);
        contactAdapter.addItem(dataManager.getAllContact());
        contactAdapter.setOnContactListener((contact, p) -> {
            Intent intent = new Intent(requireActivity(), ContactDetailsActivity.class);
            intent.putExtra("contact", contact);
            startForResult.launch(intent);
        });

    }

    private final ActivityResultLauncher<Intent> startForResult = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    contactAdapter.addItem(dataManager.getAllContact());
                    if (result.getData() != null){
                        int key = result.getData().getIntExtra("key",0);
                        if (key == 1){
                            MainActivity mainActivity = (MainActivity) getActivity();
                            if (mainActivity != null){
                                mainActivity.updateTab();
                            }
                        }
                    }
                }
            });
}