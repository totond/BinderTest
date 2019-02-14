package yanzhikai.bindertest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

public class ServerService extends Service {
    public static final String TAG = "yjkServerService";
    private Binder mBinder = new IMyAidlInterface.Stub(){

        @Override
        public void getMsg(int type) throws RemoteException {
            Log.d(TAG, "getMsg: " + type);
        }

        @Override
        public boolean checkMsg(int type) throws RemoteException {
            return false;
        }

        @Override
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            //在这里可以做权限认证，通过`Service.getCallingUid()`获取调用它的进程，判断包名是否允许执行
            return super.onTransact(code, data, reply, flags);
        }
    };

    public ServerService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate: ");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: ");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        //在onBind()返回继承自Binder的Stub类型的Binder，非常重要
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

}
