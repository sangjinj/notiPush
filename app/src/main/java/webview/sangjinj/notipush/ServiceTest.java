package webview.sangjinj.notipush;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by sangjin on 2016-07-18.
 */
public class ServiceTest extends Service implements Runnable {
    // 시작 ID
    private int mStartId;
    // 서비스에 대한 스레드에 연결된 Handler. 타이머 이용한 반복 처리시 사용.
    private Handler mHandler;
    // 서비스 동작여부 flag
    private boolean mRunning;
    // 타이머 설정 (2초)
    private static final int TIMER_PERIOD = 10 * 1000;
    private static final int COUNT = 10000;
    private int mCounter;


    // 서비스를 생성할 때 호출
    public void onCreate() {
        Log.d("MyService", "Service Created.");
        super.onCreate();
        mHandler = new Handler();
        mRunning = false;
    }

    // 서비스 시작할 때 호출. background에서의 처리가 시작됨.
    // startId : 서비스 시작요구 id. stopSelf에서 종료할 때 사용.

    //onStart는 여러번 호출될 수 있기 때문에 식별자로 사용.

    public void onStart(Intent intent, int startId) {
        Log.e("MyService", "Service startId = " + startId);
        super.onStart(intent, startId);
        mStartId = startId;
        mCounter = COUNT;

        // 동작중이 아니면 run 메소드를 일정 시간 후에 시작
        if (!mRunning) {
            // this : 서비스 처리의 본체인 run 메소드. Runnable 인터페이스를 구현 필요.
            // postDelayed : 일정시간마다 메소드 호출
            mHandler.postDelayed(this, TIMER_PERIOD);
            mRunning = true;
        }
    }



    // 서비스의 종료시 호출
    public void onDestroy() {
        // onDestroy가 호출되어 서비스가 종료되어도
        // postDelayed는 바로 정지되지 않고 다음 번 run 메소드를 호출.
        mRunning = false;
        super.onDestroy();
    }
    @Override
    public void run() {
        if (!mRunning) {
            // 서비스 종료 요청이 들어온 경우 그냥 종료
            Log.e("MyService", "run after destory");




            return;
       // } else if (--mCounter <= 0) {
        //    // 지정한 횟수 실행하면 스스로 종료
        //   Log.e("MyService", "stop Service id = "+mStartId);
        //   stopSelf(mStartId);
        } else {
            // 다음 작업을 다시 요구
            Log.e("MyService", "mCounter : " + mCounter);
            int icon = R.drawable.tour_logo;
            long when = System.currentTimeMillis();
            Random random = new Random();
            int rNum = random.nextInt(100);
            Intent intent = new Intent(this, notipushActivity.class);
            PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // NotificationCompat.Builder b = new NotificationCompat.Builder(context);
            //Notification notification = new Notification(R.drawable.jellybean, "Nomal Notification", System.currentTimeMillis());
            //Notification notification = new Notification(R.drawable.icon_spot, "일정이 얼마 남지 않았습니다. ", System.currentTimeMillis());

            Notification notif = new Notification.Builder(this)
                    .setAutoCancel(true)
                    .setContentTitle("투어메이트 일정 알림")
                    .setContentText("제목")
                    .setSmallIcon(R.drawable.icon_spot)
                    .setContentIntent(contentIntent)
                    //.setLargeIcon(R.drawable.jellybean)
                    .setStyle(new Notification.BigTextStyle()
                            .bigText("이건 테스트로 보냄 "+rNum))
                    .build();



            // flag를 설정합니다
            // FLAG_AUTO_CANCEL : 알림(터치하면 지워짐), FLAG_ONGOING_EVENT : 진행중(게속 표시)
            //notification.flags = Notification.FLAG_AUTO_CANCEL;

//        b.setAutoCancel(true)
//
//                .setDefaults(Notification.DEFAULT_ALL)
//                .setWhen(System.currentTimeMillis())
//                .setSmallIcon(icon)
//                .setTicker("TourMate")
//                .setContentTitle("일정알림")
//                .setContentText(message)
//                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
//                .setContentIntent(contentIntent)
//                .setContentInfo("Info");

            NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);


            System.out.println("rNum :::::::::::::::::::::: " + rNum);
            notificationManager.notify(rNum, notif);


            mHandler.postDelayed(this, TIMER_PERIOD);

        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
