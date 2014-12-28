package tw.com.wa.invoice.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;

import tw.com.wa.invoice.R;

/**
 * Created by Andy on 14/12/28.
 */
public class DialogUtil {

    public final static String Setting = "Setting";
    public final static String TEACH_DIALOG_VISIBLE_FLAG = "isVisibleFlag";

    public static void showTeching(final Context context){
        AlertDialog.Builder diaglogOfTech = new AlertDialog.Builder(context);
        diaglogOfTech.setTitle(R.string.teachTitle);
        diaglogOfTech.setMessage(R.string.teachContent);
        diaglogOfTech.setNegativeButton("知道", null);
        diaglogOfTech.setPositiveButton(R.string.noReminder, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SharedPreferences sp =
                        context.getSharedPreferences(Setting, Context.MODE_PRIVATE);
                sp.edit().putBoolean(TEACH_DIALOG_VISIBLE_FLAG, false).commit();
            }
        });
        diaglogOfTech.show();

    }

}
