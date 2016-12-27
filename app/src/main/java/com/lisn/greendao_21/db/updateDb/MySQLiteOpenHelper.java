package com.lisn.greendao_21.db.updateDb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import user.db.DaoMaster;

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper {
    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");

        //注意：class只需要添加已经存在的表，新增的表不需要添加，目的是保存老数据防止丢失
//        MyMigrationHelper.getInstance().migrate(db,StudentDao.class,TeacherDao.class,LessonDao.class,HistoryDao.class,testDao.class,weatherDao.class);
    }
}