package vn.nb.mycontacts.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.nb.mycontacts.R;
import vn.nb.mycontacts.UserManager;
import vn.nb.mycontacts.databinding.FragmentUserBinding;
import vn.nb.mycontacts.ui.LoginActivity;


public class UserFragment extends Fragment {

    private FragmentUserBinding binding;

    public static UserFragment newInstance() {

        return new UserFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.tvEmail.setText(UserManager.getInstance().getPerson().getEmail());
        binding.tvName.setText(UserManager.getInstance().getPerson().getName());
        binding.tvPhone.setText(UserManager.getInstance().getPerson().getPhoneNumber());
        binding.tvIcon.setText(String.valueOf(UserManager.getInstance().getPerson().getName().charAt(0)));

        binding.btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            requireActivity().startActivity(intent);
        });

    }
}