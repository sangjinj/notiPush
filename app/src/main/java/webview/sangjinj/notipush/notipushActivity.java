package webview.sangjinj.notipush;

        import android.app.Activity;
        import android.app.Notification;
        import android.app.NotificationManager;
        import android.app.PendingIntent;
        import android.content.Context;
        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.support.v4.app.NotificationCompat;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
/*
        안드로이드 로컬에서 사용하는 databases이다.
        - 실제 스마트폰 단말기 내의 data/data/databases경로에 파일이 만들어지게된다.
           에뮬레이터를 실행하고 DDMS에 들어가면 파일 구조를 볼수 있다.(실제 핸드폰으로 연결하면 파일구조를 다 보지 못하도록 막혀있다.)
        - 안드로이드 스마트폰은 보안상 접근을 허용하지 않는다. 따라서 개발자가 만일 sqlite 파일등을 스마트폰에 넣으려 할때,
          gui모드로 지원되는 DDMS는 사용할 수 없다.DDMS는 에뮬레이터에서만 의미가 있다.
          안드로이드의 sqlite 관련 패키지에는 개발자가 데이터베이스 관리할 수 있는 여러 객체들을 지원해주므로,
          실제 개발시엔 GUI모드로 하지말고 프로그래밍으로 처리한다.
*/



public class notipushActivity extends Activity implements View.OnClickListener{

        String a="";
        Button btNoti;

        protected void onCreate(Bundle savedInstanceState) {

            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_notipush);


            btNoti = (Button) findViewById(R.id.btNoti);
            btNoti.setOnClickListener(this);





        }

    private static void generateNotification(Context context, String message) {

        int icon = R.drawable.tour_logo;
        long when = System.currentTimeMillis();

        Intent intent = new Intent(context, notipushActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder b = new NotificationCompat.Builder(context);
        //Notification notification = new Notification(R.drawable.jellybean, "Nomal Notification", System.currentTimeMillis());
        //Notification notification = new Notification(R.drawable.icon_spot, "일정이 얼마 남지 않았습니다. ", System.currentTimeMillis());

        // flag를 설정합니다
        // FLAG_AUTO_CANCEL : 알림(터치하면 지워짐), FLAG_ONGOING_EVENT : 진행중(게속 표시)
        //notification.flags = Notification.FLAG_AUTO_CANCEL;

        b.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(icon)
                .setTicker("TourMate")
                .setContentTitle("일정알림")
                .setContentText(message)
                .setDefaults(Notification.DEFAULT_LIGHTS| Notification.DEFAULT_SOUND)
                .setContentIntent(contentIntent)
                .setContentInfo("Info");


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, b.build());

    }

    @Override
    public void onClick(View view) {

        //데이터베이스 구축 전담 객체를 사용하여 sqlite db를 생성
        MyOpenHelper helper = new MyOpenHelper(this);
        TextView tb = (TextView)findViewById(R.id.a);

        System.out.println(" getReadableDatabase --------------------------- "+helper.getReadableDatabase());

        //생성자 호출만으로는 db구축이 안되고 있다.
        //실제 db구축이 되는 시점은 SQLiteDatabase객체를
        //얻기위해 호출되는 메서드인 getReadableDatabase(),
        //getWritableDatabase()메서드를 호출하는 시점이다.
        //실제 스마트폰 단말기 내의 data/data/databases경로에 파일이 만들어지게된다.

        //SQLiteDatabase db = helper.getWritableDatabase();
        SQLiteDatabase db = helper.getReadableDatabase();
        // db.execSQL("insert into member(name, pwd) values ('sangjinj01', 'pwd01');");


        Cursor rs = db.rawQuery("select * from member;", null);
        //Cursor rs = db.rawQuery("select count(*) as memCnt from member;", null);

        while(rs.moveToNext()){
            System.out.println("name ::::::::::::::::: "+rs.getString(0));
            System.out.println("pwd ::::::::::::::::: "+rs.getString(1));
            a = a+rs.getString(0)+"::: "+rs.getString(1)+" \n";
            //tb.setText(a);

        }
        tb.setText(a);
        db.close();


        generateNotification(this, a);
    }
}
