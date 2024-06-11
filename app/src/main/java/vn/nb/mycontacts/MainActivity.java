package vn.nb.mycontacts;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.GridLayoutManager;

import vn.nb.mycontacts.adapter.PagerAdapter;
import vn.nb.mycontacts.adapter.TabAdapter;
import vn.nb.mycontacts.databinding.ActivityMainBinding;
import vn.nb.mycontacts.entity.DataTab;
import vn.nb.mycontacts.interfaces.OnTabListener;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());





        initTab();





    }

    TabAdapter tabAdapter = new TabAdapter();
    private void initTab() {
        binding.recyclerview.setLayoutManager(new GridLayoutManager(MainActivity.this, 4));
        binding.recyclerview.setHasFixedSize(true);

        binding.recyclerview.setAdapter(tabAdapter);

        PagerAdapter adapter = new PagerAdapter(MainActivity.this);
        binding.viewPager.setOffscreenPageLimit(4);
        binding.viewPager.setUserInputEnabled(false);
        binding.viewPager.setSaveEnabled(false);
        binding.viewPager.setAdapter(adapter);
        tabAdapter.updateView(0);




        tabAdapter.setOnTabListener((dataTab, p) -> {
            binding.viewPager.setCurrentItem(p, false);
            tabAdapter.updateView(p);

        });





    }

    public void updateTab(){
        tabAdapter.updateView(1);
        binding.viewPager.setCurrentItem(1, false);
    }




}