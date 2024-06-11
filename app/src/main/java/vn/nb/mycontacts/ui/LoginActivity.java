package vn.nb.mycontacts.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import vn.nb.mycontacts.MainActivity;
import vn.nb.mycontacts.R;
import vn.nb.mycontacts.UserManager;
import vn.nb.mycontacts.database.DataManager;
import vn.nb.mycontacts.databinding.ActivityLoginBinding;
import vn.nb.mycontacts.entity.UserInfo;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private DataManager dataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        dataManager = new DataManager(this);
        binding.tvRegister.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this, RegisterActivity.class)));

        binding.btnLogin.setOnClickListener(v -> {
            String tk = binding.edtUser.getText().toString().trim();
            String pass = binding.edtPassWord.getText().toString().trim();

            if (tk.isEmpty() || pass.isEmpty()) {
                Toast.makeText(LoginActivity.this, "Please fill in all information", Toast.LENGTH_SHORT).show();
                return;
            }
            UserInfo userInfo = dataManager.getUserInfo(tk, pass);
            if (userInfo != null) {
                UserManager.getInstance().loadUser(userInfo);
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("key",0);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else{
                Toast.makeText(LoginActivity.this, "Account or password is incorrect!", Toast.LENGTH_SHORT).show();

            }

        });
    }
}