package studio.carbonylgroup.mlogger.Utilities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import studio.carbonylgroup.mlogger.R;
import studio.carbonylgroup.mlogger.Service.WatchingService;


public class CustomBroadcastReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(final Context context, Intent intent) {

        final String action = intent.getAction();
        if (action.equals("android.intent.action.BOOT_COMPLETED"))
            if (context.getSharedPreferences(context.getString(R.string.settings_key), Context.MODE_PRIVATE).getBoolean(context.getString(R.string.ob_key), true))
                context.startService(new Intent(context, WatchingService.class));

        if (action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION))
            if (ConnectivityManager.TYPE_WIFI == ((NetworkInfo) intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO)).getType())
                if (((WifiManager) context.getApplicationContext().getSystemService(Context.WIFI_SERVICE)).
                        getConnectionInfo().getSSID().equals(context.getString(R.string.maple_leaf_ssid))) {
                    final Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try{
                            Thread.sleep(1000);
                            loginWifiAccount(context);
                            } catch (Exception e){

                            }
                        }
                    });
                    thread.start();
                }

    }

    private void loginWifiAccount(Context context) {

        Utils utils = new Utils();
        HttpClient httpClient = new DefaultHttpClient();
        HttpConnectionParams.setConnectionTimeout(httpClient.getParams(), 10000);
        HttpConnectionParams.setSoTimeout(httpClient.getParams(), 10000);
        HttpPost httpPost = new HttpPost(utils.readURL(context));
        List<NameValuePair> nameValuePairs = new ArrayList<>();
        nameValuePairs.add(new BasicNameValuePair("une", utils.readString(context, context.getString(R.string.un_key))));
        nameValuePairs.add(new BasicNameValuePair("passwd", utils.readString(context, context.getString(R.string.pw_key))));
        nameValuePairs.add(new BasicNameValuePair("username", utils.readString(context, context.getString(R.string.un_key))));
        nameValuePairs.add(new BasicNameValuePair("pwd", utils.readString(context, context.getString(R.string.pw_key))));
        // etc...
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpClient.execute(httpPost);
            BufferedReader br = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            String r=br.readLine();
            while(r!=null){
                Log.d("[][][", "没炸" + r);
                r=br.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("[][][", "炸了");
        }
    }

}