package studio.carbonylgroup.mlogger.Utilities;

import android.content.Context;
import android.content.SharedPreferences;


import studio.carbonylgroup.mlogger.R;

public class Utils {

    public String readURL(Context context) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.settings_key), Context.MODE_PRIVATE);
        return sharedPreferences.getString(context.getString(R.string.url_key), context.getString(R.string.default_url));
    }

    public String readString(Context context, String pref) {

        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.settings_key), Context.MODE_PRIVATE);
        return sharedPreferences.getString(pref, "");
    }

    public void setAllSettings(Context context, String _IP, String _UN, String _PW) {

        SharedPreferences.Editor editor = context.getSharedPreferences(context.getString(R.string.settings_key), Context.MODE_PRIVATE).edit();
        editor.putString(context.getString(R.string.url_key), _IP)
                .putString(context.getString(R.string.un_key), _UN)
                .putString(context.getString(R.string.pw_key), _PW)
                .apply();
    }
}
