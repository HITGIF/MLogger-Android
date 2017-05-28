package studio.carbonylgroup.mlogger.Utilities;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

import studio.carbonylgroup.mlogger.R;

public class Utils {

    /**
     * Determine whether the process is running
     *
     * @return boolean
     */
    public static boolean isProcessRunning(Context context, String processName) {

        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        List<RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for (RunningAppProcessInfo info : lists) {
            if (info.processName.equals(processName)) {
                isRunning = true;
            }
        }

        return isRunning;
    }

    public String readURL(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.settings_key), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.url_key), context.getString(R.string.default_url));
    }

    public String readString(Context context, String pref) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.settings_key), Context.MODE_PRIVATE);
        return sharedPreferences.getString(pref, "");
    }

    public boolean readOnBoot(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.settings_key), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(R.string.ob_key), true);
    }

    public void setAllSettings(Context context, String _IP, String _UN, String _PW, boolean _OB) {

        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.settings_key), Context.MODE_PRIVATE).edit();
        editor.putString(context.getString(R.string.url_key), _IP)
                .putString(context.getString(R.string.un_key), _UN)
                .putString(context.getString(R.string.pw_key), _PW)
                .putBoolean(context.getString(R.string.ob_key), _OB)
                .apply();
    }
}
