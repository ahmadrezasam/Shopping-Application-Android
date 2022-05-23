package com.paradox.ecommerce;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    Button btnPref;
    Button btnLogOut;
    ImageView back;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        btnPref=findViewById(R.id.btnPref);
        btnLogOut=findViewById(R.id.btnLogOut);
        back=findViewById(R.id.back);
        tv=findViewById(R.id.settingsTv);

        btnPref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPref.setVisibility(View.INVISIBLE);
                btnLogOut.setVisibility(View.INVISIBLE);
                tv.setVisibility(View.INVISIBLE);


                getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.linearLayout, new SettingsFragment())
                            .commit();
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, Login.class);
                startActivity(i);
                finish();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SettingsActivity.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }
}