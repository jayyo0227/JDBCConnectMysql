package com.jdbcconnectmysql;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Settings_Layout.getAndroidSetting(MainActivity.this);

        textResult = findViewById(R.id.textResult);

        Button startConnection = findViewById(R.id.startConnection);
        startConnection.setOnClickListener(v -> {
            Dialog dialog = new Dialog(MainActivity.this);
            dialog.setTitle(getString(R.string.dlg_conn_title));
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dlg_connection);
            dialog.show();

            EditText settingIP = dialog.findViewById(R.id.dlg_conn_edit_ip);
            EditText settingPort = dialog.findViewById(R.id.dlg_conn_edit_port);
            EditText settingDatabase = dialog.findViewById(R.id.dlg_conn_edit_database);
            EditText settingUser = dialog.findViewById(R.id.dlg_conn_edit_user);
            EditText settingPassword = dialog.findViewById(R.id.dlg_conn_edit_password);

            Button settingCancel = dialog.findViewById(R.id.dlg_cancel);
            settingCancel.setOnClickListener(v1 -> dialog.dismiss());

            Button settingComfirm = dialog.findViewById(R.id.dlg_comfirm);
            settingComfirm.setOnClickListener(v12 -> {
                if (settingIP.getText().toString().trim().equals("")
                        || settingPort.getText().toString().trim().equals("")
                        || settingDatabase.getText().toString().trim().equals("")
                        || settingUser.getText().toString().trim().equals("")
                        || settingPassword.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(), "欄位不能為空", Toast.LENGTH_SHORT).show();
                    return;
                }

                dialog.dismiss();

                new Thread(() -> {
                    MysqlConnecter conn = new MysqlConnecter();

                    conn.setIP(settingIP.getText().toString().trim());
                    conn.setPORT(settingPort.getText().toString().trim());
                    conn.setDBNAME(settingDatabase.getText().toString().trim());
                    conn.setUSER(settingUser.getText().toString().trim());
                    conn.setPASSWORD(settingPassword.getText().toString().trim());

                    String status = conn.connect();

                    String data = conn.getData();

                    runOnUiThread(() -> {
                        textResult.setText(status);
                        textResult.append("\n");
                        textResult.append(data);
                    });
                }).start();
            });
        });

        new Thread(() -> {
            MysqlConnecter conn = new MysqlConnecter();

            conn.setIP(Settings_Value.Connect_IP);
            conn.setPORT(Settings_Value.Connect_Port);
            conn.setDBNAME(Settings_Value.Connect_Database);
            conn.setUSER(Settings_Value.Connect_User);
            conn.setPASSWORD(Settings_Value.Connect_Password);

            String status = conn.connect();

            String data = conn.getData();

            runOnUiThread(() -> {
                textResult.setText(status);
                textResult.append("\n");
                textResult.append(data);
            });
        }).start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.item_Setting) {
            Intent it = new Intent();
            it.setClass(MainActivity.this, Settings_Layout.class);
            startActivity(it);
            finish();
        }

        if (item.getItemId() == R.id.reConnect) {
            new Thread(() -> {
                MysqlConnecter conn = new MysqlConnecter();

                conn.setIP(Settings_Value.Connect_IP);
                conn.setPORT(Settings_Value.Connect_Port);
                conn.setDBNAME(Settings_Value.Connect_Database);
                conn.setUSER(Settings_Value.Connect_User);
                conn.setPASSWORD(Settings_Value.Connect_Password);

                String status = conn.connect();

                String data = conn.getData();

                runOnUiThread(() -> {
                    textResult.setText(status);
                    textResult.append("\n");
                    textResult.append(data);
                });
            }).start();
        }

        return super.onOptionsItemSelected(item);
    }


}