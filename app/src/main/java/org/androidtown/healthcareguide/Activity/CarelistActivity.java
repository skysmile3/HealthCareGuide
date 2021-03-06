package org.androidtown.healthcareguide.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import org.androidtown.healthcareguide.Fragment.CareMeFragment;
import org.androidtown.healthcareguide.Fragment.CaredPeopleFragment;
import org.androidtown.healthcareguide.Model.User;
import org.androidtown.healthcareguide.R;

public class CarelistActivity extends AppCompatActivity {

    private Button caredPeopleButton;
    private Button careMeButton;
    private Fragment caredPeopleFragment;
    private Fragment careMeFragment;
    private FrameLayout container;
    public String uid;
    public String email;
    public String name;
    public static User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carelist);

        setCurrentUser();
        initView(savedInstanceState);
    }

    public void setCurrentUser(){
        Intent intent = getIntent();
        uid = intent.getStringExtra("uid");
        email = intent.getStringExtra("email");
        name = intent.getStringExtra("name");
        currentUser = new User();
        currentUser.setUid(uid);
        currentUser.setEmail(email);
        currentUser.setName(name);
    }

    public void initView(Bundle savedInstanceState){
        getSupportActionBar().hide();
        caredPeopleFragment = new CaredPeopleFragment();
        careMeFragment = new CareMeFragment();

        container = findViewById(R.id.carelist_container);
        caredPeopleButton = findViewById(R.id.cared_people_button);
        careMeButton = findViewById(R.id.care_me_button);
        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.carelist_container,caredPeopleFragment);
            ft.add(R.id.carelist_container,careMeFragment);
            ft.hide(careMeFragment);
            ft.show(caredPeopleFragment);
            ft.commit();
        }

        caredPeopleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .hide(careMeFragment)
                        .show(caredPeopleFragment)
                        .commit();
            }
        });

        careMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out)
                        .hide(caredPeopleFragment)
                        .show(careMeFragment)
                        .commit();
            }
        });
    }
}
