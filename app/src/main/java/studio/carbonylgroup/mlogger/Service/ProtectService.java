package studio.carbonylgroup.mlogger.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import studio.carbonylgroup.mlogger.StrongService;
import studio.carbonylgroup.mlogger.Utilities.Utils;


public class ProtectService extends Service {

    private StrongService startS1 = new StrongService.Stub() {

        @Override
        public void stopService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), WatchingService.class);
            getBaseContext().stopService(i);
        }

        @Override
        public void startService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), WatchingService.class);
            getBaseContext().startService(i);

        }
    };

    public void onCreate() {
        keepWatchingService();
    }

    private void keepWatchingService() {

        boolean isRun = Utils.isProcessRunning(ProtectService.this, "studio.carbonylgroup.mlogger.Service:watchingService");
        if (!isRun) {
            try {
                startS1.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onTrimMemory(int level) {
        keepWatchingService();
    }

    public int onStartCommand(Intent intent, int flags, int startId) {

        keepWatchingService();
        return START_STICKY;
    }

    public void onDestroy() {

        super.onDestroy();
        keepWatchingService();
    }

    public IBinder onBind(Intent intent) {
        return (IBinder) startS1;
    }
}
