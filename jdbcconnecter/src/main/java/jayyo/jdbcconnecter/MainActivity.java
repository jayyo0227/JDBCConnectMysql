package jayyo.jdbcconnecter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyProcessDialog.initialize(MainActivity.this);
        JDBConnecter.dbThread();
        SettingLayout.getPreferenceSetting(MainActivity.this);

        TextView textView = findViewById(R.id.textView);
        Button connect = findViewById(R.id.connect);
        connect.setOnClickListener(view -> {
            MyProcessDialog.showMessage(getString(R.string.connect_loading));

            new Thread(() -> {
                String status = JDBConnecter.connect();

                runOnUiThread(() -> {
                    textView.setText(status);
                    textView.append("\n");
                    textView.append(JDBConnecter.getData());

                    MyProcessDialog.dismiss();
                });
            }).start();
        });
    }

    @Override
    protected void onDestroy() {
        JDBConnecter.close();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_setting) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), SettingLayout.class);
            startActivity(intent);

            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}