package com.jdbcconnectmysql;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class Settings_Layout extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_layout);
        //設定隱藏狀態，時間、音量、電池、WIFI訊號 等等的狀態列
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings_layout_f, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(getResources().getString(R.string.dlg_conn_title));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        getAndroidSetting(Settings_Layout.this);
        if (item.getItemId() == android.R.id.home) {
            Intent it = new Intent();
            it.setClass(Settings_Layout.this, MainActivity.class);
            startActivity(it);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    //讀取android設定檔，進行資料傳遞
    public static void getAndroidSetting(Context context) {
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

            Settings_Value.Connect_IP = preferences.getString("settings_layout_connect_ip", "192.168.1.10").trim();
            Settings_Value.Connect_Port = preferences.getString("settings_layout_connect_port", "3306").trim();
            Settings_Value.Connect_Database = preferences.getString("settings_layout_connect_database", "mysql").trim();
            Settings_Value.Connect_User = preferences.getString("settings_layout_connect_user", "root").trim();
            Settings_Value.Connect_Password = preferences.getString("settings_layout_connect_password", "123456789").trim();


        } catch (Exception e) {
            Toast.makeText(context, "獲取設定錯誤，getAndroidSetting:" + e.toString(), Toast.LENGTH_SHORT).show();
            Log.e("KKKK", e.toString());
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.setting_layout, rootKey);

            /* 設定 EditTextPreference InputType textpassword */
            final EditTextPreference settings_password = findPreference("settings_layout_connect_password");
            if (settings_password != null) {
                settings_password.setOnBindEditTextListener(editText ->
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
            }
        }
    }

}