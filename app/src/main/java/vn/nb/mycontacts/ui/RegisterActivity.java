package vn.nb.mycontacts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import vn.nb.mycontacts.MainActivity;
import vn.nb.mycontacts.R;
import vn.nb.mycontacts.UserManager;
import vn.nb.mycontacts.database.DataManager;
import vn.nb.mycontacts.databinding.ActivityRegisterBinding;
import vn.nb.mycontacts.entity.UserInfo;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataManager = new DataManager(this);
        binding.btnRegister.setOnClickListener(v -> register());
        binding.tvLoginNow.setOnClickListener(v -> finish());

    }

    private void register() {
        String name = binding.edtName.getText().toString().trim();
        String phone = binding.edtPhoneNumber.getText().toString().trim();
        String password = binding.edtPassWord.getText().toString().trim();
        String confirmPassword = binding.edtConfirmPassword.getText().toString().trim();
        String email = binding.edtEmail.getText().toString().trim();

        if (name.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            Toast.makeText(this, "Cannot be left blank", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Constant.isValidEmail(email)) {
            Toast.makeText(RegisterActivity.this, "You need to enter the correct email", Toast.LENGTH_SHORT).show();
            binding.edtEmail.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Re-entered password does not match", Toast.LENGTH_SHORT).show();
            binding.edtConfirmPassword.requestFocus();
            return;
        }
        long insert = dataManager.insertUser(new UserInfo(name, phone, password, email));
        if (insert == 0) {
            Toast.makeText(this, "Account already exists", Toast.LENGTH_SHORT).show();
        } else if (insert == 1) {
            Toast.makeText(this, "Account creation failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Account successfully created", Toast.LENGTH_SHORT).show();
            UserInfo userInfo = dataManager.getUserInfo(phone, password);
            if (userInfo != null) {
                UserManager.getInstance().loadUser(userInfo);
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                intent.putExtra("key",0);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else {
                Toast.makeText(RegisterActivity.this, "Account or password is incorrect!", Toast.LENGTH_SHORT).show();
            }

        }


    }


}