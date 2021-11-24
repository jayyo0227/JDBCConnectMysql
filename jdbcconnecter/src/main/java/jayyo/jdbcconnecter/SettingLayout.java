package jayyo.jdbcconnecter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.MenuItem;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.EditTextPreference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;

public class SettingLayout extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_layout);
        //設定隱藏狀態，時間、音量、電池、WIFI訊號 等等的狀態列
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
        //設定片段跳轉
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.setting_layout_f, new SettingFragment())
                .commit();

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("設定");
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        getPreferenceSetting(SettingLayout.this);
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent();
            intent.setClass(SettingLayout.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public static void getPreferenceSetting(Context context) {
        String localTag = "getPreferenceSetting";
        try {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            Log.d(localTag, "取得設定 Preference " + preferences);

            JDBConnecter.setIP(preferences.getString("setting_layout_connect_ip", ""));
            JDBConnecter.setPORT(preferences.getString("setting_layout_connect_port", ""));
            JDBConnecter.setDBNAME(preferences.getString("setting_layout_connect_database", ""));
            JDBConnecter.setUSER(preferences.getString("setting_layout_connect_user", ""));
            JDBConnecter.setPASSWORD(preferences.getString("setting_layout_connect_password", ""));
        } catch (Exception e) {
            Log.e(localTag, "Exception:" + e.toString());
        }
    }

    public static class SettingFragment extends PreferenceFragmentCompat {

        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.setting_preference, rootKey);

            /* 設定 EditTextPreference InputType TEXT_PASSWORD */
            final EditTextPreference settings_password = findPreference("setting_layout_connect_password");
            if (settings_password != null) {
                settings_password.setOnBindEditTextListener(editText ->
                        editText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD));
            }
        }
    }
}
