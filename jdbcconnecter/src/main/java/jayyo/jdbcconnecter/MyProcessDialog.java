package jayyo.jdbcconnecter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

@SuppressLint("StaticFieldLeak")
public class MyProcessDialog {
    /**
     * 仿照 單例模式 的概念實作，
     * 初始化時 建立物件 並暫存，
     * 使用時 依需求改變 並顯示，
     * 但是這個做法可能較為吃 記憶體
     */
    private static AlertDialog myAlertDialog;
    private static ProgressBar bar;
    private static TextView message;
    private static ProgressBar circular;

    private MyProcessDialog() {
    }

    public static void initialize(Context context) {
        myAlertDialog = new AlertDialog.Builder(context, R.style.MyAlertDialogTheme).create();
        View loadView = LayoutInflater.from(context).inflate(R.layout.loading_dialog, null);
        myAlertDialog.setView(loadView);
        message = loadView.findViewById(R.id.message);
        bar = loadView.findViewById(R.id.progressBar);
        circular = loadView.findViewById(R.id.progressCircular);
    }

    public static void showMessage(String strText) {
        message.setText(strText);  //寫成區域變數的話，文字不會跟著變
        myAlertDialog.setCanceledOnTouchOutside(false);
        myAlertDialog.show();
    }

    public static void showProgressBar(int size) {
        circular.setVisibility(View.GONE);
        bar.setVisibility(View.VISIBLE);
        bar.setMax(size);
        bar.setProgress(0);
        myAlertDialog.setCanceledOnTouchOutside(false);
        myAlertDialog.show();
    }

    public static void addProgressBar(int size) {
        bar.incrementProgressBy(size);
    }

    public static void dismiss() {
        if (myAlertDialog != null && myAlertDialog.isShowing()) {
            myAlertDialog.dismiss();
        }
    }
}
