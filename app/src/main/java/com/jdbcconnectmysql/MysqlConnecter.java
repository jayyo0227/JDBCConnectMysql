package com.jdbcconnectmysql;

import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlConnecter {

    // 資料庫定義
    String mysql_ip = "localhost";
    String mysql_port = "3306";
    String db_name = "mysql";
    String db_user = "root";
    String db_password = "123456789";

    private Connection connection;

    public void dbThread() {
        try {
            StrictMode.setThreadPolicy(new StrictMode.
                    ThreadPolicy.
                    Builder().
                    detectDiskReads().
                    detectDiskWrites().
                    detectNetwork().
                    penaltyLog().
                    build());
            StrictMode.setVmPolicy(new StrictMode.
                    VmPolicy.
                    Builder().
                    detectLeakedSqlLiteObjects().
                    penaltyLog().
                    penaltyDeath().
                    build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String connect() {
        String localTag = "connect";
        String connectStatus = "未連線"; //除非成功連線，不然回傳狀態目前都為未連線
        try {
            // 加載驅動
            Class.forName("com.mysql.jdbc.Driver");
            Log.d(localTag, "加載驅動成功");

            String url = "jdbc:mysql://" + mysql_ip + ":" + mysql_port + "/" + db_name +
                    "?useUnicode=yes" +
                    "&characterEncoding=UTF-8" +
                    "&connectTimeout=10000" +
                    "&socketTimeout=10000";
            Log.d(localTag, "url:" + url);

            connection = DriverManager.getConnection(url, db_user, db_password);
            Log.d(localTag, "遠端連接成功");

            connectStatus = "已連線";
        } catch (Exception e) {
            Log.e(localTag, "Exception:" + e.toString());
        }

        Log.d(localTag, "connectStatus:" + connectStatus);
        return connectStatus;
    }

    public String getData(){
        String localTag = "getData";
        String data = "";
        // 下SQL執行
        try {
            Statement st = connection.createStatement();

            String sql = "SELECT * FROM help_keyword";
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("help_keyword_id");
                String name = rs.getString("name");

                data += id + ", " + name + "\n";
            }

            rs.close();
            st.close();
        } catch (Exception e){
            Log.e(localTag, e.toString());
        }
        return data;
    }

    public String test(){
        String localTag = "test";
        Connection conn = null;
        String data = "";

        // 加載驅動
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Log.d(localTag, "加載驅動成功");
        } catch (ClassNotFoundException e) {
            Log.e(localTag, "加載驅動失敗");
        }

        // 連接資料庫
        try {
            String url = "jdbc:mysql://" + mysql_ip + ":" + mysql_port +
                    "/mysql" +
                    "?useUnicode=yes" +
                    "&characterEncoding=UTF-8" +
                    "&connectTimeout=10000" +
                    "&socketTimeout=10000";

            conn = DriverManager.getConnection(url, db_user, db_password);
            Log.d(localTag, "遠端連接成功");
        } catch (SQLException e) {
            Log.e(localTag, "遠端連接失敗");
            Log.e(localTag, e.toString());
            return "";
        }

        // SQL指令
        try {
            String sql = "SELECT * FROM help_keyword";

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                String id = rs.getString("help_keyword_id");
                String name = rs.getString("name");

                data += id + ", " + name + "\n";
            }
            Log.d(localTag, "測試資料：" + data);

            rs.close();
            st.close();
            conn.close();
        } catch (Exception e){
            Log.e(localTag, e.toString());
        }
        return data;
    }

    public void setIP(String ip) {
        mysql_ip = ip;
    }
    public void setPORT(String port) {
        mysql_port = port;
    }
    public void setDBNAME(String dbname) {
        db_name = dbname;
    }
    public void setUSER(String user) {
        db_user = user;
    }
    public void setPASSWORD(String password) {
        db_password = password;
    }
}
