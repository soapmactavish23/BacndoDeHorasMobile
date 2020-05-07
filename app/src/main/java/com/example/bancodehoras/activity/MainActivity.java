package com.example.bancodehoras.activity;

import android.os.Bundle;

import com.example.bancodehoras.R;
import com.example.bancodehoras.framents.BancoHorasFragment;
import com.example.bancodehoras.framents.FolgaFragment;
import com.example.bancodehoras.framents.FuncionariosFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;


public class MainActivity extends AppCompatActivity {

    private SmartTabLayout smartTabLayout;
    private ViewPager viewPager;
    private FuncionariosFragment funcionariosFragment = new FuncionariosFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setElevation(0);

        //SmartTabLayout
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Funcionários", FuncionariosFragment.class)
                .add("Banco de Horas", BancoHorasFragment.class)
                //.add("Folgas", FolgaFragment.class)
                .create());

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        smartTabLayout = (SmartTabLayout) findViewById(R.id.viewpagertab);
        smartTabLayout.setViewPager(viewPager);

    }
}
