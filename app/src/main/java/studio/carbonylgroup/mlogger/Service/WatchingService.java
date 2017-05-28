package studio.carbonylgroup.mlogger.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import studio.carbonylgroup.mlogger.StrongService;
import studio.carbonylgroup.mlogger.Utilities.Utils;


public class WatchingService extends Service {

    private StrongService startS2 = new StrongService.Stub() {
        @Override
        public void stopService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), ProtectService.class);
            getBaseContext().stopService(i);
        }

        @Override
        public void startService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), ProtectService.class);
            getBaseContext().startService(i);
        }
    };

    public void onCreate() {
        keepProtectService();
    }

    private void keepProtectService() {

        boolean isRun = Utils.isProcessRunning(WatchingService.this, "studio.carbonylgroup.mlogger.Service:protectService");
        if (!isRun) {
            try {
                startS2.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onTrimMemory(int level) {

        keepProtectService();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        keepProtectService();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {

        super.onDestroy();
        keepProtectService();
    }

    public IBinder onBind(Intent intent) {

        return (IBinder) startS2;
    }
}
