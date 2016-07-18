package webview.sangjinj.notipush;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sangjin on 2016-07-18.
 */
public class MyOpenHelper extends SQLiteOpenHelper {

    Context context;

    public MyOpenHelper(Context context) {
        //2번째 인자는 만들어지는 sqlite파일 이름이고 4번째 인자는 개발자가 만든 sqlite3버전이다.
        super(context, "sangjinj1.sqlite", null, 1);
        this.context = context;
    }
    //최초에 기존에 없었던 db가 새롭게 만들어질때 1번 호출
    public void onCreate(SQLiteDatabase db) {
        System.out.println("######################### onCreate ##################################");
        db.execSQL("CREATE TABLE member (name VARCHAR(20), pwd VARCHAR(50));");

        // 최초 디비가 생성 될때 초기 데이터를 입력 한다.
        db.execSQL("insert into member(name, pwd) values ('sangjinj01', 'pwd01');");
        db.execSQL("insert into member(name, pwd) values ('sangjinj02', 'pwd02');");
        db.execSQL("insert into member(name, pwd) values ('sangjinj03', 'pwd03');");
        db.execSQL("insert into member(name, pwd) values ('sangjinj04', 'pwd04');");
        db.execSQL("insert into member(name, pwd) values ('sangjinj05', 'pwd05');");
        db.execSQL("insert into member(name, pwd) values ('sangjinj06', 'pwd06');");
        db.execSQL("insert into member(name, pwd) values ('sangjinj07', 'pwd07');");
        db.execSQL("insert into member(name, pwd) values ('sangjinj08', 'pwd08');");
        db.execSQL("insert into member(name, pwd) values ('sangjinj09', 'pwd09');");
        db.execSQL("insert into member(name, pwd) values ('sangjinj10', 'pwd10');");
    }

    //이미 배포했던 db에 변경이 있을경우 호출된다.
    //주로 버전의 변경이 있을때 호출
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("######################### onUpgrade ##################################");
        db.execSQL("CREATE TABLE member (name VARCHAR(20), pwd VARCHAR(50));");
    }

}
