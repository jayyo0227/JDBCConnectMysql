package jayyo.jdbcconnecter;

import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class JDBConnecter {

    // 資料庫定義
    private static String mysql_ip = "192.168.1.10";
    private static String mysql_port = "3306";
    private static String db_name = "mysql";
    private static String db_user = "root";
    private static String db_password = "";

    private static Connection connection;

    public static void dbThread() {
        StrictMode.setThreadPolicy(new StrictMode.
                ThreadPolicy.Builder().
                build());
        StrictMode.setVmPolicy(new StrictMode.
                VmPolicy.Builder().
                build());
    }

//    public void dbThread() {
//        try {
//            StrictMode.setThreadPolicy(new StrictMode.
//                    ThreadPolicy.
//                    Builder().
//                    detectDiskReads().
//                    detectDiskWrites().
//                    detectNetwork().
//                    penaltyLog().
//                    build());
//            StrictMode.setVmPolicy(new StrictMode.
//                    VmPolicy.
//                    Builder().
//                    detectLeakedSqlLiteObjects().
//                    penaltyLog().
//                    penaltyDeath().
//                    build());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    public static String connect() {
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

    public static void close() {
        String localTag = "close";
        try {
            connection.close();
            Log.e(localTag, "遠端連接關閉");
        } catch (Exception e) {
            Log.e(localTag, "Exception:" + e.toString());
        }
    }

    public static void setIP(String ip) {
        mysql_ip = ip;
    }

    public static void setPORT(String port) {
        mysql_port = port;
    }

    public static void setDBNAME(String dbname) {
        db_name = dbname;
    }

    public static void setUSER(String user) {
        db_user = user;
    }

    public static void setPASSWORD(String password) {
        db_password = password;
    }

    public static String getData(){
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
            data = e.toString();//目前考慮要回傳錯誤訊息
            Log.e(localTag, e.toString());
        }
        return data;
    }
}
