package yanzhikai.bindertest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "yjkMainActivity";
    private Button btn_bind;

    //定义aidl接口变量
    private IMyAidlInterface mServer;

    //创建ServiceConnection的匿名类
    private ServiceConnection connection = new ServiceConnection() {

        //重写onServiceConnected()方法和onServiceDisconnected()方法
        //在Activity与Service建立关联和解除关联的时候调用
        @Override
        public void onServiceDisconnected(ComponentName name) {
        }

        //在Activity与Service建立关联时调用
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            Log.d(TAG, "onServiceConnected: ");
            //使用AIDLService1.Stub.asInterface()方法获取服务器端返回的IBinder对象
            //将IBinder对象传换成了mAIDL_Service接口对象
            mServer = IMyAidlInterface.Stub.asInterface(service);

            try {
                //通过该对象调用在MyAIDLService.aidl文件中定义的接口方法,从而实现跨进程通信
                mServer.getMsg(0);

            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_bind = findViewById(R.id.btn_bind);
        btn_bind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //通过Intent指定服务端的服务名称和所在包，与远程Service进行绑定
                //参数与服务器端的action要一致,即"服务器包名.aidl接口文件名"
                Intent intent = new Intent("yanzhikai.bindertest.IMyAidlInterface");

                //Android5.0后无法只通过隐式Intent绑定远程Service
                //需要通过setPackage()方法指定包名
                intent.setPackage("yanzhikai.bindertest");

                //绑定服务,传入intent和ServiceConnection对象
                bindService(intent, connection, Context.BIND_AUTO_CREATE);

            }
        });
    }
}
